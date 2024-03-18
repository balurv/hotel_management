package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.PersonRoomBookingDetails;
import com.example.hotelmanagement.dto.RoomBookingDetails;
import com.example.hotelmanagement.entity.Person;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.PersonIdException;
import com.example.hotelmanagement.exception.RoomBookedException;
import com.example.hotelmanagement.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<?> savePerson(@RequestBody Person person) {
        Person savedPerson = personService.savePerson(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping("/bookedRooms")
    public ResponseEntity<?> getPersonBookedRooms(long personId) {
        try {
            List<PersonRoomBookingDetails[]> roomList = personService.getRoomsBooked(personId);
            return new ResponseEntity<>(roomList, HttpStatus.OK);
        } catch (PersonIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
