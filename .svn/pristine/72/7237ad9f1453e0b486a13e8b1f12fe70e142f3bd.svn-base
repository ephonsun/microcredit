package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProcessType;

/**
 * 贷款流程类型业务访问接口
 */
public interface IProcessTypeService {

	/**
	 * 新增贷款流程类型
	 * @param processType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertProcessType(LoanProcessType processType,Integer loginUserId);

	/**
	 *修改贷款流程类型
	 * @param processType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateProcessType(LoanProcessType processType,Integer loginUserId);

	/**
	 * 通过主键删除贷款流程类型
	 * @param ID 主键Id
	 */
	void deleteProcessTypeById(Integer id);

	/**
	 * 通过主键得到贷款流程类型
	 * @param ID 主键Id
	 */
	LoanProcessType getProcessTypeById(Integer id);

	/**
	 * 查询贷款流程类型
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanProcessType> queryProcessTypeList(Map<String,Object> condition);

	/**
	 * 分页查询贷款流程类型
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanProcessType> queryProcessTypeList(Map<String,Object> condition,IPageSize page);

}
