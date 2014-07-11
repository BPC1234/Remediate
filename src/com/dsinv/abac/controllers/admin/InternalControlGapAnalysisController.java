package com.dsinv.abac.controllers.admin;

import com.dsinv.abac.bean.Cell;
import com.dsinv.abac.bean.JasonBean;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.Entity;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Utils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

import static com.dsinv.abac.util.Utils.*;
import static com.dsinv.abac.util.Utils.getMessageBundlePropertyValue;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 8/20/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@SessionAttributes("NewRiskAssessmentTxBean")
public class InternalControlGapAnalysisController {
    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    private static Logger logger = Logger.getLogger(InternalControlGapAnalysisController.class);


    /**
     * Controller to get control list of transaction type
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/icga/InternalCtrlGapAnalysisAC.html", method = RequestMethod.GET)
    public String getInternalControlGapAC(HttpServletRequest request, Model model) {
        List thirdPartyTransactionControlList = new ArrayList();
        List generalLedgerTransactionControlList = new ArrayList();
        List customerTransactionControlList = new ArrayList();
        logger.debug("Existing controls start.");
        try {
            thirdPartyTransactionControlList = adminService.getControlListByTransactionType(TransactionType.THIRD_PARTY_TRANSACTION.getValue());
            logger.debug("Third Party Transaction Control list size =  " + thirdPartyTransactionControlList.size());
            generalLedgerTransactionControlList = adminService.getControlListByTransactionType(TransactionType.GENERAL_LEDGER.getValue());
            logger.debug("General Ledger Transaction Control list size =  " + generalLedgerTransactionControlList.size());
            customerTransactionControlList = adminService.getControlListByTransactionType(TransactionType.CUSTOMER_TRANSACTION.getValue());
            logger.debug("Customer Transaction Control list size =  " + customerTransactionControlList.size());

        } catch (Exception ex) {
            logger.debug("CERROR:: Internal Control Gap Analysis  " + ex);
        }
        model.addAttribute("thirdPartyTransactionControlList", thirdPartyTransactionControlList);
        model.addAttribute("generalLedgerTransactionControlList", generalLedgerTransactionControlList);
        model.addAttribute("customerTransactionControlList", customerTransactionControlList);
        model.addAttribute("mainTabId", Utils.getMessageBundlePropertyValue("mainTab.icga"));
        model.addAttribute("subTabId", Utils.getMessageBundlePropertyValue("subTabId.analyzeByControls"));
        adminService.addNode(Utils.getMessageBundlePropertyValue("landingPage.analyzeByControls"), 2, request);
        return "common/InternalCtrlGapAnalysisAC";
    }


    /**
     * control list with applied in no of transaction
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/icga/getJASONforExistingControlList.html", method = RequestMethod.POST)
    public @ResponseBody
    JasonBean getAssignmentList(HttpServletRequest request) {
        logger.debug("Control List Controller ");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "projectName";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";

        String controlIds = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "0";
        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";

        String controlIdsListAsString = "";

        JasonBean jasonData = new JasonBean();
        List dbColumnHeaderList = new ArrayList();
        int totalControl = 0;
        List controlList = new ArrayList();
        List<Cell> entry = new ArrayList<Cell>();
        List allControlList = new ArrayList<Cell>();
        Map<String, Integer> countMap = new TreeMap<String, Integer>();

        try {
            String partSql = !"0".equals(controlIds) ? InternalControlGapAnalysisController.getStringForComparingControls(controlIds): "";
            allControlList= adminJdbcService.getControlIdList(controlIds,partSql);
            controlIdsListAsString = getCommaseparatedStringFromList(allControlList);
            String[] controllIdsArray = !"0".equals(controlIds) ? getUsedControlsByControlIds(controlIds,controlIdsListAsString.split(",")): controlIdsListAsString.split(",");

            countMap = countStringOccurences( controllIdsArray, adminJdbcService);
                if(countMap != null && countMap.size() > 0) {
                    logger.debug("AMLOG:: countMap size: " + countMap.size());
                    jasonData.setPage(Utils.parseInteger(page));

                    for (String string : countMap.keySet()) {
                        Map map = new HashMap();
                        Long controlId = !Utils.isEmpty(string) ? Long.parseLong(string) : 0;
                        logger.debug("AMLOG:: controlId: " + controlId);
//                        Control control = new Control();
                        Cell cell = new Cell();
                        Control control =  controlId > 0 ?  (Control)adminService.loadEntityById(controlId,Constants.CONTROL) : new Control();
                        /*control.setId(controlId);
                        control.setName(controlName);*/
                        control.setTotalUsed( countMap.get(string));
                        cell.setCell(control);
                        entry.add(cell);

