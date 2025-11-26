package com.smitheartc.backend.cyberminer;

import com.smitheartc.backend.database.SearchObject;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SearchHandler {

    public Collection<String> handleSearch(SearchObject searchObject) {
        switch(searchObject.operator) {
            case "OR":
                System.err.println("Or detected");
                break;
            case "AND":
                System.err.println("And detected");
                break;
            case "NOT":
                System.err.println("Not detected");
                break;
        }
        return null;
    }
}
