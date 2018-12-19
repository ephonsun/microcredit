package banger.dao.intf;

import banger.domain.customer.CustSchedule;
import banger.domain.customer.CustScheduleQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface IScheduleDao {

	/**
	 * 新增
	 * @param schedule 实体对像
	 */
	void insertSchedule(CustSchedule schedule);

	/**
	 *修改
	 * @param schedule 实体对像
	 */
	void updateSchedule(CustSchedule schedule);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	void deleteScheduleById(Integer id);

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	CustSchedule getScheduleById(Integer id);

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId);

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId,Integer userId);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<CustScheduleQuery> queryScheduleList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustScheduleQuery> queryScheduleList(Map<String, Object> condition, IPageSize page);
	
	/**
	 * 查询客户日程安排列表
	 * @param condition 查询条件
	 * @return
	 */
	IPageList<CustScheduleQuery> queryCustScheduleList(Map<String, Object> condition, IPageSize page);
	
	/**
	 * 客户端查询客户日程安排列表
	 * @param condition 查询条件
	 * @return
	 */
	IPageList<CustScheduleQuery> queryCustScheduleListForApp(Map<String, Object> condition, IPageSize page);

	/**
	 * 根据客户删除日程
	 */
    void deleteScheduleByCustomerId(Integer customerId);
}
