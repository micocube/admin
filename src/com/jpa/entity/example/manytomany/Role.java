package com.jpa.entity.example.manytomany;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
/**
 * 
 * @author mico
 * 此处@Entity注解被注释，实际使用时需要为每个实体写上Entity注解
 *
 */
//@Entity
public class Role  {
	
	private Long role_id;
	private String role_name;
	private List<User> users;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	//@ManyToMany注释表示Student是多对多关系的一边，mappedBy属性定义了Student为双向关系的维护端
    //Teacher表是关系的维护者，owner side，有主导权，它有个外键指向Student表。 
    @ManyToMany(mappedBy = "roles")
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
