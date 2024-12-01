package com.springbootacademy.batch17.pos.service.impl;

import com.springbootacademy.batch17.pos.dto.CustomerDTO;
import com.springbootacademy.batch17.pos.dto.request.CustomerUpdateDTO;
import com.springbootacademy.batch17.pos.entity.Customer;
import com.springbootacademy.batch17.pos.exception.NotFoundException;
import com.springbootacademy.batch17.pos.repo.CustomerRepo;
import com.springbootacademy.batch17.pos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceIMPL implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public String saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getCustomerName(),
                customerDTO.getCustomerAddress(),
                customerDTO.getCustomerSalary(),
                customerDTO.getContactNumber(),
                customerDTO.getNic(),
                customerDTO.getActive()
        );
        customerRepo.save(customer);
        return customerDTO.getCustomerName() + " Saved Successfully";
    }

    @Override
    public String updateCustomer(CustomerUpdateDTO customerUpdateDTO) {
        if (customerRepo.existsById(customerUpdateDTO.getCustomerId())) {
            Customer customer = customerRepo.getReferenceById(customerUpdateDTO.getCustomerId());
            customer.setCustomerName(customerUpdateDTO.getCustomerName());
            customer.setCustomerAddress(customerUpdateDTO.getCustomerAddress());
            customer.setCustomerSalary(customerUpdateDTO.getCustomerSalary());
            customerRepo.save(customer);
            return customerUpdateDTO.getCustomerName() + " updated successfully";

        } else {
            throw new RuntimeException("No data found for this ID");
        }

    }

    @Override
    public CustomerDTO getCustomerById(int customerId) {
        if (customerRepo.existsById(customerId)) {        //this is use for find there is a customer in database from that id
            Customer customer = customerRepo.getReferenceById(customerId);
            CustomerDTO customerDTO = new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCustomerSalary(),
                    customer.getContactNumber(),
                    customer.getNic(),
                    customer.getActive()
            );
            return customerDTO;
        } else {
            throw new RuntimeException("No Customer in that ID"); //exception handle
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        List<Customer> getAllCustomers = customerRepo.findAll();
        if (getAllCustomers.size() > 0) {
            List<CustomerDTO> customerDTOList = new ArrayList<>();

            for (Customer customer : getAllCustomers) {
                CustomerDTO customerDTO = new CustomerDTO(
                        customer.getCustomerId(),
                        customer.getCustomerName(),
                        customer.getCustomerAddress(),
                        customer.getCustomerSalary(),
                        customer.getContactNumber(),
                        customer.getNic(),
                        customer.getActive()
                );
                customerDTOList.add(customerDTO);
            }
            return customerDTOList;
        } else {
            throw new NotFoundException("No Customer Found");
        }


    }

    @Override
    public String deleteCustomer(int customerId) {
        if (customerRepo.existsById(customerId)) {
            customerRepo.deleteById(customerId);
            return "Deleted Successfully" + customerId;
        } else {
            throw new RuntimeException("No customer found in this ID");
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomersByActiveState(boolean activeState) {
        List<Customer> getAllCustomers = customerRepo.findAllByActiveEquals(activeState);
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        for (Customer customer : getAllCustomers) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCustomerSalary(),
                    customer.getContactNumber(),
                    customer.getNic(),
                    customer.getActive()
            );
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }
}
