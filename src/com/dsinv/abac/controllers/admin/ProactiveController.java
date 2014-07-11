package com.dsinv.abac.controllers.admin;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dsinv.abac.Entity;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.FileUploadFormNewRiskAssValidation;
import com.dsinv.abac.validation.ProactiveRatioSetupValidation;
import com.dsinv.abac.vo.admin.AuditTrailVO;
import com.dsinv.abac.vo.admin.ControlVO;
import com.dsinv.abac.vo.admin.TransactionDetailVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.dsinv.abac.util.Utils.*;

@Controller
@SessionAttributes("NewRiskAssessmentTxBean")
public class ProactiveController {
    private static Logger logger = Logger.getLogger(ProactiveController.class);
    @Autowired(required = true)
    private AdminService adminService;
    private ProactiveRatioSetupValidation proactiveRatioSetupValidation;
    @Autowired(required = true)
    private FileUploadFormNewRiskAssValidation fileUploadFormNewRiskAssValidation;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private RuleController ruleController;

    /*
    * Method viewing Risk Assessment Attribute Weighting setup
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "admin/proactiveRatio.html", method = RequestMethod.GET)
    public String proactiveRatio(HttpServletRequest request, Model model) {
        try {
            ProactiveBlockWeightRatio proactiveBlockWeightRatio = new ProactiveBlockWeightRatio();
            List<ProactiveBlockWeightRatio> proactiveBlockWeightRatioList = adminService.getAllProactiveBlockWeightRatioList();
            if (proactiveBlockWeightRatioList != null) {
                for (ProactiveBlockWeightRatio proactiveBlockWeightRatioTemp : proactiveBlockWeightRatioList) {
                    if (ProactiveBlock.CPI_SCORE.getValue().equals(proactiveBlockWeightRatioTemp.getProactiveBlock() != null ? proactiveBlockWeightRatioTemp.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setCpiScore(proactiveBlockWeightRatioTemp.getRatio());
                    if (ProactiveBlock.REVENUES.getValue().equals(proactiveBlockWeightRatioTemp.getProactiveBlock() != null ? proactiveBlockWeightRatioTemp.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setRevenues(proactiveBlockWeightRatioTemp.getRatio());
                    if (ProactiveBlock.SALES_MODEL_RELATIONSHIPS.getValue().equals(proactiveBlockWeightRatioTemp.getProactiveBlock() != null ? proactiveBlockWeightRatioTemp.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setSalesModelRelationships(proactiveBlockWeightRatioTemp.getRatio());
                    if (ProactiveBlock.NATURE_OF_BUSINESS_OPERATIONS.getValue().equals(proactiveBlockWeightRatioTemp.getProactiveBlock() != null ? proactiveBlockWeightRatioTemp.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setNatureOfBusinessOperations(proactiveBlockWeightRatioTemp.getRatio());
                    if (ProactiveBlock.GOVERNMENT_INTERACTION.getValue().equals(proactiveBlockWeightRatioTemp.getProactiveBlock() != null ? proactiveBlockWeightRatioTemp.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setGovernmentInteraction(proactiveBlockWeightRatioTemp.getRatio());
                }
            }
            // adding breadcrumb
            adminService.addNode(Utils.getMessageBundlePropertyValue("risk.assessment.attribute.weighting"), 50, request);
            model.addAttribute("proactiveBlockWeightRatio", proactiveBlockWeightRatio);
        } catch (Exception ex) {
            logger.debug("CERROR:: proactive block weight ratio " + ex);
        }
        return "admin/proactiveRatio";
    }

    /*
    * Method viewing Continue Risk Assessment Workflow
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/ContinueRiskAssessmentWorkFlow.html", method = RequestMethod.GET)
    public String getContinueRiskAssesment(HttpServletRequest request, Model model) {
        request.getSession().removeAttribute("proactiveTransactionId");  // to remove id that has been created in new Risk Assement
        request.getSession().removeAttribute("serialNo");                // to remove serial(Used to keep selection the table row)
        request.getSession().removeAttribute("hiddenId");
        logger.debug("Continue Risk Assesment Workflow.");
        try {
            List<AbstractBaseEntity> proactiveProjectList = adminService.getAnyEntityList(Entity.PROACTIVE_PROJECT.getValue());
            logger.debug("Size of proactive project list is: " + proactiveProjectList.size());
            model.addAttribute("proactiveProjectList", proactiveProjectList);
        } catch (Exception ex) {
            logger.debug("CERROR:: Continue Risk Assesment Workflow: " + ex);
        }
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("continue.risk.assessment"), 2, request);
        model.addAttribute("mainTabId", Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId", Utils.getMessageBundlePropertyValue("subTabId.continueRisk"));
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
        return "common/ContinueRiskAssessmentWorkFlow";
    }

    /*
    * Method to handle Risk Assessment Attribute Weighting form data
    * @param ProactiveBlockWeightRatio proactiveBlockWeightRatio, HttpServletRequest request, BindingResult result, Map model
    * @return type String
    */
    @RequestMapping(value = "/admin/proactiveRatio.html", method = RequestMethod.POST)
    public String processRegistration(ProactiveBlockWeightRatio proactiveBlockWeightRatio, BindingResult result, HttpServletRequest request, Map model) {
        ProactiveRatioSetupValidation proactiveRatioSetupValidation1 = new ProactiveRatioSetupValidation();
        proactiveRatioSetupValidation1.validate(proactiveBlockWeightRatio, result, adminService);  // set custom validation
        if (result.hasErrors()) {
            model.put("proactiveBlockWeightRatio", proactiveBlockWeightRatio);
            return "admin/proactiveRatio";
        } else {
            try {
                ProactiveBlock[] proactiveBlocks = ProactiveBlock.getProactiveBlocks();
                for (int i = 0; i < proactiveBlocks.length; i++) {    // for each proactive block
                    double ratio = 0.0;
                    ProactiveBlockWeightRatio proactiveRatio = new ProactiveBlockWeightRatio();
                    proactiveRatio = (ProactiveBlockWeightRatio) adminService.getAbstractBaseEntityByString(Entity.PROACTIVE_BLOCK_WEIGHT_RATIO.getValue(), "proactiveBlock", proactiveBlocks[i].getValue());
                    if (proactiveRatio != null) {     // update proactive block
                        if (ProactiveBlock.CPI_SCORE.getValue().equals(proactiveRatio.getProactiveBlock() != null ? proactiveRatio.getProactiveBlock().trim() : null))
                            ratio = proactiveBlockWeightRatio.getCpiScore();
                        else if (ProactiveBlock.REVENUES.getValue().equals(proactiveRatio.getProactiveBlock() != null ? proactiveRatio.getProactiveBlock().trim() : null))
                            ratio = proactiveBlockWeightRatio.getRevenues();
                        else if (ProactiveBlock.SALES_MODEL_RELATIONSHIPS.getValue().equals(proactiveRatio.getProactiveBlock() != null ? proactiveRatio.getProactiveBlock().trim() : null))
                            ratio = proactiveBlockWeightRatio.getSalesModelRelationships();
                        else if (ProactiveBlock.NATURE_OF_BUSINESS_OPERATIONS.getValue().equals(proactiveRatio.getProactiveBlock() != null ? proactiveRatio.getProactiveBlock().trim() : null))
                            ratio = proactiveBlockWeightRatio.getNatureOfBusinessOperations();
                        else if (ProactiveBlock.GOVERNMENT_INTERACTION.getValue().equals(proactiveRatio.getProactiveBlock() != null ? proactiveRatio.getProactiveBlock().trim() : null))
                            ratio = proactiveBlockWeightRatio.getGovernmentInteraction();
                        proactiveRatio.setRatio(ratio);
                    } else {                          // create new proactive block
                        proactiveRatio = new ProactiveBlockWeightRatio();
                        proactiveRatio.setProactiveBlock(proactiveBlocks[i].getValue());
                        if (ProactiveBlock.CPI_SCORE.getValue().equals(proactiveBlocks[i].getValue() != null ? proactiveBlocks[i].getValue().trim() : null))
                            ratio = proactiveBlockWeightRatio.getCpiScore();
                        if (ProactiveBlock.REVENUES.getValue().equals(proactiveBlocks[i].getValue() != null ? proactiveBlocks[i].getValue().trim() : null))
                            ratio = proactiveBlockWeightRatio.getRevenues();
                        if (ProactiveBlock.SALES_MODEL_RELATIONSHIPS.getValue().equals(proactiveBlocks[i].getValue() != null ? proactiveBlocks[i].getValue().trim() : null))
                            ratio = proactiveBlockWeightRatio.getSalesModelRelationships();
                        if (ProactiveBlock.NATURE_OF_BUSINESS_OPERATIONS.getValue().equals(proactiveBlocks[i].getValue() != null ? proactiveBlocks[i].getValue().trim() : null))
                            ratio = proactiveBlockWeightRatio.getNatureOfBusinessOperations();
                        if (ProactiveBlock.GOVERNMENT_INTERACTION.getValue().equals(proactiveBlocks[i].getValue() != null ? proactiveBlocks[i].getValue().trim() : null))
                            ratio = proactiveBlockWeightRatio.getGovernmentInteraction();
                        proactiveRatio.setRatio(ratio);
                    }
                    adminService.saveOrUpdate(proactiveRatio);
                } // end of for block
                Utils.setGreenMessage(request, Utils.getMessageBundlePropertyValue("proactive.ratio.update.message"));
            } catch (Exception ex) {
                logger.debug("CERROR:: Admin Proactive Ratio " + ex);
            }
            return "redirect:/admin/proactiveRatio.html";
        }
    }

    /*
    * Method for viewing global map page
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/GlobalMap.html", method = RequestMethod.GET)
    public String heatMap(HttpServletRequest request, Model model) {
        String selectedButton = (String) request.getSession().getAttribute("hiddenId");
        request.getSession().removeAttribute("cName");
        request.getSession().removeAttribute("tType");
        request.getSession().removeAttribute("transactionTypeForSubHeader");
        Map<String, Map> map = new HashMap<String, Map>();
        Region region;
        Date fromDate;
        Date toDate;
        try {
            ProactiveBlockWeightRatio proactiveBlockWeightRatio = new ProactiveBlockWeightRatio();
            List<ProactiveBlockWeightRatio> list = adminService.getAllProactiveBlockWeightRatioList();
            logger.debug("List of Proactive Block Weight Ratio " + list.size());
            if (list != null) {     // get existing Proactive Block Weight Ratios
                for (ProactiveBlockWeightRatio proactiveBlockWeightRatio1 : list) {
                    if (ProactiveBlock.CPI_SCORE.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setCpiScore(proactiveBlockWeightRatio1.getRatio());
                    if (ProactiveBlock.REVENUES.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setRevenues(proactiveBlockWeightRatio1.getRatio());
                    if (ProactiveBlock.SALES_MODEL_RELATIONSHIPS.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setSalesModelRelationships(proactiveBlockWeightRatio1.getRatio());
                    if (ProactiveBlock.NATURE_OF_BUSINESS_OPERATIONS.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setNatureOfBusinessOperations(proactiveBlockWeightRatio1.getRatio());
                    if (ProactiveBlock.GOVERNMENT_INTERACTION.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                        proactiveBlockWeightRatio.setGovernmentInteraction(proactiveBlockWeightRatio1.getRatio());
                }
            } else {                // set 0 to all Proactive Block Weight Ratios
                proactiveBlockWeightRatio.setCpiScore(0);
                proactiveBlockWeightRatio.setRevenues(0);
                proactiveBlockWeightRatio.setSalesModelRelationships(0);
                proactiveBlockWeightRatio.setNatureOfBusinessOperations(0);
                proactiveBlockWeightRatio.setGovernmentInteraction(0);
            }
            if (Character.isDigit(selectedButton.charAt(0))) {
                int selectedYear = Character.getNumericValue(selectedButton.charAt(0));
                fromDate = Utils.getYearBackTime(selectedYear - 1);
            } else {
                fromDate = request.getSession().getAttribute("fromDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("fromDate")) : Utils.getTodaysDate();
            }
            toDate = request.getSession().getAttribute("toDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("toDate")) : Utils.getTodaysDate();
            List<AbstractBaseEntity> countryList = adminService.getAnyEntityList("Region");
            logger.debug("List of region " + countryList.size());

            double tiCpiRiskIndex = 0;
            String entityOrRipName = "";
            double annualRevenue = 0;
            String percentageOfGovRevenue = "";
            String salesToGovCustomers = "" ;
            int saleToCpiAndGovCustomers = 0;
            ProactiveBlockWeight proactiveBlockWeightOld = new ProactiveBlockWeight();
            for (AbstractBaseEntity abstractBaseEntity : countryList) {
                region = (Region) abstractBaseEntity;
                List<ProactiveBlockWeight> proactiveBlockWeightList = adminService.getProactiveBlockWeight(region.getId(), Utils.getYear(toDate), Utils.getYear(fromDate));
                double weightedValue = 0;
                if (proactiveBlockWeightList.size() > 0) {
                    for (ProactiveBlockWeight proactiveBlockWeight : proactiveBlockWeightList) {
                        weightedValue += getWeightedValue(proactiveBlockWeightRatio, proactiveBlockWeight);
                        //if(proactiveBlockWeight.getDate()==2013){
                            proactiveBlockWeightOld = proactiveBlockWeight;
                        //}
                    }
                    weightedValue = weightedValue / proactiveBlockWeightList.size();
                    region.setWeightedScore("" + Math.round(weightedValue));
                } else {
                    region.setWeightedScore("" + 0);
                }
                Map<String, String> map1 = new HashMap<String, String>();
                proactiveBlockWeightOld.setWeightedScoreStr(region.getWeightedScore());
                ObjectMapper mapper = new ObjectMapper();
                String proactiveBlockWeightOldStr = mapper.writeValueAsString(proactiveBlockWeightOld);
                map1.put(region.getName(), proactiveBlockWeightOldStr);
                map.put(region.getCode(), map1);
            }
            logger.debug("From date:" + fromDate + " todate=" + toDate);
            model.addAttribute("keyMap", map);
            model.addAttribute("regionList", countryList);
            model.addAttribute("proactiveBlockWeightRatio", proactiveBlockWeightRatio);
            model.addAttribute("subHeader", "1");     //for adding extra subheader
            adminService.addNode(Utils.getMessageBundlePropertyValue("heat.map"), 4, request); // adding Breadcrumb
            model.addAttribute("mainTabId", Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
            model.addAttribute("subTabId", Utils.getMessageBundlePropertyValue("subTabId.newRiskAssessment"));
        } catch (Exception ex) {
            logger.debug("CERROR:: GlobalMap " + ex);
        }
        return "common/heatMap";
    }
/*

    * Method to handle main tab and sub tab selection
    * @param User user, HttpServletRequest request
    * @return type String
*/

