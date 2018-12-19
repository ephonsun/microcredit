package banger.dao.intf;

import banger.domain.loan.contract.LoanContractTemplateType;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface IContractTemplateTypeDao {

	/**
	 * 新增
	 * @param contractTemplateType 实体对像
	 */
	void insertContractTemplateType(LoanContractTemplateType contractTemplateType);

	/**
	 *修改
	 * @param contractTemplateType 实体对像
	 */
	void updateContractTemplateType(LoanContractTemplateType contractTemplateType);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	void deleteContractTemplateTypeById(Integer id);

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	LoanContractTemplateType getContractTemplateTypeById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractTemplateType> queryContractTemplateTypeList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractTemplateType> queryContractTemplateTypeList(Map<String, Object> condition, IPageSize page);


}
