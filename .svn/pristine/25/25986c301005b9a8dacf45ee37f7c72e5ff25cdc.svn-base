package banger.service.impl;

import javax.annotation.Resource;

import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.customer.CustBasicInfo;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.OperationCode;
import banger.domain.enumerate.TableInputType;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyTemplate;
import banger.framework.reader.Reader;
import banger.framework.util.IdCardUtil;
import banger.framework.util.TypeUtil;
import banger.moduleIntf.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import banger.domain.config.AutoTableInfo;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.StringUtil;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanTemplateService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author zhusw
 *
 */
@Service
public class AppLoanTemplateService implements IAppLoanTemplateService {
	private final String SUCCESS = "success";
	
	@Resource
	private ILoanModule loanModule;
	
	@Resource
	private IConfigModule configModule;
	@Resource
	private ILoanModelScoreProvider iLoanModelScoreProvider;

	@Resource
	private ICustomerProvider customerProvider;

	@Resource
	private ICustomerModuleIntf customerModule;

	@Override
	public String saveLoanTemplateInfo(JSONObject data) {
		String checkResult = checkJSONData(data);
		LoanApplyInfo loanApplyInfo = null;
		AutoTableInfo tableInfo = null;
		Set<Object> idSet = new HashSet<Object>();
		if(SUCCESS.equals(checkResult)){
			JSONArray root = (JSONArray)data.get("data");
			JSONArray result = new JSONArray();
			for(int i=0;i<root.size();i++){
				JSONObject jo = root.getJSONObject(i);

				JSONObject rjo = new JSONObject();
				Integer tableId = jo.getInt("tableId");
				rjo.put("tableId", tableId);
				if(tableInfo==null)
					tableInfo = configModule.getAutoTemplateProvider().getTableInfoById(tableId);

				if(tableInfo!=null){
					String tableName = tableInfo.getTableDbName();
					DataTable table = new DataTable(tableName);
					for(Object key : jo.keySet()){
						String propertyName = (String)key;
						if(!isFixedPropertyName(propertyName)){
							String columnName = propertyToColumn(propertyName);
							table.addColumn(columnName);
						}
					}
					DataRow row = table.newRow();
					Object id = jo.get("id");
					if(id instanceof Integer && Integer.parseInt(id.toString())>0){
						row.set("ID", id);
					}

					for(Object key : jo.keySet()){
						String propertyName = (String)key;
						Object value = jo.get(propertyName);
						if(!isFixedPropertyName(propertyName)){
							String columnName = propertyToColumn(propertyName);
							if(value instanceof String && "IDENTIFY_NUM".equalsIgnoreCase(columnName)){
								value = IdCardUtil.toUpperCase((String)value);		//转大写
							}
							row.set(columnName, value);

							if ("REPAYMENT_DATE".equalsIgnoreCase(columnName) || "LOAN_BACK_DATE".equalsIgnoreCase(columnName)) {
								if(value!=null && !"".equals(value)) {
									Integer day = (Integer)TypeUtil.changeType(value,Integer.class);
									if(day <= 0 || day > 28){
										return AppJsonResponse.fail(CodeEnum.CODE_134, "还款日仅允许填写1-28");
									}
								}
							}

							if ("LBIZ_SURVEY_RESULT".equals(tableName) && "PROPOSAL_LIMIT".equals(columnName)) {
								if(value!=null && !"".equals(value)) {
									Integer proposalLimit = (Integer)TypeUtil.changeType(value,Integer.class);
									if(proposalLimit <= 0){
										return AppJsonResponse.fail(CodeEnum.CODE_135, "建议期限不能小于0");
									}
								}
							}

							if ("LBIZ_LOAN_APPLY_INFO".equals(tableName) && "LOAN_APPLY_AMOUNT".equals(columnName)) {
								if(value!=null && !"".equals(value)) {
									BigDecimal applyAmount = (BigDecimal)TypeUtil.changeType(value,BigDecimal.class);
									if(applyAmount.doubleValue() <= 0){
										return AppJsonResponse.fail(CodeEnum.CODE_135, "申请金额必须大于0");
									}
								}
							}

							if ("SPOUSE_AGE".equals(columnName) || "CUSTOMER_AGE".equals(columnName)) {
								if(value!=null && !"".equals(value)) {
									Integer age = (Integer)TypeUtil.changeType(value,Integer.class);
									if(age <= 0){
										return AppJsonResponse.fail(CodeEnum.CODE_ERROR, "年龄必须大于0");
									}
								}
							}


						}

					}

					checkResult = checkIdCard(row);			//校验身份证
					if(!SUCCESS.equals(checkResult))
						return checkResult;

					//不能清空有内容的必填项
					Integer loanId = Reader.intReader().getValue(row, "LOAN_ID");
					if(loanApplyInfo==null)
						loanApplyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
					if (id instanceof Integer && ((Integer) id).intValue() > 0) {
						if (loanApplyInfo != null) {
							LoanApplyTemplate loanApplyTemplate = loanModule.getLoanApplyProvider().getApplyTemplateField(loanApplyInfo.getLoanTypeId(), loanApplyInfo.getLoanProcessType(), tableId);
							if (loanApplyTemplate != null) {
								DataTable sourceTable = loanModule.getLoanApplyProvider().getLoanTemplateDataById(tableName, (Integer) id);
								if (sourceTable != null && sourceTable.rowSize() > 0) {
									DataRow sourceRow = sourceTable.getRow(0);
									for (AutoBaseField field : loanApplyTemplate.getFields()) {
										AutoFieldWrapper wrapper = (AutoFieldWrapper)field;
										if(wrapper.getIsAppShow() && wrapper.getNullable()){
											Object sVal = sourceRow.get(wrapper.getColumn());
											Object tVal = row.get(wrapper.getColumn());
											if(!TypeUtil.isNullOrEmpty(sVal)){
												if(TypeUtil.isNullOrEmpty(tVal)) {
													return AppJsonResponse.fail(CodeEnum.CODE_123, "不能清空有内容的必填项！");
												}
											}
										}
									}
								}
							}
						}
					}

					checkResult = checkCustomerNum(row);
					if(!SUCCESS.equals(checkResult))
						return checkResult;

					if("LBIZ_PERSONAL_INFO".equals(tableName)&& row.get("CUSTOMER_NUM") == null){
						row.set("CUSTOMER_NUM","0000008"+System.currentTimeMillis());
					}
					if("LBIZ_PLEDGE_ITEM".equals(tableName)){
						String num = (String)row.get("WARRANTY_NUM");
						if(StringUtil.isNullOrEmpty(num)){
							row.set("WARRANTY_NUM", loanModule.getContractCode(OperationCode.CODE_300.getCode(),true));
						}
					}
					loanModule.getLoanApplyProvider().saveLoanTemplateInfo(table);
					id = table.getRow(0).get("ID");
					rjo.put("id", id);
					idSet.add(id);

					if (loanApplyInfo != null) {
						//更新贷款表的评分得分
						BigDecimal score = iLoanModelScoreProvider.countLoanModelScoreByModeId(loanApplyInfo.getLoanId(),loanApplyInfo.getLoanTypeId(), LoanProcessTypeEnum.INVESTIGATE.type);
						LoanApplyInfo applyInfo1 = new LoanApplyInfo();
						applyInfo1.setLoanId(loanApplyInfo.getLoanId());
						applyInfo1.setModelScore(score);
						loanModule.getLoanApplyProvider().updateModelScoreByLoanId(applyInfo1);
					}
				}
				result.add(rjo);
			}

			if(tableInfo!=null && TableInputType.LIST.equalValue(tableInfo.getTableType())){
				DataTable table = loanModule.getLoanApplyProvider().getLoanTemplateDataById(loanApplyInfo.getLoanProcessType(),tableInfo.getTableDbName(),loanApplyInfo.getLoanId());
				for(DataRow row : table){
					Object id = row.get("ID");
					if(!idSet.contains(id)){
						loanModule.getLoanApplyProvider().deleteLoanTemplateDataById(tableInfo.getTableDbName(),(Integer) id);
					}
				}
			}

			return AppJsonResponse.success(result);
		}else{
			return checkResult;
		}
	} 
	
