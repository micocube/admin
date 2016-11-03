package com.jpa.util;
import java.util.ArrayList;  
import java.util.List;  

public class TreeNode {  

    private String id;          //要显示的子节点的ID  
    private String text;        //要显示的子节点的 Text  
    private String iconCls ;     //节点的图标  
    private String parentId;    //父节点的ID  
    private String state ;         //状态
    private List<TreeNode>  children;   //孩子节点的List  

    public TreeNode(){}  

    public TreeNode(String id, String text, String iconCls, String parentId,  

           List<TreeNode>children) {  
       super();  
       this.id= id;  
       this.text= text;  
       this.iconCls= iconCls;  
       this.parentId= parentId;  
       this.children= children;  
    }  

    public TreeNode(String text, String iconCls,  

            List<TreeNode>children) {  
        super();  
        this.text= text;  
        this.children= children;  
     }  

    public String getId() {  

       return id;  

    }  
    
    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	//添加孩子的方法  
	public void addChild(TreeNode node){  
       if(this.children == null){  
           children= new ArrayList<TreeNode>();  
           children.add(node);  
       }else{  
           children.add(node);  
       }  
    }

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", text=" + text + ", iconCls=" + iconCls + ", parentId=" + parentId
				+ ", children=" + children + "]";
	}  
    
}  