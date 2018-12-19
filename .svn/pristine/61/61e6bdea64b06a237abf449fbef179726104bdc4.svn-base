package banger.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IInfoAddedClassDao;
import banger.service.intf.IInfoAddedClassService;
import banger.domain.loan.LoanInfoAddedClass;

/**
 * 贷款资料附件分类表业务访问类
 */
@Repository
public class InfoAddedClassService implements IInfoAddedClassService {

	@Autowired
	private IInfoAddedClassDao infoAddedClassDao;

	/**
	 * 新增贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertInfoAddedClass(LoanInfoAddedClass infoAddedClass,Integer loginUserId){
		infoAddedClass.setCreateUser(loginUserId);
		infoAddedClass.setCreateDate(DateUtil.getCurrentDate());
		infoAddedClass.setUpdateUser(loginUserId);
		infoAddedClass.setUpdateDate(DateUtil.getCurrentDate());
		this.infoAddedClassDao.insertInfoAddedClass(infoAddedClass);
	}

	/**
	 *修改贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateInfoAddedClass(LoanInfoAddedClass infoAddedClass,Integer loginUserId){
		infoAddedClass.setUpdateUser(loginUserId);
		infoAddedClass.setUpdateDate(DateUtil.getCurrentDate());
		this.infoAddedClassDao.updateInfoAddedClass(infoAddedClass);
	}

	/**
	 * 通过主键删除贷款资料附件分类表
	 * @param CLASS_ID 主键Id
	 */
	public void deleteInfoAddedClassById(Integer classId){
		this.infoAddedClassDao.deleteInfoAddedClassById(classId);
	}

	/**
	 * 通过主键得到贷款资料附件分类表
	 * @param CLASS_ID 主键Id
	 */
	public LoanInfoAddedClass getInfoAddedClassById(Integer classId){
		return this.infoAddedClassDao.getInfoAddedClassById(classId);
	}

	/**
	 * 查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition){
		return this.infoAddedClassDao.queryInfoAddedClassList(condition);
	}

	/**
	 * 分页查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition,IPageSize page){
		return this.infoAddedClassDao.queryInfoAddedClassList(condition,page);
	}

    @Override
    public Integer queryMaxOrderNumByOwnerId(Integer ownerId) {
		return this.infoAddedClassDao.queryMaxOrderNumByOwnerId(ownerId);
    }

	/**
	 * 上移下移
	 * @param classId
	 * @param type
	 * @param ownerId
	 */
    public void moveClassOrderNo(Integer classId, String type, Integer ownerId) {
		List<LoanInfoAddedClass> list = infoAddedClassDao.queryAllClassOrder(ownerId);
		LoanInfoAddedClass moveCol = null;
		LoanInfoAddedClass changeCol = null;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClassId().intValue() == classId.intValue()) {
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
			int sortno = moveCol.getSortNo();
			moveCol.setSortNo(changeCol.getSortNo());
			changeCol.setSortNo(sortno);
			infoAddedClassDao.updateInfoAddedClass(changeCol);
			infoAddedClassDao.updateInfoAddedClass(moveCol);
		}

	}

    public List<LoanInfoAddedClass> queryInfoAddedclassOrder(Integer ownerId) {
    	return this.infoAddedClassDao.queryAllClassOrder(ownerId);
    }

}
