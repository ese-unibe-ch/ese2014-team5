/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.model.dao;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.sample.model.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml", "file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)

public class PictureDaoIntegrationTest {

    @Autowired
    UserDao userDao;

    @Autowired
    PictureDao pictureDao;

    Picture pic;

    @Before
    public void init() {
        String file = "~/hai-switzerland.jpg";
        /*Hope the path is not just working on my PC*/
        pic = new Picture();
        pic.setUrl(file);
        pic = pictureDao.save(pic);

    }

    @Test
    public void testPictureExistence() {
        assertTrue(pic != null);
        assertTrue(pic.getId() > 0);

    }

}
