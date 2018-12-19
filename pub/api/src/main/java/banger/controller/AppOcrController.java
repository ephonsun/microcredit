package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.face.FaceSetting;
import banger.domain.html5.IntoFileInfo;
import banger.framework.util.FileUtil;
import banger.framework.util.HttpRequestClient;
import banger.framework.util.StringUtil;
import banger.framework.util.ThumbUtil;
import banger.moduleIntf.ILoanModule;
import banger.service.intf.IFaceSettingService;
import banger.service.intf.IFileInfoService;
import banger.util.MultipartImageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证识别
 * Created by zhusw on 2017/9/7.
 */
@Controller
@RequestMapping("/api")
public class AppOcrController extends BaseController {

    @Autowired
    private IFaceSettingService faceSettingService;
    @Autowired
    private IFileInfoService fileInfoService;

    @Resource
    private ILoanModule loanModule;

    /**
     * 身份证识别
     * @param request
     * @param response
     */
    @NoLoginInterceptor
    @RequestMapping("/faceid/v1/ocridcard")
    public ResponseEntity<String> getFaceOcrIdcard(@RequestParam("image") MultipartFile[] images, HttpServletRequest request, HttpServletResponse response){
        File image = null;
        File image2= null;
        Integer id1=null;
        Integer id2=null;

        try {
//            if (request instanceof MultipartRequest) {
//                MultipartRequest multRequest = (MultipartRequest) request;
//                Map<String, MultipartFile> fileMap = multRequest.getFileMap();
//                if (fileMap.containsKey("image")) {
//                    image = MultipartImageUtil.createImageFile(this.getRootPath(), fileMap.get("image"));
//                }
//            }
            //form表单上传2张图片，第一张正面image,第二张反面image2
            if(images!=null&&images.length==2){
                   image=MultipartImageUtil.createImageFile(this.getRootPath(),images[0]);
//                   image2=MultipartImageUtil.createImageFile(this.getRootPath(),images[1]);
                    JSONObject json1=recordFile(images[0]);
                    id1= setIntoFileInfo(json1,1);
                    JSONObject json2=recordFile(images[1]);
                    id2=setIntoFileInfo(json2,2);
            }
        }catch (Exception e){
            return new ResponseEntity<String>("程序异常:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String legality = this.getParameter("legality");

        FaceSetting setting = this.getFaceSetting();
        String url = setting.getFaceOcrUrl();
        Map<String,Object> paramMap = new HashMap<String,Object>();

        paramMap.put("api_key",setting.getFaceOcrKey());
        paramMap.put("api_secret",setting.getFaceOcrSecret());

        if(StringUtil.isNotEmpty(legality))
            paramMap.put("legality",legality);

        if(image!=null)paramMap.put("image",image);

        try {
            Date t1 = new Date();
            HttpRequestClient.Result result = HttpRequestClient.postMultipartData(url, paramMap);
            Date t2 = new Date();
            System.out.println(t2.getTime()-t1.getTime());
            System.out.println(result.getStatus());
            System.out.println(result.getResponseBody());

            if(image!=null)image.delete();
            String s=result.getResponseBody().toString();
            System.out.println(s.substring(0,s.length()-1)+",\"id1\":"+id1+",\"id2\":"+id2+"}");
            return new ResponseEntity<String>(s.substring(0,s.length()-1)+",\"id1\":"+id1+",\"id2\":"+id2+"}", result.getStatus());
        }catch (IOException e){
            return new ResponseEntity<String>("程序异常:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @NoLoginInterceptor
    @RequestMapping("/upLoadFile")
    @ResponseBody
    public void upLoadFile(@RequestParam("image") MultipartFile[] images){
        JSONObject json=new JSONObject();
        try {
            if(images!=null&&images.length==2){
                Integer id1=null;
                Integer id2=null;
                JSONObject json1=recordFile(images[0]);
                id1= setIntoFileInfo(json1,1);
                JSONObject json2=recordFile(images[1]);
                id2=setIntoFileInfo(json2,2);
                json.put("id1",id1);
                json.put("id2",id2);
                renderJson(true,"",json);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(false,"",null);
    }

    private Integer setIntoFileInfo(JSONObject json,Integer imageType){
        IntoFileInfo file=new IntoFileInfo();
        file.setCreateTime(new Date());
        file.setFileName(json.optString("fileName"));
        file.setFilePath(json.optString("filePath"));
        file.setFileSize(Integer.parseInt(json.optString("fileSize")));
        file.setFileSrcName(json.getString("fileSrcName"));
        file.setFileThumbImageName(json.optString("fileThumbImageName"));
        file.setFileThumbImagePath(json.optString("fileThumbImagePath"));
        file.setImageType(imageType);
        return	fileInfoService.insertFileInfoReturnId(file);
    }
    private FaceSetting getFaceSetting(){
        FaceSetting setting = faceSettingService.getSettingById(1);
        return setting;
    }



    private JSONObject recordFile(MultipartFile image) {

        // 上传位置
        String path = loanModule.getLoanAddedProvider().getSavePath(
                LoanAddedFileType.IDIMAGE.savePath);
        // 缩略图
        String thumbPath = loanModule.getLoanAddedProvider()
                .getImageThumbPath();

        FileUtil.mkdirs(new String[]{path, thumbPath});
        // 获得原始文件名
        String srcName = image.getOriginalFilename();
        // 新文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(new Date());
        String newFileName = dateString + srcName;
        String newFullFilename = path + newFileName;

        if (!image.isEmpty()) {
            try {
                FileOutputStream fos = new FileOutputStream(newFullFilename);
                InputStream in = image.getInputStream();
                int b = 0;
                while ((b = in.read()) != -1) {
                    fos.write(b);
                }
                fos.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String thumbFilename = thumbPath + newFileName;

            try {
                ThumbUtil.zoomImageScale(new File(newFullFilename), thumbFilename, 400);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject json=new JSONObject();
            if(new File(newFullFilename).exists()){
                Long fileSize = new File(newFullFilename).length();
                json.put("fileName",newFileName);
                json.put("filePath",path);
                json.put("fileSize",fileSize.intValue());
                json.put("fileSrcName",srcName);
                json.put("fileThumbImageName",newFileName);
                json.put("fileThumbImagePath",thumbPath);
                return json;
            }
            return null;
        }
        return null;
    }
}
