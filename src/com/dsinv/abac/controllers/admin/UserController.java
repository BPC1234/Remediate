package com.dsinv.abac.controllers.admin;


import com.dsinv.abac.Entity;
import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.PasswordChangeValidation;
import com.dsinv.abac.validation.RegistrationValidation;
import com.dsinv.abac.validation.UserValidation;
import com.dsinv.abac.vo.admin.UserVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@SessionAttributes("user")
public class UserController {  // to handle user related task

    private static Logger logger = Logger.getLogger(UserController.class);
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private UserValidation userValidation;
    @Autowired(required = true)
    private RegistrationValidation registrationValidation;
    @Autowired(required = true)
    private PasswordChangeValidation passwordChangeValidation;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired
    private MessageSource messageSource;

    public void setUserValidation(
            UserValidation userValidation) {
        this.userValidation = userValidation;
    }
    public void setRegistrationValidation(
            RegistrationValidation registrationValidation) {
        this.registrationValidation = registrationValidation;
    }

    public void setPasswordChangeValidation(
            PasswordChangeValidation passwordChangeValidation) {
        this.passwordChangeValidation = passwordChangeValidation;
    }

    /*
    * Method for viewing landing Page
    * @param HttpServletRequest request, Model model
    * @return type String( or any .jsp File)
    *
    */
    @RequestMapping(value = "/*/landingPage.html", method = RequestMethod.GET)
    public String userPanel(HttpServletRequest request, Model model) {
        try{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)adminService.getAbstractBaseEntityByString(Entity.USER.getValue(),"userName",Utils.getLoggedUserName());
        request.getSession().setAttribute("loggUserId",user!= null ? user.getId():0);
        request.getSession().setAttribute("loggedUserEmail",user!= null ? user.getEmail():"");
        List realTmeProjectList = adminService.getAnyEntityList(Constants.REALTIME_PROJECT);
        int totalProject = 0;
        int totalNotificationFound = 0;
        int totalConfirmedByVendor = 0;
        int totalUnconfirmedByVendor = 0;

        if(realTmeProjectList != null){
            totalProject = realTmeProjectList.size();
        }
        List<Policy> policyList = adminService.getAllPolicy();
            if(user.getUserTypeId() >0 && PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
                int totalConfirmedByEmployee = adminJdbcService.getTotalPolicyConfirmedByAudiance(1,user);
                int totalUnconfirmedByEmployee = adminJdbcService.getTotalPolicyNotConfirmedByAudiance(1,user);
                totalNotificationFound = adminJdbcService.getTotalNotificationSendToAudiance(1);
                model.addAttribute("totalConfirmedPolicy",totalConfirmedByEmployee);
                model.addAttribute("totalUnConfirmedPolicy",totalUnconfirmedByEmployee);
                model.addAttribute("totalNotReviewedPolicy",(totalNotificationFound-(totalConfirmedByEmployee+totalUnconfirmedByEmployee)));
                /* for training part*/
                int totalCourseToBeStart = adminJdbcService.getTotalCourseToBeStartByUser(user);
                int totalCourseToBeRetake = adminJdbcService.getTotalCourseToBeRetakeOrPassedByUser(user,Constants.TRAINING_RETAKE);
                int totalCoursePassed = adminJdbcService.getTotalCourseToBeRetakeOrPassedByUser(user,Constants.TRAINING_CERTIFICATE);
                model.addAttribute("totalCourseToBeStart",totalCourseToBeStart);
                model.addAttribute("totalCourseToBeRetake",totalCourseToBeRetake);
                model.addAttribute("totalCoursePassed",totalCoursePassed);
            }else if(user.getUserTypeId() >0 && PersonType.VENDOR.getValue().equals(user.getUserType())){
                if(policyList != null) {
                    totalNotificationFound = adminJdbcService.getTotalNotificationSendToAudiance(2);
                    totalConfirmedByVendor = adminJdbcService.getTotalPolicyConfirmedByAudiance(2,user);
                    totalUnconfirmedByVendor = adminJdbcService.getTotalPolicyNotConfirmedByAudiance(2,user);
                }
            }

            if (user.getUserTypeId() > 0) {
                List unreadedPolicyList = adminJdbcService.getUnreadedPolicyList(user);
                if (unreadedPolicyList != null)
                    logger.debug("Unreaded Policy List size=" + unreadedPolicyList.size() + " for user=" + user.getUserName());
                model.addAttribute("unreadedPolicyList", unreadedPolicyList);
            }

        adminService.addNode("Home", 1, request);
        Utils.setGlobalLoginRoleName(request, Utils.getLoggedUserRoleName()); //getting logged User role name

        model.addAttribute("totalConfirmedPolicy",totalConfirmedByVendor);
        model.addAttribute("totalUnConfirmedPolicy",totalUnconfirmedByVendor);
        model.addAttribute("totalNotReviewedPolicy",(totalNotificationFound-(totalConfirmedByVendor+totalUnconfirmedByVendor)));
        model.addAttribute("roles", Role.getRoles());
        model.addAttribute("landingPageMainTab","1");
        model.addAttribute("completeAssignment",getRealTimeTransactionCountByStatus("Completed",realTmeProjectList));
        model.addAttribute("inProgressAssignment",getRealTimeTransactionCountByStatus("In Progress",realTmeProjectList));
        model.addAttribute("unAssignedAssignment",getRealTimeTransactionCountByStatus("",realTmeProjectList));
        model.addAttribute("totalPolicies",policyList != null ? policyList.size() : 0);
        model.addAttribute("totalProject",totalProject);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.dashBoard"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("dashboard"));
        }catch (Exception ex) {
            logger.error("Landing page : " + ex);
        }
            return "admin/landingPage";
    }

    /*
    * Method for getting userList from database
    * @param HttpServletRequest request, Map map
    * @return type Map
    */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/admin/completeUserList.html", method = RequestMethod.GET)
    public
    @ResponseBody
    Map userList(HttpServletRequest request, Map map) {
        List<User> userList = adminService.getAllUserList(fetchUserVoFromRequest(request));
        if (userList == null) {
            userList = new ArrayList<User>();
        }
        logger.debug("loading user list from db...." + userList.size());
        map.put("user", userList);
        return map;
    }

    /*
    * Method for preparing User object
    */
    private UserVo fetchUserVoFromRequest(HttpServletRequest request) {
        UserVo userVo = new UserVo();

        if (request.getParameter("un") != null) {
            userVo.setUserName((String) request.getParameter("un"));
        }
        if (request.getParameter("mn") != null) {
            userVo.setMobileNumber((String) request.getParameter("mn"));
        }
        if (request.getParameter("rl") != null) {
            userVo.setRole((String) request.getParameter("rl"));
        }
        if (request.getParameter("ia") != null) {
            userVo.setIsActive((String) request.getParameter("ia"));
        }

        return userVo;
    }

    /*
    * Method for viewing userList
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/admin/userList.html", method = RequestMethod.GET)
    public String newUserList(HttpServletRequest request, Model model) {
        logger.debug("User Controller Get");
        List<User> userList = new ArrayList<User>();
        List employeeList = new ArrayList();
        try {
            userList = adminService.getAllUserList();   // load userList by Hibernate
            employeeList = adminJdbcService.getEmployeeWithNonUserList();
            if(employeeList != null)
                logger.debug("SMN LOG: " +employeeList.size() );
        } catch ( Exception ex) {
            logger.debug("User Controller Exception :" + ex);
        }
        adminService.addNode("User Management", 50, request);  // adding Breadcrumb
        model.addAttribute("userListForTable", userList);
        model.addAttribute("userList", userList);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.getRoles());
        return "admin/userList";
    }

    /*
    * Method for viewing weighted Screen
    * @param HttpServletRequest request, Model model
    * @return type String
    */
    @RequestMapping(value = "/riskasst/weightedScreen.html", method = RequestMethod.GET)
    public String redirectWeightedScreen(HttpServletRequest request, Model model) {
        logger.debug(":: Weighted Screen Controller ::");
        request.getSession().removeAttribute("transactionTypeForSubHeader");  //for removing 'transaction type' in the sub header

        ProactiveBlockWeightRatio proactiveBlockWeightRatio = new ProactiveBlockWeightRatio();
        List<ProactiveBlockWeightRatio> list = adminService.getAllProactiveBlockWeightRatioList();
        try{
        if (list != null) {
            for (ProactiveBlockWeightRatio proactiveBlockWeightRatio1 : list) {
                if (ProactiveBlock.CPI_SCORE.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock()!=null ? proactiveBlockWeightRatio1.getProactiveBlock().trim():null))
                    proactiveBlockWeightRatio.setCpiScore(proactiveBlockWeightRatio1.getRatio());
                if (ProactiveBlock.REVENUES.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                    proactiveBlockWeightRatio.setRevenues(proactiveBlockWeightRatio1.getRatio());
                if (ProactiveBlock.SALES_MODEL_RELATIONSHIPS.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim():null))
                    proactiveBlockWeightRatio.setSalesModelRelationships(proactiveBlockWeightRatio1.getRatio());
                if (ProactiveBlock.NATURE_OF_BUSINESS_OPERATIONS.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock().trim()!= null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                    proactiveBlockWeightRatio.setNatureOfBusinessOperations(proactiveBlockWeightRatio1.getRatio());
                if (ProactiveBlock.GOVERNMENT_INTERACTION.getValue().equals(proactiveBlockWeightRatio1.getProactiveBlock() != null ? proactiveBlockWeightRatio1.getProactiveBlock().trim() : null))
                    proactiveBlockWeightRatio.setGovernmentInteraction(proactiveBlockWeightRatio1.getRatio());
            }
        } else {
            proactiveBlockWeightRatio.setCpiScore(0);
            proactiveBlockWeightRatio.setRevenues(0);
            proactiveBlockWeightRatio.setSalesModelRelationships(0);
            proactiveBlockWeightRatio.setNatureOfBusinessOperations(0);
            proactiveBlockWeightRatio.setGovernmentInteraction(0);

        }
        }catch (Exception ex){
            logger.debug("ERROR: Weighted screen :"+ex);
        }
        request.getSession().removeAttribute("cName");
        request.getSession().removeAttribute("fromDate");
        request.getSession().removeAttribute("toDate");
        request.getSession().removeAttribute("proactiveProject");
        request.getSession().removeAttribute("hiddenId");
        request.getSession().removeAttribute("tType");

        model.addAttribute("unlockSubmenu", "1");
        model.addAttribute("proactiveBlockWeightRatio", proactiveBlockWeightRatio);
        model.addAttribute("subHeader", "1");                          // for adding extra sub header
        adminService.addNode(Utils.getMessageBundlePropertyValue("landingPage.newRiskAssessment"), 2, request);  // adding Breadcrumb
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.newRiskAss"));
        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("subTabId.newRiskAssessment"));
        return "admin/weightedScreen";
    }

    /*
    * Method for viewing user add page
    * @param HttpServletRequest request, Map model
    * @return type String
    */
    @RequestMapping(value = "/admin/addUser.html", method = RequestMethod.GET)
    public String showRegistration(HttpServletRequest request, Map model) {
        User user = null;

        if(request.getParameter("id") != null && Utils.isValidNumber(request.getParameter("id")) && adminJdbcService.isUserIdExist(Long.parseLong(request.getParameter("id")))){
            user = (User) adminService.loadEntityById(Long.parseLong(request.getParameter("id")), "User");

        }else {   // otherwise initialize User as new object
            if(request.getParameter("id") != null)
                Utils.setErrorMessage(request,Utils.getMessageBundlePropertyValue("invalid.user.id"));

            user = new User();
        }


        model.put("roles", Role.getRoles());
        model.put("user", user);
        return "admin/user";
    }

    /*
    * Method to handle user form data after submitting from add user page
    * @param User user, HttpServletRequest request,BindingResult result, Map model
    * @return type String
    */
    @RequestMapping(value = "/admin/addUser.html", method = RequestMethod.POST)
    public @ResponseBody List<Map> processRegistration(HttpServletRequest request,@ModelAttribute("user") User user,  @RequestParam("id") long id,  BindingResult result, Map model) {

        logger.info("User add controller post start.");
        String userJasonObject="";
        String encryptedPassword;
        List<Map> list = new ArrayList<Map>();
        try {
            if(user.getId() == 0)  {
                userValidation.validate(user, result, adminService);          // set custom Validation by user
                encryptedPassword = Utils.encrypt(user.getGivenPassword());
                user.setPassword(encryptedPassword);
            } else {
                userValidation.validatePassword(user, result, adminService);
            }
            if (result.hasErrors()) {    // any error or invalid data from user form
                 for (Object object : result.getAllErrors()) {
                    if(object instanceof FieldError) {

                        FieldError fieldError = (FieldError) object;
                        String message = messageSource.getMessage(fieldError, null);
                        Map map= new HashMap();
                        map.put("fieldName",fieldError.getField());
                        map.put("message",message);
                        list.add(map);
                    }
                }
                    logger.error("User Post: Validation failed");
            } else {
                user.setUserType(PersonType.EMPLOYEE.getValue());
                adminService.saveOrUpdate(user);
            }
        } catch (Exception ex) {
            logger.error("Error while saving/updating user :: " + ex);
        }
        logger.info("User add controller post end.");
        return list;
    }

    /*
    * Method to handle user delete
    * @param HttpServletRequest request, Model model, Map map
    * @return type String
    */
    @RequestMapping(value = "/admin/userDelete.html", method = RequestMethod.GET)
    public String saveUpdateUser(HttpServletRequest request, Model model, Map map) {

        if(request.getParameter("id") != null && Utils.isValidNumber(request.getParameter("id"))
                && adminJdbcService.isUserIdExist(Long.parseLong(request.getParameter("id")))){
            adminService.deleteEntityById(Long.parseLong(request.getParameter("id")), "User");
//            Utils.setGreenMessage(request, Utils.getMessageBundlePropertyValue("user.delete.message")); // set deletion message
        }else {
            Utils.setErrorMessage(request,Utils.getMessageBundlePropertyValue("invalid.user.id"));
        }



        return "admin/userList";
    }

    /*
    * Method to show change password page
    * @param HttpServletRequest request, Model model, Map map
    * @return type String
    */
    @RequestMapping(value = "/*/changePassword.html", method = RequestMethod.GET)
    public String changePassword(HttpServletRequest request, Model model, Map map) {
       User user = (User)adminService.getAbstractBaseEntityByString(Entity.USER.getValue(),"userName",Utils.getLoggedUserName());
        model.addAttribute("user",user);
        adminService.addNode("Change Password",7,request);
        return "common/changePassword";
    }

    /*
    * Method to handle change password form data
    * @param User user, HttpServletRequest request, BindingResult result, Map model,SessionStatus status
    * @return type String
    */
    @RequestMapping(value = "/*/changePassword.html", method = RequestMethod.POST)
    public String changePasswordPost(User user, HttpServletRequest request,
                                      BindingResult result, Map model,SessionStatus status) {
        passwordChangeValidation.validate(user, result, adminService); // custom validation
        if (result.hasErrors()) {
            model.put("user", user);
            return "common/changePassword";
        } else {

            try {
                adminService.saveOrUpdate(user); // save or update User objects
                status.setComplete();
            } catch (Exception ex) {
                logger.debug("Error while saving/updating user :: " + ex);
            }
            Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("password.changed.message")); // set successful message
            return "common/changePassword";
        }
    }

    /*
    * Method for cancel buttons action
    * @param User user, HttpServletRequest request
    * @return type String
    */
    @RequestMapping(value = "/*/cancelButtonActionForAll.html", method = RequestMethod.GET)
    public String cancelButtonActionForAll(HttpServletRequest request) {
        String referrer = request.getHeader("referer");
        logger.debug("Refferer :"+referrer);
        return "redirect:"+referrer;
    }


    @RequestMapping(value = "/*/getUserJASON.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getControlList(HttpServletRequest request, Model model) {
        logger.debug("User JASON controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List userList = new ArrayList<Control>();

        try {

            totalItems= adminService.getEntitySize(Constants.USER);
            userList  = adminJdbcService.getPartialDataList( Utils.parseInteger(page), Utils.parseInteger(rp), qtype, query , sortname, sortorder, Constants.USER_TABLE);
            if(userList != null) {
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : userList) {
                    User user= new User();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    user.setUserName(map.get("user_name") != null ? map.get("user_name").toString() : "");
                    user.setRole(map.get("role") != null ? map.get("role").toString() : "");
                    if(map.get("is_active") != null && ("true".equals(map.get("is_active").toString().trim()))) {
                        user.setUserActiveCheckBoxHtml(Utils.getMessageBundlePropertyValue("user.selected.checkbox.html"));
                    } else {
                        user.setUserActiveCheckBoxHtml(Utils.getMessageBundlePropertyValue("user.not.selected.checkbox.html"));
                    }
                    user.setUserEditButtonHtml(Utils.getMessageBundlePropertyValue("user.edit.button.html"));
                    user.setUserDeleteButtonHtml(Utils.getMessageBundlePropertyValue("user.delete.button.html"));

                    user.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );
                    cell.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );

                    cell.setCell(user);
                    entry.add(cell);
                }
                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
            } else {
                logger.debug("No User Found");
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Get User List Exception : " + ex);
        }

        return jasonData;
    }


    @RequestMapping(value = "admin/getUser.html", method = RequestMethod.GET)
    public @ResponseBody User getControl(HttpServletRequest request,  @RequestParam("userId") long userId, Model model) {
        logger.info("Get user in ajax start.");

        User user = new User();
        String employeeName = "";
        try {
            if(userId > 0) {
                user = (User)adminService.loadEntityById(userId, Constants.USER);
                if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
                employeeName = adminJdbcService.getEmployeeNameById(user.getUserTypeId());
                user.setEmployeeName(employeeName);
                }

            } else {
                user = new User();
            }
            model.addAttribute("user", user);
        }catch (Exception ex) {
            logger.error("CERROR: Get Control List Exception : " + ex);
        }
        logger.info("Get user in ajax end.");
        return user;
    }
 @RequestMapping(value = "admin/getHoliday.html", method = RequestMethod.GET)
    public @ResponseBody Holiday getHoliday(HttpServletRequest request,  @RequestParam("holidayId") long holidayId, Model model) {
        logger.debug("Get Holiday for ajax");

        Holiday holiday= new Holiday();
        try {
            if(holidayId > 0) {
                holiday = (Holiday)adminService.loadEntityById(holidayId, Constants.HOLIDAY);

            } else {
                holiday = new Holiday();
            }

        }catch (Exception ex) {
            logger.debug("CERROR: Get Holiday List Exception : " + ex);
        }
        return holiday;
    }

    public int getRealTimeTransactionCountByStatus(String status,List realTimeTrxList){
        String stat = "";
        int count = 0;
        for(int i=0; i<realTimeTrxList.size(); i++){
            RealTimeProject realTimeProject = (RealTimeProject)realTimeTrxList.get(i);
//            stat = map.get("status") != null ? map.get("status").toString():"";
            stat = realTimeProject.getStatus() != null ? realTimeProject.getStatus() : "";
            if( status != null && stat.trim().equals(status.trim())){
            count++;
            }
        }
        return count;
    }

    /*
  * Method for viewing user add page
  * @param HttpServletRequest request, Map model
  * @return type String
  */
    @RequestMapping(value = "/*/addHoliday.html", method = RequestMethod.POST)
    public String saveHoliday(HttpServletRequest request,@ModelAttribute("holiday") Holiday holiday,  @RequestParam("id") long id,  BindingResult result, Map model) {

        if (result.hasErrors()) {    // any error or invalid data from holiday form
            model.put("holiday", holiday);
            logger.debug("Holiday Post: Validation failed");
            return "admin/holiday";
        } else {
            try {
                adminService.saveOrUpdate(holiday);
            } catch (Exception ex) {
                logger.debug("Error while saving/updating holiday :: " + ex);
            }
            // set successful message
//            Utils.setGreenMessage(request, id > 0 ? Utils.getMessageBundlePropertyValue("holiday.updated.message") : Utils.getMessageBundlePropertyValue("holiday.save.message"));
            return "admin/holiday";
        }
    }

    /*
* Method to handle user delete
* @param HttpServletRequest request, Model model, Map map
* @return type String
*/
    @RequestMapping(value = "/*/holidayDelete.html", method = RequestMethod.GET)
    public String deleteHoliday(HttpServletRequest request, Model model, Map map) {
        logger.debug("holiday delete controller");
        if(request.getParameter("id") != null && Utils.isValidNumber(request.getParameter("id"))
                && adminJdbcService.isHolidayIdExist(Long.parseLong(request.getParameter("id")))){
            adminService.deleteEntityById(Long.parseLong(request.getParameter("id")), Constants.HOLIDAY);
//            Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("holiday.delete.message")); // set deletion message
            logger.debug("holiday deleted...");
        }else {
            Utils.setErrorMessage(request,Utils.getMessageBundlePropertyValue("invalid.holiday.id"));
        }
        return "admin/holiday";
//        return "redirect:/admin/holidays.html?regionId=162";
    }

