package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.RealTimeMonitoringIntervalSetUpValidation;
import com.dsinv.abac.validation.RealTimeProjectValidation;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dsinv.abac.util.Utils.*;

@Controller
@SessionAttributes({"NewRiskAssessmentTxBean","realTimeInterval"})
public class RealTimeMonitoringController {

    private static Logger logger = Logger.getLogger(RealTimeMonitoringController.class);
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private RealTimeMonitoringIntervalSetUpValidation realTimeMonitoringIntervalSetUpValidation;
    @Autowired(required = true)
    private RealTimeProjectValidation realTimeProjectValidation;
    @Autowired(required = true)
    private RuleController ruleController;
    /**
     * Get existing real time interval
     * @param request
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/admin/realTimeMonitoringIntervalSetup.html", method = RequestMethod.GET)
    public String realTimeMonitoringIntervalSetup(HttpServletRequest request, Model model, Map map) {
        logger.debug("---------- i am in controller------------");
        RealTimeInterval realTimeInterval = null;
        List<AbstractBaseEntity> list = adminService.getAnyEntityList("RealTimeInterval");
        if (list != null) {
            realTimeInterval = list.size() > 0 ? (RealTimeInterval) list.get(0) : new RealTimeInterval();
        } else {
            realTimeInterval = new RealTimeInterval();
        }
        adminService.addNode("Real Time Monitoring Interval Setup", 50, request);
        model.addAttribute("realTimeInterval", realTimeInterval);
        return "common/realTimeMonitoringIntervalSetup";
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.MONTH_DAY_YEAR);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, false));
    }
    /**
     * save or update real time interval
     * @param realTimeInterval
     * @param request
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/realTimeMonitoringIntervalSetup.html", method = RequestMethod.POST)
    public String realTimeMonitoringIntervalSetupPost(RealTimeInterval realTimeInterval, HttpServletRequest request,
                                                      BindingResult result, Map model) {
        String fileName = "";
        String filePlacedPath;
        File filePlacedDir;
        String uploadPath = "import";
        String documents = "Documents";
        String downloads = "download";
        realTimeMonitoringIntervalSetUpValidation.validate(realTimeInterval, result);
        logger.debug("Realtime Monitoring interval post start.....");
        if (result.hasErrors()) {
            model.put("realTimeInterval", realTimeInterval);
            return "common/realTimeMonitoringIntervalSetup";
        } else {
            try {
                MultipartFile importFile = realTimeInterval.getImportFile();
                if(importFile != null) {
                    fileName = importFile.getOriginalFilename();
                    filePlacedPath = Constants.DOWNLOAD_PATH   + fileName;
                    filePlacedDir = new File( Constants.DOWNLOAD_PATH );
                    logger.debug("user.home : " + filePlacedPath);
                    if(!filePlacedDir.exists()) {
                        filePlacedDir.mkdirs();
                    } else {
                        logger.debug("File path exist.");
                    }
                    if(!Utils.isEmpty(fileName)) {
                        importFile.transferTo(new File(filePlacedPath));
                    } else {
                        logger.debug("No file name found.");
                    }
                } else {
                    logger.debug("No file for import.");
                }
                realTimeInterval.setLastProjectCreated(Utils.startOfDate(realTimeInterval.getLastProjectCreated()));
                adminService.saveOrUpdate(realTimeInterval);

            } catch (Exception ex) {
                logger.debug("Error while saving/updating user :: " + ex);
            }
            setGreenMessage(request, "Real Time Monitoring Interval Updated Successfully.");
            return "common/realTimeMonitoringIntervalSetup";
        }
    }

    @RequestMapping(value = "/admin/createRealTimeProject.html", method = RequestMethod.GET)
    public String goRealtimeMonitoringWorkFlow(HttpServletRequest request, Model model) {
        logger.debug("Create Real time project start.");
        RealTimeInterval realTimeInterval = new RealTimeInterval();
        List list = new ArrayList();
        Date toDate = new Date();
        int dayInterval = 0;
        try {
            list = adminService.getAnyEntityList("RealTimeInterval");
            if (list != null && list.size() > 0) {
                realTimeInterval = (RealTimeInterval) list.get(0);
                dayInterval = realTimeInterval.getExecuteTime();
            }
            if (dayInterval >= 0) {
                toDate = Utils.endOfDate(getDateAfterDay(dayInterval, realTimeInterval.getLastProjectCreated()));
                logger.debug("Last project creation date + day Interval :" + toDate);
                if (isBeforeDay(toDate, Calendar.getInstance().getTime()) || isToday(toDate)) {

                    saveRealTimeProject(realTimeInterval.getLastProjectCreated(), toDate);
                    realTimeInterval.setLastProjectCreated(toDate);
                    adminService.saveOrUpdateForAnyObject(realTimeInterval);
                } else {
                    logger.debug("interval date is a future day.");
                }

            } else {
                logger.debug("No day interval found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ERROR: Real time date convert exception " + e);

        }


        return "redirect:../realtime/RealtimeMonitoringWorkflow.html";
    }

   /**
    * Get all real time project 
    * @param request
    * @param model
    * @return
    */
    @RequestMapping(value = "/realtime/RealtimeMonitoringWorkflow.html", method = RequestMethod.GET)
    public String realTimeProjectList(HttpServletRequest request, Model model) {

        request.getSession().removeAttribute("proactiveTransactionId"); //for removing id created in new Risk Assement
        request.getSession().removeAttribute("reactiveTransactionId"); //for removing id created in new Risk Assement
        request.getSession().removeAttribute("serialNo"); //for removing serial
        request.getSession().removeAttribute("hiddenId");
        request.getSession().removeAttribute("pageNo");

        List realTmeProjectList = new ArrayList();
        List  iaAnalystList = null;

        try {
            realTmeProjectList = adminService.getAnyEntityList(Constants.REALTIME_PROJECT);
            logger.debug("Real Time Project List Size : " + realTmeProjectList.size());
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            iaAnalystList = adminJdbcService.getAnalystUserList();
            if(iaAnalystList != null) {
                logger.debug("AMLOG:: analyst user: " + iaAnalystList.size());
            }
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);
            model.addAttribute("realTmeProjectList", realTmeProjectList);
            model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
            model.addAttribute("loggedUser", getLoggedUserName()+"");
            model.addAttribute("iaAnalystList", iaAnalystList);
        } catch (Exception ex) {
            logger.debug("CERROR:: Real Time Monitoring Workflow: " + ex);
        }
        adminService.addNode(getMessageBundlePropertyValue("realtime.workFlow"), 2, request);
        model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId", getMessageBundlePropertyValue("subTabId.revAssignment"));
        return "common/RealtimeMonitoringWorkflow";
    }

