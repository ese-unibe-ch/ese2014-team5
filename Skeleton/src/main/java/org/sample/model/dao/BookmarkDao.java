package org.sample.model.dao;

import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkDao  extends JpaRepository<Bookmark,Long>
{

	Bookmark findByAdAndUser(Advert ad,User user);

	Iterable<Bookmark> findByUser(User user);

	Iterable<Bookmark> findByAd(Advert findById);

	Bookmark findById(long parseLong);

}
