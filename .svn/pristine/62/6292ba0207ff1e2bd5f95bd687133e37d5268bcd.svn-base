package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoLoanUseExtend;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ILoanUseDao;
import banger.domain.config.IntoLoanUse;

/**
 * 进件贷款用途数据访问类
 */
@Repository
public class LoanUseDao extends PageSizeDao<IntoLoanUse> implements ILoanUseDao {

	/**
	 * 新增进件贷款用途
	 * @param loanUse 实体对像
	 */
	public void insertLoanUse(IntoLoanUse loanUse){
		loanUse.setUseId(this.newId().intValue());
		this.execute("insertLoanUse",loanUse);
	}

	/**
	 *修改进件贷款用途
	 * @param loanUse 实体对像
	 */
	public void updateLoanUse(IntoLoanUse loanUse){
		this.execute("updateLoanUse",loanUse);
	}

	/**
	 * 通过主键删除进件贷款用途
	 * @param useId 主键Id
	 */
	public void deleteLoanUseById(Integer useId){
		this.execute("deleteLoanUseById",useId);
	}

	/**
	 * 通过主键得到进件贷款用途
	 * @param useId 主键Id
	 */
	public IntoLoanUse getLoanUseById(Integer useId){
		return (IntoLoanUse)this.queryEntity("getLoanUseById",useId);
	}

	/**
	 * 查询进件贷款用途
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoLoanUse> queryLoanUseList(Map<String,Object> condition){
		return (List<IntoLoanUse>)this.queryEntities("queryLoanUseList", condition);
	}

	/**
	 * 分页查询进件贷款用途
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoLoanUse> queryLoanUseList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoLoanUse>)this.queryEntities("queryLoanUseList", page, condition);
	}

	/**
	 * 关联查询进件贷款用途列表
	 * @return
	 */
	public List<IntoLoanUseExtend> queryIntoLoanUseList() {
		return (List<IntoLoanUseExtend>)this.queryEntities("queryIntoLoanUseList");
	}
}
