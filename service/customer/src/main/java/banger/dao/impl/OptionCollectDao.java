package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IOptionCollectDao;
import banger.domain.customer.CustOptionCollect;

/**
 * 客户项选收集数据访问类
 */
@Repository
public class OptionCollectDao extends PageSizeDao<CustOptionCollect> implements IOptionCollectDao {

	/**
	 * 新增客户项选收集
	 * @param optionCollect 实体对像
	 */
	public void insertOptionCollect(CustOptionCollect optionCollect){
		optionCollect.setId(this.newId().intValue());
		this.execute("insertOptionCollect",optionCollect);
	}

	/**
	 *修改客户项选收集
	 * @param optionCollect 实体对像
	 */
	public void updateOptionCollect(CustOptionCollect optionCollect){
		this.execute("updateOptionCollect",optionCollect);
	}

	/**
	 * 通过主键删除客户项选收集
	 * @param id 主键Id
	 */
	public void deleteOptionCollectById(Integer id){
		this.execute("deleteOptionCollectById",id);
	}

	/**
	 * 通过主键得到客户项选收集
	 * @param id 主键Id
	 */
	public CustOptionCollect getOptionCollectById(Integer id){
		return (CustOptionCollect)this.queryEntity("getOptionCollectById",id);
	}

	/**
	 * 查询客户项选收集
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustOptionCollect> queryOptionCollectList(Map<String,Object> condition){
		return (List<CustOptionCollect>)this.queryEntities("queryOptionCollectList", condition);
	}

	/**
	 * 分页查询客户项选收集
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustOptionCollect> queryOptionCollectList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustOptionCollect>)this.queryEntities("queryOptionCollectList", page, condition);
	}

	/**
	 * 得到客户收集选项
	 */
	public List<CustOptionCollect> getCustomerOptionById(Integer customerId){
		return (List<CustOptionCollect>)this.queryEntities("getCustomerOptionById", customerId);
	}

}
