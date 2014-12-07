package org.sample.controller.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.InvitationForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidDateParseException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.Address;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Enquiry;
import org.sample.model.Enquiry.InvitationStatus;
import org.sample.model.Invitation;
import org.sample.model.Notifies;
import org.sample.model.Notifies.Type;
import org.sample.model.Picture;
import org.sample.model.Search;
import org.sample.model.UserData;
import org.sample.model.UserRole;
import org.sample.model.dao.AdDao;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.BookmarkDao;
import org.sample.model.dao.EnquiryDao;
import org.sample.model.dao.InvitationDao;
import org.sample.model.dao.NotifiesDao;
import org.sample.model.dao.PictureDao;
import org.sample.model.dao.SearchDao;
import org.sample.model.dao.UserDao;
import org.sample.model.dao.UserDataDao;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 *
 */
@Service
@Transactional
public class SampleServiceImpl implements SampleService, UserDetailsService {

    @Autowired
    ServletContext servletContext;
    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    AddressDao addDao;
    @Autowired
    UserDataDao userDataDao;
    @Autowired
    AdDao adDao;
    @Autowired
    PictureDao pictureDao;
    @Autowired
    SearchDao searchDao;

    @Autowired
    BookmarkDao bookmarkDao;
    @Autowired
    NotifiesDao notifiesDao;
    @Autowired
    InvitationDao invitationDao;

    @Autowired
    EnquiryDao enquiryDao;

    @Autowired
    ServletContext context;

    EmailSender email = new EmailSender();

    /**
     * Is loading the details of the user, the user is found by his username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
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

    /**
     * Stores a given user in the database. Always creates a new User.
     *
     * @param signupUser - the user to store in the DB (has no ID)
     * @return SignupUser - the exact user-object stored in the DB (has an ID)
     */
    @Transactional
    public SignupUser saveUser(SignupUser signupUser) throws InvalidUserException {

        String firstName = signupUser.getFirstName();

        /*Controls to validate input of new User*/
        if (StringUtils.isEmpty(firstName)) {
            throw new InvalidUserException("First name must not be empty.");
        }

        String lastName = signupUser.getLastName();
        if (StringUtils.isEmpty(lastName)) {
            throw new InvalidUserException("Last name must not be empty.");
        }

        String email = signupUser.getEmail();
        if (StringUtils.isEmpty(email)) {
            throw new InvalidUserException("Email must not be empty.");
        }

        for (org.sample.model.User existingUser : userDao.findAll()) {
            if (existingUser.getEmail().equals(email)) {
                throw new InvalidUserException("Email already exists.");
            }

        }

        if (StringUtils.isEmpty(signupUser.getpassword())) {
            throw new InvalidUserException("Password must not be empty.");
        }

        if (StringUtils.isEmpty(signupUser.getpasswordRepeat())) {
            throw new InvalidUserException("Password aren't equal.");
        }

        if (signupUser.getpassword().equals(signupUser.getpasswordRepeat())) {

            Picture pic = new Picture();
            pic.setUrl("../web/images/icon/user.png");
            pic = pictureDao.save(pic);

            org.sample.model.User user = new org.sample.model.User();
            user.setFirstName(signupUser.getFirstName());
            user.setLastName(signupUser.getLastName());
            user.setEmail(signupUser.getEmail());
            user.setUsername(signupUser.getEmail());

            String password = signupUser.getpassword();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
            user.setEnabled(true);

            UserData userData = user.getUserData();
            if (userData == null) {
                userData = new UserData();
            }
            userData.setPicture(pic);
            userDataDao.save(userData);
            user.setUserData(userData);

            UserRole role = new UserRole();
            role.setRole(1);

            role = userRoleDao.save(role);
            user.setUserRole(role);
            user = userDao.save(user);

            org.sample.model.User user2 = userDao.findByUsername(signupUser.getEmail());
            signupUser.setId(user2.getId());
        } else {
            throw new InvalidUserException("password must be repeated correctly ");
        }

        return signupUser;
    }

