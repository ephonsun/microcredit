package banger.setup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;

public class MakeUpgradeFile {
	
	public static void main(String[] args){
		
		String path = (args.length>0)?args[0]:getDefaultPath();
		System.out.print(path);
		new File(path+"/error.vm").delete();
		new File(path+"/index.vm").delete();
		new File(path+"/timeout.vm").delete();
		deleteDir(new File(path+"/META-INF"));
		deleteDir(new File(path+"/temp-file"));
		deleteDir(new File(path+"/WEB-INF/lib/x64/"));
		deleteDir(new File(path+"/WEB-INF/lib/x86/"));
		deleteJar(path+"/WEB-INF/lib/");
		deleteClass(path+"/WEB-INF/classes/");
		new File(path+"/WEB-INF/toolbox.xml").delete();
		new File(path+"/WEB-INF/web.xml").delete();
		new File(path+"/WEB-INF/xfire-servlet.xml").delete();
		String targetPath = getPrePath(path);
		doZip(targetPath+"smartphone-Webapp.zip",path);
	}
	
	private static void deleteJar(String path){
		File file = new File(path);
		if(file.isDirectory()){
			for(File f : file.listFiles()){
				if(f.getName().length()>6 && "Banger".equals(f.getName().substring(0,6))){
					
				}else{
					f.delete();
				}
			}
		}
	}
	
	private static void deleteClass(String path){
		File file = new File(path);
		if(file.isDirectory()){
			for(File f : file.listFiles()){
				if(!f.isDirectory() && !"struts.xml".equals(f.getName())){
					f.delete();
				}
			}
		}
	}
	
	private static String getDefaultPath(){
		String path = new File("").getAbsolutePath().replaceAll("\\\\", "/");
		int n = path.lastIndexOf("setup");
		if(n>-1){
			path = path.substring(0,n-1);
		}
		return path+"/web/target/smartphone-Webapp-1.0/";
	}
	
	private static String getPrePath(String path){
		int n = path.substring(0, path.length()-1).lastIndexOf("/");
		if(n>-1){
			return path.substring(0,n+1);
		}else{
			return path;
		}
	}
	
	/**
     * 压缩文件
     * @param filename
     * @param path
     */
    public static void doZip(String filename,String path){
    	File zipFile = new File(path);
    	try{
    		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
    		handleDir(null,zipFile,zos);
    		zos.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private static void handleDir(String path,File dir,ZipOutputStream out) throws Exception {
    	File[] files = dir.listFiles();
    	if(files.length == 0){
    		out.putNextEntry(new ZipEntry(dir.toString()+"/"));
    		out.closeEntry();
    	}else{
    		for(File f : files){
    			if(f.isDirectory()){
    				if(path!=null){
    					if(path.length()>0){
    						handleDir(path+"/"+dir.getName(),f,out);
    					}else{
    						handleDir(dir.getName(),f,out);
    					}
    				}else{
    					handleDir("",f,out);
    				}
    			}else{
    				FileInputStream fis = new FileInputStream(f);
    				String name = path+"/"+dir.getName();
    				out.putNextEntry(new ZipEntry(name+"/"+f.getName()));
    				
    				int n = 0;
    				byte[] bytes = new byte[8092];
    				while((n=fis.read(bytes))>0){
    					out.write(bytes,0,n);
    				}
    				fis.close();
    				out.closeEntry();
    			}
    		}
    	}
    }
    
    private static boolean deleteDir(File dir){
    	if(dir.isDirectory()){
    		String[] children = dir.list();
    		
    		for(int i=0;i<children.length;i++){
    			boolean success = deleteDir(new File(dir,children[i]));
    			if(!success){
        			return false;
        		}
    		}
    	}
    	
    	return dir.delete();
    }
	
}
