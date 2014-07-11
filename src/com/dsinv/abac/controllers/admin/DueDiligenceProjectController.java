package com.dsinv.abac.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.dsinv.abac.ledger.Vendor;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Utils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
/*@SessionAttributes({"newReputationalReviewBusinessPartner", "newFinancialReviewBusinessPartner"})*/
public class DueDiligenceProjectController {


    private static Logger logger = Logger.getLogger(DueDiligenceProjectController.class);

    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;

    /*
    * Method for viewing Due Diligence landing page(old)
    * @param HttpServletRequest request, Model model
    * @return type String
    */
/*    @RequestMapping(value = "*//*//*DueDilligance.html", method = RequestMethod.GET)
    public String DueDilliganceGet(HttpServletRequest request, Model model) {
        model.addAttribute("isReputational", 1);
        model.addAttribute("isFinancial", 0);
        model.addAttribute("isNew", 1);
        model.addAttribute("isExisting", 0);
        adminService.addNode(Utils.getMessageBundlePropertyValue("thirdPartyReview"), 2, request);
        return "admin/dueDilliganceLanding";
    }*/

    /*
    * Method for viewing new ReputationalForm
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/newReputationalForm.html", method = RequestMethod.GET)
    String ajaxForNewRelationshipDiv(HttpServletRequest request, Model model) {
        logger.debug(":Due Diligence Work Flow: Reputaional Review from processing.");
        String endPoint = request.getParameter("endPoint") != null ? request.getParameter("endPoint") : "0";

        model.addAttribute("endPoint",endPoint);
        model.addAttribute("newReputationalReviewBusinessPartner", new NewReputationalReviewBusinessPartner());
        //adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.new.reputational.review"), 3, request); // adding breadcrumb
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/newReputationalForm";
    }

    /*
    * Method for viewing reputational History
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/reputationalHistory.html", method = RequestMethod.GET)
    public String getReputationalHistryList(HttpServletRequest request, Model model) {
        logger.debug("Reputaional Histry Controller");
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.reputationalReviewHistory"), 3, request); // adding breadcrumb
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/reputationalHistory";
    }

    /*
    * Method for viewing existing vendor from reputational review
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/existingVendorsForReputaionalReview.html", method = RequestMethod.GET)
    public String getExistingReputationalReview(HttpServletRequest request, Model model) {

         // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.new.reputational.review"), 2, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/existingReputationalReview";
    }

    /*
    * Method for viewing existing vendor from financial review
    * @param HttpServletRequest request, Model model
    * @return type String
    */
	@RequestMapping(value = "/riskasst/existingVendorsForFinancialReview.html", method=RequestMethod.GET)
	public ModelAndView getExistingVendorsFinancialReview(HttpServletRequest request, Model model) {
		logger.debug("Existiong vendors financial reviwe controler ");
		List<Region> regionList = new ArrayList<Region>();
		
		try {
			regionList = adminService.getRegionList();
			logger.debug("Region List size :" + regionList.size());
		}catch(Exception ex) {
			logger.debug("ERROR: Existiong vendors financial reviwe controler " + ex);
		}
		
		model.addAttribute("countryList",regionList );
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.existing.financial.review"),4, request); // adding breadcrumb
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return new ModelAndView("common/existingFinancialReview", "command", new Vendor());
	}

