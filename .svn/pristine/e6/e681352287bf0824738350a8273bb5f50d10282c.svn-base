package banger.dao.impl;

import banger.dao.intf.IIntentCustomerDao;
import banger.domain.product.ProdIntentCustomer;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 意向客户列表数据访问类
 */
@Repository
public class IntentCustomerDao extends PageSizeDao<ProdIntentCustomer> implements IIntentCustomerDao {

	/**
	 * 新增意向客户列表
	 * @param intentCustomer 实体对像
	 */
	public void insertIntentCustomer(ProdIntentCustomer intentCustomer){
		intentCustomer.setPicId(this.newId().intValue());
		this.execute("insertIntentCustomer",intentCustomer);
	}

	/**
	 *修改意向客户列表
	 * @param intentCustomer 实体对像
	 */
	public void updateIntentCustomer(ProdIntentCustomer intentCustomer){
		this.execute("updateIntentCustomer",intentCustomer);
	}

	/**
	 * 通过主键删除意向客户列表
	 * @param picId 主键Id
	 */
	public void deleteIntentCustomerById(Integer picId){
		this.execute("deleteIntentCustomerById",picId);
	}

	/**
	 * 通过主键得到意向客户列表
	 * @param picId 主键Id
	 */
	public ProdIntentCustomer getIntentCustomerById(Integer picId){
		return (ProdIntentCustomer)this.queryEntity("getIntentCustomerById",picId);
	}

	/**
	 * 查询意向客户列表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProdIntentCustomer> queryIntentCustomerList(Map<String,Object> condition){
		return (List<ProdIntentCustomer>)this.queryEntities("queryIntentCustomerList", condition);
	}

	/**
	 * 分页查询意向客户列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ProdIntentCustomer_Query> queryIntentCustomerList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ProdIntentCustomer_Query>)this.queryEntities("queryIntentCustomerList", page, condition);
	}

	@Override
	public Integer queryIntentCustomerCount(Map<String, Object> condition) {
		return this.queryInt("queryIntentCustomerCount",condition);
	}

    @Override
    public IPageList<ProdIntentCustomer_Query> queryIntentCustomerListAll(Map<String, Object> condition, IPageSize page) {
		return (IPageList<ProdIntentCustomer_Query>)this.queryEntities("queryIntentCustomerListAll", page, condition);
	}

    @Override
	public void deleteIntentCustomerByProdId(Integer prodId) {
		this.execute("deleteIntentCustomerByProdId",prodId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IPageList<ProdIntentCustomer_Query> queryIntentCustomerListApp(Map<String, Object> condition, IPageSize page) {
		return (IPageList<ProdIntentCustomer_Query>) this.queryEntities("queryIntentCustomerList", page, condition);

	}

}
