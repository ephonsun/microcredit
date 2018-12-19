package banger.service.impl;

import banger.dao.intf.IInfoAddedOwnerDao;
import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IInfoAddedOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 贷款资料附件对像分类表业务访问类
 */
@Repository
public class InfoAddedOwnerService implements IInfoAddedOwnerService {

	@Autowired
	private IInfoAddedOwnerDao infoAddedOwnerDao;

	/**
	 * 新增贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner,Integer loginUserId){
		infoAddedOwner.setCreateUser(loginUserId);
		infoAddedOwner.setCreateDate(DateUtil.getCurrentDate());
		infoAddedOwner.setUpdateUser(loginUserId);
		infoAddedOwner.setUpdateDate(DateUtil.getCurrentDate());
		this.infoAddedOwnerDao.insertInfoAddedOwner(infoAddedOwner);
	}

	/**
	 *修改贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner,Integer loginUserId){
		infoAddedOwner.setUpdateUser(loginUserId);
		infoAddedOwner.setUpdateDate(DateUtil.getCurrentDate());
		this.infoAddedOwnerDao.updateInfoAddedOwner(infoAddedOwner);
	}

	/**
	 * 通过主键删除贷款资料附件对像分类表
	 * @param OWNER_ID 主键Id
	 */
	public void deleteInfoAddedOwnerById(Integer ownerId){
		this.infoAddedOwnerDao.deleteInfoAddedOwnerById(ownerId);
	}

	/**
	 * 通过主键得到贷款资料附件对像分类表
	 * @param OWNER_ID 主键Id
	 */
	public LoanInfoAddedOwner getInfoAddedOwnerById(Integer ownerId){
		return this.infoAddedOwnerDao.getInfoAddedOwnerById(ownerId);
	}

	/**
	 * 查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition){
		return this.infoAddedOwnerDao.queryInfoAddedOwnerList(condition);
	}

	/**
	 * 分页查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition,IPageSize page){
		return this.infoAddedOwnerDao.queryInfoAddedOwnerList(condition,page);
	}

	/**
	 * 查询最大排序号
	 * @return
	 */
    public Integer getMaxSortNum() {
		return this.infoAddedOwnerDao.queryInfoAddedOwnerSortNum();
    }


	/**
	 * 上移下移
	 * @param ownerId
	 * @param type
	 */
	public void moveOwnerOrderNo(Integer ownerId, String type) {
		List<LoanInfoAddedOwner> list = infoAddedOwnerDao.queryAllOwnerOrder();
		LoanInfoAddedOwner moveCol = null;
		LoanInfoAddedOwner changeCol = null;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getOwnerId().intValue() == ownerId.intValue()) {
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
			infoAddedOwnerDao.updateInfoAddedOwner(changeCol);
			infoAddedOwnerDao.updateInfoAddedOwner(moveCol);
		}

    }

	/**
	 * 排序列表
	 * @param condition
	 * @return
	 */
	public List<LoanInfoAddedOwner> queryInfoAddedOwnerOrder() {
		return infoAddedOwnerDao.queryAllOwnerOrder();
	}


}
