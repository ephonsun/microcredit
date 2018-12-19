package banger.controller;

import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.customer.CustBasicInfoQueryForMap;
import banger.domain.enumerate.MapConfigEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.task.LocationBean;
import banger.domain.track.MapConfig;
import banger.domain.track.MapTagging;
import banger.moduleIntf.ICustomerModuleIntf;
import banger.moduleIntf.ILoanApplyProvider;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.IMapConfigService;
import banger.service.intf.ITaggingService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 地图经纬度座标页面访问类
 */
@Controller
@RequestMapping("/tagging")
public class TaggingController extends BaseMapController {
	private static final long serialVersionUID = 3826756554031677996L;

	private static double X_PI = 52.35987755982988D;
	static double ee = 0.006693421622965943D;
	static double a = 6378245.0D;
	@Autowired
	private ITaggingService taggingService;
	@Autowired
	private ILoanApplyProvider loanApplyProvider;
	@Autowired
	private ICustomerModuleIntf customerModuleIntf;
	@Autowired
	private IMapConfigService mapConfigService;
	@Autowired
	private IPermissionModule permissionModule;


	/**
	 * 根据经纬度查看地图上的点
	 * @param lng
	 * @param lat
	 * @return
	 */
	@RequestMapping("/taggingLngLatShow")
	public String taggingLngLatShow(@RequestParam(value = "lng", defaultValue = "")String lng,
							  @RequestParam(value = "lat", defaultValue = "")String lat){
		setBaiduMapJs();		//设置ngnix 转发mapJs
		setAttribute("locationj", lng);
		setAttribute("locationw", lat);
		List<MapConfig> configList = mapConfigService.queryConfigList(new HashMap<String,Object>());
		for (MapConfig mapConfig : configList) {
			setAttribute(MapConfigEnum.getNameByValue(mapConfig.getConfigType()), mapConfig.getConfigValue());
		}
		return getBasePath("tagging") + "taggingLngLatShow";
//		return "/track/baidu/vm/taggingLngLatShow";
//		return "/track/baidu/vm/taggingLngLatShow";
	}
	/**
	 * 得到标记地图页面
	 * @param isShow [0:web 标注定位，1：web查看定位，00：app标注，01：app查看]
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/getTaggingMap")
	public String getTrackMap(@RequestParam(value = "customerId", defaultValue = "0")Integer customerId,
							  @RequestParam(value = "loanId", defaultValue = "0")Integer loanId,
							  @RequestParam(value = "columnName", defaultValue = "")String columnName,
							  @RequestParam(value = "tableName", defaultValue = "")String tableName,
							  @RequestParam(value = "lbizId", defaultValue = "")String lbizId,
							  @RequestParam(value = "isShow", defaultValue = "")String isShow){
		setBaiduMapJs();		//设置ngnix 转发mapJs
		customerId = customerId == 0 ? null : customerId;
		loanId = loanId == 0 ? null : loanId;
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("customerId", customerId);
		condition.put("loanId", loanId);
		condition.put("columnName", columnName);
		condition.put("tableName", tableName);
		if (StringUtils.isNotBlank(lbizId))
			condition.put("lbizId", lbizId);
		List<MapTagging> mapTaggingList = taggingService.queryTaggingList(condition);
		//设置默认的经纬度缩放比
		List<MapConfig> configList = mapConfigService.queryConfigList(new HashMap<String,Object>());
		for (MapConfig mapConfig : configList) {
			setAttribute(MapConfigEnum.getNameByValue(mapConfig.getConfigType()), mapConfig.getConfigValue());
		}
		if (CollectionUtils.isNotEmpty(mapTaggingList)) {
			MapTagging mapTagging = mapTaggingList.get(0);
			setAttribute(MapConfigEnum.LNG.name, mapTagging.getTagLongitude().toString());
			setAttribute(MapConfigEnum.LAT.name, mapTagging.getTagLatitude().toString());
			setAttribute("tagging", mapTagging);
		}
		setAttribute("customerId", customerId);
		setAttribute("loanId", loanId);
		setAttribute("columnName", columnName);
		setAttribute("tableName", tableName);
		setAttribute("lbizId", lbizId);

		if ("1".equals(isShow)) {
			return getBasePath("tagging") + "taggingMapShow";
		} else if ("00".equals(isShow)) {
			return getBasePath("tagging") + "taggingMapApp";
		} else if ("01".equals(isShow)) {
			return getBasePath("tagging") + "taggingMapAppShow";
		}
		return getBasePath("tagging") + "taggingMap";
	}

	/**
	 * 经纬度保存，如果是贷款保存，同步客户经纬度
	 * @param mapTagging
	 */
	@RequestMapping("/saveTaggingMap")
	@ResponseBody
	public void saveTrackMap(MapTagging mapTagging){
		Integer loginUserId = getLoginInfo().getUserId();
		if (mapTagging.getTagId() ==  null) {
			taggingService.insertTagging(mapTagging, loginUserId);
		} else {
			taggingService.updateTagging(mapTagging, loginUserId);
		}
		if (mapTagging.getLoanId() != null) {
			LoanApplyInfo applyInfo = loanApplyProvider.getApplyInfoById(mapTagging.getLoanId());
			if (applyInfo != null) {
				taggingService.syncTaggingLoanToCustomer(mapTagging, applyInfo.getLoanCustomerId(), loginUserId);
			}
		}
		renderText(true,"", "");
	}



