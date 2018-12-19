/*
 * xuhj Inc.
 * Copyright (c) 2012-2100 All Rights Reserved.
 * Author     :徐红军
 * Create Date: 2012-12-27
 */
package banger.common;


import banger.common.login.AccessInfoHolder;
import banger.domain.permission.IFuncPermit;
import banger.domain.permission.ILoginInfo;
import banger.framework.pagesize.IPageSize;
import banger.framework.reader.IPropertyReader;
import banger.framework.reader.Reader;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.framework.util.XSSFilterUtil;
import banger.framework.web.dojo.IConvert;
import banger.framework.web.layout.ILayoutLoader;
import banger.framework.web.servlet.ServletContextHolder;
import banger.framework.web.servlet.ServletUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;

public abstract class BaseController implements Serializable {


	public static final String ERROR = "error";
	public static final String SUCCESS = "SUCCESS";

    private static final long serialVersionUID = -2521026016299139301L;

	// 继承BaseController的都是单例，不能使用全局变量
    protected transient final Log log = LogFactory.getLog(getClass());
    
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception e){
		StringBuilder sb = new StringBuilder();
		for(StackTraceElement traceElement : e.getStackTrace()){
			sb.append(traceElement.toString());
			sb.append("\r\n");
		}

		log.error("程序异常:"+e.getMessage(),e);

		if (ServletUtil.isAjax(request)){
			ServletUtil.renderText("错误详情："+e.getMessage()+"\r\n"+sb.toString());
		}else{
			request.setAttribute("errorMsg", sb.toString());
		}
    }
    
	/**
	 * 获得注入的bean对象
	 * @param name
	 * @return
	 */
	public Object getBean(String name){

		WebApplicationContext container  = WebApplicationContextUtils.getWebApplicationContext(ServletContextHolder.getSession().getServletContext());
		if(container.containsBean(name)){
			return container.getBean(name);
		}
		return null;
	}
	
    /**
     * 得到自动登入
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String getAutoLogin() throws UnsupportedEncodingException
    {
		HttpServletRequest request = ServletContextHolder.getRequest();
    	if(request.getParameterMap().containsKey("autoLogin")){
        	if(request.getParameterMap().containsKey("account")){
        		String account = URLDecoder.decode(this.getParameter("account"),"UTF-8");
        		return "autoLogin=true&account=\"+encodeURI(encodeURI('"+account+"'))+\"&";
        	}
    	}
    	return "";
    }
	


	public String getRootPath()
	{
		return AccessInfoHolder.getAccessInfo().getRootPath().replaceAll("\\\\", "/");
	}
	
	/**
	 * 判断是否是客户经理
	 * @return
	 */
	public boolean isCustomerManager(){
		int teamRoleId=this.getLoginInfo().getTeamRoleId();
		if(teamRoleId==4){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是团队主管
	 * @return
	 */
	public boolean isTeamManager(){
		int teamRoleId=this.getLoginInfo().getTeamRoleId();
		if(teamRoleId==3){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是后台人员
	 * @return
	 */
	public boolean isBackenManager(){
		int teamRoleId=this.getLoginInfo().getTeamRoleId();
		if(teamRoleId==5){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是不是审批人员/专职审批/授权人员/ 签订人员
	 * @return
	 */
	public boolean isApprovalOrOther(){
		String roleIds = this.getLoginInfo().getRoleIds();
		if(StringUtils.isBlank(roleIds)){
			return false;
		}
		String[] roleArr = roleIds.split(",");
		if(null!=roleArr){
			for (String role : roleArr){
				if(role.equals("6")||role.equals("12")||role.equals("14")){
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * 判断是否是多个团队主管人员
	 * @return
	 */
	public boolean isMutilTeamManager(){
		int teamRoleId=this.getLoginInfo().getTeamRoleId();
		if(teamRoleId==2){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是配置平台业务管理员
	 * @return
	 */
	public boolean isCofigPlatFormManager(){
		int teamRoleId=this.getLoginInfo().getTeamRoleId();
		if(teamRoleId==10){
			return true;
		}
		return false;
	}
	
	/**
	 * 得到引用宏的javascript文件引用集合
	 * @param links
	 * @return
	 */
	public String[] getLinkGroups(String links){
		return links.split("\\,");
	}
	
	/**
     * 判断提交方式是否是ajax
     */
    public boolean isAjax() {
        String requestType = getRequest().getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
    
	protected String render(String text, String contentType) {
        try {
			HttpServletResponse response = getResponse();
            response.setContentType(contentType);
            //response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
        } catch (IOException e) {
        }
        return null;
    }

    public String renderText(String text) {
    	this.getResponse().setHeader("result", "success".equals(text)?"success":"fail");		//token拦截器用到
        return render(text, "text/plain;charset=UTF-8");
    }
    
    /**
     * 给前端页面发送json字符串
     * @param success 请求是否成功
     * @param cause	  原因
     * @param value  具体的值
     * @return
     */
    public String renderText(boolean success,String cause,String value){
    	JSONObject json = new JSONObject();
    	json.put("success", success);
    	json.put("cause", cause);
    	json.put("value", value);
    	this.getResponse().setHeader("result", success?"success":"fail");					//token拦截器用到
    	return render(json.toString(),"text/plain;charset=UTF-8");
    }


	/**
	 * 给前端页面发送json字符串,json参数根据业务自定义
	 * @param success
	 * @param cause
	 * @param json
	 * @return
	 */
	public String renderJson(boolean success, String cause, JSONObject json){
		json.put("success", success);
		json.put("cause", cause);
		this.getResponse().setHeader("result", success?"success":"fail");					//token拦截器用到
		return render(json.toString(),"text/plain;charset=UTF-8");
	}


    public String renderHtml(String html) {
        return render(html, "text/html;charset=UTF-8");
    }

    public String renderXML(String xml) {
        return render(xml, "text/xml;charset=UTF-8");
    }

    public HttpServletRequest getRequest() {
        return ServletContextHolder.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletContextHolder.getResponse();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

	public String getRules() {
		return AccessInfoHolder.getAccessInfo().getRules();
	}

	public IPageSize getPage() {
		return AccessInfoHolder.getAccessInfo().getPage();
	}

	public String getParameter(String param){
		return getParameter(param,null);
	}
	
	public String getParameter(String param,String defulatVavlue){
		return getParameter(param,null,null,null);
	}
	
	public String getParameter(String param,String defulatVavlue,Integer maxLength){
		return getParameter(param,null,maxLength,null);
	}
	
	public String getParameter(String param,String defulatVavlue,Class<?> clazz){
		return getParameter(param,null,null,clazz);
	}
	
	public String getParameter(String param,String defulatVavlue,Integer maxLength,Class<?> clazz){
		String str = this.getRequest().getParameter(param);
		if(str!=null){
			if(maxLength!=null && str.length()>maxLength){
				return str.substring(0, maxLength);
			}
			if(StringUtil.isNotEmpty(str) && clazz!=null){
				try{
					TypeUtil.changeType(str, clazz);
				}catch(Exception e){
					throw new RuntimeException("参数"+param+"类型格式不正确:"+str);
				}
			}
			if(StringUtil.isNotEmpty(str) && !XSSFilterUtil.isJsonString(str)){
				return XSSFilterUtil.cleanXSS(str);
			}
		}else{
			return defulatVavlue;
		}
		return str;
	}
	
	public void setAttribute(String name,Object value){
		this.getRequest().setAttribute(name, value);
	}
	
	/**
	 * 得到页面模版读取接口
	 * @return
	 */
	public ILayoutLoader getLayoutLoader(){
		Object bean = this.getBean("layoutLoader");
		if(bean instanceof ILayoutLoader){
			return (ILayoutLoader)bean;
		}
		return null;
	}
	

	/**
	 * 字符串读写器 通过属性名称取属性值 支持 实体 HashMap DataRow
	 * @return
	 */
	public IPropertyReader<?> getReader(){
		return Reader.objectReader();
	}

	public String getCtx() {
		return AccessInfoHolder.getAccessInfo().getCtx();
	}

	public String getRandNum() {
		return AccessInfoHolder.getAccessInfo().getRandNum();
	}

	public String getFuncCode() {
		return AccessInfoHolder.getAccessInfo().getFuncCode();
	}

	public String getMenuCode() {
		return AccessInfoHolder.getAccessInfo().getMenuCode();
	}

	public int getPageSize() {
		return getPage().getPageSize();
	}

	public int getPageNum() {
		return getPage().getPageNum();
	}

    /**
     * 
     * @return
     */
    public ILoginInfo getLoginInfo(){
    	return AccessInfoHolder.getWebLoginInfo();
    }
    
    /**
     * 是否有操作权限
     * @return
     */
    public boolean hasFuncPermit(String funcCode){
    	IFuncPermit func = this.getFuncPermit();
    	if(func != null){
    		return func.hasFuncCode(funcCode);
    	}
    	return false;
    }

    /**
     * 得到操作权限
     * @return
     */
    public IFuncPermit getFuncPermit(){
    	return AccessInfoHolder.getWebLoginInfo();
    }

	/**
	 * 得到json对像转换器
	 * @return
	 */
	public IConvert getDojoConvert(){
		Object bean = this.getBean("dojoConvert");
		if(bean instanceof IConvert){
			return (IConvert)bean;
		}
		return null;
	}
}
