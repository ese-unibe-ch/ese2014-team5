package org.sample.model.dao;

import org.sample.model.UserData;
import org.sample.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataDao extends JpaRepository<UserData, Long> {
}