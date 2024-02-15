package com.veterinary.manager;

import com.veterinary.converter.PetConverter;
import com.veterinary.converter.PetDtoConverter;
import com.veterinary.dto.PetDto;
import com.veterinary.model.Customer;
import com.veterinary.model.Pet;
import com.veterinary.model.Treatment;
import com.veterinary.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PetManager {

    private final PetRepository petRepository;
    private final CustomerManager customerManager;
    private final TreatmentManager treatmentManager;
   public List<PetDto> findAll() {
        return petRepository.findAll().stream()
                .map(pet -> PetConverter.newInstance().convert(pet))
                .collect(Collectors.toList());
    }

    public PetDto addPet(PetDto petDto){
        Pet pet = PetDtoConverter.newInstance().convert(petDto);
        Optional<Customer> customer = customerManager.findCustomerById(petDto.getCustomerId());
        if(customer.isPresent()){
            pet.setCustomer(customer.get());
        }
        List<Treatment> treatment = treatmentManager.findByIds(petDto.getTreatments());
        pet.setTreatments(treatment);
        Pet savedPet = petRepository.save(pet);
        return PetConverter.newInstance().convert(savedPet);
    }

    public PetDto updatePet(PetDto petDto){
        Optional<Pet> petToEdit = petRepository.findById(petDto.getId());
        if(petToEdit.isPresent()){
            Pet pet = petToEdit.get();
            pet.setName(petDto.getName());
            petRepository.save(pet);
            return PetConverter.newInstance().convert(pet);
        }
        return null;
    }

    public void deletePet(Long id){
        petRepository.deleteById(id);
    }

    public List<Pet> getAllPetsHavingWeightMoreThan(Long weight){
        List<Pet> pets = petRepository.findAll();
        return pets.stream().filter(pet -> pet.getWeight()>weight).collect(Collectors.toList());
    }

    public List<Pet> getAllPetsHavingWeightMoreThanSql(Long weight){
        List<Pet> pets = petRepository.findAllPetsHavingWeightMoreThan(weight);
        return pets;
    }

    public Page<Pet> getPetsPageable(int pageNumber, int numberOfElements){
        Pageable page = PageRequest.of(pageNumber, numberOfElements);
        Page<Pet> pets = petRepository.findAll(page);
        return pets;
    }


}
