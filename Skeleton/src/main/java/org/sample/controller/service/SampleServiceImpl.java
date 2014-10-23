package org.sample.controller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.dao.AdDao;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.PictureDao;
import org.sample.model.dao.UserDao;
import org.sample.model.*;

@Service
@Transactional
public class SampleServiceImpl implements SampleService,  UserDetailsService {

    @Autowired    UserDao userDao;
   /* @Autowired    AddressDao addDao;
    @Autowired	  AdDao adDao;
    @Autowired	  PictureDao pictureDao;*/
    
    @Autowired
    ServletContext context;
    
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		// Programmatic transaction management
		/*
		return transactionTemplate.execute(new TransactionCallback<UserDetails>() {

			public UserDetails doInTransaction(TransactionStatus status) {
				com.mkyong.users.model.User user = userDao.findByUserName(username);
				List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

				return buildUserForAuthentication(user, authorities);
			}

		});*/
		
		org.sample.model.User user = userDao.findByUserName(username);
		
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);
		

	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(org.sample.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    @Transactional
    public SignupUser saveUser(SignupUser signupUser) throws InvalidUserException {

       /* User user = new User();
        user.setFirstName(signupUser.getFirstName());
        user.setLastName(signupUser.getLastName());
        user.setEmail(signupUser.getEmail());

        String password = signupUser.getpassword();
        String passwordRepeat = signupUser.getpasswordRepeat();
        if (password.equals(passwordRepeat)) {
            user.setPassword(signupUser.getpassword());
        }
    //TODO, password should not be saved readable in database. 
        //Hashfunction is needed or something similar
        //Would mean user password replaced by double, but still read in as String in signupUser

        user = userDao.save(user);

        signupUser.setId(user.getId());*/

        return null;
    }

    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    
    @Transactional
	public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException {
		String street = adForm.getStreet();
		String city = adForm.getCity();
		String plz = adForm.getPlz();
			    
		String title = adForm.getTitle();
		String roomDesc = adForm.getRoomDesc();
	    String peopleDesc = adForm.getPeopleDesc() ;
	    String roomSize = adForm.getRoomSize();
	    String fromDate = adForm.getFromDate();
	    
	    SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
	    Date todayDate=new Date();
	    Date fromDate2;
	    
	    if(StringUtils.isEmpty(title)) {
            throw new InvalidAdException("Title must not be empty"+ title);   // throw exception
        }
	    else if(title.length()<4) {
            throw new InvalidAdException("Please enter a meaningfull Title ");   // throw exception
	    }

		if(StringUtils.isEmpty(roomDesc)) {
	            throw new InvalidAdException("Room description must not be empty");   // throw exception
	    }
		else if(roomDesc.length()<10) {
            throw new InvalidAdException("Please enter more information in your Room Description");   // throw exception
	    }
		
		if(StringUtils.isEmpty(peopleDesc)) {
            throw new InvalidAdException("People description must not be empty");   // throw exception
        }
		else if(peopleDesc.length()<10) {
            throw new InvalidAdException("Please enter more information in your People Description");   // throw exception
	    }
		
		if(StringUtils.isEmpty(roomSize)||!isInteger(roomSize)) {
            throw new InvalidAdException("Please enter a valid Room size");   // throw exception
        }
		
		if(StringUtils.isEmpty(fromDate)) {
            throw new InvalidAdException("Date must not be empty");   // throw exception
        }
		if(fromDate.length()!=10) {
			throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");   // throw exception
		}
		try {
			fromDate2= dateFormater.parse(fromDate);
		} catch (ParseException e1) {
			throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");   // throw exception
		}
		if(todayDate.compareTo(fromDate2)>0) {
			throw new InvalidAdException("Please enter a future date or today");   // throw exception
		}
		
        if(StringUtils.isEmpty(street)) {
            throw new InvalidAdException("Street must not be empty");   // throw exception
        }
        
        if(StringUtils.isEmpty(city)) {
            throw new InvalidAdException("City must not be empty");   // throw exception
        }
        
        if(StringUtils.isEmpty(plz) || !isInteger(plz) || (plz.length()<4) || (plz.length()>5)) {
            throw new InvalidAdException("Please enter a valid postcode");   // throw exception
        }
        
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
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
			if(adForm.getToDate()!=null){
				dateTo = formatter.parse(adForm.getToDate());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dateFrom = new Date();
		}
        ad.setFromDate(dateFrom);
        if(adForm.getToDate()!=null){
        	ad.setToDate(dateTo);
        }
        
        for(String file : adForm.getFilenames())
        {
        	Picture pic = new Picture();
    		pic.setUrl(file);
    		
    		ad.addPicture(pic);
    		
    		//pic = pictureDao.save(pic);
        }
        
      //  address = addDao.save(address);
       // ad = adDao.save(ad);
        
		return ad.getId();
	}



	public Advert getAd(Long id) {
		
		return null;//adDao.findOne(id);
	}
    
    

	public Object findAds(String string) {
		
		Iterable<Advert> ads = null;
		if(string.equals("*"))
		{
			//ads = adDao.findAll();
		}
		else
		{
			/* SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			Transaction tx = fullTextSession.beginTransaction();

			// create native Lucene query unsing the query DSL
			// alternatively you can write the Lucene query using the Lucene query parser
			// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
			QueryBuilder qb = fullTextSession.getSearchFactory()
			    .buildQueryBuilder().forEntity(Advert.class).get();
			org.apache.lucene.search.Query query = qb
			  .keyword()
			  .onFields("roomDesc", "peopleDesc", "address.street")
			  .matching(string)
			  .createQuery();

			// wrap Lucene query in a org.hibernate.Query
			org.hibernate.Query hibQuery = 
			    fullTextSession.createFullTextQuery(query, Advert.class);

			// execute search
			ads = hibQuery.list();
			  
			tx.commit();
			session.close();*/
			
		}
		
		return ads;
	}
}
