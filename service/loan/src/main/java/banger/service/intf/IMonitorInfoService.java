package banger.service.intf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanMonitorInfoExtend;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanMonitorInfo;

/**
 * 贷款监控信息表业务访问接口
 */
public interface IMonitorInfoService {

	/**
	 * 新增贷款监控信息表
	 * @param monitorInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertMonitorInfo(LoanMonitorInfo monitorInfo, Integer loginUserId);

	/**
	 *修改贷款监控信息表
	 * @param monitorInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateMonitorInfo(LoanMonitorInfo monitorInfo, Integer loginUserId);

	/**
	 * 通过主键删除贷款监控信息表
	 * @param ID 主键Id
	 */
	void deleteMonitorInfoById(Integer id);

	/**
	 * 得到第一条，监控信息
	 * @param loanId
	 * @return
	 */
	LoanMonitorInfo getTopLoanMonitorInfo(Integer loanId);

	/**
	 * 通过主键得到贷款监控信息表
	 * @param ID 主键Id
	 */
	LoanMonitorInfo getMonitorInfoById(Integer id);

	/**
	 * 查询贷款监控信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanMonitorInfo> queryMonitorInfoList(Map<String, Object> condition);

	/**
	 * 分页查询贷款监控信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanMonitorInfo> queryMonitorInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 查询回访类型
	 * @return
	 */
    List<SysDataDictCol> queryLoanMonitorInfoRevisit();

	/**
	 * 查询监控信息
	 * @param condition
	 * @return
	 */
	List<LoanMonitorInfoExtend> queryMonitorInfoExtend(Map<String, Object> condition);

	List<LoanMonitorInfoExtend> getUpdateMonitorInfoById(Map<String, Object> condition);

    Boolean isMoniting(Integer loanId);

    LoanMonitorInfo queryLastMonitorInfoCompleted(Map<String, Object> condition);

	/**
	 * 查询该条贷款最早的监控时间的监控记录
	 * @param loanId
	 * @return
	 */
	LoanMonitorInfo queryMonitorDate(Integer loanId);

	/**
	 * 新增临时监控时，获取监控id
	 * @return
	 */
	Integer getMonitorId();
}
