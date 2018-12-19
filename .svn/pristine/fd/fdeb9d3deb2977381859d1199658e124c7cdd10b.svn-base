package banger.dao.intf;

import banger.domain.loan.*;
import banger.domain.sub.BizTypeSub;
import banger.domain.sub.CrmPrdType;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 贷款申请表数据访问接口
 */
public interface IApplyInfoDao {


	Integer getNewId();
	/**
	 * 新增贷款申请表
	 * @param applyInfo 实体对像
	 */
	void insertApplyInfo(LoanApplyInfo applyInfo);
	
	/**
	 * android 贷款申请
	 * @param applyInfo
	 */
	void insertApplyInfoStepApply(LoanApplyInfo applyInfo);

	/**
	 *修改贷款申请表
	 * @param applyInfo 实体对像
	 */
	void updateApplyInfo(LoanApplyInfo applyInfo);
	
	/**
	 * 判断是不是重复提交
	 * @return
	 */
	LoanApplyInfo getExistApplyInfo(LoanApplyInfo applyInfo);
	
	/**
	 * 同步更新贷款数据
	 * @param table
	 */
	void updateApplyInfo(DataTable table);

	/**
	 * 通过主键删除贷款申请表
	 * @param loanId 主键Id
	 */
	void deleteApplyInfoById(Integer loanId);
	
	/**
	 * 更新客户ID
	 * @param customerId
	 * @param loanId
	 */
	void updateCustomerIdById(Integer customerId,Integer loanId);

	/**
	 * 更新贷款评分项得分
	 * @param
	 */
	void updateModelScoreByLoanId(LoanApplyInfo applyInfo);

	/**
	 * 通过主键得到贷款申请表
	 * @param loanId 主键Id
	 */
	LoanApplyInfo getApplyInfoById(Integer loanId);

	/**
	 * 提交贷款
	 */
	void updateLoanAuditState(LoanApplyInfo loanApplyInfo);
	
	/**
     * 通过主键得到贷款申请表
     * @param loanId 主键Id
     */
	LoanApplyInfo_Query getApplyInfoQueryById(Integer loanId);
	
	/**
	 * 得到贷款申请列表
	 * @return
	 */
	List<LoanApplyInfo> getLoanApplyList(Map<String,Object> condition,IPageSize page);
	
	/**
	 * 得到客户贷款列表
	 * @param customerId
	 * @return
	 */
	List<LoanApplyInfo> getLoanApplyInfoListByCustomerId(Integer customerId,Integer uesrId);

	/**
	 * 查询贷款申请表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition);

	/**
	 * 分页查询贷款申请表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition,IPageSize page);
	
	/**
	 * 查询审批列表
	 * @param condition
	 * @param page
	 * @return
	 */
	IPageList<LoanApplyInfo> queryApprovalList(Map<String,Object> condition,IPageSize page);

