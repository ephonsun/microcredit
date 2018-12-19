package banger.framework.component.dataimport;

import java.util.List;

/**
 * 导入数据读取
 */
public interface IRecordReader {
	
	/**
	 * 通过列索引得到值
	 * @return
	 */
	Object getValue(int index);
	
	/**
	 * 得到行数据
	 * @return
	 */
	List<String> getRow();

	/**
	 * 列数
	 * @return
	 */
	int size();
	
	/**
	 * 行号
	 * @return
	 */
	int rownum();
	
	/**
	 * 总行数
	 * @return
	 */
	int total();
}
