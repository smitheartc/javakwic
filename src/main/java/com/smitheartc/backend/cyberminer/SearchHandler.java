package com.smitheartc.backend.cyberminer;

import com.smitheartc.backend.database.CircularShiftEntity;
import com.smitheartc.backend.database.CircularShiftRepository;
import com.smitheartc.backend.database.SearchObject;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.javatuples.Pair;


import java.util.Collection;
import java.util.List;

@Service
public class SearchHandler {
    @Autowired
    private CircularShiftRepository circularShiftRepository;

    @PersistenceContext
    EntityManager entityManager;

    public Page<CircularShiftEntity> handleSearch(SearchObject searchObject) {

        Sort sort;
        switch(searchObject.sortMethod) {
            case "alphabetical":
                sort = JpaSort.unsafe(Sort.Direction.ASC, "lower(circularShift)");
                break;
            case "hits":
                sort = Sort.by("urlHits").descending();
                break;
            default:
                sort = Sort.by("urlHits").ascending();
        }
        Pageable pageable = PageRequest.of(searchObject.pageNumber, searchObject.numberOfResults, sort);

        switch(searchObject.operator) {
            case "OR":
                String[] keywordArray = searchObject.searchTerm.split(" ");
                if (keywordArray.length == 1) {
                    return circularShiftRepository.andSearch(searchObject.searchTerm, pageable);
                }
                int pageSize = pageable.getPageSize();
                int offset = pageable.getPageNumber() * pageSize;

                StringBuilder queryBuilder = new StringBuilder("SELECT e.circularShift, e.url FROM CircularShiftEntity e WHERE e.hash IN (SELECT c.hash FROM CircularShiftEntity c WHERE ");
                // Execute the query and get the results
                for (String keyword : keywordArray) {
                    queryBuilder.append("c.circularShift")
                            .append(" LIKE '%")
                            .append(keyword) // Escape single quotes for SQL safety
                            .append("%'")
                            .append(" OR ");
                }
                int currentLength = queryBuilder.length();

                if (currentLength >= 4) {
                    queryBuilder.setLength(currentLength - 4);
                }

                queryBuilder.append(") AND e.shifted = FALSE");
                TypedQuery<CircularShiftEntity> query = entityManager.createQuery(queryBuilder.toString(), CircularShiftEntity.class);

                query.setFirstResult(offset); // Sets the starting row
                query.setMaxResults(pageSize);

                List<CircularShiftEntity> content = query.getResultList();

                String countQueryString = "SELECT COUNT(e.hash) " + queryBuilder.substring(queryBuilder.indexOf("FROM"));

                TypedQuery<Long> countQuery = entityManager.createQuery(countQueryString, Long.class);
                Long total = countQuery.getSingleResult();

                Page<CircularShiftEntity> returnPage = new PageImpl<>(content, pageable, total);
                return returnPage;

//                    return circularShiftRepository.orSearch(queryBuilder.toString(), pageable);
            case "AND":
                return circularShiftRepository.andSearch(searchObject.searchTerm, pageable);
            case "NOT":
                return circularShiftRepository.notSearch(searchObject.searchTerm, pageable);
        }
        return null;
    }
}
