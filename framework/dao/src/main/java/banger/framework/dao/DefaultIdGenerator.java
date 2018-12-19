package banger.framework.dao;

import java.util.HashMap;
import java.util.Map;

import banger.framework.entity.IEntityMap;
import banger.framework.spring.SpringContext;
import banger.framework.sql.SqlDriverException;
import banger.framework.sql.SqlExecuteException;
import banger.framework.sql.util.ISqlHelper;
import banger.framework.sql.util.SqlHelper;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;

/**
 * 产生主键Id的类,支持多数据库版本
 */
public class DefaultIdGenerator implements IdGenerator {	
	private Map<String,IdStore> idStores;
	private Object lockObj = new Object();
	private static final String EntityMapKey = "EntityMap";
	
	protected IEntityMap getEntityMap()
	{
		return (IEntityMap)SpringContext.instance().get(EntityMapKey);
	}
	
	public DefaultIdGenerator()
	{
		idStores = new HashMap<String,IdStore>();
	}
	
	public Long getId(Class<?> entityType){
		String tableName = getEntityMap().getTable(entityType);
		return this.getId(tableName);
	}
	
	public Long getId(String tableName){
		return this.getId(tableName, "");
	}
	
	@Override
	public Long[] getId(String tableName, int num, String dbConfig) {
		Long[] ids = this.getId(tableName, num,"",dbConfig);
		return ids;
	}
	
	public Long[] getId(Class<?> entityType,int num){
		String tableName = getEntityMap().getTable(entityType);
		return this.getId(tableName,num);
	}
	
	public Long[] getId(String tableName,int num){
		return this.getId(tableName,num,"");
	}
	
	public Long getId(Class<?> entityType,String dbConfig){
		String tableName = getEntityMap().getTable(entityType);
		return this.getId(tableName,dbConfig);
	}
	
	public Long getId(String tableName,String dbConfig){
		Long[] ids = this.getId(tableName, 1, dbConfig);
		return ids[0];
	}
	
	public Long[] getId(Class<?> entityType,int num,String dbConfig){
		String tableName = getEntityMap().getTable(entityType);
		return this.getId(tableName, num,"",dbConfig);
	}
	
	public Long getPkId(String tableName,String pk){
		Long[] ids = this.getId(tableName,1,pk,"");
		return ids[0];
	}
	
	public Long[] getPkId(String tableName,String pk,int num){
		Long[] ids = this.getId(tableName,num,pk,"");
		return ids;
	}
	
	public Long getPkId(String tableName,String pk,String dbConfig){
		Long[] ids = this.getId(tableName,1,pk,dbConfig);
		return ids[0];
	}
	
	public Long[] getPkId(String tableName,String pk,int num,String dbConfig){
		Long[] ids = this.getId(tableName,num,pk,dbConfig);
		return ids;
	}
	
	public Long[] getId(String tableName,int num,String pk,String dbConfig){
		synchronized(lockObj) {
			ISqlHelper ish = ("".equals(dbConfig))?SqlHelper.instance():SqlHelper.instance(dbConfig);
			try{
				String dbType = ish.getDriver().getDBType();
				if(!StringUtil.isNullOrEmpty(tableName)){
					if(!idStores.containsKey(dbType)){
						if(ish.getDialect().supportsSequences()){
							IdStore store = new Sequence();
							idStores.put(dbType, store);
						}else{
							IdStore store = new SqlStore();
							idStores.put(dbType, store);
						}
					}
					IdStore gen = idStores.get(dbType);
					if(!gen.isExist(tableName,ish)){
						long maxId = getMaxPKValue(tableName,pk,ish);
						gen.create(tableName, maxId, ish);
					}
					if(num>1){
						Long[] ids = gen.getId(tableName,num,ish);
						return ids;
					}else{
						return new Long[]{gen.getId(tableName, ish)};
					}
				}
				else throw new EntityMappingException("获取Id出错 表名不能为空");
			}catch(EntityMappingException e){
				throw e;
			}catch(SqlExecuteException e){
				throw e;
			}catch(SqlDriverException e){
				throw e;
			}
			/*
			catch(Exception e){
				throw new EntityMappingException(StringUtil.format("获取Id出错 找不到该表{0}的实体映射文件 error:"+e.getMessage(),tableName),e);
			}
			*/
			finally{
				ish.dispose();
			}
        }
	}
	
