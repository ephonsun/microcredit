package banger.controller;

import java.math.BigDecimal;
import java.util.*;

import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.app.AppScreenTrajectory;
import banger.domain.enumerate.MapConfigEnum;
import banger.domain.permission.ILoginInfo;
import banger.domain.permission.SysTeamGroup;
import banger.domain.permission.SysTeamMember_Query;
import banger.domain.task.LocationBean;
import banger.domain.track.MapConfig;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.IMapConfigService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.framework.pagesize.IPageList;
import banger.service.intf.ITrajectoryService;
import banger.domain.track.MapTrajectory;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 地图经纬度座标轨迹页面访问类
 */
@Controller
@RequestMapping("/trajectory")
public class TrajectoryController extends BaseMapController {
	private static final long serialVersionUID = 4475902345003377139L;

	private static double X_PI = 52.35987755982988D;
	static double ee = 0.006693421622965943D;
	static double a = 6378245.0D;
	@Autowired
	private ITrajectoryService trajectoryService;
	@Autowired
	private IPermissionModule permissionModule;
	@Autowired
	private IMapConfigService mapConfigService;


	/**
	 * 得到列表页面
	 * @return
	 */
	@RequestMapping("/getTrajectoryListPage")
	public String getTaskInfoListPage(){
		ILoginInfo loginInfo = getLoginInfo();
		String groupIds = loginInfo.getTeamGroupIdByRole(false);
		if (StringUtils.isNotBlank(groupIds)) {
			List<SysTeamGroup> teamGroups = permissionModule.queryGroupListByGroupIds(groupIds);
			setAttribute("teamGroups", teamGroups);
		} else {
			setAttribute("noTeam", "1");
		}
		return getBasePath("trajectory") + "trajectoryList";
	}

