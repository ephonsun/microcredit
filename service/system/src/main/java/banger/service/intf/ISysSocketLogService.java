package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysSocketLog;

/**
 * 业务访问接口
 */
public interface ISysSocketLogService {

	/**
	 * 新增
	 * @param socketLog 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertSocketLog(SysSocketLog socketLog, Integer loginUserId);

	/**
	 *修改
	 * @param socketLog 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateSocketLog(SysSocketLog socketLog, Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	void deleteSocketLogById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	SysSocketLog getSocketLogById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<SysSocketLog> querySocketLogList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysSocketLog> querySocketLogList(Map<String, Object> condition, IPageSize page);

}
