package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.dsinv.abac.util.Utils.getMessageBundlePropertyValue;

/**
 * Created by amjad on 4/2/14.
 */

@Controller
@SessionAttributes({"questionSet", "employeeTraining","trainingObj" })
public class TrainingAndCertificationController {

    private static Logger logger = Logger.getLogger(TrainingAndCertificationController.class);
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;


    @RequestMapping(value = "/training/addAQuestion.html", method = RequestMethod.POST)
    public String addAQuestion(HttpServletRequest request, @RequestParam("trainingId") long trainingId,  Model model, Map map) {
        logger.debug("Training Question Add Start.");
        String query = request.getParameter("query");
        String jsonOption = request.getParameter("jsonOpston");
        boolean isCorrect;
        String optionText;
        TrainingQuestion trainingQuestion = null;
        Training training = null;
        TrainingQuestionAnswer trainingQuestionAnswer = null;
        List<TrainingQuestionAnswer> trainingQuestionAnswerList = new ArrayList<TrainingQuestionAnswer>();
        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse(jsonOption);
            JSONArray jsonArray = (JSONArray) object;
            JSONObject realTitle= null;
            trainingQuestion = new TrainingQuestion();

            for(int i = 0 ; i < jsonArray.size(); i++){
                realTitle = (JSONObject)((JSONArray) object).get(i);
                optionText = (String) realTitle.get("optionText");
                isCorrect = (Boolean) realTitle.get("isCorrect");
                trainingQuestionAnswer = new TrainingQuestionAnswer();
                trainingQuestionAnswer.setOptionOrder(i+1);
                trainingQuestionAnswer.setOptionText(optionText);
                trainingQuestionAnswer.setCorrect(isCorrect);
                trainingQuestionAnswerList.add(trainingQuestionAnswer);
            }
            training = adminService.getTrainingById(trainingId);
            trainingQuestion.setText(query);
            trainingQuestion.setTraining(training);
            adminService.saveTrainingQuestion(trainingQuestion, trainingQuestionAnswerList);
            logger.debug("Training Question Add Successfully.");

        } catch (Exception ex) {
            logger.debug("CERROR:: Add Training Question exception: "+ ex);
            logger.debug("CERROR:: Add Training Question exception description: "+ ex.getMessage());
        }
        model.addAttribute("mainTabId", Utils.getMessageBundlePropertyValue("main.training.and.certification.id"));
        logger.debug("Training Question Add End.");
        return "admin/question";
    }

    @RequestMapping(value = "/training/trainingQuestion.html", method = RequestMethod.GET)
    public String getQuestions(HttpServletRequest request, @RequestParam("trainingId") long trainingId,  Model model, Map map) {
        logger.debug("Training Questions view start.");
        List<TrainingQuestion> questions = null;
        Training training = null;
        try {
             questions = adminService.getTrainingQuestion(trainingId);
             if(questions != null) {
                 logger.debug("Question List Size: " + questions.size());
             }else {
                 questions = new ArrayList<TrainingQuestion>();
             }
            training = adminService.getTrainingById(trainingId);
        } catch (Exception ex) {
            logger.debug("CERROR:: Training questions exception: " + ex);
            logger.debug("CERROR:: Training questions exception descriptions: " + ex.getMessage());
        }

        model.addAttribute("trainingId", trainingId);
        model.addAttribute("trainingObj", training);
        model.addAttribute("question", new TrainingQuestion());
        model.addAttribute("questions", questions);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("main.training.and.certification.id"));
        logger.debug("Training Questions view end.");
        return "admin/trainingTest";
    }

    @RequestMapping(value = "/training/addQuestion.html", method = RequestMethod.POST)
    public String saveQuestion(HttpServletRequest request, @RequestParam("trainingId") long trainingId,  Model model, Map map) {
        logger.debug("Save Question view start.");
        List<TrainingQuestion> questions = null;
        try {
             questions = adminService.getTrainingQuestion(trainingId);
             if(questions != null) {
                 logger.debug("Question List Size: " + questions);
             }

        } catch (Exception ex) {
            logger.debug("CERROR:: Training questions exception: " + ex);
            logger.debug("CERROR:: Training questions exception descriptions: " + ex.getMessage());
        }
        model.addAttribute("question", new TrainingQuestion());
        model.addAttribute("questions", questions);
        logger.debug("Save Question view end.");
        return "admin/questionSet";
    }


    @RequestMapping(value = "/training/trainingList.html", method = RequestMethod.GET)
    public String getTrainingList(HttpServletRequest request,  Model model, Map map) {
        logger.debug("Training list controller start");
        List<Training> trainingList = new ArrayList<Training>();
        try {

            User user = adminService.getUserByUserName(Utils.getLoggedUserName());
            ObjectMapper mapper = new ObjectMapper();
            String userJasonObject = mapper.writeValueAsString(user);
            model.addAttribute("user", userJasonObject);

        } catch (Exception ex)  {
            logger.debug("CERROR: Training list controller exception :" + ex);
        }

        model.addAttribute("maxFileUploadSize", Utils.getApplicationPropertyValue("file.mazFileUploadSize"));
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("main.training.and.certification.id"));
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
        model.addAttribute("trainingType", TrainingType.getTrainingTypes());
        model.addAttribute("training", new Training());
        logger.debug("Training list controller end");
        adminService.addNode(getMessageBundlePropertyValue("training.and.certification"), 2, request);
        return "admin/trainingList";
    }


    @RequestMapping(value = "/training/getJASONforTrainingList.html", method = RequestMethod.POST)
    public  @ResponseBody
    JasonBean getTrainingList(HttpServletRequest request, Model model) {
        logger.debug("Get Training list controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "documentName";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        JasonBean jasonData = new JasonBean();

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        String fromSearch = request.getParameter("fromSearch") != null ? request.getParameter("fromSearch") : "";

        List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
        sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;
        qtype = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,qtype): "";
        List dbColumnHeaderList = new ArrayList();
        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List trainingList = new ArrayList<Control>();
        Training training = new Training();
        User user = adminService.getUserByUserName(Utils.getLoggedUserName());
        try {

            String  documentName = request.getParameter("documentName") != null ? request.getParameter("documentName") : "";
            String  author = request.getParameter("author") != null ? request.getParameter("author") : "";
            String entryTime = request.getParameter("entryTime") != null ? request.getParameter("entryTime") :"";
            String trainingType = request.getParameter("trainingType") != null ? request.getParameter("trainingType") : "";

            logger.debug("documentName="+ documentName +" author="+author+" entryTime="+ entryTime+" trainingType="+trainingType);
            training.setDocumentName(documentName);
            training.setAuthor(author);
            training.setTrainingType(trainingType);
            training.setEntryDate(entryTime);


            totalItems= adminJdbcService.getTotalTrainingCount(training);
            logger.debug("total items:"+totalItems);
            trainingList  = adminJdbcService.getPartialTrainingList(training,Utils.parseInteger(page), Utils.parseInteger(rp),qtype, query, sortname, sortorder,user);
            if("max".equals(rp)){

            }else{
                if(trainingList != null) {
                    logger.debug("training list size:"+trainingList.size());
                    jasonData.setPage(Utils.parseInteger(page));
                    for(Object obj : trainingList) {
                        Training trainingNew = new Training();
                        Cell cell = new Cell();
                        Map map = (Map) obj;
                        Date entry_time =  map.get("entry_time") != null ? (Date)map.get("entry_time") : new Date();
                        trainingNew.setDocumentName(map.get("document_name") != null ? map.get("document_name").toString() : "");
                        trainingNew.setFileIconLocation("<img class='fileIcon' src='" + request.getContextPath() + "/" + (map.get("file_icon_location") != null ? map.get("file_icon_location").toString() : "") + "'> ");
                        trainingNew.setAuthor(map.get("author") != null ? map.get("author").toString() : "");
                        trainingNew.setTrainingType(map.get("training_type") != null ? map.get("training_type").toString() : "");
                        trainingNew.setTestStatus(map.get("training_status") != null ? map.get("training_status").toString() : "");
                        trainingNew.setEntryDate(Utils.dateToStrWithFormat(entry_time, Constants.MONTH_DAY_YEAR));
                        trainingNew.setPolicyDeleteHtmlButton(Utils.getMessageBundlePropertyValue("training.delete.button.html"));
                        trainingNew.setQuestionSetHtmlButton(Utils.getMessageBundlePropertyValue("training.question.set.button.html"));
                        trainingNew.setDownloadCertificateHtmlButton(Utils.getMessageBundlePropertyValue("training.certificate.button.html"));

                        if(Constants.TRAINING_RETAKE.equals(trainingNew.getTestStatus())){
                            trainingNew.setTestStartHtmlButton(Utils.getMessageBundlePropertyValue("training.retake.button.html"));
                        }else if(Constants.TRAINING_CERTIFICATE.equals(trainingNew.getTestStatus())){
                            trainingNew.setTestStartHtmlButton(Utils.getMessageBundlePropertyValue("training.certificate.button.html"));
                        }else if(Constants.TRAINING_START.equals(trainingNew.getTestStatus())){
                            trainingNew.setTestStartHtmlButton(Utils.getMessageBundlePropertyValue("training.start.button.html"));
                        }

                        trainingNew.setId(map.get("id") != null ? ((Number) map.get("id")).intValue() : 0);
                        cell.setId(map.get("id") != null ? ((Number)map.get("id")).intValue() : 0 );

                        cell.setCell(trainingNew);
                        entry.add(cell);
                    }
                    Map mapForHeader = new HashMap();
                    mapForHeader.put("entryDate","entry_time"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("documentName","document_name");
                    mapForHeader.put("author","author");
                    mapForHeader.put("trainingType","training_type");
                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);

                    jasonData.setRows(entry);
                    jasonData.setTotal(totalItems);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);
                } else {
                    logger.debug("No Training Found");
                }
                if("1".equals(fromSearch)&& "1".equals(page)){
                    trainingList  = adminJdbcService.getPartialTrainingList(training, 1, totalItems, qtype, query, sortname, sortorder,user);
                    TransactionSearchController.setTotalListSizeAndListInSession(totalItems,trainingList,request);
                }
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Exception : " + ex);
        }

        return jasonData;
    }

    @RequestMapping(value = "/training/deleteTraining.html", method = RequestMethod.GET)
    public String deleteTraining(HttpServletRequest request, @RequestParam("trainingId") long trainingId) {
        logger.debug("Training Delete Controller Start.");

        try {
            if (trainingId > 0) {
                Training training = adminService.getTrainingById(trainingId);
                if(training.getFileLocation() != null){
                    File file = new File(training.getFileLocation());
                    if (file.exists()) {
                       file.delete();
                        logger.debug("Training deleted successfully");
                    } else {
                        logger.debug("Negative Training id Not supported");
                    }
                }
                adminService.deleteTraining(trainingId);

            }
        } catch (Exception ex) {
            logger.debug("CERROR: Training Delete Controller exception :" + ex);
        }
        logger.debug("AMLOG:: Training is completed");
        logger.debug("Training Delete Controller End.");
        return "redirect:./trainingList.html";
    }
    @RequestMapping(value = "/training/startTest.html", method = RequestMethod.GET)
    public String startTest(HttpServletRequest request, @RequestParam("trainingId") long trainingId, Model model) {
        logger.debug("Training Test Controller Start.");
        List<TrainingQuestion> questionSet = null;
        EmployeeTraining employeeTraining = null;
        EmployeeMasterLedger employeeMasterLedger = null;
        Training training = null;
        User user = null;
        try {
            if (trainingId > 0) {
                user = adminService.getUserByUserName(Utils.getLoggedUserName());
                if(user != null) {      // Save user record on training.
                    employeeTraining = adminService.getEmployeeTraining(user.getUserTypeId(), trainingId);
                    if(employeeTraining != null) {
                        logger.debug("AMLOG:: Employee Training Id: " + employeeTraining.getId());
                      if(Constants.TRAINING_CERTIFICATE.equals(employeeTraining.getStatus())){
                        return "redirect:./trainingList.html";
                        }
                    } else {
                        employeeTraining = new EmployeeTraining();
                    }
                    employeeMasterLedger = new EmployeeMasterLedger();
                    employeeMasterLedger.setId(user.getUserTypeId());
                    training = adminService.getTrainingById(trainingId);
                    training.setId(trainingId);
                    employeeTraining.setTraining(training);
                    employeeTraining.setStatus(Constants.TRAINING_RETAKE);
                    employeeTraining.setEmployeeMasterLedger(employeeMasterLedger);

                } else {                // User is not valid.
                    return "redirect:./trainingList.html";
                }

                questionSet  = adminService.getTrainingQuestionSet(training);
                if(questionSet != null && questionSet.size() > 0) {
                    logger.debug("Training Question Size: " + questionSet.size());
                    if(employeeTraining != null) {
                        adminService.saveOrUpdate(employeeTraining);
                    }
                } else {
                    questionSet = new ArrayList<TrainingQuestion>();
                }
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Training-Start Controller exception :" + ex);
        }
        model.addAttribute("trainingId", trainingId);
        model.addAttribute("questionSet", questionSet);
        model.addAttribute("employeeTraining", employeeTraining);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("main.training.and.certification.id"));
        adminService.addNode(Utils.getMessageBundlePropertyValue("breadcrumb.training.FCPATrainingTest"), 3, request);  // adding Breadcrumb
        logger.debug("Training Test Controller End.");
        return "common/trainingQuestion";
    }

     @RequestMapping(value = "/training/startTest.html", method = RequestMethod.POST)
    public String getTestResult(HttpServletRequest request, @RequestParam("trainingId") long trainingId, @ModelAttribute("questionSet") List<TrainingQuestion> questionSet, @ModelAttribute("employeeTraining") EmployeeTraining employeeTraining, Model model) {
        logger.debug("Test Result Controller Start.");
         boolean pass = false;
         long selectedOption = 0 ;
         int marks=0;
         Training training = null;
        try {
            for(TrainingQuestion question : questionSet) {
                String selected = request.getParameter("myRadio_"+question.getId());
                selectedOption = !Utils.isEmpty(selected) ? Long.parseLong(selected) : 0;
                logger.debug("AMLOG:: Selected Option: " + selectedOption + " == Correct Option: " + question.getCorrectOption());
                if(question.getCorrectOption() == selectedOption) {
                    marks++;
                }
            }
            logger.debug("Total Correct Answer: " + marks);
            training = adminService.getTrainingById(trainingId);
            if(marks >= training.getPassNumber()) {
                if(employeeTraining != null) {
                    employeeTraining.setObtainedMarks(marks);
                    employeeTraining.setStatus(Constants.TRAINING_CERTIFICATE);
                    adminService.saveOrUpdate(employeeTraining);
                }

                pass = true;
                logger.debug("AMLOG:: download certificate");
            } else {
                logger.debug("AMLOG:: need retake");
            }
        } catch (Exception ex) {
            logger.debug("CERROR: Test Result Controller exception :" + ex);
            logger.debug("CERROR: Test Result Controller exception description :" + ex);
        }
        model.addAttribute("trainingId", trainingId);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("main.training.and.certification.id"));
        logger.debug("Test Result Controller End.");
        if(pass) {
            return "redirect:./trainingCertificate.html";
        } else {
            return "redirect:./trainingRetake.html";
        }
    }

    @RequestMapping(value = "/training/trainingRetake.html", method = RequestMethod.GET)
    public String retakeTraining(HttpServletRequest request, @RequestParam("trainingId") long trainingId, Model model) {
        logger.debug("Training Retake Controller Start.");

        logger.debug("Training Retake Controller End.");
        return "common/trainingRetake";
    }

    @RequestMapping(value = "/training/trainingCertificate.html", method = RequestMethod.GET)
    public String getTrainingCertificate(HttpServletRequest request, @RequestParam("trainingId") long trainingId, Model model) {
        logger.debug("Training Certificate Controller Start.");
        model.addAttribute("trainingId", trainingId);
        logger.debug("Training Certificate Controller End.");
        return "common/trainingCertificate";
    }

    @RequestMapping(value = "/training/ajaxSveTrainingDetails.html", method = RequestMethod.POST)
    public String saveTrainingDetails(HttpServletRequest request, @RequestParam("trainingId") long trainingId, @ModelAttribute("trainingObj")Training training, Model model) {
        logger.debug("Training Details Save Start.");
        String numberOfQuestion= request.getParameter("numberOfQuestion")  ;
        String passNumber= request.getParameter("passNumber")  ;
        try {
            if(training == null) {
                training = adminService.getTrainingById(trainingId);
            } else {
                logger.debug("AMLOG:: training Object found In session.");
            }
            training.setNoOfQuestion(!Utils.isNullOrEmpty(numberOfQuestion) ? Integer.parseInt(numberOfQuestion): 0);
            training.setPassNumber(!Utils.isNullOrEmpty(passNumber) ? Integer.parseInt(passNumber): 0);
            adminService.saveOrUpdate(training);
            logger.debug("Training updated successfully.");
        } catch (Exception ex) {
            logger.debug("CERROR:: Training Details Save Exception: " + ex);
            logger.debug("CERROR:: Training Details Save Exception Description: " + ex.getMessage());
        }
        logger.debug("Training Details Save End.");
        return "common/trainingCertificate";
    }

        @ModelAttribute("user")
    public String getUserJson(HttpServletRequest request,  Model model) {
        User user = adminService.getUserByUserName(Utils.getLoggedUserName());
        ObjectMapper mapper = new ObjectMapper();
        String userJasonObject = null;
        try {
            userJasonObject = mapper.writeValueAsString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userJasonObject;
    }

    @RequestMapping(value = "/training/deleteQuestionAjax.html", method = RequestMethod.POST)
    public void deleteQuestion(HttpServletRequest request, @RequestParam("trainingId") long trainingId, @RequestParam("questionId") long questionId, Model model) {
        logger.debug("Question Delete Start.");
        try{
            logger.debug("Training id: " + trainingId);
            logger.debug("Delete Question: " + questionId);
            adminService.deleteTrainingQuestion(trainingId, questionId);
        } catch (Exception ex) {
            logger.debug("CERROR:: Question Delete Exception: " + ex);
            logger.debug("CERROR:: Question Delete Exception Description: " + ex.getMessage());
        }

        logger.debug("Question Delete End.");

    }

        @RequestMapping(value = "/training/editQuestionAjax.html", method = RequestMethod.POST)
    public void updateQuestion(HttpServletRequest request, @RequestParam("trainingId") long trainingId, @RequestParam("questionId") long questionId, @ModelAttribute("trainingObj")Training training, Model model) {
        logger.debug("Question Update Start.");
            String query = request.getParameter("query");
            String jsonOption = request.getParameter("opstonsJSON");
            String optionText;
            String optionId;
            List<String > questionAnswerSQL = new ArrayList<String>();
            TrainingQuestion trainingQuestion = null;
            TrainingQuestionAnswer trainingQuestionAnswer = null;
            List<TrainingQuestionAnswer> trainingQuestionAnswerList =  new ArrayList<TrainingQuestionAnswer>();;
        try{
            JSONParser parser = new JSONParser();
            Object object = parser.parse(jsonOption);
            JSONArray jsonArray = (JSONArray) object;
            JSONObject realTitle= null;
            trainingQuestion = (TrainingQuestion)adminService.loadEntityById(questionId, Constants.TRAINING_QUESTION);

            for(int i = 0 ; i < jsonArray.size(); i++){
                realTitle = (JSONObject)((JSONArray) object).get(i);
                optionText = (String) realTitle.get("optionText");
                optionId = (String) realTitle.get("optionId");

                questionAnswerSQL.add(getAnsOptUpdateSql(optionText, Long.parseLong(optionId)));
            }
            questionAnswerSQL.add(getQuestionUpdateSql(query, trainingId));
            adminJdbcService.updateTrainingOptionAnswer(questionAnswerSQL);

         /*   if(training == null) {
                training = adminService.getTrainingById(trainingId);
            } else {
                logger.debug("Training get from session.");
            }
         */

        } catch (Exception ex) {
            logger.debug("CERROR:: Question Update Exception: " + ex);
            logger.debug("CERROR:: Question Update Exception Description: " + ex.getMessage());
        }

        logger.debug("Question Update End.");
    }

    @RequestMapping(value = "/training/downloadCertificate.html", method = RequestMethod.GET)
    public String downloadCertificate(HttpServletRequest request, @RequestParam("trainingId") long trainingId, Model model) {
        logger.debug("Download Certificate Start.");
        EmployeeTraining employeeTraining = null;
        EmployeeMasterLedger employee= null;
        Training training = null;
        User user = null;
        String employeeName = "";
        String trainingTitle = "";
        String certificated = "";
        try{
           logger.debug("Training id: " + trainingId);
            user = adminService.getUserByUserName(Utils.getLoggedUserName());
            employeeTraining = adminService.getEmployeeTraining(user.getUserTypeId(), trainingId);
            if(employeeTraining != null) {
                logger.debug("AMLOG:: Employee Training Id: " + employeeTraining.getId());
                if(employeeTraining.getEmployeeMasterLedger() != null) {
                   employee = adminService.loadEmployee(employeeTraining.getEmployeeMasterLedger().getId());
                } else {
                    employee = new EmployeeMasterLedger();
                }
            } else {
                employeeTraining = new EmployeeTraining();
            }
            training = adminService.getTrainingById(trainingId);
            trainingTitle = training.getDocumentName();
            employeeName = employee.getFirstName() + " " + employee.getLastName();
            certificated  = Utils.dateToStrWithFormat(employeeTraining.getModified(), Constants.MONTH_DAY_YEAR);

         } catch (Exception ex) {
            logger.debug("CERROR:: Download Certificate  Exception: " + ex);
            logger.debug("CERROR:: Download Certificate Exception Description: " + ex.getMessage());
        }
        logger.debug("Download Certificate End.");
        return "redirect:./printCertificate.html?name=" + employeeName+"&grantedDate="+certificated;
    }
    public String getAnsOptUpdateSql(String optionText, long id) {
         return  " UPDATE training_question_answer SET option_text = '" + optionText + "' WHERE id =  " + id + ";";
    }
    public String getQuestionUpdateSql(String optionText, long trainingId) {
         return  " UPDATE training_question SET text = '" + optionText + "' WHERE training_id =  " + trainingId + ";";
    }

/*
* Method to print Transaction Detail
* @param HttpServletRequest request, HttpServletResponse response, Model model, Locale locale
* @return type String
*/
    @RequestMapping(value = "/training/printCertificate.html", method = RequestMethod.GET)
    public String printCertificate(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        String applicantName = request.getParameter("name") != null ? request.getParameter("name") : "Applicant Name";
        String courseTitle = request.getParameter("courseTitle") != null ? request.getParameter("courseTitle") : "";
        String grantedDate = request.getParameter("grantedDate") != null ? request.getParameter("grantedDate") : "Granted :";
        String companyName = request.getParameter("companyName") != null ? request.getParameter("companyName") : "XYX Co.";
        String contextPath = request.getContextPath();
        try {
            parameters.put("companyName",companyName);
            parameters.put("declaration","Certificate of Completion");
            parameters.put("isHereby","is hereby granted to");
            parameters.put("applicantName",applicantName);
            parameters.put("toCertifyTxt","to certify the completion of the");
            parameters.put("courseName","FCPA Training Course");
            parameters.put("grantedDate","Granted : "+grantedDate);
            parameters.put("courseTitle","Compliance Officer,");
            parameters.put("nameTitle","XYZ Co.");
            parameters.put("lastTitle","name, title");
            parameters.put("backgroundImage",TrainingAndCertificationController.class.getResource("/../../resources/images/trainingBackground.png").getPath());
            InputStream pdfForm = this.getClass().getClassLoader().getResourceAsStream("/jasper/trainingCertificate.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(pdfForm, parameters, new JREmptyDataSource());
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=trainingCertificate.pdf");
            response.setContentLength(pdfData.length);
            response.getOutputStream().write(pdfData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception ex) {
            logger.debug("CERROR:: Failed to Print certificate. " + ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:./trainingList.html";
    }

}
