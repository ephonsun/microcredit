package banger.dao.impl;

import banger.dao.intf.IContractTemplateTypeDao;
import banger.domain.loan.contract.LoanContractTemplateType;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class ContractTemplateTypeDao extends PageSizeDao<LoanContractTemplateType> implements IContractTemplateTypeDao {

	/**
	 * 新增
	 * @param contractTemplateType 实体对像
	 */
	public void insertContractTemplateType(LoanContractTemplateType contractTemplateType){
		contractTemplateType.setId(this.newId().intValue());
		this.execute("insertContractTemplateType",contractTemplateType);
	}

	/**
	 *修改
	 * @param contractTemplateType 实体对像
	 */
	public void updateContractTemplateType(LoanContractTemplateType contractTemplateType){
		this.execute("updateContractTemplateType",contractTemplateType);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteContractTemplateTypeById(Integer id){
		this.execute("deleteContractTemplateTypeById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public LoanContractTemplateType getContractTemplateTypeById(Integer id){
		return (LoanContractTemplateType)this.queryEntity("getContractTemplateTypeById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanContractTemplateType> queryContractTemplateTypeList(Map<String,Object> condition){
		return (List<LoanContractTemplateType>)this.queryEntities("queryContractTemplateTypeList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanContractTemplateType> queryContractTemplateTypeList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanContractTemplateType>)this.queryEntities("queryContractTemplateTypeList", page, condition);
	}

}
