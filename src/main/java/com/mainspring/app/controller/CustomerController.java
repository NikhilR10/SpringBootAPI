package com.mainspring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainspring.app.entity.Customer;
import com.mainspring.app.entity.CustomerCreationStatus;
import com.mainspring.app.model.CustomerModel;
import com.mainspring.app.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/createCustomer")
	public ResponseEntity<CustomerCreationStatus> createCustomer(@RequestBody CustomerModel customer){
		return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
	}
	
	@GetMapping("/getCustomerDetails")
	public ResponseEntity<Customer> getCustomerDetails(@RequestParam("customerId") Integer customerId){
		return customerService.getCustomerDetails(customerId);
	}

}
