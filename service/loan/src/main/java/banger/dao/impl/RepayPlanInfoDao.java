package banger.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanRepayPlanInfoExtend;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IRepayPlanInfoDao;
import banger.domain.loan.LoanRepayPlanInfo;

/**
 * 贷款还信催款息表数据访问类
 */
@Repository
public class RepayPlanInfoDao extends PageSizeDao<LoanRepayPlanInfo> implements IRepayPlanInfoDao {

	/**
	 * 新增贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 */
	public void insertRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo){
		repayPlanInfo.setId(this.newId().intValue());
		this.execute("insertRepayPlanInfo",repayPlanInfo);
	}

	/**
	 *修改贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 */
	public void updateRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo){
		this.execute("updateRepayPlanInfo",repayPlanInfo);
	}

	/**
	 * 通过主键删除贷款还信催款息表
	 * @param id 主键Id
	 */
	public void deleteRepayPlanInfoById(Integer id){
		this.execute("deleteRepayPlanInfoById",id);
	}

	/**
	 * 通过主键得到贷款还信催款息表
	 * @param id 主键Id
	 */
	public LoanRepayPlanInfo getRepayPlanInfoById(Integer id){
		return (LoanRepayPlanInfo)this.queryEntity("getRepayPlanInfoById",id);
	}

	/**
	 * 得到第一批还款计录
	 * @param loanId
	 * @return
	 */
	public LoanRepayPlanInfo getTopLoanRepayPlanInfo(Integer loanId){
		List<LoanRepayPlanInfo> list = (List<LoanRepayPlanInfo>)this.queryTopEntities("getTopLoanRepayPlanInfo",1,loanId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 得到还款计划
	 * @param loanId
	 * @return
	 */
	public List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId){
		return (List<LoanRepayPlanInfo>)this.queryEntities("getLoanRepayPlanInfoListByLoanId",loanId);
	}

	/**
	 * app修改状态
	 */
	public void updateLoanCollectionInfo(LoanRepayPlanInfo loanRepayPlanInfo){
		this.execute("updateLoanCollectionInfo",loanRepayPlanInfo);
	}

	/**
	 * 查询贷款还信催款息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanRepayPlanInfo> queryRepayPlanInfoList(Map<String,Object> condition){
		return (List<LoanRepayPlanInfo>)this.queryEntities("queryRepayPlanInfoList", condition);
	}

	/**
	 * 分页查询贷款还信催款息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanRepayPlanInfo> queryRepayPlanInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanRepayPlanInfo>)this.queryEntities("queryRepayPlanInfoList", page, condition);
	}

	/**
	 * 查询催款列表
	 * @param loanId
	 * @param repaymentMode
     * @param processType
     * @return
	 */
    public List<LoanRepayPlanInfoExtend> queryLoanRepayPlanInfoByLoanId(Integer loanId, String repaymentMode, String processType) {
    	Map<String,Object> condition = new HashMap<String, Object>();
    	condition.put("loanId",loanId);
		condition.put("repaymentMode",repaymentMode);
		condition.put("loanProcessType",processType);
    	condition.put("nowDate", new Date());
        return (List<LoanRepayPlanInfoExtend>) this.queryEntities("queryLoanRepayPlanInfoByLoanId",condition);
    }

    public List<LoanRepayPlanInfo> queryListByLoanRepayPlanDate(Map<String, Object> condition) {
		return (List<LoanRepayPlanInfo>) this.queryEntities("checkLoanRepayPlanDateRuleAdd",condition);
    }

    public List<LoanRepayPlanInfo> queryNextPlan(Map<String, Object> condition) {
        return (List<LoanRepayPlanInfo>) this.queryEntities("queryNextPlan",condition);
    }

    public List<LoanRepayPlanInfo> queryUnConllection(Map<String, Object> condition) {
    	return (List<LoanRepayPlanInfo>) this.queryEntities("queryUnConllection",condition);
    }

	public List<LoanRepayPlanInfo> queryComplete(Map<String, Object> condition) {
    	return (List<LoanRepayPlanInfo>) this.queryEntities("queryComplete",condition);
	}

	@Override
	public void deleteRepayPlanInfoByLoanId(Integer loanId, String processType) {
		this.execute("deleteRepayPlanInfoByLoanId",loanId, processType);
	}

	@Override
	public void deleteRepayPlanInfoByLoanIdAndMode(Integer loanId, String processType, String repaymentMode) {
		this.execute("deleteRepayPlanInfoByLoanIdAndMode",loanId, processType, repaymentMode);
	}

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @param repayDate 还款时间
	 * @param principalAmount
	 * @Description:
	 * @Date: 15:36 2017/11/20
	 */
	public List<LoanRepayPlanInfo> getLoanRepayPlanInfoByLoanIdAndRepayDate(Integer loanId, Date repayDate, BigDecimal principalAmount) {
		Map<String, Object> condition = new HashedMap();
		condition.put("loanId", loanId);
		condition.put("repayDate", repayDate);
		condition.put("principalAmount", principalAmount);
		//获取一条
//		return (List<LoanRepayPlanInfo>) this.queryTopEntities("getLoanRepayPlanInfoByLoanIdAndRepayDate", condition);
		//获取多条
		return (List<LoanRepayPlanInfo>) this.queryEntities("getLoanRepayPlanInfoByLoanIdAndRepayDate", condition);
	}
}
