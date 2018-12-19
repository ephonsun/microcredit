package banger.service.impl;

import banger.dao.intf.IBasicInfoDao;
import banger.dao.intf.IScheduleDao;
import banger.domain.customer.*;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IBasicInfoProvider;
import banger.moduleIntf.ICustomerProvider;
import banger.service.intf.IBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 业务访问类
 */
@Repository
public class BasicInfoService implements IBasicInfoService,ICustomerProvider,IBasicInfoProvider {

	@Autowired
	private IBasicInfoDao basicInfoDao;
	
	@Autowired
	private IScheduleDao scheduleDao;


	/**
	 * 新增
	 * @param basicInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public Integer insertBasicInfo(Customer basicInfo,Integer loginUserId){
		basicInfo.setCreateUser(loginUserId);
		basicInfo.setCreateDate(DateUtil.getCurrentDate());
		basicInfo.setUpdateUser(loginUserId);
		basicInfo.setUpdateDate(DateUtil.getCurrentDate());
		return this.basicInfoDao.insertBasicInfo(basicInfo);
	}

	/**
	 *修改
	 * @param basicInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateBasicInfo(Customer basicInfo,Integer loginUserId){
		basicInfo.setUpdateUser(loginUserId);
		basicInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.basicInfoDao.updateBasicInfo(basicInfo);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public boolean deleteBasicInfoById(Integer id){

		Integer rows = this.basicInfoDao.deleteBasicInfoById(id);
		boolean delete = rows.intValue() == 0 ? false : true;
		if (delete) {
			scheduleDao.deleteScheduleByCustomerId(id);
		}
		return delete;
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public Customer getCustomerDetailById(Integer id){
		return this.basicInfoDao.getCustomerDetailById(id);
	}
	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public CustBasicInfo getBasicInfoById(Integer id){
		return this.basicInfoDao.getBasicInfoById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustBasicInfo> queryBasicInfoList(Map<String,Object> condition){
		return this.basicInfoDao.queryBasicInfoList(condition);
	}

	public List<CustBasicInfoQueryForMap> queryBasicInfoListForMap(Map<String, Object> params){
		return this.basicInfoDao.queryBasicInfoListForMap(params);
	}
	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustomerQuery> queryBasicInfoList(Map<String,Object> condition,IPageSize page){
		return this.basicInfoDao.queryBasicInfoList(condition, page);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustBasicInfoQuery> queryBasicInfoListForApp(Map<String,Object> condition, IPageSize page){
		return this.basicInfoDao.queryBasicInfoListForApp(condition, page);
	}

	@Override
	public void updateCustomerDataTable(DataTable datatable,String pkey) {
		this.basicInfoDao.updateCustomerDataTable(datatable, pkey);
	}
	
	@Override
	public void insertCustomerDataTable(DataTable datatable,String pkey) {
		this.basicInfoDao.insertCustomerDataTable(datatable, pkey);
	}

	@Override
	public Integer getNewId() {
		return this.basicInfoDao.getNewId();
	}

	public DataTable getDataTableById(String tableName,Integer id){
		return basicInfoDao.getDataTableById(tableName, id);
	}

	@Override
	public Boolean isUniqueCustomer(String id, String customerName, String identifyType, String identifyNum) {
		return basicInfoDao.isUniqueCustomer(id, customerName, identifyType, identifyNum);
	}

//	@Override
//	public CustBasicInfo getCustomerForCheckRepeat(String identifyType, String identifyNum) {
//		return basicInfoDao.getCustomerForCheckRepeat(identifyType,identifyNum);
//	}

	/**
	 * 通过条件查询客户id
	 * @param customerName 客户姓名
	 * @param identifyType 证件类型
	 * @param identifyNum 证件号码
	 * @return
	 */
	@Override
	public Integer getCustomerIdByCardNameType(String customerName,String identifyType,String identifyNum){
		return basicInfoDao.getCustomerIdByCardNameType(customerName,identifyType,identifyNum);
	}

	/**
	 * 通过条件查询客户
	 * @param customerName
	 * @param identifyType
	 * @param identifyNum
	 * @return
	 */
	@Override
	public CustBasicInfo getCustomerByCardNameType(String customerName, String identifyType, String identifyNum) {
		return basicInfoDao.getCustomerByCardNameType(customerName,identifyType,identifyNum);
	}

	@Override
    public IPageList<CustBasicInfoQuery> queryCusListByBelongId(Map<String, Object> condition, IPageSize page) {
        return basicInfoDao.queryCusListByBelongId(condition,page);
    }

    /**
	 * 更新客户的电话号码
	 * @param telNum
	 * @param customerId
	 */
	public void upldateCustomerTelNumById(String telNum,Integer customerId){
		basicInfoDao.upldateCustomerTelNumById(telNum,customerId);
	}
	
	/**
	 * 贷款申请保存客户
	 */
	public void insertCustomerByLoanApply(Customer customer){
		basicInfoDao.insertBasicInfo(customer);
	}
	
	/**
	 * 新增客户资料
	 * @param table
	 */
	public void insertCustomerDataByLoanApply(DataTable table){
		basicInfoDao.insertCustomerDataTable(table, "ID");
	}
	
	/**
	 * 更新客户资料
	 * @param table
	 */
	public void updateCustomerDataByLoanApply(DataTable table){
		basicInfoDao.updateCustomerDataTable(table, "ID");
	}

	public void updateBelongId(Integer id, Integer belongId) {
		basicInfoDao.updateBelongId(id, belongId);
	}

	@Override
	public CustBasicInfo isUniqueCustomerNum(Map<String, Object> condition) {
		return basicInfoDao.isUniqueCustomerNum(condition);
	}
}
