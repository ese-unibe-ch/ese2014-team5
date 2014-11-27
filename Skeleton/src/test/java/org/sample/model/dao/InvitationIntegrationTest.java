package org.sample.model.dao;

import java.util.Date;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.model.Advert;
import org.sample.model.Invitation;
import org.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml", "file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class InvitationIntegrationTest {
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    AdDao adDao;
    
    @Autowired
    InvitationDao invitationDao;
    
    User user = null;
    User user2 = null;
    User userFrom = null;
    String username = "test@mail.com";
    String password = "1234";
    String username2 = "test2@mail.com";
    String password2 = "1234";
    String username3 = "test3@mail.com";
    String password3 = "123456";
    
    Advert advert = new Advert();
    String advertTitle = "castle";
    Invitation invitation = new Invitation();
    String invitationtest = "invite me, invite me";
    Date date = new Date();
    
    @Before
    public void init() {
        
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user = userDao.save(user);
        
        user2 = new User();
        user2.setUsername(username2);
        user2.setPassword(password2);
        user2.setEmail(username2);
        user2 = userDao.save(user2);
        
        userFrom = new User();
        userFrom.setUsername(username3);
        userFrom.setPassword(password3);
        userFrom.setEmail(username3);
        userFrom = userDao.save(userFrom);
        
        advert.setTitle(advertTitle);
        advert.setUser(user);
        advert = adDao.save(advert);
        
        invitation.setAdvert(advert);
        invitation.setInvited(true);
        invitation.setTextOfInvitation(invitationtest);
        invitation.setToDate(date);
        invitation.setFromDate(date);
        invitation.setUserFrom(userFrom);
        invitation.setUserTo(user);
        invitation.setUserTo(user2);
        invitation = invitationDao.save(invitation);
    }
    
    @Test
    public void testInvitationSavingAndIntegration() {
        assertTrue(invitation.getId() > 0);
        assertTrue(invitation != null);
    }
    
    @Test
    public void testfindById() {
        
        Invitation invitationCheck = invitationDao.findById(invitation.getId());
        
        assertTrue(invitationCheck.getInvited());
        assertTrue(invitationCheck.getTextOfInvitation().equals(invitationtest));
        assertTrue(invitationCheck.getAdvert().getTitle().equals(advertTitle));
        assertTrue(invitationCheck.getToDate().equals(date));
        assertTrue(invitationCheck.getFromDate().equals(date));
        assertTrue(invitationCheck.getUserFrom().getEmail().equals(username3));
    }
    
    /*@Test
    public void testfindByUser() {
        User allUsers = invitation.getUserTo();        
        
        for (User testUser : allUsers) {
            assertTrue(testUser.getPassword().equals(password2));
            
        }
    }*/
    
}
