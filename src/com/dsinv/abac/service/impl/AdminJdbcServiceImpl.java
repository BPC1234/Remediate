package com.dsinv.abac.service.impl;

import com.dsinv.abac.dao.AdminJdbcDao;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.impl.AccountsReceivableLedger;
import com.dsinv.abac.service.AdminJdbcService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("adminJdbcService")
public class AdminJdbcServiceImpl implements AdminJdbcService {

    private static Logger logger = Logger.getLogger(AdminJdbcServiceImpl.class);
    @Autowired(required = true)
    private AdminJdbcDao adminJdbcDao;


    public List getProactiveTransaction(ProactiveProject proactiveProject) {
        return adminJdbcDao.getProactiveTransaction(proactiveProject);
    }


    public List getReactiveTransactionList(ReactiveProject reactiveProject) {
        return adminJdbcDao.getReactiveTransactionList(reactiveProject);
    }

    public  List getProactiveTransactionByTrxId(ProactiveProject proactiveProject, long proactiveTransactionId){
        return adminJdbcDao.getProactiveTransactionByTrxId(proactiveProject,proactiveTransactionId);
    }
    public  List getReactiveTransactionByTrxId(ReactiveProject reactiveProject, long reactiveTransactionId){
        return adminJdbcDao.getReactiveTransactionByTrxId(reactiveProject,reactiveTransactionId);
    }

    public void deleteProactiveTransactionByProactiveProjectId(Long proactiveProjectId){
       adminJdbcDao.deleteProactiveTransactionByProactiveProjectId(proactiveProjectId);
    }

    public void deleteProactiveTransactionCNDByProactiveTransactionId(Long proactiveTransactionId){
         adminJdbcDao.deleteProactiveTransactionCNDByProactiveTransactionId(proactiveTransactionId);
    }

    public void deleteProactiveTransactionAuditTrialByProactiveProjectId(Long proactiveTransactionId){
         adminJdbcDao.deleteProactiveTransactionAuditTrialByProactiveProjectId(proactiveTransactionId);
    }

    public void deleteReactiveTransactionByReactiveProjectId(Long reactiveProjectId) {
        adminJdbcDao.deleteProactiveTransactionByProactiveProjectId(reactiveProjectId);
    }

    public void deleteReactiveTransactionCNDByReactiveTransactionId(Long reactiveProjectId) {
        adminJdbcDao.deleteProactiveTransactionCNDByProactiveTransactionId(reactiveProjectId);
    }

    public void deleteReactiveTransactionAuditTrialByReactiveProjectId(Long reactiveProjectId) {
        adminJdbcDao.deleteProactiveTransactionAuditTrialByProactiveProjectId(reactiveProjectId);
    }

    public void deleteReactiveProjectById(Long reactiveProjectId) {
        adminJdbcDao.deleteReactiveProjectById(reactiveProjectId);

    }

    public List getControlIdList(String controlIds,String controlCompareString){
       return adminJdbcDao.getControlIdList(controlIds,controlCompareString);
    }

    public String getControlIdListByControlIds(String ids){
        return adminJdbcDao.getControlIdListByControlIds(ids);
    }

    public String getControlNameByControlId(long id){
        return adminJdbcDao.getControlNameByControlId(id);
    }

    public List getControlWiseCountryListByControlId(String controlId){
        return adminJdbcDao.getControlWiseCountryListByControlId(controlId);
    }


    public List getProactiveTransactionListByRegionAndControlId(long regionId, String controlId) {
        return adminJdbcDao.getProactiveTransactionListByRegionAndControlId(regionId,controlId);
    }

    public  List getDiscriminatorCustInfoByTransactionId(long transactionId){
        return adminJdbcDao.getDiscriminatorCustInfoByTransactionId(transactionId);
    }
    public  List getDiscriminatorVendorInfoByTransactionId(long transactionId){
        return adminJdbcDao.getDiscriminatorVendorInfoByTransactionId(transactionId);
    }
    public List getDiscriminatorEmployInfoByTransactionId(long transactionId){
        return adminJdbcDao.getDiscriminatorEmployInfoByTransactionId(transactionId);
    }

