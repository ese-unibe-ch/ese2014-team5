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
        
//    @Query(value = "SELECT u FROM User u WHERE u.selectedSearch NOT NULL;", nativeQuery = true)
//    List<User> findPossibleUsersForSearchNotification();
        
    @Query
    List<User> findByselectedSearchGreaterThanEqual(Long search);

}