package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.enumerate.CodeDictEnum;
import banger.domain.loan.*;
import banger.framework.util.JsonDateValueProcessor;
import banger.framework.web.json.JsonObject;
import banger.moduleIntf.ILoanAssetsProvider;
import banger.moduleIntf.ILoanModule;
import banger.moduleIntf.ILoanSurveryFlowProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanApplyService;
import banger.util.CodeJsonUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
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
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 贷款资产负债
 * @author kangb
 *
 */
@RequestMapping("/api")
@Controller
public class AppLoanSurveryFlowController extends BaseController implements Serializable {

	private static final long serialVersionUID = -5649175717818323257L;

	@Resource
	private ILoanSurveryFlowProvider loanSurveryFlowProvider;



	/**
	 * 根据贷款类型查询调查流程
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getSurVeryFlow", method = RequestMethod.POST)
	public ResponseEntity<String> getAssetsInfoByLoanId(HttpServletRequest request,HttpServletResponse response){
		try {
			String loanId = this.getParameter("loanId");
			if(StringUtils.isEmpty(loanId)){
				log.error("设备列表，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			LoanApplyInfo applyInfo = this.loanSurveryFlowProvider.getApplyInfoById(Integer.valueOf(loanId));
			Integer flowId = null;
			if(null != applyInfo){
				Integer typeId = applyInfo.getLoanTypeId();
				LoanType loanType = this.loanSurveryFlowProvider.getTypeById(Integer.valueOf(typeId));
				if(null != loanType){
					flowId = loanType.getFlowId();
				}
			}
			if(null != flowId && flowId !=0) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("isDel", 0);
				condition.put("flowId", flowId);
				List<LoanFlowStepItem> flowStepList = this.loanSurveryFlowProvider.queryFlowStepItemList(condition);
				JSONObject data = new JSONObject();
				JSONArray arrayStep = new JSONArray();
				for (LoanFlowStepItem flowStep : flowStepList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("flowId", flowStep.getId());
					List<LoanFlowStepContent> stepContents = this.loanSurveryFlowProvider.queryFlowStepContentList(map);
					List<String> contentList = new ArrayList<String>();
					for (LoanFlowStepContent content : stepContents) {
						contentList.add(content.getSubstance());
					}
					JSONObject stepSub = new JSONObject();
					stepSub.put("stepContent", contentList);
					stepSub.put("stepName", flowStep.getStepName());
					arrayStep.add(stepSub);
				}
				return new ResponseEntity<String>(AppJsonResponse.success(arrayStep), HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_129), HttpStatus.OK);
			}
		}catch(Exception e){
			log.error("根据贷款类型查询调查流程",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);

	}







}
