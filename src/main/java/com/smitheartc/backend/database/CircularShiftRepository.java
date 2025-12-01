package com.smitheartc.backend.database;

import java.util.Collection;

import com.smitheartc.backend.cyberminer.SearchHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularShiftRepository extends JpaRepository<CircularShiftEntity, Integer> {


    @Query("SELECT e.circularShift, e.url FROM CircularShiftEntity e ORDER BY LOWER(e.circularShift) ASC")
    Collection<String> getIndexTable();


    @Query("SELECT e.circularShift, e.url FROM CircularShiftEntity e WHERE e.hash IN (SELECT c.hash FROM CircularShiftEntity c WHERE c.circularShift LIKE %:keyword%) AND e.shifted = FALSE")
    Page<CircularShiftEntity> andSearch(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Transactional
    void deleteByUrl(String url);

    @Modifying // Indicates that the query is a DML statement (UPDATE/DELETE/INSERT)
    @Transactional // Required for modifying database operations
    @Query("UPDATE CircularShiftEntity e SET e.urlHits = e.urlHits + 1 WHERE e.url = :url")
    void incrementUrlHitsByUrl(@Param("url") String url);

    @Query("SELECT e.circularShift, e.url FROM CircularShiftEntity e WHERE e.hash IN (SELECT c.hash FROM CircularShiftEntity c WHERE c.circularShift NOT LIKE %:keyword%) AND e.shifted = FALSE")
    Page<CircularShiftEntity> notSearch(@Param("keyword") String searchTerm, Pageable pageable);
//
//    @Query("SELECT e.circularShift, e.url FROM CircularShiftEntity e WHERE e.hash IN (SELECT c.hash FROM CircularShiftEntity c WHERE :whereClause) AND e.shifted = FALSE")
//    Page<CircularShiftEntity> orSearch(String whereClause, Pageable pageable);


}
