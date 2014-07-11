package com.dsinv.abac.ledger.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dsinv.abac.ledger.Ledger;
import com.dsinv.abac.vo.admin.CSVTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */

/*@Entity
@Table(name="completion_ledger_information")*/
public class CompletionLedger extends Ledger {    

	@Column(name="document_date")
	private Date documentDate;
	
	@Column(name="render_date")
    private Date renderDate;
	
	@Column(name="service_status")
    private String serviceStatus; 

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Date getRenderDate() {
        return renderDate;
    }

    public void setRenderDate(Date renderDate) {
        this.renderDate = renderDate;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    
	@Override
	public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
		if(ledger instanceof CompletionLedger){
			CompletionLedger clLedger = (CompletionLedger) ledger;
			clLedger.setDocumentDate(csvTransaction.getDocumentDate());
			clLedger.setRenderDate(csvTransaction.getRenderDate());
			clLedger.setServiceStatus(csvTransaction.getProductCompletionStatus());
			
			clLedger.setTransaction(csvTransaction.getTransaction());
			clLedger.setTransactionAmount(csvTransaction.getTransactionAmount());
			if("+".equals(isDebit))
				clLedger.setIsDebit(1);
			else
				clLedger.setIsDebit(-1);
			return clLedger;
		}
		return null;
	}
}
