package banger.service.impl;

import banger.common.constant.DateFormatConst;
import banger.common.tools.LoanModuleUtil;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseOption;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.enumerate.EnumFiledType;
import banger.domain.enumerate.LoanAuditResultEnum;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.TableInputType;
import banger.domain.loan.*;
import banger.domain.track.MapTagging;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.reader.Reader;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.moduleIntf.*;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanApplyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class AppLoanApplyService implements IAppLoanApplyService {
	@Resource
	private ILoanModule loanModule;

	@Autowired
	private IMapTaggingProvider mapTaggingProvider;

	@Autowired
	private IFormSettingsProvider formSettingsProvider;

	@Resource
	private IConfigModule configModule;

	@Resource
	private ILoanHandOverProvider loanHandOverProvider;
	/**
	 * 得到贷款类型JSON数据
	 * @return
	 */
	public String getLoanTypeJsonString(){
		JSONObject root = new JSONObject();
		List<LoanType> list = loanModule.getLoanApplyProvider().getAllLoanTypeList();
		JSONArray ja = new JSONArray();
		if(list!=null && list.size()>0){
			for(LoanType type : list) {
				JSONObject jo = new JSONObject();
				jo.put("id", type.getTypeId());
				jo.put("name", type.getLoanTypeName());
				ja.add(jo);
			}
		}
		root.put("data",ja);
		setSuccess(root);
		return root.toString();
	}
	/**
	 * 得到贷款合同类型JSON数据
	 * @return
	 */
	public String getLoanContractTypeJsonString(){
		JSONObject root = new JSONObject();
		List<LoanType> list = loanModule.getLoanApplyProvider().getAllLoanContractTypeList();
		JSONArray ja = new JSONArray();
		if(list!=null && list.size()>0){
			for(LoanType type : list) {
				JSONObject jo = new JSONObject();
				jo.put("id", type.getTypeId());
				jo.put("name", type.getLoanTypeName());
				ja.add(jo);
			}
		}
		root.put("data",ja);
		setSuccess(root);
		return root.toString();
	}

	/**
	 * 获取贷款申请字段和下拉项
	 * @return
	 */
	public String getLoanApplyFieldJsonString(){
		JSONObject root = new JSONObject();
		
		JSONObject tjo = new JSONObject();
		tjo.put("tableId", 0);
		tjo.put("name", "贷款申请");
		tjo.put("type", "Forms");
		
		JSONArray ja = new JSONArray();
		List<AutoBaseField> fields = loanModule.getLoanApplyProvider().getApplyFieldList();
		for(AutoBaseField field : fields){
			if(field.getIsAppShow()){
				JSONObject fjo = new JSONObject();
				fjo.put("field", field.getField());
				fjo.put("name", field.getName());
				fjo.put("type", field.getType());
				//fjo.put("column", field.getColumn());
				if(field.getInputLength()>0)
					fjo.put("length", field.getInputLength());
				if(StringUtil.isNotEmpty(field.getUnit()))
					fjo.put("unit", field.getUnit());
				if(field.getNullable()!=null && field.getNullable().equals(true))
					fjo.put("required", true);
				if(field.getOptions()!=null){
					JSONArray oja = new JSONArray();
					for(AutoBaseOption option : field.getOptions()){
						JSONObject joo = new JSONObject();
						joo.put("value", option.getValue());
						joo.put("name", option.getName());
						oja.add(joo);
					}
					fjo.put("options", oja);
				}
				ja.add(fjo);
			}
		}
		
		tjo.put("fields", ja);
		
		root.put("template", tjo);
		
		setSuccess(root);
		
		return root.toString();
	}
	
	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanId
	 * @return
	 */
	public String getLoanTemplateFieldJsonString(Integer loanTypeId,String loanProcessType,Integer tableId,Integer loanId,String listAddFlag){

		boolean editable = false;
		//判断放贷信息是否保存过
		boolean firstloan = false;
		String showProcessType = loanProcessType;

		LoanApplyInfo loanInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
		if(loanInfo!=null){
			LoanProcessTypeEnum processType = LoanProcessTypeEnum.valueOfType(loanInfo.getLoanProcessType());
			if(processType.equals(LoanProcessTypeEnum.APPLY) || processType.equals(LoanProcessTypeEnum.INVESTIGATE)){
				if(loanInfo.getLoanRefuseUser()!=null && loanInfo.getLoanRefuseUser().intValue()>0){
					editable = false;
				}else{
					editable = true;
				}
			}else{
				if(processType.equals(LoanProcessTypeEnum.ALLOT)){
					showProcessType = LoanProcessTypeEnum.APPLY.type;
				}else if("18".equals(String.valueOf(tableId))){
					if("AfterLoan".equals(loanProcessType)){
						showProcessType = LoanProcessTypeEnum.LOAN.type;
					}
				}else{
					showProcessType = LoanProcessTypeEnum.INVESTIGATE.type;
				}
			}
		}

		LoanApplyTemplate temp = loanModule.getLoanApplyProvider().getApplyTemplateField(loanTypeId,showProcessType,tableId);
		DataTable table = null;
		if (!"add".equals(listAddFlag)) {
				table = loanModule.getLoanApplyProvider().getApplyTemplateData(loanTypeId, showProcessType, tableId, loanId);
			if((null == table||table.rowSize()==0)&& 18==tableId){
				firstloan = true;
				table = loanModule.getLoanApprovalProvider().getApprovalDataTableByLoanId(Integer.valueOf(loanId));
			}
		}

		//putTemplateValue(temp,table);

		JSONObject root = new JSONObject();
		//如果是放贷信息 直接塞放贷信息
			int rowSize = 1;			//List 要返回多个模板
			if(null != temp && "List".equals(temp.getType()) && null!=table && table.rowSize()!=0){
				rowSize = table.rowSize();
			}



			JSONArray tja = new JSONArray();
			for(int i=0;i<rowSize;i++){

				Object objId = null;
				DataRow row = null;
				if(table!=null && i < table.rowSize()){
					row = table.getRow(i);
				}
				if("18".equals(String.valueOf(tableId)) && firstloan){
					if (row != null){
						//从贷款合同中查询放款起始时间和结束时间
						Map<String,Object> map = selectLoanDateFromContract(loanId);
						row.set("LOAN_CREDIT_DATE", new Date());
						if(map.containsKey("loanRatioDate") && map.containsKey("loanEndDate")){
							row.set("LOAN_RATIO_DATE", (Date)map.get("loanRatioDate"));
							row.set("LOAN_END_DATE",(Date)map.get("loanEndDate"));
						}
						row.set("LOAN_MODE", row.get("LOAN_MODE"));
						row.set("LOAN_BACK_MODE", row.get("REPAYMENT_MODE"));
						row.set("LOAN_AMOUNT", row.get("RESULT_AMOUNT"));
						row.set("LOAN_LIMIT", row.get("RESULT_LIMIT"));
						row.set("LOAN_RATIO", row.get("RESULT_RATIO"));
						row.set("LOAN_BACK_DATE", row.get("REPAYMENT_DATE"));
					}
				}
				if(row!=null){
					objId = row.get("ID");
				}
				if(temp!=null){
					JSONObject tjo = new JSONObject();
					if(objId!=null)
						tjo.put("id", Integer.valueOf(objId.toString()));
					else if(TableInputType.LIST.equalType(temp.getType())){		//
						Integer id = loanModule.getLoanApplyProvider().newLbizId(temp.getTableName());
						tjo.put("id",id);
					}
					tjo.put("tableId", temp.getId());
					tjo.put("name", temp.getName());
					tjo.put("tableName",temp.getTableName());
					tjo.put("type", temp.getType());

					if(temp.getFields()!=null){
						JSONArray fja = new JSONArray();
						for(AutoBaseField field : temp.getFields()){
							if(field.getIsAppShow()){
								JSONObject fjo = new JSONObject();
								fjo.put("field", field.getField());
								fjo.put("name", field.getName());
								fjo.put("type", field.getType());
								if("CUSTOMER_NUM".equals(field.getColumn())||"Loan".equals(showProcessType)) {
									fjo.put("editable", true);
								}else{
									fjo.put("editable",editable);
								}
								fjo.put("column", field.getColumn());
								Object val = getFieldValue(field,row);
								fjo.put("value", val);
								//fjo.put("column", field.getColumn());
								if(field.getInputLength()>0)
									fjo.put("length", field.getInputLength());
								if(StringUtil.isNotEmpty(field.getUnit()))
									fjo.put("unit", field.getUnit());
								if(field.getNullable()!=null && field.getNullable().equals(true))
									fjo.put("required", true);
								if(field.getOptions()!=null){
									JSONArray oja = new JSONArray();

									/* 单选添加一个空白项 */
									if(EnumFiledType.SELECT.equalType(field.getType())) {
										JSONObject ojo = new JSONObject();
										ojo.put("value", "");
										ojo.put("name", "");
										oja.add(ojo);
									}

									for(AutoBaseOption option : field.getOptions()){
										JSONObject joo = new JSONObject();
										joo.put("value", option.getValue());
										joo.put("name", option.getName());
										oja.add(joo);
									}
									fjo.put("options", oja);
								}
								if ("telephone".equalsIgnoreCase(field.getRule())) {
									fjo.put("telephone", true);
								}
								if(EnumFiledType.ADDRESS.equalType(field.getType())){ //app显示地图有没有标注过
									Map<String,Object> condition = new HashMap<String, Object>();
									if(TableInputType.LIST.equalType(temp.getType()) && objId!=null) {
										condition.put("lbizId", objId);
									}
									condition.put("tableName",temp.getTableName());
									condition.put("columnName",field.getColumn());
									condition.put("loanId",loanInfo.getLoanId());
									List<MapTagging> taggingList = mapTaggingProvider.queryTaggingList(condition);
									fjo.put("tagging",taggingList.size()>0?true:false);
								}
								fja.add(fjo);

								if(("LBIZ_SURVEY_RESULT".equalsIgnoreCase(temp.getTableName()) && "REPAYMENT_MODE".equalsIgnoreCase(field.getColumn())) || ("LBIZ_LOAN_GRANT".equalsIgnoreCase(temp.getTableName()) && "LOAN_BACK_MODE".equalsIgnoreCase(field.getColumn()))){		//请申信息，追加审批决议
									JSONObject rjo = new JSONObject();
									rjo.put("field", "repayPlan");
									rjo.put("name", "还款计划");
									rjo.put("type", "RepayPlan");
									fja.add(rjo);
								}
							}
						}
						tjo.put("fields", fja);
					}
					tja.add(tjo);
				}else{
					tja.add(new JSONObject());
				}
			}



			root.put("templates", tja);
//		}else{
//			root.put("templates",templateList);
//		}

		setSuccess(root);
		
		return root.toString();
	}

	/**
	 * 如果贷款编辑的时候，没有贷款数据，从审批里拉
	 * @param loanId
	 * @param template
	 */
	private void setLoanInfo(String loanId, AutoBaseTemplate template){
		DataTable applyInfoData = loanHandOverProvider.getApprovalDataTableByLoanId(Integer.valueOf(loanId));
		DataTable applyInfoContract = loanHandOverProvider.getDataTableByLoanId("LOAN_CONTRACT",LoanProcessTypeEnum.CONTRACT.type,Integer.valueOf(loanId));
		DataRow dataRow = applyInfoData.getRow(0);
		DataRow dataRow1 = applyInfoContract.getRow(0);
		Map<String, Object> row = new HashMap<String, Object>();
		row.put("loanCreditDate", new Date());
		if (dataRow != null){
			row.put("loanMode", dataRow.get("LOAN_MODE"));
			row.put("loanBackMode", dataRow.get("REPAYMENT_MODE"));
			row.put("loanAmount", dataRow.get("RESULT_AMOUNT"));
			row.put("loanLimit", dataRow.get("RESULT_LIMIT"));
			row.put("loanRatio", dataRow.get("RESULT_RATIO"));
			row.put("loanBackDate", dataRow.get("REPAYMENT_DATE"));
		}
		if(dataRow1 != null){
			row.put("loanRatioDate", dataRow1.get("LOAN_CONTRACT_BEGIN"));
			row.put("loanEndDate",dataRow1.get("LOAN_CONTRACT_END"));
		}
		template.setData(new Object[]{row});
	}

	//从贷款合同中查询放款起始时间和结束时间
	private Map<String,Object> selectLoanDateFromContract(Integer loanId){
		Map<String,Object> map = new HashMap<String, Object>();
		DataTable applyInfoContract = loanHandOverProvider.getDataTableByLoanId("LOAN_CONTRACT",LoanProcessTypeEnum.CONTRACT.type,loanId);
		DataRow dataRow1 = applyInfoContract.getRow(0);
		if(dataRow1 != null){
			map.put("loanRatioDate",dataRow1.get("LOAN_CONTRACT_BEGIN"));
			map.put("loanEndDate",dataRow1.get("LOAN_CONTRACT_END"));
		}
		return map;
	}

	/**
	 * 根据贷款Id得到担保人模版 但是 内容为配偶信息
	 * @param loanId
	 * @return
	 */
	public String getGuaFieldBySpouse(Integer loanTypeId,String loanProcessType,Integer tableId,Integer loanId,String listAddFlag){

		//担保人表单
		LoanApplyTemplate temp = loanModule.getLoanApplyProvider().getApplyTemplateField(loanTypeId,loanProcessType,17);
		//配偶信息数据
		DataTable table = loanModule.getLoanApplyProvider().getApplyTemplateData(loanTypeId, loanProcessType, 3, loanId);
		DataRow row = null;
		if(null!=table){
			row = table.getRow(0);
			if(null!=row ){
				//		FULL_NAME	姓名	SPOUSE_NAME	姓名
				//		IDENTIFY_TYPE	证件类型	SPOUSE_IDENTIFY_TYPE	证件类型
				//		ID_CARD	身份证号	SPOUSE_IDENTIFY_NUM	证件号码
				//		TEL_NUM	联系电话	SPOUSE_PHONE_NUM	联系电话
				//		RELATIONSHIP	关系
				//		BUSINESS_ADDRSS	经营/工作地址	SPOUSE_COMPANY_ADDRESS	单位地址
				row.set("FULL_NAME",row.get("SPOUSE_NAME"));
				row.set("IDENTIFY_TYPE",row.get("SPOUSE_IDENTIFY_TYPE"));
				row.set("ID_CARD",row.get("SPOUSE_IDENTIFY_NUM"));
				row.set("TEL_NUM",row.get("SPOUSE_PHONE_NUM"));
				row.set("RELATIONSHIP","家庭成员");
				row.set("BUSINESS_ADDRSS",row.get("SPOUSE_COMPANY_ADDRESS"));
			}
		}

		JSONObject root = new JSONObject();
		JSONArray tja = new JSONArray();
		Object objId = null;
		if(temp!=null){
			JSONObject tjo = new JSONObject();
			if(objId!=null)
				tjo.put("id", Integer.valueOf(objId.toString()));
			else if(TableInputType.LIST.equalType(temp.getType())){		//queryTableInfoListData
				Integer id = loanModule.getLoanApplyProvider().newLbizId(temp.getTableName());
				tjo.put("id",id);
			}
			tjo.put("tableId", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("tableName",temp.getTableName());
			tjo.put("type", temp.getType());

			if(temp.getFields()!=null){
				JSONArray fja = new JSONArray();
				for(AutoBaseField field : temp.getFields()){
					if(field.getIsAppShow()){
						JSONObject fjo = new JSONObject();
						fjo.put("field", field.getField());
						fjo.put("name", field.getName());
						fjo.put("type", field.getType());
						if("CUSTOMER_NUM".equals(field.getColumn())) {
							fjo.put("editable", true);
						}else{
							fjo.put("editable",true);
						}
						fjo.put("column", field.getColumn());
						Object val = getFieldValue(field,row);
						fjo.put("value", val);
						//fjo.put("column", field.getColumn());
						if(field.getInputLength()>0)
							fjo.put("length", field.getInputLength());
						if(StringUtil.isNotEmpty(field.getUnit()))
							fjo.put("unit", field.getUnit());
						if(field.getNullable()!=null && field.getNullable().equals(true))
							fjo.put("required", true);
						if(field.getOptions()!=null){
							JSONArray oja = new JSONArray();
							/* 单选添加一个空白项 */
							if(EnumFiledType.SELECT.equalType(field.getType())) {
								JSONObject ojo = new JSONObject();
								ojo.put("value", "");
								ojo.put("name", "");
								oja.add(ojo);
							}

							for(AutoBaseOption option : field.getOptions()){
								JSONObject joo = new JSONObject();
								joo.put("value", option.getValue());
								joo.put("name", option.getName());
								oja.add(joo);
							}
							fjo.put("options", oja);
						}
						if ("telephone".equalsIgnoreCase(field.getRule())) {
							fjo.put("telephone", true);
						}
						if(EnumFiledType.ADDRESS.equalType(field.getType())){ //app显示地图有没有标注过
							Map<String,Object> condition = new HashMap<String, Object>();
							if(TableInputType.LIST.equalType(temp.getType()) && objId!=null) {
								condition.put("lbizId", objId);
							}
							condition.put("tableName",temp.getTableName());
							condition.put("columnName",field.getColumn());
							condition.put("loanId",loanId);
							List<MapTagging> taggingList = mapTaggingProvider.queryTaggingList(condition);
							fjo.put("tagging",taggingList.size()>0?true:false);
						}
						fja.add(fjo);
					}
				}
				tjo.put("fields", fja);
			}
			tja.add(tjo);
		}else{
			tja.add(new JSONObject());
		}


		root.put("templates", tja);

		setSuccess(root);

		return root.toString();
	}

	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanTypeId
	 * @param loanProcessType
	 * @param tableId
	 * @return
	 */
	public String getLoanTemplateFieldJsonString(Integer loanTypeId,String loanProcessType,Integer tableId){
		
		LoanApplyTemplate temp = loanModule.getLoanApplyProvider().getApplyTemplateField(loanTypeId,loanProcessType,tableId);
		
		JSONObject root = new JSONObject();
		
		if(temp!=null){
			
			JSONObject tjo = new JSONObject();
			tjo.put("tableId", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("tableName",temp.getTableName());
			tjo.put("type", temp.getType());
			
			if(temp.getFields()!=null){
				JSONArray fja = new JSONArray();
				for(AutoBaseField field : temp.getFields()){
					if(field.getIsAppShow()){
						JSONObject fjo = new JSONObject();
						fjo.put("field", field.getField());
						fjo.put("name", field.getName());
						fjo.put("type", field.getType());
						fjo.put("column", field.getColumn());
						fjo.put("value", field.getDefaultValue());
						if(field.getInputLength()>0)
							fjo.put("length", field.getInputLength());
						if(StringUtil.isNotEmpty(field.getUnit()))
							fjo.put("unit", field.getUnit());
						if(field.getNullable()!=null && field.getNullable().equals(true))
							fjo.put("required", true);
						if(field.getOptions()!=null){
							JSONArray oja = new JSONArray();

							/* 单选添加一个空白项 */
							if(EnumFiledType.SELECT.equalType(field.getType())) {
								JSONObject ojo = new JSONObject();
								ojo.put("value", "");
								ojo.put("name", "");
								oja.add(ojo);
							}

							for(AutoBaseOption option : field.getOptions()){
								JSONObject joo = new JSONObject();
								joo.put("value", option.getValue());
								joo.put("name", option.getName());
								oja.add(joo);
							}
							fjo.put("options", oja);
						}
						fja.add(fjo);
					}
				}
				tjo.put("fields", fja);
			}
			root.put("template", tjo);
		}else{
			root.put("template", new JSONObject());
		}
		
		setSuccess(root);
		
		return root.toString();
	}

	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanTypeId
	 * @param loanProcessType
	 * @return
	 */
	public String getLoanTemplateJsonString(Integer loanTypeId,String loanProcessType){
		JSONObject root = new JSONObject();
		
		JSONArray tja = new JSONArray();
		
		List<LoanApplyTemplate> tempList = loanModule.getLoanApplyProvider().getApplyTemplateList(loanTypeId,loanProcessType);
		for(LoanApplyTemplate temp : tempList){
			JSONObject tjo = new JSONObject();
			tjo.put("tableId", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("type", temp.getType());
			tja.add(tjo);
		}
		
		root.put("templates", tja);
		
		setSuccess(root);
		
		return root.toString();
	}
	
	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanId
	 * @return
	 */
	public String getLoanTemplateJsonString(Integer loanTypeId,String loanProcessType,Integer loanId){
		JSONObject root = new JSONObject();
		
		JSONArray tja = new JSONArray();

		boolean editable = false;
		boolean auditFlag = false;
		boolean loanedittable = false;
		boolean reportFlag = true;			//调查报告
		String showProcessType = loanProcessType;
		
		LoanApplyInfo_Query loanInfo = loanModule.getLoanApplyProvider().getApplyInfoQueryById(loanId);
		if(loanInfo!=null){
			JSONObject tjo = new JSONObject();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("loanId", loanId);
			paramMap.put("isValid", "1");
			paramMap.put("auditResult", LoanAuditResultEnum.PERSONAL.value);
			List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = loanModule.getLoanAuditProvider().queryLoanAuditByCondition(paramMap);
			if (CollectionUtils.isNotEmpty(loanCurrentAuditStatuses)) {
				tjo.put("processId", loanCurrentAuditStatuses.get(0).getProcessId());
			}
			//出账未确认阶段 判断是否能够进行授权取消操作
//			if("Undisclosed".equals(loanProcessType)){
				tjo.put("isAuthorizedCancel",true);
//			}else {
//				tjo.put("isAuthorizedCancel",false);
//			}
			tjo.put("loanTypeId",loanInfo.getLoanTypeId());
			tjo.put("loanClassId",loanInfo.getLoanClassId());
			tjo.put("loanName", loanInfo.getLoanName());
			tjo.put("loanTypeName", loanInfo.getLoanTypeName());
			tjo.put("loanProcessTypeName",loanInfo.getLoanProcessStateDisplay());
			tjo.put("loanIdentifyTypeName", loanInfo.getLoanIdentifyTypeName());
			tjo.put("loanIdentifyNum", loanInfo.getLoanIdentifyNum());
			tjo.put("loanIdentifyNumX", loanInfo.getLoanIdentifyNumX(2));
			tjo.put("loanTelNum", loanInfo.getLoanTelNum());
			tjo.put("loanTelNumX", loanInfo.getLoanTelNumX(2));
			tjo.put("customerId",loanInfo.getLoanCustomerId());
			//tjo.put("loanApplyAmount", loanInfo.getLoanAmountFormat());
			tjo.put("loanApplyAmount", loanInfo.getLoanApplyAmountFormat());
			tjo.put("loanResultAmount",loanInfo.getLoanResultAmountFormat());
			tjo.put("submitEnable", loanInfo.getSubmitEnable());
			tjo.put("refuseEnable", loanInfo.getRefuseEnable());
			tjo.put("isRefuse",loanInfo.getIsRefuse());

			LoanProcessTypeEnum processType = LoanProcessTypeEnum.valueOfType(loanInfo.getLoanProcessType());
			if(processType.equals(LoanProcessTypeEnum.APPLY) || processType.equals(LoanProcessTypeEnum.ALLOT)){
				reportFlag = false;
			}

			LoanType loanType = loanModule.getLoanApplyProvider().getLoanTypeById(loanTypeId);
			if(reportFlag && loanType!=null && loanType.getModeConfigId()!=null && loanType.getModeConfigId().intValue()>0){
				tjo.put("investigateReport",true);
			}else{
				tjo.put("investigateReport",false);
			}

			if(processType.equals(LoanProcessTypeEnum.APPLY) || processType.equals(LoanProcessTypeEnum.INVESTIGATE)){
				if(loanInfo.getLoanRefuseUser()!=null && loanInfo.getLoanRefuseUser().intValue()>0){
					editable = false;
				}else{
					editable = true;
				}
			}else{
				if(processType.equals(LoanProcessTypeEnum.ALLOT)){
					showProcessType = LoanProcessTypeEnum.APPLY.type;
				}else{
					showProcessType = LoanProcessTypeEnum.INVESTIGATE.type;
				}
				if(processType.equals(LoanProcessTypeEnum.CONTRACT)|| processType.equals(LoanProcessTypeEnum.LOAN) || processType.equals(LoanProcessTypeEnum.AFTER_LOAN)){
					auditFlag = true;			//追加审批决议
					if(processType.equals(LoanProcessTypeEnum.LOAN)){
						loanedittable = true;			//放贷阶段可以编辑，其他阶段只能查看
					}
				}
			}

			if(processType.equals(LoanProcessTypeEnum.APPROVAL)){			//审批环节
				LinkedHashMap<String, String> processMap = loanModule.getLoanApprovalProvider().getProcessMapByLoanId(loanId);
				String count = processMap.get("count" + loanInfo.getLoanAuditProcessId());
				tjo.put("moreAudit","1".equals(count) ? false : true);
			}

			if(processType.equals(LoanProcessTypeEnum.APPROVAL) ||processType.equals(LoanProcessTypeEnum.CONTRACT)|| processType.equals(LoanProcessTypeEnum.LOAN) || processType.equals(LoanProcessTypeEnum.AFTER_LOAN)  || processType.equals(LoanProcessTypeEnum.CLEARANCE)){
				JSONArray aja = new JSONArray();
				List<LoanAuditProcess> auditList = loanModule.getLoanApplyProvider().getLoanAuditProcessByLoanId(loanId);
				List<WfApproveProcess> processList = loanModule.getLoanApplyProvider().getApproveProcessListByFlowId(loanInfo.getLoanAuditFlowId(),loanInfo.getLoanAuditParamId());
				Set<Integer> processIdSet = new HashSet<Integer>();
				int index = 0;
				if(auditList!=null && auditList.size()>0){
					for(LoanAuditProcess auditProcess : auditList){
						JSONObject jo = new JSONObject();
						jo.put("processId",auditProcess.getProcessId());
						jo.put("display",auditProcess.getDisplay());
						jo.put("loanId",auditProcess.getLoanId());
						jo.put("pass",auditProcess.isPass());
						jo.put("readonly",false);
						aja.add(jo);
						processIdSet.add(auditProcess.getProcessId());
					}
					index = auditList.size();
				}
				if(processType.equals(LoanProcessTypeEnum.APPROVAL)) {
					if (processList != null && processList.size() > 0) {        //如果工作流里还有环节，追加倒后面
						for (int i = index; i < processList.size(); i++) {
							WfApproveProcess auditProcess = processList.get(i);
							if (!processIdSet.contains(auditProcess.getProcessId())) {
								JSONObject jo = new JSONObject();
								jo.put("processId", auditProcess.getProcessId());
								String display = LoanModuleUtil.getProcessNameByIndex(i + 1) + "审";
								jo.put("display", display);
								jo.put("loanId", loanId);
								jo.put("pass", false);
								jo.put("readonly", true);
								aja.add(jo);                    //安卓端没时间改，先屏弊
							}
						}
					}
				}
				root.put("audit",aja);
			}

			root.put("loan", tjo);
		}
		List<LoanApplyTemplate> tempList = loanModule.getLoanApplyProvider().getApplyTemplateList(loanTypeId,showProcessType,loanId);
		formSettingsProvider.setTemplateShowOrHide(tempList,true);

		for(LoanApplyTemplate temp : tempList){
			if ("hide".equals(temp.getShowOrHide())) {
				continue;
			}
			JSONObject tjo = new JSONObject();
			tjo.put("tableId", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("tableName",temp.getTableName());
			tjo.put("type", temp.getType());
			tjo.put("editable",editable);
			tjo.put("fieldCount", temp.getFieldCount());
			tjo.put("inputFieldCount", temp.getInputFieldCount());
			if(editable) {				//编辑状态，才统计必填项
				tjo.put("requireFieldCount", temp.getRequireFieldCount());
				tjo.put("inputRequireFieldCount", temp.getInputRequireFieldCount());
			}
			tja.add(tjo);

			if(auditFlag && "LBIZ_LOAN_APPLY_INFO".equalsIgnoreCase(temp.getTableName())){		//请申信息，追加审批决议
				tjo.put("tableId", 15);
				tjo.put("name", "审批决议");
				tjo.put("tableName","LBIZ_APPROVAL_RESOLUTION");
				tjo.put("type", TableInputType.FORMS.type);
				tjo.put("editable",false);
				tja.add(tjo);
				if(!LoanProcessTypeEnum.CONTRACT.type.equals(loanProcessType)&&!LoanProcessTypeEnum.SIGN.type.equals(loanProcessType)) {
					tjo.put("tableId", 18);
					tjo.put("name", "放贷信息");
					tjo.put("tableName", "LBIZ_LOAN_GRANT");
					tjo.put("type", TableInputType.FORMS.type);
					if (loanedittable) {
						tjo.put("editable", true);
					} else {
						tjo.put("editable", false);
					}
					tja.add(tjo);
				}
			}
		}
//		if(LoanProcessTypeEnum.SIGN.type.equals(loanProcessType)||LoanProcessTypeEnum.LOAN.type.equals(loanProcessType)||LoanProcessTypeEnum.AFTER_LOAN.type.equals(loanProcessType)||LoanProcessTypeEnum.CLEARANCE.type.equals(loanProcessType)){
//			JSONObject tjo = new JSONObject();
//			tjo.put("tableId", null);
//			tjo.put("name", "贷款合同");
//			tjo.put("tableName",null);
//			tjo.put("type", TableInputType.FORMS.type);
//			tjo.put("editable",false);
//			tja.add(tjo);
//		}
		root.put("templates", tja);
		setSuccess(root);
		
		return root.toString();
	}

	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanTypeId
	 * @param loanProcessType
	 * @return
	 */
	public String getLoanTemplateFieldJsonString(Integer loanTypeId,String loanProcessType){
		JSONObject root = new JSONObject();
		
		JSONArray tja = new JSONArray();
		
		List<LoanApplyTemplate> tempList = loanModule.getLoanApplyProvider().getApplyTemplateFieldList(loanTypeId,loanProcessType);
		for(LoanApplyTemplate temp : tempList){
			JSONObject tjo = new JSONObject();
			tjo.put("tableId", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("tableName",temp.getTableName());
			tjo.put("type", temp.getType());
			
			if(temp.getFields()!=null){
				JSONArray fja = new JSONArray();
				for(AutoBaseField field : temp.getFields()){
					if(field.getIsAppShow()){
						JSONObject fjo = new JSONObject();
						fjo.put("field", field.getField());
						fjo.put("name", field.getName());
						fjo.put("type", field.getType());
						//fjo.put("column", field.getColumn());
						fjo.put("value", field.getDefaultValue());
						if(field.getInputLength()>0)
							fjo.put("length", field.getInputLength());
						if(StringUtil.isNotEmpty(field.getUnit()))
							fjo.put("unit", field.getUnit());
						if(field.getNullable()!=null && field.getNullable().equals(true))
							fjo.put("required", true);
						if(field.getOptions()!=null){
							JSONArray oja = new JSONArray();
							for(AutoBaseOption option : field.getOptions()){
								JSONObject joo = new JSONObject();
								joo.put("value", option.getValue());
								joo.put("name", option.getName());
								oja.add(joo);
							}
							fjo.put("options", oja);
						}
						fja.add(fjo);
					}
				}
				tjo.put("fields", fja);
			}
			tja.add(tjo);
		}
		
		root.put("templates", tja);
		
		setSuccess(root);
		
		return root.toString();
	}

	/**
	 * 得到贷款审批状态信息
	 * @param loanId
	 * @return
	 */
	public String getLoanAuditStateInfoJsonString(Integer loanId,Integer processId){
		JSONObject root = new JSONObject();
		JSONArray ja = new JSONArray();
		List<LoanCurrentAuditStatus> auditStatusList = loanModule.getLoanApplyProvider().getLoanAuditStatusListById(loanId,processId);

		if(auditStatusList!=null && auditStatusList.size()>0){
			for(int i=0;i<auditStatusList.size();i++) {
				LoanCurrentAuditStatus auditStatus = auditStatusList.get(i);
				JSONObject jo = new JSONObject();
				jo.put("auditUserName",auditStatus.getAuditUserName());
				String auditStateName = "";
				if(auditStatus.getAuditResult()!=null && auditStatus.getAuditResult().intValue()==0){
					auditStateName = "待审批";
				}else if(auditStatus.getAuditResult()!=null && auditStatus.getAuditResult().intValue()==1){
					auditStateName = "审批通过";
				}
				jo.put("auditStateName",auditStateName);
				jo.put("auditResult",auditStatus.getAuditResult());

				if(i==0 && auditStatusList.size()==1){		//单人审批
					if(auditStatus.getAuditResolutionId()!=null && auditStatus.getAuditResolutionId().intValue()>0){
						DataTable table = loanModule.getLoanApplyProvider().getLoanTemplateDataById("LBIZ_APPROVAL_RESOLUTION",auditStatus.getAuditResolutionId());
						if(table!=null && table.rowSize()>0){
							DataRow dataRow = table.getRow(0);
							Object amount = dataRow.get("RESULT_AMOUNT");
							if(amount!=null){
								BigDecimal resultAmount = (BigDecimal)TypeUtil.changeType(amount,BigDecimal.class);
								jo.put("resultAmount",resultAmount.doubleValue());
							}else{
								jo.put("resultAmount",0.0);
							}
							String opinion = Reader.stringReader().getValue(dataRow,"APPROVAL_OPINION");
							jo.put("opinion",opinion);
						}
					}
				}

				ja.add(jo);
			}
		}

		root.put("data",ja);
		setSuccess(root);

		return root.toString();
	}

	@Override
	public String getLoanContractTemplateJsonString(Integer contractTypeId, Integer loanId) {

		JSONObject root = new JSONObject();

		JSONArray tja = new JSONArray();

		boolean editable = false;
		boolean auditFlag = false;
		boolean loanedittable = false;
		boolean reportFlag = true;			//调查报告
		String showProcessType = LoanProcessTypeEnum.CONTRACT.type;

		LoanApplyInfo_Query loanInfo = loanModule.getLoanApplyProvider().getApplyInfoQueryById(loanId);
		if(loanInfo!=null){
			JSONObject tjo = new JSONObject();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("loanId", loanId);
			paramMap.put("isValid", "1");
			paramMap.put("auditResult", LoanAuditResultEnum.PERSONAL.value);
			List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = loanModule.getLoanAuditProvider().queryLoanAuditByCondition(paramMap);
			if (CollectionUtils.isNotEmpty(loanCurrentAuditStatuses)) {
				tjo.put("processId", loanCurrentAuditStatuses.get(0).getProcessId());
			}
			tjo.put("loanTypeId",loanInfo.getLoanTypeId());
			if(LoanProcessTypeEnum.CONTRACT.type.equals(loanInfo.getLoanProcessType())) {
				tjo.put("contractTypeId", contractTypeId);
			}else{
				contractTypeId = loanInfo.getLoanContractTypeId();
				tjo.put("contractTypeId", loanInfo.getLoanContractTypeId());
			}
			tjo.put("loanClassId",loanInfo.getLoanClassId());
			tjo.put("loanName", loanInfo.getLoanName());
			tjo.put("loanTypeName", loanInfo.getLoanTypeName());
			tjo.put("contractCheckUserId",loanInfo.getContractCheckUser());
			tjo.put("loanProcessTypeName",loanInfo.getLoanProcessStateDisplay());
			tjo.put("loanIdentifyTypeName", loanInfo.getLoanIdentifyTypeName());
			tjo.put("loanIdentifyNum", loanInfo.getLoanIdentifyNum());
			tjo.put("loanIdentifyNumX", loanInfo.getLoanIdentifyNumX(2));
			tjo.put("loanTelNum", loanInfo.getLoanTelNum());
			tjo.put("loanTelNumX", loanInfo.getLoanTelNumX(2));
			tjo.put("customerId",loanInfo.getLoanCustomerId());
			//tjo.put("loanApplyAmount", loanInfo.getLoanAmountFormat());
			tjo.put("loanApplyAmount", loanInfo.getLoanApplyAmountFormat());
			tjo.put("loanResultAmount",loanInfo.getLoanResultAmountFormat());
			tjo.put("submitEnable", loanInfo.getSubmitEnable());
			tjo.put("refuseEnable", loanInfo.getRefuseEnable());
			tjo.put("isRefuse",loanInfo.getIsRefuse());

			LoanProcessTypeEnum processType = LoanProcessTypeEnum.valueOfType(loanInfo.getLoanProcessType());

			LoanType loanType = loanModule.getLoanApplyProvider().getLoanTypeById(loanInfo.getLoanTypeId());
			if(reportFlag && loanType!=null && loanType.getModeConfigId()!=null && loanType.getModeConfigId().intValue()>0){
				tjo.put("investigateReport",true);
			}else{
				tjo.put("investigateReport",false);
			}

			if(LoanProcessTypeEnum.CONTRACT.type.equals(loanInfo.getLoanProcessType())){
				editable = true;
			}

//				JSONArray aja = new JSONArray();
//				List<LoanAuditProcess> auditList = loanModule.getLoanApplyProvider().getLoanAuditProcessByLoanId(loanId);
//				List<WfApproveProcess> processList = loanModule.getLoanApplyProvider().getApproveProcessListByFlowId(loanInfo.getLoanAuditFlowId(),loanInfo.getLoanAuditParamId());
//				Set<Integer> processIdSet = new HashSet<Integer>();
//				int index = 0;
//				if(auditList!=null && auditList.size()>0){
//					for(LoanAuditProcess auditProcess : auditList){
//						JSONObject jo = new JSONObject();
//						jo.put("processId",auditProcess.getProcessId());
//						jo.put("display",auditProcess.getDisplay());
//						jo.put("loanId",auditProcess.getLoanId());
//						jo.put("pass",auditProcess.isPass());
//						jo.put("readonly",false);
//						aja.add(jo);
//						processIdSet.add(auditProcess.getProcessId());
//					}
//					index = auditList.size();
//				}
//
//				root.put("audit",aja);


			root.put("loan", tjo);
		}


		if(contractTypeId!=null){
		List<LoanApplyTemplate> tempList = loanModule.getLoanApplyProvider().getApplyTemplateList(contractTypeId,showProcessType,loanId);

		formSettingsProvider.setTemplateShowOrHide(tempList,true);

		for(LoanApplyTemplate temp : tempList){
			if ("hide".equals(temp.getShowOrHide())) {
				continue;
			}
			JSONObject tjo = new JSONObject();
			tjo.put("tableId", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("tableName",temp.getTableName());
			tjo.put("type", temp.getType());
			tjo.put("editable",editable);
			tjo.put("fieldCount", temp.getFieldCount());
			tjo.put("inputFieldCount", temp.getInputFieldCount());
			if(editable) {				//编辑状态，才统计必填项
				tjo.put("requireFieldCount", temp.getRequireFieldCount());
				tjo.put("inputRequireFieldCount", temp.getInputRequireFieldCount());
			}
			tja.add(tjo);


		}

		root.put("templates", tja);
		}
		setSuccess(root);


		return root.toString();
	}

	public String getContractTemplateFieldJsonString(Integer loanTypeId,Integer tableId,Integer loanId){

		boolean editable = false;
		//判断放贷信息是否保存过
		boolean firstloan = false;
		String showProcessType = LoanProcessTypeEnum.CONTRACT.type;

		LoanApplyInfo loanInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
		if(loanInfo!=null){
			LoanProcessTypeEnum processType = LoanProcessTypeEnum.valueOfType(loanInfo.getLoanProcessType());
			if(processType.equals(LoanProcessTypeEnum.CONTRACT)){
				if(loanInfo.getLoanRefuseUser()!=null && loanInfo.getLoanRefuseUser().intValue()>0){
					editable = false;
				}else{
					editable = true;
				}
			}

		}

		LoanApplyTemplate temp = loanModule.getLoanApplyProvider().getApplyTemplateField(loanTypeId,showProcessType,tableId);
		DataTable table = null;

			table = loanModule.getLoanApplyProvider().getApplyTemplateData(loanTypeId, showProcessType, tableId, loanId);
			if((null == table||table.rowSize()==0)&& 18==tableId){
				firstloan = true;
				table = loanModule.getLoanApprovalProvider().getApprovalDataTableByLoanId(Integer.valueOf(loanId));
			}

		JSONObject root = new JSONObject();
		//如果是放贷信息 直接塞放贷信息
		int rowSize = 1;			//List 要返回多个模板
		if(null != temp && "List".equals(temp.getType()) && null!=table && table.rowSize()!=0){
			rowSize = table.rowSize();
		}



		JSONArray tja = new JSONArray();
		for(int i=0;i<rowSize;i++){

			Object objId = null;
			DataRow row = null;
			if(table!=null && i < table.rowSize()){
				row = table.getRow(i);
			}
			if(row!=null){
				objId = row.get("ID");
			}
			if(temp!=null){
				JSONObject tjo = new JSONObject();
				if(objId!=null)
					tjo.put("id", Integer.valueOf(objId.toString()));
				else if(TableInputType.LIST.equalType(temp.getType())){		//
					Integer id = loanModule.getLoanApplyProvider().newLbizId(temp.getTableName());
					tjo.put("id",id);
				}
				tjo.put("tableId", temp.getId());
				tjo.put("name", temp.getName());
				tjo.put("tableName",temp.getTableName());
				tjo.put("type", temp.getType());

				if(temp.getFields()!=null){
					JSONArray fja = new JSONArray();
					for(AutoBaseField field : temp.getFields()){
						if(field.getIsAppShow()){
							JSONObject fjo = new JSONObject();
							fjo.put("field", field.getField());
							fjo.put("name", field.getName());
							fjo.put("type", field.getType());
							fjo.put("editable", editable);
							fjo.put("column", field.getColumn());
							Object val = getFieldValue(field,row);
							fjo.put("value", val);
							//fjo.put("column", field.getColumn());
							if(field.getInputLength()>0)
								fjo.put("length", field.getInputLength());
							if(StringUtil.isNotEmpty(field.getUnit()))
								fjo.put("unit", field.getUnit());
							if(field.getNullable()!=null && field.getNullable().equals(true))
								fjo.put("required", true);
							if(field.getOptions()!=null){
								JSONArray oja = new JSONArray();

									/* 单选添加一个空白项 */
								if(EnumFiledType.SELECT.equalType(field.getType())) {
									JSONObject ojo = new JSONObject();
									ojo.put("value", "");
									ojo.put("name", "");
									oja.add(ojo);
								}

								for(AutoBaseOption option : field.getOptions()){
									JSONObject joo = new JSONObject();
									joo.put("value", option.getValue());
									joo.put("name", option.getName());
									oja.add(joo);
								}
								fjo.put("options", oja);
							}
							if ("telephone".equalsIgnoreCase(field.getRule())) {
								fjo.put("telephone", true);
							}
							if(EnumFiledType.ADDRESS.equalType(field.getType())){ //app显示地图有没有标注过
								Map<String,Object> condition = new HashMap<String, Object>();
								if(TableInputType.LIST.equalType(temp.getType()) && objId!=null) {
									condition.put("lbizId", objId);
								}
								condition.put("tableName",temp.getTableName());
								condition.put("columnName",field.getColumn());
								condition.put("loanId",loanInfo.getLoanId());
								List<MapTagging> taggingList = mapTaggingProvider.queryTaggingList(condition);
								fjo.put("tagging",taggingList.size()>0?true:false);
							}
							fja.add(fjo);
						}
					}
					tjo.put("fields", fja);
				}
				tja.add(tjo);
			}else{
				tja.add(new JSONObject());
			}
		}



		root.put("templates", tja);

		setSuccess(root);

		return root.toString();
	}

	private void putTemplateValue(AutoBaseTemplate template, DataTable dataTable) {
		if(dataTable!=null && dataTable.rowSize()>0){
			DataRow row = dataTable.getRow(0);
			for(AutoBaseField field : template.getFields()){
				Object object = row.get(field.getColumn());
				if("Date".equals(field.getType())&&null!=object){
					object = new SimpleDateFormat(DateFormatConst.DEFAULT_DATE_FORMAT).format(object);
				}
				field.setValue(object);
			}
		}

	}
	
	private Object getFieldValue(AutoBaseField field, DataRow dataRow) {
		if(dataRow!=null){
			Object val = dataRow.get(field.getColumn());
			if("Date".equals(field.getType())&&null!= val){
				val = new SimpleDateFormat(DateFormatConst.DEFAULT_DATE_FORMAT).format( val);
			}
			if ((String) TypeUtil.changeType(val, String.class) == null) {
				return field.getDefaultValue();
			}
			return val;
		}
		return field.getDefaultValue();
	}
	
	private void setSuccess(JSONObject resultJson){
		resultJson.put("code", CodeEnum.CODE_100.getCode());
		resultJson.put("msg", CodeEnum.CODE_100.getMsg());
	}

//	/**
//	 * 根据自定义表单信息和贷款信息ID获取表数据
//	 * @param tableName
//	 * @param id
//	 * @return
//	 */
//	public DataTable getDataTableByLoanId(String tableName, String presType, Integer id){
//
//	}
}
