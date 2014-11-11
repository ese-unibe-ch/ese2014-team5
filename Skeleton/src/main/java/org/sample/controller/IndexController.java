package org.sample.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SampleService;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.User;
import org.sample.model.Enquiry;
import org.sample.model.Notifies;
import org.sample.model.dao.NotifiesDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

    @Autowired
    ServletContext servletContext;
    @Autowired
    SampleService sampleService;
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/siteowner", method = RequestMethod.GET)
    public ModelAndView siteowner() {
        ModelAndView model = new ModelAndView("siteowner");
        return model;
    }
    
    @RequestMapping(value = "/getnotifications", method = RequestMethod.GET)
    public @ResponseBody String getNotifications() {
    	Iterable<Notifies> notifications = (Iterable<Notifies>) sampleService.findNotificationsForUser(sampleService.getLoggedInUser());
    	
    	    	
    	String result = "{\"Notifications\":[";
    	Iterator<Notifies> iterator = notifications.iterator();
    	while(iterator.hasNext())
    	{
    		Notifies note = iterator.next();
    		
    		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    		String date = dateFormat.format(note.getDate());
    		String txt = "";
    		String url = "";
    		try
    		{
	    		switch(note.getNotetype())
	    		{
	    			case BOOKMARK:
	    				txt = "Bookmarked Ad " + note.getBookmark().getAd().getTitle() + " has changed - <span class='beside'>" + date +"</span>";
	    				url = "showad?value=" + note.getBookmark().getAd().getId();
	    				break;
	    			case MESSAGE:
	    				txt = note.getText() + " - " + "<span class='beside'>" + date +"</span>";
	    				break;
	    			case ENQUIRY:
	    				txt = "New enquiry received from " + note.getFromUser().getUsername() + " - <span class='beside'>" + date +"</span>";
	    				url = "";
	    				break;
	    			case SEARCHMATCH:
	    				txt = "New matching ad for your search! - <span class='beside'>" + date +"</span>";
	    				url = "";
	    				break;
	    			default:
	    				txt =  note.getText() + " - <span class='beside'>" + date +"</span>";
	    		}
	    		result += "{\"id\": " + note.getId() + ",\"text\":\"" + txt + "\",\"url\":\"" + url + "\",\"read\":" + note.getSeen() + "}";
	    		if(iterator.hasNext())
	    		{
	    			result += ",";
	    		}
    		}
    		catch(NullPointerException e)
    		{
    			
    		}
    		
    	}
    	result += "]}";
        return result;
    }
    
    @RequestMapping("/setread")
	public @ResponseBody String setread(Model model, @RequestParam String noteid) {
		sampleService.setRead(noteid);
		return "#";
	}
    
    @RequestMapping("/sendenquiry")
	public ModelAndView sendenquiry(@RequestParam String enquirytext,@RequestParam String adid) {
    	ModelAndView model = new ModelAndView("showad");
    	model.addObject("ad", sampleService.getAd(Long.parseLong(adid))); 
		Enquiry enq = (Enquiry) sampleService.sendEnquiry(enquirytext,adid);
		if(enq!=null)
		{
			if(sampleService.createNotificationEnquiry(enq))
				model.addObject("msg", "Your enquiry has been sent successfully.");
			else
				model.addObject("page_error", "Error! Couldn't send enquiry. Try again.");
		}
		return model;
	}
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
    	ModelAndView model = new ModelAndView("index");
    	SearchForm searchForm = new SearchForm();
    	model.addObject("searchForm", searchForm);
    	model.addObject("currentUser", (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    	model.addObject("minPrice",searchForm.getFromPrice());
		model.addObject("maxPrice",searchForm.getToPrice());
		model.addObject("minSize",searchForm.getFromSize());
		model.addObject("maxSize",searchForm.getToSize());
    	return model;
    }

    /*Core of the page, starting point,search and search output in one
     * is also containing an extended version of this search and a map version of the search */
    @RequestMapping(value = "/index")
    public ModelAndView index(@Valid SearchForm searchForm, @RequestParam(required = false) String action, BindingResult result, RedirectAttributes redirectAttributes) {

    	ModelAndView model = new ModelAndView("index");
    	model.addObject("currentUser", (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    	model.addObject("searchResults", sampleService.findAds(searchForm));
    	
    	//model.addObject("notifications",sampleService.findNotificationsForUser(sampleService.getLoggedInUser()));
    	boolean saveToProfile = false;
    	if(action!=null && action.equals("bsave")){
    		saveToProfile = true;
    	}
    	
    	//When one of the save buttons is clicked, save search.
    	if(action!=null){
    		if (!result.hasErrors()) {
	            try {
	            	sampleService.saveFromSearch(searchForm, saveToProfile);
	            	if(sampleService.getLoggedInUser() != null && sampleService.getLoggedInUser().getUserRole().getRole() == 1 && saveToProfile){
	            		model.addObject("msg", "You can find your saved search in your profile.");
	            	}
	            } catch (InvalidSearchException e) {
	            	if(sampleService.getLoggedInUser() != null && sampleService.getLoggedInUser().getUserRole().getRole() == 1 && saveToProfile){
	            		model.addObject("page_error", e.getMessage());
	            	}
	            }
	        }
    	}
    	
    	if(action!=null && action.equals("bmap"))
    	{
    		model.addObject("displayMap",1);
    		model.addObject("hasResults", 1);
    	}
    	else if(action!=null && action.equals("blist"))
    	{
    		model.addObject("displayMap",0);
    		model.addObject("hasResults", 1);
    	}
    	
    	
		model.addObject("minPrice",searchForm.getFromPrice());
		model.addObject("maxPrice",searchForm.getToPrice());
		model.addObject("minSize",searchForm.getFromSize());
		model.addObject("maxSize",searchForm.getToSize());
		
        return model;
    }

    @RequestMapping(value = "/adcreation", method = RequestMethod.GET)
    public ModelAndView adcreation() {
        ModelAndView model = new ModelAndView("adcreation");
        model.addObject("adCreationForm", new AdCreateForm());
        return model;
    }

    @RequestMapping(value = "/addediting", method = RequestMethod.GET)
    public ModelAndView addediting(@RequestParam("value") Long id) {
        ModelAndView model = new ModelAndView("addediting");
        //AdCreateForm addUpdateForm = new AdCreateForm();
        model.addObject("adCreateForm", new AdCreateForm());
        model.addObject("currentUser", sampleService.getLoggedInUser());
        model.addObject("currentAdd", sampleService.getAd(id));
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Advert ad = sampleService.getAd(id);
        try {
        	model.addObject("fromDate", formatter.format(ad.getFromDate()));
        	model.addObject("toDate", formatter.format(ad.getToDate()));
        }catch(Exception e)
        {
        	
        }
        model.addObject("idstring",id);
        //sampleService.updateAds(addUpdateForm, id)
        return model;
    }

    @RequestMapping(value = "/addUpdate") 
    public ModelAndView addUpdate(@Valid AdCreateForm adCreateForm , @RequestParam("id") String idstring, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView model;
        if (!result.hasErrors()) {
            try {
                Long id = Long.parseLong(idstring);
                sampleService.updateAd(adCreateForm,id);
                 model = new ModelAndView("profileadverts");
                 model.addObject("currentUser", sampleService.getLoggedInUser());
         	     model.addObject("adList", sampleService.findAdsForUser((User) sampleService.getLoggedInUser()));
         	     sampleService.sendNotificationsForBookmarks(sampleService.findBookmarksForAd(id));
            } catch (InvalidAdException e) {
                model = new ModelAndView("addediting");
                model.addObject("page_error", e.getMessage());
            }
        } else {
            model = new ModelAndView("addediting");
        }
        return model;
    }

    @RequestMapping(value = "/showad")
    public ModelAndView showad(@Valid BookmarkForm bookmarkForm, @RequestParam("value") Long id, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model = new ModelAndView("showad");
    	model.addObject("currentUser", sampleService.getLoggedInUser());
    	model.addObject("ad", sampleService.getAd(id));    	
    	if(sampleService.checkBookmarked(id,sampleService.getLoggedInUser()))
    	{
    		model.addObject("bookmarked", 1);
    	}
    	if(bookmarkForm!=null && bookmarkForm.getAdNumber()!=null && !bookmarkForm.getAdNumber().equals(""))
    	{
    		Long bookmarkid = sampleService.bookmark(bookmarkForm);
    		if(bookmarkid>0)
    			model.addObject("bookmarked", 1);
    	}
    	
    	if(sampleService.checkSentEnquiry(id,sampleService.getLoggedInUser()))
    	{
    		model.addObject("sentenquiry", 1);
    	}
    	
        return model;
    }

    @RequestMapping(value = "/newad", method = RequestMethod.POST)
    public ModelAndView createAd(@Valid AdCreateForm adCreationForm, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView model;
        if (!result.hasErrors()) {
            try {

                for (int i = 0; i < adCreationForm.getFiles().size(); i++) {
                    MultipartFile file = adCreationForm.getFiles().get(i);
                    try {
                        byte[] bytes = file.getBytes();

                        // Creating the directory to store file
                        String rootPath = servletContext.getRealPath("/");//null; // PLACE THE RIGHT PATH HERE
                        File dir = new File(rootPath + "/img");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                        // Create the file on server
                        File serverFile = new File(dir.getAbsolutePath()
                                + File.separator + filename);
                        BufferedOutputStream stream = new BufferedOutputStream(
                                new FileOutputStream(serverFile));
                        stream.write(bytes);
                        stream.close();

                        adCreationForm.addFile(filename);
                    } catch (Exception e) {
                        System.out.println("No files selected.");
                    }
                }

                Long id = sampleService.saveFromAd(adCreationForm);
                model = new ModelAndView("showad");
                model.addObject("ad", sampleService.getAd(id));
            } catch (InvalidAdException e) {
                //System.out.println("Invalidadexception raised");
                model = new ModelAndView("adcreation");
                model.addObject("adCreationForm", adCreationForm);
                model.addObject("page_error", e.getMessage());
            }
        } else {
            model = new ModelAndView("adcreation");
        }
        return model;
    }

    @RequestMapping(value = "/security-error", method = RequestMethod.GET)
    public String securityError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("page_error", "You do have no permission to do that!");
        return "redirect:/";
    }

}
