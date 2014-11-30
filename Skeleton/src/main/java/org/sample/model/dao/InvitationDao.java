package org.sample.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sample.model.Advert;
import org.sample.model.Invitation;

public interface InvitationDao extends JpaRepository<Invitation, Long> {

	List<Invitation> findByAdvert(Advert ad);

	Invitation findById(Long id);
}
