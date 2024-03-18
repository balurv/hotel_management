package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    Person save(Person person);

    Optional<Person> findById(Long personId);
}
