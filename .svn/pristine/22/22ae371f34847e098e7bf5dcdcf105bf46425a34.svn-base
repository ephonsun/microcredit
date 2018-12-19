package banger.dao.impl;

import banger.dao.intf.IScheduleDao;
import banger.domain.customer.CustSchedule;
import banger.domain.customer.CustScheduleQuery;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class ScheduleDao extends PageSizeDao<CustSchedule> implements IScheduleDao {

	/**
	 * 新增
	 * @param schedule 实体对像
	 */
	public void insertSchedule(CustSchedule schedule){
		schedule.setId(this.newId().intValue());
		this.execute("insertSchedule",schedule);
	}

	/**
	 *修改
	 * @param schedule 实体对像
	 */
	public void updateSchedule(CustSchedule schedule){
		this.execute("updateSchedule",schedule);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteScheduleById(Integer id){
		this.execute("deleteScheduleById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public CustSchedule getScheduleById(Integer id){
		return (CustSchedule)this.queryEntity("getScheduleById",id);
	}

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	public List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId){
		return (List<CustScheduleQuery>)this.queryEntities("getCustScheduleListByCustomerId", customerId);
	}

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	public List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId,Integer userId){
		return (List<CustScheduleQuery>)this.queryEntities("getCustScheduleListByCustomerId", new Object[]{customerId , userId});
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustScheduleQuery> queryScheduleList(Map<String,Object> condition){
		return (List<CustScheduleQuery>)this.queryEntities("queryScheduleList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustScheduleQuery> queryScheduleList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustScheduleQuery>)this.queryEntities("queryScheduleList", page, condition);
	}

	@Override
	public IPageList<CustScheduleQuery> queryCustScheduleList(
			Map<String, Object> condition, IPageSize page) {
		return (IPageList<CustScheduleQuery>)this.queryEntities("queryCustScheduleList",page,condition);
	}

	@Override
	public IPageList<CustScheduleQuery> queryCustScheduleListForApp(
			Map<String, Object> condition, IPageSize page) {
		return (IPageList<CustScheduleQuery>)this.queryEntities("queryCustScheduleListForApp",page,condition);
	}

	@Override
	public void deleteScheduleByCustomerId(Integer customerId) {
		this.execute("deleteScheduleByCuctomerId",customerId);
	}


}
