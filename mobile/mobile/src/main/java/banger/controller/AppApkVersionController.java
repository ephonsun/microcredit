package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.annotation.NoTokenAnnotation;
import banger.domain.system.SysAppVersion;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IAppVersionProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @Author: yangdw
 * @Description:安卓升级app
 * @Date: Created in 9:21 2017/9/29.
 */
@Controller
@RequestMapping("/api/v1")
public class AppApkVersionController extends BaseController{
	private static final long serialVersionUID = 432982355669235797L;

	@Resource
	private IAppVersionProvider iAppVersionProvider;

	/**
	 * 版本升级接口
	 *
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/getLastOneVersionUpd")
	@NoTokenAnnotation
	public ResponseEntity<String> getLastOneVersionUpd() {
		try {
			SysAppVersion appVersion = iAppVersionProvider.getLastOneVersionUpd();
			if (appVersion == null) {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_136), HttpStatus.OK);
			}
			String apkFilename = FileUtil.contact(appVersion.getApkUrl(), "");
			File file = new File(apkFilename);
			appVersion.setIsFileExists(0);
			if (file.exists()) {
				appVersion.setIsFileExists(1);
			}
			JSONObject jsonObject = AppJsonUtil.toJson(appVersion, "isFileExists,apkName,apkUrl,apkVersionShow,apkVersionUpd,appUrl,isUpdate,updateContent,createDate");
			return new ResponseEntity<String>(AppJsonResponse.success(jsonObject), HttpStatus.OK);
		} catch (Exception e) {
			log.error("移动端调取版本升级接口接口异常:"+e.getMessage(),e);
			e.printStackTrace();
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
		}
	}

	/**
	 * 下载图片缩略图
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/downloadApk")
	@NoTokenAnnotation
	public void downloadApk(HttpServletRequest request,HttpServletResponse response){
		SysAppVersion appVersion = iAppVersionProvider.getLastOneVersionUpd();
		String path = appVersion.getApkUrl();
		String filename = appVersion.getApkName();
//		String srcName = info.getFileSrcName();
		downloadApkFile(path,filename,response);
	}

	private void downloadApkFile(String path,String filename,HttpServletResponse response){
		if(StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(filename)){
			String apkFilename = FileUtil.contact(path, "");
			File file = new File(apkFilename);
			if (file.exists()) {
				FileInputStream stream = null;
				OutputStream output = null;
				try{
					long fileSize = file.length();
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/force-download");// 设置强制下载不打开
					String appName = URLEncoder.encode(filename,"UTF-8");
					response.addHeader("Content-Disposition", "attachment;fileName=" + appName);// 设置文件名
					response.addHeader("Content-Length", fileSize+"");

					stream = new FileInputStream(file);
					output = response.getOutputStream();
					byte[] bytes = new byte[1024];
					int len = 0;
					while((len = stream.read(bytes))!=-1){
						output.write(bytes, 0, len);
						output.flush();
					}
				}catch(IOException e){
					log.error("下截apk异常"+" error:"+e.getMessage(),e);
				}finally{
					if(stream!=null){
						try{stream.close();}catch(Exception e){}
					}
					if(output!=null){
						try{output.close();}catch(Exception e){}
					}
				}
			}
		}
	}
	private String getHoldPlaceFilename(String filename){
		String path = this.getRootPath();
		filename = FileUtil.contact(path, filename);
		return filename;
	}
}