/**
    * Get all real time project
    * @param request
    * @param model
    * @return
    */
    @RequestMapping(value = "/realtime/myTransactions.html", method = RequestMethod.GET)
    public String myTransactions(HttpServletRequest request, Model model) {
        logger.debug(":: My Transaction Controller ::");
        request.getSession().removeAttribute("proactiveTransactionId"); //for removing id created in new Risk Assement
        request.getSession().removeAttribute("reactiveTransactionId"); //for removing id created in new Risk Assement
        request.getSession().removeAttribute("serialNo"); //for removing serial
        request.getSession().removeAttribute("hiddenId");
        request.getSession().removeAttribute("pageNo");
        try {
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);
            model.addAttribute("loggedUser", getLoggedUserName()+"");
        } catch (Exception ex) {
            logger.debug("CERROR:: My Transaction : " + ex);
        }
        adminService.addNode(getMessageBundlePropertyValue("menu.my.transactions"), 2, request);
        model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId", getMessageBundlePropertyValue("subTabId.revAssignment"));
        return "common/myTransactions";
    }

    /**
     * get transaction with details by realtime project id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/realtime/realTimeSummaryView.html", method = RequestMethod.GET)
    public String realTimeSummaryView(HttpServletRequest request, Model model) {
        logger.debug("Real Time Summary View Start.");
        Map map;
        long transactionId = 0;
        RealTimeProject realTimeProject;
        long realTimeTransactionId = 0;
        String realTimeProjectId = "";
        realTimeProjectId = request.getParameter("rtProjectId");
        request.getSession().removeAttribute("ruleId");
        List trxSummaryList = new ArrayList();
        List realTimeTransactionList = null;
        List employeeList = null;
                String pageNo = request.getSession().getAttribute("pageNo")!= null? (String)request.getSession().getAttribute("pageNo"): "";
        String controlId = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "0";
        if(Utils.isEmpty(pageNo)){
            pageNo="1";
        }
        long urlParam = !isEmpty(request.getParameter("param")) ? Long.parseLong(request.getParameter("param")) : 0;
        try {
           
            if (!isEmpty(realTimeProjectId)) {
                // successfully get realtime project id with url parameter
                realTimeProject = (RealTimeProject) adminService.loadEntityById(Long.parseLong(realTimeProjectId), Constants.REALTIME_PROJECT);
            } else {
                return "redirect:./weightedScreen.html";
            }
            if(!isEmpty(realTimeProject.getAssignTo())) {
                User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            	if(getLoggedUserName().equals(realTimeProject.getAssignTo())
                        || Role.IA_MANAGER.getLabel().equals(user.getRole())
                        || Role.LEGAL.getLabel().equals(user.getRole())
                        || Role.ADMIN.getLabel().equals(user.getRole())) {


                    realTimeTransactionList = adminJdbcService.getRealTimeTransactionList(realTimeProject);

                    RealTimeTransactionCND realTimeTransactionCND = realTimeTransactionId > 0 ? adminService.getRealTimeTransactionCNDByRtTransactionid(realTimeTransactionId) : new RealTimeTransactionCND();
                    map = realTimeTransactionList != null && realTimeTransactionList.size() > 0 ? (Map) realTimeTransactionList.get(0) : new HashMap();
                    if (getRealTimeTransactionIdFromSession(request) == null) { 			// no realtime project id on session
                        setRealTimeTransactionIdOnSession(request, map.get("id") != null ? (Long) map.get("id") : new Long(0));
                    }
                    NewRiskAssessmentTxBean newRiskAssessmentTxBean = new NewRiskAssessmentTxBean();
                    newRiskAssessmentTxBean.setRealTimeTransactionCND(realTimeTransactionCND);
                    realTimeTransactionId = getRealTimeTransactionIdFromSession(request);
                    RealTimeTransaction realTimeTransaction = realTimeTransactionId > 0 ? adminService.getRealTimeTransactionById(realTimeTransactionId) : new RealTimeTransaction();
                    transactionId = realTimeTransaction != null ? realTimeTransaction.getTransaction().getId() : 0;
                    newRiskAssessmentTxBean.setProactiveTxList(realTimeTransactionList);
                    if (ProactiveController.getSerialNoFromSession(request) == null) {       // no serial no on session
                        ProactiveController.setSerialNoOnSession(request, "tr:first");
                    }
                    trxSummaryList = adminJdbcService.getTransactionSummaryList(realTimeProject.getId());
                    newRiskAssessmentTxBean.setTrxSummaryList(trxSummaryList);
                    newRiskAssessmentTxBean.setRealTimeProjectId(realTimeProject.getId());


                    employeeList = adminJdbcService.getAllEmployee();
                    if(employeeList != null) {
                        logger.debug("AMLOG:: Employee List size: " + employeeList.size());
                    }  else {
                        logger.debug("AMLOG:: Employee List: " + employeeList);
                        employeeList = new ArrayList();
                    }
                    newRiskAssessmentTxBean.setEmployeeList(employeeList);

                    ObjectMapper mapper = new ObjectMapper();
                    String userJasonObject = mapper.writeValueAsString(user);
                    model.addAttribute("user", userJasonObject);
                    model.addAttribute("NewRiskAssessmentTxBean", newRiskAssessmentTxBean);
                    model.addAttribute("reactiveProjectId", "0");
                    model.addAttribute("proactiveProjectId", "0");
                    model.addAttribute("realTimeProjectId", realTimeProjectId);
                    model.addAttribute("transactionId", transactionId);
                    model.addAttribute("ctrlId", controlId);
                    model.addAttribute("subHeader", "1");
                    model.addAttribute("myRealTimeSummaryView", "0");
                    model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
                    model.addAttribute("serialNoForTableRowSelection",ProactiveController.getSerialNoFromSession(request));
                    model.addAttribute("maxFileUploadSize", getApplicationPropertyValue("file.mazFileUploadSize"));
                    model.addAttribute("projectStatus", realTimeProject.getStatus());
                    model.addAttribute("urlParam",urlParam);
                    model.addAttribute("pageNo", pageNo);
                    adminService.addNode(getMessageBundlePropertyValue("realTime.summary.view"), 8, request);
            	} else {
                    return "redirect:../realtime/RealtimeMonitoringWorkflow.html";
                }
            }else {
            	return "redirect:../realtime/RealtimeMonitoringWorkflow.html";
            }
            
        } catch (Exception ex) {
            logger.debug("CERROR:: Real Time Summary View: " + ex);
            logger.debug("CERROR:: Error Description: " + ex.getMessage());

        }
        if(controlId.equals("0")) {
            model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
            model.addAttribute("subTabId", getMessageBundlePropertyValue("subTabId.trxMonitoring"));
        } else {
            model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.icga"));

        }

        logger.debug("Real Time Summary View end.");
        return "common/realTimeProjectTransactionList";
    }

 /**
     * get transaction with details by realtime project id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/realtime/myRealTimeSummaryView.html", method = RequestMethod.GET)
    public String myRealTimeSummaryView(HttpServletRequest request, Model model) {
        logger.debug("My Real Time Summary View Start.");
        Map map;
        long transactionId = 0;
        RealTimeProject realTimeProject;
        long realTimeTransactionId = 0;
        String realTimeProjectId = "";
        realTimeProjectId = request.getParameter("rtProjectId");
        request.getSession().removeAttribute("ruleId");
        List trxSummaryList = new ArrayList();
        List employeeList = null;
                String pageNo = request.getSession().getAttribute("pageNo")!= null? (String)request.getSession().getAttribute("pageNo"): "";
        String controlId = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "0";
        if(Utils.isEmpty(pageNo)){
            pageNo="1";
        }
        long urlParam = !isEmpty(request.getParameter("param")) ? Long.parseLong(request.getParameter("param")) : 0;
        try {

            if (!isEmpty(realTimeProjectId)) {
                // successfully get realtime project id with url parameter
                realTimeProject = (RealTimeProject) adminService.loadEntityById(Long.parseLong(realTimeProjectId), Constants.REALTIME_PROJECT);
            } else {
                return "redirect:./weightedScreen.html";
            }

            		List realTimeTransactionList = adminJdbcService.getRealTimeTransactionList(realTimeProject);
                    RealTimeTransactionCND realTimeTransactionCND = realTimeTransactionId > 0 ? adminService.getRealTimeTransactionCNDByRtTransactionid(realTimeTransactionId) : new RealTimeTransactionCND();
                    map = realTimeTransactionList != null && realTimeTransactionList.size() > 0 ? (Map) realTimeTransactionList.get(0) : new HashMap();
                    if (getRealTimeTransactionIdFromSession(request) == null) { 			// no realtime project id on session
                        setRealTimeTransactionIdOnSession(request, map.get("id") != null ? (Long) map.get("id") : new Long(0));
                    }
                    NewRiskAssessmentTxBean newRiskAssessmentTxBean = new NewRiskAssessmentTxBean();
                    newRiskAssessmentTxBean.setRealTimeTransactionCND(realTimeTransactionCND);
                    realTimeTransactionId = getRealTimeTransactionIdFromSession(request);
                    RealTimeTransaction realTimeTransaction = realTimeTransactionId > 0 ? adminService.getRealTimeTransactionById(realTimeTransactionId) : new RealTimeTransaction();
                    transactionId = realTimeTransaction != null ? realTimeTransaction.getTransaction().getId() : 0;
                    newRiskAssessmentTxBean.setProactiveTxList(realTimeTransactionList);
                    if (ProactiveController.getSerialNoFromSession(request) == null) {       // no serial no on session
                        ProactiveController.setSerialNoOnSession(request, "tr:first");
                    }
                    trxSummaryList = adminJdbcService.getTransactionSummaryList(realTimeProject.getId());
                    newRiskAssessmentTxBean.setTrxSummaryList(trxSummaryList);
                    newRiskAssessmentTxBean.setRealTimeProjectId(realTimeProject.getId());

                    User user = adminService.getUserByUserName(Utils.getLoggedUserName());
                    ObjectMapper mapper = new ObjectMapper();
                    String userJasonObject = mapper.writeValueAsString(user);
                    model.addAttribute("user", userJasonObject);
                    model.addAttribute("NewRiskAssessmentTxBean", newRiskAssessmentTxBean);
                    model.addAttribute("reactiveProjectId", "0");
                    model.addAttribute("proactiveProjectId", "0");
                    model.addAttribute("realTimeProjectId", realTimeProjectId);
                    model.addAttribute("transactionId", transactionId);
                    model.addAttribute("myRealTimeSummaryView", "1");
                    model.addAttribute("ctrlId", controlId);
                    model.addAttribute("subHeader", "1");
                    model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
                    model.addAttribute("serialNoForTableRowSelection",ProactiveController.getSerialNoFromSession(request));
                    model.addAttribute("maxFileUploadSize", getApplicationPropertyValue("file.mazFileUploadSize"));
                    model.addAttribute("projectStatus", realTimeProject.getStatus());
                    model.addAttribute("urlParam",urlParam);
                    model.addAttribute("pageNo", pageNo);
                    adminService.addNode(getMessageBundlePropertyValue("realTime.summary.view"), 8, request);

        } catch (Exception ex) {
            logger.debug("CERROR:: My Real Time Summary View: " + ex);
            logger.debug("CERROR:: Error Description: " + ex.getMessage());

        }
        if(controlId.equals("0")) {
            model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
            model.addAttribute("subTabId", getMessageBundlePropertyValue("subTabId.trxMonitoring"));
        } else {
            model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.icga"));

        }

        logger.debug(" My Real Time Summary View end.");
        return "common/realTimeProjectTransactionList";
    }

    /**
     * create real time project between from and to date 
     * @param fromDate
     * @param toDate
     */
    public void saveRealTimeProject(Date fromDate, Date toDate) {
        logger.debug("Save Realtime project method started... :");
        List suspiciousTrxList = new ArrayList();
        List list = new ArrayList();
        int assignmentSize;
        try {
            assignmentSize = adminService.getAssignmentSize();
            logger.debug("RealTime Assignment size :" + assignmentSize);
            suspiciousTrxList = adminJdbcService.getSuspiciousTrxList(new ArrayList(), fromDate, toDate);
            if(suspiciousTrxList != null && suspiciousTrxList.size() > 0) {
                RealTimeTransaction realTimeTransaction;
                RealTimeProject realTimeProject = null;
                for (int i = 0; i < suspiciousTrxList.size(); i++) {            // save each suspicious transaction to realtime transaction with real time project
                    if((i +  assignmentSize) % assignmentSize == 0 ) {
                        realTimeProject = new RealTimeProject();
                        realTimeProject.setProjectName((dateToStrWithFormat(toDate, Constants.MONTH_DAY_YEAR)).replaceAll("/", "") + "RT" + (i +  assignmentSize) / assignmentSize);
                        realTimeProject.setStatus("");
                        if(suspiciousTrxList.size() - (i +  assignmentSize) >= 0 ) {
                            realTimeProject.setNoOfTransaction(assignmentSize);
                        } else {
                            realTimeProject.setNoOfTransaction(suspiciousTrxList.size() - i );
                        }
                        adminService.save(realTimeProject);
                        logger.debug("Saved realtime project id: " + realTimeProject.getId());
                    }
                    Map map = (Map) suspiciousTrxList.get(i);
                    long trxId = (Long) map.get("transactionId");
                    Transaction transaction = new Transaction();
                    transaction.setId(trxId);
                    realTimeTransaction = new RealTimeTransaction();
                    realTimeTransaction.setTransaction(transaction);
                    realTimeTransaction.setRealTimeProject(realTimeProject);
                    adminService.save(realTimeTransaction);
                }

            }  else {
                logger.debug("Suspicious transaction list empty ");
            }
        }catch (Exception ex) {
            logger.debug("ERROR: Real Time Project Save : " + ex);
        }
    }


    /**
 *   save transaction comments, control and CND with real time project
 * @param request
 * @param newRiskAssessmentTxBean
 * @param result
 * @param model
 * @return
 * @throws IOException
 */
    @RequestMapping(value = "/realtime/realTimeSummaryView.html", method = RequestMethod.POST)
    public String realTimeSummaryViewPost(HttpServletRequest request, @ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, BindingResult result,
                                          Model model) throws IOException {
        logger.debug("::RealTimeSummaryView  POST ::");
        long realTimeProjectId = newRiskAssessmentTxBean != null ? newRiskAssessmentTxBean.getRealTimeProjectId() : 0;

        String uploadPath = "uploadFiles";
        String auditTrialMessageForCND = "";
        String auditTrialMessageForComment = "";
        String controlIds = "";
        String decissionTaken = "";
        String tType = "";
        String message="";
        List<String> auditTrialMessageList = new ArrayList<String>();
        MultipartFile multipartFileForSupportingDocument = newRiskAssessmentTxBean.getFileDataForSupportingDocument();
        String uploadPathForSupportingDocument = "supportingDocument";
        String orginalNameForSupportingDocument = multipartFileForSupportingDocument != null ? multipartFileForSupportingDocument.getOriginalFilename(): "";
        String filePathForSupportingDocument = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument + File.separator + "" + getTodaysDate().getTime() + "_" + orginalNameForSupportingDocument);
        String myRealTimeSummaryView = request.getParameter("myRealTimeSummaryView") != null ? request.getParameter("myRealTimeSummaryView") : "0";
        File dirForSupportingDocument = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument);
        String fileIconPathForSupportingDocument = "";

        String controlIdFromUrlParam = !isEmpty(request.getParameter("ctrlId")) ? request.getParameter("ctrlId") :"0";
        long urlParam=0;
        File policyFolder;
        String policyPath = System.getProperty("user.home") + File.separator + uploadPath + File.separator + Constants.TRANSACTION_POLICY_PATH;
        String orginalPolicyPath;
        User user = adminService.getUserByUserName(Utils.getLoggedUserName());
        String employsJason = request.getParameter("employeeJason");
        RealTimeTransactionComment realTimeTransactionComment = null;
        try {
            urlParam = !isEmpty(request.getParameter("param")) ? Long.parseLong(request.getParameter("param")) : 0;
            if (!dirForSupportingDocument.exists()) {
                dirForSupportingDocument.mkdirs();
            }
            fileIconPathForSupportingDocument = multipartFileForSupportingDocument!= null? ProactiveController.getFileIconPath(multipartFileForSupportingDocument): "";

            policyFolder = new File(policyPath);
            if(!policyFolder.exists()) {
                policyFolder.mkdirs();
            }

            //-------- control+decission(Transaction Details + Control tab)  part handling --------//

            RealTimeTransactionCND realTimeTransactionCND = new RealTimeTransactionCND();
            RealTimeTransaction realTimeTransaction = new RealTimeTransaction();
            if (newRiskAssessmentTxBean != null) {
                realTimeTransactionCND = newRiskAssessmentTxBean.getRealTimeTransactionCND() != null ? newRiskAssessmentTxBean.getRealTimeTransactionCND() : new RealTimeTransactionCND();
                realTimeTransaction = newRiskAssessmentTxBean.getRealTimeTransaction() != null ? newRiskAssessmentTxBean.getRealTimeTransaction() : new RealTimeTransaction();
                if(newRiskAssessmentTxBean.getTransactionPolicy() != null ) {
                    if(!Utils.isEmpty(newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename())){
                    orginalPolicyPath = policyPath + File.separator + "" + getTodaysDate().getTime() + "_" + newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename();
                    newRiskAssessmentTxBean.getTransactionPolicy().transferTo(new File(orginalPolicyPath));

                    TransactionPolicy aPolicy = new TransactionPolicy();
                    aPolicy .setUploadedBy(getLoggedUserName());
                    aPolicy .setUploaded(getTodaysDate());
                    aPolicy .setComment(newRiskAssessmentTxBean.getPolicyComment());
                    aPolicy .setFileIconLocation(ProactiveController.getFileIconPath(newRiskAssessmentTxBean.getTransactionPolicy()));
                    aPolicy .setFileName(newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename());
                    aPolicy .setFileLocation(orginalPolicyPath);
                    aPolicy .setTransaction(newRiskAssessmentTxBean.getRealTimeTransaction().getTransaction());
                    adminService.save(aPolicy);
                    auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document")
                            + " '"+ newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename()+"' "
                            + Utils.getMessageBundlePropertyValue("auditTrial.policy.upload"));
                    adminService.saveRealTimeTrxAuditTrial(realTimeTransaction,auditTrialMessageList);
                    auditTrialMessageList = new ArrayList<String>();
                }
            }
            }
            tType = realTimeTransaction != null ? realTimeTransaction.getTransaction().getTransactionType() : "";
            List<Control> controlList = adminService.getControlListByTransactionType(tType);
            int index = 0;
            for (Control control : controlList) {
                String checkboxStatus = request.getParameter("contId"+index);
                controlIds = controlIds  + "," + control.getId() + ":" + checkboxStatus.charAt(checkboxStatus.length()-1);
                String previousCheckboxStatus = request.getParameter("old_"+control.getId() + "");
                message = message + RealTimeMonitoringController.getAuditTrialMsgForControlAction(control,previousCheckboxStatus,checkboxStatus.charAt(checkboxStatus.length()-1)+"");
                index++;
            }
            auditTrialMessageList.add(message);
            controlIds = isEmpty(controlIds) ? "" : controlIds.substring(1, controlIds.length());

            decissionTaken = request.getParameter("radioDecissionArea") != null ? request.getParameter("radioDecissionArea") : null;
            if(!Utils.isEmpty(decissionTaken)) {
                setReviewed(realTimeTransaction, user);
            }
            realTimeTransactionCND.setControlIds(controlIds);
            realTimeTransactionCND.setDecision(decissionTaken);

            if (newRiskAssessmentTxBean != null) {
                realTimeTransactionCND.setRealTimeTransaction(newRiskAssessmentTxBean.getRealTimeTransaction());
                realTimeTransactionCND.setControlPersonInvolve(newRiskAssessmentTxBean.getControlPersonInvolve());
                if(!isEmpty(newRiskAssessmentTxBean.getDecisionComment())) {
                    realTimeTransactionComment = new RealTimeTransactionComment();
                    realTimeTransactionComment.setCommenterName(user.getUserName());
                    realTimeTransactionComment.setCommentDate(Utils.getTodaysDate());
                    realTimeTransactionComment.setComment(newRiskAssessmentTxBean.getDecisionComment());
                     realTimeTransactionComment.setRealTimeTransaction(realTimeTransaction);
                    adminService.saveOrUpdateForAnyObject(realTimeTransactionComment);
                }

                realTimeTransactionCND.setDecisionEmailAddress(newRiskAssessmentTxBean.getDecisionEmailAddress());
                realTimeTransactionCND.setControlComment(newRiskAssessmentTxBean.getControlComment());
                String prevControlComment = newRiskAssessmentTxBean.getPreviousControlComment();
                String currentControlComment = newRiskAssessmentTxBean.getControlComment();
                String prevDecisionComment = newRiskAssessmentTxBean.getPreviousDecissionComment();
                String currentDecisionComment = newRiskAssessmentTxBean.getDecisionComment();
                auditTrialMessageList.add(RealTimeMonitoringController.getControlCommentAction(prevDecisionComment,currentDecisionComment,"decision"));
                auditTrialMessageList.add(RealTimeMonitoringController.getControlCommentAction(prevControlComment,currentControlComment,"control"));
            }
            if (!getMessageBundlePropertyValue("newRiskAssessmentSummary.additionalInformationRequired").equals(decissionTaken)) {
                realTimeTransactionCND.setDecisionEmailAddress(Constants.EMPTY_STRING);
            }
            if (newRiskAssessmentTxBean != null) {
                if (newRiskAssessmentTxBean.getRealTimeTransaction() != null)
                    if (newRiskAssessmentTxBean.getRealTimeTransaction().getId() > 0) {
                        adminService.saveOrUpdateForAnyObject(realTimeTransactionCND);
                    }
            }
                if(!isEmpty(decissionTaken)){
                    ProactiveController.setSerialNoOnSession(request, "tr:first");
                    if(isEmpty(newRiskAssessmentTxBean.getPreviousDecission()))
                    auditTrialMessageList.add(getMessageBundlePropertyValue("checked.decision") + " '" + decissionTaken +"'.");
                    else if(!isEmpty(newRiskAssessmentTxBean.getPreviousDecission()) && !newRiskAssessmentTxBean.getPreviousDecission().equals(decissionTaken))
                    auditTrialMessageList.add(getMessageBundlePropertyValue("unChecked.decision")+" '"+ newRiskAssessmentTxBean.getPreviousDecission()
                          + "' and " + getMessageBundlePropertyValue("checked.decision") + " '" + decissionTaken +"'.");
                }

            String prevControlIds = newRiskAssessmentTxBean.getPreviousControlsIds() != null ? newRiskAssessmentTxBean.getPreviousControlsIds() : "";
            adminService.saveRealTimeTrxAuditTrial(newRiskAssessmentTxBean.getRealTimeTransaction(),auditTrialMessageList);
            auditTrialMessageList = new ArrayList<String>();

            if (multipartFileForSupportingDocument!= null) {
                logger.debug(" : file upload portion : ");
                RealTimeTransactionSupportingDocument realTimeTransactionSupportingDocument = new RealTimeTransactionSupportingDocument();
                try {
                    if (!isEmpty(orginalNameForSupportingDocument))
                        multipartFileForSupportingDocument.transferTo(new File(filePathForSupportingDocument));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                realTimeTransactionSupportingDocument.setAuthor(getLoggedUserName());
                realTimeTransactionSupportingDocument.setEntryTime(getTodaysDate());
                realTimeTransactionSupportingDocument.setFileName((orginalNameForSupportingDocument));
                realTimeTransactionSupportingDocument.setFileIconLocation(isEmpty(orginalNameForSupportingDocument) ? "" : fileIconPathForSupportingDocument);
                realTimeTransactionSupportingDocument.setFileLocation(filePathForSupportingDocument);
                realTimeTransactionSupportingDocument.setRealTimeTransaction(newRiskAssessmentTxBean.getRealTimeTransaction());
                realTimeTransactionSupportingDocument.setComment(newRiskAssessmentTxBean.getSupportingDocumentComment());
                if (!isEmpty(orginalNameForSupportingDocument)){
                    adminService.save(realTimeTransactionSupportingDocument);
                    auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document")
                            + " '"+ orginalNameForSupportingDocument+"' "
                            + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.supportingDoc"));
                    adminService.saveRealTimeTrxAuditTrial(realTimeTransaction,auditTrialMessageList);
                }

            }
            if(!Utils.isEmpty(employsJason) && !employsJason.equals("[]")) {
                adminJdbcService.saveIASql(getEmployeesSqlList(employsJason, realTimeTransaction.getId()), realTimeTransaction.getId());
            }

        } catch (Exception ex) {
            logger.debug("CERROR:: RealTime Summary View Exception " + ex);
        }

        if("1".equals(myRealTimeSummaryView))  {
            return "redirect:./myRealTimeSummaryView.html?rtProjectId=" + realTimeProjectId+"&param=" + urlParam+(!"0".equals(controlIdFromUrlParam)? "&controlIds="+controlIdFromUrlParam:"");
        }
        else  {
            return "redirect:./realTimeSummaryView.html?rtProjectId=" + realTimeProjectId+"&param=" + urlParam+(!"0".equals(controlIdFromUrlParam)? "&controlIds="+controlIdFromUrlParam:"");
        }
    }

    private void setReviewed(RealTimeTransaction realTimeTransaction, User user) {
        if(Role.ADMIN.getLabel().equals(user.getRole()))   {
             realTimeTransaction.setAdminReviewed(true);
        } else if(Role.IA_MANAGER.getLabel().equals(user.getRole())){
                realTimeTransaction.setIaManagerReviewed(true);
        } else if(Role.IA_ANALYST.getLabel().equals(user.getRole())){
                realTimeTransaction.setIaAnalystReviewed(true);
        } else if(Role.LEGAL.getLabel().equals(user.getRole())){
                realTimeTransaction.setLegalReviewed(true);
        }

    }

    public StringBuffer getEmployeesSqlList(String employee, long projecTransactionId) {
        StringBuffer internalAudits = new StringBuffer();
        internalAudits.append("INSERT INTO internal_audit(employee_FK, project_transaction_id) VALUES ") ;
        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(employee);
            JSONArray jsonArray = (JSONArray) object;
            JSONObject employeeObj= null;
            long empId;
            for(int i = 0 ; i < jsonArray.size(); i++){
                employeeObj = (JSONObject)((JSONArray) object).get(i);
                String empIdString = (String) employeeObj.get("id");
                empId = !Utils.isEmpty(empIdString) ? Long.parseLong(empIdString) : 0;
                internalAudits.append(getIntAuditSql(empId, projecTransactionId));
                internalAudits.append(",");
            }
            internalAudits.deleteCharAt(internalAudits.length() - 1);
            internalAudits.append(";");
        } catch (Exception ex) {
            logger.debug("CERROR:: Internal Audit Exception: " + ex.getMessage()  );
        }
        return internalAudits;
    }

    public String getIntAuditSql(long id, long projecTransactionId) {
        return  " (" + id + ", " + projecTransactionId + ")";
    }
    @RequestMapping(value = "/realtime/getAssignmentReview.html", method = RequestMethod.GET)
    public String getAssignmentReview(@RequestParam("rtProjectId") long realTimeProjectId , HttpServletRequest request, Model model) {
        logger.info("Assignment Review Controller Start.");
        String loggedUserName = getLoggedUserName();
        String analyst = request.getParameter("analyst") != null ? request.getParameter("analyst") : null;
        try{
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            if (realTimeProjectId > 0) {
                RealTimeProject realTimeProject = (RealTimeProject)adminService.loadEntityById(realTimeProjectId, Constants.REALTIME_PROJECT);
                if(!isEmpty(realTimeProject.getAssignTo())) {							// when project is assigned
                    if(realTimeProject.getAssignTo().equals(loggedUserName)
                            || Role.IA_MANAGER.getLabel().equals(user.getRole())
                            || Role.LEGAL.getLabel().equals(user.getRole())
                            || Role.ADMIN.getLabel().equals(user.getRole())) {
                        return "redirect:../realtime/realTimeSummaryView.html?rtProjectId=" + realTimeProjectId;
                    }else {
                        return "redirect:../realtime/RealtimeMonitoringWorkflow.html" ;
                    }
                } else {
                    String flag = request.getParameter("flag");
                    if("true".equals(flag)) {
                        if("null".equals(analyst)) {
                            realTimeProject.setAssignTo(loggedUserName);
                        } else {
                            realTimeProject.setAssignTo(analyst);
                        }
                        realTimeProject.setStatus(getMessageBundlePropertyValue("assigned"));
                        adminService.saveOrUpdateForAnyObject(realTimeProject);
                        logger.debug("Real time project successfully assigned");
                        return "redirect:../realtime/realTimeSummaryView.html?rtProjectId=" + realTimeProjectId;
                    } else {
                        return "redirect:../realtime/RealtimeMonitoringWorkflow.html" ;
                    }
                }
            } else {
                return "redirect:../realtime/RealtimeMonitoringWorkflow.html" ;
            }
        }catch(Exception ex) {
            logger.error("CERROR: Assignment Review Controller : " + ex);
        }
        logger.info("Assignment Review Controller End.");
        return "redirect:./RealtimeMonitoringWorkflow.html" ;
    }


    @RequestMapping(value = "/realtime/doCompleteAssignment.html", method = RequestMethod.GET)

    public @ResponseBody List doComplete(@RequestParam("rtProjectId") long realTimeProjectId , HttpServletRequest request) {
        logger.debug("Ajax Complete assignment Controller ");
        List<Long>  inCodedRealTimeTxList = new ArrayList<Long>();
        try {
            if (realTimeProjectId > 0) {
                inCodedRealTimeTxList = adminJdbcService.isAllRealTimeTransactionCoded(realTimeProjectId);
                if(inCodedRealTimeTxList == null) {
                    RealTimeProject realTimeProject = (RealTimeProject)adminService.loadEntityById(realTimeProjectId, Constants.REALTIME_PROJECT);
                    realTimeProject.setStatus(getMessageBundlePropertyValue("completed"));
                    adminService.saveOrUpdate(realTimeProject);
                    logger.debug("In coded real time transaction list size : ");
                    logger.debug("Assignment is completed successfully : " );
                } else {
                    logger.debug("In coded realtime transaction list size: " + inCodedRealTimeTxList.size());
                }

            }else {
                 logger.debug("Real Time Project Id Is Not Valid : " + realTimeProjectId);
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Complete assignment Controller : " + ex);
        }

        return  inCodedRealTimeTxList;
    }

    @RequestMapping(value = "/realtime/doUnassignmentProject.html", method = RequestMethod.GET)
    public @ResponseBody RealTimeProject doUnassignmentProject(@RequestParam("rtProjectId") long realTimeProjectId, HttpServletRequest request) {
        logger.debug("Ajax Un assignment Controller ");
        RealTimeProject realTimeProject = new RealTimeProject();
        try {
            if (realTimeProjectId > 0) {
                realTimeProject = (RealTimeProject) adminService.loadEntityById(realTimeProjectId, Constants.REALTIME_PROJECT);
                realTimeProject.setStatus("");
                realTimeProject.setAssignTo("");
                adminService.saveOrUpdate(realTimeProject);
                logger.debug("Assignment is completed successfully");

            } else {
                logger.debug("CERROR: Un assignment project id not valid.");
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Un assignment Controller : " + ex);
        }
        return realTimeProject;
    }


    public static Long getRealTimeTransactionIdFromSession(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("realTimeTransactionId");
    }
    public static Long getProactiveTransactionIdFromSession(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("proactiveTransactionId");
    }

    public static void setRealTimeTransactionIdOnSession(HttpServletRequest request, Long reactiveTransactionId) {
        request.getSession().setAttribute("realTimeTransactionId", reactiveTransactionId);
    }
    public static void setProactiveTransactionIdOnSession(HttpServletRequest request, Long proactiveTransactionId) {
        request.getSession().setAttribute("proactiveTransactionId", proactiveTransactionId);
    }

    public static String getControlStatus(String statusCode){
         if("1".equals(statusCode))
             return getMessageBundlePropertyValue("yes");
         else if("2".equals(statusCode))
            return getMessageBundlePropertyValue("no");
         else if("3".equals(statusCode))
            return getMessageBundlePropertyValue("n.a");
         else if("4".equals(statusCode))
            return getMessageBundlePropertyValue("see.comment");
         else
             return "";
    }

    public static String getAuditTrialMsgForControlAction(Control control,String prevStatus, String currentStatus){
        String message="";
        if("0".equals(prevStatus) && !"0".equals(currentStatus)){ // added a new control action
            message = getMessageBundlePropertyValue("auditTrial.add.control")+" "
                    + control.getId() +" "
                    + getMessageBundlePropertyValue("auditTrial.add.selectAction")+" '"
                    + RealTimeMonitoringController.getControlStatus(currentStatus)+"'.\n";
        }else if(!"0".equals(prevStatus) && "0".equals(currentStatus)){ // remove a control action
            message = getMessageBundlePropertyValue("auditTrial.add.control")+" "
                    + control.getId() +" "
                    + getMessageBundlePropertyValue("auditTrial.add.selectAction")+" '"
                    + RealTimeMonitoringController.getControlStatus(prevStatus)+"' "
                    + getMessageBundlePropertyValue("auditTrial.remove")+".\n";
        }else if(!"0".equals(prevStatus) && !"0".equals(currentStatus) && !prevStatus.equals(currentStatus)){  // changed a control action
            message = getMessageBundlePropertyValue("auditTrial.add.control")+" "
                    + control.getId()+" "
                    + getMessageBundlePropertyValue("auditTrial.control.changedAction")+" '"
                    + RealTimeMonitoringController.getControlStatus(prevStatus)+"' to '"
                    + RealTimeMonitoringController.getControlStatus(currentStatus)+"'.\n";
        }
    return message;
    }

    public static String getControlCommentAction(String prevControlComment,String currentControlComment,String ControlOrDecisionComment){
        String message = "";
        if((prevControlComment == null || isEmpty(prevControlComment)) && !isEmpty(currentControlComment)) {
            message = getMessageBundlePropertyValue("auditTrial." + ControlOrDecisionComment + ".comment")
                    +" '"+currentControlComment+"' "+ getMessageBundlePropertyValue("auditTrial.control.comment.added");
        }else if(!isEmpty(prevControlComment) && !isEmpty(currentControlComment) && !prevControlComment.equals(currentControlComment)){
            message = getMessageBundlePropertyValue("auditTrial." + ControlOrDecisionComment + ".comment")
                    +" '"+prevControlComment+"' "+ getMessageBundlePropertyValue("auditTrial.control.comment.changed")
                    +" '"+ currentControlComment+"'.";

        }else if(!isEmpty(prevControlComment) && isEmpty(currentControlComment.trim())){
            message = getMessageBundlePropertyValue("auditTrial." + ControlOrDecisionComment + ".comment")
                    +" '"+prevControlComment+"' "+ getMessageBundlePropertyValue("auditTrial.remove")+".";

        }
        return message;
    }

    @RequestMapping(value = "/realtime/assignmentList.html", method = RequestMethod.POST)
    public @ResponseBody
    JasonBean getAssignmentList(HttpServletRequest request) {
        logger.info("Assignment list controller start.");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "projectName";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        JasonBean jasonData = new JasonBean();
        int totalRealTimeProject = 0;
        List realTmeProjectList = new ArrayList();
        List dbColumnHeaderList = new ArrayList();
        List<Cell> entry = new ArrayList<Cell>();

         try {
            totalRealTimeProject = adminJdbcService.getTotalRealTimeProject(qtype, query);
            realTmeProjectList = adminJdbcService.getRealTimeTrxPartialDataList(Utils.parseInteger(page), "max".equals(rp) ? totalRealTimeProject : Utils.parseInteger(rp), qtype, query, getColumnNameWithProperty(sortname), sortorder);

             if("max".equals(rp)){
                 TransactionSearchController.setTotalListSizeAndListInSession(totalRealTimeProject,realTmeProjectList,request);
                 jasonData.setTotal(totalRealTimeProject);
             }else{
             if(realTmeProjectList != null) {
                logger.debug("Real time Project List Size : " + realTmeProjectList.size());
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : realTmeProjectList) {
                    RealTimeProject realTimeProject = new RealTimeProject();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    realTimeProject.setProjectName(map.get("project_name") != null ? map.get("project_name").toString() : "");
                    realTimeProject.setStatus(map.get("status") != null ? map.get("status").toString() : "");
                    realTimeProject.setAssignTo(map.get("assign_to") != null ? map.get("assign_to").toString() : "");
//                    realTimeProject.setNoOfTransaction(map.get("no_of_transaction") instanceof Number ? ((Number) map.get("no_of_transaction")).intValue() : 0);
                    realTimeProject.setNoOfTransaction(map.get("NoOfTransaction") instanceof Number ? ((Number) map.get("NoOfTransaction")).intValue() : 0);
                    realTimeProject.setOutstandingTransactions(map.get("outstanding_transaction") instanceof Number ? ((Number) map.get("outstanding_transaction")).intValue() : 0);
                    realTimeProject.setReview(map.get("reviewed") instanceof Number ? ((Number) map.get("reviewed")).intValue() : 0);
                    realTimeProject.setId(map.get("id") instanceof Number ? ((Number) map.get("id")).intValue() : 0);
                    cell.setId(map.get("id") instanceof Number ? ((Number)map.get("id")).intValue() : 0 );
                    realTimeProject.setUnAssignLabel(Utils.getMessageBundlePropertyValue("realtime.project.un.assign.label"));
                    cell.setCell(realTimeProject);
                    entry.add(cell);
                }
                 Map mapForHeader = new HashMap();
                 mapForHeader.put("projectName","project_name"); // key=flexigrid parameter name, value = dbField Name
                 mapForHeader.put("assignTo","assign_to");
                 mapForHeader.put("status","status");
                 mapForHeader.put("noOfTransaction","no_of_transaction");
                 mapForHeader.put("review","review");
                 mapForHeader.put("outstandingTransactions","outstanding_transaction");
                 dbColumnHeaderList.add(mapForHeader);
                 request.getSession().setAttribute(tableName,dbColumnHeaderList);


                jasonData.setRows(entry);
                jasonData.setTotal(totalRealTimeProject);
                jasonData.setDbColumnHeader(dbColumnHeaderList);
            }
             }
        } catch (Exception ex) {
            logger.error("CERROR: Real Time Project exception : " + ex);
        }

        logger.info("Assignment list controller end.");
        return jasonData;
    }

    @RequestMapping(value = "/realtime/myAssignmentList.html", method = RequestMethod.POST)
    public @ResponseBody
    JasonBean getMyAssignmentList(HttpServletRequest request) {
        logger.info("My Transaction List Controller Start.");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "projectName";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        JasonBean jasonData = new JasonBean();
        int totalRealTimeProject = 0;
        List realTmeProjectList = new ArrayList();
        List dbColumnHeaderList = new ArrayList();
        List<Cell> entry = new ArrayList<Cell>();

         try {
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            totalRealTimeProject = adminJdbcService.getTotalMyTrx(qtype, query,user);
            realTmeProjectList = adminJdbcService.getMyTrxPartialDataList(Utils.parseInteger(page), "max".equals(rp) ? totalRealTimeProject : Utils.parseInteger(rp), qtype, query, getColumnNameWithProperty(sortname), sortorder,user);

             if("max".equals(rp)){
                 TransactionSearchController.setTotalListSizeAndListInSession(totalRealTimeProject,realTmeProjectList,request);
                 jasonData.setTotal(totalRealTimeProject);
             }else{
             if(realTmeProjectList != null) {
                logger.debug("My Real time Project List Size : " + realTmeProjectList.size());
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : realTmeProjectList) {
                    RealTimeProject realTimeProject = new RealTimeProject();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    realTimeProject.setProjectName(map.get("project_name") != null ? map.get("project_name").toString() : "");
                    realTimeProject.setStatus(map.get("status") != null ? map.get("status").toString() : "");
                    realTimeProject.setNoOfTransaction(map.get("no_of_transaction") instanceof Number ? ((Number)map.get("no_of_transaction")).intValue() : 0 );
                    realTimeProject.setReview(map.get("review") instanceof Number ? ((Number)map.get("review")).intValue() : 0 );
                    realTimeProject.setId(map.get("id") instanceof Number ? ((Number)map.get("id")).intValue() : 0  );
                    cell.setId(map.get("id") instanceof Number ? ((Number)map.get("id")).intValue() : 0 );
                    cell.setCell(realTimeProject);
                    entry.add(cell);
                }
                 Map mapForHeader = new HashMap();
                 mapForHeader.put("projectName","project_name"); // key=flexigrid parameter name, value = dbField Name
                 mapForHeader.put("status","status");
                 mapForHeader.put("noOfTransaction","no_of_transaction");
                 mapForHeader.put("review","review");
                 dbColumnHeaderList.add(mapForHeader);
                 request.getSession().setAttribute(tableName,dbColumnHeaderList);


                jasonData.setRows(entry);
                jasonData.setTotal(totalRealTimeProject);
                jasonData.setDbColumnHeader(dbColumnHeaderList);
            }
             }
        } catch (Exception ex) {
            logger.error("CERROR: Real Time Project exception : " + ex);
        }

        logger.info("My Transaction List Controller End.");
        return jasonData;
    }


    public String getColumnNameWithProperty(String property) {
        String field = null;
        if(!Utils.isEmpty(property)) {
            if((property.trim()).equals(Constants.REALTIME_PROJECT_NAME)) {
                return Constants.REALTIME_PROJECT_NAME_COLUMN;
            } else if((property.trim()).equals(Constants.REALTIME_STATUS_COLUMN)) {
               return Constants.REALTIME_STATUS_COLUMN;
            }else if((property.trim()).equals(Constants.REALTIME_ASSIGNMENT_SIZE)) {
                return Constants.REALTIME_ASSIGNMENT_SIZE_COLUMN;
            }else if((property.trim()).equals(Constants.REALTIME_ASSIGNMENT_REVIEW_COLUMN)) {
                return Constants.REALTIME_ASSIGNMENT_REVIEW_COLUMN;
            }else if((property.trim()).equals(Constants.REALTIME_ASSIGNTO)) {
                return Constants.REALTIME_ASSIGNTO_COLUMN;
            }

        } else {
            logger.debug("Invalid project field ");
        }
        return field;
    }

   @RequestMapping(value = "/realtime/trxListInDetails.html", method = RequestMethod.POST)
   public @ResponseBody
   JasonBean getTrxListInDetails(HttpServletRequest request) {
       logger.debug("::Assignment List Controller ");
       String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
       String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
       String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "trxId";
       String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "asc";
       String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
       String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

       String proactiveProjectId = request.getParameter("proactiveProjectId") != null ? request.getParameter("proactiveProjectId") : "";
       String reactiveProjectId = request.getParameter("reactiveProjectId") != null ? request.getParameter("reactiveProjectId") : "";
       String realTimeProjectId = request.getParameter("realTimeProjectId") != null ? request.getParameter("realTimeProjectId") : "";
       String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
       String ruleId = request.getParameter("ruleId") != null ? request.getParameter("ruleId") : "";
       String totalCountFromFrontEnd = request.getParameter("tCount") != null ? request.getParameter("tCount") : "";
       String controlIds = request.getParameter("ctrlId") != null ? request.getParameter("ctrlId") : "0";

       String myRealTimeSummaryView = request.getParameter("myRealTimeSummaryView") != null ? request.getParameter("myRealTimeSummaryView") : "0";
       List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
       sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;
       User user = adminService.getUserByUserName(Utils.getLoggedUserName());

       if(!Utils.isEmpty(ruleId))
           request.getSession().setAttribute("ruleId",ruleId);
       else
           ruleId = (String)request.getSession().getAttribute("ruleId");

       if(!Utils.isEmpty(proactiveProjectId ))
       request.getSession().setAttribute("proactiveProjectId",proactiveProjectId);
       if(!Utils.isEmpty(reactiveProjectId ))
       request.getSession().setAttribute("reactiveProjectId",reactiveProjectId);
       if(!Utils.isEmpty(realTimeProjectId))
       request.getSession().setAttribute("realTimeProjectId",realTimeProjectId);

       if(Utils.isEmpty(proactiveProjectId ))
           proactiveProjectId = (String)request.getSession().getAttribute("proactiveProjectId");
       if(Utils.isEmpty(reactiveProjectId ))
           reactiveProjectId = (String)request.getSession().getAttribute("reactiveProjectId");
       if(Utils.isEmpty(realTimeProjectId ))
           realTimeProjectId = (String)request.getSession().getAttribute("realTimeProjectId");

//       logger.debug("SMN: realTimeProjectId="+realTimeProjectId);
       String controlCompareString = "";
       JasonBean jasonData = new JasonBean();
       int totalCount = 0;
       List trxSummaryList = new ArrayList();
       List dbColumnHeaderList = new ArrayList();
       List<Cell> entry = new ArrayList<Cell>();
       String compareControlSql = "";
       if(!"0".equals(controlIds)){
           compareControlSql = InternalControlGapAnalysisController.getStringForComparingControls(controlIds);
       }

       try {
           if(!Utils.isEmpty(proactiveProjectId) && !"0".equals(proactiveProjectId)){
               totalCount = 0;
               if(!Utils.isEmpty(totalCountFromFrontEnd))
                   totalCount = !Utils.isEmpty(totalCountFromFrontEnd)? Integer.parseInt(totalCountFromFrontEnd):0;
               else
                   totalCount =  adminJdbcService.getProactiveTransactionCount(!Utils.isEmpty(proactiveProjectId)? Long.parseLong(proactiveProjectId):0 ,compareControlSql,controlIds);
               ProactiveController.setSerialNoOnSession(request,"tr:first" );
//               logger.debug("SMN LOG:  total count="+totalCount);
               //logger.debug("SMN: COUNT="+totalCount+" Rule ID="+ruleId);
               trxSummaryList = adminJdbcService.getProactiveTransactionPartialDataList(!Utils.isEmpty(proactiveProjectId)? Long.parseLong(proactiveProjectId):0 ,compareControlSql,controlIds,
                       Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query,
                       sortname, sortorder,!Utils.isEmpty(ruleId)? Long.parseLong(ruleId): 0);

               if(trxSummaryList != null)
                   logger.debug("SMN LOG:  trxSummaryList="+trxSummaryList.size());

           }else if(!Utils.isEmpty(reactiveProjectId) && !"0".equals(reactiveProjectId)){
//               logger.debug("SMN LOG:  reactiveProjectId="+reactiveProjectId);
               totalCount = 0;
               if(!Utils.isEmpty(totalCountFromFrontEnd))
                   totalCount = !Utils.isEmpty(totalCountFromFrontEnd)? Integer.parseInt(totalCountFromFrontEnd):0;
               else
                   totalCount =  adminJdbcService.getReactiveTransactionCount(!Utils.isEmpty(reactiveProjectId)? Long.parseLong(reactiveProjectId):0 ,compareControlSql,controlIds,
                           qtype, query,sortname, sortorder,!Utils.isEmpty(ruleId)? Long.parseLong(ruleId): 0);
               ProactiveController.setSerialNoOnSession(request,"tr:first" );
               logger.debug("SMN LOG:  total count="+totalCount);
               //logger.debug("SMN: COUNT="+totalCount+" Rule ID="+ruleId);
               trxSummaryList = adminJdbcService.getReactiveTransactionPartialDataList(!Utils.isEmpty(reactiveProjectId)? Long.parseLong(reactiveProjectId):0 ,compareControlSql,controlIds,
                       Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query,
                       sortname, sortorder,!Utils.isEmpty(ruleId)? Long.parseLong(ruleId): 0);

           }else if (!isEmpty(realTimeProjectId) && !"0".equals(realTimeProjectId)) {// successfully get realtime project id with url parameter
               logger.info("Realtime project block for trx list ");
               if(!Utils.isEmpty(totalCountFromFrontEnd)){
                   totalCount = !Utils.isEmpty(totalCountFromFrontEnd)? Integer.parseInt(totalCountFromFrontEnd):0;
               }
               else{
                   ProactiveController.setSerialNoOnSession(request,"tr:first" );
               }

               if("1".equals(myRealTimeSummaryView)){
                   logger.info("My transaction summary view.");
                   totalCount =  adminJdbcService.getMyRealTimeTransactionCount(controlIds,compareControlSql,Long.parseLong(realTimeProjectId),
                           qtype, query,!Utils.isEmpty(ruleId)? Long.parseLong(ruleId): 0,user);

                   trxSummaryList = adminJdbcService.getMyRealTimeTransactionPartialDataList(controlIds,compareControlSql,Long.parseLong(realTimeProjectId),
                           Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query,
                           sortname, sortorder,!Utils.isEmpty(ruleId)? Long.parseLong(ruleId): 0,user);
               }else{
                   logger.info("Review assignment summary view.");
                   if(Role.IA_MANAGER.getLabel().equals(user.getRole())) {
                       sortname = Constants.IA_MANAGER_REVIEWED;
                       controlCompareString = Constants.IA_MANAGER_COMPARE;
                       compareControlSql=compareControlSql + " " + Constants.IA_MANAGER_COMPARE;
                   } else if(Role.LEGAL.getLabel().equals(user.getRole())) {
                       sortname = Constants.LEGAL_REVIEWED;
                       controlCompareString = Constants.LEGAL_COMPARE;
                       compareControlSql=compareControlSql + " " + Constants.LEGAL_COMPARE;
                   }  else if(Role.IA_ANALYST.getLabel().equals(user.getRole())) {
                       sortname = Constants.IA_ANALYST_REVIEWED;
                       controlCompareString = "";
                   } else if(Role.ADMIN.getLabel().equals(user.getRole())) {
                       sortname = Constants.ADMIN_REVIEWED;
                       controlCompareString = "";
                   }
                   logger.debug("ControlCompareString:"+compareControlSql);
                   sortname = " reviewed ";
                   totalCount =  adminJdbcService.getRealTimeTransactionCount("1", compareControlSql, Long.parseLong(realTimeProjectId),
                           Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query,
                           sortname, sortorder,!Utils.isEmpty(ruleId)? Long.parseLong(ruleId): 0);

                   trxSummaryList = adminJdbcService.getRealTimeTransactionPartialDataList("1", compareControlSql, Long.parseLong(realTimeProjectId),
                           Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query,
                           sortname, sortorder,!Utils.isEmpty(ruleId)? Long.parseLong(ruleId): 0);
               }
         }

        if("max".equals(rp)){
               TransactionSearchController.setTotalListSizeAndListInSession(totalCount,trxSummaryList,request);
               jasonData.setTotal(totalCount);
           }else{
               if(trxSummaryList != null) {
                   logger.debug("Transaction list size: " + trxSummaryList.size());
                   jasonData.setPage(Utils.parseInteger(page));
                   for(Object obj : trxSummaryList) {
                       RealTimeTransaction realTimeTransaction= new RealTimeTransaction();
                       Cell cell = new Cell();
                       Map map = (Map) obj;
                       Date trxDate = map.get("trxDate") != null ? (Date)map.get("trxDate") : new Date();
                       Date createdDate = map.get("createdDate") != null ? (Date)map.get("createdDate") : new Date();
                       realTimeTransaction.setTrxId(map.get("trxId") != null ? map.get("trxId").toString() : "");
                       realTimeTransaction.setDateStr(Utils.dateToStrWithFormat(trxDate, Constants.MONTH_DAY_YEAR));
                       realTimeTransaction.setDateOfApprover(Utils.dateToStrWithFormat(createdDate, Constants.MONTH_DAY_YEAR));
                       //realTimeTransaction.setAmountStr(map.get("amount") instanceof Number ? ((Number)map.get("amount")).doubleValue() : 0 );
                       realTimeTransaction.setAmountStr("$"+(map.get("amount") != null ? map.get("amount").toString() : ""));
                       realTimeTransaction.setDocCreator("John Smith");
                       realTimeTransaction.setApprover(map.get("approver") != null ? map.get("approver").toString() : "");
                       realTimeTransaction.setRuleViolated(map.get("rule_code") != null ? map.get("rule_code").toString() : "");
                       realTimeTransaction.setRuleTitle(map.get("rule_title") != null ? map.get("rule_title").toString() : "");
                       realTimeTransaction.setRuleExplanation(map.get("rule_explanation") != null ? map.get("rule_explanation").toString() : "");

                       realTimeTransaction.setReviewed(map.get("reviewed") != null ? map.get("reviewed").toString() : "");
                       realTimeTransaction.setProjectId(map.get("projectId") != null ? map.get("projectId").toString() : "");
                       realTimeTransaction.setProjectType(map.get("project") != null ? map.get("project").toString() : "");

                       realTimeTransaction.setTransactionId(map.get("id") != null ? map.get("id").toString() : "");
                       cell.setId(map.get("trxId") instanceof Number ? ((Number)map.get("trxId")).intValue() : 0 );
                       cell.setCell(realTimeTransaction);
                       entry.add(cell);
                   }
                   Map mapForHeader = new HashMap();
                   mapForHeader.put("trxId","trxId"); // key=flexigrid parameter name, value = dbField Name
                   mapForHeader.put("dateStr","trxDate");
                   mapForHeader.put("amountStr","amount");
                   mapForHeader.put("docCreator","docCreator");
                   mapForHeader.put("approver","approver");
                   mapForHeader.put("dateOfApprover","createdDate");
                   mapForHeader.put("ruleViolated","rule_code");
                   mapForHeader.put("ruleExplanation","rule_explanation");
                   dbColumnHeaderList.add(mapForHeader);
                   request.getSession().setAttribute(tableName,dbColumnHeaderList);
//                   logger.debug("SMN: dbColumnHeaderList="+dbColumnHeaderList);

                   jasonData.setRows(entry);
                   jasonData.setTotal(totalCount);
                   jasonData.setDbColumnHeader(dbColumnHeaderList);
               }
           }
       } catch (Exception ex) {
           logger.debug("CERROR: Real Time Project exception : " + ex);
       }


       return jasonData;
   }
    @RequestMapping(value = "/realtime/trxCountListInDetails.html", method = RequestMethod.POST)
    public @ResponseBody
    JasonBean getTrxCountListInDetails(HttpServletRequest request) {
        logger.info("Transaction Count List Controller Start.");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "projectName";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String proactiveProjectId = request.getParameter("proactiveProjectId") != null ? request.getParameter("proactiveProjectId") : "";
        String reactiveProjectId = request.getParameter("reactiveProjectId") != null ? request.getParameter("reactiveProjectId") : "";
        String realTimeProjectId = request.getParameter("realTimeProjectId") != null ? request.getParameter("realTimeProjectId") : "";
        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        String controlIds = request.getParameter("ctrlId") != null ? request.getParameter("ctrlId") : "";

        String myRealTimeSummaryView = request.getParameter("myRealTimeSummaryView") != null ? request.getParameter("myRealTimeSummaryView") : "0";

        List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
        sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;
        User user = adminService.getUserByUserName(Utils.getLoggedUserName());

        if(!Utils.isEmpty(proactiveProjectId ))
            request.getSession().setAttribute("proactiveProjectId",proactiveProjectId);
        if(!Utils.isEmpty(reactiveProjectId ))
            request.getSession().setAttribute("reactiveProjectId",reactiveProjectId);
        if(!Utils.isEmpty(realTimeProjectId))
            request.getSession().setAttribute("realTimeProjectId",realTimeProjectId);

        if(Utils.isEmpty(proactiveProjectId ))
            proactiveProjectId = (String)request.getSession().getAttribute("proactiveProjectId");
        if(Utils.isEmpty(reactiveProjectId ))
            reactiveProjectId = (String)request.getSession().getAttribute("reactiveProjectId");
        if(Utils.isEmpty(realTimeProjectId ))
            realTimeProjectId = (String)request.getSession().getAttribute("realTimeProjectId");

        String compareControlSql = InternalControlGapAnalysisController.getStringForComparingControls(controlIds);
        JasonBean jasonData = new JasonBean();
        int totalCount = 0;
        List trxSummaryList = new ArrayList();
        List dbColumnHeaderList = new ArrayList();
        List<Cell> entry = new ArrayList<Cell>();

        try {

            if(!isEmpty(proactiveProjectId) && !"0".equals(proactiveProjectId)){
                totalCount = adminJdbcService.getProactiveSummaryCount(!Utils.isEmpty(proactiveProjectId)? Long.parseLong(proactiveProjectId):0 ,compareControlSql,controlIds);
                trxSummaryList = adminJdbcService.getProactiveSummaryPartialDataList(!Utils.isEmpty(proactiveProjectId)? Long.parseLong(proactiveProjectId):0 ,compareControlSql,controlIds,
                        Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query, sortname, sortorder);
            }else if(!isEmpty(reactiveProjectId) && !"0".equals(reactiveProjectId)){
                totalCount = adminJdbcService.getReactiveSummaryCount(!Utils.isEmpty(reactiveProjectId)? Long.parseLong(reactiveProjectId):0 ,compareControlSql,controlIds);
                trxSummaryList = adminJdbcService.getReactiveSummaryPartialDataList(!Utils.isEmpty(reactiveProjectId)? Long.parseLong(reactiveProjectId):0 ,compareControlSql,controlIds,
                        Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query, sortname, sortorder);
            }else if (!isEmpty(realTimeProjectId) && !"0".equals(realTimeProjectId)) {
                if("1".equals(myRealTimeSummaryView)){
                    totalCount =  adminJdbcService.getMyRealTimeSummaryCount(controlIds,compareControlSql,Long.parseLong(realTimeProjectId),user);
                    trxSummaryList = adminJdbcService.getMyRealTimeSummaryPartialDataList(controlIds,compareControlSql,Long.parseLong(realTimeProjectId),
                            Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query, sortname, sortorder,user);
                }else{
                    if(Role.IA_MANAGER.getLabel().equals(user.getRole()) ) {
                        totalCount =  adminJdbcService.getRealTimeSummaryCount("1"," AND rtpCND.decision = 'Further Action Required' ",Long.parseLong(realTimeProjectId));
                        trxSummaryList = adminJdbcService.getRealTimeSummaryPartialDataList("1"," AND rtpCND.decision = 'Further Action Required' ",Long.parseLong(realTimeProjectId),
                                Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query, sortname, sortorder);
                    }else if(Role.LEGAL.getLabel().equals(user.getRole()) ) {
                        totalCount =  adminJdbcService.getRealTimeSummaryCount("1"," AND rtpCND.decision = 'Further Action Required' AND rtt.ia_manager_reviewed = 1 ",Long.parseLong(realTimeProjectId));
                        trxSummaryList = adminJdbcService.getRealTimeSummaryPartialDataList("1"," AND rtpCND.decision = 'Further Action Required' AND rtt.ia_manager_reviewed = 1 ",Long.parseLong(realTimeProjectId),
                                Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query, sortname, sortorder);
                    } else {
                        totalCount =  adminJdbcService.getRealTimeSummaryCount(controlIds,compareControlSql,Long.parseLong(realTimeProjectId));
                        trxSummaryList = adminJdbcService.getRealTimeSummaryPartialDataList(controlIds,compareControlSql,Long.parseLong(realTimeProjectId),
                                Utils.parseInteger(page), "max".equals(rp) ? totalCount : Utils.parseInteger(rp), qtype, query, sortname, sortorder);
                    }

                }


            }

            if("max".equals(rp)){
                TransactionSearchController.setTotalListSizeAndListInSession(totalCount,trxSummaryList,request);
                jasonData.setTotal(totalCount);
            }else{
                if(trxSummaryList != null) {
                    logger.debug("Transaction Summary List Size : " + trxSummaryList.size());
                    jasonData.setPage(Utils.parseInteger(page));
                    for(Object obj : trxSummaryList) {
                        RealTimeTransaction realTimeTransaction= new RealTimeTransaction();
                        Cell cell = new Cell();
                        Map map = (Map) obj;
                        realTimeTransaction.setTrxCount(map.get("totalTransaction") != null ? map.get("totalTransaction").toString() : "");
                        realTimeTransaction.setRule(map.get("rule") != null ? map.get("rule").toString() : "");
                        realTimeTransaction.setRuleIdStr(map.get("ruleId") != null ? map.get("ruleId").toString() : "");

                        cell.setCell(realTimeTransaction);
                        entry.add(cell);
                    }
                    Map mapForHeader = new HashMap();
                    mapForHeader.put("trxCount","totalTransaction"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("rule","rule");
                    mapForHeader.put("ruleIdStr","ruleId");
                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);
                    jasonData.setRows(entry);
                    jasonData.setTotal(totalCount);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);
                }
            }
        } catch (Exception ex) {
            logger.error("Real Time Project exception : " + ex);
        }

        logger.info("Transaction Count List Controller End.");
        return jasonData;
    }
   public static String getDbColumnNameByRef(List list,String ref){
       for(int i=0; i<list.size(); i++){
           Map map = (Map)list.get(i);
           if(map.get(ref)!= null){
               return  (String)map.get(ref);
           }
       }
       return null;
   }

    @RequestMapping(value = "/realtime/groupingColumn.html", method = RequestMethod.GET)
    public String groupingColumn(HttpServletRequest request,Model model) {

        logger.debug("Get Transaction Search list controller");
        String selectedColumnList = request.getParameter("selColList") != null ? request.getParameter("selColList") : null;
        String printColList = request.getParameter("printColList") != null ? request.getParameter("printColList") : null;
        String option = request.getParameter("option") != null ? request.getParameter("option") : null;
        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : null;
        String widthList = request.getParameter("widthList") != null ? request.getParameter("widthList") : null;
        String totalWidth = request.getParameter("totalWidth") != null ? request.getParameter("totalWidth") : null;
        List headerList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);

        logger.debug("printColList : "+printColList);
        logger.debug("tableName : "+tableName);
        logger.debug("totalWidth : "+totalWidth);
        logger.debug("widthList : "+widthList);
        logger.debug("option : "+option);
        logger.debug("Selected Column Header List : "+selectedColumnList);

        int totalItems = 0 ;
        List trxSearchList  = new ArrayList<Control>();
        try {
            trxSearchList = (List)request.getSession().getAttribute("list");
            if(trxSearchList != null) {
                logger.debug("trxSearchList size: "+trxSearchList.size());
                String finalHeaderList = "";
                if(selectedColumnList != null && headerList != null){
                    String arr[] = selectedColumnList.split(",");
                    for(int i=0; i< arr.length; i++){
                        for(int j=0; j<headerList.size(); j++ ){
                            Map map = (Map)headerList.get(j);
                            if(map.get(arr[i]) != null){
                                arr[i]= map.get(arr[i]).toString();
                            }
                        }
                    }

                    for(int i=0; i<arr.length; i++)  {
                        finalHeaderList = finalHeaderList + (!Utils.isEmpty(finalHeaderList) ? "," : "") + arr[i];
                    }
                }
                logger.debug("Final Header List:"+finalHeaderList);
                model.addAttribute("list", trxSearchList);
                model.addAttribute("finalHeaderlist",finalHeaderList);
                model.addAttribute("printColList",printColList);
                model.addAttribute("option",option);
                model.addAttribute("widthList",widthList);
                model.addAttribute("tableName",tableName);
                model.addAttribute("totalWidth",totalWidth);
                model.addAttribute("arrayList", trxSearchList);
            } else {
                logger.debug("No Transaction Search List Found");
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Transaction Search Exception : " + ex);
        }
        return "groupingColumnTable";
    }

    @RequestMapping(value="/admin/LoadJsonDataServlet.html", method = RequestMethod.GET)
    @ResponseBody
    public JqGridData getPeopleAtPostcode(HttpServletRequest request,Model model){
        Data data = new Data();
       // if(postcode == null || postcode.trim().length() == 0) postcode = "BS21 7RH";

        String postcode = "BS21 7RH";
        List<Person> personList = data.getData(postcode);

        logger.debug("person list"+personList.size());

        int totalNumberOfPages = 1;
        int currentPageNumber = 1;
        int totalNumberOfRecords = 8; // All in there are 8 records in our dummy data object

        JqGridData<Person> gridData = new JqGridData<Person>(totalNumberOfPages, currentPageNumber, totalNumberOfRecords, personList);

        return gridData;
    }

    @RequestMapping(value = "/policy/downloadTransactionPolicy.html", method = RequestMethod.GET)
    public void downloadTransactionPolicy(HttpServletRequest request, HttpServletResponse response, @RequestParam("policyId") long policyId) throws IOException {
        logger.debug("Transaction Policy Download start.");
        String fileName = "";
        String filePath = "";
        TransactionPolicy transactionPolicy = null;
        try {
            if (policyId > 0) {
                transactionPolicy  = adminService.loadTransactionPolicy(policyId);
                fileName = transactionPolicy .getFileName();
                filePath = transactionPolicy .getFileLocation();
            } else {
                logger.debug("No Policy Found For Id " + policyId);
            }

            ServletContext context = request.getSession().getServletContext();
            String appPath = context.getRealPath("");
            String fullPath = appPath + filePath;
            File downloadFile = new File(filePath);
            FileInputStream inputStream = new FileInputStream(downloadFile);
            String mimeType = context.getMimeType(fullPath);
            logger.debug(" mimeType :" + mimeType);
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType(mimeType);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[50000000];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outStream.close();
        } catch (FileNotFoundException ex) {
            logger.debug("Exception in FileInputStream: " + ex);
        } catch (Exception ex) {
            logger.debug("CERROR:: Exception in showing Supporting Document: " + ex);
        }
        logger.debug("Transaction Policy Download end.");
    }
}
