package com.jpa.entity.example.onetomany.oneway;

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
 *  作者
 *  实体Author：作者。
 *  实体Book：作者写的书。
 *  Author和Book是一对多的关系。
 *  在JPA中，用@OneToMany来标识一对多的关系。实现一对多的单向关联，只需在代表一的实体(Author)中使用@OneToMany映射标注就可以了，代表多的实体不需要使用任何映射标注。
 *  有两种方式实现一对多的单向关联。一种是在只使用@OneToMany来标识，这种方式是通过一张第三方表来保存关系。还有一种是使用@OneToMany和@JoinColumn来标注，这种方式是在多的一方(Book)的表中增加一个外键列来保存关系。
 * @author Luxh
 */

/**
 * 
 * @author mico
 * 此处@Entity注解被注释，实际使用时需要为每个实体写上Entity注解
 *
 */
//@Entity
@Table(name="author")
public class Author {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**作者的名字*/
    @Column(length=32)
    private String name;
    
    /**作者写的书*/
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    private Set<Book> books = new HashSet<Book>();
    
    
    
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


    public Set<Book> getBooks() {
        return books;
    }


    public void setBooks(Set<Book> books) {
        this.books = books;
    }


    
}