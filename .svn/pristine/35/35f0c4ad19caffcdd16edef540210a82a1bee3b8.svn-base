package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.*;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 业务访问接口
 */
public interface IBasicInfoService {

	/**
	 * 新增
	 * @param basicInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	Integer insertBasicInfo(Customer basicInfo, Integer loginUserId);

	/**
	 *修改
	 * @param basicInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateBasicInfo(Customer basicInfo, Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	boolean deleteBasicInfoById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	Customer getCustomerDetailById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	CustBasicInfo getBasicInfoById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<CustBasicInfo> queryBasicInfoList(Map<String, Object> condition);
	List<CustBasicInfoQueryForMap> queryBasicInfoListForMap(Map<String, Object> params);
	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustomerQuery> queryBasicInfoList(Map<String, Object> condition, IPageSize page);


	IPageList<CustBasicInfoQuery> queryBasicInfoListForApp(Map<String, Object> condition, IPageSize page);
    
    void insertCustomerDataTable(DataTable datatable,String pkey);

	void updateCustomerDataTable(DataTable datatable,String pkey);

	Integer getNewId();

	DataTable getDataTableById(String tableName, Integer id);

	Boolean isUniqueCustomer(String id, String customerName, String identifyType, String identifyNum);

	//CustBasicInfo getCustomerForCheckRepeat(String identifyType, String identifyNum);

	/**
	 * 根据三要素获取客户id
	 * @param customerName
	 * @param identifyType
	 * @param identifyNum
	 * @return
	 */
	Integer getCustomerIdByCardNameType(String customerName,String identifyType,String identifyNum);

	/**
	 * 根据三要素获取客户
	 * 2017年11月29日09:34:12
	 * @param customerName
	 * @param identifyType
	 * @param identifyNum
	 * @return
	 */
	CustBasicInfo getCustomerByCardNameType(String customerName,String identifyType,String identifyNum);

    List<CustBasicInfoQuery> queryCusListByBelongId(Map<String, Object> condition, IPageSize page);

	CustBasicInfo isUniqueCustomerNum(Map<String, Object> condition);
}
