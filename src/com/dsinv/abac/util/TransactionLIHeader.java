package com.dsinv.abac.util;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 1/20/14
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TransactionLIHeader {

    DocumentDate(1, "Document Date" ),
    DocumentNumber(2, "Document Number"),
    InitiatorSubmitterName(3, "Initiator Name"),
    ApproverTitle(4, "Approver Title"),
    DocID(5, "Initiat or SubmitterName"),
    DocDt(6, "Doc Dt"),
    CustomerType(7, "Customer Type"),
    TransNarrative(8, "Trans Narrative"),
    SKU(9, "SKU"),
    UnitQty(10, "Unit Qty"),
    UnitPrice(11, "UnitPrice"),
    GrossCost(12, "Gross Cost"),
    Discount(13, "Discount"),
    DiscountAmt(14, "Discount Amt"),
    Tax(15, "Tax"),
    Duty(16, "Duty"),
    ShippingTerms(17, "Shippig Terms"),
    ShippingID(18, "Shipping ID"),
    Submitter(19, "Submitter"),
    ManualOverride(20, "Manual Override"),
    ContractIdentification(21, "Contract Identification"),
    POamount(22, "PO amount"),
    POdate(23, "PO date"),
    UnitCost(24, "UnitCost"),
    ContactFName(25, "Contact FName"),
    ContactLName(26, "Contact LName"),
    Phone1(27, "Phone1"),
    Phone2(28, "Phone2"),
    BankRouting(29, "Bank Routing"),
    PaymentMethod(30, "Payment Method"),
    Currency(31, "Currency"),
    LName(32, "LName"),
    FName(33, "FName"),
    RegionState(34, "Region State"),
    LatLong(35, "LatLong"),
    TIN(36, "TIN"),
    Title(37, "Title"),
    BankDomicile(38, "Bank Domicile"),
    StartDt(39, "Start Dt"),
    Department(40, "Department" ),
    Supervisor(41, "Supervisor" ),
    EndDt(42, "End Dt" ),
    Salary(43, "Salary" ),
    Gender(44, "Gender" ),
    DOB(45, "DOB" ),
    Ethnicity(46, "Ethnicity" ),
    SpendLimit(47, "Spend Limit" ),
    ApprovalLimit(48, "Approval Limit"),
    BusinessUnit(49, "Business Unit"),
    DocNumber(50, "Doc Number"),
    InititatorName(51, "Inititator Name"),
    PayeeID(52, "Payee ID"),
    PayeeType(53, "Payee Type"),
    CustomsDuty(54, "Customs Duty"),
    Qty(55, "Qty"),
    Status(56, "Status"),
    PaymentTerms(57, "Payment Terms"),
    PreviousOrder(58, "Previous Order"),
    PreviousOrderDt(59, "Previous Order Dt"),
    SalesOrderNumber(60, "Sales Order Number"),
    ServiceProductStockCode(61, "Service Product Stock Code"),
    ServiceProductType(62, "Service Product Type"),
    ShipToId(63, "Ship To Id"),
    ShippedQty(64, "Shipped Qty"),
    TransCost(65, "Trans Cost"),
    ServiceDt(66, "Service Dt"),
    DocNbr(67, "Doc Nbr"),
    ApproverDt(68, "Approver Dt"),
    AccountsPayableBalance(69, "Accounts Payable Balance"),
    AccountsReceivableBalance(70, "Accounts Receivable Balance"),
    AdvanceBalance(71, "Advance Balance"),
    AdvanceType(72, "Advance Type"),
    ApproverName(73, "Approver Name" ),
    BankAccountNo(74, "Bank Account" ),
    CreditLimit(75, "Credit Limit" ),
    ExpenseAccount(76, "Expense Account" ),
    ExpenseType(77, "Expense Type"),
    InterCompanyTransaferAmount(78, "Inter Company Transafer Amount"),
    InventoryAmount(79, "Inventory Amount"),
    ORDER_QUANTITY(80, "Order Quantity"),
    PAYEE_NAME(81, "Payee Name"),
    PURCHASE_ORDER_AMOUNT(82, "Purchase Order Amount"),
    PURCHASE_ORDER_DATE(84, "Purchase Order Date"),
    RECIPIENT_BANK_ACCOUNT_DOMICILE(85, "Recipient Bank Account Domicile"),
    RECIPIENT_NAME(86, "Recipient Name"),
    PRODUCT_COMPLETION_STATUS(87, "Product Completion Status"),
    SHIP_TO_ADDRESS(88, "Ship to address"),
    SHIPPING_ENTITY(89, "Shipping Entity"),
    SHIPPING_QUANTITY(90, "Shipping Quantity"),
    TRANSACTION_DISCRIPTION(91, "Transaction Description"),
    TRANSACTION_NUMBER(92, "Transaction Number"),
    ORDER_STATUS(93, "Order Status"),


    CheckDate(94, "Check Date"),
    CheckNo(95, "Check no"),

    RenderDt(96, "Render Dt");


    private int headerId;
    private String headerCode;
    private String header;

    TransactionLIHeader(int headerId, String header)  {
        this.headerId = headerId;
        this.header = header;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public String getHeaderCode() {
        return headerCode;
    }

    public void setHeaderCode(String headerCode) {
        this.headerCode = headerCode;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
