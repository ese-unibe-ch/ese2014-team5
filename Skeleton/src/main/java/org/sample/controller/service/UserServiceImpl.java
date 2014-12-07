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
public class UserServiceImpl implements UserService, UserDetailsService {

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
     * Retrieves the current logged in user from spring Security
     *
     * @return the logged in user
     */
    public org.sample.model.User getLoggedInUser() {
        return (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
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
     * Sets a notification as read.
     *
     * @param noteid
     */
    public void setRead(String noteid) {

        Notifies note = notifiesDao.findById(Long.parseLong(noteid));
        note.setSeen(1);
        notifiesDao.save(note);
    }
}
