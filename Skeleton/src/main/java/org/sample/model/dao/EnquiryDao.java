/*
*/
package org.sample.model.dao;
import org.sample.model.Advert;
import org.sample.model.Enquiry;
import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryDao extends JpaRepository<Enquiry, Long> {
    
Enquiry findById(long id);
Enquiry findByAdvertAndUserFrom(Advert ad, User user);
Iterable<Enquiry> findByUserFrom(User user);
}