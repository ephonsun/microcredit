package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoTableInfo;
import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.enumerate.IntoFaceOCRErrorType;
import banger.domain.html5.IntoFileInfo;
import banger.domain.html5.IntoIntefaceRecord;
import banger.domain.html5.IntoIntefaceRecordQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.service.intf.IFileInfoService;
import banger.service.intf.IIntefaceRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人脸识别信息
 */
@Controller
@RequestMapping("/faceContrast")
public class FaceContrastController extends BaseController{
    private static final long serialVersionUID = -8600447847114149592L;
    @Autowired
    private IIntefaceRecordService intefaceRecordService;
    @Autowired
    private IFileInfoService fileInfoService;
    /**
     * 得到列表界面
     * @return
     */
    @RequestMapping("/getFaceContrastListPage")
    public String getFaceContrastListPage(){

        return "config/vm/faceContrastInfoList";
    }


    /**
     * 查询人脸识别表列表数据
     *
     * @return
     */
    @RequestMapping("/queryFaceContrastInfoList")
    @ResponseBody
    public void queryFaceContrastInfoList(@RequestParam(value ="txtStartDate" ,defaultValue = "")String txtStartDate,
                                                @RequestParam(value ="txtEndDate" ,defaultValue = "")String txtEndDate,
                                                @RequestParam(value ="interfaceType" ,defaultValue = "")String interfaceType,
                                                @RequestParam(value ="isSuccess" ,defaultValue = "")String isSuccess) {

        Map<String, Object> condition = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(txtStartDate))
        {
            condition.put("txtStartDate",txtStartDate);
        }
        if(StringUtils.isNotBlank(txtEndDate))
        {
            condition.put("txtEndDate",txtEndDate);
        }
        if(StringUtils.isNotBlank(interfaceType))
        {
            condition.put("interfaceType",interfaceType);
        }
        if(StringUtils.isNotBlank(isSuccess))
        {
            condition.put("isSuccess",isSuccess);
        }


         IPageList<IntoIntefaceRecordQuery> intoIntefaceRecordIPageList = intefaceRecordService.queryIntefaceRecordList(condition, this.getPage());

        renderText(JsonUtil.toJson(intoIntefaceRecordIPageList, "requestId", "faceIntoType,isSuccessed,errorMessage,time").toString());

    }
    /**
     * 查询ocr,人脸识别详情
     *
     * @return
     */
    @RequestMapping("/IntoIntefaceRecordQuery")
    public String queryFaceContrastInfo(String id){
        IntoIntefaceRecordQuery intefaceRecordQuery = intefaceRecordService.IntoIntefaceRecordQuery(Integer.parseInt(id));
      //  intefaceRecordQuery.getRequestId();
        String errors = intefaceRecordQuery.getErrorMessage().split(":")[0].toLowerCase();
        String errorsMessage ="";
        for(IntoFaceOCRErrorType type : IntoFaceOCRErrorType.values()){
            if (errors.equals(type.type)){
                errorsMessage  = type.typeMessage;
            }
        }
       setAttribute("errorsMessages",errorsMessage);
        setAttribute("intefaceRecordQuery",intefaceRecordQuery);
        return "config/vm/faceContrastInfo";

    }

    /**
     * 下载图片
     *
     * @return
     */
    @RequestMapping("/downPicture")
    public void downPicture(@RequestParam(value ="requestId" ,defaultValue = "")String requestId,
                            @RequestParam(value ="applyId" ,defaultValue = "")String applyId,
                            String imageType,HttpServletResponse response){

        Map<String,Object> condition = new HashMap();
        if (!applyId.equals("")){
            condition.put("applyId",Integer.parseInt(applyId));
        }
       if (!requestId.equals("")){
           condition.put("requestId",Integer.parseInt(requestId));
       }

        condition.put("imageType",Integer.parseInt(imageType));
        List<IntoFileInfo> intoFileInfoList =    fileInfoService.queryFileInfoList(condition);
     //   List<IntoFileInfo> intoFileInfoLis= intoFileInfoList;
        if(intoFileInfoList!=null){
            String path =intoFileInfoList.get(0).getFilePath();
            String filename = intoFileInfoList.get(0).getFileName();
            String srcName = intoFileInfoList.get(0).getFileSrcName();
            downloadImageFile(requestId,path,filename,srcName,response);
        }
    }
    private void downloadImageFile(String requestId,String path,String filename,String srcName,HttpServletResponse response){
        if(StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(filename)){
            String imageFilename = FileUtil.contact(path, filename);
            File file = new File(imageFilename);
            if (file.exists()) {
                FileInputStream stream = null;
                OutputStream output = null;
                try{
                    long fileSize = file.length();
                    response.setCharacterEncoding("utf-8");
               //     response.setContentType("application/force-download");// 设置强制下载不打开
                    String imageName = URLEncoder.encode(srcName,"UTF-8");
                    response.addHeader("Content-Disposition", "attachment;fileName=" + imageName);// 设置文件名
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
                    log.error("下截图片异常 id:"+requestId+" error:"+e.getMessage(),e);
                }finally{
                    if(stream!=null){
                        try{stream.close();}catch(Exception e){}
                    }
                    if(output!=null){
                        try{output.close();}catch(Exception e){}
                    }
                }
            }}
    }

}
