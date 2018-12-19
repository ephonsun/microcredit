package banger.framework.codetable;

import java.util.ArrayList;
import java.util.List;

import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.dao.IDataDao;

public class DataCodeTable extends AbstractCodeTable {
	private IDataDao dao;

	public void setDao(IDataDao dao) {
		this.dao = dao;
	}

	
	public List<IItem> getItems(Object ... conditions) {
		if(this.dao!=null){
			DataTable table = this.dao.queryData(sqlId,conditions);
			List<IItem> items = new ArrayList<IItem>();
			for(DataRow row : table){
				IItem item = this.getItem(row);
				items.add(item);
			}

			return items;
		}else throw new RuntimeException("DataCodeTable没有配置Dao");
	}

}
