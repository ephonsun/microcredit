package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProfitLossInfoDao;
import banger.domain.loan.LoanProfitLossInfo;

/**
 * 损益情况信息表数据访问类
 */
@Repository
public class ProfitLossInfoDao extends PageSizeDao<LoanProfitLossInfo> implements IProfitLossInfoDao {

	/**
	 * 新增损益情况信息表
	 * @param profitLossInfo 实体对像
	 */
	public void insertProfitLossInfo(LoanProfitLossInfo profitLossInfo){
		profitLossInfo.setId(this.newId().intValue());
		this.execute("insertProfitLossInfo",profitLossInfo);
	}

	/**
	 *修改损益情况信息表
	 * @param profitLossInfo 实体对像
	 */
	public void updateProfitLossInfo(LoanProfitLossInfo profitLossInfo){
		this.execute("updateProfitLossInfo",profitLossInfo);
	}

	/**
	 * 通过主键删除损益情况信息表
	 * @param id 主键Id
	 */
	public void deleteProfitLossInfoById(Integer id){
		this.execute("deleteProfitLossInfoById",id);
	}

	/**
	 * 通过主键得到损益情况信息表
	 * @param id 主键Id
	 */
	public LoanProfitLossInfo getProfitLossInfoById(Integer id){
		return (LoanProfitLossInfo)this.queryEntity("getProfitLossInfoById",id);
	}

	/**
	 * 查询损益情况信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanProfitLossInfo> queryProfitLossInfoList(Map<String,Object> condition){
		return (List<LoanProfitLossInfo>)this.queryEntities("queryProfitLossInfoList", condition);
	}

	/**
	 * 分页查询损益情况信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanProfitLossInfo> queryProfitLossInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanProfitLossInfo>)this.queryEntities("queryProfitLossInfoList", page, condition);
	}

	/**
	 * 通过贷款id得到损益情况信息表
	 * @param loanId 贷款Id
	 */
	public LoanProfitLossInfo getProfitLossInfoByLoanId(Integer loanId) {
		return (LoanProfitLossInfo)this.queryEntity("getProfitLossInfoByLoanId",loanId);
	}

	/**
	 * 通过贷款id更新
	 * @param profitLossInfo
	 */
	public void updateProfitLossInfoByLoanId(LoanProfitLossInfo profitLossInfo) {
		this.execute("updateProfitLossInfoByLoanId",profitLossInfo);
	}


	public LoanProfitLossInfo queryLoanProfitLossInfoByIds(Integer loanId, Integer loanClassId) {
		return (LoanProfitLossInfo)this.queryEntity("queryLoanProfitLossInfoByIds",loanId,loanClassId);
	}
}
