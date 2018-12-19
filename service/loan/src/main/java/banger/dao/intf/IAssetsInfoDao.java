package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsInfo;

/**
 * 资产负债表信息表数据访问接口
 */
public interface IAssetsInfoDao {

	/**
	 * 新增资产负债表信息表
	 * @param assetsInfo 实体对像
	 */
	void insertAssetsInfo(LoanAssetsInfo assetsInfo);

	/**
	 *修改资产负债表信息表
	 * @param assetsInfo 实体对像
	 */
	void updateAssetsInfo(LoanAssetsInfo assetsInfo);

	/**
	 * 通过主键删除资产负债表信息表
	 * @param id 主键Id
	 */
	void deleteAssetsInfoById(Integer id);

	/**
	 * 通过主键得到资产负债表信息表
	 * @param id 主键Id
	 */
	LoanAssetsInfo getAssetsInfoById(Integer id);

	/**
	 * 通过主键得到资产负债表信息表
	 * @param loanId 主键loanId
	 */
	LoanAssetsInfo getAssetsInfoByLoanId(Integer loanId);

	/**
	 * 查询资产负债表信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAssetsInfo> queryAssetsInfoList(Map<String, Object> condition);

	/**
	 * 分页查询资产负债表信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAssetsInfo> queryAssetsInfoList(Map<String, Object> condition, IPageSize page);


}
