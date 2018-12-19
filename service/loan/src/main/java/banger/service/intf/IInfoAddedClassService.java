package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanInfoAddedClass;

/**
 * 贷款资料附件分类表业务访问接口
 */
public interface IInfoAddedClassService {

	/**
	 * 新增贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertInfoAddedClass(LoanInfoAddedClass infoAddedClass,Integer loginUserId);

	/**
	 *修改贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateInfoAddedClass(LoanInfoAddedClass infoAddedClass,Integer loginUserId);

	/**
	 * 通过主键删除贷款资料附件分类表
	 * @param CLASS_ID 主键Id
	 */
	void deleteInfoAddedClassById(Integer classId);

	/**
	 * 通过主键得到贷款资料附件分类表
	 * @param CLASS_ID 主键Id
	 */
	LoanInfoAddedClass getInfoAddedClassById(Integer classId);

	/**
	 * 查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition);

	/**
	 * 分页查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition,IPageSize page);

	Integer queryMaxOrderNumByOwnerId(Integer ownerId);

	/**
	 * 上移下移
     * @param classId
     * @param type
     * @param ownerId
     */
    void moveClassOrderNo(Integer classId, String type, Integer ownerId);

    List<LoanInfoAddedClass> queryInfoAddedclassOrder(Integer condition);
}
