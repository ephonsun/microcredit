package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.track.MapTagging;

/**
 * 地图经纬度座标数据访问接口
 */
public interface ITaggingDao {

	/**
	 * 新增地图经纬度座标
	 * @param tagging 实体对像
	 */
	void insertTagging(MapTagging tagging);

	/**
	 *修改地图经纬度座标
	 * @param tagging 实体对像
	 */
	void updateTagging(MapTagging tagging);

	/**
	 * 通过主键删除地图经纬度座标
	 * @param tagId 主键Id
	 */
	void deleteTaggingById(Integer tagId);

	/**
	 * 通过主键得到地图经纬度座标
	 * @param tagId 主键Id
	 */
	MapTagging getTaggingById(Integer tagId);

	/**
	 * 查询地图经纬度座标
	 * @param condition 查询条件
	 * @return
	 */
	List<MapTagging> queryTaggingList(Map<String, Object> condition);

	/**
	 * 分页查询地图经纬度座标
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<MapTagging> queryTaggingList(Map<String, Object> condition, IPageSize page);

}
