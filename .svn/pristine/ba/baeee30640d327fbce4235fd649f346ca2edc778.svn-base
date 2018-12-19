package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.enumerate.CodeDictEnum;
import banger.domain.loan.*;
import banger.framework.util.JsonDateValueProcessor;
import banger.moduleIntf.ILoanAnalysislBusinessAndConsumProvider;
import banger.moduleIntf.ILoanAssetsProvider;
import banger.moduleIntf.ILoanModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanApplyService;
import banger.util.CodeJsonUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 贷款资产负债
 * @author kangb
 *
 */
@RequestMapping("/api")
@Controller
public class AppLoanAssetsController extends BaseController implements Serializable {


	private static final long serialVersionUID = -5649175717818323257L;
	@Resource
	private ILoanModule loanModule;
	
	@Resource
	private IAppLoanApplyService appLoanApplyService;

	@Resource
	private ILoanAssetsProvider loanAssetsProvider;
	@Autowired
	private ILoanAnalysislBusinessAndConsumProvider analysislBusinessAndConsumProvider;

	/**
	 * 根据贷款id得到资产负债汇总
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getAssetsInfoByLoanId", method = RequestMethod.POST)
	public ResponseEntity<String> getAssetsInfoByLoanId(HttpServletRequest request,HttpServletResponse response){
		try {
			String reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isEmpty(reqJson)){
				log.error("设备列表，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			JSONObject jsonObject = new JSONObject().fromObject(reqJson);
			Integer loanId = jsonObject.containsKey("loanId")?jsonObject.getInt("loanId"):null;

			JSONObject jo = new JSONObject();
			if(null!=loanId){
				LoanAssetsInfo assetsInfo = loanAssetsProvider.getAssetsInfoByLoanId(loanId);
				if(null!=assetsInfo){
					String[] filedNames = getFiledName(assetsInfo);
					for (String filedName : filedNames) {
						if(!filedName.equals("serialVersionUID")){
							jo.put(transVar(filedName),getFieldValueByName(filedName, assetsInfo));
						}
					}
				}
			}else{
				jo = null;
			}

			return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
		}catch(Exception e){
			log.error("根据贷款id得到资产负债汇总",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);

	}

	/**
	 * 保存资产负债 项目
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/saveAssetsItem", method = RequestMethod.POST)
	public ResponseEntity<String> saveAssetsItem(HttpServletRequest request,HttpServletResponse response){

		try {

			String reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isEmpty(reqJson)){
				log.error("保存资产负债，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

//			JsonConfig jsonConfig = new JsonConfig();
//			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
//			JSONObject jsonObject = new JSONObject().fromObject(reqJson,jsonConfig);
			JSONObject jsonObject = new JSONObject().fromObject(reqJson);
			Integer userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;
			Integer loanId = jsonObject.containsKey("loanId")?jsonObject.getInt("loanId"):null;
			Integer loanClassId = jsonObject.containsKey("loanClassId")?jsonObject.getInt("loanClassId"):null;
			String columnName = jsonObject.containsKey("columnName")?jsonObject.getString("columnName") :null;
//			JSONObject saveJson = jsonObject.containsKey("saveJson")?jsonObject.getJSONObject("saveJson"):null;

			if(null!=loanId&&StringUtils.isNotEmpty(columnName)){
				if(AppParamsConst.LOAN_ASSETS_ACCOUNT_ITEM.contains(columnName)){
					LoanAssetsAccountItem accountItem = (LoanAssetsAccountItem) JSONObject.toBean(jsonObject,LoanAssetsAccountItem.class);
					if(null!=accountItem){

						accountItem.setLoanClassId(loanClassId);
						accountItem.setTableName("LOAN_ASSETS_ACCOUNT_ITEM");
						accountItem.setColumnName(columnName);
						accountItem.setLoanId(loanId);

						BigDecimal oldAmount = new BigDecimal(0);
						BigDecimal amount = accountItem.getAmount();
						if(null==amount){
							amount = new BigDecimal(0);
						}
//						amount.setScale(2,BigDecimal.ROUND_HALF_UP);
						accountItem.setAmount(amount);

						if (null==accountItem.getId()||accountItem.getId()<=0){
							accountItem.setCreateUser(userId);
							accountItem.setCreateDate(new Date());
							loanAssetsProvider.insertAssetsAccountItem(accountItem);
						}else{
							LoanAssetsAccountItem oldAccountItem = loanAssetsProvider.getAssetsAccountById(accountItem.getId());
							if(null!=oldAmount&&null!=oldAccountItem.getAmount()){
								oldAmount = oldAccountItem.getAmount();
							}
							accountItem.setUpdateUser(userId);
							accountItem.setUpdateDate(new Date());
							loanAssetsProvider.updateAssetsAccountItem(accountItem);
						}
						// 更新总额
						loanAssetsProvider.updateAssetsAmount(loanId, loanClassId, userId, amount.subtract(oldAmount), columnName);

					}
				}else if(AppParamsConst.LOAN_ASSETS_AMOUNT_ITEM.contains(columnName)){
					LoanAssetsAmountItem amountItem = (LoanAssetsAmountItem) JSONObject.toBean(jsonObject,LoanAssetsAmountItem.class);
					if(null!=amountItem){

						amountItem.setLoanClassId(loanClassId);
						amountItem.setTableName("LOAN_ASSETS_AMOUNT_ITEM");
						amountItem.setColumnName(columnName);
						amountItem.setLoanId(loanId);

						BigDecimal oldAmount = new BigDecimal(0);
						BigDecimal amount = amountItem.getAmount();
						if(null==amount){
							amount = new BigDecimal(0);
						}
//						amount.setScale(2,BigDecimal.ROUND_HALF_UP);
						amountItem.setAmount(amount);

						if (null==amountItem.getId()||amountItem.getId()<=0){
							amountItem.setCreateUser(userId);
							amountItem.setCreateDate(new Date());
							loanAssetsProvider.insertAssetsAmountItem(amountItem);
						}else{
							LoanAssetsAmountItem oldAmountItem = loanAssetsProvider.getAssetsAmountById(amountItem.getId());
							if(null!=oldAmount&&null!=oldAmountItem.getAmount()){
								oldAmount = oldAmountItem.getAmount();
							}
							amountItem.setUpdateUser(userId);
							amountItem.setUpdateDate(new Date());
							loanAssetsProvider.updateAssetsAmountItem(amountItem);
						}

						// 更新总额
						loanAssetsProvider.updateAssetsAmount(loanId, loanClassId, userId, amount.subtract(oldAmount), columnName);

					}

				}else if(AppParamsConst.LOAN_ASSETS_DEBTS_ITEM.contains(columnName)){
					LoanAssetsDebtsItem assetsDebtsItem = (LoanAssetsDebtsItem) JSONObject.toBean(jsonObject,LoanAssetsDebtsItem.class);
					if(null!=assetsDebtsItem){
						if(jsonObject.containsKey("issueDate")){
							assetsDebtsItem.setIssueDate(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("issueDate")));
						}
						assetsDebtsItem.setLoanClassId(loanClassId);
						assetsDebtsItem.setTableName("LOAN_ASSETS_AMOUNT_ITEM");
						assetsDebtsItem.setColumnName(columnName);
						assetsDebtsItem.setLoanId(loanId);

						BigDecimal oldAmount = new BigDecimal(0);
						BigDecimal amount = assetsDebtsItem.getBalanceAmount();
						if(null==amount){
							amount = new BigDecimal(0);
						}
//						amount.setScale(2,BigDecimal.ROUND_HALF_UP);
						assetsDebtsItem.setBalanceAmount(amount);

						if (null==assetsDebtsItem.getId()||assetsDebtsItem.getId()<=0){
							assetsDebtsItem.setCreateUser(userId);
							assetsDebtsItem.setCreateDate(new Date());
							loanAssetsProvider.insertAssetsDebtsItem(assetsDebtsItem);
						}else{
							LoanAssetsDebtsItem oldDebtsItem = loanAssetsProvider.getAssetsDebtsById(assetsDebtsItem.getId());
							if(null!=oldAmount&&null!=oldDebtsItem.getBebtsAmount()){
								oldAmount = oldDebtsItem.getBalanceAmount();
							}
							assetsDebtsItem.setUpdateUser(userId);
							assetsDebtsItem.setUpdateDate(new Date());
							loanAssetsProvider.updateAssetsDebtsItem(assetsDebtsItem);
						}
						// 更新总额
						loanAssetsProvider.updateAssetsAmount(loanId, loanClassId, userId, amount.subtract(oldAmount), columnName);


					}

				}else if(AppParamsConst.LOAN_ASSETS_FIXED_ITEM.contains(columnName)){
					LoanAssetsFixedItem assetsFixedItem = (LoanAssetsFixedItem) JSONObject.toBean(jsonObject,LoanAssetsFixedItem.class);
					if(null!=assetsFixedItem){

						assetsFixedItem.setLoanClassId(loanClassId);
						assetsFixedItem.setTableName("LOAN_ASSETS_AMOUNT_ITEM");
						assetsFixedItem.setColumnName(columnName);
						assetsFixedItem.setLoanId(loanId);

						BigDecimal oldAmount = new BigDecimal(0);
						BigDecimal amount = assetsFixedItem.getAmount();
						if(null==amount){
							amount = new BigDecimal(0);
						}
//						amount.setScale(2,BigDecimal.ROUND_HALF_UP);
						assetsFixedItem.setAmount(amount);

						if (null==assetsFixedItem.getId()||assetsFixedItem.getId()<=0){
							assetsFixedItem.setCreateUser(userId);
							assetsFixedItem.setCreateDate(new Date());
							loanAssetsProvider.insertAssetsFixedItem(assetsFixedItem);
						}else{
							LoanAssetsFixedItem oldFixedItem = loanAssetsProvider.getAssetsFixedById(assetsFixedItem.getId());
							if(null!=oldAmount&&null!=oldFixedItem.getAmount()){
								oldAmount = oldFixedItem.getAmount();
							}
							assetsFixedItem.setUpdateUser(userId);
							assetsFixedItem.setUpdateDate(new Date());
							loanAssetsProvider.updateAssetsFixedItem(assetsFixedItem);
						}
						// 更新总额
						loanAssetsProvider.updateAssetsAmount(loanId, loanClassId, userId, amount.subtract(oldAmount), columnName);


					}

				}else if(AppParamsConst.LOAN_ASSETS_STOCK_ITEM.contains(columnName)){
					LoanAssetsStockItem assetsStockItem = (LoanAssetsStockItem) JSONObject.toBean(jsonObject,LoanAssetsStockItem.class);
					if(null!=assetsStockItem){

						assetsStockItem.setLoanClassId(loanClassId);
						assetsStockItem.setTableName("LOAN_ASSETS_AMOUNT_ITEM");
						assetsStockItem.setColumnName(columnName);
						assetsStockItem.setLoanId(loanId);

						BigDecimal oldAmount = new BigDecimal(0);
						BigDecimal amount = assetsStockItem.getAmount();
						if(null==amount){
							amount = new BigDecimal(0);
						}
//						amount.setScale(2,BigDecimal.ROUND_HALF_UP);
						assetsStockItem.setAmount(amount);

						if (null==assetsStockItem.getId()||assetsStockItem.getId()<=0){
							assetsStockItem.setCreateUser(userId);
							assetsStockItem.setCreateDate(new Date());
							loanAssetsProvider.insertAssetsStockItem(assetsStockItem);
						}else{
							LoanAssetsStockItem oldStockItem = loanAssetsProvider.getAssetsStockById(assetsStockItem.getId());
							if(null!=oldAmount&&null!=oldStockItem.getAmount()){
								oldAmount = oldStockItem.getAmount();
							}
							assetsStockItem.setUpdateUser(userId);
							assetsStockItem.setUpdateDate(new Date());
							loanAssetsProvider.updateAssetsStockItem(assetsStockItem);
						}
						// 更新总额
						loanAssetsProvider.updateAssetsAmount(loanId, loanClassId, userId, amount.subtract(oldAmount), columnName);


					}

				}

			}else{
				log.error("参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);

		}catch(Exception e){
			log.error("保存资产负债 项目",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}


	/**
	 * 保存资产负债 项目
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/saveAssetsOffItem", method = RequestMethod.POST)
	public ResponseEntity<String> saveAssetsOffItem(HttpServletRequest request,HttpServletResponse response){

		try {

			String reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isEmpty(reqJson)){
				log.error("保存表外资产负债，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			JSONObject jsonObject = new JSONObject().fromObject(reqJson);
			Integer userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;
			Integer loanId = jsonObject.containsKey("loanId")?jsonObject.getInt("loanId"):null;
			Integer loanClassId = jsonObject.containsKey("loanClassId")?jsonObject.getInt("loanClassId"):null;
			Double offAssetsAmount = jsonObject.containsKey("offAssetsAmount")?jsonObject.getDouble("offAssetsAmount") :null;
			Double offDebtsAmount = jsonObject.containsKey("offDebtsAmount")?jsonObject.getDouble("offDebtsAmount") :null;
			String offRemark = jsonObject.containsKey("offRemark")?jsonObject.getString("offRemark") :null;

			if(null!=loanId&&null!=loanClassId){
				LoanAssetsInfo assetsInfo = loanAssetsProvider.getAssetsInfoByLoanId(loanId);
				if(null!=assetsInfo) {
					assetsInfo.setUpdateUser(userId);
					assetsInfo.setUpdateDate(new Date());
					assetsInfo.setOffAssetsAmount(null!=offAssetsAmount?new BigDecimal(offAssetsAmount):new BigDecimal(0));
					assetsInfo.setOffDebtsAmount(null != offDebtsAmount ? new BigDecimal(offDebtsAmount) : new BigDecimal(0));
					assetsInfo.setOffRemark(StringUtils.isNotBlank(offRemark) ? offRemark : "");
					loanAssetsProvider.updateAssetsInfo(assetsInfo);
				}else{
					assetsInfo = new LoanAssetsInfo();
					assetsInfo.setCreateUser(userId);
					assetsInfo.setCreateDate(new Date());
					assetsInfo.setOffAssetsAmount(null!=offAssetsAmount?new BigDecimal(offAssetsAmount):new BigDecimal(0));
					assetsInfo.setOffDebtsAmount(null != offDebtsAmount ? new BigDecimal(offDebtsAmount) : new BigDecimal(0));
					assetsInfo.setOffRemark(StringUtils.isNotBlank(offRemark) ? offRemark : "");
					assetsInfo.setLoanId(loanId);
					assetsInfo.setLoanClassId(loanClassId);
					loanAssetsProvider.insertAssetsFixedItem(assetsInfo);
				}
				//根据贷款id处理三表数据,另存财务分析详情明细
				analysislBusinessAndConsumProvider.saveAnalysislBusinessOrConsum(loanId);

			}else{
				log.error("参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);

		}catch(Exception e){
			log.error("保存表外资产负债 项目",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	/**
	 * 根据贷款id得到资产负债 项目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getAssetsItemListByLoanId", method = RequestMethod.POST)
	public ResponseEntity<String> getAssetsItemListByLoanId(HttpServletRequest request,HttpServletResponse response){
		try {
			String reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isEmpty(reqJson)){
				log.error("设备列表，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			JSONObject jsonObject = new JSONObject().fromObject(reqJson);
			Integer loanId = jsonObject.containsKey("loanId")?jsonObject.getInt("loanId"):null;
			Integer loanClassId = jsonObject.containsKey("loanClassId")?jsonObject.getInt("loanClassId"):null;
			String columnName = jsonObject.containsKey("columnName")?jsonObject.getString("columnName"):null;

			JSONArray ja = new JSONArray();
			if(null!=loanId&&StringUtils.isNotBlank(columnName)){

				String tableName = "";
				if(AppParamsConst.LOAN_ASSETS_ACCOUNT_ITEM.contains(columnName)){
					tableName = "LOAN_ASSETS_ACCOUNT_ITEM";
				}else if(AppParamsConst.LOAN_ASSETS_AMOUNT_ITEM.contains(columnName)){
					tableName = "LOAN_ASSETS_AMOUNT_ITEM";
				}if(AppParamsConst.LOAN_ASSETS_DEBTS_ITEM.contains(columnName)){
					tableName = "LOAN_ASSETS_DEBTS_ITEM";
				}if(AppParamsConst.LOAN_ASSETS_FIXED_ITEM.contains(columnName)){
					tableName = "LOAN_ASSETS_FIXED_ITEM";
				}if(AppParamsConst.LOAN_ASSETS_STOCK_ITEM.contains(columnName)){
					tableName = "LOAN_ASSETS_STOCK_ITEM";
				}

				List<LoanAssetsAmountItem> amountItemList = loanAssetsProvider.getAssetsItemListByColumnName(loanId, columnName, tableName);

				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				ja.addAll(amountItemList,jsonConfig);

				if("ASSETS_CASH_AMOUNT".equals(columnName)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.资产负债_现金_项目);
				}else if("ASSETS_STOCK_AMOUNT".equals(columnName)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.资产负债_存货_类型);
					CodeJsonUtil.copyProperty(ja, "itemName", "itemNameDisplay");
				}else if("ASSETS_OPERATING_AMOUNT".equals(columnName)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.资产情况_其他经营_项目);
				}else if("ASSETS_NON_OPERATING_AMOUNT".equals(columnName)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.资产情况_其他非经营_项目);
				}else if("ASSETS_FIXED_AMOUNT".equals(columnName)&&"1".equals(loanClassId)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.资产情况_经营类_固定资产_类别);
				}else if("ASSETS_FIXED_AMOUNT".equals(columnName)&&"2".equals(loanClassId)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.资产情况_消费类_固定资产_类别);
				}else if("ASSETS_INVEST_AMOUNT".equals(columnName)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.资产负债_投资性资产_项目);
				}else if("DEBTS_CONSUME_AMOUNT".equals(columnName)){
					CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.负债情况_消费性负债_来源);
				}else{
					CodeJsonUtil.copyProperty(ja, "itemName", "itemNameDisplay");
					CodeJsonUtil.copyProperty(ja,"itemType","itemTypeDisplay");
				}


			}else{
				ja = null;
			}

			return new ResponseEntity<String>(AppJsonResponse.success(ja), HttpStatus.OK);
		}catch(Exception e){
			log.error("根据贷款id得到资产负债 项目列表",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);

	}



	/**
	 * 根据贷款id得到资产负债 项目明细
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getAssetsItemDetailById", method = RequestMethod.POST)
	public ResponseEntity<String> getAssetsItemDetailById(HttpServletRequest request,HttpServletResponse response){
		try {

			String reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isEmpty(reqJson)){
				log.error("设备列表，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			JSONObject jsonObject = new JSONObject().fromObject(reqJson);
			Integer id = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;
			String columnName = jsonObject.containsKey("columnName")?jsonObject.getString("columnName"):null;
			Integer loanClassId = 0;// jsonObject.containsKey("loanClassId")?jsonObject.getInt("loanClassId"):null;

			JSONObject jo = new JSONObject();
			Object object = new Object();
			if(null!=id&&StringUtils.isNotBlank(columnName)){

				if(AppParamsConst.LOAN_ASSETS_ACCOUNT_ITEM.contains(columnName)){
					object =loanAssetsProvider.getAssetsAccountById(id);
				}else if(AppParamsConst.LOAN_ASSETS_AMOUNT_ITEM.contains(columnName)){
					object =loanAssetsProvider.getAssetsAmountById(id);
				}if(AppParamsConst.LOAN_ASSETS_DEBTS_ITEM.contains(columnName)){
					object =loanAssetsProvider.getAssetsDebtsById(id);
				}if(AppParamsConst.LOAN_ASSETS_FIXED_ITEM.contains(columnName)){
					object =loanAssetsProvider.getAssetsFixedById(id);
				}if(AppParamsConst.LOAN_ASSETS_STOCK_ITEM.contains(columnName)){
					object =loanAssetsProvider.getAssetsStockById(id);
				}

				if(null!=object){
					String[] filedNames = getFiledName(object);
					if(null!=filedNames&&filedNames.length>0){
						for (String filedName : filedNames) {
							Object value = getFieldValueByName(filedName, object);
							jo.put(filedName,value);
							if("loanClassId".equals(filedName)&&value instanceof Integer){
								loanClassId = Integer.valueOf(value.toString());
							}
						}
					}

					if("ASSETS_CASH_AMOUNT".equals(columnName)){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.资产负债_现金_项目);
					}else if("ASSETS_STOCK_AMOUNT".equals(columnName)){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.资产负债_存货_类型);
					}else if("ASSETS_OPERATING_AMOUNT".equals(columnName)){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.资产情况_其他经营_项目);
					}else if("ASSETS_NON_OPERATING_AMOUNT".equals(columnName)){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.资产情况_其他非经营_项目);
					}else if("ASSETS_FIXED_AMOUNT".equals(columnName)&&1==loanClassId){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.资产情况_经营类_固定资产_类别);
					}else if("ASSETS_FIXED_AMOUNT".equals(columnName)&&2==loanClassId){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.资产情况_消费类_固定资产_类别);
					}else if("ASSETS_INVEST_AMOUNT".equals(columnName)){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.资产负债_投资性资产_项目);
					}else if("DEBTS_CONSUME_AMOUNT".equals(columnName)){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.负债情况_消费性负债_来源);
					}else{
						CodeJsonUtil.copyProperty(jo,"itemName","itemNameDisplay");
						CodeJsonUtil.copyProperty(jo,"itemType","itemTypeDisplay");
					}

					//账款结算方式
					if(AppParamsConst.LOAN_ASSETS_ACCOUNT_ITEM.contains(columnName)){
						CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.账款_结算方式);
						CodeJsonUtil.setCodeYNDisplay(jo, "renewDeposit");
					}
				}

			}else{
				jo = null;
			}

			return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
		}catch(Exception e){
			log.error("根据贷款id得到资产负债 项目列表",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);

	}


	/**
	 * 根据贷款id得到资产负债 项目明细
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/removeAssetsItemById", method = RequestMethod.POST)
	public ResponseEntity<String> removeAssetsItemById(HttpServletRequest request,HttpServletResponse response){
		try {
			String reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isEmpty(reqJson)){
				log.error("设备列表，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			JSONObject jsonObject = new JSONObject().fromObject(reqJson);
			Integer id = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;
			String columnName = jsonObject.containsKey("columnName")?jsonObject.getString("columnName"):null;

			if(null!=id&&StringUtils.isNotBlank(columnName)){

				if(AppParamsConst.LOAN_ASSETS_ACCOUNT_ITEM.contains(columnName)){
//					// 更新总额
					LoanAssetsAccountItem accountItem = loanAssetsProvider.getAssetsAccountById(id);
					if(null!=accountItem){
						loanAssetsProvider.updateAssetsAmount(accountItem.getLoanId(), accountItem.getLoanClassId(), accountItem.getCreateUser(), accountItem.getAmount().multiply(new BigDecimal(-1)), columnName);
					}
					loanAssetsProvider.removeAssetsAccountById(id);
				}else if(AppParamsConst.LOAN_ASSETS_AMOUNT_ITEM.contains(columnName)){

					// 更新总额
					LoanAssetsAmountItem amountItem = loanAssetsProvider.getAssetsAmountById(id);
					if(null!=amountItem){
						loanAssetsProvider.updateAssetsAmount(amountItem.getLoanId(), amountItem.getLoanClassId(), amountItem.getCreateUser(), amountItem.getAmount().multiply(new BigDecimal(-1)), columnName);
					}
					loanAssetsProvider.removeAssetsAmountById(id);
				}if(AppParamsConst.LOAN_ASSETS_DEBTS_ITEM.contains(columnName)){
					// 更新总额
					LoanAssetsDebtsItem debtsItem = loanAssetsProvider.getAssetsDebtsById(id);
					if(null!=debtsItem){
						loanAssetsProvider.updateAssetsAmount(debtsItem.getLoanId(), debtsItem.getLoanClassId(), debtsItem.getCreateUser(), debtsItem.getBalanceAmount().multiply(new BigDecimal(-1)), columnName);
					}
					loanAssetsProvider.removeAssetsDebtsById(id);
				}if(AppParamsConst.LOAN_ASSETS_FIXED_ITEM.contains(columnName)){
					// 更新总额
					LoanAssetsFixedItem fixedItem = loanAssetsProvider.getAssetsFixedById(id);
					if(null!=fixedItem){
						loanAssetsProvider.updateAssetsAmount(fixedItem.getLoanId(), fixedItem.getLoanClassId(), fixedItem.getCreateUser(), fixedItem.getAmount().multiply(new BigDecimal(-1)), columnName);
					}
					loanAssetsProvider.removeAssetsFixedById(id);
				}if(AppParamsConst.LOAN_ASSETS_STOCK_ITEM.contains(columnName)){
					// 更新总额
					LoanAssetsStockItem stockItem = loanAssetsProvider.getAssetsStockById(id);
					if(null!=stockItem){
						loanAssetsProvider.updateAssetsAmount(stockItem.getLoanId(), stockItem.getLoanClassId(), stockItem.getCreateUser(), stockItem.getAmount().multiply(new BigDecimal(-1)), columnName);
					}
					loanAssetsProvider.removeAssetsStockById(id);
				}
			}

			return new ResponseEntity<String>(AppJsonResponse.success(SUCCESS), HttpStatus.OK);
		}catch(Exception e){
			log.error("根据贷款id得到资产负债 项目列表",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);

	}

	/**
	 * 属性名 转字段名
	 * */
	public String transVar(String str){
		try {
			StringBuffer sbf = new StringBuffer("");
			char[] ch = str.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				if(ch[i]>='A'&&ch[i]<='Z'){
					sbf.append("_");
				}
				sbf.append(ch[i]);
			}
			return sbf.toString().toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取属性名数组
	 * */
	private String[] getFiledName(Object o) throws Exception{
		Field[] fields=o.getClass().getDeclaredFields();
		String[] fieldNames=new String[fields.length];
		for(int i=0;i<fields.length;i++){
			fieldNames[i]=fields[i].getName();
		}
		return fieldNames;
	}

	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 根据属性名获取属性值
	 * */
	private Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			if(null!=value&&value instanceof Date) {
				SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
				value = df.format(value);
			}else if(null!=value&&value instanceof BigDecimal) {
				value = new BigDecimal(String.valueOf(value)).setScale(0,BigDecimal.ROUND_DOWN);
			}
			return value;
		} catch (Exception e) {
			return "";
		}
	}

}
