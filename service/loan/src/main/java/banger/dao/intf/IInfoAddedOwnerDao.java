package banger.dao.intf;

import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 贷款资料附件对像分类表数据访问接口
 */
public interface IInfoAddedOwnerDao {

	/**
	 * 新增贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 */
	void insertInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner);

	/**
	 *修改贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 */
	void updateInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner);

	/**
	 * 通过主键删除贷款资料附件对像分类表
	 * @param ownerId 主键Id
	 */
	void deleteInfoAddedOwnerById(Integer ownerId);

	/**
	 * 通过主键得到贷款资料附件对像分类表
	 * @param ownerId 主键Id
	 */
	LoanInfoAddedOwner getInfoAddedOwnerById(Integer ownerId);

	/**
	 *
	 * @param ownerIds
	 * @return
	 */
	List<LoanInfoAddedOwner> queryInfoAddedOwnerListByOwnerIds(String ownerIds);

	/**
	 * 查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition);
	
	/**
	 * 查询贷款资料附件对像分类表
	 * @return
	 */
	List<LoanInfoAddedOwner> getAllInfoAddedOwnerList();

	/**
	 * 分页查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition,IPageSize page);

    Integer queryInfoAddedOwnerSortNum();

	/**
	 * 上移下移
	 */
    List<LoanInfoAddedOwner> queryAllOwnerOrder();


}