/*
* Method to handle registration
* @param HttpServletRequest request, Model model, Map map
* @return type String
*/
    @RequestMapping(value = "registration.html", method = RequestMethod.GET)
    public String registrationView(HttpServletRequest request, Model model, Map map) {
        logger.debug("Registration Save controller");
        try{
        Map<String, String> pTypes = new LinkedHashMap<String, String>();
        for (PersonType personType : PersonType.getPersonTypes()) {
            pTypes.put(personType.getValue(), personType.getValue());
        }
        model.addAttribute("user", new User());
        model.addAttribute("personTypes", pTypes);
        }catch(Exception ex){
            logger.debug("CERROR: Get Registration Exception : " + ex);
        }
        return "common/registration";
    }

    /*
* Method to handle user form data after submitting from add user page
* @param User user, HttpServletRequest request,BindingResult result, Map model
* @return type String
*/
    @RequestMapping(value = "registration.html", method = RequestMethod.POST)
    public String saveRegistration(HttpServletRequest request,@ModelAttribute("user") User user, BindingResult result, Map model) {

        logger.debug("Registration Controller Post");
        registrationValidation.validate(user, result, adminService, adminJdbcService);          // set custom Validation by user
        if (result.hasErrors()) {    // any error or invalid data from user form
            Map<String, String> pTypes = new LinkedHashMap<String, String>();
            for (PersonType personType : PersonType.getPersonTypes()) {
                pTypes.put(personType.getValue(), personType.getValue());
            }
            model.put("user", user);
            model.put("personTypes", pTypes);
            logger.debug("User Post: Validation failed");
            return "common/registration";
        } else {
            try {
                String encryptedPassword = Utils.encrypt(user.getPassword());
                user.setPassword(encryptedPassword);
                long personTypeId = adminJdbcService.getPersonTypeIdByEmail(user);
                user.setUserTypeId(personTypeId);
                user.setRole(Role.EMPLOYEE.getValue());
                user.setActive(false);
                adminService.saveOrUpdate(user);
                Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("registation.form.save.message"));
            } catch (Exception ex) {
                logger.debug("Error while saving/updating in Registration Controller:: " + ex);
            }
            return "redirect:registration.html";
        }
    }

    /*
* Method to handle registration
* @param HttpServletRequest request, Model model, Map map
* @return type String
*/
    @RequestMapping(value = "/*/policyView.html", method = RequestMethod.GET)
    public String showPolicy(HttpServletRequest request, @ModelAttribute("policyId")long policyId, Model model, Map map) {
        logger.debug("Show Policy controller");
        logger.debug("policyId="+policyId);
        model.addAttribute("policyId",policyId);
        return "common/showPolicy";
    }