    @RequestMapping(value = "/riskasst/ajaxCallForMainTabAndSubTabSelection.html", method = RequestMethod.GET)
    public void ajaxCallForTab(HttpServletRequest request, Model model) {
        logger.debug("  :: ajaxCallForMainTabAndSubTabSelection :: ");
        String mainTab = request.getParameter("mt");
        String subTab = request.getParameter("st");
        logger.debug("  Main tab id: " + mainTab + " sub tab id: " + subTab);
        try {
            request.getSession().setAttribute("mainTab", mainTab);
            request.getSession().setAttribute("subTab", subTab);
        } catch (Exception ex) {
            logger.debug("CERROR : Error while setting tabs id:: " + ex);
        }
    }

    /*
    * Method for setting datepickerID, fromDate, toDate in session by ajax
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/ajaxCall.html", method = RequestMethod.GET)
    public
    @ResponseBody
    String ajaxCall(HttpServletRequest request, Model model) {
        String hiddenId = request.getParameter("id");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        request.getSession().setAttribute("hiddenId", hiddenId);
        if (fromDate != null) {
            request.getSession().setAttribute("fromDate", fromDate);
        }
        if (toDate != null) {
            request.getSession().setAttribute("toDate", toDate);
        }
        return "weightedScreen";
    }


    /*
    * Method to view  Risk Assesment - Summary Sheet of High Risk Jurisdiction page
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/ProactiveWorkflow.html", method = RequestMethod.GET)
    public String ProactiveWorkflow(HttpServletRequest request, Model model) {
        try {
            String hiddenId = (String) request.getSession().getAttribute("hiddenId");
            String selectedButton = (String) request.getSession().getAttribute("hiddenId");
            String countryName = request.getParameter("cName");
            request.getSession().removeAttribute("proactiveTransactionId");
            request.getSession().removeAttribute("serialNo");    // for removing serial(Used to keep selection the table row)
            Date fromDate;
            Date toDate;
            int wScore = (request.getParameter("wScore") != null ? Integer.parseInt(request.getParameter("wScore")) : 0);
            if (getCountryNameFromSession(request) == null) {     // set countryName to session
                request.getSession().setAttribute("cName", countryName);
            }
            if (Character.isDigit(selectedButton.charAt(0))) {
                int selectedYear = Character.getNumericValue(selectedButton.charAt(0));
                fromDate = Utils.getYearBackTime(selectedYear - 1);
            } else {
                fromDate = request.getSession().getAttribute("fromDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("fromDate")) : Utils.getTodaysDate();
            }
            toDate = request.getSession().getAttribute("toDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("toDate")) : Utils.getTodaysDate();
            Region region = adminService.getRegionByName(countryName);
            List<ProactiveBlockWeight> proactiveBlockWeightList = adminService.getProactiveBlockWeight(region.getId(), Utils.getYear(toDate), Utils.getYear(fromDate));
            model.addAttribute("proactiveBlockWeightList", proactiveBlockWeightList);
            model.addAttribute("wScore", wScore);
            model.addAttribute("cName", countryName);
            model.addAttribute("subHeader", "1");                    // for adding extra sub header
            adminService.addNode("Proactive Work Flow", 5, request); // adding Breadcrumb
            model.addAttribute("mainTabId", Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
            model.addAttribute("subTabId", Utils.getMessageBundlePropertyValue("subTabId.newRiskAssessment"));
        } catch (Exception ex) {
            logger.debug("CERROR:: Proactive Workflow: " + ex);
        }
        return "admin/proactiveWorkflow";
    }

    /*
    * Method to view  new Risk Assesment Summary details page
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/newRiskAssessmentsummaryView.html", method = RequestMethod.GET)
    public String summaryView(HttpServletRequest request, Model model) {
        long transactionId = 0;
        ProactiveProject proactiveProject;
        long proactiveTransactionId = 0;
        request.getSession().removeAttribute("ruleId");
        String pageNo = request.getSession().getAttribute("pageNo") != null ? (String) request.getSession().getAttribute("pageNo") : "";
        String controlIds = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "0";
        String proactiveProjectId = request.getParameter("proactiveProjectId") != null ? request.getParameter("proactiveProjectId") : "0";
        List employeeList = null;
        if (Utils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        try {

            if (!isEmpty(proactiveProjectId) && !"0".equals(proactiveProjectId)) {
                // successfully get realtime project id with url parameter
                proactiveProject = (ProactiveProject) adminService.loadEntityById(Long.parseLong(proactiveProjectId), Constants.PROACTIVE_PROJECT);
            } else {
                logger.debug(" Invalid Proactive Project Id :" + proactiveProjectId + " Redirecting To : weightedScreen.html");
                return "redirect:./weightedScreen.html";
            }
            proactiveTransactionId = getProactiveTransactionIdFromSession(request);
            logger.debug("proactiveTransactionId :" + proactiveTransactionId);
            ProactiveTransaction proactiveTransaction = proactiveTransactionId > 0 ? adminService.getProactiveTransactionById(proactiveTransactionId) : null;
            transactionId = proactiveTransaction != null ? proactiveTransaction.getTransaction().getId() : 0;

            NewRiskAssessmentTxBean newRiskAssessmentTxBean = new NewRiskAssessmentTxBean();

            if (ProactiveController.getSerialNoFromSession(request) == null) {       // no serial no on session
                ProactiveController.setSerialNoOnSession(request, "tr:first");
            }

            employeeList = adminJdbcService.getAllEmployee();
            if(employeeList != null) {
                logger.debug("AMLOG:: Employee List size: " + employeeList.size());
            }  else {
                logger.debug("AMLOG:: Employee List: " + employeeList);
                employeeList = new ArrayList();
            }

            newRiskAssessmentTxBean.setEmployeeList(employeeList);

            newRiskAssessmentTxBean.setProactiveProjectId(proactiveProject.getId());
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);

            model.addAttribute("NewRiskAssessmentTxBean", newRiskAssessmentTxBean);
            model.addAttribute("reactiveProjectId", "0");
            model.addAttribute("proactiveProjectId", proactiveProjectId);
            model.addAttribute("realTimeProjectId", "0");
            model.addAttribute("transactionId", transactionId);
            model.addAttribute("ctrlId", controlIds);
            model.addAttribute("subHeader", "1");
            model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
            model.addAttribute("serialNoForTableRowSelection", ProactiveController.getSerialNoFromSession(request));
            model.addAttribute("maxFileUploadSize", getApplicationPropertyValue("file.mazFileUploadSize"));
            model.addAttribute("pageNo", pageNo);
            adminService.addNode(getMessageBundlePropertyValue("proactive.summary.view"), 8, request);
        } catch (Exception ex) {
            logger.debug("CERROR:: Proactive Project Summary View: " + ex);
            logger.debug("CERROR:: Error Description: " + ex.getMessage());

        }
        model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId", getMessageBundlePropertyValue("subTabId.trxMonitoring"));
        return "common/newRiskAssessmentSummary1";
    }

    /*
    * Method to show proactive Project creation page
    * @param HttpServletRequest request
    * @return type String
    */
    @RequestMapping(value = "/riskasst/saveProactiveProject.html", method = RequestMethod.GET)
    public String saveOrUpdateProactiveProject(HttpServletRequest request) {
        String transactionIds = "";
        Date fromDate;
        Date toDate;

        ProactiveProject proactiveProject = (ProactiveProject) request.getSession().getAttribute("proactiveProject");
        List<ProactiveTransaction> proactiveTransactionList;
        List suspiciousTrxList = new ArrayList();
        try {
            String urlParam[] = request.getQueryString().split("=");
            int tType = (urlParam[1] != null ? Integer.parseInt(urlParam[1]) : 0);
            String selectedButton = (String) request.getSession().getAttribute("hiddenId");
            if (selectedButton != null && Character.isDigit(selectedButton.charAt(0))) {
                int selectedYear = Character.getNumericValue(selectedButton.charAt(0));
                fromDate = Utils.getYearBackTime(selectedYear);
            } else {
                fromDate = request.getSession().getAttribute("fromDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("fromDate")) : Utils.getTodaysDate();
            }
            toDate = request.getSession().getAttribute("toDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("toDate")) : Utils.getTodaysDate();
            if (proactiveProject == null || proactiveProject.getId() == 0) {
                proactiveProject = new ProactiveProject();
            }
            String countryName = getCountryNameFromSession(request) != null ? (String) getCountryNameFromSession(request) : "";
            Region region = (Region) adminService.getAbstractBaseEntityByString(Entity.REGION.getValue(), "name", countryName.trim());
            TransactionType transactionType = TransactionType.getTransactionType(tType);
            proactiveProject.setTransactionType(transactionType.getValue());
            proactiveProject.setRegion(region);
            proactiveProject.setToDate(toDate);
            proactiveProject.setFromDate(fromDate);
            proactiveProject.setReviewerLevel(2);
            adminService.saveOrUpdate(proactiveProject);
            request.getSession().setAttribute("proactiveProject", proactiveProject);
            proactiveTransactionList = adminService.getProactiveTransactionListByProactiveProjectId(proactiveProject.getId());
            if (proactiveTransactionList != null) {    // delete ProactiveTransactionCND, ProactiveTransactionAuditTrial, SupportingDocument
                for (ProactiveTransaction proactiveTransaction : proactiveTransactionList) {
                    adminJdbcService.deleteProactiveTransactionCNDByProactiveTransactionId(proactiveTransaction.getId());
                    adminJdbcService.deleteProactiveTransactionAuditTrialByProactiveProjectId(proactiveTransaction.getId());
                    adminJdbcService.deleteSupportingDocumentByProactiveProjectId(proactiveTransaction.getId());

                }
            }
            adminJdbcService.deleteProactiveTransactionByProactiveProjectId(proactiveProject.getId());
            adminJdbcService.importProactiveTransaction();
            /* int duplicateCheckingArray[] = new int[25];
            for (int i = 1; i < 10; i++) {            // create  ProactiveTransaction with 1-10 random numbers
                int id = (int) (Math.random() * 24);
                if (duplicateCheckingArray[id] == id) {
                    continue;
                }
                duplicateCheckingArray[id] = id;
                transactionIds += id + ",";
            }
            logger.debug("Proactive project transaction ids : " + transactionIds);
            transactionIds = transactionIds.substring(0, transactionIds.length() - 1);
            logger.debug("Proactive project transaction ids : " + transactionIds);
            ProactiveTransaction proactiveTransaction;
            List<Rule> rules = adminService.getRules();
            if (rules != null) {
                logger.debug("Rules list size : " + rules.size());
                ExecutionActivity executionHeader = new ExecutionActivity();
                executionHeader.setExecutionTime(Utils.getTodaysDate());
                adminService.save(executionHeader);
                for (Rule rule : rules) {
//                    suspiciousTrxList = ruleController.getSuspiciousTxList(rule, transactionIds);
                    logger.debug("proactive project suspicious tx : " + suspiciousTrxList);
                    if (!isEmpty(suspiciousTrxList) && suspiciousTrxList.size() > 0) {                // suspicious transaction list is not empty
                        for (int i = 0; i < suspiciousTrxList.size(); i++) {            // save each suspicious transaction to reaitime transaction with real time project
                            Map map = (Map) suspiciousTrxList.get(i);
                            long trxId = (Long) map.get("transactionId");
                            Transaction transaction = new Transaction();
                            transaction.setId(trxId);
                            proactiveTransaction = new ProactiveTransaction();
                            proactiveTransaction.setTransaction(transaction);
                            proactiveTransaction.setProactiveProject(proactiveProject);
                            adminService.save(proactiveTransaction);
                        }
//                        ruleController.saveRuleExecutedData(rule, suspiciousTrxList, executionHeader);
                    }
                }
            }*/


        } catch (Exception ex) {
            logger.debug("CERROR: Proactive Project Save Exception: " + ex);
        }
        return "redirect:./newRiskAssessmentsummaryView.html?cont=0&proactiveProjectId=" + proactiveProject.getId() + "&" + request.getQueryString();
    }

    /*
    * Method to format date, numbers etc in jsp page
    * @param ServletRequestDataBinder binder
    * @return type void
    */
    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.MONTH_DAY_YEAR);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, false));
    }

    /*
    * Method to prepare email
    * @param HttpServletRequest request, HttpServletResponse response, Model model, Locale locale
    * @return type String
    */
    @RequestMapping(value = "/riskasst/emailTransactionDetail.html", method = RequestMethod.GET)
    public String generateEMLFile(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) {

        Long transactionId = null;
        Long pProjectId = null;             // Proactive Project id
        Long rProjectId = null;             // Reactive Project id
        List<Control> controlListForTable = new ArrayList<Control>();
        Long pTransactionId = getProactiveTransactionIdFromSession(request);  // Proactive TransactionId
        Long rTransactionId = getReactiveTransactionIdFromSession(request);   // Reactive TransactionId
        String tType = getTransactionTypeFromSession(request) != null ? (String) getTransactionTypeFromSession(request) : "";

        if (!Utils.isNullOrEmpty(request.getParameter("transactionId")))
            transactionId = Long.parseLong(request.getParameter("transactionId"));

        if (!Utils.isNullOrEmpty(request.getParameter("pProjectId")))
            pProjectId = Long.parseLong(request.getParameter("pProjectId"));

        if (!Utils.isNullOrEmpty(request.getParameter("rProjectId")))
            rProjectId = Long.parseLong(request.getParameter("rProjectId"));

        String subject = "Transaction Details Information.";
        String from = "sumon050789@gmail.com";
        String[] to = {"sumon050789@gmail.com"};
        String[] ccAddress = {"sumon050789@gmail.com"};//{"tanveer.hasan@dsinv.com", "moshraful.hoque@dsinv.com"};
        String[] attachedFileNames = {"transaction"};
        String[] attachFilesContentTypes = {"application/pdf"};
        String body = "Email body goes here.....";
        Map parameters = new HashMap();

        NewRiskAssessmentTxBean txBean = new NewRiskAssessmentTxBean();


        try {
            txBean.setEmployeeInfoList(getHederAndValueOfPersonInfo(adminJdbcService.getDiscriminatorEmployInfoByTransactionId(transactionId)));
            txBean.setCusromerList(getHederAndValueOfPersonInfo(adminJdbcService.getDiscriminatorCustInfoByTransactionId(transactionId)));
            txBean.setVendorMasterFileList(getHederAndValueOfPersonInfo(adminJdbcService.getDiscriminatorVendorInfoByTransactionId(transactionId)));
            txBean.setAdditionalInfoList(adminJdbcService.getAdditionalInfoListByTransactionId(transactionId));
            txBean.setUserList(adminService.getAllUserList());
            List<Control> controlList = getControlListByTransactionType(tType);

            if (pProjectId != null && pProjectId > 0) {
                ProactiveTransactionCND pTransactionCND = adminService.getProactiveTransactionCNDByProactiveTransactionid(pTransactionId);
                if (pTransactionCND != null) {
                    txBean.setProactiveTransactionCND(pTransactionCND);
                    setBeanProcativeCND(txBean, pTransactionCND);
                }
                txBean.setProactiveTransactionAuditTrialList(adminService.getProactiveTransactionAuditTrialByProactiveTxId(pTransactionId));
                controlListForTable = getControlListForProactiveTable(pTransactionCND, controlList);

            } else if (rProjectId != null && rProjectId > 0) {
                ReactiveTransactionCND rTransactionCND = adminService.getReactiveTransactionCNDByReactiveTransactionid(rTransactionId);
                if (rTransactionCND != null) {
                    txBean.setReactiveTransactionCND(rTransactionCND);
                    setBeanRecativeCND(txBean, rTransactionCND);
                }
                txBean.setProactiveTransactionAuditTrialList(adminService.getReactiveTransactionAuditTrialByReactiveTxId(rTransactionId));
                controlListForTable = getControlListForReactiveTable(rTransactionCND, controlList);
            }

            txBean.setControlListForTable(controlListForTable);

            // transaction details part
            List<TransactionDetailVO> txDetailList = new ArrayList<TransactionDetailVO>();
            TransactionDetailVO txVO = new TransactionDetailVO();
            txVO.setTxDetailComment(txBean.getDecisionComment());
            if ("Irrelevant/Non Responsive".equals(txBean.getDecision()))
                txVO.setNonResponsive("X");
            else if ("Further Review Required".equals(txBean.getDecision()))
                txVO.setFurtherReview("X");
            else
                txVO.setAdditionalInfo("X");

            txVO.setEmplyeeInfoList(txBean.getEmployeeInfoList());
            txVO.setCustomerInfoList(txBean.getCusromerList());
            txVO.setVendorInfoList(txBean.getVendorMasterFileList());
            txVO.setAdditionalInfoList(txBean.getAdditionalInfoList());
            txDetailList.add(txVO);
            txBean.setTransactionDetails(txDetailList);

            // control detail part
            List<ControlVO> pdfControls = new ArrayList<ControlVO>();
            ControlVO cVO = new ControlVO();
            cVO.setControls(txBean.getControlListForTable());
            cVO.setComment(txBean.getControlComment());
            cVO.setInvolvedPerson(txBean.getControlPersonInvolve());
            pdfControls.add(cVO);
            txBean.setControls(pdfControls);

            // Audit Trail detail part
            List<AuditTrailVO> aAuditTrailVOList = new ArrayList<AuditTrailVO>();
            AuditTrailVO aAuditVO = new AuditTrailVO();
            aAuditVO.setAuditTrailList(txBean.getProactiveTransactionAuditTrialList());
            aAuditTrailVOList.add(aAuditVO);
            txBean.setAuditTrails(aAuditTrailVOList);


            List<NewRiskAssessmentTxBean> riskAssessmentList = new ArrayList<NewRiskAssessmentTxBean>();
            riskAssessmentList.add(txBean);

            // PDF Generation Section
            InputStream pdfForm = this.getClass().getClassLoader().getResourceAsStream("/jasper/master_page.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(pdfForm, parameters, new JRBeanCollectionDataSource(riskAssessmentList));
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            byte[][] attachFilesBytes = {pdfData};
            byte[] bytes = Utils.generateEmlFileWithCc(from
                    , to
                    , ccAddress
                    , subject
                    , attachedFileNames
                    , attachFilesBytes
                    , attachFilesContentTypes
                    , body);

            response.setContentType("message/rfc822");
            response.addHeader("Content-Disposition", "attachment; filename=transaction_" + transactionId + ".eml");
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception ex) {
            logger.debug("CERROR:: Failed to send mail. " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    /*
    * Method to print Transaction Detail
    * @param HttpServletRequest request, HttpServletResponse response, Model model, Locale locale
    * @return type String
    */
    @RequestMapping(value = "/riskasst/printTransactionDetail.html", method = RequestMethod.GET)
    public String printPdfFile(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) {

        Long transactionId = null;
        Long pProjectId = null;            // Proactive Project id
        Long rProjectId = null;            // Reactive Project id
        List<Control> controlListForTable = new ArrayList<Control>();
        Long pTransactionId = getProactiveTransactionIdFromSession(request);
        Long rTransactionId = getReactiveTransactionIdFromSession(request);
        String tType = getTransactionTypeFromSession(request) != null ? (String) getTransactionTypeFromSession(request) : "";

        if (!Utils.isNullOrEmpty(request.getParameter("transactionId")))
            transactionId = Long.parseLong(request.getParameter("transactionId"));

        if (!Utils.isNullOrEmpty(request.getParameter("pProjectId")))
            pProjectId = Long.parseLong(request.getParameter("pProjectId"));

        if (!Utils.isNullOrEmpty(request.getParameter("rProjectId")))
            rProjectId = Long.parseLong(request.getParameter("rProjectId"));

        Map parameters = new HashMap();
        NewRiskAssessmentTxBean txBean = new NewRiskAssessmentTxBean();


        try {
            txBean.setEmployeeInfoList(getHederAndValueOfPersonInfo(adminJdbcService.getDiscriminatorEmployInfoByTransactionId(transactionId)));
            txBean.setCusromerList(getHederAndValueOfPersonInfo(adminJdbcService.getDiscriminatorCustInfoByTransactionId(transactionId)));
            txBean.setVendorMasterFileList(getHederAndValueOfPersonInfo(adminJdbcService.getDiscriminatorVendorInfoByTransactionId(transactionId)));
            txBean.setAdditionalInfoList(adminJdbcService.getAdditionalInfoListByTransactionId(transactionId));
            txBean.setUserList(adminService.getAllUserList());
            List<Control> controlList = getControlListByTransactionType(tType);

            if (pProjectId != null && pProjectId > 0) {
                ProactiveTransactionCND pTransactionCND = adminService.getProactiveTransactionCNDByProactiveTransactionid(pTransactionId);
                if (pTransactionCND != null) {
                    txBean.setProactiveTransactionCND(pTransactionCND);
                    setBeanProcativeCND(txBean, pTransactionCND);
                }
                txBean.setProactiveTransactionAuditTrialList(adminService.getProactiveTransactionAuditTrialByProactiveTxId(pTransactionId));
                controlListForTable = getControlListForProactiveTable(pTransactionCND, controlList);

            } else if (rProjectId != null && rProjectId > 0) {
                ReactiveTransactionCND rTransactionCND = adminService.getReactiveTransactionCNDByReactiveTransactionid(rTransactionId);
                if (rTransactionCND != null) {
                    txBean.setReactiveTransactionCND(rTransactionCND);
                    setBeanRecativeCND(txBean, rTransactionCND);
                }
                txBean.setProactiveTransactionAuditTrialList(adminService.getReactiveTransactionAuditTrialByReactiveTxId(rTransactionId));
                controlListForTable = getControlListForReactiveTable(rTransactionCND, controlList);
            }

            txBean.setControlListForTable(controlListForTable);

            // transaction details part
            List<TransactionDetailVO> txDetailList = new ArrayList<TransactionDetailVO>();
            TransactionDetailVO txVO = new TransactionDetailVO();
            txVO.setTxDetailComment(txBean.getDecisionComment());
            if ("Irrelevant/Non Responsive".equals(txBean.getDecision()))
                txVO.setNonResponsive("X");
            else if ("Further Review Required".equals(txBean.getDecision()))
                txVO.setFurtherReview("X");
            else
                txVO.setAdditionalInfo("X");

            txVO.setEmplyeeInfoList(txBean.getEmployeeInfoList());
            txVO.setCustomerInfoList(txBean.getCusromerList());
            txVO.setVendorInfoList(txBean.getVendorMasterFileList());
            txVO.setAdditionalInfoList(txBean.getAdditionalInfoList());
            txDetailList.add(txVO);
            txBean.setTransactionDetails(txDetailList);

            // control detail part
            List<ControlVO> pdfControls = new ArrayList<ControlVO>();
            ControlVO cVO = new ControlVO();
            cVO.setControls(txBean.getControlListForTable());
            cVO.setComment(txBean.getControlComment());
            cVO.setInvolvedPerson(txBean.getControlPersonInvolve());
            pdfControls.add(cVO);
            txBean.setControls(pdfControls);

            // Audit Trail detail part
            List<AuditTrailVO> aAuditTrailVOList = new ArrayList<AuditTrailVO>();
            AuditTrailVO aAuditVO = new AuditTrailVO();
            aAuditVO.setAuditTrailList(txBean.getProactiveTransactionAuditTrialList());
            aAuditTrailVOList.add(aAuditVO);
            txBean.setAuditTrails(aAuditTrailVOList);


            List<NewRiskAssessmentTxBean> riskAssessmentList = new ArrayList<NewRiskAssessmentTxBean>();
            riskAssessmentList.add(txBean);

            // PDF Generation Section
            InputStream pdfForm = this.getClass().getClassLoader().getResourceAsStream("/jasper/master_page.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(pdfForm, parameters, new JRBeanCollectionDataSource(riskAssessmentList));
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=transaction_" + transactionId + ".pdf");
            response.setContentLength(pdfData.length);
            response.getOutputStream().write(pdfData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception ex) {
            logger.debug("CERROR:: Failed to send mail. " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    /*
    * Method to handle ajaxCall in new risk assesmentment transaction detail page
    * @param HttpServletRequest request, @ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, Model model
    * @return type String
    */
    @RequestMapping(value = "/realtime/ajaxCallForNewRiskAssesment.html", method = RequestMethod.GET)
    String ajaxGetProdCart(HttpServletRequest request, @ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, Model model) {
        try {
            logger.info("--------in AJAX CONTROLLER----------");
            String txId = request.getParameter("txId");
            String transId = request.getParameter("transactionId");
            String projectType = request.getParameter("projectType") != null ? request.getParameter("projectType") : "";
            String decission = request.getParameter("decission");
            String ctrlId = request.getParameter("ctrlId");
            String serial = request.getParameter("serial");
            String pageNo = request.getParameter("pageNo");
            List transactionAuditTrialList = new ArrayList();
            List controlListForTable = new ArrayList();
            List employeInfoList = new ArrayList();
            List customerList = new ArrayList();
            List vendorMasterList = new ArrayList();
            List additionalInfoList = new ArrayList();
            List<TransactionPolicy> policyList = new ArrayList<TransactionPolicy>();
            List assignedEmployeeList= new ArrayList();
            List transactionCommentList = new ArrayList();
            long realTimeProjectId = 0l;
            long transactionId = 0;
            long proactiveProjectId = 0l;
            long reactiveProjectIdOnBean = 0l;

            request.getSession().setAttribute("pageNo",pageNo);
            if (!Utils.isNullOrEmpty(request.getParameter("rtProjectId")))
                realTimeProjectId = Long.parseLong(request.getParameter("rtProjectId"));

             String tType = getTransactionTypeFromSession(request) != null ? (String) getTransactionTypeFromSession(request) : "";
            List userList = adminService.getAllUserList();

             serial = Utils.isEmpty(serial) ? "tr:first" : serial;

            ProactiveController.setSerialNoOnSession(request, serial);
            if (!Utils.isNullOrEmpty(request.getParameter("proactiveProjectId")))
                proactiveProjectId = Long.parseLong(request.getParameter("proactiveProjectId"));

            if (!Utils.isNullOrEmpty(request.getParameter("reactiveProjectId")))
                reactiveProjectIdOnBean = Long.parseLong(request.getParameter("reactiveProjectId"));

            List controlList = getControlListByTransactionType(tType);

            if (newRiskAssessmentTxBean.getProactiveProjectId() > 0) {    // set model for proactive project
                logger.info(" Proactive project block :");
                RealTimeMonitoringController.setProactiveTransactionIdOnSession(request, Long.parseLong(txId.trim()));
                newRiskAssessmentTxBean = new NewRiskAssessmentTxBean();
                long proactiveTransactionId = RealTimeMonitoringController.getProactiveTransactionIdFromSession(request);
                ProactiveTransaction proactiveTransaction = proactiveTransactionId > 0 ? adminService.getProactiveTransactionById(proactiveTransactionId) : new ProactiveTransaction();
                newRiskAssessmentTxBean.setProactiveTransaction(proactiveTransaction);
                tType = proactiveTransaction != null ? proactiveTransaction.getTransaction().getTransactionType() : "";
                controlList = getControlListByTransactionType(tType);
                ProactiveTransactionCND proactiveTransactionCND = proactiveTransactionId > 0 ? adminService.getProactiveTransactionCNDByProactiveTransactionid(proactiveTransactionId) : null;
                if (proactiveTransactionCND != null) {
                    setBeanProcativeCND(newRiskAssessmentTxBean, proactiveTransactionCND);
                    newRiskAssessmentTxBean.setProactiveTransactionCND(proactiveTransactionCND);
                } else {
                    setBeanProactiveCNDEmpty(newRiskAssessmentTxBean);
                }
                transactionAuditTrialList = adminService.getProactiveTransactionAuditTrialByProactiveTxId(proactiveTransactionId);
                policyList = adminService.getTransactionPolicy(proactiveTransaction.getTransaction().getId());
                if (transactionAuditTrialList == null) {
                    transactionAuditTrialList = new ArrayList<ProactiveTransactionAuditTrial>();
                }
                Long trxId = !Utils.isEmpty(transId) ? Long.parseLong(transId) : 0;
                List<SuspiciousTransaction> violatedTrxList = adminService.getViolatedRuleListByTrxId(trxId);
                newRiskAssessmentTxBean.setViolationList(violatedTrxList);
                controlListForTable = getControlListForProactiveTable(proactiveTransactionCND, controlList);
                List<ProactiveTransactionSupportingDocument> proactiveTransactionSupportingDocuments = new ArrayList<ProactiveTransactionSupportingDocument>();
                proactiveTransactionSupportingDocuments = adminService.getProactiveTransactionSupportingDocumentListByTrxId(proactiveTransaction.getId());
                newRiskAssessmentTxBean.setSupportingDocumentList(proactiveTransactionSupportingDocuments);
                newRiskAssessmentTxBean.setProactiveProjectId(proactiveProjectId);
                newRiskAssessmentTxBean.setReactiveProjectId(0);
                newRiskAssessmentTxBean.setRealTimeProjectId(0);

                if (proactiveTransaction != null && proactiveTransaction.getTransaction() != null)
                    transactionId = proactiveTransaction.getTransaction().getId();
                model.addAttribute("proactiveProjectId", proactiveProjectId);
                newRiskAssessmentTxBean.setProactiveTransaction(proactiveTransaction);
                model.addAttribute("assignedEmployee", "0");
            } else if (newRiskAssessmentTxBean.getReactiveProjectId() > 0) {     // set model for Reactive project
                setReactiveTransactionIdOnSession(request, Long.parseLong(txId));
                newRiskAssessmentTxBean = new NewRiskAssessmentTxBean();
                long reactiveTransactionId = getReactiveTransactionIdFromSession(request);
                ReactiveTransaction reactiveTransaction = reactiveTransactionId > 0 ? adminService.getReactiveTransactionById(reactiveTransactionId) : new ReactiveTransaction();
                newRiskAssessmentTxBean.setReactiveTransaction(reactiveTransaction);
                ReactiveTransactionCND reactiveTransactionCND = reactiveTransactionId > 0 ? adminService.getReactiveTransactionCNDByReactiveTransactionid(reactiveTransactionId) : null;
                tType = reactiveTransaction != null ? reactiveTransaction.getTransaction().getTransactionType() : "";
                controlList = getControlListByTransactionType(tType);
                if (reactiveTransactionCND != null) {
                    setBeanRecativeCND(newRiskAssessmentTxBean, reactiveTransactionCND);
                    newRiskAssessmentTxBean.setReactiveTransactionCND(reactiveTransactionCND);
                } else {
                    setBeanProactiveCNDEmpty(newRiskAssessmentTxBean);
                }
                if(reactiveTransaction.getId() > 0 ) {
                    transactionCommentList = adminService.getReactiveTransactionCommentList(reactiveTransaction.getId());
                    if(transactionCommentList != null) {
                        logger.debug("Transaction Comment list size: " + transactionCommentList.size());
                        newRiskAssessmentTxBean.setTransactionCommentList(transactionCommentList);
                    }
                }
                transactionAuditTrialList = adminService.getReactiveTransactionAuditTrialByReactiveTxId(reactiveTransactionId);
                policyList = adminService.getTransactionPolicy(reactiveTransaction.getTransaction().getId());
                if (transactionAuditTrialList == null) {
                    transactionAuditTrialList = new ArrayList<ProactiveTransactionAuditTrial>();
                }
                Long trxId = !Utils.isEmpty(transId) ? Long.parseLong(transId) : 0;
                List<SuspiciousTransaction> violatedTrxList = adminService.getViolatedRuleListByTrxId(trxId);
                newRiskAssessmentTxBean.setViolationList(violatedTrxList);
                controlListForTable = getControlListForReactiveTable(reactiveTransactionCND, controlList);

                List<ReactiveTransactionSupportingDocument> reactiveTransactionSupportingDocuments = new ArrayList<ReactiveTransactionSupportingDocument>();
                reactiveTransactionSupportingDocuments = adminService.getReactiveTransactionSupportingDocumentListByTrxId(reactiveTransaction.getId());
                newRiskAssessmentTxBean.setSupportingDocumentList(reactiveTransactionSupportingDocuments);
                newRiskAssessmentTxBean.setReactiveProjectId(reactiveProjectIdOnBean);
                newRiskAssessmentTxBean.setProactiveProjectId(0);
                newRiskAssessmentTxBean.setRealTimeProjectId(0);
                if (reactiveTransaction != null && reactiveTransaction.getTransaction() != null)
                    transactionId = reactiveTransaction.getTransaction().getId();
                model.addAttribute("reactiveProjectId", reactiveProjectIdOnBean);
                newRiskAssessmentTxBean.setReactiveTransaction(reactiveTransaction);
                model.addAttribute("assignedEmployee", "0");
            } else if (newRiskAssessmentTxBean.getRealTimeProjectId() > 0) {   // set model for Real time Monitoring project

                RealTimeMonitoringController.setRealTimeTransactionIdOnSession(request, Long.parseLong(txId.trim()));

                newRiskAssessmentTxBean = new NewRiskAssessmentTxBean();
                long realTimeTransactionId = RealTimeMonitoringController.getRealTimeTransactionIdFromSession(request);
                RealTimeTransaction realTimeTransaction = realTimeTransactionId > 0 ? adminService.getRealTimeTransactionById(realTimeTransactionId) : new RealTimeTransaction();
                newRiskAssessmentTxBean.setRealTimeTransaction(realTimeTransaction);
                tType = realTimeTransaction != null ? realTimeTransaction.getTransaction().getTransactionType() : "";

                controlList = getControlListByTransactionType(tType);
                RealTimeTransactionCND realTimeTransactionCND = realTimeTransactionId > 0 ? adminService.getRealTimeTransactionCNDByRtTransactionid(realTimeTransactionId) : null;
                if (realTimeTransactionCND != null) {
                    setBeanRealTimeCND(newRiskAssessmentTxBean, realTimeTransactionCND);
                    newRiskAssessmentTxBean.setRealTimeTransactionCND(realTimeTransactionCND);
                } else {
                    setBeanProactiveCNDEmpty(newRiskAssessmentTxBean);
                }
                if(realTimeTransaction.getId() > 0 ) {
                    transactionCommentList = adminService.getRealTimeTranscationCommentList(realTimeTransaction.getId());
                    if(transactionCommentList != null) {
                          logger.debug("Transaction Comment list size: " + transactionCommentList.size());
                          newRiskAssessmentTxBean.setTransactionCommentList(transactionCommentList);
                    }
                }

                transactionAuditTrialList = adminService.getRealTimeTransactionAuditTrialByRtTxId(realTimeTransactionId);
                policyList = adminService.getTransactionPolicy(realTimeTransaction.getTransaction().getId());

                if (transactionAuditTrialList == null) {
                    transactionAuditTrialList = new ArrayList<ProactiveTransactionAuditTrial>();
                }
                Long trxId = !Utils.isEmpty(transId) ? Long.parseLong(transId) : 0;
                List<SuspiciousTransaction> violatedTrxList = adminService.getViolatedRuleListByTrxId(trxId);
                newRiskAssessmentTxBean.setViolationList(violatedTrxList);
                controlListForTable = getControlListForRealTimeTable(realTimeTransactionCND, controlList);
                List<RealTimeTransactionSupportingDocument> realTimeTransactionSupportingDocumentsList = new ArrayList<RealTimeTransactionSupportingDocument>();
                realTimeTransactionSupportingDocumentsList = adminService.getRealTimeTransactionSupportingDocumentListByTrxId(realTimeTransaction.getId());
                newRiskAssessmentTxBean.setSupportingDocumentList(realTimeTransactionSupportingDocumentsList);
                assignedEmployeeList = adminJdbcService.getAssignedEmployeeList(realTimeTransaction.getId());
                newRiskAssessmentTxBean.setAssignedEmployeeList(assignedEmployeeList != null ? assignedEmployeeList : new ArrayList());
                newRiskAssessmentTxBean.setProactiveProjectId(0);
                newRiskAssessmentTxBean.setReactiveProjectId(0);
                newRiskAssessmentTxBean.setRealTimeProjectId(realTimeProjectId);
                if (realTimeTransaction != null && realTimeTransaction.getTransaction() != null)
                    transactionId = realTimeTransaction.getTransaction().getId();
                model.addAttribute("realTimeProjectId", realTimeProjectId);
                newRiskAssessmentTxBean.setRealTimeTransaction(realTimeTransaction);
                model.addAttribute("assignedEmployee", "1");

            }
            // this list are common for all type porject
            newRiskAssessmentTxBean.setControlListForTable(controlListForTable);
            newRiskAssessmentTxBean.setProactiveTransactionAuditTrialList(transactionAuditTrialList);        // this list are common for all type porject
            newRiskAssessmentTxBean.setUserList(userList);

            /* for Employee info, customer & Vendor master file TAB. Added By habib*/
            employeInfoList = adminJdbcService.getDiscriminatorEmployInfoByTransactionId(transactionId);
            customerList = adminJdbcService.getDiscriminatorCustInfoByTransactionId(transactionId);
            vendorMasterList = adminJdbcService.getDiscriminatorVendorInfoByTransactionId(transactionId);
            additionalInfoList = adminJdbcService.getAdditionalInfoListByTransactionId(transactionId);

            newRiskAssessmentTxBean.setEmployeeInfoList(employeInfoList);
            newRiskAssessmentTxBean.setCusromerList(customerList);
            newRiskAssessmentTxBean.setVendorMasterFileList(vendorMasterList);
            newRiskAssessmentTxBean.setAdditionalInfoList(additionalInfoList);
//            newRiskAssessmentTxBean.setControlId(Long.parseLong(ctrlId));
            newRiskAssessmentTxBean.setPolicyList(policyList);

            /*end*/
            model.addAttribute("NewRiskAssessmentTxBean", newRiskAssessmentTxBean);
            model.addAttribute("txId", txId);
            model.addAttribute("transactionId", transId);
            model.addAttribute("decission", decission);
            model.addAttribute("ctrlId", ctrlId);
            model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
            model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);


        } catch (Exception ex) {
            logger.error("Ajax Call New Riskassesment Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("--------in AJAX CONTROLLER end----------");
        return "testAjax";
    }

    @ModelAttribute("conrolSel")
    public List<String> getControlSelList() {
        List<String> controlProList = new ArrayList<String>();
        controlProList.add("    ------------");
        controlProList.add(Utils.getMessageBundlePropertyValue("yes"));
        controlProList.add(Utils.getMessageBundlePropertyValue("no"));
        controlProList.add(Utils.getMessageBundlePropertyValue("n.a"));
        controlProList.add(Utils.getMessageBundlePropertyValue("see.comment"));
        return controlProList;
    }

    /*
    * Method to set country name to session
    * @param HttpServletRequest request
    * @return type Object
    */
    private Object getCountryNameFromSession(HttpServletRequest request) {
        return request.getSession().getAttribute("cName");
    }

    /*
    * Method to set transactionType to session
    * @param HttpServletRequest request
    * @return type Object
    */
    private Object getTransactionTypeFromSession(HttpServletRequest request) {
        return request.getSession().getAttribute("transactionType");
    }

    /*
    * Method to calculate weighted value
    * @param ProactiveBlockWeightRatio proactiveBlockWeightRatio, ProactiveBlockWeight proactiveBlockWeight
    * @return type double
    */
    private double getWeightedValue(ProactiveBlockWeightRatio proactiveBlockWeightRatio, ProactiveBlockWeight proactiveBlockWeight) {
        return proactiveBlockWeight.getCpiScore() * proactiveBlockWeightRatio.getCpiScore() / 100 +
                proactiveBlockWeight.getGovernmentInteraction() * proactiveBlockWeightRatio.getGovernmentInteraction() / 100 +
                proactiveBlockWeight.getNatureOfBusinessOperations() * proactiveBlockWeightRatio.getNatureOfBusinessOperations() / 100 +
                proactiveBlockWeight.getRevenues() * proactiveBlockWeightRatio.getRevenues() / 100 +
                proactiveBlockWeight.getSalesModelRelationships() * proactiveBlockWeightRatio.getSalesModelRelationships() / 100;
    }

    /*
    * Method to handle new Risk Assessmentsummary View
    * @param HttpServletRequest request,@ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, BindingResult result,Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/newRiskAssessmentsummaryView.html", method = RequestMethod.POST)
    public String summaryViewPost(HttpServletRequest request,@ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, BindingResult result,
                                    Model model) throws IOException {
        logger.debug("Save New RiskAssessment Summary Start.");
        String uploadPath = "uploadFiles";
        String fileIconPath = "";
        String status = "success";
        String auditTrialMessageForCND = "";
        String auditTrialMessageForComment = "";
        String decissionTaken = "";
        String controlIds = "";
        ProactiveProject proactiveProject = null;
        String tType = "";
        String message="";
        long proactiveProjectId = newRiskAssessmentTxBean != null ? newRiskAssessmentTxBean.getProactiveProjectId() : 0;
        List<String> auditTrialMessageList = new ArrayList<String>();
        String controlIdFromUrlParam = !isEmpty(request.getParameter("ctrlId")) ? request.getParameter("ctrlId") :"0";
        String myRealTimeSummaryView = request.getParameter("myRealTimeSummaryView") != null ? request.getParameter("myRealTimeSummaryView") : "0";
        ProactiveTransactionCND proactiveTransactionCND = new ProactiveTransactionCND();
        MultipartFile multipartFileForSupportingDocument = newRiskAssessmentTxBean.getFileDataForSupportingDocument();
        String uploadPathForSupportingDocument = "supportingDocument";
        String orginalNameForSupportingDocument = multipartFileForSupportingDocument.getOriginalFilename();
        String filePathForSupportingDocument = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalNameForSupportingDocument);
        File dirForSupportingDocument = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument);
        File policyFolder;
        String policyPath = System.getProperty("user.home") + File.separator + uploadPath + File.separator + Constants.TRANSACTION_POLICY_PATH;
        String fileIconPathForSupportingDocument = "";
        String orginalPolicyPath;
        try {
            if (!dirForSupportingDocument.exists()) {
                dirForSupportingDocument.mkdirs();
            }
            policyFolder = new File(policyPath);
            if(!policyFolder.exists()) {
                policyFolder.mkdirs();
            }
            fileIconPathForSupportingDocument = getFileIconPath(multipartFileForSupportingDocument);
            if (!Utils.isEmpty(request.getParameter("proactiveProjectId"))) {
                proactiveProject = (ProactiveProject) getProject(request.getParameter("proactiveProjectId"), Entity.PROACTIVE_PROJECT.getValue());
            } else if (request.getSession().getAttribute("proactiveProject") != null) {
                proactiveProject = (ProactiveProject) getProjectFromSession(request, "proactiveProject");
            } else {
                return "redirect:./weightedScreen.html";
            }
            long txId = getProactiveTransactionIdFromSession(request);
            setProactiveTransactionIdOnSession(request, txId);
            //-------- control+decission(Transaction Details + Control tab)  part handling --------//
            ProactiveTransaction proactiveTransaction = new ProactiveTransaction();
            if (newRiskAssessmentTxBean != null) {
                proactiveTransactionCND = newRiskAssessmentTxBean.getProactiveTransactionCND();
                proactiveTransaction = newRiskAssessmentTxBean.getProactiveTransaction() != null ? newRiskAssessmentTxBean.getProactiveTransaction() : new ProactiveTransaction();
                if (newRiskAssessmentTxBean.getTransactionPolicy() != null) {
                    if(!Utils.isEmpty(newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename())){
                    orginalPolicyPath = policyPath + File.separator + "" + getTodaysDate().getTime() + "_" + newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename();
                    newRiskAssessmentTxBean.getTransactionPolicy().transferTo(new File(orginalPolicyPath));

                    TransactionPolicy aPolicy = new TransactionPolicy();
                    aPolicy.setUploadedBy(getLoggedUserName());
                    aPolicy.setUploaded(getTodaysDate());
                    aPolicy.setComment(newRiskAssessmentTxBean.getPolicyComment());
                    aPolicy.setFileIconLocation(ProactiveController.getFileIconPath(newRiskAssessmentTxBean.getTransactionPolicy()));
                    aPolicy.setFileName(newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename());
                    aPolicy.setFileLocation(orginalPolicyPath);
                    aPolicy.setTransaction(newRiskAssessmentTxBean.getProactiveTransaction().getTransaction());
                    adminService.save(aPolicy);
                    auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document")
                            + " '" + newRiskAssessmentTxBean.getTransactionPolicy().getOriginalFilename() + "' "
                            + Utils.getMessageBundlePropertyValue("auditTrial.policy.upload"));
                    adminService.saveProactiveTrxAuditTrial(proactiveTransaction, auditTrialMessageList);
                    }
                }
            }
            tType = proactiveTransaction != null ? proactiveTransaction.getTransaction().getTransactionType() : "";
            List<Control> controlList = adminService.getControlListByTransactionType(tType);
            int index = 0;
            for (Control control : controlList) {
                String checkboxStatus = request.getParameter("contId" + index);
                controlIds = controlIds + "," + control.getId() + ":" + checkboxStatus.charAt(checkboxStatus.length() - 1);
                String previousCheckboxStatus = request.getParameter("old_" + control.getId() + "");
                message = message + RealTimeMonitoringController.getAuditTrialMsgForControlAction(control, previousCheckboxStatus, checkboxStatus.charAt(checkboxStatus.length() - 1) + "");
                index++;
            }
            auditTrialMessageList.add(message);
            controlIds = isEmpty(controlIds) ? "" : controlIds.substring(1, controlIds.length());
            decissionTaken = request.getParameter("radioDecissionArea") != null ? request.getParameter("radioDecissionArea") : "";
            proactiveTransactionCND.setControlIds(controlIds);
            proactiveTransactionCND.setDecision(decissionTaken);
            if (newRiskAssessmentTxBean != null) {
                proactiveTransactionCND.setProactiveTransaction(newRiskAssessmentTxBean.getProactiveTransaction());
                proactiveTransactionCND.setControlPersonInvolve(newRiskAssessmentTxBean.getControlPersonInvolve());
                proactiveTransactionCND.setDecisionComment(newRiskAssessmentTxBean.getDecisionComment());
                proactiveTransactionCND.setDecisionEmailAddress(newRiskAssessmentTxBean.getDecisionEmailAddress());
                proactiveTransactionCND.setControlComment(newRiskAssessmentTxBean.getControlComment());
                String prevControlComment = newRiskAssessmentTxBean.getPreviousControlComment();
                String currentControlComment = newRiskAssessmentTxBean.getControlComment();
                String prevDecisionComment = newRiskAssessmentTxBean.getPreviousDecissionComment();
                String currentDecisionComment = newRiskAssessmentTxBean.getDecisionComment();
                auditTrialMessageList.add(RealTimeMonitoringController.getControlCommentAction(prevDecisionComment, currentDecisionComment, "decision"));
                auditTrialMessageList.add(RealTimeMonitoringController.getControlCommentAction(prevControlComment, currentControlComment, "control"));

            }
            if (!Utils.getMessageBundlePropertyValue("newRiskAssessmentSummary.additionalInformationRequired").equals(decissionTaken)) {
                proactiveTransactionCND.setDecisionEmailAddress(Constants.EMPTY_STRING);
            }
            adminService.saveOrUpdateForAnyObject(proactiveTransactionCND);
            if (!isEmpty(decissionTaken)) {
                if (isEmpty(newRiskAssessmentTxBean.getPreviousDecission()))
                    auditTrialMessageList.add(getMessageBundlePropertyValue("checked.decision") + " '" + decissionTaken + "'.");
                else if (!isEmpty(newRiskAssessmentTxBean.getPreviousDecission()) && !newRiskAssessmentTxBean.getPreviousDecission().equals(decissionTaken))
                    auditTrialMessageList.add(getMessageBundlePropertyValue("unChecked.decision") + " '" + newRiskAssessmentTxBean.getPreviousDecission()
                            + "' and " + getMessageBundlePropertyValue("checked.decision") + " '" + decissionTaken + "'.");
            }
            adminService.saveProactiveTrxAuditTrial(newRiskAssessmentTxBean.getProactiveTransaction(), auditTrialMessageList);
            auditTrialMessageList = new ArrayList<String>();
            if (multipartFileForSupportingDocument != null) {
                ProactiveTransactionSupportingDocument proactiveTransactionSupportingDocument = new ProactiveTransactionSupportingDocument();
                try {
                    if (!Utils.isEmpty(orginalNameForSupportingDocument))
                        multipartFileForSupportingDocument.transferTo(new File(filePathForSupportingDocument));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                proactiveTransactionSupportingDocument.setAuthor(Utils.getLoggedUserName());
                proactiveTransactionSupportingDocument.setEntryTime(Utils.getTodaysDate());
                proactiveTransactionSupportingDocument.setFileName((orginalNameForSupportingDocument));
                proactiveTransactionSupportingDocument.setFileIconLocation(Utils.isEmpty(orginalNameForSupportingDocument) ? "" : fileIconPathForSupportingDocument);
                proactiveTransactionSupportingDocument.setFileLocation(filePathForSupportingDocument);
                proactiveTransactionSupportingDocument.setProactiveTransaction(newRiskAssessmentTxBean.getProactiveTransaction());
                proactiveTransactionSupportingDocument.setComment(newRiskAssessmentTxBean.getSupportingDocumentComment());
                if (!isEmpty(orginalNameForSupportingDocument)) {
                    adminService.save(proactiveTransactionSupportingDocument);
                    auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document")
                            + " '" + orginalNameForSupportingDocument + "' "
                            + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.supportingDoc"));
                    adminService.saveProactiveTrxAuditTrial(proactiveTransaction, auditTrialMessageList);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug("CERROR:: New Risk Assessment Summary View Exception " + ex);
        }
        logger.debug(" Save New RiskAssessment Summary end.");
/*
        if("1".equals(myRealTimeSummaryView))
        return "redirect:./newRiskAssessmentsummaryView.html?proactiveProjectId=" + proactiveProjectId + (!"0".equals(controlIdFromUrlParam) ? "&controlIds=" + controlIdFromUrlParam : "");
        else
*/
        return "redirect:./newRiskAssessmentsummaryView.html?proactiveProjectId=" + proactiveProjectId + (!"0".equals(controlIdFromUrlParam) ? "&controlIds=" + controlIdFromUrlParam : "");
    }


    /*
    * Method to set ProactiveTransaction as a model
    * @param Model model, Map map, String emptyString
    * @return type void
    */
    public static void setProactiveTransaction(Model model, Map map, String emptyString) {
        model.addAttribute("txId", map.get("trxId") != null ? map.get("trxId") : emptyString);
        model.addAttribute("dt", map.get("createdDate") != null ? (Date) map.get("createdDate") : "");
        model.addAttribute("amn", map.get("amount") != null ? "$" + map.get("amount") : emptyString);
        model.addAttribute("rgnName", map.get("regionName") != null ? map.get("regionName") : emptyString);
        model.addAttribute("dc", map.get("dc") != null ? map.get("dc") : "John Smith");
        model.addAttribute("app", map.get("app") != null ? map.get("app") : "Alex Liu");
        model.addAttribute("dtOfApp", map.get("dtOfApp") != null ? map.get("createdDate") : "");
        model.addAttribute("decission", map.get("decision") != null ? map.get("decission") : emptyString);
    }

    /*
    * Method to set Model With Empty String
    * @param Model model, Map map, String emptyString
    * @return type void
    */
    public static void setModelWithEmptyString(Model model, String emptyString) {
        model.addAttribute("NewRiskAssessmentTxBean", new NewRiskAssessmentTxBean());
        model.addAttribute("txId", null);
        model.addAttribute("amn", emptyString);
        model.addAttribute("rgnName", emptyString);
        model.addAttribute("dc", emptyString);
        model.addAttribute("app", emptyString);
        model.addAttribute("decission", emptyString);
    }

    /*
    * Method to set Proactive Transaction Id On Session
    * @param HttpServletRequest request, Long proactiveTransactionId
    * @return type void
    */
    public static void setProactiveTransactionIdOnSession(HttpServletRequest request, Long proactiveTransactionId) {
        request.getSession().setAttribute("proactiveTransactionId", proactiveTransactionId);
    }

    /*
    * Method to get Proactive Transaction Id From Session
    * @param HttpServletRequest request
    * @return type Long
    */
    public static Long getProactiveTransactionIdFromSession(HttpServletRequest request) {
        return request.getSession().getAttribute("proactiveTransactionId") != null ? (Long) request.getSession().getAttribute("proactiveTransactionId") : 0;
    }

    /*
    * Method to set Serial No On Session
    * @param HttpServletRequest request, int serialNo
    * @return type void
    */
    public static void setSerialNoOnSession(HttpServletRequest request, String serialNo) {
        request.getSession().setAttribute("serialNo", serialNo);
    }

    /*
    * Method to get Serial No From Session
    * @param HttpServletRequest request
    * @return type Integer
    */
    public static String getSerialNoFromSession(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("serialNo");
    }

    /*
    * Method to get First Project Transaction Map
    * @param List proactiveTransactionList
    * @return type Map
    */
    private Map getFirstProjectTransactionMap(List proactiveTransactionList) {
        return (proactiveTransactionList != null && proactiveTransactionList.size() > 0) ? (Map) proactiveTransactionList.get(0) : new HashMap();
    }

    /*
    * Method to set Transaction Type On Session
    * @param HttpServletRequest request, String transactionType
    * @return type void
    */
    private void setTransactionTypeOnSession(HttpServletRequest request, String transactionType) {
        request.getSession().setAttribute("transactionType", transactionType);
    }

    /*
    * Method to set Country Name On Session
    * @param HttpServletRequest request, String country
    * @return type void
    */
    private void setCountryNameOnSession(HttpServletRequest request, String country) {
        request.getSession().setAttribute("cName", country);
    }

    /*
    * Method to set Country Name On Session
    * @param HttpServletRequest request, String country
    * @return type void
    */
    private Object getProjectFromSession(HttpServletRequest request, String projectName) {
        return request.getSession().getAttribute(projectName);
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
    * Method to get Control List By Transaction Type
    * @param String transactionType
    * @return type List<Control>
    */
    private List<Control> getControlListByTransactionType(String transactionType) {
        List<Control> controlList = adminService.getControlListByTransactionType(transactionType);
        return controlList != null ? controlList : new ArrayList<Control>();
    }

    /*
    * Method to set Transaction Type Of Sub Header
    * @param HttpServletRequest request, String transactionType
    * @return type void
    */
    private void setTransactionTypeOfSubHeader(HttpServletRequest request, String transactionType) {
        request.getSession().setAttribute("transactionTypeForSubHeader", transactionType);
    }

    /*
    * Method to get Control List For Proactive Table
    * @param ProactiveTransactionCND proactiveTransactionCND, List<Control> controlList
    * @return type List<Control>
    */
    private List<Control> getControlListForProactiveTable(ProactiveTransactionCND proactiveTransactionCND, List<Control> controlList) {
        List<Control> controlListForTable = new ArrayList<Control>();
        if (proactiveTransactionCND != null) {
            String[] controlIds = proactiveTransactionCND.getControlIds().split(",");
            setControlOption(controlList, controlListForTable, controlIds);
        } else {
            setControlInActive(controlList, controlListForTable);
        }
        return controlListForTable;
    }

    /*
    * Method to get Control List For Real Time Table
    * @param RealTimeTransactionCND realTimeTransactionCND, List<Control> controlList
    * @return type List<Control>
    */
    private List<Control> getControlListForRealTimeTable(RealTimeTransactionCND realTimeTransactionCND, List<Control> controlList) {
        List<Control> controlListForTable = new ArrayList<Control>();
        if (realTimeTransactionCND != null) {
            String[] controlIds = realTimeTransactionCND.getControlIds().split(",");
            setControlOption(controlList, controlListForTable, controlIds);
        } else {
            setControlInActive(controlList, controlListForTable);
        }

        return controlListForTable;
    }

    /*
    * Method to set Control as Active mode
    * @param List<Control> controlList, List<Control> controlListForTable, String[] controlIds
    * @return type void
    */
    private void setControlOption(List<Control> controlList, List<Control> controlListForTable, String[] controlIds) {
        for (Control control : controlList) {
            for (int i = 0; i < controlIds.length; i++) {
                String[] idAndOpt = controlIds[i].split(":");
                if (control.getId() == Utils.getStringToInteger(idAndOpt[0])) {
                    control.setOptionValue(Utils.getStringToInteger(idAndOpt[1]));
                }
            }

            controlListForTable.add(control);
        }
    }

    /*
    * Method to get Control List For Reactive Table
    * @param ReactiveTransactionCND reactiveTransactionCND, List<Control> controlList
    * @return type List<Control>
    */
    private List<Control> getControlListForReactiveTable(ReactiveTransactionCND reactiveTransactionCND, List<Control> controlList) {
        List<Control> controlListForTable = new ArrayList<Control>();
        if (reactiveTransactionCND != null) {
            String[] controlIds = reactiveTransactionCND.getControlIds().split(",");
            setControlOption(controlList, controlListForTable, controlIds);
        } else {
            setControlInActive(controlList, controlListForTable);
        }
        return controlListForTable;
    }

    /*
    * Method to set Control InActive mode
    * @param ReactiveTransactionCND reactiveTransactionCND, List<Control> controlList
    * @return type List<Control>
    */
    private void setControlInActive(List<Control> controlList, List<Control> controlListForTable) {
        for (Control control1 : controlList) {
            control1.setOptionValue(0);
            controlListForTable.add(control1);
        }
    }

    /*
    * Method to set Control as Active mode
    * @param List<Control> controlList, List<Control> controlListForTable, String[] controlIds
    * @return type void
    */
    private void setControlActive(List<Control> controlList, List<Control> controlListForTable, String[] controlIds) {
        for (Control control : controlList) {
            control.setActive(false);
            for (int i = 0; i < controlIds.length; i++) {
                if (control.getId() == Long.parseLong(Utils.isEmpty(controlIds[i]) ? "0" : controlIds[i])) {
                    control.setActive(true);
                }
            }
            controlListForTable.add(control);
        }
    }

    /*
    * Method to set Bean Proactive CND Empty
    * @param NewRiskAssessmentTxBean newRiskAssessmentTxBean
    * @return type void
    */
    public static void setBeanProactiveCNDEmpty(NewRiskAssessmentTxBean newRiskAssessmentTxBean) {
        newRiskAssessmentTxBean.setPreviousControlsIds(Constants.EMPTY_STRING);
        newRiskAssessmentTxBean.setControlComment(Constants.EMPTY_STRING);
        newRiskAssessmentTxBean.setControlPersonInvolve(Constants.EMPTY_STRING);
        newRiskAssessmentTxBean.setDecision(Constants.EMPTY_STRING);
        newRiskAssessmentTxBean.setDecisionComment(Constants.EMPTY_STRING);
        newRiskAssessmentTxBean.setDecisionEmailAddress(Constants.EMPTY_STRING);
        newRiskAssessmentTxBean.setPreviousDecission(Constants.EMPTY_STRING);
    }

    /*
    * Method to set Bean Reactive CND Empty
    * @param NewRiskAssessmentTxBean newRiskAssessmentTxBean
    * @return type void
    */
    private void setBeanReactiveCNDEmpty(NewRiskAssessmentTxBean newRiskAssessmentTxBean) {
        setBeanProactiveCNDEmpty(newRiskAssessmentTxBean);
    }

    /*
    * Method to set Bean Proactive CND Empty
    * @param NewRiskAssessmentTxBean newRiskAssessmentTxBean, ProactiveTransactionCND proactiveTransactionCND
    * @return type void
    */
    public static void setBeanProcativeCND(NewRiskAssessmentTxBean newRiskAssessmentTxBean, ProactiveTransactionCND proactiveTransactionCND) {
        newRiskAssessmentTxBean.setPreviousControlsIds(proactiveTransactionCND.getControlIds());
        newRiskAssessmentTxBean.setControlComment(proactiveTransactionCND.getControlComment());
        newRiskAssessmentTxBean.setControlPersonInvolve(proactiveTransactionCND.getControlPersonInvolve());
        newRiskAssessmentTxBean.setDecision(proactiveTransactionCND.getDecision());
        newRiskAssessmentTxBean.setDecisionComment(proactiveTransactionCND.getDecisionComment());
        newRiskAssessmentTxBean.setDecisionEmailAddress(proactiveTransactionCND.getDecisionEmailAddress());
        newRiskAssessmentTxBean.setPreviousDecission(proactiveTransactionCND.getDecision());
        newRiskAssessmentTxBean.setPreviousControlComment(proactiveTransactionCND.getControlComment());
        newRiskAssessmentTxBean.setPreviousDecissionComment(proactiveTransactionCND.getDecisionComment());
    }

    /*
    * Method to set Bean Recative CND
    * @param NewRiskAssessmentTxBean newRiskAssessmentTxBean, ReactiveTransactionCND reactiveTransactionCND
    * @return type void
    */
    public static void setBeanRecativeCND(NewRiskAssessmentTxBean newRiskAssessmentTxBean, ReactiveTransactionCND reactiveTransactionCND) {
        newRiskAssessmentTxBean.setPreviousControlsIds(reactiveTransactionCND.getControlIds());
        newRiskAssessmentTxBean.setControlComment(reactiveTransactionCND.getControlComment());
        newRiskAssessmentTxBean.setControlPersonInvolve(reactiveTransactionCND.getControlPersonInvolve());
        newRiskAssessmentTxBean.setDecision(reactiveTransactionCND.getDecision());
//        newRiskAssessmentTxBean.setDecisionComment(reactiveTransactionCND.getDecisionComment());
        newRiskAssessmentTxBean.setDecisionEmailAddress(reactiveTransactionCND.getDecisionEmailAddress());
        newRiskAssessmentTxBean.setPreviousDecission(reactiveTransactionCND.getDecision());
        newRiskAssessmentTxBean.setPreviousControlComment(reactiveTransactionCND.getControlComment());
        newRiskAssessmentTxBean.setPreviousDecissionComment(reactiveTransactionCND.getDecisionComment());

    }

    /*
    * Method to set Bean Real Time CND
    * @param NewRiskAssessmentTxBean newRiskAssessmentTxBean, RealTimeTransactionCND realTimeTransactionCND
    * @return type void
    */
    public static void setBeanRealTimeCND(NewRiskAssessmentTxBean newRiskAssessmentTxBean, RealTimeTransactionCND realTimeTransactionCND) {
        newRiskAssessmentTxBean.setPreviousControlsIds(realTimeTransactionCND.getControlIds());
        newRiskAssessmentTxBean.setControlComment(realTimeTransactionCND.getControlComment());
        newRiskAssessmentTxBean.setPreviousControlComment(realTimeTransactionCND.getControlComment());
//        newRiskAssessmentTxBean.setPreviousDecissionComment(realTimeTransactionCND.getDecisionComment());
        newRiskAssessmentTxBean.setControlPersonInvolve(realTimeTransactionCND.getControlPersonInvolve());
        newRiskAssessmentTxBean.setDecision(realTimeTransactionCND.getDecision());
//        newRiskAssessmentTxBean.setDecisionComment(realTimeTransactionCND.getDecisionComment());
        newRiskAssessmentTxBean.setDecisionEmailAddress(realTimeTransactionCND.getDecisionEmailAddress());
        newRiskAssessmentTxBean.setPreviousDecission(realTimeTransactionCND.getDecision());
    }

    /*
    * Method to get File Icon Path
    * @param MultipartFile multipartFile
    * @return type String
    */
    public static String getFileIconPath(MultipartFile multipartFile) {
        String fileIconPath;
        String fileType = "";
        fileType = multipartFile.getContentType();
        if ("image/png".equals(fileType)) {
            fileIconPath = "/resources/images/png.png";
        } else if ("image/gif".equals(fileType)) {
            fileIconPath = "/resources/images/gif.png";
        } else if ("image/jpeg".equals(fileType)) {
            fileIconPath = "/resources/images/jpeg.png";
        } else if ("application/zip".equals(fileType) || "application/file".equals(fileType) || "application/x-gzip".equals(fileType)) {
            fileIconPath = "/resources/images/zip-file-icon.jpg";
        } else if ("application/x-rar-compressed".equals(fileType)) {
            fileIconPath = "/resources/images/rar.png";
        } else if ("application/pdf".equals(fileType)) {
            fileIconPath = "/resources/images/pdf.png";
        } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(fileType)) {  //docx
            fileIconPath = "/resources/images/docx.png";
        } else if ("application/msword".equals(fileType)) {    //doc
            fileIconPath = "/resources/images/docx.png";
        } else if ("audio/".contains(fileType)) {              //mpeg
            fileIconPath = "/resources/images/wma.png";
        } else if ("application/pptx".equals(fileType)) {      //mpeg
            fileIconPath = "/resources/images/pptx.png";
        } else if ("application/vnd.ms-excel".equals(fileType)) {
            fileIconPath = "/resources/images/xlsx.png";
        } else if ("application/vnd.ms-powerpoint".equals(fileType)) {
            fileIconPath = "/resources/images/pptx.png";
        } else if ("text/html".equals(fileType)) {
            fileIconPath = "/resources/images/html.png";
        } else if ("image/bmp".equals(fileType)) {
            fileIconPath = "/resources/images/bmp.png";
        } else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(fileType)) {
            fileIconPath = "/resources/images/xlsx.png";
        } else if (fileType.contains("video/")) {
            fileIconPath = "/resources/images/video.jpeg";
        } else {
            fileIconPath = "/resources/images/notepad-icon-.png";
        }
        logger.debug("File Type: " + fileType);
        logger.debug("File Icon Path: " + fileIconPath);
        return fileIconPath;
    }

    /*
    * Method to show Supporting Document by ajax call
    * @param HttpServletRequest request, @ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean,HttpServletResponse response, Model model
    * @return type void
    */
    @RequestMapping(value = "/riskasst/supportingDocumentShow.html", method = RequestMethod.GET)
    public void ajaxSupportingDocumentShow(HttpServletRequest request, @ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean,
                                           HttpServletResponse response, Model model) throws IOException {
        logger.debug(" Supporting Document Download Start :");
        String supportingDocId = "";
        String fileName = "";
        String filePath = "";
        ProactiveTransactionSupportingDocument proactiveTransactionSupportingDocument;
        ReactiveTransactionSupportingDocument reactiveTransactionSupportingDocument;
        RealTimeTransactionSupportingDocument realTimeTransactionSupportingDocument;
        logger.debug(" newRiskAssessmentTxBean  :" + newRiskAssessmentTxBean.getProactiveProjectId());
        try {
            supportingDocId = request.getParameter("supportingDocumentId");
            Long proactiveProjectId = newRiskAssessmentTxBean != null ? newRiskAssessmentTxBean.getProactiveProjectId() : 0;
            Long reactiveProjectId = newRiskAssessmentTxBean != null ? newRiskAssessmentTxBean.getReactiveProjectId() : 0;
            Long realTimeProjectId = newRiskAssessmentTxBean != null ? newRiskAssessmentTxBean.getRealTimeProjectId() : 0;
            logger.debug(" Proactive project id  :" + proactiveProjectId);
            logger.debug(" Reactive project id :" + reactiveProjectId);
            logger.debug(" Realtime project id :" + realTimeProjectId);
            if (proactiveProjectId > 0) {    // get supporting document from proactive transaction SupportingDocument
                logger.debug(" Proactive  :");
                proactiveTransactionSupportingDocument = adminService.getProactiveTransactionSupportingDocumentById(Long.parseLong(supportingDocId));
                fileName = proactiveTransactionSupportingDocument.getFileName();
                filePath = proactiveTransactionSupportingDocument.getFileLocation();
            } else if (reactiveProjectId > 0) {  // get supporting document from Reactive transaction SupportingDocument
                reactiveTransactionSupportingDocument = adminService.getReactiveTransactionSupportingDocumentById(Long.parseLong(supportingDocId));
                fileName = reactiveTransactionSupportingDocument.getFileName();
                filePath = reactiveTransactionSupportingDocument.getFileLocation();
            } else if (realTimeProjectId > 0) {   // get supporting document from Real time transaction SupportingDocument
                logger.debug(" Realtime  :");
                realTimeTransactionSupportingDocument = adminService.getRealTimeTransactionSupportingDocumentById(Long.parseLong(supportingDocId));
                fileName = realTimeTransactionSupportingDocument.getFileName();
                filePath = realTimeTransactionSupportingDocument.getFileLocation();

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

    /*
    * Method to upload file by ajax call
    * @param MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/ajaxCallForFileUpload.html", method = RequestMethod.POST)
    String ajaxCallForFileUpload(MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model) {
        try {
            logger.debug("AjaxCallForFileUpload (POST) : ");
            String fileIconPath = "";
            String uploadPath = "uploadFiles";
            String comment = "";
            Long realTimeProjectId;
            Long transactionId;
            Long proactiveProjectId;
            Long reactiveProjectId;
            List trxSupportingDocumentList = new ArrayList();
            List<String> auditTrialMessageList = new ArrayList<String>();
            logger.debug(" ProactiveProjectId : " + request.getParameter("proactiveProjectId"));
            logger.debug(" ReactiveProjectId : " + request.getParameter("reactiveProjectId"));
            logger.debug(" RealTimeProjectId : " + request.getParameter("realTimeProjectId"));

            Iterator<String> itr = request.getFileNames();
            MultipartFile multipartFileForSupportingDocument = request.getFile(itr.next());

            if (multipartFileForSupportingDocument != null) {
                String uploadPathForSupportingDocument = Utils.getMessageBundlePropertyValue("uploadFilePathSupportingDocument");
                String orginalNameForSupportingDocument = multipartFileForSupportingDocument.getOriginalFilename();

                String filePathForSupportingDocument = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalNameForSupportingDocument);
                File dirForSupportingDocument = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForSupportingDocument);
                String fileIconPathForSupportingDocument = "";
                logger.debug("FILE TO SAVE :" + filePathForSupportingDocument);


                proactiveProjectId = !Utils.isNullOrEmpty(request.getParameter("proactiveProjectId")) ? Long.parseLong(request.getParameter("proactiveProjectId")) : 0;
                reactiveProjectId = !Utils.isNullOrEmpty(request.getParameter("reactiveProjectId")) ? Long.parseLong(request.getParameter("reactiveProjectId")) : 0;
                realTimeProjectId = !Utils.isNullOrEmpty(request.getParameter("realTimeProjectId")) ? Long.parseLong(request.getParameter("realTimeProjectId")) : 0;
                comment = request.getParameter("commentRegion");
                logger.debug(" COMMENT : " + comment);
                try {
                    if (!dirForSupportingDocument.exists()) {  // make directory
                        dirForSupportingDocument.mkdirs();
                    }
                    fileIconPathForSupportingDocument = getFileIconPath(multipartFileForSupportingDocument);

                    if (!Utils.isEmpty(orginalNameForSupportingDocument))
                        multipartFileForSupportingDocument.transferTo(new File(filePathForSupportingDocument));  // upload file to specific directory
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (proactiveProjectId > 0) {    // save record for  proactive Project
                    logger.debug(" IN proactiveProjectId : ");

                    transactionId = ProactiveController.getProactiveTransactionIdFromSession(requestForServlet);
                    ProactiveTransactionSupportingDocument proactiveTransactionSupportingDocument = new ProactiveTransactionSupportingDocument();
                    ProactiveTransaction proactiveTransaction = adminService.getProactiveTransactionById(transactionId);

                    proactiveTransactionSupportingDocument.setProactiveTransaction(proactiveTransaction);
                    proactiveTransactionSupportingDocument.setAuthor(Utils.getLoggedUserName());
                    proactiveTransactionSupportingDocument.setEntryTime(Utils.getTodaysDate());
                    proactiveTransactionSupportingDocument.setFileName((orginalNameForSupportingDocument));
                    proactiveTransactionSupportingDocument.setFileIconLocation(Utils.isEmpty(orginalNameForSupportingDocument) ? "" : fileIconPathForSupportingDocument);
                    proactiveTransactionSupportingDocument.setFileLocation(filePathForSupportingDocument);
                    proactiveTransactionSupportingDocument.setComment(Utils.isNullOrEmpty(comment) ? "" : comment);
                    adminService.save(proactiveTransactionSupportingDocument);
                    List<ProactiveTransactionSupportingDocument> proactiveTransactionSupportingDocumentList = new ArrayList<ProactiveTransactionSupportingDocument>();
                    proactiveTransactionSupportingDocumentList = adminService.getProactiveTransactionSupportingDocumentListByTrxId(proactiveTransaction.getId());
                    trxSupportingDocumentList = proactiveTransactionSupportingDocumentList;
                    if (proactiveTransaction != null && proactiveTransaction.getTransaction() != null)
                        setTransactionTypeOnSession(request, proactiveTransaction.getTransaction().getTransactionType());
                    logger.debug(" proactiveTransactionId :" + transactionId);
                    logger.debug(" Proactive Project  transaction Supporting Document : ");

                } else if (reactiveProjectId > 0) {  // save record for  Reactive Project
                    logger.debug(" IN ReactiveProjectId : ");
                    transactionId = ProactiveController.getReactiveTransactionIdFromSession(requestForServlet);
                    ReactiveTransactionSupportingDocument reactiveTransactionSupportingDocument = new ReactiveTransactionSupportingDocument();
                    ReactiveTransaction reactiveTransaction = adminService.getReactiveTransactionById(transactionId);
                    reactiveTransaction.setId(transactionId);

                    reactiveTransactionSupportingDocument.setReactiveTransaction(reactiveTransaction);
                    reactiveTransactionSupportingDocument.setAuthor(Utils.getLoggedUserName());
                    reactiveTransactionSupportingDocument.setEntryTime(Utils.getTodaysDate());
                    reactiveTransactionSupportingDocument.setFileName((orginalNameForSupportingDocument));
                    reactiveTransactionSupportingDocument.setFileIconLocation(Utils.isEmpty(orginalNameForSupportingDocument) ? "" : fileIconPathForSupportingDocument);
                    reactiveTransactionSupportingDocument.setFileLocation(filePathForSupportingDocument);
                    reactiveTransactionSupportingDocument.setComment(Utils.isNullOrEmpty(comment) ? "" : comment);
                    adminService.save(reactiveTransactionSupportingDocument);
                    List<ReactiveTransactionSupportingDocument> reactiveTransactionSupportingDocumentList = new ArrayList<ReactiveTransactionSupportingDocument>();
                    reactiveTransactionSupportingDocumentList = adminService.getReactiveTransactionSupportingDocumentListByTrxId(reactiveTransaction.getId());
                    trxSupportingDocumentList = reactiveTransactionSupportingDocumentList;
                    logger.debug(" ReactiveTransactionId : " + transactionId);
                    logger.debug(" Saving Reactive Project transaction Supporting Document");
                } else if (realTimeProjectId > 0) {   // save record for  Real time project
                    logger.debug(" IN RealTimeProjectId : ");
                    transactionId = RealTimeMonitoringController.getRealTimeTransactionIdFromSession(requestForServlet);
                    RealTimeTransactionSupportingDocument realTimeTransactionSupportingDocument = new RealTimeTransactionSupportingDocument();
                    RealTimeTransaction realTimeTransaction = adminService.getRealTimeTransactionById(transactionId);

                    realTimeTransactionSupportingDocument.setRealTimeTransaction(realTimeTransaction);
                    realTimeTransactionSupportingDocument.setAuthor(Utils.getLoggedUserName());
                    realTimeTransactionSupportingDocument.setEntryTime(Utils.getTodaysDate());
                    realTimeTransactionSupportingDocument.setFileName((orginalNameForSupportingDocument));
                    realTimeTransactionSupportingDocument.setFileIconLocation(Utils.isEmpty(orginalNameForSupportingDocument) ? "" : fileIconPathForSupportingDocument);
                    realTimeTransactionSupportingDocument.setFileLocation(filePathForSupportingDocument);
                    realTimeTransactionSupportingDocument.setComment(Utils.isNullOrEmpty(comment) ? "" : comment);
                    adminService.save(realTimeTransactionSupportingDocument);
                    List<RealTimeTransactionSupportingDocument> realTimeTransactionSupportingDocumentsList = new ArrayList<RealTimeTransactionSupportingDocument>();
                    realTimeTransactionSupportingDocumentsList = adminService.getRealTimeTransactionSupportingDocumentListByTrxId(realTimeTransaction.getId());
                    trxSupportingDocumentList = realTimeTransactionSupportingDocumentsList;
                    logger.debug(" RealTimeTransactionId : " + transactionId);
                    logger.debug(" Saving -RealTime Project transaction Supporting Document");
                    auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document")
                            + " '" + orginalNameForSupportingDocument + "' "
                            + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.supportingDoc"));
                    adminService.saveRealTimeTrxAuditTrial(realTimeTransaction, auditTrialMessageList);
                }

                logger.debug(" Proactive Project Id : " + proactiveProjectId);
                logger.debug(" Reactive Project Id : " + reactiveProjectId);
                logger.debug(" Real Time Project Id : " + realTimeProjectId);

                logger.debug("-SuccessFul-");
                model.addAttribute("supportingDocumentList", trxSupportingDocumentList);
                model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);

            }

        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "ajaxForFileUpload";
    }

    /*
    * Method to get Reactive Transaction Id From Session
    * @param MultipartHttpServletRequest request
    * @return type Long
    */
    public static Long getReactiveTransactionIdFromSession(HttpServletRequest request) {
        return (request.getSession().getAttribute("reactiveTransactionId") != null ? (Long) request.getSession().getAttribute("reactiveTransactionId") : 0);
    }

    /*
    * Method to set Reactive Transaction Id On Session
    * @param HttpServletRequest request, Long reactiveTransactionId
    * @return type void
    */
    public static void setReactiveTransactionIdOnSession(HttpServletRequest request, Long reactiveTransactionId) {
        request.getSession().setAttribute("reactiveTransactionId", reactiveTransactionId);
    }

    /*
    * Method to get Heder And Value Of Person Info
    * @param List infoList
    * @return type List
    */
    public static List getHederAndValueOfPersonInfo(List infoList) throws ParseException {
        List headerAndValueList = new ArrayList();
        for (int i = 0; i < infoList.size(); i++) {

            Map map = (Map) infoList.get(i);
            Set set = map.keySet();
            for (Object s : set) {
                if (map.get(s) != null && !Utils.isEmpty(map.get(s).toString().trim()) && !("id").equals(s)) {
                    Map header = new HashMap();
                    header.put("header", WordUtils.capitalize(s.toString().replace("_", " ")));
                    // if(Utils.isValidXlsStrToDate(map.get(s).toString()))
                    //          header.put("value",Utils.getXlsDateToString(map.get(s).toString()));
                    // else
                    header.put("value", map.get(s).toString());

                    headerAndValueList.add(header);
                }
            }

        }
        return headerAndValueList;
    }

    /**
     * Download Policy
     *
     * @param request
     * @param response
     * @param policyId
     * @throws IOException
     */
    @RequestMapping(value = "/policy/policyShow.html", method = RequestMethod.GET)
    public void policyShow(HttpServletRequest request, HttpServletResponse response, @RequestParam("policyId") long policyId) throws IOException {
        logger.debug("Policy Download Controller :");
        String fileName = "";
        String filePath = "";
        Policy policy = new Policy();
        try {
            if (policyId > 0) {
                policy = (Policy) adminService.loadEntityById(policyId, Entity.POLICY.getValue());
                User user = adminService.getUserByUserName(Utils.getLoggedUserName());
                fileName = policy.getFileName();
                filePath = policy.getFileLocation();

                if (user.getUserTypeId() > 0) {
                    PolicyAndProcedure policyAndProcedure = adminService.getPolicyAndProcedure(user, policyId);

                    if (policyAndProcedure != null) {
                        if (policyAndProcedure.isConfirmed() == false && policyAndProcedure.getConfirmDate() == null) {
                            policyAndProcedure.setDownloaded(Utils.getTodaysDate());
                            adminService.update(policyAndProcedure);
                            logger.debug("PolicyAndProcedure Updated Successfully :");
                        }
                    } else {
                        policyAndProcedure = new PolicyAndProcedure();
                        if (PersonType.EMPLOYEE.getValue().equals(user.getUserType())) {
                            EmployeeMasterLedger employeeMasterLedger = new EmployeeMasterLedger();
                            employeeMasterLedger.setId(user.getUserTypeId());
                            policyAndProcedure.setEmployeeMasterLedger(employeeMasterLedger);
                        } else if (PersonType.VENDOR.getValue().equals(user.getUserType())) {
                            VendorMasterLedger vendorMasterLedger = new VendorMasterLedger();
                            vendorMasterLedger.setId(user.getUserTypeId());
                            policyAndProcedure.setVendorMasterLedger(vendorMasterLedger);
                        }
                        policyAndProcedure.setPolicy(policy);
                        policyAndProcedure.setDownloaded(Utils.getTodaysDate());
                        adminService.save(policyAndProcedure);
                        logger.debug("PolicyAndProcedure Saved Successfully :");
                    }
                }


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
    }

    /**
     * Supporting document delete
     *
     * @param supportingDocId
     * @throws IOException
     */

    @RequestMapping(value = "/riskasst/deleteSupportingDoc.html", method = RequestMethod.GET)
    String ajaxCallForDetails( HttpServletRequest request,@ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, @RequestParam("supportingDocId") long supportingDocId, Model model) {
        try {
            logger.debug("Supporting document delete Controller");
            long realTimeProjectId = 0;
            long transactionId = 0;
            long proactiveProjectId = 0;
            long reactiveProjectId = 0;
            List trxSupportingDocumentList = new ArrayList();
            List<String> auditTrialMessageList = new ArrayList<String>();
            if (newRiskAssessmentTxBean != null) {
                proactiveProjectId = newRiskAssessmentTxBean.getProactiveProjectId();
                reactiveProjectId = newRiskAssessmentTxBean.getReactiveProjectId();
                realTimeProjectId = newRiskAssessmentTxBean.getRealTimeProjectId();
            }

            if (proactiveProjectId > 0) {    // save record for  proactive Project
                logger.debug(" IN proactiveProjectId : ");
                ProactiveTransactionSupportingDocument proactiveTransactionSupportingDocument = adminService.getProactiveTransactionSupportingDocumentById(supportingDocId);
                transactionId = ProactiveController.getProactiveTransactionIdFromSession(request);
                ProactiveTransaction proactiveTransaction = adminService.getProactiveTransactionById(transactionId);
                adminService.deleteEntityById(supportingDocId, Entity.PROACTIVE_TRANSACTION_SUPPORT_DOCUMENT.getValue());
                if (proactiveTransactionSupportingDocument != null) {
                    File file = new File(proactiveTransactionSupportingDocument.getFileLocation());
                    logger.debug("File To Delete : " + proactiveTransactionSupportingDocument.getFileLocation());
                    if (file.exists() && file.delete()) {
                        logger.debug("File : " + proactiveTransactionSupportingDocument.getFileLocation() + " deleted successfully.");
                        auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.supportingDocument")
                                + " '" + proactiveTransactionSupportingDocument.getFileName() + "' "
                                + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document.delete"));
                        adminService.saveProactiveTrxAuditTrial(proactiveTransaction, auditTrialMessageList);

                    }
                }
                List<ProactiveTransactionSupportingDocument> proactiveTransactionSupportingDocumentList = new ArrayList<ProactiveTransactionSupportingDocument>();
                proactiveTransactionSupportingDocumentList = adminService.getProactiveTransactionSupportingDocumentListByTrxId(proactiveTransaction.getId());
                trxSupportingDocumentList = proactiveTransactionSupportingDocumentList;
                logger.debug(" proactiveTransactionId :" + transactionId);

            } else if (reactiveProjectId > 0) {  // save record for  Reactive Project
                logger.debug(" IN ReactiveProjectId : ");
                ReactiveTransactionSupportingDocument reactiveTransactionSupportingDocument = adminService.getReactiveTransactionSupportingDocumentById(supportingDocId);
                transactionId = ProactiveController.getReactiveTransactionIdFromSession(request);
                ReactiveTransaction reactiveTransaction = adminService.getReactiveTransactionById(transactionId);
                reactiveTransaction.setId(transactionId);
                adminService.deleteEntityById(supportingDocId, Entity.REACTIVE_TRANSACTION_SUPPORT_DOCUMENT.getValue());
                if (reactiveTransactionSupportingDocument != null) {
                    File file = new File(reactiveTransactionSupportingDocument.getFileLocation());
                    logger.debug("File To Delete : " + reactiveTransactionSupportingDocument.getFileLocation());
                    if (file.exists() && file.delete()) {
                        logger.debug("File : " + reactiveTransactionSupportingDocument.getFileLocation() + " deleted successfully.");
                        auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.supportingDocument")
                                + " '" + reactiveTransactionSupportingDocument.getFileName() + "' "
                                + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document.delete"));
                        adminService.saveReactiveTrxAuditTrial(reactiveTransaction, auditTrialMessageList);

                    }
                }
                List<ReactiveTransactionSupportingDocument> reactiveTransactionSupportingDocumentList = new ArrayList<ReactiveTransactionSupportingDocument>();
                reactiveTransactionSupportingDocumentList = adminService.getReactiveTransactionSupportingDocumentListByTrxId(reactiveTransaction.getId());
                trxSupportingDocumentList = reactiveTransactionSupportingDocumentList;
                logger.debug(" ReactiveTransactionId : " + transactionId);

            } else if (realTimeProjectId > 0) {   // save record for  Real time project
                logger.debug(" IN RealTimeProjectId : ");
                logger.debug(" supportingDocId : " + supportingDocId);
                RealTimeTransactionSupportingDocument realTimeTransactionSupportingDocument = adminService.getRealTimeTransactionSupportingDocumentById(supportingDocId);
                transactionId = RealTimeMonitoringController.getRealTimeTransactionIdFromSession(request);
                RealTimeTransaction realTimeTransaction = adminService.getRealTimeTransactionById(transactionId);
                adminService.deleteEntityById(supportingDocId, Entity.REALTIME_TRANSACTION_SUPPORT_DOCUMENT.getValue());
                if (realTimeTransactionSupportingDocument != null) {
                    File file = new File(realTimeTransactionSupportingDocument.getFileLocation());
                    logger.debug("File To Delete : " + realTimeTransactionSupportingDocument.getFileLocation());
                    if (file.exists() && file.delete()) {
                        logger.debug("File : " + realTimeTransactionSupportingDocument.getFileLocation() + " deleted successfully.");
                        auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.supportingDocument")
                                + " '" + realTimeTransactionSupportingDocument.getFileName() + "' "
                                + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document.delete"));
                        adminService.saveRealTimeTrxAuditTrial(realTimeTransaction, auditTrialMessageList);

                    }
                }
                List<RealTimeTransactionSupportingDocument> realTimeTransactionSupportingDocumentsList = new ArrayList<RealTimeTransactionSupportingDocument>();
                realTimeTransactionSupportingDocumentsList = adminService.getRealTimeTransactionSupportingDocumentListByTrxId(realTimeTransaction.getId());
                trxSupportingDocumentList = realTimeTransactionSupportingDocumentsList;
                logger.debug(" RealTimeTransactionId : " + transactionId);
            }
            logger.debug("-SuccessFul-");
            model.addAttribute("supportingDocumentList", trxSupportingDocumentList);
            model.addAttribute("supportingDocOption", "1");
            model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);


        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "ajaxForFileUpload";
    }

     /**
     * Policy Attachment document delete
     *
     * @param txPolicyId
     * @throws IOException
     */

    @RequestMapping(value = "/policy/deleteTransactionPolicy.html", method = RequestMethod.GET)
    String deleteTransactionPolicy( HttpServletRequest request,@ModelAttribute("NewRiskAssessmentTxBean") NewRiskAssessmentTxBean newRiskAssessmentTxBean, @RequestParam("txPolicyId") long txPolicyId, Model model) {
        try {
            logger.debug("Transaction Policy delete Controller");
            long realTimeProjectId = 0;
            long transactionId = 0;
            long proactiveProjectId = 0;
            long reactiveProjectId = 0;
            List<String> auditTrialMessageList = new ArrayList<String>();
            List policyList = null;
            if (newRiskAssessmentTxBean != null) {
                proactiveProjectId = newRiskAssessmentTxBean.getProactiveProjectId();
                reactiveProjectId = newRiskAssessmentTxBean.getReactiveProjectId();
                realTimeProjectId = newRiskAssessmentTxBean.getRealTimeProjectId();
            }

            TransactionPolicy transactionPolicy = adminService.loadTransactionPolicy(txPolicyId);
            if (transactionPolicy  != null) {
                transactionId = transactionPolicy.getTransaction().getId();
                File file = new File(transactionPolicy.getFileLocation());
                    logger.debug("File To Delete : " + transactionPolicy.getFileLocation());
                    if (file.exists() && file.delete()) {
                        logger.debug("File : " + transactionPolicy.getFileLocation() + " deleted successfully.");
                        auditTrialMessageList.add(Utils.getMessageBundlePropertyValue("auditTrial.fileupload.transactionPolicy")
                                + " '" + transactionPolicy .getFileName() + "' "
                                + Utils.getMessageBundlePropertyValue("auditTrial.fileupload.document.delete"));
                        if(proactiveProjectId > 0){
                            adminService.saveProactiveTrxAuditTrial(new ProactiveTransaction(), auditTrialMessageList);
                        }
                        else if(reactiveProjectId> 0){
                            adminService.saveReactiveTrxAuditTrial(new ReactiveTransaction(), auditTrialMessageList);
                        }
                        if(realTimeProjectId > 0){
                            adminService.saveRealTimeTrxAuditTrial(new RealTimeTransaction(), auditTrialMessageList);
                        }
                    }
                }
            adminService.deleteEntityById(txPolicyId, Entity.TRANSACTION_POLICY.getValue());
            logger.debug("Transaction Policy delete Successfuly");
            policyList = adminService.getTransactionPolicy(transactionId);
            model.addAttribute("txPolicyOption", "1");
            model.addAttribute("txPolicyList", policyList);

        } catch (Exception ex) {
            logger.error("Transaction Policy Delete Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "ajaxForFileUpload";
    }

     @RequestMapping(value = "/riskasst/getJASONforContinueRiskAss.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getProactiveProjectList(HttpServletRequest request, Model model) {
        logger.debug("Get control list controller");
        String page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        int totalItems = 0;
        List<Cell> entry = new ArrayList<Cell>();
        List proactiveProjectList = new ArrayList<Control>();

        try {

            totalItems = adminService.getEntitySize(Constants.PROACTIVE_PROJECT);
            proactiveProjectList = adminJdbcService.getPartialProactiveProjectDataList(Utils.parseInteger(page), Utils.parseInteger(rp));
            if (proactiveProjectList != null) {
                jasonData.setPage(Utils.parseInteger(page));
                for (Object obj : proactiveProjectList) {
                    ProactiveProject proactiveProject = new ProactiveProject();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    Date fromDate = map.get("from_date") != null ? (Date) map.get("from_date") : new Date();
                    Date toDate = map.get("to_date") != null ? (Date) map.get("to_date") : new Date();
                    Date created = map.get("created") != null ? (Date) map.get("created") : new Date();
                    logger.debug("--from date--" + fromDate + " to date-" + toDate);
                    logger.debug("--region name--" + map.get("regionName"));
                    proactiveProject.setRegionName(map.get("regionName") != null ? map.get("regionName").toString() : "");
                    proactiveProject.setAuthor(map.get("author") != null ? map.get("author").toString() : "");
                    proactiveProject.setDateRange(Utils.dateToStrWithFormat(fromDate, Constants.MONTH_DAY_YEAR) + " to " + Utils.dateToStrWithFormat(toDate, Constants.MONTH_DAY_YEAR));
                    proactiveProject.setCreatedDate(Utils.dateToStrWithFormat(fromDate, Constants.MONTH_DAY_YEAR));
                    proactiveProject.setId(map.get("proactiveProjectId") != null ? ((Number) map.get("proactiveProjectId")).intValue() : 0);
                    cell.setId(map.get("proactiveProjectId") != null ? ((Number) map.get("proactiveProjectId")).intValue() : 0);

                    cell.setCell(proactiveProject);
                    entry.add(cell);
                }
                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
            } else {
                logger.debug("No Proctive Project Found");
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Exception : " + ex);
        }

        return jasonData;
    }

     @RequestMapping(value = "/riskasst/getJASONforProactiveWorkflowList.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getJASONforProactiveWorkflowList(HttpServletRequest request, Model model) {
        logger.debug("Get Proactive Work flow list controller");
        String page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        int totalItems = 0;
        List<Cell> entry = new ArrayList<Cell>();
        List proactiveBlockWeightList = new ArrayList<Control>();

        try {
            String selectedButton = (String) request.getSession().getAttribute("hiddenId");
            String countryName = request.getParameter("cName") != null ? request.getParameter("cName") : "1";

            Date fromDate;
            Date toDate;
            int wScore = (request.getParameter("wScore") != null ? Integer.parseInt(request.getParameter("wScore")) : 0);
            logger.debug("-------in json countryName---" + countryName + " selectedButton=" + selectedButton + " wScore = " + wScore);
            if (Character.isDigit(selectedButton.charAt(0))) {
                int selectedYear = Character.getNumericValue(selectedButton.charAt(0));
                fromDate = Utils.getYearBackTime(selectedYear - 1);
            } else {
                fromDate = request.getSession().getAttribute("fromDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("fromDate")) : Utils.getTodaysDate();
            }
            toDate = request.getSession().getAttribute("toDate") != null ? Utils.stringToDate((String) request.getSession().getAttribute("toDate")) : Utils.getTodaysDate();
            Region region = adminService.getRegionByName(countryName);
            //List<ProactiveBlockWeight> proactiveBlockWeightList = adminService.getProactiveBlockWeight(region.getId(), Utils.getYear(toDate), Utils.getYear(fromDate));


            proactiveBlockWeightList = adminJdbcService.getPartialProactiveBlockWeightList(region.getId(), Utils.getYear(toDate), Utils.getYear(fromDate), Utils.parseInteger(page), Utils.parseInteger(rp));
            totalItems = proactiveBlockWeightList != null ? proactiveBlockWeightList.size() : 0;
            if (proactiveBlockWeightList != null) {
                jasonData.setPage(Utils.parseInteger(page));
                for (Object obj : proactiveBlockWeightList) {
                    ProactiveBlockWeight proactiveBlockWeight = new ProactiveBlockWeight();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    proactiveBlockWeight.setDate(map.get("date") != null ? ((Number) map.get("date")).intValue() : 0);
                    proactiveBlockWeight.setCpiScoreStr(map.get("cpi_score") != null ? map.get("cpi_score").toString() + "%" : "");
                    proactiveBlockWeight.setRevenuesStr(map.get("revenues") != null ? map.get("revenues").toString() + "%" : "");
                    proactiveBlockWeight.setNoOfAgents("35");
                    proactiveBlockWeight.setNoOfCustomBrokers("75");
                    proactiveBlockWeight.setRevenueAttributableToAgents("$500");
                    proactiveBlockWeight.setNoOfGovtCustomers("50");
                    proactiveBlockWeight.setDateLastFCPAAuditInvestigation("12/12/2012");
                    proactiveBlockWeight.setId(map.get("id") != null ? ((Number) map.get("id")).intValue() : 0);
                    cell.setId(map.get("id") != null ? ((Number) map.get("id")).intValue() : 0);
                    cell.setCell(proactiveBlockWeight);
                    entry.add(cell);
                }
                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
            } else {
                logger.debug("No Proactiv eWork flow  Found");
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Exception : " + ex);
        }

        return jasonData;
    }

    /**
     * Get all transaction list with details for selected country
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/riskasst/proactiveTransactionSummaryView.html", method = RequestMethod.GET)
    public String InternalCtrlGapAnalysisSummeryView(HttpServletRequest request, Model model) {
        logger.debug(" :: proactive Transaction Summary View GET ::");

        String pageNo = request.getSession().getAttribute("pageNo") != null ? (String) request.getSession().getAttribute("pageNo") : "";
        String controlIds = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "";
        String proactiveProjectId = request.getParameter("proactiveProjectId") != null ? request.getParameter("proactiveProjectId") : "0";
        logger.debug("SMN LOG: conmtrol ids=" + controlIds);
        logger.debug("SMN LOG: proactiveProjectId ids=" + proactiveProjectId);

        if (Utils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        try {

            if (!isEmpty(controlIds)) {

                model.addAttribute("NewRiskAssessmentTxBean", new NewRiskAssessmentTxBean());
                model.addAttribute("reactiveProjectId", "0");
                model.addAttribute("proactiveProjectId", proactiveProjectId);
                model.addAttribute("realTimeProjectId", "0");

                model.addAttribute("ctrlId", controlIds);
                model.addAttribute("serialNoForTableRowSelection", ProactiveController.getSerialNoFromSession(request));
                model.addAttribute("maxFileUploadSize", getApplicationPropertyValue("file.mazFileUploadSize"));
                model.addAttribute("pageNo", pageNo);
            }

        } catch (Exception ex) {
            logger.debug("CERROR::proactive Transaction Summary Summary View: " + ex);
            logger.debug("CERROR:: Error Description: " + ex.getMessage());

        }
        model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId", getMessageBundlePropertyValue("subTabId.trxMonitoring"));

        return "common/newRiskAssessmentSummary1";
    }
}