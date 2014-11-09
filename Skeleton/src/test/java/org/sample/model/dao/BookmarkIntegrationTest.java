package org.sample.model.dao;

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
import org.sample.model.Advert;
import org.sample.model.Bookmark;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml", "file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BookmarkIntegrationTest {

    @Autowired
    UserDao userDao;

    @Autowired
    AdDao adDao;

    @Autowired
    BookmarkDao bookmarkdao;

    User user = null;
    User userFrom = null;
    String username = "test@mail.com";
    String password = "1234";

    Advert advert = new Advert();
    String advertTitle = "castle";

    Bookmark bookmark = null;

    @Before
    public void init() {
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user = userDao.save(user);

        advert.setTitle(advertTitle);
        advert.setUser(user);
        advert = adDao.save(advert);

        bookmark = new Bookmark();
        bookmark.setAd(advert);
        bookmark.setUser(user);
        bookmark = bookmarkdao.save(bookmark);
    }

    @Test
    public void testBookmarkSavingAndIntegration() {
        assertTrue(bookmark.getId() > 0);
        assertTrue(bookmark != null);
    }
    
    @Test
    public void checkfindByUser() {
    Iterable <Bookmark> bookmarks = bookmarkdao.findByUser(user);
    
    for(Bookmark book : bookmarks) {
    assertTrue(book.getAd().getTitle().equals(advertTitle));
    }
    }
    
    @Test
    public void checkfindByAdAndUser() {
    Bookmark foundbookmark = bookmarkdao.findByAdAndUser(advert, user);    
    
    assertTrue(foundbookmark.getAd().getTitle().equals(advertTitle));
    assertTrue(foundbookmark.getUser().getUsername().equals(username));
    }

}
