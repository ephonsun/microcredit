package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IDataDictColDao;
import banger.domain.system.SysDataDictCol;

/**
 * 数据字典字段表数据访问类
 */
@Repository
public class DataDictColDao extends PageSizeDao<SysDataDictCol> implements IDataDictColDao {

	/**
	 * 新增数据字典字段表
	 * @param dataDictCol 实体对像
	 */
	public void insertDataDictCol(SysDataDictCol dataDictCol){
		int id = this.newId().intValue();
		dataDictCol.setDictColId(id);
		this.execute("insertDataDictCol",dataDictCol);
	}

	/**
	 *修改数据字典字段表
	 * @param dataDictCol 实体对像
	 */
	public void updateDataDictCol(SysDataDictCol dataDictCol){
		this.execute("updateDataDictCol",dataDictCol);
	}

	/**
	 * 通过主键删除数据字典字段表
	 * @param dictColId 主键Id
	 */
	public void deleteDataDictColById(SysDataDictCol dataDictCol){
		this.execute("deleteDataDictColById",dataDictCol);
	}

	/**
	 * 通过主键得到数据字典字段表
	 * @param dictColId 主键Id
	 */
	public SysDataDictCol getDataDictColById(Integer dictColId){
		return (SysDataDictCol)this.queryEntity("getDataDictColById",dictColId);
	}

	/**
	 * 查询数据字典字段表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysDataDictCol> queryDataDictColList(Map<String,Object> condition){
		return (List<SysDataDictCol>)this.queryEntities("queryDataDictColList", condition);
	}

	/**
	 * 分页查询数据字典字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysDataDictCol> queryDataDictColList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysDataDictCol>)this.queryEntities("queryDataDictColList", page, condition);
	}
	
	@Override
	public Integer queryDataDictColCount(Map<String, Object> condition) {
		return this.queryInt("queryDataDictColCount",condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDataDictCol> queryDataDictColListByName(String name) {
		return (List<SysDataDictCol>) this.queryEntities("queryDataDictColListByName", name);
	}

	@Override
	public Integer getDataDictColMaxNo(Integer dataDictId) {
		return this.queryInt("getDataDictColMaxNo", dataDictId);
	}

	@Override
	public void updateDataDictColNoById(SysDataDictCol dataDictCol) {
		this.execute("updateDataDictColNoById", dataDictCol);
	}

	/**
	 * 查询行业分类
	 * @return
	 */
    public List<SysDataDictCol> queryIndustryGuidelines() {
    	Map<String,Object> condition = new HashMap<String, Object>();
    	return (List<SysDataDictCol>) this.queryEntities("industryGuidelinesSelect",condition);
    }

}
