package banger.dao.impl;

import banger.dao.intf.IApplyInfoDao;
import banger.domain.loan.*;
import banger.domain.sub.BizTypeSub;
import banger.domain.sub.CrmPrdType;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.sql.util.ISqlEngine;
import banger.framework.sql.util.SqlEngine;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款申请表数据访问类
 */
@Repository
public class ApplyInfoDao extends PageSizeDao<LoanApplyInfo> implements IApplyInfoDao {

	public Integer getNewId() {
		return this.newId().intValue();
	}
	/**
	 * 新增贷款申请表
	 * @param applyInfo 实体对像
	 */
	public void insertApplyInfo(LoanApplyInfo applyInfo){
		applyInfo.setLoanId(this.newId().intValue());
		this.execute("insertApplyInfo",applyInfo);
	}
	
	/**
	 * android 贷款申请
	 * @param applyInfo
	 */
	public void insertApplyInfoStepApply(LoanApplyInfo applyInfo){
		applyInfo.setLoanId(this.newId().intValue());
		this.execute("insertApplyInfoStepApply",applyInfo);
	}

	/**
	 *修改贷款申请表
	 * @param applyInfo 实体对像
	 */
	public void updateApplyInfo(LoanApplyInfo applyInfo){
		this.execute("updateApplyInfo",applyInfo);
	}
	
	/**
	 * 同步更新贷款数据
	 * @param table
	 */
	public void updateApplyInfo(DataTable table){
		this.updateData(table, "LOAN_ID");
	}

	/**
	 * 通过主键删除贷款申请表
	 * @param loanId 主键Id
	 */
	public void deleteApplyInfoById(Integer loanId){
		this.execute("deleteApplyInfoById",loanId);
	}
	
	/**
	 * 更新客户ID
	 * @param customerId
	 * @param loanId
	 */
	public void updateCustomerIdById(Integer customerId,Integer loanId){
		this.execute("updateCustomerIdById",customerId,loanId);
	}

	/**
	 * 更新贷款评分项得分
	 */
	public void updateModelScoreByLoanId(LoanApplyInfo applyInfo) {
		this.execute("updateModelScoreByLoanId",applyInfo);
	}

	/**
	 * 通过主键得到贷款申请表
	 * @param loanId 主键Id
	 */
	public LoanApplyInfo getApplyInfoById(Integer loanId){
		LoanApplyInfo loanApplyInfo = (LoanApplyInfo)this.queryEntity("getApplyInfoById",loanId);
		return loanApplyInfo;
	}

	/**
	 * 修改贷款审批状态
	 *
	 */
	public void updateLoanAuditState(LoanApplyInfo loanApplyInfo){
		this.execute("updateLoanAuditState",loanApplyInfo);
	}
	
	/**
	 * 判断是不是重复提交
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LoanApplyInfo getExistApplyInfo(LoanApplyInfo applyInfo){
		List<LoanApplyInfo> list =  (List<LoanApplyInfo>)this.queryEntities("getExistApplyInfo",applyInfo);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 安卓查询贷款申请列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanApplyInfo> getLoanApplyList(Map<String,Object> condition,IPageSize page){
		return (List<LoanApplyInfo>)this.queryEntities("getLoanApplyList",page,new Object[]{condition});
	}

	/**
	 * 查询贷款申请表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition){
		return (List<LoanApplyInfo>)this.queryEntities("queryApplyInfoList", condition);
	}

	/**
	 * 分页查询贷款申请表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanApplyInfo>)this.queryEntities("queryApplyInfoList", page, condition);
	}
	
	/**
	 * 得到客户贷款列表
	 * @param customerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanApplyInfo> getLoanApplyInfoListByCustomerId(Integer customerId,Integer userId){
		Map<String,Object> condtion = new HashMap<String, Object>();
		condtion.put("customerId",customerId);
		condtion.put("userId",userId);
		return (List<LoanApplyInfo>)this.queryEntities("getLoanApplyInfoListByCustomerId", condtion);
	}

	@SuppressWarnings("unchecked")
	public IPageList<LoanApplyInfo> queryApprovalList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<LoanApplyInfo>)this.queryEntities("queryApprovalList", page, condition);
	}
	
	   /**
     * 通过主键得到贷款申请表
     * @param loanId 主键Id
     */
	public LoanApplyInfo_Query getApplyInfoQueryById(Integer loanId){
		return (LoanApplyInfo_Query)this.queryEntity("getApplyInfoQueryById",loanId);
	}

