package org.sample.model.dao;

import org.sample.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressDao  extends JpaRepository<Address,Long>{

}
