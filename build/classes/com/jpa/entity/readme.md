1.所有注解写在属性上面，不要写在方法上面

2.为所有列写注释@Column(columnDefinition="varchar(200) COMMENT '字段注释'") 此处的COMMENT 后面一定要带'' ，否则表建不出来