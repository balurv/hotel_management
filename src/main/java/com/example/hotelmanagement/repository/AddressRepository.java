package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByHotelId(long hotelId);
}
