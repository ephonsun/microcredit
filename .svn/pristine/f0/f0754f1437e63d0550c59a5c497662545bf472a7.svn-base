/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :Ftp帮助类
 * Author     :QianJie
 * Create Date:Nov 21, 2012
 */
package banger.framework.util;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wumh
 * @version $Id: FtpHelper.java,v 0.1 Jan 22, 2013 15:36:30 PM wumh Exp $
 */
public class FtpHelper {
    private static final Logger logger = LoggerFactory.getLogger(FtpHelper.class);

    private static FTPClient client = null;

    /**
     * 建立ftp连接
     * @param ftpHost
     * @param ftpPort
     * @param loginName
     * @param loginPwd
     * @param workPath
     * @throws FTPException 
     * @throws FTPIllegalReplyException 
     * @throws IOException 
     * @throws IllegalStateException 
     */
    public static void createConnect(String ftpHost, int ftpPort, String loginName, String loginPwd,
                              String workPath) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException,SocketTimeoutException{
        client = new FTPClient();
        client.connect(ftpHost, ftpPort);
        client.setCharset("UTF-8");
        client.login(loginName, loginPwd);
        client.changeDirectory(workPath);//切换目录
    }

    /**
     * 安全断开ftp连接
     */
    public static void disConnect() {
        if (client != null) {
            try {
                client.disconnect(true);
            } catch (Exception e) {
                logger.error("安全断开ftp连接 error:"+e.getMessage(),e);
            }
        }
    }

    /**
     * 上传本地文件到ftp
     * @param localFile 本地文件绝对路径
     * @return 
     */
    public static Map<String, String> uploadFile(String localFile) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            client.upload(new java.io.File(localFile));
            map.put("resultCode", "Success");
            map.put("resultMsg", "成功");
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否已存在相同文件
     * @param fileName 文件名
     * @return
     */
    public static boolean isHasExistenceFile(String fileName) {
        try {
            boolean result = false;
            FTPFile[] ftpFiles = client.list();
            for (FTPFile file : ftpFiles) {
                if (FTPFile.TYPE_FILE == file.getType()) {
                    if (file.getName().equals(fileName)) {
                        result = true;
                        break;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 根据指定目录获取指定目录下文件夹
     * @param path 当前目录
     * @return
     */
    public static ArrayList<String> getDirsByPath(String path) {
        try {
            ArrayList<String> dirList = new ArrayList<String>();
            client.changeDirectory(path); //client.changeDirectory("//a");//切换目录
            FTPFile[] ftpFiles = client.list();
            for (FTPFile file : ftpFiles) {
                if (FTPFile.TYPE_DIRECTORY == file.getType() && !(file.getName().equalsIgnoreCase(".")||file.getName().equalsIgnoreCase(".."))) {
                    dirList.add(file.getName());
                }
            }
            return dirList;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    /**
     * 根据指定目录获取指定目录下文件
     * @param path 当前目录
     * @return
     */
    public static ArrayList<String> getFilesByPath(String path) {
        try {
            ArrayList<String> dirList = new ArrayList<String>();
            client.changeDirectory(path); //client.changeDirectory("//a");//切换目录
            FTPFile[] ftpFiles = client.list();
            for (FTPFile file : ftpFiles) {
                if (FTPFile.TYPE_FILE == file.getType()) {
                    dirList.add(file.getName());
                }
            }
            return dirList;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 切换目录
     * @param path
     * @throws IllegalStateException
     * @throws IOException
     * @throws FTPIllegalReplyException
     * @throws FTPException
     */
    public static boolean changeWorkingDirectory(String path){
    	try{
    		client.changeDirectory(path);
    		return true;
    	}catch (Exception e) {
    		return false;
    	}
    	
    }
    
    /**
     * 下载文件到本地
     * @param localFile
     * @param remoteFileName
     * @return
     */
    public static boolean downLoadFile(String localFileFullName,String remoteFileName) {
    	try {
    		String fileName = remoteFileName.substring(remoteFileName.lastIndexOf("\\")+1);
    		fileName = fileName.substring(fileName.lastIndexOf("/")+1);
    		String savePath = localFileFullName + "\\";
    		File file = new File(savePath);
			if (!file.exists()) {// 文件不存在则创建
				file.mkdirs();
			}
        	File localFile = new File(savePath+fileName);
        	client.setType(FTPClient.TYPE_BINARY);
            client.setCharset("GBK");
        	client.download(remoteFileName, localFile);
    		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
