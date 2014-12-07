package org.sample.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SampleService;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.model.Advert;
import org.sample.model.Search;
import org.sample.model.User;
import org.sample.model.Enquiry;
import org.sample.model.dao.SearchDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    SearchDao searchDao;

    @RequestMapping(value = "/siteowner", method = RequestMethod.GET)
    public ModelAndView siteowner() {
        ModelAndView model = new ModelAndView("siteowner");
        return model;
    }

    

    @RequestMapping("/sendenquiry")
    public ModelAndView sendenquiry(@RequestParam String enquirytext, @RequestParam String adid) {
        ModelAndView model = new ModelAndView("showad");
        model.addObject("ad", sampleService.getAd(Long.parseLong(adid)));
        Enquiry enq = (Enquiry) sampleService.sendEnquiry(enquirytext, adid);
        if (enq != null) {
            if (sampleService.createNotificationEnquiry(enq)) {
                model.addObject("msg", "Your enquiry has been sent successfully.");
            } else {
                model.addObject("page_error", "Error! Couldn't send enquiry. Try again.");
            }
        }
        return model;
    }

    /**
     * Main start page controller, contains also search window
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        SearchForm searchForm = new SearchForm();
        model.addObject("searchForm", searchForm);
        model.addObject("currentUser", (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addObject("minPrice", searchForm.getFromPrice());
        model.addObject("maxPrice", searchForm.getToPrice());
        model.addObject("minSize", searchForm.getFromSize());
        model.addObject("maxSize", searchForm.getToSize());
        return model;
    }

    /**
     * Index page, central part of the apllication, the controller is listening for searches and has several buttons to create account, etc..
     * @param searchForm
     * @param action
     * @param searchid
     * @param result
     * @param redirectAttributes
     * @return 
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(@Valid SearchForm searchForm, @RequestParam(required = false) String action, @RequestParam(required = false) Long searchid, BindingResult result, RedirectAttributes redirectAttributes) {

        ModelAndView model = new ModelAndView("index");
        model.addObject("currentUser", (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        
        //When coming from 'Searches' page, autofill the search form.
        if (searchid != null){
	        Search search = searchDao.findOne(searchid);
	    	searchForm = new SearchForm();
	    	searchForm.setSearch(search.getFreetext());
	    	searchForm.setFromPrice(search.getPriceFrom());
	    	searchForm.setToPrice(search.getPriceTo());
	    	searchForm.setFromSize(search.getSizeFrom());
	    	searchForm.setToSize(search.getSizeTo());
	    	searchForm.setNearCity(search.getArea());
	    	searchForm.setNumberOfPeople(search.getPeopleAmount());
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    	if(search.getFromDate()!=null){
	    		searchForm.setFromDate(dateFormat.format(search.getFromDate()));
	    	} else {
	    		searchForm.setFromDate("");
	    	}
	    	if(search.getToDate()!=null){
	    		searchForm.setToDate(dateFormat.format(search.getToDate()));
	    	} else {
	    		searchForm.setToDate("");
	    	}
        }
        
        model.addObject("searchForm", searchForm);
        model.addObject("searchResults", sampleService.findAds(searchForm));

        //model.addObject("notifications",sampleService.findNotificationsForUser(sampleService.getLoggedInUser()));
        boolean saveToProfile = false;
        if (action != null && action.equals("bsave")) {
            saveToProfile = true;
        }

        //When one of the save buttons is clicked, save search.
        if (action != null) {
            if (!result.hasErrors()) {
                try {
                    sampleService.saveFromSearch(searchForm, saveToProfile);
                    if (sampleService.getLoggedInUser() != null && sampleService.getLoggedInUser().getUserRole().getRole() == 1 && saveToProfile) {
                        model.addObject("msg", "You can find your saved search in your profile.");
                    }
                } catch (InvalidSearchException e) {
                    if (sampleService.getLoggedInUser() != null && sampleService.getLoggedInUser().getUserRole().getRole() == 1 && saveToProfile) {
                        model.addObject("page_error", e.getMessage());
                    }
                }
            }
        }

        if (action != null && action.equals("bmap")) {
            model.addObject("displayMap", 1);
            model.addObject("hasResults", 1);
        } else if (action != null && action.equals("blist")) {
            model.addObject("displayMap", 0);
            model.addObject("hasResults", 1);
        }

        model.addObject("minPrice", searchForm.getFromPrice());
        model.addObject("maxPrice", searchForm.getToPrice());
        model.addObject("minSize", searchForm.getFromSize());
        model.addObject("maxSize", searchForm.getToSize());

        return model;
    }
    
    
    /**
     * Controller which waits for entered new Ad Input
     * @return 
     */
    @RequestMapping(value = "/adcreation", method = RequestMethod.GET)
    public ModelAndView adcreation() {
        ModelAndView model = new ModelAndView("adcreation");
        model.addObject("adCreationForm", new AdCreateForm());
        return model;
    }
    
    /**
     * Controller which shows the given ad found by id as output and waits for changes of this ad as Input
     * @param id
     * @return 
     */
    @RequestMapping(value = "/addediting", method = RequestMethod.GET)
    public ModelAndView addediting(@RequestParam("value") Long id) {
        ModelAndView model = new ModelAndView("addediting");
        //AdCreateForm addUpdateForm = new AdCreateForm();
        model.addObject("adCreateForm", new AdCreateForm());
        model.addObject("currentUser", sampleService.getLoggedInUser());
        model.addObject("currentAd", sampleService.getAd(id));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Advert ad = sampleService.getAd(id);
        try {
            model.addObject("fromDate", formatter.format(ad.getFromDate()));
            model.addObject("toDate", formatter.format(ad.getToDate()));
        } catch (Exception e) {

        }
        model.addObject("idstring", id);
        //sampleService.updateAds(addUpdateForm, id)
        return model;
    }
    
    /**
     * Controller which waits for the changed ad from the addediting
     * @param adCreateForm
     * @param idstring
     * @param result
     * @param redirectAttributes
     * @return 
     */
    @RequestMapping(value = "/addupdate", method = RequestMethod.POST)
    public ModelAndView addUpdate(@Valid AdCreateForm adCreateForm, @RequestParam("id") String idstring, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView model;
        if (!result.hasErrors()) {
            try {
            	try {

                    for (int i = 0; i < adCreateForm.getFiles().size(); i++) {
                        MultipartFile file = adCreateForm.getFiles().get(i);
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

                            adCreateForm.addFile(filename);

                        } catch (Exception e) {
                            System.out.println("No files selected.");
                        }
                    }
                Long id = Long.parseLong(idstring);
                sampleService.updateAd(adCreateForm, id);
                model = new ModelAndView("profileadverts");
                model.addObject("currentUser", sampleService.getLoggedInUser());
                model.addObject("adList", sampleService.findAdsForUser((User) sampleService.getLoggedInUser()));
                sampleService.sendNotificationsForBookmarks(sampleService.findBookmarksForAd(id));
	            } catch (InvalidAdException e) {
	                model = new ModelAndView("addediting");
	                model.addObject("page_error", e.getMessage());
	            }
            }
            finally{}
        }
        else {
            model = new ModelAndView("addediting");
        }
        
        return model;
    }
    
    /**
     * Controller which shows the ad and waits for the signal of the user to change the ad, make a bookmark, etc...
     * @param bookmarkForm
     * @param id
     * @param result
     * @param redirectAttributes
     * @return 
     */
    @RequestMapping(value = "/showad")
    public ModelAndView showad(@Valid BookmarkForm bookmarkForm, @RequestParam("value") Long id, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView model = new ModelAndView("showad");
        model.addObject("currentUser", sampleService.getLoggedInUser());
        model.addObject("ad", sampleService.getAd(id));
        if (sampleService.checkBookmarked(id, sampleService.getLoggedInUser())) {
            model.addObject("bookmarked", 1);
        }
        if (bookmarkForm != null && bookmarkForm.getAdNumber() != null && !bookmarkForm.getAdNumber().equals("")) {
            Long bookmarkid = sampleService.bookmark(bookmarkForm);
            if (bookmarkid > 0) {
                model.addObject("bookmarked", 1);
            }
        }

        if (sampleService.checkSentEnquiry(id, sampleService.getLoggedInUser())) {
            model.addObject("sentenquiry", 1);
        }

        return model;
    }
    
    /**
     * Post part of the controller for producing a new ad
     * @param adCreationForm
     * @param result
     * @param redirectAttributes
     * @return 
     */
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

                sampleService.createNewSearchNotifications(id);

            } catch (InvalidAdException e) {
                model = new ModelAndView("adcreation");
                model.addObject("adCreationForm", adCreationForm);
                model.addObject("page_error", e.getMessage());
            }
        } else {
            model = new ModelAndView("adcreation");
        }
        return model;
    }

    /**
     * Controller will be activated in case of security error
     * @param redirectAttributes
     * @return 
     */
    @RequestMapping(value = "/security-error", method = RequestMethod.GET)
    public String securityError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("page_error", "You do have no permission to do that!");
        return "redirect:/";
    }

}
