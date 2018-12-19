package banger.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.pagesize.PageList;
import banger.framework.spring.SpringContext;
import banger.framework.sql.mapping.ISqlItemCount;
import banger.framework.sql.mapping.ISqlMap;
import banger.service.intf.ISqlMonitorService;
import org.springframework.stereotype.Repository;

@Repository
public class SqlMonitorService implements ISqlMonitorService {
	protected static final String SqlMapKey = "SqlMap";
	
	protected ISqlMap getSqlMap(){
		return (ISqlMap)SpringContext.instance().get(SqlMapKey);
	}

	@Override
	public IPageList<ISqlItemCount> getSqlMonitorList(IPageSize page) {
		List<ISqlItemCount> list = new ArrayList<ISqlItemCount>();
		list.addAll(getSqlMap().getSqlItems());
		Collections.sort(list,new SqlItemCounttAverageSort());
		PageList<ISqlItemCount> pageList = new PageList<ISqlItemCount>(0);
		int limit = PageSizeHelper.getLimit(page.getPageSize(), page.getPageNum());
		int offset = PageSizeHelper.getOffset(page.getPageSize(), page.getPageNum());
		for(int i=offset;i<limit;i++){
			if(i<list.size()){
				pageList.add(list.get(i));
			}
		}
		pageList.setTotal(list.size());
		pageList.setPageNum(page.getPageNum());
		pageList.setPageSize(page.getPageSize());
		return pageList;
	}

	@Override
	public List<ISqlItemCount> getAllSqlMonitorList() {
		List<ISqlItemCount> list = new ArrayList<ISqlItemCount>();
		list.addAll(getSqlMap().getSqlItems());
		Collections.sort(list,new SqlItemCounttAverageSort());
		return list;
	}
	
	static class PageSizeHelper {
		public static int getLimit(int pagesize, int pagenum) {
			return pagesize * pagenum;
		}

		public static int getOffset(int pagesize, int pagenum) {
			return pagesize * (pagenum - 1);
		}
	}
	
	static class SqlItemCounttAverageSort implements Comparator<ISqlItemCount> {

		@Override
		public int compare(ISqlItemCount item1, ISqlItemCount item2) {
			if(item1.getAverageTime()>item2.getAverageTime()){
				return -1;
			}else if(item1.getAverageTime()<item2.getAverageTime()){
				return 1;
			}else{
				return 0;
			}
		}
		
	}

}
