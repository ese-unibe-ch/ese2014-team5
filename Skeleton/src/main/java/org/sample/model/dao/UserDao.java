package org.sample.model.dao;

import java.util.List;

import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);
        
        User findByEmail(String email);

        @Query(value = "SELECT * FROM User WHERE selectedSearch NOT NULL", nativeQuery = true)
		List<User> findAllWithSelectedSearch();
        
        @Query(value = "SELECT selectedSearch FROM User WHERE selectedSearch NOT NULL;", nativeQuery = true)
        List<Integer> findPossibleSearchIDsForNotification();

}

//"SELECT * FROM User INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID;"