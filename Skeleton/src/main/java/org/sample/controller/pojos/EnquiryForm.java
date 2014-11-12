/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.sample.controller.pojos;

/**
 * Form which retrieves the data of the Enquiry from the Controller and the Jsp
 * 
 */

public class EnquiryForm {
    
    
private String enquiryTitle;
private String enquiryText;

private String username;
private String adNumber;


public String getUsername() {
return username;
}
public void setUsername(String username) {
this.username = username;
}

public String getAdNumber() {
return adNumber;
}

public void setAdNumber(String adNumber) {
this.adNumber = adNumber;
}

public String getEnquiryTitle() {
return enquiryTitle;
}

public void setEnquiryTitle(String enquiryTitle) {
this.enquiryTitle = enquiryTitle;
}

public String getEnquiryText() {
return enquiryText;
}

public void setEnquiryText(String enquiryText) {
this.enquiryText = enquiryText;
}

}