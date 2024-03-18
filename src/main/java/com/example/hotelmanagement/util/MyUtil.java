package com.example.hotelmanagement.util;

import java.time.LocalDateTime;

public class MyUtil {
    public static LocalDateTime addDays(int numberOfDays){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterAddingNumberOfDays = now.plusDays(numberOfDays);
        return afterAddingNumberOfDays;
    }
}
