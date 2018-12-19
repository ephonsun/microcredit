package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.domain.task.TskTaskStatQuery;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.service.intf.ITaskStatService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 任务情况统计表页面访问类
 *
 */
@Controller
@RequestMapping("taskStat")
public class TaskStatController extends BaseController{

	private static final long serialVersionUID = 2649941976229006551L;

	@Autowired
	private ITaskStatService taskStatService;

	/**
     * form表单提交 Date类型数据绑定
     * <功能详细描述>
     * @param binder
     * @see [类、类#方法、类#成员]
     */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 得到团队任务报表页面
	 * @return
	 */
	@RequestMapping("getTaskStatPage")
	public String getTaskStatPage(){
		Integer loginUserId = getLoginInfo().getUserId();
		String groupPermit=getLoginInfo().getTeamGroupIdByRole();

		Integer teamGroupId= getLoginInfo().getTeamGroupId();
		if(groupPermit==""&&teamGroupId!=null){
			groupPermit=teamGroupId.toString();
	}

		System.out.println("数据权限:"+getLoginInfo().getUserGroupPermit()+"团队id:"+getLoginInfo().getTeamGroupId());
		setAttribute("userId", loginUserId);
		if(this.isCustomerManager()){
			setAttribute("role",1 );
		}else{
			setAttribute("role",2 );
		}
		setAttribute("groupPermit",groupPermit);
		setAttribute("gp",getLoginInfo().getTeamGroupIdByRole());
		setAttribute("userName",getLoginInfo().getUserName());
		setAttribute("teamGroupID",teamGroupId);
		return "/task/vm/taskStat";

	}

	/**
	 * 查看个人明细
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("getPersonListMxPage")
	public String getPersonListMXPage(@RequestParam(value = "userId",defaultValue = "") String userId,
									@RequestParam(value = "startDate",defaultValue = "") String startDate,
									@RequestParam(value = "endDate",defaultValue = "") String endDate){

		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("startDate",startDate);
		condition.put("endDate",endDate);
		condition.put("userId", userId);
		List<TskTaskStatQuery> taskStatList=taskStatService.queryPersonList(condition);
		setAttribute("list",taskStatList);
		setAttribute("startDate",startDate);
		setAttribute("endDate",endDate);
		setAttribute("name",getLoginInfo().getUserName());
		String date=DateUtil.format(new Date(),DateUtil.DEFAULT_DATETIME_FORMAT);
		setAttribute("d",date);
		return "/task/vm/personList";
	}

	/**
	 * 查看团队明细
	 * @param teamGroupId
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	@RequestMapping("getGroupListMxPage")
	public String getGroupListMxPage(
			@RequestParam(value = "teamGroupId",defaultValue = "") String teamGroupId,
			@RequestParam(value = "startDate",defaultValue = "") String startDate,
			@RequestParam(value = "endDate",defaultValue = "") String endDate){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("startDate",startDate);
		condition.put("endDate",endDate);
		condition.put("teamGroupId", teamGroupId);
		condition.put("taskLevel",3);
		List<TskTaskStatQuery> taskStatList=taskStatService.queryTaskStatList(condition);

		setAttribute("list",taskStatList);
		setAttribute("startDate",startDate);
		setAttribute("endDate",endDate);
		setAttribute("name",getLoginInfo().getUserName());
		String date=DateUtil.format(new Date(),DateUtil.DEFAULT_DATETIME_FORMAT);
		setAttribute("d",date);
		return "/task/vm/groupList";
	}
	/**
	 * 得到个人任务报表统计页面
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("getPersonListPage")
	public String getPersonListPage(@RequestParam(value = "userId",defaultValue = "") String userId,
									@RequestParam(value = "startDate",defaultValue = "") String startDate,
									@RequestParam(value = "endDate",defaultValue = "") String endDate){

		Map<String,Object> condition = new HashMap<String,Object>();
		Date nowDate=new Date();
		condition.put("startDate",startDate);
		condition.put("endDate",endDate);
		condition.put("userId", userId);
		condition.put("nowDate",nowDate);
		//List<TskTaskStatQuery> taskStatList=taskStatService.queryPersonList(condition);
		List<TskTaskStatQuery> taskStatList=taskStatService.queryLoanTasksList(condition);
		setAttribute("list",taskStatList);
		setAttribute("startDate",startDate);
		setAttribute("endDate",endDate);
		setAttribute("name",getLoginInfo().getUserName());
		String date=DateUtil.format(new Date(),DateUtil.DEFAULT_DATETIME_FORMAT);
		setAttribute("d",date);
		return "/task/vm/personListx";
	}

	/**
	 * 查看团队任务统计页面
	 * @param teamGroupId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("getGroupListPage")
	public String getGroupListPage(
								   @RequestParam(value = "teamGroupId",defaultValue = "") String teamGroupId,
								   @RequestParam(value = "startDate",defaultValue = "") String startDate,
								   @RequestParam(value = "endDate",defaultValue = "") String endDate){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("startDate",startDate);
		condition.put("endDate",endDate);
		condition.put("teamGroupId",teamGroupId);
		condition.put("taskLevel",3);
		condition.put("nowDate",new Date());
		//List<TskTaskStatQuery> taskStatList=taskStatService.queryTaskStatList(condition);
		List<TskTaskStatQuery> taskStatList=taskStatService.queryLoanTasksListForTeam(condition);
		setAttribute("list",taskStatList);
		setAttribute("startDate",startDate);
		setAttribute("endDate",endDate);
		setAttribute("name",getLoginInfo().getUserName());
		String date=DateUtil.format(new Date(),DateUtil.DEFAULT_DATETIME_FORMAT);
		setAttribute("d",date);
        return "/task/vm/groupListx";
	}

	/**
	 * 根据数据权限查询团队列表
	 */
	@RequestMapping("queryGroupListByGroupPermit")
	@ResponseBody
	@NoLoginInterceptor
	public void queryGroupListByGroupPermit(@RequestParam(value="groupPermit",defaultValue ="") String groupPermit){
		JSONObject resultJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if(!"".equals(groupPermit)){
			List<TskTaskStatQuery> list=taskStatService.queryGroupListByGroupPermit(groupPermit);
			for(TskTaskStatQuery t:list){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("teamGroupId",t.getTeamGroupId());
				jsonObject.put("groupName",t.getGroupName());
				jsonArray.add(jsonObject);
			}
		}
			resultJson.put("data",jsonArray);
		    renderJson(true,"",resultJson);
	}
	/**
	 * 根据团队id 获取经理
	 */
	  @RequestMapping("queryMemberByTeamGroupId")
	  @ResponseBody
	  @NoLoginInterceptor
	  public void queryMemberByGroupId(@RequestParam(defaultValue ="0" ,value="teamGroupId")  String teamGroupId){
			List<TskTaskStatQuery> list=taskStatService.queryMemberByTeamGroupId(teamGroupId);
		  JSONObject resultJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();

		  for (TskTaskStatQuery t: list) {
			  JSONObject jsonObject = new JSONObject();
			  jsonObject.put("userId", t.getUserId());
			  jsonObject.put("userName",t.getUserName());
			  jsonObject.put("groupName",t.getGroupName());
			  jsonArray.add(jsonObject);
		  }
		  resultJson.put("data", jsonArray);
		  renderJson(true, "", resultJson);
	  }

