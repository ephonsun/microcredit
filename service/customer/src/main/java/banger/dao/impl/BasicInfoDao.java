package banger.dao.impl;

import banger.common.annotation.DataDaoAnnotation;
import banger.dao.intf.IBasicInfoDao;
import banger.domain.customer.*;
import banger.framework.collection.DataTable;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.sql.util.ISqlEngine;
import banger.framework.sql.util.SqlEngine;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class BasicInfoDao extends PageSizeDao<CustBasicInfo> implements IBasicInfoDao {

	/**
	 * 新增
	 * @param basicInfo 实体对像
	 */
	public Integer insertBasicInfo(Customer basicInfo){
		Integer id = this.newId().intValue();
		basicInfo.setId(id);
		this.execute("insertBasicInfo",basicInfo);
		return id;
	}

	/**
	 *修改
	 * @param basicInfo 实体对像
	 */
	public void updateBasicInfo(Customer basicInfo){
		this.execute("updateBasicInfo",basicInfo);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public Integer deleteBasicInfoById(Integer id){
		return this.execute("deleteBasicInfoById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public Customer getCustomerDetailById(Integer id){
		return (Customer)this.queryEntity("getCustomerDetailById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public CustBasicInfo getBasicInfoById(Integer id){
		return (CustBasicInfo)this.queryEntity("getBasicInfoById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustBasicInfo> queryBasicInfoList(Map<String,Object> condition){
		return (List<CustBasicInfo>)this.queryEntities("queryBasicInfoList", condition);
	}

	public List<CustBasicInfoQueryForMap> queryBasicInfoListForMap(Map<String, Object> params){
		int pageSize = 10;
		return (List<CustBasicInfoQueryForMap>)this.queryTopEntities("custBasicInfoQueryForMap", pageSize, params);
	}


	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustomerQuery> queryBasicInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustomerQuery>)this.queryEntities("queryBasicInfoList", page, condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustBasicInfoQuery> queryBasicInfoListForApp(Map<String,Object> condition, IPageSize page){
		return (IPageList<CustBasicInfoQuery>)this.queryEntities("queryBasicInfoListForApp", page, condition);
	}

	@Override
	public Integer getNewId() {
		return this.newId().intValue();
	}

	/**
	 * 更新客户的电话号码
	 * @param telNum
	 * @param customerId
	 */
	public void upldateCustomerTelNumById(String telNum,Integer customerId){
		this.execute("upldateCustomerTelNumById",telNum,customerId);
	}
	
	@Override
	@DataDaoAnnotation
	public void insertCustomerDataTable(DataTable datatable,String pkey) {
		this.insertData(datatable,pkey);
	}

	@Override
	@DataDaoAnnotation
	public void updateCustomerDataTable(DataTable datatable,String pkey) {
		this.updateData(datatable,pkey);
	}

	@Override
	public DataTable getDataTableById(String tableName,Integer id){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		DataTable table = ish.getDataTable("SELECT * FROM "+tableName+" WHERE ID="+id);
		ish.dispose();
		return table;
	}

	@Override
	public CustBasicInfo getCustomerForCheckRepeat( String identifyType, String identifyNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("identifyType",identifyType);
		map.put("identifyNum",identifyNum);
		CustBasicInfo u = (CustBasicInfo)this.queryEntity("getCustomerForCheckRepeat",map);
		return  u;
	}

	@Override
	public Boolean isUniqueCustomer(String id, String customerName, String identifyType, String identifyNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("customerName",customerName);
		map.put("identifyType",identifyType);
		map.put("identifyNum",identifyNum);
		map.put("id",id);
		CustBasicInfo u = (CustBasicInfo)this.queryEntity("getCustomerForCheckRepeat",map);
		if(u == null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 通过条件查询客户id
	 * @param customerName 客户姓名
	 * @param identifyType 证件类型
	 * @param identifyNum 证件号码
	 * @return
	 */
	public Integer getCustomerIdByCardNameType(String customerName,String identifyType,String identifyNum){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("customerName",customerName);
		map.put("identifyType",identifyType);
		map.put("identifyNum",identifyNum);
		CustBasicInfo cust = (CustBasicInfo)this.queryEntity("getCustomerIdByCardNameType",map);
		if(cust!=null){
			return cust.getId();
		}else{
			return null;
		}
	}

	/**
	 * 通过条件查询客户
	 * 2017年11月29日09:37:31
	 * @param customerName 客户姓名
	 * @param identifyType 证件类型
	 * @param identifyNum 证件号码
	 * @return
	 */
	@Override
	public CustBasicInfo getCustomerByCardNameType(String customerName, String identifyType, String identifyNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("customerName",customerName);
		map.put("identifyType",identifyType);
		map.put("identifyNum",identifyNum);
		CustBasicInfo custBasicInfo = (CustBasicInfo)this.queryEntity("getCustomerIdByCardNameType",map);
		return custBasicInfo;
	}


	@Override
    public IPageList<CustBasicInfoQuery> queryCusListByBelongId(Map<String, Object> condition, IPageSize page) {
		return (IPageList<CustBasicInfoQuery>)this.queryEntities("queryCusListByBelongId", page, condition);
	}

	/**
	 * 更新客户归属人
	 * @param id
	 * @param belongId
	 */
	public void updateBelongId(Integer id, Integer belongId) {
		Customer basicInfo = new Customer();
		basicInfo.setBelongUserId(belongId);
		basicInfo.setId(id);
		updateBasicInfo(basicInfo);
	}

	@Override
	public CustBasicInfo isUniqueCustomerNum(Map<String, Object> condition) {
		CustBasicInfo custBasicInfo = (CustBasicInfo)this.queryEntity("isUniqueCustomerNum",condition);
		return custBasicInfo;
	}
}
