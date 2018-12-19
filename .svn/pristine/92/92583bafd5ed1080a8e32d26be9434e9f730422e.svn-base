package banger.moduleIntf;

import banger.domain.config.IntoLoanUse;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.domain.html5.IntoFileInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * Created by panl on 2017/11/23
 * 进件客户表访问接口（外部接口）
 */
public interface IIntoCustomersProvider {


	/**
	 * 通过主键得到进件附件信息
	 * @param ID 主键Id
	 */
	IntoFileInfo getFileInfoById(Integer id);

	/**
	 * 更新客户的预申请贷款LoanId
	 * @param applyId
	 * @param loanId
	 */
	void updateIntoCustomerLoanId(Integer applyId,Integer loanId);




	/**
	 * App端
	 * 客户经理分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoCustApplyInfoQuery> IntoAppCustApplyInfoMemberList(Map<String, Object> condition, IPageSize page);

	/**
	 * APP端
	 * 通过主键得到进件申请信息表
	 * @param APPLY_ID 主键Id
	 */
	IntoCustApplyInfoQuery getAppCustApplyInfoById(Integer applyId);

	/**
	 * 通过主键得到进件贷款用途
	 * @param USE_ID 主键Id
	 */
	IntoLoanUse getLoanUseById(Integer useId);

	/**
	 * 查询进件附件信息
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoFileInfo> queryFileInfoList(Map<String, Object> condition);

	/**
	 * 通过主键删除进件客户表
	 * @param applyId 主键Id
	 */
	void deleteIntoCustomerById(Integer applyId);


}
