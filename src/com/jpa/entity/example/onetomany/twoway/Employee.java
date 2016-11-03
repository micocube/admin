package com.jpa.entity.example.onetomany.twoway;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author mico
 * 此处@Entity注解被注释，实际使用时需要为每个实体写上Entity注解
 *
 */
//@Entity
@Table(name="employee")
public class Employee {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**雇员姓名*/
    @Column(name="name")
    private String name;
    
    /**所属公司*/
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name="company_id")//设置在employee表中的关联字段(外键)
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

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
}