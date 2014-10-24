package org.sample.model.dao;

import org.sample.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao  extends JpaRepository<Picture,Long>{

}
