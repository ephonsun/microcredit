package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IScoreDetailInfoDao;
import banger.service.intf.IScoreDetailInfoService;
import banger.domain.loan.LoanScoreDetailInfo;

/**
 * 贷款评分明细表业务访问类
 */
@Repository
public class ScoreDetailInfoService implements IScoreDetailInfoService {

	@Autowired
	private IScoreDetailInfoDao scoreDetailInfoDao;

	/**
	 * 新增贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo,Integer loginUserId){
		this.scoreDetailInfoDao.insertScoreDetailInfo(scoreDetailInfo);
	}

	/**
	 *修改贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo,Integer loginUserId){
		this.scoreDetailInfoDao.updateScoreDetailInfo(scoreDetailInfo);
	}

	/**
	 * 通过主键删除贷款评分明细表
	 * @param ID 主键Id
	 */
	public void deleteScoreDetailInfoById(Integer id){
		this.scoreDetailInfoDao.deleteScoreDetailInfoById(id);
	}

	/**
	 * 通过主键得到贷款评分明细表
	 * @param ID 主键Id
	 */
	public LoanScoreDetailInfo getScoreDetailInfoById(Integer id){
		return this.scoreDetailInfoDao.getScoreDetailInfoById(id);
	}

	/**
	 * 查询贷款评分明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanScoreDetailInfo> queryScoreDetailInfoList(Map<String,Object> condition){
		return this.scoreDetailInfoDao.queryScoreDetailInfoList(condition);
	}

	/**
	 * 分页查询贷款评分明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanScoreDetailInfo> queryScoreDetailInfoList(Map<String,Object> condition,IPageSize page){
		return this.scoreDetailInfoDao.queryScoreDetailInfoList(condition,page);
	}

	@Override
	public void deleteScoreDetailInfoByLoanId(Integer loanId) {
		this.scoreDetailInfoDao.deleteScoreDetailInfoByLoanId(loanId);
	}
}