	/**
	 * 地图搜索客户
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/getCustomerMapSearch")
	public String customerMapSearch(@RequestParam(value = "forApp", defaultValue = "false")boolean forApp, @RequestParam(value = "userId", defaultValue = "")String userId){
		setBaiduMapJs();		//设置ngnix 转发mapJs
		List<MapConfig> configList = mapConfigService.queryConfigList(new HashMap<String,Object>());
		for (MapConfig mapConfig : configList) {
			setAttribute(MapConfigEnum.getNameByValue(mapConfig.getConfigType()), mapConfig.getConfigValue());
		}
		if (StringUtils.isBlank(userId)) {
			userId = getLoginInfo().getUserId().toString();
		}
		setAttribute("userId", userId);
		setAttribute("forApp", forApp);
		if (forApp)
			return getBasePath("tagging") + "customerMapSearchApp";
		else
			return getBasePath("tagging") + "customerMapSearch";
	}


	/**
	 * 地图搜索兼容了app端，非登录请求，不能使用session信息
	 * @param customer
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/getSearchResult")
	public String getSearchResult(@RequestParam(value = "customer", defaultValue = "")String customer, @RequestParam(value = "userId", defaultValue = "")String userId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customer);
		if (StringUtils.isBlank(userId)) {
			if (getLoginInfo() != null)
				userId = getLoginInfo().getUserId().toString();
		}
		params.put("userId", userId);
		JSONArray pointArray = new JSONArray();
		List<CustBasicInfoQueryForMap> custBasicInfoQueryForMaps;
		if (StringUtils.isNotBlank(userId)) {
			custBasicInfoQueryForMaps = customerModuleIntf.queryBasicInfoListForMap(params);
			//点过滤 竟可能控制在50个点以内
			int count = custBasicInfoQueryForMaps.size();
			int jump = count/50;//跳点
			if (jump==0){
				jump=1;
			}
			int i = 0;//计算
			for (CustBasicInfoQueryForMap c : custBasicInfoQueryForMaps) {
				//整除 取一次点
				if (i%jump==0) {
					JSONObject userJson = new JSONObject();
					userJson.put("lng", c.getTagLongitude());
					userJson.put("lat", c.getTagLatitude());
					userJson.put("customerName", c.getCustomerName());
					pointArray.add(userJson);
				}else {
					//取最后一次
					if (i+1==count){
						JSONObject userJson = new JSONObject();
						userJson.put("lng", c.getTagLongitude());
						userJson.put("lat", c.getTagLatitude());
						userJson.put("customerName", c.getCustomerName());
						pointArray.add(userJson);
					}
				}
				i++;
			}
		} else {
			custBasicInfoQueryForMaps = new ArrayList<CustBasicInfoQueryForMap>();
		}

		setAttribute("points", pointArray);
		setAttribute("customers", custBasicInfoQueryForMaps);
		return getBasePath("tagging") + "searchResult";
	}

	@NoLoginInterceptor
	@RequestMapping("/getAddressByLngLat")
	@ResponseBody
	public void getAddressByLngLat(@RequestParam(value = "lng", defaultValue = "")String lng,
								   @RequestParam(value = "lat", defaultValue = "")String lat){
		String address = taggingService.getAddressByLngLat(lng, lat);
		renderText(address);
	}

	@NoLoginInterceptor
	@RequestMapping("/tasnform")
	public void tasnform(@RequestParam(value = "lng", defaultValue = "")String lng,
							@RequestParam(value = "lat", defaultValue = "")String lat) {
				double[] latlng = new double[]{0.0D, 0.0D};
				if(outOfChina(Double.parseDouble(lat), Double.parseDouble(lng))) {
					latlng[0] = Double.parseDouble(lat);
					latlng[1] = Double.parseDouble(lng);
//					return new LocationBean(latlng[0], latlng[1]);
				} else {
					double dLat = toLat(Double.parseDouble(lng) - 105.0D, Double.parseDouble(lat) - 35.0D);
					double dLng = toLon(Double.parseDouble(lng) - 105.0D, Double.parseDouble(lat) - 35.0D);
					double radLat = Double.parseDouble(lat) / 180.0D * 3.141592653589793D;
					double magic = Math.sin(radLat);
					magic = 1.0D - ee * magic * magic;
					double sqrtMagic = Math.sqrt(magic);
					dLat = dLat * 180.0D / (a * (1.0D - ee) / (magic * sqrtMagic) * 3.141592653589793D);
					dLng = dLng * 180.0D / (a / sqrtMagic * Math.cos(radLat) * 3.141592653589793D);
					latlng[0] = Double.parseDouble(lat) + dLat;
					latlng[1] = Double.parseDouble(lng) + dLng;
					LocationBean locationBean =  new LocationBean(fixedDecimal(latlng[0], 6), fixedDecimal(latlng[1], 6));
					String latAndLng = locationBean.getLatitude() + "," + locationBean.getLongitude();
					renderText(latAndLng);
				}
	}
	private static double fixedDecimal(double value, int num) {
		return (new BigDecimal(value)).setScale(num, 4).doubleValue();
	}

	private static boolean outOfChina(double lat, double lng) {
		return lng >= 72.004D && lng <= 137.8347D?lat < 0.8293D || lat > 55.8271D:true;
	}

	private static double toLat(double x, double y) {
		double ret = -100.0D + 2.0D * x + 3.0D * y + 0.2D * y * y + 0.1D * x * y + 0.2D * Math.sqrt(Math.abs(x));
		ret += (20.0D * Math.sin(6.0D * x * 3.141592653589793D) + 20.0D * Math.sin(2.0D * x * 3.141592653589793D)) * 2.0D / 3.0D;
		ret += (20.0D * Math.sin(y * 3.141592653589793D) + 40.0D * Math.sin(y / 3.0D * 3.141592653589793D)) * 2.0D / 3.0D;
		ret += (160.0D * Math.sin(y / 12.0D * 3.141592653589793D) + 320.0D * Math.sin(y * 3.141592653589793D / 30.0D)) * 2.0D / 3.0D;
		return ret;
	}

	private static double toLon(double x, double y) {
		double ret = 300.0D + x + 2.0D * y + 0.1D * x * x + 0.1D * x * y + 0.1D * Math.sqrt(Math.abs(x));
		ret += (20.0D * Math.sin(6.0D * x * 3.141592653589793D) + 20.0D * Math.sin(2.0D * x * 3.141592653589793D)) * 2.0D / 3.0D;
		ret += (20.0D * Math.sin(x * 3.141592653589793D) + 40.0D * Math.sin(x / 3.0D * 3.141592653589793D)) * 2.0D / 3.0D;
		ret += (150.0D * Math.sin(x / 12.0D * 3.141592653589793D) + 300.0D * Math.sin(x / 30.0D * 3.141592653589793D)) * 2.0D / 3.0D;
		return ret;
	}
//	/**
//	 * 得到地图经纬度座标列表页面
//	 * @return
//	 */
//	@RequestMapping("/getTaggingListPage")
//	public String getTaggingListPage(){
//		Map<String,Object> condition = new HashMap<String,Object>();
//		IPageList<MapTagging> taggingList = taggingService.queryTaggingList(condition,this.getPage());
//		setAttribute("taggingList",taggingList);
//		return SUCCESS;
//	}
//
//	/**
//	 * 查询地图经纬度座标列表数据
//	 * @return
//	 */
//	@RequestMapping("/queryTaggingListData")
//	public String queryTaggingListData(){
//		Map<String,Object> condition = new HashMap<String,Object>();
//		IPageList<MapTagging> taggingList = taggingService.queryTaggingList(condition,this.getPage());
//		renderText(JsonUtil.toJson(taggingList, "tagId","tagLongitude,tagLatitude,customerId,loanId,tableName,columnName,createDate,updateDate,createUser,updateUser").toString());
//		return null;
//	}
//
//	/**
//	 * 得到地图经纬度座标新增页面
//	 * @return
//	 */
//	@RequestMapping("/getTaggingInsertPage")
//	public String getTaggingInsertPage(){
//		MapTagging tagging = new MapTagging();
//		setAttribute("tagging",tagging);
//		return SUCCESS;
//	}
//
//	/**
//	 * 得到地图经纬度座标修改页面
//	 * @return
//	 */
//	@RequestMapping("/getTaggingUpdatePage")
//	public String getTaggingUpdatePage(){
//		String tagId = getParameter("tagId");
//		MapTagging tagging = taggingService.getTaggingById(Integer.parseInt(tagId));
//		setAttribute("tagging",tagging);
//		return SUCCESS;
//	}
//
//	/**
//	 * 得到地图经纬度座标查看页面
//	 * @return
//	 */
//	@RequestMapping("/getTaggingDetailPage")
//	public String getTaggingDetailPage(){
//		String tagId = getParameter("tagId");
//		MapTagging tagging = taggingService.getTaggingById(Integer.parseInt(tagId));
//		setAttribute("tagging",tagging);
//		return SUCCESS;
//	}
//
//	/**
//	 * 新增地图经纬度座标数据
//	 * @return
//	 */
//	@RequestMapping("/insertTagging")
//	public String insertTagging(@ModelAttribute MapTagging tagging){
//		Integer loginUserId = getLoginInfo().getUserId();
//		taggingService.insertTagging(tagging,loginUserId);
//		renderText(SUCCESS);
//		return null;
//	}
//
//	/**
//	 * 修改地图经纬度座标数据
//	 * @return
//	 */
//	@RequestMapping("/updateTagging")
//	public String updateTagging(@ModelAttribute MapTagging tagging){
//		Integer loginUserId = getLoginInfo().getUserId();
//		taggingService.updateTagging(tagging,loginUserId);
//		renderText(SUCCESS);
//		return null;
//	}
//
//	/**
//	 * 删除地图经纬度座标数据
//	 * @return
//	 */
//	@RequestMapping("/deleteTagging")
//	public String deleteTagging(){
//		String tagId = getParameter("tagId");
//		taggingService.deleteTaggingById(Integer.parseInt(tagId));
//		renderText(SUCCESS);
//		return null;
//	}

}
