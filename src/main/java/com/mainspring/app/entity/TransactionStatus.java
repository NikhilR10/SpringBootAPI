package com.mainspring.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity
@Table(name = "transaction_status")
@Data
@JsonInclude(Include.NON_NULL)
public class TransactionStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "transaction_id")
	private Integer transactionId;
	
	@Column(name = "source_balance")
	private Double sourceBalance;
	
	@Column(name = "destination_balance")
	private Double destinationBalance;
	
	@Column(name = "message")
	private String message;

}
