package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.Entity;
import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.dao.AdminDao;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dsinv.abac.util.Utils.*;
import static com.dsinv.abac.util.Utils.getLoggedUserName;
import static com.dsinv.abac.util.Utils.getTodaysDate;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/20/13
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@SessionAttributes({"reactiveProject", "NewRiskAssessmentTxBean"})
public class ReactiveProjectController {

    private static Logger logger = Logger.getLogger(ReactiveProjectController.class);
    
    // begin object initialize 
    @Autowired(required = true)
    private AdminDao adminDao;
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private ReactiveProjectValidation reactiveProjectValidation;
    @Autowired(required = true)
    private RuleController ruleController;


    /**
     * This Controller To Create New Or Update Reactive project
     * @param request
     * @param model
     * @return reactive project credentials input form
     */
    @RequestMapping(value = "/reactive/getReactiveProjectForm.html", method = RequestMethod.GET)
    public String addReactiveProjectForm(HttpServletRequest request, @RequestParam("projectId") long projectId, Model model) {

        ReactiveProject  reactiveProject = new ReactiveProject();
        try{
            if (projectId > 0) {   //  existing reactive project of selected id
                reactiveProject = (ReactiveProject) adminService.loadEntityById(projectId, Entity.REACTIVE_PROJECT.getValue());
            } else {  // create new reactive project
                reactiveProject.setRegion(new Region());
                reactiveProject.setPaymentDate(Utils.getTodaysDate());
            }
        } catch (Exception ex) {
            logger.debug("Reactive Project exception :" + ex);
        }
        return "admin/reactiveProject";
    }

    /**
     * To get all country list
     * @return region list
     */
    @ModelAttribute("regionList")
    public List<Region> getRegionList() {
        List<Region> regionList = adminService.getRegionList();
        return regionList;
    }

