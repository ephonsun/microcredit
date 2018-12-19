package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoColumnDict;

/**
 * 业务访问接口
 */
public interface IColumnDictService {

	/**
	 * 新增
	 * @param columnDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertColumnDict(AutoColumnDict columnDict, Integer loginUserId);

	/**
	 *修改
	 * @param columnDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateColumnDict(AutoColumnDict columnDict, Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param DICT_ID 主键Id
	 */
	void deleteColumnDictById(Integer dictId);

	/**
	 * 通过主键得到
	 * @param DICT_ID 主键Id
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