	/**
	 * 查询贷款列表
	 * @param condition
	 * @param page
	 * @return
	 */
	IPageList<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String,Object> condition, IPageSize page);


	List<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String, Object> condition);

	/**
	 * 根据主键查询子类，别名，主要包括归属客户经理，团队名称
	 * @param loanId
	 * @return
	 */
	LoanApplyInfo_Web_Query queryOneSubInfo(Integer loanId);

	/**
	 * 根据自定义表名和主键获取数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	DataTable getDataTableById(String tableName, Integer id);
	/**
	 * 根据表名和贷款流程获取当前流程的数据
	 * @param tableName
	 * @param presType
	 * @param id
	 * @return
	 */
	DataTable getDataTableByLoanId(String tableName, String presType, Integer id);


	public DataTable getTrustedDataTableByLoanId(String presType, Integer loanId, Integer paymentStatus);

	/**
	 * 根据贷款id 查出审批决议主记录
	 * @param loanId
	 * @return
	 */
	DataTable getApprovalDataTableByLoanId(Integer loanId);

	DataTable selectApprovalDataTableByLoanId(Integer loanId);
	/**
	 * 根据自定义表单信息,贷款信息ID和创建用户ID获取表数据
	 * @param tableName
	 * @param loanId
	 * @return
	 */
	DataTable getDataTableByLoanIdAndUserId(String tableName, String presType, Integer loanId, Integer loginUserId);

	/**
	 * 根据模板的表名和id删除数据
	 * @param tableName
	 * @param id
	 */
	void deleteTemplateByNameAndId(String tableName, Integer id);

	/**
	 * 根据自定义表名和贷款id删除原有信息
	 * @param tableName
	 * @param presType
	 * @param loanId
	 * @param id
	 */
	void deleteTemplateByNameAndLoanId(String tableName, String presType, Integer loanId, Integer id);

	/**
	 * 更新监控状态
	 * @param loanId
	 * @param loanMonitorState
	 * @param loanMonitorType
	 * @param loanMonitorDate
	 */
	void updateLoanMonitorState(Integer loanId,String loanMonitorState,String loanMonitorType,Date loanMonitorDate);

	/**
	 * 更新催收状态
	 * @param loanId
	 * @param loanCollectionState
	 * @param loanCollectionDate
	 * @param loanRepayPlanDate
	 */
	void updateLoanCollectionState(Integer loanId,String loanCollectionState,Date loanCollectionDate,Date loanRepayPlanDate);

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param flowId
	 */
	void resetLoanFlowByFlowId(Integer flowId);

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param processId
	 */
	void resetLoanFlowByProcessId(Integer processId);

    List<LoanApplyInfo> queryHandOverLoanIds(Map<String, Object> condition);
    
    	/**
	 * 修改贷款状态
	 * @param loanId
	 * @param afterState
	 */
	void updateLoanAfterState(Integer loanId,String afterState);

    IPageList<LoanApplyInfo_Web_Query> queryLoanAuditList(Map<String, Object> condition, IPageSize page);

    void updateApplyInfoIgnoreNull(LoanApplyInfo applyInfoById);

	Integer queryFirstMonitorDay();

	Integer queryNormalMonitorDay();

	Integer queryConcernMonitorDay();

	Integer queryRepayPlanValue();

	Integer queryOperationCode();

	void resetOperationCode();

	void updateOperationCode();

	public Integer querySerialCode();

	public void resetSerialCode();

	public void updateSerialCode();


    boolean checkLoanType(Integer typeId);

    List<LoanApplyInfo_Web_Query> queryMinInGroup(Integer teamGroupId);

	/**
	 * 通过贷款账户得到贷款申请表
	 * @param loanAccount
	 */
	LoanApplyInfo getApplyInfoByLoanAccount(String loanAccount);

    String selectLoanBackModeByLoanId(Integer loanId);


	String selectUnitByTableNameAndColumnName(String tableName, String columnName);

	/**
	 * 是否使用分配受限表
	 * @return
	 */
	String queryLoanAllotConfigStatus();

	DataTable getPledgeDataTableByLoanId(String presType, Integer loanId, Integer mortgageOrPledge);

	// 更新受托支付状态
	void updateTrustedDataTableById(String presType, String id, Integer paymentStatus);

	// 更新调查建议表单 LBIZ_SURVEY_RESULT
	void updateSurveyDataTableById( String loanId, Map<String,String> map);

	DataTable getPledgeDataTableById(Integer id);

	void updatePledgeStatusById(Integer id,String status);

	void updatePledgeById(PledgInfo pledgInfo);

	List<DepositBank> getDepositBankListByCondition(Map<String,Object> condition);

	List<LoanOrientation> getLoanOrientationList();

	List<BizTypeSub> queryTypeSubList(Map<String,Object> condition);

	List<CrmPrdType> queryCrmPrdType(Map<String,Object> condition);

	IPageList<LoanApplyInfo_Web_Query> queryCollectionList(Map<String, Object> condition, IPageSize page);
}
