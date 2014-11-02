package org.sample.controller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.Address;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Picture;
import org.sample.model.Search;
import org.sample.model.UserRole;
import org.sample.model.dao.AdDao;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.BookmarkDao;
import org.sample.model.dao.PictureDao;
import org.sample.model.dao.SearchDao;
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
public class SampleServiceImpl implements SampleService, UserDetailsService {

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    AddressDao addDao;
    @Autowired
    AdDao adDao;
    @Autowired
    PictureDao pictureDao;
    @Autowired
    SearchDao searchDao;
    
    @Autowired
    BookmarkDao bookmarkDao;

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
     * Retrieves a collection of {@link GrantedAuthority} based on a numerical
     * role
     *
     * @param role the numerical role
     * @return a collection of {@link GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    /**
     * Converts a numerical role to an equivalent list of roles
     *
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
     *
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

        

        String firstName = signupUser.getFirstName();
        /*Controls to validate input of new User*/
        if (StringUtils.isEmpty(firstName)) {
            throw new InvalidUserException("FirstName must not be empty");   // throw exception
        }

        String lastName = signupUser.getLastName();
        if (StringUtils.isEmpty(lastName)) {
            throw new InvalidUserException("LastName must not be empty ");   // throw exception
        }

        String email = signupUser.getEmail();
        if (StringUtils.isEmpty(email)) {
            throw new InvalidUserException("Email must not be empty ");   // throw exception
        }

        if (StringUtils.isEmpty(signupUser.getpassword())) {
            throw new InvalidUserException("password must not be empty ");   // throw exception
        }

        if (StringUtils.isEmpty(signupUser.getpasswordRepeat())) {
            throw new InvalidUserException("passwordRepeat must not be empty ");   // throw exception
        }

        if (signupUser.getpassword().equals(signupUser.getpasswordRepeat())) {


            org.sample.model.User user = new org.sample.model.User();
            user.setFirstName(signupUser.getFirstName());
            user.setLastName(signupUser.getLastName());
            user.setEmail(signupUser.getEmail());
            user.setUsername(signupUser.getEmail());

            String password = signupUser.getpassword();
            String passwordRepeat = signupUser.getpasswordRepeat();
        	
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
        } else {
            throw new InvalidUserException("password must be repeated correctly ");
        }

        return signupUser;
    }
    
