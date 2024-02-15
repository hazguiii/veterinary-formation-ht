package com.veterinary.controller;

import com.veterinary.dto.CustomerDto;
import com.veterinary.manager.CustomerManager;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CustomerController {
    private final CustomerManager customerManager;


    @ApiOperation("get all Customers")
    @Timed
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/customers")
    ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerManager.findAll();
        return ResponseEntity.ok(customers);
    }

    @ApiOperation("save Customer")
    @PostMapping(produces = APPLICATION_JSON_VALUE, path = "/v1/add-customer")
    ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto){
        CustomerDto addedCustomer = customerManager.addCustomer(customerDto);
        return ResponseEntity.ok(addedCustomer);
    }

    @ApiOperation("edit Customer")
    @PutMapping(produces = APPLICATION_JSON_VALUE, path="/v1/edit-customer")
    ResponseEntity<CustomerDto> updatedCustomer(@RequestBody CustomerDto customerDto){
        CustomerDto updatedCustomer = customerManager.updateCustomer(customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @ApiOperation("edit Customer")
    @DeleteMapping (produces = APPLICATION_JSON_VALUE, path="/v1/delete-customer")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCustomer(@RequestBody Long id){
        customerManager.deleteCustomer(id);
    }

}
