package banger.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyInfo_Query;
import banger.domain.loan.LoanType;
import banger.domain.system.SysBasicConfig;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IBasicConfigProvider;
import banger.moduleIntf.ILoanSurveryFlowProvider;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.loan.LoanCustomerApplyInfo;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ILoanModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;

/**
 * 客户相关的代款功能
 * @author zhusw
 *
 */
@RequestMapping("/api")
@Controller
public class AppLoanCustomerController extends BaseController {
	private static final long serialVersionUID = -6415795564046474897L;
	private static final String RESULT_CUSTOMER_LOAN_LIST_PARAMS = "loanId,loanTitle,loanProcessType,loanProcessTypeName,loanTypeId,loanClassId,loanTypeName,loanName,loanIdentifyType,loanIdentifyTypeName,loanIdentifyNum,loanIdentifyNumX,loanTelNum,loanTelNumX,loanApplyAmount,createUser,loanRefuseReasonDisplay,loanProcessStateDisplay,loanCollectionStateDisplay,loanMonitorStateDisplay,loanAmountFormat,loanRepayAmount,loanRepayDate,loanRepayAmountFormat,loanMonitorDate,loanMonitorTypeName,submitEnable,refuseEnable,gobackEnable,allotEnable,loanAccountAmount,loanAccountAmountFormat,loanStep";
	
	@Resource
	private ILoanModule loanModule;

	@Autowired
	private IBasicConfigProvider basicConfigProvider;

	@Resource
	private ILoanSurveryFlowProvider loanSurveryFlowProvider;
	
	/**
	 * http://127.0.0.1:8080/loan/api/v1/getCustomerLoanApplyInfoList.html?customerId=186&userId=23
	 * 得到客户贷款列表
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getCustomerLoanApplyInfoList", method = RequestMethod.GET)
	public ResponseEntity<String> getCustomerLoanApplyInfoList(HttpServletRequest request,HttpServletResponse response){
		try {
			String customerId = this.getParameter("customerId");
			String userId = this.getParameter("userId");
			if(StringUtil.isNotEmpty(customerId) && StringUtil.isNotEmpty(userId)){
				List<LoanApplyInfo> applyList = loanModule.getLoanApplyProvider().getLoanApplyInfoListByCustomerId(Integer.parseInt(customerId),Integer.parseInt(userId));
				this.setLoanApplyInfoList(applyList);
				JSONArray resArray = AppJsonUtil.toJsonArray(applyList, RESULT_CUSTOMER_LOAN_LIST_PARAMS);
				return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("查询得到客户贷款列表接口异常:"+e.getMessage(),e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	private void setLoanApplyInfoList(List<LoanApplyInfo> applyList){
		//显示催收贷款(当前日期+催收设置天数>=应还款时间)
		SysBasicConfig cssz = basicConfigProvider.getSysBasicConfigByKey("cfsz");
		Date collectionDate = DateUtil.addDay(DateUtil.getCurrentDate(),cssz.getConfigValue());
		List<LoanType> typeList = loanSurveryFlowProvider.getAllLoanTypeList();
		Map<Integer,LoanType> typeMap = new HashMap<Integer, LoanType>();
		for(LoanType loanType : typeList){
			typeMap.put(loanType.getTypeId(),loanType);
		}
		for(LoanApplyInfo loanApplyInfo : applyList) {
			if(loanApplyInfo.getLoanRepayDate()!=null && collectionDate!=null) {
				if(loanApplyInfo.getLoanRepayDate().compareTo(collectionDate)>0)
					loanApplyInfo.setLoanCollectionState("");
			}
			if(LoanProcessTypeEnum.CLEARANCE.type.equals(loanApplyInfo.getLoanProcessType())){
				loanApplyInfo.setLoanCollectionState("");
				loanApplyInfo.setLoanMonitorState("");
			}
			LoanApplyInfo_Query loanApplyInfoQuery = (LoanApplyInfo_Query)loanApplyInfo;
			if(loanApplyInfoQuery.getLoanTypeId()!=null && typeMap.containsKey(loanApplyInfoQuery.getLoanTypeId())){
				LoanType loanType = typeMap.get(loanApplyInfoQuery.getLoanTypeId());
				if(loanType!=null && loanType.getFlowId()!=null && loanType.getFlowId().intValue()>0){
					loanApplyInfoQuery.setLoanStep(true);			//设置了贷款流程才显示
				}
			}
		}
	}
	
}
