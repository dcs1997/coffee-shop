package com.coffee.ecommerce.customer;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request){

      return  ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> uodateCustomer(@RequestBody @Valid CustomerRequest request){

        customerService.updateCustomer(request);
        return  ResponseEntity.accepted().build();
    }


    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){

        return  ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("exist/{customer-id}")
    public ResponseEntity<Boolean> existById(@PathVariable ("customer-id") String customerId){

        return  ResponseEntity.ok(customerService.existById(customerId));
    }

    @GetMapping("{customer-id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable ("customer-id") String customerId){

        return  ResponseEntity.ok(customerService.findCustomerById(customerId));
    }

    @DeleteMapping("{customer-id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable ("customer-id") String customerId){
        customerService.deleteCustomerById(customerId);
        return  ResponseEntity.accepted().build();
    }
}
