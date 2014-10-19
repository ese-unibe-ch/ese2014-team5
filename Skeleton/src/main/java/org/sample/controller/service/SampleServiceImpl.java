package org.sample.controller.service;

import org.sample.controller.pojos.AdCreationForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.AddUserAccount;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.Ad;
import org.sample.model.Address;
import org.sample.model.User;
import org.sample.model.dao.AdDao;
import org.sample.model.dao.AddressDao;
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
    @Transactional
    public SignupForm saveFrom(SignupForm signupForm) throws InvalidUserException {

        String firstName = signupForm.getFirstName();

        if (!StringUtils.isEmpty(firstName) && "ESE".equalsIgnoreCase(firstName)) {
            throw new InvalidUserException("Sorry, ESE is not a valid name");   // throw exception
        }

        Address address = new Address();
        address.setStreet("TestStreet");

        User user = new User();
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

            AddUserAccount User = new AddUserAccount();
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
	public AdCreationForm saveFrom(AdCreationForm adForm) throws InvalidAdException {
		String street = adForm.getStreet();

        if(!StringUtils.isEmpty(street)) {
            throw new InvalidUserException("Street must not be empty");   // throw exception
        }
        
        Address address = new Address();
        address.setStreet(street);
        address.setCity(adForm.getCity());
        address.setPlz(adForm.getPlz());

        Ad ad = new Ad();
        ad.setAddress(address);
        ad.setTitle(adForm.getTitle());
        ad.setPeopleDescription(adForm.getPeopleDescription());
        ad.setRoomDescription(adForm.getRoomDescription());
        
        ad.setSize(Integer.parseInt(adForm.getSize()));
        
        // need to parse dates before
       // ad.setFrom(adForm.getFrom());
       // ad.setTo(adForm.getTo());
        
        address = addDao.save(address);
        ad = adDao.save(ad);
        
		return adForm;
	}
}
