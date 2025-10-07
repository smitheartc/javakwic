package com.smitheartc.javakwic;

import java.util.ArrayList;
import java.util.Collections;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smitheartc.javakwic.database.ResponseDTO;

@Service
public class AlphabeticShift {
    @Autowired
    private CircularShift circularShift;

    @Autowired
    private ResponseDTO responseDTO;

    private int index = 0;

    private ArrayList<Integer> orderedCSIndices = new ArrayList<>();

    public void alpha() {
        //load in all circularshifts - might need to fix later, we'll see
        ArrayList<String> frontendResponse = responseDTO.getCircularShiftedLines();
        ArrayList<Pair<String,Integer>> circularShifts = new ArrayList<>();
        for (; index < circularShift.getNumberOfShifts(); index++) {
            String circularlyShiftedLine = circularShift.getLine(index);
            frontendResponse.add(circularlyShiftedLine);
            circularShifts.add(new Pair<String,Integer>(circularlyShiftedLine, index));
        }

        frontendResponse.add("\n"); //at the end of each alphabetization, delineate between different lines

        Collections.sort(circularShifts); //puts all circular shifts in alphabetical order



        for (Pair<String, Integer> x : circularShifts) {
            orderedCSIndices.add(x.getValue1());
        }   

        System.err.println(orderedCSIndices);
    }

    public int i_th(int i) {
        return orderedCSIndices.get(i);
    }

    public void clearMemory() {
        orderedCSIndices = new ArrayList<>();
        index = 0;
    }
}