    /**
     * Updates a given user with new data
     *
     * @param profileUpdateForm
     */
    @Transactional
    public void updateUser(SignupUser profileUpdateForm) throws InvalidUserException {
        org.sample.model.User user = (org.sample.model.User) getLoggedInUser();
        user.setFirstName(profileUpdateForm.getFirstName());
        user.setLastName(profileUpdateForm.getLastName());
        user.setEmail(profileUpdateForm.getEmail());

        String password = profileUpdateForm.getpassword();
        String passwordRepeat = profileUpdateForm.getpasswordRepeat();
        if (!password.equals(passwordRepeat)) {
            throw new InvalidUserException("Passwords are not equal.");
        }
        if (!password.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
        }

        UserData data = user.getUserData();
        if (data == null) {
            data = new UserData();
        }

        MultipartFile file = profileUpdateForm.getFile();
        try {
        	
            byte[] bytes = file.getBytes();
            if (bytes.length <= 0) {
                throw new Exception();
            }
            // Creating the directory to store file
            String rootPath = servletContext.getRealPath("/");//null; // PLACE THE RIGHT PATH HERE
            File dir = new File(rootPath + "/img");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + filename);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            if(file.getOriginalFilename().length()>4)
            {
            	profileUpdateForm.setFilenames(filename);

            	Picture pic = new Picture();
            	pic.setUrl(filename);
            	pic = pictureDao.save(pic);
            	data.setPicture(pic);
            }

        } catch (Exception e) {
            System.out.println("Error uploading file");
        }

        data.setAge(profileUpdateForm.getAge());
        data.setBio(profileUpdateForm.getBio());
        data.setHobbies(profileUpdateForm.getHobbies());
        data.setProfession(profileUpdateForm.getProfession());
        data.setQuote(profileUpdateForm.getQuote());

