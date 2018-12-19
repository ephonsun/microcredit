package banger.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import banger.common.annotation.FuncPermitAnnotation;
import banger.common.session.AppSessionManage;
import banger.domain.permission.WebLoginInfo;
import banger.framework.util.StringUtil;
import banger.framework.web.servlet.ServletContextHolder;
import banger.framework.web.servlet.ServletUtil;

public class DataPermitInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ServletContextHolder.prepare(request,response);
		
		FuncPermitAnnotation funcPermitAnnotation = ((HandlerMethod) handler).getMethodAnnotation(FuncPermitAnnotation.class);
		if(null != funcPermitAnnotation){
			if(StringUtil.isNotEmpty(funcPermitAnnotation.values())){
				String userIdStr = request.getHeader("userId");
				if(StringUtil.isNotEmpty(userIdStr)){
					Integer userId = Integer.parseInt(userIdStr);
					WebLoginInfo webLoginInfo = AppSessionManage.getSession(userId);
					if(webLoginInfo!=null){
						String[] funcCodes = funcPermitAnnotation.values().split("\\,");
						for(String funcCode : funcCodes){
							if(!webLoginInfo.hasFuncCode(funcCode.trim())){
								ServletUtil.renderText("{\"code\":99,\"msg\":\"没有数据权限\"}");
								return false;
							}
						}
					}else{
						ServletUtil.renderText("{\"code\":99,\"msg\":\"没有数据权限\"}");
						return false;
					}
				}else{
					ServletUtil.renderText("{\"code\":99,\"msg\":\"没有数据权限\"}");
					return false;
				}
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
