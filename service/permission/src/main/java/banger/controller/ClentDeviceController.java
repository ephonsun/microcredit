package banger.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

import banger.framework.pagesize.IPageList;
import banger.service.intf.IClentDeviceService;
import banger.domain.permission.PadClentDevice;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面访问类
 */
public class ClentDeviceController extends BaseController {
	private static final long serialVersionUID = 8918502286224774910L;

	private IClentDeviceService clentDeviceService;
	private PadClentDevice clentDevice;

	public void setClentDeviceService(IClentDeviceService clentDeviceService) {
		this.clentDeviceService = clentDeviceService;
	}

	public PadClentDevice getClentDevice() {
		return this.clentDevice;
	}

	public void setClentDevice(PadClentDevice clentDevice) {
		this.clentDevice = clentDevice;
	}

	/**
	 * 得到列表页面
	 * @return
	 */
	public String getClentDeviceListPage(HttpServletRequest request){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<PadClentDevice> clentDeviceList = clentDeviceService.queryClentDeviceList(condition,this.getPage());
		request.setAttribute("clentDeviceList",clentDeviceList);
		return SUCCESS;
	}

	/**
	 * 查询列表数据
	 * @return
	 */
	public String queryClentDeviceListData(HttpServletRequest request){
		Map<String,Object> condition = new TreeMap<String, Object>();
		condition.put("localIp",this.getParameter("localIp"));
		condition.put("deviceId",this.getParameter("deviceId"));
		condition.put("version",this.getParameter("version"));
		IPageList<PadClentDevice> clentDeviceList = clentDeviceService.queryClentDeviceList(condition,this.getPage());
		this.renderText(JsonUtil.toJson(clentDeviceList, "id","version,deviceid,account,localip,createTime,lastTime").toString());
		return null;
	}

	/**
	 * 得到新增页面
	 * @return
	 */
	public String getClentDeviceInsertPage(HttpServletRequest request){
		this.clentDevice = new PadClentDevice();
		request.setAttribute("clentDevice",clentDevice);
		return SUCCESS;
	}

	/**
	 * 得到修改页面
	 * @return
	 */
	public String getClentDeviceUpdatePage(HttpServletRequest request){
		String id = this.getParameter("id");
		this.clentDevice = this.clentDeviceService.getClentDeviceById(id);
		request.setAttribute("clentDevice",this.clentDevice);
		return SUCCESS;
	}

	/**
	 * 得到查看页面
	 * @return
	 */
	public String getClentDeviceDetailPage(HttpServletRequest request){
		String id = this.getParameter("id");
		this.clentDevice = this.clentDeviceService.getClentDeviceById(id);
		request.setAttribute("clentDevice",this.clentDevice);
		return SUCCESS;
	}

	/**
	 * 新增数据
	 * @return
	 */
	public String insertClentDevice(){
		Integer loginUserId = this.getLoginInfo().getUserId();
		this.clentDeviceService.insertClentDevice(this.clentDevice,loginUserId);
		this.renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改数据
	 * @return
	 */
	public String updateClentDevice(){
		Integer loginUserId = this.getLoginInfo().getUserId();
		this.clentDeviceService.updateClentDevice(this.clentDevice,loginUserId);
		this.renderText(SUCCESS);
		return null;
	}

}