                        map.put("control_name",control.getName());
                        map.put("transaction_type",control.getTransactionType());
                        map.put("total",countMap.get(string));
                        controlList.add(map);
                    }
                    int totalItem = controlList != null ? controlList.size() : 0;

                    Map mapForHeader = new HashMap();
                    mapForHeader.put("name","control_name"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("transactionType","transaction_type");
                    mapForHeader.put("totalUsed","total");
                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);

                    jasonData.setRows(entry);
                    jasonData.setTotal(totalItem);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);

                     if("max".equals(rp)){
                         logger.debug("SMN LOG: custom controlList size="+controlList.size());
                     TransactionSearchController.setTotalListSizeAndListInSession(totalItem,controlList,request);
                    }
                }
        } catch (Exception ex) {
            logger.debug("CERROR: Real Time Project exception : " + ex);
        }


        return jasonData;
    }

    @RequestMapping(value = "/icga/existingCtrlGapAnalysis.html", method = RequestMethod.GET)
    public String getExistingControl(HttpServletRequest request, Model model) {
        logger.debug("Existing controls start.");
        String controlIds = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "0";
        logger.debug("Control Ids="+controlIds);
        logger.debug("Existing controls end.");
        model.addAttribute("controlIds",controlIds);
        model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.icga"));
        adminService.addNode(getMessageBundlePropertyValue("landingPage.existingControls"), 2, request);
        return "common/internalCtrlGapAnalysis";
    }


    @RequestMapping(value = "/icga/AnalyzedControlsToInternalCtrlGapAnalysis.html", method = RequestMethod.GET)
    public String getAnalyzedControlsToInternalCtrlGapAnalysis(HttpServletRequest request, Model model) {

        return "common/AnalyzedControlsToInternalCtrlGapAnalysis";
    }

    /**
     * Get country wise no of transaction for selected control in control list
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/icga/ajaxCallForICGACountryWiseTx.html", method = RequestMethod.GET)
    String ajaxCallForICGACountryWiseTx(HttpServletRequest request, Model model) {
        logger.debug(" :: ajaxCallForICGACountryWiseTx ::");
        String controlIds = request.getParameter("ctrlId");              // control id selected in control list
        List countryWiseControlList = new ArrayList();
        logger.debug(" ControlIds : " + controlIds);
        try {
            countryWiseControlList = controlIds != null ? adminJdbcService.getControlWiseCountryListByControlId(controlIds) : new ArrayList();
        } catch (Exception ex) {
            logger.debug("CERROR :: CountryWiseControlList exception : " + ex);
        }

        logger.debug(" list : " + countryWiseControlList);

        model.addAttribute("countryWiseControlList", countryWiseControlList);
        model.addAttribute("controlIds", controlIds);
        return "icgaCountryWiseTx";

    }

    @RequestMapping(value = "/icga/InternalCtrlGapAnalysisAC.html", method = RequestMethod.POST)
    public String InternalCtrlGapAnalysisACPost(HttpServletRequest request, Model model) {
        logger.debug(" :: InternalCtrlGapAnalysisAC POSt ::");
        String controlIds = "";
        List<Control> controlList = adminService.getAllControl();
        for (Control control : controlList) {
            String checkboxStatus = request.getParameter(control.getId() + "");
            if (checkboxStatus != null && Constants.CHECK_BOX_ON.equals(checkboxStatus)) {
                controlIds = controlIds + "," + control.getId();
            }

        }
        controlIds = Utils.isEmpty(controlIds) ? "" : controlIds.substring(1, controlIds.length());
        logger.debug(" ControlIds : " + controlIds);
        if (Utils.isEmpty(controlIds)) {
            Utils.setErrorMessage(request, Utils.getMessageBundlePropertyValue("icga.analyzeByControl.noIdisSelect"));
            return "redirect:./InternalCtrlGapAnalysisAC.html";
        }
        return "redirect:internalCtrlGapAnalysis.html?icga=0&controlIds=" + controlIds;

    }

    /**
     * Get all transaction list with details for selected country
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/icga/InternalCtrlGapAnalysisSummaryView.html", method = RequestMethod.GET)
    public String InternalCtrlGapAnalysisSummeryView(HttpServletRequest request, Model model) {
        logger.debug(" :: InternalCtrlGapAnalysisSummaryView GET ::");

        String pageNo = request.getSession().getAttribute("pageNo")!= null? (String)request.getSession().getAttribute("pageNo"): "";
        String controlIds = request.getParameter("controlIds") != null ? request.getParameter("controlIds") : "";
        logger.debug("SMN LOG: conmtrol ids="+controlIds);

        if(Utils.isEmpty(pageNo)){
            pageNo="1";
        }
        try {

            if (!isEmpty(controlIds)) {

                    model.addAttribute("NewRiskAssessmentTxBean", new NewRiskAssessmentTxBean());
                    model.addAttribute("reactiveProjectId", "0");
                    model.addAttribute("proactiveProjectId", "0");
                    model.addAttribute("realTimeProjectId","0");

                    model.addAttribute("ctrlId", controlIds);
                    model.addAttribute("serialNoForTableRowSelection",ProactiveController.getSerialNoFromSession(request));
                    model.addAttribute("maxFileUploadSize", getApplicationPropertyValue("file.mazFileUploadSize"));
                    model.addAttribute("pageNo", pageNo);
            }

        } catch (Exception ex) {
            logger.debug("CERROR::icga Summary View: " + ex);
            logger.debug("CERROR:: Error Description: " + ex.getMessage());

        }
        model.addAttribute("mainTabId", getMessageBundlePropertyValue("mainTab.transactionMonitoring"));
        model.addAttribute("subTabId", getMessageBundlePropertyValue("subTabId.trxMonitoring"));

        return "common/newRiskAssessmentSummary1";
    }


    /**
     * Only for internal use
     * <p/>
     * Count total for each control
     *
     * @param strArray
     * @param adminJdbcService
     * @return
     */
    private static Map countStringOccurences(String[] strArray, AdminJdbcService adminJdbcService) {
        logger.debug("Count String Occurences method found");

        Map<String, Integer> countMap = new TreeMap<String, Integer>();
        Map<String, Integer> controlIdsMap = new TreeMap<String, Integer>();
        Set<String> controlIdsSet = new TreeSet<String>();
        Set<String> keySet = countMap.keySet();
        List list = new ArrayList();

        for (String string : strArray) {
            String control[] = string.split(":");
            if ( !Utils.isNullOrEmpty(control[1]) && Integer.parseInt(control[1].trim()) == 2 ) {
                if (!countMap.containsKey(control[0])) {
                    countMap.put(control[0], 1);
                } else {
                    Integer count = countMap.get(control[0]);
                    count = count + 1;
                    countMap.put(control[0], count);
                }
            }
        }


        return countMap;
    }

    private static String getCommaseparatedStringFromList(List controllIdList) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < controllIdList.size(); i++) {
            Map map = (Map) controllIdList.get(i);
            if(map.get("control_ids") != null) {
                result.append(map.get("control_ids"));
                result.append(",");
            }
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
    }

    private void setProjectTransactionIdOnSession(HttpServletRequest request, Long proactiveTransactionId) {
        request.getSession().setAttribute("projectTransactionId", proactiveTransactionId);
    }

    private Long getProjectTransactionIdFromSession(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("projectTransactionId");
    }

    private Map getFirstProjectTransactionMap(List proactiveTransactionList) {
        return (proactiveTransactionList != null && proactiveTransactionList.size() > 0) ? (Map) proactiveTransactionList.get(0) : new HashMap();
    }

    private List<Control> getControlListByTransactionType(String transactionType) {
        List<Control> controlList = adminService.getControlListByTransactionType(transactionType);
        return controlList != null ? controlList : new ArrayList<Control>();
    }

    private void setCountryNameOnSession(HttpServletRequest request, String country) {
        request.getSession().setAttribute("cName", country);
    }

    private List<Control> getControlListForProactiveTable(ProactiveTransactionCND proactiveTransactionCND, List<Control> controlList) {
        List<Control> controlListForTable = new ArrayList<Control>();
        if (proactiveTransactionCND != null) {
            String[] controlIds = proactiveTransactionCND.getControlIds().split(",");
            setControlActive(controlList, controlListForTable, controlIds);
        } else {
            setControlInActive(controlList, controlListForTable);
        }
        return controlListForTable;
    }

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

    private void setControlInActive(List<Control> controlList, List<Control> controlListForTable) {
        for (Control control1 : controlList) {
            control1.setActive(false);
            controlListForTable.add(control1);
        }
    }

    private List<Control> getControlListForReactiveTable(ReactiveTransactionCND reactiveTransactionCND, List<Control> controlList) {
        List<Control> controlListForTable = new ArrayList<Control>();
        if (reactiveTransactionCND != null) {
            String[] controlIds = reactiveTransactionCND.getControlIds().split(",");
            setControlActive(controlList, controlListForTable, controlIds);
        } else {
            setControlInActive(controlList, controlListForTable);
        }
        return controlListForTable;
    }

    private List getHederAndValueOfPersonInfo(List infoList) throws ParseException {
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

    // save transaction type on session for further use  
    private void setTransactionTypeOnSession(HttpServletRequest request, String transactionType) {
        request.getSession().setAttribute("transactionType", transactionType);
    }

    //  save proactive transaction id on session for further use
    private void setProactiveTransactionIdOnSession(HttpServletRequest request, Long proactiveTransactionId) {
        request.getSession().setAttribute("proactiveTransactionId", proactiveTransactionId);
    }

    // save reactive transaction id on session for further use
    private void setReactiveTransactionIdOnSession(HttpServletRequest request, Long reactiveTransactionId) {
        request.getSession().setAttribute("reactiveTransactionId", reactiveTransactionId);
    }


    @RequestMapping(value = "/icga/projectWiseControlCount.html", method = RequestMethod.GET)
    String ajaxCallForProjectWiseControlCount(HttpServletRequest request, Model model) {
        try {
            logger.debug("--------in AJAX project Wise Control Count CONTROLLER----------");
            String controlId = request.getParameter("controlId") != null ? request.getParameter("controlId") : "0";
            logger.debug("controlId : " + controlId);
            String controlName = !"0".equals(controlId) ? adminJdbcService.getControlNameByControlId(Long.parseLong(controlId)):"";
            model.addAttribute("controlId", controlId);
            model.addAttribute("controlName", controlName);

        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "common/icgaCountryWiseTx";
    }

    /**
     * control list with applied in no of Project
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/icga/getJASONforProjectWiseControlList.html", method = RequestMethod.POST)
    public @ResponseBody
    JasonBean getProjectWiseControlCountList(HttpServletRequest request) {
        logger.debug("Project Wise Control List Controller ");
        String  page = request.getParameter("page") != null ? request.getParameter("page") : "1";
        String rp = request.getParameter("rp") != null ? request.getParameter("rp") : "10";
        String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "projectName";
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String query = request.getParameter("query") != null ? request.getParameter("query") : "false";
        String qtype = request.getParameter("qtype") != null ? request.getParameter("qtype") : "false";
        String controlIdsListAsString = request.getParameter("controlId") != null ? request.getParameter("controlId") : "";

        String tableName = request.getParameter("tableName") != null ? request.getParameter("tableName") : "";

        List dbColumnList =  TransactionSearchController.getDbColumnHeaderList(request,tableName);
        sortname = dbColumnList!= null? RealTimeMonitoringController.getDbColumnNameByRef(dbColumnList,sortname): null;
        logger.debug("SMN: controlIdsListAsString="+controlIdsListAsString);

        List dbColumnHeaderList = new ArrayList();
        JasonBean jasonData = new JasonBean();

        int totalItems = 0 ;
        List<Cell> entry = new ArrayList<Cell>();
        List trxSearchList  = new ArrayList<Control>();
        GlobalTransactionSearch globalTransactionSearch = new GlobalTransactionSearch();
        try {

           totalItems = adminJdbcService.getProjectWiseCountByControlIds(controlIdsListAsString,getStringForComparingControls(controlIdsListAsString),
                   qtype, query, sortname, sortorder);
           trxSearchList = adminJdbcService.getProjectWiseCountListByControlIds(controlIdsListAsString,getStringForComparingControls(controlIdsListAsString),
                    Utils.parseInteger(page),"max".equals(rp) ? totalItems : Utils.parseInteger(rp),qtype, query, sortname, sortorder);

            logger.debug("TOTAL count---"+totalItems+" page="+page+" rp="+rp);
            if("max".equals(rp)){
                 TransactionSearchController.setTotalListSizeAndListInSession(totalItems,trxSearchList,request);
                 jasonData.setTotal(totalItems);
            }else{
            if(trxSearchList != null) {
                int count=0;
                    logger.debug("trxSearchList size="+trxSearchList.size());
                    jasonData.setPage(Utils.parseInteger(page));
                    for(Object obj : trxSearchList) {
                        RealTimeProject realTimeProject = new RealTimeProject();
                        Cell cell = new Cell();
                        Map map = (Map) obj;

                        realTimeProject.setProjectName(map.get("project") != null ? map.get("project").toString() : "");
                        realTimeProject.setTransactionIds(map.get("transaction_ids") != null ? map.get("transaction_ids").toString() : "");
                        realTimeProject.setTotal(map.get("total") != null ? map.get("total").toString() : "");
                        realTimeProject.setProjectType(map.get("projectType") != null ? map.get("projectType").toString() : "");
                        realTimeProject.setProjectId(map.get("projectId") != null ? map.get("projectId").toString() : "");

                        cell.setId(count++);
                        cell.setCell(realTimeProject);
                        entry.add(cell);
                    }

                    Map mapForHeader = new HashMap();
                    mapForHeader.put("projectName","project"); // key=flexigrid parameter name, value = dbField Name
                    mapForHeader.put("transactionIds","transaction_ids");
                    mapForHeader.put("total","total");
                    mapForHeader.put("projectId","projectId");
                    mapForHeader.put("projectType","projectType");
                    dbColumnHeaderList.add(mapForHeader);
                    request.getSession().setAttribute(tableName,dbColumnHeaderList);

                    jasonData.setRows(entry);
                    jasonData.setTotal(totalItems);
                    jasonData.setDbColumnHeader(dbColumnHeaderList);
                } else {
                    logger.debug("No Transaction Search List Found");
                }
            }

        }catch (Exception ex) {
            logger.debug("CERROR:Project Wise Control Exception : " + ex);
        }

        return jasonData;

    }

    public static String getStringForComparingControls(String controlIds){
        String query="";
        String ctlId="";
        if(controlIds != null && !Utils.isEmpty(controlIds)){
        String arr[] = controlIds.split(",");

            for(int i=0; i<arr.length; i++){
                if(i==0)query = query+"AND ( ";
                else query = query+"OR ( ";
                query = query+" control_ids LIKE '"+arr[i]+":2,%' OR control_ids LIKE '%,"+arr[i]+":2' OR control_ids LIKE '%,"+arr[i]+":2,%' OR control_ids = '"+arr[i]+":2' )" ;

            }
//            logger.debug("SMN LOG:PART OF SQL="+query);
           return query;
        }else return null;

    }

    public static String[] getUsedControlsByControlIds(String controlIds,String[] usedControlIds){
        Vector<String> customArr = new Vector<String>();
        if(controlIds != null && !"0".equals(controlIds)){
            String arr[] = controlIds.split(",");
            for(String useControl : usedControlIds){
                 for(int i=0; i<arr.length; i++){
//                   if(useControl.equals(arr[i]+":1") || useControl.equals(arr[i]+":2")|| useControl.equals(arr[i]+":3")|| useControl.equals(arr[i]+":4")){
                   if(useControl.equals(arr[i]+":2")){
                       customArr.add(useControl);
                  }
                }
             }
            return customArr.toArray(new String[customArr.size()]);
        }else return null;

    }
}