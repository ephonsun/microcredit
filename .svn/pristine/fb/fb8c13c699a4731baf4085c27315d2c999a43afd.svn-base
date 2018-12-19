package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoTableDict;

/**
 * 数据访问接口
 */
public interface ITableDictDao {

	/**
	 * 新增
	 * @param tableDict 实体对像
	 */
	void insertTableDict(AutoTableDict tableDict);

	/**
	 *修改
	 * @param tableDict 实体对像
	 */
	void updateTableDict(AutoTableDict tableDict);

	/**
	 * 通过主键删除
	 * @param dictId 主键Id
	 */
	void deleteTableDictById(Integer dictId);

	/**
	 * 通过主键得到
	 * @param dictId 主键Id
	 */
	AutoTableDict getTableDictById(Integer dictId);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoTableDict> queryTableDictList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoTableDict> queryTableDictList(Map<String, Object> condition, IPageSize page);

}
