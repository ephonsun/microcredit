package banger.moduleIntf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.*;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * Created by jiangd on 2017/4/10.
 */
public interface ICustomerModuleIntf {

    //IPageList<CustomerQuery> queryBasicInfoList(Map<String, Object> condition, IPageSize page);

    IPageList<CustBasicInfoQuery> queryBasicInfoListForApp(Map<String, Object> condition, IPageSize page);
    
	/**
	 * 客户端查询客户日程安排列表
	 * @param condition 查询条件
	 * @return
	 */
	IPageList<CustScheduleQuery> queryCustScheduleListForApp(Map<String, Object> condition, IPageSize page);

	/**
	 * 客户端查询客户日程安排列表
	 * @param condition 查询条件
	 * @return
	 */
	IPageList<CustScheduleQuery> queryOtherScheduleListForApp(Map<String, Object> condition, IPageSize page);
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
	 * 更新日程状态
	 * @param schedule
	 * @param loginUserId
	 */
	void updateSchedule(CustSchedule schedule,Integer loginUserId);

	/**
	 * 更新日程状态
	 * @param schedule
	 * @param loginUserId
	 */
	void insertSchedule(CustSchedule schedule,Integer loginUserId);

	void updateCustomerDataTable(DataTable datatable,String pkey);
	
	void insertCustomerDataTable(DataTable datatable,String pkey);

	Integer getNewId();

	DataTable getDataTableById(String tableName,Integer id);

	Boolean isUniqueCustomer(String id, String customerName, String identifyType, String identifyNum);

	/**
	 * 客户经理查询预申请分配营销客户
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustMarketCustomerQuery> queryMemberMarketCustomerList(Map<String,Object> condition,IPageSize page);

	/**
	 * 作废营销客户表
	 * @param MARKET_CUSTOMER_ID 主键Id
	 */
	void deleteCustMarketCustomerById(Integer marketCustomerId);

	CustBasicInfo getBasicInfoById(Integer id);
	
	/**
	 * 提交征信调查申请
	 * @param custCustomerCreditCheck
	 * @param loginUserId
	 */
	void customerCreditCheckApply(CustCustomerCreditCheck custCustomerCreditCheck,Integer loginUserId);

	/**
	 * 保存上传的调查附件
	 * @param custCreditCheckFile
	 */
	void saveCreditCheckFile(List<CustCreditCheckFile> custCreditCheckFile, Integer loginUserId);
	
	/**
	 * 根据姓名、身份证号码查询
	 * @param condition
	 * @return
	 */
	CustCustomerCreditCheck queryByNameAndNo(Map<String,Object> condition);
	
	/**
	 * 查询客户征信调查附件表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustCreditCheckFile> queryCustCreditCheckFileList(Map<String,Object> condition);
	
	/**
	 * 根据调查资料文件id查询文件
	 * @param fileId
	 * @return
	 */
	CustCreditCheckFile queryCustCreditCheckFile(Integer fileId);
	
	
	/**
	 * 根据贷款id查询该笔贷款提交的征信调查
	 * @param loanId
	 * @return
	 */
	List<CustCustomerCreditCheck> getCustomerCreditCheckByLoanId(Integer loanId);


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


	/**
	 * 地图搜索客户
	 * @param params
	 * @return
	 */
	List<CustBasicInfoQueryForMap> queryBasicInfoListForMap(Map<String, Object> params);

	CustBasicInfo isUniqueCustomerNum(Map<String, Object> condition);
	
	CustSchedule getScheduleById(Integer scheduleId);
	
}
