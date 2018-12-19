/*
 * banger Inc.
 * ToDo       :This is Class Description...
 * Author     :Administrator
 * Create Date:2014-3-8
 */
package banger.domain.permission;

/**
 * @author Administrator
 * @version $Id: PmsDept_Query.java,v 0.1 2014-3-8 下午4:46:25 Administrator Exp $
 */
public class PmsDept_Query extends PmsDept{
	
	private static final long serialVersionUID = 5114587899986032984L;
	private String itemIds;
	private String itemValues;
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public String getItemValues() {
		return itemValues;
	}
	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}

}
