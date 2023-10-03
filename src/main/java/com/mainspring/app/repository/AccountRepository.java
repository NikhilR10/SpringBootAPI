package com.mainspring.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mainspring.app.entity.Account;
import com.mainspring.app.entity.Customer;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	List<Account> findByCustomerId(Customer customerId);

}
