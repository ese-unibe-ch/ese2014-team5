package org.sample.model.dao;

import java.util.List;

import org.sample.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SearchDao  extends JpaRepository<Search,Long>
{

	@Query(value = "SELECT * FROM Search WHERE user_id = ?1", nativeQuery = true)
    List <Search> findByUserId(Long userid);

}
