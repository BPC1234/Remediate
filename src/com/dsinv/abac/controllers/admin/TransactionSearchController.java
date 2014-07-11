package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.Entity;
import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.GlobalSearchValidation;
import com.dsinv.abac.validation.RealTimeMonitoringIntervalSetUpValidation;
import com.dsinv.abac.validation.RealTimeProjectValidation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dsinv.abac.util.Utils.getMessageBundlePropertyValue;

@Controller
@SessionAttributes({"GlobalTransactionSearch"})
public class TransactionSearchController {

    private static Logger logger = Logger.getLogger(TransactionSearchController.class);
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private GlobalSearchValidation globalSearchValidation;


    /*
    * Method to format date, numbers etc in jsp page
    * @param ServletRequestDataBinder binder
    * @return type void
    */
   /* @InitBinder("GlobalTransactionSearch")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Float.class,new MyCustomNumberEditor(Float.class, true));
        //binder.setValidator(globalSearchValidation);
    }*/

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        //binder.registerCustomEditor(Long.class,new MyCustomNumberEditor(Long.class, true));
        //binder.registerCustomEditor(Double.class,new MyCustomNumberEditor(Double.class, true));
    }

    /**
     * Show Transaction Search Page
     * @param request
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/realtime/transactionSearch.html", method = RequestMethod.GET)
    public String transactionSearchGet(HttpServletRequest request, Model model, Map map) {
        logger.debug(" :: transaction Search Controller GET :: ");
        GlobalTransactionSearch globalTransactionSearch = new GlobalTransactionSearch();
        logger.debug("="+TransactionType.getTransactionTypes());
        globalTransactionSearch.setTransactionDate(Utils.getTodaysDate());
        model.addAttribute("transactionTypeList",TransactionType.getTransactionTypes());
        model.addAttribute("GlobalTransactionSearch",globalTransactionSearch);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        adminService.addNode(getMessageBundlePropertyValue("mainTab.transactionSearch.title"), 2, request);
//        model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("search.button.title").toLowerCase());

        return "common/transactionSearch";
    }

    /**
     * Search Transaction
     * @param request
     * @param model
     * @param globalTransactionSearch
     * @return
     */
    @RequestMapping(value = "/realtime/transactionSearch.html", method = RequestMethod.POST)
    public String transactionSearchPost(HttpServletRequest request,
        @ModelAttribute("GlobalTransactionSearch") GlobalTransactionSearch globalTransactionSearch,BindingResult result,
        Model model) {
        logger.debug(" :: transaction Search Controller POSt::");
        globalSearchValidation.validate(globalTransactionSearch, result);
        if (result.hasErrors()) { // validation failed
            logger.debug("transaction validation failed");
            return "common/transactionSearch";
        }

        String param = request.getParameter("param");
        List TrxList = adminJdbcService.getTransactionList(globalTransactionSearch);
        globalTransactionSearch.setTrxList(TrxList);
        model.addAttribute("transactionTypeList",TransactionType.getTransactionTypes());
        model.addAttribute("GlobalTransactionSearch",globalTransactionSearch);
        model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
        model.addAttribute("mainTabId",Utils.getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        /*model.addAttribute("subTabId",Utils.getMessageBundlePropertyValue("search.button.title").toLowerCase());*/
        return "common/transactionSearch";
    }
    public class MyCustomNumberEditor extends CustomNumberEditor {
        public MyCustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {
            super(numberClass, numberFormat, allowEmpty);
        }

        public MyCustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
            super(numberClass, allowEmpty);
        }
    }

    @RequestMapping(value = "/*/getJASONforTrxSearchDataList.html", method = RequestMethod.POST)
    public  @ResponseBody  JasonBean getJASONforProactiveWorkflowList(HttpServletRequest request, Model model) {
        logger.debug("Get Transaction Search list controller");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "assignment_size";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";
        String fromSearch = request.getParameter("fromSearch") != null ? request.getParameter("fromSearch") : "";

        List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
        sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;
//        logger.debug("SMN: sortname="+sortname);

        List dbColumnHeaderList = new ArrayList();
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List trxSearchList  = new ArrayList<Control>();
        GlobalTransactionSearch globalTransactionSearch = new GlobalTransactionSearch();
        try {
            Long trxId = !Utils.isEmpty(request.getParameter("trxId")) ? Long.parseLong(request.getParameter("trxId")) : null;
            String  nameOfThirdParty = request.getParameter("nameOfThirdParty") != null ? request.getParameter("nameOfThirdParty") : "";
            Double amount = !Utils.isEmpty(request.getParameter("amount")) ? Double.parseDouble(request.getParameter("amount")) : null;
            String transactionDate = request.getParameter("transactionDate") != null ? request.getParameter("transactionDate") :"";
            String transactionType = request.getParameter("transactionType") != null ? request.getParameter("transactionType") : "";
            String freeText = request.getParameter("freeText") != null ? request.getParameter("freeText") : "";
            String approverName = request.getParameter("approver") != null ? request.getParameter("approver") : "";
            logger.debug("transactionId="+ trxId +" nameOfThirdParty="+nameOfThirdParty+" amount="+ amount+" transactionDate="
                    +transactionDate+" transactionType="+transactionType+" freeText="+freeText+" Approver="+approverName);
           // Date trxDate = !Utils.isEmpty(transactionDate) ? Utils.strToDate(transactionDate): null;
            Date trxDate = !Utils.isEmpty(transactionDate) ? Utils.getDateFromString(Constants.MONTH_DAY_YEAR,transactionDate): null;

            globalTransactionSearch.setTransactionId(trxId);
            globalTransactionSearch.setNameOfThirdParty(nameOfThirdParty);
            globalTransactionSearch.setAmount(amount);
            globalTransactionSearch.setTransactionDate(trxDate);
            globalTransactionSearch.setTransactionType(transactionType);
            globalTransactionSearch.setApprover(approverName);
            globalTransactionSearch.setFreeText(freeText);

            totalItems = adminJdbcService.getTotalTransactionCount(globalTransactionSearch);
            logger.debug("TOTAL count---"+totalItems+" page="+page+" rp="+rp);

            trxSearchList  = adminJdbcService.getPartialTransactionList(globalTransactionSearch, Utils.parseInteger(page),"max".equals(rp) ? totalItems : Utils.parseInteger(rp),
                    qtype,query,sortname,sortorder);


            /*trxSearchList  = adminJdbcService.getPartialTransactionList(globalTransactionSearch, Utils.parseInteger(page),"max".equals(rp) ? totalItems : Utils.parseInteger(rp),
                    qtype,query,sortname,sortorder);
*/
            if("max".equals(rp)){
               // TransactionSearchController.setTotalListSizeAndListInSession(totalItems,trxSearchList,request);
               // jasonData.setTotal(totalItems);
            }else{
            if(trxSearchList != null) {
                logger.debug("trxSearchList size="+trxSearchList.size());
                jasonData.setPage(Utils.parseInteger(page));
                for(Object obj : trxSearchList) {
                    GlobalTransactionSearch globalTransactionSearch1= new GlobalTransactionSearch();
                    Cell cell = new Cell();
                    Map map = (Map) obj;
                    Date trxDateNew =  map.get("trxDate") != null ? (Date)map.get("trxDate") : new Date();
                    String projectName = map.get("project") != null ? map.get("project").toString() : "";
                    if(!Utils.isEmpty(projectName)){
                        if("0".equals(projectName))
                            projectName = Utils.getMessageBundlePropertyValue("proactiveProject");
                        else if("1".equals(projectName))
                            projectName = Utils.getMessageBundlePropertyValue("reactiveProject");
                        else if("2".equals(projectName))
                            projectName = Utils.getMessageBundlePropertyValue("realTimeProject");
                    }

                    globalTransactionSearch1.setTrxDateStr(Utils.dateToStrWithFormat(trxDateNew,Constants.MONTH_DAY_YEAR));
                    globalTransactionSearch1.setTrxIdStr(map.get("trxId") != null ? map.get("trxId").toString(): "");
                    globalTransactionSearch1.setProjectName(projectName);
                    globalTransactionSearch1.setTrxModule(map.get("trxModule") != null ? map.get("trxModule").toString() : "");
                    globalTransactionSearch1.setTrxAmountStr(map.get("amount") != null ? map.get("amount").toString() : "");
                    globalTransactionSearch1.setRegion(map.get("regionName") != null ? map.get("regionName").toString() : "");
                    globalTransactionSearch1.setCustomerName(map.get("customerName") != null ? map.get("customerName").toString() : "");
                    //globalTransactionSearch1.setEmployeeName(map.get("employeeName") != null ? map.get("employeeName").toString() : "");
                    //globalTransactionSearch1.setVendorName(map.get("vendorName") != null ? map.get("vendorName").toString() : "");
                    globalTransactionSearch1.setTransactionType(map.get("transactionType") != null ? map.get("transactionType").toString() : "");
                    globalTransactionSearch1.setApprover(map.get("approver") != null ? map.get("approver").toString() : "");
                    globalTransactionSearch1.setRealTimeProjectIdStr(map.get("realTimeProjectId") != null ? map.get("realTimeProjectId").toString() : "");

                    //globalTransactionSearch1.setNameOfThirdParty(map.get("customerName") != null ? map.get("customerName").toString() : "");
                    cell.setId(map.get("trxId") != null ? ((Number)map.get("trxId")).intValue() : 0 );
                    cell.setCell(globalTransactionSearch1);
                    entry.add(cell);
                }

                Map mapForHeader = new HashMap();
                mapForHeader.put("trxIdStr","trxId"); // key=flexigrid parameter name, value = dbField Name
                mapForHeader.put("trxModule","trxModule");
                mapForHeader.put("trxAmountStr","amount");
                mapForHeader.put("trxDateStr","trxDate");
                mapForHeader.put("customerName","customerName");
                mapForHeader.put("approver","approver");
                //mapForHeader.put("vendorName","vendorName");
                //mapForHeader.put("employeeName","employeeName");
                dbColumnHeaderList.add(mapForHeader);
                request.getSession().setAttribute(tableName,dbColumnHeaderList);

                jasonData.setRows(entry);
                jasonData.setTotal(totalItems);
                jasonData.setDbColumnHeader(dbColumnHeaderList);
            } else {
                logger.debug("No Transaction Search List Found");
            }
                if("1".equals(fromSearch)&& "1".equals(page)){
                    logger.debug("SMN LOG:: second time list iteration----");
                    trxSearchList  = adminJdbcService.getPartialTransactionList(globalTransactionSearch, 1,totalItems,
                            qtype,query,sortname,sortorder);
                    TransactionSearchController.setTotalListSizeAndListInSession(totalItems,trxSearchList,request);
                }
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Transaction Search Exception : " + ex);
        }

        return jasonData;
    }
 @RequestMapping(value = "/admin/getTrxSearchDataList.html", method = RequestMethod.GET)
    public String workflowList(HttpServletRequest request, Model model) {
        logger.debug("Get Transaction Search list controller");
         String selectedColumnList = request.getParameter("selColList") != null ? request.getParameter("selColList") : null;
        String printColList = request.getParameter("printColList") != null ? request.getParameter("printColList") : null;
        String option = request.getParameter("option") != null ? request.getParameter("option") : null;
        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : null;
        List headerList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);

        logger.debug("printColList : "+printColList);
        logger.debug("tableName : "+tableName);
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
                model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
            } else {
                logger.debug("No Transaction Search List Found");
            }
        }catch (Exception ex) {
            logger.debug("CERROR: Transaction Search Exception : " + ex);
        }
        return "table";
    }

    public static List getDbColumnHeaderList(HttpServletRequest request,String tableIdOrClass){
        if(request.getSession().getAttribute(tableIdOrClass)!= null){
        return (List)request.getSession().getAttribute(tableIdOrClass);
        }
        return null;
    }

    public static void setTotalListSizeAndListInSession(int totalItems, List trxSearchList,HttpServletRequest request){
        request.getSession().setAttribute("totalItems",totalItems);
        request.getSession().setAttribute("list",trxSearchList);

    }

    @RequestMapping(value = "/*/realTimeSummaryViewFromTxSearch.html", method = RequestMethod.GET)
    public @ResponseBody RealTimeTransaction ajaxCallForCreatingPieChartForPolicyReviewCertification(HttpServletRequest request, Model model) {
        logger.debug("::  AJAX CALL Realtime Time Summary View From Tx Search Controller ::");
        long rtProjectId = request.getParameter("rtProjectId") != null ? Long.parseLong(request.getParameter("rtProjectId")) : 0;
        long trxId = request.getParameter("trxId") != null ? Long.parseLong(request.getParameter("trxId")) : 0;
        logger.debug("RtProjectId : " + rtProjectId+" trxId:"+trxId);
        RealTimeTransaction realTimeTransaction = new RealTimeTransaction();
        try {
            int indexOf = 0;
            int pageNo = 1;
            String serial = "tr:first";
            serial = "row"+trxId;
            List<Map> list = adminJdbcService.getRealTimeTransactionListByRtProjectId(rtProjectId);
            Map map = new HashMap();
            map.put("trxId",trxId);
            map.put("realTimeProjectId",rtProjectId);
            indexOf = list.indexOf(map);

            if(indexOf < 10)
                pageNo = 1;
            else
                pageNo = ((indexOf/10)+1);

            logger.debug("index of: "+indexOf+" page no:"+pageNo);
            logger.debug("SMN:new serial="+serial);
            request.getSession().setAttribute("pageNo",pageNo+"");
            ProactiveController.setSerialNoOnSession(request,serial);

        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception in Realtime Time Summary View From Tx Search Controller" + ex.getMessage());
            ex.printStackTrace();
        }
        return realTimeTransaction ;
    }
}

