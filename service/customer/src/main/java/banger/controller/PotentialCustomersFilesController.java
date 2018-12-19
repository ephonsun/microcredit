package banger.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import banger.domain.customer.CustPotentialCustomersFilesQuery;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.framework.pagesize.IPageList;
import banger.service.intf.IPotentialCustomersFilesService;
import banger.domain.customer.CustPotentialCustomersFiles;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 潜在客户附件文件表页面访问类
 */
@Controller
@RequestMapping("/potentialCustomersFiles")
public class PotentialCustomersFilesController extends BaseController {
	private static final long serialVersionUID = 8407026124325248907L;
	@Autowired
	private IPotentialCustomersFilesService potentialCustomersFilesService;


	private String BASE_PATH= "/customer/vm/potentialCustomer/potentialFile/";

	/**
	 * 得到潜在客户附件文件表列表页面
	 * @param module [Tabs:影像资料标签页；List：影像资料列表显示；Show：影像资料预览页面；Big：大图页面；]
	 * @return
	 */
	@RequestMapping("/getPotentialCustomersFilesPage/{module}")
	public String getPotentialCustomersFilesListPage(@PathVariable(value = "module") String module,
													 @RequestParam(value = "potentialId", defaultValue = "") String potentialId){
		//如果是影像资料预览页面，设置影像资料数据
		if ("Show".equals(module)) {
			List<CustPotentialCustomersFiles> potentialCustomersFilesList = potentialCustomersFilesService.getPotentialFilesListByPotentialId(Integer.parseInt(potentialId));
			setAttribute("list", potentialCustomersFilesList);
		}
		setAttribute("potentialId", potentialId);
		return BASE_PATH + "CustPotentialCustomersFiles" + module;
	}

	/**
	 * 查询潜在客户附件文件表列表数据
	 * @return
	 */
	@RequestMapping("/queryPotentialCustomersFilesListData")
	public void queryPotentialCustomersFilesListData(@RequestParam(value = "potentialId", defaultValue = "") String potentialId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("potentialId", potentialId);
		condition.put("isDel", 0);
		IPageList<CustPotentialCustomersFilesQuery> potentialCustomersFilesList = potentialCustomersFilesService.queryPotentialCustomersFilesQueryList(condition,this.getPage());
		renderText(JsonUtil.toJson(potentialCustomersFilesList, "id","fileName,createDate,createUserName", DateUtil.DEFAULT_DATETIME_FORMAT).toString());
	}


	/**
	 * 删除潜在客户附件文件表数据
	 * @return
	 */
	@RequestMapping("/deletePotentialCustomersFiles")
	@ResponseBody
	public void deletePotentialCustomersFiles(){
		String id = getParameter("id");
		potentialCustomersFilesService.deletePotentialCustomersFilesById(Integer.parseInt(id));
		renderText(SUCCESS);
	}



	/**
	 * 图片流
	 * @param isOriginal 是否查看原图，默认为空（查看缩略图）
	 */
	@RequestMapping("/potentialImage")
	public void potentialImage(@RequestParam(value = "id", defaultValue = "") String id,
							   @RequestParam(value = "isOriginal", defaultValue = "") String isOriginal, HttpServletResponse response){
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			CustPotentialCustomersFiles file = potentialCustomersFilesService.getPotentialCustomersFilesById(Integer.parseInt(id));
			String filePath;
			if (StringUtils.isNotBlank(isOriginal))
				filePath= file.getFilePath() + file.getFileName();
			else
				filePath = file.getFileThumbImagePath() + file.getFileThumbImageName();

			FileUtil.downloadFile(filePath, response, this.getRootPath());
		}

	}
}
