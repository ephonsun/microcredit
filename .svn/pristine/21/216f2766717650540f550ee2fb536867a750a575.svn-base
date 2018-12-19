package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.loan.*;
import banger.framework.util.ApiJsonUtil;
import banger.moduleIntf.ILoanAnalysislBusinessAndConsumProvider;
import banger.moduleIntf.ILoanCrossCheckSaleProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.*;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.JsonResultUtil;
import net.sf.json.JSONObject;
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


/**
 * 查询交叉检验销售额主界面
 * @author 熊伟
 */
@RequestMapping("/api")
@Controller
public class AppCrossCheckSaleController extends BaseController {

	@Resource
	ILoanCrossCheckSaleProvider loanCrossCheckSaleProvider;

	@Autowired
	private ILoanAnalysislBusinessAndConsumProvider analysislBusinessAndConsumProvider;

	/**
	 * app端通过贷款id和贷款类别获取销售额检验详情接口
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getCrossCheckSaleByLoanId", method = RequestMethod.POST)
	public ResponseEntity<String> getCrossCheckSaleByLoanId(HttpServletRequest request, HttpServletResponse response) {
		String reqJson = null;
		try {
			reqJson = HttpParseUtil.getJsonStr(request);//传过来的数据必须是Json类型
			if (StringUtils.isBlank(reqJson)) {
				log.error("查询销售额检验详情接口，参数为空"+reqJson);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = null;
			try {
				reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA);
				if (containsKeys != null) //不等于null
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
			} catch (Exception e) {
				log.error("查询销售检验详情接口，json解析出错|" + reqJson, e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			Integer loanId = reqObj.containsKey("loanId") ? reqObj.getInt("loanId") : null;
			LoanCrossCheckSale lccs = loanCrossCheckSaleProvider.getCrossCheckSaleByLoanId(loanId);
			JSONObject resObj = new JSONObject();
			if (lccs != null) {
				resObj = ApiJsonUtil.toJson(lccs, AppParamsConst.PARAMS_RESPONSE_GET_CROSSCHECKSALE_BYLOANID);
			}
			return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询销售额检验详情接口异常" + reqJson, e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}



	/**
	 * app端选择保存销售额检验信息接口
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/saveCrossCheckSale",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> saveCrossCheckSale(HttpServletRequest request, HttpServletResponse response) {
		String reqJson = null;
		try {
			reqJson = HttpParseUtil.getJsonStr(request);
			if (StringUtils.isBlank(reqJson)) {
				log.error("保存销售检验信息接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = null;
			LoanCrossCheckSale lccs = null;
			Integer userId=null;
			try {
				reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA_PRIMARY);

				if (containsKeys == null) {
					lccs = (LoanCrossCheckSale)JSONObject.toBean(reqObj,LoanCrossCheckSale.class);
					userId=reqObj.containsKey("userId")?reqObj.getInt("userId"):0;
				}else{
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("保存销售额检验信息接口，json解析出错|" + reqJson, e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			loanCrossCheckSaleProvider.saveCrossCheckSale(lccs,userId);  //保存接口的处理  将userId进行赋值
			Integer loanId=lccs.getLoanId();
			if(null!=loanId) {
				loanCrossCheckSaleProvider.updateSaleDev(loanId);//修改
				//根据贷款id处理三表数据,另存财务分析详情明细
				analysislBusinessAndConsumProvider.saveAnalysislBusinessOrConsum(loanId);
				return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
			}
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,  "loanId参数不能为空！"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("保存销售额检验信息接口" + reqJson, e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
}




