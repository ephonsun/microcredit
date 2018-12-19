package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.customer.CustOptionCollect;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ICustomerOptionProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.ErrorUtil;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhusw on 2017/6/2.
 */
@Controller
@RequestMapping("/api")
public class AppCustomerOptionController extends BaseController {
    public static final String RESULT_CUSTOMER_OPTION_LIST_PARAMS = "id,customerId,optionParam,optionValue,optionDisplay";

    @Resource
    ICustomerOptionProvider customerOptionProvider;

    /**
     * http://127.0.0.1:8080/loan/api/v1/getCustomerCollectOption.html?customerId=1
     * 得到客户收集数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getCustomerCollectOption")
    public ResponseEntity<String> getCustomerCollectOption(HttpServletRequest request, HttpServletResponse response){
        String customerId = this.getParameter("customerId");
        try {
            if(StringUtil.isNotEmpty(customerId)){
                List<CustOptionCollect> optionList = customerOptionProvider.getCustomerOptionById(Integer.parseInt(customerId));
                JSONArray resArray = AppJsonUtil.toJsonArray(optionList,RESULT_CUSTOMER_OPTION_LIST_PARAMS);
                return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
            }else{
                return new ResponseEntity<String>(AppJsonResponse.success(new JSONArray()), HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorUtil.logError("查询客户收集数据接口异常",log,e,request);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 保存客户收集数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/saveCustomerCollectOption")
    public ResponseEntity<String> saveCustomerCollectOption(HttpServletRequest request, HttpServletResponse response){
        String reqJson = null;
        try {
            reqJson= HttpParseUtil.getJsonStr(request);
            if(StringUtils.isBlank(reqJson)){
                log.error("保存客户收集数据接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            try {
                JSONObject reqObj = JSONObject.fromObject(reqJson);
                String resultJson = saveCustomerCollectOption(reqObj);
                return new ResponseEntity<String>(resultJson, HttpStatus.OK);
            } catch (Exception e) {
                log.error("保存客户收集数据接口，json解析出错|"+reqJson,e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("保存客户收集数据接口异常|"+reqJson,e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 保存客户收集信息
     * @param root
     * @return
     */
    private String saveCustomerCollectOption(JSONObject root){
        JSONArray ja = (JSONArray)root.get("data");
        JSONArray result = new JSONArray();
        List<CustOptionCollect> optionList = new ArrayList<CustOptionCollect>();
        for(int i=0;i<ja.size();i++) {
            JSONObject jo = ja.getJSONObject(i);
            String optionParam = jo.getString("optionParam");
            String optionValue = jo.getString("optionValue");
            Integer customerId = jo.getInt("customerId");
            String optionDisplay = jo.getString("optionDisplay");
            CustOptionCollect option = new CustOptionCollect();
            option.setOptionParam(optionParam);
            option.setOptionValue(optionValue);
            option.setCustomerId(customerId);
            option.setOptionDisplay(optionDisplay);
            optionList.add(option);
        }
        customerOptionProvider.saveCustomerOptions(optionList);
        return AppJsonResponse.success();
    }

}
