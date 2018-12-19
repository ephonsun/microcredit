package banger.framework.component.dataimport.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportHandler;
import banger.framework.component.dataimport.IImportProcess;

public class POIExcel2003Listener implements IImportProcess {

		public POIExcel2003Listener(){
			
		}

		public void process(IImportHandler handler) {
			try {
				IImportContext ctx = handler.getContext();
				Excel2003Reader excelReader = new Excel2003Reader(new File(ctx.getFilename()),ctx.getMaxRow(),ctx.getBatch(),handler);
				excelReader.process();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
