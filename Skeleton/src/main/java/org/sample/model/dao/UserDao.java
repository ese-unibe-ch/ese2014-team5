package org.sample.model.dao;

import java.util.List;

import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long> {

	@Query
	User findByUsername(String username);
    
	@Query
    User findByEmail(String email);
    
    @Query
    List<User> findByselectedSearchGreaterThanEqual(Long search);

	User findById(Long id);

}
