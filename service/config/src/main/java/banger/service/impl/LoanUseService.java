package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoLoanUseExtend;
import banger.moduleIntf.ILoanUseProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ILoanUseDao;
import banger.service.intf.ILoanUseService;
import banger.domain.config.IntoLoanUse;

/**
 * 进件贷款用途业务访问类
 */
@Repository
public class LoanUseService implements ILoanUseService ,ILoanUseProvider {

	@Autowired
	private ILoanUseDao loanUseDao;

	/**
	 * 新增进件贷款用途
	 * @param loanUse 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertLoanUse(IntoLoanUse loanUse,Integer loginUserId){
		loanUse.setCreateUser(loginUserId);
		loanUse.setCreateDate(DateUtil.getCurrentDate());
		loanUse.setUpdateUser(loginUserId);
		loanUse.setUpdateDate(DateUtil.getCurrentDate());
		this.loanUseDao.insertLoanUse(loanUse);
	}

	/**
	 *修改进件贷款用途
	 * @param loanUse 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateLoanUse(IntoLoanUse loanUse,Integer loginUserId){
		loanUse.setUpdateUser(loginUserId);
		loanUse.setUpdateDate(DateUtil.getCurrentDate());
		this.loanUseDao.updateLoanUse(loanUse);
	}

	/**
	 * 通过主键删除进件贷款用途
	 * @param USE_ID 主键Id
	 */
	public void deleteLoanUseById(Integer useId){
		this.loanUseDao.deleteLoanUseById(useId);
	}

	/**
	 * 通过主键得到进件贷款用途
	 * @param USE_ID 主键Id
	 */
	public IntoLoanUse getLoanUseById(Integer useId){
		return this.loanUseDao.getLoanUseById(useId);
	}

	/**
	 * 查询进件贷款用途
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoLoanUse> queryLoanUseList(Map<String,Object> condition){
		return this.loanUseDao.queryLoanUseList(condition);
	}

	/**
	 * 分页查询进件贷款用途
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoLoanUse> queryLoanUseList(Map<String,Object> condition,IPageSize page){
		return this.loanUseDao.queryLoanUseList(condition,page);
	}

	/**
	 * 关联查询进件贷款用途列表
	 * @return
	 */
	public List<IntoLoanUseExtend> queryIntoLoanUseList() {
		return this.loanUseDao.queryIntoLoanUseList();
	}
}
