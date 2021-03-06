package util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TreeNode implements Serializable {
	
	private static final long serialVersionUID = 7592482157363279522L;
	
	private String id; // ID
	private String label; // 节点显示
	private Boolean leaf; // 是否叶子
	private String cls; // 图标
	private String parentId;
	private boolean open;
	private List<TreeNode> children;// 树节点子节点列表
	private Map<String, Object> extra;// 额外的属性列表
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Boolean getLeaf() {
		return leaf;
	}
	
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	public String getCls() {
		return cls;
	}
	
	public void setCls(String cls) {
		this.cls = cls;
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public Map<String, Object> getExtra() {
		return extra;
	}
	
	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
}
