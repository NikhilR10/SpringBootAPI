package com.mainspring.app.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity
@Table(name = "transaction")
@Data
@JsonInclude(Include.NON_NULL)
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false)
	private Account accountId;
	
	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;
	
	@Column(name = "transfer_account_id")
	private Integer transferAccountId;
	
	@Column(name = "transfer_amount")
	private Double transferAmount;
	
	@Column(name = "withdrawal")
	private Double withdrawal;

	@Column(name = "deposit")
	private Double deposit;
	
	@Column(name = "closing_balance")
	private Double closingBalance;

}
