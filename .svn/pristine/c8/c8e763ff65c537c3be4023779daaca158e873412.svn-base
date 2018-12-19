package banger.controller;

import banger.common.BaseController;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.taskdefs.Sleep;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RequestMapping("/transitionPictures")
@Controller
public class TransitionPicturesController extends BaseController{

    private String startImagePath = "/apk";
    /**
     * 跳转APP过渡图片页面
     * @return
     */
    @RequestMapping("getTransitionPicturesPage")
    public String getTransitionPicturesPage(){
        return "/system/vm/transitionPicturePage";
    }

    /**
     * 展示
     * @param request
     * @param response
     */
    @RequestMapping("/showStartImage")
    public void downloadStartImage(HttpServletRequest request, HttpServletResponse response){
        String filename = this.getParameter("filename");
        String path = this.getRootPath();
        String imagePath = FileUtil.contact(path, startImagePath);
        downloadImageFile(imagePath,filename,response);
    }

    /**
     * 更换或者上传照片
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public void uploadImg(@RequestParam MultipartFile  file) throws Exception {
        if(null != file) {
            String path = FileUtil.contact(getRootPath(),"apk");
            File fp = new File(path);
            fp.mkdirs();
            String filename = FileUtil.contact(path,"start.jpg");
            File f = new File(filename);
            if (f.exists()) {
                File destFile = new File(path+Math.random()*10000+".jpg");
                backups(f,destFile);
            }

            FileUtils.copyToFile(file.getInputStream(),f);
        }
    }

    /**
     * 备份
     */
    private void backups(File srcFile,File destFile){
        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadImageFile(String path,String filename,HttpServletResponse response){
        if(StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(filename)){
            String imageFilename = FileUtil.contact(path, filename);
            File file = new File(imageFilename);
            if (file.exists()) {
                FileInputStream stream = null;
                OutputStream output = null;
                try{
                    long fileSize = file.length();
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("Content-Type", "image/jpeg");
                    String imageName = URLEncoder.encode(filename,"UTF-8");
                    response.setDateHeader("expries", -1);
                    response.setHeader("Cache-Control", "no-cache");
                    response.setHeader("Pragma", "no-cache");
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
                    log.error("展示图片异常 error:"+e.getMessage(),e);
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
}
