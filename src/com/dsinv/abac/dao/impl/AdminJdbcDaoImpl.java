package com.dsinv.abac.dao.impl;

/**
 * Created with IntelliJ IDEA.
 * User: habib
 * Date: 7/22/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */

import com.dsinv.abac.Entity;
import com.dsinv.abac.dao.AdminJdbcDao;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import org.apache.log4j.Logger;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class AdminJdbcDaoImpl implements AdminJdbcDao {
    private static Logger logger = Logger.getLogger(AdminJdbcDaoImpl.class);
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired(required = true)
    public void setJdbcDataSource(DataSource jdbcDataSource) {
        this.jdbcTemplate = new JdbcTemplate(jdbcDataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcDataSource);
    }

    public List getProactiveTransaction(ProactiveProject proactiveProject) {

        /*  String subQuery = "";
        if(TransactionType.THIRD_PARTY_TRANSACTION.getValue().equals(proactiveProject.getTransactionType())) {
            subQuery = "LEFT JOIN transaction_li txli ON (txli.id = trx.id ) " +
                    " LEFT JOIN transaction_li_lable txlila ON (txlila.id = txli.transaction_li_lable_id)";
        }*/
        String sql = "SELECT pt.id,trx.created createdDate,FORMAT(trx.amount,2) amount, region.name regionName, ppCnd.decision,trx.id trxId"
                + " FROM"
                + " proactive_transaction pt "
                + " JOIN proactive_project pp ON(pt.proactive_project_id = pp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN region ON(pp.region_id = region.id)"
                + " LEFT JOIN proactive_transaction_CND ppCnd ON(pt.id = ppCnd.proactive_transaction_id)"
                + " WHERE pp.region_id = ?"
                + (proactiveProject.getId() > 0 ? " AND pp.id = ? " : "")
                + " AND pp.transaction_type = ?"
                + " AND pp.from_date >= ? AND pp.to_date <= ?";

        List paramList = new ArrayList();
        paramList.add(proactiveProject.getRegion().getId());
        paramList.add(proactiveProject.getId());
        paramList.add(proactiveProject.getTransactionType());
        paramList.add(proactiveProject.getFromDate());
        paramList.add(proactiveProject.getToDate());


        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }


    public List getReactiveTransactionList(ReactiveProject reactiveProject) {
        String sql = "SELECT pt.id,trx.created createdDate,FORMAT(trx.amount,2) amount, region.name regionName, ppCnd.decision, trx.transaction_date trxDate, trx.id trxId"
                + " FROM"
                + " reactive_transaction pt "
                + " JOIN reactive_project pp ON(pt.reactive_project_id = pp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN region ON(pp.region_id = region.id)"
                + " LEFT JOIN reactive_transaction_CND ppCnd ON(pt.id = ppCnd.reactive_transaction_id)"
                + " WHERE pp.region_id = ?"
                + (reactiveProject.getId() > 0 ? " AND pp.id = ? " : "")
                + " AND pp.transaction_type = ?"
                + " AND pp.payment_date = ?";

        List paramList = new ArrayList();
        paramList.add(reactiveProject.getRegion().getId());
        paramList.add(reactiveProject.getId());
        paramList.add(reactiveProject.getTransactionType());
        paramList.add(reactiveProject.getPaymentDate());

        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public List getProactiveTransactionByTrxId(ProactiveProject proactiveProject, long proactiveTransactionId) {
        String sql = "SELECT pt.id,trx.created createdDate,FORMAT(trx.amount,2) amount, region.name regionName, ppCnd.decision,trx.id trxId"
                + " FROM"
                + " proactive_transaction pt "
                + " JOIN proactive_project pp ON(pt.proactive_project_id = pp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN region ON(pp.region_id = region.id)"
                + " LEFT JOIN proactive_transaction_CND ppCnd ON(pt.id = ppCnd.proactive_transaction_id)"
                + " WHERE pp.region_id = ? AND pt.id = ?"
                + " AND pp.transaction_type = ?"
                + (proactiveProject.getId() > 0 ? " AND pp.id = ? " : "")
                + " AND pp.from_date >= ? AND pp.to_date <= ?";
        List paramList = new ArrayList();
        paramList.add(proactiveProject.getRegion().getId());
        paramList.add(proactiveTransactionId);
        paramList.add(proactiveProject.getTransactionType());
        paramList.add(proactiveProject.getId());
        paramList.add(proactiveProject.getFromDate());
        paramList.add(proactiveProject.getToDate());


        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }


    public List getReactiveTransactionByTrxId(ReactiveProject reactiveProject, long reactiveTransactionId) {
        String sql = "SELECT pt.id,trx.created createdDate,FORMAT(trx.amount,2) amount, region.name regionName, ppCnd.decision,trx.id trxId"
                + " FROM"
                + " reactive_transaction pt "
                + " JOIN reactive_project pp ON(pt.reactive_project_id = pp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN region ON(pp.region_id = region.id)"
                + " LEFT JOIN reactive_transaction_CND ppCnd ON(pt.id = ppCnd.reactive_transaction_id)"
                + " WHERE pp.region_id = ? AND pt.id = ?"
                + " AND pp.transaction_type = ?" + (reactiveProject.getId() > 0 ? " AND pp.id = ? " : "")
                + " AND pp.payment_date = ?";
        List paramList = new ArrayList();
        paramList.add(reactiveProject.getRegion().getId());
        paramList.add(reactiveTransactionId);
        paramList.add(reactiveProject.getTransactionType());
        paramList.add(reactiveProject.getId());
        paramList.add(reactiveProject.getPaymentDate());


        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public void deleteProactiveTransactionByProactiveProjectId(Long proactiveProjectId) {
        jdbcTemplate.execute("DELETE FROM proactive_transaction WHERE proactive_project_id =" + proactiveProjectId);
    }

    public void deleteProactiveTransactionCNDByProactiveTransactionId(Long proactiveTransactionId) {
        jdbcTemplate.execute("DELETE FROM proactive_transaction_CND WHERE proactive_transaction_id =" + proactiveTransactionId);
    }

    public void deleteProactiveTransactionAuditTrialByProactiveProjectId(Long proactiveTransactionId) {
        jdbcTemplate.execute("DELETE FROM proactive_transaction_audit_trial WHERE proactive_transaction_id =" + proactiveTransactionId);
    }

    public void deleteReactiveTransaction(Long reactiveTransactionId) {
        jdbcTemplate.execute("DELETE FROM reactive_transaction WHERE id =" + reactiveTransactionId);
    }
    public void deleteReactiveTransactionCNDByReactiveTransactionId(Long reactiveTransactionId) {
        jdbcTemplate.execute("DELETE FROM reactive_transaction_CND WHERE reactive_transaction_id =" + reactiveTransactionId);
    }


    public void deleteReactiveTransactionAuditTrialByReactiveTransactionId(Long reactiveTransactionId) {
        jdbcTemplate.execute("DELETE FROM reactive_transaction_audit_trial WHERE reactive_transaction_id = " + reactiveTransactionId);
    }

    public void deleteReactiveProject(Long reactiveProjectId) {
        jdbcTemplate.execute("DELETE FROM reactive_project WHERE id = " + reactiveProjectId);
    }

    public List getReactiveTransactionIdByReactiveProject(long reactiveProject) {
        String sql = "SELECT id FROM reactive_transaction WHERE reactive_project_id = ? ";
        return jdbcTemplate.queryForList(sql, new Object[]{reactiveProject});
    }

    public void deleteReactiveProjectById(Long reactiveProjectId) {
        List reactiveTransactionIdList = getReactiveTransactionIdByReactiveProject(reactiveProjectId);
        long reactiveTransactionId = 0;
        for (Object object : reactiveTransactionIdList) {
            reactiveTransactionId = (Long) ((Map) object).get("id");
            deleteReactiveTransactionAuditTrialByReactiveTransactionId(reactiveTransactionId);
            deleteReactiveTransactionCNDByReactiveTransactionId(reactiveTransactionId);
            deleteReactiveTransaction(reactiveTransactionId);
        }
        deleteReactiveProject(reactiveProjectId);
    }

    public List getControlIdList(String controlIds,String controlCompareString) {
        String clause = "";
        if (Utils.getLoggedUserRoleName().equals(Role.IA_ANALYST.getLabel())) {
            clause = " AND rp.assign_to ='"+Utils.getLoggedUserName()+"' ";
        }
        if(Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())){
           clause = " AND rcnd.decision = 'Further Action Required' ";
        }

        logger.debug("ControlIds:"+controlIds+" controlCompareString:"+controlCompareString+" clause:"+clause);

        String sql = "SELECT GROUP_CONCAT( pcnd.control_ids ) control_ids,"
                + " GROUP_CONCAT( proactive_transaction_id ) transaction_ids"
//                + ", GROUP_CONCAT(region.id) region_ids"
                + " FROM"
                + " proactive_transaction_CND pcnd"
                + " JOIN proactive_transaction ptx ON(ptx.id = pcnd.proactive_transaction_id)"
                + " JOIN proactive_project pp ON(pp.id = ptx.proactive_project_id)"
//                + " JOIN region ON(region.id = pp.region_id)"
                + " WHERE pcnd.control_ids IS NOT NULL "
                + (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())? " AND pcnd.decision = 'Further Action Required' " : "")
//                + " AND pcnd.control_ids > '' "
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):" AND pcnd.control_ids > '' " )
                + " UNION ALL"
                + " SELECT GROUP_CONCAT( rcnd.control_ids ) control_ids,"
                + " GROUP_CONCAT( reactive_transaction_id ) transaction_ids "
//                + ", GROUP_CONCAT(region.id) region_ids"
                + " FROM "
                + " reactive_transaction_CND rcnd  "
                + " JOIN reactive_transaction rtx ON(rtx.id = rcnd.reactive_transaction_id)"
                + " JOIN reactive_project rp ON(rp.id = rtx.reactive_project_id)"
//                + " JOIN region ON(region.id = rp.region_id)"
                + " WHERE rcnd.control_ids IS NOT NULL "
                + (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())? " AND rcnd.decision = 'Further Action Required' " : "")
//                + " AND rcnd.control_ids > '' "
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"  AND rcnd.control_ids > '' " )
                + " UNION ALL"
                + " SELECT GROUP_CONCAT( rcnd.control_ids ) control_ids,"
                + " GROUP_CONCAT( real_time_transaction_id ) transaction_ids"
//                + ", GROUP_CONCAT(region.id) region_ids"
                + " FROM "
                + " real_time_transaction_CND rcnd  "
                + " JOIN real_time_transaction rtx ON(rtx.id = rcnd.real_time_transaction_id)"
                + " JOIN real_time_project rp ON(rp.id = rtx.real_time_project_id)"
//                + " JOIN region ON(region.id = rp.region_id)"
                + " WHERE rcnd.control_ids IS NOT NULL "
                + clause
