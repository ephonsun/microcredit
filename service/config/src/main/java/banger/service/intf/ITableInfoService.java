package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoTableInfo;

/**
 * 业务访问接口
 */
public interface ITableInfoService {

	/**
	 * 新增
	 * @param tableInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTableInfo(AutoTableInfo tableInfo, Integer loginUserId);

	/**
	 *修改
	 * @param tableInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTableInfo(AutoTableInfo tableInfo, Integer loginUserId);

	/**
	 * 得到所有模板数据
	 * @return
	 */
	List<AutoTableInfo> queryAllTableInfoList();


	/**
	 * 通过主键删除
	 * @param TABLE_ID 主键Id
	 */
	void deleteTableInfoById(Integer tableId);

	/**
	 * 通过主键得到
	 * @param TABLE_ID 主键Id
	 */
	AutoTableInfo getTableInfoById(Integer tableId);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoTableInfo> queryTableInfoList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoTableInfo> queryTableInfoList(Map<String, Object> condition, IPageSize page);


	/**
	 *
	 * @return
	 */
	List<AutoTableInfo> queryHiddenFormList(String formname);

	List<AutoTableInfo> queryControlFormList();
}
