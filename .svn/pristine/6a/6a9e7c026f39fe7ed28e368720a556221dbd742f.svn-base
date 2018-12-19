package banger.service.impl;

import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.ITypeDao;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseOption;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.customer.CustBasicInfo;
import banger.domain.customer.CustCustomerBlack;
import banger.domain.customer.CustPotentialCustomersFiles;
import banger.domain.enumerate.*;
import banger.domain.html5.IntoFileInfo;
import banger.domain.loan.*;
import banger.domain.permission.PmsUser;
import banger.domain.sub.BizTypeSub;
import banger.domain.sub.CrmPrdType;
import banger.domain.system.SysBasicConfig;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.moduleIntf.*;
import banger.service.intf.*;
import banger.socket.SocketDemo;
import banger.util.CodedProduceUtil;
import banger.util.LoanMsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 贷款申请表业务访问类
 */
@Service
public class ApplyInfoService implements IApplyInfoService,ILoanHandOverProvider {

	@Resource
	private IApplyInfoDao applyInfoDao;
	@Autowired
	private ITypeDao typeDao;
	@Resource
    private ILoanInfoSyncService loanInfoSyncService;
	@Resource
	private ILoanApplyService loanApplyService;
	@Autowired
	private ILoanSyncCustomerService loanSyncCustomerService;
	@Autowired
	private ICurrentAuditStatusService currentAuditStatusService;
	@Autowired
	private ILoanOperationService loanOperationService;
	@Autowired
	private IPermissionModule permissionModule;
	@Resource
	private IRepayPlanInfoService repayPlanInfoService;
	@Resource
	private ICustomerBlackProvider customerBlackProvider;
	@Resource
	private ICustomerMarketProvider customerMarketProvider;
	@Resource
	private IIntoCustomersProvider intoCustomersProvider;
	@Resource
	private IInfoAddedFileProvider iInfoAddedFileProvider;
	@Autowired
	private IConfigModule configModule;
	@Resource
	private IBasicConfigProvider iBasicConfigProvider;
	@Resource
	private ILoanTaskProvider loanTaskProvider;
	@Autowired
	private IMapTaggingProvider mapTaggingProvider;
	@Autowired
	private IProfitLossInfoService profitLossInfoService;
	@Autowired
	private IAssetsInfoService assetsInfoService;
	@Autowired
	private ILoanModelScoreService iLoanModelScoreService;

	@Autowired
	private ICustomerModuleIntf customerModuleIntf;

	@Autowired
	private ISystemModule systemModule;
	@Autowired
	private IPotentialCustomerFileProvider potentialCustomerFileProvider;

	@Autowired
	private SocketDemo socketDemo;

    @Value("${tp.url}")
    private String tpUrl;

	/**
	 * 新增贷款申请表
	 * @param applyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertApplyInfo(LoanApplyInfo applyInfo,Integer loginUserId){
		applyInfo.setCreateUser(loginUserId);
		applyInfo.setCreateDate(DateUtil.getCurrentDate());
		applyInfo.setUpdateUser(loginUserId);
		applyInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.applyInfoDao.insertApplyInfo(applyInfo);
	}

	/**
	 *修改贷款申请表
	 * @param applyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateApplyInfo(LoanApplyInfo applyInfo,Integer loginUserId){
		applyInfo.setUpdateUser(loginUserId);
		applyInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.applyInfoDao.updateApplyInfo(applyInfo);
	}

	/**
	 * 通过主键删除贷款申请表
	 * @param loanId 主键Id
	 */
	public void deleteApplyInfoById(Integer loanId){
		this.applyInfoDao.deleteApplyInfoById(loanId);
	}

	/**
	 * 通过主键得到贷款申请表
	 * @param loanId 主键Id
	 */
	public LoanApplyInfo getApplyInfoById(Integer loanId){
		return this.applyInfoDao.getApplyInfoById(loanId);
	}

