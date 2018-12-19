package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IProcessTypeDao;
import banger.service.intf.IProcessTypeService;
import banger.domain.loan.LoanProcessType;

/**
 * 贷款流程类型业务访问类
 */
@Repository
public class ProcessTypeService implements IProcessTypeService {

	@Autowired
	private IProcessTypeDao processTypeDao;

	/**
	 * 新增贷款流程类型
	 * @param processType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertProcessType(LoanProcessType processType,Integer loginUserId){
		this.processTypeDao.insertProcessType(processType);
	}

	/**
	 *修改贷款流程类型
	 * @param processType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProcessType(LoanProcessType processType,Integer loginUserId){
		this.processTypeDao.updateProcessType(processType);
	}

	/**
	 * 通过主键删除贷款流程类型
	 * @param ID 主键Id
	 */
	public void deleteProcessTypeById(Integer id){
		this.processTypeDao.deleteProcessTypeById(id);
	}

	/**
	 * 通过主键得到贷款流程类型
	 * @param ID 主键Id
	 */
	public LoanProcessType getProcessTypeById(Integer id){
		return this.processTypeDao.getProcessTypeById(id);
	}

	/**
	 * 查询贷款流程类型
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanProcessType> queryProcessTypeList(Map<String,Object> condition){
		return this.processTypeDao.queryProcessTypeList(condition);
	}

	/**
	 * 分页查询贷款流程类型
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanProcessType> queryProcessTypeList(Map<String,Object> condition,IPageSize page){
		return this.processTypeDao.queryProcessTypeList(condition,page);
	}

}
