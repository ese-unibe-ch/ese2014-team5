package org.sample.model.dao;

import org.sample.model.Advert;
import org.springframework.data.repository.CrudRepository;

public interface AdDao  extends CrudRepository<Advert,Long>{

}
