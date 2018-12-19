/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :机构用户树节点的包装类
 * Author     :zsw
 * Create Date:2013-03-29
 */
package banger.domain.trees;

import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsUser;
import banger.framework.tree.IDataTreeNode;
import banger.framework.tree.TreeNode;

public class DeptUserTreeNode extends TreeNode<DeptUserTreeNode> implements IDataTreeNode<DeptUserTreeNode> {
	private PmsDept dept;					//机构
	private PmsUser user;					//人员
	public int deleteUserCount=0;				//删除用户数
	public int enableUserCount=0;				//启用用户数
	public int disableUserCount=0;			//禁用用户数
	public int nonManagerUserCount=0;			//包含客户经理用户数
	public String[] roleIds=new String[0];
	
	public DeptUserTreeNode(PmsDept dept){
		this.dept = dept;
		this.user = null;
	}
	
	public DeptUserTreeNode(PmsUser user){
		this.dept = null;
		this.user = user;
	}
	
	public boolean isDept(){
		return this.dept!=null;
	}
	
	public PmsUser getUser(){
		return this.user;
	}
	
	public PmsDept getDept(){
		return this.dept;
	}
	
	/**
	 * 重写添加子节点函数,计算用户数,为了过滤没有用户数的机构
	 */
	@Override
	public DeptUserTreeNode add(DeptUserTreeNode node){
	    super.add(node);
		if(node.getUser()!=null){
			DeptUserTreeNode p = node;
			if(node.getUser().getUserIsdel()==1){
				while((p = p.getParent())!=null)p.deleteUserCount++;
			}
			/*else if(!node.hasManagerRole()){
				while((p = p.getParent())!=null)p.nonManagerUserCount++;
			}
			*/
			else {
				while((p = p.getParent())!=null){
					p.enableUserCount++;
					System.out.println("p.enableUserCount : " +p.enableUserCount + ":" + p.getName());
				}
			}
		}
		return node;
	}
	
	public boolean hasManagerRole(){
		for(String roleId : this.roleIds){
			if(roleId.equals("4"))return true;
		}
		return false;
	}
	
	/**
	 * 得到树节点的ID
	 * @return
	 */
	public Object getId(){
		return (this.isDept())?this.dept.getDeptId():this.user.getUserId();
	}
	
	/**
	 * 得到树父节点的ID
	 * @return
	 */
	public Object getParentId(){
		if(this.getParent()!=null){
			return this.getParent().getId();
		}
		else{
			return (this.isDept())?this.dept.getDeptParentId():this.user.getUserDeptId();
		}
	}
	
	/**
	 * 得到树节点的名称
	 * @return
	 */
	public String getName(){
		return (this.isDept())?this.dept.getDeptName():this.user.getUserName();
	}

    @Override
    public boolean getChecked() {
        return false;
    }

    @Override
    public String[] getParamNames() {
        return new String[0];
    }

    @Override
    public Object getParamValue(String name) {
        return null;
    }
}
