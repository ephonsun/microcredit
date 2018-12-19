package banger.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banger.framework.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.task.TskTaskInfoQuery;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.ITaskModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.PageSizeUtil;

@Controller
@RequestMapping("/api")
public class AppTaskController extends BaseController{

	private static final long serialVersionUID = 4913822165796724272L;
	public static final String TASK_CONDITION = "offset,isOverDate";
	@Autowired
	private ITaskModule taskModule;
	
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/queryTaskList", method = RequestMethod.POST)
	public ResponseEntity<String> queryTaskList(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询任务列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = JSONObject.fromObject(reqJson);
			String containsKeys = AppJsonUtil.containsKeys(reqObj, TASK_CONDITION);
			if (containsKeys == null) {
				IPageSize page = PageSizeUtil.getPageLimit(reqObj,AppParamsConst.PAGE_SIZE_TASK_LIST);
				Map<String, Object> condition = new HashedMap();
				//查询客户经理所属团队
				 Integer userId=(Integer)reqObj.get("assignUserId");
				// Integer teamGroupId=permissionModule.getGroupIdByUserId(userId);
				condition.put("createUser", userId);
				condition.put("assignUserId", userId);
				condition.put("taskTitle", reqObj.get("taskTitle"));
				condition.put("isOverDate", String.valueOf(reqObj.get("isOverDate")));
				//condition.put("mutilGroupIds", user.getUserGroupPermit());
				//condition.put("teamGroupId", teamGroupId);
				condition.put("nowDate", DateUtil.format(new Date(),DateUtil.DEFAULT_DATE_FORMAT));

				@SuppressWarnings("unchecked")
				List<TskTaskInfoQuery> taskList = taskModule.queryTaskInfoListForApp(condition,page);
				JSONArray resArray = AppJsonUtil.listToArray(taskList, "taskTitle,startDate,endDate,taskMold,taskType,taskTarget,taskFinish,remark,taskPercent,taskMoldStr","yy/MM/dd");
				String resultJson = resArray.toString();
				return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
			}else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("查询任务列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	

}
