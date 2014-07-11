package com.dsinv.abac.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dsinv.abac.Entity;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.Vendor;
import com.dsinv.abac.ledger.impl.AccountsPayableLedger;
import com.dsinv.abac.ledger.impl.AccountsReceivableLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.util.Constants;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dsinv.abac.dao.AdminDao;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.vo.admin.UserVo;


@Repository
public class AdminDaoImpl implements AdminDao {
    private static Logger logger = Logger.getLogger(AdminDaoImpl.class);

    private HibernateTemplate hibernateTemplate;
    private Session session;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUserList(UserVo userVo) {
        String sql = "From User ";
        boolean hasClause = false;

        if (!Utils.isNullOrEmpty(userVo.getUserName())) {
            sql += (hasClause ? "AND " : "WHERE ") + "userName LIKE '%?%'";
        }
        if (!Utils.isNullOrEmpty(userVo.getMobileNumber())) {
            sql += (hasClause ? "AND " : "WHERE ") + "mobileNumber LIKE '%?%'";
        }
        if (!Utils.isNullOrEmpty(userVo.getRole())) {
            sql += (hasClause ? "AND " : "WHERE ") + "role = '?'";
        }
        if (!Utils.isNullOrEmpty(userVo.getIsActive())) {
            sql += (hasClause ? "AND " : "WHERE ") + "active = '?'";
        }
        logger.debug(sql);
        return hibernateTemplate.find(sql,userVo.getUserName(),userVo.getMobileNumber(),userVo.getRole(),userVo.getIsActive());
    }


    public List<User> getAllUserList() {
        return hibernateTemplate.find("FROM User");
    }


    public List<ReactiveProject> getReactiveProjectList() {
        return hibernateTemplate.find("FROM ReactiveProject");
    }


    public List<Region> getRegionList() {
        return hibernateTemplate.find("FROM Region");
    }


    public List<User> getAllUserList(String userName) {
        return hibernateTemplate.find("FROM User where userName LIKE '%?%'",userName);
    }


    public AbstractBaseEntity loadEntityById(Long id, String className) {
        List<AbstractBaseEntity> entityList = hibernateTemplate.find("From "+className+" where id = ?", id);
        if (entityList != null && entityList.size() > 0)
            return entityList.get(0);
        return null;
    }

    public void saveOrUpdate(AbstractBaseEntity entity) {

        logger.debug("Saving an Entity: "+entity.getClass());
        hibernateTemplate.saveOrUpdate(entity);
    }

    public void deleteEntityById(Long id, String className) {

        hibernateTemplate.bulkUpdate("Delete From "+className+" where id = ?", id);
    }

    public boolean isUserNameExist(String userName){
        List<User> userList =  hibernateTemplate.find("FROM User where userName = ?",userName);
        if(userList != null && userList.size() > 0)
            return true;
        else
            return false;
    }

    public List<ProactiveBlockWeightRatio> getAllProactiveBlockWeightRatioList(){
        return hibernateTemplate.find("FROM ProactiveBlockWeightRatio");
    }

    public AbstractBaseEntity getAbstractBaseEntityByString(String className,String anyColumn,String columnValue){

        List<AbstractBaseEntity> entityList = hibernateTemplate.find("From "+className+" where "+anyColumn+" = ?", columnValue);
        if(entityList != null && entityList.size() > 0)
            return entityList.get(0);
        return null;
    }

    public List<AbstractBaseEntity> getAnyEntityList(String className){
        return hibernateTemplate.find("FROM "+className);
    }


    public List<ProactiveBlockWeight> getProactiveBlockWeight(long regionId, int toDate, int fromDate) {
        String query = "FROM " + Entity.PROACTIVE_BLOCK_WEIGHT.getValue() +
                " WHERE region_id = ? " +
                " AND date >= ? " +
                " AND date <= ? ";
        List param = new ArrayList();
        param.add(regionId);
        param.add(fromDate);
        param.add(toDate);
        return hibernateTemplate.find( query, param.toArray() );
    }

    public ProactiveProject getProactiveProject(Region region){
        List<ProactiveProject> projects = hibernateTemplate.find("FROM ProactiveProjcet Where Region = ?",region.getId());
        if(projects != null && projects.size()> 0)
            return projects.get(0);
        return null;
    }

    public List<Control> getControlListByControlId(String controlIds){
        return hibernateTemplate.find("FROM Control where id IN (?)", controlIds);
    }

    public List<Control> getControlListByTransactionType(String transactionType){
        return hibernateTemplate.find("FROM Control where transactionType = ?",transactionType);
    }

