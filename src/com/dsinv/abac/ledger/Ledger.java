package com.dsinv.abac.ledger;

import javax.persistence.*;

import com.dsinv.abac.entity.Transaction;
import com.dsinv.abac.vo.admin.CSVTransaction;


@MappedSuperclass
public abstract class Ledger {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private Long id;
	
	@Column(name="transaction_amount")
	private Double transactionAmount;
	
	@Column(name="is_debit")
	private Integer isDebit;
	
	@ManyToOne
    @JoinColumn(name="transaction_fk")
	private Transaction transaction;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Integer getIsDebit() {
		return isDebit;
	}
	public void setIsDebit(Integer isDebit) {
		this.isDebit = isDebit;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public abstract Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit);
}
