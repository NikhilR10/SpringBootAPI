package com.mainspring.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainspring.app.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM transaction WHERE account_id IN (SELECT account_id FROM account WHERE customer_id = :customerId);")
	List<Transaction> findTransactionsByCustomerId(@Param("customerId") Integer customerId);

}
