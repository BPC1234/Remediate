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
 * Date: 6/19/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */

/*@Entity
@Table(name="shipping_ledger_information")*/
public class ShippingLedger extends Ledger {

	@Column(name="document_date")
	private Date documentDate;
	
	@Column(name="render_date")
    private Date renderDate;
	
	@Column(name="sales_order_number")
    private Integer salesOrderNumber;
	
	@Column(name="ship_to_address")
    private String shipToAddress;
	
	@Column(name="shipping_entity")
    private String shippingEntity;
	
	@Column(name="shipping_number")
    private String shippingNumber;
	
	@Column(name="shipping_quantity")
    private double shippingQuantity;

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

    public Integer getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(Integer salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getShippingEntity() {
        return shippingEntity;
    }

    public void setShippingEntity(String shippingEntity) {
        this.shippingEntity = shippingEntity;
    }

    public String getShippingNumber() {
        return shippingNumber;
    }

    public void setShippingNumber(String shippingNumber) {
        this.shippingNumber = shippingNumber;
    }

    public double getShippingQuantity() {
        return shippingQuantity;
    }

    public void setShippingQuantity(double shippingQuantity) {
        this.shippingQuantity = shippingQuantity;
    }

	@Override
	public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
		if(ledger instanceof ShippingLedger){
			ShippingLedger slLedger = (ShippingLedger) ledger;
			slLedger.setDocumentDate(csvTransaction.getDocumentDate());
			slLedger.setRenderDate(csvTransaction.getRenderDate());
			slLedger.setSalesOrderNumber(csvTransaction.getSalesOrderNumber());
			slLedger.setShipToAddress(csvTransaction.getShipToAddress());
			slLedger.setShippingEntity(csvTransaction.getShippingEntity());
			slLedger.setShippingNumber(csvTransaction.getShippingNumber());
			slLedger.setShippingQuantity(csvTransaction.getShippingQuantity());
			
			slLedger.setTransaction(csvTransaction.getTransaction());
			slLedger.setTransactionAmount(csvTransaction.getTransactionAmount());			
			if("+".equals(isDebit))
				slLedger.setIsDebit(1);
			else
				slLedger.setIsDebit(-1);
			return slLedger;			
		}
		return null;
	}
}
