package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITaggingDao;
import banger.domain.track.MapTagging;

/**
 * 地图经纬度座标数据访问类
 */
@Repository
public class TaggingDao extends PageSizeDao<MapTagging> implements ITaggingDao {

	/**
	 * 新增地图经纬度座标
	 * @param tagging 实体对像
	 */
	public void insertTagging(MapTagging tagging){
		tagging.setTagId(this.newId().intValue());
		this.execute("insertTagging",tagging);
	}

	/**
	 *修改地图经纬度座标
	 * @param tagging 实体对像
	 */
	public void updateTagging(MapTagging tagging){
		this.execute("updateTagging",tagging);
	}

	/**
	 * 通过主键删除地图经纬度座标
	 * @param tagId 主键Id
	 */
	public void deleteTaggingById(Integer tagId){
		this.execute("deleteTaggingById",tagId);
	}

	/**
	 * 通过主键得到地图经纬度座标
	 * @param tagId 主键Id
	 */
	public MapTagging getTaggingById(Integer tagId){
		return (MapTagging)this.queryEntity("getTaggingById",tagId);
	}

	/**
	 * 查询地图经纬度座标
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MapTagging> queryTaggingList(Map<String,Object> condition){
		return (List<MapTagging>)this.queryEntities("queryTaggingList", condition);
	}

	/**
	 * 分页查询地图经纬度座标
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<MapTagging> queryTaggingList(Map<String,Object> condition,IPageSize page){
		return (IPageList<MapTagging>)this.queryEntities("queryTaggingList", page, condition);
	}

}
