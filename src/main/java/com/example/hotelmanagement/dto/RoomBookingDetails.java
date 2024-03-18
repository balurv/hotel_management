package com.example.hotelmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomBookingDetails {
    private long personId;
    private long roomId;
    @Min(value = 1, message = "Number of days must be greater than or equal to 1")
    private int numberOfDays;
}
