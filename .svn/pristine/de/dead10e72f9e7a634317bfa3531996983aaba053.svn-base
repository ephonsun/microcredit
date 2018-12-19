package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;

import banger.domain.loan.*;
import banger.framework.util.ApiJsonUtil;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.JsonResultUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 查询交叉检验主界面
 *
 */
@RequestMapping("/api")
@Controller
public class AppCrossCheckController extends BaseController {


	@Resource
	ILoanCrossCheckQuanyiquanProvider loanCrossCheckQuanyiquanProvider;
	@Resource
    ILoanCrossCheckIncomeProvider loanCrossCheckIncomeProvider;
	@Resource
    ILoanCrossCheckSaleProvider loanCrossCheckSaleProvider;
	@Resource
    ILoanCrossCheckGrossProfitProvider laonCrossCheckGrossProfitProvider;
	@Resource
    ILoanCrossCheckNetProfitProvider loanCrossCheckNetProfitProvider;



	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getCrossCheck")
	public ResponseEntity<String> getCrossCheck(HttpServletRequest request, HttpServletResponse response) {
		try {
			String loanId = this.getParameter("loanId");
			String loanClassId = this.getParameter("loanClassId");
			if (StringUtils.isNotBlank(loanId) && StringUtils.isNotBlank(loanClassId)) {
				String result = getCrossCheckString(Integer.parseInt(loanId), Integer.parseInt(loanClassId));
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
		} catch (Exception e) {

			log.error("查交叉检验表主界面接口异常:" + e.getMessage(), e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	private String getCrossCheckString(Integer loanId, Integer loanClassId) {
		JSONObject root = new JSONObject();
		JSONObject data = new JSONObject();


		if(loanClassId==1){//经营类
            setCrossCheckQuanyiquan(loanId,data);
			setCrossCheckSaleService(loanId,data);
			setCrossCheckGrossProfitService(loanId,data);
			setCrossCheckNetProfitService(loanId,data);

        }else if(loanClassId==2){ //消费类
            setCrossCheckQuanyiquan(loanId,data);
            setCrossCheckIncome(loanId,data);
        }
		root.put("data", data);
		JsonResultUtil.setSuccess(root);
		return root.toString();

	}

	//查询交叉检验权益表
	private void setCrossCheckQuanyiquan(Integer loanId,JSONObject data){
		LoanCrossCheckQuanyiquan lccq = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(loanId);
		JSONObject jo = new JSONObject();
		if(lccq!=null){
			jo = ApiJsonUtil.toJson(lccq, "deservedRight,actualRight,deviation,deviationRatio");
		}
		data.put("LOAN_CROSS_CHECK_QUANYIQUAN",jo);
	}

	//查询交叉检验收入表
	private void setCrossCheckIncome(Integer loanId,JSONObject data){
		LoanCrossCheckIncome lcci = loanCrossCheckIncomeProvider.getCrossCheckIncomeByLoanId(loanId);
		JSONObject jo = new JSONObject();
		if(lcci!=null){
			jo = ApiJsonUtil.toJson(lcci, "wageFlow,wageFlowDeviation,incomeCert,incomeCertDeviation,personTax,personTaxDeviation,otherIncome,otherIncomeDeviation");

		}
		data.put("LOAN_CROSS_CHECK_INCOME",jo);
	}

	//查询交叉检验销售表
	private void setCrossCheckSaleService(Integer loanId,JSONObject data){
		LoanCrossCheckSale lccs = loanCrossCheckSaleProvider.getCrossCheckSaleByLoanId(loanId);
		JSONObject jo = new JSONObject();
		if(lccs!=null){
			jo = ApiJsonUtil.toJson(lccs, "checkSaleAmount1,deviationRatio1,checkSaleAmount2,deviationRatio2,checkSaleAmount3,deviationRatio3");
		}
		data.put("LOAN_CROSS_CHECK_SALE",jo);
	}

	//查询交叉检验毛利表
	private void setCrossCheckGrossProfitService(Integer loanId,JSONObject data){
		LoanCrossCheckGrossProfit lccgp = laonCrossCheckGrossProfitProvider.getCrossCheckGrossProfitByLoanId(loanId);
		JSONObject jo = new JSONObject();
		if(lccgp!=null){
			jo = ApiJsonUtil.toJson(lccgp, "checkGrossProfitRatio1,deviationRatio1,checkGrossProfitRatio2,deviationRatio2,checkGrossProfitRatio3,deviationRatio3");

		}
		data.put("LOAN_CROSS_CHECK_GROSS_PROFIT",jo);
	}

	//查询交叉检验净利表
	private void setCrossCheckNetProfitService(Integer loanId,JSONObject data){
		LoanCrossCheckNetProfit lccnp = loanCrossCheckNetProfitProvider.getCrossCheckNetProfitByLoanId(loanId);
		JSONObject jo = new JSONObject();
		if(lccnp!=null){
			jo = ApiJsonUtil.toJson(lccnp, "checkNetProfitRatio1,deviationRatio1,checkNetProfitRatio2,deviationRatio2,checkNetProfitRatio3,deviationRatio3");
		}
		data.put("LOAN_CROSS_CHECK_NET_PROFIT",jo);
	}

}




