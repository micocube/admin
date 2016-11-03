package com.jpa.entity.example.onetomany.twoway;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 公司
 * 实体Company：公司。
 * 实体Employee：雇员。
 * Company和Employee是一对多关系。那么在JPA中，如何表示一对多的双向关联呢？
 * JPA使用@OneToMany和@ManyToOne来标识一对多的双向关联。一端(Company)使用@OneToMany,多端(Employee)使用@ManyToOne。
 * 在JPA规范中，一对多的双向关系由多端(Employee)来维护。就是说多端(Employee)为关系维护端，负责关系的增删改查。一端(Company)则为关系被维护端，不能维护关系。
 * 一端(Company)使用@OneToMany注释的mappedBy="company"属性表明Company是关系被维护端。
 * 多端(Employee)使用@ManyToOne和@JoinColumn来注释属性company,@ManyToOne表明Employee是多端，@JoinColumn设置在employee表中的关联字段(外键)。
 * @author Luxh
 */
/**
 * 
 * @author mico
 * 此处@Entity注解被注释，实际使用时需要为每个实体写上Entity注解
 *
 */
//@Entity
@Table(name="company")
public class Company {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**公司名称*/
    @Column(name="name",length=32)
    private String name;
    
    /**拥有的员工*/
    @OneToMany(mappedBy="company",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    //拥有mappedBy注解的实体类为关系被维护端
    //mappedBy="company"中的company是Employee中的company属性
    private Set<Employee> employees = new HashSet<Employee>();
    

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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
     
}