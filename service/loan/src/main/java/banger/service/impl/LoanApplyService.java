package banger.service.impl;

import banger.dao.intf.*;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseOption;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.config.AutoTableInfo;
import banger.domain.enumerate.*;
import banger.domain.loan.*;
import banger.domain.system.SysBasicConfig;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataSet;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.reader.Reader;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.moduleIntf.*;
import banger.service.intf.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class LoanApplyService implements ILoanApplyService {
	@Autowired
	private IApplyInfoDao applyInfoDao;
	@Autowired
	private ITypeDao typeDao;
	@Autowired
	private ILoanFieldExtendDao loanFieldExtendDao;
	@Autowired
	private ILoanTemplateExtendDao loanTemplateExtendDao;
	@Autowired
	private ILoanInfoSyncService loanInfoSyncService;
	@Autowired
	private ILoanSyncCustomerService loanSyncCustomerService;
	@Autowired
	private ILoanOperationService loanOperationService;
	@Autowired
	private ILoanAuditProcessDao loanAuditProcessDao;
	@Autowired
	private ICurrentAuditStatusDao currentAuditStatusDao;
	@Autowired
	private IApproveProcessDao approveProcessDao;
	@Resource
	private ICustomerBlackProvider customerBlackProvider;
	@Resource
	private IAutoTemplateProvider autoTemplateProvider;
	@Resource
	private ITeamGroupProvider teamGroupProvider;
	@Autowired
	private IApplyInfoService applyInfoService;
	@Autowired
	private ITypeService typeService;

	@Autowired
	private IRepayPlanInfoService repayPlanInfoService;
	@Autowired
	private ISystemModule systemModule;
	@Autowired
	private IFormSettingsProvider formSettingProvider;
	@Autowired
	private IMapTaggingProvider mapTaggingProvider;
	@Autowired
	private ICommPeoInfoDao commPeoInfoDao;

	/**
	 * 是否代码表
	 */
	private List<AutoBaseOption> yesNoList;

	public LoanApplyService(){
		this.yesNoList = new ArrayList<AutoBaseOption>();
		this.yesNoList.add(new AutoBaseOption("1","是"));
		this.yesNoList.add(new AutoBaseOption("0","否"));
	}

	/**
	 * 得到贷款申请字段
	 * @return
	 */
	public List<AutoBaseField> getApplyFieldList(){
		List<AutoBaseField> list = new ArrayList<AutoBaseField>();
		list.add(new AutoFieldWrapper(0,"loanName","LOAN_NAME","借款人",EnumFiledType.TEXT.fieldType,"",true,EnumFiledType.TEXT.getInputLength(0),true,false));
		list.add(new AutoFieldWrapper(0,"loanIdentifyType","LOAN_IDENTIFY_TYPE","证件类型",EnumFiledType.SELECT.fieldType,"",true,getDictOptions("CD_IDENTIFY_TYPE"),true,false));
		list.add(new AutoFieldWrapper(0,"loanIdentifyNum","LOAN_IDENTIFY_NUM","证件号码",EnumFiledType.TEXT.fieldType,"",true,EnumFiledType.TEXT.getInputLength(1),true,false));
		list.add(new AutoFieldWrapper(0,"loanTelNum","LOAN_TEL_NUM","联系电话",EnumFiledType.TEXT.fieldType,"",true,EnumFiledType.TEXT.getInputLength(1),true,false));
		list.add(new AutoFieldWrapper(0,"loanApplyAmount","LOAN_APPLY_AMOUNT","请申金额",EnumFiledType.DECIMAL.fieldType,"",true,EnumFiledType.DECIMAL.getInputLength(0),true,false));
		list.add(new AutoFieldWrapper(0,"loanTypeId","LOAD_TYPE_ID","贷款类型",EnumFiledType.SELECT.fieldType,"",true,getCodeTableOptions("cdLoanType"),true,false));
		return list;
	}

	/**
	 * 贷款类型列表
	 * @return
	 */
	public List<LoanType> getAllLoanTypeList(){
		return typeDao.getAllLoanTypeList();
	}

	/**
	 * 贷款合同类型列表
	 * @return
	 */
	public List<LoanType> getAllLoanContractTypeList(){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("isActived",1);
		condition.put("isDel",0);
		condition.put("isContractType",1);
		return typeDao.queryTypeList(condition);
	}

	public LoanType getLoanTypeById(Integer typeId) {
		return typeDao.getTypeById(typeId);
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
	 * 推得到贷款业务类型
	 * @return
	 */
	private List<AutoBaseOption> getCodeTableOptions(String codetable){
		List<AutoBaseOption> list = new ArrayList<AutoBaseOption>();
		List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable(codetable, "");
		for(ICodeTable.IItem item : items){
			list.add(new AutoBaseOption(item.getValue(),item.getName()));
		}
		return list;
	}

	/**
	 * 得到贷款模板
	 */
	public List<LoanApplyTemplate> getApplyTemplateFieldList(Integer loanTypeId,String processType) {
		List<LoanTemplateExtend> tempList = loanTemplateExtendDao.getLoanTemplateList(loanTypeId, processType);
		List<LoanFieldExtend> fieldList = loanFieldExtendDao.getLoanFieldList(loanTypeId, processType);
		Map<Integer,LoanApplyTemplate> map = new HashMap<Integer,LoanApplyTemplate>();
		List<LoanApplyTemplate> list = new ArrayList<LoanApplyTemplate>();
		for(LoanTemplateExtend tempExtend : tempList){
			LoanApplyTemplate temp = new LoanApplyTemplate();
			temp.setId(tempExtend.getTableId());
			temp.setName(tempExtend.getTableDisplayName());
			temp.setType(TableInputType.valueOf(tempExtend.getTableType()).type);
			map.put(temp.getId(), temp);
			for(LoanFieldExtend fieldExtend : fieldList){
				if(fieldExtend.getTableName().equals(temp.getTableName())){
					String propertyName = getLowerName(fieldExtend.getFieldColumn());
					Boolean nullable = fieldExtend.getFieldIsRequired()!=null && fieldExtend.getFieldIsRequired().equals(1);
					boolean isAppShow = fieldExtend.getFieldAppIsShow()!=null && fieldExtend.getFieldAppIsShow().intValue()>0;
					boolean isWebShow = fieldExtend.getFieldWebIsShow()!=null && fieldExtend.getFieldWebIsShow().intValue()>0;
					if(isAppShow || isWebShow){
						if(StringUtil.isNotEmpty(fieldExtend.getFieldDictName())){
							temp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(),propertyName,fieldExtend.getFieldColumn(),fieldExtend.getFieldColumnDisplay(),fieldExtend.getFieldType(),fieldExtend.getFieldNumberUnit(),nullable,fieldExtend.getFieldLength(),getDictOptions(fieldExtend.getFieldDictName()),isAppShow,isWebShow,fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
						}else{
							if(EnumFiledType.YES_NO.equalType(fieldExtend.getFieldType())) {
								temp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(), yesNoList, isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
							}else{
								temp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(), isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
							}
						}
					}
				}
			}
			list.add(temp);
		}
		
		return list;
	}
	
	/**
	 * 得到贷款模板
	 */
	public List<LoanApplyTemplate> getApplyTemplateList(Integer loanTypeId,String processType) {
		List<LoanTemplateExtend> tempList = loanTemplateExtendDao.getLoanTemplateList(loanTypeId, processType);
		List<LoanApplyTemplate> list = new ArrayList<LoanApplyTemplate>();
		for(LoanTemplateExtend tempExtend : tempList){
			LoanApplyTemplate temp = new LoanApplyTemplate();
			temp.setId(tempExtend.getTableId());
			temp.setName(tempExtend.getTableDisplayName());
			temp.setType(TableInputType.valueOf(tempExtend.getTableType()).type);
			list.add(temp);
		}
		
		return list;
	}

	/**
	 * 检查字段必填项
	 */
	public Integer checkTemplateRequireField(Integer loanTypeId,String processType,Integer loanId) {

		List<LoanTemplateExtend> tempList = loanTemplateExtendDao.getLoanTemplateList(loanTypeId, processType);
		List<LoanFieldExtend> fieldList = loanFieldExtendDao.getLoanFieldList(loanTypeId, processType);
		Map<String,LoanTemplateExtend> templeteMap = new HashMap<String, LoanTemplateExtend>();
		Map<String,List<LoanFieldExtend>> fieldMap = new HashMap<String, List<LoanFieldExtend>>();
		BigDecimal applyAmount = null;		//申请金额
		BigDecimal proposeAmount = null;	//建议金额
		for(LoanTemplateExtend tempExtend : tempList){
			templeteMap.put(tempExtend.getTableDbName(),tempExtend);
			DataTable table = loanTemplateExtendDao.getTemplateDataById(processType, tempExtend.getTableDbName(), loanId);
			tempExtend.setData(table);
			tempExtend.setFieldList(fieldList);
		}
		//隐藏表单中的字段不做校验
		formSettingProvider.setSubmitTemplateShowOrHide(tempList);

		for(LoanFieldExtend fieldExtend : fieldList){
			if(templeteMap.containsKey(fieldExtend.getTableName()) && !"hide".equals(templeteMap.get(fieldExtend.getTableName()).getTableHide())){
				LoanTemplateExtend temp = templeteMap.get(fieldExtend.getTableName());
				if(TableInputType.FORMS == TableInputType.valueOf(temp.getTableType())){
					if (fieldExtend.getFieldAppIsShow() != null && fieldExtend.getFieldAppIsShow().intValue() > 0) {
						boolean nullable = fieldExtend.getFieldIsRequired() != null && fieldExtend.getFieldIsRequired().equals(1);
						if (nullable) {
							if (!fieldMap.containsKey(fieldExtend.getTableName()))
								fieldMap.put(fieldExtend.getTableName(), new ArrayList<LoanFieldExtend>());
							fieldMap.get(fieldExtend.getTableName()).add(fieldExtend);
						}
					}
				}
			}
		}

		for(String tableName : fieldMap.keySet()){
			Integer requireFieldCount = 0;             //必填字段数
			Integer inputRequireFieldCount = 0;        //已输入的必填字段数
//			DataTable table = loanTemplateExtendDao.getTemplateDataById(processType, tableName, loanId);
			LoanTemplateExtend loanTemplateExtend = templeteMap.get(tableName);
			DataTable table =(DataTable) loanTemplateExtend.getData();
			List<LoanFieldExtend> requirefieldList = fieldMap.get(tableName);
			for (LoanFieldExtend fieldExtend : requirefieldList) {
				requireFieldCount++;
				if(table!=null && table.rowSize()>0){
					if (hasValueInDataRow(table.getRow(0), fieldExtend.getFieldColumn(), fieldExtend)) {
						inputRequireFieldCount++;
					}

					if("LBIZ_LOAN_APPLY_INFO".equals(fieldExtend.getTableName()) && "LOAN_APPLY_AMOUNT".equals(fieldExtend.getFieldColumn())){
						applyAmount = (BigDecimal)TypeUtil.changeType(table.getRow(0).get("LOAN_APPLY_AMOUNT"),BigDecimal.class);
					}
					if("LBIZ_SURVEY_RESULT".equals(fieldExtend.getTableName()) && "PROPOSAL_AMOUNT".equals(fieldExtend.getFieldColumn())) {
						proposeAmount = (BigDecimal) TypeUtil.changeType(table.getRow(0).get("PROPOSAL_AMOUNT"), BigDecimal.class);
					}
				}

			}
			if(inputRequireFieldCount<requireFieldCount)return 121;					//信息不完善
		}

		if(applyAmount!=null && proposeAmount!=null){
			if(proposeAmount.doubleValue()>applyAmount.doubleValue())
				return 132;							//建议金额不能大于申请金额
		}

		return 100;			//成功
	}
	
	/**
	 * 安卓得到贷款模板
	 */
	public List<LoanApplyTemplate> getApplyTemplateList(Integer loanTypeId,String processType,Integer loanId) {
		List<LoanTemplateExtend> tempList = loanTemplateExtendDao.getLoanTemplateList(loanTypeId, processType);
		List<LoanFieldExtend> fieldList = loanFieldExtendDao.getLoanFieldList(loanTypeId, processType);
		List<LoanApplyTemplate> list = new ArrayList<LoanApplyTemplate>();
		for(LoanTemplateExtend tempExtend : tempList){
			if(tempExtend.getIsActived()!=null && tempExtend.getIsActived().intValue()==0)
				continue;
			if(tempExtend.getTableIsActived()!=null && tempExtend.getTableIsActived().intValue()==0)
				continue;

			LoanApplyTemplate temp = new LoanApplyTemplate();
			temp.setId(tempExtend.getTableId());
			temp.setTableName(tempExtend.getTableDbName());
			temp.setName(tempExtend.getTableDisplayName());
			temp.setType(TableInputType.valueOf(tempExtend.getTableType()).type);

			if (TableInputType.FORMS == TableInputType.valueOf(tempExtend.getTableType())) {            //表单模板时
				Integer fieldCount = 0;                       //字段数
				Integer inputFieldCount = 0;                  //已输入的字段数
				Integer requireFieldCount = 0;             //必填字段数
				Integer inputRequireFieldCount = 0;        //已输入的必填字段数

				DataTable table = loanTemplateExtendDao.getTemplateDataById(processType, tempExtend.getTableDbName(), loanId);

				for (LoanFieldExtend fieldExtend : fieldList) {
					if (fieldExtend.getTableName().equals(temp.getTableName())) {
						boolean nullable = fieldExtend.getFieldIsRequired() != null && fieldExtend.getFieldIsRequired().equals(1);
						if (fieldExtend.getFieldAppIsShow() != null && fieldExtend.getFieldAppIsShow().intValue() > 0) {
							fieldCount++;
							if (nullable) requireFieldCount++;
							if (table != null && table.rowSize() > 0) {
								if (hasValueInDataRow(table.getRow(0), fieldExtend.getFieldColumn(), fieldExtend)) {
									inputFieldCount++;
									if (nullable) inputRequireFieldCount++;
								}
							}
						}

						String propertyName = getLowerName(fieldExtend.getFieldColumn());
						boolean isAppShow = fieldExtend.getFieldAppIsShow()!=null && fieldExtend.getFieldAppIsShow().intValue()>0;
						boolean isWebShow = fieldExtend.getFieldWebIsShow()!=null && fieldExtend.getFieldWebIsShow().intValue()>0;
						if(isAppShow || isWebShow){
							if(StringUtil.isNotEmpty(fieldExtend.getFieldDictName())){
								temp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(),propertyName,fieldExtend.getFieldColumn(),fieldExtend.getFieldColumnDisplay(),fieldExtend.getFieldType(),fieldExtend.getFieldNumberUnit(),nullable,fieldExtend.getFieldLength(),getDictOptions(fieldExtend.getFieldDictName()),isAppShow,isWebShow,fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
							}else{
								if(EnumFiledType.YES_NO.equalType(fieldExtend.getFieldType())){
									temp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(),yesNoList,isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
								}else{
									temp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(), isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
								}
							}
						}
					}

				}
				if (table != null)temp.setData(table.getRows());
				temp.setFieldCount(fieldCount);
				temp.setInputFieldCount(inputFieldCount);
				temp.setRequireFieldCount(requireFieldCount);
				temp.setInputRequireFieldCount(inputRequireFieldCount);
			}

			list.add(temp);
		}
		
		return list;
	}
	
	/**
	 * 得到贷款申请模板
	 * @return
	 */
	public LoanApplyTemplate getApplyTemplateField(Integer loanTypeId,String processType,Integer tableId){
		LoanTemplateExtend temp = loanTemplateExtendDao.getLoanTemplateById(loanTypeId, processType,tableId);
		
		if(temp!=null){
			List<LoanFieldExtend> fieldList = loanFieldExtendDao.getLoanFieldList(loanTypeId, processType,tableId);
			Map<Integer,LoanApplyTemplate> map = new HashMap<Integer,LoanApplyTemplate>();
			
			LoanApplyTemplate applyTemp = new LoanApplyTemplate();
			applyTemp.setId(temp.getTableId());
			applyTemp.setName(temp.getTableDisplayName());
			applyTemp.setType(TableInputType.valueOf(temp.getTableType()).type);
			applyTemp.setTableName(temp.getTableDbName());
			map.put(applyTemp.getId(), applyTemp);
			for(LoanFieldExtend fieldExtend : fieldList){
				String propertyName = getLowerName(fieldExtend.getFieldColumn());
				Boolean nullable = fieldExtend.getFieldIsRequired()!=null && fieldExtend.getFieldIsRequired().equals(1);
				boolean isAppShow = fieldExtend.getFieldAppIsShow()!=null && fieldExtend.getFieldAppIsShow().intValue()>0;
				boolean isWebShow = fieldExtend.getFieldWebIsShow()!=null && fieldExtend.getFieldWebIsShow().intValue()>0;
				if(isAppShow || isWebShow){
					if(StringUtil.isNotEmpty(fieldExtend.getFieldDictName())){
						applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(),propertyName,fieldExtend.getFieldColumn(),fieldExtend.getFieldColumnDisplay(),fieldExtend.getFieldType(),fieldExtend.getFieldNumberUnit(),nullable,fieldExtend.getFieldLength(),getDictOptions(fieldExtend.getFieldDictName()),isAppShow,isWebShow,fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
					}else{
						if(EnumFiledType.YES_NO.equalType(fieldExtend.getFieldType())){
							applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(),yesNoList,isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
						}else{
							applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(), isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
						}
					}
				}
			}
			
			return applyTemp;
		}
		return null;
	}

	/**
	 * 得到贷款审批决议模板
	 * @return
	 */
	public LoanApplyTemplate getAuditTemplateField(Integer tableId){
		AutoTableInfo temp = autoTemplateProvider.getTableInfoById(tableId);

		if(temp!=null){
			List<LoanFieldExtend> fieldList = loanFieldExtendDao.getLoanFieldListByTableId(tableId);
			Map<Integer,LoanApplyTemplate> map = new HashMap<Integer,LoanApplyTemplate>();

			LoanApplyTemplate applyTemp = new LoanApplyTemplate();
			applyTemp.setId(temp.getTableId());
			applyTemp.setName(temp.getTableDisplayName());
			applyTemp.setType(TableInputType.valueOf(temp.getTableType()).type);
			applyTemp.setTableName(temp.getTableDbName());
			map.put(applyTemp.getId(), applyTemp);
			for(LoanFieldExtend fieldExtend : fieldList){
				String propertyName = getLowerName(fieldExtend.getFieldColumn());
				Boolean nullable = fieldExtend.getFieldIsRequired()!=null && fieldExtend.getFieldIsRequired().equals(1);
				boolean isAppShow = fieldExtend.getFieldAppIsShow()!=null && fieldExtend.getFieldAppIsShow().intValue()>0;
				boolean isWebShow = fieldExtend.getFieldWebIsShow()!=null && fieldExtend.getFieldWebIsShow().intValue()>0;
				if(isAppShow || isWebShow){
					if(StringUtil.isNotEmpty(fieldExtend.getFieldDictName())){
						applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(),propertyName,fieldExtend.getFieldColumn(),fieldExtend.getFieldColumnDisplay(),fieldExtend.getFieldType(),fieldExtend.getFieldNumberUnit(),nullable,fieldExtend.getFieldLength(),getDictOptions(fieldExtend.getFieldDictName()),isAppShow,isWebShow,fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
					}else{
						if(EnumFiledType.YES_NO.equalType(fieldExtend.getFieldType())){
							applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(),yesNoList,isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
						}else{
							applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(), isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
						}
					}
				}
			}

			return applyTemp;
		}
		return null;
	}

	/**
	 * 得到贷款审核模板
	 * @return
	 */
	public LoanApplyTemplate getTemplateFieldByProcessId(Integer processId){
		Integer tableId = 15;
		String tableName = "LBIZ_APPROVAL_RESOLUTION";
		LoanApplyTemplate applyTemp = new LoanApplyTemplate();
		applyTemp.setId(tableId);
		applyTemp.setTableName(tableName);
		applyTemp.setName("审批信息");
		applyTemp.setType(TableInputType.FORMS.type);

		List<LoanFieldExtend> fieldList = loanFieldExtendDao.getTemplateFieldListByProcessId(processId,tableId);

		for(LoanFieldExtend fieldExtend : fieldList){
			String propertyName = getLowerName(fieldExtend.getFieldColumn());
			Boolean nullable = fieldExtend.getFieldIsRequired()!=null && fieldExtend.getFieldIsRequired().equals(1);
			boolean isAppShow = fieldExtend.getFieldAppIsShow()!=null && fieldExtend.getFieldAppIsShow().intValue()>0;
			boolean isWebShow = fieldExtend.getFieldWebIsShow()!=null && fieldExtend.getFieldWebIsShow().intValue()>0;
			if(isAppShow || isWebShow){
				if(StringUtil.isNotEmpty(fieldExtend.getFieldDictName())){
					applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(),propertyName,fieldExtend.getFieldColumn(),fieldExtend.getFieldColumnDisplay(),fieldExtend.getFieldType(),fieldExtend.getFieldNumberUnit(),nullable,fieldExtend.getFieldLength(),getDictOptions(fieldExtend.getFieldDictName()),isAppShow,isWebShow,fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
				}else{
					if(EnumFiledType.YES_NO.equalType(fieldExtend.getFieldType())) {
						applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(), yesNoList, isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
					}else{
						applyTemp.getFields().add(new AutoFieldWrapper(fieldExtend.getFieldId(), propertyName, fieldExtend.getFieldColumn(), fieldExtend.getFieldColumnDisplay(), fieldExtend.getFieldType(), fieldExtend.getFieldNumberUnit(), nullable, fieldExtend.getFieldLength(), isAppShow, isWebShow, fieldExtend.getFieldConstraintRule(),fieldExtend.getPopupUrl(),fieldExtend.getDefaultValue()));
					}
				}
			}
		}

		return applyTemp;
	}


	/**
	 * 列名转字段名
	 * @param columnName
	 * @return
	 */
	private String getLowerName(String columnName){
		String[] parts = columnName.split("_");
		String lowerName = "";
		for(int i=0;i<parts.length;i++){
			if(i==0){
				lowerName+=parts[i].toLowerCase();
			}else{
				lowerName+=parts[i].substring(0,1)+parts[i].substring(1).toLowerCase();
			}
		}
		return lowerName;
	}

	public void saveApplyInfo(LoanApplyInfo applyInfo){
		saveApplyInfo(applyInfo,-1);
	}

	/**
	 * 保存贷款申请信息
	 * @param applyInfo
	 */
	public void saveApplyInfo(LoanApplyInfo applyInfo,Integer applyId) {

		if("1".equals(applyInfo.getLoanIdentifyType())){
			String idCard = applyInfo.getLoanIdentifyNum();
			Integer age = IdCardUtil.getAgeByIdCard(idCard);
			String sex = IdCardUtil.getSexByIdCard(idCard);
			//Date birthday = IdCardUtil.getBirthdayByIdCard(idCard);
			if(age!=null)applyInfo.setLoanAge(age);
			if(StringUtil.isNotEmpty(sex)) {
				if ("男".equals(sex)) {
					applyInfo.setLoanSex("1");
				}else if("女".equals(sex)){
					applyInfo.setLoanSex("0");
				}else{
					applyInfo.setLoanSex(sex);
				}
			}
		}

		if(applyInfo.getLoanId()!=null && applyInfo.getLoanId().intValue()>0){
			if(StringUtil.isNotEmpty(applyInfo.getLoanProcessType())){
				String processType = LoanProcessTypeEnum.valueOfType(applyInfo.getLoanProcessType()).type;
				applyInfo.setLoanProcessType(processType);
			}else{
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.APPLY.type);
			}
			applyInfo.setLoanApplyUserId(applyInfo.getCreateUser());
			applyInfo.setLoanBelongId(applyInfo.getCreateUser());
			applyInfo.setUpdateUser(applyInfo.getCreateUser());
			applyInfo.setUpdateDate(new Date());
			applyInfo.setLoanApplyDate(new Date());
			if(StringUtil.isNotEmpty(applyInfo.getLoanIdentifyNum()))
				applyInfo.setLoanIdentifyNum(IdCardUtil.toUpperCase(applyInfo.getLoanIdentifyNum()));		//转大写
			
			applyInfoDao.updateApplyInfo(applyInfo);
			
			loanInfoSyncService.syncLoanToBiz(applyInfo.getLoanId());		//同频贷款到子表
			
			Integer customerId = loanSyncCustomerService.syncLoanCustomerInfo(applyInfo.getLoanId());	//同频贷款到客户,如果客户存在，则同步客户到贷款
			if(customerId!=null && customerId.intValue()>0){
				applyInfo.setLoanCustomerId(customerId);
			}
			
		}else{

			LoanType loanType = typeDao.getTypeById(applyInfo.getLoanTypeId());			//根据贷款类型取三表类型
			if(loanType!=null) {
				applyInfo.setLoanClassId(loanType.getLoanClassId());
			}

			if(StringUtil.isNotEmpty(applyInfo.getLoanProcessType())){
				String processType = LoanProcessTypeEnum.valueOfType(applyInfo.getLoanProcessType()).type;
				applyInfo.setLoanProcessType(processType);
			}else{
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.APPLY.type);
			}
			applyInfo.setLoanApplyUserId(applyInfo.getCreateUser());
			applyInfo.setUpdateUser(applyInfo.getCreateUser());
			applyInfo.setLoanBelongId(applyInfo.getCreateUser());
			applyInfo.setCreateDate(new Date());
			applyInfo.setUpdateDate(new Date());
			applyInfo.setLoanApplyDate(new Date());

			if(StringUtil.isNotEmpty(applyInfo.getLoanIdentifyNum()))
				applyInfo.setLoanIdentifyNum(IdCardUtil.toUpperCase(applyInfo.getLoanIdentifyNum()));		//转大写
			
			LoanApplyInfo existInfo = applyInfoDao.getExistApplyInfo(applyInfo);
			if(existInfo!=null){
				applyInfo.setLoanId(existInfo.getLoanId());
			}else{
				applyInfoDao.insertApplyInfo(applyInfo);
			}
			Integer potentialId = applyInfo.getPotentialCustomerId();
			if (potentialId != null && potentialId.intValue() != 0){
				Integer loanId = applyInfo.getLoanId();
				applyInfoService.syncPotentialFiles(potentialId, loanId, applyInfo.getCreateUser());
			}


			loanOperationService.addLoanOperation(applyInfo.getLoanId(),LoanOperationType.LOAN_APPLY_CREATE.typeName,"",applyInfo.getCreateUser(), LoanProcessTypeEnum.APPLY.type);
			
			loanInfoSyncService.syncLoanToBiz(applyInfo.getLoanId(), applyId);		//同频贷款到子表
			Integer customerId = loanSyncCustomerService.syncLoanCustomerInfo(applyInfo.getLoanId());	//同频贷款到客户,如果客户存在，则同步客户到贷款
			mapTaggingProvider.syncTaggingCustomerToLoan(applyInfo.getLoanId(), customerId, applyInfo.getCreateUser());
			loanSyncCustomerService.updateBelongId(customerId, applyInfo.getLoanBelongId());
			if(customerId!=null && customerId.intValue()>0){
				applyInfo.setLoanCustomerId(customerId);
			}
		}
	}

	/**
	 * 拒绝贷款申请信息
     * @param applyInfo
     * @param userId
     */
	public void refuseApplyInfo(LoanApplyInfo applyInfo, Integer userId){
		if(applyInfo.getLoanId()!=null && applyInfo.getLoanId().intValue()>0) {
			LoanApplyInfo loanInfo = applyInfoDao.getApplyInfoById(applyInfo.getLoanId());
			applyInfo.setLoanRefuseUser(userId);
			applyInfo.setUpdateUser(userId);
			applyInfo.setUpdateDate(new Date());
			applyInfo.setLoanRefuseDate(new Date());
			applyInfoDao.updateApplyInfo(applyInfo);

			//操作日志
			if(StringUtil.isNotEmpty(applyInfo.getLoanRefuseType())){
				LoanRefuseType refuseType = LoanRefuseType.valueOfType(applyInfo.getLoanRefuseType());
				if(refuseType!=null) {
					String content = "";
					if ("99".equals(applyInfo.getLoanRefuseReason())) {
						if (StringUtil.isNotEmpty(applyInfo.getLoanRefuseRemark())) {
							content = applyInfo.getLoanRefuseRemark();
						}
					} else {
						if (StringUtil.isNotEmpty(applyInfo.getLoanRefuseRemark())) {
							content = getRefuseReasonText(applyInfo.getLoanRefuseReason(), refuseType)+":"+applyInfo.getLoanRefuseRemark();
						}else{
							content = getRefuseReasonText(applyInfo.getLoanRefuseReason(), refuseType);
						}
					}

					LoanOperationType operationType = null;
					if(refuseType.equals(LoanRefuseType.BANK)){
						operationType = LoanOperationType.LOAN_BANK_REFUSE;
					}else{
						operationType = LoanOperationType.LOAN_CUSTOMER_REFUSE;
					}

					loanOperationService.addLoanOperation(applyInfo.getLoanId(), operationType.typeName, content, userId, loanInfo.getLoanProcessType());
				}
			}


		}
	}

	/**
	 * 得到拒绝理由文本
	 * @param refuseReason
	 * @return
	 */
	private String getRefuseReasonText(String refuseReason,LoanRefuseType refuseType){
		List<AutoBaseOption> optionList = getDictOptions(LoanRefuseType.BANK.equals(refuseType)?"CD_LOAN_BANK_REFUSE_REASON":"CD_LOAN_CUSTOMER_REFUSE_REASON");
		for(AutoBaseOption option : optionList){
			if(option.getValue().equals(refuseReason)){
				return option.getName();
			}
		}
		return "";
	}

	/**
	 * 提交贷款申请信息
	 * @param applyInfo
	 */
	public String submitLoanApplyInfo(LoanApplyInfo applyInfo){
		if(applyInfo.getLoanId()!=null && applyInfo.getLoanId().intValue()>0) {

			LoanApplyInfo loanInfo = applyInfoDao.getApplyInfoById(applyInfo.getLoanId());


			if(loanInfo!=null) {

				boolean isBlack = customerBlackProvider.isExistCustomerBlack(loanInfo.getLoanName(),loanInfo.getLoanIdentifyType(),loanInfo.getLoanIdentifyNum());
				if(!isBlack) {

					if(checkTemplateRequireField(loanInfo.getLoanTypeId(), loanInfo.getLoanProcessType(), loanInfo.getLoanId())>100){
						return "信息不完善";
					}

					Integer groupId = teamGroupProvider.queryGroupIdByUserId(loanInfo.getLoanBelongId().toString());

					if(groupId==null || groupId.intValue()<1){
						return "当前用户没有归属团队";
					}

					String processType = LoanProcessTypeEnum.ALLOT.type;
					//查看是否开启带宽分配控制校验，如果开启需要先判断时候具有合适的客户经理，才能执行下面逻辑
					LoanType loanType = typeService.getTypeById(loanInfo.getLoanTypeId());
					List<LoanApplyInfo_Web_Query> info = applyInfoService.queryMinInGroup(groupId);
					Integer allotId = getMemberId(loanInfo.getLoanApplyUserId(),loanType.getAllotTarget(), info);//分配给谁
					if (loanType.getIsAutoAllot()!=null && loanType.getIsAutoAllot().intValue()==1){
						if (allotId == null ) {
							return "贷款分配，没有合适的客户经理";
						}
					}

					/*String processType = LoanProcessTypeEnum.ALLOT.type;*/
					applyInfo.setLoanProcessType(processType);

					applyInfo.setUpdateUser(applyInfo.getCreateUser());
					applyInfo.setUpdateDate(new Date());
					applyInfo.setLoanApplyDate(new Date());

					applyInfoDao.updateApplyInfo(applyInfo);
					loanOperationService.addLoanOperation(applyInfo.getLoanId(), LoanOperationType.LOAN_APPLY_SUBMIT.typeName, "", applyInfo.getCreateUser(), LoanProcessTypeEnum.APPLY.type);
					applyInfoService.sendLoanMsg(applyInfo,null);
					if(loanInfo.getLoanTypeId()!=null&&loanInfo.getLoanTypeId().intValue()>0) {
//						LoanType loanType = typeService.getTypeById(loanInfo.getLoanTypeId());
						if(loanType.getIsAutoAllot()!=null&&loanType.getIsAutoAllot().intValue()==1&& loanType.getAllotTarget()!=null&&LoanProcessTypeEnum.ALLOT.type.equals(processType)){
							//查询团队下的各个经理待调查的数量（从小到大排序）
//							List<LoanApplyInfo_Web_Query> info = applyInfoService.queryMinInGroup(groupId);
//							Integer allotId = getMemberId(loanInfo.getLoanApplyUserId(),loanType.getAllotTarget(), info);//分配给谁
//							if(allotId!=null){
								applyInfoService.loanAllotSignSave(applyInfo.getLoanId().toString(), allotId, loanInfo.getLoanApplyUserId());//执行自动分配
//							}
						}
					}

					return "success";
				}else{
					return "客户贷款受限";
				}
			}else{
				return "找不到贷款记录";
			}
		}else{
			return "贷款ID不能为空";
		}
	}
	public Integer getMemberId(Integer loginUserId,Integer autoTarget,List<LoanApplyInfo_Web_Query> info){
		Integer memberId=null;
		if(autoTarget==0){//团队
			if(info != null && info.size()>0){
				memberId=info.get(0).getUserId();
			}else{
				return null;
			}
		}
		if(autoTarget==1){//申请人
			String loginUser = loginUserId.toString();
			for(LoanApplyInfo_Web_Query lai : info){
				String id =lai.getUserId().toString();
				if(id.equals(loginUser)){
					memberId=loginUserId;
					return memberId;
				}
			}
			return null;
		}
		if(autoTarget==2){//非申请人
			if(info != null && info.size()>0){
				if(info.get(0).getUserId().intValue()==loginUserId.intValue()){
					if(info.size()>1) {
						memberId = info.get(1).getUserId();
					}else {
						memberId = null;
					}
				}else{
					memberId=info.get(0).getUserId();
				}
			}else{
				return null;
			}
		}
		return  memberId;
	}
	/**
	 * app 贷款退回接口
	 * @param loanId
	 * @param loanProcessType
	 * @param remark
	 * @return
	 */
	public String gobackLoanApplyInfo(Integer loanId,String loanProcessType, Integer loginerId, String remark){
		LoanApplyInfo loanInfo = applyInfoDao.getApplyInfoById(loanId);
		if(loanInfo!=null){
			if(loanInfo.getLoanProcessType().equals(loanProcessType)){
				applyInfoService.backApplyInfo(String.valueOf(loanId), loginerId, remark);
				return "success";
			}else{
				return "当前贷款阶段不正确";
			}
		}else{
			return "找不到贷款记录";
		}
	}


	/**
	 * 安卓查询贷款申请列表
	 */
	@Override
	public List<LoanApplyInfo> getLoanApplyList(Integer userId, IPageSize page) {
		return null;
	}
	
	/**
	 * 安卓查询贷款申请列表
	 * @return
	 */
	public List<LoanApplyInfo> getLoanApplyList(Map<String,Object> condition,IPageSize page){
		return this.applyInfoDao.getLoanApplyList(condition, page);
	}


	/**
	 * 得到客户贷款列表
	 * @param customerId
	 * @return
	 */
	public List<LoanApplyInfo> getLoanApplyInfoListByCustomerId(Integer customerId,Integer userId){
		List<LoanApplyInfo> list = applyInfoDao.getLoanApplyInfoListByCustomerId(customerId,userId);
		return list;
	}

	/**
	 * 保存代款模板信息
	 * @param table
	 */
	public Integer saveLoanTemplateInfo(DataTable table){
		Integer newId = loanTemplateExtendDao.saveLoanTemplateInfo(table);
		if(table!=null && table.rowSize()>0){
			Integer id = Reader.intReader().getValue(table.getRow(0),"ID");
			Integer loanId = Reader.intReader().getValue(table.getRow(0),"LOAN_ID");
			String processType = Reader.stringReader().getValue(table.getRow(0), "LOAN_PROCESS_TYPE");
			loanInfoSyncService.syncBizToLoan(id,table.getName(),processType);
			loanSyncCustomerService.syncLoanCustomerInfo(loanId, table.getName());
		}
		return newId;
	}

	public Integer[] saveLoanTemplateInfo(DataTable table,Boolean flag){
		Integer[] newId = loanTemplateExtendDao.saveLoanTemplateInfo(table,flag);
		return newId;
	}

	/**
	 * 
	 * @param amount
	 * @return
	 */
	private String getDecimalFormat(BigDecimal amount){
		if(amount!=null){
			return amount.doubleValue()+"";
		}
		return "";
	}
	
	/**
	 * 判断列是否为空
	 * @param row
	 * @param columnName
	 * @param field
	 * @return
	 */
	private boolean hasValueInDataRow(DataRow row,String columnName,LoanFieldExtend field){
		if(row!=null){
			Object value = row.get(columnName);
			if(value!=null){
				EnumFiledType fieldType = EnumFiledType.valueOfType(field.getFieldType());
				if(fieldType!=null){
					if(fieldType.isStringType()){
						if(!"".equals(value.toString().trim())){
							return true;
						}
					}else{
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public DataTable getApplyTemplateData(Integer loanTypeId,
			String processType, Integer templateId, Integer loanId) {
		LoanTemplateExtend template = this.loanTemplateExtendDao.getLoanTemplateById(loanTypeId, processType, templateId);
		if(template!=null){
			String tableName = template.getTableDbName();
			DataTable table = loanTemplateExtendDao.getTemplateDataById(processType,tableName,loanId);
			return table;
		}
		return null;
	}

	/**
	 *
	 * @param tableName
	 * @param id
	 * @return
	 */
	public DataTable getLoanTemplateDataById(String tableName,Integer id){
		DataTable table = loanTemplateExtendDao.getTemplateDataById(tableName,id);
		return table;
	}

	/**
	 * 删除贷款申请所有模板数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	public void deleteLoanTemplateDataById(String tableName,Integer id){
		loanTemplateExtendDao.deleteLoanTemplateDataById(tableName,id);
	}

	/**
	 * 得到贷款申请所有模板数据
	 * @param processType
	 * @param tableName
	 * @param loanId
	 * @return
	 */
	public DataTable getLoanTemplateDataById(String processType,String tableName,Integer loanId){
		DataTable table = loanTemplateExtendDao.getTemplateDataById(processType,tableName,loanId);
		return table;
	}
	
	/**
	 * 得到贷款申请所有模板数据
	 */
	@Override
	public DataSet getApplyTemplateDataSet(Integer loanTypeId,
			String processType, Integer loanId){
		List<LoanTemplateExtend> list = this.loanTemplateExtendDao.getLoanTemplateList(loanTypeId,processType);
		
		DataSet ds = new DataSet("");
		if(list!=null && list.size()>0){
			for(LoanTemplateExtend temp : list){
				DataTable table = getApplyTemplateData(loanTypeId,processType,temp.getTableId(),loanId);
				if(table!=null){
					ds.addTable(table);
				}
			}
		}
		
		return ds;
	}

	/**
	 * 查询审核状态
	 * @param loanId
	 * @return
	 */
	public List<LoanAuditProcess> getLoanAuditProcessByLoanId(Integer loanId){
		List<LoanAuditProcess> list = this.loanAuditProcessDao.getLoanAuditProcessByLoanId(loanId);
		String[] chineStrs = new String[]{"一","二","三","四","五","六","七","八","九","十"};
 		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				if(i<10) {
					list.get(i).setDisplay(chineStrs[i]+"审");
				}else if(i<20){
					list.get(i).setDisplay("十"+chineStrs[i%10]+"审");
				}else{
					list.get(i).setDisplay(chineStrs[i/10]+"十"+chineStrs[i%10]+"审");
				}
			}
		}
		return list;
	}
	
    /**
     * 通过主键得到贷款申请表
     * @param loanId 主键Id
     */
	public LoanApplyInfo_Query getApplyInfoQueryById(Integer loanId){
		return this.applyInfoDao.getApplyInfoQueryById(loanId);
	}

	/**
	 * 得到当前审核状态
	 * @param loanId
	 * @param processId
	 * @return
	 */
	public List<LoanCurrentAuditStatus> getLoanAuditStatusListById(Integer loanId,Integer processId){
		return this.currentAuditStatusDao.getLoanAuditStatusListById(loanId,processId);
	}

	/**
	 * 得到工作流环节
	 * @param flowId
	 * @param paramId
	 * @return
	 */
	public List<WfApproveProcess> getApproveProcessListByFlowId(Integer flowId,Integer paramId){
		return this.approveProcessDao.getApproveProcessListByFlowId(flowId,paramId);
	}

	@Override
	public void insertApplyInfo(LoanApplyInfo applyInfo, Integer loginUserId) {

	}

	/**
	 * 无业务更新贷款信息
	 * @param applyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	@Override
	public void updateApplyInfo(LoanApplyInfo applyInfo, Integer loginUserId) {
		applyInfo.setUpdateDate(new Date());
		applyInfo.setUpdateUser(loginUserId);
		this.applyInfoDao.updateApplyInfo(applyInfo);
	}

	public void updateLoanModelScore(LoanApplyInfo applyInfo) {
		this.applyInfoDao.updateApplyInfo(applyInfo);
	}

	@Override
	public void deleteApplyInfoById(Integer loanId) {
		this.applyInfoDao.deleteApplyInfoById(loanId);
	}

	@Override
	public LoanApplyInfo getApplyInfoById(Integer loanId) {
		return this.applyInfoDao.getApplyInfoById(loanId);
	}

	@Override
	public List<LoanApplyInfo> queryApplyInfoList(Map<String, Object> condition) {
		return this.applyInfoDao.queryApplyInfoList(condition);
	}

	@Override
	public IPageList<LoanApplyInfo> queryApplyInfoList(Map<String, Object> condition, IPageSize page) {
		return this.applyInfoDao.queryApplyInfoList(condition,page);
	}

	@Override
	public IPageList<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String, Object> condition, IPageSize page) {
		return this.applyInfoDao.queryAllInfoList(condition,page);
	}


	@Override
	public IPageList<LoanApplyInfo_Web_Query> queryCollectionList(Map<String, Object> condition, IPageSize page) {
		return this.applyInfoDao.queryCollectionList(condition,page);
	}

	@Override
	public List<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String, Object> condition) {
		return this.applyInfoDao.queryAllInfoList(condition);
	}

    @Override
    public IPageList<LoanApplyInfo_Web_Query> queryLoanAuditList(Map<String, Object> condition, IPageSize page) {
        return applyInfoDao.queryLoanAuditList(condition,page);
    }

    //忽略null更新贷款主表
    public void updateApplyInfoIgnoreNull(LoanApplyInfo applyInfoById, Integer loginUserId) {
		applyInfoById.setUpdateDate(new Date());
		applyInfoById.setUpdateUser(loginUserId);
		this.applyInfoDao.updateApplyInfoIgnoreNull(applyInfoById);
    }

	@Override
	public boolean checkLoanType(Integer typeId) {
		return applyInfoDao.checkLoanType(typeId);
	}

	public void updateModelScoreByLoanId(LoanApplyInfo applyInfo) {
		this.applyInfoDao.updateModelScoreByLoanId(applyInfo);
	}

    /**
     * 提前产生自定义表Id
     * @param tableName 表名
     * @return
     */
    public Integer newLbizId(String tableName){
        return this.loanTemplateExtendDao.newTableId(tableName,"ID");
    }

	@Override
	public void creatRepayPlanInfo(Integer loanId, Integer loginUserId) {
		DataTable datatable = applyInfoService.getDataTableByLoanId("LBIZ_LOAN_GRANT", LoanProcessTypeEnum.LOAN.type, Integer.valueOf(loanId));
		if (datatable != null && datatable.getRows() != null && datatable.getRows().length != 0) {
			DataRow row = datatable.getRow(0);
			//生成还款计划
			Integer date, loanLimit;
			String loanAmount, loanBackMode, loanCreditDate, loanRatio, ratioDate;
			if (row.get("LOAN_BACK_DATE") == null) return;
			date = Integer.parseInt(String.valueOf(row.get("LOAN_BACK_DATE")));
			if (row.get("LOAN_AMOUNT") == null) return;
			loanAmount = String.valueOf(row.get("LOAN_AMOUNT"));
			if (row.get("LOAN_BACK_MODE") == null) return;
			loanBackMode = String.valueOf(row.get("LOAN_BACK_MODE"));
			if (row.get("LOAN_CREDIT_DATE") == null) return;
			loanCreditDate = String.valueOf(row.get("LOAN_CREDIT_DATE"));
			if (row.get("LOAN_LIMIT") == null) return;
			loanLimit = Integer.parseInt(String.valueOf(row.get("LOAN_LIMIT")));
			if (row.get("LOAN_RATIO") == null) return;
			loanRatio = String.valueOf(row.get("LOAN_RATIO"));
			if (row.get("LOAN_RATIO_DATE") == null) return;
			ratioDate = String.valueOf(row.get("LOAN_RATIO_DATE"));
			//用于还款计划,本期应还日期,本期应还金额

			LoanApplyInfo applyInfo = new LoanApplyInfo();
			if (date != null && StringUtils.isNotBlank(loanBackMode) && StringUtils.isNotBlank(loanCreditDate)) {
				double dayRatio = 0l;
				SysBasicConfig sysConfig = systemModule.getSysBasicConfig("lxdw");
				if (sysConfig!=null && sysConfig.getConfigValue() != null) {
					Integer ratioType = sysConfig.getConfigValue();
					if (InterestUnitEnum.INTEREST_UNIT_YEAR.value == ratioType)
						dayRatio = Double.parseDouble(loanRatio)/360;
					if (InterestUnitEnum.INTEREST_UNIT_MONTH.value == ratioType)
						dayRatio = Double.parseDouble(loanRatio)/30;
				}

				String unit = applyInfoDao.selectUnitByTableNameAndColumnName("LBIZ_LOAN_GRANT", "LOAN_RATIO");
				BigDecimal div = new BigDecimal("1");
				if ("%".equals(unit)) {
					div = new BigDecimal("100");
				} else if ("‰".equals(unit)) {
					div = new BigDecimal("1000");
				}
				//TODO 南郊版本定制
				if (!"1".equals(loanBackMode) && !"2".equals(loanBackMode)  && !"3".equals(loanBackMode))
					loanBackMode = LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
				List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = repayPlanInfoService.getRepayPlanInfoListForL(String.valueOf(loanId), loanBackMode, loanAmount, String.valueOf(loanLimit), loanRatio, ratioDate, date, loginUserId);
				List<LoanRepayPlanInfoExtend> loanRepayPlanInfosNews = new ArrayList<LoanRepayPlanInfoExtend>();
				boolean setLoanInfo = false;

				double prevMoney = Double.parseDouble(loanAmount);
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
//				BigDecimal accrual = new BigDecimal("0");
				for (int i = 0; i <loanRepayPlanInfos.size() ; i++) {
					LoanRepayPlanInfoExtend loanRepayPlanInfoExtend = loanRepayPlanInfos.get(i);
					start.setTime(loanRepayPlanInfoExtend.getRatioDate());
					end.setTime(loanRepayPlanInfoExtend.getLoanRepayPlanDate());
					long days = ( end.getTimeInMillis() - start.getTimeInMillis())/(24*60*60*1000);
					loanRepayPlanInfoExtend.setLoanId(loanId);
					loanRepayPlanInfoExtend.setLoanAccrualBalanceAmount(new BigDecimal(prevMoney));
					BigDecimal accrualAmout = new BigDecimal(prevMoney*dayRatio).multiply(new BigDecimal(days));
					accrualAmout = accrualAmout.divide(div);
					loanRepayPlanInfoExtend.setLoanAccrualAmount(accrualAmout);
					if (loanRepayPlanInfoExtend.getLoanPrincipalAmount().doubleValue()==0 &&(loanRepayPlanInfoExtend.getLoanAccrualAmount() == null || loanRepayPlanInfoExtend.getLoanAccrualAmount().doubleValue() == 0 )) {
						//本息都是零肯定不是等额本息本金
						if ("3".equals(loanBackMode)) {	//按月还息
							if (!setLoanInfo) {
								applyInfo.setLoanRepayDate(loanRepayPlanInfoExtend.getLoanRepayPlanDate());
								applyInfo.setLoanRepayAmount(loanRepayPlanInfoExtend.getRepayment());
								setLoanInfo = true;
							}
						} else {//灵活还款
							if (accrualAmout.doubleValue() == 0) {
								loanRepayPlanInfoExtend.setLoanRepayDate(loanRepayPlanInfoExtend.getLoanRepayPlanDate());
								loanRepayPlanInfoExtend.setLoanRepayState(1);
								loanRepayPlanInfoExtend.setLoanIsOverdue(0);
							}
						}
					} else {
						loanRepayPlanInfoExtend.setLoanRepayState(0);
						if ("3".equals(loanBackMode) || "4".equals(loanBackMode)) {
							prevMoney = prevMoney - loanRepayPlanInfoExtend.getLoanPrincipalAmount().doubleValue();
						}
						if (!setLoanInfo) {
							applyInfo.setLoanRepayDate(loanRepayPlanInfoExtend.getLoanRepayPlanDate());
							applyInfo.setLoanRepayAmount(loanRepayPlanInfoExtend.getRepayment());
							setLoanInfo = true;
						}
					}
					loanRepayPlanInfoExtend.setLoanProcessType(LoanProcessTypeEnum.APPROVAL.type);
					loanRepayPlanInfoExtend.setRepaymentMode(loanBackMode);
					loanRepayPlanInfosNews.add(loanRepayPlanInfoExtend);
				}
				repayPlanInfoService.savePlanList(loanRepayPlanInfosNews);
			}
			applyInfo.setLoanId(loanId);
			applyInfo.setLoanBalanceAmount(new BigDecimal(loanAmount));
			applyInfo.setLoanCollectionState("Collection");
			applyInfo.setLoanCreditAmount(new BigDecimal(loanAmount));
			applyInfoDao.updateApplyInfo(applyInfo);
		}

	}
	@Override
	public IPageList<CommPeoInfo_Query> queryCommPeoInfoListData(Map<String,Object> condition,IPageSize page){
		return commPeoInfoDao.queryCommPeoInfoListData(condition, page);
	}
	@Override
	public void updateCommPeoInfo(CommPeoInfo commPeoInfo){
		commPeoInfoDao.updateCommPeoInfo(commPeoInfo);
	}
	@Override
	public void deleteCommPeoInfo(Integer id){
		commPeoInfoDao.deleteCommPeoInfo(id);
	}

	@Override
	public CommPeoInfo queryCommPeoInfoById(Integer id) {
		return commPeoInfoDao.queryCommPeoInfoById(id);
	}

	@Override
	public List<CommPeoInfo> queryCommPeoInfoListByItemId(Integer itemId) {
		return commPeoInfoDao.queryCommPeoInfoListByItemId(itemId);
	}

	@Override
	public List<CommPeoInfo> queryCommPeoInfoListByLoanId(Integer loanId) {
		return commPeoInfoDao.queryCommPeoInfoListByLoanId(loanId);
	}
}