    /*
    * Method to process ReputationalForm data by ajax
    * @param @ModelAttribute("newReputationalReviewBusinessPartner") NewReputationalReviewBusinessPartner newReputationalReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/reputationalFormProcess.html", method = RequestMethod.POST)
    String ajaxForreputationalFormProcess(@ModelAttribute("newReputationalReviewBusinessPartner") NewReputationalReviewBusinessPartner newReputationalReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model) {
        logger.debug(":Due Diligence Work Flow: Reputaional Review from processing.");
        if(newReputationalReviewBusinessPartner == null)
            newReputationalReviewBusinessPartner = new  NewReputationalReviewBusinessPartner();

        List<WorldCompliance> wcList = new ArrayList<WorldCompliance>();
        WorldCompliance aWC = new WorldCompliance();
        aWC.setId(0);
        aWC.setFindedResult("SUFIAN;a.k.a. ABULAIL; a.k.a. DJOLAIBA THE SUDANESE; a.k.a. JOLAIBA; a.k.a. OULD EL SAYEIGH); DOB 06 Aug 1962; POB Al-Bawgah, Sudan; alt. POB Albaouga, Sudan; nationality Canada;alt. nationalit	y Sudan; Passport BC166787 (Canada) (individual)[SDGT].");
        wcList.add(aWC);
        aWC = new WorldCompliance();
        aWC.setId(1);
        aWC.setFindedResult("ABD AL RAZEQ, Abu Sufian (a.k.a. 'ABD AL-RAZZIQ, Abu Sufian al- Salamabi Muhammed Ahmed; a.k.a. ABDELRAZEK, Abousofian; a.k.a. ABDELRAZIK, Abousfian Salman; a.k.a. ABDELRAZIK, Abousofian; a.k.a. ABDELRAZIK, Abousofiane; a.k.a. ABDELRAZIK, Sofian; a.k.a. ABOU EL LAYTH; a.k.a. ABOULAIL; a.k.a. ABUJUIRIAH; a.k.a. ABU");
        wcList.add(aWC);
        aWC = new WorldCompliance();
        aWC.setId(2);
        aWC.setFindedResult("ABD AL-RAZZIQ, Abu Sufian al-Salamabi Muhammed Ahmed (a.k.a. ABD AL RAZEQ, Abu Sufian; a.k.a. ABDELRAZEK, Abousofian; a.k.a. ABDELRAZIK, Abousfian Salman; a.k.a. ABDELRAZIK, Abousofian; a.k.a. ABDELRAZIK, Abousofiane; a.k.a. ABDELRAZIK, Sofian; a.k.a. ABOU EL LAYTH; a.k.a. ABOULAIL; a.k.a. ABUJUIRIAH; a.k.a. ABU SUFIAN; a.k.a. ABULAIL");
        wcList.add(aWC);
        newReputationalReviewBusinessPartner.setWcList(wcList);
        model.addAttribute("newReputationalReviewBusinessPartner", newReputationalReviewBusinessPartner);
        model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.new.reputational.review"), 4, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/reputationalFormProcess";
    }

    /*
    * Method to process ReputationalForm data
    * @param @ModelAttribute("newReputationalReviewBusinessPartner") NewReputationalReviewBusinessPartner newReputationalReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/reputationalFormProcess.html", method = RequestMethod.GET)
    String reputationalFormProcess(@ModelAttribute("newReputationalReviewBusinessPartner") NewReputationalReviewBusinessPartner newReputationalReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model) {
        logger.debug(":Due Diligence Work Flow: Reputaional Review from processing.");
        if(newReputationalReviewBusinessPartner == null)
            newReputationalReviewBusinessPartner = new  NewReputationalReviewBusinessPartner();

        List<WorldCompliance> wcList = new ArrayList<WorldCompliance>();
        WorldCompliance aWC = new WorldCompliance();
        aWC.setId(0);
        aWC.setFindedResult("SUFIAN;a.k.a. ABULAIL; a.k.a. DJOLAIBA THE SUDANESE; a.k.a. JOLAIBA; a.k.a. OULD EL SAYEIGH); DOB 06 Aug 1962; POB Al-Bawgah, Sudan; alt. POB Albaouga, Sudan; nationality Canada;alt. nationalit	y Sudan; Passport BC166787 (Canada) (individual)[SDGT].");
        wcList.add(aWC);
        aWC = new WorldCompliance();
        aWC.setId(1);
        aWC.setFindedResult("ABD AL RAZEQ, Abu Sufian (a.k.a. 'ABD AL-RAZZIQ, Abu Sufian al- Salamabi Muhammed Ahmed; a.k.a. ABDELRAZEK, Abousofian; a.k.a. ABDELRAZIK, Abousfian Salman; a.k.a. ABDELRAZIK, Abousofian; a.k.a. ABDELRAZIK, Abousofiane; a.k.a. ABDELRAZIK, Sofian; a.k.a. ABOU EL LAYTH; a.k.a. ABOULAIL; a.k.a. ABUJUIRIAH; a.k.a. ABU");
        wcList.add(aWC);
        aWC = new WorldCompliance();
        aWC.setId(2);
        aWC.setFindedResult("ABD AL-RAZZIQ, Abu Sufian al-Salamabi Muhammed Ahmed (a.k.a. ABD AL RAZEQ, Abu Sufian; a.k.a. ABDELRAZEK, Abousofian; a.k.a. ABDELRAZIK, Abousfian Salman; a.k.a. ABDELRAZIK, Abousofian; a.k.a. ABDELRAZIK, Abousofiane; a.k.a. ABDELRAZIK, Sofian; a.k.a. ABOU EL LAYTH; a.k.a. ABOULAIL; a.k.a. ABUJUIRIAH; a.k.a. ABU SUFIAN; a.k.a. ABULAIL");
        wcList.add(aWC);
        newReputationalReviewBusinessPartner.setWcList(wcList);
        model.addAttribute("newReputationalReviewBusinessPartner", newReputationalReviewBusinessPartner);
        model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.new.reputational.review"), 4, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/reputationalFormProcess";
    }

    /*
    * Method to update ReputationalReview
    * @param @ModelAttribute("newReputationalReviewBusinessPartner") NewReputationalReviewBusinessPartner newReputationalReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/updateReputationalReview.html", method = RequestMethod.POST)
    String updateReputationalReview(@ModelAttribute("newReputationalReviewBusinessPartner") NewReputationalReviewBusinessPartner newReputationalReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model) {
        logger.debug(" New Financial Project Form Controller");
        return "redirect:./newReputationalForm.html";
    }

    /*
    * Method to show new Financial Project by ajax call
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/newFinancialProject.html", method = RequestMethod.GET)
    String ajaxForNewFinancialProject(HttpServletRequest request, Model model) {
        logger.debug(" New Financial Project Form Controller");
        model.addAttribute("newFinancialReviewBusinessPartner", new NewFinancialReviewBusinessPartner());
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.newFinancialReviewForm"), 3, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/newFinancialReview";
    }

    /*
    * Method to process new Financial review data by ajax call
    * @param @ModelAttribute("newFinancialReviewBusinessPartner") NewFinancialReviewBusinessPartner newFinancialReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/newFinancialReviewProcess.html", method = RequestMethod.POST)
    String ajaxForNewFinancialReviewProcess(@ModelAttribute("newFinancialReviewBusinessPartner") NewFinancialReviewBusinessPartner newFinancialReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model) {
        logger.debug(":Due Diligence Work Flow: Financial Review from processing.");
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.newFinancialReviewForm"), 3, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/newFinancialReviewProcess";
    }

    /*
    * Method to process new Financial review data by ajax call
    * @param @ModelAttribute("newFinancialReviewBusinessPartner") NewFinancialReviewBusinessPartner newFinancialReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/newFinancialReviewProcess.html", method = RequestMethod.GET)
    String ajaxForNewFinancialReviewProcessGet(@ModelAttribute("newFinancialReviewBusinessPartner") NewFinancialReviewBusinessPartner newFinancialReviewBusinessPartner
            , BindingResult result
            , HttpServletRequest request
            , Model model) {
        logger.debug(":Due Diligence Work Flow: Financial Review from processing.");
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.newFinancialReviewForm"), 3, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/newFinancialReviewProcess";
    }

    /*
    * Method to show financial Review Summary
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/financialReviewSummary.html", method = RequestMethod.GET)
    String financialReviewSummary(HttpServletRequest request, Model model) {
        logger.debug(" New Financial Review Summary Form Controller(BY AJAX CALL)");
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.landingPage.financialReviewSummary"), 3, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/financialReviewSummary";
    }

    /*
    * Method to show due Diligence Landing page(new)
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/dueDiligenceLanding.html", method = RequestMethod.GET)
    String getDueDiligenceLanding(HttpServletRequest request, Model model) {
        logger.debug(" Due Diligence Landing Page");
        // adding breadcrumb
        adminService.addNode(Utils.getMessageBundlePropertyValue("thirdPartyReview"), 2, request);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/dueDiligenceLanding";
    }

    /*
    * Method to show balance Sheet
    * @return type String
    */
    @RequestMapping(value = "/riskasst/balanceSheet.html", method = RequestMethod.GET)
    public String getBalanceSheet() {
        logger.debug("Balance Sheet Form Controller");
        return "common/balanceSheet";
    }

