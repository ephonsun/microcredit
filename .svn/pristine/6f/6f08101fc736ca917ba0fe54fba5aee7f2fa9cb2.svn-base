package banger.service.intf;

import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUserImport;
import banger.domain.permission.PmsUser_Query;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.progress.ProgressBar;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-6-10
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public interface IUserImportService {
    /**
     * 通过导入的Excel文件得到表头
     * @param excelFilename
     * @return
     */
    List<String> getImportExcelHead(String excelFilename);

    ProgressBar importExcel(String userId,String userDeptId, String excelFilename, List<ColumnInfo> columns);

    IImportResult getImportResultByUserId(String userId);

    /**
     *自动导入用户信息
     * @return TODO
     */
    String autoImportUserInfo() throws Exception;
}
