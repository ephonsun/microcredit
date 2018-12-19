package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ISurveyFlowInfoDao;
import banger.domain.loan.LoanSurveyFlowInfo;

/**
 * 调查流程表数据访问类
 */
@Repository
public class SurveyFlowInfoDao extends PageSizeDao<LoanSurveyFlowInfo> implements ISurveyFlowInfoDao {

	/**
	 * 新增调查流程表
	 * @param surveyFlowInfo 实体对像
	 */
	public void insertSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo){
		surveyFlowInfo.setId(this.newId().intValue());
		this.execute("insertSurveyFlowInfo",surveyFlowInfo);
	}

	/**
	 *修改调查流程表
	 * @param surveyFlowInfo 实体对像
	 */
	public void updateSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo){
		this.execute("updateSurveyFlowInfo",surveyFlowInfo);
	}

	/**
	 * 通过主键删除调查流程表
	 * @param id 主键Id
	 */
	public void deleteSurveyFlowInfoById(Integer id){
		this.execute("deleteSurveyFlowInfoById",id);
	}

	/**
	 * 通过主键得到调查流程表
	 * @param id 主键Id
	 */
	public LoanSurveyFlowInfo getSurveyFlowInfoById(Integer id){
		return (LoanSurveyFlowInfo)this.queryEntity("getSurveyFlowInfoById",id);
	}

	/**
	 * 查询调查流程表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanSurveyFlowInfo> querySurveyFlowInfoList(Map<String,Object> condition){
		return (List<LoanSurveyFlowInfo>)this.queryEntities("querySurveyFlowInfoList", condition);
	}

	/**
	 * 分页查询调查流程表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanSurveyFlowInfo> querySurveyFlowInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanSurveyFlowInfo>)this.queryEntities("querySurveyFlowInfoList", page, condition);
	}

}
