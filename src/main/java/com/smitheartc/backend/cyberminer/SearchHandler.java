package com.smitheartc.backend.cyberminer;

import com.smitheartc.backend.database.CircularShiftEntity;
import com.smitheartc.backend.database.CircularShiftRepository;
import com.smitheartc.backend.database.SearchObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SearchHandler {
    @Autowired
    private CircularShiftRepository circularShiftRepository;

    public Page<CircularShiftEntity> handleSearch(SearchObject searchObject) {

        Sort sort;
        switch(searchObject.sortMethod) {
            case "alphabetical":
                sort = JpaSort.unsafe(Sort.Direction.ASC, "lower(circularShift)");
                break;
            case "hits":
                sort = Sort.by("urlHits").ascending();
                break;
            default:
                sort = Sort.by("urlHits").ascending();
        }
        Pageable pageable = PageRequest.of(searchObject.pageNumber, searchObject.numberOfResults, sort);

        switch(searchObject.operator) {
            case "OR":
                System.err.println("Or detected");
                break;
            case "AND":
                return circularShiftRepository.andSearch(searchObject.searchTerm, pageable);
            case "NOT":
                System.err.println("Not detected");
                break;
        }
        return null;
    }
}
