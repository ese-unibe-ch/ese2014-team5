package org.sample.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.sample.controller.pojos.LoginForm;
import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.service.SampleService;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
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
    SampleService sampleService;
    
    @Autowired
    ServletContext context;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
    	ModelAndView model = new ModelAndView("index");
    	model.addObject("searchForm", new SearchForm());
        return model;
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index2() {
    	ModelAndView model = new ModelAndView("index");    
    	model.addObject("searchForm", new SearchForm());
        return model;
    }
    
    @RequestMapping(value = "/siteowner", method = RequestMethod.GET)
    public ModelAndView siteowner() {
    	ModelAndView model = new ModelAndView("siteowner");    
        return model;
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search() {
    	ModelAndView model = new ModelAndView("search"); 
    	model.addObject("searchForm", new SearchForm());
    	model.addObject("searchResults", sampleService.findAds("*"));
        return model;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
 
		ModelAndView model = new ModelAndView("login");
		if (error != null) {
			model.addObject("error", "Invalid username or password!");
		}
 
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.addObject("loginForm", new LoginForm());
		return model;
 
	}
    
    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
 
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");
 
		return model;
 
	}

    @RequestMapping(value = "/adcreation", method = RequestMethod.GET)
    public ModelAndView adcreation() {
    	ModelAndView model = new ModelAndView("adcreation");
    	model.addObject("adCreationForm", new AdCreateForm());    	
        return model;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	sampleService.saveFrom(signupForm);
            	model = new ModelAndView("show");
            } catch (InvalidUserException e) {
            	model = new ModelAndView("index");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("index");
        }   	
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
        				 String rootPath = context.getRealPath("/");
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
            	System.out.println("Invalidadexception raised");
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