	/**
	 * 个人明细导出(暂时用不上)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("exportReport")
	public void exportReport(HttpServletRequest request, HttpServletResponse response)throws IOException {
		Map<String,Object> condition =new HashMap<String, Object>();
		condition.put("startDate",getParameter("startDate"));
		condition.put("endDate",getParameter("endDate"));
		condition.put("nowDate", DateUtil.format(new Date(),DateUtil.DEFAULT_DATE_FORMAT));
		condition.put("userId",getParameter("userId"));
		//原始实体数据
		List<TskTaskStatQuery> list=taskStatService.queryPersonList(condition);
		//转为list<String[] data>
		List<List<String>> reportList = new ArrayList<List<String>>();
		for (int i = 0; i < list.size(); i++) {
			TskTaskStatQuery task=list.get(i);
			List<String> colunms = new ArrayList<String>();
			colunms.add(task.getGroupName().toString());
			colunms.add(task.getTaskTitle().toString());
			colunms.add(task.getUserName().toString());
			colunms.add(task.getTarget().toString());
			if(task.getFinished()!=null){
				colunms.add(task.getFinished().toString());
			}else{
				colunms.add("");
			}
			colunms.add(task.getTaskPercent().toString());
			reportList.add(colunms);
		}
		List<String> head = new ArrayList<String>();
		head.add("团队名称");
		head.add("任务名称");
		head.add("客户经理");
		head.add("任务目标");
		head.add("已完成");
		head.add("完成比");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		String fileName="exportReport"+sdf.format(new Date());
		String path = getRootPath()+"exportReport";
		String filePath = FileUtil.contact(path,fileName);
		File csvFile = taskStatService.createCSVFile(head, reportList, path, fileName);
		downFile(response,csvFile.getPath(),fileName+".csv");
	}

	/**
	 * 团队明细导出(暂时用不上)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("exportReport2")
	public void exportReport2(HttpServletRequest request, HttpServletResponse response)throws IOException {
		Map<String,Object> condition =new HashMap<String, Object>();
		condition.put("startDate",getParameter("startDate"));
		condition.put("endDate",getParameter("endDate"));
		condition.put("nowDate", DateUtil.format(new Date(),DateUtil.DEFAULT_DATE_FORMAT));
		condition.put("teamGroupId",getParameter("teamGroupId"));
		condition.put("taskLevel",3);
		//原始实体数据
		List<TskTaskStatQuery> list=taskStatService.queryTaskStatList(condition);
		//转为list<String[] data>
		List<List<String>> reportList = new ArrayList<List<String>>();
		for (int i = 0; i < list.size(); i++) {
			TskTaskStatQuery task=list.get(i);
			List<String> colunms = new ArrayList<String>();
			colunms.add(task.getGroupName().toString());
			colunms.add(task.getTaskTitle().toString());
			colunms.add(task.getUserName().toString());
			colunms.add(task.getTarget().toString());
			if(task.getFinished()!=null){
				colunms.add(task.getFinished().toString());
			}else{
				colunms.add("");
			}
			colunms.add(task.getTaskPercent().toString());
			reportList.add(colunms);
		}
		List<String> head = new ArrayList<String>();
		head.add("团队名称");
		head.add("任务名称");
		head.add("客户经理");
		head.add("任务目标");
		head.add("已完成");
		head.add("完成比");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		String fileName="exportReport"+sdf.format(new Date());
		String path = getRootPath()+"exportReport";
		String filePath = FileUtil.contact(path,fileName);
		File csvFile = taskStatService.createCSVFile(head, reportList, path, fileName);
		downFile(response,csvFile.getPath(),fileName+".csv");
	}

	/**
	 * 个人统计导出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("exportReportx")
	public void exportReportx(HttpServletRequest request, HttpServletResponse response)throws IOException {
		Map<String,Object> condition =new HashMap<String, Object>();
		condition.put("startDate",getParameter("startDate"));
		condition.put("endDate",getParameter("endDate"));
		condition.put("nowDate",new Date());
		condition.put("userId",getParameter("userId"));
		//原始实体数据
		List<TskTaskStatQuery> list=taskStatService.queryLoanTasksList(condition);
		//转为list<String[] data>
		List<List<String>> reportList = new ArrayList<List<String>>();
		for (int i = 0; i < list.size(); i++) {
			TskTaskStatQuery task=list.get(i);
			List<String> colunms = new ArrayList<String>();
			colunms.add(task.getGroupName().toString());
			colunms.add(task.getUserName().toString());
			colunms.add(task.getTaskSum().toString());
			colunms.add(task.getTaskIng().toString());
			colunms.add(task.getTaskFinished().toString());
			colunms.add(task.getTaskNoFinished().toString());
			reportList.add(colunms);
		}
		List<String> head = new ArrayList<String>();
		head.add("团队名称");
		head.add("客户经理");
		head.add("任务数量");
		head.add("执行中的任务");
		head.add("已完成数");
		head.add("未完成数");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		String fileName="exportReport"+sdf.format(new Date());
		String path = getRootPath()+"exportReport";
		String filePath = FileUtil.contact(path,fileName);
		File csvFile = taskStatService.createCSVFile(head, reportList, path, fileName);
		downFile(response,csvFile.getPath(),fileName+".csv");
	}

	/**
	 * 团队统计导出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("exportReportx2")
	public void exportReportx2(HttpServletRequest request, HttpServletResponse response)throws IOException {
		Map<String,Object> condition =new HashMap<String, Object>();
		condition.put("startDate",getParameter("startDate"));
		condition.put("endDate",getParameter("endDate"));
		condition.put("nowDate",new Date());
		condition.put("teamGroupId",getParameter("teamGroupId"));
		condition.put("taskLevel",3);
		//原始实体数据
		List<TskTaskStatQuery> list=taskStatService.queryLoanTasksListForTeam(condition);
		//转为list<String[] data>
		List<List<String>> reportList = new ArrayList<List<String>>();
		for (int i = 0; i < list.size(); i++) {
			TskTaskStatQuery task=list.get(i);
			List<String> colunms = new ArrayList<String>();
			colunms.add(task.getGroupName().toString());
			colunms.add(task.getTaskSum().toString());
			colunms.add(task.getTaskIng().toString());
			colunms.add(task.getTaskFinished().toString());
			colunms.add(task.getTaskNoFinished().toString());
			reportList.add(colunms);
		}
		List<String> head = new ArrayList<String>();
		head.add("团队名称");
		head.add("任务数量");
		head.add("执行中的任务");
		head.add("已完成数");
		head.add("未完成数");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		String fileName="exportReport"+sdf.format(new Date());
		String path = getRootPath()+"exportReport";
		String filePath = FileUtil.contact(path,fileName);
		File csvFile = taskStatService.createCSVFile(head, reportList, path, fileName);
		downFile(response,csvFile.getPath(),fileName+".csv");
	}
	private void downFile(HttpServletResponse response, String path,String filename) {
		File file = new File(path);
		try {
			if (file.exists()) {


				InputStream ins = new FileInputStream(file);
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面


				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader(
						"Content-disposition",
						"attachment;filename="
								+ filename);// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			}
		} catch (IOException e) {
			log.error("文件下载出错", e);
		}finally {
			if(file.exists()){
				file.delete();
			}
		}
	}

}
