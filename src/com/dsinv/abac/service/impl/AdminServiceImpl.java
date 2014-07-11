package com.dsinv.abac.service.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.Vendor;
import com.dsinv.abac.ledger.impl.AccountsPayableLedger;

import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.util.TransactionLIHeader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.dsinv.abac.breadCrum.BreadCrumbTree;
import com.dsinv.abac.breadCrum.Node;
import com.dsinv.abac.dao.AdminDao;
import com.dsinv.abac.entity.AbstractBaseEntity;
import com.dsinv.abac.entity.Control;
import com.dsinv.abac.entity.ProactiveBlockWeight;
import com.dsinv.abac.entity.ProactiveBlockWeightRatio;
import com.dsinv.abac.entity.ProactiveProject;
import com.dsinv.abac.entity.ProactiveTransaction;
import com.dsinv.abac.entity.ProactiveTransactionAuditTrial;
import com.dsinv.abac.entity.ProactiveTransactionCND;
import com.dsinv.abac.entity.ProactiveTransactionSupportingDocument;
import com.dsinv.abac.entity.ReactiveProject;
import com.dsinv.abac.entity.ReactiveTransaction;
import com.dsinv.abac.entity.ReactiveTransactionAuditTrial;
import com.dsinv.abac.entity.ReactiveTransactionCND;
import com.dsinv.abac.entity.ReactiveTransactionSupportingDocument;
import com.dsinv.abac.entity.Region;
import com.dsinv.abac.entity.Transaction;
import com.dsinv.abac.entity.TransactionLi;
import com.dsinv.abac.entity.User;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.vo.admin.CSVTransaction;
import com.dsinv.abac.vo.admin.UserVo;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    private static Logger logger = Logger.getLogger(AdminServiceImpl.class);
    @Autowired(required = true)
    private AdminDao adminDao;

    @Override
	@Transactional(readOnly = true)
    public List<User> getAllUserList(UserVo userVo) {

        List<User> userList = adminDao.getAllUserList(userVo);
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                if (user != null) {
                    // Utils.setEditDeleteLinkOnAbstractEntity(user, "user");
                }
            }
        }
        return userList;
    }

    @Override
	@Transactional(readOnly = true)
    public List<User> getAllUserList() {

        List<User> userList = adminDao.getAllUserList();
        if (userList != null && userList.size() > 0) {
            return userList;
        }
        return null;
    }

    @Override
	@Transactional(readOnly = true)
    public List<ReactiveProject> getReactiveProjectList() {

        List<ReactiveProject> reactiveProjectList = adminDao.getReactiveProjectList();
        if (reactiveProjectList != null && reactiveProjectList.size() > 0) {
            return reactiveProjectList;
        }
        return null;
    }

    @Override
    public List<Region> getRegionList() {
        List<Region> regionList = adminDao.getRegionList();
        if (regionList != null && regionList.size() > 0) {
            return regionList;
        }
        return null;
    }


    @Override
	@Transactional(readOnly = true)
    public List<User> getAllUserList(String userName) {

        List<User> userList = adminDao.getAllUserList(userName);
        if (userList != null && userList.size() > 0) {
            return userList;
        }
        return null;
    }

    @Override
	@Transactional(readOnly = true)
    public AbstractBaseEntity loadEntityById(Long id, String className) {
        return adminDao.loadEntityById(id, className);
    }

    @Override
	public void saveOrUpdate(AbstractBaseEntity entity) {
        adminDao.saveOrUpdate(entity);

    }

    @Override
	public void deleteEntityById(Long id, String className) {
        adminDao.deleteEntityById(id, className);
    }

    @Override
	public boolean isUserNameExist(String userName) {
        boolean test = adminDao.isUserNameExist(userName.trim());

        return test;
    }

    @Override
	@Transactional(readOnly = true)
    public List<ProactiveBlockWeightRatio> getAllProactiveBlockWeightRatioList() {

        List<ProactiveBlockWeightRatio> ProactiveBlockWeightRatioList = adminDao.getAllProactiveBlockWeightRatioList();
        if (ProactiveBlockWeightRatioList != null && ProactiveBlockWeightRatioList.size() > 0) {
            return ProactiveBlockWeightRatioList;
        }
        return null;
    }

    @Override
	public AbstractBaseEntity getAbstractBaseEntityByString(String className, String anyColumn, String columnValue) {
        return adminDao.getAbstractBaseEntityByString(className, anyColumn, columnValue);
    }


    @Override
	public void addNode(String nodeName,int level, HttpServletRequest request) {
/*        int level2 = 0;
        int level = 0;*/
//        String requestURL = new String(request.getRequestURL());
        String requestURL  = getFullURL(request);
        String queryString = request.getQueryString();


        BreadCrumbTree tree = (BreadCrumbTree) request.getSession().getAttribute("breadcrumb");
        if (tree == null) {
            tree = new BreadCrumbTree();
            request.getSession().setAttribute("breadcrumb", tree);
            //level2 = 0;
        }/* else {
            List<Node> newList = tree.getTree();
            int count = 0;
            for (Node n : newList) {
                if (nodeName.equals(n.getName())) {
                    count++;
                    level = n.getLevel();
                }
                level2 = n.getLevel();
            }
            if (count == 0) {
                level2++;
            } else {
                level2 = level;

            }

        }*/

       // Node node = new Node(nodeName, requestURL, level2);
        Node node = new Node(nodeName, requestURL, level);
        tree.addNode(node);
        request.getSession().setAttribute("breadcrumb", tree);

    }

    @Override
	public List<AbstractBaseEntity> getAnyEntityList(String className){
       return adminDao.getAnyEntityList(className);

    }


    @Override
	public List<ProactiveBlockWeight> getProactiveBlockWeight(long regionId, int toDate, int fromDate) {
        return adminDao.getProactiveBlockWeight(regionId,toDate,fromDate);
    }

    @Override
	public ProactiveProject getProactiveProject(Region region) {
        return  adminDao.getProactiveProject(region);
    }

    @Override
	public List<Control> getControlListByControlId(String controlIds){
        return adminDao.getControlListByControlId(controlIds);
    }

    @Override
	public List<Control> getControlListByTransactionType(String transactionType){
        return adminDao.getControlListByTransactionType(transactionType);
    }

    @Override
	public ProactiveTransaction getProactiveTransactionById(long id){
        return adminDao.getProactiveTransactionById(id);
    }
    @Override
	public void save(Object obj){
        adminDao.save(obj);
    }

    @Override
	public void update(Object obj){
        adminDao.update(obj);
    }

    @Override
	public void saveOrUpdateForAnyObject(Object obj){
        adminDao.saveOrUpdateForAnyObject(obj);
    }

    @Override
	public List<ProactiveTransactionAuditTrial> getProactiveTransactionAuditTrialByProactiveTxId(long proactiveTransactionId){
     return adminDao.getProactiveTransactionAuditTrialByProactiveTxId(proactiveTransactionId);
    }

    @Override
	public ProactiveTransactionCND getProactiveTransactionCNDByProactiveTransactionid(long id){
        return adminDao.getProactiveTransactionCNDByProactiveTransactionid(id);
    }


    @Override
	public Region getRegionByName(String countryName) {
        return adminDao.getRegionByName(countryName);
    }

    public  String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    @Override
	public Transaction getTransactionById(Long trxId){
        return adminDao.getTransactionById(trxId);
    }

    @Override
	public List<ProactiveTransaction> getProactiveTransactionListByProactiveProjectId(Long proactiveProjectId){
        return adminDao.getProactiveTransactionListByProactiveProjectId(proactiveProjectId);
    }

    @Override
	public void delete(Object obj){
       adminDao.delete(obj);
    }

    @Override
	public ReactiveTransaction getReactiveTransactionById(long id){
        return adminDao.getReactiveTransactionById(id);
    }
    @Override
	public ReactiveTransactionCND getReactiveTransactionCNDByReactiveTransactionid(long id){
        return adminDao.getReactiveTransactionCNDByReactiveTransactionid(id);
    }

    @Override
	public List<ReactiveTransactionAuditTrial> getReactiveTransactionAuditTrialByReactiveTxId(long reactiveTransactionId){
        return adminDao.getReactiveTransactionAuditTrialByReactiveTxId(reactiveTransactionId);
    }

    @Override
	public  List<ProactiveTransactionSupportingDocument> getProactiveTransactionSupportingDocumentListByTrxId(long proactiveTrxId){
          return adminDao.getProactiveTransactionSupportingDocumentListByTrxId(proactiveTrxId);
    }

    @Override
	public  List<ReactiveTransactionSupportingDocument> getReactiveTransactionSupportingDocumentListByTrxId(long reactiveTrxId){
        return adminDao.getReactiveTransactionSupportingDocumentListByTrxId(reactiveTrxId);
    }

    @Override
	public ProactiveTransactionSupportingDocument getProactiveTransactionSupportingDocumentById(long proactiveTrxCommentId){
        return adminDao.getProactiveTransactionSupportingDocumentById(proactiveTrxCommentId);
    }
    @Override
	public  ReactiveTransactionSupportingDocument getReactiveTransactionSupportingDocumentById(long proactiveTrxCommentId){
        return adminDao.getReactiveTransactionSupportingDocumentById(proactiveTrxCommentId);
    }

    @Override
	public List<Control> getAllControl(){
        return adminDao.getAllControl();
    }

    @Override
	public List<Object> CSV2Bean(String filePath, String[] headers, CellProcessor[] processors, Object aObj) throws FileNotFoundException, IOException{
		CsvBeanReader reader = new CsvBeanReader(new FileReader(filePath), CsvPreference.STANDARD_PREFERENCE);
		List<Object> objectList = new ArrayList<Object>();
		//Read headers
	    reader.getHeader(true);
		while ((aObj = reader.read(aObj.getClass(), headers, processors)) != null) {		      
			objectList.add(aObj);
		}
		return objectList;
	}

	@Override
	public long  saveCSVTransaction(Object object, int jobNo) {
        long trxId = 0;
		if(object instanceof CSVTransaction){
            logger.debug("AMLOG 3:: Transaction creation start.");
			CSVTransaction csvTransaction=(CSVTransaction)object;
 			Transaction aTx = new Transaction ();
            logger.debug("AMLOG 5:: Save ledger end.");
		}
        return  trxId;
	}

    @Override
    public AccountsPayableLedger getAccountsPayableLedger(String ledgerType) {
        return adminDao.getAccountsPayableLedger(ledgerType);
    }

    public void deleteTraining(long trainingId){
        adminDao.deleteTraining(trainingId);
    }

    public void saveTrainingQuestion(TrainingQuestion trainingQuestion, List<TrainingQuestionAnswer> trainingQuestionAnswerList){
        adminDao.saveTrainingQuestion(trainingQuestion, trainingQuestionAnswerList);
    }
    public void deleteTrainingQuestion(long trainingId, long questionId){
        adminDao.deleteTrainingQuestion(trainingId, questionId);
    }

    private List<TransactionLi> getTransactionLiList(CSVTransaction csvTransaction) {
		logger.debug("AMLOG:: TransactionLi Creation start. ");
        List<TransactionLi> txLiList = new ArrayList<TransactionLi>();
		
		/*TransactionLiLabel aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Accounts Payable Balance");*/
		TransactionLi txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Accounts Payable Balance");
		txLi.setValue(csvTransaction.getAccountsPayableBalance().toString());
		txLiList.add(txLi);
		
	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Accounts Receivable Balance");		*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Accounts Receivable Balance");
        txLi.setValue(csvTransaction.getAccountsReceivableBalance().toString());
		txLiList.add(txLi);
