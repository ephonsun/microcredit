/*
 * xuhj Inc.
 * Copyright (c) 2012-2100 All Rights Reserved.
 * ToDo       :异常拦截器
 * Author     :徐红军
 * Create Date: 2012-12-27
 */
package banger.common.interceptor;

import java.net.URLDecoder;

import banger.common.login.AccessInfoHolder;
import banger.framework.web.servlet.ServletUtil;

import banger.domain.permission.IAutoLogin;
import banger.domain.permission.IFuncPermit;
import banger.domain.permission.ILoginInfo;
import banger.framework.util.BeanUtil;
import banger.framework.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionInterceptor implements HandlerInterceptor {
	Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 如果有NoLoginInterceptor不需要登录注解的方法，直接放回true
		if (handler instanceof HandlerMethod){
			NoLoginInterceptor methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(NoLoginInterceptor.class);if(null != methodAnnotation){
				return true;
			}
		}

		AccessInfoHolder.AccessInfo accessInfo = AccessInfoHolder.getAccessInfo();
		try {
			//项目初期注释action拦截器
			//判断是否需要登录才能访问
			if (isNeedLogin(accessInfo)) {
				String auto = accessInfo.getAutoLogin();
				//是否为自动登入
				if(StringUtil.isNotEmpty(auto)){
					String account = accessInfo.getAccount();
					if(StringUtil.isNotEmpty(account)){
						account = URLDecoder.decode(account,"utf-8");
						ILoginInfo login = accessInfo.getWebLoginInfo();
						if(login==null || !account.equals(login.getAccount())){
							IAutoLogin autoLogin = (IAutoLogin)BeanUtil.getJustOneBeanByClass(IAutoLogin.class);
							if(autoLogin!=null){
								autoLogin.autoLoginAccount(account);
							}
						}
					}
				}else{
					Object loginInfo =accessInfo.getWebLoginInfo();
					if(loginInfo == null){
						if(ServletUtil.isAjax(request)){
							ServletUtil.renderText("timeout");
							return false;
						}else{
							ServletUtil.renderText("timeout");
							// TODO 开发完成换成登录超时页面
							response.sendRedirect("/");
							return false;
						}
					}
				}
				if(!this.hasPermit(accessInfo)){			//是否有操作权限
					if(ServletUtil.isAjax(request)){
						ServletUtil.renderText("nopermit");
						return false;
					}else{
						response.sendRedirect("/nopermitform.html");
						return false;
					}
				}
			}else{
				//不需要登录就能访问的请求 直接调用action
				return true;
			}
			return true;
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement traceElement : e.getStackTrace()){
				sb.append(traceElement.toString());
				sb.append("\r\n");
			}

			log.error("程序异常:"+e.getMessage(),e);

			if (ServletUtil.isAjax(request)){
				ServletUtil.renderText("错误详情："+sb.toString());
			}else{
				request.setAttribute("errorMsg", sb.toString());
			}
			response.sendRedirect("/error");
			return false;
		}
	}
	
	private boolean isNeedLogin(AccessInfoHolder.AccessInfo accessInfo){
		if("none".equalsIgnoreCase(accessInfo.getNeedLogin())){
			return false;
		}else{
			return true;
		}
	}
	
	private boolean hasPermit(AccessInfoHolder.AccessInfo accessInfo){
		IFuncPermit permit = accessInfo.getWebLoginInfo();
		String funcCode = accessInfo.getFuncCode();
		String menuCode = accessInfo.getMenuCode();
		
		if(StringUtil.isNotEmpty(menuCode)){
			if(!permit.hasMenuCode(menuCode.trim())){
				return false;
			}
		}else if(StringUtil.isNotEmpty(funcCode)){
			if(!permit.hasFuncCode(funcCode.trim())){
				return false;
			}
		}
		
		return true;
	}



	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
