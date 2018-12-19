package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAssetsInfoDao;
import banger.domain.loan.LoanAssetsInfo;

/**
 * 资产负债表信息表数据访问类
 */
@Repository
public class AssetsInfoDao extends PageSizeDao<LoanAssetsInfo> implements IAssetsInfoDao {

	/**
	 * 新增资产负债表信息表
	 * @param assetsInfo 实体对像
	 */
	public void insertAssetsInfo(LoanAssetsInfo assetsInfo){
		assetsInfo.setId(this.newId().intValue());
		this.execute("insertAssetsInfo",assetsInfo);
	}

	/**
	 *修改资产负债表信息表
	 * @param assetsInfo 实体对像
	 */
	public void updateAssetsInfo(LoanAssetsInfo assetsInfo){
		this.execute("updateAssetsInfo",assetsInfo);
	}

	/**
	 * 通过主键删除资产负债表信息表
	 * @param id 主键Id
	 */
	public void deleteAssetsInfoById(Integer id){
		this.execute("deleteAssetsInfoById",id);
	}

	/**
	 * 通过主键得到资产负债表信息表
	 * @param id 主键Id
	 */
	public LoanAssetsInfo getAssetsInfoById(Integer id){
		return (LoanAssetsInfo)this.queryEntity("getAssetsInfoById",id);
	}

	/**
	 * 通过主键得到资产负债表信息表
	 * @param loanId 主键 loanId
	 */
	public LoanAssetsInfo getAssetsInfoByLoanId(Integer loanId){
		return (LoanAssetsInfo)this.queryEntity("getAssetsInfoByLoanId",loanId);
	}

	/**
	 * 查询资产负债表信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAssetsInfo> queryAssetsInfoList(Map<String,Object> condition){
		return (List<LoanAssetsInfo>)this.queryEntities("queryAssetsInfoList", condition);
	}

	/**
	 * 分页查询资产负债表信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAssetsInfo> queryAssetsInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAssetsInfo>)this.queryEntities("queryAssetsInfoList", page, condition);
	}


}
