package com.smitheartc.javakwic;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class LineStorage {
    private StringBuffer characterStorage;

    private ArrayList<String> urlStorage;

    private int lineCount;

    public int getLineCount() {
        return lineCount;
    }
    public void setLineCount(int i) {
        this.lineCount = i;
    }

    public LineStorage(){ //constructor
        characterStorage = new StringBuffer();
        urlStorage = new ArrayList<>();
        lineCount = 0;
    }

    public void addLine(String line) {

        if (!(characterStorage.length() == 0)) {
            characterStorage.append('$');
        }

        String[] splitLine = line.split("~");
        urlStorage.addLast(splitLine[0]);
        characterStorage.append(splitLine[1]);
        lineCount++;

        System.err.println(characterStorage.toString());

    }

    public String getLine(int lineNumber) {
        return characterStorage.toString().split("\\$")[lineNumber];
    }

    public String getUrl(int lineNumber) {
        return urlStorage.get(lineNumber);
    }

    public int word(int lineNumber) { //returns number of words in a line
        int number = this.getLine(lineNumber).split(" ").length; //retrieves line, then splits that line into words, then gets amount of words
        return number;
        
    }

    public void clearMemory(){
        characterStorage = new StringBuffer();
    }
}
