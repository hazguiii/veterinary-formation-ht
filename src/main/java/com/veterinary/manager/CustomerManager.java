package com.veterinary.manager;

import com.veterinary.converter.CustomerConverter;
import com.veterinary.converter.CustomerDtoConverter;
import com.veterinary.dto.CustomerDto;
import com.veterinary.model.Customer;
import com.veterinary.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerManager {

    private CustomerRepository customerRepository;

    public Optional<Customer> findCustomerById(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        return customer;
    }


    public List<CustomerDto> findAll() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customersDto = customers.stream().map(customer -> CustomerConverter.newInstance().convert(customer)).collect(Collectors.toList());
        return customersDto;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = CustomerDtoConverter.newInstance().convert(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerConverter.newInstance().convert(savedCustomer);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findById(customerDto.getId());
        if(customer.isPresent()){
            Customer oldCustomer = customer.get();
            oldCustomer.setFirstname(customerDto.getFirstname());
            oldCustomer.setLastname(customerDto.getLastname());
            oldCustomer.setIdNumber(customerDto.getIdNumber());
            oldCustomer.setMail(customerDto.getMail());
            oldCustomer.setPhoneNumber(customerDto.getPhoneNumber());
            Customer updatedCustomer = customerRepository.save(oldCustomer);
            return CustomerConverter.newInstance().convert(updatedCustomer);
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
