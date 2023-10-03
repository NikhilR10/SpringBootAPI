package com.mainspring.app.service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mainspring.app.entity.Customer;
import com.mainspring.app.entity.CustomerCreationStatus;
import com.mainspring.app.model.CustomerModel;
import com.mainspring.app.repository.CustomerCreationStatusRepository;
import com.mainspring.app.repository.CustomerRespository;
import com.mainspring.app.utils.HttpUtil;

@Service
public class CustomerService {

	@Autowired
	private HttpUtil httpUtil;

	@Autowired
	private CustomerRespository customerRespository;

	@Autowired
	private CustomerCreationStatusRepository customerCreationStatusRepository;

	private static final String CREATE_ACCOUNT = "http://localhost:9090/createAccount?customerId=";

	public CustomerCreationStatus createCustomer(CustomerModel cus) {
		Customer customer = new Customer();
		customer.setName(cus.getName());
		customer.setAddress(cus.getAddress());
		customer.setDob(cus.getDob());
		customer.setPanNo(cus.getPanNo());
		Customer save = customerRespository.save(customer);

		HttpRequest createAccountReq = HttpRequest.newBuilder().uri(URI.create(CREATE_ACCOUNT + save.getId()))
				.header("Content-Type", "application/json").POST(BodyPublishers.noBody()).build();
		
		httpUtil.getApiResponse(createAccountReq);

		CustomerCreationStatus status = new CustomerCreationStatus();
		status.setCustomerId(save);
		status.setMessage("Customer Created Successfully");
		status.setCreatedAt(LocalDateTime.now());
		return customerCreationStatusRepository.save(status);
	}

	public ResponseEntity<Customer> getCustomerDetails(int customerId) {
		Optional<Customer> customer = customerRespository.findById(customerId);
		if (customer.isPresent())
			return new ResponseEntity<>(customer.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

}
