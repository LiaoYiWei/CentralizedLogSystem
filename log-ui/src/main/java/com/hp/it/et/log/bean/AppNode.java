package com.hp.it.et.log.bean;

import java.util.ArrayList;
import java.util.List;

public class AppNode {
	// [{"id":"1","text":"aa",leaf:true,checked:true}]

	private String id;
	private String text;
	private String cls;
	private String nodeType;
	private boolean expanded;
	private boolean leaf;
	private boolean checked;

	public String getNodeType() {
		return nodeType;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	private List children = new ArrayList();

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
