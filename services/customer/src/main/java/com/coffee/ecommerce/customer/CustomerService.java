package com.coffee.ecommerce.customer;

import com.coffee.ecommerce.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        Customer customer= customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.id()).orElseThrow(() -> new CustomerNotFoundException(
                String.format("Cannot update customer:: No customer found with the provided ID:: %s",request.id())));

        mergeCusotmer(customer, request);
        customerRepository.save(customer);
    }

    private void mergeCusotmer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if(StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address()!= null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(mapper:: fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existById(String customerId) {
       return  customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with provided id :: %s", customerId)));
    }

    public void deleteCustomerById(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
