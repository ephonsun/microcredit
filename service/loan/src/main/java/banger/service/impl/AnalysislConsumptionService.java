package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAnalysislConsumptionDao;
import banger.service.intf.IAnalysislConsumptionService;
import banger.domain.loan.LoanAnalysislConsumption;

/**
 * 消费贷财务分析详情总表业务访问类
 */
@Repository
public class AnalysislConsumptionService implements IAnalysislConsumptionService {

	@Autowired
	private IAnalysislConsumptionDao analysislConsumptionDao;

	/**
	 * 新增消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAnalysislConsumption(LoanAnalysislConsumption analysislConsumption,Integer loginUserId){
		this.analysislConsumptionDao.insertAnalysislConsumption(analysislConsumption);
	}

	/**
	 *修改消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAnalysislConsumption(LoanAnalysislConsumption analysislConsumption,Integer loginUserId){
		this.analysislConsumptionDao.updateAnalysislConsumption(analysislConsumption);
	}

	/**
	 * 通过主键删除消费贷财务分析详情总表
	 * @param ID 主键Id
	 */
	public void deleteAnalysislConsumptionById(Integer id){
		this.analysislConsumptionDao.deleteAnalysislConsumptionById(id);
	}

	/**
	 * 通过主键得到消费贷财务分析详情总表
	 * @param ID 主键Id
	 */
	public LoanAnalysislConsumption getAnalysislConsumptionById(Integer id){
		return this.analysislConsumptionDao.getAnalysislConsumptionById(id);
	}

	/**
	 * 查询消费贷财务分析详情总表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAnalysislConsumption> queryAnalysislConsumptionList(Map<String,Object> condition){
		return this.analysislConsumptionDao.queryAnalysislConsumptionList(condition);
	}

	/**
	 * 分页查询消费贷财务分析详情总表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAnalysislConsumption> queryAnalysislConsumptionList(Map<String,Object> condition,IPageSize page){
		return this.analysislConsumptionDao.queryAnalysislConsumptionList(condition,page);
	}

}
