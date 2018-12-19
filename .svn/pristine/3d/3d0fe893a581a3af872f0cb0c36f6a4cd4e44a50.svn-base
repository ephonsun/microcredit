package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProcessTypeDao;
import banger.domain.loan.LoanProcessType;

/**
 * 贷款流程类型数据访问类
 */
@Repository
public class ProcessTypeDao extends PageSizeDao<LoanProcessType> implements IProcessTypeDao {

	/**
	 * 新增贷款流程类型
	 * @param processType 实体对像
	 */
	public void insertProcessType(LoanProcessType processType){
		processType.setId(this.newId().intValue());
		this.execute("insertProcessType",processType);
	}

	/**
	 *修改贷款流程类型
	 * @param processType 实体对像
	 */
	public void updateProcessType(LoanProcessType processType){
		this.execute("updateProcessType",processType);
	}

	/**
	 * 通过主键删除贷款流程类型
	 * @param id 主键Id
	 */
	public void deleteProcessTypeById(Integer id){
		this.execute("deleteProcessTypeById",id);
	}

	/**
	 * 通过主键得到贷款流程类型
	 * @param id 主键Id
	 */
	public LoanProcessType getProcessTypeById(Integer id){
		return (LoanProcessType)this.queryEntity("getProcessTypeById",id);
	}

	/**
	 * 查询贷款流程类型
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanProcessType> queryProcessTypeList(Map<String,Object> condition){
		return (List<LoanProcessType>)this.queryEntities("queryProcessTypeList", condition);
	}

	/**
	 * 分页查询贷款流程类型
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanProcessType> queryProcessTypeList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanProcessType>)this.queryEntities("queryProcessTypeList", page, condition);
	}

}
