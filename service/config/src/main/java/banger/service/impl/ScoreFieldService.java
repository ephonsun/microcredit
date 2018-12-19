package banger.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IScoreFieldParamsDao;
import banger.domain.config.ModeScoreFieldExtend;
import banger.domain.config.ModeScoreFieldParams;
import banger.domain.loan.LoanProfitBizIncomeItem;
import banger.domain.loan.LoanProfitBizIncomeMonth;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IScoreFieldDao;
import banger.service.intf.IScoreFieldService;
import banger.domain.config.ModeScoreField;

/**
 * 评分模型评分项明细表业务访问类
 */
@Repository
public class ScoreFieldService implements IScoreFieldService {

	@Autowired
	private IScoreFieldDao scoreFieldDao;
	@Autowired
	private IScoreFieldParamsDao scoreFieldParamsDao;
	/**
	 * 新增评分模型评分项明细表
	 * @param scoreField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertScoreField(ModeScoreField scoreField,Integer loginUserId){
		this.scoreFieldDao.insertScoreField(scoreField);
	}

	/**
	 *修改评分模型评分项明细表
	 * @param scoreField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateScoreField(ModeScoreField scoreField,Integer loginUserId){
		this.scoreFieldDao.updateScoreField(scoreField);
	}

	/**
	 * 通过主键删除评分模型评分项明细表
	 * @param optionId 主键Id
	 */
	public void deleteScoreFieldById(Integer optionId){
		this.scoreFieldDao.deleteScoreFieldById(optionId);
	}

	/**
	 * 通过主键得到评分模型评分项明细表
	 * @param optionId 主键Id
	 */
	public ModeScoreField getScoreFieldById(Integer optionId){
		return this.scoreFieldDao.getScoreFieldById(optionId);
	}

	/**
	 * 查询评分模型评分项明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<ModeScoreField> queryScoreFieldList(Map<String,Object> condition){
		return this.scoreFieldDao.queryScoreFieldList(condition);
	}

	/**
	 * 分页查询评分模型评分项明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ModeScoreField> queryScoreFieldList(Map<String,Object> condition,IPageSize page){
		return this.scoreFieldDao.queryScoreFieldList(condition,page);
	}

    @Override
    public List<ModeScoreFieldExtend> queryModelScoreInfoFieldList(Map<String, Object> condition) {
        return this.scoreFieldDao.queryModelScoreInfoFieldList(condition);
    }

	@Override
	public void deleteScoreFieldByModeId(Map<String, Object> condition) {
		scoreFieldDao.deleteScoreFieldByModeId(condition);
	}

	/**
	 * @param modeId 当前模型
	 * @param copyModeId 要复制的模型
	 * @param modeScoreFields 要复制的模型的评分项
	 * @param modeScoreFieldParams 要复制的模型的评分项参数
	 * @param loginUserId
	 */
    public void copyModel(Integer modeId, Integer copyModeId, List<ModeScoreField> modeScoreFields, List<ModeScoreFieldParams> modeScoreFieldParams, Integer loginUserId) {
    	//遍历逐个复制评分项
		for (int i = 0; i < modeScoreFields.size(); i++) {
			ModeScoreField oldField = modeScoreFields.get(i);
			ModeScoreField scoreField = new ModeScoreField();
			scoreField.setModeId(modeId);
			scoreField.setTemplateId(oldField.getTemplateId());
			scoreField.setFieldId(oldField.getFieldId());
			//插入，并获取新插入的评分项ID
			Integer optionId = scoreFieldDao.insertScoreFieldReturnId(scoreField);
			//遍历逐个复制评分项参数
			for (int j = 0; j < modeScoreFieldParams.size(); j++) {
				//如果评分项参数的评分项ID与新复制的评分项评分项ID相同，新建复制
				if (modeScoreFieldParams.get(j).getOptionId() == oldField.getOptionId()) {
					ModeScoreFieldParams oldScoreFieldParams = modeScoreFieldParams.get(j);
					ModeScoreFieldParams scoreFieldParams = new ModeScoreFieldParams();
					scoreFieldParams.setModeId(modeId);
					scoreFieldParams.setTemplateId(oldScoreFieldParams.getTemplateId());
					scoreFieldParams.setFieldId(oldScoreFieldParams.getFieldId());
					//评分项ID
					scoreFieldParams.setOptionId(optionId);
					scoreFieldParams.setOptionParam1(oldScoreFieldParams.getOptionParam1());
					scoreFieldParams.setOptionParam2(oldScoreFieldParams.getOptionParam2());
					scoreFieldParams.setOptionParam3(oldScoreFieldParams.getOptionParam3());
					scoreFieldParams.setOptionParam4(oldScoreFieldParams.getOptionParam4());
					scoreFieldParams.setParamScore(oldScoreFieldParams.getParamScore());
					scoreFieldParamsDao.insertScoreFieldParams(scoreFieldParams);
				}
			}
		}
    }
	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:新增或者修改
	 * @Date: 19:51 2017/8/29
	 */

	public void saveModelScoreField(ModeScoreField modeScoreField) {
		if (modeScoreField.getOptionId() != null) {
			//修改,先删除
			scoreFieldParamsDao.deleteScoreFieldParamsByOptionId(modeScoreField.getOptionId());
		}else{
			//新增
			scoreFieldDao.insertScoreField(modeScoreField);
		}
		//不管是新增还是修改都会重新插入params表中
		String params = modeScoreField.getParams();
		JSONArray ja =  JSONArray.fromObject(params);
		for (int i = 0; i < ja.size(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			ModeScoreFieldParams param = (ModeScoreFieldParams) JSONObject.toBean(jo, ModeScoreFieldParams.class);
			param.setFieldId(modeScoreField.getFieldId());
			param.setModeId(modeScoreField.getModeId());
			param.setOptionId(modeScoreField.getOptionId());
			param.setTemplateId(modeScoreField.getTemplateId());
			scoreFieldParamsDao.insertScoreFieldParams(param);
		}
	}
}