/*
		aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Advance Balance");*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Advance Balance");
		txLi.setValue(csvTransaction.getAdvanceBalance().toString());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Advance Type");*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Advance Type");
		txLi.setValue(csvTransaction.getAdvanceType());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Approval Limit");*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Approval Limit");
		txLi.setValue(csvTransaction.getApprovalLimit().toString());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Approver Name");*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Approver Name");
        txLi.setValue(csvTransaction.getApproverName());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Bank Account");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Bank Account");
        txLi.setValue(csvTransaction.getBankAccount());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Business Unit");
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
		txLi.setValue(csvTransaction.getBusinessUnit());
        txLi.setHeadName("Business Unit");
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Contract Identification");		*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Contract Identification");
        txLi.setValue(csvTransaction.getContractIdentification());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Credit Limit");		
		*/
        txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
		txLi.setValue(csvTransaction.getCreditLimit().toString());
        txLi.setHeadName("Credit Limit");
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Discount Amount");*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Discount Amount");
		txLi.setValue(csvTransaction.getDiscountAmount().toString());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Document Date");	*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
		txLi.setValue(csvTransaction.getDocumentDateText());
        txLi.setHeadName("Document Date");
        txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Document Number");*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Document Number");
        txLi.setValue(csvTransaction.getDocumentNumber());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Expense Account");	*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Expense Account");
        txLi.setValue(csvTransaction.getExpenseAccount());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Expense Type");		*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
		txLi.setHeadName("Expense Type");
        txLi.setValue(csvTransaction.getExpenseType());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Initiator Submitter Name");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Initiator Submitter Name");
        txLi.setValue(csvTransaction.getInitiatorSubmitterName());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Inter Company Transfer Amount");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
		txLi.setHeadName("Inter Company Transfer Amount");
        txLi.setValue(csvTransaction.getInterCompanyTransferAmount().toString());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Inventory Amount");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Inventory Amount");
		txLi.setValue(csvTransaction.getInventoryAmount().toString());
		txLiList.add(txLi);
