package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.permission.PmsDeptImport;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.progress.ProgressBar;

public interface IDeptImportService {
	
	/**
	 * 通过导入的Excel文件得到表头
	 * @param excelFilename
	 * @return
	 */
	List<String> getImportExcelHead(String excelFilename);
	
	/**
	 * 开妈导入机构数据
	 * @param userId 用户Id
	 * @param excelFilename 导入文件
	 * @param columns 导入列和字段的对应关系
	 * @return
	 */
	ProgressBar importExcel(String userId,String excelFilename,List<ColumnInfo> columns);
	
	/**
	 * 得到导入结果
	 * @param userId
	 * @return
	 */
	IImportResult getImportResultByUserId(String userId);

    /**
     * 获取所有的机构信息
     * @return
     */
    Map<String, PmsDeptImport> getAllExistDeptMap();
	
}
