package banger.action;

import banger.common.BaseController;
import banger.common.login.AccessInfoHolder;
import banger.framework.util.JwtUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.XSSFilterUtil;
import banger.framework.web.servlet.ServletContextHolder;
import banger.framework.web.servlet.ServletUtil;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/24.
 */
public class AppBaseController implements Serializable  {

    // 继承BaseController的都是单例，不能使用全局变量
    protected transient final Log log = LogFactory.getLog(getClass());

    public HttpServletRequest getRequest() {
        return ServletContextHolder.getRequest();
    }
    
    public HttpServletResponse getResponse() {
        return ServletContextHolder.getResponse();
    }

    public String getParameter(String param){
		String str = this.getRequest().getParameter(param);
		if(StringUtil.isNotEmpty(str) && !XSSFilterUtil.isJsonString(str)){
			return XSSFilterUtil.cleanXSS(str);
		}
		return str;
	}

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
        log.error("程序异常:"+e.getMessage(),e);
        String resultErrorJson = AppJsonResponse.fail(CodeEnum.CODE_101);
        ServletUtil.renderText(resultErrorJson,HttpStatus.OK.value());
    }


    public String getRootPath()
    {
        return AccessInfoHolder.getAccessInfo().getRootPath().replaceAll("\\\\", "/");
    }
    
    public Integer getUserId(){
    	String userIdStr = getRequest().getHeader("userId");
    	if(StringUtil.isNotEmpty(userIdStr)){
    		return Integer.parseInt(userIdStr);
    	}
    	return null;
    }
    
    public String getToken(){
		String urlToken = this.getRequest().getParameter("token");
		String headToken = this.getRequest().getHeader("token");
		
		String token = "";
		
		if(StringUtil.isNotEmpty(urlToken)){
			token = urlToken;
		}else if(StringUtil.isNotEmpty(headToken)){
			token = headToken;
		}else{
			Cookie[] cookies = this.getRequest().getCookies();
			if(cookies!=null){
				for(int i = 0; i < cookies.length; i++){
					if("token".equals(cookies[i].getName())){
						token = cookies[i].getValue();
					}
				}
			}
		}
		
		return token;
    }
    
    public void updateTaken(){
    	String token = this.getToken();
    	if(StringUtil.isNotEmpty(token)){
    		String tokenStr = JwtUtil.updateToken(token);
    		this.getResponse().addHeader("token", tokenStr);
    		this.getResponse().setHeader("result", "success");
    	}
    }


    /**
     * 参数同意异常处理，如果为空，或者指定必填项未填，返回结果，如果返回null说明参数正常
     * @param reqJson
     * @param params
     * @return
     */
    public ResponseEntity<String> checkAppParams(String interfaceName, String reqJson, String params){
        if (StringUtils.isBlank(reqJson)) {
            log.error(interfaceName + ":参数为空");
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
        }
        JSONObject reqObj = null;
        try {
            reqObj = JSONObject.fromObject(reqJson);
            String containsKeys = AppJsonUtil.containsKeys(reqObj, params);
            if (containsKeys != null)
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, interfaceName + ":" + containsKeys + "参数不能为空！"), HttpStatus.OK);
        } catch (Exception e) {
            log.error(interfaceName + ":json解析出错|" + reqJson, e);
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
        }
        return null;
    }
}
