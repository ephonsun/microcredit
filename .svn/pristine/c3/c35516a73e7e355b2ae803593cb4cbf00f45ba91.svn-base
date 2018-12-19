package banger.moduleImpl;

import banger.domain.customer.*;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.ICustomerModuleIntf;
import banger.service.intf.IBasicInfoService;
import banger.service.intf.ICustCreditCheckFileService;
import banger.service.intf.ICustCustomerCreditCheckService;
import banger.service.intf.ICustMarketCustomerService;
import banger.service.intf.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerModuleImpl implements ICustomerModuleIntf{

	@Autowired
	private IBasicInfoService basicInfoService;

	@Autowired
	private IScheduleService scheduleService;
	
	@Autowired
	private ICustMarketCustomerService custMarketCustomerService;
	
	@Autowired
	private ICustCustomerCreditCheckService custCustomerCreditCheckService;
	
	@Autowired
	private ICustCreditCheckFileService custCreditCheckFileService;

//	@Override
//	public IPageList<CustomerQuery> queryBasicInfoList(
//			Map<String, Object> condition, IPageSize page) {
//		return basicInfoService.queryBasicInfoList(condition, page);
//	}

	@Override
	public IPageList<CustBasicInfoQuery> queryBasicInfoListForApp(
			Map<String, Object> condition, IPageSize page) {
		return basicInfoService.queryBasicInfoListForApp(condition, page);
	}

	@Override
	public IPageList<CustScheduleQuery> queryCustScheduleListForApp(
			Map<String, Object> condition, IPageSize page) {
		return scheduleService.queryCustScheduleListForApp(condition, page);
	}

	@Override
	public IPageList<CustScheduleQuery> queryOtherScheduleListForApp(
			Map<String, Object> condition, IPageSize page) {
		return scheduleService.queryScheduleList(condition, page);
	}

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	public List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId){
		return scheduleService.getCustScheduleListByCustomerId(customerId);
	}

	/**
	 * 得到客户日程数据
	 * @param customerId
	 * @return
	 */
	public List<CustScheduleQuery> getCustScheduleListByCustomerId(Integer customerId,Integer userId){
		return scheduleService.getCustScheduleListByCustomerId(customerId,userId);
	}

	@Override
	public void updateSchedule(CustSchedule schedule, Integer loginUserId) {
		scheduleService.updateSchedule(schedule, loginUserId);
	}

	/**
	 * 更新日程状态
	 * @param schedule
	 * @param loginUserId
	 */
	public void insertSchedule(CustSchedule schedule,Integer loginUserId){
		scheduleService.insertSchedule(schedule, loginUserId);
	}
	
	@Override
	public void insertCustomerDataTable(DataTable datatable,String pkey) {
		basicInfoService.insertCustomerDataTable(datatable, pkey);
	}

	@Override
	public void updateCustomerDataTable(DataTable datatable,String pkey) {
		basicInfoService.updateCustomerDataTable(datatable, pkey);
	}

	@Override
	public Integer getNewId() {
		return basicInfoService.getNewId();
	}

	public DataTable getDataTableById(String tableName,Integer id){
		return basicInfoService.getDataTableById(tableName, id);
	}

	@Override
	public Boolean isUniqueCustomer(String id, String customerName, String identifyType, String identifyNum) {
		return basicInfoService.isUniqueCustomer(id, customerName, identifyType, identifyNum);
	}

	@Override
	public IPageList<CustMarketCustomerQuery> queryMemberMarketCustomerList(
			Map<String, Object> condition, IPageSize page) {
		return custMarketCustomerService.queryMemberMarketCustomerList(condition, page,true);
	}

	@Override
	public void deleteCustMarketCustomerById(Integer marketCustomerId) {
		custMarketCustomerService.deleteCustMarketCustomerById(marketCustomerId);
	}

	@Override
	public CustBasicInfo getBasicInfoById(Integer id) {
		return basicInfoService.getBasicInfoById(id);
	}

	@Override
	public void customerCreditCheckApply(
			CustCustomerCreditCheck custCustomerCreditCheck, Integer loginUserId) {
		custCustomerCreditCheckService.insertCustCustomerCreditCheck(custCustomerCreditCheck, loginUserId,true);
	}

	@Override
	public void saveCreditCheckFile(
			List<CustCreditCheckFile> custCreditCheckFiles, Integer loginUserId) {
		for (CustCreditCheckFile custCreditCheckFile: custCreditCheckFiles) {
			custCreditCheckFileService.insertCustCreditCheckFile(custCreditCheckFile, loginUserId);
		}
	}

	@Override
	public CustCustomerCreditCheck queryByNameAndNo(
			Map<String, Object> condition) {
		return custCustomerCreditCheckService.queryByNameAndNo(condition);
	}

	@Override
	public List<CustCreditCheckFile> queryCustCreditCheckFileList(
			Map<String, Object> condition) {
		return custCreditCheckFileService.queryCustCreditCheckFileList(condition);
	}

	@Override
	public CustCreditCheckFile queryCustCreditCheckFile(Integer fileId) {
		return custCreditCheckFileService.getCustCreditCheckFileById(fileId);
	}

	@Override
	public List<CustCustomerCreditCheck> getCustomerCreditCheckByLoanId(
			Integer loanId) {
		return custCustomerCreditCheckService.getCustomerCreditCheckByLoanId(loanId);
	}


	@Override
	public Integer getCustomerIdByCardNameType(String customerName,String identifyType,String identifyNum){
		return basicInfoService.getCustomerIdByCardNameType(customerName,identifyType,identifyNum);
	}

	/**
	 * 根据三要素获取客户
	 * @param customerName
	 * @param identifyType
	 * @param identifyNum
	 * @return
	 */
	@Override
	public CustBasicInfo getCustomerByCardNameType(String customerName, String identifyType, String identifyNum) {
		return basicInfoService.getCustomerByCardNameType(customerName,identifyType,identifyNum);
	}


	public List<CustBasicInfoQueryForMap> queryBasicInfoListForMap(Map<String, Object> params){
		return basicInfoService.queryBasicInfoListForMap(params);
	}

	@Override
	public CustBasicInfo isUniqueCustomerNum(Map<String, Object> condition) {
		return basicInfoService.isUniqueCustomerNum(condition);
	}
	
	@Override
	public CustSchedule getScheduleById(Integer scheduleId) {
		return scheduleService.getScheduleById(scheduleId);
	}
}
