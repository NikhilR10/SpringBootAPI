package com.mainspring.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainspring.app.entity.Transaction;
import com.mainspring.app.entity.TransactionStatus;
import com.mainspring.app.service.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping("/deposit")
	public ResponseEntity<TransactionStatus> deposit(@RequestParam("accountId") Integer accountId, @RequestParam("amount") Double amount){
		return transactionService.deposit(accountId, amount);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<TransactionStatus> withdraw(@RequestParam("accountId") Integer accountId, @RequestParam("amount") Double amount){
		return transactionService.withdraw(accountId, amount);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<TransactionStatus> transfer(@RequestParam("sourceAccountId") Integer sourceAccountId, @RequestParam("targetAccountId") Integer targetAccountId, @RequestParam("amount") Double amount){
		return transactionService.transfer(sourceAccountId, targetAccountId, amount);
	}
	
	@GetMapping("/getTransactions")
	public ResponseEntity<List<Transaction>> getTransactions(@RequestParam("customerId") Integer customerId){
		return transactionService.getTransactions(customerId);
	}

}
