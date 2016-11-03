package com.jpa.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.entity.Project;
import com.jpa.core.BaseDAO;

public interface ProjectRepository extends BaseDAO<Project, Long> {
    @Query("SELECT u FROM Project u WHERE UPPER(u.name) LIKE CONCAT('%',:keyword,'%') ORDER BY u.name")
    public Page<Project> vagueSelect(@Param(value = "keyword") String keyword,Pageable pageable);

}