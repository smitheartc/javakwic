package com.smitheartc.javakwic;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class Input {

    @Autowired
    private LineStorage lineStorage;

    //included a read and store operation per instructions lol
    public void read(String itemToRead) {

        this.store(itemToRead);

    }

    public void store(String line) {
        lineStorage.addLine(line);
    }
}

