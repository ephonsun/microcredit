package banger.common.tools;

import java.util.Map;

import banger.common.listener.SpringContextUtil;

public class RegisterInterfaceImpl {
	@SuppressWarnings("rawtypes")
	public static Map getInterfaceImpl(Class cls){
		return SpringContextUtil.getApplicationContext().getBeansOfType(cls);
	}
}
