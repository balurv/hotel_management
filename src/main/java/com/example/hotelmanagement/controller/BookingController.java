package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.RoomBookingDetails;
import com.example.hotelmanagement.exception.RoomBookedException;
import com.example.hotelmanagement.exception.ViolationException;
import com.example.hotelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping
    public ResponseEntity<?> updateRoom(RoomBookingDetails roomBookingDetails){
        Object object = null;
        try {
            object = bookingService.updatePersonRoomBooking(roomBookingDetails);
        }catch (RoomBookedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if(object == null){
            return new ResponseEntity<>("CHECK THE PROVIDED DATA", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("PERSON_ID BOOKED PROVIDED ROOM_ID", HttpStatus.OK);
    }
}
