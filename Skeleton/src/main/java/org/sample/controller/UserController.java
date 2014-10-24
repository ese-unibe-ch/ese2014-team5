/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.controller;

import javax.servlet.http.HttpServletRequest;

import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SampleService;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
public class UserController {
    
    @Autowired
    SampleService sampleService;

    @Autowired
    UserDao userDao;
    
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView showProfile(@RequestParam(value = "name", required = false) String name) {
     ModelAndView model = new ModelAndView("profile");
     model.addObject("currentUser", userDao.findByUsername(name));
     return model;
    }
    
   /* @RequestMapping(value = "/login", method = RequestMethod.GET)
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

   		return model;
    
   	}*/
    
    @RequestMapping("/login")
	public String login(Model model, @RequestParam(required=false) String message) {
		model.addAttribute("message", message);
		return "/login";
	}
	
	@RequestMapping(value = "/denied")
 	public String denied() {
		return "access/denied";
	}
	
	@RequestMapping(value = "/login/failure")
 	public String loginFailure() {
		String message = "Login Failure!";
		return "redirect:/login?message="+message;
	}
	
	@RequestMapping(value = "/logout/success")
 	public String logoutSuccess() {
		String message = "Logout Success!";
		return "redirect:/login?message="+message;
	}

    
}
