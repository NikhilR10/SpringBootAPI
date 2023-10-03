package com.mainspring.app.service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mainspring.app.entity.Account;
import com.mainspring.app.entity.Statement;
import com.mainspring.app.entity.Transaction;
import com.mainspring.app.entity.TransactionStatus;
import com.mainspring.app.repository.AccountRepository;
import com.mainspring.app.repository.StatementRepository;
import com.mainspring.app.repository.TransactionRepository;
import com.mainspring.app.repository.TransactionStatusRepository;
import com.mainspring.app.utils.HttpUtil;

@Service
public class TransactionService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionStatusRepository transactionStatusRepository;

	@Autowired
	private StatementRepository statementRepository;

	@Autowired
	private HttpUtil httpUtil;

	private static final String EVALUATE_MIN_BAL = "http://localhost:9090/evaluateMinBal?balance=";

	public ResponseEntity<TransactionStatus> deposit(Integer accountId, Double amount) {
		Optional<Account> acc = accountRepository.findById(accountId);
		if (acc.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		Account account = acc.get();
		double accBal = account.getBalance();
		account.setBalance(amount + accBal);
		Account save = accountRepository.save(account);

		Statement statement = new Statement();
		statement.setAccountId(save);
		statement.setDate(LocalDateTime.now());
		statement.setDeposit(amount);
		statement.setClosingBalance(save.getBalance());
		statementRepository.save(statement);

		Transaction transaction = new Transaction();
		transaction.setAccountId(save);
		transaction.setClosingBalance(save.getBalance());
		transaction.setDeposit(amount);
		transaction.setTransactionDate(LocalDateTime.now());
		Transaction trans = transactionRepository.save(transaction);

		TransactionStatus status = new TransactionStatus();
		status.setTransactionId(trans.getId());
		status.setMessage("Transaction Successful");
		return new ResponseEntity<>(transactionStatusRepository.save(status), HttpStatus.OK);
	}

	public ResponseEntity<TransactionStatus> withdraw(Integer accountId, Double amount) {
		Optional<Account> acc = accountRepository.findById(accountId);
		if (acc.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		Account account = acc.get();
		double newBal = account.getBalance() - amount;

		HttpRequest minBalReq = HttpRequest.newBuilder().uri(URI.create(EVALUATE_MIN_BAL + newBal))
				.header("Content-Type", "application/json").GET().build();
		String apiResponse = httpUtil.getApiResponse(minBalReq);
		if ("DENIED".equalsIgnoreCase(apiResponse)) {
			Transaction transaction = new Transaction();
			transaction.setAccountId(account);
			transaction.setClosingBalance(account.getBalance());
			transaction.setTransactionDate(LocalDateTime.now());
			Transaction trans = transactionRepository.save(transaction);

			TransactionStatus status = new TransactionStatus();
			status.setTransactionId(trans.getId());
			status.setMessage("Declined! Less than minimum balance required");
			return new ResponseEntity<>(transactionStatusRepository.save(status), HttpStatus.OK);
		}

		account.setBalance(newBal);
		Account save = accountRepository.save(account);

		Statement statement = new Statement();
		statement.setAccountId(save);
		statement.setDate(LocalDateTime.now());
		statement.setDeposit(amount);
		statement.setClosingBalance(save.getBalance());
		statementRepository.save(statement);

		Transaction transaction = new Transaction();
		transaction.setAccountId(save);
		transaction.setClosingBalance(save.getBalance());
		transaction.setWithdrawal(amount);
		transaction.setTransactionDate(LocalDateTime.now());
		Transaction trans = transactionRepository.save(transaction);

		TransactionStatus status = new TransactionStatus();
		status.setTransactionId(trans.getId());
		status.setMessage("Transaction Successful");
		return new ResponseEntity<>(transactionStatusRepository.save(status), HttpStatus.OK);
	}

	public ResponseEntity<TransactionStatus> transfer(Integer sourceAccId, Integer targetAccId, Double amount) {
		Optional<Account> sourceAcc = accountRepository.findById(sourceAccId);
		if (sourceAcc.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		Optional<Account> targetAcc = accountRepository.findById(targetAccId);
		if (targetAcc.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		Account source = sourceAcc.get();
		double newBal = source.getBalance() - amount;
		HttpRequest minBalReq = HttpRequest.newBuilder().uri(URI.create(EVALUATE_MIN_BAL + newBal))
				.header("Content-Type", "application/json").GET().build();
		String apiResponse = httpUtil.getApiResponse(minBalReq);
		if ("DENIED".equalsIgnoreCase(apiResponse)) {
			Transaction transaction = new Transaction();
			transaction.setAccountId(source);
			transaction.setClosingBalance(source.getBalance());
			transaction.setTransactionDate(LocalDateTime.now());
			Transaction trans = transactionRepository.save(transaction);

			TransactionStatus status = new TransactionStatus();
			status.setTransactionId(trans.getId());
			status.setMessage("Declined! Less than minimum balance required");
			return new ResponseEntity<>(transactionStatusRepository.save(status), HttpStatus.OK);
		}

		source.setBalance(newBal);
		Account sourceSave = accountRepository.save(source);

		Statement sourceStatement = new Statement();
		sourceStatement.setAccountId(sourceSave);
		sourceStatement.setDate(LocalDateTime.now());
		sourceStatement.setWithdrawal(amount);
		sourceStatement.setClosingBalance(sourceSave.getBalance());
		statementRepository.save(sourceStatement);

		Account target = targetAcc.get();
		double targetNewBal = target.getBalance() + amount;
		target.setBalance(targetNewBal);
		Account targetSave = accountRepository.save(target);

		Statement targetStatment = new Statement();
		targetStatment.setAccountId(targetSave);
		targetStatment.setDate(LocalDateTime.now());
		targetStatment.setDeposit(amount);
		targetStatment.setClosingBalance(targetSave.getBalance());
		statementRepository.save(targetStatment);

		Transaction sourceTransaction = new Transaction();
		sourceTransaction.setAccountId(sourceSave);
		sourceTransaction.setClosingBalance(sourceSave.getBalance());
		sourceTransaction.setTransactionDate(LocalDateTime.now());
		sourceTransaction.setTransferAccountId(target.getAccountId());
		sourceTransaction.setTransferAmount(amount);
		Transaction sourceTrans = transactionRepository.save(sourceTransaction);

		Transaction targetTransaction = new Transaction();
		targetTransaction.setAccountId(targetSave);
		targetTransaction.setClosingBalance(targetSave.getBalance());
		targetTransaction.setDeposit(amount);
		targetTransaction.setTransactionDate(LocalDateTime.now());
		transactionRepository.save(targetTransaction);

		TransactionStatus status = new TransactionStatus();
		status.setTransactionId(sourceTrans.getId());
		status.setSourceBalance(sourceSave.getBalance());
		status.setDestinationBalance(targetSave.getBalance());
		status.setMessage("Transaction Successful");
		return new ResponseEntity<>(transactionStatusRepository.save(status), HttpStatus.OK);
	}

	public ResponseEntity<List<Transaction>> getTransactions(Integer customerId) {
		return new ResponseEntity<>(transactionRepository.findTransactionsByCustomerId(customerId), HttpStatus.OK);
	}

}