    public  ProactiveTransaction getProactiveTransactionById(long id){
        List<ProactiveTransaction> list = hibernateTemplate.find("From "+Entity.PROACTIVE_TRANSACTION.getValue()+" where id = ?", id);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;

    }

    public void save(Object obj){
        logger.debug("Saving an Object: "+obj.getClass());
        hibernateTemplate.save(obj);
    }

    public void update(Object obj){
        hibernateTemplate.update(obj);
    }

    public void saveOrUpdateForAnyObject(Object obj){
        logger.debug("Saving or Updating an Object: "+obj.getClass());
        hibernateTemplate.saveOrUpdate(obj);
    }

    public List<ProactiveTransactionAuditTrial> getProactiveTransactionAuditTrialByProactiveTxId(long proactiveTransactionId){
        List<ProactiveTransactionAuditTrial> list = hibernateTemplate.find("From "+Entity.PROACTIVE_TRANSACTION_AUDIT_TRIAL.getValue()+" where proactiveTransaction.id = ?", proactiveTransactionId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public ProactiveTransactionCND getProactiveTransactionCNDByProactiveTransactionid(long id){
        List<ProactiveTransactionCND> list = hibernateTemplate.find("From "+Entity.PROACTIVE_TRANSACTION_CND.getValue()+" where proactiveTransaction.id = ?", id);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;

    }


    public Region getRegionByName(String countryName) {
        List<Region> list =  hibernateTemplate.find("FROM "+Entity.REGION.getValue()+" where name =? " , countryName);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public Transaction getTransactionById(Long trxId){
        List<Transaction> list =  hibernateTemplate.find("FROM "+Entity.TRANSACTION.getValue()+" where id =? " ,trxId);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }


    public List<ProactiveTransaction> getProactiveTransactionListByProactiveProjectId(Long proactiveProjectId){
        List<ProactiveTransaction> list =  hibernateTemplate.find("FROM "+Entity.PROACTIVE_TRANSACTION.getValue()+" where  proactiveProject.id=? " ,proactiveProjectId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }


    public void delete(Object obj){
        hibernateTemplate.delete(obj);
    }

    public  ReactiveTransaction getReactiveTransactionById(long id){
        List<ReactiveTransaction> list = hibernateTemplate.find("From "+Entity.REACTIVE_TRANSACTION.getValue()+" where id = ?", id);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;

    }

    public ReactiveTransactionCND getReactiveTransactionCNDByReactiveTransactionid(long id){
        List<ReactiveTransactionCND> list = hibernateTemplate.find("From "+Entity.REACTIVE_TRANSACTION_CND.getValue()+" where reactiveTransaction.id = ?", id);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;

    }

    public List<ReactiveTransactionAuditTrial> getReactiveTransactionAuditTrialByReactiveTxId(long reactiveTransactionId){
        List<ReactiveTransactionAuditTrial> list = hibernateTemplate.find("From "+Entity.REACTIVE_TRANSACTION_AUDIT_TRIAL.getValue()+" where reactiveTransaction.id = ?", reactiveTransactionId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }


    public  List<ProactiveTransactionSupportingDocument> getProactiveTransactionSupportingDocumentListByTrxId(long proactiveTrxId){
        List<ProactiveTransactionSupportingDocument> list = hibernateTemplate.find("From ProactiveTransactionSupportingDocument where proactiveTransaction.id = ?", proactiveTrxId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public  List<ReactiveTransactionSupportingDocument> getReactiveTransactionSupportingDocumentListByTrxId(long reactiveTrxId){
        List<ReactiveTransactionSupportingDocument> list = hibernateTemplate.find("From ReactiveTransactionSupportingDocument where reactiveTransaction.id = ?", reactiveTrxId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public ProactiveTransactionSupportingDocument getProactiveTransactionSupportingDocumentById(long proctiveTrxSuppDocId){
        List<ProactiveTransactionSupportingDocument> list = hibernateTemplate.find("From ProactiveTransactionSupportingDocument where id = ?", proctiveTrxSuppDocId);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public ReactiveTransactionSupportingDocument getReactiveTransactionSupportingDocumentById(long reactiveTrxSuppDocId){
        List<ReactiveTransactionSupportingDocument> list = hibernateTemplate.find("From ReactiveTransactionSupportingDocument where id = ?", reactiveTrxSuppDocId);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public List<Control> getAllControl(){
        List<Control> list = hibernateTemplate.find("From Control");
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

	@Override
	public long getPersonEntity(String name, String ledger) {
		List<Object> personList = hibernateTemplate.find("From  "+ ledger+ " WHERE entityName = ? ",name);
        if (personList != null && personList.size() > 0)  {
            Map obj =  (Map)personList.get(0);
            return Long.parseLong(obj.get("id") != null ? obj.get("id").toString(): "0");
        }
        return 0;
	}

	@Override
	public void saveCSVTransaction(Transaction aTx) {
		logger.debug("Saving a transaction....");
		save(aTx);
		for (TransactionLi txLi : aTx.getTransactionLiList()) {
			txLi.setTransaction(aTx);
			save(txLi);
		}
		logger.debug("Complete the saving of a transaction.");
	}

    public  AccountsPayableLedger getAccountsPayableLedger(String ledgerType){
        List list = hibernateTemplate.find("From "+ " Ledger "+" where ledger_type = ?", Constants.LEDGER_TYPE_AP);
        if (list != null && list.size() > 0)
            return (AccountsPayableLedger)list.get(0);
        return null;

    }

    public List getTransactionList(Date fromDate, Date toDate) {
        List transactionList = hibernateTemplate.find("From "+ " Transaction "+" where created >= ? AND created <= ?", fromDate, toDate);
        if(transactionList != null && transactionList.size() >0)
            return transactionList;
        return  null;
    }

    public RealTimeTransaction getRealTimeTransactionById(long id){
        List<RealTimeTransaction> list = hibernateTemplate.find("From "+Entity.REAL_TIME_TRANSACTION.getValue()+" where id = ?", id);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }


    public RealTimeTransactionCND getRealTimeTransactionCNDByRtTransactionid(long id){
        List<RealTimeTransactionCND> list = hibernateTemplate.find("From "+Entity.REAL_TIME_TRANSACTION_CND.getValue()+" where realTimeTransaction.id = ?", id);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public List<RealTimeTransactionAuditTrial> getRealTimeTransactionAuditTrialByRtTxId(long realTimeTransactionId){
        List<RealTimeTransactionAuditTrial> list = hibernateTemplate.find("From "+Entity.REAL_TIME_TRANSACTION_AUDIT_TRIAL.getValue()+" where realTimeTransaction.id = ?", realTimeTransactionId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public List<RealTimeTransactionComment> getRealTimeTranscationCommentList(long realTimeTransactionId) {
        List<RealTimeTransactionComment> list = hibernateTemplate.find("From RealTimeTransactionComment where real_time_transaction_id = ?", realTimeTransactionId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public List<ReactiveTransactionComment> getReactiveTransactionCommentList(long reactiveTransactionId) {
        List<ReactiveTransactionComment> list = hibernateTemplate.find("From ReactiveTransactionComment where reactive_transaction_id = ?", reactiveTransactionId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public  List<RealTimeTransactionSupportingDocument> getRealTimeTransactionSupportingDocumentListByTrxId(long realTimeTrxId){
        List<RealTimeTransactionSupportingDocument> list = hibernateTemplate.find("From "+Entity.REAL_TIME_TRANSACTION_SUPPORTING_DOCUMENT.getValue()+" where realTimeTransaction.id = ?", realTimeTrxId);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public RealTimeTransactionSupportingDocument getRealTimeTransactionSupportingDocumentById(long rtTrxSuppDocId){
        List<RealTimeTransactionSupportingDocument> list = hibernateTemplate.find("From RealTimeTransactionSupportingDocument where id = ?", rtTrxSuppDocId);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public List<Vendor> getVendorList(String vendorNname, String zip, String countryCode) {
    	List<Vendor> vendorsList = new ArrayList<Vendor>();
    	return vendorsList;
    }

    public List<TransactionApproval> getAllTransactionApprovalList(){
        List<TransactionApproval> list = hibernateTemplate.find("From  TransactionApproval");
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public  List<Policy> getAllPolicy() {
        List<Policy> list = hibernateTemplate.find("From Policy");
        if (list != null && list.size() > 0)
            return list;
        return null;
    }
    public List<TransactionPolicy> getTransactionPolicy(long transactionId) {
        List<TransactionPolicy> list = hibernateTemplate.find("From TransactionPolicy WHERE transaction_id = ? ", transactionId  );
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public  List<Training> getAllTraining() {
        List<Training> list = hibernateTemplate.find("From Training");
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public void saveRealTimeTrxAuditTrial(RealTimeTransaction realTimeTransaction,List<String> messageList){
       for(String message: messageList){
//       logger.debug("---message is: "+message);
           if(message != null && !Utils.isEmpty(message)){
           RealTimeTransactionAuditTrial realTimeTransactionAuditTrial = new RealTimeTransactionAuditTrial();
           realTimeTransactionAuditTrial.setRealTimeTransaction(realTimeTransaction);
           realTimeTransactionAuditTrial.setChangeAction(message);
           realTimeTransactionAuditTrial.setChangeAuthor(Utils.getLoggedUserName());
           realTimeTransactionAuditTrial.setChangeDate(Utils.getTodaysDate());
           hibernateTemplate.save(realTimeTransactionAuditTrial);
           }
       }
    }

    public   int getEntitySize(String entity){
        List list = hibernateTemplate.find("Select count(*) From " + entity );

        if (list != null && list.size() > 0)    {
            return Integer.parseInt((list.get(0)).toString());

        }
        return 0;

    }

    public void saveProactiveTrxAuditTrial(ProactiveTransaction proactiveTransaction,List<String> messageList){
        for(String message: messageList){
            logger.debug("--Proactive trx aud trial -message is: "+message);
            if(message != null && !Utils.isEmpty(message)){
                ProactiveTransactionAuditTrial proactiveTransactionAuditTrial = new ProactiveTransactionAuditTrial();
                proactiveTransactionAuditTrial.setProactiveTransaction(proactiveTransaction);
                proactiveTransactionAuditTrial.setChangeAction(message);
                proactiveTransactionAuditTrial.setChangeAuthor(Utils.getLoggedUserName());
                proactiveTransactionAuditTrial.setChangeDate(Utils.getTodaysDate());
                hibernateTemplate.save(proactiveTransactionAuditTrial);
            }
        }
    }

    public void saveReactiveTrxAuditTrial(ReactiveTransaction reactiveTransaction,List<String> messageList){
        for(String message: messageList){
            logger.debug("--Reactive trx aud trial-message is: "+message);
            if(message != null && !Utils.isEmpty(message)){
                ReactiveTransactionAuditTrial reactiveTransactionAuditTrial= new ReactiveTransactionAuditTrial();
                reactiveTransactionAuditTrial.setReactiveTransaction(reactiveTransaction);
                reactiveTransactionAuditTrial.setChangeAction(message);
                reactiveTransactionAuditTrial.setChangeAuthor(Utils.getLoggedUserName());
                reactiveTransactionAuditTrial.setChangeDate(Utils.getTodaysDate());
                hibernateTemplate.save(reactiveTransactionAuditTrial);
            }
        }
    }

    public TransactionApproval getTransactionApprovalById(long id) {
        List<TransactionApproval> transactionApprovalList = hibernateTemplate.find("From TransactionApproval where id = ?", id);
        if (transactionApprovalList != null && transactionApprovalList.size() > 0)
            return transactionApprovalList.get(0);
        return null;
    }

    public List<Rule> getRules() {
        List<Rule> ruleList = hibernateTemplate.find("From Rule");
        if (ruleList != null && ruleList.size() > 0)
            return ruleList;
        return null;
    }

    public Rule getRuleById(long id){
        List<Rule> ruleList = hibernateTemplate.find("From Rule WHERE id=?",id);
        if (ruleList != null && ruleList.size() > 0)
            return ruleList.get(0);
        return null;
    }

    public List<Lookup> getLookupList(){
        List<Lookup> list = hibernateTemplate.find("From Lookup");
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public int getAssignmentSize() {
        List list =  hibernateTemplate.find("SELECT assignmentSize From RealTimeInterval ");
        if (list != null && list.size() > 0)    {
            return Integer.parseInt((list.get(0)).toString());
        }
        return 0;
    }

    public List<SuspiciousTransaction> getViolatedRuleListByTrxId(long trxId){
        List<SuspiciousTransaction> list =  hibernateTemplate.find("FROM SuspiciousTransaction WHERE transactionId = ?",trxId);
        if (list != null && list.size() > 0)    {
            return list;
        }
        return null;
    }

    public Policy getPolicyById(long policyId){
        List<Policy> list =  hibernateTemplate.find("FROM Policy WHERE id = ?",policyId);
        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;

    }

    public Training getTrainingById(long trainingId){
        List<Training> list =  hibernateTemplate.find("FROM Training WHERE id = ?",trainingId);
        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;

    }
    public TransactionPolicy loadTransactionPolicy(long id){
        List<TransactionPolicy> list =  hibernateTemplate.find("FROM TransactionPolicy WHERE id = ?",id);
        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;

    }
    public List<TrainingQuestion> getTrainingQuestion(long trainingId) {
        List<TrainingQuestion> list =  hibernateTemplate.find("FROM TrainingQuestion WHERE training_id = ? ",trainingId);
        if (list != null && list.size() > 0)    {
            return list;
        }
        return null;

    }
    public List<TrainingQuestion> getTrainingQuestionSet(Training training) {

        hibernateTemplate.setMaxResults(training.getNoOfQuestion());
        List<TrainingQuestion> list =  hibernateTemplate.find("FROM TrainingQuestion  WHERE training_id = ? ",training.getId());
        hibernateTemplate.setMaxResults(0);
        if (list != null && list.size() > 0)    {
            return list;
        }
        return null;

    }
    public void deleteTraining(long trainingId) {

        hibernateTemplate.bulkUpdate("DELETE FROM EmployeeTraining where training_FK = ? ", trainingId);
        hibernateTemplate.bulkUpdate("DELETE FROM TrainingQuestionAnswer where training_question_id IN (SELECT id FROM TrainingQuestion where training_id = ? )", trainingId);
        hibernateTemplate.bulkUpdate("DELETE FROM TrainingQuestion where training_id = ? ", trainingId);
        hibernateTemplate.bulkUpdate("DELETE FROM Training where id = ? ", trainingId);

    }

    public User getUserByUserName(String userName){
        List<User> list =  hibernateTemplate.find("FROM User WHERE userName = ?",userName);
        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;

    }

    public PolicyAndProcedure getPolicyAndProcedure(User user,long policyId){
        String sql = "";
        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())) {
            sql ="FROM PolicyAndProcedure WHERE employeeMasterLedger.id = ?";
        } else if(PersonType.VENDOR.getValue().equals(user.getUserType())) {
            sql ="FROM PolicyAndProcedure WHERE vendorMasterLedger.id = ?";
        } /*else if(PersonType.ADMIN.getValue().equals(user.getUserType())) {
            sql ="FROM PolicyAndProcedure WHERE vendorMasterLedger.id = ?";
        }*/

        List<PolicyAndProcedure> list =  hibernateTemplate.find(sql+" AND policy.id="+policyId,user.getUserTypeId());
        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;

    }
    public void saveTrainingQuestion(TrainingQuestion trainingQuestion, List<TrainingQuestionAnswer> trainingQuestionAnswerList) {
        saveOrUpdate(trainingQuestion);
        for(TrainingQuestionAnswer trainingQuestionAnswer : trainingQuestionAnswerList) {
            trainingQuestionAnswer.setTrainingQuestion(trainingQuestion);
            saveOrUpdate(trainingQuestionAnswer);
            if(trainingQuestionAnswer.isCorrect()) {
                trainingQuestion.setCorrectOption(trainingQuestionAnswer.getId());
                saveOrUpdate(trainingQuestion);
            }
        }
    }

    public RealTimeTransaction getRealTimeTransactionByTrxIdAndRtProjectId(long trxId,long rtProjectId){
        List<Object> queryParam=new ArrayList<Object>();
        if(trxId > 0 )
            queryParam.add(trxId);
        if(rtProjectId > 0)
            queryParam.add(rtProjectId);

        List<RealTimeTransaction> list =  hibernateTemplate.find(" FROM RealTimeTransaction WHERE 1=1 "
                                                           + (trxId > 0 ? "AND transaction.id = ? " : "")
                                                           + (rtProjectId >0 ? "AND realTimeProject.id=?" : ""),queryParam.toArray());

        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;

    }
    public void deleteTrainingQuestion(long trainingId, long questionId) {
        hibernateTemplate.bulkUpdate("DELETE FROM TrainingQuestionAnswer where training_question_id = ? ", questionId);
        hibernateTemplate.bulkUpdate("DELETE FROM TrainingQuestion where training_id = ? AND  id = ?", trainingId, questionId);
    }
    public EmployeeTraining getEmployeeTraining(long employFK, long trainingFK) {
        List<EmployeeTraining> list =  hibernateTemplate.find("FROM EmployeeTraining WHERE employee_master_ledger_id = ? AND training_FK = ? ",employFK, trainingFK);
        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;
    }
    public EmployeeMasterLedger loadEmployee(long employeeId){

        List<EmployeeMasterLedger> list =  hibernateTemplate.find("FROM EmployeeMasterLedger WHERE id = ? ",employeeId);
        if (list != null && list.size() > 0)    {
            return list.get(0);
        }
        return null;
    }

   public List<EmployeeMasterLedger> getAllEmployeeMasterLedger(){

        List<EmployeeMasterLedger> list =  hibernateTemplate.find("FROM EmployeeMasterLedger");
        if (list != null && list.size() > 0)    {
            return list;
        }
        return null;
    }

}