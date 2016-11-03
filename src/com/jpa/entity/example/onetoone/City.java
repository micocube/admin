package com.jpa.entity.example.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



/**
 * 
 * @author mico
 * 此处@Entity注解被注释，实际使用时需要为每个实体写上Entity注解
 *
 */
//@Entity
@Table(name="city")
public class City {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**城市名称*/
    @Column(length=32)
    private String name;
    
    /**城市的市长*/
    @OneToOne(cascade=CascadeType.ALL)//City是关系的维护端
    @JoinColumn(name="mayor_id")//指定外键的名称
    @Fetch(FetchMode.JOIN)//会使用left join查询,只产生一条语句
    //@Fetch(FetchMode.JOIN) 会使用left join查询，只产生一条sql语句(默认使用)
    //@Fetch(FetchMode.SELECT)   会产生N+1条sql语句
    //@Fetch(FetchMode.SUBSELECT)  产生两条sql语句 第二条语句使用id in (…..)查询出所有关联的数据
    private Mayor mayor;
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

    public Mayor getMayor() {
        return mayor;
    }

    public void setMayor(Mayor mayor) {
        this.mayor = mayor;
    }
    
    
    
}