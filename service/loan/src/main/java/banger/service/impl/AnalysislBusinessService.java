package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAnalysislBusinessDao;
import banger.service.intf.IAnalysislBusinessService;
import banger.domain.loan.LoanAnalysislBusiness;

/**
 * 经营贷财务分析详情总表业务访问类
 */
@Repository
public class AnalysislBusinessService implements IAnalysislBusinessService {

	@Autowired
	private IAnalysislBusinessDao analysislBusinessDao;

	/**
	 * 新增经营贷财务分析详情总表
	 * @param analysislBusiness 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAnalysislBusiness(LoanAnalysislBusiness analysislBusiness,Integer loginUserId){
		this.analysislBusinessDao.insertAnalysislBusiness(analysislBusiness);
	}

	/**
	 *修改经营贷财务分析详情总表
	 * @param analysislBusiness 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAnalysislBusiness(LoanAnalysislBusiness analysislBusiness,Integer loginUserId){
		this.analysislBusinessDao.updateAnalysislBusiness(analysislBusiness);
	}

	/**
	 * 通过主键删除经营贷财务分析详情总表
	 * @param ID 主键Id
	 */
	public void deleteAnalysislBusinessById(Integer id){
		this.analysislBusinessDao.deleteAnalysislBusinessById(id);
	}

	/**
	 * 通过主键得到经营贷财务分析详情总表
	 * @param ID 主键Id
	 */
	public LoanAnalysislBusiness getAnalysislBusinessById(Integer id){
		return this.analysislBusinessDao.getAnalysislBusinessById(id);
	}

	/**
	 * 查询经营贷财务分析详情总表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String,Object> condition){
		return this.analysislBusinessDao.queryAnalysislBusinessList(condition);
	}

	/**
	 * 分页查询经营贷财务分析详情总表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String,Object> condition,IPageSize page){
		return this.analysislBusinessDao.queryAnalysislBusinessList(condition,page);
	}

}
