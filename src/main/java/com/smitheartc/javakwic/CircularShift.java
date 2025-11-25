package com.smitheartc.javakwic;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Service
public class CircularShift {

    @Autowired
    private LineStorage lineStorage;



    private static final Set<String> NOISE_WORDS = new HashSet<>(Arrays.asList( //"noise eliminator"
        "A", "AN", "AND", "ARE", "AS", "AT", "BE", "BUT", "BY", "FOR", "IF",
        "IN", "INTO", "IS", "IT", "NO", "NOT", "OF", "ON", "OR", "SUCH", "THAT",
        "THE", "THEIR", "THEN", "THERE", "THESE", "THEY", "THIS", "TO", "WAS",
        "WILL", "WITH"
    ));

    private ArrayList<Pair<Integer,Integer>> virtualCircularShiftStorage = new ArrayList<>();

    public int getNumberOfShifts() {
        return virtualCircularShiftStorage.size();
    }

    public Pair<Integer,Integer> getVirtualCircularShift(int i) {
        return virtualCircularShiftStorage.get(i);
    }

    public void addLine(int lineNumber){
        int numberOfWords = lineStorage.word(lineNumber);
        for (int offset = 0; offset < numberOfWords; offset++) {
            if (NOISE_WORDS.contains(lineStorage.getLine(lineNumber).split(" ")[offset].toUpperCase())) { //noise eliminator function - if Noise Words "contains" the first word of a circularly shifted line, it is skipped
                continue;
            }
            Pair<Integer, Integer> virtualCircularShift = new Pair<Integer,Integer>(lineNumber, offset);
            virtualCircularShiftStorage.addLast(virtualCircularShift);            
        }
        System.err.println(virtualCircularShiftStorage);
    }

    public String getLine(int circularShiftNumber) {
        if (circularShiftNumber > virtualCircularShiftStorage.size()) return "";

        Pair<Integer, Integer> virtualCircularShift = virtualCircularShiftStorage.get(circularShiftNumber);
        System.err.println("Here is the virtualCircularShift: " + virtualCircularShift);



        String[] originalLine = lineStorage.getLine(virtualCircularShift.getValue0()).split(" ");
        int offset = virtualCircularShift.getValue1();

        String[] shiftedLine = new String[originalLine.length];

        //copy the original array starting from offset into the shifted array starting at 0
        System.arraycopy(originalLine, offset, shiftedLine, 0, originalLine.length - offset);

        //copy what was skipped from the original array into the leftover slots of shifted array
        System.arraycopy(originalLine, 0, shiftedLine, originalLine.length - offset, offset);

        return String.join(" ", shiftedLine);
    }

    public int word(int circularShiftNumber) {
        return -1;
    }

    public void clearMemory() {
        virtualCircularShiftStorage = new ArrayList<>();
    }
}
