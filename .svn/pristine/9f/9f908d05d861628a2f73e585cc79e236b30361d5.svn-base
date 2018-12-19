package banger.service.intf;

import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 贷款资料附件对像分类表业务访问接口
 */
public interface IInfoAddedOwnerService {

	/**
	 * 新增贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner,Integer loginUserId);

	/**
	 *修改贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner,Integer loginUserId);

	/**
	 * 通过主键删除贷款资料附件对像分类表
	 * @param OWNER_ID 主键Id
	 */
	void deleteInfoAddedOwnerById(Integer ownerId);

	/**
	 * 通过主键得到贷款资料附件对像分类表
	 * @param OWNER_ID 主键Id
	 */
	LoanInfoAddedOwner getInfoAddedOwnerById(Integer ownerId);

	/**
	 * 查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition);

	/**
	 * 分页查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition,IPageSize page);

	/**
	 * 获取牌序号
	 * @return
	 */
    Integer getMaxSortNum();

	/**
	 * 上移下移
	 * @param ownerId
	 * @param type
	 */
    void moveOwnerOrderNo(Integer ownerId, String type);

	/**
	 * 排序列表
	 * @param condition
	 * @return
	 */
    List<LoanInfoAddedOwner> queryInfoAddedOwnerOrder();

}
