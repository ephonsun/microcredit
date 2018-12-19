package banger.domain.enumerate;

import banger.framework.collection.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款监控状态
 * @author zhusw
 *
 */
public enum LoanMonitorState {
	
	MONITOR("Monitor", "监控", "未完成"),
	MONITOR_COMPLETE("MonitorComplete", "监控完成", "已完成"),

	 ;
	 
	public final String state; // 贷款监控状态
	public final String stateName; // 贷款监控状态名称
	public final String optionName;	//下拉文本
	 
	 LoanMonitorState(String state,String stateName, String optionName){
		 this.state = state;
		 this.stateName = stateName;
		 this.optionName = optionName;
	 }

	public boolean equalState(String state){
		return this.state.equalsIgnoreCase(state);
	}
	 
	 public static LoanMonitorState valueOfState(String state){
		 for (LoanMonitorState monitorState : LoanMonitorState.values()) {
			 if (monitorState.state.equals(state)) {
				 return monitorState;
			 }
		 }
		 return null;
	 }

	 public static String getNameByState(String state){
		 for (LoanMonitorState monitorState : LoanMonitorState.values()) {
			 if (monitorState.state.equals(state)) {
				 return monitorState.stateName;
			 }
		 }
		 return "";
	 }

	public static String getOptionNameByState(String state){
		for (LoanMonitorState monitorState : LoanMonitorState.values()) {
			if (monitorState.state.equals(state)) {
				return monitorState.optionName;
			}
		}
		return "";
	}

	public static List<OptionItem> getOptionItems() {
		List<OptionItem> items = new ArrayList<OptionItem>();
		for (LoanMonitorState monitorState : LoanMonitorState.values()) {
			OptionItem item = new OptionItem(monitorState.state,monitorState.optionName);
			items.add(item);
		}
		return items;
	}

	public static String getStateByOptionName(String optionName){
		for (LoanMonitorState monitorState : LoanMonitorState.values()) {
			if(monitorState.optionName.equals(optionName)){
				return monitorState.state;
			}
		}
		return null;
	}
}
