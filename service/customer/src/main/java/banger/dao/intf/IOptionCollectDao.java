package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustOptionCollect;

/**
 * 客户项选收集数据访问接口
 */
public interface IOptionCollectDao {

	/**
	 * 新增客户项选收集
	 * @param optionCollect 实体对像
	 */
	void insertOptionCollect(CustOptionCollect optionCollect);

	/**
	 *修改客户项选收集
	 * @param optionCollect 实体对像
	 */
	void updateOptionCollect(CustOptionCollect optionCollect);

	/**
	 * 通过主键删除客户项选收集
	 * @param id 主键Id
	 */
	void deleteOptionCollectById(Integer id);

	/**
	 * 通过主键得到客户项选收集
	 * @param id 主键Id
	 */
	CustOptionCollect getOptionCollectById(Integer id);

	/**
	 * 查询客户项选收集
	 * @param condition 查询条件
	 * @return
	 */
	List<CustOptionCollect> queryOptionCollectList(Map<String, Object> condition);

	/**
	 * 分页查询客户项选收集
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustOptionCollect> queryOptionCollectList(Map<String, Object> condition, IPageSize page);

	/**
	 * 得到客户收集选项
	 */
	List<CustOptionCollect> getCustomerOptionById(Integer customerId);

}
