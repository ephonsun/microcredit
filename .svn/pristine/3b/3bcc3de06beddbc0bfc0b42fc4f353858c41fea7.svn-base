package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoAutoTableColumnQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoAutoTableColumn;

/**
 * 进件自定义字段表业务访问接口
 */
public interface IAutoTableColumnService {

	/**
	 * 新增进件自定义字段表
	 * @param autoTableColumn 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAutoTableColumn(IntoAutoTableColumn autoTableColumn, Integer loginUserId);

	/**
	 *修改进件自定义字段表
	 * @param autoTableColumn 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAutoTableColumn(IntoAutoTableColumn autoTableColumn, Integer loginUserId);

	/**
	 * 通过主键删除进件自定义字段表
	 * @param FIELD_ID 主键Id
	 */
	void deleteAutoTableColumnById(Integer fieldId);

	/**
	 * 通过主键得到进件自定义字段表
	 * @param FIELD_ID 主键Id
	 */
	IntoAutoTableColumn getAutoTableColumnById(Integer fieldId);

	/**
	 * 查询进件自定义字段表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoAutoTableColumn> queryAutoTableColumnList(Map<String, Object> condition);

	/**
	 * 分页查询进件自定义字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoAutoTableColumn> queryAutoTableColumnList(Map<String, Object> condition, IPageSize page);

	/**
	 * 联表查询进件自定义字段
	 */
	List<IntoAutoTableColumnQuery> queryConnectAutoTable();

	/**
	 * 联表查询
	 * 根据主键
	 */
	IntoAutoTableColumnQuery queryConnectAutoTableById(Integer fieldId);

	/**
	 * @Author panl
	 * @Date 2017/12/20 9:52
	 * 上移下移
	 */
	void moveIntoInfoFieldId(Integer fieldId,String type);

	/**
	 * 查询最大排序号
	 */
	Integer queryMaxSortNo();


}