    /*
    * Method to show income Statement
    * @return type String
    */
    @RequestMapping(value = "/riskasst/incomeStatement.html", method = RequestMethod.GET)
    public String getIncomeStatement() {
        logger.debug("Income Statement Form Controller");
        return "common/incomeStatement";
    }

    /*
    * Method to upload file for Reputational new Form
    * @param MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/ajaxCallForReputationalFormFileUpload.html", method = RequestMethod.POST)
    public String ajaxCallForReputationalFormFileUpload(MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model) {
        try {
            logger.debug(" ajaxCallForReputationalFormFileUpload---POST-");
            String fileIconPath = "";
            String uploadPath = "uploadFiles";
            Iterator<String> itr = request.getFileNames();
            MultipartFile multipartFileForReputationalForm = request.getFile(itr.next());   // get multiPartFile from request

            String uploadPathForReputationalForm = Utils.getMessageBundlePropertyValue("dueDilligance.uploadFilePathForReputationalReview");
            String orginalNameForReputationalForm = multipartFileForReputationalForm.getOriginalFilename();

            String filePathForReputationalForm = (System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForReputationalForm + File.separator + "" + Utils.getTodaysDate().getTime() + "_" + orginalNameForReputationalForm);
            File dirForReputationalForm = new File(System.getProperty("user.home") + File.separator + uploadPath + File.separator + uploadPathForReputationalForm);
            String fileIconPathForReputationalForm = "";
            logger.debug("FILE TO SAVE :" + filePathForReputationalForm);
            try {
                if (!dirForReputationalForm.exists()) {  // make directory to upload file if not exist
                    dirForReputationalForm.mkdirs();
                }
                fileIconPathForReputationalForm = ProactiveController.getFileIconPath(multipartFileForReputationalForm);

                if (!Utils.isEmpty(orginalNameForReputationalForm))
                    multipartFileForReputationalForm.transferTo(new File(filePathForReputationalForm)); // upload file to specific directory
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "ajaxForFileUpload";
    }

    /*
    * Method to show existing Vendors List
    * @param MultipartHttpServletRequest request
    * @return type String
    */
    @RequestMapping(value = "/riskasst/existingVendorsList.html", method = RequestMethod.GET)
	public String getExistingVendorsList(HttpServletRequest request,Model model){
		logger.debug("Existing Vendor List Controler");
		String vendorName = request.getParameter("name"); 
		String zip = request.getParameter("zip"); 
		String countryCode = request.getParameter("countryCode");
		List<Vendor> vendorsList = new ArrayList<Vendor>(); 
		try{
			vendorsList = adminService.getVendorList(vendorName, zip, countryCode);
			logger.debug("Existing vendor list size :" + vendorsList.size());
		}catch(Exception ex) {
			logger.debug("CERRORL:Existing Vendor List ");
		}
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "existingVendorsList";
	}

