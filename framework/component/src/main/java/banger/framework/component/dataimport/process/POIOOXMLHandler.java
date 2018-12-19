package banger.framework.component.dataimport.process;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportHandler;
import banger.framework.component.dataimport.IImportProcess;
import banger.framework.component.dataimport.IRecordReader;

/**
 * Excel读取包装类
 */
public class POIOOXMLHandler implements IImportProcess {

	public POIOOXMLHandler(){

	}
	
	@Override
	public void process(IImportHandler handler) {
		IImportContext ctx = handler.getContext();
		new ExcelHandler(new File(ctx.getFilename()),"1",ctx.getMaxRow(),ctx.getBatch(),handler).start();
		handler.end();
	}
	
	private enum XSSFDataType {
        BOOL,
        ERROR,
        FORMULA,
        INLINESTR,
        SSTINDEX,
        NUMBER,
    }
	
	/**
	 * 以下是网上公开的实子,修改了部份方法
	 */
	private class ExcelHandler extends DefaultHandler implements IRecordReader {
		protected transient final Log log = LogFactory.getLog(getClass());
		private XSSFReader xssfReader;
		private ReadOnlySharedStringsTable sst;
		private StylesTable stylesTable;
		private boolean vIsOpen = false;
		private StringBuffer value = new StringBuffer();
		protected XSSFDataType nextDataType;
		// 当前遍历的Excel单元格列索引
		protected int thisColumnIndex = -1;
	    // The last column printed to the output stream
	    protected int lastColumnIndex = -1;
	    protected int maxColumnCount = -1;
	    protected int formatIndex;
	    protected String formatString;
	    private final DataFormatter formatter = new DataFormatter();
		private List<String> rowDatas = null;
		
	    private File file;
	    private String sheetIndex;
		private String nullString;
		private IImportHandler handler;
		private int maxRow;
		private int batch;
		private int currentRow;
		private int totalRow;
		
		public ExcelHandler(File file,String sheetIndex,int maxRow,int batch,IImportHandler handler){
			this.file = file;
			this.sheetIndex = sheetIndex;
			this.handler = handler;
			this.nullString = "";
			this.maxRow = maxRow;
			this.batch = batch;
			this.currentRow = 0;
			this.totalRow = 0;
		}
		
