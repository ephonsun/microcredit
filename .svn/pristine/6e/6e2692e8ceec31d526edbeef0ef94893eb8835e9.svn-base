package banger.domain.enumerate;

import banger.framework.collection.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款状态
 * @author
 *
 */
public enum AfterLoanTypeEnum {

	NORMAL("1","正常"),
	FOLLOW("2","关注"),
	SECONDARY("3","次级"),
	SUSPICIOUS("4","可疑"),
	LOSS("5","损失"),
	;

	public final String code;
	public final String name;

	AfterLoanTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;

	}

	public static String getTypeNameByCode(String code){
		for(AfterLoanTypeEnum loanType : AfterLoanTypeEnum.values()){
			if(loanType.code.equals(code)){
				return loanType.name;
			}
		}
		return null;
	}

    public static List<OptionItem> getOptionItems() {
		List<OptionItem> items = new ArrayList<OptionItem>();
		for (AfterLoanTypeEnum state : AfterLoanTypeEnum.values()) {
			OptionItem item = new OptionItem(state.code,state.name);
			items.add(item);
		}
		return items;
    }
}
