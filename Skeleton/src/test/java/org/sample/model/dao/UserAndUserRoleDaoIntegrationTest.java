package org.sample.model.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class UserAndUserRoleDaoIntegrationTest {

    @Autowired
    UserDao userDao;
    
    @Autowired
    UserRoleDao userRoleDao;

    @Test
    public void testUserRoleReference() {
    	String username = "test@mail.com";
    	String password = "1234";
    	
    	User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setEmail(username);
    	
    	
    	UserRole userRole = new UserRole();
    	userRole.setRole(1);

    	userRole = userRoleDao.save(userRole);
    	
    	user.setUserRole(userRole);
    	
    	assertTrue(user.getId()==null);
    	
    	user = userDao.save(user);
    	
    	assertTrue(user!=null);
    	assertTrue(user.getId()>0);
    	
    	assertTrue(user.getUserRole().getRole()==1);
    }
    
    @Test
    public void testFindUserByEmail() {
    	String username = "test@mail.com";
    	String password = "1234";
    	
    	User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setEmail(username);
    	user = userDao.save(user);
    	
    	User userFromDB = userDao.findByEmail(username);
    	
    	assertNotNull(userFromDB);
    	assertTrue(userFromDB.getEmail().equals(username));
    }

    
    @Test
    public void testFindUserByUsername() {
    	String username = "test@mail.com";
    	String password = "1234";
    	
    	User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setEmail(username);
    	user = userDao.save(user);
    	
    	User userFromDB = userDao.findByUsername(username);
    	
    	assertNotNull(userFromDB);
    	assertTrue(userFromDB.getEmail().equals(username));
    }
}
