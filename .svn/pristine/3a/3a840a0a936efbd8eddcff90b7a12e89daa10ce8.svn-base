package banger.util;

import banger.framework.util.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/7.
 */
public class MultipartImageUtil {

    public static File createImageFile(String path,MultipartFile multipartFile) throws IOException {
        FileItem fileItem = ((CommonsMultipartFile)multipartFile).getFileItem();
        String tempPath = FileUtil.contact(path,"uploadTempImages");
        if(!new File(tempPath).exists())new File(tempPath).mkdirs();
        String newImageFilename = FileUtil.contact(tempPath,new Date().getTime()+"_"+multipartFile.getOriginalFilename());
        File file = new File(newImageFilename);
        multipartFile.transferTo(file);
        return file;
    }

}
