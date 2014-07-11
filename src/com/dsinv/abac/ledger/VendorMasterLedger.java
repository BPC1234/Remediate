package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Training;
import com.dsinv.abac.entity.Policy;
import com.dsinv.abac.entity.Transaction;
import com.dsinv.abac.vo.admin.CSVTransaction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
 

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/19/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="vendor_master_ledger")
public class VendorMasterLedger extends Ledger{

    @Column(name="entity_address")
    private String entityAddress;

    @Column(name="entity_address1")
    private String entityAddress1;

    @Column(name="vendor_id")
    private String vendorId;

    @Column(name="entity_name")
    private String entityName;

    @Column(name="entity_status")
    private String entityStatus;

    @Column(name="entity_type")
    private String entityType;

    @Column(name="recipient_bank_account_domicile")
    private String recipientBankAccountDomicile;

    @Column(name="tin")
    private String tin;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "bank_account_location")
    private String bankAccountLocation;

    @Column(name="email")
    private String email;

    private String paymentMethod;

    private String currency;
    private String bankRouting;
    private String contactFirstName;
    private String contactLastName;

    public String getEntityAddress() {
        return entityAddress;
    }

    public void setEntityAddress(String entityAddress) {
        this.entityAddress = entityAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountLocation() {
        return bankAccountLocation;
    }

    public void setBankAccountLocation(String bankAccountLocation) {
        this.bankAccountLocation = bankAccountLocation;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRecipientBankAccountDomicile() {
        return recipientBankAccountDomicile;
    }

    public void setRecipientBankAccountDomicile(String recipientBankAccountDomicile) {
        this.recipientBankAccountDomicile = recipientBankAccountDomicile;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEntityAddress1() {
        return entityAddress1;
    }

    public void setEntityAddress1(String entityAddress1) {
        this.entityAddress1 = entityAddress1;
    }

    public String getBankRouting() {
        return bankRouting;
    }

    public void setBankRouting(String bankRouting) {
        this.bankRouting = bankRouting;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        if(ledger instanceof VendorMasterLedger){
            VendorMasterLedger vmLedger = (VendorMasterLedger)ledger;
            vmLedger.setEntityAddress(csvTransaction.getEntityAddress());
            vmLedger.setVendorId("");
            vmLedger.setEntityName(csvTransaction.getEntityName());
            vmLedger.setEntityStatus(csvTransaction.getEntityStatus());
            vmLedger.setEntityType(csvTransaction.getEntityType());
            vmLedger.setRecipientBankAccountDomicile(csvTransaction.getRecipientBankAccountDomicile());
            vmLedger.setTin(csvTransaction.getTin());
            vmLedger.setTransaction(csvTransaction.getTransaction());
            vmLedger.setTransactionAmount(csvTransaction.getTransactionAmount());
            if("+".equals(isDebit))
                vmLedger.setIsDebit(1);
            else
                vmLedger.setIsDebit(-1);
            return vmLedger;
        }
        return null;
    }

    /**
     *   in append statement statement must maintain serial according to insert sql field selection
     *   please follow VENDOR_MASTER_LEDGER_QUERY in constant class.
     * @param ledger
     * @param csvTransaction
     * @param isDebit
     * @return
     */
    public String insertsql(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        StringBuffer insert = new StringBuffer();
        insert.append("('");
        insert.append(csvTransaction.getVendorAddress());
        insert.append("','");
        insert.append(csvTransaction.getVendorAddress1());
        insert.append("',");
        insert.append(csvTransaction.getEntityId());
        insert.append(",'");
        insert.append(csvTransaction.getVendorName());
        insert.append("','");
        insert.append(csvTransaction.getEntityStatus());
        insert.append("','");
        insert.append(csvTransaction.getEntityType());
        insert.append("','");
        insert.append(csvTransaction.getRecipientBankAccountDomicile());
        insert.append("','");
        insert.append(csvTransaction.getTin());
        insert.append("','");
        insert.append(csvTransaction.getVendorCity());
        insert.append("','");
        insert.append(csvTransaction.getVendorState());
        insert.append("','");
        insert.append(csvTransaction.getVendorZipCode());
        insert.append("','");
        insert.append(csvTransaction.getVendorBankAccountNo());
        insert.append("','");
        insert.append(csvTransaction.getVendorBankAccountLocation());
        insert.append("','");
        insert.append(csvTransaction.getPaymentMethod());
        insert.append("','");
        insert.append(csvTransaction.getPaymentCurrency());
 /*       insert.append("',");
        insert.append(csvTransaction.getTransaction().getId());*/
        insert.append("',");
        if ("+".equals(isDebit))
            insert.append(1);
        else
            insert.append(-1);
        insert.append(")");


        return insert.toString();
    }
}
