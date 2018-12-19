package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustOptionCollect;

/**
 * 客户项选收集业务访问接口
 */
public interface IOptionCollectService {

	/**
	 * 新增客户项选收集
	 * @param optionCollect 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertOptionCollect(CustOptionCollect optionCollect, Integer loginUserId);

	/**
	 *修改客户项选收集
	 * @param optionCollect 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateOptionCollect(CustOptionCollect optionCollect, Integer loginUserId);

	/**
	 * 通过主键删除客户项选收集
	 * @param ID 主键Id
	 */
	void deleteOptionCollectById(Integer id);

	/**
	 * 通过主键得到客户项选收集
	 * @param ID 主键Id
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

}
