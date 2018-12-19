package banger.dao.intf;

import banger.domain.config.IntoAutoTableColumn;
import banger.domain.config.IntoAutoTableColumnQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 进件自定义字段表数据访问接口
 */
public interface IAutoTableColumnDao {

	/**
	 * 新增进件自定义字段表
	 * @param autoTableColumn 实体对像
	 */
	void insertAutoTableColumn(IntoAutoTableColumn autoTableColumn);

	/**
	 *修改进件自定义字段表
	 * @param autoTableColumn 实体对像
	 */
	void updateAutoTableColumn(IntoAutoTableColumn autoTableColumn);

	/**
	 *修改进件自定义字段表
	 * @param IntoAutoTableColumnQuery 实体对像
	 */
	void updateAutoTableColumnQuery(IntoAutoTableColumnQuery intoAutoTableColumnQuery);

	/**
	 * 通过主键删除进件自定义字段表
	 * @param fieldId 主键Id
	 */
	void deleteAutoTableColumnById(Integer fieldId);

	/**
	 * 通过主键得到进件自定义字段表
	 * @param fieldId 主键Id
	 */
	IntoAutoTableColumn getAutoTableColumnById(Integer fieldId);

	/**
	 * 查询进件自定义字段表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoAutoTableColumn> queryAutoTableColumnList(Map<String, Object> condition);

	/**
	 * 分页查询进件自定义字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoAutoTableColumn> queryAutoTableColumnList(Map<String, Object> condition, IPageSize page);

	/**
	 * 联表查询进件自定义字段
	 */
	List<IntoAutoTableColumnQuery> queryConnectAutoTable();

	/**
	 * 联表查询
	 * 根据主键
	 */
	IntoAutoTableColumnQuery queryConnectAutoTableById(Integer fieldId);

	/**
	 * 查询最大排序号
	 */
	Integer queryMaxSortNo();
}