        data = userDataDao.save(data);
        user.setUserData(data);
        user = userDao.save(user);
    }

    /**
     * Checks of the entered String s can be converted to an Integer
     *
     * @param s
     * @return
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks the adForm and saves the ad to the database
     *
     * @param adForm
     * @return the id of the new ad
     * @throws InvalidAdException
     */
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
        todayDate.setTime(todayDate.getTime() - 24 * 60 * 60 * 1000);
        Date fromDate2;

        if (StringUtils.isEmpty(title)) {
            throw new InvalidAdException("Title must not be empty" + title);
        } else if (title.length() < 4) {
            throw new InvalidAdException("Please enter a meaningfull Title ");
        }

        if (StringUtils.isEmpty(roomDesc)) {
            throw new InvalidAdException("Room description must not be empty");
        } else if (roomDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your Room Description");
        }

        if (StringUtils.isEmpty(peopleDesc)) {
            throw new InvalidAdException("People description must not be empty");
        } else if (peopleDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your People Description");
        }

        if (StringUtils.isEmpty(numberOfPeople)) {
            throw new InvalidAdException("Number of people in the WG must be entered");
        }

        if (StringUtils.isEmpty(roomSize) || !isInteger(roomSize)) {
            throw new InvalidAdException("Please enter a valid Room size");
        }

        if (!fromDate.equals("")) {
            try {
                fromDate2 = dateFormater.parse(fromDate);
            } catch (ParseException e1) {
                throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");
            }
            if (todayDate.getTime() > fromDate2.getTime()) {
                throw new InvalidAdException("Please enter a future date or today");
            }
        }

        if (StringUtils.isEmpty(street)) {
            throw new InvalidAdException("Street must not be empty");
        }

        if (StringUtils.isEmpty(city)) {
            throw new InvalidAdException("City must not be empty");
        }

        if (StringUtils.isEmpty(plz) || !isInteger(plz) || (plz.length() < 4) || (plz.length() > 5)) {
            throw new InvalidAdException("Please enter a valid postcode");
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
        ad.setFusedSearch(adForm.getTitle() + " " + adForm.getRoomDesc() + " " + adForm.getPeopleDesc());
        ad.setRoomPrice(Integer.parseInt(adForm.getRoomPrice()));
        ad.setRoomSize(Integer.parseInt(adForm.getRoomSize()));
        ad.setNumberOfPeople(Integer.parseInt(adForm.getNumberOfPeople()));
        ad.setUser(userDao.findByUsername(adForm.getUsername()));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            if (adForm.getFromDate() != null) {
                dateFrom = formatter.parse(adForm.getFromDate());
            } else {
                dateFrom = todayDate;
            }
            if (adForm.getToDate() != null) {
                dateTo = formatter.parse(adForm.getToDate());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            dateFrom = new Date();
        }
        ad.setFromDate(dateFrom);
        if (adForm.getToDate() != null) {
            ad.setToDate(dateTo);
        }

        if (!adForm.getFilenames().isEmpty()) {
            for (String file : adForm.getFilenames()) {
                Picture pic = new Picture();
                pic.setUrl(file);

                ad.addPicture(pic);
                pic = pictureDao.save(pic);
            }
        } else {
            Picture pic = new Picture();
            pic.setUrl("../web/images/icon/home.png");
            pic = pictureDao.save(pic);
            ad.addPicture(pic);
        }

        address = addDao.save(address);
        ad = adDao.save(ad);

        return ad.getId();
    }

    /**
     * Takes the updateForm and the id of the user and changes his information
     * on the database
     *
     * @param updateForm
     * @param id
     * @throws InvalidAdException
     */
    @Transactional
    public void updateAd(AdCreateForm updateForm, long id) throws InvalidAdException {
        String street = updateForm.getStreet();
        String city = updateForm.getCity();
        String plz = updateForm.getPlz();

        String title = updateForm.getTitle();
        String roomDesc = updateForm.getRoomDesc();
        String peopleDesc = updateForm.getPeopleDesc();
        String roomSize = updateForm.getRoomSize();
        String fromDate = updateForm.getFromDate();
        String numberOfPeople = updateForm.getNumberOfPeople();

        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        Date fromDate2;

        if (StringUtils.isEmpty(title)) {
            throw new InvalidAdException("Title must not be empty" + title);
        } else if (title.length() < 4) {
            throw new InvalidAdException("Please enter a meaningfull Title ");
        }

        if (StringUtils.isEmpty(roomDesc)) {
            throw new InvalidAdException("Room description must not be empty");
        } else if (roomDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your Room Description");
        }

        if (StringUtils.isEmpty(peopleDesc)) {
            throw new InvalidAdException("People description must not be empty");
        } else if (peopleDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your People Description");
        }

        if (StringUtils.isEmpty(numberOfPeople)) {
            throw new InvalidAdException("Number of people in the WG must be entered");
        }

        if (StringUtils.isEmpty(roomSize) || !isInteger(roomSize)) {
            throw new InvalidAdException("Please enter a valid Room size");
        }

        if (StringUtils.isEmpty(fromDate)) {
            throw new InvalidAdException("Date must not be empty");
        }
        if (fromDate.length() != 10) {
            throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");
        }
        try {
            fromDate2 = dateFormater.parse(fromDate);
        } catch (ParseException e1) {
            throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");
        }

        if (StringUtils.isEmpty(street)) {
            throw new InvalidAdException("Street must not be empty");
        }

        if (StringUtils.isEmpty(city)) {
            throw new InvalidAdException("City must not be empty");
        }

        if (StringUtils.isEmpty(plz) || !isInteger(plz) || (plz.length() < 4) || (plz.length() > 5)) {
            throw new InvalidAdException("Please enter a valid postcode");
        }

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setPlz(updateForm.getPlz());
        address = addDao.save(address);

        Advert ad = getAd(id);
        if (ad == null) {
            throw new InvalidAdException("AD ID IS INCORRECT!");
        }
        ad.setAddress(address);
        ad.setTitle(updateForm.getTitle());
        ad.setPeopleDesc(updateForm.getPeopleDesc());
        ad.setRoomDesc(updateForm.getRoomDesc());
        ad.setFusedSearch(updateForm.getTitle() + " " + updateForm.getRoomDesc() + " " + updateForm.getPeopleDesc());
        ad.setRoomPrice(Integer.parseInt(updateForm.getRoomPrice()));
        ad.setRoomSize(Integer.parseInt(updateForm.getRoomSize()));
        ad.setNumberOfPeople(Integer.parseInt(updateForm.getNumberOfPeople()));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFrom = null;
        Date dateTo = null;
        if (updateForm.getFromDate() != "") {
            try {
                dateFrom = formatter.parse(updateForm.getFromDate());
                if (updateForm.getToDate() != "") {
                    dateTo = formatter.parse(updateForm.getToDate());
                }
            } catch (ParseException e) {
                dateFrom = new Date();
            }
        }

        ad.setFromDate(dateFrom);
        if (updateForm.getToDate() != "") {
            ad.setToDate(dateTo);
        }
        if (updateForm.getFilenames() != null) {
            for (String file : updateForm.getFilenames()) {
                Picture pic = new Picture();
                pic.setUrl(file);

                ad.addPicture(pic);

                pic = pictureDao.save(pic);
            }
        }
        ad = adDao.save(ad);

    }

    /**
     * Takes the saveForm and checks the content. Saves the content of the
     * search String
     *
     * @param searchForm
     * @param saveToProfile
     * @return
     */
    @Transactional
    public Long saveFromSearch(SearchForm searchForm, boolean saveToProfile) {
        String freetext = searchForm.getSearch();
        String priceFrom = searchForm.getFromPrice();
        String priceTo = searchForm.getToPrice();
        String sizeFrom = searchForm.getFromSize();
        String sizeTo = searchForm.getToSize();
        String area = searchForm.getNearCity();
        String peopleAmount = searchForm.getNumberOfPeople();
        String fromDate = searchForm.getFromDate();
        String toDate = searchForm.getToDate();

        if (!(!StringUtils.isEmpty(freetext) || !priceFrom.equals("0") || !priceTo.equals("0")
                || !sizeFrom.equals("0") || !sizeTo.equals("0") || !StringUtils.isEmpty(area)
                || !StringUtils.isEmpty(peopleAmount) || fromDate != null || toDate != null)) {
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
        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
        Date insertFromdate = null;
        try {
            insertFromdate = dateFormater.parse(fromDate);
        } catch (ParseException ex) {
            //Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date insertTodate = null;
        try {
            insertTodate = dateFormater.parse(toDate);
        } catch (ParseException ex) {
            //Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        search.setFromDate(insertFromdate);
        search.setToDate(insertTodate);
        if (getLoggedInUser() != null && getLoggedInUser().getUserRole().getRole() == 1 && saveToProfile == true) {
            search.setUser(userDao.findOne(searchForm.getUserId()));
        }

        search = searchDao.save(search);

        return search.getId();
    }

    /**
     * Retrieves an ad from the database.
     *
     * @param id
     * @return Advert - the advert returned from the database / Null, when there
     * is no advert with the given id
     */
    public Advert getAd(Long id) {

        return adDao.findOne(id);
    }

    /**
     * This function takes the search form and checks if the content is correct
     * If yes then it commits a search. And retrieves the output The search is
     * done by SQL-Querries
     *
     * @param form
     * @return
     */
    @Transactional
    public Iterable<Advert> findAds(SearchForm form) {

        if (form.getFromPrice() == (null)) {
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

            if (priceMax < priceMin) {
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

            if (roomSizeMax < roomSizeMin) {
                int placeholder = roomSizeMax;
                roomSizeMax = roomSizeMin;
                roomSizeMin = placeholder;
            }

        } catch (NumberFormatException e) {
            roomSizeMin = 0;
            roomSizeMax = 9999999;
        }

        String town = form.getNearCity();
        if (town == null || town.length() == 0) {
            town = "";

        }

        String textSearch = form.getSearch();
        if (textSearch == null || textSearch.length() == 0) {
            textSearch = "";
        }

        boolean simpleSearch = false;
        Iterable<org.sample.model.Advert> ads = null;
        boolean noDateRangeUp = false;
        boolean noDateRangeDown = false;
        int people = 0;
        if (form.getNumberOfPeople() == null || form.getNumberOfPeople().equals("") || form.getNumberOfPeople().length() == 0) {
            people = 99;
        } else {
            people = Integer.parseInt(form.getNumberOfPeople());
        }

        Date dateFrom = null;
        Date dateTo = null;
        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");

        if (form.getFromDate() != null && form.getFromDate() != "") {
            try {
                dateFrom = dateFormater.parse(form.getFromDate());
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            try {
                dateFrom = dateFormater.parse("01/01/1980");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            noDateRangeDown = true;
        }

        if (form.getToDate() != null && form.getToDate() != "") {
            try {
                dateTo = dateFormater.parse(form.getToDate());

            } catch (Exception ex) {

            }
        } else {
            try {
                dateTo = dateFormater.parse("01/01/2100");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            noDateRangeUp = true;
        }

        if (dateFrom.compareTo(dateTo) > 0) {
            /*Turn the dates if they are not in timeline*/
            Date placeholder = dateTo;
            dateTo = dateFrom;
            dateFrom = placeholder;
        }

        if (noDateRangeDown == false && noDateRangeUp == false) {
            ads = adDao.findByFromDateBeforeAndToDateAfterAndNumberOfPeopleLessThanEqualAndRoomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(dateFrom, dateTo, people, priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);
        } else if (noDateRangeDown == true && noDateRangeUp == false) {
            ads = adDao.findByToDateAfterAndNumberOfPeopleLessThanEqualAndRoomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(dateTo, people, priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);
        } else if (noDateRangeDown == false && noDateRangeUp == true) {
            ads = adDao.findByFromDateBeforeAndToDateAfterAndNumberOfPeopleLessThanEqualAndRoomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(dateFrom, dateFrom, people, priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);
        } else if (noDateRangeDown == true && noDateRangeUp == true) {
            ads = adDao.findByroomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);

        }

        return ads;
    }

    /**
     * Retrieves the current logged in user from spring Security
     *
     * @return the logged in user
     */
    public org.sample.model.User getLoggedInUser() {
        return (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Retrieves all ads created by given user from the database.
     *
     * @param user - the user to look for ads
     * @return Iterable<Adverts> - All ads created by the given user
     */
    public Iterable<Advert> findAdsForUser(org.sample.model.User user) {
        return adDao.findByUserId(user.getId());
    }

    /**
     * Deletes a search with given id
     *
     * @param value - the id of the search to delete
     */
    public void removeSearch(Long searchId) {
        Search search = searchDao.findOne(searchId);
        search.setUser(null);
        searchDao.save(search);
    }

    /**
     * Bookmarks an ad specified in the bookmarkForm parameter
     *
     * @param bookmarkForm
     * @return the id of the bookmark entry
     */
    public Long bookmark(BookmarkForm bookmarkForm) {

        Bookmark bookmark = new Bookmark();
        bookmark.setAd(adDao.findById((long) Integer.parseInt(bookmarkForm.getAdNumber())));
        bookmark.setUser(userDao.findByUsername(bookmarkForm.getUsername()));

        return bookmarkDao.save(bookmark).getId();
    }

    public boolean checkBookmarked(Long id, org.sample.model.User user) {

        return (bookmarkDao.findByAdAndUser(adDao.findById(id), user) != null) ? true : false;
    }

    /**
     * Retrieves a list of bookmarked ads for a given user
     *
     * @param user
     * @return Iterable<Adverts> - List of bookmarked ads
     */
    public Object findBookmarkedAdsForUser(org.sample.model.User user) {

        Iterable<Bookmark> bookmarks = bookmarkDao.findByUser(user);
        ArrayList<Advert> ads = new ArrayList<Advert>();
        for (Bookmark bm : bookmarks) {
            ads.add(bm.getAd());
        }
        return ads;
    }

    /**
     * Removes a bookmark in the db
     *
     * @param adid
     * @param username
     */
    public void deleteBookmark(String adid, String username) {
        bookmarkDao.delete(bookmarkDao.findByAdAndUser(adDao.findById(Long.parseLong(adid)), userDao.findByUsername(username)));

    }

    /**
     * Finds the notifications of a given user
     *
     * @param user
     * @return
     */
    public Object findNotificationsForUser(org.sample.model.User user) {

        return notifiesDao.findByToUser(user);
    }

    /**
     * Creates a new text notification, this is used for system messages to the
     * user
     *
     * @param user - the user to whom to display the message
     * @param text - the text you want to display to the user
     */
    public void createNotification(org.sample.model.User user, String text) {

        Notifies note = new Notifies();
        note.setText(text);
        note.setToUser(user);
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.MESSAGE);
        note.setSeen(0);
        notifiesDao.save(note);

        try {
            /*Place to directly send an email to the user as notification*/
            email.GenerateAnEmail(note);
        } catch (MessagingException ex) {
            Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getNotificationsForJSP() {
        SampleServiceImpl sV = new SampleServiceImpl();

        return sV.userDao.findByUsername("sz@tune-x.ch").getUsername();
    }

    /**
     * Sets a notification as read.
     *
     * @param noteid
     */
    public void setRead(String noteid) {

        Notifies note = notifiesDao.findById(Long.parseLong(noteid));
        note.setSeen(1);
        notifiesDao.save(note);
    }

    /**
     * According to the selected search (i.e. filter) in users' profiles, this
     * creates notifications as soon as new (matching) ads are created.
     *
     * @param id The id of the advert that causes new notifications for saved
     * searches.
     */
    public void createNewSearchNotifications(Long id) {
        Advert advert = adDao.findOne(id);
        List<org.sample.model.User> possibleUsersForSearchNotification = userDao.findByselectedSearchGreaterThanEqual(Long.valueOf(1));

        for (org.sample.model.User user : possibleUsersForSearchNotification) {
            Search search = searchDao.findOne(user.getSelectedSearch());
            if ((advert.getTitle().contains(search.getFreetext()) || advert.getRoomDesc().contains(search.getFreetext()))
                    && (search.getPriceFrom() == "" || advert.getRoomPrice() >= Integer.parseInt(search.getPriceFrom()))
                    && (search.getPriceTo() == "" || advert.getRoomPrice() <= Integer.parseInt(search.getPriceTo()))
                    && (search.getSizeFrom() == "" || advert.getRoomSize() >= Integer.parseInt(search.getSizeFrom()))
                    && (search.getSizeTo() == "" || advert.getRoomSize() <= Integer.parseInt(search.getSizeTo()))
                    && (search.getArea() == "" || advert.getAddress().getCity().equals(search.getArea()))
                    && (search.getPeopleAmount() == "" || advert.getnumberOfPeople() == Integer.parseInt(search.getPeopleAmount()))
                    && (search.getFromDate() == null || advert.getFromDate().equals(search.getFromDate()) || advert.getFromDate().after(search.getFromDate()))
                    && (search.getToDate() == null || advert.getToDate().equals(search.getToDate()) || advert.getToDate().before(search.getToDate()))) {

                Notifies newNotification = new Notifies();
                newNotification.setSearch(search);
                newNotification.setAd(advert);
                newNotification.setToUser(user);
                newNotification.setDate(new Date());
                newNotification.setSeen(0);
                notifiesDao.save(newNotification);

                try {
                    email.GenerateAnEmail(newNotification);
                } catch (MessagingException ex) {
                    Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    /**
     * Creates an enquiry for a given ad with given text
     *
     * @param enquirytext
     * @param adid
     * @return the Enquiry object entered to the database
     */
    public Object sendEnquiry(String enquirytext, String adid) {
        Enquiry enquiry = new Enquiry();
        Advert ad = adDao.findById(Long.parseLong(adid));
        enquiry.setEnquiryText(enquirytext);
        //enquiry.setEnquiryTitle("Enquiry from User "+ this.getLoggedInUser().getUsername() );
        enquiry.setAdvert(ad);
        enquiry.setUserFrom(this.getLoggedInUser());
        enquiry.setUserTo(ad.getUser());
        return enquiryDao.save(enquiry);
    }

    /**
     * Creates a new enquiry notification, to the user created the ad
     *
     * @param enq
     * @return true: if notification was generated successfully
     */
    public boolean createNotificationEnquiry(Enquiry enq) {

        Notifies note = new Notifies();
        note.setText(enq.getEnquiryText());
        note.setToUser(enq.getUserTo());
        note.setFromUser(enq.getUserFrom());
        note.setAd(enq.getAdvert());
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.ENQUIRY);
        note.setSeen(0);
        note = notifiesDao.save(note);

        try {
            email.GenerateAnEmail(note);
        } catch (MessagingException ex) {
            Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (note != null) ? true : false;
    }

    /**
     * Creates a bookmark
     *
     * @param mark
     * @return
     */
    public boolean createNotificationBookmark(Bookmark mark) {

        Notifies note = new Notifies();
        note.setToUser(mark.getUser());
        note.setFromUser(null);
        note.setAd(mark.getAd());
        note.setBookmark(mark);
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.BOOKMARK);
        note.setSeen(0);
        note = notifiesDao.save(note);

        try {
            email.GenerateAnEmail(note);
        } catch (MessagingException ex) {
            Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (note != null) ? true : false;
    }

    /**
     * Checks if enquiry was already sent for given ad (id) and user
     *
     * @param id
     * @param loggedInUser
     * @return true: if enquiry was already sent, false: if no enquiry was sent
     * yet
     */
    public boolean checkSentEnquiry(Long id, org.sample.model.User loggedInUser) {

        Enquiry enq = enquiryDao.findByAdvertAndUserFrom(adDao.findById(id), loggedInUser);

        if (enq != null) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves all bookmarks for ad id
     *
     * @param id
     * @return
     */
    public Object findBookmarksForAd(Long id) {

        Iterable<Bookmark> bookmarks = bookmarkDao.findByAd(adDao.findById(id));
        Iterator it = bookmarks.iterator();
        ArrayList<Bookmark> marks = new ArrayList<Bookmark>();
        if (!it.hasNext()) {
            return null;
        } else {
            while (it.hasNext()) {
                marks.add((Bookmark) it.next());
            }
            return marks;
        }

    }

    /**
     * sends notifications to users who have bookmarked the given ad
     *
     * @param findBookmarksForAd
     */
    public void sendNotificationsForBookmarks(Object bookmarks) {
        if (bookmarks != null) {
            for (Bookmark mark : (List<Bookmark>) bookmarks) {
                createNotificationBookmark(mark);
            }
        }
    }

    /**
     * This finds the enquiries a user received
     *
     * @param loggedInUser the user which is logged in
     * @return
     */
    public Object findSentEnquiriesForUser(org.sample.model.User loggedInUser) {

        return enquiryDao.findByUserFrom(loggedInUser);
    }

    /**
     * This method set the bookmark as readed
     *
     * @param adid
     */
    public void setReadBookmarkNote(String adid) {

        Bookmark bm = bookmarkDao.findByAdAndUser(adDao.findById(Long.parseLong(adid)), getLoggedInUser());
        List<Notifies> notes = notifiesDao.findByToUserAndBookmark(getLoggedInUser(), bm);
        for (Notifies note : notes) {
            note.setSeen(1);
            notifiesDao.save(note);
        }
    }

    /**
     * This method finds the enquiries which are send for one advert
     *
     * @param ad
     * @return
     */
    public Object findEnquiriesForAd(Advert ad) {

        return enquiryDao.findByAdvert(ad);
    }

    /**
     * This method finds the notfications of type enquiry for one advert
     *
     * @param ad
     * @return
     */
    public List<Notifies> findNotificationsEnquiryForAd(Advert ad) {

        return notifiesDao.findByAdAndNotetype(ad, Type.ENQUIRY);
    }

    /**
     * This method set an enquiry note as readed
     *
     * @param id
     */
    public void setReadEnquiryNoteForAdId(String id) {
        List<Notifies> notes = notifiesDao.findByAdAndNotetype(adDao.findById(Long.parseLong(id)), Type.ENQUIRY);
        for (Notifies note : notes) {
            note.setSeen(1);
            notifiesDao.save(note);
        }
    }

    /**
     * This method generates with the invForm the invitation on database level
     *
     * @param invForm
     * @throws InvalidDateParseException
     */
    public void createInvitation(InvitationForm invForm) throws InvalidDateParseException {
        Invitation inv = new Invitation();
        Advert ad = adDao.findById(invForm.getAdvertId());

        inv.setAdvert(ad);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            inv.setFromDate(formatter.parse(invForm.getFromDate()));
        } catch (ParseException e) {
            throw new InvalidDateParseException("no parse possible");
        }
        inv.setTextOfInvitation(invForm.getTextOfInvitation());
        inv.setToDate(calculateToDate(invForm));

        inv = invitationDao.save(inv);

        for (String id : invForm.getSelected_enquiries().split(",")) {

            Enquiry enq = enquiryDao.findById(Long.parseLong(id));
            enq.setStatus(InvitationStatus.UNKNOWN);
            enq.setRating(1);
            enq.setInvitation(inv);
            enq = enquiryDao.save(enq);
            createNotificationInvitation(enq, inv);
        }
    }

    /**
     * Creates an notification for an invitation, starts also the class to send
     * an email of this notification
     *
     * @param enq
     * @param inv
     * @return
     */
    public boolean createNotificationInvitation(Enquiry enq, Invitation inv) {

        Notifies note = new Notifies();
        note.setText("You are invited to visit the room " + enq.getAdvert().getTitle() + ", on " + inv.getFromDate() + "<br/><br/> " + inv.getTextOfInvitation());
        note.setToUser(enq.getUserFrom());
        note.setFromUser(enq.getUserTo());
        note.setAd(enq.getAdvert());
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.INVITATION);
        note.setInvitation(inv);
        note.setSeen(0);
        note = notifiesDao.save(note);

        try {
            /*Place to directly send an email to the user as notification*/
            email.GenerateAnEmail(note);
        } catch (MessagingException ex) {
            Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (note != null) ? true : false;
    }

    /**
     * Calculates the toDate of the invitation, generates a date of format:
     * "yyyy-MM-dd HH:mm"
     *
     * @param invForm
     * @return
     * @throws InvalidDateParseException
     */
    public Date calculateToDate(InvitationForm invForm) throws InvalidDateParseException {

        Date durationDate = null;
        Date toDate = new Date();
        Date fromDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        try {
            durationDate = formatter.parse(invForm.getDuration());
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            fromDate = formatter.parse(invForm.getFromDate());
        } catch (ParseException e) {
            throw new InvalidDateParseException("no parse possible");
        }
        toDate.setTime(fromDate.getTime() + durationDate.getTime());
        return toDate;

    }

    /**
     * This method finds the invitations for one advert
     *
     * @param ad
     * @return
     */
    public List<Invitation> findInvitationsForAd(Advert ad) {
        return (List<Invitation>) invitationDao.findByAdvert(ad);
    }

    /**
     * Is finding all invitation which are not cancelled for one advert
     *
     * @param ad
     * @return
     */
    public List<Invitation> findNotCancelledInvitationsForAd(Advert ad) {
        List<Invitation> invs = (List<Invitation>) invitationDao.findByAdvert(ad);
        ArrayList<Invitation> notCInvs = new ArrayList<Invitation>();
        for (Invitation inv : invs) {
            if (!inv.getCancelled()) {
                notCInvs.add(inv);
            }
        }
        return notCInvs;
    }

    /**
     * This method deletes an invitation, it also send a note about that to
     * inform the people which were invited before.
     *
     * @param id
     */
    public void cancelInvitation(Long id) {
        Invitation inv = invitationDao.findById(id);
        for (Enquiry enq : enquiryDao.findByInvitation(inv)) {
            createNotification(enq.getUserFrom(), "The invitation for advert '" + enq.getAdvert().getTitle() + "' was cancelled.");
            enq.setInvitation(null);
            enq.setStatus(InvitationStatus.UNKNOWN);
            enq = enquiryDao.save(enq);
        }
        inv.setCancelled(true);
        inv = invitationDao.save(inv);
    }

    /**
     * Is setting all enquiry notification to status readed
     *
     * @param userid
     */
    public void setEnquiryNotificationsReadForUserId(Long userid) {
        List<Notifies> notes = (List<Notifies>) notifiesDao.findByToUser(userDao.findById(userid));
        for (Notifies note : notes) {
            if (note.getNotetype().equals(Notifies.Type.INVITATION)) {
                note.setSeen(1);
                note = notifiesDao.save(note);
            }
        }
    }

    /**
     * This method allows sets the status of the invitation to accepted
     *
     * @param id
     */
    public void acceptInvitationForEnquiryId(Long id) {
        Enquiry enq = enquiryDao.findById(id);
        enq.setStatus(InvitationStatus.ACCEPTED);
        enq = enquiryDao.save(enq);
        createNotificationEnquiry(enq);
    }

    /**
     * This method sets the status of the invitation to not accepted
     *
     * @param id
     */
    public void cancelInvitationForEnquiryId(Long id) {
        Enquiry enq = enquiryDao.findById(id);
        enq.setStatus(InvitationStatus.CANCELLED);
        enq = enquiryDao.save(enq);
        createNotificationEnquiry(enq);
    }

    /**
     * Is setting a rank for an enquiry with a given id.
     *
     * @param id
     * @param rank
     */
    public void setRatingForEnquiry(Long id, int rank) {
        Enquiry enq = enquiryDao.findById(id);
        enq.setRating(rank);
        enq = enquiryDao.save(enq);
    }

    /**
     * finds all dependencies of given ad id and removes them
     */
	public void deleteAd(Long id) {
		Advert ad = adDao.findById(id);
		Iterable<Notifies> notes = notifiesDao.findByAd(ad);
		notifiesDao.deleteInBatch(notes);
		Iterable<Enquiry> enqs = enquiryDao.findByAdvert(ad);
		enquiryDao.deleteInBatch(enqs);
		Iterable<Invitation> invs = invitationDao.findByAdvert(ad);
		invitationDao.deleteInBatch(invs);
		Iterable<Bookmark> bms = bookmarkDao.findByAd(ad);
		bookmarkDao.deleteInBatch(bms);
		adDao.delete(ad);
	}

	public void deletePic(Long picid,Long adid) {
		Advert ad = adDao.findById(adid);
		Set<Picture> pics = ad.getPictures();
		Picture picToRemove = null;
		for(Picture p : pics)
		{
			if(p.getId()==picid)
			{
				picToRemove = p;
			}
		}
		if(picToRemove!=null)
		{
			pics.remove(picToRemove);
			ad.setPictures(pics);
			adDao.save(ad);
			pictureDao.delete(picToRemove);
		}
	}

}
