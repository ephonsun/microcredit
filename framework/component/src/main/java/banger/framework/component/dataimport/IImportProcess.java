package banger.framework.component.dataimport;

public interface IImportProcess {

	/**
	 * 添加导出进程
	 * @param process
	 */
	void process(IImportHandler handler);
}
