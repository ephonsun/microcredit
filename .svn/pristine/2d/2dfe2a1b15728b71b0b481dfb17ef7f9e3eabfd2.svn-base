package banger.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanMonitorInfoExtend;
import banger.domain.system.SysDataDictCol;

import banger.domain.loan.LoanRepayPlanInfo;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IMonitorInfoDao;
import banger.domain.loan.LoanMonitorInfo;

/**
 * 贷款监控信息表数据访问类
 */
@Repository
public class MonitorInfoDao extends PageSizeDao<LoanMonitorInfo> implements IMonitorInfoDao {

	/**
	 * 新增贷款监控信息表
	 * @param monitorInfo 实体对像
	 */
	public void insertMonitorInfo(LoanMonitorInfo monitorInfo){
		if(monitorInfo.getId() == null){
			monitorInfo.setId(this.newId().intValue());
		}
		this.execute("insertMonitorInfo",monitorInfo);
	}

	/**
	 *修改贷款监控信息表
	 * @param monitorInfo 实体对像
	 */
	public void updateMonitorInfo(LoanMonitorInfo monitorInfo){
		this.execute("updateMonitorInfo",monitorInfo);
	}

	/**
	 * 通过主键删除贷款监控信息表
	 * @param id 主键Id
	 */
	public void deleteMonitorInfoById(Integer id){
		this.execute("deleteMonitorInfoById",id);
	}

	/**
	 * 通过主键得到贷款监控信息表
	 * @param id 主键Id
	 */
	public LoanMonitorInfo getMonitorInfoById(Integer id){
		return (LoanMonitorInfo)this.queryEntity("getMonitorInfoById",id);
	}

	/**
	 * 得到第一条，监控信息
	 * @param loanId
	 * @return
	 */
	public LoanMonitorInfo getTopLoanMonitorInfo(Integer loanId){
		List<LoanMonitorInfo> list = (List<LoanMonitorInfo>)this.queryTopEntities("getTopLoanMonitorInfo",1,loanId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 得到监控信息
	 * @param loanId
	 * @return
	 */
	public List<LoanMonitorInfo> getLoanMonitorInfoListByLoanId(Integer loanId){
		return (List<LoanMonitorInfo>)this.queryEntities("getLoanMonitorInfoListByLoanId",loanId);
	}

	/**
	 * 查询贷款监控信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanMonitorInfo> queryMonitorInfoList(Map<String,Object> condition){
		return (List<LoanMonitorInfo>)this.queryEntities("queryMonitorInfoList", condition);
	}

	/**
	 * 分页查询贷款监控信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanMonitorInfo> queryMonitorInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanMonitorInfo>)this.queryEntities("queryMonitorInfoList", page, condition);
	}

    public List<SysDataDictCol> queryLoanMonitorInfoRevisit() {
		Map<String,Object> condition = new HashMap<String, Object>();
		return (List<SysDataDictCol>) this.queryEntities("getLoanMonitorInfoRevisit",condition);
    }

    public List<LoanMonitorInfoExtend> queryMonitorInfoExtend(Map<String, Object> condition) {
		return (List<LoanMonitorInfoExtend>) this.queryEntities("getMonitorInfoExtend",condition);
    }

    public List<LoanMonitorInfoExtend> getUpdateMonitorInfoById(Map<String, Object> condition) {
		return (List<LoanMonitorInfoExtend>) this.queryEntities("getMonitorInfoExtendById",condition);
    }

    @Override
    public Boolean isMoniting(Integer loanId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanId",loanId);
		condition.put("loanMonitorState","Monitor");
		List<LoanMonitorInfo> monitorInfos = queryMonitorInfoList(condition);
		if(monitorInfos != null && monitorInfos.size()>0){
			return true;
		}
		return false;
    }

    @Override
    public LoanMonitorInfo queryLastMonitorInfoCompleted(Map<String, Object> condition) {
        List<LoanMonitorInfo> l = (List<LoanMonitorInfo>) this.queryEntities("queryLastMonitorInfoCompleted",condition);
        if(l != null && l.size() > 0){
        	return l.get(0);
		}
		return null;
	}

	@Override
	public LoanMonitorInfo queryMonitorDate(Integer loanId) {
		return (LoanMonitorInfo) this.queryEntity("queryMonitorDate",loanId);
	}

	/**
	 * 新增临时监控时，获取监控id
	 * @return
	 */
	@Override
	public Integer getMonitorId() {
		return this.newId().intValue();
	}
}
