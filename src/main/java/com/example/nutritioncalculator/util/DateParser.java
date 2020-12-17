package com.example.nutritioncalculator.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service converts string into LocalDate object.
 *
 * @author Bartek Dec
 */
@Service
public class DateParser {

    /**
     * This method converts provided string into the corresponding LocalDate object.
     *
     * @param stringDate Date in string format.
     * @return Instance of LocalDate.
     */
    public LocalDate convertDate(String stringDate) {
        String[] date = stringDate.split("-");

        return LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
    }
}
