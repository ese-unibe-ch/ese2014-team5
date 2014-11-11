
package org.sample.model.dao;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.sample.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml", "file:src/main/webapp/WEB-INF/config/spring-database.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)

public class AddressDaoIntegrationTest {

    @Autowired
    UserDao userDao;

    @Autowired
    AddressDao addressDao;

    Address addr;

    @Before
    public void init() {
      
        addr = new Address();
        addr.setStreet("Hochschulstrasse 4");
        addr.setPlz("3012");
        addr.setCity("Bern");
        addr = addressDao.save(addr);

    }

    @Test
    public void AddressSavingTest() {
        assertTrue(addr.getId() > 0);
        assertTrue(addr != null);
    }

}
