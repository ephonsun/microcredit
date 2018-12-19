package banger.common.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banger.domain.permission.WebLoginInfo;

public class AppSessionManage {
	public static Map<Integer,WebLoginInfo> userLoginMap;
	
	static{
		userLoginMap = new ConcurrentHashMap<Integer,WebLoginInfo>();
	}
	
	public static void setSession(Integer userId,WebLoginInfo webLoginInfo){
		userLoginMap.put(userId, webLoginInfo);
	}
	
	public static WebLoginInfo getSession(Integer userId){
		return userLoginMap.get(userId);
	}
	
}
