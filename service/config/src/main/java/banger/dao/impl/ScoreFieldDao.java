package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeScoreFieldExtend;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IScoreFieldDao;
import banger.domain.config.ModeScoreField;

/**
 * 评分模型评分项明细表数据访问类
 */
@Repository
public class ScoreFieldDao extends PageSizeDao<ModeScoreField> implements IScoreFieldDao {

	/**
	 * 新增评分模型评分项明细表
	 * @param scoreField 实体对像
	 */
	public void insertScoreField(ModeScoreField scoreField){
		scoreField.setOptionId(this.newId().intValue());
		this.execute("insertScoreField",scoreField);
	}

	/**
	 *修改评分模型评分项明细表
	 * @param scoreField 实体对像
	 */
	public void updateScoreField(ModeScoreField scoreField){
		this.execute("updateScoreField",scoreField);
	}

	/**
	 * 通过主键删除评分模型评分项明细表
	 * @param optionId 主键Id
	 */
	public void deleteScoreFieldById(Integer optionId){
		this.execute("deleteScoreFieldById",optionId);
	}

	/**
	 * 通过主键得到评分模型评分项明细表
	 * @param optionId 主键Id
	 */
	public ModeScoreField getScoreFieldById(Integer optionId){
		return (ModeScoreField)this.queryEntity("getScoreFieldById",optionId);
	}

	/**
	 * 查询评分模型评分项明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ModeScoreField> queryScoreFieldList(Map<String,Object> condition){
		return (List<ModeScoreField>)this.queryEntities("queryScoreFieldList", condition);
	}

	/**
	 * 分页查询评分模型评分项明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ModeScoreField> queryScoreFieldList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ModeScoreField>)this.queryEntities("queryScoreFieldList", page, condition);
	}

    @Override
    public List<ModeScoreFieldExtend> queryModelScoreInfoFieldList(Map<String, Object> condition) {
		return (List<ModeScoreFieldExtend>) this.queryEntities("queryModelScoreInfoFieldList",condition);
    }

    @Override
    public void deleteScoreFieldByModeId(Map<String, Object> condition) {
        this.execute("deleteScoreFieldByModeId",condition);
    }

	/**
	 * 插入，并返回插入的评分项ID
	 * @param scoreField
	 * @return
	 */
	public Integer insertScoreFieldReturnId(ModeScoreField scoreField) {
		Integer optionId = this.newId().intValue();
		scoreField.setOptionId(optionId);
		this.execute("insertScoreField",scoreField);
		return optionId;
	}

}
