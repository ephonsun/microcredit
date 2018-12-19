package banger.framework.component.dataimport.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportHandler;
import banger.framework.component.dataimport.IImportProcess;
import banger.framework.component.dataimport.IRecordReader;

public class DELFileReader implements IImportProcess {

	@Override
	public void process(IImportHandler handler) {
		IImportContext ctx = handler.getContext();
		new DELFileLoader(new File(ctx.getFilename()),ctx.getMaxRow(),ctx.getBatch(),handler).readFile();
		handler.end();
	}
	
	class DELFileLoader implements IRecordReader {
		private int batch;			//一批数量
		private int currentRow;		//当前读取行号
		private int totalRow;		//行总数
		private int maxRow;
		private File file;
		private IImportHandler handler;
		private List<String> rowDatas;
		
		public DELFileLoader(File file,int maxRow,int batch,IImportHandler handler){
			this.file = file;
			this.maxRow = maxRow;
			this.batch = batch;
			this.handler = handler;
			this.totalRow = -1;
			this.rowDatas = new ArrayList<String>();
		}
		
		public void readFile(){
			try {
				FileInputStream stream = new FileInputStream(this.file);
				InputStreamReader inputReader = new InputStreamReader(stream,"utf-8");
		    	BufferedReader reader=new BufferedReader(inputReader);
		    	String line = "";
		    	while ((line = reader.readLine()) != null) {
					this.parserLineString(line);
					if(currentRow>maxRow)break;
				}
				stream.close();
				inputReader.close();
				reader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void parserLineString(String line){
			String part = "";
			int state =0 ;		//解析状态
			this.rowDatas.clear();
			for(int i=0;i<line.length();i++){
				char c = line.charAt(i);
				state = this.parser(c, state);
				if(state==-1){
					this.rowDatas.add(this.format(part));
					part="";
				}else{
					part+=c;
				}
			}
			this.handler.read(this);
			if(this.currentRow>0 && this.batch>0 && this.currentRow%this.batch==0)this.handler.batchCommit();
			currentRow++;
		}
		
		/**
		 * 解析字符串
		 * @param c
		 * @param state 0:开始  1:非字符串开头 -1:结束 2:字符串开头 -2:字符串关闭  
		 * @return
		 */
		private int parser(char c,int state){
			if(c!=' ' && c!='	'){
				if(state==0 || state==-1){
					if(c==',')return -1;
					return (c=='"')?2:1;
				}else if(state==1)return (c==',')?-1:1;
				else if(state==2)return (c=='"')?-2:2;
				else if(state==-2){
					if(c==',')return -1;
					if(c=='"')return 2;
				}
			}
			return state;
		}
		
		/**
		 * 字符串内容处理
		 * @param str
		 * @return
		 */
		private String format(String str){
			String part = str.trim();
			if(part.length()>0){
				if(part.charAt(0)=='"'){
					part=part.substring(1,part.length()-1);
					part=part.replaceAll("\"\"", "\"");
				}else{
					if(part.charAt(0)=='+' || part.charAt(0)=='-'){
						int n = part.indexOf("E");
						double s1 = Double.parseDouble(part.substring(1,n));
						int s2 = Integer.parseInt(part.substring(n+2));
						return s1*s2+"";
					}
				}
			}
			return part;
		}

		public Object getValue(int index) {
			if(this.rowDatas!=null && index<this.rowDatas.size()){
				return this.rowDatas.get(index);
			}
			return null;
		}

		public List<String> getRow() {
			return this.rowDatas;
		}

		public int size() {
			if(this.rowDatas!=null){
				return this.rowDatas.size();
			}
			return 0;
		}
		
		public int rownum() {
			return this.currentRow;
		}
		
		public int total() {
			return this.totalRow ;
		}
		
	}

}
