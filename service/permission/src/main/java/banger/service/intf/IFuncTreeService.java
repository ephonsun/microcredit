package banger.service.intf;

import java.util.List;

public interface IFuncTreeService {
	
	/**
	 * 得到角色功能树
	 * @return
	 */
	String getRoleFuncTree();
	
	/**
	 * 得到角色功能树
	 * @param checkIds 选中节点
	 * @return
	 */
	String getRoleFuncTree(List<String> checkIds);

	/**
	 * 得到不可勾选的角色功能树
	 * @param checkIds 选中节点
	 * @return
	 */
	String getRoleDisFuncTree(List<String> funcIds);
	
}
