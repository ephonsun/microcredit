package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.loan.LoanCrossCheckQuanyiquan;
import banger.framework.util.ApiJsonUtil;
import banger.moduleIntf.ILoanCrossCheckQuanyiquanProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 查询交叉检验权益主界面
 * @author 熊伟
 */
@RequestMapping("/api")
@Controller
public class AppCrossCheckQuanyiquanController extends BaseController {

	@Resource
	ILoanCrossCheckQuanyiquanProvider loanCrossCheckQuanyiquanProvider;

	/**
	 * app端通过贷款id和贷款类别获取权益检验详情接口
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getCrossCheckQuanyiquanByLoanId", method = RequestMethod.POST)
	public ResponseEntity<String> getCrossCheckQuanyiquanByLoanId(HttpServletRequest request, HttpServletResponse response) {
		String reqJson = null;
		try {
			reqJson = HttpParseUtil.getJsonStr(request);//传过来的数据必须是Json类型
			if (StringUtils.isBlank(reqJson)) {
				log.error("查询权益检验详情接口，参数为空"+reqJson);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = null;
			try {
				reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA);
				if (containsKeys != null) //不全等于null
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
			} catch (Exception e) {
				log.error("查询权益检验详情接口，json解析出错|" + reqJson, e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			Integer loanId = reqObj.containsKey("loanId") ? reqObj.getInt("loanId") : null;
			LoanCrossCheckQuanyiquan lccq = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(loanId);
			JSONObject resObj = new JSONObject();
			if (lccq != null) {
				resObj = ApiJsonUtil.toJson(lccq, AppParamsConst.PARAMS_RESPONSE_GET_CROSSCHECKQUANYIQUAN_BYLOANID);
			}
			return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询检验详情接口异常" + reqJson, e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}



	/**
	 * app端选择保存权益检验信息接口
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/saveCrossCheckQuanyiquan",method = RequestMethod.POST)
	public ResponseEntity<String> saveCrossCheckQuanyiquan(HttpServletRequest request, HttpServletResponse response) {
		String reqJson = null;
		try {
			reqJson = HttpParseUtil.getJsonStr(request);
			if (StringUtils.isBlank(reqJson)) {
				log.error("保存权益检验信息接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = null;
			LoanCrossCheckQuanyiquan lccq = null;
			Integer userId=null;
			String initRightDate=null;
			try {
				reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA_PRIMARY);//判断里面的参数不为空

				if (containsKeys == null) {
					lccq = (LoanCrossCheckQuanyiquan)JSONObject.toBean(reqObj,LoanCrossCheckQuanyiquan.class);//Date对时间的封装不同

					userId=reqObj.containsKey("userId")?reqObj.getInt("userId"):0;
					initRightDate=reqObj.containsKey("initRightDate")?reqObj.getString("initRightDate"):null;
					if(initRightDate!=null){
						java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
						Date date =  formatter.parse(initRightDate);
						lccq.setInitRightDate(date);
					}

				}else{
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("保存权益检验信息接口，json解析出错|" + reqJson, e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			loanCrossCheckQuanyiquanProvider.saveCrossCheckQuanyiquan(lccq,userId);  //保存接口的处理  将userId进行赋值
			Integer loanId=lccq.getLoanId();
			if(null!=loanId) {
				loanCrossCheckQuanyiquanProvider.updateQuanyiquanDev(loanId);//修改
				return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
			}
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,  "loanId参数不能为空！"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("保存权益检验信息接口" + reqJson, e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
}




