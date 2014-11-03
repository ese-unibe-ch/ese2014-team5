/*
*/
package org.sample.model.dao;
import org.sample.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryDAO extends JpaRepository<Enquiry, Long> {
    
Enquiry findbyId(long id);

}