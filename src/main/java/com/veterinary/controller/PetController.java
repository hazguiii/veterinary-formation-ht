package com.veterinary.controller;

import com.veterinary.dto.PetDto;
import com.veterinary.manager.PetManager;
import com.veterinary.model.Pet;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
@Timed
public class PetController {

    private final PetManager petManager;


    @ApiOperation("get all pets")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/pets")
    ResponseEntity<List<PetDto>> getAllPets() {
        List<PetDto> pets = petManager.findAll();
        return ResponseEntity.ok(pets);
    }

    @ApiOperation("save pet")
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/add-pet")
    ResponseEntity<PetDto> addPet(@RequestBody  PetDto petDto){
        PetDto addedPet = petManager.addPet(petDto);
        return ResponseEntity.ok(addedPet);
    }

    @ApiOperation("edit pet")
    @PutMapping(produces = APPLICATION_JSON_VALUE, path="/v1/edit-pet")
    ResponseEntity<PetDto> updatedPet(@RequestBody PetDto petDto){
        PetDto updatedPet = petManager.updatePet(petDto);
        return ResponseEntity.ok(updatedPet);
    }

    @ApiOperation("edit pet")
    @DeleteMapping (produces = APPLICATION_JSON_VALUE, path="/v1/delete-pet")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deletePet(@RequestBody Long id){
        petManager.deletePet(id);
    }

    @ApiOperation("get pets with pagination")
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/get-pets-pagination")
    ResponseEntity<Page<Pet>> addPet(@RequestParam int pageNumber,
                                  @RequestParam int size){
        Page<Pet> petsPageable = petManager.getPetsPageable(pageNumber, size);
        return ResponseEntity.ok(petsPageable);
    }

}
