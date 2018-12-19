/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :yuanme
 * Create Date:2012-4-11
 */
package banger.framework.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanme
 * @version SystemUtil.java,v 0.1 2012-4-11 下午2:18:30
 */
public class SystemUtil {
	
    public static boolean isWindows() {
        boolean result = false;
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            result = true;
        }
        return result;
    }
    
    /**
     * 得到内存信息
     * @return
     */
    public static Map<String,Long> getMemoryInfo(){
    	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    	MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
    	Map<String,Long> map = new HashMap<String,Long>();
    	map.put("maxMemory", memoryUsage.getMax());
    	map.put("usedMemory", memoryUsage.getUsed());
    	map.put("initMemory", memoryUsage.getInit());
    	return map;
    }
    
    public static void main(String[] args){
    	List<MemoryPoolMXBean> mpools = ManagementFactory.getMemoryPoolMXBeans();
    	int size = mpools.size();
    	for(int i=0;i<size;i++){
    		MemoryPoolMXBean mp = mpools.get(i);
    		String name = mp.getName();
    		float usedMemory = mp.getUsage().getUsed()/1024;
    		float totalMemory = mp.getUsage().getMax()/1024;
    		System.out.println(name+"	"+usedMemory+"	"+totalMemory);
    	}
    	
    }
    
}