	private String propertyToColumn(String propertyName){
		StringBuffer colBf = new StringBuffer();
		if(StringUtil.isNotEmpty(propertyName)){
			for (int i = 0; i < propertyName.length(); i++) {
				if(Character.isUpperCase(propertyName.charAt(i))){
					colBf.append("_");
				}
				colBf.append(propertyName.charAt(i));
			}
		}
		return colBf.toString().toUpperCase();
	}
	
	private String checkJSONData(JSONObject data){
		if(data.get("data") instanceof JSONArray){
			return SUCCESS;
		}
		return AppJsonResponse.fail(CodeEnum.CODE_110,"参数不能为空！");
	}

	/**
	 * 校验身份证
	 * @param row
	 * @return
	 */
	private String checkIdCard(DataRow row){
		String tableName = row.getTable().getName();
		String result = "";
		if("LBIZ_JOINT_BORROWER".equals(tableName) || "LBIZ_LOAN_GUARANTEE".equals(tableName)){			//共同借款人和担保人
			String idCard = Reader.stringReader().getValue(row,"ID_CARD");
			String idType = Reader.stringReader().getValue(row,"IDENTIFY_TYPE");
			result = IdCardUtil.checkIdCard(idType,idCard);
		}else if("LBIZ_SPOUSE_INFO".equals(tableName)){		//配偶信息
			String idType = Reader.stringReader().getValue(row,"SPOUSE_IDENTIFY_TYPE");
			String idCard = Reader.stringReader().getValue(row,"SPOUSE_IDENTIFY_NUM");
			result = IdCardUtil.checkIdCard(idType,idCard);
		}else if("LBIZ_PERSONAL_INFO".equals(tableName)){		//个人信息
			String idType = Reader.stringReader().getValue(row,"IDENTIFY_TYPE");
			String idCard = Reader.stringReader().getValue(row,"IDENTIFY_NUM");
			result = IdCardUtil.checkIdCard(idType,idCard);
		}else{
			return SUCCESS;
		}

		if("pass".equals(result)){
			return SUCCESS;
		}else {
			return AppJsonResponse.fail(CodeEnum.CODE_128, "身份证格式不正确");
		}
	}
	/**
	 * 校验客户内码
	 * @param row
	 * @return
	 */
	private String checkCustomerNum(DataRow row){
		String tableName = row.getTable().getName();
		if("LBIZ_PERSONAL_INFO".equals(tableName)){		//个人信息
			String customerNum = Reader.stringReader().getValue(row,"CUSTOMER_NUM");
			if (StringUtil.isNullOrEmpty(customerNum)) {
				return SUCCESS;
			}
			if (!IdCardUtil.isCheckCustomerNum(customerNum)) {
				return AppJsonResponse.fail(CodeEnum.CODE_139,"客户内码只能由数字和英文组成");
			}
			//校验唯一性
			String idType = Reader.stringReader().getValue(row,"IDENTIFY_TYPE");
			String idCard = Reader.stringReader().getValue(row,"IDENTIFY_NUM");
			String custName = Reader.stringReader().getValue(row,"CUSTOMER_NAME");
			Integer customerId = customerProvider.getCustomerIdByCardNameType(custName, idType, idCard);
			if (StringUtil.isNotEmpty(customerNum)) {
				Map<String, Object> condition = new HashedMap();
				condition.put("id", customerId);
				condition.put("type", 1);
				condition.put("customerNum", customerNum);
				CustBasicInfo customer = customerModule.isUniqueCustomerNum(condition);
				if(customer != null){
					//存在客户编码
					return AppJsonResponse.fail(CodeEnum.CODE_138, "客户内码重复");
				}
			}
		}
		return SUCCESS;
	}

	private boolean isFixedPropertyName(String property){
		if("tableId".equalsIgnoreCase(property)){
			return true;
		}
		return false;
	}

}
