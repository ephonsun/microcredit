package banger.framework.component.dataimport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import banger.framework.util.FileUtil;
import banger.framework.component.constraint.IConstraint;
import banger.framework.component.dataimport.process.DELFileReader;
import banger.framework.component.dataimport.process.POIExcel2003Listener;
import banger.framework.component.dataimport.process.POIOOXMLHandler;
import banger.framework.component.progress.ProgressBar;
import banger.framework.util.StringUtil;
import org.apache.commons.io.FileUtils;

public abstract class AbstractImportHandler implements IImportHandler,IImportResult {
	protected List<String> head;						//文件列名
	protected ProgressBar progressBar;					//进度条对像
	protected IImportContext context;
	protected int rowCount;							//总共多少条
	protected int successCount;							//成功数
	protected int failCount;							//失败数
	protected int insertCount = 0;					//新增多少条
	protected int updateCount;					//更新多少条
	protected String failFilename;				//导入失败文件
    protected String reportFileName;             // 导入报表文件名

	public int getSuccessCount() {
		return successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public List<String> getHead(){
		return this.head;
	}
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getInsertCount() {
		return insertCount;
	}

	public void setInsertCount(int insertCount) {
		this.insertCount = insertCount;
	}

	public int getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	
	public String getFailFilename() {
		return failFilename;
	}

	public void setFailFilename(String failFilename) {
		this.failFilename = failFilename;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    /**
	 * 设置导入上下文
	 * @param context
	 */
	public void setContext(IImportContext context){
		this.context = context;
	}
	
	/**
	 * 得到导入上下文
	 */
	public IImportContext getContext(){
		return this.context;
	}
	
	@Override
	public void read(IRecordReader reader) {
		if(reader.rownum()>0){
			if(this.progressBar!=null)this.progressBar.setCurrent(reader.rownum());
			//this.rowCount = reader.total();
		}else{
			if(head!=null)head.clear();
			else head=new ArrayList<String>();
			for(int i=0;i<reader.size();i++){
				head.add((String)reader.getValue(i));
			}
			if(this.progressBar!=null)this.progressBar.setTotal(reader.total()-1);
		}
	}
	
	protected boolean valid(Object data){
		List<IConstraint> constraints = this.context.getConstraints();
		for(IConstraint constraint : constraints){
			constraint.verify(data);
		}
		return true;
	}
	
	/**
	 * 开始执行导入
	 */
	public void start(){
		String fix = FileUtil.getFileFix(context.getFilename());
		if(fix.equals(".xls")){
			new POIExcel2003Listener().process(this);
		}else if(fix.equals(".xlsx")){
			new POIOOXMLHandler().process(this);
		}else if(fix.equals(".del")){
			new DELFileReader().process(this);
		}
	}
	
	/**
	 * 导入结束
	 */
	public void end(){
		if(this.progressBar!=null){
			this.progressBar.setCompleted(true);
			this.progressBar.setClosed(true);
		}
	}
	
	public IImportResult getResult(){
		return this;
	}

    /**
     * 写错误文件内容
     *
     * @param reader
     * @param error
     */
    public void writeErrorFileByLine(IRecordReader reader, String childFileName, String error) {
        String filename = this.getFailFilename();
        if (StringUtil.isNullOrEmpty(filename)) {
            // 文件不存在时，先创建文件
            String path = FileUtil.getParentPath(this.context.getFilename());
            filename = FileUtil.contact(path, childFileName + "errorExcel.csv");
            String content = "错误信息";
            if(FileUtil.isExistFile(filename)){
                FileUtil.deleteDir(new File(filename));
            }
            for(String head : this.head){
                content = content + "," + head;
            }

			try {
				FileUtils.write(new File(filename),content,"GBK",true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setFailFilename(filename);
        }

        String content = "\r\n" + error;
        for(int i = 0; i < this.head.size(); i++){
            content = content + "," + (reader.getValue(i) == null ? "" : reader.getValue(i));
        }
		try {
			FileUtils.write(new File(filename),content,"GBK",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.failCount++;
    }

	/**
	 * 写错误文件内容
	 *
	 * @param
	 * @param error
	 */
	public void writeErrorFileByLine(String content, String childFileName, String error) {
		String filename = this.getFailFilename();
		if (StringUtil.isNullOrEmpty(filename)) {
			// 文件不存在时，先创建文件
			String path = FileUtil.getParentPath(this.context.getFilename());
			filename = FileUtil.contact(path, childFileName + "errorExcel.csv");
			String headContent = "错误信息";
			if(FileUtil.isExistFile(filename)){
				FileUtil.deleteDir(new File(filename));
			}
			for(String head : this.head){
				headContent = headContent + "," + head;
			}

			try {
				FileUtils.write(new File(filename),headContent,"GBK",true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setFailFilename(filename);
		}


		try {
			FileUtils.write(new File(filename),content,"GBK",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.failCount++;
	}

    /**
     * 写报表文件内容
     *
     * @param reader
     * @param importResult
     * @param childFileName
     *               可以为空
     * @param error
     */
    public void writeLineReportFile(IRecordReader reader, String importResult, String childFileName, String error) {
        String filename = this.getReportFileName();
        if (StringUtil.isNullOrEmpty(filename)) {
            // 文件不存在时，先创建文件
            String path = FileUtil.getParentPath(this.context.getFilename());
            filename = FileUtil.contact(path, childFileName + "reportExcel.csv");
            String content = "导入结果,错误信息";
            if(FileUtil.isExistFile(filename)){
                FileUtil.deleteDir(new File(filename));
            }
            for(String head : this.head){
                content = content + "," + head;
            }
			try {
				FileUtils.write(new File(filename),content,"GBK",true);
			} catch (IOException e) {
				e.printStackTrace();
			}
            this.setReportFileName(filename);
        }

        String content = "\r\n" + importResult + "," + error;
        for(int i = 0; i < this.head.size(); i++){
            content = content + "," + (reader.getValue(i) == null ? "" : reader.getValue(i));
        }
		try {
			FileUtils.write(new File(filename),content,"GBK",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
