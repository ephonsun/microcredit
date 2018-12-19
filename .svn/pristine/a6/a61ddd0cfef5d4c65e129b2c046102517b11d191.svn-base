package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanSurveyFlowInfo;

/**
 * 调查流程表业务访问接口
 */
public interface ISurveyFlowInfoService {

	/**
	 * 新增调查流程表
	 * @param surveyFlowInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo, Integer loginUserId);

	/**
	 *修改调查流程表
	 * @param surveyFlowInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo, Integer loginUserId);

	/**
	 * 通过主键删除调查流程表
	 * @param ID 主键Id
	 */
	void deleteSurveyFlowInfoById(Integer id);

	/**
	 * 通过主键得到调查流程表
	 * @param ID 主键Id
	 */
	LoanSurveyFlowInfo getSurveyFlowInfoById(Integer id);

	/**
	 * 查询调查流程表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanSurveyFlowInfo> querySurveyFlowInfoList(Map<String, Object> condition);

	/**
	 * 分页查询调查流程表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanSurveyFlowInfo> querySurveyFlowInfoList(Map<String, Object> condition, IPageSize page);

}
