package com.dsinv.abac.ledger.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dsinv.abac.entity.PersonEntity;
import com.dsinv.abac.ledger.Ledger;
import com.dsinv.abac.vo.admin.CSVTransaction;

/*@Entity
@Table(name="advanced_ledger_information")*/
public class AdvancedLedger extends Ledger{

	@Column(name="advance_balance")
	private double advanceBalance;
	
	@Column(name="advance_type")
    private String advanceType;
	
	@ManyToOne
    @JoinColumn(name="customer_fk")
    private PersonEntity customer;
	
	@ManyToOne
    @JoinColumn(name="vendor_fk")
    private PersonEntity vendor;

	@ManyToOne
    @JoinColumn(name="employee_fk")
    private PersonEntity employee;

    public double getAdvanceBalance() {
        return advanceBalance;
    }

    public void setAdvanceBalance(double advanceBalance) {
        this.advanceBalance = advanceBalance;
    }

    public String getAdvanceType() {
        return advanceType;
    }

    public void setAdvanceType(String advanceType) {
        this.advanceType = advanceType;
    }

	public PersonEntity getCustomer() {
		return customer;
	}

	public void setCustomer(PersonEntity customer) {
		this.customer = customer;
	}

	public PersonEntity getVendor() {
		return vendor;
	}

	public void setVendor(PersonEntity vendor) {
		this.vendor = vendor;
	}

	public PersonEntity getEmployee() {
		return employee;
	}

	public void setEmployee(PersonEntity employee) {
		this.employee = employee;
	}

	@Override
	public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {		
		
		if(ledger instanceof AdvancedLedger){
			AdvancedLedger alLedger = (AdvancedLedger) ledger;
			alLedger.setAdvanceBalance(csvTransaction.getAdvanceBalance());
			alLedger.setAdvanceType(csvTransaction.getAdvanceType());
			/*alLedger.setCustomer(csvTransaction.getTransaction().getCustomer());
			alLedger.setEmployee(csvTransaction.getTransaction().getEmployee());
			alLedger.setVendor(csvTransaction.getTransaction().getVendor());
			*/
			alLedger.setTransaction(csvTransaction.getTransaction());
			alLedger.setTransactionAmount(csvTransaction.getTransactionAmount());
			if("+".equals(isDebit))
				alLedger.setIsDebit(1);
			else
				alLedger.setIsDebit(-1);
			return alLedger;
		}
		return null;
	}
}
