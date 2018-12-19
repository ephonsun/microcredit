package banger.moduleIntf;

import banger.domain.config.AutoBaseField;
import banger.domain.loan.*;
import banger.framework.collection.DataSet;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

public interface ILoanApplyProvider {

	/**
	 * 得到贷款申请字段
	 * @return
	 */
	List<AutoBaseField> getApplyFieldList();

	/**
	 * 贷款类型列表
	 * @return
	 */
	List<LoanType> getAllLoanTypeList();

	/**
	 * 贷款合同类型列表
	 * @return
	 */
	List<LoanType> getAllLoanContractTypeList();

	LoanType getLoanTypeById(Integer typeId);
    /**
     * 通过主键得到贷款申请表
     * @param loanId 主键Id
     */
	LoanApplyInfo_Query getApplyInfoQueryById(Integer loanId);

	/**
	 * 通过主键得到贷款申请表
	 * @param loanId 主键Id
	 */
	LoanApplyInfo getApplyInfoById(Integer loanId);

	/**
	 * 得到贷款申请模板
	 * @return
	 */
	List<LoanApplyTemplate> getApplyTemplateList(Integer loanTypeId,String processType);
	
	/**
	 * 得到贷款模板
	 */
	List<LoanApplyTemplate> getApplyTemplateList(Integer loanTypeId,String processType,Integer loanId);
	
	/**
	 * 得到贷款申请模板
	 * @return
	 */
	List<LoanApplyTemplate> getApplyTemplateFieldList(Integer loanTypeId,String processType);

	/**
	 * 得到贷款审批决议模板
	 * @return
	 */
	public LoanApplyTemplate getAuditTemplateField(Integer tableId);
	
	/**
	 * 得到贷款申请模板
	 * @return
	 */
	LoanApplyTemplate getApplyTemplateField(Integer loanTypeId,String processType,Integer templateId);

	/**
	 * 得到贷款审核模板
	 * @return
	 */
	LoanApplyTemplate getTemplateFieldByProcessId(Integer processId);
	
	/**
	 *  得到贷款申请模板数据
	 * @return
	 */
	DataTable getApplyTemplateData(Integer loanTypeId,String processType,Integer templateId,Integer loanId);
	
	/**
	 * 得到贷款申请所有模板数据
	 * @param loanTypeId
	 * @param processType
	 * @param loanId
	 * @return
	 */
	DataSet getApplyTemplateDataSet(Integer loanTypeId,String processType, Integer loanId);

	/**
	 * 得到贷款申请所有模板数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	DataTable getLoanTemplateDataById(String tableName,Integer id);

	/**
	 * 删除贷款申请所有模板数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	void deleteLoanTemplateDataById(String tableName,Integer id);

	/**
	 * 得到贷款申请所有模板数据
	 * @param processType
	 * @param tableName
	 * @param loanId
	 * @return
	 */
	DataTable getLoanTemplateDataById(String processType,String tableName,Integer loanId);
	
	/**
	 * 保存贷款申请信息
	 * @param applyInfo
	 */
	void saveApplyInfo(LoanApplyInfo applyInfo);

	/**
	 * 保存贷款申请信息
	 * @param applyInfo
	 */
	void saveApplyInfo(LoanApplyInfo applyInfo,Integer marketCustomerId);

	/**
	 * 更新贷款评分得分
	 * @param applyInfo
	 */
	void updateLoanModelScore(LoanApplyInfo applyInfo);

	/**
	 * 拒绝贷款申请信息
     * @param applyInfo
     * @param userId
     */
	void refuseApplyInfo(LoanApplyInfo applyInfo, Integer userId);

	/**
	 * 提交贷款申请信息
	 * @param applyInfo
	 */
	String submitLoanApplyInfo(LoanApplyInfo applyInfo);

	/**
	 * 退回贷款信息
	 * @param loanId
	 * @param loanProcessType
	 * @param remark
	 * @return
	 */
	String gobackLoanApplyInfo(Integer loanId,String loanProcessType,Integer loginUserId, String remark);
	
	/**
	 * 保存代款模板信息
	 * @param table
	 */
	Integer saveLoanTemplateInfo(DataTable table);

	/**
	 * 保存代款模板信息
	 * @param table
	 */
	Integer[] saveLoanTemplateInfo(DataTable table,Boolean flag);
	
	/**
	 * 得到贷款申请列表
	 * @return
	 */
	List<LoanApplyInfo> getLoanApplyList(Integer userId,IPageSize page);
	
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
	List<LoanApplyInfo> getLoanApplyInfoListByCustomerId(Integer customerId,Integer userId);

	/**
	 * 查询审核状态
	 * @param loanId
	 * @return
	 */
	List<LoanAuditProcess> getLoanAuditProcessByLoanId(Integer loanId);

	/**
	 * 得到当前审核状态
	 * @param loanId
	 * @param processId
	 * @return
	 */
	List<LoanCurrentAuditStatus> getLoanAuditStatusListById(Integer loanId,Integer processId);

	/**
	 * 得到工作流环节
	 * @param flowId
	 * @param paramId
	 * @return
	 */
	List<WfApproveProcess> getApproveProcessListByFlowId(Integer flowId,Integer paramId);

	/**
	 * 检查字段必填项
	 */
	Integer checkTemplateRequireField(Integer loanTypeId,String processType,Integer loanId);


	/**
	 * 根据条件查询贷款
	 */
	public List<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition);

	/**
	 *
	 * @param applyInfo
	 */
	void updateModelScoreByLoanId(LoanApplyInfo applyInfo);

    /**
     * 提前产生自定义表Id
     * @param tableName 表名
     * @return
     */
    Integer newLbizId(String tableName);

    /**
     * 等额本息，等额本金生成还款计划
     * @param loanId
     * @param loginUserId
     */
    void creatRepayPlanInfo(Integer loanId, Integer loginUserId);

	/**
	 *修改贷款申请表
	 * @param applyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateApplyInfo(LoanApplyInfo applyInfo,Integer loginUserId);
}
