package com.smitheartc.backend.cyberminer;

import com.smitheartc.backend.database.CircularShiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlDeleter {

    @Autowired
    private CircularShiftRepository circularShiftRepository;

    @Transactional
    public void deleteUrl(String url) {

        circularShiftRepository.deleteByUrl(url);
    }
}