	/**
	 * 查询贷款申请表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition){
		return this.applyInfoDao.queryApplyInfoList(condition);
	}

	/**
	 * 分页查询贷款申请表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition,IPageSize page){
		return this.applyInfoDao.queryApplyInfoList(condition,page);
	}

	@Override
	public LoanApplyInfo_Web_Query queryOneSubInfo(Integer loanId) {
		return applyInfoDao.queryOneSubInfo(loanId);
	}

	/**
	 * web申请保存
	 * @param map
	 * @param id
	 * @param processType 申请流程【Apply | Allot】
	 *@param loanTypeId
	 * @param loginUserId  @return
	 */
	@Override
	public Map<String, Object> saveLoanApplyInfo(Map<String, Object> map, String id, String processType, Integer loanTypeId, Integer loginUserId, Integer teamGroupId, Integer customerId,  Integer applyId,Integer potentialId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		String message = "保存成功";
		Integer loanId = 0;
		LoanApplyInfo applyInfo = new LoanApplyInfo();

		if(StringUtils.isNotBlank(id) && StringUtils.isNumeric(id) ){
			loanId = Integer.valueOf(id);
			applyInfo = getApplyInfoById(loanId);
			applyInfo.setLoanApplyUserId(loginUserId);
			applyInfo.setLoanBelongId(loginUserId);
			applyInfo.setLoanProcessType(processType);
			if ("Allot".equals(processType)) {
				//如果被加入了黑名单，直接返回
				boolean isBlack = customerBlackProvider.isExistCustomerBlack(applyInfo.getLoanName(),applyInfo.getLoanIdentifyType(),applyInfo.getLoanIdentifyNum());
				if(isBlack) {
					resultMap.put("success", false);
					message = "客户贷款受限，提交申请失败";
					applyInfo.setLoanProcessType(LoanProcessTypeEnum.APPLY.type);
				} else {
					message = "提交申请成功";
				}
				Object saveLog = resultMap.get("success");
				if (saveLog != null && "true".equals(String.valueOf(saveLog))) {
					// 记录操作日志
					loanOperationService.addLoanOperation(loanId, LoanOperationType.LOAN_APPLY_SUBMIT.typeName, "", loginUserId, LoanProcessTypeEnum.APPLY.type);
				}
			}
			setApplyInfo(applyInfo, map);
			applyInfoDao.updateApplyInfo(applyInfo);
		}else{

			LoanType loanType = typeDao.getTypeById(loanTypeId);			//根据贷款类型取三表类型
			if(loanType!=null) {
				applyInfo.setLoanClassId(loanType.getLoanClassId());
			}

			applyInfo.setLoanApplyDate(new Date());
			setDefaultValues(applyInfo, processType, loanTypeId, loginUserId);
			setApplyInfo(applyInfo, map);
			if ("Allot".equals(processType)) {
				//如果被加入了黑名单，直接返回
				boolean isBlack = customerBlackProvider.isExistCustomerBlack(applyInfo.getLoanName(),applyInfo.getLoanIdentifyType(),applyInfo.getLoanIdentifyNum());
				if(isBlack) {
					resultMap.put("success", false);
					resultMap.put("newSave", true);
					message = "客户贷款受限，信息保存成功，提交申请失败";
					applyInfo.setLoanProcessType(LoanProcessTypeEnum.APPLY.type);
				} else {
					message = "提交并申请成功";
				}
			}
			applyInfo.setLoanCustomerId(customerId);
			applyInfo.setPotentialCustomerId(potentialId);//增加潜在客户id
			//三要素校验
			SysBasicConfig sysBasicConfig = systemModule.getSysBasicConfig("sysjy");
			//是否启用三要素校验控制开关 1 启用 2 关闭
			if(StringUtils.equals(sysBasicConfig.getConfigStatus(),"1")){
				String customerName = applyInfo.getLoanName();  //贷款人姓名
				String identifyNum = applyInfo.getLoanIdentifyNum();   //证件号码
				String identifyType = applyInfo.getLoanIdentifyType();  //证件类型
				CustBasicInfo custBasicInfo = customerModuleIntf.getCustomerByCardNameType(customerName,identifyType,identifyNum);
				if(custBasicInfo != null){
					if(custBasicInfo.getBelongUserId().intValue() != loginUserId.intValue()){
						resultMap.put("success", false);
						message = "客户不归属于当前客户经理，信息保存失败";
						resultMap.put("message", message);
						return resultMap;
					}
				}
			}
			applyInfoDao.insertApplyInfo(applyInfo);
			loanId = applyInfo.getLoanId();
			//同步潜在客户影像资料到贷款资料
			if (potentialId != null && potentialId.intValue() != 0){
				syncPotentialFiles(potentialId, loanId, loginUserId);
			}
			loanSyncCustomerService.syncLoanCustomerInfo(loanId);	//同频贷款到客户,如果客户存在，则同步客户到贷款
			//转申请过了的客户，更新关联关系
			if (applyId != null) {
				intoCustomersProvider.updateIntoCustomerLoanId(applyId, loanId);
				Map<String, Object> condition = new HashMap();
				condition.put("applyId", applyId);
				List<IntoFileInfo> intoFileInfoList = intoCustomersProvider.queryFileInfoList(condition);
				for (IntoFileInfo intoFileInfo : intoFileInfoList) {
						LoanInfoAddedFiles loanInfoAddedFiles = new LoanInfoAddedFiles();
						loanInfoAddedFiles.setOwnerId(0);
						loanInfoAddedFiles.setClassId(0);
						loanInfoAddedFiles.setLoanId(loanId);
						loanInfoAddedFiles.setLoanProcessType("Apply");
						loanInfoAddedFiles.setIsDel(0);
						if (intoFileInfo.getFileName() != null) {
							String suffix = intoFileInfo.getFileName().substring(intoFileInfo.getFileName().lastIndexOf("."));
							String name = intoFileInfo.getFileName().substring(0, intoFileInfo.getFileName().lastIndexOf("."));

							loanInfoAddedFiles.setFileId(name);
							loanInfoAddedFiles.setFileFix(suffix);
							loanInfoAddedFiles.setFileName(intoFileInfo.getFileName());
						}
						loanInfoAddedFiles.setFileType("Image");
						loanInfoAddedFiles.setFilePath(intoFileInfo.getFilePath());
						loanInfoAddedFiles.setFileSize(intoFileInfo.getFileSize());
						loanInfoAddedFiles.setFileSrcName(intoFileInfo.getFileSrcName());
						loanInfoAddedFiles.setFileThumbImagePath(intoFileInfo.getFileThumbImagePath());
						loanInfoAddedFiles.setFileThumbImageName(intoFileInfo.getFileThumbImageName());
						loanInfoAddedFiles.setCallTime(0);
						loanInfoAddedFiles.setMonitorId(0);
						iInfoAddedFileProvider.insertInfoAddedFiles(loanInfoAddedFiles, loginUserId);
				}
			}
			mapTaggingProvider.syncTaggingCustomerToLoan(loanId, customerId, loginUserId);

			// 记录操作日志
			loanOperationService.addLoanOperation(loanId, LoanOperationType.LOAN_APPLY_CREATE.typeName, "", loginUserId, LoanProcessTypeEnum.APPLY.type);
			if ("Allot".equals(processType)) {
				//processType = Allot 为申请提交
				sendLoanMsg(applyInfo, null);

				Object saveLog = resultMap.get("success");
				if (saveLog != null && "true".equals(String.valueOf(saveLog))) {
					loanOperationService.addLoanOperation(loanId, LoanOperationType.LOAN_APPLY_SUBMIT.typeName, "", loginUserId, LoanProcessTypeEnum.APPLY.type);
				}
			}
		}

		List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByLoanType(String.valueOf(loanTypeId), LoanProcessTypeEnum.APPLY.type,null);
		if(CollectionUtils.isNotEmpty(templateList)){
			Map<String, Object> customerMap = new HashMap<String, Object>();
			DataTable dataTable;
			for (AutoBaseTemplate autoTemplate : templateList) {
                String tableName = autoTemplate.getTableName();
                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(tableName);
                if (CollectionUtils.isNotEmpty(list)) {
                    for (Map<String, Object> dataMap : list) {
                        setCustomerMap(customerMap, dataMap);
                        dataTable = new DataTable();
                        dataTable.setName(tableName);
                        dataTable.addColumn("ID");
                        String dataId = (String) customerMap.get("ID");
                        dataTable.newRow();
                        DataRow dataRow = dataTable.getRow(0);
                        if (StringUtils.isNotBlank(dataId) && StringUtils.isNumericSpace(dataId)) {
							dataRow.set("ID", Integer.parseInt(dataId));
							if ("LBIZ_PERSONAL_INFO".equals(tableName) || "LBIZ_FAMILY_INFO".equals(tableName) || "LBIZ_SPOUSE_INFO".equals(tableName)) {
								applyInfoDao.deleteTemplateByNameAndLoanId(tableName, LoanProcessTypeEnum.APPLY.type, loanId, Integer.parseInt(dataId));
							}
						}
                        dataRow.set("LOAN_ID", loanId);
                        dataRow.set("LOAN_PROCESS_TYPE", LoanProcessTypeEnum.APPLY.type);
                        List<AutoBaseField> fieldList = autoTemplate.getFields();
						setDataRowByFieldList(dataRow, fieldList, customerMap);
						if("LBIZ_PERSONAL_INFO".equals(tableName)&& dataId.equals("")){
							dataRow.set("CUSTOMER_NUM","0000008"+System.currentTimeMillis());
						}
                        loanApplyService.saveLoanTemplateInfo(dataTable);
                    }
                }
			}
			loanSyncCustomerService.syncLoanCustomerInfo(loanId);	//同频贷款到客户,如果客户存在，则同步客户到贷款
		}
		resultMap.put("id", String.valueOf(loanId));
		resultMap.put("message", message);
		return resultMap;
	}
//	Allot	申请提交
//	Investigate	分配完成呢
//	Approval	调查提交
//	Contract	审批完成
//	Sign	合同提交
//	Loan	合同签订
//	Authorized	已放款
//	Undisclosed	已授权
//	AfterLoan	放款完成
	@Override
	public String sendLoanMsg(LoanApplyInfo loanApplyInfo, String[] userIds){
		try{
			String process = loanApplyInfo.getLoanProcessType();
			String operName = "";
			String operRole = "";
			String superName = "";
			String superRole = "";
			BigDecimal amount = loanApplyInfo.getLoanApplyAmount().divide(new BigDecimal(10000));
			PmsUser operUser=null,superUser=null;
			if(process.equalsIgnoreCase("Allot")){
				operRole = "客户经理";
				superRole = "团队主管";
				operUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanApplyUserId());
				superUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanAllotUserId());
			}else if(process.equalsIgnoreCase("Investigate")){
				operRole = "团队主管";
				superRole = "客户经理";
				operUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanAllotUserId());
				superUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanInvestigationUserId());
			}else if(process.equalsIgnoreCase("Approval")){
				amount = loanApplyInfo.getLoanProposeAmount().divide(new BigDecimal(10000));
				operRole = "客户经理";
				superRole = "审批人员";
				operUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanInvestigationUserId());
				for (int i = 0; i < userIds.length; i++) {
					if(i!=0){
						superName+=",";
					}
					superUser = permissionModule.getPmsUserByUserId(Integer.valueOf(userIds[i]));
					superName += superUser.getUserName();
				}
				superUser = null;
			}else if(process.equalsIgnoreCase("Contract")){
				amount = loanApplyInfo.getLoanResultAmount().divide(new BigDecimal(10000));
				operRole = "审批人员";
				superRole = "客户经理";
				for (int i = 0; i < userIds.length; i++) {
					operUser = permissionModule.getPmsUserByUserId(Integer.valueOf(userIds[i]));
				}
				superUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanInvestigationUserId());
			}else if(process.equalsIgnoreCase("Sign")){
				operRole = "客户经理";
				superRole = "合同复核";
				operUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanInvestigationUserId());
				superUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getContractCheckUser());
			}else if(process.equalsIgnoreCase("Loan")){
				operRole = "合同复核";
				superRole = "后台人员";
				operUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getContractCheckUser());
				superName="后台人员";
