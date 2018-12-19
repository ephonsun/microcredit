/**
 *
 *
 * @Author :merlin
 * @Date: 2017.3.29
 */
package banger.common.interceptor;

import banger.common.login.AccessInfoHolder;
import banger.domain.permission.WebLoginInfo;
import banger.framework.util.ClientRefererUtil;
import banger.framework.util.StringUtil;
import banger.framework.web.servlet.ServletContextHolder;
import banger.framework.web.servlet.ServletUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author Merlin
 * @Date 2017.3.29
 */
public class ParamInterceptor implements HandlerInterceptor {
	Log log = LogFactory.getLog(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ServletContextHolder.prepare(request,response);
		
		/*
		boolean needFilter = true;
		
		NoFilterAnnotation noFilterAnnotation = ((HandlerMethod) handler).getMethodAnnotation(NoFilterAnnotation.class);
		if(null != noFilterAnnotation){
			needFilter = false;
		}
		
		if(needFilter){
			Map<String,String[]> requestMap = request.getParameterMap();
			for(String key : requestMap.keySet()){
				String[] words = requestMap.get(key);
				if(KeyWorkFilter.hasFilterWords(words)){
					if(ServletUtil.isAjax(request)){
						ServletUtil.renderText("unsafe");
						return false;
					}else{
						response.sendRedirect("/hasunsafechar.html");
						return false;
					}
				}
			}
		}
		*/
		
		AccessInfoHolder.init(request);
		
		// 获取请求是从哪里来的  
        String referer = request.getHeader("referer");
        if(StringUtil.isNotEmpty(referer)){
        	String host = ClientRefererUtil.getHostUrl(referer);
			AccessInfoHolder.AccessInfo accessInfo = AccessInfoHolder.getAccessInfo();
			WebLoginInfo webloginInfo = accessInfo.getWebLoginInfo();
			if(webloginInfo!=null && StringUtil.isNotEmpty(webloginInfo.getReferer())){
				if(!host.equals(webloginInfo.getReferer())){
					if(ServletUtil.isAjax(request)){
						ServletUtil.renderText("unsafe");
						return false;
					}else{
						response.sendRedirect("/hasunsafechar.html");
						return false;
					}
				}else{
//					System.out.println(host+"->"+webloginInfo.getReferer());
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
