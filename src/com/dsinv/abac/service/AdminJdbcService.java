package com.dsinv.abac.service;

import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.impl.AccountsReceivableLedger;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AdminJdbcService {

    List getProactiveTransaction(ProactiveProject proactiveProject);
    List getReactiveTransactionList(ReactiveProject reactiveProject);
    List getProactiveTransactionByTrxId(ProactiveProject proactiveProject, long proactiveTransactionId);
    List getReactiveTransactionByTrxId(ReactiveProject reactiveProject, long reactiveTransactionId);
    void deleteProactiveTransactionByProactiveProjectId(Long proactiveProjectId);
    void deleteProactiveTransactionCNDByProactiveTransactionId(Long proactiveTransactionId);
    void deleteProactiveTransactionAuditTrialByProactiveProjectId(Long proactiveTransactionId);
    void deleteReactiveTransactionByReactiveProjectId(Long reactiveProjectId);
    void deleteReactiveTransactionCNDByReactiveTransactionId(Long reactiveProjectId);
    void deleteReactiveTransactionAuditTrialByReactiveProjectId(Long reactiveProjectId);
    void deleteReactiveProjectById(Long reactiveProjectId);
    List getControlIdList(String controlIds,String controlCompareString);
    String getControlIdListByControlIds(String ids);
    String getControlNameByControlId(long id);
    List getControlWiseCountryListByControlId(String controlId);
    List getProactiveTransactionListByRegionAndControlId(long  regionId, String controlId);
    List getDiscriminatorCustInfoByTransactionId(long transactionId);
    List getDiscriminatorVendorInfoByTransactionId(long transactionId);
    List getDiscriminatorEmployInfoByTransactionId(long transactionId);
    List getAdditionalInfoListByTransactionId(long transactionId);
    void deleteSupportingDocumentByProactiveProjectId(Long proactiveTransactionId);
    List getSuspiciousTrxList(List trxIds, Date fromDate, Date toDate) ;
    String getTrxIdsBetween(Date fromDate, Date toDate);
    List getRealTimeTransactionList(RealTimeProject realTimeProject);
    boolean isUserIdExist(long userId);
    List isAllRealTimeTransactionCoded(long realTimeProjectId);
    List getTransactionList(GlobalTransactionSearch globalTransactionSearch);
    List getPartialDataList( int page, int rp , String  qtype, String query, String sortname, String sortorder, String className);
    List getPartialProactiveProjectDataList( int page, int rp);
    List getPartialReactiveProjectDataList( int page, int rp);
    List getPartialProactiveBlockWeightList(long regionId, int toDate, int fromDate, int page, int rp);
    List getPartialTransactionList(GlobalTransactionSearch globalTransactionSearch,int page, int rp,String qtype, String query, String sortname, String sortorder);
    int getTotalTransactionCount(GlobalTransactionSearch globalTransactionSearch);
    List getRules();
    void saveSuspiciousTxList(Rule rule, int jobNo);
    List getTransactionSummaryList(long realtimeProjectId);
    List getColumnNameList(String tableName);
    void deleteSuspiciousTransaction(int job);
    int getLastJobId();
    List getTempLedgerData(String ledger);
    void saveAccountsPaybleLedger(long txid, long apid, String VMFid);
    long saveVendorMasterLedger(long txid, String vmId);
    long saveCustomerMasterLedger(long txid, long vmId);
    long saveEmployeeMasterLedger(long txid, String vmId);
    void saveAccountsReceiveAbleLedger(long txid, long arId);
    List<Long> getTrxIdListBetween(Date fromDate, Date toDate);
    List<Object> getRealTimeTrxPartialDataList(int page, int rp,  String qtype, String query, String sortname, String sortorder);
    int getTotalRealTimeProject(String qtype, String query );
    long saveVendorPersonInfo(long vmId, String vendor, long vmfid);
    long getLastPersonId();
    long saveCustomerPersonInfo(long cuId, String customer, long cmfid, String employee, long emfid );
    long saveEmployeePersonInfo(long cuId, String customer, long cmfid);
    void saveTransactionLi(long txId,  String headCode, String ledger);
    void saveFundsDisbursementLedger(long txId, long arId);
    Map getVendorMasterLedger(long vmId);
    Map getCustomerMasterLedger(long cmId);
    Map getFundsDisbursementLedger(long arId);
    Map getOrdersEntityLedger(long apId);
    long getLastTransactionId();
    void executeSql(StringBuffer sql);
    void saveIASql(StringBuffer sql, long txId);
    List<Object> getRealTimeSummaryPartialDataList(String controlIds,String compareControlSql,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder);
    List<Object> getRealTimeTransactionPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId);
    int getRealTimeTransactionCount(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId);
    int getRealTimeSummaryCount(String controlIds,String compareControlSql,long realTimeProjectId);
    List getARDetailListForTemp();
    List getAccountReceivableList(String tableName);
    void executeQueryForTransNarrativeSave(String query);
    List getRealTimeProjectListForGrouping(String groupByColumn);
    boolean isHolidayIdExist(long holidayId);
    List getListByCustomQuery(String sql);
    long getVendorMasterLedgerId(String VMFid);
    long getCustomerMasterLedgerId(long VMFid);
    long getEmployeeMasterLedgerId(String VMFid);
    long getPersonWithFK(String discriminator, long vmfid);
    long getLastId(String ledgerName);
    long getLedgerId(String ledger, String value);
    List getAllEmailId(String masterLedger);
    boolean isValidPersonTypeWithMail(User user);
    long getPersonTypeIdByEmail(User user);
    boolean isEmailExistWithPersonType(User user);
    List getUnreadedPolicyList(User user);
    int getPolicyCount(Policy policy);
    List getPartialPolicyList(Policy policy,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user);
    int getTotalPolicyCount(Policy policy);
    List getPartialTrainingList(Training training,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user);
    int getTotalTrainingCount(Training training);
    List<Object> getAllTransactionPartialDataList(String controlIds,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId);
    int getAllTransactionPartialDataListCount(String qtype, String query, String sortname, String sortorder,long ruleId);
    List<Object> getAllSummaryCountList(String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder);
    int getTotalSummaryCountList(String controlId,String qtype, String query, String sortname, String sortorder);
    List<Object> getProactiveTransactionPartialDataList(long proactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId);
    int getProactiveTransactionCount(long proactiveProjectId,String controlCompareString,String controlId);
    int getProactiveSummaryCount(long proactiveProjectId,String controlCompareString,String controlId);
    List<Object> getProactiveSummaryPartialDataList(long proactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder);
    List<Object> getReactiveTransactionPartialDataList(long reactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId);
    int getReactiveTransactionCount(long reactiveProjectId,String controlCompareString,String controlId,String qtype, String query, String sortname, String sortorder,long ruleId);
    int getReactiveSummaryCount(long reactiveProjectId,String controlCompareString,String controlId);
    List<Object> getReactiveSummaryPartialDataList(long reactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder);
    List getProjectWiseCountListByControlIds(String controlIds,String controlCompareString,int page, int rp,  String qtype, String query, String sortname, String sortorder);
    int getProjectWiseCountByControlIds(String controlIds,String controlCompareString,String qtype, String query, String sortname, String sortorder);
    String getcommaSeparatedControlIds();
    void importReactiveTransaction();
    void importProactiveTransaction();
    void deletePolicyAndProcedureByPolicyId(long policyId);
    int getTotalPolicyConfirmedByPolicyId(long policyId,int status);
    int getTotalTrainingParticipant(long traiingId,int status);
    int getTotalPolicyUnconfirmedByPolicyId(long policyId,int status);
    int getTotalEmployeeNeedRetake(long trainingId,int status);
    int getPolicyListCount(Policy policy,String qtype, String query, String sortname,User user);
    int getTotalEmployeeWithEmailAddresd();
    int getTotalVendorWithEmailAddresd();
    List<Map> getRealTimeTransactionListByRtProjectId(long rtProjectId);
    List<Map> getRealTimeTransactionListFurtherAction(RealTimeProject rtProjectId);
    int getTotalPolicyConfirmedByAudiance(int audianceCode,User user);
    int getTotalPolicyNotConfirmedByAudiance(int audianceCode,User user);
    int getTotalNotificationSendToAudiance(int audianceCode);
    void updateTrainingOptionAnswer(List<String> questionAnswerBf);
    int getTotalCourseToBeStartByUser(User user);
    int getTotalCourseToBeRetakeOrPassedByUser(User user,String retakeOrPassed);
    List getAllEmployee();
    List<Object> getMyTrxPartialDataList(int page, int rp,  String qtype, String query, String sortname, String sortorder,User user);
    int getTotalMyTrx(String qtype, String query ,User user);
    List<Object> getMyRealTimeTransactionPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId,User user);
    int getMyRealTimeTransactionCount(String controlIds,String controlCompareString,long realTimeProjectId,String qtype, String query,long ruleId,User user);
    List<Object> getMyRealTimeSummaryPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user);
    int getMyRealTimeSummaryCount(String controlIds,String compareControlSql,long realTimeProjectId,User user);
    List getAssignedEmployeeList(long txId);
    List getEmployeeWithNonUserList();
    boolean isUserExistWithPersonTypeId(long personTypeId);
    List getAnalystUserList();
    String getEmployeeNameById(long id);
    int getTotalOutstandingPolicyByUser(User user);
    int getTotalSignedPolicyByUser(User user);
}