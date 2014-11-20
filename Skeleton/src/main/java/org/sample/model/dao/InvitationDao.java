package org.sample.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sample.model.Invitation;

public interface InvitationDao extends JpaRepository<Invitation, Long> {

    Invitation findById(long parseLong);

}
