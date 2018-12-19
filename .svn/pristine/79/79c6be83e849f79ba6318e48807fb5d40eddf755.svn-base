package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.dao.impl.IndustryGuidelinesDao;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.loan.LoanInfoAddedClass;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanInfoAddedFilesExtend;
import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.service.intf.IInfoAddedClassService;
import banger.framework.web.json.JsonArray;
import banger.framework.web.json.JsonObject;
import banger.service.intf.IInfoAddedFilesService;
import banger.service.intf.IInfoAddedOwnerService;
import banger.service.intf.ILoanApplyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/loanFile")
public class  LoanFileController extends BaseController{

    private static final long serialVersionUID = -6121276929407614868L;
    @Autowired
    private IInfoAddedFilesService infoAddedFilesService;
    @Autowired
    private ILoanApplyService loanApplyService;
    @Autowired
    private IInfoAddedOwnerService iInfoAddedOwnerService;
    @Autowired
    private IInfoAddedClassService iInfoAddedClassService;

    @Value("${file_root_path}")
    private String fileRootPath;
    /**
     * 跳转贷款资料页面
     */
    @RequestMapping("/getLoanFilePage")
    public ModelAndView getLoanInfoPage(@RequestParam("loanId") Integer loanId,@RequestParam("loanTypeId") Integer loanTypeId,@RequestParam("precType") String precType,@RequestParam("showApply") Integer showApply){
        ModelAndView mv = new ModelAndView("/loan/vm/loanFile");
        String userRoles = getLoginInfo().getRoleIds();
        String[] roles = userRoles.split(",");
        for (int i = 0; i<roles.length;i++){
            if(GroupRolesEnum.MANAGER.getRoleId().toString().equals(roles[i])){
                if("apply".equals(precType) || "investigate".equals(precType)) {
                    mv.addObject("isDelete",true);
                }
            }
        }
        mv.addObject("precType",getParameter("precType"));
        mv.addObject("module",getParameter("module"));
        mv.addObject("loanTypeId",loanTypeId);
        mv.addObject("loanId",loanId);
        mv.addObject("showApply",showApply);
        return mv;
    }

