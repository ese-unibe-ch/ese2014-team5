package org.sample.controller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.Address;
import org.sample.model.Advert;
import org.sample.model.Picture;
import org.sample.model.UserRole;
import org.sample.model.dao.AdDao;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.PictureDao;
import org.sample.model.dao.UserDao;
import org.sample.model.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class SampleServiceImpl implements SampleService,  UserDetailsService {

    @Autowired	  UserRoleDao userRoleDao;
    
    @Autowired
	private UserDao userDao;
    @Autowired    AddressDao addDao;
    @Autowired	  AdDao adDao;
    @Autowired	  PictureDao pictureDao;
    
    @Autowired
    ServletContext context;
    
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    	
    	try {
    		//System.out.println("FIND USER BY NAME: " + username);
			org.sample.model.User domainUser = userDao.findByUsername(username);
			
			//System.out.println("FOUND USER BY NAME, PW is: " + domainUser.getPassword());
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			return new User(
					domainUser.getUsername(), 
					domainUser.getPassword(),
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					getAuthorities(domainUser.getUserRole().getRole()));
			
		} catch (Exception e) {
			//System.out.println("ERROR! -----------------------------------------------------------------------------");
			throw new RuntimeException(e);
		}
		
	}

    /**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}
	
	/**
	 * Converts a numerical role to an equivalent list of roles
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();
		
		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
			
		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}
		
		return roles;
	}
	
	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

    @Transactional
    public SignupUser saveUser(SignupUser signupUser) throws InvalidUserException {

        org.sample.model.User user = new org.sample.model.User();
        user.setFirstName(signupUser.getFirstName());
        user.setLastName(signupUser.getLastName());
        user.setEmail(signupUser.getEmail());
        user.setUsername(signupUser.getEmail());

        String password = signupUser.getpassword();
        String passwordRepeat = signupUser.getpasswordRepeat();
        if (password.equals(passwordRepeat)) {
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    		String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
            user.setEnabled(true);
            
            UserRole role = new UserRole();
            role.setRole(1);
            
            role = userRoleDao.save(role);
            user.setUserRole(role);
            user = userDao.save(user);
            
       
            
            org.sample.model.User user2 = userDao.findByUsername(signupUser.getEmail());
            System.out.println("User found! " + user2.getUsername());
            signupUser.setId(user2.getId());
        }
    //TODO, password should not be saved readable in database. 
        //Hashfunction is needed or something similar
        //Would mean user password replaced by double, but still read in as String in signupUser

        return signupUser;
    }
    
    @Transactional
    public void updateUser(SignupUser profileUpdateForm) throws InvalidUserException {
    	org.sample.model.User user = (org.sample.model.User)getLoggedInUser();
        user.setFirstName(profileUpdateForm.getFirstName());
        user.setLastName(profileUpdateForm.getLastName());
        user.setEmail(profileUpdateForm.getEmail());

        String password = profileUpdateForm.getpassword();
        String passwordRepeat = profileUpdateForm.getpasswordRepeat();
        if (!password.equals(passwordRepeat)) {
        	throw new InvalidUserException("Passwords are not equal.");
        }
        if(!password.isEmpty()){
        	user.setPassword(password);
        }

        user = userDao.save(user);
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
    		
    		pic = pictureDao.save(pic);
        }
        
        address = addDao.save(address);
        ad = adDao.save(ad);
        
		return ad.getId();
	}



	public Advert getAd(Long id) {
		
		return adDao.findOne(id);
	}
    
    

	public Object findAds(String string) {
		
		Iterable<Advert> ads = null;
		if(string.equals("*"))
		{
			ads = adDao.findAll();
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

	public Object getLoggedInUser() {
		return userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
}
