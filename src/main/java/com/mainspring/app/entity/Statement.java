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
@Table(name = "statement")
@Data
@JsonInclude(Include.NON_NULL)
public class Statement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false)
	private Account accountId;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "narration")
	private String narration;
	
	@Column(name ="chq_ref_no")
	private String chqRefNo;
	
	@Column(name = "value_date")
	private LocalDateTime valueDate;
	
	@Column(name = "withdrawal")
	private Double withdrawal;

	@Column(name = "deposit")
	private Double deposit;
	
	@Column(name = "closing_balance")
	private Double closingBalance;

}
