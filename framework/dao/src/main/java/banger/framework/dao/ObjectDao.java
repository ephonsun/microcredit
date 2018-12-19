package banger.framework.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.entity.EntityUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.pagesize.PageList;
import banger.framework.spring.SpringContext;
import banger.framework.sql.SqlMapException;
import banger.framework.sql.mapping.SqlItem;
import banger.framework.util.ReflectorUtil;

/**
 * 提供实体对像访问数据库操作
 */
public class ObjectDao extends DataDao implements IObjectDao {
	private static final String IdGeneratorKey = "IdGenerator";

	protected IdGenerator getIdGenerator() {
		return (IdGenerator) SpringContext.instance().get(IdGeneratorKey);
	}

	/**
	 * 查询并返回实体对像集合
	 * @param sqlId
	 * @param conditions 条件数据
	 * @return
	 */
	public List<?> queryList(String sqlId,Object ... conditions){
		return this.queryList(sqlId, -1, -1, conditions);
	}
	
	/**
	 * 分页查询并返回实体对像集合
	 * @param sqlId
	 * @param pageSize 第页多少记录
	 * @param pageNum 第几页
	 * @param conditions 条件数据
	 * @return
	 */
	public List<?> queryList(String sqlId,int pageSize,int pageNum,Object ... conditions){
		List<Object> list = new ArrayList<Object>();
		this.queryToList(list, sqlId, pageSize, pageNum, conditions);
		return list;
	}
	
	/**
	 * 分页查询返回实体对像集合
	 * @param list
	 * @param sqlId
	 * @param pageSize
	 * @param pageNum
	 * @param conditions
	 */
	private void queryToList(List<Object> list,String sqlId,int pageSize,int pageNum,Object ... conditions){
		DataTable table = (pageSize > 0 && pageNum > 0) ? this.queryData(sqlId,
				pageSize, pageNum, conditions) : this.queryData(sqlId,
				conditions);
		Class<?> type = this.getResultClass(sqlId);
		for (DataRow row : table) {
			Object object = ReflectorUtil.newInstance(type);
			EntityUtil.copyProperties(type, object, row);
			EntityUtil.copyProperties(object, row ,this.getPropertyMap(sqlId));
			list.add(object);
		}
	}
	
	/**
	 * 分页查询并返回实体对像集合
	 * @param sqlId	查询数量的sqlId
	 * @param countSqlId 查询总数的sqlId
	 * @param page 分页对像
	 * @param conditions 条件数据
	 * @return
	 */
	public IPageList<?> queryList(String sqlId,String countSqlId,IPageSize page,Object ... conditions){
		int count = this.queryInt(countSqlId, conditions);
		IPageList<Object> pagelist = new PageList<Object>(count);
		page.setTotal(count);
		this.queryToList(pagelist,sqlId,page.getPageSize(),page.getPageNum(),conditions);
		return pagelist;
	}
	
	/**
	 * 分页查询并返回实体对像集合
	 * @param sqlId 查询数量的sqlId
	 * @param page 分页对像
	 * @param conditions 条件数据
	 * @return
	 */
	public IPageList<?> queryList(String sqlId,IPageSize page,Object ... conditions){
		int count = this.queryCount(sqlId, conditions);
		IPageList<Object> pagelist = new PageList<Object>(count);
		page.setTotal(count);
		this.queryToList(pagelist,sqlId,page.getPageSize(),page.getPageNum(),conditions);
		return pagelist;
	}
	
	/**
	 * 
	 * @param sqlId
	 * @param top
	 * @param conditions
	 * @return
	 */
	public List<?> queryList(String sqlId,int top,Object ... conditions){
		return null;
	}
	
	/**
	 * 
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryObject(String sqlId,Object ... conditions){
		DataTable table = this.queryData(sqlId, conditions);
		Class<?> type = this.getResultClass(sqlId);
		if (table.rowSize() > 0) {
			DataRow row = table.getRow(0);
			Object object = ReflectorUtil.newInstance(type);
			EntityUtil.copyProperties(type, object, row);
			EntityUtil.copyProperties(object, row ,this.getPropertyMap(sqlId));
			return object;
		}
		return null;
	}
	
	/**
	 * 产生一个新的唯一id
	 * @param tablename	表名
	 * @return
	 */
	public Long newId(String tableName){
		return this.getIdGenerator().getId(tableName,this.dbConfig);
	}
	
	/**
	 * 产生一个新的唯一id
	 * @param tablename	表名
	 * @return
	 */
	public Long newId(String tableName,String pk){
		return this.getIdGenerator().getPkId(tableName,pk,this.dbConfig);
	}
	
	/**
	 * 产生一个新的唯一id
	 * @param type 实体类
	 * @return
	 */
	public Long newId(Class<?> type){
		return this.getIdGenerator().getId(type,this.dbConfig);
	}
	
	/**
	 * 产生一批新的唯一id
	 * @param tablename 表名
	 * @param num 数量
	 * @return
	 */
	public Long[] newId(String tableName,int num){
		return this.getIdGenerator().getId(tableName,num,this.dbConfig);
	}
	
	/**
	 * 产生一批新的唯一id
	 * @param type 实体类
	 * @param num 数量
	 * @return
	 */
	public Long[] newId(Class<?> type,int num){
		return this.getIdGenerator().getId(type,num,this.dbConfig);
	}
	
	/**
	 * 得到返回结果类形
	 * @param sqlId
	 * @return
	 */
	private Class<?> getResultClass(String sqlId) {
		SqlItem item = (SqlItem) this.getSqlMap().get(sqlId);
		if (item != null) {
			if (item.getResult() != null) {
				return item.getResult().getClassForName();
			}else throw new SqlMapException("返回类型未定义,sql节点result属性为空");
		}
		return null;
	}
	
	/**
	 * 
	 * @param sqlId
	 * @return
	 */
	private Map<String, String> getPropertyMap(String sqlId) {
		SqlItem item = (SqlItem) this.getSqlMap().get(sqlId);
		if (item != null) {
			if (item.getResult() != null){
				if (item.getResult().getProperties() != null) {
					return item.getResult().getProperties();
				}
			}
		}
		return null;
	}
	
}
