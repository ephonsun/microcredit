package banger.dao.impl;

import banger.dao.intf.ISysOpeventLogDao;
import banger.domain.system.SysOpeventLog;
import banger.domain.system.SysOpeventLog_Query;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysOpeventLogDao extends PageSizeDao<SysOpeventLog> implements ISysOpeventLogDao{

	public void addSysOpeventLog(SysOpeventLog sysOpeventLog) {
		sysOpeventLog.setOpeventId(newId().intValue());
		this.execute("insertSysOpeventLog", sysOpeventLog);
	}

	public void delSysOpeventLog(Map<String, Object> cond) {
		this.execute("deleteSysOpeventLog", cond);
	}
	
	@SuppressWarnings("unchecked")
	public IPageList<SysOpeventLog_Query> querySysOpeventLog(IPageSize page,Map<String,Object> cond){
		return (IPageList<SysOpeventLog_Query>)this.queryEntities("querySysOpeventLog", page, cond);	
	}

    public List<SysOpeventLog_Query> querySysOpeventLog(Map<String,Object> cond){
        return (List<SysOpeventLog_Query>)this.queryEntities("queryLogByCondition", cond);
    }

    public List<SysOpeventLog> getOpeventModule() {
    	Map<String,Object> condition = new HashMap<String, Object>();
        return (List<SysOpeventLog>) this.queryEntities("getOpeventModule",condition);
    }

    @Override
    public IPageList<SysOpeventLog_Query> queryLogByCondition(Map<String, Object> condition,IPageSize page) {
        return (IPageList<SysOpeventLog_Query>) this.queryEntities("queryLogByCondition",page,condition);
    }
}
