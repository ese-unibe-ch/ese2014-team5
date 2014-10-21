package org.sample.model.dao;

import org.sample.model.UserDeprecated;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserDeprecated,Long> {
}