	/**
	 * 查询待分配列表
	 * @param condition
	 * @param page
	 * @return
	 */

	public IPageList<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String,Object> condition, IPageSize page){
		return (IPageList<LoanApplyInfo_Web_Query>)this.queryEntities("queryAllInfoList", page, condition);
	}

	public IPageList<LoanApplyInfo_Web_Query> queryCollectionList(Map<String,Object> condition, IPageSize page){
		return (IPageList<LoanApplyInfo_Web_Query>)this.queryEntities("queryCollectionList", page, condition);
	}

	/**
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public List<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String,Object> condition){
		return (List<LoanApplyInfo_Web_Query>)this.queryEntities("queryAllInfoList", condition);
	}


	public LoanApplyInfo_Web_Query queryOneSubInfo(Integer loanId){
		return (LoanApplyInfo_Web_Query)this.queryEntity("queryOneSubInfo", loanId);
	}


	public DataTable getDataTableById(String tableName, Integer id){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT * FROM "+tableName+" WHERE ID="+id);
		DataTable table = ish.getDataTable(sql.toString());
		ish.dispose();
		return table;
	}

	public DataTable getDataTableByLoanId(String tableName,  String presType, Integer id){
		return getDataTableByLoanIdAndUserId(tableName, presType, id, null);
	}

	public DataTable getApprovalDataTableByLoanId(Integer loanId){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT * FROM LBIZ_APPROVAL_RESOLUTION WHERE IS_MASTER = 1 and  LOAN_ID="+loanId);
		DataTable table = ish.getDataTable(sql.toString());
		ish.dispose();
		return table;
	}

	public DataTable selectApprovalDataTableByLoanId(Integer loanId){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT * FROM LBIZ_APPROVAL_RESOLUTION WHERE LOAN_ID="+loanId+ " ORDER BY ID DESC fetch first 1 rows only");
		DataTable table = ish.getDataTable(sql.toString());
		ish.dispose();
		return table;
	}

	//mortgageOrPledge 1. 抵押    2. 质押
	public DataTable getPledgeDataTableByLoanId(String presType, Integer loanId, Integer mortgageOrPledge){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT * FROM LBIZ_PLEDGE_ITEM WHERE LOAN_ID="+loanId);
		if (StringUtils.isNotBlank(presType))
			sql.append(" AND LOAN_PROCESS_TYPE='" + presType + "'");
		if (null!=mortgageOrPledge)
			sql.append(" AND MORTGAGE_OR_PLEDGE='" + mortgageOrPledge + "'" );
		DataTable table = ish.getDataTable(sql.toString());
		ish.dispose();
		return table;
	}


	// 根据受托支付状态查询受托支付列表
	@Override
	public DataTable getTrustedDataTableByLoanId(String presType, Integer loanId, Integer paymentStatus){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT * FROM LBIZ_TRUSTED_PAYMENY WHERE 1 = 1 ");
		if (null!=loanId&&loanId>0)
			sql.append(" AND LOAN_ID=" + loanId );
		if (null!=paymentStatus)
			sql.append(" AND PAYMENT_STATUS='" + paymentStatus + "'");
		if (StringUtils.isNotBlank(presType))
			sql.append(" AND LOAN_PROCESS_TYPE='" + presType + "'");
		DataTable table = ish.getDataTable(sql.toString());
		ish.dispose();
		return table;
	}

	// 更新受托支付状态
	@Override
	public void updateTrustedDataTableById(String presType, String id, Integer paymentStatus){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("UPDATE LBIZ_TRUSTED_PAYMENY SET");
		if (null!=id)
			sql.append(" ID=" + id );
		if (null!=paymentStatus)
			sql.append(" ,PAYMENT_STATUS='" + paymentStatus + "'");
		if (StringUtils.isNotBlank(presType))
			sql.append(" ,LOAN_PROCESS_TYPE='" + presType  + "'");

		sql.append("  WHERE ID=" + id  );

		ish.executeNoneQuery(sql.toString());
		ish.dispose();
	}


	// 更新调查建议表单 LBIZ_SURVEY_RESULT
//	贷款投向	LOAN_ORIENTATION
//	投向名称	ORIENTATION_NAME
//
//	业务品种	BIZ_TYPE
//	业务品种名称	BIZ_TYPE_NAME
//	产品自定义名称	PRD_USERDF_NAME
//	产品名称	PRD_NAME
//	产品主键	PRD_PK
//	核心产品号	ACCOUNT_CLASS
//	核心产品名称	ACCOUNT_CLASS_NAME
//	业务品种分类名称	BIZ_TYPE_DETAIL

//	业务条线	BUSINESS_LINE
//	业务条线名称	BUSINESS_LINE_NAME
//	主要产品分类	MAIN_PRO_TYPE
//	主要产品分类名称	MAIN_PRO_TYPE_NAME

	@Override
	public void updateSurveyDataTableById(String loanId, Map<String,String> map){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("UPDATE LBIZ_SURVEY_RESULT SET");
		if (null!=loanId)
			sql.append(" LOAN_ID=" + loanId );
		if (StringUtils.isNotBlank(MapUtils.getString(map,"loanOrientation")))
			sql.append(" ,LOAN_ORIENTATION='" + MapUtils.getString(map,"loanOrientation") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"orientationName")))
			sql.append(" ,ORIENTATION_NAME='" + MapUtils.getString(map,"orientationName") + "'");

		if (StringUtils.isNotBlank(MapUtils.getString(map,"bizType")))
			sql.append(" ,BIZ_TYPE='" + MapUtils.getString(map,"bizType") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"bizTypeName")))
			sql.append(" ,BIZ_TYPE_NAME='" + MapUtils.getString(map,"bizTypeName") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"prdUserdfName")))
			sql.append(" ,PRD_USERDF_NAME='" + MapUtils.getString(map,"prdUserdfName") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"prdName")))
			sql.append(" ,PRD_NAME='" + MapUtils.getString(map,"prdName") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"prdPk")))
			sql.append(" ,PRD_PK='" + MapUtils.getString(map,"prdPk") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"accountClass")))
			sql.append(" ,ACCOUNT_CLASS='" + MapUtils.getString(map,"accountClass") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"accountClassName")))
			sql.append(" ,ACCOUNT_CLASS_NAME='" + MapUtils.getString(map,"accountClassName") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"bizTypeDetail")))
			sql.append(" ,BIZ_TYPE_DETAIL='" + MapUtils.getString(map,"bizTypeDetail") + "'");

		if (StringUtils.isNotBlank(MapUtils.getString(map,"businessLine")))
			sql.append(" ,BUSINESS_LINE='" + MapUtils.getString(map,"businessLine") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map, "businessLineName")))
			sql.append(" ,BUSINESS_LINE_NAME='" + MapUtils.getString(map,"businessLineName") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"mainProType")))
			sql.append(" ,MAIN_PRO_TYPE='" + MapUtils.getString(map,"mainProType") + "'");
		if (StringUtils.isNotBlank(MapUtils.getString(map,"mainProTypeName")))
			sql.append(" ,MAIN_PRO_TYPE_NAME='" + MapUtils.getString(map,"mainProTypeName") + "'");

		sql.append("  WHERE LOAN_ID=" + loanId  );

		ish.executeNoneQuery(sql.toString());
		ish.dispose();
	}


	@Override
	public DataTable getPledgeDataTableById(Integer id) {
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT * FROM LBIZ_PLEDGE_ITEM WHERE ID="+id);
		DataTable table = ish.getDataTable(sql.toString());
		ish.dispose();
		return table;
	}

	@Override
	public void updatePledgeStatusById(Integer id,String status) {
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		ish.executeNoneQuery("UPDATE  LBIZ_PLEDGE_ITEM SET  PLEDGE_STATUS="+status+" WHERE ID="+id);
	}

	@Override
	public void updatePledgeById(PledgInfo pledgInfo) {
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		ish.executeNoneQuery("UPDATE  LBIZ_PLEDGE_ITEM SET  OTHER_CRED_NO='"+pledgInfo.getTxqzh()+
				"', RIGHT_ORG='"+pledgInfo.getQsdjjg()+"', INPUT_DATE= '"+pledgInfo.getDjrq()+"', INPUT_BR_ID= '"+pledgInfo.getDjjg()+"', CREATE_USER_NO='"+pledgInfo.getDjr()+
				"', ASSURANCE_TYPE='"+pledgInfo.getTbxz()+"', ASSURANCE_NO= '"+pledgInfo.getDbbh()+"', ASSURANCE_AMT='"+pledgInfo.getDbje()+"',CO_NAME='"+pledgInfo.getDbgsmc()+
				"', ASSURANCE_DATE= '"+pledgInfo.getTbrq()+"', END_DATE='"+pledgInfo.getTbdqrq()+"' WHERE ID="+pledgInfo.getId());
	}

	public DataTable getDataTableByLoanIdAndUserId(String tableName, String presType, Integer loanId, Integer loginUserId){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT * FROM "+tableName+" WHERE LOAN_ID="+loanId);
		if (StringUtils.isNotBlank(presType))
			sql.append(" AND LOAN_PROCESS_TYPE='" + presType + "'");
		if (loginUserId != null)
			sql.append(" AND create_user=" + loginUserId);
			sql.append(" order by id asc ");
		DataTable table = ish.getDataTable(sql.toString());
		ish.dispose();
		return table;
	}

	/**
	 * 根据表名删除模板数据
	 * @param tableName
	 * @param id
	 */
	public void deleteTemplateByNameAndId(String tableName, Integer id) {
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		ish.executeNoneQuery("DELETE  FROM "+tableName+" WHERE ID="+id);
	}

	public void deleteTemplateByNameAndLoanId(String tableName, String presType, Integer loanId, Integer id) {
		DataTable dataTable = getDataTableByLoanId(tableName, presType, loanId);
		for (DataRow row : dataTable.getRows()) {
			Integer oldId = (Integer) row.get("ID");
			if (id.intValue() != oldId.intValue()){
				deleteTemplateByNameAndId(tableName, oldId);
			}
		}
	}

	/**
	 * 更新监控状态
	 * @param loanId
	 * @param loanMonitorState
	 * @param loanMonitorType
	 * @param loanMonitorDate
	 */
	public void updateLoanMonitorState(Integer loanId,String loanMonitorState,String loanMonitorType,Date loanMonitorDate){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loanId",loanId);
		map.put("loanMonitorState",loanMonitorState);
		map.put("loanMonitorType",loanMonitorType);
		map.put("loanMonitorDate",loanMonitorDate);
		this.execute("updateLoanMonitorState",map);
	}

	/**
	 * 更新催收状态
	 * @param loanId
	 * @param loanCollectionState
	 * @param loanCollectionDate
	 * @param loanRepayPlanDate
	 */
	public void updateLoanCollectionState(Integer loanId,String loanCollectionState,Date loanCollectionDate,Date loanRepayPlanDate){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loanId",loanId);
		map.put("loanCollectionState",loanCollectionState);
		map.put("loanCollectionDate",loanCollectionDate);
		map.put("loanRepayPlanDate",loanRepayPlanDate);
		this.execute("updateLoanCollectionState",map);
	}

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param flowId
	 */
	public void resetLoanFlowByFlowId(Integer flowId){
		this.execute("resetLoanFlowByFlowId",flowId);
	}

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param processId
	 */
	public void resetLoanFlowByProcessId(Integer processId){
		this.execute("resetLoanFlowByProcessId",processId);
	}

    public List<LoanApplyInfo> queryHandOverLoanIds(Map<String, Object> condition) {
		return (List<LoanApplyInfo>) this.queryEntities("queryHandOverLoanIds",condition);
    }
    
  /**
	 * 修改贷款状态
	 * @param loanId
	 * @param afterState
	 */
	public void updateLoanAfterState(Integer loanId,String afterState){
		this.execute("updateLoanAfterState",new Object[]{loanId,afterState});
	}

    public IPageList<LoanApplyInfo_Web_Query> queryLoanAuditList(Map<String, Object> condition, IPageSize page) {
        return (IPageList<LoanApplyInfo_Web_Query>) this.queryEntities("queryLoanAuditList",page,condition);
    }

    public void updateApplyInfoIgnoreNull(LoanApplyInfo applyInfoById) {
		this.execute("updateApplyInfoIgnoreNull",applyInfoById);
    }


    public Integer queryFirstMonitorDay() {
       return this.queryInt("getFirstMonitorDay");
    }

	public Integer queryNormalMonitorDay() {
		return this.queryInt("queryNormalMonitorDay");
	}

	public Integer queryConcernMonitorDay() {
		return this.queryInt("queryConcernMonitorDay");
	}

	public Integer queryRepayPlanValue() {
		return this.queryInt("queryRepayPlanDay");
	}

	public Integer queryOperationCode(){
		return this.queryInt("queryOperationCode");
	}

	public void resetOperationCode(){
		this.execute("resetOperationCode");
	}

	public void updateOperationCode(){
		this.execute("updateOperationCode");
	}

	public Integer querySerialCode(){
		return this.queryInt("querySerialCode");
	}

	public void resetSerialCode(){
		this.execute("resetSerialCode");
	}

	public void updateSerialCode(){
		this.execute("updateSerialCode");
	}


	public boolean checkLoanType(Integer typeId) {
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		String sql = "SELECT COUNT(*) AS COUNT FROM LOAN_APPLY_INFO WHERE LOAN_TYPE_ID="+ typeId;
		Map<String, Object> map = ish.getMap(sql);
		if (map == null) {
			return false;
		} else {
			Integer i = (Integer)map.get("COUNT");
			if (i.intValue() > 0)
				return true;
			else
				return false;
		}
	}

	public List<LoanApplyInfo_Web_Query> queryMinInGroup(Integer teamGroupId) {
		String allotConfigStatus = (String) this.queryValue("queryLoanAllotConfigStatus");
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("teamGroupId",teamGroupId);
		if(allotConfigStatus != null && !"".equals(allotConfigStatus)){
			condition.put("allotConfigStatus",allotConfigStatus);
		}
		return (List<LoanApplyInfo_Web_Query>)this.queryEntities("queryMinInGroup",condition);
	}

	@Override
	public LoanApplyInfo getApplyInfoByLoanAccount(String loanAccount) {
		return (LoanApplyInfo)this.queryEntity("getApplyInfoByLoanAccount",loanAccount);
	}

	@Override
	public String selectLoanBackModeByLoanId(Integer loanId) {
		return (String) this.queryValue("getLoanBackModeByLoanId",loanId);
	}

	@Override
	public String selectUnitByTableNameAndColumnName(String tableName, String columnName) {
		return (String) this.queryValue("selectUnitByTableNameAndColumnName",tableName, columnName);
	}

	/**
	 * 是否启用分配受限表
	 * @return
	 */
	@Override
	public String queryLoanAllotConfigStatus() {
		return (String) this.queryValue("queryLoanAllotConfigStatus");
	}

	@Override
	public List<DepositBank> getDepositBankListByCondition(Map<String,Object> condition){
		return (List<DepositBank>)this.queryEntities("getDepositBankListByCondition",condition);
	}

	@Override
	public  List<LoanOrientation> getLoanOrientationList(){
		return (List<LoanOrientation>)this.queryEntities("getLoanOrientationList");
	}

	@Override
	public List<BizTypeSub> queryTypeSubList(Map<String,Object> condition){
		return (List<BizTypeSub>)this.queryEntities("queryTypeSubList", condition);
	}

	@Override
	public List<CrmPrdType> queryCrmPrdType(Map<String, Object> condition) {
		return (List<CrmPrdType>)this.queryEntities("queryCrmPrdType", condition);
	}

}
