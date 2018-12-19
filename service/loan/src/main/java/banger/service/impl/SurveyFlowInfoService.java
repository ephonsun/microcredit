package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ISurveyFlowInfoDao;
import banger.service.intf.ISurveyFlowInfoService;
import banger.domain.loan.LoanSurveyFlowInfo;

/**
 * 调查流程表业务访问类
 */
@Repository
public class SurveyFlowInfoService implements ISurveyFlowInfoService {

	@Autowired
	private ISurveyFlowInfoDao surveyFlowInfoDao;

	/**
	 * 新增调查流程表
	 * @param surveyFlowInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo,Integer loginUserId){
		surveyFlowInfo.setCreateUser(loginUserId);
		surveyFlowInfo.setCreateDate(DateUtil.getCurrentDate());
		surveyFlowInfo.setUpdateUser(loginUserId);
		surveyFlowInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.surveyFlowInfoDao.insertSurveyFlowInfo(surveyFlowInfo);
	}

	/**
	 *修改调查流程表
	 * @param surveyFlowInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateSurveyFlowInfo(LoanSurveyFlowInfo surveyFlowInfo,Integer loginUserId){
		surveyFlowInfo.setUpdateUser(loginUserId);
		surveyFlowInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.surveyFlowInfoDao.updateSurveyFlowInfo(surveyFlowInfo);
	}

	/**
	 * 通过主键删除调查流程表
	 * @param ID 主键Id
	 */
	public void deleteSurveyFlowInfoById(Integer id){
		this.surveyFlowInfoDao.deleteSurveyFlowInfoById(id);
	}

	/**
	 * 通过主键得到调查流程表
	 * @param ID 主键Id
	 */
	public LoanSurveyFlowInfo getSurveyFlowInfoById(Integer id){
		return this.surveyFlowInfoDao.getSurveyFlowInfoById(id);
	}

	/**
	 * 查询调查流程表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanSurveyFlowInfo> querySurveyFlowInfoList(Map<String,Object> condition){
		return this.surveyFlowInfoDao.querySurveyFlowInfoList(condition);
	}

	/**
	 * 分页查询调查流程表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanSurveyFlowInfo> querySurveyFlowInfoList(Map<String,Object> condition,IPageSize page){
		return this.surveyFlowInfoDao.querySurveyFlowInfoList(condition,page);
	}

}
