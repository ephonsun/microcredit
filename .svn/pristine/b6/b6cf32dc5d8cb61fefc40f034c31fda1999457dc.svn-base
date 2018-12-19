package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysImportHistory;

/**
 * 导入历史表业务访问接口
 */
public interface IImportHistoryService {

	/**
	 * 新增导入历史表
	 * @param importHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertImportHistory(SysImportHistory importHistory, Integer loginUserId);

	/**
	 *修改导入历史表
	 * @param importHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateImportHistory(SysImportHistory importHistory, Integer loginUserId);

	/**
	 * 通过主键删除导入历史表
	 * @param id 主键Id
	 */
	void deleteImportHistoryById(Integer id);

	/**
	 * 通过主键得到导入历史表
	 * @param id 主键Id
	 */
	SysImportHistory getImportHistoryById(Integer id);

	/**
	 * 查询导入历史表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysImportHistory> queryImportHistoryList(Map<String, Object> condition);

	/**
	 * 分页查询导入历史表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysImportHistory> queryImportHistoryList(Map<String, Object> condition, IPageSize page);

}
