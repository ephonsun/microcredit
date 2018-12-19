package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanScoreDetailInfo;

/**
 * 贷款评分明细表业务访问接口
 */
public interface IScoreDetailInfoService {

	/**
	 * 新增贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo, Integer loginUserId);

	/**
	 *修改贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo, Integer loginUserId);

	/**
	 * 通过主键删除贷款评分明细表
	 * @param ID 主键Id
	 */
	void deleteScoreDetailInfoById(Integer id);

	/**
	 * 通过主键得到贷款评分明细表
	 * @param ID 主键Id
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
