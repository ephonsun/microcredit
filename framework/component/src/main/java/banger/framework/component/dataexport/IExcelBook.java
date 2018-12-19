package banger.framework.component.dataexport;

import java.io.OutputStream;
import java.util.List;

public interface IExcelBook {

	/**
	 * 添加一个Excel工作表
	 * @param index	序号
	 * @param name 名称
	 * @return 工作表
	 */
	IExcelSheet addSheet(String name, String[] columns);
	
	/**
	 * 得到所有工作表对像
	 * @return
	 */
	List<IExcelSheet> getSheets();
	
	/**
	 * 
	 */
	void write();
	
	/**
	 * 
	 */
	void write(OutputStream stream);
	
}