    public List getAdditionalInfoListByTransactionId(long transactionId){
        return adminJdbcDao.getAdditionalInfoListByTransactionId(transactionId);
    }

    public void deleteSupportingDocumentByProactiveProjectId(Long proactiveTransactionId){
        adminJdbcDao.deleteSupportingDocumentByProactiveProjectId(proactiveTransactionId);
    }

    public List getSuspiciousTrxList(List trxIds, Date fromDate, Date toDate){
        return adminJdbcDao.getSuspiciousTrxList(trxIds, fromDate, toDate);
    }

    public String getTrxIdsBetween(Date fromDate, Date toDate){
        return adminJdbcDao.getTrxIdsBetween(fromDate, toDate);
    }

    public   List getRealTimeTransactionList(RealTimeProject realTimeProject){
        return adminJdbcDao.getRealTimeTransactionList(realTimeProject);

    }

    public boolean isUserIdExist(long userId){
        return adminJdbcDao.isUserIdExist(userId);
    }
    public List isAllRealTimeTransactionCoded(long realTimeProjectId){
        return adminJdbcDao.isAllRealTimeTransactionCoded(realTimeProjectId);
    }

    public List getTransactionList(GlobalTransactionSearch globalTransactionSearch){
        return  adminJdbcDao.getTransactionList(globalTransactionSearch);
    }
    public List getPartialDataList( int page, int rp , String qtype, String query, String sortname, String sortorder,String className) {
        return  adminJdbcDao.getPartialDataList( page, rp ,qtype, query,   sortname,   sortorder,className);
    }

    public  List getPartialProactiveProjectDataList( int page, int rp){
        return adminJdbcDao.getPartialProactiveProjectDataList(page,rp);
    }

    public List getPartialReactiveProjectDataList( int page, int rp){
        return adminJdbcDao.getPartialReactiveProjectDataList(page,rp);
    }

    public List getPartialProactiveBlockWeightList(long regionId, int toDate, int fromDate, int page, int rp){
        return adminJdbcDao.getPartialProactiveBlockWeightList(regionId,toDate,fromDate,page,rp);
    }

    public List getPartialTransactionList(GlobalTransactionSearch globalTransactionSearch,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getPartialTransactionList(globalTransactionSearch,page,rp,qtype,query,sortname,sortorder);
    }

    public int getTotalTransactionCount(GlobalTransactionSearch globalTransactionSearch){
        return adminJdbcDao.getTotalTransactionCount(globalTransactionSearch);
    }
    public List getRules() {
        return adminJdbcDao.getRules();
    }
    public void saveSuspiciousTxList(Rule rule,  int jobNo) {
        adminJdbcDao.saveSuspiciousTxList(rule, jobNo);
    }
    public List getTransactionSummaryList(long realtimeProjectId){
        return adminJdbcDao.getTransactionSummaryList(realtimeProjectId);
    }

