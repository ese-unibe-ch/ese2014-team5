package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.InvitationForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.controller.service.AdvertService;
import org.sample.controller.service.BookmarkService;
import org.sample.controller.service.EnquiryService;
import org.sample.controller.service.SearchService;
import org.sample.controller.service.UserService;
import org.sample.exceptions.InvalidDateParseException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.Advert;
import org.sample.model.User;
import org.sample.model.dao.SearchDao;
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
    UserService userService;
    @Autowired
    AdvertService advertService;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    EnquiryService enquiryService;
    @Autowired
    SearchService searchService;

    @Autowired
    UserDao userDao;
    
    @Autowired
    SearchDao searchDao;
    
    /**
     * Shows the profile (of the given user)  Controller
     * @return 
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(@RequestParam("name") String username) {
	    ModelAndView model = new ModelAndView("profile");
	    if(username=="")
	    {
	    	model.addObject("currentUser", userService.getLoggedInUser());
	    }
	    else
	    {
	    	model.addObject("currentUser", userDao.findByUsername(username));
	    }
	    model.addObject("loggedInUser", userService.getLoggedInUser());
	    return model;
    }
    
    /**
     * Controller which takes the known profile info as default input and waits for changes to 
     * send them to the database
     * @return 
     */
    @RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
    public ModelAndView editProfile() {
	    ModelAndView model = new ModelAndView("profileediting");
	    model.addObject("profileUpdateForm", new SignupUser());
	    model.addObject("currentUser", userService.getLoggedInUser());
	    return model;
    }
    
    /**
     * Post function which takes all the changed Info about the profile/user and redirect that to the database
     * @param profileUpdateForm
     * @param result
     * @param redirectAttributes
     * @return 
     */
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public ModelAndView updateProfile(@Valid SignupUser profileUpdateForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	userService.updateUser(profileUpdateForm);
            	model = new ModelAndView("profile");
            	model.addObject("currentUser", userService.getLoggedInUser());
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
     * @param value The id of the search that has to be deleted from the user profile.
     * @param filter The id of the search that is selected for notifications, or 0 for no notifications.
     */
    @RequestMapping(value = "/saved-searches")
    public ModelAndView showSearches(@RequestParam(required = false) Long value, Long filter) {
	    ModelAndView model = new ModelAndView("profilesearches");
	    
	    if (value!=null){
	    	searchService.removeSearch(value);
	    }
	    
	    User currentUser = userService.getLoggedInUser();
	    if (filter!=null && filter==0){
	    	currentUser.setSelectedSearch(null);
	    	userDao.save(currentUser);
	    } else if (filter!=null){
	    	currentUser.setSelectedSearch(filter);
	    	userDao.save(currentUser);
	    	model.addObject("msg", "You will be notified as soon as a new ad is uploaded that matches your search.");
	    }
	    model.addObject("selectedSearch", currentUser.getSelectedSearch());
	    model.addObject("currentUser", currentUser);
	    model.addObject("searchList", searchDao.findByUserId(currentUser.getId()));
	    return model;
    }
    
    /**
     * Shows a list of the adverts of a user
     * @return 
     */
    @RequestMapping(value = "/my-ads", method = RequestMethod.GET)
    public ModelAndView showMyAds() {
	    ModelAndView model = new ModelAndView("profileadverts");
	    model.addObject("currentUser", userService.getLoggedInUser());
	    model.addObject("adList", advertService.findAdsForUser((User) userService.getLoggedInUser()));
	    return model;
    }
    
    /**
     * This shows the enquiries one advert received, is also the screen for sending invitations
     * @param id
     * @return 
     */
    @RequestMapping(value = "/showenquiries", method = RequestMethod.GET)
    public ModelAndView showAdEnquiries(@RequestParam("value") Long id) {
        ModelAndView model = new ModelAndView("showenquiries");
        model.addObject("currentUser", userService.getLoggedInUser());
        Advert ad = advertService.getAd(id);
        model.addObject("ad", ad);
        model.addObject("invitationForm", new InvitationForm());
        model.addObject("enqlist", enquiryService.findEnquiriesForAd(ad));
        model.addObject("invitationsList",enquiryService.findNotCancelledInvitationsForAd(ad));
        return model;
    }
    
    /**
     * The post class for sending an invitation to the wanted users
     * @param invForm
     * @param result
     * @param redirectAttributes
     * @return
     * @throws InvalidDateParseException 
     */
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public ModelAndView invite(@Valid InvitationForm invForm, BindingResult result, RedirectAttributes redirectAttributes) throws InvalidDateParseException {
        
    	enquiryService.createInvitation(invForm);
    	Advert ad = advertService.getAd(invForm.getAdvertId());
    	ModelAndView model = new ModelAndView("showenquiries");
        model.addObject("currentUser", userService.getLoggedInUser());
        model.addObject("ad", advertService.getAd(invForm.getAdvertId()));
        model.addObject("invitationForm", new InvitationForm());
        model.addObject("enqlist", enquiryService.findEnquiriesForAd(ad));
        model.addObject("invitationsList",enquiryService.findNotCancelledInvitationsForAd(ad));
        return model;
    }
    
    /**
     * This shows the bookmarks which an user has done
     * @return 
     */
    @RequestMapping(value = "/bookmarks", method = RequestMethod.GET)
    public ModelAndView showBookmarks() {
	    ModelAndView model = new ModelAndView("bookmarks");
	    model.addObject("currentUser", userService.getLoggedInUser());
	    model.addObject("adList", bookmarkService.findBookmarkedAdsForUser(userService.getLoggedInUser()));
	   
	    
	    return model;
    }
    
    /**
     * This cotroller part send the enquiries which an user send to the screen
     * @return 
     */
    @RequestMapping(value = "/sentenquiries", method = RequestMethod.GET)
    public ModelAndView showSentEnquiries() {
	    ModelAndView model = new ModelAndView("showSentEnquiries");
	    model.addObject("currentUser", userService.getLoggedInUser());
	    model.addObject("enquiriesList", enquiryService.findSentEnquiriesForUser(userService.getLoggedInUser()));
	    return model;
    }
    
    /**
     * Allows to delete a bookmark
     * @param model
     * @param adid
     * @param username
     * @return 
     */
    @RequestMapping("/removeBookmark")
	public String removeBookmark(Model model, @RequestParam String adid,@RequestParam String username) {
		bookmarkService.deleteBookmark(adid,username);
		return "/bookmarks";
	}
    
    /**
     * The login function is controlled by this method
     * @param model
     * @param message
     * @return 
     */    
    @RequestMapping("/login")
	public String login(Model model, @RequestParam(required=false) String message) {
		model.addAttribute("message", message);
		//sampleService.createNotification(sampleService.getLoggedInUser(), "Welcome back!");
		return "/login";
	}
	
        /**
         * This is showning up when the user want's to access things he should not
         * @return 
         */
	@RequestMapping(value = "/denied")
 	public String denied() {
		return "access/denied";
	}
	
        /**
         * Warning if the login fails
         * @return 
         */
	@RequestMapping(value = "/login/failure")
 	public String loginFailure() {
		String message = "User does not exist or password is incorrect!";
		return "redirect:/login?message="+message;
	}
	
        /**
         * Showing up when login was sucessful
         * @return 
         */
	@RequestMapping(value = "/logout/success")
 	public String logoutSuccess() {
		String message = "Logout Success!";
		return "redirect:/login?message="+message;
	}

    
}
