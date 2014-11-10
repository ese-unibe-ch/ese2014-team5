package org.sample.model.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.model.Notifies;
import org.sample.model.Search;
import org.sample.model.dao.SearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import org.sample.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml", "file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class SearchDaoIntegrationTest {

    @Autowired
    UserDao userDao;

    @Autowired
    SearchDao searchDao;

    Search search = new Search();
    Date date1 = new Date();
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

        search.setUser(user);
        search.setArea("Area 51");
        search.setFreetext("blablabla blablabla");
        search.setPeopleAmount("20");
        search.setPriceFrom("300");
        search.setPriceTo("500");
        search.setSizeFrom("10");
        search.setSizeTo("20");
        search.setToDate(date1);
        search.setFromDate(date1);
        search = searchDao.save(search);
    }

    @Test
    public void testSearchInit() {       
    assertTrue(search.getId()>0);
    }
    
        @Test
    public void testSearchSaving() {
        assertTrue(search!=null);
    }
    
        @Test
    public void testFindById() throws ParseException {
    List <Search> savedSearch = searchDao.findByUserId(user.getId());    
    SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");

    assertNotNull(savedSearch);
    assertTrue(savedSearch.get(0).getArea().equals("Area 51"));
    assertTrue(savedSearch.get(0).getFreetext().equals("blablabla blablabla"));
    assertTrue(savedSearch.get(0).getPeopleAmount().equals("20"));
    assertTrue(savedSearch.get(0).getPriceFrom().equals("300"));
    assertTrue(savedSearch.get(0).getPriceTo().equals("500"));
    assertTrue(savedSearch.get(0).getSizeFrom().equals("10"));
    assertTrue(savedSearch.get(0).getSizeTo().equals("20"));
    assertTrue(savedSearch.get(0).getToDate().equals(date1));
    assertTrue(savedSearch.get(0).getFromDate().equals(date1));

    
    }



}
