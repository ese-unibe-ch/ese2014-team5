package org.sample.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.sample.model.User;

public class UserDaoImpl implements UserDao {

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

}