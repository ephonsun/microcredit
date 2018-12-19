package banger.moduleIntf;

import banger.domain.config.ModeScoreCustomField;

import java.util.List;

/**
 * @Author: yangdw
 * @Description:评分模型积分项模板字段表业务访问类对外接口
 * @Date: Created in 16:04 2017/9/15.
 */
public interface ITemplateFieldProvider {

	List<ModeScoreCustomField> getLoanModelScoreByModeId(Integer modeId);
}
