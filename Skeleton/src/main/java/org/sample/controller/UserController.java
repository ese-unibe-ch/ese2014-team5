package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.pojos.SignupUser;
import org.sample.controller.service.SampleService;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Transactional
@Controller
public class UserController {
    
    @Autowired
    SampleService sampleService;

    @Autowired
    UserDao userDao;
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showProfile() {
	    ModelAndView model = new ModelAndView("profile");
	    model.addObject("currentUser", sampleService.getLoggedInUser());
	    return model;
    }
    
    @RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
    public ModelAndView editProfile() {
	    ModelAndView model = new ModelAndView("profileediting");
	    model.addObject("profileUpdateForm", new SignupUser());
	    model.addObject("currentUser", sampleService.getLoggedInUser());
	    return model;
    }
    
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public ModelAndView updateProfile(@Valid SignupUser profileUpdateForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	sampleService.updateUser(profileUpdateForm);
            	model = new ModelAndView("profile");
            	model.addObject("currentUser", sampleService.getLoggedInUser());
            	model.addObject("msg", "You've updated your profile successfully.");
            } catch (InvalidUserException e) {
            	model = new ModelAndView("profileediting");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("profileediting");
        }   	
    	return model;
    }
    
    /**
     * Redirects to the 'saved searches' section in user profile
     * 
     */
    @RequestMapping(value = "/saved-searches", method = RequestMethod.GET)
    public ModelAndView showSearches() {
	    ModelAndView model = new ModelAndView("profilesearches");
	    model.addObject("currentUser", sampleService.getLoggedInUser());
	    //model.addObject("searchList", );
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