//                + " AND rcnd.control_ids > '' "
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):" AND rcnd.control_ids > '' " );
        logger.debug("SMN LOG:SQL:"+sql);
        return jdbcTemplate.queryForList(sql);
    }

    public String getControlIdListByControlIds(String ids) {
        String sql = "SELECT GROUP_CONCAT(control.id) control_id"
                + " FROM"
                + " control"
                + (ids != null ? " WHERE control.id IN (" + ids + ")" : "");

        Map map = jdbcTemplate.queryForMap(sql);
        if (map != null) return (String) map.get("control_id");
        return null;
    }

    public String getControlNameByControlId(long id) {
        String sql = "SELECT  control.name controlName"
                + " FROM"
                + " control"
                + " WHERE control.id = ? ";
        List paramList = new ArrayList();
        paramList.add(id);
        Map map = jdbcTemplate.queryForMap(sql, paramList.toArray());
        if (map != null) return (String) map.get("controlName");
        return null;
    }

    public List getControlWiseCountryListByControlId(String controlId) {

        String secondCondition = controlId + ",%";
        String thirdCondition = "%," + controlId + ",%";
        String fourthCondition = "%," + controlId;

        String sql = "select totalTxControl.name countryName, totalTxControl.counId,  sum(totalTxControl.totalControlId) totalControlId "
                + " from ("
                + "        SELECT control_ids, region.id counId, region.name, count( region.name ) totalControlId"
                + "        FROM proactive_transaction_CND ptcnd"
                + "        JOIN proactive_transaction pt ON ( pt.id = ptcnd.proactive_transaction_id )"
                + "        JOIN proactive_project pp ON ( pp.id = pt.proactive_project_id )"
                + "        JOIN region ON ( region.id = pp.region_id )"
                + "        WHERE control_ids = ?"
                + "        OR control_ids LIKE ?"
                + "        OR control_ids LIKE ?"
                + "        OR control_ids LIKE ?"
                + "        GROUP BY region.name"
                + "        UNION ALL"
                + "        SELECT control_ids, region.id counId, region.name, count( region.name ) totalControlId"
                + "        FROM reactive_transaction_CND rtcnd"
                + "        JOIN reactive_transaction rt ON ( rt.id = rtcnd.reactive_transaction_id )"
                + "        JOIN reactive_project rp ON ( rp.id = rt.reactive_project_id )"
                + "        JOIN region ON ( region.id = rp.region_id )"
                + "        WHERE control_ids = ?"
                + "        OR control_ids LIKE ?"
                + "        OR control_ids LIKE ?"
                + "        OR control_ids LIKE ?"
                + "        GROUP BY region.name"
                + "     ) totalTxControl"
                + "  group by totalTxControl.name";

        List paramList = new ArrayList();
        paramList.add(controlId);
        paramList.add(secondCondition);
        paramList.add(thirdCondition);
        paramList.add(fourthCondition);

        paramList.add(controlId);
        paramList.add(secondCondition);
        paramList.add(thirdCondition);
        paramList.add(fourthCondition);


        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }


    public List getProactiveTransactionListByRegionAndControlId(long regionId, String controlId) {

        String secondCondition = controlId + ",%";
        String thirdCondition = "%," + controlId + ",%";
        String fourthCondition = "%," + controlId;

        String query = " SELECT 0 project, pt.id , pp.id projectId, pp.transaction_type transactionType, trx.id trxId, trx.created createdDate,trx.amount, trx.transaction_date trxDate, region.name regionName, ptcnd.decision " +
                " FROM proactive_transaction_CND ptcnd " +
                " JOIN proactive_transaction pt ON ( pt.id = ptcnd.proactive_transaction_id ) " +
                " JOIN proactive_project pp ON ( pp.id = pt.proactive_project_id )" +
                " JOIN region ON ( region.id = pp.region_id ) " +
                " JOIN transaction trx ON(pt.transaction_id = trx.id)" +
                " WHERE region.id = ? " +
                " AND ( " +
                " control_ids = ? " +
                " OR control_ids LIKE ? " +
                " OR control_ids LIKE ? " +
                " OR control_ids LIKE ? " +
                " ) " +
                " UNION ALL " +
                " SELECT 1 project, rt.id , rp.id projectId, rp.transaction_type transactionType,trx.id trxId, trx.created createdDate,trx.amount amount,trx.transaction_date trxDate, region.name regionName, rtcnd.decision decision" +
                " FROM reactive_transaction_CND rtcnd " +
                " JOIN reactive_transaction rt ON ( rt.id = rtcnd.reactive_transaction_id ) " +
                " JOIN reactive_project rp ON ( rp.id = rt.reactive_project_id ) " +
                " JOIN region ON ( region.id = rp.region_id ) " +
                " JOIN transaction trx ON(rt.transaction_id = trx.id) " +
                " WHERE region.id = ? " +
                " AND ( " +
                " control_ids = ? " +
                " OR control_ids LIKE ? " +
                " OR control_ids LIKE ? " +
                " OR control_ids LIKE ? " +
                " ) ";


        List paramList = new ArrayList();
        paramList.add(regionId);
        paramList.add(controlId);
        paramList.add(secondCondition);
        paramList.add(thirdCondition);
        paramList.add(fourthCondition);

        paramList.add(regionId);
        paramList.add(controlId);
        paramList.add(secondCondition);
        paramList.add(thirdCondition);
        paramList.add(fourthCondition);
        return jdbcTemplate.queryForList(query, paramList.toArray());
    }


    public List getDiscriminatorCustInfoByTransactionId(long transactionId) {
     /*   String sql = " SELECT perInfo.* "
                + " FROM "
                + " transaction tx"
                + " LEFT JOIN person_information perInfo ON (perInfo.id = tx.customer_FK)"
                + " WHERE tx.id = ?" +
                " AND tx.customer_FK IS NOT NULL";*/
        String sql =  "SELECT cml.* "
                + " FROM "
                + " transaction tx "
                + " JOIN customer_master_ledger cml ON(tx.customer_master_ledger_FK = cml.id)"
                + " WHERE tx.id = ?";

        List paramList = new ArrayList();
        paramList.add(transactionId);
        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public List getDiscriminatorVendorInfoByTransactionId(long transactionId) {
       /* String sql = " SELECT perInfo.* "
                + " FROM "
                + " transaction tx"
                + " LEFT JOIN person_information perInfo ON (perInfo.id = tx.vendor_FK)"
                + " WHERE tx.id = ? "
                + " AND tx.vendor_FK IS NOT NULL";*/
        String sql = "SELECT vml.* "
                + " FROM "
                + " transaction tx "
                + " JOIN vendor_master_ledger vml ON(tx.vendor_master_ledger_FK = vml.id)"
                + " WHERE tx.id = ?";

        List paramList = new ArrayList();
        paramList.add(transactionId);
        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public List getDiscriminatorEmployInfoByTransactionId(long transactionId) {
       /* String sql = " SELECT perInfo.* "
                + " FROM "
                + " transaction tx"
                + " LEFT JOIN person_information perInfo ON (perInfo.id = tx.employee_FK)"
                + " WHERE tx.id = ?"
                + " AND tx.employee_FK IS NOT NULL";*/
 String sql = "SELECT eml.* "
         + " FROM "
         + " transaction tx "
         + " JOIN employee_master_ledger eml ON(tx.employee_master_ledger_FK = eml.id)"
         + " WHERE tx.id = ?";;


        List paramList = new ArrayList();
        paramList.add(transactionId);
        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public List getAdditionalInfoListByTransactionId(long transactionId) {
        String sql = "  SELECT txli.headName Header, txli.value value, txli.head_id AS headerId"
                + " FROM "
                + " transaction tx"
                + " JOIN transaction_li txli ON ( txli.transaction_id = tx.id )"
//                + " JOIN transaction_li_label txlilable ON ( txli.transaction_li_label_id = txlilable.id )"
                + " WHERE tx.id = ?";

        List paramList = new ArrayList();
        paramList.add(transactionId);
        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public void deleteSupportingDocumentByProactiveProjectId(Long proactiveTransactionId) {
        logger.debug("proactive supporting document is deleting, proactive transaction id: " + proactiveTransactionId);
        jdbcTemplate.execute("DELETE FROM proactive_transaction_supporting_document WHERE proactive_transaction_id =" + proactiveTransactionId);

    }

    public List getSuspiciousTrxList(List trxIds, Date fromDate, Date toDate) {

//        if(Utils.isEmpty(trxIds))return new ArrayList();
        String sql = " SELECT DISTINCT trx.transaction_id transactionId "
                + "FROM suspicious_transaction trx "
                + "WHERE trx.transaction_id in ( SELECT tx.id id FROM  transaction tx " +
                " WHERE tx.created >= ? AND tx.created <= ?)";


        List paramList = new ArrayList();
        paramList.add(fromDate);
        paramList.add(toDate);
        return jdbcTemplate.queryForList(sql, paramList.toArray());

    }

   /* public List getSuspiciousTrxList(long ruleId, String trxIds, String weekEndOrHoliDay) {

        if(Utils.isEmpty(trxIds))return new ArrayList();

        String sql = "SELECT DISTINCT( ledger.transactionId ) transactionId "
                + "FROM ( "
                + "SELECT apl.transaction_fk  transactionId, apl.transaction_date trxDate "
                + "FROM accounts_payable_ledger_information apl "
                + "UNION "
                + "SELECT arl.transaction_fk  transactionId , arl.transaction_date trxDate "
                + "FROM accounts_receivable_ledger_information arl "
                + "UNION "
                + " SELECT gl.transaction_fk  transactionId , gl.transaction_date trxDate "
                + "FROM general_ledger_information gl "
                + ") ledger"
                + " WHERE ledger.trxDate IN ( "+ weekEndOrHoliDay +" ) "
                + " AND ledger.transactionId IN ("+ trxIds +")";

        return jdbcTemplate.queryForList(sql);
    }
*/
    public String getTrxIdsBetween(Date fromDate, Date toDate) {
        String sql = "  SELECT GROUP_CONCAT(tx.id) id "
                + " FROM "
                + " transaction tx"
                + " WHERE tx.created >= ? AND tx.created <= ?";

        List paramList = new ArrayList();
        paramList.add(fromDate);
        paramList.add(toDate);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if (list != null && list.size() > 0){
            Map map = (Map) list.get(0);
            return (String)map.get("id");
        }
        return null;
    }
    public List<Long> getTrxIdListBetween(Date fromDate, Date toDate) {
        String sql = " SELECT tx.id id "
                + " FROM "
                + " transaction tx"
                + " WHERE tx.created >= ? AND tx.created <= ?";

        List paramList = new ArrayList();
        paramList.add(fromDate);
        paramList.add(toDate);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if (list != null && list.size() > 0){
            return list;
        }
        return null;
    }

    public List getRealTimeTransactionList(RealTimeProject realTimeProject) {
        String sql = "SELECT GROUP_CONCAT(strx.rule_code) rule_code,rtpt.id,trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount, rtpCND.decision,trx.id trxId, trx.approver, trx.approve_date "
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + " LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)"
                + (realTimeProject.getId() > 0 ? " WHERE rtp.id = ? " : "")
                + " GROUP BY rtpt.id";

        List paramList = new ArrayList();
        if(realTimeProject.getId() > 0)
        paramList.add(realTimeProject.getId());


        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public List getRealTimeTransactionListFurtherAction(RealTimeProject realTimeProject) {
        String sql = "SELECT GROUP_CONCAT(strx.rule_code) rule_code,rtpt.id,trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount, rtpCND.decision,trx.id trxId, trx.approver, trx.approve_date "
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + " LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)"
                + (realTimeProject.getId() > 0 ? " WHERE rtp.id = ? AND rtpCND.decision = 'Further Action Required'" : "")
                + " GROUP BY rtpt.id";

        List paramList = new ArrayList();
        if(realTimeProject.getId() > 0)
        paramList.add(realTimeProject.getId());


        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public boolean isUserIdExist(long userId){
        String sql = "SELECT user.id userId"
                + " FROM"
                + " user "
                + " WHERE user.id = ?";
        List paramList = new ArrayList();
        paramList.add(userId);

        List userList = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(userList != null && userList.size() > 0)
            return true;
        else
            return false;
    }

    public List isAllRealTimeTransactionCoded(long realTimeProjectId) {

        String sql = "SELECT rttr.id realTimeTxId"
                + " FROM "
                + " real_time_transaction rttr "
                + " LEFT JOIN real_time_transaction_CND rtpCND ON(rtpCND.real_time_transaction_id = rttr.id) "
                +"  LEFT  JOIN real_time_project rtp  ON ( rtp.id = rttr.real_time_project_id ) "
                +"  LEFT  JOIN real_time_transaction_comment rttc  ON ( rttr.id = rttc.real_time_transaction_id ) "
                +"  WHERE rtp.id = ?" +
                "   AND( (rtpCND.control_Ids IS NULL OR rtpCND.control_Ids  LIKE ? )" +
                "          OR ( ((rtpCND.control_Ids  LIKE ? OR rtpCND.control_Ids  LIKE ? ) AND rtpCND.control_comment = ?))" +
                "          OR (rtpCND.decision IS NULL OR rtpCND.decision = ?)" +
                "          OR (rttc.comment IS NULL OR  rttc.comment = ?))";


        List paramList = new ArrayList();
        paramList.add(realTimeProjectId);
        paramList.add("%:0%");                 // 0 for Empty selection
        paramList.add("%:2%");                 // 2 for NO selection
        paramList.add("%:4%");                 // 4 for See Comment selection
        paramList.add("");
        paramList.add("");
        paramList.add("");

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0)
            return list;
        else
            return null;

    }

    public List getTransactionList(GlobalTransactionSearch globalTransactionSearch){
        String query = " SELECT 0 project, pi_customer.discriminator_value customer,region.name regionName, "
                       + "pi_vendor.discriminator_value vendor, "
                       + "pi_employee.discriminator_value empl, "
                       + "pt.id proactiveTrx_id , "
                       + "pp.id projectId, "
                       + "pp.transaction_type transactionType, "
                       + "trx.id trxId, trx.created createdDate, "
                       + "trx.amount, trx.transaction_date trxDate, "
                       + "pi_customer.name customerName, "
                       + "pi_vendor.name vendorName, "
                       + "trx.transaction_module trxModule, "
                       + "pi_employee.name employeeName "
                       + "FROM transaction trx "
                       + "JOIN proactive_transaction pt ON ( pt.transaction_id = trx.id) "
                       + "JOIN proactive_project pp ON ( pp.id = pt.proactive_project_id ) "
                       + "LEFT JOIN region region ON ( pp.region_id = region.id ) "
                       + "LEFT JOIN  person_information pi_customer ON ( pi_customer.id = trx.customer_FK ) "
                       + "LEFT JOIN  person_information pi_vendor ON ( pi_vendor.id = trx.vendor_FK ) "
                       + "LEFT JOIN  person_information pi_employee ON ( pi_employee.id = trx.employee_FK ) "
                       + (globalTransactionSearch.getTransactionId()!=null ? " WHERE trx.id = ? " : " WHERE trx.id > 0 ")
                       + (globalTransactionSearch.getAmount() != null ? " AND trx.amount = ? " : " ")
                       + (!Utils.isEmpty(globalTransactionSearch.getTransactionType())  ? " AND pp.transaction_type = ? " : " ")
                       + (globalTransactionSearch.getTransactionDate() != null  ? " AND trx.transaction_date = ? " : " ")
                       + (!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty()) ?
                             " AND (pi_customer.name = ? OR pi_vendor.name = ? OR pi_employee.name = ? ) " : " ")

                       + "UNION ALL "
                       + "SELECT 1 project, pi_customer.discriminator_value customer,region.name regionName, "
                       + "pi_vendor.discriminator_value vendor, "
                       + "pi_employee.discriminator_value empl, "
                       + "rt.id proactiveTrx_id , "
                       + "rp.id projectId, "
                       + "rp.transaction_type transactionType, "
                       + "trx.id trxId, trx.created createdDate, "
                       + "trx.amount, trx.transaction_date trxDate, "
                       + "pi_customer.name customerName, "
                       + "pi_vendor.name vendorName, "
                       + "trx.transaction_module trxModule, "
                       + "pi_employee.name employeeName "
                       + "FROM transaction trx "
                       + "JOIN reactive_transaction rt ON ( rt.transaction_id = trx.id) "
                       + "JOIN reactive_project rp ON ( rp.id = rt.reactive_project_id ) "
                       + "LEFT JOIN region region ON ( rp.region_id = region.id ) "
                       + "LEFT JOIN  person_information pi_customer ON ( pi_customer.id = trx.customer_FK ) "
                       + "LEFT JOIN  person_information pi_vendor ON ( pi_vendor.id = trx.vendor_FK ) "
                       + "LEFT JOIN  person_information pi_employee ON ( pi_employee.id = trx.employee_FK ) "
                       + (globalTransactionSearch.getTransactionId() !=null ? " WHERE trx.id = ? " : " WHERE trx.id > 0 ")
                       + (globalTransactionSearch.getAmount()!= null ? " AND trx.amount = ? " : " ")
                       + (!Utils.isEmpty(globalTransactionSearch.getTransactionType())  ? " AND rp.transaction_type = ? " : " ")
                       + (globalTransactionSearch.getTransactionDate() != null  ? " AND trx.transaction_date = ? " : " ")
                       + (!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty()) ?
                             " AND (pi_customer.name = ? OR pi_vendor.name = ? OR pi_employee.name = ? ) " : " ")
                       + "UNION ALL "
                       + "SELECT 2 project, pi_customer.discriminator_value customer,0 regionName, "
                       + "pi_vendor.discriminator_value vendor, "
                       + "pi_employee.discriminator_value empl, "
                       + "rtt.id proactiveTrx_id , "
                       + "rtp.id projectId, "
                       + "trx.transaction_type transactionType, "
                       + "trx.id trxId, trx.created createdDate, "
                       + "trx.amount, trx.transaction_date trxDate, "
                       + "pi_customer.name customerName, "
                       + "pi_vendor.name vendorName, "
                       + "trx.transaction_module trxModule, "
                       + "pi_employee.name employeeName "
                       + "FROM transaction trx "
                       + "JOIN real_time_transaction rtt ON ( rtt.transaction_id = trx.id) "
                       + "JOIN real_time_project rtp ON ( rtp.id = rtt.real_time_project_id ) "
                       + "LEFT JOIN  person_information pi_customer ON ( pi_customer.id = trx.customer_FK ) "
                       + "LEFT JOIN  person_information pi_vendor ON ( pi_vendor.id = trx.vendor_FK ) "
                       + "LEFT JOIN  person_information pi_employee ON ( pi_employee.id = trx.employee_FK ) "
                       + (globalTransactionSearch.getTransactionId()!=null ? " WHERE trx.id = ? " : " WHERE trx.id > 0 ")
                       + (globalTransactionSearch.getAmount() != null ? " AND trx.amount = ? " : " ")
                       + (!Utils.isEmpty(globalTransactionSearch.getTransactionType())  ? " AND trx.transaction_type = ? " : " ")
                       + (globalTransactionSearch.getTransactionDate() != null  ? " AND trx.transaction_date = ? " : " ")
                       + (!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty()) ?
                             " AND (pi_customer.name = ? OR pi_vendor.name = ? OR pi_employee.name = ? )" : " ");


        List paramList = new ArrayList();
        if(globalTransactionSearch.getTransactionId() != null)
            paramList.add(globalTransactionSearch.getTransactionId());

        if(globalTransactionSearch.getAmount() != null)
            paramList.add(globalTransactionSearch.getAmount());

        if(!Utils.isEmpty(globalTransactionSearch.getTransactionType()))
            paramList.add(globalTransactionSearch.getTransactionType());

        if(globalTransactionSearch.getTransactionDate() != null)
            paramList.add(globalTransactionSearch.getTransactionDate());

        if(!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty())){
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
        }

        if(globalTransactionSearch.getTransactionId() != null)
            paramList.add(globalTransactionSearch.getTransactionId());

        if(globalTransactionSearch.getAmount() != null)
            paramList.add(globalTransactionSearch.getAmount());

        if(!Utils.isEmpty(globalTransactionSearch.getTransactionType()))
            paramList.add(globalTransactionSearch.getTransactionType());

        if(globalTransactionSearch.getTransactionDate() != null)
            paramList.add(globalTransactionSearch.getTransactionDate());

        if(!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty())){
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
        }

        if(globalTransactionSearch.getTransactionId() != null)
            paramList.add(globalTransactionSearch.getTransactionId());

        if(globalTransactionSearch.getAmount() != null)
            paramList.add(globalTransactionSearch.getAmount());

        if(!Utils.isEmpty(globalTransactionSearch.getTransactionType()))
            paramList.add(globalTransactionSearch.getTransactionType());

        if(globalTransactionSearch.getTransactionDate() != null)
            paramList.add(globalTransactionSearch.getTransactionDate());

        if(!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty())){
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
            paramList.add(globalTransactionSearch.getNameOfThirdParty());
        }

        return jdbcTemplate.queryForList(query, paramList.toArray());
    }

    public List<Object> getPartialDataList(int page, int rp,  String qtype, String query, String sortname, String sortorder,String className) {
        if(qtype.equals("holiday_date") || qtype.equals("entry_time")){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        int start = (page - 1)*rp ;
        String sql = "SELECT * "
                + " FROM " + className;
                if(!Utils.isEmpty(query)) {
                    sql +=  " WHERE "+ qtype+" LIKE ?  LIMIT ?, ? ";
                } else {
                    sql +=  " ORDER BY " + sortname + " "+ sortorder +" LIMIT ?, ? ";
                }

        List paramList = new ArrayList();
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if (list != null && list.size() > 0)
            return list;
        return null;
    }
    public List<Object> getRealTimeTrxPartialDataList(int page, int rp,  String qtype, String query, String sortname, String sortorder) {
        String sql = "";
        int start = (page - 1)*rp ;
        if (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())) {
            sql = "select DISTINCT rtproject.id, rtproject.* , reviewTable.reviewed , count(rt.id) NoOfTransaction " +
                    "from real_time_project rtproject "
                    + "   JOIN  real_time_transaction rt ON (rt.real_time_project_id = rtproject.id)"
                    + "   JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id)" +
                    " LEFT JOIN   (" +
                    "                SELECT  retipr.id rid, COUNT(retitx.ia_manager_reviewed) reviewed " +
                    "                FROM real_time_project retipr " +
                    "                LEFT JOIN real_time_transaction retitx ON  (retitx.real_time_project_id = retipr.id) " +
                    "                JOIN real_time_transaction_CND innerCnd ON(innerCnd.real_time_transaction_id = retitx.id)" +
                    "                WHERE  retitx.ia_manager_reviewed = 1 " +
                    "                AND  innerCnd.decision = 'Further Action Required'" +
                    "                GROUP BY retipr.id " +
                    "              )reviewTable ON (rtproject.id = reviewTable.rid)"
                    + " WHERE rtcnd.decision = 'Further Action Required' " +
                    (!Utils.isEmpty(query)?  " AND "+qtype+" LIKE ? " : "")+
                    "GROUP BY rtproject.id ";

        } else if (Utils.getLoggedUserRoleName().equals(Role.LEGAL.getLabel())) {
            sql = "select DISTINCT rtproject.id, rtproject.* , reviewTable.reviewed , count(rt.id) NoOfTransaction " +
                    "from real_time_project rtproject "
                    + "   JOIN  real_time_transaction rt ON (rt.real_time_project_id = rtproject.id)"
                    + "   JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id)" +
                    " LEFT JOIN   (" +
                    "                SELECT  retipr.id rid, COUNT(retitx.legal_reviewed) reviewed " +
                    "                FROM real_time_project retipr " +
                    "                LEFT JOIN real_time_transaction retitx ON  (retitx.real_time_project_id = retipr.id) " +
                    "                JOIN real_time_transaction_CND innerCnd ON(innerCnd.real_time_transaction_id = retitx.id)" +
                    "                WHERE  retitx.legal_reviewed = 1 " +
                    "                AND  innerCnd.decision = 'Further Action Required'" +
                    "                GROUP BY retipr.id " +
                    "              )reviewTable ON (rtproject.id = reviewTable.rid)"
                    + " WHERE rtcnd.decision = 'Further Action Required' AND rt.ia_manager_reviewed = 1 "  +
                    (!Utils.isEmpty(query)?  " AND "+qtype+" LIKE ? " : "")+
                    "GROUP BY rtproject.id ";

        } else if (Utils.getLoggedUserRoleName().equals(Role.ADMIN.getLabel())
                || Utils.getLoggedUserRoleName().equals(Role.EMPLOYEE.getLabel())) {
            sql = "select  rtproject.*,innerTable.outstanding_transaction, reviewTable.reviewed, count(rttx.id) NoOfTransaction " +
                    "from real_time_project rtproject "
                    +"LEFT JOIN   (" +
                    "    SELECT  retipr.id rid, COUNT(retitx.admin_reviewed) reviewed" +
                    "      FROM real_time_project retipr " +
                    "      LEFT JOIN real_time_transaction retitx ON  (retitx.real_time_project_id = retipr.id)" +
                    "    WHERE  retitx.admin_reviewed = 1 " +
                    "    GROUP BY retipr.id " +
                    "    )reviewTable ON (rtproject.id = reviewTable.rid)"
                    + "LEFT JOIN( "
                    +       "SELECT rtp.id,COUNT(rtp.id) outstanding_transaction FROM real_time_project rtp "
                    +       "JOIN real_time_transaction rt ON(rt.real_time_project_id = rtp.id) "
                    +       "LEFT JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id) "
                    +       "WHERE rtcnd.decision LIKE '%More information required%' "
                    +       "GROUP BY rtp.id "
                    +       ") innerTable ON(rtproject.id = innerTable.id) " +
                    "  LEFT JOIN real_time_transaction rttx ON (rttx.real_time_project_id = rtproject.id)" +
                    " WHERE 1=1 "+
                    (!Utils.isEmpty(query)?  " AND "+qtype+" LIKE ? " : "")+
                    "GROUP BY rtproject.id "    ;
        }  else if(Utils.getLoggedUserRoleName().equals(Role.IA_ANALYST.getLabel())) {
            sql = "select  rtproject.*,innerTable.outstanding_transaction, reviewTable.reviewed, count(rttx.id) NoOfTransaction " +
                    "from real_time_project rtproject "
                    +"LEFT JOIN   (" +
                    "    SELECT  retipr.id rid, COUNT(retitx.ia_analyst_reviewed) reviewed " +
                    "      FROM real_time_project retipr " +
                    "      LEFT JOIN real_time_transaction retitx ON  (retitx.real_time_project_id = retipr.id) " +
                    "    WHERE  retitx.ia_analyst_reviewed = 1 " +
                    "    GROUP BY retipr.id " +
                    "    )reviewTable ON (rtproject.id = reviewTable.rid) "
                    + "LEFT JOIN( "
                    +       "SELECT rtp.id,COUNT(rtp.id) outstanding_transaction FROM real_time_project rtp "
                    +       "JOIN real_time_transaction rt ON(rt.real_time_project_id = rtp.id) "
                    +       "LEFT JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id) "
                    +       "WHERE rtcnd.decision LIKE '%More information required%' "
                    +       "GROUP BY rtp.id "
                    +       ") innerTable ON(rtproject.id = innerTable.id) " +
                    "  LEFT JOIN real_time_transaction rttx ON (rttx.real_time_project_id = rtproject.id) " +
                    "  WHERE rtproject.assign_to = ? AND (select role from user where user_name = ?) = ? " +
                    (!Utils.isEmpty(query)?  " AND "+qtype+" LIKE ?" : "")+
                    "GROUP BY rtproject.id "    ;
        }
        List paramList = new ArrayList();
        if(Utils.getLoggedUserRoleName().equals(Role.IA_ANALYST.getLabel())) {
            paramList.add(Utils.getLoggedUserName());
            paramList.add(Utils.getLoggedUserName());
            paramList.add(Role.IA_ANALYST.getLabel());
        }
        if(!Utils.isEmpty(query)) {
           sql +=  " ORDER BY " + sortname + " "+ sortorder;
        }
        sql +=  " LIMIT ?, ? ";
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public int getTotalRealTimeProject(String qtype, String query ){
        String sql = "";

        if (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())) {
            sql = "select COUNT(DISTINCT rtproject.id) totalSum"
                    + " from real_time_project rtproject "
                    + "   JOIN  real_time_transaction rt ON (rt.real_time_project_id = rtproject.id)"
                    + "   JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id)"
                    + " WHERE rtcnd.decision = 'Further Action Required' ";

        } else if (Utils.getLoggedUserRoleName().equals(Role.LEGAL.getLabel())) {
            sql = "select COUNT(DISTINCT rtproject.id) totalSum"
                    + " from real_time_project rtproject "
                    + "   JOIN  real_time_transaction rt ON (rt.real_time_project_id = rtproject.id)"
                    + "   JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id)"
                    + " WHERE rtcnd.decision = 'Further Action Required' AND rt.legal_reviewed = 1 ";

        } else if (Utils.getLoggedUserRoleName().equals(Role.ADMIN.getLabel())
                || Utils.getLoggedUserRoleName().equals(Role.EMPLOYEE.getLabel())) {
            sql = "select COUNT(rtproject.id) totalSum "
                     +" from real_time_project rtproject "
                    + "LEFT JOIN( "
                    +       "SELECT rtp.id,COUNT(rtp.id) outstanding_transaction FROM real_time_project rtp "
                    +       "JOIN real_time_transaction rt ON(rt.real_time_project_id = rtp.id) "
                    +       "LEFT JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id) "
                    +       "WHERE rtcnd.decision LIKE '%More information required%' "
                    +       "GROUP BY rtp.id "
                    +       ") innerTable ON(rtproject.id = innerTable.id) WHERE 1=1 ";
        } else if(Utils.getLoggedUserRoleName().equals(Role.IA_ANALYST.getLabel())) {
            sql = "select COUNT(rtproject.id) totalSum "
                    +" from real_time_project rtproject "
                    + "LEFT JOIN( "
                    +       "SELECT rtp.id,COUNT(rtp.id) outstanding_transaction FROM real_time_project rtp "
                    +       "JOIN real_time_transaction rt ON(rt.real_time_project_id = rtp.id) "
                    +       "LEFT JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id) "
                    +       "WHERE rtcnd.decision LIKE '%More information required%' "
                    +       "GROUP BY rtp.id "
                    +       ") innerTable ON(rtproject.id = innerTable.id) " +
                    "  WHERE rtproject.assign_to = ? AND (select role from user where user_name = ?) = ?" ;
        }

        if(!Utils.isEmpty(query)) {
            sql +=  " AND "+ qtype+" LIKE ? ";
        } else {
        }
        List paramList = new ArrayList();
        if(Utils.getLoggedUserRoleName().equals(Role.IA_ANALYST.getLabel())) {
            paramList.add(Utils.getLoggedUserName());
            paramList.add(Utils.getLoggedUserName());
            paramList.add(Role.IA_ANALYST.getLabel());
        }
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalSum").toString());
        }
        return 0;
    }

    public  List getPartialProactiveProjectDataList( int page, int rp){
        int start = (page - 1)*rp ;
        String sql = "SELECT pp.id proactiveProjectId, pp.created_by author, pp.from_date,pp.to_date,region.name regionName,pp.created "
                + "FROM proactive_project pp "
                + "JOIN  region region ON(region.id = pp.region_id) "
                + "LIMIT ?, ?";

        List paramList = new ArrayList();
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public  List getPartialReactiveProjectDataList( int page, int rp){
        int start = (page - 1)*rp ;
        String sql = "SELECT rp.id reactiveProjectId,rp.project_name, rp.amount, rp.payment_date,rp.transaction_type,region.name regionName "
                + "FROM reactive_project rp "
                + "JOIN  region region ON(region.id = rp.region_id) "
                + "LIMIT ?, ?";

        List paramList = new ArrayList();
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public List getPartialProactiveBlockWeightList(long regionId, int toDate, int fromDate, int page, int rp){
        int start = (page - 1)*rp ;
        String sql = "SELECT * FROM proactive_block_weight pbw"
                + " WHERE pbw.region_id = ? "
                + " AND pbw.date >= ? "
                + " AND pbw.date <= ? "
                + "LIMIT ?, ?";

        List param = new ArrayList();
        param.add(regionId);
        param.add(fromDate);
        param.add(toDate);
        param.add(start);
        param.add(rp);
        List list = jdbcTemplate.queryForList(sql, param.toArray());

        if (list != null && list.size() > 0)
            return list;
        return null;


    }

    public List getPartialTransactionList(GlobalTransactionSearch globalTransactionSearch,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        int start = (page - 1)*rp ;
        if(qtype.equals("trx.transaction_date")){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        String sql = "SELECT trx.transaction_type transactionType, "
                + "trx.id trxId, trx.created createdDate, "
                + "FORMAT(trx.amount,2) amount, trx.transaction_date trxDate, "
//                + "cml.entity_name customerName, "
//                + "vml.entity_name vendorName, "
                + "trx.transaction_module trxModule, "
//                + "eml.entity_name employeeName "
                + "CONCAT_WS(',',cml.entity_name,vml.entity_name,eml.entity_name) customerName, "
                + "rtp.id realTimeProjectId, "
                + "trx.approver "
                + "FROM transaction trx "
                + "LEFT JOIN  customer_master_ledger cml ON ( cml.id = trx.customer_master_ledger_FK) "
                + "LEFT JOIN  vendor_master_ledger vml ON ( vml.id = trx.vendor_master_ledger_FK) "
                + "LEFT JOIN  employee_master_ledger eml ON ( eml.id = trx.employee_master_ledger_FK) "
                + "LEFT JOIN  real_time_transaction rtt ON ( rtt.transaction_id = trx.id) "
                + "LEFT JOIN real_time_project rtp ON(rtp.id = rtt.real_time_project_id) "
                + (globalTransactionSearch.getTransactionId()!=null ? " WHERE trx.id = ? " : " WHERE trx.id > 0 ")
                + (globalTransactionSearch.getAmount() != null ? " AND trx.amount = ? " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getTransactionType())  ? " AND trx.transaction_type = ? " : " ")
                + (globalTransactionSearch.getTransactionDate() != null  ? " AND trx.transaction_date = ? " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty()) ?
                  " AND (cml.entity_name LIKE ? OR vml.entity_name LIKE ? OR eml.entity_name LIKE ? ) " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getFreeText()) ?
                  " AND (cml.entity_name LIKE ? OR vml.entity_name LIKE ? OR eml.entity_name LIKE ? OR trx.id = ?  OR  trx.amount LIKE ? OR  trx.transaction_type LIKE ? OR  DATE_FORMAT(trx.transaction_date,\"%m/%d/%Y\") LIKE ?) " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getApprover()) ? " AND trx.approver LIKE ? " : " " )
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                //+ " ORDER BY trx.id ASC "
                + " ORDER BY " + sortname + " "+ sortorder
                + " LIMIT ?, ? " ;
        List paramList = new ArrayList();
        if(globalTransactionSearch.getTransactionId() != null)
            paramList.add(globalTransactionSearch.getTransactionId());

        if(globalTransactionSearch.getAmount() != null)
            paramList.add(globalTransactionSearch.getAmount());

        if(!Utils.isEmpty(globalTransactionSearch.getTransactionType()))
            paramList.add(globalTransactionSearch.getTransactionType());

        if(globalTransactionSearch.getTransactionDate() != null)
            paramList.add(globalTransactionSearch.getTransactionDate());

        if(!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty())){
            String nameOfthirdParty = "%"+globalTransactionSearch.getNameOfThirdParty().trim()+"%";
            paramList.add(nameOfthirdParty);
            paramList.add(nameOfthirdParty);
            paramList.add(nameOfthirdParty);
        }
        if(!Utils.isEmpty(globalTransactionSearch.getFreeText())){

            String freeText = "%"+globalTransactionSearch.getFreeText().trim()+"%";

            paramList.add(freeText);
            paramList.add(freeText);
            paramList.add(freeText);
            paramList.add(globalTransactionSearch.getFreeText());
            paramList.add(freeText);
            paramList.add(freeText);
            paramList.add(freeText);
        }
        if(!Utils.isEmpty(globalTransactionSearch.getApprover())){
            paramList.add("%"+globalTransactionSearch.getApprover()+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public int getTotalTransactionCount(GlobalTransactionSearch globalTransactionSearch){
        String query = "SELECT COUNT(trx.id) totalSum "
                + "FROM transaction trx "
                + "LEFT JOIN  customer_master_ledger cml ON ( cml.id = trx.customer_master_ledger_FK) "
                + "LEFT JOIN  vendor_master_ledger vml ON ( vml.id = trx.vendor_master_ledger_FK) "
                + "LEFT JOIN  employee_master_ledger eml ON ( eml.id = trx.employee_master_ledger_FK) "
                + (globalTransactionSearch.getTransactionId()!=null ? " WHERE trx.id = ? " : " WHERE trx.id > 0 ")
                + (globalTransactionSearch.getAmount() != null ? " AND trx.amount = ? " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getTransactionType())  ? " AND trx.transaction_type = ? " : " ")
                + (globalTransactionSearch.getTransactionDate() != null  ? " AND trx.transaction_date = ? " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty()) ?
                " AND (cml.entity_name LIKE ? OR vml.entity_name LIKE ? OR eml.entity_name LIKE ? ) " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getFreeText()) ?
                " AND (cml.entity_name LIKE ? OR vml.entity_name LIKE ? OR eml.entity_name LIKE ? OR trx.id = ?  OR  trx.amount LIKE ? OR  trx.transaction_type LIKE ? OR  DATE_FORMAT(trx.transaction_date,\"%m/%d/%Y\") LIKE ?) " : " ")
                + (!Utils.isEmpty(globalTransactionSearch.getApprover()) ? " AND trx.approver LIKE ? " : " " );

        List paramList = new ArrayList();
        if(globalTransactionSearch.getTransactionId() != null)
            paramList.add(globalTransactionSearch.getTransactionId());

        if(globalTransactionSearch.getAmount() != null)
            paramList.add(globalTransactionSearch.getAmount());

        if(!Utils.isEmpty(globalTransactionSearch.getTransactionType()))
            paramList.add(globalTransactionSearch.getTransactionType());

        if(globalTransactionSearch.getTransactionDate() != null)
            paramList.add(globalTransactionSearch.getTransactionDate());

        if(!Utils.isEmpty(globalTransactionSearch.getNameOfThirdParty())){
            String nameOfthirdParty = "%"+globalTransactionSearch.getNameOfThirdParty().trim()+"%";
            paramList.add(nameOfthirdParty);
            paramList.add(nameOfthirdParty);
            paramList.add(nameOfthirdParty);

        }
        if(!Utils.isEmpty(globalTransactionSearch.getFreeText())){

            String freeText = "%"+globalTransactionSearch.getFreeText().trim()+"%";

            paramList.add(freeText);
            paramList.add(freeText);
            paramList.add(freeText);
            paramList.add(globalTransactionSearch.getFreeText());
            paramList.add(freeText);
            paramList.add(freeText);
            paramList.add(freeText);
        }
        if(!Utils.isEmpty(globalTransactionSearch.getApprover())){
            paramList.add("%"+globalTransactionSearch.getApprover()+"%");
        }

        List list = jdbcTemplate.queryForList(query, paramList.toArray());
        if (list != null && list.size() > 0)    {
            Map map = (Map)list.get(0);
            return ((Number)map.get("totalSum")).intValue();

        }
        return 0;
    }

    public List getRules(){
       String sql = "SELECT * FROM rule ";
        List list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0)
            return list;
        return null;


    }
    public void saveSuspiciousTxList(Rule rule, int jobId){
        String ruleQuery = "";
        String sql = "";

        ruleQuery = rule.getSelectClause();
        sql += " INSERT INTO suspicious_transaction(rule_id, rule_title,rule_code, rule_explanation, transaction_id, job_id) " + ruleQuery;
//        sql += ruleQuery;

        Map namedParameters = new HashMap();
        namedParameters.put("ruleId", rule.getId());
        namedParameters.put("ruleTitle", rule.getRuleTitle());
        namedParameters.put("ruleCode", rule.getRuleCode());
        namedParameters.put("jobId", jobId);
        namedParameters.put("ruleExp", rule.getRuleExplanation());

        namedParameterJdbcTemplate.update(sql, namedParameters);

    }
    public List getTransactionSummaryList(long realtimeProjectId) {

        String query = " select count(st.transaction_id) totalTransaction" +
                ", st.rule_title rule" +
                ",ru.id ruleId" +
                ",GROUP_CONCAT(st.transaction_id) transactionIds " +
                " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT " +
                "                              tx.id AS TransactionId " +
                "                            FROM real_time_transaction rtt " +
                "                            JOIN transaction tx ON (rtt.transaction_id = tx.id) " +
                "                            WHERE rtt.real_time_project_id = ?) " +
                " GROUP BY st.rule_id ";

        List param = new ArrayList();
        param.add(realtimeProjectId);

        List list = jdbcTemplate.queryForList(query, param.toArray() );
        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public List getColumnNameList(String tableName){
        String sql = "SHOW COLUMNS FROM " + tableName;
/*

        List param = new ArrayList();
        param.add(tableName);
*/

        List list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0)
            return list;
        return null;


    }
    public  boolean isTransactionSuspiciousWithSameRule(long txId, long ruleId, long executionHeaderId) {

        List list = jdbcTemplate.queryForList("");

        if (list != null && list.size() > 0)
            return true;
        return false;
    }
    public void deleteSuspiciousTransaction(int job) {
        String sql = " DELETE FROM suspicious_transaction WHERE job_id = ? " ;
        List param = new ArrayList();
        param.add(job);
        int row = jdbcTemplate.update(sql, param.toArray());
        logger.debug(row + " row(s) deleted.");
    }
    public int getLastJobId(){
        String sql = "SELECT MAX(job_id) FROM job";
        return jdbcTemplate.queryForInt(sql);
    }
    public List getTempLedgerData(String ledger) {
        if(Utils.isEmpty(ledger)) {
            return null;
        }
        String sql = " SELECT * FROM " + ledger + " " ;
        return  jdbcTemplate.queryForList(sql);
    }
    public void saveAccountsPaybleLedger(long txid, long apid, String VMFid){
        String sql =
                " INSERT INTO    accounts_payable_ledger_information (" +
//                " id  " +
                "  approver_name " +
                ", transaction_amount " +
                "  , document_date" +
                "  ,document_number " +
                "  ,contact_identification " +
                "  ,initiator_or_submitor_name " +
                "  ,manual_override " +
//                "  ,transaction_number " +
                "  ,transaction_narrative_description " +
                "  ,unit_of_measure_quantity " +
                "  ,unit_of_measure_cost " +
                "  ,transaction_fk " +
                " ) " +
                "  SELECT " +
//                "  "+txid +
                "    ApproverName " +
                "    ,TransAmount1 AS UnitCost " +
                "    ,STR_TO_DATE(DocDate, '%m/%d/%Y') as DocumentDate  " +
                "    ,DocNumber " +
                "    ,ContractIdentification " +
                "    ,InitiatorSubmitterName" +
                "    ,ManualOverride" +
//                "    ,TransNumber" +
                "    ,TransNarrative" +
                        "    ,UnitQty" +
                        "    ,SUBSTRING_INDEX(UnitCost, '$', -1) AS UnitCost" +

                "    ,  " +txid   +
                "    " +
                "  FROM APDetail1 ap where ap.id = ?";

        List param = new ArrayList();

        param.add(apid);
        jdbcTemplate.update(sql, param.toArray());

    }

    public long saveVendorMasterLedger(long txid, String vmId){
        String sql =
                " INSERT INTO   vendor_master_ledger (" +
//                        " id  " +
                        " entity_name " +
                        ", contactFirstName " +
                        ", contactLastName " +
                        ", entity_address " +
                       /* "  , city " +
                        "  ,state " +
                        "  ,zip_code " +*/
                        "  ,entity_status " +
//                        "  ,entity_type " +
//                        "  ,tin " +
                        "  ,bank_account_no " +
                        "  ,bank_account_location " +
                        "  ,bankRouting " +
//                        "  ,paymentMethod " +
//                        "  ,currency " +
                        "  ,vendor_id " +

//                        "  ,transaction_fk " +
                        " ) " +
                        "  SELECT " +
//                        "  ? AS VendorId" +
                        "    VendorName " +
                        "    , ContactFName " +
                        "    , ContactLName " +
                        "    , Address " +
                        /*"    , City " +
                        "    ,State " +
                        "    ,Zip " +*/
                        "    ,Status " +
//                        "    ,Type " +
//                        "    ,TIN " +
                        "    ,BankAccountNo" +
                        "    ,BankLocation" +
                        "    ,BankRouting" +
//                        "    ,PaymentMethod" +
//                        "    ,Currency" +
                        "    , ? AS VMFid" +
                        "    " +
                        "  FROM VMF1 vm where vm.VMFid = ? ";

        List param = new ArrayList();

//        param.add(txid);
        param.add(vmId);
//        param.add(txid);
        param.add(vmId);
        jdbcTemplate.update(sql, param.toArray());

        sql = "SELECT MAX(id) FROM vendor_master_ledger";
        return jdbcTemplate.queryForLong(sql);

    }

    public void saveAccountsReceiveAbleLedger(long txid, long arid){
        String sql =
                " INSERT INTO accounts_receivable_ledger_information (" +
//                        " id  " +
                        " approver_name " +
                        ", transaction_amount " +
                        "  , document_date" +
                        "  ,document_number " +
                        "  ,render_date " +
                        "  ,transaction_date " +
                        "  ,manual_override " +
                        "  ,transaction_narrative_description " +

                        "  ,unit_of_measure_quantity " +
                        "  ,unit_of_measure_cost " +
                        "  ,transaction_fk " +
                        " ) " +
                        "  SELECT " +
//                        "  "+txid +
                        "    Approver " +
                        "    , SUBSTRING_INDEX( REPLACE( TransAmt, ',', '' ), '$', -1) AS TransAmt " +
                        "    ,STR_TO_DATE(DocDt, '%m/%d/%Y') as DocDt  " +
                        "    ,DocID " +
                        "    ,STR_TO_DATE(RenderDt, '%m/%d/%Y') as RenderDt " +
                        "    ,STR_TO_DATE(TransDt, '%m/%d/%Y') as TransDt " +
                        "    ,ManualOverride" +
                        "    ,TransNarrative" +
                        "    ,UnitQty" +
                        "    ,SUBSTRING_INDEX(UnitPrice, '$', -1) AS UnitCost" +

                        "    ,  " +txid   +
                        "    " +
                        "  FROM ARDetail ar where ar.id = ? ";

        List param = new ArrayList();

        param.add(arid);
        jdbcTemplate.update(sql, param.toArray());

    }


    public long saveCustomerMasterLedger(long txid, long cmId){
        String sql =
                " INSERT INTO   customer_master_ledger (" +
//                        " id  " +
                        " first_name " +
                        ", last_name " +
                        ", entity_address " +
                        "  , entity_name " +
                        "  ,entity_status " +
                        "  ,customer_id " +
                        "  ,regionState " +
                        "  ,latLong " +
                        "  ,tin " +
                        "  ,title " +
                        "  ,bankDomicile " +
                        "  ,bankRouting " +
                        "  ,startDt " +
                        "  ,endDt " +
                        "  ,department " +
                        "  ,supervisor " +
                        "  ,salary " +
                        "  ,currency " +
                        "  ,gender " +
                        "  ,DOB " +
                        "  ,ethnicity " +
                        "  ,spendLimit " +
                        "  ,approvalLimit " +
                        "  ,businessUnit " +

                        " ) " +
                        "  SELECT " +
//                        "  "+txid +
                        "    FName " +
                        "    ,LName " +
                        "    , Address " +
                        "    , CONCAT(FName, \" \", LName) AS Name" +
                        "    ,Status " +
                        "    ,EMPid " +
                        "    ,RegionState" +
                        "    ,LatLong" +
                        "    ,TIN" +
                        "    ,Title" +
                        "    ,BankDomicile" +
                        "    ,BankRouting" +
                        "    ,StartDt" +
                        "    ,EndDt" +
                        "    ,Department" +
                        "    ,Supervisor" +
                        "    ,Salary" +
                        "    ,Currency" +
                        "    ,Gender" +
                        "    ,DOB" +
                        "    ,Ethnicity" +
                        "    ,SpendLimit" +
                        "    ,ApprovalLimit" +
                        "    ,BusinessUnit" +

                        "    " +
                        "  FROM EMF em where em.id = ? ";

        List param = new ArrayList();

//        param.add(cmId);
        param.add(cmId);
        jdbcTemplate.update(sql, param.toArray());

        sql = "SELECT MAX(id) FROM customer_master_ledger";
        return jdbcTemplate.queryForLong(sql);
    }

    public long saveEmployeeMasterLedger(long txid, String emId){
        String sql =
                " INSERT INTO   employee_master_ledger (" +

                        " first_name " +
                        ", last_name " +
                        ", employee_address " +
//                        "  , city " +
                        "  , country_name " +
                        "  , region_state " +
                        /*"  , lat_long " +*/
                        "  , title " +
                        "  , bank_name " +
                        "  , bank_routing " +
                        "  , start_date " +
                        "  , end_date " +
                        "  , department " +
                        "  , supervisor " +
                        "  , salary " +
                        "  , currency " +
                        "  , gender " +
                        "  , dob " +
                        /*"  , ethnicity " +*/
                        "  , spend_limit " +
                        "  , business_unit " +
//                        "  , employee_id " +
//                        "  ,state " +
//                        "  ,zip" +
                        "  ,status " +
//                        "  ,entity_type " +
                        /*"  ,tin " +*/
                        "  ,bank_account_no " +
                        "  ,employee_id " +
                        "  ,bank_domicile " +
                        "  ,approval_limit " +

//                        "  ,transaction_fk " +
                        " ) " +
                        "  SELECT " +
//                        "  "+txid +
                        "    FName " +
                        "    ,LName " +
                        "    , Address " +
//                        "    , City " +
                        "    , Country " +
                        "    , RegionState " +
                        /*"    , LatLong " +*/
                        "    , Title " +
                        "    , BankName " +
                        "    , BankRouting " +
                        "    , STR_TO_DATE(StartDt, '%m/%d/%Y') " +
                        "    , STR_TO_DATE(EndDt, '%m/%d/%Y') " +
                        "    , Department" +
                        "    , Supervisor" +
                        "    , Salary" +
                        "    , Currency" +
                        "    , Gender" +
                        "    , DOB" +
//                        "    , Ethnicity" +
                        "    , SpendLimit" +
                        "    , BusinessUnit" +
//                        "    , EMPid" +
//                        "    ,State " +
//                        "    ,Zip " +
                        "    ,Status " +
//                        "    ,Type " +
                        /*"    ,TIN " +*/
                        "    ,BankAccountNo" +
                        "    ,EMPid " +
                        "    ,BankDomicile" +
                        "    ,CAST(SUBSTRING_INDEX( REPLACE( ApprovalLimit, ',', '' ), '$', -1) AS DECIMAL(7)) AS   ApprovalLimit " +
//                        "    ,  " +txid   +
                        "    " +
                        "  FROM EMF1 em where em.EMPid = ? ";

        List param = new ArrayList();

//        param.add(emId);
        param.add(emId);
        jdbcTemplate.update(sql, param.toArray());

        sql = "SELECT MAX(id) FROM employee_master_ledger";
        return jdbcTemplate.queryForLong(sql);

    }
    public long saveVendorPersonInfo(long vmId, String vendor, long vmfid) {
        String sql =
                " INSERT INTO person_information (" +
                        " id  " +
                        ", name " +
                        ", address " +
                        "  ,city " +
                        "  ,state " +
                        "  ,zip_code " +
                        "  ,type " +
                       "  ,discriminator_value " +
                        "  ,bank_account_no " +
                        "  ,vendor_master_ledger_FK " +

                        "  ,bank_account_location " +
                        " ) " +
                        "  SELECT " +
                        "  ? AS personId" +
                        "    ,VendorName " +
                        "    ,Address " +
                        "    , City " +
                        "    ,State " +
                        "    ,Zip " +
                        "    ,Type " +
                        "    , ? " +
                        "    ,BankAccountNo" +
                        "    , ? " +

                        "    ,BankLocation" +
                        "    " +
                        "  FROM VMF vm where vm.id = ? ";


        List param = new ArrayList();
        long lastPersonId = getLastPersonId();
        param.add(lastPersonId+1);
        param.add(vendor);
        param.add(vmfid);
        param.add(vmId);
        jdbcTemplate.update(sql, param.toArray());
        if(lastPersonId < getLastPersonId()) {
            return getLastPersonId();
        }

        return 0;
    }

    public long saveCustomerPersonInfo(long cuId, String customer, long cmfid, String employee, long emfid ) {
        String sql =
                " INSERT INTO person_information (" +
                        " id  " +
                        ", name " +
                        ", address " +
                        "  ,city " +
                        "  ,gender " +
                        "  ,zip_code " +
                        "  ,country_name " +
                        "  ,discriminator_value " +
                        "  ,bank_account_no " +
                        "  ,customer_master_ledger_FK " +
                        "  ,employee_master_ledger_FK " +

                        "  ,bank_name " +
                        " ) " +
                        "  SELECT " +
                        "  ? AS personId" +
                        "    ,CONCAT(FName, \" \", LName)  AS Name " +
                        "    ,Address " +
                        "    , City " +
                        "    ,Gender " +
                        "    ,Zip " +
                        "    ,Country " +
                        "    , ? " +
                        "    ,BankAccountNo" +
                        "    ,? " +
                        "    ,? " +

                        "    ,BankName" +
                        "    " +
                        "  FROM EMF em where em.id = ? ";


        List param = new ArrayList();
        long lastPersonId = getLastPersonId();
        param.add(lastPersonId+1);
        param.add(customer);
        param.add(cmfid);
        param.add(emfid);
        param.add(cuId);
        jdbcTemplate.update(sql, param.toArray());
        if(lastPersonId < getLastPersonId()) {
            return getLastPersonId();
        }

        return 0;
    }

    public long saveEmployeePersonInfo(long vmId, String employee, long emfid) {
        String sql =
                " INSERT INTO person_information (" +
                        " id  " +
                        ", name " +
                        ", address " +
                        "  ,city " +
                        "  ,gender " +
                        "  ,zip_code " +
                        "  ,country_name " +
                        "  ,discriminator_value " +
                        "  ,bank_account_no " +
                        "  ,employee_master_ledger_FK " +

                        "  ,bank_name " +
                        " ) " +
                        "  SELECT " +
                        "  ? AS personId" +
                        "    ,CONCAT(FName, \" \", LName)  AS Name " +
                        "    ,Address " +
                        "    , City " +
                        "    ,Gender " +
                        "    ,Zip " +
                        "    ,Country " +
                        "    , ? " +
                        "    ,BankAccountNo" +
                        "    ,? " +

                        "    ,BankName" +
                        "    " +
                        "  FROM EMF em where em.id = ? ";


        List param = new ArrayList();
        long lastPersonId = getLastPersonId();
        param.add(lastPersonId+1);
        param.add(employee);
        param.add(emfid);
        param.add(vmId);
        jdbcTemplate.update(sql, param.toArray());
        if(lastPersonId < getLastPersonId()) {
            return getLastPersonId();
        }

        return 0;
    }


    public long getLastPersonId(){
        String sql = "SELECT MAX(id) FROM person_information";
        return jdbcTemplate.queryForLong(sql);
    }
    public void saveTransactionLi(long txId, String value, String header) {
        String sql =
                " INSERT INTO   transaction_li (" +
                        "  transaction_id " +
                        "  ,headName " +
                        "  ,value " +
                        " ) " +
                        "  VALUES( " +
                        "  ? " +
                        "    , ? " +
                        "    , ? " +
                        "  )  " ;
//                        "  FROM "+ledger+" where "+ledger+".id = ? ";

        List param = new ArrayList();

        param.add(txId);
        param.add(header);
        param.add(value);
//        param.add(ledgerId);

        jdbcTemplate.update(sql, param.toArray());

    }

   public void saveFundsDisbursementLedger(long txId, long id) {
        String sql =
                " INSERT INTO   fund_disbursement_ledger (" +
                        " contractIdentification " +
                        ", checkDate " +
                        "  , checkNo " +
                        "  ,initiatorOrSubmitterName " +
                        "  ,manualOverride " +
                        "  ,transactionNarrativeOrDescription " +
                        "  ,payeeId " +

                        "  ,transaction_fk " +
                        " ) " +
                        "  SELECT " +
                        "  ContractIdentification" +
                        "    , CheckDate " +
                        "    , CheckNo " +
                        "    , InitiatorSubmitterName " +
                        "    , ManualOverride " +
                        "    , TransNarrative " +
                        "    , PayeeID " +
                        "    , ? AS TransactionFK " +
                        "    " +
                        "  FROM FDDetail1 fd where fd.id = ? ";

        List param = new ArrayList();

        param.add(txId);
        param.add(id);

        jdbcTemplate.update(sql, param.toArray());
    }
    public Map getVendorMasterLedger(long vmId) {
        String sql = "SELECT * FROM VMF vm WHERE vm.id = ? ";

        List param = new ArrayList();
        param.add(vmId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            return (Map)list.get(0);
        }
        return null;
    }
    public Map getCustomerMasterLedger(long cmId) {
        String sql = "SELECT * FROM EMF em WHERE em.id = ? ";

        List param = new ArrayList();
        param.add(cmId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            return (Map)list.get(0);
        }
        return null;
    }

    public  Map  getFundsDisbursementLedger(long arId)  {
        String sql = "SELECT * FROM FDDetail fd WHERE fd.Transid = ? ";

        List param = new ArrayList();
        param.add(arId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            return (Map)list.get(0);
        }
        return null;
    }
    public  Map  getOrdersEntityLedger(long apId)  {
        String sql = "SELECT * FROM OEDetail fd WHERE fd.TransID = ? ";

        List param = new ArrayList();
        param.add(apId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            return (Map)list.get(0);
        }
        return null;
    }

    public long getLastTransactionId(){
        String sql = "SELECT MAX(id) FROM transaction";
        return jdbcTemplate.queryForLong(sql);
    }

    public void executeSql(StringBuffer sql){
         jdbcTemplate.update(sql.toString());
    }
  public void saveIASql(StringBuffer sql, long txId){
      jdbcTemplate.execute("DELETE FROM internal_audit WHERE project_transaction_id = " + txId);
         jdbcTemplate.update(sql.toString());
    }

    public List<Object> getRealTimeSummaryPartialDataList(String controlIds,String compareControlSql ,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        int start = (page - 1)*rp ;
        String sql = " select count(st.transaction_id) totalTransaction"
                     + ", st.rule_title rule"
                     + ",ru.id ruleId"
                     + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                     + " from suspicious_transaction st "
                     + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                     + " WHERE st.transaction_id IN ( SELECT "
                     + "                     tx.id AS TransactionId "
                     + "                     FROM real_time_transaction rtt "
                     + "                     JOIN transaction tx ON (rtt.transaction_id = tx.id) "
                     +                      (!"0".equals(controlIds)? (!Utils.isEmpty(compareControlSql)? "JOIN real_time_transaction_CND rtpCND ON(rtt.id = rtpCND.real_time_transaction_id)":""):"")
                     + "                     WHERE rtt.real_time_project_id = ? "
                     +                      (!"0".equals(controlIds)? (!Utils.isEmpty(compareControlSql)? compareControlSql : ""):"" )
                     +                      " ) "
                     + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE ? "):"")
                     + " GROUP BY st.rule_id "
                     + " ORDER BY " + sortname + " "+ sortorder
                     + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        paramList.add(realTimeProjectId);
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }
    public List<Object> getRealTimeTransactionPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        if(qtype.equals("transaction_date") || qtype.equals("trx.created") ){
           qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        String userRole = Utils.getLoggedUserRoleName();
        String reviewed = "";
        if(Role.IA_MANAGER.getLabel().equals(userRole)) {
            reviewed = ",rtpt."+Constants.IA_MANAGER_REVIEWED.trim()+" reviewed ";
        } else if(Role.LEGAL.getLabel().equals(userRole)) {
            reviewed = " ,rtpt."+Constants.LEGAL_REVIEWED.trim()+" reviewed ";
        }  else if(Role.IA_ANALYST.getLabel().equals(userRole)) {
            reviewed =  " ,rtpt."+Constants.IA_ANALYST_REVIEWED.trim()+" reviewed ";
        } else if(Role.ADMIN.getLabel().equals(userRole)) {
            reviewed = " ,rtpt."+Constants.ADMIN_REVIEWED.trim()+" reviewed ";
        }

        int start = (page - 1)*rp ;
        String sql = "SELECT GROUP_CONCAT(strx.rule_code) rule_code,GROUP_CONCAT(strx.rule_title separator '#') rule_title,rtpt.id, "
                + " trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount, "
                + " rtpCND.decision,trx.id trxId, trx.approver,GROUP_CONCAT(strx.rule_explanation separator '#') rule_explanation"
                + (!Utils.isEmpty(reviewed)? reviewed: "")
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? "JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)":" LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) "):"LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) ")
                + " WHERE 1=1 "
                + (realTimeProjectId > 0 ? " AND  rtp.id = ? " : "")
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"" )
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY rtpt.id"
                + (!Utils.isEmpty(sortname)? " ORDER BY " + sortname + " "+ sortorder:"")
                + " LIMIT ?,?";

        List paramList = new ArrayList();
        if(realTimeProjectId > 0)
            paramList.add(realTimeProjectId);
        if(ruleId > 0 )
            paramList.add(ruleId);
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }
    public int getRealTimeTransactionCount(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        String sql = "SELECT COUNT(innerSql.trxId) totalCount FROM(SELECT trx.id trxId "
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                +  (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? "JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)":" LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) "):" LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) ")
                + " WHERE 1=1 "
                + (realTimeProjectId > 0 ? " AND  rtp.id = ? " : "")
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"" )
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY rtpt.id "
                +") innerSql";

        List param = new ArrayList();
        param.add(realTimeProjectId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }
    public int getRealTimeSummaryCount(String controlIds,String compareControlSql,long realTimeProjectId){
        String sql = "SELECT COUNT(innerTable.ruleId)  totalCount "
                + "FROM (SELECT ru.id ruleId"
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT "
                + "                     tx.id AS TransactionId "
                + "                     FROM real_time_transaction rtt "
                + "                     JOIN transaction tx ON (rtt.transaction_id = tx.id) "
                +                      (!"0".equals(controlIds)? (!Utils.isEmpty(compareControlSql)? "JOIN real_time_transaction_CND rtpCND ON(rtt.id = rtpCND.real_time_transaction_id)":""):"")
                + "                     WHERE rtt.real_time_project_id = ? "
                +                      (!"0".equals(controlIds)? (!Utils.isEmpty(compareControlSql)? compareControlSql : ""):"" )
                +                      " ) "
                + " GROUP BY st.rule_id ) innerTable";

        List param = new ArrayList();
        param.add(realTimeProjectId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public List getARDetailListForTemp(){
        String sql = "SELECT ar.TransNarrative "
                + " FROM ARDetail ar";

        List list = jdbcTemplate.queryForList(sql);
        return list;
    }

    public List getAccountReceivableList(String tableName){
        String sql = "SELECT * "
                + " FROM "+tableName;

        List list = jdbcTemplate.queryForList(sql);
        return list;
    }

    public void executeQueryForTransNarrativeSave(String query){
    jdbcTemplate.execute(query);
    }

    public List getRealTimeProjectListForGrouping(String groupByColumn){

        String sql = "select rtproject.*,innerTable.outstanding_transaction from real_time_project rtproject "
                + "LEFT JOIN( "
                +       "SELECT rtp.id,COUNT(rtp.id) outstanding_transaction FROM real_time_project rtp "
                +       "JOIN real_time_transaction rt ON(rt.real_time_project_id = rtp.id) "
                +       "LEFT JOIN real_time_transaction_CND rtcnd ON(rtcnd.real_time_transaction_id = rt.id)"
                +       "WHERE rtcnd.decision LIKE '%More information required%' "
                +       "GROUP BY rtp.id "
                +       ") innerTable ON(rtproject.id = innerTable.id) ";

        List list = jdbcTemplate.queryForList(sql);

        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public boolean isHolidayIdExist(long holidayId){
        String sql = "SELECT h.id holidayId"
                + " FROM"
                + " holiday h "
                + " WHERE h.id = ?";
        List paramList = new ArrayList();
        paramList.add(holidayId);

        List userList = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(userList != null && userList.size() > 0)
            return true;
        else
            return false;
    }

    public List getListByCustomQuery(String sql){
        List list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0)
            return list;
        return null;
    }
    public long getVendorMasterLedgerId(String VMFid) {
        String sql = "SELECT id FROM vendor_master_ledger vml WHERE vml.vendor_id = ? ";
        List paramList = new ArrayList();
        paramList.add(VMFid);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0)   {
            Map map = (Map)list.get(0);
            return Long.parseLong(map.get("id").toString());
        }
        else
            return 0;
    }

    public long getCustomerMasterLedgerId(long VMFid) {
        String sql = "SELECT id FROM customer_master_ledger cml WHERE cml.customer_id = ? ";
        List paramList = new ArrayList();
        paramList.add(VMFid);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0)   {
            Map map = (Map)list.get(0);
            return Long.parseLong(map.get("id").toString());
        }
        else
            return 0;
    }
    public long getEmployeeMasterLedgerId(String EMFid) {
        String sql = "SELECT id FROM employee_master_ledger cml WHERE cml.employee_id = ? ";
        List paramList = new ArrayList();
        paramList.add(EMFid);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0)   {
            Map map = (Map)list.get(0);
            return Long.parseLong(map.get("id").toString());
        }
        else
            return 0;
    }
    public long getPersonWithFK(String discriminator, long vmfid) {
        List paramList = new ArrayList();
        paramList.add(vmfid);
        return  jdbcTemplate.queryForLong("SELECT id FROM person_information WHERE " + discriminator + " = ? ", paramList.toArray()) ;
    }

    public long getLastId(String ledgerName){
        String sql = "SELECT MAX(id) FROM " + ledgerName;
        return jdbcTemplate.queryForLong(sql);
    }
    public long getLedgerId(String value, String ledger) {

        String sql = "SELECT MAX(id) From "+ ledger +" WHERE entity_name = ? ";
        List paramList = new ArrayList();
        paramList.add(value);
        return  jdbcTemplate.queryForLong(sql, paramList.toArray()) ;
    }
    public List getAllEmailId(String masterLedger) {
        String sql = "SELECT email From "+ masterLedger+ " WHERE email IS NOT NULL AND email != '' ";
        List list = new ArrayList();
        list = jdbcTemplate.queryForList(sql) ;
        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public boolean isValidPersonTypeWithMail(User user) {
        String ptype="";
        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
            ptype = Constants.EMPLOYEE_MASTER_LEDGER;
        }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
            ptype = Constants.VENDOR_MASTER_LEDGER;
        }else if(PersonType.CUSTOMER.getValue().equals(user.getUserType())){
            ptype = Constants.CUSTOMER_MASTER_LEDGER;
        }
        String sql = "SELECT * From "+ ptype +" WHERE email = ? ";
        List paramList = new ArrayList();
        paramList.add(user.getEmail());
        List list = jdbcTemplate.queryForList(sql, paramList.toArray()) ;
        if(list != null && list.size() > 0 ){
            return true;
        }
        return false;
    }

    public long getPersonTypeIdByEmail(User user){
        String ptype="";
        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
            ptype = Constants.EMPLOYEE_MASTER_LEDGER;
        }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
            ptype = Constants.VENDOR_MASTER_LEDGER;
        }else if(PersonType.CUSTOMER.getValue().equals(user.getUserType())){
            ptype = Constants.CUSTOMER_MASTER_LEDGER;
        }
        String sql = "SELECT MAX(id) From "+ ptype +" WHERE email = ? ";
        List paramList = new ArrayList();
        paramList.add(user.getEmail());
        return  jdbcTemplate.queryForLong(sql, paramList.toArray()) ;
    }

    public boolean isEmailExistWithPersonType(User user){
        String sql = "SELECT * From user WHERE user_type=? AND email = ? ";
        List paramList = new ArrayList();
        paramList.add(user.getUserType());
        paramList.add(user.getEmail());

        List list = jdbcTemplate.queryForList(sql, paramList.toArray()) ;
        if(list != null && list.size() > 0 ){
            return false;
        }
        return true;
    }

    public List getUnreadedPolicyList(User user){
        String ptype="";
        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
            ptype = Constants.EMPLOYEE_MASTER_LEDGER_FK;
        }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
            ptype = Constants.VENDOR_MASTER_LEDGER_FK;
        }else if(PersonType.CUSTOMER.getValue().equals(user.getUserType())){
            ptype = Constants.CUSTOMER_MASTER_LEDGER_FK;
        }
        String sql = "SELECT * From "
                      + "policy "
                      + "WHERE policy.id NOT IN (select ppc.policy_FK from policy_and_procedure ppc "
                                               + "WHERE ppc."+ptype +" = ?) ";

        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());

        List list = jdbcTemplate.queryForList(sql, paramList.toArray()) ;
        if(list != null && list.size() > 0 ){
            return list;
        }
        return null;
   }

    public int getPolicyCount(Policy policy){
        String sql = "SELECT COUNT(*) FROM policy";
        return jdbcTemplate.queryForInt(sql);
    }

    public List getPartialPolicyList(Policy policy,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user){
        int start = (page - 1)*rp ;
        String clause = "";
        String subClause = "";
        List paramList = new ArrayList();

        if(qtype.equals("entry_time")){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }

        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
            clause = " AND pp.employee_master_ledger_FK =? ";
            policy.setAudianceCode(1);

        }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
            clause = " AND pp.vendor_master_ledger_FK =? ";
            policy.setAudianceCode(2);

        } else if(PersonType.ADMIN.getValue().equals(user.getUserType())){
            clause = " AND pp.employee_master_ledger_FK =? ";
            policy.setAudianceCode(0);
        }


        String sql = "";

        if(user.getUserTypeId() >= 0){
            sql = "SELECT policy.*,'Outstanding' policy_status "
                    + "FROM policy "
                    + "WHERE (policy.audiance_code = 0 OR policy.audiance_code = ? ) "
                    + "AND policy.id "
                    + "      NOT IN( SELECT pp.policy_FK from policy_and_procedure pp "
                    + "             WHERE 1 = 1 "
                    +               clause
                    + "            ) "
                    + (!Utils.isEmpty(policy.getDocumentName()) ?  " AND policy.document_name LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getAuthor()) ?  " AND policy.author LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getPolicyType()) ?  " AND policy.policy_type = ? " : "")
                    + (!Utils.isEmpty(policy.getEntryDate()) ? " AND DATE_FORMAT(policy.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")

                    + " UNION ALL "

                    + "SELECT policy.*,'Signed' policy_status "
                    + "FROM policy_and_procedure pp "
                    + "JOIN policy ON(policy.id = pp.policy_FK) "
                    + "WHERE 1 = 1 "
                    +  clause
                    + "AND pp.is_confirmed = 0 "
                    + (!Utils.isEmpty(policy.getDocumentName()) ?  " AND policy.document_name LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getAuthor()) ?  " AND policy.author LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getPolicyType()) ?  " AND policy.policy_type = ? " : "")
                    + (!Utils.isEmpty(policy.getEntryDate()) ? " AND DATE_FORMAT(policy.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")


                    + " UNION ALL "

                    + "SELECT policy.*,'Confirmed' policy_status "
                    + "FROM policy_and_procedure pp "
                    + "JOIN policy ON(policy.id = pp.policy_FK) "
                    + "WHERE 1 = 1 "
                    +  clause
                    + "AND pp.is_confirmed = 1 "
                    + (!Utils.isEmpty(policy.getDocumentName()) ?  " AND policy.document_name LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getAuthor()) ?  " AND policy.author LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getPolicyType()) ?  " AND policy.policy_type = ? " : "")
                    + (!Utils.isEmpty(policy.getEntryDate()) ? " AND DATE_FORMAT(policy.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")

                    + " ORDER BY " + sortname + " "+ sortorder
                    + " LIMIT ?, ? " ;

            paramList.add(policy.getAudianceCode());
            if(!Utils.isEmpty(clause)) {
                paramList.add(user.getUserTypeId());
            }
            if(!Utils.isEmpty(policy.getDocumentName())){
                String docName = "%"+policy.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(policy.getAuthor())){
                String author = "%"+policy.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(policy.getPolicyType())){
                paramList.add(policy.getPolicyType());
            }
            if(!Utils.isEmpty(policy.getEntryDate()) ){
                paramList.add("%"+policy.getEntryDate()+"%");
            }
            if(!Utils.isEmpty(clause)) {
                paramList.add(user.getUserTypeId());
            }
            if(!Utils.isEmpty(policy.getDocumentName())){
                String docName = "%"+policy.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(policy.getAuthor())){
                String author = "%"+policy.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(policy.getPolicyType())){
                paramList.add(policy.getPolicyType());
            }
            if(!Utils.isEmpty(policy.getEntryDate()) ){
                paramList.add("%"+policy.getEntryDate()+"%");
            }
            if(!Utils.isEmpty(clause)) {
                paramList.add(user.getUserTypeId());
            }
            if(!Utils.isEmpty(policy.getDocumentName())){
                String docName = "%"+policy.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(policy.getAuthor())){
                String author = "%"+policy.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(policy.getPolicyType())){
                paramList.add(policy.getPolicyType());
            }
            if(!Utils.isEmpty(policy.getEntryDate()) ){
                paramList.add("%"+policy.getEntryDate()+"%");
            }

        }
        paramList.add(start);
        paramList.add(rp);
        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public int getTotalPolicyCount(Policy policy){
        String sql = "SELECT COUNT(policy.id) totalSum "
                + "FROM policy "
                + "WHERE 1=1 "
                + (!Utils.isEmpty(policy.getDocumentName()) ?  " AND policy.document_name LIKE ? " : "")
                + (!Utils.isEmpty(policy.getAuthor()) ?  " AND policy.author LIKE ? " : "")
                + (!Utils.isEmpty(policy.getPolicyType()) ?  " AND policy.policy_type = ? " : "")
                + (!Utils.isEmpty(policy.getEntryDate()) ? " AND DATE_FORMAT(policy.entry_time,\"%m/%d/%Y\") LIKE ? " : " ");
        List paramList = new ArrayList();

        if(!Utils.isEmpty(policy.getDocumentName())){
            String docName = "%"+policy.getDocumentName().trim()+"%";
            paramList.add(docName);
        }

        if(!Utils.isEmpty(policy.getAuthor())){
            String author = "%"+policy.getAuthor().trim()+"%";
            paramList.add(author);
        }
        if(!Utils.isEmpty(policy.getPolicyType())){
            paramList.add(policy.getPolicyType());
        }
        if(!Utils.isEmpty(policy.getEntryDate())){
            paramList.add("%"+policy.getEntryDate()+"%");
        }
        List list = jdbcTemplate.queryForList(sql,paramList.toArray());
        if (list != null && list.size() > 0)    {
            Map map = (Map)list.get(0);
            return ((Number)map.get("totalSum")).intValue();

        }
        return 0;
    }

    public List getPartialTrainingList(Training training,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user){
        int start = (page - 1)*rp ;
        if(qtype.equals("entry_time")){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        String sql = "";
        List paramList = new ArrayList();
        if(user.getUserTypeId() > 0){
        sql = "SELECT tr.*,'start' training_status "
                + "FROM training tr "
                + "WHERE tr.id "
                + "NOT IN( SELECT empTr.training_FK from employee_training empTr "
                + "        WHERE empTr.employee_master_ledger_id = ? "
                + "       ) "
                + (!Utils.isEmpty(training.getDocumentName()) ?  " AND tr.document_name LIKE ? " : "")
                + (!Utils.isEmpty(training.getAuthor()) ?  " AND tr.author LIKE ? " : "")
                + (!Utils.isEmpty(training.getTrainingType()) ?  " AND tr.training_type = ? " : "")
                + (!Utils.isEmpty(training.getEntryDate()) ? " AND DATE_FORMAT(tr.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")

                + "UNION ALL "

                + "SELECT tr.*,empTr.status training_status "
                + "from employee_training empTr "
                + "JOIN training tr ON(tr.id = empTr.training_FK) "
                + "WHERE empTr.employee_master_ledger_id = ? AND empTr.status = ? "
                + (!Utils.isEmpty(training.getDocumentName()) ?  " AND tr.document_name LIKE ? " : "")
                + (!Utils.isEmpty(training.getAuthor()) ?  " AND tr.author LIKE ? " : "")
                + (!Utils.isEmpty(training.getTrainingType()) ?  " AND tr.training_type = ? " : "")
                + (!Utils.isEmpty(training.getEntryDate()) ? " AND DATE_FORMAT(tr.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")

                + "UNION ALL "

                + "SELECT tr.*,empTr.status training_status "
                + "from employee_training empTr "
                + "JOIN training tr ON(tr.id = empTr.training_FK) "
                + "WHERE empTr.employee_master_ledger_id = ? AND empTr.status =? "
                + (!Utils.isEmpty(training.getDocumentName()) ?  " AND tr.document_name LIKE ? " : "")
                + (!Utils.isEmpty(training.getAuthor()) ?  " AND tr.author LIKE ? " : "")
                + (!Utils.isEmpty(training.getTrainingType()) ?  " AND tr.training_type = ? " : "")
                + (!Utils.isEmpty(training.getEntryDate()) ? " AND DATE_FORMAT(tr.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " ORDER BY " + sortname + " "+ sortorder
                + " LIMIT ?, ? " ;

            paramList.add(user.getUserTypeId());
            if(!Utils.isEmpty(training.getDocumentName())){
                String docName = "%"+training.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(training.getAuthor())){
                String author = "%"+training.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(training.getTrainingType())){
                paramList.add(training.getTrainingType());
            }
            if(!Utils.isEmpty(training.getEntryDate()) ){
                paramList.add("%"+training.getEntryDate()+"%");
            }

            paramList.add(user.getUserTypeId());
            paramList.add(Constants.TRAINING_RETAKE);
            if(!Utils.isEmpty(training.getDocumentName())){
                String docName = "%"+training.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(training.getAuthor())){
                String author = "%"+training.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(training.getTrainingType())){
                paramList.add(training.getTrainingType());
            }
            if(!Utils.isEmpty(training.getEntryDate()) ){
                paramList.add("%"+training.getEntryDate()+"%");
            }

            paramList.add(user.getUserTypeId());
            paramList.add(Constants.TRAINING_CERTIFICATE);
        }else{
         sql = "SELECT * "
                + "FROM training "
                + "WHERE 1=1 "
                + (!Utils.isEmpty(training.getDocumentName()) ?  " AND training.document_name LIKE ? " : "")
                + (!Utils.isEmpty(training.getAuthor()) ?  " AND training.author LIKE ? " : "")
                + (!Utils.isEmpty(training.getTrainingType()) ?  " AND training.training_type = ? " : "")
                + (!Utils.isEmpty(training.getEntryDate()) ? " AND DATE_FORMAT(training.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " ORDER BY " + sortname + " "+ sortorder
                + " LIMIT ?, ? " ;
        }
//        logger.debug("SQL:"+sql);
        if(!Utils.isEmpty(training.getDocumentName())){
            String docName = "%"+training.getDocumentName().trim()+"%";
            paramList.add(docName);
        }

        if(!Utils.isEmpty(training.getAuthor())){
            String author = "%"+training.getAuthor().trim()+"%";
            paramList.add(author);
        }
        if(!Utils.isEmpty(training.getTrainingType())){
            paramList.add(training.getTrainingType());
        }
        if(!Utils.isEmpty(training.getEntryDate()) ){
            paramList.add("%"+training.getEntryDate()+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        return jdbcTemplate.queryForList(sql, paramList.toArray());
    }

    public int getTotalTrainingCount(Training training){
        String sql = "SELECT COUNT(training.id) totalSum "
                + "FROM training "
                + "WHERE 1=1 "
                + (!Utils.isEmpty(training.getDocumentName()) ?  " AND training.document_name LIKE ? " : "")
                + (!Utils.isEmpty(training.getAuthor()) ?  " AND training.author LIKE ? " : "")
                + (!Utils.isEmpty(training.getTrainingType()) ?  " AND training.training_type = ? " : "")
                + (!Utils.isEmpty(training.getEntryDate()) ? " AND DATE_FORMAT(training.entry_time,\"%m/%d/%Y\") LIKE ? " : " ");
        List paramList = new ArrayList();

        if(!Utils.isEmpty(training.getDocumentName())){
            String docName = "%"+training.getDocumentName().trim()+"%";
            paramList.add(docName);
        }

        if(!Utils.isEmpty(training.getAuthor())){
            String author = "%"+training.getAuthor().trim()+"%";
            paramList.add(author);
        }
        if(!Utils.isEmpty(training.getTrainingType())){
            paramList.add(training.getTrainingType());
        }
        if(!Utils.isEmpty(training.getEntryDate())){
            paramList.add("%"+training.getEntryDate()+"%");
        }
        List list = jdbcTemplate.queryForList(sql,paramList.toArray());
        if (list != null && list.size() > 0)    {
            Map map = (Map)list.get(0);
            return ((Number)map.get("totalSum")).intValue();

        }
        return 0;
    }

    public List<Object> getAllTransactionPartialDataList(String controlIds,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        if(qtype.equals("transaction_date") || qtype.equals("trx.created") ){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        int start = (page - 1)*rp ;
        String sql = "SELECT  0 project, pt.id , rtp.id projectId ,GROUP_CONCAT(strx.rule_code) rule_code,GROUP_CONCAT(strx.rule_title separator '#') rule_title,trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount,"
                + " ptCND.decision,trx.id trxId, trx.approver,GROUP_CONCAT(strx.rule_explanation separator '#') rule_explanation"
                + " FROM"
                + " proactive_transaction pt "
                + " JOIN proactive_project rtp ON(pt.proactive_project_id = rtp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + " JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id)"
                + " WHERE 1=1"
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY pt.id"

                +  " UNION ALL "

                + "SELECT  3 project, rtpt.id , rtp.id projectId ,GROUP_CONCAT(strx.rule_code) rule_code,GROUP_CONCAT(strx.rule_title separator '#') rule_title,trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount,"
                + " rtpCND.decision,trx.id trxId, trx.approver,GROUP_CONCAT(strx.rule_explanation separator '#') rule_explanation"
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + " JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)"
                + " WHERE 1=1"
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY rtpt.id"
                + (!Utils.isEmpty(sortname)? " ORDER BY " + sortname + " "+ sortorder:"")
                + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();

        if(ruleId > 0 ){
            paramList.add(ruleId);
            paramList.add(ruleId);
        }

        paramList.add(start);
        paramList.add(rp);

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public int getAllTransactionPartialDataListCount(String qtype, String query, String sortname, String sortorder,long ruleId){
        if(qtype.equals("transaction_date") || qtype.equals("trx.created") ){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }

        String sql = " SELECT SUM(innerSql.total) totalSum FROM ( SELECT  COUNT(pt.id) total "
                + " FROM"
                + " proactive_transaction pt "
                + " JOIN proactive_project rtp ON(pt.proactive_project_id = rtp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + " JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id)"
                + " WHERE 1=1"
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")

                +  " UNION ALL "

                + " SELECT  COUNT(rtpt.id) total "
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + " JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)"
                + " WHERE 1=1"
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " ) innerSql";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();

        if(ruleId > 0 ){
            paramList.add(ruleId);
            paramList.add(ruleId);
        }
        List list = jdbcTemplate.queryForList(sql,paramList.toArray());
        if (list != null && list.size() > 0)    {
            Map map = (Map)list.get(0);
            return ((Number)map.get("totalSum")).intValue();

        }
        return 0;
    }

    public List<Object> getAllSummaryCountList(String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        int start = (page - 1)*rp ;
        String sql = " select count(st.transaction_id) totalTransaction"
                + ", st.rule_title rule"
                + ",ru.id ruleId"
                + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( "
                + "                 SELECT trx.id AS TransactionId"
                + "                 FROM "
                + "                 proactive_transaction pt"
                + "                 JOIN proactive_project rtp ON(pt.proactive_project_id = rtp.id)"
                + "                 JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + "                 LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + "                 JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id)"
//                + "                 WHERE 1=1 AND ptCND.control_ids LIKE '%1:%'"

                + "                 UNION ALL"

                + "                 SELECT trx.id AS TransactionId"
                + "                 FROM"
                + "                 real_time_transaction rtpt"
                + "                 JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + "                 JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + "                 LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + "                 JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)"
//                + "                 WHERE 1=1  AND rtpCND.control_ids LIKE '%1:%'"
                + "                 ) "
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE ? "):"")
                + " GROUP BY st.rule_id "
                + " ORDER BY " + sortname + " "+ sortorder
                + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public int getTotalSummaryCountList(String controlId,String qtype, String query, String sortname, String sortorder){
        String sql = "SELECT COUNT(innerSql.totalTransaction) totalSum from (SELECT COUNT(st.transaction_id) totalTransaction"
                + ", st.rule_title rule"
                + ",ru.id ruleId"
                + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( "
                + "                 SELECT trx.id AS TransactionId"
                + "                 FROM "
                + "                 proactive_transaction pt"
                + "                 JOIN proactive_project rtp ON(pt.proactive_project_id = rtp.id)"
                + "                 JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + "                 LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + "                 JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id)"
//                + "                 WHERE 1=1 AND ptCND.control_ids LIKE '%1:%'"

                + "                 UNION ALL"

                + "                 SELECT trx.id AS TransactionId"
                + "                 FROM"
                + "                 real_time_transaction rtpt"
                + "                 JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + "                 JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + "                 LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + "                 JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)"
//                + "                 WHERE 1=1  AND rtpCND.control_ids LIKE '%1:%'"
                + "                 ) "
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE ? "):"")
                + " GROUP BY st.rule_id "
                + " ORDER BY " + sortname + " "+ sortorder
                + " ) innerSql";

        //logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }

        List list = jdbcTemplate.queryForList(sql,paramList.toArray());
        if (list != null && list.size() > 0)    {
            Map map = (Map)list.get(0);
            return ((Number)map.get("totalSum")).intValue();

        }
        return 0;
    }

    public List<Object> getProactiveTransactionPartialDataList(long proactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        if(qtype.equals("transaction_date") || qtype.equals("trx.created") ){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        int start = (page - 1)*rp ;
        String sql = "SELECT  0 project, pt.id , rtp.id projectId ,GROUP_CONCAT(strx.rule_code) rule_code,GROUP_CONCAT(strx.rule_title separator '#') rule_title,trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount,"
                + " ptCND.decision,trx.id trxId, trx.approver,GROUP_CONCAT(strx.rule_explanation separator '#') rule_explanation"
                + " FROM"
                + " proactive_transaction pt "
                + " JOIN proactive_project rtp ON(pt.proactive_project_id = rtp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
//                + " JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id) "
                + (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? "JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id) ":" LEFT JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id) "):" LEFT JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id) ")
                + " WHERE 1=1"
                + (proactiveProjectId > 0 ? " AND  rtp.id = ? " : "")
                + (!Utils.isEmpty(controlId)? controlCompareString : "")
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY pt.id"
                + (!Utils.isEmpty(sortname)? " ORDER BY " + sortname + " "+ sortorder:"")
                + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        if(proactiveProjectId > 0)
            paramList.add(proactiveProjectId);
        if(ruleId > 0 )
            paramList.add(ruleId);
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }
    public int getProactiveTransactionCount(long proactiveProjectId,String controlCompareString,String controlId){
        String sql = "SELECT  COUNT(tx.id) totalCount "
                + "FROM proactive_transaction pt "
                + "JOIN transaction tx ON (pt.transaction_id = tx.id) "
                + (!Utils.isEmpty(controlId)? "JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id) " : "" )
                + "WHERE pt.proactive_project_id = ? "
                + (!Utils.isEmpty(controlId)? controlCompareString : "");

        List param = new ArrayList();
        param.add(proactiveProjectId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getProactiveSummaryCount(long proactiveProjectId,String controlCompareString,String controlId){
        String sql = "SELECT COUNT(innerTable.ruleId) totalCount "
                + " FROM ("
                + " select count(st.transaction_id) totalTransaction"
                + ", st.rule_title rule"
                + ",ru.id ruleId"
                + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT "
                + "                     tx.id AS TransactionId "
                + "                     FROM proactive_transaction pt "
                + "                     JOIN transaction tx ON (pt.transaction_id = tx.id) "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? "JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id) " : "" ):"")
                + "                     WHERE pt.proactive_project_id = ? "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? controlCompareString : " "):"")
                + "                     )"
                + " GROUP BY st.rule_id ) innerTable";

        List param = new ArrayList();
        param.add(proactiveProjectId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public List<Object> getProactiveSummaryPartialDataList(long proactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        int start = (page - 1)*rp ;
        String sql = " select count(st.transaction_id) totalTransaction"
                + ", st.rule_title rule"
                + ",ru.id ruleId"
                + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT "
                + "                     tx.id AS TransactionId "
                + "                     FROM proactive_transaction pt "
                + "                     JOIN transaction tx ON (pt.transaction_id = tx.id) "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? "JOIN proactive_transaction_CND ptCND ON(pt.id = ptCND.proactive_transaction_id) " : "" ):"")
                + "                     WHERE pt.proactive_project_id = ? "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? controlCompareString : " "):"")
                + "                     )"
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE ? "):"")
                + " GROUP BY st.rule_id "
                + " ORDER BY " + sortname + " "+ sortorder
                + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        paramList.add(proactiveProjectId);
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public List<Object> getReactiveTransactionPartialDataList(long reactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId){
        if(qtype.equals("transaction_date") || qtype.equals("trx.created") ){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        int start = (page - 1)*rp ;
        String sql = "SELECT  0 project, pt.id , rtp.id projectId ,GROUP_CONCAT(strx.rule_code) rule_code,GROUP_CONCAT(strx.rule_title separator '#') rule_title,trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount,"
                + " ptCND.decision,trx.id trxId, trx.approver,GROUP_CONCAT(strx.rule_explanation separator '#') rule_explanation"
                + " FROM"
                + " reactive_transaction pt "
                + " JOIN reactive_project rtp ON(pt.reactive_project_id = rtp.id)"
                + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? "JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) ":" LEFT JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) "):" LEFT JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) ")
                + " WHERE 1=1"
                + (reactiveProjectId > 0 ? " AND  rtp.id = ? " : "")
                + (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"" )
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY pt.id"
                + (!Utils.isEmpty(sortname)? " ORDER BY " + sortname + " "+ sortorder:"")
                + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        if(reactiveProjectId > 0)
            paramList.add(reactiveProjectId);
        if(ruleId > 0 )
            paramList.add(ruleId);
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }
    public int getReactiveTransactionCount(long reactiveProjectId,String controlCompareString,String controlId,String qtype, String query, String sortname, String sortorder,long ruleId){
        String sql = /*"SELECT  COUNT(tx.id) totalCount "
                + "FROM reactive_transaction pt "
                + "JOIN transaction tx ON (pt.transaction_id = tx.id) "
                + (!Utils.isEmpty(controlId)? "JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) " : "" )
                + "WHERE pt.reactive_project_id = ? "
                + (!Utils.isEmpty(controlId)? controlCompareString : "");*/

                "SELECT count(innerSql.trxId) totalCount "
                        + " FROM ( SELECT trx.id trxId "
                        + " FROM"
                        + " reactive_transaction pt "
                        + " JOIN reactive_project rtp ON(pt.reactive_project_id = rtp.id)"
                        + " JOIN transaction trx ON(pt.transaction_id = trx.id)"
                        + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                        +  (!Utils.isEmpty(controlCompareString)? "JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) ":" LEFT JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) ")
                        + " WHERE 1=1 "
                        + (reactiveProjectId > 0 ? " AND  rtp.id = ? " : "")
                        + (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"" )
                        + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                        + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                        + " GROUP BY pt.id ) innerSql";

        List param = new ArrayList();
        param.add(reactiveProjectId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getReactiveSummaryCount(long reactiveProjectId,String controlCompareString,String controlId){
        String sql = "SELECT COUNT(innerTable.ruleId) totalCount "
                + " FROM ("
                + " select count(st.transaction_id) totalTransaction"
                + ", st.rule_title rule"
                + ",ru.id ruleId"
                + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT "
                + "                     tx.id AS TransactionId "
                + "                     FROM reactive_transaction pt "
                + "                     JOIN transaction tx ON (pt.transaction_id = tx.id) "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? "JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) " : "" ):"")
                + "                     WHERE pt.reactive_project_id = ? "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? controlCompareString : " "):"")
                + "                     )"
                + " GROUP BY st.rule_id ) innerTable";

        List param = new ArrayList();
        param.add(reactiveProjectId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public List<Object> getReactiveSummaryPartialDataList(long reactiveProjectId,String controlCompareString,String controlId,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        int start = (page - 1)*rp ;
        String sql = " select count(st.transaction_id) totalTransaction"
                + ", st.rule_title rule"
                + ",ru.id ruleId"
                + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT "
                + "                     tx.id AS TransactionId "
                + "                     FROM reactive_transaction pt "
                + "                     JOIN transaction tx ON (pt.transaction_id = tx.id) "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)? "JOIN reactive_transaction_CND ptCND ON(pt.id = ptCND.reactive_transaction_id) " : "" ):"")
                + "                     WHERE pt.reactive_project_id = ? "
                +                      (!"0".equals(controlId)? (!Utils.isEmpty(controlCompareString)?  controlCompareString : " "):"")
                + "                     )"
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE ? "):"")
                + " GROUP BY st.rule_id "
                + " ORDER BY " + sortname + " "+ sortorder
                + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        paramList.add(reactiveProjectId);
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public List getProjectWiseCountListByControlIds(String controlIds,String controlCompareString,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        String clause = "";
        if (Utils.getLoggedUserRoleName().equals(Role.IA_ANALYST.getLabel())) {
            clause = " AND rp.assign_to ='"+Utils.getLoggedUserName()+"' ";
        }
        if(Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())){
            clause = " AND rcnd.decision = 'Further Action Required' ";
        }

        String sql = "SELECT 'Proactive Project' project,pp.id projectId,0 projectType, " +
            " GROUP_CONCAT( ptx.transaction_id) transaction_ids," +
            " COUNT(ptx.transaction_id) total "
