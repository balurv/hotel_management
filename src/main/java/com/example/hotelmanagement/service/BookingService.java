package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.RoomBookingDetails;
import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.Person;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.RoomBookedException;
import com.example.hotelmanagement.exception.ViolationException;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.PersonRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.util.MyUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Object updatePersonRoomBooking(RoomBookingDetails roomBookingDetails) throws RoomBookedException, ViolationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RoomBookingDetails>> violations = validator.validate(roomBookingDetails);
        if(!violations.isEmpty()){
            throw new ViolationException(violations.toString());
        }

        Optional<Person> optionalPerson = personRepository.findById(roomBookingDetails.getPersonId());
        Optional<Room> optionalRoom = roomRepository.findById(roomBookingDetails.getRoomId());

        if(optionalPerson.isPresent() && optionalRoom.isPresent()){
            Person person = optionalPerson.get();
            Room room = optionalRoom.get();

            if(room.isAvailable()) {
                room.setAvailable(false);
                Booking booking = new Booking();

                booking.setPerson(person);
                booking.setRoom(room);
                booking.setBookedFrom(LocalDateTime.now());
                booking.setBookedTill(MyUtil.addDays(roomBookingDetails.getNumberOfDays()));
                booking.setActive(true);

                bookingRepository.save(booking);
            }
            else {
                throw new RoomBookedException("Room is booked!");
            }
            return personRepository.save(person);
        }
        return null;
    }
}
