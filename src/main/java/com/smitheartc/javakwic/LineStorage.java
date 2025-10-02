package com.smitheartc.javakwic;

import org.springframework.stereotype.Service;

@Service
public class LineStorage {
    private StringBuffer characterStorage;

    private int lineCount;

    public int getLineCount() {
        return lineCount;
    }
    public void setLineCount(int i) {
        this.lineCount = i;
    }

    public LineStorage(){ //constructor
        characterStorage = new StringBuffer();
        lineCount = 0;
    }

    public void addLine(String line) {

        if (!(characterStorage.length() == 0)) {
            characterStorage.append('$');
        }
        
        characterStorage.append(line);
        lineCount++;

        System.err.println(characterStorage.toString());

        }

    public String getLine(int lineNumber) {
        return characterStorage.toString().split("\\$")[lineNumber];
    }

    public int word(int lineNumber) { //returns number of words in a line
        int number = this.getLine(lineNumber).split(" ").length; //retrieves line, then splits that line into words, then gets amount of words
        return number;
        
    }

    public void clearMemory(){
        characterStorage = new StringBuffer();
    }
}
