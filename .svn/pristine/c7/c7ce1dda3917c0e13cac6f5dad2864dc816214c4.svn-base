package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IModeScoreInfoProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IScoreInfoDao;
import banger.service.intf.IScoreInfoService;
import banger.domain.config.ModeScoreInfo;

/**
 * 评分模型信息表业务访问类
 */
@Repository
public class ScoreInfoService implements IScoreInfoService,IModeScoreInfoProvider {

	@Autowired
	private IScoreInfoDao scoreInfoDao;

	/**
	 * 新增评分模型信息表
	 * @param scoreInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertScoreInfo(ModeScoreInfo scoreInfo,Integer loginUserId){
		scoreInfo.setCreateUser(loginUserId);
		scoreInfo.setCreateDate(DateUtil.getCurrentDate());
		scoreInfo.setUpdateUser(loginUserId);
		scoreInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.scoreInfoDao.insertScoreInfo(scoreInfo);
	}

	/**
	 *修改评分模型信息表
	 * @param scoreInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateScoreInfo(ModeScoreInfo scoreInfo,Integer loginUserId){
		scoreInfo.setUpdateUser(loginUserId);
		scoreInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.scoreInfoDao.updateScoreInfo(scoreInfo);
	}

	/**
	 * 通过主键删除评分模型信息表
	 * @param MODE_ID 主键Id
	 */
	public void deleteScoreInfoById(Integer modeId){
		this.scoreInfoDao.deleteScoreInfoById(modeId);
	}

	/**
	 * 通过主键得到评分模型信息表
	 * @param MODE_ID 主键Id
	 */
	public ModeScoreInfo getScoreInfoById(Integer modeId){
		return this.scoreInfoDao.getScoreInfoById(modeId);
	}

	/**
	 * 查询评分模型信息表
	 * @param condition 查询条件
	 * @return
	 */
	public List<ModeScoreInfo> queryScoreInfoList(Map<String,Object> condition){
		return this.scoreInfoDao.queryScoreInfoList(condition);
	}

	/**
	 * 分页查询评分模型信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ModeScoreInfo> queryScoreInfoList(Map<String,Object> condition,IPageSize page){
		return this.scoreInfoDao.queryScoreInfoList(condition,page);
	}

    @Override
    public List<ModeScoreInfo> queryModelScoreInfoCopy(Map<String, Object> condition) {
        return this.scoreInfoDao.queryModelScoreInfoCopy(condition);
    }

	@Override
	public List<ModeScoreInfo> queryModeScoreInfoes() {
		return this.scoreInfoDao.queryModeScoreInfoes();
	}
}