    public List getColumnNameList(String tableName){
        return adminJdbcDao.getColumnNameList(tableName);
    }
    public  boolean isTransactionSuspiciousWithSameRule(long txId, long ruleId, long executionHeaderId) {
        return adminJdbcDao.isTransactionSuspiciousWithSameRule(txId, ruleId, executionHeaderId);
    }
    public void deleteSuspiciousTransaction(int job) {
         adminJdbcDao.deleteSuspiciousTransaction(job);
    }
    public int getLastJobId() {
        return  adminJdbcDao.getLastJobId();
    }
    public List getTempLedgerData(String ledger){
        return adminJdbcDao.getTempLedgerData(ledger);
    }
    public void saveAccountsPaybleLedger(long txid, long apid, String VMFid){
        adminJdbcDao.saveAccountsPaybleLedger(txid, apid, VMFid);
    }
    public void saveAccountsReceiveAbleLedger(long txid, long arid){
        adminJdbcDao.saveAccountsReceiveAbleLedger(txid, arid);
    }
    public long saveVendorMasterLedger(long txid, String vmId) {
        return adminJdbcDao.saveVendorMasterLedger(txid,vmId);
    }
    public long saveCustomerMasterLedger(long txid, long vmId) {
        return adminJdbcDao.saveCustomerMasterLedger(txid,vmId);
    }
    public long saveEmployeeMasterLedger(long txid, String vmId) {
        return adminJdbcDao.saveEmployeeMasterLedger(txid,vmId);
    }
    public List<Long> getTrxIdListBetween(Date fromDate, Date toDate) {
        return adminJdbcDao.getTrxIdListBetween(fromDate, toDate);
    }
    public List<Object> getRealTimeTrxPartialDataList(int page, int rp,  String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getRealTimeTrxPartialDataList(page,rp,qtype,query,sortname,sortorder);
    }

    public int getTotalRealTimeProject(String qtype, String query ) {
        return adminJdbcDao.getTotalRealTimeProject(qtype, query);
    }

    public long saveVendorPersonInfo(long vmId, String vendor, long vmfid) {
          return adminJdbcDao.saveVendorPersonInfo(vmId, vendor, vmfid);
    }

    public long saveCustomerPersonInfo(long cuId, String customer, long cmfid, String employee, long emfid ){
          return adminJdbcDao.saveCustomerPersonInfo(cuId, customer, cmfid, employee, emfid );
    }
    public long saveEmployeePersonInfo(long cuId, String customer, long cmfid) {
          return adminJdbcDao.saveEmployeePersonInfo(cuId, customer, cmfid);
    }
    public long getLastPersonId() {
          return adminJdbcDao.getLastPersonId();
    }
    public void saveTransactionLi(long txId,  String headCode, String ledger){
         adminJdbcDao.saveTransactionLi(  txId,    headCode,   ledger);
    }
    public void saveFundsDisbursementLedger(long txId, long arId) {
        adminJdbcDao.saveFundsDisbursementLedger(txId, arId);
    }
    public Map getVendorMasterLedger(long vmId) {
        return adminJdbcDao.getVendorMasterLedger(vmId);
    }
    public Map getCustomerMasterLedger(long cmId) {
        return adminJdbcDao.getCustomerMasterLedger(cmId);
    }
    public Map  getFundsDisbursementLedger(long arId) {
        return adminJdbcDao.getFundsDisbursementLedger(arId);
    }
    public Map getOrdersEntityLedger(long apId){
        return adminJdbcDao.getOrdersEntityLedger(apId);
    }
    public long getLastTransactionId(){
        return adminJdbcDao.getLastTransactionId();
    }

    public void executeSql(StringBuffer sql){
        adminJdbcDao.executeSql(  sql);
    }
    public void saveIASql(StringBuffer sql, long txId){
        adminJdbcDao.saveIASql(  sql, txId);
    }