		/**
		 * 初始化
		 */
		public void start(){
			try {
				OPCPackage op = OPCPackage.open(file.getAbsolutePath(),PackageAccess.READ);
				this.xssfReader = new XSSFReader(op);
				this.sst = new ReadOnlySharedStringsTable(op);
				this.stylesTable = xssfReader.getStylesTable();
				
				// 开始解析
				parseXmlContent(new InputSource(getOneSheetStream(sheetIndex)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if("dimension".equals(qName)){
				String dimension = attributes.getValue("ref");
				String[] part = dimension.split(":");
				if(part.length>1){
					String str="";
					for(int i=0;i<part[1].length();i++){
						if(part[1].charAt(i)>=48 && part[1].charAt(i)<=57)str+=part[1].charAt(i);
					}
					if(str!=null && str.length()>0)this.totalRow = Integer.parseInt(str);
				}
			}
			else if ("inlineStr".equals(qName) || "v".equals(qName)) {
				vIsOpen = true;
				// Clear contents cache
				value.setLength(0);
			}
			// c => cell
			else if ("c".equals(qName)) {
				// Get the cell reference
				String r = attributes.getValue("r");
				int firstDigit = -1;
				for (int c = 0; c < r.length(); ++c) {
					if (Character.isDigit(r.charAt(c))) {
						firstDigit = c;
						break;
					}
				}
				// 当前列索引
				thisColumnIndex = nameToColumn(r.substring(0, firstDigit));

				for (int i = lastColumnIndex+1; i < thisColumnIndex; ++i) {
					rowDatas.add(nullString);
					lastColumnIndex++;
				}
				//if (thisColumnIndex > -1)
				//	lastColumnIndex = thisColumnIndex;

				// Set up defaults.
				this.nextDataType = XSSFDataType.NUMBER;
				this.formatIndex = -1;
				this.formatString = null;
				String cellType = attributes.getValue("t");
				String cellStyleStr = attributes.getValue("s");
				if ("b".equals(cellType))
					nextDataType = XSSFDataType.BOOL;
				else if ("e".equals(cellType))
					nextDataType = XSSFDataType.ERROR;
				else if ("inlineStr".equals(cellType))
					nextDataType = XSSFDataType.INLINESTR;
				else if ("s".equals(cellType))
					nextDataType = XSSFDataType.SSTINDEX;
				else if ("str".equals(cellType))
					nextDataType = XSSFDataType.FORMULA;
				else if (cellStyleStr != null) {
					// It's a number, but almost certainly one
					//  with a special style or format 
					int styleIndex = Integer.parseInt(cellStyleStr);
					XSSFCellStyle style = this.stylesTable.getStyleAt(styleIndex);
					this.formatIndex = style.getDataFormat();
					this.formatString = style.getDataFormatString();
					if (this.formatString == null)
						this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
				}
			}
			// row => 行开始
			else if ("row".equals(qName)) {
				rowDatas = new LinkedList<String>();
			}else{
				System.out.print("");
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			Object thisObj = null;
			
			if("v".equals(qName)) {
				thisObj = extractCellValue(value,nextDataType);	// 当前单元格数据
				
				if (lastColumnIndex == -1) {
					lastColumnIndex = 0;
				}
				try {
					
					for (int i = lastColumnIndex+1; i < thisColumnIndex; ++i) {
						rowDatas.add(nullString);
					}

					rowDatas.add(thisObj==null?nullString:String.valueOf(thisObj));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				// Update column
				if (thisColumnIndex > -1)
					lastColumnIndex = thisColumnIndex;
			}else if("row".equals(qName)) {
				try {
					// Print out any missing commas if needed
					if (maxColumnCount > 0) {
						// Columns are 0 based
						if (lastColumnIndex == -1) {
							lastColumnIndex = 0;
						}
						for (int i = lastColumnIndex; i < (this.maxColumnCount)-1; i++) {
							rowDatas.add(nullString);
						}
					}
					
					this.handler.read(this);
					if(this.currentRow>0 && this.batch>0 && this.currentRow%this.batch==0)this.handler.batchCommit();
					currentRow++;			//当前行
					
				}catch (Exception e){
					e.printStackTrace();
				}
				
				lastColumnIndex = -1;
				
				// 抛出异常来中止读取
				if(currentRow>=maxRow){
					super.endDocument();
					throw new SAXException("取出超出设定行数");
				}
			}else{
//				System.out.println(qName);
				//rowDatas.add(nullString);
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if(vIsOpen){
				value.append(ch,start,length);
			}
		}
		
		@Override
		public void endDocument() throws SAXException {
			if(this.currentRow>0 && this.batch>0 && this.currentRow%this.batch>0)this.handler.batchCommit();
		}

		/**
		 * <p>获取指定sheet的数据流</p>
		 * @param sheetId
		 * @return
		 */
		private InputStream getOneSheetStream(String sheetId){
			InputStream in = null;
			try {
				in = xssfReader.getSheet("rId"+sheetId);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return in;
		}
		
		/**
		 * <p>以xml的格式解析Excel数据</p>
		 * @param sheetSource
		 * @param
		 * @param
		 * @throws Exception
		 */
		private void parseXmlContent(InputSource sheetSource) throws Exception {
			XMLReader xmlReader = null;
			try {
				System.out.println("create XML reader.");
				xmlReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
				xmlReader.setContentHandler(this);
				xmlReader.parse(sheetSource);
			}catch (SAXException e) {
				if(!"取出超出设定行数".equals(e.getMessage())){
					e.printStackTrace();
					log.error("取出超出设定行数 error:"+e.getMessage(),e);
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error("以xml的格式解析Excel数据 error:"+e.getMessage(),e);
			}
		}
		
		/**
		 * 从列名转换为列索引
		 * @param name
		 * @return
		 */
		private int nameToColumn(String name) {
	        int column = -1;
	        for (int i = 0; i < name.length(); ++i) {
	            int c = name.charAt(i);
	            column = (column + 1) * 26 + c - 'A';
	        }
	        return column;
	    }
		
		/**
		 * 抽取单元格数据
		 * @param value
		 * @param nextDataType
		 * @return
		 */
		private Object extractCellValue(StringBuffer value,XSSFDataType nextDataType){
			Object obj = "";
			switch(nextDataType) {
			case BOOL : 
				char first = value.charAt(0);
				obj = first=='0'?false:true;
				break;
			case ERROR : 
				obj = value.toString().trim();
				break;
			case FORMULA : 
				obj = value.toString().trim();
				break;
			case INLINESTR : 
				XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
				obj = rtsi.toString().trim();
				break;
			case SSTINDEX : 
				String sstIndex = value.toString().trim();
				try {
					int idx = Integer.parseInt(sstIndex);
					XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
					obj = rtss.toString().trim();
				}catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
				break;
			case NUMBER :
				String n = value.toString().trim();
				if (this.formatString != null) {
					obj = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex, this.formatString);
				}else{
					obj = n;
				}
				break;
			default:
				obj = "";
				break;
			}
			
			return obj;
		}

		@Override
		public Object getValue(int index) {
			if(this.rowDatas!=null && index<this.rowDatas.size()){
				return this.rowDatas.get(index);
			}
			return null;
		}
		
		public List<String> getRow(){
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