    @RequestMapping("getLoanInfoIds")
    @ResponseBody
    public String getLoanInfoIds() {
        String loanId=this.getParameter("loanId");
        String fileTypeStr = this.getParameter("fileType");
        List<File> list = new ArrayList<File>();
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(fileTypeStr)) {
            String fileTypes [] = fileTypeStr.split(",");
            for(String fileType :fileTypes){
                Map<String,Object> condition = new HashMap<String,Object>();
                condition.put("fileType",fileType);
                condition.put("loanId",loanId);
                condition.put("isDel",0);
                List<LoanInfoAddedFilesExtend> loanInfoAddedFilesExtendList = infoAddedFilesService.queryLoanInfoFile(condition);
                for (LoanInfoAddedFilesExtend loanAddFile : loanInfoAddedFilesExtendList) {
                    File file = new File(FileUtil.contact(loanAddFile.getFilePath(),loanAddFile.getFileName()));
                    if(file.exists()){
                        list.add(file);
                    }
                    sb.append(loanAddFile.getId()+",");
                }
            }
        }
        if(list.size() == 0){
            return null;
        }
        String ids = sb.toString().substring(0,sb.length()-1);
        return ids;
    }
    /**
     * 查询贷款资料列表
     */
    @RequestMapping("/queryLoanFile")
    @ResponseBody
    public void queryLoanInfo(@RequestParam(value = "loanId",defaultValue = "") Integer loanId){
        Map<String,Object> condition=new HashMap<String, Object>();
        String loanProcessType=getParameter("loanProcessType");
        String fileType=getParameter("fileType");
        condition.put("loanId",loanId);
        condition.put("isDel",0);
        condition.put("loanProcessType",loanProcessType);
        condition.put("fileType",fileType);
        List<LoanInfoAddedFilesExtend> loanInfoAddedFilesExtendList = infoAddedFilesService.queryLoanInfoFile(condition);
        renderText(JsonUtil.toJson(loanInfoAddedFilesExtendList, "id", "className,ownerName,loanProcessType,fileType,filename,fileSrcName,createDate,ownerId,classId").toString());
    }



    /**
     * 文件上传
     * @param uplodeFile
     * @param loanId
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadFiles")
    @ResponseBody
    public void uploadFiles(@RequestParam("uplodeFile") MultipartFile uplodeFile, @RequestParam("loanId") Integer loanId) throws IOException {
        Integer loginUserId = getLoginInfo().getUserId();
        String precType = loanApplyService.getApplyInfoById(loanId).getLoanProcessType();
        if(uplodeFile!= null){
            //文件名
            String fileName = uplodeFile.getOriginalFilename();
            //文件路径r
            String filePath = FileUtil.contact(fileRootPath, "Other/");
            Long size = uplodeFile.getSize();
            //写入
            infoAddedFilesService.uploadFile(fileName,filePath,uplodeFile.getInputStream(),"Other",loanId,precType,size,loginUserId);
        }
    }

    /**
     * 文件下载
     * @param request
     * @param response
     * @throws IOException
     */
    @NoLoginInterceptor
    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.valueOf(getParameter("id"));
        String download = getParameter("download");//0查看缩略图 1下载图片 2查看原图
        LoanInfoAddedFiles infoAddedFilesById = infoAddedFilesService.getInfoAddedFilesById(id);
        if (null == infoAddedFilesById) {
            return;
        }
        String path = infoAddedFilesById.getFilePath();
        String filePath = FileUtil.contact(path,infoAddedFilesById.getFileName());
        File file = new File(filePath);

        response.setCharacterEncoding("utf-8");
        if("1".equals(download)){
            response.setContentType("application/force-download");// 设置强制下载不打开
            String fileName = URLEncoder.encode(infoAddedFilesById.getFileName(), "UTF-8");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        }

        OutputStream os = response.getOutputStream();

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            byte[] buffer = new byte[2048];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis,2048);
            int i;
            while ((i = bis.read(buffer)) > 0) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (Exception e) {
            log.error("downloadFile error", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 下载前判断文件是否存在
     */
    @RequestMapping("checkFileExsit")
    @ResponseBody
    public void checkFileExsit(@RequestParam("id") Integer id){
        LoanInfoAddedFiles infoAddedFilesById = infoAddedFilesService.getInfoAddedFilesById(id);
        String path = infoAddedFilesById.getFilePath();
        String filePath = FileUtil.contact(path,infoAddedFilesById.getFileName());
        File f = new File(filePath);
        if(f.exists()){
            renderText(true, "", filePath);
        }else{
            renderText(false, "文件不存在！", null);
        }
    }

    @RequestMapping("playVideoFile")
    public void playVideoFile(@RequestParam("id") Integer id){
        LoanInfoAddedFiles infoAddedFilesById = infoAddedFilesService.getInfoAddedFilesById(id);
        String path = infoAddedFilesById.getFilePath();
        String filePath = FileUtil.contact(path,infoAddedFilesById.getFileName());
        File file = new File(filePath);
        if(file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                this.getResponse().addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(infoAddedFilesById.getFileName(),"utf-8").replace("+", "%20"));

                //this.getResponse().setContentType("movie/*"); //设置返回的文件类型
                OutputStream output = this.getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象
                BufferedInputStream bis = new BufferedInputStream(fis);//输入缓冲流
                BufferedOutputStream bos = new BufferedOutputStream(output);//输出缓冲流

                byte data[] = new byte[4096];//缓冲字节数

                int size = 0;
                size = bis.read(data);
                while (size != -1) {
                    bos.write(data, 0, size);
                    size = bis.read(data);
                }
                bis.close();
                bos.flush();//清空输出缓冲流
                bos.close();
                output.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log.error(e);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e);
            }
        }

    }

    /**
     * 批量打包下载
     * @param request
     * @param response
     * @throws IOException
     */
    @NoLoginInterceptor
    @RequestMapping("/downloadFiles")
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer loanId=Integer.valueOf(this.getParameter("loanId"));
        String ids = getParameter("ids");
        String[] split = ids.split(",");
        String fileTypes []=this.getParameter("fileType").split(",");
        List<LoanInfoAddedFilesExtend> loanMountList = new ArrayList<LoanInfoAddedFilesExtend>();
        String loanName = loanApplyService.getApplyInfoById(loanId).getLoanName();
        String zipName = this.getCheckedName(loanName);
        for(String fileType :fileTypes) {
            List<LoanInfoAddedFilesExtend> loanPartMountList = new ArrayList<LoanInfoAddedFilesExtend>();
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("fileType", fileType);
            condition.put("loanId", loanId);
            condition.put("isDel", 0);
            loanPartMountList = infoAddedFilesService.getAddFileMount(condition);
            loanMountList.addAll(loanPartMountList);
        }
        File downFile = new File(fileRootPath+"\\"+zipName+"_贷款资料.zip");
        if(!downFile.exists()){
            downFile.createNewFile();
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(downFile));
        File fileMount= infoAddedFilesService.exportFileMount(loanMountList,fileRootPath);//下载excel统计文件
        downLoadFilesUtil(fileMount,zipName+"_贷款资料",out);
        for (int i = 0; i < split.length; i++) {
            LoanInfoAddedFilesExtend infoAddedFiles = infoAddedFilesService.getInfoAddedFilesExtendById(Integer.valueOf(split[i]));
            if(null != infoAddedFiles){
                File file = new File(FileUtil.contact(infoAddedFiles.getFilePath(),infoAddedFiles.getFileName()));
                if(file.exists()) {
                    if(infoAddedFiles.getFileType().equals("Image")){
                        if(StringUtil.isNullOrEmpty(infoAddedFiles.getClassName()) && StringUtil.isNotEmpty(infoAddedFiles.getOwnerName())){
                            downLoadFilesUtil(file,zipName+"_贷款资料/图片/"+infoAddedFiles.getOwnerName()+"/"+infoAddedFiles.getClassName(),out);
                        }else if(StringUtil.isNullOrEmpty(infoAddedFiles.getClassName()) && StringUtil.isNullOrEmpty(infoAddedFiles.getOwnerName())){
                            downLoadFilesUtil(file,zipName+"_贷款资料/图片/",out);
                        }else {
                            downLoadFilesUtil(file, zipName+"_贷款资料/图片/" + infoAddedFiles.getOwnerName() + "/" + infoAddedFiles.getClassName(), out);
                        }
                    }else if(infoAddedFiles.getFileType().equals("Audio")){
                        downLoadFilesUtil(file,zipName+"_贷款资料/录音",out);
                    }else if(infoAddedFiles.getFileType().equals("Video")){
                        downLoadFilesUtil(file,zipName+"_贷款资料/视频",out);
                    }else if(infoAddedFiles.getFileType().equals("Other")){
                        downLoadFilesUtil(file,zipName+"_贷款资料/其他",out);
                    }
                }
            }
        }
        out.close();
        this.downFile(getResponse(), fileRootPath+"\\"+zipName+"_贷款资料.zip");
        if(downFile.exists()){
            downFile.delete();
        }
    }

    private void downLoadFilesUtil(File file,String dirPath ,ZipOutputStream out)throws IOException{
        try {
            FileInputStream fis = new FileInputStream(file);
            String dir=dirPath+'/'+file.getName();
            out.putNextEntry(new ZipEntry(dir));
            //设置压缩文件内的字符编码，不然会变成乱码
            out.setEncoding("GBK");
            int len;
            byte[] buffer = new byte[1024];
            // 读入需要下载的文件的内容，打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.closeEntry();
            fis.close();

        } catch (Exception e) {
            log.error("文件下载出错", e);
        }
    }
    @RequestMapping("deleteFile")
    @ResponseBody
    public void deleteFile(@RequestParam("fileName") String fileName,@RequestParam("loanId") Integer loanId){
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("loanId",loanId);
        condition.put("fileSrcName",fileName);
        condition.put("isDel",0);
        List<LoanInfoAddedFiles> loanInfoAddedFiles = infoAddedFilesService.queryInfoAddedFilesList(condition);
        //删除相同文件最新的那条
        if(loanInfoAddedFiles != null && loanInfoAddedFiles.size() > 0){
            infoAddedFilesService.deleteInfoAddedFilesById(loanInfoAddedFiles.get(loanInfoAddedFiles.size()-1).getId());
        }
    }

    //删除贷款资料
    @RequestMapping("deleteloanFile")
    @ResponseBody
    public void deleteloanFile(@RequestParam("id") Integer id){

        infoAddedFilesService.deleteInfoAddedFilesById(id);
    }

    private void downFile(HttpServletResponse response, String str) {
        try {
            String path = str;
            String zipName =str.substring(str.lastIndexOf("\\")+1);
            File file = new File(path);
            if (file.exists()) {
                InputStream ins = new FileInputStream(path);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/x-download");// 设置response内容的类型
                response.setHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + URLEncoder.encode(zipName, "UTF-8"));// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[1024];
                // 开始向网络传输文件流
                while ((bytesRead = bins.read(buffer)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件下载出错", e);
        }
    }

    /*得到文件筛选选择界面*/
    @RequestMapping("/getDownloadSelectPage")
    public String getDownloadSelectPage(){
        Integer loanId= Integer.valueOf(this.getParameter("loanId"));
        this.setAttribute("loanId",loanId);
        return "/loan/vm/loanFileManage/downloanSelect";
    }

   /* //得到预览图片界面
    @RequestMapping("/getPreviewImagePage")
    public ModelAndView getPreviewImagePage(@RequestParam("loanId") Integer loanId){
        ModelAndView mv = new ModelAndView("/loan/vm/loanFileManage/previewImage");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("loanId",loanId);
        map.put("isDel",0);
        map.put("fileType","Image");
        List<LoanInfoAddedFiles> thumbImageList = infoAddedFilesService.queryInfoAddedFilesList(map);
        setAttribute("thumbImageList",thumbImageList);
        return mv;
    }*/


    //得到预览图片界面
    @RequestMapping("/getPreviewImagePage")
    public ModelAndView getPreviewImagePage(@RequestParam("loanId")Integer loanId){
        ModelAndView mv = new ModelAndView("/loan/vm/loanFileManage/previewImage");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("loanId",loanId);
        map.put("isDel",0);
        map.put("fileType","Image");
        List<LoanInfoAddedFilesExtend> thumbImageList = infoAddedFilesService.queryLoanInfoFile(map);
        for(LoanInfoAddedFilesExtend info : thumbImageList){
            if(info.getClassName() == null){
                info.setClassName("未分类");
            }
        }
        setAttribute("thumbImageList",thumbImageList);
        setAttribute("loanId",loanId);
        return mv;
    }

    @RequestMapping("/getSearchList")
    public String searchList(@RequestParam("loanId")Integer loanId){
        Map<String,Object> map = new HashMap<String,Object>();
        String ownerId=getParameter("ownerId");
        String classId=getParameter("classId");
        map.put("loanId",loanId);
        map.put("isDel",0);
        map.put("fileType","Image");
        if(StringUtil.isNotEmpty(ownerId)){
            map.put("ownerId",ownerId);
        }
        if(StringUtil.isNotEmpty(classId)){
            map.put("classId",classId);
        }

        List<LoanInfoAddedFilesExtend> thumbImageList = infoAddedFilesService.queryLoanInfoFile(map);
        for(LoanInfoAddedFilesExtend list : thumbImageList){
            if(list.getClassName() == null){
                list.setClassName("未分类");
            }
        }
        setAttribute("thumbImageList",thumbImageList);
        return  "/loan/vm/loanFileManage/searchList";
    }
    /**
     * 获取资料分类Json数组
     */
    @RequestMapping("/getOwnerName")
    @ResponseBody
    public void getOwnerName(@RequestParam("loanId") Integer loanId){
        JSONObject resultJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<LoanInfoAddedFilesExtend> list=infoAddedFilesService.queryOwnerNameByLoanId(loanId);
        for(int i=0;i<list.size();i++){
            JSONObject jsonObject = new JSONObject();
            if(list.get(i).getOwnerId()!=null){
                jsonObject.put("ownerId",list.get(i).getOwnerId());
                jsonObject.put("ownerName",list.get(i).getOwnerName());
                jsonArray.add(jsonObject);
            }
        }
        resultJson.put("data",jsonArray);
        renderJson(true,"",resultJson);
    }

    /**
     * 根据ownerId获取资料子类Json数组
     * @param ownerId
     */
    @RequestMapping("/getClassNameByOwnerId")
    @ResponseBody
    public void getClassNameByOwnerId(@RequestParam("ownerId") Integer ownerId,@RequestParam("loanId") String loanId) {
        JSONObject resultJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        if(ownerId!=null){
            List<LoanInfoAddedFilesExtend> list=infoAddedFilesService.queryClassNameByOwnerId(ownerId,loanId);
            for(int i=0;i<list.size();i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("classId",list.get(i).getClassId());
                jsonObject.put("className",list.get(i).getClassName());
                jsonArray.add(jsonObject);
            }
        }
        resultJson.put("data",jsonArray);
        renderJson(true,"",resultJson);
    }

    /**
     * 图片流
     * @param request
     * @param response
     */
    @RequestMapping("/previewImage")
    public void previewImage(@RequestParam("fileId")String fileId, HttpServletRequest request, HttpServletResponse response){


       outImage(fileId, response, false);
    }

    private void outImage(String fileId, HttpServletResponse response, boolean isOriginalImage){
        Integer id = Integer.parseInt(fileId);
        LoanInfoAddedFiles loanInfoAddedFiles = infoAddedFilesService.getInfoAddedFilesById(id);
        String filePath;
        if (isOriginalImage)
            filePath= loanInfoAddedFiles.getFilePath() + loanInfoAddedFiles.getFileName();
        else
            filePath = loanInfoAddedFiles.getFileThumbImagePath() + loanInfoAddedFiles.getFileThumbImageName();

        FileUtil.downloadFile(filePath, response, this.getRootPath());
    }
    /**
     * 点击缩略图查看原图
     * @param fileId
     */
    @NoLoginInterceptor
    @RequestMapping("/getOriginalImage")
    public void getOriginalImage(@RequestParam("id")String fileId,HttpServletRequest request, HttpServletResponse response){
        outImage(fileId, response, true);
    }


    private String getCheckedName(String name) {
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
        Matcher matcher = pattern.matcher(name);
        name = matcher.replaceAll(""); // 将匹配到的非法字符以空替换
        return name.trim();
    }

    @RequestMapping("/showImageDiv")
    public String getvPhotoDivPage(@RequestParam("urlStr")String urlStr){
        setAttribute("urlStr",urlStr);
        return "/loan/vm/vPhotoDiv";
    }

}
