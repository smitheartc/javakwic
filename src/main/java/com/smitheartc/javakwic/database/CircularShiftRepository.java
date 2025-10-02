package com.smitheartc.javakwic.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularShiftRepository extends CrudRepository<StoredCircularShift, Integer> {

}
