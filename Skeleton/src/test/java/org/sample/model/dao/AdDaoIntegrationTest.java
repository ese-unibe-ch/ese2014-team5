package org.sample.model.dao;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

import org.junit.Before;
import org.sample.model.Address;
import org.sample.model.Advert;
import org.sample.model.Bookmark;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml", "file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AdDaoIntegrationTest {

    @Autowired
    UserDao userDao;

    @Autowired
    AdDao adDao;
    
    @Autowired
    AddressDao addressDao;

    User user = null;
    User userFrom = null;
    String username = "test@mail.com";
    String password = "1234";
    String street = "Hochschulstrasse 4";
    String plz = "3012";
    String city = "Bern";
    String title = "room";
    String peopleDesc = "All are very nice people";
    String roomDesc = "A wonderful room!";
    int peopleNr = 2;
    int price = 100;
    int size = 110;
    Advert advert,ad;
    Address address;
    List<Advert> adList;
    long id=1;

    @Before
    public void init() {
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user = userDao.save(user);
        
        address = new Address();
        address.setStreet(street);
        address.setPlz(plz);
        address.setCity(city);
        address = addressDao.save(address);
        
        advert = new Advert();        
        advert.setTitle(title);
        advert.setUser(user);
        advert.setAddress(address);
        advert.setNumberOfPeople(peopleNr);
        advert.setPeopleDesc(peopleDesc);
        advert.setRoomDesc(roomDesc);
        advert.setRoomPrice(price);
        advert.setRoomSize(size);
        advert = adDao.save(advert);
        
        ad=null;
        adList=null;
    }

    @Test
    public void savingAdvertTest() {
        assertTrue(advert.getId() > 0);
        assertTrue(advert != null);
    }
    
    @Test
    public void findByIdTest() {
    	
    	assertNull(ad);

    	ad = adDao.findById(advert.getId());
    	
    	assertNotNull(ad);
    	assertEquals(ad,advert);
    }
    
    @Test
    public void findByroomPriceBetweenTest() {
    	
    	assertNull(ad);
    	
    	adList = adDao.findByroomPriceBetween(price-1,price+1);
    	
    	assertNotNull(adList.get(0));
    	assertEquals(adList.get(0),advert);
    }

    @Test
    public void findByroomSizeBetweenTest() {
    	
    	assertNull(adList);
    	assertNull(ad);
    	
    	adList = adDao.findByroomSizeBetween(size-1,size+1);
    	
    	assertNotNull(adList.get(0));
    	assertEquals(adList.get(0),advert);
    }
    
    @Test
    public void findByUserIdTest() {
    	
    	assertNull(adList);
    	assertNull(ad);
    	
    	adList = adDao.findByUserId(user.getId());
    	
    	assertNotNull(adList.get(0));
    	assertEquals(adList.get(0),advert);
    }
    
    @Test
    public void findByNumberOfPeolpleLessThanEqualTest() {
    	
    	assertNull(adList);
    	assertNull(ad);
    	
    	adList = adDao.findByNumberOfPeopleLessThanEqual(peopleNr);
    			
    	assertNotNull(adList.get(0));
    	assertEquals(adList.get(0),advert);
    }
}
