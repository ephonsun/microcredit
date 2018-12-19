package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.config.AutoTableColumnQuery;
import banger.domain.config.IntoTemplatesField;
import banger.domain.config.ModeTemplateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITableColumnDao;
import banger.domain.config.AutoTableColumn;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IAutoFieldProvider;
import banger.service.intf.ITableColumnService;

/**
 * 业务访问类
 */
@Repository
public class TableColumnService implements ITableColumnService, IAutoFieldProvider {

	@Autowired
	private ITableColumnDao tableColumnDao;

	/**
	 * 新增
	 * 
	 * @param tableColumn
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void insertTableColumn(AutoTableColumn tableColumn, Integer loginUserId) {
		tableColumn.setCreateUser(loginUserId);
		tableColumn.setCreateDate(DateUtil.getCurrentDate());
		tableColumn.setUpdateUser(loginUserId);
		tableColumn.setUpdateDate(DateUtil.getCurrentDate());
		this.tableColumnDao.insertTableColumn(tableColumn);
	}


	/**
	 * @return
	 */
	public List<AutoTableColumn> getAllTableColumnList() {
		return tableColumnDao.getAllTableColumnList();
	}

	/**
	 * 修改
	 * 
	 * @param tableColumn
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void updateTableColumn(AutoTableColumn tableColumn, Integer loginUserId) {
		tableColumn.setUpdateUser(loginUserId);
		tableColumn.setUpdateDate(DateUtil.getCurrentDate());
		this.tableColumnDao.updateTableColumn(tableColumn);
	}

	/**
	 * 通过主键删除
	 *
	 *            主键Id
	 */
	public void deleteTableColumnById(Integer fieldId) {
		this.tableColumnDao.deleteTableColumnById(fieldId);
	}

	/**
	 * 通过主键得到
	 *
	 *            主键Id
	 */
	public AutoTableColumn getTableColumnById(Integer fieldId) {
		return this.tableColumnDao.getTableColumnById(fieldId);
	}

	/**
	 * 查询
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public List<AutoTableColumn> queryTableColumnList(Map<String, Object> condition) {
		return this.tableColumnDao.queryTableColumnList(condition);
	}

	/**
	 * 得到表字段
	 * @return
	 */
	public List<AutoTableColumn> getFieldListByTableName(String tableName){
		return this.tableColumnDao.getFieldListByTableName(tableName);
	}

	/**
	 * 通过表名和例名通到自定义字段
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public AutoTableColumn getTableColumnByTableAndColumn(String tableName,String columnName){
		return this.tableColumnDao.getTableColumnByTableAndColumn(tableName,columnName);
	}

	/**
	 * 分页查询
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	public IPageList<AutoTableColumn> queryTableColumnList(Map<String, Object> condition, IPageSize page) {
		return this.tableColumnDao.queryTableColumnList(condition, page);
	}

	/**
	 * 得到审核条件字段
	 * @return
	 */
	public List<AutoTableColumn> getConditionFieldList() {
		return this.tableColumnDao.getConditionFieldList();
	}

	@Override
	public List<AutoTableColumn> getAllActivedTableColumnList() {
		return tableColumnDao.getAllActivedTableColumnList();
	}

	/**
	 * 得到审核条件字段
	 * @param loanType
	 * @return
	 */
	public List<AutoTableColumn> getConditionFieldList(Integer loanType) {
		return this.tableColumnDao.getConditionFieldList(loanType);
	}

	/**
	 * 上移下移
	 */
	@Override
	public void moveTableColumnOrderNo(String type, Integer tableId, Integer fieldId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tableId", tableId);
		// 查出所集合
		List<AutoTableColumn> list = tableColumnDao.queryTableColumnByConditionOrder(condition);
		AutoTableColumn moveCol = null;
		AutoTableColumn changeCol = null;
		if (null == list || list.size() == 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFieldId().intValue() == fieldId.intValue()) {
				// 移动行
				moveCol = list.get(i);
				// 下移
				if ("moveDown".equals(type)) {
					int last = i + 1;
					// 如果不是最后一行
					if (last < list.size()) {
						changeCol = list.get(last);
					}
					// 上移
				} else if ("moveUp".equals(type)) {
					int pre = i - 1;
					if (i > 0) {
						changeCol = list.get(pre);
					}
				}
			}
		}
		// 对换排序号
		if (changeCol != null && moveCol != null) {
			int sortno = moveCol.getFieldSortno();
			moveCol.setFieldSortno(changeCol.getFieldSortno());
			changeCol.setFieldSortno(sortno);
			this.tableColumnDao.updateTableColumn(moveCol);
			this.tableColumnDao.updateTableColumn(changeCol);
		}
	}

    @Override
    public List<ModeTemplateField> queryModelTemplateInfoColumn(Map<String, Object> condition) {
		return this.tableColumnDao.queryModelTemplateInfoColumn(condition);
    }

	@Override
	public List<IntoTemplatesField> queryIntoTemplatesInfoColumn(Map<String, Object> condition) {
		return this.tableColumnDao.queryIntoTemplatesInfoColumn(condition);
	}

	@Override
	public List<AutoTableColumn> queryTableColumnListOfCust() {
		return tableColumnDao.queryTableColumnListOfCust();
	}
	/**
	 * 查询所有自定义字段数据包括表名描述
	 * @return
	 */
	@Override
	public List<AutoTableColumnQuery> getAllTableColumnQueryList() {
		return tableColumnDao.getAllTableColumnQueryList();
	}

}
