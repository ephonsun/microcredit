package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IConfigFileProvider;
import banger.service.intf.ITableColumnService;
import banger.service.intf.ITableInfoService;
import banger.service.intf.ITemplateFieldService;
import banger.util.ExcelImportUitl;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IConfigFileDao;
import banger.service.intf.IConfigFileService;
import banger.domain.config.ModeConfigFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模板配置文件信息表，保存上传模板，合同相对应的路径业务访问类
 */
@Repository
public class ConfigFileService implements IConfigFileService,IConfigFileProvider {

	@Autowired
	private ITableInfoService tableInfoService;
	@Autowired
	private ITableColumnService tableColumnService;
	@Autowired
	private ITemplateFieldService templateFieldService;
	@Autowired
	private IConfigFileDao configFileDao;

	/**
	 * 新增模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertConfigFile(ModeConfigFile configFile,Integer loginUserId){
		this.configFileDao.insertConfigFile(configFile);
	}

	/**
	 *修改模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateConfigFile(ModeConfigFile configFile,Integer loginUserId){
		this.configFileDao.updateConfigFile(configFile);
	}

	/**
	 * 通过主键删除模板配置文件信息表，保存上传模板，合同相对应的路径
	 */
	public void deleteConfigFileById(Integer id){
		this.configFileDao.deleteConfigFileById(id);
	}

	/**
	 * 通过主键得到模板配置文件信息表，保存上传模板，合同相对应的路径
	 */
	public ModeConfigFile getConfigFileById(Integer id){
		return this.configFileDao.getConfigFileById(id);
	}

	/**
	 * 查询模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param condition 查询条件
	 * @return
	 */
	public List<ModeConfigFile> queryConfigFileList(Map<String,Object> condition){
		return this.configFileDao.queryConfigFileList(condition);
	}



	/**
	 * 分页查询模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ModeConfigFile> queryConfigFileList(Map<String,Object> condition,IPageSize page){
		return this.configFileDao.queryConfigFileList(condition,page);
	}

	@Override
	public String getRowHtmlForShow(String filePath) {
		return ExcelImportUitl.getRowHtmlForShow(filePath, tableInfoService, tableColumnService, templateFieldService);
	}

	@Override
	public String reloadVm(Integer id, String filePath) {
		return ExcelImportUitl.reloadVm(id, filePath, tableInfoService, tableColumnService, templateFieldService);
	}

}
