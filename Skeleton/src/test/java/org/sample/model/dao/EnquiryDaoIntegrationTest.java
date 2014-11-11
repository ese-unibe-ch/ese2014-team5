package org.sample.model.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.model.User;
import org.sample.model.UserRole;
import org.sample.model.Enquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import org.junit.Before;
import org.sample.model.Advert;
import org.sample.model.Notifies;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml", "file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)

public class EnquiryDaoIntegrationTest {

    @Autowired
    UserDao userDao;

    @Autowired
    EnquiryDao enquiryDAO;

    @Autowired
    AdDao adDao;

    User user = null;
    User userFrom = null;
    String username = "test@mail.com";
    String password = "1234";

    String username2 = "test2@mail.com";
    String password2 = "4321";
    Advert advert = new Advert();

    Enquiry enquiry = new Enquiry();
    String enqText = " blabla bla bla";
    String enqTitle = "love your Advert";
    String advertTitle = "castle";

    @Before
    public void init() {
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user = userDao.save(user);

        userFrom = new User();
        userFrom.setUsername(username2);
        userFrom.setPassword(password2);
        userFrom.setEmail(username2);
        userFrom = userDao.save(userFrom);

        advert.setTitle(advertTitle);
        advert.setUser(user);
        advert = adDao.save(advert);

        enquiry.setAdvert(advert);
        enquiry.setEnquiryText(enqText);
        enquiry.setEnquiryTitle(enqTitle);
        enquiry.setUserTo(user);
        enquiry.setUserFrom(userFrom);
        enquiry = enquiryDAO.save(enquiry);
    }

    @Test
    public void testEnquiryInit() {
    assertTrue(enquiry.getId() > 0);
    }
    
    @Test
    public void testEnquirySaving() {
    assertTrue(enquiry != null);    
    }
    
    @Test
    public void testEnquiryfindByIdAndCheckContent() {
    Enquiry foundEnquiry = enquiryDAO.findById(enquiry.getId());
    
    assertNotNull(foundEnquiry);
    assertTrue(foundEnquiry.getEnquiryText().equals(enqText));
    assertTrue(foundEnquiry.getEnquiryTitle().equals(enqTitle));
    assertTrue(foundEnquiry.getUserFrom().getUsername().equals(username2));
    assertTrue(foundEnquiry.getUserTo().getUsername().equals(username));
    assertTrue(foundEnquiry.getAdvert().getTitle().equals(advertTitle));       
    }

    
    @Test
    public void testfindByAdvertAndUserFrom() {
    Enquiry foundEnquiry = enquiryDAO.findByAdvertAndUserFrom(advert, userFrom);
    
    assertNotNull(foundEnquiry);
    assertTrue(foundEnquiry.getEnquiryText().equals(enqText));
    assertTrue(foundEnquiry.getEnquiryTitle().equals(enqTitle));
    assertTrue(foundEnquiry.getUserFrom().getUsername().equals(username2));
    assertTrue(foundEnquiry.getUserTo().getUsername().equals(username));
    assertTrue(foundEnquiry.getAdvert().getTitle().equals(advertTitle));       

    }

}
