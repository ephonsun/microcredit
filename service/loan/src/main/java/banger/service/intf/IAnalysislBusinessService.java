package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAnalysislBusiness;

/**
 * 经营贷财务分析详情总表业务访问接口
 */
public interface IAnalysislBusinessService {

	/**
	 * 新增经营贷财务分析详情总表
	 * @param analysislBusiness 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAnalysislBusiness(LoanAnalysislBusiness analysislBusiness, Integer loginUserId);

	/**
	 *修改经营贷财务分析详情总表
	 * @param analysislBusiness 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAnalysislBusiness(LoanAnalysislBusiness analysislBusiness, Integer loginUserId);

	/**
	 * 通过主键删除经营贷财务分析详情总表
	 * @param ID 主键Id
	 */
	void deleteAnalysislBusinessById(Integer id);

	/**
	 * 通过主键得到经营贷财务分析详情总表
	 * @param ID 主键Id
	 */
	LoanAnalysislBusiness getAnalysislBusinessById(Integer id);

	/**
	 * 查询经营贷财务分析详情总表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String, Object> condition);

	/**
	 * 分页查询经营贷财务分析详情总表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String, Object> condition, IPageSize page);

}
