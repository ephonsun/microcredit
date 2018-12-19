package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.StringUtil;
import banger.domain.enumerate.CodeDictEnum;
import banger.domain.enumerate.LoanClassEnum;
import banger.domain.loan.*;
import banger.framework.util.DateUtil;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.CodeJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.JsonResultUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.math.BigInteger;

/**
 * 损益情况
 */
@RequestMapping("/api")
@Controller
public class AppLoanIncomeStatementController extends BaseController {

    @Resource
    private ILoanIncomeStatementProvider loanIncomeStatementProvider;
    @Resource
    private ILoanProfitLossRatioItemProvider loanProfitLossRatioItemProvider;
	@Resource
	private IProfitBizIncomeMonthProvider iProfitBizIncomeMonthProvider;
	@Resource
	private IProfitBizIncomeItemProvider iProfitBizIncomeItemProvider;
	@Resource
	private IProfitConsumIncomeItemProvider iProfitConsumIncomeItemProvider;

	private String BUSINESS_INCOME = "BUSINESS_INCOME_AMOUNT";
	private String HOME_INCOME = "HOME_INCOME_AMOUNT";
	private String OTHER_INCOME = "OTHER_INCOME_AMOUNT";
	private String FATHER_TABLE = "LOAN_PROFIT_LOSS_INFO";

	public static final String RESULT_BIZ_LOANINCOME = "id,loanId,loanClassId,itemName,averageAmount,totalAmount,remark";
	public static final String RESULT_MONTH_LOANINCOME = "id,incomeId,yearVal,monthVal,monthAmount";
	public static final String RESULT_CUSTOMER_LOANINCOME = "id,loanId,loanClassId,itemName,preYearAmount,curYearAmount,monthAvgAmount,totalAmount,remark";
	public static final String SAVE_LOAN_CONSUM_INCOME = "loanId,loanClassId,columnName,itemName,preYearAmount,curYearAmount,monthAvgAmount,totalAmount,createUser,updateUser";
    public static final String SAVE_PROFIT_LOSS_RATIO_ITEM ="loanId,loanClassId,columnName,tableName,productCategory,salePrice,costPrice,saleRatio";

