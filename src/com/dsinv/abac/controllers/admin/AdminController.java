package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.Entity;
import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.ControlValidation;
import com.dsinv.abac.validation.HolidayValidation;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static com.dsinv.abac.util.Utils.getMessageBundlePropertyValue;

@Controller
@SessionAttributes({"control", "holiday"})
public class AdminController {

    private static Logger logger = Logger.getLogger(AdminController.class);
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private ControlValidation controlValidation;
    @Autowired(required = true)
    private HolidayValidation holidayValidation;


    /**
     * Get transaction Approval page
     * @param request
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/realtime/transactionApprove.html", method = RequestMethod.GET)
    public String realTimeMonitoringIntervalSetup(HttpServletRequest request, Model model, Map map) {
        logger.debug(" :: transaction Approval Controller::111 22 ");
        TransactionApproval transactionApproval = new TransactionApproval();
        List<TransactionApproval> trxApproveList = new ArrayList();
        try {
            trxApproveList = adminService.getAllTransactionApprovalList();
            transactionApproval.setList(trxApproveList);
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);

        } catch (Exception ex) {
            logger.debug("Transaction Approve Exception: " + ex.getMessage());
        }
        model.addAttribute("transactionApproval",transactionApproval);
        model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.trxApproval"));
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
        adminService.addNode(getMessageBundlePropertyValue("landingPage.transactionApprove"), 2, request);
        return "common/transactionApproval";
    }

    /**
     * Get transaction Approval page
     * @param request
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/realtime/transactionApprove.html", method = RequestMethod.POST)
    public String transactionApprovePost(HttpServletRequest request,@ModelAttribute("transactionApproval") TransactionApproval transactionApproval, BindingResult result, Model model, Map map) {
        try {
            logger.debug("ajax Call For Trx Approval File Upload (POST) : ");
            String fileIconPath = "";
            String uploadPath = "uploadFiles";
            String comment = "";
            List<TransactionApproval> trxApproveList = new ArrayList();
            MultipartFile multipartFileForTrxApproval = transactionApproval.getFileData();

            if (multipartFileForTrxApproval != null) {
                String uploadPathForfileIconPath = Utils.getMessageBundlePropertyValue("uploadFilePathTrxApproval");
                String orginalFileName = multipartFileForTrxApproval.getOriginalFilename();

                String uploadFilePathTrxApproval = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalFileName);
                File dirForTrxApproval = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath);
                String fileIconPathForTrxApproval = "";
                logger.debug("FILE TO SAVE :" + uploadFilePathTrxApproval);
                logger.debug(" COMMENT : " + comment);
                try {
                    if (!dirForTrxApproval.exists()) {  // make directory
                        dirForTrxApproval.mkdirs();
                    }
                    fileIconPathForTrxApproval = ProactiveController.getFileIconPath(multipartFileForTrxApproval);

                    if (!Utils.isEmpty(orginalFileName))
                        multipartFileForTrxApproval.transferTo(new File(uploadFilePathTrxApproval));  // upload file to specific directory
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                logger.debug("-SuccessFul-");
                transactionApproval.setEntryTime(Utils.getTodaysDate());
                transactionApproval.setFileIconLocation(fileIconPathForTrxApproval);
                transactionApproval.setFileName(orginalFileName);
                transactionApproval.setFileLocation(uploadFilePathTrxApproval);
                transactionApproval.setAuthor(Utils.getLoggedUserName());
                adminService.save(transactionApproval);
                Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("trx.approval.save.successfully"));
                transactionApproval = new TransactionApproval();
                logger.debug(" trx approval list : "+trxApproveList);
            }

        } catch (Exception ex) {
            logger.debug("CERROR:: Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:./transactionApprove.html";
    }

    /*
    * Method to upload file by ajax call
    * @param MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model
    * @return type String
    */
    @RequestMapping(value = "/admin/transactionApprovalDelete.html", method = RequestMethod.GET)
    String deleteTransactionApproval(HttpServletRequest request, Model model) {
        logger.debug(" Transaction Approval delete controller: ");
        String trxAppId =  request.getParameter("trxAppId");
        logger.debug(" trxAppId: "+trxAppId);
        long trxApprovalId =  !Utils.isEmpty(trxAppId) ? Long.parseLong(trxAppId): 0 ;

        TransactionApproval transactionApproval = adminService.getTransactionApprovalById(trxApprovalId);
        if( transactionApproval != null){
            File file = new File(transactionApproval.getFileLocation());
            logger.debug("File To Delete : "+transactionApproval.getFileLocation());
            if(file.exists() && file.delete()){
                adminService.delete(transactionApproval);
                Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("trx.approval.delete.successfully"));
            }
        }
        return "redirect:./transactionApprove.html";
        }

         /*
    * Method to upload file by ajax call
    * @param MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model
    * @return type String
    */
    @RequestMapping(value = "/admin/trxApprovalFileUpload.html", method = RequestMethod.POST)
    String ajaxCallForFileUpload(MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model) {
        try {
            logger.debug("ajax Call For Trx Approval File Upload (POST) : ");
            String fileIconPath = "";
            String uploadPath = "uploadFiles";
            String comment = "";
            List<TransactionApproval> trxApproveList = new ArrayList();
            TransactionApproval transactionApproval = new TransactionApproval();
            comment = request.getParameter("commentRegion");
            transactionApproval.setComment(comment);
            Iterator<String> itr = request.getFileNames();
            MultipartFile multipartFileForTrxApproval = request.getFile(itr.next());

            if (multipartFileForTrxApproval != null) {
                String uploadPathForfileIconPath = Utils.getMessageBundlePropertyValue("uploadFilePathTrxApproval");
                String orginalFileName = multipartFileForTrxApproval.getOriginalFilename();

                String uploadFilePathTrxApproval = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalFileName);
                File dirForTrxApproval = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath);
                String fileIconPathForTrxApproval = "";
                logger.debug("FILE TO SAVE :" + uploadFilePathTrxApproval);
                logger.debug(" COMMENT : " + comment);
                try {
                    if (!dirForTrxApproval.exists()) {  // make directory
                        dirForTrxApproval.mkdirs();
                    }
                    fileIconPathForTrxApproval = ProactiveController.getFileIconPath(multipartFileForTrxApproval);

                    if (!Utils.isEmpty(orginalFileName))
                        multipartFileForTrxApproval.transferTo(new File(uploadFilePathTrxApproval));  // upload file to specific directory
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                logger.debug("-SuccessFul-");
                transactionApproval.setEntryTime(Utils.getTodaysDate());
                transactionApproval.setFileIconLocation(fileIconPathForTrxApproval);
                transactionApproval.setFileName(orginalFileName);
                transactionApproval.setFileLocation(uploadFilePathTrxApproval);
                transactionApproval.setAuthor(Utils.getLoggedUserName());
                adminService.save(transactionApproval);
                transactionApproval = new TransactionApproval();
                logger.debug(" trx approval list : "+trxApproveList);
            }

        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:./transactionApprove.html";
    }

    /**
     * Method to get all control
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/controls.html", method = RequestMethod.GET)
    public String getControlListFix(HttpServletRequest request, Model model) {
        logger.debug("Get control list controller");
        List<Control> controlList = new ArrayList<Control>();
        String controlId = request.getParameter("controlId");
        Control control = new Control();

        try {
            controlList = adminService.getAllControl();
            if(controlList != null) {
                logger.debug("Control list size : " + controlList.size());
            } else {
                logger.debug("No Control Found");
            }

            if(!Utils.isEmpty(controlId)) {
                long  id = Long.parseLong(controlId);
                control = (Control)adminService.loadEntityById(id, Constants.CONTROL);
                logger.debug("Control edit : " + control);
                model.addAttribute("control" ,control);
            } else {
                logger.debug("Control add : " + control);
                control = new Control();
                model.addAttribute("control" ,control);
            }

        }catch (Exception ex) {
            logger.debug("CERROR: Get Control List Exception : " + ex);
        }

        model.addAttribute("controlList" ,controlList);

        return "admin/controlList";
    }

    @RequestMapping(value = "/admin/getControlJASON.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getControlList(HttpServletRequest request, Model model) {
        logger.debug("Get control list controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "transaction_type";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List controlList = new ArrayList<Control>();

        try {

            totalItems= adminService.getEntitySize(Constants.CONTROL);
            controlList  = adminJdbcService.getPartialDataList( Utils.parseInteger(page), Utils.parseInteger(rp) , qtype, query, sortname, sortorder, Constants.CONTROL_TABLE);
            if(controlList != null) {
                logger.debug("Control list size : " + controlList.size());
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : controlList) {
                    Control control= new Control();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    control.setName(map.get("name") != null ? map.get("name").toString() : "");
                    control.setTransactionType(map.get("transaction_type") != null ? map.get("transaction_type").toString() : "");
                    if(map.get("active") != null && ("true".equals(map.get("active").toString().trim()))) {
                        control.setActiveCheckBoxHtml(Utils.getMessageBundlePropertyValue("selected.checkbox.html"));
                    } else {
                        control.setActiveCheckBoxHtml(Utils.getMessageBundlePropertyValue("checkbox.html"));
                    }
                    control.setEditButtonHtml(Utils.getMessageBundlePropertyValue("edit.button.html"));
                    control.setDeleteButtonHtml(Utils.getMessageBundlePropertyValue("delete.button.html"));
                    control.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );
                    cell.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );

                    cell.setCell(control);
                    entry.add(cell);
                }
                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
            } else {
                logger.debug("No Control Found");
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Get Control List Exception : " + ex);
        }

        return jasonData;
    }


    /**
     * Mehod to get control info
     * @param request
     * @param controlId
     * @param model
     * @return
     */
    @RequestMapping(value = "admin/addControl.html", method = RequestMethod.GET)
    public String addControl(HttpServletRequest request , @RequestParam("controlId") long  controlId, Model model) {

        String name = request.getParameter("controlName");
        String transactionType = request.getParameter("transactionType");
        boolean isActive = !Utils.isEmpty(request.getParameter("isActive")) ? Boolean.valueOf(request.getParameter("isActive")) : false  ;
        String add = request.getParameter("Add");
        String edit = request.getParameter("Edit");
        Control control = new Control();
        logger.debug("Control Get Method");
        logger.debug("Control id :" + controlId);
        try {

            if(controlId > 0 ) {
                AbstractBaseEntity abstractBaseEntity = adminService.loadEntityById(controlId, Entity.CONTROL.getValue());
                if (abstractBaseEntity != null) {
                    control = (Control) abstractBaseEntity;
                } else {
                    logger.debug("Control not found for id : " + controlId);
                    return "redirect:/admin/controls.html";
                }
            } else {
                control = new Control();
                control.setName(name);
                control.setTransactionType(transactionType);
                control.setActive(isActive);
                adminService.save(control);
                logger.debug(" Control object for save : " + control);
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Add control error : " + ex);
        }
        logger.debug("Edited Control id :" + control.getId());
        model.addAttribute("control" , control);
        model.addAttribute("mainTabId","");
        model.addAttribute("subTabId","");

        return "admin/control";
    }

    /**
     * Transaction type in model
     * @return
     */
    @ModelAttribute("transactionType")
    public List<TransactionType> getRegionList() {
        return TransactionType.getTransactionTypes();
    }

    /**
     * Method to save or update a control object
     * @param request
     * @param control
     * @param result
     * @return
     */
    @RequestMapping(value = "admin/addControl.html", method = RequestMethod.POST)
    public String saveControl(HttpServletRequest request, @ModelAttribute("control") Control control, BindingResult result, @RequestParam("controlId") long controlId) {
        logger.debug("Control Save Post Method ");
        controlValidation.validate(control, result);
        if (result.hasErrors()) { // validation failed
            logger.debug("Control validation failed");
            Utils.setErrorMessage(request,Utils.getMessageBundlePropertyValue("control.validation.error"));
            return  "redirect:/admin/controls.html";
        }
        try {
            if(control != null){
            long id = control.getId();
            logger.debug("Control : " + control);
            adminService.saveOrUpdate(control);
             if(id > 0 )
                Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("control.update.message"));
            else
                Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("control.save.message"));
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Control save exceptions : " + ex);
        }
        return "redirect:/admin/controls.html";
    }


    /**
     * Method to delete a control
     * @param request
     * @param controlId
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/admin/controlDelete.html", method = RequestMethod.GET)
    public String deleteControl(HttpServletRequest request,  @RequestParam("controlId") long controlId,  Model model, Map map) {
        try {
            adminService.deleteEntityById(controlId, Entity.CONTROL.getValue());
            Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("control.delete.message"));
        } catch (Exception ex) {
            logger.debug("CERROR: Control delete exception :" + ex);
        }

        return "redirect:/admin/controls.html";
    }

    /**
     * Upload a policies document
     * @param request
     * @param model
     * @param map
     * @return  policies document jsp
     */
    @RequestMapping(value = "/admin/addPolicy.html", method = RequestMethod.GET)
    public String importPolicies(HttpServletRequest request,  Model model, Map map) {
        Policy policy = new Policy();

        try {
            logger.debug("Policies Upload Controller");
        } catch (Exception ex) {
            logger.debug("CERROR: Policies Upload Controller exception :" + ex);
        }

        model.addAttribute("policy",policy);
        model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.trxApproval"));
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
        return "admin/policy";
    }

    /**
     * Policy file upload controller
     * @param request
     * @param requestForServlet
     * @param model
     * @return
     */
    @RequestMapping(value = "/policy/uploadPolicy.html", method = RequestMethod.POST)
    void ajaxCallForPolicyFileUpload(MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model) {
        try {
            logger.debug("Policy File Upload Ajax Call Post : ");
            Policy policy = new Policy();
            String uploadPath = "uploadFiles";
            String documentName = request.getParameter("documentName");
            String implementedDate = request.getParameter("implementedDate");
            String audiance = request.getParameter("audiance") != null? request.getParameter("audiance"): "0" ;
            String policyType = request.getParameter("policyType");
            Iterator<String> itr = request.getFileNames();
            MultipartFile multipartFileForTrxApproval = request.getFile(itr.next());

            if (multipartFileForTrxApproval != null) {
                String uploadPathForfileIconPath = Utils.getMessageBundlePropertyValue("policies.upload.path");
                String orginalFileName = multipartFileForTrxApproval.getOriginalFilename();

                String uploadFilePathTrxApproval = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalFileName);
                File dirForTrxApproval = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath);
                String fileIconPathForTrxApproval = "";
                logger.debug("FILE TO SAVE :" + uploadFilePathTrxApproval);
                logger.debug(" documentName : " + documentName+" policyType="+policyType+" implemented date="+implementedDate+" audiance="+audiance);
                try {
                    if (!dirForTrxApproval.exists()) {  // make directory
                        dirForTrxApproval.mkdirs();
                    }
                    fileIconPathForTrxApproval = ProactiveController.getFileIconPath(multipartFileForTrxApproval);

                    if (!Utils.isEmpty(orginalFileName))
                        multipartFileForTrxApproval.transferTo(new File(uploadFilePathTrxApproval));  // upload file to specific directory
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                policy.setEntryTime(Utils.getTodaysDate());
                policy.setFileIconLocation(fileIconPathForTrxApproval);
                policy.setFileName(orginalFileName);
                policy.setFileLocation(uploadFilePathTrxApproval);
                policy.setAuthor(Utils.getLoggedUserName());
                policy.setDocumentName(documentName);
                policy.setAudianceCode(Integer.parseInt(audiance));
                policy.setImplementedDate(Utils.getDateFromString(Constants.MONTH_DAY_YEAR,implementedDate));
                policy.setPolicyType(policyType);
                adminService.save(policy);
                sendPolicyInvitation(audiance);
                logger.debug("Policy Save SuccessFul");
            }

        } catch (Exception ex) {
            logger.debug("CERROR:: Policy File Upload Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
    }
/**
     * Training file upload controller
     * @param request
     * @param requestForServlet
     * @param model
     * @return
     */
    @RequestMapping(value = "/training/uploadTraining.html", method = RequestMethod.POST)
    void ajaxCallForTrainingFileUpload(MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model) {
        try {
            logger.debug("Training File Upload Ajax Call Post : ");
            int audiance = 1;

            Training training = new Training();
            String uploadPath = "uploadFiles";
            String documentName = request.getParameter("documentName");
            String trainingType = request.getParameter("trainingType");
            String implementedDate = request.getParameter("implementedDate");
            Iterator<String> itr = request.getFileNames();
            MultipartFile multipartFileForTrxApproval = request.getFile(itr.next());

            if (multipartFileForTrxApproval != null) {
                String uploadPathForfileIconPath = Utils.getMessageBundlePropertyValue("training.upload.path");
                String orginalFileName = multipartFileForTrxApproval.getOriginalFilename();

                String uploadFilePathTrxApproval = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalFileName);
                File dirForTrxApproval = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForfileIconPath);
                String fileIconPathForTrxApproval = "";
                logger.debug("FILE TO SAVE :" + uploadFilePathTrxApproval);
                logger.debug(" documentName : " + documentName);
                try {
                    if (!dirForTrxApproval.exists()) {  // make directory
                        dirForTrxApproval.mkdirs();
                    }
                    fileIconPathForTrxApproval = ProactiveController.getFileIconPath(multipartFileForTrxApproval);

                    if (!Utils.isEmpty(orginalFileName))
                        multipartFileForTrxApproval.transferTo(new File(uploadFilePathTrxApproval));  // upload file to specific directory
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                training.setEntryTime(Utils.getTodaysDate());
                training.setFileIconLocation(fileIconPathForTrxApproval);
                training.setFileName(orginalFileName);
                training.setFileLocation(uploadFilePathTrxApproval);
                training.setAuthor(Utils.getLoggedUserName());
                training.setDocumentName(documentName);
                training.setTrainingType(trainingType);
                training.setAudianceCode(audiance);
                training.setImplementedDate(Utils.getDateFromString(Constants.MONTH_DAY_YEAR,implementedDate));
                adminService.save(training);
                sendTrainingInvitation();
                logger.debug("Training Save SuccessFul");
            }

        } catch (Exception ex) {
            logger.debug("CERROR:: Training File Upload Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /**
     * Get All Policy
     * @param request
     * @param model   landingPage.html
     * @param map
     * @return
     */
    @RequestMapping(value = "/policy/policyList.html", method = RequestMethod.GET)
    public String getPolicyList(HttpServletRequest request,  Model model, Map map) {
        logger.debug("Policy list controller");
        int totalOutstandingPolicies = 0;
        int totalSignedPolicies = 0 ;
        List<Policy> policyList = new ArrayList<Policy>();
        try {
            policyList = adminService.getAllPolicy();
            if(policyList != null) {
                logger.debug("Policy list size : " + policyList.size());
            }else  {
                logger.debug("No policy found : " + policyList);
            }

            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);
            totalOutstandingPolicies = adminJdbcService.getTotalOutstandingPolicyByUser(user);
            totalSignedPolicies = adminJdbcService.getTotalSignedPolicyByUser(user);

            if(user.getUserTypeId() >0){
                List unreadedPolicyList = adminJdbcService.getUnreadedPolicyList(user);
                model.addAttribute("unreadedPolicyList", unreadedPolicyList);
            }

            model.addAttribute("totalPolicies",policyList != null ? policyList.size() : 0);

        } catch (Exception ex)  {
            logger.debug("CERROR: Policy list controller exception :" + ex);
        }

        model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("main.procedure.and.certification.id"));
        /*model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.trxApproval"));*/
        model.addAttribute("outstandingPolicies", totalOutstandingPolicies);
        model.addAttribute("signedPolicies", totalSignedPolicies);
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
        model.addAttribute("policyList", PolicyType.getPolicyTypes());
        model.addAttribute("policy", new Policy());
        adminService.addNode(getMessageBundlePropertyValue("procedures.and.certification"), 2, request);
        return "admin/policyList";
    }

    @RequestMapping(value = "/policy/getJASONforPolicyList.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getProactiveProjectList(HttpServletRequest request, Model model) {
        logger.debug("Get Policy list controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "document_name";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        String fromSearch = request.getParameter("fromSearch") != null ? request.getParameter("fromSearch") : "";
        String userStringFromJason = request.getParameter("user") != null ? request.getParameter("user") : "";
        List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
        sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;
        qtype = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,qtype): "";
        List dbColumnHeaderList = new ArrayList();
        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List policyList = new ArrayList<Control>();
        Policy policy = new Policy();

        try {
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());

            String  documentName = request.getParameter("documentName") != null ? request.getParameter("documentName") : "";
            String  author = request.getParameter("author") != null ? request.getParameter("author") : "";
            String entryTime = request.getParameter("entryTime") != null ? request.getParameter("entryTime") :"";
            String policyType = request.getParameter("policyType") != null ? request.getParameter("policyType") : "";

            logger.debug("documentName="+ documentName +" author="+author+" entryTime="+ entryTime+" policyType="+policyType);
            // Date trxDate = !Utils.isEmpty(transactionDate) ? Utils.strToDate(transactionDate): null;
            Date policyUploadedDate = !Utils.isEmpty(entryTime) ? Utils.getDateFromString(Constants.MONTH_DAY_YEAR,entryTime): null;

            policy.setDocumentName(documentName);
            policy.setAuthor(author);
            policy.setPolicyType(policyType);
            policy.setEntryTime(policyUploadedDate);
            policy.setEntryDate(entryTime);

            totalItems =  adminJdbcService.getPolicyListCount(policy,qtype,query,sortname,user);
            policyList  = adminJdbcService.getPartialPolicyList(policy,Utils.parseInteger(page), "max".equals(rp) ? totalItems : Utils.parseInteger(rp), qtype, query, sortname, sortorder,user);
//            totalItems= policyList != null? policyList.size(): 0 ;
            logger.debug("total items="+totalItems);

            if("max".equals(rp)){
                // TransactionSearchController.setTotalListSizeAndListInSession(totalItems,policyList,request);
                // jasonData.setTotal(totalItems);
            }else{
                if(policyList != null) {
                    logger.debug("policy list size="+policyList.size());
                    jasonData.setPage(Utils.parseInteger(page));
                    for(Object obj : policyList) {
                        Policy policyNew = new Policy();
                        Cell cell = new Cell();
                        Map map = (Map) obj;
                        Date entry_time =  map.get("entry_time") != null ? (Date)map.get("entry_time") : new Date();
                        policyNew.setFileName(map.get("document_name") != null ? map.get("document_name").toString() : "");
                        policyNew.setFileIconLocation("<img class='fileIcon' src='" + request.getContextPath() + "/" + (map.get("file_icon_location") != null ? map.get("file_icon_location").toString() : "") + "'> ");
                        policyNew.setAuthor(map.get("author") != null ? map.get("author").toString() : "");
                        policyNew.setPolicyType(map.get("policy_type") != null ? map.get("policy_type").toString() : "");
                        policyNew.setPolicyStatus(map.get("policy_status") != null ? map.get("policy_status").toString() : "");
                        policyNew.setEntryDate(Utils.dateToStrWithFormat(entry_time, Constants.MONTH_DAY_YEAR));
                        policyNew.setPolicyDeleteHtmlButton(Utils.getMessageBundlePropertyValue("policy.delete.button.html"));
//                        policyNew.setPolicyDownloadHtmlButton(Utils.getMessageBundlePropertyValue("policy.download.button.html"));

                        if(Constants.SIGNED.equals(policyNew.getPolicyStatus())){
                            policyNew.setPolicyViewHtmlButton(Utils.getMessageBundlePropertyValue("policy.view.button.html"));
                        }else if(Constants.OUTSTANDING.equals(policyNew.getPolicyStatus())){
                            policyNew.setPolicyViewHtmlButton(Utils.getMessageBundlePropertyValue("policy.download.button.html"));
                        }else if(Constants.CONFIRMED.equals(policyNew.getPolicyStatus())){
                            policyNew.setPolicyViewHtmlButton(Utils.getMessageBundlePropertyValue("policy.confirmation.complete.html"));
                        }


                        policyNew.setId(map.get("id") != null ? ((Number) map.get("id")).intValue() : 0);
                        cell.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );

                        cell.setCell(policyNew);
                        entry.add(cell);
                    }
                    Map mapForHeader = new HashMap();
                    mapForHeader.put("entryDate","entry_time"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("fileName","document_name");
                    mapForHeader.put("author","author");
                    mapForHeader.put("policyType","policy_type");
                    mapForHeader.put("policyStatus","policy_status");
                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);

                    jasonData.setRows(entry);
                    jasonData.setTotal(totalItems);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);
                } else {
                    logger.debug("No Policy Found");
                }
                if("1".equals(fromSearch)&& "1".equals(page)){

                    policyList  = adminJdbcService.getPartialPolicyList(policy, 1, totalItems, qtype, query, sortname, sortorder,user);
                    TransactionSearchController.setTotalListSizeAndListInSession(totalItems,policyList,request);
                }
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Exception : " + ex);
        }

        return jasonData;
    }


    @RequestMapping(value = "/policy/deletePolicy.html", method = RequestMethod.GET)
    public String deletePolicy(HttpServletRequest request, @RequestParam("policyId") long policyId) {
        logger.debug("Policy Delete Controller");

        try {
            if(policyId > 0) {
                Policy policy = adminService.getPolicyById(policyId);
                File file = new File(policy.getFileLocation());
                logger.debug("Policy Id : "+policyId);
                logger.debug("File To Delete : "+policy.getFileLocation());
                if(file.exists() && file.delete()){
                    adminJdbcService.deletePolicyAndProcedureByPolicyId(policyId);
                    adminService.deleteEntityById(policyId, Entity.POLICY.getValue());
                    logger.debug("Policy deleted successfully");
            }else {
                logger.debug("Negative Policy id Not supported");
            }

         }
        }catch (Exception ex)  {
            logger.debug("CERROR: Policy Delete Controller exception :" + ex);
        }


        return "redirect:./policyList.html";
    }


    @RequestMapping(value = "admin/getControl.html", method = RequestMethod.GET)
    public @ResponseBody Control getControl(HttpServletRequest request, Model model) {
        logger.debug("Get control for ajax");
        String controlId = request.getParameter("controlId");
        Control control = new Control();
        try {
            if(!Utils.isEmpty(controlId)) {
                long  id = Long.parseLong(controlId);
                control = (Control)adminService.loadEntityById(id, Constants.CONTROL);
                logger.debug("Control edit : " + control);
                model.addAttribute("control" ,control);
            } else {
                logger.debug("Control add : " + control);
                control = new Control();
                model.addAttribute("control" ,control);
            }

        }catch (Exception ex) {
            logger.debug("CERROR: Get Control List Exception : " + ex);
        }
        return control;
    }

    @RequestMapping(value = "/realtime/getTrxApprovalJASONList.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getTrxApprovalJASONList(HttpServletRequest request, Model model) {
        logger.debug("Get Trx ApprovalJASON List controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List trxApprovallList = new ArrayList<Control>();

        try {

            totalItems= adminService.getEntitySize(Constants.TRANSACTION_APPROVAL);
            trxApprovallList = adminJdbcService.getPartialDataList( Utils.parseInteger(page), Utils.parseInteger(rp) , qtype, query,sortname, sortorder, Constants.TRANSACTION_APPROVAL_TABLE);
            if(trxApprovallList  != null) {
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : trxApprovallList ) {
                    TransactionApproval transactionApproval = new TransactionApproval();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    Date entry_time =  map.get("entry_time") != null ? (Date)map.get("entry_time") : new Date();
                    transactionApproval.setFileName(map.get("file_name") != null ? map.get("file_name").toString() : "");
                    transactionApproval.setFileIconLocation("<img class='fileIcon' src='" + request.getContextPath() + "/" + (map.get("file_icon_location") != null ? map.get("file_icon_location").toString() : "") + "'> ");
                    transactionApproval.setAuthor(map.get("author") != null ? map.get("author").toString() : "");
                    transactionApproval.setEntryDate(Utils.dateToStrWithFormat(entry_time, Constants.MONTH_DAY_YEAR));
                    transactionApproval.setTrxApproveDeleteHtmlButton(Utils.getMessageBundlePropertyValue("trxApproval.delete.button.html"));
                    transactionApproval.setId(map.get("id") != null ? ((Number) map.get("id")).intValue() : 0);
                    cell.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );

                    cell.setCell(transactionApproval);
                    entry.add(cell);
                }
                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
            } else {
                logger.debug("No Control Found");
            }
        }catch (Exception ex) {
            logger.debug("CERROR: get Trx Approval JASON List Exception : " + ex);
        }

        return jasonData;
    }

    @RequestMapping(value = "/admin/holidays.html", method = RequestMethod.GET)
    public String getHolidays(HttpServletRequest request, @RequestParam("regionId") long regionId, Model model) {
        logger.debug("Holiday Get Controller :");
        Holiday holiday = new Holiday();
        Weekend weekend = new Weekend();
        Region region = new Region();
        try {
            if (regionId > 0) {
                region = (Region) adminService.loadEntityById(regionId, Entity.REGION.getValue());
                if(region.getWeekend() != null ) {
                    weekend = (Weekend) adminService.loadEntityById(region.getWeekend().getId(), Entity.WEEKEND.getValue());
                }
            }
            region.setWeekend(weekend);
            holiday.setRegion(region);
        } catch (Exception ex) {
            logger.debug("CERROR: Holiday Get Controller exception :" + ex);
        }
        model.addAttribute("holiday", holiday);
        return "admin/holidays";
    }

    @RequestMapping(value = "/admin/holidays.html", method = RequestMethod.POST)
    public String saveHolidays(HttpServletRequest request, @ModelAttribute("holiday") Holiday holiday, BindingResult result, Model model) {
        logger.debug("Holiday Post Controller :");
        holidayValidation.validate(holiday, request, result);
        try {
             if (result.hasErrors()) {
                 return "admin/holidays";
             }

//             adminService.save(holiday.getWeekend());
             adminService.saveOrUpdate(holiday);
        } catch (Exception ex) {
            logger.debug("CERROR: Holiday Save Controller exception :" + ex);
        }
        return "redirect:./holidays.html?regionId=" + holiday.getRegion().getId();
    }


    @ModelAttribute("regionList")
    public List getAllCountry() {
        return adminService.getAnyEntityList(Entity.REGION.getValue());
    }
   @ModelAttribute("dayNames")
    public List getDayNames() {
        return Utils.getDayNames();
    }

    @RequestMapping(value = "/admin/holidayList.html", method = RequestMethod.POST)
    public @ResponseBody
    JasonBean getAssignmentList(HttpServletRequest request) {
        logger.debug("Holiday List Controller ");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "description";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
        sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;
//        logger.debug("SMN: sortname="+sortname);

        JasonBean jasonData = new JasonBean();
        int totalRealTimeProject = 0;
        List holidayList = new ArrayList();
        List dbColumnHeaderList = new ArrayList();
        List<Cell> entry = new ArrayList<Cell>();

        try {
//            realTmeProjectList = adminService.getAnyEntityList(Constants.REALTIME_PROJECT);
            totalRealTimeProject = adminService.getEntitySize(Constants.HOLIDAY);
            logger.debug("TOTAL count---"+totalRealTimeProject+" page="+page+" rp="+rp+" query="+query+" qtype="+qtype+" sortname="+sortname+" sortorder="+sortorder);
            holidayList = adminJdbcService.getPartialDataList(Utils.parseInteger(page),
                    "max".equals(rp) ? totalRealTimeProject : Utils.parseInteger(rp), qtype, query, sortname, sortorder,Constants.HOLIDAY_TABLE_NAME);
            if("max".equals(rp)){
                TransactionSearchController.setTotalListSizeAndListInSession(totalRealTimeProject,holidayList,request);
                logger.debug("----List:"+holidayList );
                jasonData.setTotal(totalRealTimeProject);
            }else{
                if(holidayList != null) {
                    logger.debug("Holiday List Size : " + holidayList.size());
                    jasonData.setPage(Utils.parseInteger(page));
                    for(Object obj : holidayList) {
                        Holiday holiday = new Holiday();
                        Cell cell = new Cell();
                        Map map = (Map) obj;
                        Date holidayDate =  map.get("holiday_date") != null ? (Date)map.get("holiday_date") : new Date();

                        holiday.setDescription(map.get("description") != null ? map.get("description").toString() : "");
                        holiday.setDateStr(Utils.dateToStrWithFormat(holidayDate,Constants.MONTH_DAY_YEAR));
                        cell.setId(map.get("id") instanceof Number ? ((Number)map.get("id")).intValue() : 0 );
                        holiday.setDeleteButtonHtml(Utils.getMessageBundlePropertyValue("holiday.delete.button.html"));
                        holiday.setEditButtonHtml(Utils.getMessageBundlePropertyValue("holiday.edit.button.html"));
                        holiday.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );
                        cell.setCell(holiday);
                        entry.add(cell);
                    }
                    Map mapForHeader = new HashMap();
                    mapForHeader.put("description","description"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("dateStr","holiday_date");
                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);


                    jasonData.setRows(entry);
                    jasonData.setTotal(totalRealTimeProject);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);
                }
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Holiday List exception : " + ex);
        }


        return jasonData;
    }

    public void sendTrainingInvitation() {
        logger.debug("Training invitation start.");
        String subject = Utils.getMessageBundlePropertyValue("training.and.certification.mail.subject");
        String from = Utils.getApplicationPropertyValue("reset.password.mail.sender");
        String body ="";
        List employeesEmailList = null;

        try {
            employeesEmailList = adminJdbcService.getAllEmailId(Constants.EMPLOYEE_MASTER_LEDGER);
            if(employeesEmailList != null) {
                logger.debug("Employee master list size: " + employeesEmailList.size());
                String[] to = {};
                String[] bcc = new String[employeesEmailList.size()];
                for (int i = 0; i< employeesEmailList.size(); i++) {
                    bcc[i] = ((Map)employeesEmailList.get(i)).get("email").toString();
                }
                body = Utils.getStringFromFile(AdminController.class.getResource("/../../resources/training-and-certification-mail.txt").getPath());
                Utils.sendAnEmail(from,to,bcc,subject, body);
               logger.debug("Training invitation send.");
            }
        } catch (Exception ex) {
            logger.debug("CERROR:: Training invitation exception: " + ex);
            logger.debug("CERROR:: Training invitation exception description: " + ex.getMessage());
        }
    }

    public void sendPolicyInvitation(String audiance) {
        logger.debug("Policy invitation start.");
        String subject = Utils.getMessageBundlePropertyValue("policy.and.procedure.mail.subject");
        String from = Utils.getApplicationPropertyValue("reset.password.mail.sender");
        String body ="";
        List employeesEmailList = null;
        List vendorEmailList = null;

        try {
            body = Utils.getStringFromFile(AdminController.class.getResource("/../../resources/policy-mail-body.txt").getPath());
            if("Employee".equals(audiance)){
            employeesEmailList = adminJdbcService.getAllEmailId(Constants.EMPLOYEE_MASTER_LEDGER);
                String[] to = {};
                senMailsByList(from,to,subject,body,employeesEmailList,"email");
            }else if("Vendor".equals(audiance)){
            vendorEmailList = adminJdbcService.getAllEmailId(Constants.VENDOR_MASTER_LEDGER);
            if(vendorEmailList != null) {
                logger.debug("vendor master list size: " + vendorEmailList.size());
                String[] to = {};
                senMailsByList(from,to,subject,body,vendorEmailList,"email");
                logger.debug("Policy invitation send From Vendor.");
            }
            }
        } catch (Exception ex) {
            logger.debug("CERROR:: Policy invitation exception: " + ex);
            logger.debug("CERROR:: Policy invitation exception description: " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/admin/indivisualPolicyView.html", method = RequestMethod.GET)
    public String getPolicyDetails(HttpServletRequest request, @RequestParam("policyType") String policyType, Model model) {
        logger.debug("policy View Get Controller :");

        try {

        } catch (Exception ex) {
            logger.debug("CERROR: Policy Details view Controller exception :" + ex);
        }
        model.addAttribute("policyType", policyType);
        return "common/policyDetailsView";
    }


    @RequestMapping(value = "/admin/getJASONforPolicyDetails.html", method = RequestMethod.POST)
    public  @ResponseBody  JasonBean getJASONforProactiveWorkflowList(HttpServletRequest request, Model model) {
        logger.debug("Get Policy Details controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String policyType = request.getParameter("policyType") != null ? request.getParameter("policyType") : "";

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";

        List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
        sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;

        List dbColumnHeaderList = new ArrayList();
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List policyList  = new ArrayList<Control>();
        Policy policy = new Policy();
        try {
            policy.setPolicyType(policyType);
            totalItems = adminJdbcService.getPolicyCount(policy);
            logger.debug("TOTAL count---"+totalItems+" page="+page+" rp="+rp);
            policyList = adminJdbcService.getPartialDataList(Utils.parseInteger(page),"max".equals(rp) ? totalItems : Utils.parseInteger(rp),
                    qtype,query,sortname,sortorder,Constants.POLICY_TABLE);
            if("max".equals(rp)){
                TransactionSearchController.setTotalListSizeAndListInSession(totalItems,policyList,request);
                jasonData.setTotal(totalItems);
            }else{
                if(policyList != null) {
                    logger.debug("Policy List size="+policyList.size());
                    jasonData.setPage(Utils.parseInteger(page));
                    for(Object obj : policyList) {
                        policy= new Policy();
                        Cell cell = new Cell();
                        Map map = (Map) obj;
                        int noOfEmployeeConfirmed = (int )(Math.random() * 16 + 1);
                        int noOfEmployeeUnconfirmed = (int )(Math.random() * 16 + 1);
                        Date uploadDate =  map.get("entry_time") != null ? (Date)map.get("entry_time") : new Date();

                        policy.setEntryDate(Utils.dateToStrWithFormat(uploadDate,Constants.MONTH_DAY_YEAR));
                        policy.setDocumentName(map.get("document_name") != null ? map.get("document_name").toString(): "");
                        policy.setNotificationWentOutDateStr(Utils.dateToStrWithFormat(uploadDate,Constants.MONTH_DAY_YEAR));
                        policy.setPolicyType(map.get("policy_type") != null ? map.get("policy_type").toString() : "");
                        policy.setNoOfEmployeeConfirmed(noOfEmployeeConfirmed);
                        policy.setNoOfEmployeeUnconfirmed(noOfEmployeeUnconfirmed);
                        policy.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );


                        cell.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );
                        cell.setCell(policy);
                        entry.add(cell);
                    }

                    Map mapForHeader = new HashMap();
                    mapForHeader.put("documentName","document_name"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("entryDate","entry_time");
                    mapForHeader.put("notificationWentOutDateStr","entry_time");
                    mapForHeader.put("policyType","policy_type");

                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);

                    jasonData.setRows(entry);
                    jasonData.setTotal(totalItems);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);
                } else {
                    logger.debug("No Policy List Found");
                }
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Policy Exception : " + ex);
        }

        return jasonData;
    }
    @RequestMapping(value = "/training/trainingShow.html", method = RequestMethod.GET)
    public void policyShow(HttpServletRequest request, HttpServletResponse response, @RequestParam("trainingId") long trainingId) throws IOException {
        logger.debug("Training Download Controller Start.");
        String fileName = "";
        String filePath = "";
        Training training = new Training();
        try {
            if (trainingId > 0) {
                training = (Training) adminService.loadEntityById(trainingId, Entity.TRAINING.getValue());
                fileName = training.getFileName();
                filePath = training.getFileLocation();
            } else {
                logger.debug("No Training Found For Id " + trainingId);
            }

            ServletContext context = request.getSession().getServletContext();
            String appPath = context.getRealPath("");
            String fullPath = appPath + filePath;
            File downloadFile = new File(filePath);
            FileInputStream inputStream = new FileInputStream(downloadFile);
            String mimeType = context.getMimeType(fullPath);
            logger.debug(" mimeType :" + mimeType);
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //response.addHeader("", "attachment;filename=" + fileName);
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
    }

    @ModelAttribute("policyTypes")
    public List<PolicyType> getPolicyTypes() {
        List<PolicyType> policyTypes = PolicyType.getPolicyTypes();

        return policyTypes;
    }

    public static void senMailsByList(String from,String[] to,String subject,String body,List audianceList,String emailsColumnName){
        if(audianceList != null) {
            logger.debug("audiance list size: " + audianceList.size());
            String[] bcc = new String[audianceList.size()];
            for (int i = 0; i< audianceList.size(); i++) {
                bcc[i] = ((Map)audianceList.get(i)).get(emailsColumnName).toString();
            }
         try{
            Utils.sendAnEmail(from,to,bcc,subject, body);
        } catch (Exception ex) {
            logger.debug("CERROR:: Policy invitation exception: " + ex);
            logger.debug("CERROR:: Policy invitation exception description: " + ex.getMessage());
        }
            logger.debug("invitation send Successfully.");
        }
    }

    public int getCountByStatus(String status,List list,String columnName){
        String stat = "";
        int count = 0;
        for(int i=0; i<list.size(); i++){
            Map map = (Map)list.get(i);
            stat = map.get(columnName) != null ? map.get(columnName).toString():"";
            if( status != null && stat.trim().equals(status.trim())){
                count++;
            }
        }
        return count;
    }
}

