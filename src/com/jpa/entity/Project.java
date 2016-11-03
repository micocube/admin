package com.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Project {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO) 
		private Long id;
		@Column(columnDefinition="varchar(50) COMMENT '项目名'")
		private String name;
		@Column(columnDefinition="varchar(300) COMMENT '项目路径'")
		private String path;
		@Column(columnDefinition="date COMMENT '项目创建时间'")
		private Date createTime;
		
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
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		
		
		
		
}
