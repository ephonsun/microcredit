package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanScoreDetailInfo;

/**
 * 贷款评分明细表数据访问接口
 */
public interface IScoreDetailInfoDao {

	/**
	 * 新增贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 */
	void insertScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo);

	/**
	 *修改贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 */
	void updateScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo);

	/**
	 * 通过主键删除贷款评分明细表
	 * @param id 主键Id
	 */
	void deleteScoreDetailInfoById(Integer id);

	/**
	 * 通过主键得到贷款评分明细表
	 * @param id 主键Id
	 */
	LoanScoreDetailInfo getScoreDetailInfoById(Integer id);

	/**
	 * 查询贷款评分明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanScoreDetailInfo> queryScoreDetailInfoList(Map<String, Object> condition);

	/**
	 * 分页查询贷款评分明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanScoreDetailInfo> queryScoreDetailInfoList(Map<String, Object> condition, IPageSize page);

	void deleteScoreDetailInfoByLoanId(Integer loanId);
}
