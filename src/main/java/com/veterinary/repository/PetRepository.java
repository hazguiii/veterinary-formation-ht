package com.veterinary.repository;

import com.veterinary.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query(value = "SELECT * FROM veterinary.pet WHERE weight>:weight",nativeQuery = true)
    List<Pet> findAllPetsHavingWeightMoreThan(Long weight);


}
