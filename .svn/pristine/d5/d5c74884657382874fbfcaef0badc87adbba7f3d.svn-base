package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITableDictDao;
import banger.domain.config.AutoTableDict;

/**
 * 数据访问类
 */
@Repository
public class TableDictDao extends PageSizeDao<AutoTableDict> implements ITableDictDao {

	/**
	 * 新增
	 * @param tableDict 实体对像
	 */
	public void insertTableDict(AutoTableDict tableDict){
		tableDict.setDictId(this.newId().intValue());
		this.execute("insertTableDict",tableDict);
	}

	/**
	 *修改
	 * @param tableDict 实体对像
	 */
	public void updateTableDict(AutoTableDict tableDict){
		this.execute("updateTableDict",tableDict);
	}

	/**
	 * 通过主键删除
	 * @param dictId 主键Id
	 */
	public void deleteTableDictById(Integer dictId){
		this.execute("deleteTableDictById",dictId);
	}

	/**
	 * 通过主键得到
	 * @param dictId 主键Id
	 */
	public AutoTableDict getTableDictById(Integer dictId){
		return (AutoTableDict)this.queryEntity("getTableDictById",dictId);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableDict> queryTableDictList(Map<String,Object> condition){
		return (List<AutoTableDict>)this.queryEntities("queryTableDictList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoTableDict> queryTableDictList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoTableDict>)this.queryEntities("queryTableDictList", page, condition);
	}

}
