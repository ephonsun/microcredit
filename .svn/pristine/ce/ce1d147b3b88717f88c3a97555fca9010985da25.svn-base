package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITypeRelTableDao;
import banger.domain.loan.LoanTypeRelTable;
import banger.domain.loan.LoanTypeRelTableExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.ITypeRelTableService;

/**
 * 贷款类型匹配表单业务访问类
 */
@Repository
public class TypeRelTableService implements ITypeRelTableService {

	@Autowired
	private ITypeRelTableDao typeRelTableDao;

	/**
	 * 新增贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void insertTypeRelTable(LoanTypeRelTable typeRelTable, Integer loginUserId) {
		typeRelTable.setCreateUser(loginUserId);
		typeRelTable.setCreateDate(DateUtil.getCurrentDate());
		typeRelTable.setUpdateUser(loginUserId);
		typeRelTable.setUpdateDate(DateUtil.getCurrentDate());
		this.typeRelTableDao.insertTypeRelTable(typeRelTable);
	}

	/**
	 * 修改贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void updateTypeRelTable(LoanTypeRelTable typeRelTable, Integer loginUserId) {
		typeRelTable.setUpdateUser(loginUserId);
		typeRelTable.setUpdateDate(DateUtil.getCurrentDate());
		this.typeRelTableDao.updateTypeRelTable(typeRelTable);
	}

	/**
	 * 通过主键删除贷款类型匹配表单
	 * 
	 * @param ID
	 *            主键Id
	 */
	public void deleteTypeRelTableById(Integer id) {
		this.typeRelTableDao.deleteTypeRelTableById(id);
	}

	/**
	 * 通过主键得到贷款类型匹配表单
	 * 
	 * @param ID
	 *            主键Id
	 */
	public LoanTypeRelTable getTypeRelTableById(Integer id) {
		return this.typeRelTableDao.getTypeRelTableById(id);
	}

	/**
	 * 查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public List<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition) {
		return this.typeRelTableDao.queryTypeRelTableList(condition);
	}

	/**
	 * 分页查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	public IPageList<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition, IPageSize page) {
		return this.typeRelTableDao.queryTypeRelTableList(condition, page);
	}

	/**
	 * 根据条件查询自定义表单
	 */
	public List<LoanTypeRelTable> queryAutoTableInfoByCondition(Map<String, Object> condition) {
		return this.typeRelTableDao.queryAutoTableInfoByCondition(condition);
	}

	/**
	 * 查询最大排序号
	 */
	public Integer queryLoanTypeRelTableMaxOrderNum(Integer typeId, String precType) {
		return this.typeRelTableDao.queryLoanTypeRelTableMaxOrderNum(typeId, precType);
	}

	/**
	 * 上移下移
	 */
	public void moveTableInfoOrderNo(String type, Integer typeId, String precType, Integer tableId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("typeId", typeId);
		condition.put("precType", precType);
		// 查出所集合
		List<LoanTypeRelTable> list = typeRelTableDao.queryAutoTableInfoByConditionOrder(condition);
		LoanTypeRelTable moveCol = null;
		LoanTypeRelTable changeCol = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTableId().intValue() == tableId.intValue()) {
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
			int sortno = moveCol.getSortno();
			moveCol.setSortno(changeCol.getSortno());
			changeCol.setSortno(sortno);
			this.typeRelTableDao.updateTypeRelTable(changeCol);
			this.typeRelTableDao.updateTypeRelTable(moveCol);
		}

	}

	/**
	 * 
	 * @param typeID
	 *            贷款类型id
	 * @param precType
	 *            表单名
	 * @return
	 */
	public List<LoanTypeRelTableExtend> queryAutoTableInfoByCondition(Integer typeId, String precType) {
		return typeRelTableDao.queryAutoTableInfoByCondition(typeId, precType);
	}

}
