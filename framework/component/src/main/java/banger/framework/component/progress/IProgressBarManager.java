package banger.framework.component.progress;

import java.util.List;

public interface IProgressBarManager {
	
	boolean contains(String id);
	
	/**
	 * 添加一个新的进度条
	 * @param id
	 * @param group
	 * @return
	 */
	ProgressBar add(String id,String group);
	
	/**
	 * 得到进度条
	 * @param id
	 * @return
	 */
	ProgressBar getProgressBarById(String id);
	
	/**
	 * 通过组得到进度条
	 * @param group
	 * @return
	 */
	List<ProgressBar> getProgressBarByGroup(String group);
	
	/**
	 * 移除进度条
	 * @param id
	 */
	void remove(String id);
	
	/**
	 * 移除进度条组
	 * @param group
	 */
	void removeGroup(String group);
	
}
