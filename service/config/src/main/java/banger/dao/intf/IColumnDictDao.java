package banger.dao.intf;

import banger.domain.config.AutoColumnDict;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface IColumnDictDao {

	/**
	 * 新增
	 * @param columnDict 实体对像
	 */
	void insertColumnDict(AutoColumnDict columnDict);

	/**
	 *修改
	 * @param columnDict 实体对像
	 */
	void updateColumnDict(AutoColumnDict columnDict);

	/**
	 * 通过主键删除
	 * @param dictId 主键Id
	 */
	void deleteColumnDictById(Integer dictId);

	/**
	 * 通过主键得到
	 * @param dictId 主键Id
	 */
	AutoColumnDict getColumnDictById(Integer dictId);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoColumnDict> queryColumnDictList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoColumnDict> queryColumnDictList(Map<String, Object> condition, IPageSize page);

}
