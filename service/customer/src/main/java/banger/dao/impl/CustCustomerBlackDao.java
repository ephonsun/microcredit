package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustCustomerBlackDao;
import banger.domain.customer.CustCustomerBlack;
import banger.domain.customer.CustCustomerBlackQuery;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 客户黑名单表数据访问类
 */
@Repository
public class CustCustomerBlackDao extends PageSizeDao<CustCustomerBlack> implements ICustCustomerBlackDao {

	/**
	 * 新增客户黑名单表
	 * @param custCustomerBlack 实体对像
	 */
	public void insertCustCustomerBlack(CustCustomerBlack custCustomerBlack){
		custCustomerBlack.setCustomerBlackId(this.newId().intValue());
		this.execute("insertCustCustomerBlack",custCustomerBlack);
	}

	/**
	 *修改客户黑名单表
	 * @param custCustomerBlack 实体对像
	 */
	public void updateCustCustomerBlack(CustCustomerBlack custCustomerBlack){
		this.execute("updateCustCustomerBlack",custCustomerBlack);
	}

	/**
	 * 通过主键删除客户黑名单表
	 * @param customerBlackId 主键Id
	 */
	public void deleteCustCustomerBlackById(Integer customerBlackId){
		this.execute("deleteCustCustomerBlackById",customerBlackId);
	}

	/**
	 * 通过主键得到客户黑名单表
	 * @param customerBlackId 主键Id
	 */
	public CustCustomerBlack getCustCustomerBlackById(Integer customerBlackId){
		return (CustCustomerBlack)this.queryEntity("getCustCustomerBlackById",customerBlackId);
	}

	/**
	 * 查询客户黑名单表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustCustomerBlack> queryCustCustomerBlackList(Map<String,Object> condition){
		return (List<CustCustomerBlack>)this.queryEntities("queryCustCustomerBlackList", condition);
	}

	/**
	 * 分页查询客户黑名单表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustCustomerBlackQuery> queryCustCustomerBlackList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustCustomerBlackQuery>)this.queryEntities("queryCustomerBlackApproveList", page, condition);
	}

	/**
	 * 查询待审和审合通过，并且未删除的黑名单记录
	 */
	public Integer getCustomerBlackCount(String customerName,String cardType,String cardNo){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("customerName",customerName);
		condition.put("cardType",cardType);
		condition.put("cardNo",cardNo);
		return this.queryInt("getCustomerBlackCount",condition);
	}

}