   /**
    * To initialize simple date format in form   
    * @param binder
    */
    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.MONTH_DAY_YEAR); 
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, false));
    }

    /**
     * POST controller to save or update reactive project
     * @param request
     * @param reactiveProject
     * @param result
     * @return
     */
    @RequestMapping(value = "/reactive/addReactiveProject.html", method = RequestMethod.POST)
    public String processReactiveSubmit(HttpServletRequest request,
                                        @ModelAttribute("reactiveProject") ReactiveProject reactiveProject,
                                        BindingResult result) {
        logger.debug("Reactive Project Save start.");
        long id = 0;
        if (result.hasErrors()) { // validation failed
            logger.debug("Reactive Project Validation failed");
            return "admin/reactiveProject";
        } else {
            try {
                reactiveProjectValidation.validate(reactiveProject, result);
                Region region = (Region) adminService.loadEntityById(reactiveProject.getRegion().getId(), Entity.REGION.getValue());
                reactiveProject.setRegion(region);
                id = reactiveProject.getId();
                adminService.saveOrUpdate(reactiveProject);
                adminJdbcService.importReactiveTransaction();
                if (id == 0) { // successfully project save message
                    Utils.setGreenMessage(request, Utils.getMessageBundlePropertyValue("reactive.project.saved.message"));
                } else { // successfully project update message
                    Utils.setGreenMessage(request, Utils.getMessageBundlePropertyValue("reactive.project.updated.message"));
                }
                logger.debug("Reactive Project Save end.");
            } catch (Exception ex) {
                logger.debug("CERROR::Reactive Project Save Exception: " + ex);
            }

        }

        return "redirect:/admin/reactiveProjectList.html";

    }

    /**
     * Get Method for reactive project list
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/reactive/reactiveProjectList.html", method = RequestMethod.GET)
    public String ReactiveProjectList(HttpServletRequest request,
                                      Model model) {

        List<ReactiveProject> reactiveProjectList = adminService.getReactiveProjectList();
        adminService.addNode("Reactive Project List",50, request);
        model.addAttribute("reactiveProjectList", reactiveProjectList);
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
        model.addAttribute("reactiveProject", new ReactiveProject());
        model.addAttribute("transactionTypeList",TransactionType.getTransactionTypes());
        return "admin/reactiveProjectList";
    }

    /**
     *
     * @param request
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/reactive/reactiveProjectDelete.html", method = RequestMethod.GET)
    public @ResponseBody

    String deleteReactiveProject(HttpServletRequest request,@RequestParam("projectId") long projectId ) {
        try{
            if(projectId > 0) {
                adminJdbcService.deleteReactiveProjectById(projectId);
                Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("reactive.project.delete.message"));
            }  else {
                logger.debug("Project not found");
            }
        } catch (Exception ex) {
            logger.debug("Reactive Project Delete Excepion : " + ex);
        }
        return "";
    }
    
    /**
     * Reactive project list for reactive workflow
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/reactive/ReactiveWorkflow.html", method = RequestMethod.GET)
    public String getReactiveWorkflow(HttpServletRequest request, Model model) {
        logger.debug("Reactive Workflow start.");
        request.getSession().removeAttribute("proactiveTransactionId"); // remove proactive transaction id created in new Risk Assement
        request.getSession().removeAttribute("reactiveTransactionId"); // removing reactive transaction id created in new Risk Assement
        request.getSession().removeAttribute("serialNo"); //removing serial No.
        request.getSession().removeAttribute("hiddenId");
        List reactiveProjectList = adminService.getAnyEntityList(Entity.REACTIVE_PROJECT.getValue());
        model.addAttribute("reactiveProjectList", reactiveProjectList);
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR); // simple date format for form 
        adminService.addNode(Utils.getMessageBundlePropertyValue("landingPage.investigations"),2,request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.investigations"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.investig"));
        logger.debug("Reactive Workflow end.");
        return "common/ReactiveWorkflow";
    }

    @RequestMapping(value = "/reactive/getJASONforReactiveProjectList.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getReactiveProjectList(HttpServletRequest request, Model model) {
        logger.debug("Get control list controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List reactiveProjectList = new ArrayList<Control>();

        try {

            totalItems= adminService.getEntitySize(Constants.REACTIVE_PROJECT);
            reactiveProjectList  = adminJdbcService.getPartialReactiveProjectDataList(Utils.parseInteger(page), Utils.parseInteger(rp));
            if(reactiveProjectList != null) {
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : reactiveProjectList) {
                    ReactiveProject reactiveProject= new ReactiveProject();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    Date paymentDate =  map.get("payment_date") != null ? (Date)map.get("payment_date") : new Date();
                    reactiveProject.setRegionName(map.get("regionName") != null ? map.get("regionName").toString() : "");
                    reactiveProject.setAmount(map.get("amount") != null ? ((Number)map.get("amount")).intValue() : 0 );
                    reactiveProject.setProjectName(map.get("project_name") != null ? map.get("project_name").toString() : "");
                    reactiveProject.setTransactionType(map.get("transaction_type") != null ? map.get("transaction_type").toString() : "");
                    reactiveProject.setPaymentDateStr(Utils.dateToStrWithFormat(paymentDate,Constants.MONTH_DAY_YEAR));
                    reactiveProject.setId(map.get("reactiveProjectId") != null ? ((Number)map.get("reactiveProjectId")).intValue() : 0 );
                    cell.setId(map.get("reactiveProjectId") != null ? ((Number)map.get("reactiveProjectId")).intValue() : 0 );

                    cell.setCell(reactiveProject);
                    entry.add(cell);
                }
                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
            } else {
                logger.debug("No Reactive Project Found");
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Exception : " + ex);
        }

        return jasonData;
    }

 @RequestMapping(value = "/reactive/getJASONforReactiveProjectAdminList.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getReactiveProjectAdminList(HttpServletRequest request, Model model) {
        logger.debug("Reactive Project List (JASON) Start.");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List reactiveProjectList = new ArrayList<Control>();

        try {

            totalItems= adminService.getEntitySize(Constants.REACTIVE_PROJECT);
            reactiveProjectList  = adminJdbcService.getPartialReactiveProjectDataList(Utils.parseInteger(page), Utils.parseInteger(rp));
            if(reactiveProjectList != null) {
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : reactiveProjectList) {
                    ReactiveProject reactiveProject= new ReactiveProject();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    Date paymentDate =  map.get("payment_date") != null ? (Date)map.get("payment_date") : new Date();
                    reactiveProject.setRegionName(map.get("regionName") != null ? map.get("regionName").toString() : "");
                    reactiveProject.setAmount(map.get("amount") != null ? ((Number)map.get("amount")).intValue() : 0 );
                    reactiveProject.setProjectName(map.get("project_name") != null ? map.get("project_name").toString() : "");
                    reactiveProject.setTransactionType(map.get("transaction_type") != null ? map.get("transaction_type").toString() : "");
                    reactiveProject.setPaymentDateStr(Utils.dateToStrWithFormat(paymentDate,Constants.MONTH_DAY_YEAR));
                    reactiveProject.setId(map.get("reactiveProjectId") != null ? ((Number)map.get("reactiveProjectId")).intValue() : 0 );
                    reactiveProject.setReactiveProjectEditHtmlButton(Utils.getMessageBundlePropertyValue("reactive.project.edit.button.html"));
                    reactiveProject.setReactiveProjectDeleteHtmlButton(Utils.getMessageBundlePropertyValue("reactive.project.delete.button.html"));
                    cell.setId(map.get("reactiveProjectId") != null ? ((Number)map.get("reactiveProjectId")).intValue() : 0 );

                    cell.setCell(reactiveProject);
                    entry.add(cell);
                }
                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
            } else {
                logger.debug("No Reactive Project Found");
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Exception : " + ex);
        }
     logger.debug("Reactive Project List (JASON) end.");
        return jasonData;
    }

    @RequestMapping(value = "/reactive/getReactiveProject.html", method = RequestMethod.GET)
    public @ResponseBody
    ReactiveProject addReactiveProject(HttpServletRequest request, @RequestParam("projectId") long projectId, Model model) {

        ReactiveProject  reactiveProject = new ReactiveProject();
        try{
            if (projectId > 0) {   //  existing reactive project of selected id
                reactiveProject = (ReactiveProject) adminService.loadEntityById(projectId, Entity.REACTIVE_PROJECT.getValue());

            } else {  // create new reactive project
                reactiveProject.setRegion(new Region());
                reactiveProject.setPaymentDate(Utils.getTodaysDate());
            }
        } catch (Exception ex) {
            logger.debug("Reactive Project exception :" + ex);
        }

        return reactiveProject;
    }
    /*
  * Method to view  reactive summary view page
  * @param HttpServletRequest request, Model model
  * @return type String
  */
    @RequestMapping(value = "/reactive/reactiveSummaryView.html", method = RequestMethod.GET)
    public String getReactiveSummeryView(HttpServletRequest request, Model model) {
        logger.debug("Reactive Summary view start.");
        long transactionId = 0;
        ReactiveProject reactiveProject;
        long reactiveTransactionId = 0;
        request.getSession().removeAttribute("ruleId");
        String pageNo = request.getSession().getAttribute("pageNo")!= null? (String)request.getSession().getAttribute("pageNo"): "";
        String controlIds = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "0";
        String reactiveProjectId = request.getParameter("reactiveProjectId") != null ? request.getParameter("reactiveProjectId") : "0";
        Map map;
        List trxSummaryList = new ArrayList();
        List employeeList = null;
        if(Utils.isEmpty(pageNo)){
            pageNo="1";
        }
        long urlParam = !isEmpty(request.getParameter("param")) ? Long.parseLong(request.getParameter("param")) : 0;
        try {
            if (!isEmpty(reactiveProjectId)) {
                reactiveProject = (ReactiveProject) adminService.loadEntityById(Long.parseLong(reactiveProjectId), Constants.REACTIVE_PROJECT);
            } else {
                return "redirect:./weightedScreen.html";
            }
            List reactiveTransactionList = adminJdbcService.getReactiveTransactionList(reactiveProject);
            ReactiveTransactionCND reactiveTransactionCND= reactiveTransactionId > 0 ? adminService.getReactiveTransactionCNDByReactiveTransactionid(reactiveTransactionId) : new ReactiveTransactionCND();
            map = reactiveTransactionList != null && reactiveTransactionList.size() > 0 ? (Map) reactiveTransactionList.get(0) : new HashMap();
            NewRiskAssessmentTxBean newRiskAssessmentTxBean = new NewRiskAssessmentTxBean();
            if (getReactiveTransactionIdFromSession(request) == null) { 			// no realtime project id on session
                setReactiveTransactionIdOnSession(request, map.get("id") != null ? (Long) map.get("id") : new Long(0));
            }
            newRiskAssessmentTxBean.setReactiveTransactionCND(reactiveTransactionCND);
            reactiveTransactionId = getReactiveTransactionIdFromSession(request);
            ReactiveTransaction reactiveTransaction = reactiveTransactionId > 0 ? adminService.getReactiveTransactionById(reactiveTransactionId) : new ReactiveTransaction();
            transactionId = reactiveTransaction != null ? reactiveTransaction.getTransaction().getId() : 0;
            newRiskAssessmentTxBean.setProactiveTxList(reactiveTransactionList);
            if (ProactiveController.getSerialNoFromSession(request) == null) {       // no serial no on session
                ProactiveController.setSerialNoOnSession(request, "tr:first");
            }
            trxSummaryList = adminJdbcService.getTransactionSummaryList(reactiveProject.getId());
            newRiskAssessmentTxBean.setTrxSummaryList(trxSummaryList);
            newRiskAssessmentTxBean.setRealTimeProjectId(reactiveProject.getId());

            employeeList = adminJdbcService.getAllEmployee();
            if(employeeList != null) {
                logger.debug("AMLOG:: Employee List size: " + employeeList.size());
            }  else {
                logger.debug("AMLOG:: Employee List: " + employeeList);
                employeeList = new ArrayList();
            }
            newRiskAssessmentTxBean.setEmployeeList(employeeList);

            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);


            model.addAttribute("NewRiskAssessmentTxBean", newRiskAssessmentTxBean);
            model.addAttribute("reactiveProjectId",reactiveProject.getId());
            model.addAttribute("proactiveProjectId","0");
            model.addAttribute("realTimeProjectId", "0");
            model.addAttribute("transactionId", transactionId);
            model.addAttribute("ctrlId", controlIds);
            model.addAttribute("subHeader", "1");
            model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
            model.addAttribute("serialNoForTableRowSelection",ProactiveController.getSerialNoFromSession(request));
            model.addAttribute("maxFileUploadSize", getApplicationPropertyValue("file.mazFileUploadSize"));
            model.addAttribute("urlParam",urlParam);
            model.addAttribute("pageNo", pageNo);
            adminService.addNode(getMessageBundlePropertyValue("reactive.summary.view"), 8, request);
        } catch (Exception ex) {
            logger.debug("CERROR:: Proactive Project Summary View: " + ex);
            logger.debug("CERROR:: Error Description: " + ex.getMessage());

        }
        model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.investigations"));
        logger.debug("Reactive Summary view end.");
        return "common/reactiveTransactionList";
    }
    /*
    * Method to handle Reactive SummaryView Assessmentsummary View
    * @param (HttpServletRequest request, @ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, BindingResult result,Model model
    * @return type String
    */
    @RequestMapping(value = "/reactive/reactiveSummaryView.html", method = RequestMethod.POST)
    public String reactiveSummaryViewPost(HttpServletRequest request, @ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, BindingResult result,
                                          Model model) throws IOException {
        logger.debug("Reactive Summary Save start.");
        String fileIconPath = "";
        String uploadPath = "uploadFiles";
        String auditTrialMessageForCND = "";
        String auditTrialMessageForComment = "";
        String controlIds = "";
        String decissionTaken = "";
        String tType = "";
        String message="";
        long reactiveProjectId = newRiskAssessmentTxBean != null ? newRiskAssessmentTxBean.getReactiveProjectId() : 0;
        List<String> auditTrialMessageList = new ArrayList<String>();
        String controlIdFromUrlParam = !isEmpty(request.getParameter("ctrlId")) ? request.getParameter("ctrlId") :"0";
        String myRealTimeSummaryView = request.getParameter("myRealTimeSummaryView") != null ? request.getParameter("myRealTimeSummaryView") : "0";
        MultipartFile multipartFileForSupportingDocument = newRiskAssessmentTxBean.getFileDataForSupportingDocument();
        String uploadPathForSupportingDocument = "supportingDocument";
        String orginalNameForSupportingDocument = multipartFileForSupportingDocument.getOriginalFilename();
        String filePathForSupportingDocument = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalNameForSupportingDocument);
        File dirForSupportingDocument = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument);
        String fileIconPathForSupportingDocument = "";
        File policyFolder;
        String policyPath = System.getProperty("user.home") + File.separator + uploadPath + File.separator + Constants.TRANSACTION_POLICY_PATH;
        String orginalPolicyPath;
        String employsJason = request.getParameter("employeeJason");
        long urlParam=0;
        ReactiveTransactionComment reactiveTransactionComment = null;
        try {
            urlParam = !isEmpty(request.getParameter("param")) ? Long.parseLong(request.getParameter("param")) : 0;
            if (!dirForSupportingDocument.exists()) {
                dirForSupportingDocument.mkdirs();
            }
            policyFolder = new File(policyPath);
            if(!policyFolder.exists()) {
                policyFolder.mkdirs();
            }
            fileIconPathForSupportingDocument = ProactiveController.getFileIconPath(multipartFileForSupportingDocument);  // set file icon
            long txId = getReactiveTransactionIdFromSession(request);

            ReactiveTransaction reactiveTransaction= new ReactiveTransaction();
            ReactiveTransactionCND reactiveTransactionCND = new ReactiveTransactionCND();
            if (newRiskAssessmentTxBean != null) {
                reactiveTransactionCND = newRiskAssessmentTxBean.getReactiveTransactionCND() != null ? newRiskAssessmentTxBean.getReactiveTransactionCND() : new ReactiveTransactionCND();
                reactiveTransaction = newRiskAssessmentTxBean.getReactiveTransaction() != null ? newRiskAssessmentTxBean.getReactiveTransaction() : new ReactiveTransaction();
                if(!isEmpty(newRiskAssessmentTxBean.getDecisionComment())) {
                    reactiveTransactionComment =  new ReactiveTransactionComment();
                    reactiveTransactionComment.setCommentDate(Utils.getTodaysDate());
                    reactiveTransactionComment.setCommenterName(getLoggedUserName());
                    reactiveTransactionComment.setComment(newRiskAssessmentTxBean.getDecisionComment());
                    reactiveTransactionComment.setReactiveTransaction(reactiveTransaction);
                    adminService.saveOrUpdateForAnyObject(reactiveTransactionComment);
                }

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
                    aPolicy .setTransaction(newRiskAssessmentTxBean.getReactiveTransaction().getTransaction());
                    adminService.save(aPolicy);
                    auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document")
                            + " '"+ newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename()+"' "
                            + Utils.getMessageBundlePropertyValue("auditTrial.policy.upload"));
                    adminService.saveReactiveTrxAuditTrial(reactiveTransaction, auditTrialMessageList);
                    auditTrialMessageList = new ArrayList<String>();
                }
                }
            }
            tType = reactiveTransaction != null ? reactiveTransaction.getTransaction().getTransactionType() : "";
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


            decissionTaken = request.getParameter("radioDecissionArea") != null ? request.getParameter("radioDecissionArea") : "";
            reactiveTransactionCND.setControlIds(controlIds);
            reactiveTransactionCND.setDecision(decissionTaken);
            if (newRiskAssessmentTxBean != null) {
                reactiveTransactionCND.setReactiveTransaction(newRiskAssessmentTxBean.getReactiveTransaction());
                reactiveTransactionCND.setControlPersonInvolve(newRiskAssessmentTxBean.getControlPersonInvolve());
//                reactiveTransactionCND.setDecisionComment(newRiskAssessmentTxBean.getDecisionComment());
                reactiveTransactionCND.setDecisionEmailAddress(newRiskAssessmentTxBean.getDecisionEmailAddress());
                reactiveTransactionCND.setControlComment(newRiskAssessmentTxBean.getControlComment());
                String prevControlComment = newRiskAssessmentTxBean.getPreviousControlComment();
                String currentControlComment = newRiskAssessmentTxBean.getControlComment();
                String prevDecisionComment = newRiskAssessmentTxBean.getPreviousDecissionComment();
                String currentDecisionComment = newRiskAssessmentTxBean.getDecisionComment();
                logger.debug("reactive: previous decision comment="+prevDecisionComment);
                auditTrialMessageList.add(RealTimeMonitoringController.getControlCommentAction(prevDecisionComment,currentDecisionComment,"decision"));
                logger.debug("reactive: previous control comment="+prevControlComment);
                auditTrialMessageList.add(RealTimeMonitoringController.getControlCommentAction(prevControlComment,currentControlComment,"control"));

            }
            if (!Utils.getMessageBundlePropertyValue("newRiskAssessmentSummary.additionalInformationRequired").equals(decissionTaken)) {
                reactiveTransactionCND.setDecisionEmailAddress(Constants.EMPTY_STRING);
            }
            if (newRiskAssessmentTxBean != null) {
                if (newRiskAssessmentTxBean.getReactiveTransaction() != null)
                    if (newRiskAssessmentTxBean.getReactiveTransaction().getId() > 0) {
                        adminService.saveOrUpdateForAnyObject(reactiveTransactionCND);
                    }
            }
            if(!isEmpty(decissionTaken)){
                if(isEmpty(newRiskAssessmentTxBean.getPreviousDecission()))
                    auditTrialMessageList.add(getMessageBundlePropertyValue("checked.decision") + " '" + decissionTaken +"'.");
                else if(!isEmpty(newRiskAssessmentTxBean.getPreviousDecission()) && !newRiskAssessmentTxBean.getPreviousDecission().equals(decissionTaken))
                    auditTrialMessageList.add(getMessageBundlePropertyValue("unChecked.decision")+" '"+ newRiskAssessmentTxBean.getPreviousDecission()
                            + "' and " + getMessageBundlePropertyValue("checked.decision") + " '" + decissionTaken +"'.");
            }

            adminService.saveReactiveTrxAuditTrial(newRiskAssessmentTxBean.getReactiveTransaction(),auditTrialMessageList);
            auditTrialMessageList = new ArrayList<String>();
            //-------- End of Comment(CND Tab) part  --------//
            if (multipartFileForSupportingDocument!= null) {
                ReactiveTransactionSupportingDocument reactiveTransactionSupportingDocument = new ReactiveTransactionSupportingDocument();
                try {
                    if (!Utils.isEmpty(orginalNameForSupportingDocument))
                        multipartFileForSupportingDocument.transferTo(new File(filePathForSupportingDocument));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reactiveTransactionSupportingDocument.setAuthor(Utils.getLoggedUserName());
                reactiveTransactionSupportingDocument.setEntryTime(Utils.getTodaysDate());
                reactiveTransactionSupportingDocument.setFileName((orginalNameForSupportingDocument));
                reactiveTransactionSupportingDocument.setFileIconLocation(Utils.isEmpty(orginalNameForSupportingDocument) ? "" : fileIconPathForSupportingDocument);
                reactiveTransactionSupportingDocument.setFileLocation(filePathForSupportingDocument);
                reactiveTransactionSupportingDocument.setReactiveTransaction(newRiskAssessmentTxBean.getReactiveTransaction());
                reactiveTransactionSupportingDocument.setComment(newRiskAssessmentTxBean.getSupportingDocumentComment());
                if (!isEmpty(orginalNameForSupportingDocument)){
                    adminService.save(reactiveTransactionSupportingDocument);
                    auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document")
                            + " '"+ orginalNameForSupportingDocument+"' "
                            + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.supportingDoc"));
                    adminService.saveReactiveTrxAuditTrial(reactiveTransaction,auditTrialMessageList);
                }
            }
            if(!Utils.isEmpty(employsJason)) {
//                adminJdbcService.saveIASql(getEmployeesSqlList(employsJason, reactiveTransaction.getId()), reactiveTransaction.getId());
            }
//            logger.debug(" ------ reactiveProject ------ " + reactiveProject);
        } catch (Exception ex) {
            logger.debug("CERROR:: Reactive Summary View Exception " + ex);
            logger.debug("CERROR:: Reactive Summary View error description " + ex.getMessage());
        }
        logger.debug("Reactive Summary Save end.");
        /*if("1".equals(myRealTimeSummaryView))
        return "redirect:./myReactiveSummaryView.html?reactiveProjectId=" + reactiveProjectId+"&param=" + urlParam+(!"0".equals(controlIdFromUrlParam)? "&controlIds="+controlIdFromUrlParam:"");
        else*/
        return "redirect:./reactiveSummaryView.html?reactiveProjectId=" + reactiveProjectId+"&param=" + urlParam+(!"0".equals(controlIdFromUrlParam)? "&controlIds="+controlIdFromUrlParam:"");
    }

    public static Long getReactiveTransactionIdFromSession(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("reactiveTransactionId");
    }
    public static void setReactiveTransactionIdOnSession(HttpServletRequest request, Long reactiveTransactionId) {
        request.getSession().setAttribute("reactiveTransactionId", reactiveTransactionId);
    }
    /*
  * Method to get project by id
  * @param String projectId, String entity
  * @return type Object
  */
    private Object getProject(String projectId, String entity) {
        return adminService.loadEntityById(Long.parseLong(projectId), entity);
    }
    /*
   * Method to set Country Name On Session
   * @param HttpServletRequest request, String country
   * @return type void
   */
    private Object getProjectFromSession(HttpServletRequest request, String projectName) {
        return request.getSession().getAttribute(projectName);
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
            logger.debug("AMLOG:: jason array size: " + jsonArray.size());
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

}



