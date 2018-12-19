package banger.dao.intf;

import java.util.List;
import java.util.Map;
import banger.domain.system.SysOpeventLog;
import banger.domain.system.SysOpeventLog_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

public interface ISysOpeventLogDao {
	/**
	 * 新增日志
	 * @param sysOpeventLog
	 */
	public void addSysOpeventLog(SysOpeventLog sysOpeventLog);
	
	/**
	 * 清除日志
	 * @param cond
	 */
	public void delSysOpeventLog(Map<String, Object> cond);

	/**
     *
     * @param page
     * @param cond
     * @return
     */
    public IPageList<SysOpeventLog_Query> querySysOpeventLog(IPageSize page, Map<String, Object> cond);

    /**
     *
     * @param cond
     * @return
     */
    public List<SysOpeventLog_Query> querySysOpeventLog(Map<String, Object> cond);

	/**
	 * 查询操作模块
	 * @return
	 */
    List<SysOpeventLog> getOpeventModule();

	IPageList<SysOpeventLog_Query> queryLogByCondition(Map<String, Object> condition, IPageSize page);
}
