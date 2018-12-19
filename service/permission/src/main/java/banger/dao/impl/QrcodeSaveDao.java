package banger.dao.impl;

import banger.dao.intf.IQrcodeSaveDao;
import banger.domain.permission.IntoQrcodeSave;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件二维码存储表数据访问类
 */
@Repository
public class QrcodeSaveDao extends PageSizeDao<IntoQrcodeSave> implements IQrcodeSaveDao {
	/**
	 * @Author panl
	 * @Date 2017/12/12 0:02
	 * 通过用户账号查找
	 */
	public IntoQrcodeSave queryQrcode(String userAccount) {
		return (IntoQrcodeSave)this.queryEntity("queryQrcode",userAccount);
	}

	/**
	 * 新增进件二维码存储表
	 * @param qrcodeSave 实体对像
	 */
	public void insertQrcodeSave(IntoQrcodeSave qrcodeSave){
		qrcodeSave.setId(this.newId().intValue());
		this.execute("insertQrcodeSave",qrcodeSave);
	}

	/**
	 *修改进件二维码存储表
	 * @param qrcodeSave 实体对像
	 */
	public void updateQrcodeSave(IntoQrcodeSave qrcodeSave){
		this.execute("updateQrcodeSave",qrcodeSave);
	}

	/**
	 * 通过主键删除进件二维码存储表
	 * @param id 主键Id
	 */
	public void deleteQrcodeSave(){
		this.execute("deleteQrcodeSave");
	}

	/**
	 * 通过主键得到进件二维码存储表
	 * @param id 主键Id
	 */
	public IntoQrcodeSave getQrcodeSaveById(Integer id){
		return (IntoQrcodeSave)this.queryEntity("getQrcodeSaveById",id);
	}

	/**
	 * 查询进件二维码存储表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoQrcodeSave> queryQrcodeSaveList(Map<String,Object> condition){
		return (List<IntoQrcodeSave>)this.queryEntities("queryQrcodeSaveList", condition);
	}

	/**
	 * 分页查询进件二维码存储表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoQrcodeSave> queryQrcodeSaveList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoQrcodeSave>)this.queryEntities("queryQrcodeSaveList", page, condition);
	}

}
