package banger.service.impl;

import banger.dao.intf.IAutoTableColumnDao;
import banger.domain.config.IntoAutoTableColumn;
import banger.domain.config.IntoAutoTableColumnQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IAutoTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件自定义字段表业务访问类
 */
@Repository
public class AutoTableColumnService implements IAutoTableColumnService {

	@Autowired
	private IAutoTableColumnDao autoTableColumnDao;

	/**
	 * 新增进件自定义字段表
	 * @param autoTableColumn 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAutoTableColumn(IntoAutoTableColumn autoTableColumn,Integer loginUserId){
		autoTableColumn.setCreateUser(loginUserId);
		autoTableColumn.setCreateDate(DateUtil.getCurrentDate());
		autoTableColumn.setUpdateUser(loginUserId);
		autoTableColumn.setUpdateDate(DateUtil.getCurrentDate());
		this.autoTableColumnDao.insertAutoTableColumn(autoTableColumn);
	}

	/**
	 *修改进件自定义字段表
	 * @param autoTableColumn 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAutoTableColumn(IntoAutoTableColumn autoTableColumn,Integer loginUserId){
		autoTableColumn.setUpdateUser(loginUserId);
		autoTableColumn.setUpdateDate(DateUtil.getCurrentDate());
		this.autoTableColumnDao.updateAutoTableColumn(autoTableColumn);
	}

	/**
	 * 通过主键删除进件自定义字段表
	 * @param FIELD_ID 主键Id
	 */
	public void deleteAutoTableColumnById(Integer fieldId){
		this.autoTableColumnDao.deleteAutoTableColumnById(fieldId);
	}

	/**
	 * 通过主键得到进件自定义字段表
	 * @param FIELD_ID 主键Id
	 */
	public IntoAutoTableColumn getAutoTableColumnById(Integer fieldId){
		return this.autoTableColumnDao.getAutoTableColumnById(fieldId);
	}

	/**
	 * 查询进件自定义字段表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoAutoTableColumn> queryAutoTableColumnList(Map<String,Object> condition){
		return this.autoTableColumnDao.queryAutoTableColumnList(condition);
	}

	/**
	 * 分页查询进件自定义字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoAutoTableColumn> queryAutoTableColumnList(Map<String,Object> condition,IPageSize page){
		return this.autoTableColumnDao.queryAutoTableColumnList(condition,page);
	}

	/**
	 * 联表查询进件信息
	 * @return
	 */
	public List<IntoAutoTableColumnQuery> queryConnectAutoTable() {
		return this.autoTableColumnDao.queryConnectAutoTable();
	}

	@Override
	public IntoAutoTableColumnQuery queryConnectAutoTableById(Integer fieldId) {
		return this.autoTableColumnDao.queryConnectAutoTableById(fieldId);
	}

	/**
	 * 上移下移
	 * @param fieldId
	 * @param type
	 */
	public void moveIntoInfoFieldId(Integer fieldId, String type) {
//		Map<String, Object> condition = new HashMap<String, Object>();
		List<IntoAutoTableColumnQuery> list = autoTableColumnDao.queryConnectAutoTable();

		IntoAutoTableColumnQuery movCol = null;
		IntoAutoTableColumnQuery changeCol = null;

		for (int i=0;i<list.size();i++){
			if(list.get(i).getFieldId().intValue() == fieldId.intValue()){
				movCol = list.get(i);
				//下移
				if("moveDown".equals(type)){
					int last = i + 1;
					if(last < list.size()){
						changeCol = list.get(last);
					}
				}else if("moveUp".equals(type)){ //上移
					int first = i - 1;
					if (i > 0){
						changeCol = list.get(first);
					}
				}
			}
		}

		if(movCol != null && changeCol != null){
			int sortNo = movCol.getFieldSortno();
			movCol.setFieldSortno(changeCol.getFieldSortno());
			changeCol.setFieldSortno(sortNo);

			autoTableColumnDao.updateAutoTableColumnQuery(movCol);
			autoTableColumnDao.updateAutoTableColumnQuery(changeCol);
		}
	}

	/**
	 * 查询最大排序号
	 * @return
	 */
	public Integer queryMaxSortNo() {
		return this.autoTableColumnDao.queryMaxSortNo();
	}

}
