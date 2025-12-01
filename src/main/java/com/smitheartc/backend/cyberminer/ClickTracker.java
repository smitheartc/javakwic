package com.smitheartc.backend.cyberminer;

import com.smitheartc.backend.database.CircularShiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClickTracker {

    @Autowired
    private CircularShiftRepository circularShiftRepository;

    @Transactional
    public void urlClick(String url) {
        circularShiftRepository.incrementUrlHitsByUrl(url);
    }
}
