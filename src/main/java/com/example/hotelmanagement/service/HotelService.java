package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Address;
import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.HotelIdException;
import com.example.hotelmanagement.repository.AddressRepository;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BookingRepository bookingRepository;
    public Hotel saveHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }
    @Cacheable("hotels_List")
    public List<Hotel> getAllHotel(){
        return hotelRepository.findAll();
    }

    public Room saveRoom(Long hotelId, Room room) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if(optionalHotel.isPresent()){
            Hotel hotel = optionalHotel.get();
            room.setHotel(hotel);
            return roomRepository.save(room);
        }
        return null;
    }

    @Cacheable("hotels_Room_List")
    public List<Room> getAllRoom(long hotelId) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if(optionalHotel.isPresent()) {
            return roomRepository.findAllByHotelId(hotelId);
        }
        return null;
    }

    public Address updateAddress(long hotelId, Address address) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if(optionalHotel.isPresent()){
            Hotel hotel = optionalHotel.get();
            address.setHotel(hotel);
            return addressRepository.save(address);
        }
        return null;
    }

    public List<Room> getHotelRoomBooked(long hotelId) throws HotelIdException {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if(optionalHotel.isPresent()){
            Hotel hotel = optionalHotel.get();
            List<Room> hotelRoomList = roomRepository.findAllByIsAvailable(false)
                    .stream()
                    .filter(room -> room.getHotel().equals(hotel))
                    .toList();
            return hotelRoomList;
        }
        throw new HotelIdException("Hotel Id not found");
    }

    public Address getHotelAddress(long hotelId) throws HotelIdException {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if(optionalHotel.isPresent()){
           Address address = addressRepository.findByHotelId(hotelId);
           return address;
        }
        throw new HotelIdException("Hotel Id not found");
    }
}
