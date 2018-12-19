package banger.moduleIntf;

import banger.domain.config.ModeConfigFile;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 模板配置文件信息表，保存上传模板，合同相对应的路径业务访问接口
 */
public interface IConfigFileProvider {

	/**
	 * 通过主键得到模板配置文件信息表，保存上传模板，合同相对应的路径
	 */
	ModeConfigFile getConfigFileById(Integer id);

	/**
	 * 查询模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param condition 查询条件
	 * @return
	 */
	List<ModeConfigFile> queryConfigFileList(Map<String, Object> condition);

    //void doExportFromRow(HttpServletRequest request, HttpServletResponse response, String fileName, String path);
}
