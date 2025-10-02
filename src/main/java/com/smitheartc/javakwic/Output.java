package com.smitheartc.javakwic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smitheartc.javakwic.database.CircularShiftRepository;
import com.smitheartc.javakwic.database.ResponseDTO;
import com.smitheartc.javakwic.database.StoredCircularShift;

@Component
public class Output {

    @Autowired 
    private AlphabeticShift alphabeticShift;

    @Autowired 
    private CircularShift circularShift;

    @Autowired
    private CircularShiftRepository circularShiftRepository;

    @Autowired 
    private ResponseDTO responseDTO;

    public void storeResults() {
        int numberOfShifts = circularShift.getNumberOfShifts();
        ArrayList<String> frontendResponse = responseDTO.getAlphabetizedLines();

        int lineNumber = 0;

        for (int i = 0; i < numberOfShifts; i++) {

            //janky way of checking if a newline needs to be added
            int csIndex = alphabeticShift.i_th(i);
            int newLineNumber = circularShift.getVirtualCircularShift(csIndex).getValue0();
            String lineToBeStored = circularShift.getLine(csIndex); //gets the actual content of the first circular shift
            if (lineNumber != newLineNumber) {
                frontendResponse.add("\n");
                lineNumber = newLineNumber;
            }
            frontendResponse.add(lineToBeStored);

            StoredCircularShift record = new StoredCircularShift(lineToBeStored);

            circularShiftRepository.save(record);            
        }
    }

    
}