/*
		aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Manual Override");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
		txLi.setHeadName("Manual Override");
        txLi.setValue(csvTransaction.getManualOverride());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Order Quantity");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Order Quantity");
		txLi.setValue(csvTransaction.getOrderQuantity().toString());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Order Status");
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Order Status");
		txLi.setValue(csvTransaction.getOrderStatus());
		txLiList.add(txLi);
/*
		aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Payee Name");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Payee Name");
        txLi.setValue(csvTransaction.getPayeeName());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Payment Currency");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Payment Currency");
		txLi.setValue(csvTransaction.getPaymentCurrency());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Payment Method");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Payment Method");
        txLi.setValue(csvTransaction.getPaymentMethod());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Payment Terms");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Payment Terms");
		txLi.setValue(csvTransaction.getPaymentTerms());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Previous Order");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Previous Order");
		txLi.setValue(csvTransaction.getPreviousOrder());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Previous Order Date");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Previous Order Date");
		txLi.setValue(csvTransaction.getPreviousOrderDateText());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Purchase Order Amount");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Purchase Order Amount");
		txLi.setValue(csvTransaction.getPurchaseOrderAmount().toString());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Purchase Order Date");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Purchase Order Date");
		txLi.setValue(csvTransaction.getPurchaseOrderDateText());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Recipient Bank Account Domicile");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Recipient Bank Account Domicile");
		txLi.setValue(csvTransaction.getRecipientBankAccountDomicile());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Recipient Name");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Recipient Name");
		txLi.setValue(csvTransaction.getRecipientName());
		txLiList.add(txLi);
/*
		aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Render Date");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Render Date");
		txLi.setValue(csvTransaction.getRenderDateText());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Sales Order Number");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Sales Order Number");
		txLi.setValue(csvTransaction.getSalesOrderNumber().toString());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Product Completion Status");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Product Completion Status");
        txLi.setValue(csvTransaction.getProductCompletionStatus());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Product Stock Code");		
*/		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Product Stock Code");
        txLi.setValue(csvTransaction.getProductStockCode());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Product Type");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Product Type");
		txLi.setValue(csvTransaction.getProductType());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Ship To Address");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Ship To Address");
		txLi.setValue(csvTransaction.getShipToAddress());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Shipped Quantity");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Shipped Quantity");
		txLi.setValue(csvTransaction.getShippedQuantity().toString());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Shipping Entity");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Shipping Entity");
		txLi.setValue(csvTransaction.getShippingQuantity().toString());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Shipping Number");		
