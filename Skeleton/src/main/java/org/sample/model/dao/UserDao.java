package org.sample.model.dao;

import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);
        
        User findByEmail(String email);

}

