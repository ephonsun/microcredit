package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.track.MapTagging;

/**
 * 地图经纬度座标业务访问接口
 */
public interface ITaggingService {

	/**
	 * 新增地图经纬度座标
	 * @param tagging 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTagging(MapTagging tagging, Integer loginUserId);

	/**
	 *修改地图经纬度座标
	 * @param tagging 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTagging(MapTagging tagging, Integer loginUserId);

	/**
	 * 通过主键删除地图经纬度座标
	 * @param TAG_ID 主键Id
	 */
	void deleteTaggingById(Integer tagId);

	/**
	 * 通过主键得到地图经纬度座标
	 * @param TAG_ID 主键Id
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

	/**
	 * 同步经纬度，根据贷款的经纬度，同步客户的经纬度
	 */
	void syncTaggingLoanToCustomer(MapTagging mapTagging, Integer customerId, Integer loginUserId);


	/**
	 * 根据经纬度查询中文
	 * @param lng
	 * @param lat
	 * @return
	 */
	String getAddressByLngLat(String lng, String lat);
}