*/		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Shipping Number");
        txLi.setValue(csvTransaction.getShippingNumber());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Shipping Quantity");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Shipping Quantity");
		txLi.setValue(csvTransaction.getShippingQuantity().toString());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Tin");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Tin");
		txLi.setValue(csvTransaction.getTin());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Transaction Cost");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Transaction Cost");
        txLi.setValue(csvTransaction.getTransactionCost().toString());
		txLiList.add(txLi);
/*

		aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Transaction Document Number");		
*/
		txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Transaction Document Number");
        txLi.setValue(csvTransaction.getTransactionDocumentNumber());
		txLiList.add(txLi);

	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Transaction Narrative");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Transaction Narrative");
        txLi.setValue(csvTransaction.getTransactionNarrative());
		txLiList.add(txLi);

		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Transaction Description");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Transaction Description");
        txLi.setValue(csvTransaction.getTransactionDescription());
		txLiList.add(txLi);
	/*
		aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Transaction Number");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Transaction Number");
        txLi.setValue(csvTransaction.getTransactionNumber());
		txLiList.add(txLi);
	/*
		aLabel = new TransactionLiLabel();
		aLabel.setHeadName("UnitOf Measure Cost");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("UnitOf Measure Cost");
        txLi.setValue(csvTransaction.getUnitOfMeasureCost().toString());
		txLiList.add(txLi);
		
	/*	aLabel = new TransactionLiLabel();
		aLabel.setHeadName("Unit Of Measure Price");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("Unit Of Measure Price");
        txLi.setValue(csvTransaction.getUnitOfMeasurePrice().toString());
		txLiList.add(txLi);

		
		/*aLabel = new TransactionLiLabel();
		aLabel.setHeadName("UnitOf Measure Quantity");		
	*/	txLi = new TransactionLi();
