package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysAppVersion;
import banger.framework.pagesize.IPageList;
import banger.framework.util.FileUtil;
import banger.framework.util.ZipUtil;
import banger.service.intf.IAppVersionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * APP升级版本信息表页面访问类
 */
@Controller
@RequestMapping("/sysAppVersion")
public class AppVersionController extends BaseController {
	private static final long serialVersionUID = 1514916313227387181L;
	@Autowired
	private IAppVersionService appVersionService;

	/**
	 * 得到APP升级版本信息表列表页面
	 * @return
	 */
	@RequestMapping("/getAppVersionListPage")
	public ModelAndView getAppVersionListPage(){
		ModelAndView mv = new ModelAndView("/upgrade/vm/appVersionListPage");
		return mv;
	}

	/**
	 * 查询APP升级版本信息表列表数据
	 * @return
	 */
	@RequestMapping("/queryAppVersionListData")
	public String queryAppVersionListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<SysAppVersion> appVersionList = appVersionService.queryAppVersionList(condition,this.getPage());
		for(int i=0;i<appVersionList.size();i++){
			SysAppVersion version = appVersionList.get(i);
			if("1".equals(version.getIsUpdate())){
				version.setIsUpdate("是");
			}else{
				version.setIsUpdate("否");
			}
			if (i > 0) {
				if (version.getIsDel().intValue() != 1) {
					version.setIsDel(2);
				}
			}
			if(version.getUserId() == 0)
				version.setUserName("配置用户");
		}
		renderText(JsonUtil.toJson(appVersionList, "id","userId,userName,apkName,apkVersionShow,apkVersionUpd,isUpdate,isDel,updateContent,createDate").toString());
		return null;
	}

	/**
	 * 得到APP升级版本信息表新增页面
	 * @return
	 */
	@RequestMapping("/getAppVersionInsertPage")
	public String getAppVersionInsertPage(){
		SysAppVersion appVersion = new SysAppVersion();
		setAttribute("appVersion",appVersion);
		return SUCCESS;
	}

	/**
	 * 得到APP升级版本信息表修改页面
	 * @return
	 */
	@RequestMapping("/getAppVersionUpdatePage")
	public String getAppVersionUpdatePage(){
		String id = getParameter("id");
		SysAppVersion appVersion = appVersionService.getAppVersionById(Integer.parseInt(id));
		setAttribute("appVersion",appVersion);
		return SUCCESS;
	}

	/**
	 * 得到APP升级版本信息表查看页面
	 * @return
	 */
	@RequestMapping("/getAppVersionDetailPage")
	public String getAppVersionDetailPage(){
		String id = getParameter("id");
		SysAppVersion appVersion = appVersionService.getAppVersionById(Integer.parseInt(id));
		setAttribute("appVersion",appVersion);
		return SUCCESS;
	}

	/**
	 * 新增APP升级版本信息表数据
	 * @return
	 */
	@RequestMapping("/insertAppVersion")
	public String insertAppVersion(@ModelAttribute SysAppVersion appVersion){
		Integer loginUserId = getLoginInfo().getUserId();
		appVersion.setUserId(loginUserId);
		appVersionService.insertAppVersion(appVersion,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改APP升级版本信息表数据
	 * @return
	 */
	@RequestMapping("/updateAppVersion")
	public String updateAppVersion(@ModelAttribute SysAppVersion appVersion){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			String id = getParameter("id");
			appVersion.setIsDel(1);
			appVersion.setId(Integer.valueOf(id));
			appVersionService.updateAppVersion(appVersion,loginUserId);
			renderText(true, "删除APP成功！", null);
			return null;
		} catch (Exception e) {
			log.error("删除APP报错", e);
		}
		renderText(false, "删除APP失败！", null);
		return null;
	}

	/**
	 * 删除APP升级版本信息表数据
	 * @return
	 */
	@RequestMapping("/deleteAppVersion")
	public String deleteAppVersion(){
		String id = getParameter("id");
		appVersionService.deleteAppVersionById(Integer.parseInt(id));
		renderText(SUCCESS);
		return null;
	}

	/**
	 * @Author: yangdw
	 * @Description:跳转app升级包页面
	 * @Date: 15:25 2017/9/20
	 */
	@RequestMapping("/getUpgradeMobilePage")
	public ModelAndView getUpgradeMobilePage(){
		ModelAndView mv = new ModelAndView("/upgrade/vm/uploadMobileFiles");
		//获取最新的版本
		SysAppVersion version = appVersionService.getLastOneVersionUpd();
		if (version != null) {
			mv.addObject("lastVersionUpd", version.getApkVersionUpd());
		}else{
			mv.addObject("lastVersionUpd", 0);
		}
		return mv;
	}

	/**
	 * @Author: yangdw
	 * @param request
	 * @param uplodeFile 上传app升级包
	 * @Description:
	 * @Date: 15:57 2017/9/28
	 */
	@RequestMapping("/doAppUpload")
	public void doAppUpload(HttpServletRequest request, @RequestParam("uplodeFile") MultipartFile uplodeFile) throws IOException {
		try {
			String fullFilename = URLDecoder.decode(this.getParameter("fullFilename"),"UTF-8");
			String path = request.getSession().getServletContext().getResource("/apk").getFile();
			if(!FileUtil.isExistFilePath(path)){
				path = FileUtil.contact(getRootPath(), "apk");
			}
			String configFileName = FileUtil.getFileName(fullFilename);
			File file = new File(path);
			if (!file.exists()) {
				// 文件不存在则创建
				file.mkdirs();
			}else{
				/*//需要删除上一次上传的app和remark.txt文件
				File[] files = file.listFiles();
				for (File delfile : files) {
					delfile.delete();
				}*/
				File[] files=file.listFiles();
				for(int i=0;i<files.length;i++){
					if (!FileUtil.getFileName(files[i].getPath()).equals(configFileName)) {
						FileUtil.deleteDir(files[i]);
					}
				}
			}
			String saveFilename = FileUtil.contact(path, configFileName);
//			uplodeFile.transferTo(new File(saveFilename));
			JSONObject json = new JSONObject();
			json.put("fileName", FileUtil.getFileName(saveFilename));
			json.put("fileUrl", saveFilename);
			renderJson(true, "成功", json);
		} catch (Exception e){
			JSONObject json = new JSONObject();
			renderJson(true, "系统异常", json);
			renderText("系统异常");
			log.error("升级app报错:",e);
		}
	}


	/**
	 * @Author: yangdw
	 * @param
	 * @Description:获取上传apk的升级版本号和显示版本号
	 * @Date: 10:36 2017/10/23
	 */
	@RequestMapping("/getAppUploadFileVersion")
	public void getAppUploadFileVersion(HttpServletRequest request, @RequestParam("uplodeFile") MultipartFile uplodeFile) {
		try {
			String fullFilename = URLDecoder.decode(this.getParameter("fullFilename"),"UTF-8");
			String path = request.getSession().getServletContext().getResource("/apk").getFile();
			if(!FileUtil.isExistFilePath(path)){
				path = FileUtil.contact(getRootPath(), "apk");
			}
			String configFileName = FileUtil.getFileName(fullFilename);
			File file = new File(path);
			if (!file.exists()) {
				// 文件不存在则创建
				file.mkdirs();
			}
			String saveFilename = FileUtil.contact(path, configFileName);
			uplodeFile.transferTo(new File(saveFilename));

			//解压apk文件
			ZipUtil.unZip(saveFilename,path);
			JSONObject json = new JSONObject();
			//获取显示版本号,和升级版本号
			String xmlPath = path + File.separator + "assets" + File.separator + "version.xml";
			File f = new File(xmlPath);
			if (!f.exists()) {
				renderJson(true, "成功", json);
				return;
			}
//			JSONObject json = getXmlContent(path + File.separator + "assets" + File.separator + "version.xml");
			String str = FileUtil.getFileString(xmlPath, "UTF-8");
			String apkVersionShow = str.substring(str.indexOf("<versionName>") + 13, str.indexOf("</versionName>"));
			String apkVersionUpd = str.substring(str.indexOf("<versionCode>") + 13, str.indexOf("</versionCode>"));
			json.put("apkVersionShow", apkVersionShow);
			json.put("apkVersionUpd", apkVersionUpd);
			renderJson(true, "成功", json);
		} catch (Exception e){
			JSONObject json = new JSONObject();
			renderJson(true, "系统异常", json);
			renderText("系统异常");
			log.error("获取上传apk的升级版本号和显示版本号报错:",e);
		}
	}

//	/**
//	 * @Author: yangdw
//	 * @param fileUrl xml路径
//	 * @param params 需要返回的xml中的key
//	 * @Description:
//	 * @Date: 14:01 2017/10/23
//	 */
//	private JSONObject getXmlContent(String fileUrl,String ... params) {
//
//		Element element = null;
//		// 可以使用绝对路劲
//		File f = new File(fileUrl);
//		// documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
//		DocumentBuilder db = null;
//		DocumentBuilderFactory dbf = null;
//		JSONObject json = new JSONObject();
//		try {
//			//文件不存在
//			if(!f.exists()){
//				log.info("读取上传版本的version.xml文件不存在!!!");
//				return null;
//			}
//
//			// 返回documentBuilderFactory对象
//			dbf = DocumentBuilderFactory.newInstance();
//			// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
//			db = dbf.newDocumentBuilder();
//			// 得到一个DOM并返回给document对象
//			Document dt = db.parse(f);
//			// 得到一个elment根元素
//			element = dt.getDocumentElement();
//			// 获得根节点
////			System.out.println("根元素：" + element.getNodeName());
//			// 获得根元素下的子节点
//			NodeList childNodes = element.getChildNodes();
//			// 遍历这些子节点
//			for (int i = 0; i < childNodes.getLength(); i++) {
//				// 获得每个对应位置i的结点
//				Node node1 = childNodes.item(i);
//				if ("ydwd".equals(node1.getNodeName())) {
//					// 如果节点的名称为"ydwd"，则输出ydwd元素属性type
////					System.out.println("\r\n一级: " + node1.getAttributes().getNamedItem("type").getNodeValue() + ". ");
//					// 获得<Accounts>下的节点
//					NodeList nodeDetail = node1.getChildNodes();
//					// 遍历<ydwd>下的节点
//					for (int j = 0; j < nodeDetail.getLength(); j++) {
//						// 获得<ydwd>元素每一个节点
//						Node detail = nodeDetail.item(j);
//						if ("versionName".equals(detail.getNodeName())) // 输出versionName
//							json.put("apkVersionShow", detail.getTextContent());
//						else if ("versionCode".equals(detail.getNodeName())) // 输出versionCode
//							json.put("apkVersionUpd", detail.getTextContent());
//					}
//				}
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			log.error("读取上传版本的version.xml文件报错",e);
//		}
//		return json;
//	}
}