//			superUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanCreditUserId());
			}else if(process.equalsIgnoreCase("Authorized")){
				amount = loanApplyInfo.getLoanCreditAmount().divide(new BigDecimal(10000));
				operRole = "后台人员";
				superRole = "放款授权";
				operUser = permissionModule.getPmsUserByUserId(loanApplyInfo.getLoanCreditUserId());
				superName="放款授权人员";
			}else if(process.equalsIgnoreCase("Undisclosed")){
				operRole = "放款授权";
				for (int i = 0; i < userIds.length; i++) {
					operUser = permissionModule.getPmsUserByUserId(Integer.valueOf(userIds[i]));
				}
			}else if(process.equalsIgnoreCase("AfterLoan")){

			}

			if(null!=operUser){
				operName = operUser.getUserName();
			}
			if(null!=superUser){
				superName = superUser.getUserName();
			}

//		String operType,String operName,String operRole,String amount,String customerName,String customerMobile,String superName,String superRole,String loanTypeName
			LoanMsgUtil.sendLoanMsg(process,operName,operRole,amount.doubleValue(),loanApplyInfo.getLoanName(),loanApplyInfo.getLoanTelNum(),superName,superRole,loanApplyInfo.getLoanTypeId(),tpUrl);
		} catch (Exception e){

		}


		return null;
	}


	/**
     * 新建applyInfo对象是设置默认的值
     * @param processType 贷款流程
     * @param loanTypeId 贷款类型
     * @param UserId 创建人ID
     */
    private void setDefaultValues(LoanApplyInfo applyInfo, String processType, Integer loanTypeId, Integer UserId){
        applyInfo.setLoanProcessType(processType);
        applyInfo.setLoanTypeId(loanTypeId);
        applyInfo.setCreateUser(UserId);
        applyInfo.setUpdateUser(UserId);
        applyInfo.setLoanBelongId(UserId);
		applyInfo.setLoanApplyUserId(UserId);
        if (LoanProcessTypeEnum.ALLOT.type.equals(processType)) {
            applyInfo.setLoanApplyDate(new Date());
        }
        applyInfo.setCreateDate(new Date());
        applyInfo.setUpdateDate(new Date());
    }
    /**
     * 将原始数据转换成表单map
     * @param customerMap
     * @param map
     */
	private void setCustomerMap(Map<String, Object> customerMap, Map<String, Object> map) {
		customerMap.clear();
		for (String key : map.keySet()) {
			String val = (String) map.get(key);
			if (key.indexOf("field.") > -1) {
				String propertyName = key.substring("field.".length());
				if(val != null){
					if ("identifyNum".equals(propertyName))
						val = IdCardUtil.toUpperCase(val);
					if ("idCard".equals(propertyName))
						val = IdCardUtil.toUpperCase(val);
					customerMap.put(propertyName, val);
				}
			}
		}
	}
	/**
	 * 私有方法，从表单map设值给applyInfo
	 * @param applyInfo
	 * @param map
	 */
	private void setApplyInfo(LoanApplyInfo applyInfo, Map<String, Object> map){
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("LBIZ_PERSONAL_INFO");
		Map<String, Object> customerMap = new HashMap<String, Object>();
		if (CollectionUtils.isNotEmpty(list))
			customerMap = list.get(0);


		applyInfo.setLoanName((String)customerMap.get("field.customerName"));
		applyInfo.setLoanIdentifyType((String)customerMap.get("field.identifyType"));
		applyInfo.setLoanIdentifyNum((String)customerMap.get("field.identifyNum"));
		String customerAge = (String)customerMap.get("field.customerAge");
		if(StringUtils.isNotBlank(customerAge) && StringUtils.isNumeric(customerAge) ){
			applyInfo.setLoanAge(Integer.valueOf(customerAge));
		}
		applyInfo.setLoanSex((String)customerMap.get("field.customerSex"));
		applyInfo.setLoanTelNum((String)customerMap.get("field.phoneNumber"));

        list = (List<Map<String, Object>>) map.get("LBIZ_LOAN_APPLY_INFO");
        if (CollectionUtils.isNotEmpty(list))
            customerMap = list.get(0);
		String loanApplyAmout = (String)customerMap.get("field.loanApplyAmount");
		if (StringUtils.isNotBlank(loanApplyAmout)){
			BigDecimal bd = new BigDecimal(loanApplyAmout);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			applyInfo.setLoanApplyAmount(bd);
		}
		//保存行业
		list = (List<Map<String, Object>>) map.get("LBIZ_BUSINESS_SUBJECT");
		if (CollectionUtils.isNotEmpty(list)){
			String businessCatalog = (String)list.get(0).get("field.businessCatalog");
			applyInfo.setLoanBusinessCatalog(businessCatalog);
		}
	}

	/**
	 * web分配保存
	 * @param ids
	 * @param memberUserId
	 * @param loginUserId
	 * @return
	 */
	@Override
    public Map<String, Object> loanAllotSignSave(String ids, Integer memberUserId, Integer loginUserId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String logDesc = "分配给";
		resultMap.put("success", true);
		String message = "";
		if (StringUtils.isBlank(ids) || memberUserId.intValue() == 0 || loginUserId == null) {
			resultMap.put("success", false);
			resultMap.put("message", "参数错误");
			return resultMap;
		}
        String[] loanIds = ids.split(",");
//        boolean jx = false;
//		SysBasicConfig config = iBasicConfigProvider.getSysBasicConfigByKey("jcdc");
//		if (config != null && 1 == config.getConfigValue().intValue()) {
//			jx = true;
//		}
		for (int i = 0; i < loanIds.length; i++) {
			Integer id = Integer.parseInt(loanIds[i]);
			LoanApplyInfo applyInfo = getApplyInfoById(id);
			LoanType loanType=typeDao.getTypeById(applyInfo.getLoanTypeId());
			if (applyInfo == null) {
				resultMap.put("success", false);
				resultMap.put("message", "参数错误,请刷新数据后操作");
				return resultMap;
			}

			if (loanType.getIsAutoAllot() !=null && loanType.getIsAutoAllot().intValue() == 0) {
				applyInfo.setLoanAllotUserId(loginUserId);
			} else {
				logDesc = "自动分配给";
			}

			if (memberUserId.intValue() == applyInfo.getLoanApplyUserId().intValue()&&loanType.getAllotTarget()!=null&&loanType.getAllotTarget().intValue()==2){
				if (StringUtils.isBlank(message))
					message = applyInfo.getLoanName();
				else
					message += "," + applyInfo.getLoanName();
				continue;
			}
//			if (jx && applyInfo.getLoanApplyUserId().intValue() == memberUserId.intValue()) {
//				if (StringUtils.isBlank(message))
//					message = applyInfo.getLoanName();
//				else
//					message += "," + applyInfo.getLoanName();
//				continue;
//			}

            applyInfo.setLoanBelongId(memberUserId);
            applyInfo.setLoanAssignmentDate(new Date());
            applyInfo.setLoanProcessType(LoanProcessTypeEnum.INVESTIGATE.type);
            applyInfo.setLoanInvestigationUserId(memberUserId);
            updateApplyInfo(applyInfo, loginUserId);
            loanInfoSyncService.syncApplyToInvestigate(id);
			loanSyncCustomerService.updateBelongId(applyInfo.getLoanCustomerId(), memberUserId);

			if(applyInfo.getLoanClassId()!=null && applyInfo.getLoanClassId().intValue()>0) {
				LoanProfitLossInfo loanProfitLossInfo = new LoanProfitLossInfo();
				loanProfitLossInfo.setLoanId(applyInfo.getLoanId());
				loanProfitLossInfo.setLoanClassId(applyInfo.getLoanClassId());
				profitLossInfoService.saveProfitLossInfo(loanProfitLossInfo, applyInfo.getLoanBelongId());

				LoanAssetsInfo assetsInfo = new LoanAssetsInfo();
				assetsInfo.setLoanId(applyInfo.getLoanId());
				assetsInfo.setLoanClassId(applyInfo.getLoanClassId());
				assetsInfoService.saveAssetsInfo(assetsInfo, applyInfo.getLoanBelongId());
			}

			PmsUser user = permissionModule.getPmsUserByUserId(memberUserId);

			// 记录操作日志
			loanOperationService.addLoanOperation(applyInfo.getLoanId(), LoanOperationType.LOAN_APPLY_ALLOT.typeName, logDesc + user.getUserName(), loginUserId, LoanProcessTypeEnum.ALLOT.type);
			sendLoanMsg(applyInfo,null);

		}
		if (StringUtils.isBlank(message)) {
			message = "分配成功";
		} else {
			resultMap.put("success", false);
			message = message + "的贷款不符合分配规则，请重新分配！";
		}
		resultMap.put("message", message);
        return resultMap;
    }

	@Override
	public DataTable getDataTableById(String tableName, Integer id){
		return applyInfoDao.getDataTableById(tableName, id);
	}

	@Override
	public DataTable getApprovalDataTableByLoanId(Integer loanId){
		return applyInfoDao.getApprovalDataTableByLoanId(loanId);
	}

	public DataTable selectApprovalDataTableByLoanId(Integer loanId){
		return applyInfoDao.selectApprovalDataTableByLoanId(loanId);
	}
	@Override
	public DataTable getPledgeDataTableByLoanId(String presType, Integer loanId, Integer mortgageOrPledge){
		return applyInfoDao.getPledgeDataTableByLoanId(presType, loanId, mortgageOrPledge);
	}

	@Override
	public DataTable getPledgeDataTableById(Integer id) {
		return applyInfoDao.getPledgeDataTableById(id);
	}


	@Override
	public void updatePledgeStatusById(Integer id, String status) {
		applyInfoDao.updatePledgeStatusById(id,status);
	}

	@Override
	public void updatePledgeById(PledgInfo pledgInfo) {
		applyInfoDao.updatePledgeById(pledgInfo);
	}

	@Override
	public DataTable getDataTableByLoanId(String tableName, String presType, Integer id){
		return applyInfoDao.getDataTableByLoanId(tableName, presType, id);
	}

	@Override
	public DataTable getDataTableByLoanIdAndUserId(String tableName, String presType, Integer loanId, Integer loginUserId){
		return applyInfoDao.getDataTableByLoanIdAndUserId(tableName, presType, loanId, loginUserId);
	}

	public List<DepositBank> getDepositBankListByCondition(Map<String,Object> condition){
		return applyInfoDao.getDepositBankListByCondition(condition);
	}

	public String getLoanOrientationJson(){
		List<LoanOrientation> list = applyInfoDao.getLoanOrientationList();
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				LoanOrientation orientation = list.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", orientation.getId());
				jo.put("pId", orientation.getpId());
				jo.put("name", orientation.getCnName());
				jo.put("icon", orientation.getIcon());
				jo.put("locate",orientation.getLocate());
				ja.add(jo);
			}
		}
		return ja.toString();
	}


	@Override
	public JSONArray getLoanOrientationTreeJson(){
		List<LoanOrientation> list = applyInfoDao.getLoanOrientationList();
		JSONArray ja = new JSONArray();
		JSONArray jaa = new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				LoanOrientation orientation = list.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", StringUtils.isNotBlank(orientation.getId())?orientation.getId():"");
				jo.put("pId", StringUtils.isNotBlank(orientation.getpId())?orientation.getpId():"");
				jo.put("name", StringUtils.isNotBlank(orientation.getCnName())?orientation.getCnName():"");
				jo.put("icon", StringUtils.isNotBlank(orientation.getIcon())?orientation.getIcon():"");
				jo.put("locate",StringUtils.isNotBlank(orientation.getCnName())?orientation.getCnName():"");
				ja.add(jo);
				if(orientation.getpId().equals("0")){
					jaa.add(jo);
				}
			}
			if(null!=jaa){
				for (int i = 0; i < jaa.size(); i++) {
					recursionJson(jaa.getJSONObject(i), ja);
				}
			}
		}
		return jaa;
	}

	@Override
	public String getBizAndPrdTypeJson(Map<String,Object> condition) {
		List<BizTypeSub> list = applyInfoDao.queryTypeSubList(condition);
		JSONArray ja = new JSONArray();
		BizTypeSub bizTypeSub = null;
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				bizTypeSub = list.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", bizTypeSub.getSubCode());
				jo.put("pId", bizTypeSub.getParentCode());
				if (bizTypeSub.getCmiName()!=null) {
					jo.put("name", bizTypeSub.getSubName() + "--" + bizTypeSub.getCmiName());
				}else {
					jo.put("name", bizTypeSub.getSubName());
				}
				jo.put("locate",bizTypeSub.getSubOrder());
				jo.put("prdNam",bizTypeSub.getPrdName());
				jo.put("prdCode",bizTypeSub.getPrdCode());
				jo.put("prdPk",bizTypeSub.getPrdPk());
				jo.put("cmiCode",bizTypeSub.getCmiCode());
				jo.put("cmiName",bizTypeSub.getCmiName());
				ja.add(jo);
			}
		}
		return ja.toString();
	}



	@Override
	public JSONArray getBizAndPrdTypeTreeJson() {
		List<BizTypeSub> list = applyInfoDao.queryTypeSubList(null);
		JSONArray ja = new JSONArray();
		JSONArray jaa = new JSONArray();
		BizTypeSub bizTypeSub = null;
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				bizTypeSub = list.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", StringUtils.isNotBlank(bizTypeSub.getSubCode())?bizTypeSub.getSubCode():"");
				jo.put("pId", StringUtils.isNotBlank(bizTypeSub.getParentCode())?bizTypeSub.getParentCode():"");
				if (bizTypeSub.getCmiName()!=null) {
					jo.put("name", bizTypeSub.getSubName() + "--" + bizTypeSub.getCmiName());
				}else {
					jo.put("name", StringUtils.isNotBlank(bizTypeSub.getSubName())?bizTypeSub.getSubName():"");
				}
				jo.put("locate",StringUtils.isNotBlank(bizTypeSub.getSubName())?bizTypeSub.getSubName():"");
				jo.put("prdNam",StringUtils.isNotBlank(bizTypeSub.getPrdName())?bizTypeSub.getPrdName():"");
				jo.put("prdCode",StringUtils.isNotBlank(bizTypeSub.getPrdCode())?bizTypeSub.getPrdCode():"");
				jo.put("prdPk",StringUtils.isNotBlank(bizTypeSub.getPrdPk())?bizTypeSub.getPrdPk():"");
				jo.put("cmiCode",StringUtils.isNotBlank(bizTypeSub.getCmiCode())?bizTypeSub.getCmiCode():"");
				jo.put("cmiName",StringUtils.isNotBlank(bizTypeSub.getCmiName())?bizTypeSub.getCmiName():"");
				ja.add(jo);
				if(StringUtils.isBlank(bizTypeSub.getParentCode())){
					jaa.add(jo);
				}
			}
		}

		if(null!=jaa){
			for (int i = 0; i < jaa.size(); i++) {
				recursionJson(jaa.getJSONObject(i), ja);
			}
		}

		return jaa;
	}

	private void recursionJson(JSONObject jo,JSONArray ja){

		JSONObject o ;
		String pId = null;
		JSONArray a ;
		for (int i = 0; i < ja.size(); i++) {
			o = ja.getJSONObject(i);
			pId = o.getString("pId");
			if(StringUtil.isNotEmpty(pId)&&jo.getString("id").equals(pId)){
				if(jo.containsKey("children")){
					a = jo.getJSONArray("children");
				}else{
					a = new JSONArray();
				}
				if(jo.containsKey("locate")&&o.containsKey("locate")){
					o.put("locate",jo.getString("locate")+">"+o.getString("locate"));
				}

				recursionJson(o, ja);
				a.add(o);
				jo.put("children",a);

			}
		}

	}

	@Override
	public String queryCrmPrdTypeJson(Map<String, Object> condition) {
		List<CrmPrdType> list = applyInfoDao.queryCrmPrdType(condition);
		JSONArray ja = new JSONArray();
		CrmPrdType data = null;
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				data = list.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", data.getSubCode());
				jo.put("pId", data.getYwtxbm());
				jo.put("ywtx",data.getYwtx());
				jo.put("name",data.getPrdName());
				jo.put("crmCode",data.getCrmCode());
				jo.put("subCode",data.getSubCode());
				ja.add(jo);
			}
		}
		return ja.toString();
	}

	@Override
	public JSONArray queryCrmPrdTypeTreeJson() {
		List<CrmPrdType> list = applyInfoDao.queryCrmPrdType(null);
		JSONArray ja = new JSONArray();
		JSONArray jaa = new JSONArray();
		CrmPrdType data = null;
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				data = list.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", StringUtils.isNotBlank(data.getSubCode())?data.getSubCode():"");
				jo.put("pId", StringUtils.isNotBlank(data.getYwtxbm())?data.getYwtxbm():"");
				jo.put("ywtx",StringUtils.isNotBlank(data.getYwtx())?data.getYwtx():"");
				jo.put("name",StringUtils.isNotBlank(data.getPrdName())?data.getPrdName():"");
				jo.put("crmCode",StringUtils.isNotBlank(data.getCrmCode())?data.getCrmCode():"");
				jo.put("subCode",StringUtils.isNotBlank(data.getSubCode())?data.getSubCode():"");
				ja.add(jo);
				if(StringUtils.isBlank(data.getYwtxbm())){
					jaa.add(jo);
				}
			}
			if(null!=jaa){
				for (int i = 0; i < jaa.size(); i++) {
					recursionJson(jaa.getJSONObject(i), ja);
				}
			}
		}
		return jaa;
	}




