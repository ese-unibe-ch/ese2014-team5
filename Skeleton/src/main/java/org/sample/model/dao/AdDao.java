package org.sample.model.dao;

import org.sample.model.Ad;
import org.sample.model.Address;
import org.springframework.data.repository.CrudRepository;


public interface AdDao  extends CrudRepository<Ad,Long>{

}
