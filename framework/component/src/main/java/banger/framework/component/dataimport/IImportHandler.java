package banger.framework.component.dataimport;

/**
 * 导入进程控制类
 */
public interface IImportHandler {
	
	/**
	 * 设置上下文
	 * @param context
	 */
	void setContext(IImportContext context);
	
	/**
	 * 得到上下文
	 * @return
	 */
	IImportContext getContext();
	
	/**
	 * 开始执行导入
	 */
	void start();
	
	/**
	 * 导入结束
	 */
	void end();
	
	/**
	 * 读取记录
	 * @param reader
	 */
	void read(IRecordReader reader);
	
	/**
	 * 批量提交
	 */
	void batchCommit();
	
	/**
	 * 得到导入结果
	 * @return
	 */
	IImportResult getResult();
	
}
