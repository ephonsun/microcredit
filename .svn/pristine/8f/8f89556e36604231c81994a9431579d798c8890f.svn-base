package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableColumnQuery;
import banger.domain.config.IntoTemplatesField;
import banger.domain.config.ModeTemplateField;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 业务访问接口
 */
public interface ITableColumnService {

	/**
	 * 新增
	 * 
	 * @param tableColumn
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	void insertTableColumn(AutoTableColumn tableColumn, Integer loginUserId);

	/**
	 * 修改
	 * 
	 * @param tableColumn
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	void updateTableColumn(AutoTableColumn tableColumn, Integer loginUserId);

	/**
	 * 通过主键删除
	 * 
	 * @param FIELD_ID
	 *            主键Id
	 */
	void deleteTableColumnById(Integer fieldId);

	/**
	 * 通过主键得到
	 * 
	 * @param FIELD_ID
	 *            主键Id
	 */
	AutoTableColumn getTableColumnById(Integer fieldId);

	List<AutoTableColumn> getAllTableColumnList();
	/**
	 * 查询
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<AutoTableColumn> queryTableColumnList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	IPageList<AutoTableColumn> queryTableColumnList(Map<String, Object> condition, IPageSize page);

	/**
	 * 上移下移
	 * 
	 * @param type
	 *            上移还是下移
	 * @param tableId
	 *            表id
	 * @param fieldId
	 */
	void moveTableColumnOrderNo(String type, Integer tableId, Integer fieldId);

	/**
	 * 查询评分项
	 * @param condition
	 * @return
	 */
	List<ModeTemplateField> queryModelTemplateInfoColumn(Map<String, Object> condition);

	List<IntoTemplatesField> queryIntoTemplatesInfoColumn(Map<String, Object> condition);
	/**
	 * 查询用户自定义表
	 * @return
	 */
	List<AutoTableColumn> queryTableColumnListOfCust();
	/**
	 * 查询所有自定义字段数据包括表名描述
	 * @return
	 */
    List<AutoTableColumnQuery> getAllTableColumnQueryList();
}
