package com.example.prog3.utils;

import java.time.LocalDate;

public class YearUtil {
    public static String getLastDigitOfTheYear(){
        return String.valueOf(LocalDate.now().getYear()).substring(2);
    }
}
