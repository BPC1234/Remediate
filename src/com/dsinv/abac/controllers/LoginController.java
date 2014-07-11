package com.dsinv.abac.controllers;

import com.dsinv.abac.Entity;
import com.dsinv.abac.entity.Role;
import com.dsinv.abac.entity.User;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.UserValidation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes({"user"})
public class LoginController {  // to handle login related task

    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private UserValidation userValidator;

    /*
    * Method to show login page
    * @param @RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String redirectLogin(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model) {
        if (!Utils.isNullOrEmpty(error)) {
            model.addAttribute("error", Utils.getMessageBundlePropertyValue("login.error"));
        }
        model.addAttribute("landingPageMainTab", 0);
        return "login";
    }

    /*
    * Method to show landing page for successful login  or login page again
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/forward.html", method = RequestMethod.GET)
    public String forwardOnRole(HttpServletRequest request, Model model) {
        logger.debug("Forward Controller Start.");
        logger.debug("AMLOG:: system user: " + System.getProperty("user.home"));
        if (Utils.isInRole(Role.ADMIN.getLabel())) {
            logger.debug("Forward Controller Redirect AS " + Role.ADMIN.getLabel());
            return "redirect:/admin/landingPage.html";
        }else if(Utils.isInRole(Role.EMPLOYEE.getLabel())){
            logger.debug("Forward Controller Redirect AS " + Role.EMPLOYEE.getLabel());
            return "redirect:/common/landingPage.html";
        } else if(Utils.isInRole(Role.LEGAL.getLabel())){
            logger.debug("Forward Controller Redirect AS " + Role.LEGAL.getLabel());
            return "redirect:/legal/landingPage.html";
        }else if(Utils.isInRole(Role.IA_ANALYST.getLabel())){
            logger.debug("Forward Controller Redirect AS " + Role.IA_ANALYST.getLabel());
            return "redirect:/iaanalyst/landingPage.html";
        }else if(Utils.isInRole(Role.IA_MANAGER.getLabel())){
            logger.debug("Forward Controller Redirect AS " + Role.IA_MANAGER.getLabel());
            return "redirect:/iamanager/landingPage.html";
        }else if(Utils.isInRole(Role.COMPLIANCE.getLabel())){
            logger.debug("Forward Controller Redirect AS " + Role.COMPLIANCE.getLabel());
            return "redirect:/compliance/landingPage.html";
        } else {
            return "redirect:/login.html";
        }
    }

    /*
    * Method to show access denied page for unauthorised or invalid request
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/access-denied.html", method = RequestMethod.GET)
    public String accessDenied(HttpServletRequest request, Model model) {
        return "accessDenied";
    }

    /*
* Method to show landing page for successful login  or login page again
* @param HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "admin/transNarrativeCopy.html", method = RequestMethod.GET)
    public String copyForTemp(HttpServletRequest request, Model model) {

        //SUBSTRING_INDEX( REPLACE( ApprovalLimit, ',', '' ), '$', -1) AS   ApprovalLimit
        String ooption = request.getParameter("opt");
        logger.debug("----OPT----" + ooption);
        List arList = new ArrayList();
        List list = new ArrayList();

        if ("1".equals(ooption)) {
            arList = adminJdbcService.getAccountReceivableList("accounts_receivable_ledger_information");
            logger.debug("----accounts_receivable_ledger_information-list size----" + arList.size());
            list = adminJdbcService.getAccountReceivableList("ARDetail");
            logger.debug("-----ARDetail size----" + list.size());

            for (int i = 0; i < arList.size(); i++) {
                Map arListmap = (Map) arList.get(i);

                Map map = (Map) list.get(i);

                String sql = "UPDATE accounts_receivable_ledger_information ar "
                        + " SET ar.transaction_narrative_description = '" + map.get("TransNarrative") + "'"
                        + " WHERE ar.id =" + arListmap.get("id");

                logger.debug("SQL=" + sql);

                adminJdbcService.executeQueryForTransNarrativeSave(sql);
            }
        } else if ("2".equals(ooption)) {
            arList = adminJdbcService.getAccountReceivableList("EMF");
            logger.debug("----employee_master_ledger-list size----" + arList.size());
            list = adminJdbcService.getAccountReceivableList("employee_master_ledger");
            logger.debug("-----EML size----" + list.size());

            for (int i = 0; i < arList.size(); i++) {
                Map map = (Map) arList.get(i);
                Map arMap = (Map) list.get(i);
                String sql = "UPDATE employee_master_ledger eml"
                        + " SET eml.approval_limit = SUBSTRING_INDEX( REPLACE( '" + map.get("ApprovalLimit") + "', ',', '' ), '$', -1)"
                        + " WHERE eml.id =" + arMap.get("id");

                logger.debug("SQL=" + sql);

                adminJdbcService.executeQueryForTransNarrativeSave(sql);
            }


        } else if ("3".equals(ooption)) {
            String arr[] = {"care of","C/O"};
            arList = adminJdbcService.getListByCustomQuery("SELECT vm.entity_address FROM vendor_master_ledger vm"
                     + " WHERE vm.entity_address NOT LIKE '%P.O.%' "
                     + " AND vm.entity_address NOT LIKE  '%pobox%' "
                     + " AND vm.entity_address NOT LIKE '%po box%' "
                     + " AND vm.entity_address NOT LIKE '%p o%'");
            logger.debug("----list size----" + arList.size());

            list = adminJdbcService.getAccountReceivableList("vendor_master_ledger");
            logger.debug("-----vendor master ledger size----" + list.size());
            int count = 5;
            int arListSize = 0;
            for (int i = 0; i < list.size(); i++) {
                count--;
                int arrCount = 1;
                boolean test = true;
                Map map = (Map) arList.get(arListSize++);
                Map arMap = (Map) list.get(i);
                logger.debug("arListSize="+arListSize);
                if(arListSize >= (arList.size()-1)){
                    arListSize = 0;
                }
                String address = arMap.get("entity_address") != null? arMap.get("entity_address").toString(): "";
                String addrerss1 = "";
                if(address.contains("P.O.") || address.contains("pobox")|| address.contains("po box")|| address.contains("p o")){
                    if(i%4==0){
                        addrerss1 = "";
                    }else
                        addrerss1 = map.get("entity_address") != null? map.get("entity_address").toString(): "";

                    test = false;
                }else{
                    addrerss1 = map.get("entity_address") != null? map.get("entity_address").toString(): "";
                    test = true;
                }

                if(i%5==0 && test==true){
                    addrerss1 = arr[arrCount--]+" "+addrerss1;
                }

                if(arrCount == -1){
                    arrCount = 1;
                }

                String sql = "UPDATE vendor_master_ledger vml"
                        + " SET vml.entity_address1 = '" + addrerss1 +"'"
                        + " WHERE vml.id =" + arMap.get("id");


                if(test == true && count == 0){
                    count = 5;
                }else{
                    adminJdbcService.executeQueryForTransNarrativeSave(sql);
                }

                logger.debug("SQL=" + sql);
            }
        }else if ("4".equals(ooption)) {
            String arr[] = {"care of","C/O"};
            arList = adminJdbcService.getListByCustomQuery("SELECT vm.entity_address FROM customer_master_ledger vm"
                     + " WHERE vm.entity_address NOT LIKE '%P.O.%' "
                     + " AND vm.entity_address NOT LIKE  '%pobox%' "
                     + " AND vm.entity_address NOT LIKE '%po box%' "
                     + " AND vm.entity_address NOT LIKE '%p o%'");
            logger.debug("----list size----" + arList.size());

            list = adminJdbcService.getAccountReceivableList("customer_master_ledger");
            logger.debug("-----vendor master ledger size----" + list.size());
            int count = 5;
            int arListSize = 0;
            for (int i = 0; i < list.size(); i++) {
                count--;
                int arrCount = 1;
                boolean test = true;
                Map map = (Map) arList.get(arListSize++);
                Map arMap = (Map) list.get(i);
                logger.debug("arListSize="+arListSize);
                if(arListSize >= (arList.size()-1)){
                    arListSize = 0;
                }
                String address = arMap.get("entity_address") != null? arMap.get("entity_address").toString(): "";
                String addrerss1 = "";
                if(address.contains("P.O.") || address.contains("pobox")|| address.contains("po box")|| address.contains("p o")){
                    if(i%4==0){
                        addrerss1 = "";
                    }else
                        addrerss1 = map.get("entity_address") != null? map.get("entity_address").toString(): "";

                    test = false;
                }else{
                    addrerss1 = map.get("entity_address") != null? map.get("entity_address").toString(): "";
                    test = true;
                }

                if(i%5==0 && test==true){
                    addrerss1 = arr[arrCount--]+" "+addrerss1;
                }

                if(arrCount == -1){
                    arrCount = 1;
                }

                String sql = "UPDATE customer_master_ledger vml"
                        + " SET vml.entity_address1 = '" + addrerss1 +"'"
                        + " WHERE vml.id =" + arMap.get("id");


                if(test == true && count == 0){
                    count = 5;
                }else{
                    adminJdbcService.executeQueryForTransNarrativeSave(sql);
                }


            }
           } else if ("5".equals(ooption)) {
            arList = adminJdbcService.getListByCustomQuery("SELECT STR_TO_DATE(oe.ApproverDt, '%m/%d/%Y') as ApproverDate  FROM OEDetail oe");
            list = adminJdbcService.getAccountReceivableList("transaction");
            int arListSize = 0;
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) arList.get(arListSize++);
                Map arMap = (Map) list.get(i);

                if(arListSize >= (arList.size()-1)){
                    arListSize = 0;
                }

                String approveDate= map.get("ApproverDate") != null? map.get("ApproverDate").toString():"";
                logger.debug("AMLOG:: approveDate: " + approveDate);
                if(approveDate != null)   {
                String sql = "UPDATE transaction tx"
                        + " SET tx.approve_date = '" + approveDate +"'"
                        + " WHERE tx.id =" + arMap.get("id");
                logger.debug("AMLOG:: sql: " + sql);
                    adminJdbcService.executeQueryForTransNarrativeSave(sql);
                }
            }
        } else if ("6".equals(ooption)) {
            logger.debug("SMN LOG: option 6");
            arList = adminJdbcService.getListByCustomQuery("SELECT *"
                    + " FROM transaction"
                    + " WHERE customer_master_ledger_FK IS NULL"
                    + " AND employee_master_ledger_FK IS NULL AND vendor_master_ledger_FK IS NULL");

            logger.debug("SMN LOG: first list size="+arList.size());
            List employeeList = adminJdbcService.getAccountReceivableList("employee_master_ledger");
            logger.debug("SMN LOG: employee list size="+employeeList.size());
            List customerList = adminJdbcService.getAccountReceivableList("customer_master_ledger");
            logger.debug("SMN LOG: customer list size="+customerList.size());
            int employeeListSize = 0;
            int customerListSize = 0;

            for (int i = 0; i < arList.size(); i++) {
                Map empMap = (Map) employeeList.get(employeeListSize++);
                Map cusMap = (Map) customerList.get(customerListSize++);
                Map trxMap = (Map) arList.get(i);

                String sql = "UPDATE transaction tx"
                        + " SET tx.customer_master_ledger_FK = '" + cusMap.get("id")+"'"
                        + ",tx.employee_master_ledger_FK = '" + empMap.get("id") +"'"
                        + " WHERE tx.id =" + trxMap.get("id");
                logger.debug("AMLOG:: sql: " + sql);
                    adminJdbcService.executeQueryForTransNarrativeSave(sql);
            }
        } else if("7".equals(ooption)) {
        List emfList = adminJdbcService.getTempLedgerData("EMF");
        if(emfList != null && emfList.size() > 0) {
            for (int i = 0; i < emfList.size(); i++) {
                Map map = (Map) emfList.get(i);
                long EMPid = Long.parseLong(map.get("EMPid") != null? map.get("EMPid").toString(): "");
                long cmpid = adminJdbcService.getCustomerMasterLedgerId(EMPid);
                if(cmpid == 0) {
                    saveCusomerMasterLedger(0, EMPid);
                }
                long empId = adminJdbcService.getEmployeeMasterLedgerId("");
                if(empId == 0) {
                    saveEmployeeMasterLedger(0, EMPid);
                }
            }
        }
    }else if("8".equals(ooption)) {        // generate employee master ledger table
        List emfList = adminJdbcService.getTempLedgerData("EMF");
        if(emfList != null && emfList.size() > 0) {
            for (int i = 0; i < emfList.size(); i++) {
                Map map = (Map) emfList.get(i);
                long id =  Long.parseLong(map.get("id") != null? map.get("id").toString(): "");
                saveCusomerMasterLedger(0, id);
                saveEmployeeMasterLedger(0, id);

            }
        }
    }
        return "login";
    }

    @RequestMapping(value = "/*/sendAnEmail.html", method = RequestMethod.GET)
    public String sendAnEmail( HttpServletRequest request, @RequestParam("name") String name, Model model) {
        logger.debug("Send email controller start.");
        String subject = Utils.getApplicationPropertyValue("reset.mail.subject");
        String from = Utils.getApplicationPropertyValue("reset.password.mail.sender");
        String body = "";
        User user = null;
        try {
            Object userObj = adminService.getAbstractBaseEntityByString(Constants.USER, "user_name", name);
            if(userObj == null) {
                model.addAttribute("error", Utils.getMessageBundlePropertyValue("user.not.found.error"));
                return "redirect:/login.html";
            } else {
                user = (User) userObj;
            }
            String[] to = {user.getEmail()};
            String[] bcc={};
            body = Utils.getStringFromFile(LoginController.class.getResource("/../../resources/reset-password-mail.txt").getPath());
            body = body.replace("?", user.getUserName());
            body = body.replace("#", "http://" + request.getLocalAddr() + ":" + request.getLocalPort()+request.getContextPath() + "/admin/resetPassword.html?name=" + Utils.encryptSt(user.getUserName().trim()));
            logger.debug("AMLOG:: User name decript: " + Utils.decryptSt(Utils.encryptSt(user.getUserName().trim())) );
            Utils.sendAnEmail(from, to,bcc, subject, body);
            model.addAttribute("userMail", user.getEmail());
        } catch (Exception e) {
            logger.debug("CERROR:: Failed to send mail. " + e);
            logger.debug("CERROR:: Failed to send mail description. " + e.getMessage());
        }

        logger.debug("Send email controller end.");
        return "admin/emailRecovery";
    }
    @RequestMapping(value = "/*/resetPassword.html", method = RequestMethod.GET)
    public String resetUserPassword(HttpServletRequest request, @RequestParam("name") String name, Model model ) {
        logger.debug("Password change controller start.");
        User tempUser = new User();

        try {
             logger.debug("AMLOG:: User name: " + Utils.decryptSt(name) );
             tempUser = (User)adminService.getAbstractBaseEntityByString(Constants.USER, "user_name", Utils.decryptSt(name) );
             logger.debug("User id: " + tempUser.getId());
        } catch (Exception ex) {
            logger.debug("CERROR:: Reset password exception: " + ex.getMessage());
            logger.debug("CERROR:: Reset password exception description: " + ex);
        }
        logger.debug("Password change controller end.");
        model.addAttribute("user",tempUser);
        return "admin/resetPassword";
    }
    @RequestMapping(value = "/*/resetPassword.html", method = RequestMethod.POST)
    public String saveNewPassword(HttpServletRequest request, @ModelAttribute("user") User tempUser, BindingResult result) {
        logger.debug("Save new password start.");
        try {

            userValidator.validateNewPassword(tempUser, result, adminService);
            if(result.hasErrors()) {
                logger.debug("Conformation password not match.");
                return "admin/resetPassword";
            } else {
                tempUser.setPassword(Utils.encrypt(tempUser.getGivenPassword()));
                adminService.saveOrUpdate(tempUser);
            }

        } catch (Exception e) {
            logger.debug("CERROR:: Save new password exception: " + e);
            logger.debug("CERROR:: Save new password exception description: " + e.getMessage());
        }

        logger.debug("Save new password end.");
        return "redirect:/login.html";
    }

