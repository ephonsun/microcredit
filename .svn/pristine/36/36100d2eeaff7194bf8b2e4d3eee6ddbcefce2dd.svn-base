package banger.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import banger.service.intf.IAppProductService;

/**
 * 产品接口
 * @author taowj
 *
 */
@Service
public class AppProductService implements IAppProductService{

	@Override
	public void downloadProductFile(File file, OutputStream os, long skipLen) throws Exception{
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			byte[] buffer = new byte[2048];
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			if(skipLen > 0)
				bis.skip(skipLen);
			int i ;
			while ((i = bis.read(buffer)) > 0) {
				os.write(buffer, 0, i);
			}
			os.flush();
		} finally {
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {}
			}
		}
	}
	
	
}
