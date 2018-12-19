package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanInfoAddedClass;

/**
 * 贷款资料附件分类表数据访问接口
 */
public interface IInfoAddedClassDao {

	/**
	 * 新增贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 */
	void insertInfoAddedClass(LoanInfoAddedClass infoAddedClass);

	/**
	 *修改贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 */
	void updateInfoAddedClass(LoanInfoAddedClass infoAddedClass);

	/**
	 * 通过主键删除贷款资料附件分类表
	 * @param classId 主键Id
	 */
	void deleteInfoAddedClassById(Integer classId);

	/**
	 * 通过主键得到贷款资料附件分类表
	 * @param classId 主键Id
	 */
	LoanInfoAddedClass getInfoAddedClassById(Integer classId);

	/**
	 *
	 * @param ownerIds
	 * @return
	 */
	List<LoanInfoAddedClass> queryInfoAddedClassListByOwnerIds(String ownerIds);

	/**
	 * 查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition);
	
	/**
	 * 查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedClass> getAllInfoAddedClassList();

	/**
	 * 分页查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition,IPageSize page);

	Integer queryMaxOrderNumByOwnerId(Integer ownerId);

	/**
	 * 查询排序列表
	 * @return
	 * @param ownerId
	 */
    List<LoanInfoAddedClass> queryAllClassOrder(Integer ownerId);
}
