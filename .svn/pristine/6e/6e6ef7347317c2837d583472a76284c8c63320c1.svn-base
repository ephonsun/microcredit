package banger.common.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banger.framework.util.StringUtil;

public class ValidateCodeManage {
	private static Map<String,String> validateCodeMap;
	private static Map<String,Integer> errorCountMap;		//输入错误次数
	
	static{
		validateCodeMap = new ConcurrentHashMap<String,String>();
		errorCountMap = new ConcurrentHashMap<String,Integer>();
	}
	
	public static Integer getErrorCount(String account){
		if(errorCountMap.containsKey(account)){
			Integer count = errorCountMap.get(account);
			return count;
		}
		return 0;
	}
	
	public static void addErrorCount(String account){
		if(errorCountMap.containsKey(account)){
			Integer count = errorCountMap.get(account);
			errorCountMap.put(account, count+1);
		}else{
			errorCountMap.put(account, 1);
		}
	}
	
	public static void clearErrorCount(String account){
		errorCountMap.remove(account);
	}
	
	public static boolean valid(String requestId,String validateCode){
		if(StringUtil.isNotEmpty(requestId) && StringUtil.isNotEmpty(validateCode)){
			if(validateCodeMap.containsKey(requestId)){
				String code = validateCodeMap.get(requestId);
				boolean flag = validateCode.equalsIgnoreCase(code);
				if(flag){
					validateCodeMap.remove(requestId);
				}
				return flag;
			}
		}
		return false;
	}
	
	public static void setValidateCode(String requestId,String validateCode){
		if(StringUtil.isNotEmpty(requestId)){
			validateCodeMap.put(requestId, validateCode);
		}
	}
	
}