//		txLi.setTransactionLiLabel(aLabel);
        txLi.setHeadName("UnitOf Measure Quantity");
        txLi.setValue(csvTransaction.getUnitOfMeasureQuantity().toString());
		txLiList.add(txLi);
        logger.debug("AMLOG:: TransactionLi Creation end. ");
		return txLiList; 
	}

    @Override
	public List getTransactionList(Date fromDate, Date toDate) {
        return adminDao.getTransactionList(fromDate, toDate);
    }

    public RealTimeTransaction getRealTimeTransactionById(long id){
            return adminDao.getRealTimeTransactionById(id);
        }

    public RealTimeTransactionCND getRealTimeTransactionCNDByRtTransactionid(long id){
        return adminDao.getRealTimeTransactionCNDByRtTransactionid(id);
    }

    public List<RealTimeTransactionAuditTrial> getRealTimeTransactionAuditTrialByRtTxId(long realTimeTransactionId){
     return adminDao.getRealTimeTransactionAuditTrialByRtTxId(realTimeTransactionId);

    }
    public List<RealTimeTransactionComment> getRealTimeTranscationCommentList(long realTimeTransactionId){
     return adminDao.getRealTimeTranscationCommentList(realTimeTransactionId);

    }

    public List<ReactiveTransactionComment> getReactiveTransactionCommentList(long reactiveTransactionId){
     return adminDao.getReactiveTransactionCommentList(reactiveTransactionId);

    }

    public List<RealTimeTransactionSupportingDocument> getRealTimeTransactionSupportingDocumentListByTrxId(long realTimeTrxId){
        return adminDao.getRealTimeTransactionSupportingDocumentListByTrxId(realTimeTrxId);
    }

    public RealTimeTransactionSupportingDocument getRealTimeTransactionSupportingDocumentById(long rtTrxSuppDocId){
        return adminDao.getRealTimeTransactionSupportingDocumentById(rtTrxSuppDocId);

    }
    public List<Vendor> getVendorList(String vendorNname, String zip, String countryCode) {
    	return adminDao.getVendorList(vendorNname,zip, countryCode);
    }

    public List<TransactionApproval> getAllTransactionApprovalList(){
        return adminDao.getAllTransactionApprovalList();
    }

    public List<Policy> getAllPolicy(){
        return adminDao.getAllPolicy();
    }

    public List<TransactionPolicy> getTransactionPolicy(long transactionId) {
        return adminDao.getTransactionPolicy(transactionId);
    }

    public List<Training> getAllTraining(){
        return adminDao.getAllTraining();
    }

    public void saveRealTimeTrxAuditTrial(RealTimeTransaction realTimeTransaction,List<String> messageList){
        adminDao.saveRealTimeTrxAuditTrial(realTimeTransaction,messageList);
    }

    public   int getEntitySize(String entity){
        return adminDao.getEntitySize(entity);

    }

    public void saveProactiveTrxAuditTrial(ProactiveTransaction proactiveTransaction,List<String> messageList){
        adminDao.saveProactiveTrxAuditTrial(proactiveTransaction,messageList);
    }

    public void saveReactiveTrxAuditTrial(ReactiveTransaction reactiveTransaction,List<String> messageList){
        adminDao.saveReactiveTrxAuditTrial(reactiveTransaction,messageList);
    }

    public TransactionApproval getTransactionApprovalById(long id){
        return adminDao.getTransactionApprovalById(id);
    }
    public List<Rule> getRules(){
        return adminDao.getRules();
    }
    public int getAssignmentSize(){
        return adminDao.getAssignmentSize();
    }

    public Rule getRuleById(long id){
        return adminDao.getRuleById(id);
    }

    public List<Lookup> getLookupList(){
        return adminDao.getLookupList();
    }

    public List<SuspiciousTransaction> getViolatedRuleListByTrxId(long trxId){
        return adminDao.getViolatedRuleListByTrxId(trxId);
    }

    public Policy getPolicyById(long policyId){
        return adminDao.getPolicyById(policyId);

    }

    public Training getTrainingById(long trainingId){
        return adminDao.getTrainingById(trainingId);

    }
    public TransactionPolicy loadTransactionPolicy(long id){
        return adminDao.loadTransactionPolicy(id);

    }

    public List<TrainingQuestion> getTrainingQuestion(long trainingId){
        return adminDao.getTrainingQuestion(trainingId);
    }

    public List<TrainingQuestion> getTrainingQuestionSet(Training trainingId){
        return adminDao.getTrainingQuestionSet(trainingId);
    }
    public EmployeeTraining getEmployeeTraining(long employFK, long trainingFK){
        return adminDao.getEmployeeTraining(  employFK,   trainingFK);
    }
    public EmployeeMasterLedger loadEmployee(long employeeId){
        return adminDao.loadEmployee(employeeId);
    }

    public String getATransactionLiSQL(CSVTransaction csvTransaction, long txId) {
        StringBuffer transactionLiSB = new StringBuffer();

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.AccountsPayableBalance);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getAccountsPayableBalance());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.AccountsReceivableBalance);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getAccountsReceivableBalance());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.AdvanceBalance);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getAdvanceBalance());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.AdvanceType);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getAdvanceType());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ApprovalLimit);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getApprovalLimit());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ApproverName);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getApproverName());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.BankAccountNo);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getBankAccount());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.BusinessUnit);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getBusinessUnit());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ContractIdentification);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getContractIdentification());
        transactionLiSB.append("'),");

         transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.CreditLimit);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getCreditLimit());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.DiscountAmt);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getDiscountAmount());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.DocDt);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getDocumentDate());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.DocNbr);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getDocumentNumber());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ExpenseAccount);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getExpenseAccount());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ExpenseType);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getExpenseType());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.InitiatorSubmitterName);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getInitiatorSubmitterName());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.InterCompanyTransaferAmount);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getInterCompanyTransferAmount());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.InventoryAmount);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getInterCompanyTransferAmount());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ManualOverride);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getManualOverride());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ORDER_QUANTITY);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getOrderQuantity());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ORDER_STATUS);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getOrderStatus());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PAYEE_NAME);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getOrderStatus());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.Currency);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getPaymentCurrency());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PaymentMethod);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getPaymentMethod());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PaymentTerms);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getPaymentTerms());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PreviousOrder);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getPreviousOrder());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PreviousOrderDt);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getPreviousOrderDate());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PURCHASE_ORDER_AMOUNT);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getPurchaseOrderAmount());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PURCHASE_ORDER_DATE);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getPurchaseOrderDate());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.RECIPIENT_BANK_ACCOUNT_DOMICILE);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getRecipientBankAccountDomicile());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.RECIPIENT_NAME);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getRecipientName());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.RenderDt);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getRenderDate());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.SalesOrderNumber);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getSalesOrderNumber());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.PRODUCT_COMPLETION_STATUS);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getProductCompletionStatus());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ServiceProductStockCode);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getProductStockCode());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ServiceProductType);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getProductType());
        transactionLiSB.append("'),");


        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.SHIP_TO_ADDRESS);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getShipToAddress());
        transactionLiSB.append("'),");


        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ShippedQty);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getShippedQuantity());
        transactionLiSB.append("'),");


        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.SHIPPING_ENTITY);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getShippingEntity());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.ShippingID);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getShippingNumber());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.SHIPPING_QUANTITY);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getShippingQuantity());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.TIN);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getTin());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.TransCost);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getTransactionCost());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.DocNbr);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getTransactionDocumentNumber());
        transactionLiSB.append("'),");
