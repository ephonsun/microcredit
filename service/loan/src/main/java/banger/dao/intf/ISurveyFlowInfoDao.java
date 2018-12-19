package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanSurveyFlowInfo;

/**
 * 调查流程表数据访问接口
 */
public interface ISurveyFlowInfoDao {

	/**
	 * 新增调查流程表
	 * @param surveyFlowInfo 实体对像
	 */
	void insertSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo);

	/**
	 *修改调查流程表
	 * @param surveyFlowInfo 实体对像
	 */
	void updateSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo);

	/**
	 * 通过主键删除调查流程表
	 * @param id 主键Id
	 */
	void deleteSurveyFlowInfoById(Integer id);

	/**
	 * 通过主键得到调查流程表
	 * @param id 主键Id
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