    /*
    * Method to show existing Vendors List for financial review
    * @param MultipartHttpServletRequest request
    * @return type String
    */
    @RequestMapping(value = "/riskasst/existingVendorsListForFinancialReview.html", method = RequestMethod.GET)
    public String getExistingVendorsListForFinancialReview(HttpServletRequest request,Model model){
        logger.debug(" Existing Vendor List ForFinancialReview Controler");
        String vendorName = request.getParameter("name");
        String zip = request.getParameter("zip");
        String countryCode = request.getParameter("countryCode");
        List<Vendor> vendorsList = new ArrayList<Vendor>();
        try{
            vendorsList = adminService.getVendorList(vendorName, zip, countryCode);
            logger.debug("Existing vendor list size :" + vendorsList.size());
        }catch(Exception ex) {
            logger.debug("CERRORL:Existing Vendor List ");
        }
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "existingVendorsListForFinancialReview";
    }

    /*
* Method for viewing new ReputationalForm Print
* @param HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "/riskasst/newReputationalFormPrint.html", method = RequestMethod.GET)
    String newReputationalFormPrint(HttpServletRequest request, Model model) {
        logger.debug(":New Reputational Form Print Controller.");

        String endPoint = request.getParameter("endPoint") != null ? request.getParameter("endPoint") : "0";
        String companyName = request.getParameter("cName") != null ? request.getParameter("cName") : "Accuride Corporation.";
        String userName = request.getParameter("uName") != null ? request.getParameter("uName") : "Britanney Castaneda.";
        String created = request.getParameter("created") != null ? request.getParameter("created") : "02-05-2014";
        model.addAttribute("endPoint",endPoint);
        model.addAttribute("companyName",companyName);
        model.addAttribute("userName",userName);
        model.addAttribute("created",created);
        adminService.addNode(Utils.getMessageBundlePropertyValue("summary"), 3, request); // adding breadcrumb
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/newReputationalFormPrint";
    }
/*
* Method for viewing new ReputationalForm Print
* @param HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "/riskasst/newReputationalFormSearch.html", method = RequestMethod.GET)
    String newReputationalFormSearch(HttpServletRequest request, Model model) {
        logger.debug(":New Reputational Form Search Controller.");

        String endPoint = request.getParameter("endPoint") != null ? request.getParameter("endPoint") : "0";
        model.addAttribute("endPoint",endPoint);
        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.landingPage.header.ReputationalReview"), 2, request); // adding breadcrumb
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/newReputationalFormSearch";
    }
/*
* Method for viewing Financial Review
* @param HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "/riskasst/financialReview.html", method = RequestMethod.GET)
    String financialReview(HttpServletRequest request, Model model) {
        logger.debug(":Financial Review Controller.");

        adminService.addNode(Utils.getMessageBundlePropertyValue("dueDilligance.landingPage.header.financialReview"), 2, request); // adding breadcrumb
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.thirdPartyReview"));
        return "common/newFinancialReview";
    }

    @RequestMapping(value = "/riskasst/getJASONforReputationalReview.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getJASONforreputationalReview(HttpServletRequest request, Model model) {
        logger.debug("Get Reputational Review Json controller");
        String page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        logger.debug("rp : " + rp);
        JasonBean jasonData = new JasonBean();

        int totalRealTimeProject = 0;
        List<GlobalTransactionSearch> realTmeProjectList = new ArrayList<GlobalTransactionSearch>();
        List dbColumnHeaderList = new ArrayList();
        List<Cell> entry = new ArrayList<Cell>();

        try {
            totalRealTimeProject = 7;
            realTmeProjectList = makeTransactionListForRepuReview(request);

            if("max".equals(rp)){
                TransactionSearchController.setTotalListSizeAndListInSession(totalRealTimeProject,realTmeProjectList,request);
                jasonData.setTotal(totalRealTimeProject);
            }else{
                if(realTmeProjectList != null) {
                    logger.debug("Project List Size : " + realTmeProjectList.size());
                    jasonData.setPage(Utils.parseInteger(page));
                    for(GlobalTransactionSearch globalTransactionSearch: realTmeProjectList) {
                        Cell cell = new Cell();

                        cell.setId(globalTransactionSearch.getTransactionId());
                        cell.setCell(globalTransactionSearch);
                        entry.add(cell);
                    }
                    Map mapForHeader = new HashMap();
                    mapForHeader.put("Business Name","businessName"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("User Name","userName");
                    mapForHeader.put("Created","createdDateStr");
                    mapForHeader.put("Others Documents","otherDoc");
                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);


                    jasonData.setRows(entry);
                    jasonData.setTotal(totalRealTimeProject);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);
                }
            }
        } catch (Exception ex) {
            logger.error("CERROR:Reputational Review exception : " + ex);
        }

        logger.info("Reputational Review Controller End.");
        return jasonData;
    }

    public List<GlobalTransactionSearch> makeTransactionListForRepuReview(HttpServletRequest request){
        List<GlobalTransactionSearch> list = new ArrayList<GlobalTransactionSearch>();

        GlobalTransactionSearch globalTransactionSearch = new GlobalTransactionSearch();

        globalTransactionSearch.setTransactionId(1l);
        globalTransactionSearch.setBusinessName("globalTransactionSearch");
        globalTransactionSearch.setUserName("Serena Buchanan");
        globalTransactionSearch.setCreatedDateStr("01/15/2014");
        globalTransactionSearch.setApprover("<img src=\""+request.getContextPath()+"/resources/images/csv.png\" class=\"fileIcon\">");
        globalTransactionSearch.setOtherDoc("Contract");
        list.add(globalTransactionSearch);
        globalTransactionSearch = new GlobalTransactionSearch();

        globalTransactionSearch.setTransactionId(2l);
        globalTransactionSearch.setBusinessName("Bellwether Technology Corporation");
        globalTransactionSearch.setUserName("Vivian Christensen");
        globalTransactionSearch.setCreatedDateStr("03/28/2014");
        globalTransactionSearch.setApprover("<img src=\""+request.getContextPath()+"/resources/images/pdf.png\" class=\"fileIcon\">");
        globalTransactionSearch.setOtherDoc("Contract");
        list.add(globalTransactionSearch);
        globalTransactionSearch = new GlobalTransactionSearch();

        globalTransactionSearch.setTransactionId(3l);
        globalTransactionSearch.setBusinessName("Brocade Communications Systems");
        globalTransactionSearch.setUserName("Nyssa Dickson");
        globalTransactionSearch.setCreatedDateStr("02/12/2014");
        globalTransactionSearch.setApprover("<img src=\""+request.getContextPath()+"/resources/images/xlsx.png\" class=\"fileIcon\">");
        globalTransactionSearch.setOtherDoc("Contract");
        list.add(globalTransactionSearch);
        globalTransactionSearch = new GlobalTransactionSearch();

        globalTransactionSearch.setTransactionId(4l);
        globalTransactionSearch.setBusinessName("Bucyrus International");
        globalTransactionSearch.setUserName("Savage Heredia");
        globalTransactionSearch.setCreatedDateStr("01/18/2014");
        globalTransactionSearch.setApprover("<img src=\""+request.getContextPath()+"/resources/images/pdf.png\" class=\"fileIcon\">");
        globalTransactionSearch.setOtherDoc("Contract");
        list.add(globalTransactionSearch);
        globalTransactionSearch = new GlobalTransactionSearch();

        globalTransactionSearch.setTransactionId(5l);
        globalTransactionSearch.setBusinessName("Cognizant Technology Solutions");
        globalTransactionSearch.setUserName("Macdonald Rhineland-Palatinate");
        globalTransactionSearch.setCreatedDateStr("02/20/2014");
        globalTransactionSearch.setApprover("<img src=\""+request.getContextPath()+"/resources/images/text.png\" class=\"fileIcon\">");
        globalTransactionSearch.setOtherDoc("Contract");
        list.add(globalTransactionSearch);
        globalTransactionSearch = new GlobalTransactionSearch();

        globalTransactionSearch.setTransactionId(6l);
        globalTransactionSearch.setBusinessName("Carnival Corporation & plc");
        globalTransactionSearch.setUserName("Dickson Bremen");
        globalTransactionSearch.setCreatedDateStr("01/29/2014");
        globalTransactionSearch.setApprover("<img src=\""+request.getContextPath()+"/resources/images/docx.png\" class=\"fileIcon\">");
        globalTransactionSearch.setOtherDoc("Contract");
        list.add(globalTransactionSearch);
        globalTransactionSearch = new GlobalTransactionSearch();

        globalTransactionSearch.setTransactionId(7l);
        globalTransactionSearch.setBusinessName("Corrections Corporation of America");
        globalTransactionSearch.setUserName("Allen Puntarenas");
        globalTransactionSearch.setCreatedDateStr("03/19/2014");
        globalTransactionSearch.setApprover("<img src=\""+request.getContextPath()+"/resources/images/jpeg.png\" class=\"fileIcon\">");
        globalTransactionSearch.setOtherDoc("Contract");
        list.add(globalTransactionSearch);
    return list;
    }
}
