package banger.controller;

import banger.action.AppBaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.annotation.NoTokenAnnotation;
import banger.domain.customer.CustBasicInfoQueryForMap;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.track.MapTagging;
import banger.domain.track.MapTrajectory;
import banger.framework.util.DateUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.ErrorUtil;
import banger.util.HttpParseUtil;
import banger.util.RequestMapUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zhusw on 2017/8/2.
 */
@Controller
@RequestMapping("/api")
public class AppMapController extends AppBaseController {

    @Resource
    private IMapTaggingProvider mapTaggingProvider;
    @Resource
    private ICustomerModuleIntf customerModuleIntf;
    @Resource
    private IPermissionModule permissionModule;
    @Autowired
    private ILoanModule loanModule;

    @Resource
    private IMapTrajectoryProvider mapTrajectoryProvider;

    /**
     * http://127.0.0.1:8080/loan/api/v1/getMapTagging.html?id=1&type=customer&tableName=LBIZ_PERSONAL_INFO&columnName=HOME_ADDRESS
     * 查询地址类自定义字段的经纬度
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/getMapTagging")
    @NoTokenAnnotation
    public ResponseEntity<String> getMapTagging(HttpServletRequest request, HttpServletResponse response){
        String containsKeys = RequestMapUtil.paramsIsNotEmpty(request,"id,type,tableName,columnName");
        if(containsKeys == null){
            String id = this.getParameter("id");
            String lbizId = this.getParameter("lbizId");
            String type = this.getParameter("type");
            String tableName = this.getParameter("tableName");
            String columnName = this.getParameter("columnName");
            Map<String,Object> codition = new HashMap<String, Object>();
            codition.put("tableName",tableName);
            codition.put("columnName",columnName);
            if("loan".equalsIgnoreCase(type)){
                codition.put("loanId",id);
            }else{
                codition.put("customerId",id);
            }
            if(StringUtil.isNotEmpty(lbizId)){
                codition.put("lbizId",Integer.parseInt(lbizId));
            }
            try {
                List<MapTagging> list = mapTaggingProvider.queryTaggingList(codition);
                if (list != null && list.size() > 0) {
                    MapTagging mapTagging = list.get(0);
                    JSONObject jo = new JSONObject();
                    jo.put("tagId",mapTagging.getTagId());
                    jo.put("longitude", mapTagging.getTagLongitude());
                    jo.put("latitude", mapTagging.getTagLatitude());
                    return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
                }
            }catch (Exception e) {
                ErrorUtil.logError("查询地址类的经纬度接口异常",log,e,request);
            }
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
        }
    }

    /**
     * 保存地址类自定义字段的经纬度
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/saveMapTagging")
    @NoTokenAnnotation
    public ResponseEntity<String> saveMapTagging(HttpServletRequest request, HttpServletResponse response){
        String containsKeys = RequestMapUtil.paramsIsNotEmpty(request,"id,type,tableName,columnName,longitude,latitude,userId");
        if(containsKeys == null) {
            String id = this.getParameter("id");
            String type = this.getParameter("type");
            String tableName = this.getParameter("tableName");
            String columnName = this.getParameter("columnName");
            String longitude = this.getParameter("longitude");
            String latitude = this.getParameter("latitude");
            String userId = this.getParameter("userId");
            String tagId = this.getParameter("tagId");
            String lbizId = this.getParameter("lbizId");

            if(StringUtils.isEmpty(longitude)||StringUtils.isEmpty(latitude)){
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"定位失败！"), HttpStatus.OK);
            }

            MapTagging mapTagging = new MapTagging();
            mapTagging.setTableName(tableName);
            mapTagging.setColumnName(columnName);
            mapTagging.setTagLatitude(Double.parseDouble(latitude));
            mapTagging.setTagLongitude(Double.parseDouble(longitude));
            if("loan".equalsIgnoreCase(type)){
                mapTagging.setLoanId(Integer.parseInt(id));
            }else{
                mapTagging.setCustomerId(Integer.parseInt(id));
            }
            mapTagging.setCreateUser(Integer.parseInt(userId));

            if(StringUtil.isNotEmpty(tagId)){
                mapTagging.setTagId(Integer.parseInt(tagId));
            }

            if(StringUtil.isNotEmpty(lbizId)){
                mapTagging.setLbizId(Integer.parseInt(lbizId));
            }

            try{
                if(mapTagging.getTagId()!=null && mapTagging.getTagId().intValue()>0){
                    mapTaggingProvider.updateTagging(mapTagging,Integer.parseInt(userId));
                }else{
                    mapTaggingProvider.insertTagging(mapTagging,Integer.parseInt(userId));
                }
                if (mapTagging.getLoanId() != null) {
                    LoanApplyInfo applyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(mapTagging.getLoanId());
                    if (applyInfo != null) {
                        mapTaggingProvider.syncTaggingLoanToCustomer(mapTagging, applyInfo.getLoanCustomerId(), Integer.valueOf(userId));
                    }
                }
                return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
            }catch(Exception e) {
                ErrorUtil.logError("保存地址类的经纬度接口异常",log,e,request);
            }
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
        }
    }




    /**
     * 客户地图搜索接口
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/searchMapTagging")
    @NoTokenAnnotation
    public ResponseEntity<String> searchMapTagging(HttpServletRequest request, HttpServletResponse response){
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            ResponseEntity<String> responseEntity = checkAppParams("客户地图搜索接口", reqJson, "userId");
            if (responseEntity != null)
                return responseEntity;
            JSONObject reqObj = JSONObject.fromObject(reqJson);
            String customer = reqObj.containsKey("customer")?reqObj.getString("customer"):"";
            String userId = reqObj.containsKey("userId")?reqObj.getString("userId"):"";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customer", customer);
            params.put("userId", userId);
            JSONArray pointArray = new JSONArray();
            List<CustBasicInfoQueryForMap> custBasicInfoQueryForMaps = customerModuleIntf.queryBasicInfoListForMap(params);
            for (CustBasicInfoQueryForMap c : custBasicInfoQueryForMaps) {
                JSONObject userJson = new JSONObject();
                userJson.put("lng", c.getTagLongitude());
                userJson.put("lat", c.getTagLatitude());
                userJson.put("name", c.getCustomerName());
                userJson.put("phone", c.getPhoneNumber());
                if ("HOME_ADDRESS".equals(c.getColumnName()))
                    userJson.put("address", c.getHomeAddress());
                else
                    userJson.put("address", c.getLiveAddress());
                pointArray.add(userJson);
            }
            return new ResponseEntity<String>(AppJsonResponse.success(pointArray), HttpStatus.OK);
        } catch (Exception e) {
            log.error("客户地图搜索接口： reqJson:"+reqJson,e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 上传手机的经纬度轨迹
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/uploadTrajectory")
    @NoTokenAnnotation
    public ResponseEntity<String> uploadTrajectory(HttpServletRequest request, HttpServletResponse response){
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            if (StringUtils.isBlank(reqJson)) {
                log.error("上传手机的经纬度轨迹接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = JSONObject.fromObject(reqJson);
            String deviceId = reqObj.containsKey("deviceId")?reqObj.getString("deviceId"):"";
            JSONArray ja = reqObj.getJSONArray("data");
            List<MapTrajectory> list = new ArrayList<MapTrajectory>();
            for(int i=0;i<ja.size();i++){
                JSONObject jo = ja.getJSONObject(i);
                MapTrajectory trajectory = new MapTrajectory();
                trajectory.setClientTime(DateUtil.parser(jo.getString("clientTime"),DateUtil.DEFAULT_DATETIME_FORMAT));
                trajectory.setTraLatitude(jo.getDouble("latitude"));
                trajectory.setTraLongitude(jo.getDouble("longitude"));
                trajectory.setCreateDate(new Date());
                trajectory.setCreateUser(jo.getInt("userId"));
                trajectory.setDeviceId(deviceId);
                list.add(trajectory);
            }
            mapTrajectoryProvider.insertTrajectory(list);
            return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
        }catch(Exception e) {
            log.error("保存地址类的经纬度接口异常 reqJson:"+reqJson,e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }


    @NoLoginInterceptor
    @RequestMapping("/v1/getAddressByLngLat")
    @NoTokenAnnotation
    public ResponseEntity<String> getAddressByLngLat(HttpServletRequest request, HttpServletResponse response){
        String containsKeys = RequestMapUtil.paramsIsNotEmpty(request,"lng,lat");
        if(containsKeys == null){
            String lng = this.getParameter("lng");
            String lat = this.getParameter("lat");
            try {
                String address = mapTaggingProvider.getAddressByLngLat(lng, lat);
                return new ResponseEntity<String>(AppJsonResponse.success(address), HttpStatus.OK);
            }catch (Exception e) {
                ErrorUtil.logError("查询地址类的经纬度接口异常",log,e,request);
            }
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
        }
    }
}