//                + ", GROUP_CONCAT(region.id) region_ids"
            + " FROM"
            + " proactive_transaction_CND pcnd"
            + " JOIN proactive_transaction ptx ON(ptx.id = pcnd.proactive_transaction_id)"
            + " JOIN proactive_project pp ON(pp.id = ptx.proactive_project_id)"
//                + " JOIN region ON(region.id = pp.region_id)"
            + " WHERE control_ids IS NOT NULL "
            + (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())? " AND pcnd.decision = 'Further Action Required' " : "")
            + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
            + " GROUP BY pp.id "
            + " UNION ALL"

            + " SELECT 'Reactive Project' project,rp.id projectId,1 projectType, "
            + " GROUP_CONCAT( rtx.transaction_id) transaction_ids,"
            + " COUNT(rtx.transaction_id) total "
//                + ", GROUP_CONCAT(region.id) region_ids"
            + " FROM "
            + " reactive_transaction_CND rcnd  "
            + " JOIN reactive_transaction rtx ON(rtx.id = rcnd.reactive_transaction_id)"
            + " JOIN reactive_project rp ON(rp.id = rtx.reactive_project_id)"
//                + " JOIN region ON(region.id = rp.region_id)"
            + " WHERE control_ids IS NOT NULL "
            + (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())? " AND rcnd.decision = 'Further Action Required' " : "")
            + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
            + " GROUP BY rp.id "

            + " UNION ALL"

            + " SELECT rp.project_name project,rp.id projectId,2 projectType, "
            + " GROUP_CONCAT( rtx.transaction_id) transaction_ids,"
            + " COUNT(rtx.transaction_id) total "
