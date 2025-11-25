package com.smitheartc.javakwic.database;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularShiftRepository extends CrudRepository<CircularShiftEntity, Integer> {

    @Query("SELECT e.circularShift, LOWER(e.circularShift) AS lowercase FROM CircularShiftEntity e ORDER BY lowercase ASC")
    Collection<String> getIndexTable();

}
