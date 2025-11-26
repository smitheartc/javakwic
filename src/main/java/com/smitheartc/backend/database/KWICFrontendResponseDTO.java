package com.smitheartc.backend.database;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class KWICFrontendResponseDTO {
    
    private ArrayList<String> circularShiftedLines = new ArrayList<>();
    public ArrayList<String> getCircularShiftedLines() {
        return circularShiftedLines;
    }
    
    private ArrayList<String> alphabetizedLines = new ArrayList<>();
    public ArrayList<String> getAlphabetizedLines() {
        return alphabetizedLines;
    }

    
    public void clearMemory() {
        circularShiftedLines = new ArrayList<>();
        alphabetizedLines = new ArrayList<>();
    }   
    
    
}
