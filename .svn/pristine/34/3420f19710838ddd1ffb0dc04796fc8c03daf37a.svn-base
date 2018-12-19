package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeConfigFile;

/**
 * 模板配置文件信息表，保存上传模板，合同相对应的路径业务访问接口
 */
public interface IConfigFileService {

	/**
	 * 新增模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertConfigFile(ModeConfigFile configFile, Integer loginUserId);

	/**
	 *修改模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateConfigFile(ModeConfigFile configFile, Integer loginUserId);

	/**
	 * 通过主键删除模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param ID 主键Id
	 */
	void deleteConfigFileById(Integer id);

	/**
	 * 通过主键得到模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param ID 主键Id
	 */
	ModeConfigFile getConfigFileById(Integer id);

	/**
	 * 查询模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param condition 查询条件
	 * @return
	 */
	List<ModeConfigFile> queryConfigFileList(Map<String, Object> condition);

	/**
	 * 分页查询模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ModeConfigFile> queryConfigFileList(Map<String, Object> condition, IPageSize page);


	String getRowHtmlForShow(String filePath);

	String reloadVm(Integer id, String filePath);
}
