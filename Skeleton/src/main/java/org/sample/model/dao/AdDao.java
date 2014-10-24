package org.sample.model.dao;

import org.sample.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdDao  extends JpaRepository<Advert,Long>
{
	// Suchanfragen können hier erstellt werden
	// siehe http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-lookup-strategies
	
	// z.B. so:
	@Query(value = "SELECT * FROM Advert WHERE roomDesc LIKE '*?0*'", nativeQuery = true)
	Advert findByRoomDesc(String roomDesc);
}
