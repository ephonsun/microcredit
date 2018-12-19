package banger.framework.component.dataexport.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import banger.framework.component.dataexport.IExcelBook;
import banger.framework.component.dataexport.IExcelSheet;
import banger.framework.util.FileUtil;

public class CSVExcelBook implements IExcelBook {
	private List<IExcelSheet> sheets;
	private File file;
	private FileOutputStream fileStream;
	private OutputStreamWriter writer;
	
	public CSVExcelBook(String filename) throws IOException{
		this.file=new File(filename);
		this.sheets = new ArrayList<IExcelSheet>();
		String path = FileUtil.getParentPath(filename)+"/";
        if(!FileUtil.isExistFilePath(path))
        	FileUtil.createDirectory(path);
        if (FileUtil.isExistFile(filename)){
        	file.delete();
        }
        file.createNewFile();
		this.fileStream = new FileOutputStream(this.file);
		this.writer = new OutputStreamWriter(fileStream);
	}
	
	public IExcelSheet addSheet(String name, String[] columns) {
		if(this.sheets.size()==0){
			IExcelSheet sheet = new CSVExcelSheet(this);
			this.sheets.add(sheet);
			String headStr = this.buildHeadString(columns);
			try {
				this.writer.write(headStr+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.sheets.get(0);
	}

	public List<IExcelSheet> getSheets() {
		return this.sheets;
	}
	
	private String buildHeadString(String[] columns){
		String columnStr ="";
		for(int i=0;i<columns.length;i++){
		    String str ="";
            if(columns[i]!=null){
            	String val = columns[i].replaceAll("\"","\"\"").replaceAll("\n", "\r\n");
            	str = "\""+val+"\"";
            }
            if(i==0)columnStr+=str;
            else columnStr+=","+str;
        }
		return columnStr;
	}

	public void write() {
		try {
			this.writer.close();
			this.fileStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class CSVExcelSheet implements IExcelSheet{
		private int rowSize;
		private CSVExcelBook book;
		
		public CSVExcelSheet(CSVExcelBook book){
			this.rowSize = 1;
			this.book = book;
		}

		public int rowSize() {
			return rowSize;
		}
		
		/**
		 * 前多少位是数字
		 * @param bit
		 * @return
		 */
		private boolean isNumber(String str,int min,int max){
			if(str.length()>=min){
				for(int i=0;i<min;i++){
					if(!Character.isDigit(str.charAt(i)))return false;
				}
				if(str.length()>max){				//科学计数法,位数不能太于255
					return false;
				}else{
					return true;
				}
			}else if(str.length()>0 && str.charAt(0)=='0'){				//首字母是0
				for(int i=0;i<str.length();i++){
					if(!Character.isDigit(str.charAt(i)))return false;
				}
				return true;
			}
			return false;
		}
		
		private String buildDataString(Object[] row){
			String rowStr ="";
			for(int i=0;i<row.length;i++){
			    String str ="";
	            if(row[i]!=null){
	            	String val = this.removeWPSTable(row[i].toString().replaceAll("\"", "\"\""));
	            	if(val.indexOf(',')>-1 || val.indexOf('\n')>-1){
	            		str = "\""+val+"\"";
	            	}else if(isNumber(val,10,255)){
	            		str = "=\""+val+"\"";
	            	}else{
	            		str = val;
	            	}
	            }
	            if(i==0)rowStr+=str;
	            else rowStr+=","+str;
	        }
			return rowStr;
		}
		
		private String removeWPSTable(String str){
			if(str!=null && str.length()>5){
				int n = str.toLowerCase().indexOf("<table");
				if(n>-1){
					return str.substring(0, n)+str.substring(n+1);
				}
			}
			return str;
		}

		public void addRow(Object[] row) {
			rowSize++;
			String rowStr = this.buildDataString(row);
			try {
				this.book.writer.write(rowStr+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void addRows(List<Object[]> rows) {
			if(rows!=null){
				this.addRows(rows.toArray(new Object[0][0]));
			}
		}

		public void addRows(Object[][] rows) {
			if(rows!=null){
				for(Object[] row : rows){
					this.addRow(row);
				}
			}
		}
	}

	@Override
	public void write(OutputStream stream) {
		throw new RuntimeException("没有实现此函数");
	}
}
