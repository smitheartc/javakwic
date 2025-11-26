package com.smitheartc.backend.kwicSystem;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.javatuples.Pair;
import com.smitheartc.backend.database.CircularShiftRepository;
import com.smitheartc.backend.database.KWICFrontendResponseDTO;
import com.smitheartc.backend.database.CircularShiftEntity;

@Component
public class Output {

    @Autowired
    private LineStorage lineStorage;

    @Autowired 
    private AlphabeticShift alphabeticShift;

    @Autowired 
    private CircularShift circularShift;

    @Autowired
    private CircularShiftRepository circularShiftRepository;

    @Autowired 
    private KWICFrontendResponseDTO kwicFrontendResponseDTO;

    public void storeResults() {
        int numberOfShifts = circularShift.getNumberOfShifts();
        ArrayList<String> frontendResponse = kwicFrontendResponseDTO.getAlphabetizedLines();

        int lineNumber = 0;

        for (int i = 0; i < numberOfShifts; i++) {

            //janky way of checking if a newline needs to be added
            int csIndex = alphabeticShift.i_th(i);
            Pair<Integer,Integer> cs = circularShift.getVirtualCircularShift(csIndex);
            int newLineNumber = cs.getValue0();

            boolean shifted = (cs.getValue1() != 0);

            //adding a unique ID for each cs so table shows who the original is
            int lineHash = lineStorage.getLine(newLineNumber).hashCode();
            String url = lineStorage.getUrl(newLineNumber);
            System.err.println("Here's the url: " + url);

            String lineToBeStored = circularShift.getLine(csIndex); //gets the actual content of the first circular shift
            if (lineNumber != newLineNumber) {
                frontendResponse.add("\n");
                lineNumber = newLineNumber;
            }
            frontendResponse.add(lineToBeStored);

            CircularShiftEntity record = new CircularShiftEntity(lineToBeStored, url, lineHash, shifted);

            circularShiftRepository.save(record);            
        }

    }
    public Collection<String> getIndexTable() {
        return circularShiftRepository.getIndexTable();
    };
    
}
