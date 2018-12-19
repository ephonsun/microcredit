package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysMobileInfo;
import banger.domain.system.SysMobileRecord;
import banger.framework.pagesize.IPageList;
import banger.service.intf.IMobileInfoService;
import banger.service.intf.IMobileRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面访问类
 */
@Controller
@RequestMapping("/sysMobileInfo")
public class MobileInfoController extends BaseController {

	private static final long serialVersionUID = 1981042132883478556L;
	@Autowired
	private IMobileInfoService mobileInfoService;
	@Autowired
	private IMobileRecordService mobileRecordService;

	/**
	 * 得到列表页面
	 * @return
	 */
	@RequestMapping("/getMobileInfoListPage")
	public String getMobileInfoListPage(){
		return "/system/vm/sysMobileInfoList";
	}

	/**
	 * 查询列表数据
	 * @return
	 */
	@RequestMapping("/queryMobileInfoListData")
	@ResponseBody
	public void queryMobileInfoListData(){
		String serialNo = this.getParameter("serialNo");
		String mobileBrand = this.getParameter("mobileBrand");
		String mobileModel = this.getParameter("mobileModel");
		String systemVersion = this.getParameter("systemVersion");
		String userName = this.getParameter("userName");
		String txtStartDate = this.getParameter("txtStartDate");
		String txtEndDate = this.getParameter("txtEndDate");

		Map<String, Object> condition = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(serialNo)){
			condition.put("serialNo",serialNo);
		}
		if(StringUtils.isNotBlank(mobileBrand)){
			condition.put("mobileBrand",mobileBrand);
		}
		if(StringUtils.isNotBlank(mobileModel)){
			condition.put("mobileModel",mobileModel);
		}
		if(StringUtils.isNotBlank(systemVersion)){
			condition.put("systemVersion",systemVersion);
		}
		if(StringUtils.isNotBlank(userName)){
			condition.put("userName",userName);
		}
		if(StringUtils.isNotBlank(txtStartDate)){
			condition.put("lastLoginTime",txtStartDate);
		}
		if(StringUtils.isNotBlank(txtEndDate)){
			condition.put("lastLoginTimeEnd",txtEndDate);
		}

		IPageList<SysMobileInfo> mobileInfoList = mobileInfoService.queryMobileInfoList(condition,this.getPage());
		renderText(JsonUtil.toJson(mobileInfoList, "serialNo","serialNo,userName,updateDate,createDate,lastLoginTime,createUser,mobileBrand,delFlag,updateUser,lastUserId,systemVersion,mobileModel","yyyy-MM-dd HH:mm:ss").toString());
	}

	/**
	 * 得到新增页面
	 * @return
	 */
	@RequestMapping("/getMobileInfoInsertPage")
	public String getMobileInfoInsertPage(){
		SysMobileInfo mobileInfo = new SysMobileInfo();
		setAttribute("mobileInfo",mobileInfo);
		return SUCCESS;
	}

	/**
	 * 得到修改页面
	 * @return
	 */
	@RequestMapping("/getMobileInfoUpdatePage")
	public String getMobileInfoUpdatePage(){
		String serialNo = getParameter("serialNo");
		SysMobileInfo mobileInfo = mobileInfoService.getMobileInfoById(serialNo);
		setAttribute("mobileInfo",mobileInfo);
		return SUCCESS;
	}

	/**
	 * 得到查看页面
	 * @return
	 */
	@RequestMapping("/getMobileInfoDetailPage")
	public String getMobileInfoDetailPage(){
		String serialNo = getParameter("serialNo");
		SysMobileInfo mobileInfo = mobileInfoService.getMobileInfoById(serialNo);
		setAttribute("mobileInfo",mobileInfo);
		return "/system/vm/sysMobileDetail";
	}


	/**
	 * 获取登录记录页面
	 */
	@RequestMapping("/getloginRecordPage")
	public String getloginRecordPage(){
		String serialNo = getParameter("serialNo");
		setAttribute("serialNo",serialNo);
		return "/system/vm/sysMobileRecordList";
	}

	/**
	 * 获取登录记录列表数据
	 */
	@RequestMapping("/queryloginRecordList")
	@ResponseBody
	public void queryloginRecordList(){
		String serialNo = getParameter("serialNo");
		Map<String, Object> condition = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(serialNo)){
			condition.put("serialNo",serialNo);
		}
		IPageList<SysMobileRecord> mobileRecordList = mobileRecordService.queryMobileRecordList(condition,this.getPage());
		renderText(JsonUtil.toJson(mobileRecordList, "id","serialNo,userName,loginLongitude,loginLatitude,loginDate,loginIp","yyyy-MM-dd HH:mm:ss").toString());
	}

	/**
	 * 新增数据
	 * @return
	 */
	@RequestMapping("/insertMobileInfo")
	public String insertMobileInfo(@ModelAttribute SysMobileInfo mobileInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		mobileInfoService.insertMobileInfo(mobileInfo,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改数据
	 * @return
	 */
	@RequestMapping("/updateMobileInfo")
	public String updateMobileInfo(@ModelAttribute SysMobileInfo mobileInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		mobileInfoService.updateMobileInfo(mobileInfo,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 更改状态
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public String uptateStatus(){
		Integer loginUserId = getLoginInfo().getUserId();
		String serialNo = getParameter("serialNo");
		String isActived = getParameter("isActived");
		SysMobileInfo mobileInfo = new SysMobileInfo();
		mobileInfo.setSerialNo(serialNo);
		if (isActived != "" && Integer.parseInt(isActived)==2){
			mobileInfo.setIsActived(1);
		}else if(isActived != "" && Integer.parseInt(isActived)==1){
			mobileInfo.setIsActived(2);
		}
		mobileInfoService.updateMobileInfo(mobileInfo,loginUserId);
		if (Integer.parseInt(isActived)==2){
			renderText(true, "启用成功！",null);
		}else{
			renderText(true, "禁用成功！",null);
		}
		return null;
	}

	/**
	 * 解绑
	 */
	@RequestMapping("/unBind")
	public String unBind(){
		Integer loginUserId = getLoginInfo().getUserId();
		String serialNo = getParameter("serialNo");
		SysMobileInfo mobileInfo = mobileInfoService.getMobileInfoById(serialNo);
		if(mobileInfo.getIsBind()==2){
			renderText(true, "已经解绑",null);
		}else{
			mobileInfo.setIsBind(2);
			mobileInfoService.updateMobileInfo(mobileInfo,loginUserId);
			renderText(true, "解绑成功",null);
		}
		return null;
	}

	/**
	 * 删除数据
	 * @return
	 */
	@RequestMapping("/deleteMobileInfo")
	public String deleteMobileInfo(){
		String serialNo = getParameter("serialNo");
		mobileInfoService.deleteMobileInfoById(serialNo);
		renderText(SUCCESS);
		return null;
	}

}
