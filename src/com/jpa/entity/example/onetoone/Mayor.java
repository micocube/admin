package com.jpa.entity.example.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * 
 * @author mico
 * 此处@Entity注解被注释，实际使用时需要为每个实体写上Entity注解
 *
 */
//@Entity
@Table(name="mayor")
public class Mayor {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**市长姓名*/
    @Column(length=32)
    private String name;
    
    /**管理的城市*/
    @OneToOne(mappedBy="mayor",cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    //mappedBy="mayor"表明Mayor是关系被维护端,"mayor"是City实体中的属性
    private City city;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}