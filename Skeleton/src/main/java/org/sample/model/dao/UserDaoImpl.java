package org.sample.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired(required=true)
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = getSessionFactory().getCurrentSession().createQuery("from User where username='"+username+"'").list();
	//	users = getSessionFactory().getCurrentSession().createQuery("from User where username='"+username+"'").l;
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User save(User user) {
		
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(user);
		session.flush();
		
		return null;
	}

}