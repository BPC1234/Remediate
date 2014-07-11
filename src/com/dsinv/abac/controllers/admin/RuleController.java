package com.dsinv.abac.controllers.admin;


import com.dsinv.abac.entity.*;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.validation.ControlValidation;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RuleController {

    private static Logger logger = Logger.getLogger(RuleController.class);

    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private ControlValidation controlValidation;


    /**
     * Get transaction Approval page
     *
     * @param request
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value = "/admin/ruleCreation.html", method = RequestMethod.GET)
    public String realTimeMonitoringIntervalSetup(HttpServletRequest request, Model model, Map map) {
        logger.debug(" :: Rule Creation Controller::");
        String selectClause;
        String fromClause;
        String whereClause;
        String groupByClause;
        String orderByClause;
        List<Rule> ruleList = new ArrayList<Rule>();
        String opt = request.getParameter("opt");
        long ruleId = request.getParameter("ruleId") != null ? Long.parseLong(request.getParameter("ruleId").toString()) : 0;
        logger.debug("Rule Id : " + ruleId);
        TransactionApproval transactionApproval = new TransactionApproval();
        List<Lookup> lookupList = new ArrayList<Lookup>();
        lookupList = adminService.getLookupList();
        if (ruleId > 0) {
            logger.debug("---i am in if----");
            String[] clauses = new String[10];
            Rule rule = adminService.getRuleById(ruleId);
            logger.debug("FULL QUERY = "+rule.getSelectClause()+rule.getFromClause()+rule.getWhereClause());
            clauses = getIndividualClausesByQuery(rule.getSelectClause()+rule.getFromClause()+rule.getWhereClause());
            for(int i=0; i<clauses.length; i++){
                logger.debug("-------"+getClauseNameByString(clauses[i]));
                if(getClauseNameByString(clauses[i]) != null){
                 if(getClauseNameByString(clauses[i]).equalsIgnoreCase("SELECT"))  {
                     ruleList = getRuleListWithDetailsBySelectClause("SELECT",clauses[i],new ArrayList<Rule>());
                     logger.debug("----------First Rule list size========="+ruleList.size());
                 }

                 if(getClauseNameByString(clauses[i]).equalsIgnoreCase("GROUP BY") || getClauseNameByString(clauses[i]).equalsIgnoreCase("ORDER BY")){
                     ruleList = getRuleListWithOrderByAndGroupByDetails(getClauseNameByString(clauses[i]),clauses[i],ruleList);
                     logger.debug("----------Second Rule list size========="+ruleList.size());
                 }

                 if(getClauseNameByString(clauses[i]).equalsIgnoreCase("HAVING"))  {
                        ruleList = getRuleListWithHavingClauseDetails("HAVING",clauses[i],ruleList);
                        logger.debug("----------Third Rule list size========="+ruleList.size());
                    }

                 if(getClauseNameByString(clauses[i]).equalsIgnoreCase("WHERE"))  {
                        ruleList = getRuleListWithWhereClauseDetails("WHERE", clauses[i], ruleList);
                        logger.debug("----------Fourth Rule list size========="+ruleList.size());
                    }

                 }
                }
                if(ruleList != null){
                    for(Rule newRule:ruleList){
                        logger.debug("object rule:"+newRule);
                        for(int i=0; i<lookupList.size(); i++){
                            Lookup lookup = lookupList.get(i);
                           if(newRule.getTableName().trim().equals(lookup.getLedgerName().trim())){
                               lookup.setSelectTableName(true);
                               lookupList.set(i,lookup);
                           }
                        }
                    }
                }

            }

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(ruleList);
        } catch (Exception e) {
            e.printStackTrace();
        }

            model.addAttribute("ruleList", ruleList);
            model.addAttribute("list", json);
            model.addAttribute("rule", new Rule());
            model.addAttribute("lookupList", lookupList);
            model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);
            if (opt != null && "1".equals(opt)) {
                Utils.setGreenMessage(request, Utils.getMessageBundlePropertyValue("rule.save.successful.message"));
            }
        return "admin/ruleCreation";
    }

    /**
     * To Save Rule
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/ruleCreation.html", method = RequestMethod.POST)
    public void saveRule(HttpServletRequest request, @ModelAttribute("rule") Rule rule, Model model) {
        logger.debug(" :: Rule Creation Save Controller POST::");
        logger.debug(" :: Rule ::" + rule);
        adminService.save(rule);
        //return "redirect:/admin/ruleCreation.html";
    }

    /*
* Method to upload file by ajax call
* @param MultipartHttpServletRequest request, HttpServletRequest requestForServlet, Model model
* @return type String
*/
    @RequestMapping(value = "/*/getColumnNameByAjaxCall.html", method = RequestMethod.POST)
    String ajaxCallForFileUpload(HttpServletRequest request, Model model) {
        try {
            logger.debug("get Column Name By Ajax Call Controller (POST) : ");

            String lookupIds = request.getParameter("lookupIds");
            logger.debug("---lookupIds---" + lookupIds);
            String lookupIdArray[] = lookupIds.split(",");
            List<Rule> ruleList = new ArrayList();
            for (int i = 0; i < lookupIdArray.length; i++) {
                Rule rule = new Rule();
                List columnNameList = new ArrayList();
                logger.debug("---table name---" + lookupIdArray[i]);
                columnNameList = adminJdbcService.getColumnNameList(lookupIdArray[i]);
                rule.setColumnNameList(columnNameList);
                rule.setTableName(lookupIdArray[i]);

                ruleList.add(rule);
                logger.debug("column list : " + columnNameList);

            }
            model.addAttribute("ruleList", ruleList);
            model.addAttribute("MMddYYYY", Constants.MONTH_DAY_YEAR);


        } catch (Exception ex) {
            logger.debug("CERROR:: Ajax Call Exception " + ex.getMessage());
            ex.printStackTrace();
        }
        return "ruleDefinitionDiv";
    }

    public static String[] getIndividualClausesByQuery(String query) {

     /*   String[] isExistClause = {"SELECT","FROM","WHERE","HAVING","GROUP BY","ORDER BY",""};*/

        String temp = query.replace("SELECT", "#SELECT").
                replace("FROM", "#FROM").
                replace("WHERE", "#WHERE").
                replace("HAVING", "#HAVING").
                replace("GROUP BY", "#GROUP BY").
                replace("ORDER BY", "#ORDER BY");
        String[] clauses = temp.split("#");

      return clauses;
    }

    public static String getClauseNameByString(String clause){
           if(clause.contains("SELECT"))
               return "SELECT";
           else if(clause.contains("FROM"))
               return "FROM";
           else if(clause.contains("WHERE"))
               return "WHERE";
           else if(clause.contains("HAVING"))
               return "HAVING";
           else if(clause.contains("GROUP BY"))
               return "GROUP BY";
           else if(clause.contains("ORDER BY"))
               return "ORDER BY";
          else
            return null;
    }

    public static List<Rule> getRuleListWithDetailsBySelectClause(String clauseName,String clause,List<Rule> ruleList){
     if(clause != null && !Utils.isEmpty(clause)){
         clause = clause.replace(clauseName,"").replace("SELECT DISTINCT","").replace("SELECT","").replace("?,","").replace("trx.id transactionId ,",""); // replace SELECT/FROM/WHERE = ""
         logger.debug("final clause="+clause);
         String[] array = clause.split(",");
         for(int i=0; i<array.length; i++){
             String columnNameWithTableName = !Utils.isEmpty(array[i]) ? array[i] : null ;
             logger.debug("value="+columnNameWithTableName);
           Rule rule = getTableNameColumnNameWithAggrFnName(columnNameWithTableName);
           ruleList.add(rule);
         }

     return ruleList;
     }
    return null;
    }


    public static List<Rule> getRuleListWithHavingClauseDetails(String clauseName,String clause,List<Rule> ruleList){
        if(clause != null && !Utils.isEmpty(clause)){
            clause = clause.replace(clauseName,""); // replace SELECT/FROM/WHERE = ""
            logger.debug("final clause="+clause);
            String temp = clause.replace("AND","AND #").replace("OR","OR #");
            String[] array = temp.split("#");
            for(int i=0; i<array.length; i++){
                logger.debug("setting  having condition==="+array[i]);
                Rule rule = getRuleBySingleHavingCondition(array[i]);
                logger.debug("return rule=="+rule);
                if(rule != null && ruleList != null){
                    for(int j=0; j<ruleList.size(); j++){
                        Rule newRule = ruleList.get(j);
                        if((newRule.getTableName()+"."+newRule.getColumnName()).equals(rule.getTableNameWithColumnName())){
                            newRule.setHavingExtraCondition(rule.getHavingExtraCondition());
                            newRule.setHavingCompareValue(rule.getHavingCompareValue());
                            newRule.setHavingCondition(rule.getHavingCondition());
                            newRule.setHavingClauseSelect(rule.getHavingClauseSelect());
                            newRule.setHavingAggregateFn(rule.getHavingAggregateFn());
                            newRule.setTableNameWithColumnName(rule.getTableNameWithColumnName());
                            ruleList.set(j,newRule);
                        }

                    }
                }
            }
            return ruleList;
        }
        return null;
    }

    public static List<Rule> getRuleListWithOrderByAndGroupByDetails(String clauseName,String clause,List<Rule> ruleList){
        if(clause != null && !Utils.isEmpty(clause)){
            clause = clause.replace(clauseName,"").replace("?,",""); // replace SELECT/FROM/WHERE = ""
            logger.debug("final clause="+clause);
            String[] array = clause.split(",");
            for(int i=0; i<array.length; i++){
                String columnNameWithTableName = !Utils.isEmpty(array[i]) ? array[i] : null ;
                logger.debug("value="+columnNameWithTableName);
                Rule rule = getTableNameColumnNameWithAggrFnName(columnNameWithTableName);
                if(ruleList != null){
                    for(int j=0; j<ruleList.size(); j++){
                        Rule newRule = ruleList.get(j);
                        if(rule.getTableName().equals(newRule.getTableName()) && rule.getColumnName().equals(newRule.getColumnName())){
                             if(clauseName.equals("GROUP BY")) {
                                 newRule.setGroupBySelect("1");
                             }

                             if(clauseName.equals("ORDER BY")){
                                 newRule.setOrderBySelect("1");
                             }
                         ruleList.set(j,newRule);

                        }

                    }
                }
            }

            return ruleList;
        }
        return null;
    }

    public static Rule getTableNameColumnNameWithAggrFnName(String str){
    String[] aggregateFunctionArray = {"AVG()", "COUNT(DISTINCT)", "COUNT()", "SUM()", "MAX()", "MIN()", "BIT_AND()", "BIT_OR()", "BIT_XOR()",
                "GROUP_CONCAT()", "STD()", "STDDEV_POP()", "STDDEV_SAMP()", "STDDEV()", "VAR_POP()", "VAR_SAMP()", "VARIANCE()"};

        boolean isAggregateFn = false;
        Rule rule = new Rule();
          for(int i=0; i<aggregateFunctionArray.length; i++){
              if(str.contains(aggregateFunctionArray[i].replace(")", ""))){
               isAggregateFn = true;
                  if(isAggregateFn == true){
                      logger.debug("match aggregate function=="+aggregateFunctionArray[i]);
                      str = str.replace(")","").replace(aggregateFunctionArray[i].replace(")", ""), "");
                      logger.debug("after replacing=="+str);
                      String[] arr = str.trim().replace("."," ").split(" ");
                      logger.debug("arr length=="+arr.length);
                      if(arr.length >= 1){
                          rule.setTableName(arr[0]);
                          rule.setColumnName(arr[1]);
                          rule.setAggregateFnSelect(true);
                          rule.setAggregateFnValue(aggregateFunctionArray[i]);
                      }
                  }
              }
          }
        if(isAggregateFn == false){
            String[] arr = str.trim().replace("."," ").split(" ");
            logger.debug("if false arr length=="+arr.length);
            if(arr.length >= 1){
                rule.setTableName(arr[0]);
                rule.setColumnName(arr[1]);
                rule.setAggregateFnSelect(false);
            }
        }
        return rule;
    }

    public static Rule getRuleBySingleHavingCondition(String singleHavingClause){
        if(singleHavingClause != null){
        String[] arr = singleHavingClause.trim().replaceAll("[ ]+", " ").replace(" )",")").split(" "); //remove multiple spaces into one
        Rule rule = new Rule();
        if(arr == null || arr.length < 4){
            logger.debug("ERROR : Unknown Having Clause.");
            return null;
        }else if(arr != null && arr.length >= 4){
                logger.debug("having single condition length="+arr.length);
                rule.setHavingAggregateFn(arr[0]+")");
                rule.setTableNameWithColumnName(arr[1].replace(")",""));
                rule.setHavingClauseSelect("1");
                rule.setHavingCondition(arr[2]);
                rule.setHavingCompareValue(arr[3]);
                rule.setHavingExtraCondition(".");
         }

         if(arr != null && arr.length == 5){
               rule.setHavingExtraCondition(arr[4]);
         }
      return rule;
    }
      return null;
    }

    public static Rule getRuleBySingleWhereCondition(String singleWhereClause){
        if(singleWhereClause != null){
            int maxArrLength = 0;
            String[] syntaxArr = {"=", "<", ">", "<=", ">=", "<>", "!=", "NOT LIKE", "LIKE", "IS NULL", "IS NOT NULL", "NOT IN", "IN",
                    "NOT EXISTS", "EXISTS", "NOT BETWEEN", "BETWEEN"};

            Rule rule = new Rule();

            Pattern pattern = Pattern.compile(".*'([^']*)'.*");
            Matcher matcher = pattern.matcher(singleWhereClause);
            if(matcher.matches()) {
               rule.setWhereColumnValue(matcher.group(1));
                singleWhereClause = singleWhereClause.replace("'"+matcher.group(1)+"'","").replaceAll("[ ]+", " ");
                logger.debug("found-"+rule.getWhereColumnValue());
            }

            for(int i=0; i<syntaxArr.length; i++){
             if(singleWhereClause.contains(syntaxArr[i])){
                 rule.setWhereFirstCondition(syntaxArr[i]);
                 singleWhereClause = singleWhereClause.replace(syntaxArr[i],"");
                 logger.debug("found-"+rule.getWhereFirstCondition()+"---new string ---"+singleWhereClause);
             }
            }

            singleWhereClause = singleWhereClause.trim().replaceAll("[ ]+", " ");
            String[] arr = singleWhereClause.split(" "); //remove multiple spaces into one
            logger.debug("having single WHERE length="+arr.length);
            if(rule.getWhereFirstCondition() != null && (rule.getWhereFirstCondition().equals("BETWEEN") ||rule.getWhereFirstCondition().equals("NOT BETWEEN"))){
               if(arr.length == 4 ){
                rule.setWhereClauseFirstSelectionValue(arr[0]);
                rule.setWhereClauseFirstTextValue(arr[1]); // as condition sign was replacesd by space
                rule.setWhereClauseSecondTextValue(arr[3]);
               }else if(arr.length >= 5 ){
                rule.setWhereExtraCondition(arr[5]);
              }
            }else if(rule.getWhereFirstCondition() != null && (rule.getWhereFirstCondition().equals("IS NULL") || rule.getWhereFirstCondition().equals("IS NOT NULL") )){
                if(arr.length <= 2 ){
                    rule.setWhereClauseFirstSelectionValue(arr[0]);
                    // first condition set previously
                    rule.setWhereExtraCondition(arr[1]); // as condition sign was replacesd by space
                    //rule.setWhereClauseSecondTextValue(arr[2]);
                }else if(arr.length >2 && arr[2].equals("(")){
                    rule.setWhereExtraCondition(arr[1]+arr[2]);
                }
            }
                /*if(arr[1].equals("=") || arr[1].equals("<")|| arr[1].equals(">")|| arr[1].equals("<=")|| arr[1].equals(">=")|| arr[1].equals("<>")
                        || arr[1].equals("!=") || arr[1].equals("NOT LIKE")|| arr[1].equals("LIKE")|| arr[1].equals("NOT IN")|| arr[1].equals("IN")
                        || arr[1].equals("NOT EXIST")|| arr[1].equals("EXIST")){

               logger.debug("having single WHERE length="+arr.length);
               //logger.debug("having single WHERE condition="+singleHavingClause);
               }*/
                /*rule.setHavingAggregateFn(arr[0]+")");
                rule.setTableNameWithColumnName(arr[1].replace(")",""));
                rule.setHavingClauseSelect("1");
                rule.setHavingCondition(arr[2]);
                rule.setHavingCompareValue(arr[3]);
                rule.setHavingExtraCondition(".");*/
            }



        return null;
    }


    public static List<Rule> getRuleListWithWhereClauseDetails(String clauseName,String clause,List<Rule> ruleList){
        if(clause != null && !Utils.isEmpty(clause)){
            clause = clause.replaceAll("[ ]+", " ").replace("WHERE (","").replace(") AND tx.job_id = ?",""); // replace SELECT/FROM/WHERE = ""
            logger.debug("previous WHERE clause="+clause);
            String temp = clause.replace("AND","AND #")
                    .replace("OR","OR #")
                    .replace("AND # (","AND (#")
                    .replace("OR # (","OR (#")
                    .replace(") AND #",") AND#")
                    .replace(") OR #",") OR#")
                    .replace(")",")#");

            logger.debug("final WHERE clause="+temp);

            String[] array = temp.split("#");
            for(int i=0; i<array.length; i++){  // -1 for discard the last string ')'
                logger.debug("setting  WHERE condition==="+array[i]);
                Rule rule = getRuleBySingleWhereCondition(array[i]);
                logger.debug("return rule=="+rule);
                if(rule != null && ruleList != null){
                    for(int j=0; j<ruleList.size(); j++){
                        Rule newRule = ruleList.get(j);
                        if((newRule.getTableName()+"."+newRule.getColumnName()).equals(rule.getTableNameWithColumnName())){
                            newRule.setHavingExtraCondition(rule.getHavingExtraCondition());
                            newRule.setHavingCompareValue(rule.getHavingCompareValue());
                            newRule.setHavingCondition(rule.getHavingCondition());
                            newRule.setHavingClauseSelect(rule.getHavingClauseSelect());
                            newRule.setHavingAggregateFn(rule.getHavingAggregateFn());
                            newRule.setTableNameWithColumnName(rule.getTableNameWithColumnName());
                            ruleList.set(j,newRule);
                        }

                    }
                }
            }
            return ruleList;
        }
        return null;
    }

}

