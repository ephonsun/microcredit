package banger.common.interceptor;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.jsonwebtoken.Claims;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import banger.common.annotation.NoTokenAnnotation;
import banger.common.annotation.TokenAnnotation;
import banger.common.annotation.TokenRepeatAnnotation;
import banger.framework.util.DateUtil;
import banger.framework.util.JwtUtil;
import banger.framework.util.StringUtil;
import banger.framework.web.servlet.ServletContextHolder;
import banger.framework.web.servlet.ServletUtil;

public class HeaderTokenInterceptor implements HandlerInterceptor {
	private static Map<String,Integer> tokenMap;		//防止token重复提交
	private static Map<Integer,Boolean> clearMap;
	
	static{
		tokenMap = new ConcurrentHashMap<String,Integer>();
		clearMap = new ConcurrentHashMap<Integer,Boolean>();
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ServletContextHolder.prepare(request,response);
		
		this.dailyClear();   //第天清除token
		
		boolean needLogin = true;				//需要登入
		boolean tokenGenerat = false;			//产生新的token
		boolean isRepeat = false;				//防止重复提交
		
		String urlToken = request.getParameter("token");
		String headToken = request.getHeader("token");
		String headUserId = request.getHeader("userId");
		
		if (handler instanceof HandlerMethod){
			
			NoTokenAnnotation noTokenAnnotation = ((HandlerMethod) handler).getMethodAnnotation(NoTokenAnnotation.class);
			if(null != noTokenAnnotation){
				return true;			//不需要token验证的controllor
			}
			
			NoLoginInterceptor noLoginAnnotation = ((HandlerMethod) handler).getMethodAnnotation(NoLoginInterceptor.class);
			if(null != noLoginAnnotation){
				needLogin = false;
			}
			
			if(StringUtil.isNullOrEmpty(urlToken)){
				TokenAnnotation tokenAnnotation = ((HandlerMethod) handler).getMethodAnnotation(TokenAnnotation.class);
				if(null != tokenAnnotation){
					tokenGenerat = true;
				}
			}
			
			TokenRepeatAnnotation repeatAnnotation = ((HandlerMethod) handler).getMethodAnnotation(TokenRepeatAnnotation.class);
			if(null != repeatAnnotation){
				isRepeat = true;
			}
		}
		
		String requestURI = request.getRequestURI();
		

		String token = "";
		
		if(StringUtil.isNotEmpty(urlToken)){
			token = urlToken;
		}else if(StringUtil.isNotEmpty(headToken)){
			token = headToken;
		}else{
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				for(int i = 0; i < cookies.length; i++){
					if("token".equals(cookies[i].getName())){
						token = cookies[i].getValue();
					}
				}
			}
		}
		
		if(!requestURI.contains("/api/")){
			if(needLogin){
				if(StringUtil.isNotEmpty(token)){
					Claims claims = JwtUtil.verifyToken(token);
					String id=claims.getId();
		            String subject=claims.getSubject();
		            String issuer=claims.getIssuer();
		            if(!"loan".equals(id) || !"banger".equals(issuer) || !"micro".equals(subject)){
		            	if(ServletUtil.isAjax(request)){
							ServletUtil.renderText("nopermit");
							return false;
						}else{
							response.sendRedirect("/nopermitform.html");
							return false;
						}
		            }else{
		            	if(tokenGenerat && !ServletUtil.isAjax(request)){
		            		//tokenMap.remove(token);			//称除记数token，防止重复提交
		            		token = JwtUtil.updateToken(token);
		            		String redirectUrl = requestURI+"?"+(StringUtil.isNotEmpty(request.getQueryString())?request.getQueryString():"");
		            		if(StringUtil.isNotEmpty(request.getQueryString())){
		            			response.sendRedirect(redirectUrl+"&token="+token);
		            		}else{
		            			response.sendRedirect(redirectUrl+"token="+token);
		            		}
		            		return false;
		            	}else{
			            	long t1 = claims.getExpiration().getTime();
			            	long t2 = new Date().getTime();
			            	long t3 = t1 - t2;
				            if(t3<0){
				            	if(ServletUtil.isAjax(request)){
									ServletUtil.renderText("timeout");
									return false;
								}else{
									ServletUtil.renderText("timeout");
									response.sendRedirect("/");
									return false;
								}
				            }else if(isRepeat && tokenMap.containsKey(token) && isPost(request)){
				            	Integer count = tokenMap.get(token);
				            	if(count!=null && count.intValue()>0){
				            		ServletUtil.renderText("repeatsubmit");
									return false;
				            	}
				            }
		            	}
		            }
				}else{
					if(ServletUtil.isAjax(request)){
						ServletUtil.renderText("timeout");
						return false;
					}else{
						ServletUtil.renderText("timeout");
						response.sendRedirect("/");
						return false;
					}
				}
			}
		}else{
			if(StringUtil.isNotEmpty(token) && StringUtil.isNotEmpty(headUserId)){
				Claims claims = JwtUtil.verifyToken(token);
				String id=claims.getId();
	            String subject=claims.getSubject();
	            String issuer=claims.getIssuer();
	            if(!"banger".equals(issuer) || !"micro".equals(subject)){
	            	ServletUtil.renderText("{\"code\":99,\"msg\":\"没有权限\"}");
					return false;
	            }
	            if(!headUserId.equals(id)){
	            	ServletUtil.renderText("{\"code\":98,\"msg\":\"登入信息无效\"}");
					return false;
	            }
	            if(isRepeat && tokenMap.containsKey(token) && isPost(request)){
	            	Integer count = tokenMap.get(token);
	            	if(count!=null && count.intValue()>0){
	            		ServletUtil.renderText("{\"code\":98,\"msg\":\"重复提交\"}");
						return false;
	            	}
	            }
			}else{
				ServletUtil.renderText("{\"code\":99,\"msg\":\"没有权限\"}");
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
		String urlToken = request.getParameter("token");
		String headToken = request.getHeader("token");
		
		String token = "";
		if(StringUtil.isNotEmpty(urlToken)){
			token = urlToken;
		}else if(StringUtil.isNotEmpty(headToken)){
			token = headToken;
		}else{
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				for(int i = 0; i < cookies.length; i++){
					if("token".equals(cookies[i].getName())){
						token = cookies[i].getValue();
					}
				}
			}
		}
		
		boolean isRepeat = false;				//防止重复提交
		
		if (handler instanceof HandlerMethod){
			TokenRepeatAnnotation repeatAnnotation = ((HandlerMethod) handler).getMethodAnnotation(TokenRepeatAnnotation.class);
			if(null != repeatAnnotation){
				isRepeat = true;
			}
		}
		
		if(StringUtil.isNotEmpty(token) && isRepeat){
			String result = response  .getHeader("result");
			if("success".equals(result)){
				if(!tokenMap.containsKey(token)){
					tokenMap.put(token, 1);
				}else{
					Integer count = tokenMap.get(token);
					tokenMap.put(token, count+1);
				}
			}
		}
	}
	
	private void dailyClear(){
		int day = DateUtil.getDay(new Date());
		if(clearMap.containsKey(day)){
			if(!clearMap.get(day)){
				clearMap.put(day, true);
				tokenMap.clear();
			}
		}else{
			clearMap.put(day, true);
			tokenMap.clear();
		}
	}
	
	private boolean isPost(HttpServletRequest request){
		String method = request.getMethod();
        return "POST".equalsIgnoreCase(method);
	}

}
