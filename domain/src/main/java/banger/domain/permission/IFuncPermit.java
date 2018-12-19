package banger.domain.permission;

import java.util.Set;

public interface IFuncPermit {
	
	/**
	 * 得到操作权限
	 * @return
	 */
	Set<String> getFuncCodes();
	
	/**
	 * 是否有操作权限
	 * @param funcCode
	 * @return
	 */
	boolean hasFuncCode(String funcCode);
	
	/**
	 * 得到操作权限
	 * @return
	 */
	Set<String> getMenuCodes();
	
	/**
	 * 是否有操作权限
	 * @param funcCode
	 * @return
	 */
	boolean hasMenuCode(String menuCode);
	
}
