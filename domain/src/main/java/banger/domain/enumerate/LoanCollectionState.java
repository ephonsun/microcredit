package banger.domain.enumerate;

import banger.framework.collection.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款催收状态
 * @author zhusw
 *
 */
public enum LoanCollectionState {
	
	 COLLECTION("Collection", "未提醒"),
	 COLLECTION_COMPLETE("CollectionComplete", "已提醒"),
	 ;
	 
	 public final String state; // 贷款监控状态
	 public final String stateName; // 贷款监控状态名称
	 
	 LoanCollectionState(String state,String stateName){
		 this.state = state;
		 this.stateName = stateName;
	 }

	 public boolean equalState(String state){
	 	return this.state.equalsIgnoreCase(state);
	 }
	 
	 public static LoanCollectionState valueOfState(String state){
		 for (LoanCollectionState collectionState : LoanCollectionState.values()) {
			 if (collectionState.state.equals(state))
				 return collectionState;
		 }
		 return null;
	 }

	 public static String getNameByState(String state){
		 for (LoanCollectionState collectionState : LoanCollectionState.values()) {
			 if (collectionState.state.equals(state))
			 	return collectionState.stateName;
		 }
		 return "";
	 }

	public static List<OptionItem> getOptionItems() {
		List<OptionItem> items = new ArrayList<OptionItem>();
		for (LoanCollectionState collectionState : LoanCollectionState.values()) {
			OptionItem item = new OptionItem(collectionState.state, collectionState.stateName);
			items.add(item);
		}
		return items;
	}
}
