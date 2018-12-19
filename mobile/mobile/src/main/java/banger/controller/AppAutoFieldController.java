package banger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import banger.common.BaseController;
import banger.constant.AppParamsConst;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;

/**
 * 
 * @author zhusw
 *
 */
@Controller
@RequestMapping("/api")
public class AppAutoFieldController extends BaseController {
	public static final String REQUEST_TEMPLATE_FIELD_PARAMS ="templateId,";				//
	
	@RequestMapping(value = "/v1/getTemplateField")
	public ResponseEntity<String> queryProductListDataByTemplateId(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null;
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询产品列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = null;
			try {
				reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_QUERY_PRODUCT_LIST_DATA);
				if(containsKeys != null)
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
			} catch (Exception e) {
				log.error("查询产品列表接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("查询产品列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	public static void main(String[] args) {
		JSONObject root = new JSONObject();
		root.put("code", "100");
		JSONObject subJsonObject = new JSONObject();
		JSONObject tableJson = new JSONObject();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				tableJson.put("COLUM" + j, j);
			}

			subJsonObject.put("TABLE" + i, tableJson);
		}
		root.put("data", subJsonObject);
		System.out.println(root.toString());
	}
	
}