	private Long getMaxPKValue(String tableName,String pk,ISqlHelper ish){
		String pkName = StringUtil.isNotEmpty(pk)?pk:getEntityMap().getIdColumn(tableName);
		if(StringUtil.isNullOrEmpty(tableName))throw new EntityMappingException(StringUtil.format("获取Id出错 找不到该表{0}的实体映射文件",tableName));
		if(StringUtil.isNullOrEmpty(pkName))throw new EntityMappingException(StringUtil.format("获取Id出错 找不到该表{0}的实体映射文件或未定义主键列",tableName));
		int n = tableName.indexOf(".");
		if(n>-1)tableName = tableName.substring(n+1,tableName.length());
		String sqlString = StringUtil.format("SELECT MAX(\"{0}\") AS VAL FROM \"{1}\"",pkName,tableName);
		try{
			Object value = ish.executeScalar(sqlString);
			long id = (Long)TypeUtil.changeType(value,Long.class);
			return id+1;
		}catch(Exception e){
			return new Long(1);
		}
	}

	interface IdStore{
		Long getId(String name,ISqlHelper ish);
		
		Long[] getId(String name,int num,ISqlHelper ish);
		
		void create(String name,long value,ISqlHelper ish);
		
		boolean isExist(String name,ISqlHelper ish);
	}
	
	class Sequence implements IdStore{
		private Map<String,Long> ids;
		
		public Sequence(){
			ids = new HashMap<String,Long>();
		}
		
		@Override
		public Long getId(String name,ISqlHelper ish){
			String sequenceName=this.getSequenceName(name);
			long id = ish.getDbMeta().getSequenceNextValue(sequenceName);
		    this.ids.put(sequenceName, id);
		    return id;
		}
		
		@Override
		public Long[] getId(String name,int num,ISqlHelper ish){
			String sequenceName=this.getSequenceName(name);
			Long[] ids = ish.getDbMeta().getSequenceNextValue(sequenceName,num);
		    this.ids.put(sequenceName, ids[ids.length-1]);
		    return ids;
		}
		
		@Override
		public void create(String name,long value,ISqlHelper ish){
			String seq=this.getSequenceName(name);
			ish.getDbMeta().createSequence(seq, value);
		}
		
		@Override
		public boolean isExist(String name,ISqlHelper ish){
			String seq=this.getSequenceName(name);
			int n = seq.indexOf(".");
			if(n>-1)seq = seq.substring(n+1,seq.length());
			if(this.ids.containsKey(seq))return true;
			else return ish.getDbMeta().getSequence(seq)!=null;
		}
		
		private String getSequenceName(String name){
			int n = name.indexOf(".");
			String seq = (n>-1)?name.substring(0,n+1)+"SEQ_"+name.substring(n+1,name.length())
					:"SEQ_"+name;
		    return seq;
		}
	}
	
	/**
	 * 不支持Sequence的数据据,产生Id的类
	 * 支持集群
	 */
	class SqlStore implements IdStore{
		private Map<String,IdRange> ids;
		private final long INCREMENT = 1000;
		
		public SqlStore(){
			this.ids = new HashMap<String,IdRange>();
		}
		
		@Override
		public Long getId(String name,ISqlHelper ish) {
			
			if(this.ids.containsKey(name)){
				IdRange range = this.ids.get(name);
				if(range.next())return range.getId();
			}
			long id = this.getTopId(name,ish);
			long topId = id+INCREMENT-1;
			this.ids.put(name,new IdRange(id,topId));
			return id;
		}
		
		@Override
		public Long[] getId(String name,int num, ISqlHelper ish) {
			Long[] ids = new Long[num];
			for(int i=0;i<num;i++){
				ids[i] = this.getId(name, ish);
			}
			return ids;
		}
	
		@Override
		public void create(String name, long value, ISqlHelper ish) {
			String sqlString = StringUtil.format("INSERT INTO \"ID_SEQUENCE\" (\"ID_NAME\",\"ID_VALUE\") VALUES ('{0}',{1})", name,value);
	    	ish.executeNoneQuery(sqlString);
		}
		
		@Override
		public boolean isExist(String name,ISqlHelper ish)
		{
			if(this.ids.containsKey(name))return true;
			else
			{
				String sqlString = StringUtil.format("SELECT * FROM \"ID_SEQUENCE\" WHERE \"ID_NAME\" = '{0}'",name);
				return ish.executeNoneQuery(sqlString)>0;
			}
		}
		
		private long getTopId(String name,ISqlHelper ish)
		{
			Object obj = ish.executeScalar(StringUtil.format("SELECT \"ID_VALUE\" FROM \"ID_SEQUENCE\" WHERE \"ID_NAME\" = '{0}'",name));
			long value = (Long)TypeUtil.changeType(obj,Long.class);
			ish.executeNoneQuery(StringUtil.format("UPDATE \"ID_SEQUENCE\" SET \"ID_VALUE\" = {0} WHERE \"ID_NAME\" = '{1}'",value+INCREMENT,name));
			return value;
		}
		
		class IdRange
		{
			private long id;
			private long topId;
			
			public IdRange(long id,long topId)
			{
				this.id = id;
				this.topId = topId;
			}
			
			public long getId()
			{
				return id;
			}
			
			public boolean next()
			{
				this.id++;
				return (this.id+1)>this.topId;
			}
		}
	}


}
