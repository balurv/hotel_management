package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.dto.PersonRoomBookingDetails;
import com.example.hotelmanagement.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByPersonId(Long id);

    @Query("SELECT new com.example.hotelmanagement.dto.PersonRoomBookingDetails(b, r) FROM Booking b JOIN b.room r WHERE b.person.id = :personId")
    List<Object[]> findBookingRoomByPersonId(@Param("personId") Long personId);
}
