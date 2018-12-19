package banger.dao.impl;

import banger.dao.intf.IAutoTableColumnDao;
import banger.domain.config.IntoAutoTableColumn;
import banger.domain.config.IntoAutoTableColumnQuery;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件自定义字段表数据访问类
 */
@Repository
public class AutoTableColumnDao extends PageSizeDao<IntoAutoTableColumn> implements IAutoTableColumnDao {

	/**
	 * 新增进件自定义字段表
	 * @param autoTableColumn 实体对像
	 */
	public void insertAutoTableColumn(IntoAutoTableColumn autoTableColumn){
		autoTableColumn.setFieldId(this.newId().intValue());
//		autoTableColumn.setFieldSortno(this.newId().intValue());
		this.execute("insertAutoTableColumn",autoTableColumn);
	}

	/**
	 *修改进件自定义字段表
	 * @param autoTableColumn 实体对像
	 */
	public void updateAutoTableColumn(IntoAutoTableColumn autoTableColumn){
		this.execute("updateAutoTableColumn",autoTableColumn);
	}

	@Override
	public void updateAutoTableColumnQuery(IntoAutoTableColumnQuery intoAutoTableColumnQuery) {
		this.execute("updateAutoTableColumn",intoAutoTableColumnQuery);
	}

	/**
	 * 通过主键删除进件自定义字段表
	 * @param fieldId 主键Id
	 */
	public void deleteAutoTableColumnById(Integer fieldId){
		this.execute("deleteAutoTableColumnById",fieldId);
	}

	/**
	 * 通过主键得到进件自定义字段表
	 * @param fieldId 主键Id
	 */
	public IntoAutoTableColumn getAutoTableColumnById(Integer fieldId){
		return (IntoAutoTableColumn)this.queryEntity("getAutoTableColumnById",fieldId);
	}

	/**
	 * 查询进件自定义字段表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoAutoTableColumn> queryAutoTableColumnList(Map<String,Object> condition){
		return (List<IntoAutoTableColumn>)this.queryEntities("queryAutoTableColumnList", condition);
	}

	/**
	 * 分页查询进件自定义字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoAutoTableColumn> queryAutoTableColumnList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoAutoTableColumn>)this.queryEntities("queryAutoTableColumnList", page, condition);
	}

	/**
	 * 联表查询进件信息
	 * @return
	 */
	public List<IntoAutoTableColumnQuery> queryConnectAutoTable() {
		return (List<IntoAutoTableColumnQuery>)this.queryEntities("queryConnectAutoTable");
	}

	@Override
	public IntoAutoTableColumnQuery queryConnectAutoTableById(Integer fieldId) {
		return (IntoAutoTableColumnQuery)this.queryEntity("queryConnectAutoTableById",fieldId);
	}

	@Override
	public Integer queryMaxSortNo() {
		return this.queryInt("queryMaxSortNo");
	}

}