//                + ", GROUP_CONCAT(region.id) region_ids"
            + " FROM "
            + " real_time_transaction_CND rcnd  "
            + " JOIN real_time_transaction rtx ON(rtx.id = rcnd.real_time_transaction_id)"
            + " JOIN real_time_project rp ON(rp.id = rtx.real_time_project_id)"
//                + " JOIN region ON(region.id = rp.region_id)"
            + " WHERE control_ids IS NOT NULL "
            + clause
            + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
            + " GROUP BY rp.id ";

        List list = jdbcTemplate.queryForList(sql);

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }
    public void importReactiveTransaction() {
        String sql =  "INSERT  INTO reactive_transaction (transaction_id, reactive_project_id) ( " +
                "  SELECT transaction_id, (SELECT max(id) FROM reactive_project) AS ReactiveProjectId " +
                "  FROM `suspicious_transaction` " +
                "  WHERE " +
                "    rule_code ='R2' OR " +
                "    rule_code ='R3' " +
                "  LIMIT 20 " +
                ") " +
                "UNION " +
                "( " +
                "  SELECT DISTINCT transaction_id, (SELECT max(id) FROM reactive_project) AS ReactiveProjectId " +
                "  FROM `suspicious_transaction` " +
                "  WHERE " +
                "    rule_code !='R2' OR " +
                "    rule_code !='R3' " +
                "  ORDER BY RAND( ) LIMIT 20 " +
                ") ;";
        jdbcTemplate.update(sql);
    }
    public void importProactiveTransaction() {
        String sql =  "INSERT  INTO proactive_transaction (transaction_id, proactive_project_id) ( " +
                "  SELECT transaction_id, (SELECT max(id) FROM proactive_project) AS ReactiveProjectId " +
                "  FROM `suspicious_transaction` " +
                "  WHERE " +
                "    rule_code ='R2' OR " +
                "    rule_code ='R3' " +
                "  LIMIT 20 " +
                ") " +
                "UNION " +
                "( " +
                "  SELECT DISTINCT transaction_id, (SELECT max(id) FROM proactive_project) AS ReactiveProjectId " +
                "  FROM `suspicious_transaction` " +
                "  WHERE " +
                "    rule_code !='R2' OR " +
                "    rule_code !='R3' " +
                "  ORDER BY RAND( ) LIMIT 20 " +
                ") ;";
        jdbcTemplate.update(sql);
    }

    public int getProjectWiseCountByControlIds(String controlIds,String controlCompareString,String qtype, String query, String sortname, String sortorder){
        String clause = "";
        if (Utils.getLoggedUserRoleName().equals(Role.IA_ANALYST.getLabel())) {
            clause = " AND rp.assign_to ='"+Utils.getLoggedUserName()+"' ";
        }
        if(Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())){
            clause = " AND rcnd.decision = 'Further Action Required' ";
        }

        String sql = "SELECT COUNT(innerSql.project) totalCount FROM "
                +" (SELECT 'Proactive Project' project,pp.id projectId,0 projectType, " +
                " GROUP_CONCAT( ptx.transaction_id) transaction_ids," +
                " COUNT(ptx.transaction_id) total "
                + " FROM"
                + " proactive_transaction_CND pcnd"
                + " JOIN proactive_transaction ptx ON(ptx.id = pcnd.proactive_transaction_id)"
                + " JOIN proactive_project pp ON(pp.id = ptx.proactive_project_id)"
                + " WHERE control_ids IS NOT NULL "
                + (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())? " AND pcnd.decision = 'Further Action Required' " : "")
                + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
                + " GROUP BY pp.id "
                + " UNION ALL"

                + " SELECT 'Reactive Project' project,rp.id projectId,1 projectType, "
                + " GROUP_CONCAT( rtx.transaction_id) transaction_ids,"
                + " COUNT(rtx.transaction_id) total "
                + " FROM "
                + " reactive_transaction_CND rcnd  "
                + " JOIN reactive_transaction rtx ON(rtx.id = rcnd.reactive_transaction_id)"
                + " JOIN reactive_project rp ON(rp.id = rtx.reactive_project_id)"
                + " WHERE control_ids IS NOT NULL "
                + (Utils.getLoggedUserRoleName().equals(Role.IA_MANAGER.getLabel())? " AND rcnd.decision = 'Further Action Required' " : "")
                + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
                + " GROUP BY rp.id "

                + " UNION ALL"

                + " SELECT rp.project_name project,rp.id projectId,2 projectType, "
                + " GROUP_CONCAT( rtx.transaction_id) transaction_ids,"
                + " COUNT(rtx.transaction_id) total "
                + " FROM "
                + " real_time_transaction_CND rcnd  "
                + " JOIN real_time_transaction rtx ON(rtx.id = rcnd.real_time_transaction_id)"
                + " JOIN real_time_project rp ON(rp.id = rtx.real_time_project_id)"
                + " WHERE control_ids IS NOT NULL "
                +  clause
                + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
                + " GROUP BY rp.id ) innerSql ";

        List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public List getControlWiseCountListByControlIds(String controlIds,String controlCompareString,int page, int rp,  String qtype, String query, String sortname, String sortorder){
        String sql = "SELECT 'Proactive Project' project,pp.id projectId,0 projectType, " +
                " GROUP_CONCAT( ptx.transaction_id) transaction_ids," +
                " COUNT(ptx.transaction_id) total "
                + " FROM"
                + " proactive_transaction_CND pcnd"
                + " JOIN proactive_transaction ptx ON(ptx.id = pcnd.proactive_transaction_id)"
                + " JOIN proactive_project pp ON(pp.id = ptx.proactive_project_id)"
                + " WHERE control_ids IS NOT NULL "
                + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
                + " GROUP BY pp.id "
                + " UNION ALL"

                + " SELECT 'Reactive Project' project,rp.id projectId,1 projectType, "
                + " GROUP_CONCAT( rtx.transaction_id) transaction_ids,"
                + " COUNT(rtx.transaction_id) total "
                + " FROM "
                + " reactive_transaction_CND rcnd  "
                + " JOIN reactive_transaction rtx ON(rtx.id = rcnd.reactive_transaction_id)"
                + " JOIN reactive_project rp ON(rp.id = rtx.reactive_project_id)"
                + " WHERE control_ids IS NOT NULL "
                + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
                + " GROUP BY rp.id "

                + " UNION ALL"

                + " SELECT rp.project_name project,rp.id projectId,2 projectType, "
                + " GROUP_CONCAT( rtx.transaction_id) transaction_ids,"
                + " COUNT(rtx.transaction_id) total "
                + " FROM "
                + " real_time_transaction_CND rcnd  "
                + " JOIN real_time_transaction rtx ON(rtx.id = rcnd.real_time_transaction_id)"
                + " JOIN real_time_project rp ON(rp.id = rtx.real_time_project_id)"
                + " WHERE control_ids IS NOT NULL "
                + (!Utils.isEmpty(controlCompareString)? controlCompareString : " ")
                + " GROUP BY rp.id ";

           /* + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
            + " GROUP BY pt.id"
            + (!Utils.isEmpty(sortname)? " ORDER BY " + sortname + " "+ sortorder:"")
            + " LIMIT ?,?";*/




       /* List paramList = new ArrayList();
        paramList.add(reactiveProjectId);
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);*/
        List list = jdbcTemplate.queryForList(sql);

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public String getcommaSeparatedControlIds(){
        String sql = "SELECT GROUP_CONCAT(control.id) control_id"
                + " FROM"
                + " control";
        Map map = jdbcTemplate.queryForMap(sql);
        if (map != null) return (String) map.get("control_id");
        return null;
    }

    public void deletePolicyAndProcedureByPolicyId(long policyId){
        String sql = "DELETE FROM policy_and_procedure WHERE policy_FK = ?";
        List paramList = new ArrayList();
        paramList.add(policyId);
        jdbcTemplate.update(sql,paramList.toArray());

    }

    public int getTotalPolicyConfirmedByPolicyId(long policyId,int status){
        String sql = "SELECT COUNT(pp.id) totalCount "
                    + "FROM "
                    + "policy_and_procedure pp "
                    + "WHERE pp.policy_FK = ? "
                    + (status == 0 ? "" : (status == 1? " AND (pp.vendor_master_ledger_FK IS NULL AND pp.employee_master_ledger_FK IS NOT NULL) " : " AND (pp.vendor_master_ledger_FK IS NOT NULL AND pp.employee_master_ledger_FK IS NULL) "))
                    + "AND pp.is_confirmed =1";

        List paramList = new ArrayList();
        paramList.add(policyId);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;

    }

    public int getTotalPolicyUnconfirmedByPolicyId(long policyId,int status){
        String sql = "SELECT COUNT(pp.id) totalCount "
                    + "FROM "
                    + "policy_and_procedure pp "
                    + "WHERE pp.policy_FK = ? "
                    + (status == 0 ? "" : (status == 1? " AND (pp.vendor_master_ledger_FK IS NULL AND pp.employee_master_ledger_FK IS NOT NULL) " : " AND (pp.vendor_master_ledger_FK IS NOT NULL AND pp.employee_master_ledger_FK IS NULL) "))
                    + "AND pp.is_confirmed = 0";

        List paramList = new ArrayList();
        paramList.add(policyId);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;

    }

    public int getPolicyListCount(Policy policy,String qtype, String query, String sortname, User user){
        String clause = "";
        String subClause = "";
        List paramList = new ArrayList();
        if(qtype.equals("entry_time")){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }

        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
            clause = " AND pp.employee_master_ledger_FK =? ";
            policy.setAudianceCode(1);
        }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
            clause = " AND pp.vendor_master_ledger_FK =? ";
            policy.setAudianceCode(2);
        }else if(PersonType.ADMIN.getValue().equals(user.getUserType())){
            clause = "";
            policy.setAudianceCode(0);
        }

        String sql = "";

        if(user.getUserTypeId() >= 0 ){
            sql = "SELECT COUNT(innerSql.policyId) totalCount FROM (SELECT policy.id policyId "
                    + "FROM policy "
                    + "WHERE (policy.audiance_code = 0 OR policy.audiance_code = ? ) "
                    + "AND policy.id "
                    + "      NOT IN( SELECT pp.policy_FK from policy_and_procedure pp "
                    + "             WHERE 1 = 1 "
                    +               clause
                    + "            ) "
                    + (!Utils.isEmpty(policy.getDocumentName()) ?  " AND policy.document_name LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getAuthor()) ?  " AND policy.author LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getPolicyType()) ?  " AND policy.policy_type = ? " : "")
                    + (!Utils.isEmpty(policy.getEntryDate()) ? " AND DATE_FORMAT(policy.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")

                    + " UNION ALL "

                    + "SELECT policy.id policyId "
                    + "FROM policy_and_procedure pp "
                    + "JOIN policy ON(policy.id = pp.policy_FK) "
                    + "WHERE 1 = 1 "
                    +  clause
                    + "AND pp.is_confirmed = 0 "
                    + (!Utils.isEmpty(policy.getDocumentName()) ?  " AND policy.document_name LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getAuthor()) ?  " AND policy.author LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getPolicyType()) ?  " AND policy.policy_type = ? " : "")
                    + (!Utils.isEmpty(policy.getEntryDate()) ? " AND DATE_FORMAT(policy.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")


                    + " UNION ALL "

                    + "SELECT policy.id policyId "
                    + "FROM policy_and_procedure pp "
                    + "JOIN policy ON(policy.id = pp.policy_FK) "
                    + "WHERE 1 = 1 "
                    +  clause
                    + "AND pp.is_confirmed = 1 "
                    + (!Utils.isEmpty(policy.getDocumentName()) ?  " AND policy.document_name LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getAuthor()) ?  " AND policy.author LIKE ? " : "")
                    + (!Utils.isEmpty(policy.getPolicyType()) ?  " AND policy.policy_type = ? " : "")
                    + (!Utils.isEmpty(policy.getEntryDate()) ? " AND DATE_FORMAT(policy.entry_time,\"%m/%d/%Y\") LIKE ? " : " ")
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                    + " ) innerSql " ;


            paramList.add(policy.getAudianceCode());
            if(!Utils.isEmpty(clause)) {
                paramList.add(user.getUserTypeId());
            }
            if(!Utils.isEmpty(policy.getDocumentName())){
                String docName = "%"+policy.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(policy.getAuthor())){
                String author = "%"+policy.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(policy.getPolicyType())){
                paramList.add(policy.getPolicyType());
            }
            if(!Utils.isEmpty(policy.getEntryDate()) ){
                paramList.add("%"+policy.getEntryDate()+"%");
            }
            if(!Utils.isEmpty(clause)) {
                paramList.add(user.getUserTypeId());
            }
            if(!Utils.isEmpty(policy.getDocumentName())){
                String docName = "%"+policy.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(policy.getAuthor())){
                String author = "%"+policy.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(policy.getPolicyType())){
                paramList.add(policy.getPolicyType());
            }
            if(!Utils.isEmpty(policy.getEntryDate()) ){
                paramList.add("%"+policy.getEntryDate()+"%");
            }
            if(!Utils.isEmpty(clause)) {
                paramList.add(user.getUserTypeId());
            }
            if(!Utils.isEmpty(policy.getDocumentName())){
                String docName = "%"+policy.getDocumentName().trim()+"%";
                paramList.add(docName);
            }

            if(!Utils.isEmpty(policy.getAuthor())){
                String author = "%"+policy.getAuthor().trim()+"%";
                paramList.add(author);
            }
            if(!Utils.isEmpty(policy.getPolicyType())){
                paramList.add(policy.getPolicyType());
            }
            if(!Utils.isEmpty(policy.getEntryDate()) ){
                paramList.add("%"+policy.getEntryDate()+"%");
            }

        }
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getTotalEmployeeWithEmailAddresd(){
       String sql = "SELECT COUNT(eml.id) totalCount "
                   + " FROM  employee_master_ledger eml "
                   + " WHERE eml.email IS NOT NULL AND eml.email  != '' ";
        List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getTotalVendorWithEmailAddresd(){
        String sql = "SELECT COUNT(vml.id) totalCount "
                   + " FROM  vendor_master_ledger vml"
                   + " WHERE vml.email IS NOT NULL AND vml.email  != '' ";
        List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }



    public List<Map> getRealTimeTransactionListByRtProjectId(long rtProjectId){
        String sql = "SELECT trx.id trxId,rtt.real_time_project_id realTimeProjectId "
                + "FROM transaction trx "
                + "LEFT JOIN  real_time_transaction rtt ON ( rtt.transaction_id = trx.id) "
                + "WHERE rtt.real_time_project_id= ? "
                + "ORDER BY trx.id ASC";

        List paramList = new ArrayList();
        paramList.add(rtProjectId);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }




    public int getTotalTrainingParticipant(long trainingId, int audianceCode){
        String sql = "SELECT COUNT(et.id) totalCount "
                + "FROM "
                + "employee_training et "
                + "WHERE et.training_FK = ? "
                + (audianceCode == 0 ? "" : (audianceCode == 1? " AND (et.employee_master_ledger_id IS NOT NULL) " : ""))
                + "AND (et.status = ? OR et.status = ?)" ;

        List paramList = new ArrayList();
        paramList.add(trainingId);
        paramList.add(Constants.TRAINING_CERTIFICATE);
        paramList.add(Constants.TRAINING_RETAKE);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;

    }

    public int getTotalEmployeeNeedRetake(long trainingId, int audianceCode){
        String sql = "SELECT COUNT(et.id) totalCount "
                + "FROM "
                + "employee_training et  "
                + "WHERE et.training_FK = ? "
                + (audianceCode == 0 ? "" : (audianceCode == 1? " AND  et.employee_master_ledger_id IS NOT NULL " : ""))
                + "AND et.status = ?";

        List paramList = new ArrayList();
        paramList.add(trainingId);
        paramList.add(Constants.TRAINING_RETAKE);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;

    }

    public int getTotalPolicyConfirmedByAudiance(int audianceCode,User user){
        String sql = "SELECT COUNT(pp.id) totalCount "
                + "FROM "
                + "policy_and_procedure pp "
                + "WHERE 1=1 "
                + (audianceCode == 0 ? "" : (audianceCode == 1? " AND (pp.vendor_master_ledger_FK IS NULL AND pp.employee_master_ledger_FK IS NOT NULL) " : " AND (pp.vendor_master_ledger_FK IS NOT NULL AND pp.employee_master_ledger_FK IS NULL) "))
                + (audianceCode == 0 ? "" : (audianceCode == 1? " AND pp.employee_master_ledger_FK = ? " : " AND  pp.vendor_master_ledger_FK = ?  "))
                + "AND pp.is_confirmed = 1";

//        logger.debug("SQL:"+sql);
        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getTotalPolicyNotConfirmedByAudiance(int audianceCode,User user){
        String sql = "SELECT COUNT(pp.id) totalCount "
                + "FROM "
                + "policy_and_procedure pp "
                + "WHERE 1=1 "
                + (audianceCode == 0 ? "" : (audianceCode == 1? " AND (pp.vendor_master_ledger_FK IS NULL AND pp.employee_master_ledger_FK IS NOT NULL) " : " AND (pp.vendor_master_ledger_FK IS NOT NULL AND pp.employee_master_ledger_FK IS NULL) "))
                + (audianceCode == 0 ? "" : (audianceCode == 1? " AND pp.employee_master_ledger_FK = ? " : " AND  pp.vendor_master_ledger_FK = ? "))
                + "AND pp.is_confirmed = 0";

//        logger.debug("SQL:"+sql);
        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getTotalNotificationSendToAudiance(int audianceCode){
        String sql = "SELECT COUNT(policy.id) totalCount "
                + "FROM "
                + "policy "
                + "WHERE policy.audiance_code =? OR policy.audiance_code = 0";

        List paramList = new ArrayList();
        paramList.add(audianceCode);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }
    public void updateTrainingOptionAnswer(List<String> questionAnswerSql) {
        for(String sql : questionAnswerSql) {
            jdbcTemplate.update(sql);
        }
    }

    public int getTotalCourseToBeStartByUser(User user){
        String sql = "SELECT COUNT(tr.id) totalCount "
                + "FROM training tr "
                + "WHERE tr.id "
                + "NOT IN( SELECT empTr.training_FK from employee_training empTr "
                + "        WHERE empTr.employee_master_ledger_id = ? "
                + "       ) " ;

        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getTotalCourseToBeRetakeOrPassedByUser(User user,String retakeOrPassed){
        String sql = "SELECT COUNT(tr.id) totalCount "
                + "from employee_training empTr "
                + "JOIN training tr ON(tr.id = empTr.training_FK) "
                + "WHERE empTr.employee_master_ledger_id = ? AND empTr.status = ? " ;

        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());
        paramList.add(retakeOrPassed);

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }
    public List getAllEmployee() {
       String sql = "SELECT id, first_name AS firstName, last_name AS lastName FROM employee_master_ledger";
        List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public List getAssignedEmployeeList(long txId){


       String sql = "SELECT emp.id, emp.first_name AS firstName, emp.last_name AS lastName " +
               " FROM employee_master_ledger emp " +
               " JOIN internal_audit ia ON (ia.employee_FK = emp.id) " +
               " WHERE ia.project_transaction_id = ? ";
        List paramList = new ArrayList();
        paramList.add(txId);

        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public List<Object> getMyTrxPartialDataList(int page, int rp,  String qtype, String query, String sortname, String sortorder,User user) {
        String sql = "";
        int start = (page - 1)*rp ;
            sql = "SELECT rp.id,COUNT(rp.id) no_of_transaction, rp.project_name,rp.status"
                    + " FROM internal_audit ia "
                    + " JOIN  real_time_transaction rt ON (rt.id = ia.project_transaction_id)"
                    + " JOIN  real_time_project rp ON(rp.id = rt.real_time_project_id)"
                    + " WHERE ia.employee_FK = ? "
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                    + " GROUP BY rp.id"
                    + " LIMIT ?, ?";
        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if (list != null && list.size() > 0)
            return list;
        return null;
    }

    public int getTotalMyTrx(String qtype, String query,User user ){
           String sql = "SELECT COUNT(rp.id) totalCount"
                    + " FROM internal_audit ia "
                    + " JOIN  real_time_transaction rt ON (rt.id = ia.project_transaction_id)"
                    + " JOIN  real_time_project rp ON(rp.id = rt.real_time_project_id)"
                    + " WHERE ia.employee_FK = ? "
                    + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):" ")
                    + " GROUP BY rp.id";

        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public List<Object> getMyRealTimeTransactionPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,long ruleId,User user){
        if(qtype.equals("transaction_date") || qtype.equals("trx.created") ){
            qtype = "DATE_FORMAT("+qtype+",\"%m/%d/%Y\") ";
        }
        int start = (page - 1)*rp ;
        String sql = "SELECT GROUP_CONCAT(strx.rule_code) rule_code,GROUP_CONCAT(strx.rule_title separator '#') rule_title,rtpt.id,trx.created createdDate, trx.transaction_date trxDate, FORMAT(trx.amount,2) amount,"
                + " rtpCND.decision,trx.id trxId, trx.approver,GROUP_CONCAT(strx.rule_explanation separator '#') rule_explanation"
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN internal_audit ia ON(ia.project_transaction_id = rtpt.id) "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? "JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)":" LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) "):"LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) ")
                + " WHERE 1=1 "
                + " AND ia.employee_FK=? "
                + (realTimeProjectId > 0 ? " AND  rtp.id = ? " : "")
                + (ruleId > 0 ? " AND strx.rule_id= ? " : "")
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"" )
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY rtpt.id"
                + (!Utils.isEmpty(sortname)? " ORDER BY " + sortname + " "+ sortorder:"")
                + " LIMIT ?,?";
        List paramList = new ArrayList();
        paramList.add(user.getUserTypeId());
        if(realTimeProjectId > 0)
            paramList.add(realTimeProjectId);
        if(ruleId > 0 )
            paramList.add(ruleId);
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public int getMyRealTimeTransactionCount(String controlIds,String controlCompareString,long realTimeProjectId,String qtype, String query,long ruleId,User user){
        String sql = "SELECT COUNT(innerSql.trxId) totalCount FROM(SELECT trx.id trxId "
                + " FROM"
                + " real_time_transaction rtpt "
                + " JOIN internal_audit ia ON(ia.project_transaction_id = rtpt.id) "
                + " JOIN real_time_project rtp ON(rtpt.real_time_project_id = rtp.id)"
                + " JOIN transaction trx ON(rtpt.transaction_id = trx.id)"
                + " LEFT JOIN suspicious_transaction strx ON(trx.job_id =  strx.job_id AND trx.id = strx.transaction_id )"
                +  (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? "JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id)":" LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) "):" LEFT JOIN real_time_transaction_CND rtpCND ON(rtpt.id = rtpCND.real_time_transaction_id) ")
                + " WHERE 1=1 "
                + " AND ia.employee_FK=? "
                + (realTimeProjectId > 0 ? " AND  rtp.id = ? " : "")
                + (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"" )
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE '%"+query+"%' "):"")
                + " GROUP BY rtpt.id"
                +") innerSql";

        List param = new ArrayList();
        param.add(user.getUserTypeId());
        param.add(realTimeProjectId);

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public List<Object> getMyRealTimeSummaryPartialDataList(String controlIds,String controlCompareString,long realTimeProjectId,int page, int rp,  String qtype, String query, String sortname, String sortorder,User user){
        int start = (page - 1)*rp ;
        String sql = " select count(st.transaction_id) totalTransaction"
                + ", st.rule_title rule"
                + ",ru.id ruleId"
                + ",GROUP_CONCAT(st.transaction_id) transactionIds "
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT "
                + "                     tx.id AS TransactionId "
                + "                     FROM real_time_transaction rtt "
                + "                     JOIN internal_audit ia ON(ia.project_transaction_id = rtt.id)"
                + "                     JOIN transaction tx ON (rtt.transaction_id = tx.id) "
                +                      (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? "JOIN real_time_transaction_CND rtpCND ON(rtt.id = rtpCND.real_time_transaction_id)":""):"")
                + "                     WHERE rtt.real_time_project_id = ? "
                + "                     AND ia.employee_FK = ?"
                +                      (!"0".equals(controlIds)? (!Utils.isEmpty(controlCompareString)? controlCompareString : ""):"" )
                +                      " ) "
                + (!Utils.isEmpty(query)? (" AND "+ qtype+" LIKE ? "):"")
                + " GROUP BY st.rule_id "
                + " ORDER BY " + sortname + " "+ sortorder
                + " LIMIT ?,?";

