package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IDataDictDao;
import banger.domain.system.SysDataDict;

/**
 * 系统数据字典表数据访问类
 */
@Repository
public class DataDictDao extends PageSizeDao<SysDataDict> implements IDataDictDao {

	/**
	 * 新增系统数据字典表
	 * @param dataDict 实体对像
	 */
	public void insertDataDict(SysDataDict dataDict){
		dataDict.setDataDictId(this.newId().intValue());
		this.execute("insertDataDict",dataDict);
	}

	/**
	 *修改系统数据字典表
	 * @param dataDict 实体对像
	 */
	public void updateDataDict(SysDataDict dataDict){
		this.execute("updateDataDict",dataDict);
	}

	/**
	 * 通过主键删除系统数据字典表
	 * @param dataDictId 主键Id
	 */
	public void deleteDataDictById(SysDataDict dataDict){
		this.execute("deleteDataDictById",dataDict);
	}
	
	/**
	 * 通过中文名得到系统数据字典表
	 * @param dictCnName 中文名
	 */
	@Override
	public SysDataDict getDataDictByCnName(String dictCnName) {
		return (SysDataDict)this.queryEntity("getDataDictByCnName",dictCnName);
	}

	/**
	 * 通过主键得到系统数据字典表
	 * @param dataDictId 主键Id
	 */
	public SysDataDict getDataDictById(Integer dataDictId){
		return (SysDataDict)this.queryEntity("getDataDictById",dataDictId);
	}

	/**
	 * 查询系统数据字典表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysDataDict> queryDataDictList(Map<String,Object> condition){
		return (List<SysDataDict>)this.queryEntities("queryDataDictList", condition);
	}

	/**
	 * 分页查询系统数据字典表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysDataDict> queryDataDictList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysDataDict>)this.queryEntities("queryDataDictList", page, condition);
	}

	@Override
	public Integer queryDataDictCount(Map<String, Object> condition) {
		return this.queryInt("queryDataDictCount",condition);
	}

	@Override
	public Integer getDataDictCountByName(String name) {
		return this.queryCount("getDataDictCountByName", name);
	}

	@Override
	public SysDataDict getDataDictByEnName(String dictEnName) {
		return (SysDataDict)this.queryEntity("getDataDictByEnName",dictEnName);
	}

}
