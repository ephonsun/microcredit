package banger.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableInfo;
import banger.framework.util.FileUtil;
import banger.service.intf.ITableColumnService;
import banger.service.intf.ITableInfoService;
import banger.service.intf.ITemplateFieldService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.service.intf.IConfigFileService;
import banger.domain.config.ModeConfigFile;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 模板配置文件信息表，保存上传模板，合同相对应的路径页面访问类
 */
@Controller
@RequestMapping("/modeConfigFile")
public class ConfigFileController extends BaseController {
	private static final long serialVersionUID = 7903050447425471260L;
	@Autowired
	private IConfigFileService configFileService;
	@Autowired
	private ITableInfoService tableInfoService;
	@Autowired
	private ITableColumnService tableColumnService;
	@Autowired
	private ITemplateFieldService templateFieldService;
	@Value("${file_root_path}")
	private String fileRootPath;

	private String BASE_PATH = "/config/vm/modelConfig/";

	@RequestMapping("getReportPage")
	public String  getReportPage(){
		return BASE_PATH + "reportList";
	}

	@PostMapping("queryConfigFiles")
	public void queryConfigFiles() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isDel", 0);
		params.put("modeType", 1);
		List<ModeConfigFile> modeConfigFiles = configFileService.queryConfigFileList(params);
		renderText(JsonUtil.toJson(modeConfigFiles, "id", "modeName").toString());
	}

	@RequestMapping("importReport")
	public String  importReport(@RequestParam(value = "id", defaultValue = "")String id){
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			ModeConfigFile configFile = configFileService.getConfigFileById(Integer.parseInt(id));
			setAttribute("configFile", configFile);
			String html = configFileService.getRowHtmlForShow(configFile.getFilePath());
			setAttribute("importTable", html);
		}
		return BASE_PATH + "investigateReportImport";
	}




	@PostMapping("saveConfigFiles")
	@ResponseBody
	public void saveConfigFiles(ModeConfigFile configFile) {
		configFile.setModeType(1);
		Integer loginUserId = getLoginInfo().getUserId();
		if (configFile.getId() == null) {
			configFileService.insertConfigFile(configFile, loginUserId);
		} else {
			configFileService.updateConfigFile(configFile, loginUserId);
		}
		String dataTable = configFileService.reloadVm(configFile.getId(), configFile.getFilePath());
		ModeConfigFile configFilenew = new ModeConfigFile();
		configFilenew.setId(configFile.getId());
		configFilenew.setDataTable(dataTable);
		configFileService.updateConfigFile(configFilenew, loginUserId);
		renderText(true,"保存成功","");
	}

	@PostMapping("deleteConfieFile")
	@ResponseBody
	public void deleteConfieFile(@RequestParam(value = "id", defaultValue = "")String id) {
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			ModeConfigFile configFile = configFileService.getConfigFileById(Integer.parseInt(id));
			configFile.setIsDel(1);
			configFileService.updateConfigFile(configFile, getLoginInfo().getUserId());
			renderText(true, "删除成功", "");
		} else {
			renderText(false,"参数错误","");
		}
	}

	@PostMapping("checkFileName")
	@ResponseBody
	public void checkFileName(@RequestParam(value = "id", defaultValue = "")String id,
							  @RequestParam(value = "modeName", defaultValue = "")String modeName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modeName", modeName);
		params.put("isDel", 0);
		params.put("modeType", 1);
		List<ModeConfigFile> modeConfigFiles = configFileService.queryConfigFileList(params);
		if (CollectionUtils.isNotEmpty(modeConfigFiles)){
			if (StringUtils.isNotBlank(id)) {
				if (modeConfigFiles.get(0).getId().toString().equals(id)) {
					renderText(false,"","");
				} else {
					renderText(true,"","");
				}
			} else {
				renderText(true,"","");
			}
		} else {
			renderText(false,"","");
		}
	}


	@RequestMapping("/uploadFiles")
	@ResponseBody
	public void uploadFiles(@RequestParam MultipartFile file) throws IOException {
		Integer loginUserId = getLoginInfo().getUserId();
		if(file!= null){
			//文件名
			String fileName = file.getOriginalFilename();
			//文件路径r
			String filePath = FileUtil.contact(fileRootPath, "mode/");
			Long size = file.getSize();
			//写入
			String saveFilePath = uploadFile(fileName,filePath,file.getInputStream(),size,loginUserId);
			if (StringUtils.isNotBlank(saveFilePath)) {
				String html = configFileService.getRowHtmlForShow(saveFilePath);
				renderText(true, saveFilePath, html);
			} else {
				renderText(false, "上传失败", "");
			}

		}
	}

	private String uploadFile(String fileName, String filePath, InputStream inputStream, Long size, Integer loginUserId) {
		try {
			File f = new File(filePath);
			if (!f.exists()) { //目录不存在，则创建相应的目录
				f.mkdirs();
			}
			int r = new Random().nextInt(1000);
			String ext = fileName.substring(fileName.indexOf("."), fileName.length());
			String saveName = System.currentTimeMillis() + String.valueOf(r) + ext;
			String saveFilePath = FileUtil.contact(filePath,saveName);
			File obj = new File(saveFilePath);
			//将文件上传到服务器
			FileUtils.copyToFile(inputStream,obj);
			return saveFilePath;
		} catch (Exception e) {
			log.error("uploadFile error",e);
			return "";
		}
	}


}