    public List<Object> getRealTimeSummaryPartialDataList(String controlIds,String compareControlSql,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getRealTimeSummaryPartialDataList(controlIds,compareControlSql,realTimeProjectId,page,rp,qtype,query,sortname,sortorder);
    }
    public List<Object> getRealTimeTransactionPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        return adminJdbcDao.getRealTimeTransactionPartialDataList(controlIds,controlCompareString,realTimeProjectId,page,rp,qtype,query,sortname,sortorder,ruleId);
    }

    public int getRealTimeTransactionCount(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        return adminJdbcDao.getRealTimeTransactionCount(controlIds,controlCompareString,realTimeProjectId,page, rp, qtype, query, sortname, sortorder,ruleId);
    }

    public int getRealTimeSummaryCount(String controlIds,String compareControlSql,long realTimeProjectId){
        return adminJdbcDao.getRealTimeSummaryCount(controlIds,compareControlSql,realTimeProjectId);
    }

    public List getARDetailListForTemp(){
        return adminJdbcDao.getARDetailListForTemp();
    }
    public List getAccountReceivableList(String tableName){
        return adminJdbcDao.getAccountReceivableList(tableName);
    }
    public void executeQueryForTransNarrativeSave(String query){
        adminJdbcDao.executeQueryForTransNarrativeSave(query);
    }

    public List getRealTimeProjectListForGrouping(String groupByColumn){
       return adminJdbcDao.getRealTimeProjectListForGrouping(groupByColumn);
    }

    public boolean isHolidayIdExist(long holidayId){
        return  adminJdbcDao.isHolidayIdExist(holidayId);
    }

    public List getListByCustomQuery(String sql){
        return adminJdbcDao.getListByCustomQuery(sql);
    }
    public long getVendorMasterLedgerId(String VMFid){
        return  adminJdbcDao.getVendorMasterLedgerId(VMFid);
    }
    public long getCustomerMasterLedgerId(long VMFid){
        return  adminJdbcDao.getCustomerMasterLedgerId(VMFid);
    }

    public long getEmployeeMasterLedgerId(String VMFid){
        return  adminJdbcDao.getEmployeeMasterLedgerId(VMFid);
    }
    public long getPersonWithFK(String discriminator, long vmfid) {
        return adminJdbcDao.getPersonWithFK(discriminator, vmfid);
    }
    public long getLastId(String ledgerName){
        return adminJdbcDao.getLastId(ledgerName);
    }
    public long getLedgerId(String ledger, String value){
        return adminJdbcDao.getLedgerId(ledger, value);
    }
    public List getAllEmailId(String masterLedger) {
        return adminJdbcDao.getAllEmailId(masterLedger);
    }

    public boolean isValidPersonTypeWithMail(User user){
        return adminJdbcDao.isValidPersonTypeWithMail(user);
    }

    public long getPersonTypeIdByEmail(User user){
        return adminJdbcDao.getPersonTypeIdByEmail(user);
    }

    public boolean isEmailExistWithPersonType(User user){
        return adminJdbcDao.isEmailExistWithPersonType(user);
    }

    public List getUnreadedPolicyList(User user){
        return adminJdbcDao.getUnreadedPolicyList(user);
    }
    public int getPolicyCount(Policy policy){
        return adminJdbcDao.getPolicyCount(policy);
    }
    public List getPartialPolicyList(Policy policy,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user){
        return adminJdbcDao.getPartialPolicyList(policy,page,rp,qtype,query,sortname,sortorder,user);
    }
    public int getTotalPolicyCount(Policy policy){
        return adminJdbcDao.getTotalPolicyCount(policy);
    }
    public List getPartialTrainingList(Training training,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user){
        return adminJdbcDao.getPartialTrainingList(training,page,rp,qtype,query,sortname,sortorder,user);
    }
    public int getTotalTrainingCount(Training training){
        return adminJdbcDao.getTotalTrainingCount(training);
    }
    public List<Object> getAllTransactionPartialDataList(String controlIds,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        return adminJdbcDao.getAllTransactionPartialDataList(controlIds,page,rp,qtype,query,sortname,sortorder,ruleId);
    }
    public int getAllTransactionPartialDataListCount(String qtype, String query, String sortname, String sortorder,long ruleId){
        return adminJdbcDao.getAllTransactionPartialDataListCount(qtype,query,sortname,sortorder,ruleId);
    }
    public List<Object> getAllSummaryCountList(String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getAllSummaryCountList(controlId,page,rp,qtype,query,sortname,sortorder);
    }
    public int getTotalSummaryCountList(String controlId,String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getTotalSummaryCountList(controlId,qtype,query,sortname,sortorder);
    }
    public List<Object> getProactiveTransactionPartialDataList(long proactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        return adminJdbcDao.getProactiveTransactionPartialDataList(proactiveProjectId,controlCompareString,controlId,page, rp,qtype, query, sortname, sortorder,ruleId);
    }
    public int getProactiveTransactionCount(long proactiveProjectId,String controlCompareString,String controlId){
        return adminJdbcDao.getProactiveTransactionCount(proactiveProjectId,controlCompareString,controlId);
    }
    public int getProactiveSummaryCount(long proactiveProjectId,String controlCompareString,String controlId){
        return adminJdbcDao.getProactiveSummaryCount(proactiveProjectId,controlCompareString,controlId);
    }
    public List<Object> getProactiveSummaryPartialDataList(long proactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getProactiveSummaryPartialDataList(proactiveProjectId,controlCompareString,controlId,page, rp,qtype, query, sortname, sortorder);
    }
    public List<Object> getReactiveTransactionPartialDataList(long reactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        return adminJdbcDao.getReactiveTransactionPartialDataList(reactiveProjectId,controlCompareString,controlId,page, rp,qtype, query, sortname, sortorder,ruleId);
    }
    public int getReactiveTransactionCount(long reactiveProjectId,String controlCompareString,String controlId,String qtype, String query, String sortname, String sortorder,long ruleId){
        return adminJdbcDao.getReactiveTransactionCount(reactiveProjectId,controlCompareString,controlId,qtype,query, sortname, sortorder,ruleId);
    }
    public int getReactiveSummaryCount(long reactiveProjectId,String controlCompareString,String controlId){
        return adminJdbcDao.getReactiveSummaryCount(reactiveProjectId,controlCompareString,controlId);
    }
    public List<Object> getReactiveSummaryPartialDataList(long reactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getReactiveSummaryPartialDataList(reactiveProjectId,controlCompareString,controlId,page, rp,qtype, query, sortname, sortorder);
    }
    public List getProjectWiseCountListByControlIds(String controlIds,String controlCompareString,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getProjectWiseCountListByControlIds(controlIds,controlCompareString,page, rp,qtype, query, sortname, sortorder);
    }
    public int getProjectWiseCountByControlIds(String controlIds,String controlCompareString, String qtype, String query, String sortname, String sortorder){
        return adminJdbcDao.getProjectWiseCountByControlIds(controlIds,controlCompareString,qtype, query, sortname, sortorder);
    }
    public String getcommaSeparatedControlIds(){
        return adminJdbcDao.getcommaSeparatedControlIds();
    }
    public void importReactiveTransaction(){
          adminJdbcDao.importReactiveTransaction();
    }
    public void importProactiveTransaction(){
          adminJdbcDao.importProactiveTransaction();
    }

    public void deletePolicyAndProcedureByPolicyId(long policyId){
         adminJdbcDao.deletePolicyAndProcedureByPolicyId(policyId);
    }

    public int getTotalPolicyConfirmedByPolicyId(long policyId,int status){
        return adminJdbcDao.getTotalPolicyConfirmedByPolicyId(policyId,status);
    }

    public int getTotalTrainingParticipant(long traiingId, int status){
        return adminJdbcDao.getTotalTrainingParticipant(traiingId, status);
    }

    public int getTotalPolicyUnconfirmedByPolicyId(long policyId,int status){
        return adminJdbcDao.getTotalPolicyUnconfirmedByPolicyId(policyId,status);
    }

    public int getTotalEmployeeNeedRetake(long trainingId, int status){
        return adminJdbcDao.getTotalEmployeeNeedRetake(trainingId, status);
    }

    public int getPolicyListCount(Policy policy,String qtype, String query, String sortname, User user){
        return adminJdbcDao.getPolicyListCount(policy,qtype,query,sortname,user);
    }

    public int getTotalEmployeeWithEmailAddresd(){
        return adminJdbcDao.getTotalEmployeeWithEmailAddresd();
    }

    public int getTotalVendorWithEmailAddresd(){
    return adminJdbcDao.getTotalVendorWithEmailAddresd();
    }

    public List<Map> getRealTimeTransactionListByRtProjectId(long rtProjectId){
        return adminJdbcDao.getRealTimeTransactionListByRtProjectId(rtProjectId);
    }
    public List<Map> getRealTimeTransactionListFurtherAction(RealTimeProject rtProjectId){
        return adminJdbcDao.getRealTimeTransactionListFurtherAction(rtProjectId);
    }

    public int getTotalPolicyConfirmedByAudiance(int audianceCode,User user){
     return adminJdbcDao.getTotalPolicyConfirmedByAudiance(audianceCode,user);
    }

    public int getTotalPolicyNotConfirmedByAudiance(int audianceCode,User user){
        return adminJdbcDao.getTotalPolicyNotConfirmedByAudiance(audianceCode,user);
    }

    public int getTotalNotificationSendToAudiance(int audianceCode){
        return adminJdbcDao.getTotalNotificationSendToAudiance(audianceCode);
    }

    public void updateTrainingOptionAnswer(List<String> questionAnswerBf){
         adminJdbcDao.updateTrainingOptionAnswer( questionAnswerBf);
    }
    public int getTotalCourseToBeStartByUser(User user){
        return adminJdbcDao.getTotalCourseToBeStartByUser(user);
    }

    public int getTotalCourseToBeRetakeOrPassedByUser(User user,String retakeOrPassed){
        return adminJdbcDao.getTotalCourseToBeRetakeOrPassedByUser(user,retakeOrPassed);
    }

    public List getAllEmployee(){
        return adminJdbcDao.getAllEmployee();
    }

    public List getAssignedEmployeeList(long txId){
        return adminJdbcDao.getAssignedEmployeeList(txId);
    }


    public List getEmployeeWithNonUserList() {
        return adminJdbcDao.getEmployeeWithNonUserList();
    }

    public List<Object> getMyTrxPartialDataList(int page, int rp,  String qtype, String query, String sortname, String sortorder,User user){
        return adminJdbcDao.getMyTrxPartialDataList(page,rp,qtype,query,sortname,sortorder,user);
    }
    public int getTotalMyTrx(String qtype, String query,User user ){
        return adminJdbcDao.getTotalMyTrx(qtype, query,user);
    }

    public List<Object> getMyRealTimeTransactionPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId,User user){
        return adminJdbcDao.getMyRealTimeTransactionPartialDataList(controlIds,controlCompareString,realTimeProjectId,page,rp,qtype,query,sortname,sortorder,ruleId,user);
    }
    public int getMyRealTimeTransactionCount(String controlIds,String controlCompareString,long realTimeProjectId,String qtype, String query,long ruleId,User user){
        return adminJdbcDao.getMyRealTimeTransactionCount(controlIds,controlCompareString,realTimeProjectId,qtype,query,ruleId,user);
    }

    public List<Object> getMyRealTimeSummaryPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user){
        return adminJdbcDao.getMyRealTimeSummaryPartialDataList(controlIds,controlCompareString,realTimeProjectId,page, rp, qtype, query, sortname, sortorder,user);
    }

    public int getMyRealTimeSummaryCount(String controlIds,String compareControlSql,long realTimeProjectId,User user){
        return adminJdbcDao.getMyRealTimeSummaryCount(controlIds,compareControlSql,realTimeProjectId,user);
    }

    public List  getAnalystUserList(){
        return adminJdbcDao.getAnalystUserList();
    }

    public boolean isUserExistWithPersonTypeId(long personTypeId){
        return adminJdbcDao.isUserExistWithPersonTypeId(personTypeId);
    }

    public String getEmployeeNameById(long id){
        return adminJdbcDao.getEmployeeNameById(id);
    }

    public int getTotalOutstandingPolicyByUser(User user){
           return adminJdbcDao.getTotalOutstandingPolicyByUser(user);
    }
    public int getTotalSignedPolicyByUser(User user){
        return adminJdbcDao.getTotalSignedPolicyByUser(user);
    }
}
