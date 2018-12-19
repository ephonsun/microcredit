package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeConfigFile;

/**
 * 模板配置文件信息表，保存上传模板，合同相对应的路径数据访问接口
 */
public interface IConfigFileDao {

	/**
	 * 新增模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 */
	void insertConfigFile(ModeConfigFile configFile);

	/**
	 *修改模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 */
	void updateConfigFile(ModeConfigFile configFile);

	/**
	 * 通过主键删除模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param id 主键Id
	 */
	void deleteConfigFileById(Integer id);

	/**
	 * 通过主键得到模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param id 主键Id
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

}
