package org.sample.model.dao;

import java.util.List;

import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Notifies;
import org.sample.model.Notifies.Type;
import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifiesDao  extends JpaRepository<Notifies,Long>
{
	Iterable<Notifies> findByToUser(User user);

	Notifies findById(long parseLong);

	List<Notifies> findByToUserAndBookmark(org.sample.model.User user, Bookmark bookmark);

	List<Notifies> findByAdAndNotetype(Advert ad, Type enquiry);
        
    List<Notifies> findByToUserAndNotetype(User user, Type invitation);

	Iterable<Notifies> findByAd(Advert ad);
}
