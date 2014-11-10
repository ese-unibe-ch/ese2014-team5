package org.sample.model.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.model.Notifies;
import org.sample.model.User;
import org.sample.model.UserRole;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class NotifiesDaoIntegrationTest {

	@Autowired
	UserDao userDao;
	
    @Autowired
    NotifiesDao notifiesDao;
    
    User user = null;
    Notifies note = null;
    String username = "test@mail.com";
	String password = "1234";

    @Before
    public void init() {
    	
    	user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setEmail(username);
    	user = userDao.save(user);
    	
    	note = new Notifies();
    	note.setToUser(user);
    	note.setNotetype(Notifies.Type.BOOKMARK);
    	note.setSeen(0);
    	note.setText("Text 1");
    	note = notifiesDao.save(note);
    	
    	note = new Notifies();
    	note.setToUser(user);
    	note.setNotetype(Notifies.Type.SEARCHMATCH);
    	note.setSeen(0);
    	note.setText("Text 2");
    	note = notifiesDao.save(note);
    }
    
    @Test
    public void testInit() {
    	assertTrue(user.getId()>0);
    }
    
    @Test
    public void testNotifiesSaving() {
    	assertTrue(note!=null);
    	assertTrue(note.getId()>0);
    }
    
    @Test
    public void testFindById() {
    	Notifies notify = notifiesDao.findById(note.getId());
    	
    	assertNotNull(notify);
    	assertTrue(notify.getToUser().getUsername().equals(username));
    }
    
    @Test
    public void testFindNotesForUser() {
    	ArrayList<Notifies> notes = new ArrayList<Notifies>();
    	Iterable<Notifies> notifies = notifiesDao.findByToUser(user);
    	
    	assertNotNull(notifies);
    	
    	Iterator<Notifies> iterator = notifies.iterator();
    	while(iterator.hasNext())
    	{
    		notes.add(iterator.next());
    	}
    	
    	assertTrue(notes.size()==2);
    	
    	assertTrue(notes.get(0).getNotetype()==Notifies.Type.BOOKMARK);
    	assertTrue(notes.get(1).getNotetype()==Notifies.Type.SEARCHMATCH);
    }

}
