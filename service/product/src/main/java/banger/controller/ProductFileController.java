package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.product.ProdProductFile;
import banger.framework.util.FileUtil;
import banger.service.intf.IProductFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 产品列表页面访问类
 */
@Controller
@RequestMapping("/prodProductFile")
public class ProductFileController extends BaseController {

	private static final long serialVersionUID = 2863908563706000308L;

	@Autowired
	private IProductFileService productFileService;
	
	@Value("${file_root_path}")  
    private String fileRootPath; 

	
	/*@RequestMapping("/downloadFile")
	public ResponseEntity<byte[]> downloadFile() throws IOException {
		String ppfId = getParameter("ppfId");
		ProdProductFile productFile = productFileService.getProductFileById(Integer.valueOf(ppfId));
		String path = productFile.getPpfFilePath();
		File file = new File(path);
		byte[] by = FileUtils.readFileToByteArray(file);
		HttpHeaders headers = new HttpHeaders();
		String fileName = URLEncoder.encode(productFile.getPpfFileNameOld(),"UTF-8");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(by.length);
		return new ResponseEntity<byte[]>(by, headers, HttpStatus.CREATED);
	}*/
	
	@RequestMapping("/uploadImage")
	@ResponseBody
	public String uploadImage(@RequestParam(value = "fileImageInput", required = false) CommonsMultipartFile file) throws IOException{
		if(file!= null){
			String fileName = file.getOriginalFilename();
			String filePath = FileUtil.contact(fileRootPath, "product_image");
			ProdProductFile productFile = productFileService.uploadFile(fileName, filePath, file.getInputStream(),0);
			Integer loginUserId = getLoginInfo().getUserId();
			productFileService.insertProductFile(productFile, loginUserId);
			return productFile.getPpfId()+"";
		}
		return null;
	}
	
	@RequestMapping("/uploadVideo")
	@ResponseBody
	public String uploadVideo(@RequestParam(value = "fileVideoInput", required = false) CommonsMultipartFile file) throws IOException{
		if(file!= null){
			String fileName = file.getOriginalFilename();
			String filePath = FileUtil.contact(fileRootPath, "product_video");
			ProdProductFile productFile = productFileService.uploadFile(fileName, filePath, file.getInputStream(),1);
			Integer loginUserId = getLoginInfo().getUserId();
			productFileService.insertProductFile(productFile, loginUserId);
			return productFile.getPpfId()+"";
		}
		return null;
	}
	
	
	@NoLoginInterceptor
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String ppfId = getParameter("ppfId");
		String download = getParameter("download");//0查看缩略图 1下载图片 2查看原图
		ProdProductFile productFile = productFileService.getProductFileById(Integer.valueOf(ppfId));
		String path = productFile.getPpfFilePath();
		File file = new File(path);
		response.setContentType("image/jpeg");
		response.setCharacterEncoding("utf-8");
		if("0".equals(download)){
			File cutFile = new File(productFile.getPpfCutFilePath());
			if(cutFile.exists())
				file = cutFile;
		}else if("1".equals(download)){
			response.setContentType("application/force-download");// 设置强制下载不打开
			String fileName = URLEncoder.encode(productFile.getPpfFileNameOld(),"UTF-8");
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
		}
		OutputStream os = response.getOutputStream();
		if (file.exists()) {
			productFileService.downloadFile(file, os);
		}
	}

}
