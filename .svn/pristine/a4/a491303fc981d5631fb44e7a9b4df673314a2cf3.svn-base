package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IConfigFileDao;
import banger.domain.config.ModeConfigFile;

/**
 * 模板配置文件信息表，保存上传模板，合同相对应的路径数据访问类
 */
@Repository
public class ConfigFileDao extends PageSizeDao<ModeConfigFile> implements IConfigFileDao {

	/**
	 * 新增模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 */
	public void insertConfigFile(ModeConfigFile configFile){
		configFile.setId(this.newId().intValue());
		this.execute("insertConfigFile",configFile);
	}

	/**
	 *修改模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param configFile 实体对像
	 */
	public void updateConfigFile(ModeConfigFile configFile){
		this.execute("updateConfigFile",configFile);
	}

	/**
	 * 通过主键删除模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param id 主键Id
	 */
	public void deleteConfigFileById(Integer id){
		this.execute("deleteConfigFileById",id);
	}

	/**
	 * 通过主键得到模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param id 主键Id
	 */
	public ModeConfigFile getConfigFileById(Integer id){
		return (ModeConfigFile)this.queryEntity("getConfigFileById",id);
	}

	/**
	 * 查询模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ModeConfigFile> queryConfigFileList(Map<String,Object> condition){
		return (List<ModeConfigFile>)this.queryEntities("queryConfigFileList", condition);
	}

	/**
	 * 分页查询模板配置文件信息表，保存上传模板，合同相对应的路径
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ModeConfigFile> queryConfigFileList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ModeConfigFile>)this.queryEntities("queryConfigFileList", page, condition);
	}

}
