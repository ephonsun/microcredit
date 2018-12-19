package banger.framework.component.progress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banger.framework.util.StringUtil;

/**
 * 进度条管理类
 */
public class ProgressBarManager implements IProgressBarManager {
	private Map<String,ProgressBar> barMap;
	private Map<String,List<ProgressBar>> groupMap;
	
	public ProgressBarManager(){
		this.barMap = new ConcurrentHashMap<String,ProgressBar>();
		this.groupMap = new ConcurrentHashMap<String,List<ProgressBar>>();
	}
	
	public boolean contains(String id){
		return this.barMap.containsKey(id);
	}
	
	/**
	 * 添加一个新的进度条
	 * @param id
	 * @param group
	 * @return
	 */
	public ProgressBar add(String id,String group){
		if(!this.contains(id)){
			ProgressBar bar = new ProgressBar();
			bar.setId(id);
			if(!StringUtil.isNullOrEmpty(group)){
				bar.setGroup(group);
				if(!this.groupMap.containsKey(group))this.groupMap.put(group,new ArrayList<ProgressBar>());
				this.groupMap.get(group).add(bar);
			}
			this.barMap.put(id,bar);
			return bar;
		}else{
			return this.barMap.get(id);
		}
	}
	
	/**
	 * 得到进度条
	 * @param id
	 * @return
	 */
	public ProgressBar getProgressBarById(String id){
		return this.barMap.get(id);
	}
	
	/**
	 * 通过组得到进度条
	 * @param group
	 * @return
	 */
	public List<ProgressBar> getProgressBarByGroup(String group){
		return this.getProgressBarByGroup(group);
	}
	
	/**
	 * 移除进度条
	 * @param id
	 */
	public void remove(String id){
		if(this.contains(id)){
			ProgressBar bar = this.barMap.get(id);
			String group = this.getProgressBarById(id).getGroup();
			if(!StringUtil.isNullOrEmpty(group)){
				List<ProgressBar> bars = this.getProgressBarByGroup(group);
				if(bars!=null && bars.size()>0)bars.remove(bar);
			}
			this.barMap.remove(id);
		}
	}
	
	/**
	 * 移除进度条组
	 * @param group
	 */
	public void removeGroup(String group){
		List<ProgressBar> bars = this.getProgressBarByGroup(group);
		if(bars!=null && bars.size()>0){
			for(ProgressBar bar : bars){
				this.barMap.remove(bar.getId());
			}
			this.groupMap.remove(group);
		}
	}
	
}
