package banger.framework.tree;

import banger.framework.tree.ITreeNode;

public interface IDataTreeNode<T> extends ITreeNode<T> {
	
	Object getId();
	
	Object getParentId();
	
	String getName();
	
	boolean getChecked();
	
	String[] getParamNames();
	
	Object getParamValue(String name);
}
