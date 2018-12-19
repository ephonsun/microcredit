package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.system.SysDataDict;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 系统数据字典表数据访问接口
 */
public interface IDataDictDao {

	/**
	 * 新增系统数据字典表
	 * @param dataDict 实体对像
	 */
	void insertDataDict(SysDataDict dataDict);

	/**
	 *修改系统数据字典表
	 * @param dataDict 实体对像
	 */
	void updateDataDict(SysDataDict dataDict);

	/**
	 * 通过主键删除系统数据字典表
	 * @param dataDictId 主键Id
	 */
	void deleteDataDictById(SysDataDict dataDict);

	/**
	 * 通过主键得到系统数据字典表
	 * @param dataDictId 主键Id
	 */
	SysDataDict getDataDictById(Integer dataDictId);
	
	/**
	 * 通过中文名得到系统数据字典表
	 * @param dictCnName 中文名
	 */
	SysDataDict getDataDictByCnName(String dictCnName);
	
	SysDataDict getDataDictByEnName(String dictEnName);

	/**
	 * 查询系统数据字典表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysDataDict> queryDataDictList(Map<String,Object> condition);

	/**
	 * 分页查询系统数据字典表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysDataDict> queryDataDictList(Map<String,Object> condition,IPageSize page);
	
	Integer queryDataDictCount(Map<String, Object> condition);
	
	Integer getDataDictCountByName(String name);

}
