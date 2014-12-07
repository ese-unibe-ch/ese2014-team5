/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.controller.service.AdvertService;
import org.sample.controller.service.BookmarkService;
import org.sample.controller.service.EnquiryService;
import org.sample.controller.service.UserService;
import org.sample.exceptions.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    
	@Autowired
    UserService userService;
    @Autowired
    AdvertService advertService;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    EnquiryService enquiryService;

    /**
     * Controllerpart which waits for entered registration information
     * @return 
     */
   @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
    	ModelAndView model = new ModelAndView("register");
    	model.addObject("signupUser", new SignupUser());    	
        return model;
    }

    /**
     * Controllerpart which handles the Input of the Registration or brings you back if the registration is incorrect
     * @param signupUser
     * @param result
     * @param redirectAttributes
     * @return 
     */
    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public ModelAndView createRegistration(@Valid SignupUser signupUser, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	userService.saveUser(signupUser);
            	model = new ModelAndView("/index");
            	model.addObject("searchForm", new SearchForm());
            	model.addObject("minPrice",0);
        		model.addObject("maxPrice",3000);
        		model.addObject("minSize",0);
        		model.addObject("maxSize",200);
            } catch (InvalidUserException e) {
            	model = new ModelAndView("register");
            	model.addObject("signupUser", signupUser);  
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("register"); 
        }   	
    	return model;
    }
    
}
