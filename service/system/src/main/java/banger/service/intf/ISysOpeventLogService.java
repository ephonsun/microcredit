package banger.service.intf;

import banger.domain.system.SysOpeventLog;
import banger.domain.system.SysOpeventLog_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ISysOpeventLogService {
	/**
	 * 新增日志
	 * @param sysOpeventLog
	 */
	public void addSysOpereventLog(SysOpeventLog sysOpeventLog);
	
	/**
	 * 清空所有日志
	 */
	public void deleteAllSysOperentLog();
	
	/**
	 * 根据用户清空日志
	 * @param UserId
	 */
	public void deleteSysOperentLogByUser(int UserId);
	
	/**
	 * 根据日期范围清空日志
	 * @param beginDate
	 * @param endDate
	 */
	public void deleteSysOperentLogByDate(Date beginDate, Date endDate);
	
	/**
	 * 分页获取日志
	 * @param page
	 * @param cond
	 * @return
	 */
	public IPageList<SysOpeventLog_Query> querySysOpeventLog(IPageSize page, Map<String, Object> cond);

    /**
     * @param cond
     * @return
     */
    public List<SysOpeventLog_Query> querySysOpeventLog(Map<String, Object> cond);

	/**
	 * 查询操作对象
	 * @return
	 */
	List<SysOpeventLog> getOpeventModule();

	/**
	 * 根据条件查询日志
	 * @param condition
	 * @param page
	 */
	IPageList<SysOpeventLog_Query> queryLogByCondition(Map<String, Object> condition, IPageSize page);

	File createCSVFile(List<String> head, List<List<String>> dataList,
					   String outPutPath, String filename) throws IOException;

}
