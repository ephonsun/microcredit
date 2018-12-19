package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IScoreInfoDao;
import banger.domain.config.ModeScoreInfo;

/**
 * 评分模型信息表数据访问类
 */
@Repository
public class ScoreInfoDao extends PageSizeDao<ModeScoreInfo> implements IScoreInfoDao {

	/**
	 * 新增评分模型信息表
	 * @param scoreInfo 实体对像
	 */
	public void insertScoreInfo(ModeScoreInfo scoreInfo){
		scoreInfo.setModeId(this.newId().intValue());
		this.execute("insertScoreInfo",scoreInfo);
	}

	/**
	 *修改评分模型信息表
	 * @param scoreInfo 实体对像
	 */
	public void updateScoreInfo(ModeScoreInfo scoreInfo){
		this.execute("updateScoreInfo",scoreInfo);
	}

	/**
	 * 通过主键删除评分模型信息表
	 * @param modeId 主键Id
	 */
	public void deleteScoreInfoById(Integer modeId){
		this.execute("deleteScoreInfoById",modeId);
	}

	/**
	 * 通过主键得到评分模型信息表
	 * @param modeId 主键Id
	 */
	public ModeScoreInfo getScoreInfoById(Integer modeId){
		return (ModeScoreInfo)this.queryEntity("getScoreInfoById",modeId);
	}

	/**
	 * 查询评分模型信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ModeScoreInfo> queryScoreInfoList(Map<String,Object> condition){
		return (List<ModeScoreInfo>)this.queryEntities("queryScoreInfoList", condition);
	}

	/**
	 * 分页查询评分模型信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ModeScoreInfo> queryScoreInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ModeScoreInfo>)this.queryEntities("queryScoreInfoList", page, condition);
	}

    @Override
    public List<ModeScoreInfo> queryModelScoreInfoCopy(Map<String, Object> condition) {
        return (List<ModeScoreInfo>) this.queryEntities("queryModelScoreInfoCopy",condition);
    }

    @Override
    public List<ModeScoreInfo> queryModeScoreInfoes() {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
        return queryScoreInfoList(condition);
    }

}
