package com.mainspring.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainspring.app.entity.AccountCreationStatus;
import com.mainspring.app.entity.Statement;
import com.mainspring.app.model.AccountModel;
import com.mainspring.app.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/createAccount")
	public ResponseEntity<AccountCreationStatus> createAccount(@RequestParam("customerId") Integer customerId) {
		return new ResponseEntity<>(accountService.createAccount(customerId), HttpStatus.OK);
	}

	@GetMapping("/getCustomerAccounts")
	public ResponseEntity<List<AccountModel>> getCustomerAccounts(@RequestParam("customerId") Integer customerId) {
		return accountService.getCustomerAccounts(customerId);
	}

	@GetMapping("/getAccount")
	public ResponseEntity<AccountModel> getAccount(@RequestParam("accountId") Integer accountId) {
		return accountService.getAccount(accountId);
	}

	@GetMapping("/getAccountStatement")
	public ResponseEntity<List<Statement>> getAccountStatement(@RequestParam("accountId") Integer accountId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		return accountService.getAccountStatement(accountId, fromDate, toDate);
	}

	@PostMapping("/account/deposit")
	public ResponseEntity<String> deposit(@RequestParam("accountId") Integer accountId,
			@RequestParam("amount") Double amount) {
		return accountService.deposit(accountId, amount);
	}

	@PostMapping("/account/withdraw")
	public ResponseEntity<String> withdraw(@RequestParam("accountId") Integer accountId,
			@RequestParam("amount") Double amount) {
		return accountService.withdraw(accountId, amount);
	}

}
