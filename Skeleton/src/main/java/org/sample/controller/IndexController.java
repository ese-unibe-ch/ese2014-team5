package org.sample.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SampleService;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidSearchException;
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
    
    @RequestMapping(value = "/siteowner", method = RequestMethod.GET)
    public ModelAndView siteowner() {
    	ModelAndView model = new ModelAndView("siteowner");    
        return model;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
    	ModelAndView model = new ModelAndView("index");
    	model.addObject("searchForm", new SearchForm());
    	model.addObject("currentUser", (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return model;
    }
    
    /*Core of the page, starting point,search and search output in one
    * is also containing an extended version of this search and a map version of the search */
    @RequestMapping(value = "/index")
    public ModelAndView index(@Valid SearchForm searchForm, @RequestParam(required = false) String action, BindingResult result, RedirectAttributes redirectAttributes) {

    	ModelAndView model = new ModelAndView("index");
    	model.addObject("currentUser", (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    	model.addObject("searchResults", sampleService.findAds(searchForm));
    	
    	//When one of the save buttons is clicked, save search.
    	if(action!=null){
    		if (!result.hasErrors()) {
	            try {
	            	sampleService.saveFromSearch(searchForm);
	            	if(sampleService.getLoggedInUser() != null && sampleService.getLoggedInUser().getUserRole().getRole() == 1){
	            		model.addObject("msg", "You can find your saved search in your profile.");
	            	}
	            } catch (InvalidSearchException e) {
	            	if(sampleService.getLoggedInUser().getUserRole().getRole() == 1){
	            		model.addObject("page_error", e.getMessage());
	            	}
	            }
	        }
    	}
    	
    	if(action!=null && action.equals("bmap"))
    	{
    		model.addObject("displayMap",1);
    	}
    	else if(action!=null && action.equals("blist"))
    	{
    		model.addObject("displayMap",0);
    	}
    	
    	
    	model.addObject("hasResults", 1);
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
    
    @RequestMapping(value = "/showad", method = RequestMethod.GET)
    public ModelAndView showad(@RequestParam("value") Long id) {
    	ModelAndView model = new ModelAndView("showad");
    	model.addObject("ad", sampleService.getAd(id));    	
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
        				if (!dir.exists())
        					dir.mkdirs();

        				String filename =  System.currentTimeMillis() + "_" + file.getOriginalFilename();
        				
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


