package org.sample.controller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.User;
import org.sample.exceptions.InvalidAdException;
import org.sample.model.Advert;
import org.sample.model.Address;
import org.sample.model.Picture;
import org.sample.model.UserDeprecated;
import org.sample.model.dao.AdDao;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.PictureDao;
import org.sample.model.dao.UserAccountDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SampleServiceImpl implements SampleService {

    @Autowired    UserDao userDao;
    @Autowired    AddressDao addDao;
    @Autowired	  AdDao adDao;
    @Autowired	  PictureDao pictureDao;
    
    @Autowired
    ServletContext context;
    
    @Transactional
    public SignupForm saveFrom(SignupForm signupForm) throws InvalidUserException {

        String firstName = signupForm.getFirstName();

        if (!StringUtils.isEmpty(firstName) && "ESE".equalsIgnoreCase(firstName)) {
            throw new InvalidUserException("Sorry, ESE is not a valid name");   // throw exception
        }

        Address address = new Address();
        address.setStreet("TestStreet");

        UserDeprecated user = new UserDeprecated();
        user.setFirstName(signupForm.getFirstName());
        user.setEmail(signupForm.getEmail());
        user.setLastName(signupForm.getLastName());
        user.setAddress(address);

        address = addDao.save(address);
        user = userDao.save(user);   // save object to DB

        // Iterable<Address> addresses = addDao.findAll();  // find all 
        // Address anAddress = addDao.findOne((long)3); // find by ID
        signupForm.setId(user.getId());

        return signupForm;

    }


        @Autowired
        UserAccountDao UserAccountDao;

        @Transactional
        public SignupUser saveUser(SignupUser signupUser) throws InvalidUserException {

            User User = new User();
            User.setFirstName(signupUser.getFirstName());
            User.setLastName(signupUser.getLastName());
            User.setEmail(signupUser.getEmail());

            String password = signupUser.getpassword();
            String passwordRepeat = signupUser.getpasswordRepeat();
            if (password.equals(passwordRepeat)) {
                User.setpassword(signupUser.getpassword());
            }
        //TODO, password should not be saved readable in database. 
            //Hashfunction is needed or something similar
            //Would mean user password replaced by double, but still read in as String in signupUser

            User = UserAccountDao.save(User);

            signupUser.setId(User.getId());

            return signupUser;
        }


    
    @Transactional
	public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException {
		String street = adForm.getStreet();

   /*     if(!StringUtils.isEmpty(street)) {
            throw new InvalidUserException("Street must not be empty");   // throw exception
        }
        
        if(!StringUtils.isEmpty(adForm.getRoomSize())) {
            throw new InvalidUserException("Size must not be empty");   // throw exception
        }*/
        
        Address address = new Address();
        address.setStreet(street);
        address.setCity(adForm.getCity());
        address.setPlz(adForm.getPlz());

        Advert ad = new Advert();
        ad.setAddress(address);
        ad.setTitle(adForm.getTitle());
        ad.setPeopleDesc(adForm.getPeopleDesc());
        ad.setRoomDesc(adForm.getRoomDesc());
        
        ad.setRoomSize(Integer.parseInt(adForm.getRoomSize()));
        
        // need to parse dates before
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFrom = null;
        Date dateTo = null;
		try {
			dateFrom = formatter.parse(adForm.getFromDate());
			dateTo = formatter.parse(adForm.getToDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dateFrom = new Date();
		}
        ad.setFromDate(dateFrom);
        ad.setToDate(dateTo);
        
        for(String file : adForm.getFilenames())
        {
        	Picture pic = new Picture();
    		pic.setUrl(file);
    		
    		ad.addPicture(pic);
    		
    		pic = pictureDao.save(pic);
        }
        
        address = addDao.save(address);
        ad = adDao.save(ad);
        
		return ad.getId();
	}



	public Advert getAd(Long id) {
		
		return adDao.findOne(id);
	}
    
    
    public User getUser(Long id) {
    	
        return UserAccountDao.findOne(id);
    }
    
    public Object getUserByFirstNameAndLastName(String fname, String lname) {
   
        for(User user : UserAccountDao.findAll()) {
            if(user.getFirstName().equals(fname) && user.getLastName().equals(lname)) {
                return user;
            }
        }
        
        return null;
    }
}