/*
* Method to handle registration
* @param HttpServletRequest request, Model model, Map map
* @return type String
*/
    @RequestMapping(value = "/policy/underStandPolicy.html", method = RequestMethod.GET)
    public void underStandPolicy(HttpServletRequest request, @ModelAttribute("policyId") long policyId,Model model, Map map) {
        logger.debug("Understand Policy controller");
    try{
       User user = (User)adminService.getAbstractBaseEntityByString(Entity.USER.getValue(),"userName",Utils.getLoggedUserName());
       logger.debug("SML log: policeId="+policyId+" logged userName="+user.getUserName());
//       PolicyAndProcedure policyAndProcedure = new PolicyAndProcedure();
        PolicyAndProcedure policyAndProcedure = adminService.getPolicyAndProcedure(user,policyId);
        if(policyAndProcedure != null && policyAndProcedure.isConfirmed() == false && policyAndProcedure.getConfirmDate() == null){
            Policy policy = new Policy();
            policy.setId(policyId);
            policyAndProcedure.setPolicy(policy);

            if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
                EmployeeMasterLedger employeeMasterLedger = new EmployeeMasterLedger();
                employeeMasterLedger.setId(user.getUserTypeId());
                policyAndProcedure.setEmployeeMasterLedger(employeeMasterLedger);
            }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
                VendorMasterLedger vendorMasterLedger = new VendorMasterLedger();
                vendorMasterLedger.setId(user.getUserTypeId());
                policyAndProcedure.setVendorMasterLedger(vendorMasterLedger);
            }
            policyAndProcedure.setConfirmDate(Utils.getTodaysDate());
            policyAndProcedure.setConfirmed(true);
            adminService.update(policyAndProcedure);
            Utils.setGreenMessage(request,Utils.getMessageBundlePropertyValue("policy.confirmation.message"));
            logger.debug("Policy Confirmed Succesfully.");
        }else{
            Utils.setErrorMessage(request,Utils.getMessageBundlePropertyValue("policy.confirmation..error.message"));
            logger.debug("Policy Not Confirmed.");

        }

    } catch (Exception ex) {
        logger.debug("Error while Understand Policy Controller:: " + ex);
    }
//        return "redirect:./policyList.html";
    }
}