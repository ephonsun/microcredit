package banger.service.impl;

import java.io.*;
import java.util.List;
import java.util.Map;

import banger.framework.util.IdCardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.IScheduleDao;
import banger.domain.customer.CustSchedule;
import banger.domain.customer.CustScheduleQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.SensitiveInfoUtils;
import banger.service.intf.IScheduleService;

/**
 * 业务访问类
 */
@Repository
public class ScheduleService implements IScheduleService {

	@Autowired
	private IScheduleDao scheduleDao;

	/**
	 * 新增
	 * @param schedule 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertSchedule(CustSchedule schedule,Integer loginUserId){
		schedule.setCreateUser(loginUserId);
		schedule.setCreateDate(DateUtil.getCurrentDate());
		schedule.setUpdateUser(loginUserId);
		schedule.setUpdateDate(DateUtil.getCurrentDate());
//		schedule.setState(1);
		schedule.setDelFlag(0);		
		this.scheduleDao.insertSchedule(schedule);
	}

	/**
	 *修改
	 * @param schedule 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateSchedule(CustSchedule schedule,Integer loginUserId){
		schedule.setUpdateUser(loginUserId);
		schedule.setUpdateDate(DateUtil.getCurrentDate());
		this.scheduleDao.updateSchedule(schedule);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteScheduleById(Integer id){
		this.scheduleDao.deleteScheduleById(id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public CustSchedule getScheduleById(Integer id){
		return this.scheduleDao.getScheduleById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustScheduleQuery> queryScheduleList(Map<String,Object> condition){
		return this.scheduleDao.queryScheduleList(condition);
	}

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	public List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId){
		return getCustScheduleListByCustomerId(customerId,null);
	}

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	public List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId,Integer userId){
		List<CustScheduleQuery> list = this.scheduleDao.getCustScheduleListByCustomerId(customerId,userId);
		for (CustScheduleQuery custScheduleQuery : list) {
			custScheduleQuery.setPlanTimeStr(DateUtil.format(custScheduleQuery.getPlanTime(), "yyyy-MM-dd HH:mm"));
			custScheduleQuery.setPlanTimeStr1(DateUtil.format(custScheduleQuery.getPlanTime(), "yy/MM/dd HH:mm"));
			if(custScheduleQuery.getState()==1){
				custScheduleQuery.setStateCN("未完成");
			}else{
				custScheduleQuery.setStateCN("已完成");
			}
		}
		return list;
	}

	/**
	 * 分页查询  客户查看日程
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustScheduleQuery> queryScheduleList(Map<String,Object> condition,IPageSize page){
		IPageList<CustScheduleQuery> list=this.scheduleDao.queryScheduleList(condition,page);
		for (CustScheduleQuery custScheduleQuery : list) {//先暂时这么翻译			
			//联系时间
			custScheduleQuery.setPlanTimeStr(DateUtil.format(custScheduleQuery.getPlanTime(), "yyyy-MM-dd HH:mm"));
			custScheduleQuery.setPlanTimeStr1(DateUtil.format(custScheduleQuery.getPlanTime(), "yy/MM/dd HH:mm"));
		}
		
		return list;
	}

	/**
	 * 日程安排列表查询
	 */
	@Override
	public IPageList<CustScheduleQuery> queryCustScheduleList(
			Map<String, Object> condition, IPageSize page) {
		IPageList<CustScheduleQuery> list=scheduleDao.queryCustScheduleList(condition, page);
		for (CustScheduleQuery custScheduleQuery : list) {//先暂时这么翻译
			//联系时间
			custScheduleQuery.setPlanTimeStr(DateUtil.format(custScheduleQuery.getPlanTime(), "yyyy-MM-dd HH:mm"));
			//手机号码脱敏处理
			String phoneNumber=custScheduleQuery.getPhoneNumber();
			custScheduleQuery.setPhoneNumber(IdCardUtil.getTelNumX(phoneNumber,1));
			if(custScheduleQuery.getCustomerAge()!=null){
				custScheduleQuery.setCustomerAgeStr(custScheduleQuery.getCustomerAge()+"岁");
			}
		}
		return list;
	}

	@Override
	public IPageList<CustScheduleQuery> queryCustScheduleListForApp(
			Map<String, Object> condition, IPageSize page) {
		IPageList<CustScheduleQuery> list=scheduleDao.queryCustScheduleListForApp(condition, page);
		for (CustScheduleQuery custScheduleQuery : list) {
			//手机号码脱敏处理
			//String phoneNumber=custScheduleQuery.getPhoneNumber();			
			//custScheduleQuery.setPhoneNumber(SensitiveInfoUtils.fixedCustomerPhone(phoneNumber));
			//时间转换  2017-04-15 14:30
			custScheduleQuery.setPlanTimeStr(DateUtil.format(custScheduleQuery.getPlanTime(), "yyyy-MM-dd HH:mm"));
			custScheduleQuery.setPlanTimeStr1(DateUtil.format(custScheduleQuery.getPlanTime(), "yy/MM/dd HH:mm"));
			if(custScheduleQuery.getState()==1){
				custScheduleQuery.setStateCN("未完成");
			}else{
				custScheduleQuery.setStateCN("已完成");
			}
		}
		return list;
	}


}