	/**
     * 查询损益主界面
     * @param request
     * @param response
     * @return
	 * @Author 黄意
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/queryLoanProfitLossInfo")
    public ResponseEntity<String> queryLoanProfitLossInfo(HttpServletRequest request, HttpServletResponse response){
        try {
            String loanId=this.getParameter("loanId");
            String loanClassId=this.getParameter("loanClassId");
            if(StringUtil.isNotEmpty(loanId)&&StringUtil.isNotEmpty(loanClassId)){
                String result = getProfitLossJsonString(Integer.parseInt(loanId),Integer.parseInt(loanClassId));
                return new ResponseEntity<String>(result, HttpStatus.OK);
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        }catch (Exception e){

            log.error("查询损益表主界面接口异常:"+e.getMessage(),e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    private String getProfitLossJsonString(Integer loanId,Integer loanClassId){
        JSONObject root = new JSONObject();
        JSONObject data = new JSONObject();
        String start_Year_Month;
        String end_Year_Month;
        LoanProfitLossInfo loanProfitLossInfo=loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(loanId);
        if(loanProfitLossInfo!=null) {//有数据时
            if (LoanClassEnum.LOAN_BUSINESS_CLASS.equalValue(loanClassId)) {      //经营类
                data.put("BUSINESS_INCOME_AMOUNT",loanProfitLossInfo.getBusinessIncomeAmount());
                data.put("GROSS_PROFIT_RATIO",loanProfitLossInfo.getGrossProfitRatio());
                data.put("OTHER_INCOME_AMOUNT",loanProfitLossInfo.getOtherIncomeAmount());
                data.put("FIXED_COST_DEFRAY_AMOUNT",loanProfitLossInfo.getFixedCostDefrayAmount());
                data.put("TEX_DEFRAY_AMOUNT",loanProfitLossInfo.getTexDefrayAmount());
                data.put("OTHER_DEFRAY_AMOUNT",loanProfitLossInfo.getOtherDefrayAmount());
                start_Year_Month=loanProfitLossInfo.getYearStart()+"-"+loanProfitLossInfo.getMonthStart();
                end_Year_Month= loanProfitLossInfo.getYearEnd()+"-"+loanProfitLossInfo.getMonthEnd();
                data.put("start_Year_Month",DateUtil.format(DateUtil.parser(start_Year_Month,"yyyy-MM"),"yyyy-MM"));
                data.put("end_Year_Month",DateUtil.format(DateUtil.parser(end_Year_Month,"yyyy-MM"),"yyyy-MM"));
            } else if (LoanClassEnum.LOAN_CONSUMER_CLASS.equalValue(loanClassId)) {//消费类
                data.put("HOME_INCOME_AMOUNT",loanProfitLossInfo.getHomeIncomeAmount());
                data.put("OTHER_INCOME_AMOUNT",loanProfitLossInfo.getOtherIncomeAmount());
                data.put("FIXED_DEFRAY_AMOUNT",loanProfitLossInfo.getFixedDefrayAmount());
                data.put("TEX_PERSONAL_AMOUNT",loanProfitLossInfo.getTexPersonalAmount());
                data.put("OTHER_DEFRAY_AMOUNT",loanProfitLossInfo.getOtherDefrayAmount());
                start_Year_Month=loanProfitLossInfo.getYearStart()+"-"+loanProfitLossInfo.getMonthStart();
                end_Year_Month= loanProfitLossInfo.getYearEnd()+"-"+loanProfitLossInfo.getMonthEnd();
                data.put("start_Year_Month",DateUtil.format(DateUtil.parser(start_Year_Month,"yyyy-MM"),"yyyy-MM"));
                data.put("end_Year_Month",DateUtil.format(DateUtil.parser(end_Year_Month,"yyyy-MM"),"yyyy-MM"));
            }
        }
        /*
        else{//无数据时
            if (LoanClassEnum.LOAN_BUSINESS_CLASS.equalValue(loanClassId)) {      //经营类
                data.put("BUSINESS_INCOME_AMOUNT","");
                data.put("GROSS_PROFIT_RATIO","");
                data.put("OTHER_INCOME_AMOUNT","");
                data.put("FIXED_COST_DEFRAY_AMOUNT","");
                data.put("TEX_DEFRAY_AMOUNT","");
                data.put("OTHER_DEFRAY_AMOUNT","");
                data.put("start_Year_Month","");
                data.put("end_Year_Month","");
            } else if (LoanClassEnum.LOAN_CONSUMER_CLASS.equalValue(loanClassId)) {//消费类
                data.put("HOME_INCOME_AMOUNT","");
                data.put("OTHER_INCOME_AMOUNT","");
                data.put("FIXED_DEFRAY_AMOUNT","");
                data.put("TEX_PERSONAL_AMOUNT","");
                data.put("OTHER_DEFRAY_AMOUNT","");
                data.put("start_Year_Month","");
                data.put("end_Year_Month","");
            }
        }
        */
        root.put("data",data);
        JsonResultUtil.setSuccess(root);
        return root.toString();
    }

	/**
	 * 更新月份区间
	 *
	 * @param request
	 * @param response
	 * @return
	 * @Author 黄意
	 */

	@NoLoginInterceptor
	@RequestMapping("/v1/updateInterval")
	public ResponseEntity<String> updateInterval(HttpServletRequest request, HttpServletResponse response) {
		String reqJson = null;
		try {
			reqJson = HttpParseUtil.getJsonStr(request);
			if (StringUtils.isBlank(reqJson)) {
				log.error("更改月份区间接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			String startYearMonth = "";
			String endYearMonth = "";
			String loanId = "";
			String userId = "";
			String loanClassId = "";
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, "startYearMonth,endYearMonth,loanId,userId,loanClassId");
				if (containsKeys == null) {
					startYearMonth = reqObj.containsKey("startYearMonth") ? reqObj.getString("startYearMonth") : "";
					endYearMonth = reqObj.containsKey("endYearMonth") ? reqObj.getString("endYearMonth") : "";
					loanId = reqObj.containsKey("loanId") ? reqObj.getString("loanId") : "";
					userId = reqObj.containsKey("userId") ? reqObj.getString("userId") : "";
					loanClassId = reqObj.containsKey("loanClassId") ? reqObj.getString("loanClassId") : "";
				} else {
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("更新月份区间申请接口，json解析出错|" + reqJson, e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			if (StringUtil.isNotEmpty(startYearMonth) && StringUtil.isNotEmpty(endYearMonth) && StringUtil.isNotEmpty(loanId) && StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(loanClassId)) {
				loanIncomeStatementProvider.updateInterval(startYearMonth,endYearMonth,loanId,loanClassId,userId);
				String result = getProfitLossJsonString(Integer.parseInt(loanId),Integer.parseInt(loanClassId));
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error("更新月份区间接口异常:" + e.getMessage(), e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

    /**
     * 查询毛利率列表
     *
     * @param request
     * @param response
     * @return
	 * @Author 黄意
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/queryGrossProfitMarginList")
    public ResponseEntity<String> queryGrossProfitMarginList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String columnName = this.getParameter("columnName");
            String loanId = this.getParameter("loanId");
            if (StringUtil.isNotEmpty(columnName) && StringUtil.isNotEmpty(loanId)) {
                List<LoanProfitLossRatioItem> list = loanProfitLossRatioItemProvider.queryGrossProfitMarginList(Integer.parseInt(loanId), columnName);
                JSONArray resArray = AppJsonUtil.toJsonArray(list, "id,productCategory,saleRatio,weightingProfitRatio");
                return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("查询毛利率列表接口异常:" + e.getMessage(), e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }
	/**
	 * 获取毛利率明细
	 *
	 * @param request
	 * @param response
	 * @return
	 * @Author 黄意
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/queryGrossProfitMargin")
	public ResponseEntity<String> queryGrossProfitMargin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = this.getParameter("id");
			if (StringUtil.isNotEmpty(id)) {
				LoanProfitLossRatioItem loanProfitLossRatioItem= loanProfitLossRatioItemProvider.getProfitLossRatioItemById(Integer.parseInt(id));
				JSONObject resObj = AppJsonUtil.toJson(loanProfitLossRatioItem, "productCategory,salePrice,costPrice,crossProfit,profitRatio,saleRatio,weightingProfitRatio,remark");
				return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("查询毛利率明细接口异常:" + e.getMessage(), e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

   /**
    * @Author: yangdw
    * @params:  * @param null
    * @Description:获取经营类贷款收入支出明细列表
    * @Date: 18:31 2017/8/23
    */
   @NoLoginInterceptor
   @RequestMapping("/v1/queryProfitBizIncomeItemList")
   public ResponseEntity<String> queryProfitBizIncomeItemList(HttpServletRequest request, HttpServletResponse response){
	   try{

		   String loanId = this.getParameter("loanId");
		   String columnName = this.getParameter("columnName");
           log.info("移动端开始调取经营类收入和支出列表接口,参数loanId:------" + loanId+"columnName:"+columnName);
           if (StringUtil.isNotEmpty(loanId)&&StringUtil.isNotEmpty(columnName)) {
			   Integer loanid = Integer.valueOf(loanId);
			   //获取经营类营业收入列表
			   Map<String, Object> map = new HashedMap();
			   map.put("columnName", columnName);
			   map.put("loanId", loanid);
			   map.put("loanClassId", 1);
			   List<LoanProfitBizIncomeItem> list = iProfitBizIncomeItemProvider.queryProfitBizIncomeItemList(map);
               JSONArray resArray = AppJsonUtil.toJsonArray(list, RESULT_BIZ_LOANINCOME);
			   if (list.size() > 0) {
				   listCodeToName(resArray, list, 1);
			   }
			   return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
           } else {
               return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
           }
       }catch (Exception e){
           log.error("移动端调取经营类收入和支出列表接口异常:"+e.getMessage(),e);
		   e.printStackTrace();
		   return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	   }
   }

   /**
    * @Author: yangdw
    * @params:  * @param null
    * @Description:获取经营类贷款收入支出明细详情
    * @Date: 14:37 2017/8/25
    */
   @NoLoginInterceptor
   @RequestMapping("/v1/queryProfitBizIncomeMonthList")
   public ResponseEntity<String> queryProfitBizIncomeMonthList(HttpServletRequest request, HttpServletResponse response){
	   try{

		   String itemId = this.getParameter("itemId");
		   log.info("移动端开始调取获取经营类贷款收入支出明细详情接口,参数itemId:------" + itemId);
		   if (StringUtil.isNotEmpty(itemId)) {
			   Integer id = Integer.valueOf(itemId);
			   //获取经营类营业收入详情
			   Map<String, Object> map = new HashedMap();
			   map.put("incomeId", id);
			   LoanProfitBizIncomeItem item = iProfitBizIncomeItemProvider.getProfitBizIncomeItemById(id);
			   List<LoanProfitBizIncomeMonth> monthList = iProfitBizIncomeMonthProvider.queryProfitBizIncomeMonthList(map);
			   JSONArray resArray = AppJsonUtil.toJsonArray(monthList, RESULT_MONTH_LOANINCOME);
			   JSONObject jsonObject = AppJsonUtil.toJson(item, RESULT_BIZ_LOANINCOME);
			   codeToName(jsonObject, item, 1);

			   JSONObject rjo = new JSONObject();
			   rjo.put("item", jsonObject);
			   rjo.put("months", resArray);
			   return new ResponseEntity<String>(AppJsonResponse.success(rjo), HttpStatus.OK);
		   } else {
			   return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
		   }
	   }catch (Exception e){
		   log.error("移动端开始调取获取经营类贷款收入支出明细详情接口异常:"+e.getMessage(),e);
		   e.printStackTrace();
		   return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	   }
   }

   /**
    * @Author: yangdw
    * @params:  * @param null
    * @Description:保存或者修改营业收入或者支出明细以及月份项根据id判断
    * @Date: 9:02 2017/8/24
    */

	@NoLoginInterceptor
	@RequestMapping(value = "/v1/saveProfitBizIncomeItem", method = RequestMethod.POST)
	public ResponseEntity<String> saveProfitBizIncomeItem(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null;
		try {
			reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("保存营业收入或者支出明细以及月份项接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				LoanProfitBizIncomeItem loanProfitBizIncomeItem = (LoanProfitBizIncomeItem)JSONObject.toBean(reqObj,LoanProfitBizIncomeItem.class);
				int userId = reqObj.getInt("userId");
				JSONArray ja = reqObj.getJSONArray("months");
				List<LoanProfitBizIncomeMonth> monthList = new ArrayList<LoanProfitBizIncomeMonth>();
				for(int i=0;i<ja.size();i++){
					JSONObject jo = ja.getJSONObject(i);
					LoanProfitBizIncomeMonth month = (LoanProfitBizIncomeMonth)JSONObject.toBean(jo,LoanProfitBizIncomeMonth.class);
					monthList.add(month);
				}
				loanProfitBizIncomeItem.setTableName(FATHER_TABLE);
				iProfitBizIncomeItemProvider.saveProfitBizIncomeItem(loanProfitBizIncomeItem,monthList,userId);

				JSONObject rjo = new JSONObject();
				rjo.put("itemId", loanProfitBizIncomeItem.getId());
				return new ResponseEntity<String>(AppJsonResponse.success(rjo), HttpStatus.OK);
			}catch (Exception e){
				log.error("保存营业收入明细或者支出以及月份项接口异常参数解析异常|"+reqJson,e);
				e.printStackTrace();
			}
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);

		} catch (Exception e) {
			log.error("保存营业收入或者支出明细以及月份项接口异常|"+reqJson,e);
			e.printStackTrace();
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:删除营业收入支出明细以及月份项
	 * @Date: 10:58 2017/8/24
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/deteleProfitBizIncomeItem")
	public ResponseEntity<String> deteleProfitBizIncomeItem(HttpServletRequest request,HttpServletResponse response) {
		try{

			String itemId = this.getParameter("itemId");
			String userId = this.getParameter("userId");
			log.info("移动端开始调取删除营业收入明细以及月份项,参数itemId:------" + itemId);
			if (StringUtil.isNotEmpty(itemId)) {
				Integer id = Integer.valueOf(itemId);
				LoanProfitBizIncomeItem itme = iProfitBizIncomeItemProvider.getProfitBizIncomeItemById(id);
				iProfitBizIncomeItemProvider.deleteProfitBizIncomeItemById(id);
				iProfitBizIncomeMonthProvider.deleteProfitBizIncomeItemMonthByItemId(id);
				//更新主表的收入总计
				loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(itme.getLoanId(), 1, Integer.valueOf(userId));

				return new ResponseEntity<String>(AppJsonResponse.success("删除成功"), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
		}catch (Exception e){
			log.error("移动端调取删除营业收入明细以及月份项接口异常:"+e.getMessage(),e);
			e.printStackTrace();
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
		}
	}
	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:消费类收入和支出列表接口
	 * @Date: 14:12 2017/8/24
	 */

	@NoLoginInterceptor
	@RequestMapping("/v1/queryProfitConsumIncomeItemList")
	public ResponseEntity<String> queryProfitConsumIncomeItemList(HttpServletRequest request, HttpServletResponse response){
		try{

			String loanId = this.getParameter("loanId");
			String columnName = this.getParameter("columnName");
			log.info("移动端开始调取消费类收入和支出列表接口,参数loanId:------" + loanId+"columnName:"+columnName);
			if (StringUtil.isNotEmpty(loanId)) {
				Integer loanid = Integer.valueOf(loanId);
				//获取经营类营业收入列表
				Map<String, Object> map = new HashedMap();
				map.put("columnName", columnName);
				map.put("loanId", loanid);
				map.put("loanClassId", 2);
				List<LoanProfitConsumIncomeItem> list = iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map);
				JSONArray resArray = AppJsonUtil.toJsonArray(list, RESULT_CUSTOMER_LOANINCOME);
				if (list.size() > 0) {
					listCodeToName(resArray, list, 2);
				}
				return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
		}catch (Exception e){
			log.error("移动端调取经营类收入和支出列表接口异常:"+e.getMessage(),e);
			e.printStackTrace();
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
		}
	}


	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:获取消费类贷款收入支出
	 * @Date: 14:37 2017/8/25
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/getProfitConsumIncomeItemById")
	public ResponseEntity<String> getProfitConsumIncomeItemById(HttpServletRequest request, HttpServletResponse response){
		try{

			String itemId = this.getParameter("itemId");
			log.info("移动端开始调取获取获取消费类贷款收入支出详情接口,参数itemId:------" + itemId);
			if (StringUtil.isNotEmpty(itemId)) {
				Integer id = Integer.valueOf(itemId);
				//获取消费类贷款收入支出详情
				LoanProfitConsumIncomeItem profitConsumIncomeItem = iProfitConsumIncomeItemProvider.getProfitConsumIncomeItemById(id);
				JSONObject jsonObject = AppJsonUtil.toJson(profitConsumIncomeItem, RESULT_CUSTOMER_LOANINCOME);
				codeToName(jsonObject, profitConsumIncomeItem, 2);
				return new ResponseEntity<String>(AppJsonResponse.success(jsonObject), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
		}catch (Exception e){
			log.error("移动端开始调取获取获取消费类贷款收入支出详情接口异常:"+e.getMessage(),e);
			e.printStackTrace();
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
		}
	}
	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:保存或者修改消费类收入或者支出明细根据id判断
	 * @Date: 14:19 2017/8/24
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/saveLoanProfitConsumIncomeItem", method = RequestMethod.POST)
	public ResponseEntity<String> saveLoanProfitConsumIncomeItem(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null;
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("保存或者修改消费类收入或者支出明细接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			LoanProfitConsumIncomeItem incomeItem = null;
			boolean insertOrUpd = false;
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, SAVE_LOAN_CONSUM_INCOME);
				if(containsKeys == null){
					if(reqObj.containsKey("id"))
						insertOrUpd = true;
					incomeItem = (LoanProfitConsumIncomeItem) JSONObject.toBean(reqObj,LoanProfitConsumIncomeItem.class);
				}else {
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("保存或者修改消费类收入或者支出明细接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			if(insertOrUpd){
				//更新
				iProfitConsumIncomeItemProvider.updateProfitConsumIncomeItem(incomeItem,incomeItem.getUpdateUser());

			}else{
				//添加
				iProfitConsumIncomeItemProvider.insertProfitConsumIncomeItem(incomeItem,incomeItem.getCreateUser());
			}
			//更新主表的收入总计
			loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(incomeItem.getLoanId(), 2, incomeItem.getUpdateUser());

			JSONObject rjo = new JSONObject();
			rjo.put("itemId", incomeItem.getId());
			return new ResponseEntity<String>(AppJsonResponse.success(rjo), HttpStatus.OK);
		} catch (Exception e) {
			log.error("存或者修改消费类收入或者支出明细接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:移动端删除消费收入支出明细
	 * @Date: 15:27 2017/8/24
	 */

	@NoLoginInterceptor
	@RequestMapping(value = "/v1/deleteProfitConsumIncomeItem", method = RequestMethod.POST)
	public ResponseEntity<String> deleteProfitConsumIncomeItem(HttpServletRequest request,HttpServletResponse response) {
		try{

			String itemId = this.getParameter("itemId");
			String userId = this.getParameter("userId");
			log.info("移动端开始调取删除消费收入支出明细,参数itemId:------" + itemId + "loginid:" + userId);
			if (StringUtil.isNotEmpty(itemId) && StringUtil.isNotEmpty(userId)) {
				Integer id = Integer.valueOf(itemId);
				LoanProfitConsumIncomeItem itme = iProfitConsumIncomeItemProvider.getProfitConsumIncomeItemById(id);
				iProfitConsumIncomeItemProvider.deleteProfitConsumIncomeItemById(id);
				//更新主表的收入总计
				loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(itme.getLoanId(), 2, Integer.valueOf(userId));

				return new ResponseEntity<String>(AppJsonResponse.success("删除成功"), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
		}catch (Exception e){
			log.error("移动端开始调取删除消费收入支出明细接口异常:"+e.getMessage(),e);
			e.printStackTrace();
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
		}
	}
    /**
     * 根据id删除毛利率条目
     *
     * @param request
     * @param response
     * @return
	 * @Author 黄意
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/deleteProfitLossRatioItemById")
    public ResponseEntity<String> deleteProfitLossRatioItemById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = this.getParameter("id");
            String userId = this.getParameter("userId");
            if (StringUtil.isNotEmpty(id)) {
                LoanProfitLossRatioItem loanProfitLossRatioItem = loanProfitLossRatioItemProvider.getProfitLossRatioItemById(Integer.parseInt(id));
                if (loanProfitLossRatioItem != null) {
                    //毛利率
                    BigDecimal grossProfitRatio = loanIncomeStatementProvider.getProfitLossInfoByLoanId(loanProfitLossRatioItem.getLoanId()).getGrossProfitRatio();
                    //删除的加权毛利率
                    BigDecimal profitRatio = loanProfitLossRatioItem.getWeightingProfitRatio();
                    //剩余毛利率
                    BigDecimal bigDecimal = OperationUtil.minus(grossProfitRatio, profitRatio);
                    //更新毛利率
                    if (bigDecimal != null && StringUtil.isNotEmpty(userId)) {
						LoanProfitLossInfo loanProfitLossInfo=new LoanProfitLossInfo();
						loanProfitLossInfo.setLoanId(loanProfitLossRatioItem.getLoanId());
						loanProfitLossInfo.setGrossProfitRatio(bigDecimal);
						loanIncomeStatementProvider.updateProfitLossInfoByLoanId(loanProfitLossInfo,Integer.parseInt(userId));
                    }
                    loanProfitLossRatioItemProvider.deleteProfitLossRatioItemById(Integer.parseInt(id));
                    return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
                } else {//记录不存在
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_119), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }

        } catch (Exception e) {
            log.error("删除毛利率接口异常:" + e.getMessage(), e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 新增或编辑毛利率明细接口
     *
     * @param request
     * @param response
     * @return
	 * @Author 黄意
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/saveProfitLossRatioItem")
    public ResponseEntity<String> saveProfitLossRatioItem(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoanProfitLossRatioItem loanProfitLossRatioItem = null;
            String reqJson = "";
            String userId = "";
            String remark = "";
			boolean insertOrUpd = false;
            try {
                reqJson = HttpParseUtil.getJsonStr(request);
                JSONObject reqObj = JSONObject.fromObject(reqJson);
                String containsKeys = AppJsonUtil.containsKeys(reqObj, SAVE_PROFIT_LOSS_RATIO_ITEM);
                if (containsKeys == null) {
                	Integer id=reqObj.containsKey("id")?reqObj.getInt("id"):null;
                	if(id!=null&&id.intValue()>0){
                		insertOrUpd=true;
					}
                    userId = reqObj.containsKey("userId") ? reqObj.getString("userId") : "";
                    remark = reqObj.containsKey("remark") ? reqObj.getString("remark") : "";
                    loanProfitLossRatioItem = (LoanProfitLossRatioItem) JSONObject.toBean(reqObj, LoanProfitLossRatioItem.class);
                    loanProfitLossRatioItem.setRemark(remark);
                    if (StringUtil.isNullOrEmpty(userId)) {
                        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, "userId参数不能为空！"), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
                }

            } catch (Exception e) {
                log.error("新增或编辑毛利率接口，json解析出错|" + reqJson, e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }

			if(insertOrUpd){//更新
				loanProfitLossRatioItemProvider.updateProfitLossRatioItem(loanProfitLossRatioItem,Integer.parseInt(userId));

			}else{//新增
				loanProfitLossRatioItemProvider.insertProfitLossRatioItem(loanProfitLossRatioItem,Integer.parseInt(userId));
			}

            return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("新增或编辑毛利率接口异常 error:" + e.getMessage(), e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }



	private Object codeToName(JSONObject jo,Object object, int i){
		if (i == 1) {
			LoanProfitBizIncomeItem item = (LoanProfitBizIncomeItem) object;
			//经营收入
			if ("BUSINESS_INCOME_AMOUNT".equals(item.getColumnName()))
				CodeJsonUtil.copyProperty(jo, "itemName", "itemNameDisplay");
			else if ("OTHER_INCOME_AMOUNT".equals(item.getColumnName())) {
				//其他收入
				CodeJsonUtil.setCodeDisplay(jo,CodeDictEnum.损益情况_其他收入_项目);
			} else if ("FIXED_COST_DEFRAY_AMOUNT".equals(item.getColumnName())) {
				//固定成本支出
				CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.损益情况_经营类_固定成本支出_项目);
			}else if ("TEX_DEFRAY_AMOUNT".equals(item.getColumnName())) {
				//所得税支出
				CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.损益情况_所得税支出_项目);
			}else if ("OTHER_DEFRAY_AMOUNT".equals(item.getColumnName())) {
				//其他支出
				CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.损益情况_其他支出_项目);
			}
		} else if (i == 2) {
			LoanProfitConsumIncomeItem item = (LoanProfitConsumIncomeItem) object;
			if ("HOME_INCOME_AMOUNT".equals(item.getColumnName())) {
				//家庭收入
				CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.损益情况_家庭收入_项目);
			} else if ("OTHER_INCOME_AMOUNT".equals(item.getColumnName())) {
				//其他收入
				CodeJsonUtil.copyProperty(jo, "itemName", "itemNameDisplay");
			} else if ("FIXED_DEFRAY_AMOUNT".equals(item.getColumnName())) {
				//固定支出
				CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.损益情况_消费类_固定支出_项目);
			}else if ("TEX_PERSONAL_AMOUNT".equals(item.getColumnName())) {
				//个人所得所支出
				CodeJsonUtil.setCodeDisplay(jo, CodeDictEnum.损益情况_所得税支出_项目);
			}else if ("OTHER_DEFRAY_AMOUNT".equals(item.getColumnName())) {
				//其他支出
				CodeJsonUtil.copyProperty(jo, "itemName", "itemNameDisplay");
			}
		}
		return null;
	}
	private void listCodeToName(JSONArray ja,List list, int i){
		if (i == 1) {
			List<LoanProfitBizIncomeItem> itemList = (List<LoanProfitBizIncomeItem>)(Object)list;
			//经营收入
			if ("BUSINESS_INCOME_AMOUNT".equals(itemList.get(0).getColumnName()))
				CodeJsonUtil.copyProperty(ja, "itemName", "itemNameDisplay");
			else if ("OTHER_INCOME_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//其他收入
				CodeJsonUtil.setCodeDisplay(ja,CodeDictEnum.损益情况_其他收入_项目);
			} else if ("FIXED_COST_DEFRAY_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//固定成本支出
				CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.损益情况_经营类_固定成本支出_项目);
			}else if ("TEX_DEFRAY_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//所得税支出
				CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.损益情况_所得税支出_项目);
			}else if ("OTHER_DEFRAY_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//其他支出
				CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.损益情况_其他支出_项目);
			}
		} else if (i == 2) {
			List<LoanProfitConsumIncomeItem> itemList = (List<LoanProfitConsumIncomeItem>)(Object)list;
			if ("HOME_INCOME_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//家庭收入
				CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.损益情况_家庭收入_项目);
			} else if ("OTHER_INCOME_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//其他收入
				CodeJsonUtil.copyProperty(ja, "itemName", "itemNameDisplay");
			} else if ("FIXED_DEFRAY_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//固定支出
				CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.损益情况_消费类_固定支出_项目);
			}else if ("TEX_PERSONAL_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//个人所得所支出
				CodeJsonUtil.setCodeDisplay(ja, CodeDictEnum.损益情况_所得税支出_项目);
			}else if ("OTHER_DEFRAY_AMOUNT".equals(itemList.get(0).getColumnName())) {
				//其他支出
				CodeJsonUtil.copyProperty(ja, "itemName", "itemNameDisplay");
			}
		}
	}
}
