package banger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IProfitBizIncomeMonthDao;
import banger.domain.loan.LoanProfitBizIncomeMonth;
import banger.moduleIntf.ILoanIncomeStatementProvider;
import banger.moduleIntf.IProfitBizIncomeItemProvider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IProfitBizIncomeItemDao;
import banger.service.intf.IProfitBizIncomeItemService;
import banger.domain.loan.LoanProfitBizIncomeItem;

import javax.annotation.Resource;

/**
 * 损益情况经营类收入支出明细表业务访问类
 */
@Repository
public class ProfitBizIncomeItemService implements IProfitBizIncomeItemService,IProfitBizIncomeItemProvider {

	@Autowired
	private IProfitBizIncomeItemDao profitBizIncomeItemDao;
	@Autowired
	private IProfitBizIncomeMonthDao profitBizIncomeMonthDao;
	@Resource
	private ILoanIncomeStatementProvider loanIncomeStatementProvider;

	/**
	 * 新增损益情况经营类收入支出明细表
	 * @param profitBizIncomeItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertProfitBizIncomeItem(LoanProfitBizIncomeItem profitBizIncomeItem,Integer loginUserId){
		profitBizIncomeItem.setCreateUser(loginUserId);
		profitBizIncomeItem.setCreateDate(DateUtil.getCurrentDate());
		profitBizIncomeItem.setUpdateUser(loginUserId);
		profitBizIncomeItem.setUpdateDate(DateUtil.getCurrentDate());
		this.profitBizIncomeItemDao.insertProfitBizIncomeItem(profitBizIncomeItem);
	}

	/**
	 *修改损益情况经营类收入支出明细表
	 * @param profitBizIncomeItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProfitBizIncomeItem(LoanProfitBizIncomeItem profitBizIncomeItem,Integer loginUserId){
		profitBizIncomeItem.setUpdateUser(loginUserId);
		profitBizIncomeItem.setUpdateDate(DateUtil.getCurrentDate());
		this.profitBizIncomeItemDao.updateProfitBizIncomeItem(profitBizIncomeItem);
	}

	/**
	 * 通过主键删除损益情况经营类收入支出明细表
	 * @param id 主键Id
	 */
	public void deleteProfitBizIncomeItemById(Integer id){
		this.profitBizIncomeItemDao.deleteProfitBizIncomeItemById(id);
	}

	/**
	 * 通过主键得到损益情况经营类收入支出明细表
	 * @param id 主键Id
	 */
	public LoanProfitBizIncomeItem getProfitBizIncomeItemById(Integer id){
		return this.profitBizIncomeItemDao.getProfitBizIncomeItemById(id);
	}

	/**
	 * 查询损益情况经营类收入支出明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanProfitBizIncomeItem> queryProfitBizIncomeItemList(Map<String,Object> condition){
		return this.profitBizIncomeItemDao.queryProfitBizIncomeItemList(condition);
	}

	/**
	 * 分页查询损益情况经营类收入支出明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanProfitBizIncomeItem> queryProfitBizIncomeItemList(Map<String,Object> condition,IPageSize page){
		return this.profitBizIncomeItemDao.queryProfitBizIncomeItemList(condition,page);
	}

	@Override
	public void saveProfitBizIncomeItem(LoanProfitBizIncomeItem loanProfitBizIncomeItem, List<LoanProfitBizIncomeMonth> monthList, int userId) {

		if (loanProfitBizIncomeItem.getId() == null) {
			//收入明细id为空是添加
			loanProfitBizIncomeItem.setLoanClassId(1);
			loanProfitBizIncomeItem.setCreateUser(userId);
			//保存营业收入明细
			profitBizIncomeItemDao.insertProfitBizIncomeItem(loanProfitBizIncomeItem);
		}else{
			//修改营业收入明细
			profitBizIncomeItemDao.updateProfitBizIncomeItem(loanProfitBizIncomeItem);
			//修改月份项,先删除之前的,然后重新添加
			profitBizIncomeMonthDao.deleteProfitBizIncomeItemMonthByItemId(loanProfitBizIncomeItem.getId());
		}
		//保存月份项
		for (LoanProfitBizIncomeMonth incomeMonth : monthList) {
			incomeMonth.setIncomeId(loanProfitBizIncomeItem.getId());
			profitBizIncomeMonthDao.insertProfitBizIncomeMonth(incomeMonth);
		}
		//更新主表的收入总计
		loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(loanProfitBizIncomeItem.getLoanId(), 1, userId);
	}
}
