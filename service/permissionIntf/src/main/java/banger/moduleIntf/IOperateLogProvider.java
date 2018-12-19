package banger.moduleIntf;

/**
 * Created by Administrator on 14-3-28.
 */
public interface IOperateLogProvider {

	/**
	 * 添加操作日志
	 * @param module
	 * @param action
	 * @param userId
	 * @param ip
	 */
	void addSysOpeventLog(String module, String action, Integer userId,String ip);
}