/*
        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.TransNarrative);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getTransactionNarrative());
        transactionLiSB.append("'),");*/


        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.TransNarrative);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getTransactionDescription());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.TRANSACTION_NUMBER);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getTransactionNumber());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.UnitCost);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getUnitOfMeasureCost());
        transactionLiSB.append("'),");

        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.UnitPrice);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getUnitOfMeasurePrice());
        transactionLiSB.append("'),");


        transactionLiSB.append("(");
        transactionLiSB.append(txId);
        transactionLiSB.append(",'");
        transactionLiSB.append(TransactionLIHeader.UnitQty);
        transactionLiSB.append("','");
        transactionLiSB.append(csvTransaction.getUnitOfMeasureQuantity());
        transactionLiSB.append("')");

        return  transactionLiSB.toString();
    }

    public String getACustomerSQL(CSVTransaction csvTransaction) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("('");
        stringBuffer.append(csvTransaction.getCustomerName());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerTaxEinNo());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerBankAccountNo());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerBankAccountLocation());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerType());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerAddress());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerAddress1());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerCity());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerState());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerZipCode());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getCustomerCountry());
        stringBuffer.append("','");
        stringBuffer.append(Constants.DISCRIMINATOR_VALUE_CUSTOMER);
        stringBuffer.append("')");
        return stringBuffer.toString();

    }

    public String getAVendorSQL(CSVTransaction csvTransaction) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("('");
        stringBuffer.append(csvTransaction.getVendorName());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorTaxEinNo());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorBankAccountNo());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorBankAccountLocation());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorType());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorAddress());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorAddress1());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorCity());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorState());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorZipCode());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getVendorCountry());
        stringBuffer.append("','");
        stringBuffer.append(Constants.DISCRIMINATOR_VALUE_VENDOR);
        stringBuffer.append("')");
        return stringBuffer.toString();

    }

    public String getAEmployeeSQL(CSVTransaction csvTransaction) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("('");
        stringBuffer.append(csvTransaction.getEmployeeName());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeTaxEinNo());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeBankAccountNo());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeBankAccountLocation());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeType());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeAddress());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeAddress1());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeCity());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeState());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeZipCode());
        stringBuffer.append("','");
        stringBuffer.append(csvTransaction.getEmployeeCountry());
        stringBuffer.append("','");
        stringBuffer.append(Constants.DISCRIMINATOR_VALUE_EMPLOYEE);
        stringBuffer.append("')");
        return stringBuffer.toString();

    }

    public String getTansactionSQL(CSVTransaction csvTransaction, long customerId, long vendorId, long employeeId, int jobId) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer transactionSB = new StringBuffer();

        transactionSB.append("('");
        transactionSB.append(csvTransaction.getTransactionType());
        transactionSB.append("','");
        transactionSB.append(csvTransaction.getTransactionModule());
        transactionSB.append("','");
        transactionSB.append(fmt.format(csvTransaction.getTransactionDate()));
        transactionSB.append("','");
        transactionSB.append(csvTransaction.getTransactionCreatedBy());
        transactionSB.append("','");
        transactionSB.append(fmt.format(csvTransaction.getTransactionDate()));
        transactionSB.append("',");
        transactionSB.append(csvTransaction.getTransactionAmount());
        transactionSB.append(",'");
        transactionSB.append(csvTransaction.getApproverName());
        transactionSB.append("','");
        transactionSB.append(Utils.formattingDateInDb(csvTransaction.getApproveDate()));
        transactionSB.append("',");
        transactionSB.append(0);
        transactionSB.append(",");
        transactionSB.append(customerId > 0 ? customerId : null);
        transactionSB.append(",");
        transactionSB.append(vendorId > 0 ? vendorId: null);
        transactionSB.append(",");
        transactionSB.append(employeeId > 0 ? employeeId : null);
        transactionSB.append(",");
        transactionSB.append(jobId);
        transactionSB.append(")");
        return transactionSB.toString();

    }
    public User getUserByUserName(String userName){
       return adminDao.getUserByUserName(userName);
    }

    public PolicyAndProcedure getPolicyAndProcedure(User user,long policyId){
        return adminDao.getPolicyAndProcedure(user,policyId);
    }

    public RealTimeTransaction getRealTimeTransactionByTrxIdAndRtProjectId(long trxId,long rtProjectId){
        return adminDao.getRealTimeTransactionByTrxIdAndRtProjectId(trxId,rtProjectId);
    }
    public List<EmployeeMasterLedger> getAllEmployeeMasterLedger(){
        return adminDao.getAllEmployeeMasterLedger();
    }
}
