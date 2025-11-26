package com.smitheartc.backend.database;

import java.util.Collection;

import com.smitheartc.backend.cyberminer.SearchHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularShiftRepository extends JpaRepository<CircularShiftEntity, Integer> {

    // @Query("SELECT e.circularShift, LOWER(e.circularShift) AS lowercase FROM CircularShiftEntity e ORDER BY lowercase ASC")
    // Collection<String> getIndexTable();


    @Query("SELECT e.circularShift, e.url FROM CircularShiftEntity e ORDER BY LOWER(e.circularShift) ASC")
    Collection<String> getIndexTable();


    // OR if you want to use a specific JPQL query:
    @Query("SELECT e.circularShift, e.url FROM CircularShiftEntity e WHERE e.hash IN (SELECT c.hash FROM CircularShiftEntity c WHERE c.circularShift LIKE %:keyword%) AND e.shifted = FALSE")
    Page<CircularShiftEntity> andSearch(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
