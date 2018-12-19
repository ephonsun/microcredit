package banger.framework.component.dataimport;

import java.util.List;

import banger.framework.component.constraint.IConstraint;
import banger.framework.component.dataimport.context.ColumnInfo;

public interface IImportContext {
		
	/**
	 * 得到文件名
	 * @return
	 */
	String getFilename();
	
	/**
	 * 设置文件名
	 * @param filename
	 */
	void setFilename(String filename);
	
	/**
	 * 得到导入列
	 * @return
	 */
	List<ColumnInfo> getColumns();
	
	/**
	 * 设置列
	 * @return
	 */
	void setColumns(List<ColumnInfo> columns);
	
	/**
	 * 得到约束
	 * @return
	 */
	List<IConstraint> getConstraints();
	
	/**
	 * 设置约束
	 * @param constraint
	 */
	void setConstraints(List<IConstraint> constraints);
	
	/**
	 * 添加约束
	 * @param constraint
	 */
	void addConstraint(IConstraint constraint);
	
	/**
	 * 得到读取最大行
	 * @param maxRow
	 */
	int getMaxRow();
	
	/**
	 * 
	 */
	void setMaxRow(int maxRow);
	
	/**
	 * 批数量
	 * @return
	 */
	int getBatch();
	
	/**
	 * 设置批量
	 * @param batch
	 */
	void setBatch(int batch);
	
}
