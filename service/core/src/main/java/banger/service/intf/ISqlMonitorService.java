package banger.service.intf;

import java.util.List;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.sql.mapping.ISqlItemCount;

public interface ISqlMonitorService {

	/**
	 * 
	 * @param page
	 * @return
	 */
	IPageList<ISqlItemCount> getSqlMonitorList(IPageSize page);
	
	/**
	 * 
	 * @return
	 */
	List<ISqlItemCount> getAllSqlMonitorList();
	
}
