package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.entity.*;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.ControlValidation;
import com.dsinv.abac.validation.HolidayValidation;
import com.dsinv.abac.vo.admin.AuditTrailVO;
import com.dsinv.abac.vo.admin.ControlVO;
import com.dsinv.abac.vo.admin.TransactionDetailVO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.dsinv.abac.util.Utils.getMessageBundlePropertyValue;

@Controller
/*@SessionAttributes({"control", "holiday"})*/
public class ReportingController {

    private static Logger logger = Logger.getLogger(ReportingController.class);
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private ControlValidation controlValidation;
    @Autowired(required = true)
    private HolidayValidation holidayValidation;


    /**
     * Get Policy Review Certification page
     * @param request
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/report/policyReviewCertification.html", method = RequestMethod.GET)
    public String realTimeMonitoringIntervalSetup(HttpServletRequest request, Model model, Map map) {
        logger.debug(" :: Policy Review Certification  GET:: ");
        /*TransactionApproval transactionApproval = new TransactionApproval();
        List<TransactionApproval> trxApproveList = new ArrayList();
        trxApproveList = adminService.getAllTransactionApprovalList();
        transactionApproval.setList(trxApproveList);
        model.addAttribute("transactionApproval",transactionApproval);
        model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.trxApproval"));
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);*/
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.reporting"));
        adminService.addNode(getMessageBundlePropertyValue("menu.reporting.policyReviewCertification"), 2, request);
        return "admin/policyReviewCertification";
    }

    @RequestMapping(value = "/report/makePieChartForPolicyReviewCertification.html", method = RequestMethod.GET)
    public @ResponseBody Policy ajaxCallForCreatingPieChartForPolicyReviewCertification(HttpServletRequest request,@ModelAttribute("policyId") String policyIdStr) {

        logger.debug("::  AJAX CALL to Make Pie Chart For Policy ReviewCertification Controller ::");
        Policy policy = new Policy();
        try {
            int totalEmployee = 0;
            int totalEmployeeWithMailAddress = 0;
            int totalVendor = 0;
            int totalVendorWithMailAddress = 0;
            int totalConfirmedByVendor = 0;
            int totalUnconfirmedByEmployee = 0;
            int totalUnconfirmedByVendor = 0;
            int totalConfirmedByEmployee = 0;
            long policyId = 0;
//            String policyIdStr = request.getParameter("policyId") != null ? request.getParameter("policyId") : "";
            policyId = !Utils.isEmpty(policyIdStr) ? Long.parseLong(policyIdStr) : 0;
            policy = adminService.getPolicyById(policyId);
            logger.debug("SMN LOG:AUDIANCE CODE:"+policy.getAudianceCode());
            // status o for all, 1 for employee and 2 for vendor
            if(policy.getAudianceCode() == 0 || policy.getAudianceCode() == 1 ){  // audiance code 0 for all, 1 for employee and 2 for vendor
                totalEmployee = adminService.getEntitySize(Constants.EMPLOYEE_MASTER_LEDGER_CL);
                totalEmployeeWithMailAddress = adminJdbcService.getTotalEmployeeWithEmailAddresd();
                totalConfirmedByEmployee = adminJdbcService.getTotalPolicyConfirmedByPolicyId(policyId,1);
                totalUnconfirmedByEmployee = adminJdbcService.getTotalPolicyUnconfirmedByPolicyId(policyId,1);

                policy.setTotalEmployee(totalEmployee);
                policy.setTotalEmployeeWithemail(totalEmployeeWithMailAddress);
                policy.setTotalConfirmedByEmployee(totalConfirmedByEmployee);
                policy.setTotalUnconfirmedByEmployee(totalUnconfirmedByEmployee);
                logger.debug("SMN LOG:in employee block totalEmployee: " + totalEmployee
                        +" totalEmployeeWithMailAddress:"+totalEmployeeWithMailAddress+" totalConfirmed by employee="+totalConfirmedByEmployee
                +" total notConfirmed:"+totalUnconfirmedByEmployee);


            }
            if(policy.getAudianceCode() == 0 || policy.getAudianceCode() == 2){
                totalVendor = adminService.getEntitySize(Constants.VENDOR_MASTER_LEDGER_CL);
                totalVendorWithMailAddress = adminJdbcService.getTotalVendorWithEmailAddresd();
                totalConfirmedByVendor = adminJdbcService.getTotalPolicyConfirmedByPolicyId(policyId,2);
                totalUnconfirmedByVendor = adminJdbcService.getTotalPolicyUnconfirmedByPolicyId(policyId,2);

                policy.setTotalVendor(totalVendor);
                policy.setTotalVendorWithEmail(totalVendorWithMailAddress);
                policy.setTotalConfirmedByVendor(totalConfirmedByVendor);
                policy.setTotalUnconfirmedByVendor(totalUnconfirmedByVendor);
                logger.debug("SMN LOG:in Vendor block totalVendor:"+totalVendor
                        +totalEmployeeWithMailAddress+" totalVendorWithMailAddress:"+totalVendorWithMailAddress);
            }

            logger.debug("policyId : " + policyId);
           // logger.debug("SMN LOG: totalConfirmedByEmployee : " + totalConfirmedByEmployee+" totalConfirmedByVendor:"+totalConfirmedByVendor
            //+" totalUnconfirmedByEmployee :"+totalUnconfirmedByEmployee +" totalUnconfirmedByVendor:"+totalUnconfirmedByVendor);

            /*ObjectMapper mapper = new ObjectMapper();
            String policyJasonObject = mapper.writeValueAsString(policy);
            model.addAttribute("policy",policyJasonObject);*/
            //policy.setConfirmed();

        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return policy;
    }

    @RequestMapping(value = "/report/trainingReview.html", method = RequestMethod.GET)
    public String trainingReview(HttpServletRequest request, Model model) {
        logger.debug("Training Review Controller Start.");
        try {
            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);
        } catch (Exception ex) {
            logger.debug("CEEOR:: Training Review Exception.");
        }
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.reporting"));
        adminService.addNode(getMessageBundlePropertyValue("menu.reporting.trainingReviewCertification"), 2, request);
        logger.debug("Training Review Controller end.");
        return "admin/trainingReview";
    }


    @RequestMapping(value = "/training/trainingPieChart.html", method = RequestMethod.GET)
    public @ResponseBody Training makeTrainingReviewPieChart(HttpServletRequest request, @RequestParam("trainingId") long trainingId, Model model) {

        logger.debug("Training Pie Chart Ajax Controller Start.");
        Training training = null;
        try {
            int totalEmployee = 0;
            int totalEmployeeWithMailAddress = 0;
            int totalEmployeeNeedRetake = 0;
            int totalParticipantEmployee = 0;

            training = adminService.getTrainingById(trainingId);

            // status o for all, 1 for employee and 2 for vendor
            logger.debug("AMLOG:: training Audiance: " + training.getAudianceCode());
            if(training.getAudianceCode() == 1 ){  // audiance code 0 for all, 1 for employee and 2 for vendor
                totalEmployee = adminService.getEntitySize(Constants.EMPLOYEE_MASTER_LEDGER_CL);
                totalEmployeeWithMailAddress = adminJdbcService.getTotalEmployeeWithEmailAddresd();
                totalParticipantEmployee = adminJdbcService.getTotalTrainingParticipant(trainingId, training.getAudianceCode());
                totalEmployeeNeedRetake = adminJdbcService.getTotalEmployeeNeedRetake(trainingId, training.getAudianceCode());
                logger.debug("Total Participant Employee: " + totalParticipantEmployee);
                logger.debug("Total Employee Need Retake: " + totalEmployeeNeedRetake);
                training.setTotalEmployee(totalEmployee);
                training.setTotalEmployeeWithemail(totalEmployeeWithMailAddress);
                training.setTotalParticipantEmployee(totalParticipantEmployee);
                training.setTotalEmployeeNeedRetake(totalEmployeeNeedRetake);
                training.setTotalOutstanding(totalEmployeeWithMailAddress-totalParticipantEmployee);
            }


        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        logger.debug("Training Pie Chart Ajax Controller End.");
        return training;
    }

    @RequestMapping(value = "/report/pieChartPrint.html", method = RequestMethod.GET)
    public String callAjaxForPieChartPrint(HttpServletRequest request/*,@ModelAttribute("policy") Policy policy*/, HttpServletResponse response, Model model) {
        logger.debug("Pie Chart print Controller Start.");
        String  policyStr = request.getParameter("policy") != null ? request.getParameter("policy") : "";
        JSONParser parser = new JSONParser();
        try {
            ArrayList<policyBean> dataList = new ArrayList<policyBean>();
            Object object = parser.parse(policyStr);
            JSONObject realTitle = (JSONObject)object;
            int totalEmployee = ((Long)realTitle.get("totalEmployee")).intValue();
            int totalEmployeeWithMailAddress = ((Long)realTitle.get("totalEmployeeWithemail")).intValue();
            int totalVendor = ((Long)realTitle.get("totalVendor")).intValue();
            int totalVendorWithMailAddress = ((Long)realTitle.get("totalVendorWithEmail")).intValue();
            int totalConfirmedByVendor = ((Long)realTitle.get("totalConfirmedByVendor")).intValue();;
            int totalUnconfirmedByEmployee = ((Long)realTitle.get("totalUnconfirmedByEmployee")).intValue();;
            int totalUnconfirmedByVendor = ((Long)realTitle.get("totalUnconfirmedByVendor")).intValue();;
            int totalConfirmedByEmployee = ((Long)realTitle.get("totalConfirmedByEmployee")).intValue();;
            int audianceCode = ((Long)realTitle.get("audianceCode")).intValue();;

            int totalConfirmedPercent = 0;
            int totalUnconfirmedPercent =0;
            int totalNotReviewed = 0;
            int totalConfirmedPercent2 = 0;
            int totalUnconfirmedPercent2 =0;
            int totalNotReviewed2 = 0;

        Map parameters = new HashMap();
            parameters.put("policyName", realTitle.get("documentName"));
            parameters.put("policyType",realTitle.get("policyType"));
            parameters.put("uploaded",realTitle.get("author"));
            parameters.put("uploadDate",Utils.dateToStrWithFormat(new Date((Long)realTitle.get("entryTime")),Constants.MONTH_DAY_YEAR));
        if(audianceCode == 1 || audianceCode == 0){

            totalConfirmedPercent = (totalConfirmedByEmployee*100)/totalEmployeeWithMailAddress;
            totalUnconfirmedPercent =(totalUnconfirmedByEmployee*100)/totalEmployeeWithMailAddress;
            totalNotReviewed = 100-(totalConfirmedPercent+totalUnconfirmedPercent);

            parameters.put("chartFor","                           Pie chart based on Employee information.");
            parameters.put("totalEmpOrVen","Total Employees : ");
            parameters.put("notification","Notification : ");
            parameters.put("confirmed","Confirmed : ");
            parameters.put("notConfirmed","Not Confirmed : ");
            parameters.put("reviewed","Reviewed : ");
            parameters.put("value1"," "+totalEmployee);
            parameters.put("value2"," "+totalEmployeeWithMailAddress);
            parameters.put("value3"," "+totalConfirmedByEmployee);
            parameters.put("value4"," "+totalUnconfirmedByEmployee);
            parameters.put("value5"," "+(totalEmployeeWithMailAddress-(totalConfirmedByEmployee+totalUnconfirmedByEmployee)));
        }
            if(audianceCode == 2){
                totalConfirmedPercent = (totalConfirmedByVendor*100)/totalVendorWithMailAddress;
                totalUnconfirmedPercent =(totalUnconfirmedByVendor*100)/totalVendorWithMailAddress;
                totalNotReviewed = 100- (totalConfirmedPercent+totalUnconfirmedPercent);

                parameters.put("chartFor","                           Pie chart based on Vendor information.");
                parameters.put("totalEmpOrVen","Total Vendors : ");
                parameters.put("notification","Notification : ");
                parameters.put("confirmed","Confirmed : ");
                parameters.put("notConfirmed","Not Confirmed : ");
                parameters.put("reviewed","Reviewed : ");
                parameters.put("value1"," "+totalVendor);
                parameters.put("value2"," "+totalVendorWithMailAddress);
                parameters.put("value3"," "+totalConfirmedByVendor);
                parameters.put("value4"," "+totalUnconfirmedByVendor);
                parameters.put("value5"," "+(totalVendorWithMailAddress-(totalConfirmedByVendor+totalUnconfirmedByVendor)));
            }

            if(audianceCode == 0 ){
                dataList.clear();

                totalConfirmedPercent2 = (totalConfirmedByVendor*100)/totalVendorWithMailAddress;
                totalUnconfirmedPercent2 =(totalUnconfirmedByVendor*100)/totalVendorWithMailAddress;
                totalNotReviewed2 = 100- (totalConfirmedPercent2+totalUnconfirmedPercent2);

                parameters.put("chartFor2","                           Pie chart based on Vendor information.");
                parameters.put("totalVen","Total Vendors : ");
                parameters.put("notification2","Notification : ");
                parameters.put("confirmed2","Confirmed : ");
                parameters.put("notConfirmed2","Not Confirmed : ");
                parameters.put("reviewed2","Reviewed : ");
                parameters.put("value11"," "+totalVendor);
                parameters.put("value22"," "+totalVendorWithMailAddress);
                parameters.put("value33"," "+totalConfirmedByVendor);
                parameters.put("value44"," "+totalUnconfirmedByVendor);
                parameters.put("value55"," "+(totalVendorWithMailAddress-(totalConfirmedByVendor+totalUnconfirmedByVendor)));

            }

            dataList.add(new policyBean("Not Reviewed",totalNotReviewed,"Not Reviewed",totalNotReviewed2));
            dataList.add(new policyBean("Confirmed",totalConfirmedPercent,"Confirmed",totalConfirmedPercent2));
            dataList.add(new policyBean("Not Confirmed",totalUnconfirmedPercent,"Not Confirmed",totalUnconfirmedPercent2));

            JRBeanCollectionDataSource beanColDataSource =
                    new JRBeanCollectionDataSource(dataList);

            InputStream pdfForm = this.getClass().getClassLoader().getResourceAsStream("/jasper/pieReportForSinglePolicy.jasper");
            if(audianceCode ==0 ){
              pdfForm = this.getClass().getClassLoader().getResourceAsStream("/jasper/pieReportForBothPolicy.jasper");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(pdfForm, parameters, beanColDataSource);
//            JasperPrint jasperPrint = JasperFillManager.fillReportToFile(sourceFileName, parameters, beanColDataSource);
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=policyPieChart.pdf");
            response.setContentLength(pdfData.length);
            response.getOutputStream().write(pdfData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception ex) {
            logger.debug("CERROR:: Failed to Print certificate. " + ex.getMessage());
            ex.printStackTrace();
        }



        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.reporting"));
        adminService.addNode(getMessageBundlePropertyValue("menu.reporting.trainingReviewCertification"), 2, request);
        logger.debug("Training Review Controller end.");
        return "admin/trainingReview";
    }

}

