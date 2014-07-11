package com.dsinv.abac.service;

import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.Vendor;
import com.dsinv.abac.ledger.impl.AccountsPayableLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.vo.admin.CSVTransaction;
import com.dsinv.abac.vo.admin.UserVo;

import javax.servlet.http.HttpServletRequest;

import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface AdminService {

    List<User> getAllUserList(UserVo userVo);
    List<User> getAllUserList();
    List<User> getAllUserList(String userName);
    List<ReactiveProject> getReactiveProjectList();
    List<Region> getRegionList();
    AbstractBaseEntity loadEntityById(Long id, String className);
    void saveOrUpdate(AbstractBaseEntity entity);  /////////
    void deleteEntityById(Long id, String className);
    boolean isUserNameExist(String userName);
    List<ProactiveBlockWeightRatio> getAllProactiveBlockWeightRatioList();
    AbstractBaseEntity getAbstractBaseEntityByString(String className,String anyColumn,String columnValue);
    ProactiveProject getProactiveProject(Region region);
    void addNode(String nodeName,int level, HttpServletRequest request);
    List<AbstractBaseEntity> getAnyEntityList(String className);
    List<ProactiveBlockWeight> getProactiveBlockWeight(long regionId,int toDate, int fromDate);
    List<Control> getControlListByControlId(String controlIds);
    List<Control> getControlListByTransactionType(String transactionType);
    ProactiveTransaction getProactiveTransactionById(long id);
    void save(Object obj);
    void update(Object obj);
    void saveOrUpdateForAnyObject(Object obj);
    List<ProactiveTransactionAuditTrial> getProactiveTransactionAuditTrialByProactiveTxId(long proactiveTransactionId);
    ProactiveTransactionCND getProactiveTransactionCNDByProactiveTransactionid(long id);
    Region getRegionByName(String countryName);
    Transaction getTransactionById(Long trxId);
    List<ProactiveTransaction> getProactiveTransactionListByProactiveProjectId(Long proactiveProjectId);
    void delete(Object obj);
    ReactiveTransaction getReactiveTransactionById(long id);
    ReactiveTransactionCND getReactiveTransactionCNDByReactiveTransactionid(long id);
    List<ReactiveTransactionAuditTrial> getReactiveTransactionAuditTrialByReactiveTxId(long reactiveTransactionId);
    List<ProactiveTransactionSupportingDocument> getProactiveTransactionSupportingDocumentListByTrxId(long proactiveTrxId);
    List<ReactiveTransactionSupportingDocument> getReactiveTransactionSupportingDocumentListByTrxId(long reactiveTrxId);
    ProactiveTransactionSupportingDocument getProactiveTransactionSupportingDocumentById(long proctiveTrxSuppDocId);
    ReactiveTransactionSupportingDocument getReactiveTransactionSupportingDocumentById(long reactiveTrxSuppDocId);
    List<Control> getAllControl();
    List<Object> CSV2Bean(String filePath, String[] headers, CellProcessor[] processors, Object aObj) throws FileNotFoundException, IOException;
	long saveCSVTransaction(Object object, int jobNo);
    AccountsPayableLedger getAccountsPayableLedger(String ledgerType);
    List getTransactionList(Date fromDate, Date toDate);
    RealTimeTransaction getRealTimeTransactionById(long id);
    RealTimeTransactionCND getRealTimeTransactionCNDByRtTransactionid(long id);
    List<RealTimeTransactionAuditTrial> getRealTimeTransactionAuditTrialByRtTxId(long realTimeTransactionId);
    List<RealTimeTransactionComment> getRealTimeTranscationCommentList(long realTimeTransactionId);
    List<ReactiveTransactionComment> getReactiveTransactionCommentList(long reactiveTransactionId);
    List<RealTimeTransactionSupportingDocument> getRealTimeTransactionSupportingDocumentListByTrxId(long realTimeTrxId);
    RealTimeTransactionSupportingDocument getRealTimeTransactionSupportingDocumentById(long rtTrxSuppDocId);
    List<Vendor> getVendorList(String vendorNname, String zip, String countryCode);
    List<TransactionApproval> getAllTransactionApprovalList();
    List<Policy> getAllPolicy();
    public List<TransactionPolicy> getTransactionPolicy(long transactionId);
    List<Training> getAllTraining();
    void saveRealTimeTrxAuditTrial(RealTimeTransaction realTimeTransaction,List<String> messageList);
    int getEntitySize(String entity);
    void saveProactiveTrxAuditTrial(ProactiveTransaction proactiveTransaction,List<String> messageList);
    void saveReactiveTrxAuditTrial(ReactiveTransaction reactiveTransaction,List<String> messageList);
    TransactionApproval getTransactionApprovalById(long id);
    List getRules();
    Rule getRuleById(long id);
    List<Lookup> getLookupList();
    int getAssignmentSize();
    List<SuspiciousTransaction> getViolatedRuleListByTrxId(long trxId);
    Policy getPolicyById(long policyId);
    Training getTrainingById(long trainingId);
    String getACustomerSQL(CSVTransaction csvTransaction);
    String getAVendorSQL(CSVTransaction csvTransaction);
    String getAEmployeeSQL(CSVTransaction csvTransaction);
    String getATransactionLiSQL(CSVTransaction csvTransaction, long txId);
    String getTansactionSQL(CSVTransaction csvTransaction, long customerId, long vendorId, long employeeId, int jobId);
    TransactionPolicy loadTransactionPolicy(long id);
    List<TrainingQuestion> getTrainingQuestion(long trainingId);
    List<TrainingQuestion> getTrainingQuestionSet(Training trainingId);
    void deleteTraining(long trainingId);
    User getUserByUserName(String userName);
    PolicyAndProcedure getPolicyAndProcedure(User user,long policyId);
    void saveTrainingQuestion(TrainingQuestion trainingQuestion, List<TrainingQuestionAnswer> trainingQuestionAnswerList) ;
    RealTimeTransaction getRealTimeTransactionByTrxIdAndRtProjectId(long trxId,long rtProjectId);
    void deleteTrainingQuestion(long trainingId, long questionId);
    EmployeeTraining getEmployeeTraining(long employFK, long trainingFK);
    EmployeeMasterLedger loadEmployee(long employeeId);
    List<EmployeeMasterLedger> getAllEmployeeMasterLedger();
}