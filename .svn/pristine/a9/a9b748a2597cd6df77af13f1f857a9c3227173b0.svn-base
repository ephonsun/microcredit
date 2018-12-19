package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoLoanUseExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoLoanUse;

/**
 * 进件贷款用途业务访问接口
 */
public interface ILoanUseService {

	/**
	 * 新增进件贷款用途
	 * @param loanUse 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertLoanUse(IntoLoanUse loanUse, Integer loginUserId);

	/**
	 *修改进件贷款用途
	 * @param loanUse 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateLoanUse(IntoLoanUse loanUse, Integer loginUserId);

	/**
	 * 通过主键删除进件贷款用途
	 * @param USE_ID 主键Id
	 */
	void deleteLoanUseById(Integer useId);

	/**
	 * 通过主键得到进件贷款用途
	 * @param USE_ID 主键Id
	 */
	IntoLoanUse getLoanUseById(Integer useId);

	/**
	 * 查询进件贷款用途
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoLoanUse> queryLoanUseList(Map<String, Object> condition);

	/**
	 * 分页查询进件贷款用途
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoLoanUse> queryLoanUseList(Map<String, Object> condition, IPageSize page);

	/**
	 * 关联查询进件贷款用途列表
	 * @return
	 */
	List<IntoLoanUseExtend> queryIntoLoanUseList();

}
