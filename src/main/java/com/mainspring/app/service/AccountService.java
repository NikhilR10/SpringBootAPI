package com.mainspring.app.service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mainspring.app.entity.Account;
import com.mainspring.app.entity.AccountCreationStatus;
import com.mainspring.app.entity.Customer;
import com.mainspring.app.entity.Statement;
import com.mainspring.app.model.AccountModel;
import com.mainspring.app.repository.AccountCreationStatusRespository;
import com.mainspring.app.repository.AccountRepository;
import com.mainspring.app.repository.CustomerRespository;
import com.mainspring.app.repository.StatementRepository;
import com.mainspring.app.utils.HttpUtil;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountCreationStatusRespository accountCreationStatusRespository;
	
	@Autowired
	private CustomerRespository customerRespository;
	
	@Autowired
	private StatementRepository statementRepository;
	
	@Autowired
	private HttpUtil httpUtil;
	
	private static final String DEPOSIT = "http://localhost:9090/deposit?accountId=";
	private static final String WITHDRAW = "http://localhost:9090/withdraw?accountId=";
	
	public AccountCreationStatus createAccount(Integer customerId) {
		Optional<Customer> customer = customerRespository.findById(customerId);
		if(customer.isEmpty()) 
			return null;
		Account savings = new Account();
		savings.setCustomerId(customer.get());
		savings.setAccountType("SAVINGS");
		savings.setBalance(1000.00);
		savings.setCreatedAt(LocalDateTime.now());
		Account save = accountRepository.save(savings);
		
		AccountCreationStatus status = new AccountCreationStatus();
		status.setAccountId(save);
		status.setMessage("Account Created Successfully");
		accountCreationStatusRespository.save(status);
		
		Account current = new Account();
		current.setCustomerId(customer.get());
		current.setAccountType("CURRENT");
		current.setBalance(1000.00);
		current.setCreatedAt(LocalDateTime.now());
		Account cur = accountRepository.save(current);
		
		AccountCreationStatus curStatus = new AccountCreationStatus();
		curStatus.setAccountId(cur);
		curStatus.setMessage("Account Created Successfully");
		return accountCreationStatusRespository.save(curStatus);
	}
	
	public ResponseEntity<List<AccountModel>> getCustomerAccounts(Integer customerId) {
		Optional<Customer> customer = customerRespository.findById(customerId);
		if(customer.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		List<Account> accounts = accountRepository.findByCustomerId(customer.get());
		List<AccountModel> accs = new ArrayList<>();
		for(Account account : accounts) {
			AccountModel model = new AccountModel();
			model.setId(account.getAccountId());
			model.setBalance(account.getBalance());
			accs.add(model);
		}
		return new ResponseEntity<>(accs, HttpStatus.OK);
	}
	
	public ResponseEntity<AccountModel> getAccount(Integer accountId){
		Optional<Account> account = accountRepository.findById(accountId);
		if(account.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		AccountModel model = new AccountModel();
		model.setId(account.get().getAccountId());
		model.setBalance(account.get().getBalance());
		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Statement>> getAccountStatement(Integer accountId, String fromDate, String toDate){
		return new ResponseEntity<>(statementRepository.findStatementsBetweenDate(fromDate, toDate, accountId), HttpStatus.OK);
	}
	
	public ResponseEntity<String> deposit(Integer accountId, Double amount){
		String reqUrl = DEPOSIT + accountId + "&amount=" + amount;
		HttpRequest depositReq = HttpRequest.newBuilder().uri(URI.create(reqUrl))
				.header("Content-Type", "application/json").POST(BodyPublishers.noBody()).build();
		return new ResponseEntity<>(httpUtil.getApiResponse(depositReq), HttpStatus.OK);
	}
	
	public ResponseEntity<String> withdraw(Integer accountId, Double amount){
		String reqUrl = WITHDRAW + accountId + "&amount=" + amount;
		HttpRequest depositReq = HttpRequest.newBuilder().uri(URI.create(reqUrl))
				.header("Content-Type", "application/json").POST(BodyPublishers.noBody()).build();
		return new ResponseEntity<>(httpUtil.getApiResponse(depositReq), HttpStatus.OK);
	}

}
