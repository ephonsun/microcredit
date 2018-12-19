package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ICrossCheckQuanyiquanDao;
import banger.domain.loan.LoanCrossCheckQuanyiquan;

/**
 * 交叉检验权益表数据访问类
 */
@Repository
public class CrossCheckQuanyiquanDao extends PageSizeDao<LoanCrossCheckQuanyiquan> implements ICrossCheckQuanyiquanDao {

	/**
	 * 新增交叉检验权益表
	 * @param crossCheckQuanyiquan 实体对像
	 */
	public void insertCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan){
		crossCheckQuanyiquan.setId(this.newId().intValue());
		this.execute("insertCrossCheckQuanyiquan",crossCheckQuanyiquan);
	}

	/**
	 *修改交叉检验权益表
	 * @param crossCheckQuanyiquan 实体对像
	 */
	public void updateCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan){
		this.execute("updateCrossCheckQuanyiquan",crossCheckQuanyiquan);
	}

	/**
	 * 通过主键删除交叉检验权益表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckQuanyiquanById(Integer id){
		this.execute("deleteCrossCheckQuanyiquanById",id);
	}

	/**
	 * 通过主键得到交叉检验权益表
	 * @param id 主键Id
	 */
	public LoanCrossCheckQuanyiquan getCrossCheckQuanyiquanById(Integer id){
		return (LoanCrossCheckQuanyiquan)this.queryEntity("getCrossCheckQuanyiquanById",id);
	}

	/**
	 * 查询交叉检验权益表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanCrossCheckQuanyiquan> queryCrossCheckQuanyiquanList(Map<String,Object> condition){
		return (List<LoanCrossCheckQuanyiquan>)this.queryEntities("queryCrossCheckQuanyiquanList", condition);
	}

	/**
	 * 分页查询交叉检验权益表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanCrossCheckQuanyiquan> queryCrossCheckQuanyiquanList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanCrossCheckQuanyiquan>)this.queryEntities("queryCrossCheckQuanyiquanList", page, condition);
	}

	@Override
	public LoanCrossCheckQuanyiquan getCrossCheckQuanyiquanByLoanId(Integer loanId) {
		return (LoanCrossCheckQuanyiquan)this.queryEntity("getCrossCheckQuanyiquanByLoanId",loanId);
	}

	/**
	 * 修改交叉检验权益表偏差字段
	 * @param condition
	 */
	public void updateCrossCheckQuanyiquanDevRatio(Map<String,Object> condition){
		this.execute("updateCroCheQuanyiquanDevRatio",condition);
	}
}
