package banger.framework.component.dataexport.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import banger.framework.component.dataexport.IExcelBook;
import banger.framework.component.dataexport.IExcelSheet;
import banger.framework.util.FileUtil;

public class POIExcelBook implements IExcelBook {
	private HSSFWorkbook book;
	private String filename;
	private List<IExcelSheet> sheets;
	
	public POIExcelBook(String filename){
		this.filename = filename;
		this.book = new HSSFWorkbook();
		this.sheets = new ArrayList<IExcelSheet>();
	}
	
	public IExcelSheet addSheet(String name,String[] columns) {
		HSSFSheet sheet = this.book.createSheet(name);
		IExcelSheet sheetWrapper = new POIExcelSheetWrapper(name,columns,sheet);
		this.sheets.add(sheetWrapper);
		return sheetWrapper;
	}
	
	public List<IExcelSheet> getSheets(){
		return this.sheets;
	}

	public void write(){
		try {
		    String filePath = FileUtil.getParentPath(this.filename);
		    FileUtil.createDirectory(filePath);
            File newfile = new File(this.filename);
            if(! newfile.exists()){
                newfile.createNewFile();
            }
            
			FileOutputStream file = new FileOutputStream(newfile);
			 
			book.write(file);
			file.flush();
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(OutputStream stream){
		try {
			book.write(stream);
			stream.flush();
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class  POIExcelSheetWrapper implements IExcelSheet{
		private int rowSize;
		private HSSFSheet sheet;
		
		public POIExcelSheetWrapper(String name,String[] columns,HSSFSheet sheet){
			this.rowSize = 0;
			this.sheet = sheet;
			this.addHeadRow(columns);
		}
		
		private void addHeadRow(String[] columns){
			HSSFRow newRow = sheet.createRow(0);
			this.rowSize++;
			for(int i=0;i<columns.length;i++){
				if(columns[i]!=null){
					Cell cell = newRow.createCell(i);
					cell.setCellValue(columns[i]);
				}
			}
		}
		
		public void addRow(Object[] row){
			HSSFRow newRow = sheet.createRow(rowSize);
			this.rowSize++;
			for(int i=0;i<row.length;i++){
				if(row[i]!=null){
					Cell cell = newRow.createCell(i);
					cell.setCellValue(row[i].toString());
				}
			}
		}
		
		/**
		 * 
		 */
		public int rowSize(){
			return rowSize;
		}

		/**
		 * 向Excel工作表添加数据
		 * @param rows 多行数据
		 */
		public void addRows(List<Object[]> rows) {
			if(rows!=null){
				rows.toArray(new Object[0][0]);
			}
		}

		/**
		 * 向Excel工作表添加数据
		 * @param rows 多行数据
		 */
		public void addRows(Object[][] rows) {
			if(rows!=null){
				for(Object[] row : rows){
					this.addRow(row);
				}
			}
		}
		
	}
}