    /*User get's updated by this function*/
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
        } catch (NumberFormatException e) {
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
        String peopleDesc = adForm.getPeopleDesc();
        String roomSize = adForm.getRoomSize();
        String fromDate = adForm.getFromDate();
        String numberOfPeople = adForm.getNumberOfPeople();
        
        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        Date fromDate2;

        if (StringUtils.isEmpty(title)) {
            throw new InvalidAdException("Title must not be empty" + title);   // throw exception
        } else if (title.length() < 4) {
            throw new InvalidAdException("Please enter a meaningfull Title ");   // throw exception
        }

        if (StringUtils.isEmpty(roomDesc)) {
            throw new InvalidAdException("Room description must not be empty");   // throw exception
        } else if (roomDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your Room Description");   // throw exception
        }

        if (StringUtils.isEmpty(peopleDesc)) {
            throw new InvalidAdException("People description must not be empty");   // throw exception
        } else if (peopleDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your People Description");   // throw exception
        }
        
        if(StringUtils.isEmpty(numberOfPeople)) { 
            throw new InvalidAdException("Number of people in the WG must be entered");  // throw exception
        } 
        
        if (StringUtils.isEmpty(roomSize) || !isInteger(roomSize)) {
            throw new InvalidAdException("Please enter a valid Room size");   // throw exception
        }

        if (StringUtils.isEmpty(fromDate)) {
            throw new InvalidAdException("Date must not be empty");   // throw exception
        }
        if (fromDate.length() != 10) {
            throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");   // throw exception
        }
        try {
            fromDate2 = dateFormater.parse(fromDate);
        } catch (ParseException e1) {
            throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");   // throw exception
        }
        if (todayDate.compareTo(fromDate2) > 0) {
            throw new InvalidAdException("Please enter a future date or today");   // throw exception
        }

        if (StringUtils.isEmpty(street)) {
            throw new InvalidAdException("Street must not be empty");   // throw exception
        }

        if (StringUtils.isEmpty(city)) {
            throw new InvalidAdException("City must not be empty");   // throw exception
        }

        if (StringUtils.isEmpty(plz) || !isInteger(plz) || (plz.length() < 4) || (plz.length() > 5)) {
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
        ad.setFusedSearch(adForm.getTitle() + " " + adForm.getRoomDesc() + " " + adForm.getPeopleDesc()); // Needed for making search simpler
        ad.setRoomPrice(Integer.parseInt(adForm.getRoomPrice()));
        ad.setRoomSize(Integer.parseInt(adForm.getRoomSize()));
        ad.setNumberOfPeople(Integer.parseInt(adForm.getNumberOfPeople()));
        System.out.println("USERNAME: " +adForm.getUsername());
        System.out.println("USERNAME: " +userDao.findByUsername(adForm.getUsername()).getUsername());
        ad.setUser(userDao.findByUsername(adForm.getUsername()));
        System.out.println("USERNAME: " +ad.getUser().getUsername());
        // need to parse dates before
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = formatter.parse(adForm.getFromDate());
            if (adForm.getToDate() != null) {
                dateTo = formatter.parse(adForm.getToDate());
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            dateFrom = new Date();
        }
        ad.setFromDate(dateFrom);
        if (adForm.getToDate() != null) {
            ad.setToDate(dateTo);
        }

        for (String file : adForm.getFilenames()) {
            Picture pic = new Picture();
            pic.setUrl(file);

            ad.addPicture(pic);

            pic = pictureDao.save(pic);
        }

        address = addDao.save(address);
        ad = adDao.save(ad);

        return ad.getId();
    }
    
    @Transactional
    public Long saveFromSearch(SearchForm searchForm) {
        String freetext = searchForm.getSearch();
        String priceFrom = searchForm.getFromPrice();
        String priceTo = searchForm.getToPrice();
        String sizeFrom = searchForm.getFromSize();
        String sizeTo = searchForm.getToSize();
        String area = searchForm.getNearCity();
        String peopleAmount = searchForm.getNumberOfPeople();
    	Date fromDate = searchForm.getFromDate();
        Date toDate = searchForm.getToDate();
        
        //TODO Change this if search parameters change
        if ( StringUtils.isEmpty(freetext) && priceFrom.equals("0") && priceTo.equals("0") && 
        		sizeFrom.equals("0") && sizeTo.equals("0") && StringUtils.isEmpty(area) && 
        		StringUtils.isEmpty(peopleAmount) && fromDate.equals(null) && toDate.equals(null)) {
            throw new InvalidSearchException("Search is not being saved because no filters are set.");
        }
        
        Search search = new Search();
        search.setFreetext(freetext);
        search.setPriceFrom(priceFrom);
        search.setPriceTo(priceTo);
        search.setSizeFrom(sizeFrom);
        search.setSizeTo(sizeTo);
        search.setArea(area);
        search.setPeopleAmount(peopleAmount);
        search.setFromDate(fromDate);
        search.setToDate(toDate);
        if(getLoggedInUser() != null && getLoggedInUser().getUserRole().getRole() == 1){
        	search.setUser( userDao.findOne(searchForm.getUserId()) );
        }
        
        search = searchDao.save(search);

        return search.getId();
    }

    public Advert getAd(Long id) {

        return adDao.findOne(id);
    }

    
    /* The search of the webpage, contains simple and complex mode for details.
    The search is 
    */
    @Transactional
    public Iterable<Advert> findAds(SearchForm form) {

        /*Checking for simple forumula if content is correct, means not null, etc...*/
        if (form.getFromPrice() == (null)) { // No 0 allowed
            form.setFromPrice("0");
        }
        if (form.getToPrice() == (null)) {
            form.setToPrice("0");
        }
        int priceMin = 0;
        int priceMax = 0;
        try {
            priceMin = Integer.parseInt(form.getFromPrice());
            priceMax = Integer.parseInt(form.getToPrice());

            if (priceMax < priceMin) { // This would crash the database...
                int placeholder = priceMax;
                priceMax = priceMin;
                priceMin = placeholder;
            }
        } catch (NumberFormatException e) {
            priceMax = 9999999;
            priceMin = 0;
        }

        if (form.getFromSize() == null) {
            form.setFromSize("0");
        }
        if (form.getToSize() == null) {
            form.setToSize("0");
        }
        int roomSizeMin = 0;
        int roomSizeMax = 0;
        try {
            roomSizeMin = Integer.parseInt(form.getFromSize());
            roomSizeMax = Integer.parseInt(form.getToSize());
            
            if (roomSizeMax < roomSizeMin) { // This would crash the database...
                int placeholder = roomSizeMax;
                roomSizeMax = roomSizeMin;
                roomSizeMin = placeholder;
            }

        } catch (NumberFormatException e) {
            roomSizeMin = 0;
            roomSizeMax = 9999999;
        }
        
        String town = form.getNearCity();
        if(town == null || town.length() == 0) {
        town = "";    /*This handels errors and makes that the Search finds everything if no city is given*/
        }
        
        String TextSearch = form.getSearch();
        if(TextSearch == null || TextSearch.length() == 0) {
        TextSearch = "";  //Is like empty search, contains is always true...  
        }
   
        boolean simpleSearch = true;
        Iterable <org.sample.model.Advert> ads = null;
        if(simpleSearch == true) {
        /*This is the search function for the simple search /
        /*Searches strictly in range Price and Bigness of room, searches fuzzy in part */
        ads = adDao.findByroomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(priceMin, priceMax, roomSizeMin, roomSizeMax, town, TextSearch);
	} else {
        /* The complex search allows it to search for more criterea in a much more complex function*/    
        int people = Integer.parseInt(form.getNumberOfPeople());
        Date date = new Date();
        ads = adDao.findByroomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContainingAndNumberOfPeopleLessThanEqualAndFromDateBeforeAndToDateAfter(roomSizeMin, roomSizeMax, roomSizeMin, roomSizeMax, town, TextSearch, people , date, date);
        }
        
		return ads;
	}

    public org.sample.model.User getLoggedInUser() {
		return (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	public Iterable<Advert> findAdsForUser(org.sample.model.User user) {
		return adDao.findByUserId(user.getId());
	}

	public void removeSearch(Long searchId) {
		Search search = searchDao.findOne(searchId);
		search.setUser(null);
		
		search = searchDao.save(search);
	}

	public Long bookmark(BookmarkForm bookmarkForm) {
		
		Bookmark bookmark = new Bookmark();
		bookmark.setAd(adDao.findById((long) Integer.parseInt(bookmarkForm.getAdNumber())));
		bookmark.setUser(userDao.findByUsername(bookmarkForm.getUsername()));
		
		
		return bookmarkDao.save(bookmark).getId();
	}

	public boolean checkBookmarked(Long id, org.sample.model.User user) {
		
		return (bookmarkDao.findByAdAndUser(adDao.findById(id),user)!=null)? true : false;
	}

	public Object findBookmarkedAdsForUser(org.sample.model.User user) {
		
		Iterable<Bookmark> bookmarks = bookmarkDao.findByUser(user);
		ArrayList<Advert> ads = new ArrayList<Advert>();
		for(Bookmark bm : bookmarks)
		{
			ads.add(bm.getAd());
		}
		return ads;
	}

	public void deleteBookmark(String adid, String username) {
		bookmarkDao.delete(bookmarkDao.findByAdAndUser(adDao.findById(Long.parseLong(adid)), userDao.findByUsername(username)));

	}
	
}
