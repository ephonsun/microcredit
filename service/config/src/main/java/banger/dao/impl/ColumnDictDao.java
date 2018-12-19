package banger.dao.impl;

import banger.dao.intf.IColumnDictDao;
import banger.domain.config.AutoColumnDict;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class ColumnDictDao extends PageSizeDao<AutoColumnDict> implements IColumnDictDao {

	/**
	 * 新增
	 * @param columnDict 实体对像
	 */
	public void insertColumnDict(AutoColumnDict columnDict){
		columnDict.setDictId(this.newId().intValue());
		this.execute("insertColumnDict",columnDict);
	}

	/**
	 *修改
	 * @param columnDict 实体对像
	 */
	public void updateColumnDict(AutoColumnDict columnDict){
		this.execute("updateColumnDict",columnDict);
	}

	/**
	 * 通过主键删除
	 * @param dictId 主键Id
	 */
	public void deleteColumnDictById(Integer dictId){
		this.execute("deleteColumnDictById",dictId);
	}

	/**
	 * 通过主键得到
	 * @param dictId 主键Id
	 */
	public AutoColumnDict getColumnDictById(Integer dictId){
		return (AutoColumnDict)this.queryEntity("getColumnDictById",dictId);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoColumnDict> queryColumnDictList(Map<String,Object> condition){
		return (List<AutoColumnDict>)this.queryEntities("queryColumnDictList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoColumnDict> queryColumnDictList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoColumnDict>)this.queryEntities("queryColumnDictList", page, condition);
	}

}
