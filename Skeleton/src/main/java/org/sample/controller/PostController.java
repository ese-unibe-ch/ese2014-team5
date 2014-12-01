package org.sample.controller;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.sample.controller.service.SampleService;
import org.sample.model.Advert;
import org.sample.model.Enquiry;
import org.sample.model.Notifies;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles all jquery transactions
 * @author sevi
 *
 */
@Controller
public class PostController {

    @Autowired
    ServletContext servletContext;
    @Autowired
    SampleService sampleService;
    @Autowired
    UserDao userDao;
    
    /**
     * Controller for output notifications
     *
     * @return
     */
    @RequestMapping(value = "/getnotifications", method = RequestMethod.GET)
    public @ResponseBody
    String getNotifications() {
        Iterable<Notifies> notifications = (Iterable<Notifies>) sampleService.findNotificationsForUser(sampleService.getLoggedInUser());

        String result = "{\"Notifications\":[";
        Iterator<Notifies> iterator = notifications.iterator();
        while (iterator.hasNext()) {
            Notifies note = iterator.next();

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String date = dateFormat.format(note.getDate());
            String txt = "";
            String url = "";
            try {
                switch (note.getNotetype()) {
                    case BOOKMARK:
                        txt = "Bookmarked Ad " + note.getBookmark().getAd().getTitle() + " has changed - <span class='beside'>" + date + "</span>";
                        url = "showad?value=" + note.getBookmark().getAd().getId();
                        break;
                    case MESSAGE:
                        txt = note.getText() + " - " + "<span class='beside'>" + date + "</span>";
                        break;
                    case ENQUIRY:
                        txt = "New enquiry received from " + note.getFromUser().getUsername() + " - <span class='beside'>" + date + "</span>";
                        url = "";
                        break;
                    case SEARCHMATCH:
                        txt = "New matching ad for your search! - <span class='beside'>" + date + "</span>";
                        url = "";
                        break;
                    case INVITATION:
                        txt = "New Invitation for your enquiry! - <span class='beside'>" + date + "</span>";
                        url = "";
                        break;
                    default:
                        txt = note.getText() + " - <span class='beside'>" + date + "</span>";
                }
                result += "{\"id\": " + note.getId() + ",\"type\":\"" + note.getNotetype() + "\",\"text\":\"" + txt + "\",\"url\":\"" + url + "\",\"read\":" + note.getSeen() + "}";
                if (iterator.hasNext()) {
                    result += ",";
                }
            } catch (NullPointerException e) {

            }

        }
        result += "]}";
        return result;
    }
    
    @RequestMapping(value = "/getnumenquiriesforad", method = RequestMethod.GET)
    public @ResponseBody
    String getNumEnquiriesForAd(@RequestParam("id") Long id) {
        String result = "Enquiries: ";
        int numNewNotes = 0;
    	Advert ad = sampleService.getAd(id);
    	List<Enquiry> enqs = (List<Enquiry>) sampleService.findEnquiriesForAd(ad);
    	List<Notifies> notes = sampleService.findNotificationsEnquiryForAd(ad);
    	for(Notifies note : notes)
    	{
    		if(note.getSeen()==0)
    		{
    			numNewNotes++;
    		}
    	}
    	result += enqs.size() + "<br/>";
    	if(numNewNotes>0)
    	{
    		result += "<span style = \"color:green;\">New: " + numNewNotes + "</span><br/><br/>";
    	}
        return result;
    }

    @RequestMapping("/setread")
    public @ResponseBody
    String setread(Model model, @RequestParam String noteid) {
        sampleService.setRead(noteid);
        return "#";
    }
    
    @RequestMapping("/setreadbookmark")
    public @ResponseBody
    String setreadBookmark(Model model, @RequestParam String id) {
        sampleService.setReadBookmarkNote(id);
        return "#";
    }
    
    @RequestMapping("/setreadenquiries")
    public @ResponseBody
    String setreadEnquiries(Model model, @RequestParam String id) {
        sampleService.setReadEnquiryNoteForAdId(id);
        return "#";
    }
    
    @RequestMapping("/deleteinvitation")
    public @ResponseBody
    String deleteInvitation(Model model, @RequestParam Long id) {
        sampleService.cancelInvitation(id);
        return "#";
    }
    
    @RequestMapping("/acceptinvitation")
    public @ResponseBody
    String setAcceptInvitation(Model model, @RequestParam Long id) {
        sampleService.acceptInvitationForEnquiryId(id);
        return "#";
    }
    
    @RequestMapping("/cancelinvitation")
    public @ResponseBody
    String setCancelInvitation(Model model, @RequestParam Long id) {
        sampleService.cancelInvitationForEnquiryId(id);
        return "#";
    }
    
    @RequestMapping("/setrank")
    public @ResponseBody
    String setRank(Model model, @RequestParam Long id, @RequestParam int rank) {
        sampleService.setRatingForEnquiry(id,rank);
        return "#";
    }
}