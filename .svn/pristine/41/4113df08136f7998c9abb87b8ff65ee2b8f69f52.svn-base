package banger.framework.component.dataexport;

import java.io.IOException;

import banger.framework.component.dataexport.process.CSVExcelBook;
import banger.framework.component.dataexport.process.POIExcelBook;

public class ExcelExport {

	/**
	 * 创建一个Excel工作薄对像
	 * @param mode	导出的方式
	 * @param filename 文件名
	 * @return Excel工作薄接口
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws WriteException 
	 */
	public static IExcelBook createBook(String filename,ExportMode mode) throws IOException {
		switch(mode){
			case POI:
				return new POIExcelBook(filename);
			case CSV:
				return new CSVExcelBook(filename);
			default:
				return null;
		}
	}
	
}
