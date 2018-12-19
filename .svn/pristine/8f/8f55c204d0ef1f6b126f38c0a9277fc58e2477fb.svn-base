package banger.framework.dao;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

public interface IPageSizeDao<T> extends IEntityDao<T> {

	public IPageList<?> queryEntities(String dataSqlId,String countSqlId,IPageSize page,Object ... conditions);
	
	public IPageList<?> queryEntities(String sqlId,IPageSize page, Object... conditions);
	
}
