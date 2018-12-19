package banger.service.impl;

import banger.dao.intf.IMonitorInfoDao;
import banger.domain.loan.LoanMonitorInfo;
import banger.domain.loan.LoanMonitorInfoExtend;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IMonitorInfoPrivder;
import banger.service.intf.IMonitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 贷款监控信息表业务访问类
 */
@Repository
public class MonitorInfoService implements IMonitorInfoService,IMonitorInfoPrivder {

	@Autowired
	private IMonitorInfoDao monitorInfoDao;

	/**
	 * 新增贷款监控信息表
	 * @param monitorInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertMonitorInfo(LoanMonitorInfo monitorInfo,Integer loginUserId){
		monitorInfo.setCreateUser(loginUserId);
		monitorInfo.setCreateDate(DateUtil.getCurrentDate());
		monitorInfo.setUpdateUser(loginUserId);
		monitorInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.monitorInfoDao.insertMonitorInfo(monitorInfo);
	}

	/**
	 * 得到第一条，监控信息
	 * @param loanId
	 * @return
	 */
	public LoanMonitorInfo getTopLoanMonitorInfo(Integer loanId){
		return monitorInfoDao.getTopLoanMonitorInfo(loanId);
	}

	/**
	 *修改贷款监控信息表
	 * @param monitorInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateMonitorInfo(LoanMonitorInfo monitorInfo,Integer loginUserId){
		monitorInfo.setUpdateUser(loginUserId);
		monitorInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.monitorInfoDao.updateMonitorInfo(monitorInfo);
	}

	/**
	 * 通过主键删除贷款监控信息表
	 * @param ID 主键Id
	 */
	public void deleteMonitorInfoById(Integer id){
		this.monitorInfoDao.deleteMonitorInfoById(id);
	}

	/**
	 * 通过主键得到贷款监控信息表
	 * @param ID 主键Id
	 */
	public LoanMonitorInfo getMonitorInfoById(Integer id){
		return this.monitorInfoDao.getMonitorInfoById(id);
	}

	/**
	 * 查询贷款监控信息表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanMonitorInfo> queryMonitorInfoList(Map<String,Object> condition){
		return this.monitorInfoDao.queryMonitorInfoList(condition);
	}

	/**
	 * 分页查询贷款监控信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanMonitorInfo> queryMonitorInfoList(Map<String,Object> condition,IPageSize page){
		return this.monitorInfoDao.queryMonitorInfoList(condition,page);
	}

    public List<SysDataDictCol> queryLoanMonitorInfoRevisit() {
        return this.monitorInfoDao.queryLoanMonitorInfoRevisit();
    }

	public List<LoanMonitorInfoExtend> queryMonitorInfoExtend(Map<String, Object> condition) {
		return this.monitorInfoDao.queryMonitorInfoExtend(condition);
	}

	public List<LoanMonitorInfoExtend> getUpdateMonitorInfoById(Map<String, Object> condition) {
		return this.monitorInfoDao.getUpdateMonitorInfoById(condition);
	}

    @Override
    public Boolean isMoniting(Integer loanId) {
        return monitorInfoDao.isMoniting(loanId);
    }

    @Override
    public LoanMonitorInfo queryLastMonitorInfoCompleted(Map<String, Object> condition) {
        return monitorInfoDao.queryLastMonitorInfoCompleted(condition);
    }

	@Override
	public LoanMonitorInfo queryMonitorDate(Integer loanId) {
		return monitorInfoDao.queryMonitorDate(loanId);
	}

	/**
	 * 新建临时监控时，获取监控id
	 * @return
	 */
	@Override
	public Integer getMonitorId() {
		return monitorInfoDao.getMonitorId();
	}

}
