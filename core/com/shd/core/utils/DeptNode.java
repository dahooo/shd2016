package com.shd.core.utils;

import java.util.List;

public class DeptNode {

	
	private String title;
	private String key;
	private List<DeptNode> children;
	private boolean select;
	private boolean activate;
	private boolean expand;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<DeptNode> getChildren() {
		return children;
	}
	public void setChildren(List<DeptNode> children) {
		this.children = children;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public boolean isActivate() {
		return activate;
	}
	public void setActivate(boolean activate) {
		this.activate = activate;
	}
	public boolean isExpand() {
		return expand;
	}
	public void setExpand(boolean expand) {
		this.expand = expand;
	}
	

}
