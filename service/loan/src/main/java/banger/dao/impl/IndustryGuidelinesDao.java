package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IIndustryGuidelinesDao;
import banger.domain.loan.LoanIndustryGuidelines;

/**
 * 行业指引表数据访问类
 */
@Repository
public class IndustryGuidelinesDao extends PageSizeDao<LoanIndustryGuidelines> implements IIndustryGuidelinesDao {

	/**
	 * 新增行业指引表
	 * @param industryGuidelines 实体对像
	 */
	public void insertIndustryGuidelines(LoanIndustryGuidelines industryGuidelines){
		industryGuidelines.setId(this.newId().intValue());
		this.execute("insertLoanIndustryGuidelines",industryGuidelines);
	}

	/**
	 *修改行业指引表
	 * @param industryGuidelines 实体对像
	 */
	public void updateIndustryGuidelines(LoanIndustryGuidelines industryGuidelines){
		this.execute("updateLoanIndustryGuidelines",industryGuidelines);
	}

	/**
	 * 通过主键删除行业指引表
	 * @param id 主键Id
	 */
	public void deleteIndustryGuidelinesById(Integer id){
		this.execute("deleteLoanIndustryGuidelinesById",id);
	}

	/**
	 * 通过主键得到行业指引表
	 * @param id 主键Id
	 */
	public LoanIndustryGuidelines getIndustryGuidelinesById(Integer id){
		return (LoanIndustryGuidelines)this.queryEntity("getLoanIndustryGuidelinesById",id);
	}

	/**
	 * 查询行业指引表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanIndustryGuidelines> queryIndustryGuidelinesList(Map<String,Object> condition){
		return (List<LoanIndustryGuidelines>)this.queryEntities("queryLoanIndustryGuidelinesList", condition);
	}

	/**
	 * 分页查询行业指引表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanIndustryGuidelines> queryIndustryGuidelinesList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanIndustryGuidelines>)this.queryEntities("queryLoanIndustryGuidelinesList", page, condition);
	}

	public List<LoanIndustryGuidelines> queryLoanIndustryGuidelinesListByCondition(Map<String, Object> map) {
		return (List<LoanIndustryGuidelines>)this.queryEntities("queryLoanIndustryGuidelinesList", map);
	}

	public List<LoanIndustryGuidelines> getIndustryGuidelinesByGroup(Map<String, Object> map) {
		return (List<LoanIndustryGuidelines>)this.queryEntities("getIndustryGuidelinesByGroup", map);
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:右边模糊查询行业指引项目名称
	 * @Date: 10:28 2017/9/6
	 */
	public List<LoanIndustryGuidelines> queryLoanIndustryGuidelinesListByLike(String str) {
		return (List<LoanIndustryGuidelines>)this.queryEntities("queryLoanIndustryGuidelinesListByLike", str);
	}
    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:批量添加
     * @Date: 16:56 2017/9/7
     */

	public void insertIndustryListByImport(List<LoanIndustryGuidelines> insertList) {
		Long[] ids = this.newId(insertList.size());
		for (int i=0;i<insertList.size();i++) {
			LoanIndustryGuidelines industryGuidelines = insertList.get(i);
			industryGuidelines.setId(ids[i].intValue());
			industryGuidelines.setGradeFirst(industryGuidelines.getGradeFirst().trim().length() > 20 ?
					industryGuidelines.getGradeFirst().trim().replace(",", "").substring(0, 20) :
					industryGuidelines.getGradeFirst().trim().replace(",", ""));
			industryGuidelines.setGradeSecond(industryGuidelines.getGradeSecond().trim().length() > 20 ?
					industryGuidelines.getGradeSecond().trim().replace(",", "").substring(0, 20) :
					industryGuidelines.getGradeSecond().trim().replace(",", ""));
			industryGuidelines.setGradeThird(industryGuidelines.getGradeThird().trim().length() > 20 ?
					industryGuidelines.getGradeThird().trim().replace(",", "").substring(0, 20) :
					industryGuidelines.getGradeThird().trim().replace(",", ""));
			industryGuidelines.setGradeTerms(industryGuidelines.getGradeFirst() + "," + industryGuidelines.getGradeSecond() +
					"," + industryGuidelines.getGradeThird());
			this.execute("insertLoanIndustryGuidelines",industryGuidelines);
		}
//		this.executes("insertIndustryListByImport",insertList);
	}

	@Override
	public void truncateLoanIndustryGuideLines() {
		this.execute("truncateLoanIndustryGuideLines");
	}
}
