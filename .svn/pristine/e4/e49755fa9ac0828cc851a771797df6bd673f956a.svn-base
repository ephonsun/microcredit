package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IScoreDetailInfoDao;
import banger.domain.loan.LoanScoreDetailInfo;

/**
 * 贷款评分明细表数据访问类
 */
@Repository
public class ScoreDetailInfoDao extends PageSizeDao<LoanScoreDetailInfo> implements IScoreDetailInfoDao {

	/**
	 * 新增贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 */
	public void insertScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo){
		scoreDetailInfo.setId(this.newId().intValue());
		this.execute("insertScoreDetailInfo",scoreDetailInfo);
	}

	/**
	 *修改贷款评分明细表
	 * @param scoreDetailInfo 实体对像
	 */
	public void updateScoreDetailInfo(LoanScoreDetailInfo scoreDetailInfo){
		this.execute("updateScoreDetailInfo",scoreDetailInfo);
	}

	/**
	 * 通过主键删除贷款评分明细表
	 * @param id 主键Id
	 */
	public void deleteScoreDetailInfoById(Integer id){
		this.execute("deleteScoreDetailInfoById",id);
	}

	/**
	 * 通过主键得到贷款评分明细表
	 * @param id 主键Id
	 */
	public LoanScoreDetailInfo getScoreDetailInfoById(Integer id){
		return (LoanScoreDetailInfo)this.queryEntity("getScoreDetailInfoById",id);
	}

	/**
	 * 查询贷款评分明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanScoreDetailInfo> queryScoreDetailInfoList(Map<String,Object> condition){
		return (List<LoanScoreDetailInfo>)this.queryEntities("queryScoreDetailInfoList", condition);
	}

	/**
	 * 分页查询贷款评分明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanScoreDetailInfo> queryScoreDetailInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanScoreDetailInfo>)this.queryEntities("queryScoreDetailInfoList", page, condition);
	}

	@Override
	public void deleteScoreDetailInfoByLoanId(Integer loanId) {
		this.execute("deleteScoreDetailInfoByLoanId",loanId);
	}
}
