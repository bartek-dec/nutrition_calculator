package com.example.nutritioncalculator.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateParserTest {

    private DateParser dateParser;

    @BeforeEach
    void setUp() {
        dateParser = new DateParser();
    }

    @Test
    void should_convert_string_to_corresponding_date() {
        //given
        String dateToParse = "2020-01-12";
        LocalDate expectedDate = LocalDate.of(2020, 1, 12);

        //when
        LocalDate date = dateParser.convertDate(dateToParse);

        //then
        assertEquals(expectedDate, date);
    }
}