//	private

	/**
	 * 拒绝贷款信息保存
	 * @param refuseType 拒绝类型
	 * @param refuseReason 原因
	 * @param refuseRemark 备注
	 * @param joinBlack 是否拉黑
	 * @param loanId 贷款主键
	 * @param loginUserId 用户id
	 * @return
	 */
	@Override
	public boolean saveRefuseInfo(String refuseType, String refuseReason, String refuseRemark, String joinBlack, String loanId, Integer loginUserId) {
		if(!StringUtils.isNotBlank(loanId) || !StringUtils.isNumeric(loanId) ) return  false;
		int id = Integer.valueOf(loanId);
		//LoanApplyInfo applyInfo = new LoanApplyInfo();
//		applyInfo.setLoanId(id);
		LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(id);
		if (applyInfo != null) {
			if ("1".equals(joinBlack)) {
				CustCustomerBlack custCustomerBlack = new CustCustomerBlack();
				custCustomerBlack.setIsDel(0);
				custCustomerBlack.setCustomerName(applyInfo.getLoanName());
				custCustomerBlack.setCardType(applyInfo.getLoanIdentifyType());
				custCustomerBlack.setCardNo(applyInfo.getLoanIdentifyNum());
				custCustomerBlack.setCustomerId(applyInfo.getLoanCustomerId());
				custCustomerBlack.setApproveStatus(2);
				custCustomerBlack.setApproveDate(new Date());
				custCustomerBlack.setApproveUserId(applyInfo.getCreateUser());
				custCustomerBlack.setCreateUser(applyInfo.getCreateUser());
				customerBlackProvider.saveCustomerBlack(custCustomerBlack);
			}
			applyInfo.setLoanRefuseUser(loginUserId);
			applyInfo.setLoanRefuseDate(new Date());
			applyInfo.setLoanRefuseReason(refuseReason);
			applyInfo.setLoanRefuseType(refuseType);
			applyInfo.setLoanRefuseRemark(refuseRemark);
			applyInfoDao.updateApplyInfo(applyInfo);
			//loanSyncCustomerService.syncLoanCustomerInfo(id);	//同频贷款到客户,如果客户存在，则同步客户到贷款

			//操作拒绝日志
			String content = "";
			if ("99".equals(refuseReason)) {
				if (StringUtil.isNotEmpty(refuseRemark)) {
					content = refuseRemark;
				}
			} else {
				if (StringUtil.isNotEmpty(refuseRemark)) {
					content = getRefuseReasonText(refuseReason, refuseType)+":"+refuseRemark;
				}else{
					content = getRefuseReasonText(refuseReason, refuseType);
				}
			}
			LoanOperationType operationType = LoanRefuseType.BANK.type.equals(refuseType) ? LoanOperationType.LOAN_BANK_REFUSE : LoanOperationType.LOAN_CUSTOMER_REFUSE;
			loanOperationService.addLoanOperation(applyInfo.getLoanId(), operationType.typeName, content, loginUserId, applyInfo.getLoanProcessType());
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 得到拒绝理由文本
	 * @param refuseReason
	 * @return
	 */
	private String getRefuseReasonText(String refuseReason,String refuseType){
		List<AutoBaseOption> optionList = getDictOptions(LoanRefuseType.BANK.type.equals(refuseType)?"CD_LOAN_BANK_REFUSE_REASON":"CD_LOAN_CUSTOMER_REFUSE_REASON");
		for(AutoBaseOption option : optionList){
			if(option.getValue().equals(refuseReason)){
				return option.getName();
			}
		}
		return "";
	}
	/**
	 * 推得到贷款业务类型
	 * @return
	 */
	private List<AutoBaseOption> getDictOptions(String dictName){
		List<AutoBaseOption> list = new ArrayList<AutoBaseOption>();
		List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable("cdDictColByName", dictName);
		for(ICodeTable.IItem item : items){
			list.add(new AutoBaseOption(item.getValue(),item.getName()));
		}
		return list;
	}

	/**
	 * 回退保存
	 * @param loanId
	 * @param loginerId
	 * @return
	 */
	@Override
	public boolean backApplyInfo(String loanId, Integer loginerId, String note) {
		if(!StringUtils.isNotBlank(loanId) || !StringUtils.isNumeric(loanId) ) return  false;
		int id = Integer.valueOf(loanId);
		LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(id);
		if (applyInfo != null) {
			String processType = applyInfo.getLoanProcessType();
			String prevType = LoanProcessTypeEnum.getPrevType(processType);
			String operationTypeName =  LoanOperationType.LOAN_APPLY_BACK.typeName;
			String loanProcessType = applyInfo.getLoanProcessType();
			if (LoanProcessTypeEnum.APPROVAL.type.equals(loanProcessType)){
				//根据贷款信息更新所有审批信息为不可用
				operationTypeName =  LoanOperationType.LOAN_APPROVAL_BACK.typeName;
				currentAuditStatusService.updateAuditStatusByLoanId(Integer.parseInt(loanId));
				repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), loanProcessType);
			} else if (LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())){
				applyInfo.setLoanBelongId(applyInfo.getLoanApplyUserId());
				if (applyInfo.getLoanAllotUserId().intValue() == 0) {
					prevType = LoanProcessTypeEnum.APPLY.type;
				}
				loanSyncCustomerService.updateBelongId(applyInfo.getLoanCustomerId(), applyInfo.getLoanApplyUserId());
				applyInfo.setLoanInvestigationUserId(0);
				repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), loanProcessType);
			}

			// 记录操作日志
			loanOperationService.addLoanOperation(applyInfo.getLoanId(), operationTypeName, note, loginerId, processType);

			applyInfo.setLoanProcessType(prevType);
			applyInfoDao.updateApplyInfo(applyInfo);
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * @param map 表单表名
	 * @param id 贷款ID loanId
	 * @param loginUserId  登录userId
	 * @return
	 */
	@Override
	public String saveLoanInvestigateInfo(Map<String, Object> map, String id, Integer loginUserId) {

		Integer loanId = 0;
		if(StringUtils.isNotBlank(id) && StringUtils.isNumeric(id) ) {
			loanId = Integer.valueOf(id);
			LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(loanId);
			if (applyInfo != null) {
				Integer loanTypeId = applyInfo.getLoanTypeId();
				// 得到贷款环节模板集合 templateList 模板 不包含贷款数据
				List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByLoanType(String.valueOf(loanTypeId), LoanProcessTypeEnum.INVESTIGATE.type,null);
				applyInfo.setLoanBelongId(loginUserId);
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.INVESTIGATE.type);

				setApplyInfo(applyInfo, map);
				setInvestigateInfo(applyInfo, map);
				applyInfo.setUpdateDate(new Date());
				applyInfo.setUpdateUser(loginUserId);
				applyInfoDao.updateApplyInfo(applyInfo);
				loanSyncCustomerService.syncLoanCustomerInfo(loanId);	//同频贷款到客户,如果客户存在，则同步客户到贷款

				Map<String, Object> customerMap = new HashMap<String, Object>();
				DataTable dataTable;
				for (AutoBaseTemplate autoTemplate : templateList) {
					String tableName = autoTemplate.getTableName();
					List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(tableName);
					if (CollectionUtils.isNotEmpty(list)) {
						for (Map<String, Object> dataMap : list) {
							setCustomerMap(customerMap, dataMap);
							dataTable = new DataTable();
							dataTable.setName(tableName);
							dataTable.addColumn("ID");
							String dataId = (String) customerMap.get("ID");

							dataTable.newRow();
							DataRow dataRow = dataTable.getRow(0);
							if (StringUtils.isNotBlank(dataId) && StringUtils.isNumericSpace(dataId)) {
								dataRow.set("ID", Integer.parseInt(dataId));
								DataTable table = loanApplyService.getLoanTemplateDataById(tableName, Integer.parseInt(dataId));
								if("LBIZ_PLEDGE_ITEM".equals(tableName)) {
									if (table != null&&table.rowSize()>0) {
//										if(applyInfo.getSyncStatus()==-1){//改前
										if(null!=applyInfo.getSyncStatus()&&-1==applyInfo.getSyncStatus()){//改后
											dataRow.set("WARRANTY_NUM", CodedProduceUtil.getCode(OperationCode.CODE_300.getCode(), true));
										}
										DataRow row = table.getRow(0);
										String num = (String) row.get("WARRANTY_NUM");
										String cusCode = (String) row.get("CUSTOMER_NUM");
										if (StringUtil.isNullOrEmpty(num)) {
											dataRow.set("WARRANTY_NUM", CodedProduceUtil.getCode(OperationCode.CODE_300.getCode(), true));
										}
										if (StringUtil.isNullOrEmpty(cusCode)) {
											dataRow.set("CUSTOMER_NUM", CodedProduceUtil.getCusCode());
										}
									}else{
										dataRow.set("WARRANTY_NUM", CodedProduceUtil.getCode(OperationCode.CODE_300.getCode(), true));
										dataRow.set("CUSTOMER_NUM",CodedProduceUtil.getCusCode());
									}
								}
								if("LBIZ_LOAN_GUARANTEE".equals(tableName)){
									if (table != null&&table.rowSize()>0) {
										DataRow row = table.getRow(0);
										String cusCode = (String) row.get("CUSTOMER_NUM");
										if(null!=applyInfo.getSyncStatus()&&applyInfo.getSyncStatus()==-1){
											dataRow.set("CUSTOMER_NUM", CodedProduceUtil.getCusCode());
										}
										if (StringUtil.isNullOrEmpty(cusCode)) {
											dataRow.set("CUSTOMER_NUM", CodedProduceUtil.getCusCode());
										}
									}else{
										dataRow.set("CUSTOMER_NUM",CodedProduceUtil.getCusCode());
									}
								}
							}else{
								if("LBIZ_PLEDGE_ITEM".equals(tableName)){
									dataRow.set("WARRANTY_NUM", CodedProduceUtil.getCode(OperationCode.CODE_300.getCode(), true));
									dataRow.set("CUSTOMER_NUM",CodedProduceUtil.getCusCode());
								}
								if("LBIZ_LOAN_GUARANTEE".equals(tableName)){
									dataRow.set("CUSTOMER_NUM",CodedProduceUtil.getCusCode());
								}
							}
							dataRow.set("LOAN_ID", loanId);
							dataRow.set("LOAN_PROCESS_TYPE", LoanProcessTypeEnum.INVESTIGATE.type);
							List<AutoBaseField> fieldList = autoTemplate.getFields();
							setDataRowByFieldList(dataRow, fieldList, customerMap);
							loanApplyService.saveLoanTemplateInfo(dataTable);
						}
					}
				}
				//更新贷款表的评分得分
				BigDecimal score = iLoanModelScoreService.countLoanModelScoreByModeId(applyInfo.getLoanId(),
						applyInfo.getLoanTypeId(), LoanProcessTypeEnum.INVESTIGATE.type);
				LoanApplyInfo applyInfo1 = new LoanApplyInfo();
				applyInfo1.setLoanId(applyInfo.getLoanId());
				applyInfo1.setModelScore(score);
				applyInfoDao.updateModelScoreByLoanId(applyInfo1);
			}
		}

		return String.valueOf(loanId);
	}
	/**
	 * 私有方法，从表单map设值给applyInfo
	 * @param applyInfo
	 * @param map
	 */
	private void setInvestigateInfo(LoanApplyInfo applyInfo, Map<String, Object> map){
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("LBIZ_SURVEY_RESULT");
		Map<String, Object> customerMap = new HashMap<String, Object>();
		if (CollectionUtils.isNotEmpty(list)) {
			customerMap = list.get(0);
		}
		String proposalAmount = (String) customerMap.get("field.proposalAmount");
		if (StringUtils.isNotBlank(proposalAmount))
			applyInfo.setLoanProposeAmount(new BigDecimal(proposalAmount));
		String repaymentMode = (String) customerMap.get("field.repaymentMode");
		if (StringUtils.isNotBlank(repaymentMode))
			applyInfo.setRepaymentMode(repaymentMode);


	}



	@Override
    public void deleteLoanTemplateByMap(Map<String, Object> deleteIdsMap) {
        for (String key : deleteIdsMap.keySet()) {
            if (key.indexOf("LBIZ_") > -1) {
                if(StringUtils.isNotBlank(String.valueOf(deleteIdsMap.get(key)))){
                	String ids = String.valueOf(deleteIdsMap.get(key));
                	if (StringUtils.isNotBlank(ids)) {
                		String[] idList = ids.split(",");
						for (int i = 0; i < idList.length; i++) {
							int id = Integer.parseInt(idList[i]);
							applyInfoDao.deleteTemplateByNameAndId(key, id);
						}
					}


                }
            }
        }
    }


	/**
	 * 保存放贷信息
	 * @param map
	 * @param id
	 * @param loginUserId
	 * @param isLoanMoney 【是否放款，不是放款仅仅保存】
	 * @return
	 */
    @Override
	public String  saveLoanLoanMoneyInfo(Map<String, Object> map, String id, Integer loginUserId, boolean isLoanMoney) {
		Integer loanId = 0;
		if(StringUtils.isNotBlank(id) && StringUtils.isNumeric(id) ) {
			loanId = Integer.valueOf(id);
			LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(loanId);
			if (applyInfo != null) {
				Integer loanTypeId = applyInfo.getLoanTypeId();
				List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByLoanType(String.valueOf(loanTypeId), LoanProcessTypeEnum.LOAN.type,null);
				applyInfo.setUpdateDate(new Date());
				applyInfo.setUpdateUser(loginUserId);
				if (isLoanMoney) {
					applyInfo.setSyncStatus(2);
					applyInfo.setLoanProcessType(LoanProcessTypeEnum.AFTER_LOAN.type);
					applyInfo.setLoanAfterState(AfterLoanTypeEnum.NORMAL.code);
					Integer investigateUserId = applyInfo.getLoanInvestigationUserId();
					if (investigateUserId != null) {
						Integer teamGroupId=permissionModule.getGroupIdByUserId(investigateUserId);
						applyInfo.setLoanAfterGroupId(teamGroupId);
					}
				}
				applyInfo.setUpdateDate(new Date());
				applyInfo.setUpdateUser(loginUserId);

				List<Map<String, Object>> loanList = (List<Map<String, Object>>) map.get("LBIZ_LOAN_GRANT");
				if (CollectionUtils.isNotEmpty(loanList)) {
					Map<String, Object> firstMap = loanList.get(0);
					String loanCreditDate = (String) firstMap.get("field.loanCreditDate");
					String resultAmount = (String) firstMap.get("field.loanAmount");
					if (StringUtils.isNotBlank(resultAmount))
						applyInfo.setLoanCreditAmount(new BigDecimal(resultAmount));
					String repaymentMode = (String) firstMap.get("field.loanBackMode");
					if (StringUtils.isNotBlank(repaymentMode))
						applyInfo.setRepaymentMode(repaymentMode);
					if (StringUtils.isNotBlank(loanCreditDate))
						applyInfo.setLoanCreditDate((Date) TypeUtil.changeType(loanCreditDate, Date.class));
					//合同号
					String contractNumber = (String) firstMap.get("field.loanContractNumber");
					if(StringUtils.isNotBlank(contractNumber)){
						applyInfo.setLoanContractNumber(contractNumber);
					}

				}

				Map<String, Object> customerMap = new HashMap<String, Object>();
				DataTable dataTable;
				for (AutoBaseTemplate autoTemplate : templateList) {
					String tableName = autoTemplate.getTableName();
					List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(tableName);
					if (CollectionUtils.isNotEmpty(list)) {
						for (Map<String, Object> dataMap : list) {
							setCustomerMap(customerMap, dataMap);
							String oldBackMode = applyInfoDao.selectLoanBackModeByLoanId(loanId);
							if (oldBackMode!=null && !oldBackMode.equals(applyInfo.getRepaymentMode())) {
								repayPlanInfoService.deleteRepayPlanInfoByLoanId(loanId, LoanProcessTypeEnum.APPROVAL.type);
							}
							dataTable = new DataTable();
							dataTable.setName(tableName);
							dataTable.addColumn("ID");
							String dataId = (String) customerMap.get("ID");

							dataTable.newRow();
							DataRow dataRow = dataTable.getRow(0);
							if (StringUtils.isNotBlank(dataId) && StringUtils.isNumericSpace(dataId))
								dataRow.set("ID", Integer.parseInt(dataId));
							dataRow.set("LOAN_PROCESS_TYPE", LoanProcessTypeEnum.LOAN.type);
							dataRow.set("LOAN_ID", loanId);
							dataRow.set("CREATE_DATE", new Date());
							dataRow.set("UPDATE_DATE", new Date());
							dataRow.set("CREATE_USER", loginUserId);
							dataRow.set("UPDATE_USER", loginUserId);

							List<AutoBaseField> fieldList = autoTemplate.getFields();
							setDataRowByFieldList(dataRow, fieldList, customerMap);
							loanApplyService.saveLoanTemplateInfo(dataTable);
						}
                    }
                }
                updateApplyInfo(applyInfo, loginUserId);
				if (isLoanMoney) {
					loanApplyService.creatRepayPlanInfo(loanId, loginUserId);
					loanTaskProvider.updateLoanTaskAmount(loanId);
					// 记录操作日志
					loanOperationService.addLoanOperation(loanId, LoanOperationType.LOAN_LOAN.typeName, "", loginUserId, LoanProcessTypeEnum.LOAN.type);
				}
			}
			return String.valueOf(loanId);
		} else {
			return "";
		}
	}

	private void setDataRowByFieldList(DataRow dataRow, List<AutoBaseField> fieldList, Map<String, Object> customerMap) {
		if(CollectionUtils.isNotEmpty(fieldList)){
			for (AutoBaseField baseFiled : fieldList) {
				String column = baseFiled.getColumn();
				String field = baseFiled.getField();
				Object o= MapUtils.getObject(customerMap,field);
				dataRow.set(column,o);
			}
		}
	}


	/**
	 * 查询需转交的所有贷款
	 * @param condition 参数条件
	 * @return
	 */
    public List<LoanApplyInfo> queryHandOverLoanIds(Map<String, Object> condition) {
        return this.applyInfoDao.queryApplyInfoList(condition);
    }


	public List <LoanApplyInfo_Web_Query>queryMinInGroup(Integer teamGroupId) {
		return applyInfoDao.queryMinInGroup(teamGroupId);
	}

	/**
	 * 是否使用分配受限表(截至2017年11月29日12:30:33 暂时没有调用该方法记录)
	 * @return
	 */
	@Override
	public String queryLoanAllotConfigStatus() {
		return applyInfoDao.queryLoanAllotConfigStatus();
	}

	@Override
	public void syncPotentialFiles(Integer potentialId, Integer loanId, Integer userId) {
		List<CustPotentialCustomersFiles> files = potentialCustomerFileProvider.getPotentialFilesListByPotentialId(potentialId);
		if (CollectionUtils.isNotEmpty(files)) {
			for (CustPotentialCustomersFiles file : files) {
				LoanInfoAddedFiles loanInfoAddedFiles = new LoanInfoAddedFiles();
				loanInfoAddedFiles.setOwnerId(0);
				loanInfoAddedFiles.setClassId(0);
				loanInfoAddedFiles.setLoanId(loanId);
				loanInfoAddedFiles.setLoanProcessType("Apply");
				loanInfoAddedFiles.setIsDel(0);
				if(file.getFileName()!=null){
					String suffix = file.getFileName().substring(file.getFileName().lastIndexOf("."));
					String name = file.getFileName().substring(0, file.getFileName().lastIndexOf("."));
					loanInfoAddedFiles.setFileId(name);
					loanInfoAddedFiles.setFileFix(suffix);
					loanInfoAddedFiles.setFileName(file.getFileName());
				}
				loanInfoAddedFiles.setFileType("Image");
				loanInfoAddedFiles.setFilePath(file.getFilePath());
				loanInfoAddedFiles.setFileSize(file.getFileSize());
				loanInfoAddedFiles.setFileSrcName(file.getFileSrcName());
				loanInfoAddedFiles.setFileThumbImagePath(file.getFileThumbImagePath());
				loanInfoAddedFiles.setFileThumbImageName(file.getFileThumbImageName());
				loanInfoAddedFiles.setCallTime(0);
				loanInfoAddedFiles.setMonitorId(0);
				iInfoAddedFileProvider.insertInfoAddedFiles(loanInfoAddedFiles,userId);
			}
		}
	}

	public DataTable getTrustedDataTableByLoanId(String presType, Integer loanId, Integer paymentStatus){
		return applyInfoDao.getTrustedDataTableByLoanId(presType,loanId,paymentStatus);
	}

	public  void updateTrustedDataTableById(String presType, String id, Integer paymentStatus){
		applyInfoDao.updateTrustedDataTableById(presType,id,paymentStatus);
	}



	@Override
	public String refreshLoanAccount(final Integer userId) {

		try {
			//一分钟之内不能重复刷新refreshUserMap
			Date nowDate = new Date();
			if(refreshUserMap.containsKey(userId)){
				Date preDate = refreshUserMap.get(userId);
				if(DateUtil.addMinute(preDate,5).after(nowDate)){
					return "155";
				}
			}

			//开启刷新贷款账户的线程
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Map<String,Object> condition = new HashMap<String, Object>();
						condition.put("UndisclosedORAfter", "true");
						condition.put("loanAccountExist","true");
						condition.put("loanBelongId",userId);
						List<LoanApplyInfo> applyInfoList = applyInfoDao.queryApplyInfoList(condition);
						if(CollectionUtils.isNotEmpty(applyInfoList)){
							for (LoanApplyInfo loan : applyInfoList){
								updateLoanAccount(loan);
								Thread.currentThread().sleep(5000);
							}
						}
					} catch (InterruptedException e) {
						log.error("userId=" + userId + "更新贷款账户出错：" + e);
					}
				}
			});

			thread.start();

			refreshUserMap.put(userId,nowDate);
			return "100";
		}catch (Exception e){
			log.error("userId="+userId+"更新贷款账户出错：" + e);
		}

		return "101";

	}

	@Override
	public String refreshLoanAccountTab(Integer loanId){
		try {
			LoanApplyInfo loanApplyInfo = applyInfoDao.getApplyInfoById(loanId);
			updateLoanAccount(loanApplyInfo);
			return "success";
		}catch (Exception e){
			return e.getMessage();
		}

	}



	private static Map<Integer,Date> refreshUserMap;
	static {
		refreshUserMap = new HashMap<Integer, Date>();
	}
	private static final Logger log = LoggerFactory.getLogger(TrustedPaymentJobService.class);

	/**
	 * 根据贷款账号更新贷款账户数据
	 * @param loanApplyInfo
	 * @return
	 */
	private void updateLoanAccount(LoanApplyInfo loanApplyInfo){
		try{

			String acc_bal,lon_bal,lon_state,next_amt,qf_bal,qf_amt,qf_term;

			final String loanAccountNo = loanApplyInfo.getLoanAccountNo();
			HashMap<String,String> map = new HashMap<String,String>(){{ put("贷款帐号",loanAccountNo); }};
			String returnStr = socketDemo.queryCusInfo(loanApplyInfo.getLoanId(), map, SocketCodeTypeEnum.QRY10400);
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			if(jsonObject.getBoolean("success")){
				lon_bal = jsonObject.getString("lon_bal");//贷款余额
				lon_state = jsonObject.getString("lon_state");//贷款状态
				next_amt = jsonObject.getString("next_amt");//下次还款金额
				qf_bal = jsonObject.getString("qf_bal");//欠本金额
				qf_amt = jsonObject.getString("qf_amt");//欠息金额
				qf_term = jsonObject.getString("qf_term");//欠息金额

				if(!banger.common.tools.StringUtil.isNullOrEmpty(lon_bal)){
					loanApplyInfo.setLoanBalanceAmount(BigDecimal.valueOf(Double.valueOf(lon_bal)));
				}
				if(!banger.common.tools.StringUtil.isNullOrEmpty(lon_state)){
					loanApplyInfo.setLoanStatus(lon_state);
					if("结清".equals(lon_state.trim())){
						loanApplyInfo.setLoanProcessType(LoanProcessTypeEnum.CLEARANCE.type);
					}
				}
				if(!banger.common.tools.StringUtil.isNullOrEmpty(next_amt)){
					loanApplyInfo.setNextRepaymentAmount(BigDecimal.valueOf(Double.valueOf(next_amt)));
				}
				if(!banger.common.tools.StringUtil.isNullOrEmpty(qf_bal)){
					loanApplyInfo.setLoanArrears(BigDecimal.valueOf(Double.valueOf(qf_bal)));
				}
				if (!banger.common.tools.StringUtil.isNullOrEmpty(qf_amt)){
					loanApplyInfo.setLoanIrrevocableInterest(BigDecimal.valueOf(Double.valueOf(qf_amt)));
				}
				if (!banger.common.tools.StringUtil.isNullOrEmpty(qf_term)){
					loanApplyInfo.setOverdueLimit(Integer.parseInt(qf_term));
				}
			}

			final String accountNum = loanApplyInfo.getAccountNum();
			if(!StringUtil.isNullOrEmpty(accountNum)){
				HashMap<String,String> map2 = new HashMap<String,String>(){{ put("账户",accountNum); }};
				String returnString = socketDemo.queryCusInfo(loanApplyInfo.getLoanId(), map2, SocketCodeTypeEnum.QRY00400);
				jsonObject = JSONObject.fromObject(returnString);
				if(jsonObject.getBoolean("success")){
					acc_bal = jsonObject.getString("bal");
					if(!banger.common.tools.StringUtil.isNullOrEmpty(acc_bal)){
						loanApplyInfo.setLoanAccountAmount(new BigDecimal(acc_bal));
					}
				}
			}

			applyInfoDao.updateApplyInfo(loanApplyInfo);
		}catch (Exception e){
			log.error("更新贷款账户出错：" + e);
		}

	}

}
