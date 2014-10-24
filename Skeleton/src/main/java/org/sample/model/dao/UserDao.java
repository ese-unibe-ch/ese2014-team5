package org.sample.model.dao;

import org.sample.model.User;

public interface UserDao {

	User findByUserName(String username);

	User save(User user);

}