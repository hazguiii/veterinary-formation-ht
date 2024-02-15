package com.veterinary.controller;

import com.veterinary.dto.TreatmentDto;
import com.veterinary.manager.TreatmentManager;
import com.veterinary.model.Treatment;
import feign.Response;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@Validated
@Slf4j
@Timed
@AllArgsConstructor
public class TreatmentController {

    private final TreatmentManager treatmentManager;

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/treatments")
    ResponseEntity<List<TreatmentDto>> findAll(){
        return ResponseEntity.ok(treatmentManager.findAll());
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/add-treatment")
    ResponseEntity<TreatmentDto> saveTreatment(@RequestBody TreatmentDto treatmentDto){
        TreatmentDto savedTreatmentDto = treatmentManager.saveTreatment(treatmentDto);
        return ResponseEntity.ok(savedTreatmentDto);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/update-treatment")
    ResponseEntity<TreatmentDto> updateTreatment(@RequestBody TreatmentDto treatmentDto){
        TreatmentDto updatedTreatment = treatmentManager.updateTreatment(treatmentDto);
        return ResponseEntity.ok(updatedTreatment);
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/delete-treatment")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteTreatment(@RequestBody Long id){
        treatmentManager.deleteTreatment(id);
    }

}
