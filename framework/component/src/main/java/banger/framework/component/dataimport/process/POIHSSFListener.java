package banger.framework.component.dataimport.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportHandler;
import banger.framework.component.dataimport.IImportProcess;
import banger.framework.component.dataimport.IRecordReader;

public class POIHSSFListener implements IImportProcess {

	public POIHSSFListener(){
	}

	public void process(IImportHandler handler) {
		try {
			IImportContext ctx = handler.getContext();
			XLS2CSVmra xls2csv = new XLS2CSVmra(new File(ctx.getFilename()),ctx.getMaxRow(),ctx.getBatch(),handler);
			xls2csv.process();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class XLS2CSVmra implements HSSFListener,IRecordReader {
	    private POIFSFileSystem fs;
	    private int lastRowNumber;
	    @SuppressWarnings("unused")
		private int lastColumnNumber;
	    private boolean outputFormulaValues = true;
	    private SheetRecordCollectingListener workbookBuildingListener;
	    private HSSFWorkbook stubWorkbook;

	    // Records we pick up as we process
	    private SSTRecord sstRecord;
	    private FormatTrackingHSSFListener formatListener;
	   
	   
	    @SuppressWarnings("unused")
		private int sheetIndex = -1;
	    private BoundSheetRecord[] orderedBSRs;
	    @SuppressWarnings("rawtypes")
		private ArrayList boundSheetRecords = new ArrayList();

	    // For handling formulas with string results
	    private int nextRow;
	    private int nextColumn;
	    private boolean outputNextStringRecord;
	    private List<String> rowDatas = null;
	    private File file;
		private IImportHandler handler;
		@SuppressWarnings("unused")
		private String nullString;
		@SuppressWarnings("unused")
		private int maxRow;
		private int batch;
		private int currentRow;
		private int totalRow;

	    public XLS2CSVmra(File file,int maxRow,int batch,IImportHandler handler) throws IOException, FileNotFoundException {
	    	this.file = file;
	    	this.fs = new POIFSFileSystem(new FileInputStream(this.file));
			this.handler = handler;
			this.nullString = "";
			this.maxRow = maxRow;
			this.batch = batch;
			this.currentRow = 0;
			this.totalRow = 0;
	    }
	   
	    public void process() throws IOException {
	        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
	        formatListener = new FormatTrackingHSSFListener(listener);

	        HSSFEventFactory factory = new HSSFEventFactory();
	        HSSFRequest request = new HSSFRequest();

	        if(outputFormulaValues) {
	            request.addListenerForAllRecords(formatListener);
	        } else {
	            workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
	            request.addListenerForAllRecords(workbookBuildingListener);
	        }

	        factory.processWorkbookEvents(request, fs);
	        
	        if(this.currentRow>0 && this.batch>0 && this.currentRow%this.batch>0)this.handler.batchCommit();
	        
	        handler.end();
	    }

	   
	    @SuppressWarnings("unchecked")
		public void processRecord(Record record) {
	        int thisRow = -1;
	        int thisColumn = -1;
	        String thisStr = null;

	        switch (record.getSid())
	        {
	        case BoundSheetRecord.sid:
	            boundSheetRecords.add(record);
	            break;
	        case BOFRecord.sid:
	            BOFRecord br = (BOFRecord)record;
	            if(br.getType() == BOFRecord.TYPE_WORKSHEET) {
	                // Create sub workbook if required
	                if(workbookBuildingListener != null && stubWorkbook == null) {
	                    stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
	                }
	               
	                sheetIndex++;
	                if(orderedBSRs == null) {
	                    orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
	                }
	                /*
	                output.println();
	                output.println(
	                        orderedBSRs[sheetIndex].getSheetname() +
	                        " [" + (sheetIndex+1) + "]:"
	                );
	                */
	            }
	            break;

	        case SSTRecord.sid:
	            sstRecord = (SSTRecord) record;
	            break;

	        case BlankRecord.sid:
	            BlankRecord brec = (BlankRecord) record;

	            thisRow = brec.getRow();
	            thisColumn = brec.getColumn();
	            thisStr = "";
	            break;
	        case BoolErrRecord.sid:
	            BoolErrRecord berec = (BoolErrRecord) record;

	            thisRow = berec.getRow();
	            thisColumn = berec.getColumn();
	            thisStr = "";
	            break;

	        case FormulaRecord.sid:
	            FormulaRecord frec = (FormulaRecord) record;

	            thisRow = frec.getRow();
	            thisColumn = frec.getColumn();

	            if(outputFormulaValues) {
	                if(Double.isNaN( frec.getValue() )) {
	                    // Formula result is a string
	                    // This is stored in the next record
	                    outputNextStringRecord = true;
	                    nextRow = frec.getRow();
	                    nextColumn = frec.getColumn();
	                } else {
	                    thisStr = formatListener.formatNumberDateCell(frec);
	                }
	            } else {
	                thisStr = '"' +
	                    //HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedexpression_r()) + '"';
	                	HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
	            }
	            break;
	        case StringRecord.sid:
	            if(outputNextStringRecord) {
	                // String for formula
	                StringRecord srec = (StringRecord)record;
	                thisStr = srec.getString();
	                thisRow = nextRow;
	                thisColumn = nextColumn;
	                outputNextStringRecord = false;
	            }
	            break;

	        case LabelRecord.sid:
	            LabelRecord lrec = (LabelRecord) record;

	            thisRow = lrec.getRow();
	            thisColumn = lrec.getColumn();
	            thisStr = '"' + lrec.getValue() + '"';
	            break;
	        case LabelSSTRecord.sid:
	            LabelSSTRecord lsrec = (LabelSSTRecord) record;

	            thisRow = lsrec.getRow();
	            thisColumn = lsrec.getColumn();
	            if(sstRecord == null) {
	                thisStr = '"' + "(No SST Record, can't identify string)" + '"';
	            } else {
	                thisStr = '"' + sstRecord.getString(lsrec.getSSTIndex()).toString() + '"';
	            }
	            break;
	        case NoteRecord.sid:
	            NoteRecord nrec = (NoteRecord) record;

	            thisRow = nrec.getRow();
	            thisColumn = nrec.getColumn();
	            // TODO: Find object to match nrec.getShapeId()
	            thisStr = '"' + "(TODO)" + '"';
	            break;
	        case NumberRecord.sid:
	            NumberRecord numrec = (NumberRecord) record;

	            thisRow = numrec.getRow();
	            thisColumn = numrec.getColumn();

	            // Format
	            thisStr = formatListener.formatNumberDateCell(numrec);
	            break;
	        case RKRecord.sid:
	            RKRecord rkrec = (RKRecord) record;

	            thisRow = rkrec.getRow();
	            thisColumn = rkrec.getColumn();
	            thisStr = '"' + "(TODO)" + '"';
	            break;
	        default:
	            break;
	        }

	        // Handle new row
	        if(thisRow != -1 && thisRow != lastRowNumber) {
	            lastColumnNumber = -1;
	        }

	        // Handle missing column
	        if(record instanceof MissingCellDummyRecord) {
	            MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
	            thisRow = mc.getRow();
	            thisColumn = mc.getColumn();
	            thisStr = "";
	        }

	        // If we got something to print out, do so
	        if(thisStr != null) {
	            if(thisColumn > 0) {
	                //output.print(',');
	            }
	            //output.print(thisStr);
	        }

	        // Update column and row count
	        if(thisRow > -1)
	            lastRowNumber = thisRow;
	        if(thisColumn > -1)
	            lastColumnNumber = thisColumn;
	        
	        if(rowDatas==null)
	        	rowDatas = new LinkedList<String>();
	        
	        rowDatas.add(thisStr);

	        // Handle end of row
	        if(record instanceof LastCellOfRowDummyRecord) {
	            // Print out any missing commas if needed
	        	/*
	            if(minColumns > 0) {
	                // Columns are 0 based
	                if(lastColumnNumber == -1) { lastColumnNumber = 0; }
	                for(int i=lastColumnNumber; i<(minColumns); i++) {
	                    //output.print(',');
	                }
	            }
	            */

	            // We're onto a new row
	            lastColumnNumber = -1;
	            
	            this.handler.read(this);
	            if(this.currentRow>0 && this.batch>0 && this.currentRow%this.batch==0)this.handler.batchCommit();
	            this.currentRow++;
	            this.maxRow++;
	            
	            rowDatas = new LinkedList<String>();

	            // End the row
	           //output.println();
	        }
	    }

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
