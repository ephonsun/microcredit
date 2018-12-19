package banger.dao.impl;

import banger.dao.intf.ICustApplyInfoDao;
import banger.domain.html5.IntoCustApplyInfo;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 进件申请信息表数据访问类
 */
@Repository
public class CustApplyInfoDao extends PageSizeDao<IntoCustApplyInfo> implements ICustApplyInfoDao {
	/**
	 * App端
	 * 通过主键得到进件申请信息表
	 * @param applyId 主键Id
	 */
	@Override
	public IntoCustApplyInfoQuery getAppCustApplyInfoById(Integer applyId) {
		return (IntoCustApplyInfoQuery) this.queryEntity("getAppCustApplyInfoById",applyId);
	}

	/**
	 * 客户经理
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<IntoCustApplyInfoQuery> IntoCustApplyInfoMemberList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<IntoCustApplyInfoQuery>)this.queryEntities("IntoCustApplyInfoMemberList", page, condition);
	}


	/**
	 * App
	 * 客户经理
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<IntoCustApplyInfoQuery> IntoAppCustApplyInfoMemberList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<IntoCustApplyInfoQuery>)this.queryEntities("IntoAppCustApplyInfoMemberList", page, condition);
	}

	/**
	 * 新增进件申请信息表
	 * @param custApplyInfo 实体对像
	 */
	public Integer insertCustApplyInfo(IntoCustApplyInfo custApplyInfo){
		Integer applyId=this.newId().intValue();
		custApplyInfo.setApplyId(applyId);
		this.execute("insertCustApplyInfo",custApplyInfo);
		return applyId;
	}

	/**
	 *修改进件申请信息表
	 * @param custApplyInfo 实体对像
	 */
	public void updateCustApplyInfo(IntoCustApplyInfo custApplyInfo){
		this.execute("updateCustApplyInfo",custApplyInfo);
	}

	/**
	 * 通过主键删除进件申请信息表
	 * @param applyId 主键Id
	 */
	public void deleteCustApplyInfoById(Integer applyId){
		this.execute("deleteCustApplyInfoById",applyId);
	}

	/**
	 * 通过主键得到进件申请信息表
	 * @param applyId 主键Id
	 */
	public IntoCustApplyInfo getCustApplyInfoById(Integer applyId){
		return (IntoCustApplyInfo)this.queryEntity("getCustApplyInfoById",applyId);
	}

	public IntoCustApplyInfoQuery getCustApplyInfoByIdQuery(Integer applyId){
		return (IntoCustApplyInfoQuery)this.queryEntity("getCustApplyInfoByIdQuery",applyId);
	}
//	/**
//	 * 查询进件申请信息表
//	 * @param condition 查询条件
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<IntoCustApplyInfo> queryCustApplyInfoList(Map<String,Object> condition){
//		return (List<IntoCustApplyInfo>)this.queryEntities("queryCustApplyInfoList", condition);
//	}
//
//
//	public IPageList<IntoCustApplyInfo> queryCustApplyInfoList(Map<String,Object> condition,IPageSize page){
//		return (IPageList<IntoCustApplyInfo>)this.queryEntities("queryCustApplyInfoList", page, condition);
//	}
	/**
	 * 营销团队
	 * 分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<IntoCustApplyInfoQuery> IntoCustApplyInfoList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<IntoCustApplyInfoQuery>)this.queryEntities("IntoCustApplyInfoListQuery", page, condition);
	}

//	/**
//	 *
//	 * 分页查询进件申请信息表
//	 * @param condition 查询条件
//	 * @param page 分页对像
//	 * @return
//	 */
//	@Override
//	public IPageList<IntoCustApplyInfoQuery> IntoAppCustApplyInfoList(Map<String, Object> condition, IPageSize page) {
//		return (IPageList<IntoCustApplyInfoQuery>)this.queryEntities("IntoAppCustApplyInfoList", page, condition);
//
//	}

	/**
	 * 团队主管
	 * 分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<IntoCustApplyInfoQuery> queryIntoCustApplyInfoList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<IntoCustApplyInfoQuery>)this.queryEntities("QueryIntoCustApplyInfoList", page, condition);
	}

	@Override
	public void signIntoCustomer(Map<String, Object> condition) {
		this.execute("signIntoCustomer",condition);
	}

	@Override
	public void deleteIntoCustomerById(Integer applyId) {
		this.execute("deleteIntoCustomerById",applyId);
	}

	@Override
	public void updateIntoCustomerLoanId(Integer applyId, Integer loanId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("applyId",applyId);
		condition.put("loanId",loanId);
		this.execute("updateIntoCustomerLoanId",condition);
	}

}
