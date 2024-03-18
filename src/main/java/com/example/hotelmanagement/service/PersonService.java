package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.PersonRoomBookingDetails;
import com.example.hotelmanagement.dto.RoomBookingDetails;
import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.Person;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.PersonIdException;
import com.example.hotelmanagement.exception.RoomBookedException;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.PersonRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<PersonRoomBookingDetails> getRoomsBooked(long personId) throws PersonIdException {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            List<Booking> bookingList = bookingRepository.findAllByPersonId(person.getId());
            List<Long> roomId = bookingList.stream()
                    .map(booking -> booking.getRoom().getId())
                    .toList();
            List<Room> roomList = roomRepository.findAllById(roomId);
            List<PersonRoomBookingDetails> personRoomBookingDetails = new ArrayList<>();
            personRoomBookingDetails.add(new PersonRoomBookingDetails( bookingList, roomList));
            return personRoomBookingDetails;
        }
        throw new PersonIdException("Provided person id not found");
    }
}
