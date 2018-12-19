package banger.framework.web.dojo;

import banger.framework.collection.DataColumn;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.framework.web.json.*;

public class DataTableConvert extends AbstractConvert {
	
	@Override
	public Object toObject(Class<?> clazz,JsonElement json) {
		DataTable table  = (DataTable) ReflectorUtil.newInstance(clazz);
		if(json instanceof JsonArray){
			JsonArray ja = (JsonArray)json;
			this.copyTo(table,ja);
		}else if(json instanceof JsonObject){
			JsonObject jo = (JsonObject)json;
			this.copyTo(table,jo);
		}else if(json instanceof JsonPair){
			JsonPair jp = (JsonPair)json;
			if(jp.getPropertyValue() instanceof JsonArray)
			{
				JsonArray ja = (JsonArray)jp.getPropertyValue();
				String tableName = jp.getPropertyName().getValue();
				table.setName(tableName);
				this.copyTo(table,ja);
			}
			else throw new DojoConvertException(StringUtil.format("Json转化为DataTable对像出错 JsonString {0}", json));
		}
		return table;
	}
	
	private void copyTo(DataTable table,JsonValue jv){
		if(jv instanceof JsonObject){
			JsonObject jo = (JsonObject)jv;
			if(table.colSize()==0){
				String[] colNames = this.getColumnNames(jo);
				for(String colName : colNames)
					table.addColumn(colName);
			}
			DataRow row = table.newRow();
			for(JsonPair jp : jo.getPairs()){
				String colName = jp.getPropertyName().getValue();
				JsonValue val = jp.getPropertyValue();
				if(val instanceof JsonObject || val instanceof JsonArray){
					Object obj = this.toObject(DataTable.class,val);
					row.set(colName,obj);
				}else if(val!=null && val.getType()!=JsonValueType.Null){
					Class<?> valType = JsonType.getValueType(val);
					IConvert convert = this.selector.getConvert(valType);
					Object obj = convert.toObject(valType,val);
					row.set(colName,obj);
				}
			}
		}
		else throw new DojoConvertException(StringUtil.format("Json转化为DataRow对像出错 JsonString {0}", jv));
	}
	
	private void copyTo(DataTable table,JsonArray ja)
	{
		for(JsonValue jv : ja.getValues()){
			copyTo(table,jv);
		}
	}
	
	private String[] getColumnNames(JsonObject jo){
		int size = jo.getPairs().size();
		String[] colNames = new String[size];
		for(int i=0;i<size;i++){
			JsonPair jp = jo.getPairs().get(i);
			colNames[i] = jp.getPropertyName().getValue();
		}
		return colNames;
	}

	/**
	 * DataTable转Json对像
	 */
	@Override
	public JsonElement toJson(Object data) {
		DataTable table = (DataTable)data;
		JsonArray ja = new JsonArray();
		DataColumn[] cols = table.getColumns();
		for(DataRow row : table){
			JsonObject jo = new JsonObject();
			for(DataColumn col : cols){
				String colName = col.getName();
				Object val = row.get(colName);
				JsonPair jp = new JsonPair();
				jp.setPropertyName(new JsonString(colName));
				if(val!=null){
					IConvert convert = this.selector.getConvert(val.getClass());
					JsonValue jv = (JsonValue)convert.toJson(val);
					jp.setPropertyValue(jv);
				}else{
					jp.setPropertyValue(new JsonNull());
				}
				jo.getPairs().add(jp);
			}
			ja.getValues().add(jo);
		}
		return ja;
	}

	@Override
	public boolean enableConvert(Class<?> clazz) {
		return DataTable.class.equals(clazz);
	}

	@Override
	public int order() {
		return 0;
	}
	
}
