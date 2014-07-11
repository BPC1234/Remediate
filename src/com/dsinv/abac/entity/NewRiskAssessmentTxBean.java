package com.dsinv.abac.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dsinv.abac.vo.admin.AuditTrailVO;
import com.dsinv.abac.vo.admin.ControlVO;
import com.dsinv.abac.vo.admin.TransactionDetailVO;

/**
 * Created with IntelliJ IDEA.
 * User: habib
 * Date: 7/16/13
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewRiskAssessmentTxBean {

    private ProactiveTransactionCND proactiveTransactionCND;
    private ProactiveTransaction proactiveTransaction;
    private ReactiveTransactionCND reactiveTransactionCND;
    private RealTimeTransactionCND realTimeTransactionCND;
    private ReactiveTransaction reactiveTransaction;
    private RealTimeTransaction realTimeTransaction;
    
    // properties for pdf and email.
    private List<TransactionDetailVO> transactionDetails;
    private List<ControlVO> controls;
    private List<AuditTrailVO> auditTrails;
    private List  assignedEmployeeList;

    private List list;
    private List controlListForTable;
    private Control control;
    private String abc;
    private String filename;
    private List userList;
    private List proactiveTransactionAuditTrialList;

    private String previousControlsIds;
    private List proactiveTxList;
    private MultipartFile fileData;
    private MultipartFile fileDataForSupportingDocument;
    private MultipartFile transactionPolicy;
    private long proactiveProjectId;
    private long reactiveProjectId;
    private long realTimeProjectId;
    private long controlId;

    private String emptyString;
    private String controlComment;
    private String controlPersonInvolve;
    private String decision;
    private String decisionEmailAddress;
    private String decisionComment;
    private String policyComment;

    private String  transactionComment;      // comment for comment table
    private String previousDecission;
    private String supportingDocumentComment;
    private String previousControlComment;
    private String previousDecissionComment;
    private List supportingDocumentList;

    private List employeeInfoList;
    private List cusromerList;
    private List vendorMasterFileList;
    private List additionalInfoList;
    private List policyList;
    private List trxSummaryList;
    private List violationList;
    private List employeeList;
    private List transactionCommentList;



    public String getEmptyString() {
        return emptyString;
    }

    public void setEmptyString(String emptyString) {
        this.emptyString = emptyString;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }


    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public List getControlListForTable() {
        return controlListForTable;
    }

    public void setControlListForTable(List controlListForTable) {
        this.controlListForTable = controlListForTable;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public List getUserList() {
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public List getProactiveTransactionAuditTrialList() {
        return proactiveTransactionAuditTrialList;
    }

    public void setProactiveTransactionAuditTrialList(List proactiveTransactionAuditTrialList) {
        this.proactiveTransactionAuditTrialList = proactiveTransactionAuditTrialList;
    }

    public String getPreviousControlsIds() {
        return previousControlsIds;
    }

    public void setPreviousControlsIds(String previousControlsIds) {
        this.previousControlsIds = previousControlsIds;
    }

    public List  getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public List getProactiveTxList() {
        return proactiveTxList;
    }

    public void setProactiveTxList(List proactiveTxList) {
        this.proactiveTxList = proactiveTxList;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }
    public long getProactiveProjectId() {
        return proactiveProjectId;
    }

    public void setProactiveProjectId(long proactiveProjectId) {
        this.proactiveProjectId = proactiveProjectId;
    }

    public String getControlComment() {
        return controlComment;
    }

    public void setControlComment(String controlComment) {
        this.controlComment = controlComment;
    }

    public String getControlPersonInvolve() {
        return controlPersonInvolve;
    }

    public void setControlPersonInvolve(String controlPersonInvolve) {
        this.controlPersonInvolve = controlPersonInvolve;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getDecisionEmailAddress() {
        return decisionEmailAddress;
    }

    public void setDecisionEmailAddress(String decisionEmailAddress) {
        this.decisionEmailAddress = decisionEmailAddress;
    }

    public String getDecisionComment() {
        return decisionComment;
    }

    public void setDecisionComment(String decisionComment) {
        this.decisionComment = decisionComment;
    }

    public String getTransactionComment() {
        return transactionComment;
    }

    public void setTransactionComment(String transactionComment) {
        this.transactionComment = transactionComment;
    }

    public ProactiveTransaction getProactiveTransaction() {
        return proactiveTransaction;
    }

    public void setProactiveTransaction(ProactiveTransaction proactiveTransaction) {
        this.proactiveTransaction = proactiveTransaction;
    }

    public String getPreviousDecission() {
        return previousDecission;
    }

    public void setPreviousDecission(String previousDecission) {
        this.previousDecission = previousDecission;
    }

    public ProactiveTransactionCND getProactiveTransactionCND() {
        return proactiveTransactionCND;
    }

    public void setProactiveTransactionCND(ProactiveTransactionCND proactiveTransactionCND) {
        this.proactiveTransactionCND = proactiveTransactionCND;
    }

    public ReactiveTransactionCND getReactiveTransactionCND() {
        return reactiveTransactionCND;
    }

    public void setReactiveTransactionCND(ReactiveTransactionCND reactiveTransactionCND) {
        this.reactiveTransactionCND = reactiveTransactionCND;
    }

    public ReactiveTransaction getReactiveTransaction() {
        return reactiveTransaction;
    }

    public void setReactiveTransaction(ReactiveTransaction reactiveTransaction) {
        this.reactiveTransaction = reactiveTransaction;
    }

    public long getReactiveProjectId() {
        return reactiveProjectId;
    }

    public void setReactiveProjectId(long reactiveProjectId) {
        this.reactiveProjectId = reactiveProjectId;
    }

    public String getSupportingDocumentComment() {
        return supportingDocumentComment;
    }

    public void setSupportingDocumentComment(String supportingDocumentComment) {
        this.supportingDocumentComment = supportingDocumentComment;
    }

    public String getPreviousControlComment() {
        return previousControlComment;
    }

    public void setPreviousControlComment(String previousControlComment) {
        this.previousControlComment = previousControlComment;
    }

    public MultipartFile getFileDataForSupportingDocument() {
        return fileDataForSupportingDocument;
    }

    public void setFileDataForSupportingDocument(MultipartFile fileDataForSupportingDocument) {
        this.fileDataForSupportingDocument = fileDataForSupportingDocument;
    }

    public List getSupportingDocumentList() {
        return supportingDocumentList;
    }

    public void setSupportingDocumentList(List supportingDocumentList) {
        this.supportingDocumentList = supportingDocumentList;
    }

    public List getEmployeeInfoList() {
        return employeeInfoList;
    }

    public void setEmployeeInfoList(List employeeInfoList) {
        this.employeeInfoList = employeeInfoList;
    }

    public List getCusromerList() {
        return cusromerList;
    }

    public void setCusromerList(List cusromerList) {
        this.cusromerList = cusromerList;
    }

    public List getVendorMasterFileList() {
        return vendorMasterFileList;
    }

    public void setVendorMasterFileList(List vendorMasterFileList) {
        this.vendorMasterFileList = vendorMasterFileList;
    }

    public List getAdditionalInfoList() {
        return additionalInfoList;
    }

    public void setAdditionalInfoList(List additionalInfoList) {
        this.additionalInfoList = additionalInfoList;
    }

    public long getControlId() {
        return controlId;
    }

    public void setControlId(long controlId) {
        this.controlId = controlId;
    }

    public long getRealTimeProjectId() {
        return realTimeProjectId;
    }

    public void setRealTimeProjectId(long realTimeProjectId) {
        this.realTimeProjectId = realTimeProjectId;
    }

    public RealTimeTransactionCND getRealTimeTransactionCND() {
        return realTimeTransactionCND;
    }

    public void setRealTimeTransactionCND(RealTimeTransactionCND realTimeTransactionCND) {
        this.realTimeTransactionCND = realTimeTransactionCND;
    }

    public RealTimeTransaction getRealTimeTransaction() {
        return realTimeTransaction;
    }

    public void setRealTimeTransaction(RealTimeTransaction realTimeTransaction) {
        this.realTimeTransaction = realTimeTransaction;
    }

	public List<TransactionDetailVO> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetailVO> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public List<ControlVO> getControls() {
		return controls;
	}

	public void setControls(List<ControlVO> controls) {
		this.controls = controls;
	}

	public List<AuditTrailVO> getAuditTrails() {
		return auditTrails;
	}

	public void setAuditTrails(List<AuditTrailVO> auditTrails) {
		this.auditTrails = auditTrails;
	}

	public String getFilename() {
		return filename;
	}

    public List getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List policyList) {
        this.policyList = policyList;
    }

    public String getPreviousDecissionComment() {
        return previousDecissionComment;
    }

    public void setPreviousDecissionComment(String previousDecissionComment) {
        this.previousDecissionComment = previousDecissionComment;
    }

    public List getTrxSummaryList() {
        return trxSummaryList;
    }

    public void setTrxSummaryList(List trxSummaryList) {
        this.trxSummaryList = trxSummaryList;
    }

    public List getViolationList() {
        return violationList;
    }

    public void setViolationList(List violationList) {
        this.violationList = violationList;
    }

    public MultipartFile getTransactionPolicy() {
        return transactionPolicy;
    }

    public void setTransactionPolicy(MultipartFile transactionPolicy) {
        this.transactionPolicy = transactionPolicy;
    }

    public String getPolicyComment() {
        return policyComment;
    }

    public void setPolicyComment(String policyComment) {
        this.policyComment = policyComment;
    }

    public List getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List employeeList) {
        this.employeeList = employeeList;
    }

    public List getAssignedEmployeeList() {
        return assignedEmployeeList;
    }

    public void setAssignedEmployeeList(List assignedEmployeeList) {
        this.assignedEmployeeList = assignedEmployeeList;
    }

    public List getTransactionCommentList() {
        return transactionCommentList;
    }

    public void setTransactionCommentList(List transactionCommentList) {
        this.transactionCommentList = transactionCommentList;
    }

    @Override
    public String toString() {
        return "NewRiskAssessmentTxBean{" +
                "proactiveTransactionCND=" + proactiveTransactionCND +
                ", proactiveTransaction=" + proactiveTransaction +
                ", reactiveTransactionCND=" + reactiveTransactionCND +
                ", realTimeTransactionCND=" + realTimeTransactionCND +
                ", reactiveTransaction=" + reactiveTransaction +
                ", realTimeTransaction=" + realTimeTransaction +
                ", transactionDetails=" + transactionDetails +
                ", controls=" + controls +
                ", auditTrails=" + auditTrails +
                ", list=" + list +
                ", controlListForTable=" + controlListForTable +
                ", control=" + control +
                ", abc='" + abc + '\'' +
                ", filename='" + filename + '\'' +
                ", userList=" + userList +
                ", proactiveTransactionAuditTrialList=" + proactiveTransactionAuditTrialList +
                ", previousControlsIds='" + previousControlsIds + '\'' +
                ", proactiveTxList=" + proactiveTxList +
                ", fileData=" + fileData +
                ", fileDataForSupportingDocument=" + fileDataForSupportingDocument +
                ", proactiveProjectId=" + proactiveProjectId +
                ", reactiveProjectId=" + reactiveProjectId +
                ", realTimeProjectId=" + realTimeProjectId +
                ", controlId=" + controlId +
                ", emptyString='" + emptyString + '\'' +
                ", controlComment='" + controlComment + '\'' +
                ", controlPersonInvolve='" + controlPersonInvolve + '\'' +
                ", decision='" + decision + '\'' +
                ", decisionEmailAddress='" + decisionEmailAddress + '\'' +
                ", decisionComment='" + decisionComment + '\'' +
                ", transactionComment='" + transactionComment + '\'' +
                ", previousDecission='" + previousDecission + '\'' +
                ", supportingDocumentComment='" + supportingDocumentComment + '\'' +
                ", previousControlComment='" + previousControlComment + '\'' +
                ", previousDecissionComment='" + previousDecissionComment + '\'' +
                ", supportingDocumentList=" + supportingDocumentList +
                ", employeeInfoList=" + employeeInfoList +
                ", cusromerList=" + cusromerList +
                ", vendorMasterFileList=" + vendorMasterFileList +
                ", additionalInfoList=" + additionalInfoList +
                ", policyList=" + policyList +
                ", trxSummaryList=" + trxSummaryList +
                ", violationList=" + violationList +
                '}';
    }
}
