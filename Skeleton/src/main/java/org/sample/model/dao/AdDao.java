package org.sample.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.sample.model.Advert;
import org.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdDao  extends JpaRepository<Advert,Long>
{
	// Suchanfragen können hier erstellt werden
	// siehe http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-lookup-strategies
	
	// z.B. so:
	@Query(value = "SELECT * FROM Advert WHERE roomDesc LIKE '*?1*'", nativeQuery = true)
	Advert findByRoomDesc(String roomDesc);
        
        //The completeSearchString for the hardInfos like price range, etc... , Beware, Values could be 0...
        @Query
        List <Advert> findByroomPriceBetween(int roomPriceMin, int roomPriceMax);
        
        @Query
        List <Advert> findByroomSizeBetween(int roomSizeMin, int roomSizeMax);
        
        @Query(value = "SELECT * FROM Advert WHERE user_id = ?1", nativeQuery = true)
        List <Advert> findByUserId(Long userid);
        
        @Query
        List <Advert> findByroomPriceBetweenAndRoomSizeBetween(int roomPriceMin, int roomPriceMax, int roomSizeMin, int roomSizeMax);
        
        @Query
        List <Advert> findByroomPriceBetweenAndRoomSizeBetweenAndRoomDescContaining(int roomPriceMin, int roomPriceMax, int roomSizeMin, int roomSizeMax, String search);

        @Query
        List <Advert> findByroomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndRoomDescContaining(int roomPriceMin, int roomPriceMax, int roomSizeMin, int roomSizeMax, String City, String search);

        @Query
        List <Advert> findByroomPriceBetweenAndRoomSizeBetweenAndRoomDescContainingAndAddressCity(int roomPriceMin, int roomPriceMax, int roomSizeMin, int roomSizeMax, String search, String City);


}
