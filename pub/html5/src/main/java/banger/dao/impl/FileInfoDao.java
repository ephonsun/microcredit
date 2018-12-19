package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IFileInfoDao;
import banger.domain.html5.IntoFileInfo;

/**
 * 进件附件信息数据访问类
 */
@Repository
public class FileInfoDao extends PageSizeDao<IntoFileInfo> implements IFileInfoDao {

	/**
	 * 新增进件附件信息
	 * @param fileInfo 实体对像
	 */
	public void insertFileInfo(IntoFileInfo fileInfo){
		fileInfo.setId(this.newId().intValue());
		this.execute("insertFileInfo",fileInfo);
	}

	/**
	 *修改进件附件信息
	 * @param fileInfo 实体对像
	 */
	public void updateFileInfo(IntoFileInfo fileInfo){
		this.execute("updateFileInfo",fileInfo);
	}

	/**
	 * 通过主键删除进件附件信息
	 * @param id 主键Id
	 */
	public void deleteFileInfoById(Integer id){
		this.execute("deleteFileInfoById",id);
	}

	/**
	 * 通过主键得到进件附件信息
	 * @param id 主键Id
	 */
	public IntoFileInfo getFileInfoById(Integer id){
		return (IntoFileInfo)this.queryEntity("getFileInfoById",id);
	}

	/**
	 * 查询进件附件信息
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoFileInfo> queryFileInfoList(Map<String,Object> condition){
		return (List<IntoFileInfo>)this.queryEntities("queryFileInfoList", condition);
	}

	/**
	 * 分页查询进件附件信息
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoFileInfo> queryFileInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoFileInfo>)this.queryEntities("queryFileInfoList", page, condition);
	}
	/**
	 * 新增进件附件信息 返回id
	 * @param intoFileInfo 实体对像
	 * */
	@Override
	public Integer insertFileInfoReturnId(IntoFileInfo intoFileInfo) {
		Integer id = this.newId().intValue();
		intoFileInfo.setId(id);
		this.execute("insertFileInfo",intoFileInfo);
		return id;
	}

}