	/**
	 * 得到轨迹页面
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/getTrajectoryPage")
	public String getTaskInfoPage(@RequestParam(value = "id", defaultValue = "0")Integer id,@RequestParam(value = "flag", defaultValue = "0")Integer flag){
		setBaiduMapJs();		//设置ngnix 转发mapJs
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("createUser", id);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		condition.put("clientTime", c.getTime());
		c.add(Calendar.DAY_OF_MONTH, 1);
		condition.put("clientTimeEnd", c.getTime());
		JSONArray pointArray = getPointArray(condition);
		setAttribute("points", pointArray);
		setAttribute("id", id);
		setAttribute("flag", flag);
		List<MapConfig> configList = mapConfigService.queryConfigList(new HashMap<String,Object>());
		for (MapConfig mapConfig : configList) {
			setAttribute(MapConfigEnum.getNameByValue(mapConfig.getConfigType()), mapConfig.getConfigValue());
		}
		return getBasePath("trajectory") + "trajectoryPage";
	}

	@NoLoginInterceptor
	@RequestMapping("/getTrajectoryData")
	@ResponseBody
	public void getTrajectoryData(@RequestParam(value = "id", defaultValue = "0")Integer id,
									@RequestParam(value = "date", defaultValue = "")String date,
									@RequestParam(value = "startTime", defaultValue = "")String startTime,
									@RequestParam(value = "endTime", defaultValue = "")String endTime){
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("createUser", id);
		Date startDate;
		Date endDate;
		if (StringUtils.isBlank(date)) {
			date = DateUtil.format(new Date(), DateUtil.DEFAULT_DATE_FORMAT);
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && startTime.compareTo(endTime) > 0 ) {
			String temp = endTime;
			endTime = startTime;
			startTime = temp;
		}

		if (StringUtils.isNotBlank(startTime)) {
			String start = date + " " + startTime;
			startDate = DateUtil.parser(start, DateUtil.DEFAULT_DATEMINUTE_FORMAT);
		} else {
			startDate = DateUtil.parser(date, DateUtil.DEFAULT_DATE_FORMAT);
		}

		if (StringUtils.isNotBlank(endTime)) {
			String end = date + " " + endTime;
			endDate = DateUtil.parser(end, DateUtil.DEFAULT_DATEMINUTE_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.MINUTE, 1);
			endDate = c.getTime();
		} else {
			endDate = DateUtil.parser(date, DateUtil.DEFAULT_DATE_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DAY_OF_MONTH, 1);
			endDate = c.getTime();
		}
		condition.put("clientTime", startDate);
		condition.put("clientTimeEnd", endDate);
		//condition.put("createUser", "0");
		JSONArray pointArray = getPointArray(condition);
		if (pointArray.size() > 0 ) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", pointArray);
			renderJson(true, "", jsonObject);
		} else {
			renderText(false, "没有查到数据", "");
		}

	}

	private JSONArray getPointArray(Map<String, Object> condition) {
		Integer id = (Integer) condition.get("createUser");
		if (id == null || id.intValue() == 0) {
			condition.remove("createUser");
		}
		List<MapTrajectory> mapTrajectories = trajectoryService.queryTrajectoryList(condition);
		LocationBean locationBean = new LocationBean();
		for (MapTrajectory mapTrajectory:mapTrajectories){
			locationBean = tasnform(mapTrajectory.getTraLongitude().toString(),mapTrajectory.getTraLatitude().toString());
			mapTrajectory.setTraLongitude(locationBean.getLongitude());
			mapTrajectory.setTraLatitude(locationBean.getLatitude());
		}
		JSONArray pointArray = new JSONArray();
		//点过滤 竟可能控制在50个点以内
		int count = mapTrajectories.size();
		int jump = count/50;//跳点
		if (jump==0){
			jump=1;
		}
		int i = 0;//计算
		for(MapTrajectory mapTrajectory:mapTrajectories){
			if (i%jump==0) {
				JSONObject userJson = new JSONObject();
				userJson.put("lng", mapTrajectory.getTraLongitude());
				userJson.put("lat", mapTrajectory.getTraLatitude());
				userJson.put("time", DateUtil.format(mapTrajectory.getClientTime(),DateUtil.DEFAULT_DATETIME_FORMAT));
				pointArray.add(userJson);
			}else {
				//取最后一次
				if (i+1==count){
					JSONObject userJson = new JSONObject();
					userJson.put("lng", mapTrajectory.getTraLongitude());
					userJson.put("lat", mapTrajectory.getTraLatitude());
					userJson.put("time", DateUtil.format(mapTrajectory.getClientTime(),DateUtil.DEFAULT_DATETIME_FORMAT));
					pointArray.add(userJson);
				}
			}
			i++;
		}
//		for (int i=mapTrajectories.size() -1 ; i>=0; i--) {
//			MapTrajectory mapTrajectory = mapTrajectories.get(i);
//			JSONObject userJson = new JSONObject();
//			userJson.put("lng", mapTrajectory.getTraLongitude());
//			userJson.put("lat", mapTrajectory.getTraLatitude());
//			userJson.put("time", DateUtil.format(mapTrajectory.getClientTime(),DateUtil.DEFAULT_DATETIME_FORMAT));
//			pointArray.add(userJson);
//		}
		return pointArray;
	}


	@RequestMapping("/queryManage")
	@ResponseBody
	public void queryManage(@RequestParam(value = "teamId", defaultValue = "") String teamId,
							@RequestParam(value = "memberUser", defaultValue = "") String memberUser){

		if (StringUtils.isBlank(teamId))
			teamId = getLoginInfo().getTeamGroupIdByRole(false);
		if (StringUtils.isBlank(teamId)) teamId = "-1";
		IPageList<SysTeamMember_Query> members = permissionModule.queryMemberListByRoles(this.getPage(), teamId, memberUser);
		renderText(JsonUtil.toJson(members, "userId", "userName,teamName", "").toString());

	}

	@NoLoginInterceptor
	@RequestMapping("/trajectoryMapShow")
	public String trajectoryMapShow(){
		List<AppScreenTrajectory> mapTrajectories= this.trajectoryService.getMapInfo();
		JSONArray jsonArray = new JSONArray();
		if (mapTrajectories != null) {
			for(AppScreenTrajectory mq : mapTrajectories){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("userName",mq.getUserName());
				jsonObject.put("lat", String.valueOf(mq.getTraLatitude()));//纬度
				jsonObject.put("lng", String.valueOf(mq.getTraLongitude()));//经度
				jsonArray.add(jsonObject);
			}
		}
		List<MapConfig> configList = mapConfigService.queryConfigList(new HashMap<String,Object>());
		for (MapConfig mapConfig : configList) {
			setAttribute(MapConfigEnum.getNameByValue(mapConfig.getConfigType()), mapConfig.getConfigValue());
		}
		setAttribute("maps", jsonArray);
		return getBasePath("trajectory") + "trajectoryMapShow";
	}



	private LocationBean tasnform(@RequestParam(value = "lng", defaultValue = "")String lng,
						 @RequestParam(value = "lat", defaultValue = "")String lat) {
		double[] latlng = new double[]{0.0D, 0.0D};
			if(outOfChina(Double.parseDouble(lat), Double.parseDouble(lng))) {
				latlng[0] = Double.parseDouble(lat);
				latlng[1] = Double.parseDouble(lng);
				LocationBean locationBean = new LocationBean();
				return locationBean;
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
//				String latAndLng = locationBean.getLatitude() + "," + locationBean.getLongitude();
				return locationBean;
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
//	 * 得到地图经纬度座标轨迹列表页面
//	 * @return
//	 */
//	@RequestMapping("/getTrajectoryListPage")
//	public String getTrajectoryListPage(){
//		Map<String,Object> condition = new HashMap<String,Object>();
//		IPageList<MapTrajectory> trajectoryList = trajectoryService.queryTrajectoryList(condition,this.getPage());
//		setAttribute("trajectoryList",trajectoryList);
//		return SUCCESS;
//	}
//
//	/**
//	 * 查询地图经纬度座标轨迹列表数据
//	 * @return
//	 */
//	@RequestMapping("/queryTrajectoryListData")
//	public String queryTrajectoryListData(){
//		Map<String,Object> condition = new HashMap<String,Object>();
//		IPageList<MapTrajectory> trajectoryList = trajectoryService.queryTrajectoryList(condition,this.getPage());
//		renderText(JsonUtil.toJson(trajectoryList, "traId","traLongitude,traLatitude,deviceId,clientTime,createDate,createUser").toString());
//		return null;
//	}
//
//	/**
//	 * 得到地图经纬度座标轨迹新增页面
//	 * @return
//	 */
//	@RequestMapping("/getTrajectoryInsertPage")
//	public String getTrajectoryInsertPage(){
//		MapTrajectory trajectory = new MapTrajectory();
//		setAttribute("trajectory",trajectory);
//		return SUCCESS;
//	}
//
//	/**
//	 * 得到地图经纬度座标轨迹修改页面
//	 * @return
//	 */
//	@RequestMapping("/getTrajectoryUpdatePage")
//	public String getTrajectoryUpdatePage(){
//		String traId = getParameter("traId");
//		MapTrajectory trajectory = trajectoryService.getTrajectoryById(Integer.parseInt(traId));
//		setAttribute("trajectory",trajectory);
//		return SUCCESS;
//	}
//
//	/**
//	 * 得到地图经纬度座标轨迹查看页面
//	 * @return
//	 */
//	@RequestMapping("/getTrajectoryDetailPage")
//	public String getTrajectoryDetailPage(){
//		String traId = getParameter("traId");
//		MapTrajectory trajectory = trajectoryService.getTrajectoryById(Integer.parseInt(traId));
//		setAttribute("trajectory",trajectory);
//		return SUCCESS;
//	}
//
//	/**
//	 * 新增地图经纬度座标轨迹数据
//	 * @return
//	 */
//	@RequestMapping("/insertTrajectory")
//	public String insertTrajectory(@ModelAttribute MapTrajectory trajectory){
//		Integer loginUserId = getLoginInfo().getUserId();
//		trajectoryService.insertTrajectory(trajectory,loginUserId);
//		renderText(SUCCESS);
//		return null;
//	}
//
//	/**
//	 * 修改地图经纬度座标轨迹数据
//	 * @return
//	 */
//	@RequestMapping("/updateTrajectory")
//	public String updateTrajectory(@ModelAttribute MapTrajectory trajectory){
//		Integer loginUserId = getLoginInfo().getUserId();
//		trajectoryService.updateTrajectory(trajectory,loginUserId);
//		renderText(SUCCESS);
//		return null;
//	}
//
//	/**
//	 * 删除地图经纬度座标轨迹数据
//	 * @return
//	 */
//	@RequestMapping("/deleteTrajectory")
//	public String deleteTrajectory(){
//		String traId = getParameter("traId");
//		trajectoryService.deleteTrajectoryById(Integer.parseInt(traId));
//		renderText(SUCCESS);
//		return null;
//	}

}
