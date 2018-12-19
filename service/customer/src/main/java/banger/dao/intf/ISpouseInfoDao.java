package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.Customer;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustSpouseInfo;

/**
 * 数据访问接口
 */
public interface ISpouseInfoDao {

	/**
	 * 新增
	 * @param spouseInfo 实体对像
	 */
	void insertSpouseInfo(Customer spouseInfo);

	/**
	 *修改
	 * @param spouseInfo 实体对像
	 */
	void updateSpouseInfo(Customer spouseInfo);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	void deleteSpouseInfoById(Integer id);

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	CustSpouseInfo getSpouseInfoById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<CustSpouseInfo> querySpouseInfoList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustSpouseInfo> querySpouseInfoList(Map<String, Object> condition, IPageSize page);

}
