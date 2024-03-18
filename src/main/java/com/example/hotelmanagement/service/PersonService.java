package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.PersonRoomBookingDetails;
import com.example.hotelmanagement.entity.Person;
import com.example.hotelmanagement.exception.PersonIdException;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.PersonRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<PersonRoomBookingDetails[]> getRoomsBooked(long personId) throws PersonIdException {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            List<PersonRoomBookingDetails[]> result = bookingRepository.findBookingRoomByPersonId(person.getId());
            return result;
        }
        throw new PersonIdException("Provided person id not found");
    }
}
