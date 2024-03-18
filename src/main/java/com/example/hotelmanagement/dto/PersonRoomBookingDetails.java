package com.example.hotelmanagement.dto;

import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonRoomBookingDetails {
    private Booking booking;
    private Room room;
}