    /*
* Method to show login page
* @param @RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "home.html", method = RequestMethod.GET)
    public String getHome(HttpServletRequest request, Model model) {
        logger.debug("Home page Controller:");
        return "common/home";
    }
/*
* Method to show login page
* @param @RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "about.html", method = RequestMethod.GET)
    public String getAbout(HttpServletRequest request, Model model) {
        logger.debug("About page Controller:");
        return "common/about";
    }
/*
* Method to show login page
* @param @RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "contact.html", method = RequestMethod.GET)
    public String getContact(HttpServletRequest request, Model model) {
        logger.debug("Contact page Controller:");
        return "common/contact";
    }

    /*
* Method to show login page
* @param @RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "feature.html", method = RequestMethod.GET)
    public String getFeature(HttpServletRequest request, Model model) {
        logger.debug("Feature page Controller:");
        return "common/feature";
    }

/*
* Method to show login page
* @param @RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "support.html", method = RequestMethod.GET)
    public String getSupport(HttpServletRequest request, Model model) {
        logger.debug("Support page Controller:");
        return "common/support";
    }

    public long saveCusomerMasterLedger(long txId, long cmId) {

        try {
            return adminJdbcService.saveCustomerMasterLedger(txId, cmId);
        } catch ( Exception ex) {
            logger.debug("CERROR: Accounts payable ledger exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
        return 0;
    }
    public long saveEmployeeMasterLedger(long txId, long cmId) {

        try {
            return adminJdbcService.saveEmployeeMasterLedger(txId, "");
        } catch ( Exception ex) {
            logger.debug("CERROR: Accounts payable ledger exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
        return 0;
    }

    /*
* Method to show login page
* @param @RequestParam(value = "error", required = false) String error, HttpServletRequest request, Model model
* @return type String
*/
    @RequestMapping(value = "/admin/getUserObject.html", method = RequestMethod.GET)
    public @ResponseBody User getUserObject(HttpServletRequest request, Model model) {
        User user = (User)adminService.getAbstractBaseEntityByString(Entity.USER.getValue(),"userName",Utils.getLoggedUserName());
    return user;
    }
}

