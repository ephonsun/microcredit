package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysDataDictCol;

/**
 * 数据字典字段表数据访问接口
 */
public interface IDataDictColDao {

	/**
	 * 新增数据字典字段表
	 * @param dataDictCol 实体对像
	 */
	void insertDataDictCol(SysDataDictCol dataDictCol);

	/**
	 *修改数据字典字段表
	 * @param dataDictCol 实体对像
	 */
	void updateDataDictCol(SysDataDictCol dataDictCol);

	/**
	 * 通过主键删除数据字典字段表
	 * @param dictColId 主键Id
	 */
	void deleteDataDictColById(SysDataDictCol dataDictCol);

	/**
	 * 通过主键得到数据字典字段表
	 * @param dictColId 主键Id
	 */
	SysDataDictCol getDataDictColById(Integer dictColId);

	/**
	 * 查询数据字典字段表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysDataDictCol> queryDataDictColList(Map<String,Object> condition);

	/**
	 * 分页查询数据字典字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysDataDictCol> queryDataDictColList(Map<String,Object> condition,IPageSize page);
	
	Integer queryDataDictColCount(Map<String, Object> condition);
	
	Integer getDataDictColMaxNo(Integer dataDictId);
	
	List<SysDataDictCol> queryDataDictColListByName(String name);
	
	void updateDataDictColNoById(SysDataDictCol dataDictCol);

    List<SysDataDictCol> queryIndustryGuidelines();
}
