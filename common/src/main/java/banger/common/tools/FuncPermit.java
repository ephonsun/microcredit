package banger.common.tools;

import java.util.Set;

import banger.common.login.AccessInfoHolder;
import banger.domain.permission.IFuncPermit;

public class FuncPermit {

	/**
	 * 得到操作权限
	 * @return
	 */
	public static Set<String> getFuncCodes(){
		IFuncPermit func = AccessInfoHolder.getWebLoginInfo();
		if(func != null){
			return func.getMenuCodes();
		}
		return null;
	}
	
	/**
	 * 是否有操作权限
	 * @param funcCode
	 * @return
	 */
	public static boolean hasFuncCode(String funcCode){
		IFuncPermit func = AccessInfoHolder.getWebLoginInfo();
    	if(func != null){
    		return func.hasFuncCode(funcCode);
    	}
    	return false;
	}
	
	/**
	 * 得到操作权限
	 * @return
	 */
	public static Set<String> getMenuCodes(){
		IFuncPermit func = AccessInfoHolder.getWebLoginInfo();
		if(func != null){
			return func.getMenuCodes();
		}
		return null;
	}
	
	/**
	 * 是否有操作权限
	 * @param menuCode
	 * @return
	 */
	public static boolean hasMenuCode(String menuCode){
		IFuncPermit func = AccessInfoHolder.getWebLoginInfo();
		if(func != null){
			return func.hasMenuCode(menuCode);
		}
		return false;
	}

}
