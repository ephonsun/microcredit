package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.ILoanSurveryFlowProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITypeDao;
import banger.domain.loan.LoanType;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.ITypeService;

/**
 * 贷款类型业务访问类
 */
@Repository
public class TypeService implements ITypeService {

	@Autowired
	private ITypeDao typeDao;

	/**
	 * 新增贷款类型
	 *
	 * @param type
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void insertType(LoanType type, Integer loginUserId) {

		type.setCreateUser(loginUserId);
		type.setCreateDate(DateUtil.getCurrentDate());
		type.setUpdateUser(loginUserId);
		type.setUpdateDate(DateUtil.getCurrentDate());
		this.typeDao.insertType(type);
	}

	/**
	 * 修改贷款类型
	 *
	 * @param type
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void updateType(LoanType type, Integer loginUserId) {
		type.setUpdateUser(loginUserId);
		type.setUpdateDate(DateUtil.getCurrentDate());
		this.typeDao.updateType(type);
	}

	/**
	 * 通过主键删除贷款类型
	 *
	 * @param TYPE_ID
	 *            主键Id
	 */
	public void deleteTypeById(Integer typeId) {
		this.typeDao.deleteTypeById(typeId);
	}

	/**
	 * 通过主键得到贷款类型
	 *
	 * @param TYPE_ID
	 *            主键Id
	 */
	public LoanType getTypeById(Integer typeId) {
		return this.typeDao.getTypeById(typeId);
	}

	/**
	 * 查询贷款类型
	 */
	public List<LoanType> getAllLoanTypeList(){
		return this.typeDao.getAllLoanTypeList();
	}

	/**
	 * 查询贷款类型
	 *
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public List<LoanType> queryTypeList(Map<String, Object> condition) {
		return this.typeDao.queryTypeList(condition);
	}

	/**
	 * 查询贷款类型
	 *
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public List<LoanType> queryContractTypeList(Map<String, Object> condition) {
		return this.typeDao.queryTypeList(condition);
	}

	/**
	 * 分页查询贷款类型
	 *
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	public IPageList<LoanType> queryTypeList(Map<String, Object> condition, IPageSize page) {
		return this.typeDao.queryTypeList(condition, page);
	}

	/**
	 * 获取贷款类型最大排序号
	 *
	 * @return
	 */
	public Integer queryMaxOrderNum() {
		return this.typeDao.queryMaxOrderNum();
	}

	/**
	 * 获取贷款类型最大排序号
	 *
	 * @return
	 */
	public Integer queryContractMaxOrderNum() {
		return this.typeDao.queryContractMaxOrderNum();
	}

}
