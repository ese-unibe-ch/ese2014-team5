package org.sample.model.dao;

import org.sample.model.Notifies;
import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifiesDao  extends JpaRepository<Notifies,Long>
{
	Iterable<Notifies> findByToUser(User user);

	Notifies findById(long parseLong);
}