//        logger.debug("SMN: SQL="+sql);
        List paramList = new ArrayList();
        paramList.add(realTimeProjectId);
        paramList.add(user.getUserTypeId());
        if(!Utils.isEmpty(query)) {
            paramList.add("%"+query+"%");
        }
        paramList.add(start);
        paramList.add(rp);
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());

        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public int getMyRealTimeSummaryCount(String controlIds,String compareControlSql,long realTimeProjectId,User user){
        String sql = "SELECT COUNT(innerTable.ruleId)  totalCount "
                + "FROM (SELECT ru.id ruleId"
                + " from suspicious_transaction st "
                + " LEFT JOIN rule ru ON (ru.id = st.rule_id)"
                + " WHERE st.transaction_id IN ( SELECT "
                + "                     tx.id AS TransactionId "
                + "                     FROM real_time_transaction rtt "
                + "                     JOIN internal_audit ia ON(ia.project_transaction_id = rtt.id)"
                + "                     JOIN transaction tx ON (rtt.transaction_id = tx.id) "
                +                      (!"0".equals(controlIds)? (!Utils.isEmpty(compareControlSql)? "JOIN real_time_transaction_CND rtpCND ON(rtt.id = rtpCND.real_time_transaction_id)":""):"")
                + "                     WHERE rtt.real_time_project_id = ? "
                + "                     AND ia.employee_FK = ?"
                +                      (!"0".equals(controlIds)? (!Utils.isEmpty(compareControlSql)? compareControlSql : ""):"" )
                +                      " ) "
                + " GROUP BY st.rule_id ) innerTable";

        List param = new ArrayList();
        param.add(realTimeProjectId);
        param.add(user.getUserTypeId());

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }


    public List getAnalystUserList() {
        String sql = "SELECT * FROM user WHERE role = ? " ;

        List param = new ArrayList();
        param.add(Role.IA_ANALYST.getLabel());

        List list = jdbcTemplate.queryForList(sql, param.toArray());
        if(list != null && list.size() > 0) {

            return list;
        }
        return null;
    }

    public List getEmployeeWithNonUserList(){
        String sql = "SELECT eml.id, CONCAT(eml.first_name,' ',eml.last_name) empName, eml.email"
                + " FROM employee_master_ledger eml"
                + " WHERE eml.id  NOT IN(SELECT user.user_type_id FROM user WHERE user.user_type IS NOT NULL"
                + "                      AND user.user_type = 'Employee' AND user.user_type_id >0)"
                + " AND eml.email IS NOT NULL AND TRIM(eml.email) > '' ";

            List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public boolean isUserExistWithPersonTypeId(long personTypeId){
        String sql = "SELECT id From user WHERE user_type_id = ? ";
        List paramList = new ArrayList();
        paramList.add(personTypeId);

        List list = jdbcTemplate.queryForList(sql, paramList.toArray()) ;
        if(list != null && list.size() > 0 ){
            return false;
        }
        return true;
    }

    public String getEmployeeNameById(long id){
        String sql = "SELECT CONCAT(eml.first_name,' ',eml.last_name) empName "
                    + " FROM employee_master_ledger eml "
                    + " WHERE id = ? ";
        List paramList = new ArrayList();
        paramList.add(id);

        Map map = jdbcTemplate.queryForMap(sql, paramList.toArray()) ;
            return map.get("empName") != null ? (String)map.get("empName") : "";
    }

    public int getTotalOutstandingPolicyByUser(User user){
         String clause = "";
         List paramList = new ArrayList();


        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
            clause = " AND pp.employee_master_ledger_FK =? ";
            paramList.add(1);

        }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
            clause = " AND pp.vendor_master_ledger_FK =? ";
            paramList.add(2);
        } else if(PersonType.ADMIN.getValue().equals(user.getUserType())){
            clause = " AND pp.employee_master_ledger_FK =? ";
            paramList.add(0);
        }


        String sql = "SELECT COUNT(policy.id) totalCount "
                    + "FROM policy "
                    + "WHERE (policy.audiance_code = 0 OR policy.audiance_code = ? ) "
                    + "AND policy.id "
                    + "      NOT IN( SELECT pp.policy_FK from policy_and_procedure pp "
                    + "             WHERE 1 = 1 "
                    +               clause
                    + "            ) ";

        paramList.add(user.getUserTypeId());
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }

    public int getTotalSignedPolicyByUser(User user){
        String clause = "";
        List paramList = new ArrayList();


        if(PersonType.EMPLOYEE.getValue().equals(user.getUserType())){
            clause = " AND pp.employee_master_ledger_FK =? ";

        }else if(PersonType.VENDOR.getValue().equals(user.getUserType())){
            clause = " AND pp.vendor_master_ledger_FK =? ";
        } else if(PersonType.ADMIN.getValue().equals(user.getUserType())){
            clause = " AND pp.employee_master_ledger_FK =? ";
        }
        String sql =  "SELECT COUNT(policy.id) totalCount"
                + " FROM policy_and_procedure pp"
                + " JOIN policy ON(policy.id = pp.policy_FK)"
                + " WHERE 1 = 1 "
                +  clause
                + " AND pp.is_confirmed = 0";

        paramList.add(user.getUserTypeId());
        List list = jdbcTemplate.queryForList(sql, paramList.toArray());
        if(list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            return Integer.parseInt(map.get("totalCount").toString());
        }
        return 0;
    }


}
