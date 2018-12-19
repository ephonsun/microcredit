/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :数据服务端校验结果类
 * Author     :zhushengwei
 * Create Date:2013-3-28
 */
package banger.common.valid;

import java.util.ArrayList;
import java.util.List;

public class ValidResult {
	private List<ValidResultItem> items;				//错误结果集合

	public List<ValidResultItem> getItems() {
		return items;
	}

	public void setItems(List<ValidResultItem> items) {
		this.items = items;
	}
	
	public ValidResult(){
		this.items=new ArrayList<ValidResultItem>();
	}
	
	/**
	 * 添加错误信息
	 * @param item
	 */
	public void addItem(ValidResultItem item){
		this.items.add(item);
	}
}
