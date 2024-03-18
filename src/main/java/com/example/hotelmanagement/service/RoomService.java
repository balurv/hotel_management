package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Scheduled(fixedRate = 60000) // runs every minute, adjust as needed
    public void updateRoomAvailability() {
        logger.info("Starting room availability update at {}", LocalDateTime.now());

        Iterable<Booking> bookings = bookingRepository.findAll();
        for(Booking booking : bookings){
            Optional<Room> optionalRoom = roomRepository.findById(booking.getRoom().getId());
            if(optionalRoom.isPresent()){
                Room room = optionalRoom.get();
                if(booking.isActive() && !room.isAvailable() && booking.getBookedTill() != null && booking.getBookedTill().isBefore(LocalDateTime.now())){
                    booking.setActive(false);
                    room.setAvailable(true);

                    roomRepository.save(room);
                    bookingRepository.save(booking);
                    logger.info("Room {} availability updated to true.", room.getId());
                }
            }
        }
        logger.info("Room availability update completed at {}", LocalDateTime.now());
    }
}
