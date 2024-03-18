package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Address;
import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.HotelIdException;
import com.example.hotelmanagement.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<?> saveHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @PutMapping("/address")
    public ResponseEntity<?> upateHotelAddress(long hotelId, Address address) {
        Address updatedAddress = hotelService.updateAddress(hotelId, address);
        if (updatedAddress == null) {
            return new ResponseEntity<>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<?> getAllHotel() {
        List<Hotel> hotelList = hotelService.getAllHotel();
        return new ResponseEntity<>(hotelList, HttpStatus.OK);
    }

    @GetMapping("/room")
    public ResponseEntity<?> getRoomOfHotel(@RequestParam long hotelId) {
        List<Room> roomList = hotelService.getAllRoom(hotelId);
        if (roomList != null) {
            return new ResponseEntity<>(roomList, HttpStatus.OK);
        }
        return new ResponseEntity<>("BAD REQUEST: CHECK HOTEL_ID", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/roomBooked")
    public ResponseEntity<?> getHotelRoomBooked(long hotelId) {
        try {
            List<Room> roomList = hotelService.getHotelRoomBooked(hotelId);
            return new ResponseEntity<>(roomList, HttpStatus.OK);
        } catch (HotelIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/address")
    public ResponseEntity<?> getHotelAddress(long hotelId){
        try{
            Address address = hotelService.getHotelAddress(hotelId);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (HotelIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/room")
    public ResponseEntity<?> saveRoom(@RequestParam Long hotelId, @RequestBody Room room) {
        Room savedRoom = hotelService.saveRoom(hotelId, room);
        if (savedRoom != null) {
            return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("BAD REQUEST: CHECK HOTEL_ID", HttpStatus.BAD_REQUEST);
    }
}
