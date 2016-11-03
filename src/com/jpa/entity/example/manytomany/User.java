package com.jpa.entity.example.manytomany;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * 
 * @author mico
 * �˴�@Entityע�ⱻע�ͣ�ʵ��ʹ��ʱ��ҪΪÿ��ʵ��д��Entityע��[�м�]
 *
 */
//@Entity
public class User {
	
    
	private Long id; // id
    private String name; // name
    private String pwd; // pwd
    private Integer age; // age
    private Date creatTime; // creatTime
    private Integer level;
    private List<Role> roles;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role", 
            joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "id") }, 
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "role_id") })
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}