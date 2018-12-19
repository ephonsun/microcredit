package banger.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.enumerate.TableSyncEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.framework.util.HttpRequestClient;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.IMapTaggingProvider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITaggingDao;
import banger.service.intf.ITaggingService;
import banger.domain.track.MapTagging;

/**
 * 地图经纬度座标业务访问类
 */
@Repository
public class TaggingService implements ITaggingService,IMapTaggingProvider {


	@Value("${ruitu.service.url}")
	private String ruituServiceUrl;
	@Autowired
	private ITaggingDao taggingDao;
	@Autowired
	private IConfigModule configModule;

	/**
	 * 新增地图经纬度座标
	 * @param tagging 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTagging(MapTagging tagging,Integer loginUserId){
		tagging.setCreateUser(loginUserId);
		tagging.setCreateDate(DateUtil.getCurrentDate());
		tagging.setUpdateUser(loginUserId);
		tagging.setUpdateDate(DateUtil.getCurrentDate());
		this.taggingDao.insertTagging(tagging);
	}

	/**
	 *修改地图经纬度座标
	 * @param tagging 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTagging(MapTagging tagging,Integer loginUserId){
		tagging.setUpdateUser(loginUserId);
		tagging.setUpdateDate(DateUtil.getCurrentDate());
		this.taggingDao.updateTagging(tagging);
	}


	@Override
	public void syncTaggingLoanToCustomer(MapTagging mapTagging, Integer customerId, Integer loginUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		TableSyncEnum tableSyncEnum = TableSyncEnum.valueOfTarget(mapTagging.getTableName());
		if (tableSyncEnum != null) {
			map.put("tableName", mapTagging.getTableName());
			map.put("columnName", mapTagging.getColumnName());
			map.put("customerId", customerId);
			List<MapTagging> mapTaggings = queryTaggingList(map);
			Double lat = mapTagging.getTagLatitude();
			Double lng = mapTagging.getTagLongitude();

			if (CollectionUtils.isNotEmpty(mapTaggings)) {
				updateMapping(mapTaggings.get(0), lat, lng, loginUserId);
			} else {
				//客户表的自定义字段表id和客户id一样
				insertMapping(mapTagging.getTableName(), mapTagging.getColumnName(), 0,customerId, lat, lng, loginUserId, customerId);
			}
		}
	}

	@Override
	public String getAddressByLngLat(String lng, String lat) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("lng", lng);
			params.put("lat", lat);
//			http://localhost:8080//BankApi/locationapi?lng=113.46278&lat=22.67948
			HttpRequestClient.Result result = HttpRequestClient.postMultipartData(ruituServiceUrl + "/BankApi/locationapi", params);
			String resultStr = result.getResponseBody();
			if (StringUtils.isNotBlank(resultStr)){
				JSONObject jsonObject = JSONObject.fromObject(resultStr);
				JSONObject data = (JSONObject) jsonObject.get("data");
				String address = "无法获取定位!";
				if(null!=data){
					address = (String)data.get("distName") + data.get("roadName")  + data.get("poiName");
				}

				return address;
			} else {
				return "";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "无法获取定位！";
		}
	}

	private void insertMapping(String tableName, String columnName,Integer loanId, Integer customerId, Double lat, Double lng, Integer loginUserId, Integer lbizId){
		MapTagging tagging = new MapTagging();
		tagging.setTableName(tableName);
		tagging.setColumnName(columnName.toString());
		tagging.setLoanId(loanId);
		tagging.setCustomerId(customerId);
		tagging.setTagLongitude(lng);
		tagging.setTagLatitude(lat);
		//客户标记地图，没有列表形式的地址
//		tagging.setLbizId(lbizId);
		insertTagging(tagging, loginUserId);
	}
	private void updateMapping(MapTagging tagging, Double lat, Double lng, Integer loginUserId) {
		if (lat != tagging.getTagLatitude() || lng != tagging.getTagLongitude()) {
			tagging.setTagLongitude(lng);
			tagging.setTagLatitude(lat);
			updateTagging(tagging, loginUserId);
		}
	}

	@Override
	public void syncTaggingCustomerToLoan(Integer loanId, Integer customerId, Integer loginUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", customerId);
		if (customerId != null && customerId.intValue() != 0) {
			List<MapTagging> mapTaggings = taggingDao.queryTaggingList(map);
			if (mapTaggings != null) {
				String tableName, columnName;
				double lat, lng;
				for (MapTagging mapTagging : mapTaggings) {
					tableName = mapTagging.getTableName();
					TableSyncEnum tableSyncEnum = TableSyncEnum.valueOfTarget(tableName);
					if (tableSyncEnum != null) {
						columnName = mapTagging.getColumnName();
						map = new HashMap<String, Object>();
						map.put("loanId", loanId);
						map.put("tableName", tableSyncEnum.targetTableName);
						map.put("columnName", columnName);
						List<MapTagging> customerTaggings = taggingDao.queryTaggingList(map);
						lat = mapTagging.getTagLatitude();
						lng = mapTagging.getTagLongitude();

						if (CollectionUtils.isNotEmpty(customerTaggings)) {
							updateMapping(customerTaggings.get(0), lat, lng, loginUserId);
						} else {
//							TableSyncEnum
//							Integer lbizId = configMod
							insertMapping(tableSyncEnum.targetTableName, columnName, loanId, 0, lat, lng, loginUserId ,mapTagging.getLbizId());
						}
					}
				}
			}
		}
	}

	/**
	 * 通过主键删除地图经纬度座标
	 * @param tagId 主键Id
	 */
	public void deleteTaggingById(Integer tagId){
		this.taggingDao.deleteTaggingById(tagId);
	}

	/**
	 * 通过主键得到地图经纬度座标
	 * @param tagId 主键Id
	 */
	public MapTagging getTaggingById(Integer tagId){
		return this.taggingDao.getTaggingById(tagId);
	}

	/**
	 * 查询地图经纬度座标
	 * @param condition 查询条件
	 * @return
	 */
	public List<MapTagging> queryTaggingList(Map<String,Object> condition){
		return this.taggingDao.queryTaggingList(condition);
	}

	/**
	 * 分页查询地图经纬度座标
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<MapTagging> queryTaggingList(Map<String,Object> condition,IPageSize page){
		return this.taggingDao.queryTaggingList(condition,page);
	}


	public JSONArray getAddressJson(String loanId, String customerId){
		JSONArray jsonArray = new JSONArray();
		//如果是新建返回空的数组，不要返回null，js会报错
		if ((StringUtils.isBlank(loanId) || !StringUtils.isNumeric(loanId)) &&  (StringUtils.isBlank(customerId) || !StringUtils.isNumeric(customerId)))
			return jsonArray;
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(loanId))
			params.put("loanId", loanId);
		if (StringUtils.isNotBlank(customerId))
			params.put("customerId", customerId);
		List<MapTagging> mapTaggings = queryTaggingList(params);
		for (MapTagging mapTagging : mapTaggings) {
			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("lng", mapTagging.getTagLongitude());
//			jsonObject.put("lat", mapTagging.getTagLatitude());
			jsonObject.put("tableName", mapTagging.getTableName());
			jsonObject.put("columnName", mapTagging.getColumnName());
			jsonObject.put("lbizId", mapTagging.getLbizId());
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}



}
