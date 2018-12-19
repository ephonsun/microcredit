package banger.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import banger.dao.intf.IFuncDao;
import banger.domain.permission.PmsFunc;
import banger.service.intf.IFuncTreeService;
@Service
public class FuncTreeService implements IFuncTreeService {
	
	@Resource
	private IFuncDao funcDao;
	
	public void setFuncDao(IFuncDao funcDao) {
		this.funcDao = funcDao;
	}

	/**
	 * 得到角色功能树
	 * @return
	 */
	public String getRoleFuncTree(List<String> funcIds){
		Set<String> funcSet = new HashSet<String>();
		if(funcIds!=null && funcIds.size()>0){
			for(String funcId : funcIds)funcSet.add(funcId);
		}
		List<PmsFunc> funcs = this.funcDao.getAllFunc();
		Map<String,PmsFunc> funcMap = new HashMap<String,PmsFunc>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(int i=0;i<funcs.size();i++){
			PmsFunc func = funcs.get(i);
			funcMap.put(func.getFuncId(), func);
			if(!funcMap.containsKey(func.getFuncParentId()))rootCount++;

			JSONObject jo = new JSONObject();
			jo.put("id", func.getFuncId());
			jo.put("pId", func.getFuncParentId());
			jo.put("name", func.getFuncName());
			if(funcSet.contains(func.getFuncId()))jo.put("checked", true);
			jo.put("icon", "../core/imgs/icon/agency.gif");
			ja.add(jo);
		}

		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}
	
	/**
	 * 得到角色功能树
	 * @return
	 */
	public String getRoleFuncTree(){
		return this.getRoleFuncTree(null);
	}
	
	/**
	 * 得到不可编辑角色功能树
	 * @return
	 */
	public String getRoleDisFuncTree(List<String> funcIds){
		Set<String> funcSet = new HashSet<String>();
		if(funcIds!=null && funcIds.size()>0){
			for(String funcId : funcIds)funcSet.add(funcId);
		}
		List<PmsFunc> funcs = this.funcDao.getAllFunc();
		Map<String,PmsFunc> funcMap = new HashMap<String,PmsFunc>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(int i=0;i<funcs.size();i++){
			PmsFunc func = funcs.get(i);
			funcMap.put(func.getFuncId(), func);
			if(!funcMap.containsKey(func.getFuncParentId()))rootCount++;
			
			JSONObject jo = new JSONObject();
			jo.put("id", func.getFuncId());
			jo.put("pId", func.getFuncParentId());
			jo.put("name", func.getFuncName());
			if(funcSet.contains(func.getFuncId()))jo.put("checked", true);
			jo.put("icon", "../core/imgs/icon/agency.gif");
			jo.put("chkDisabled", true);
			ja.add(jo);
		}
		
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}
	
}
