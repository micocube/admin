package com.jpa.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.core.BaseDAO;
import com.jpa.entity.formal.User;

public interface UserRepository extends BaseDAO<User, Long> {
    @Query("SELECT u FROM User u WHERE UPPER(u.name) LIKE CONCAT('%',:keyword,'%') ORDER BY u.name")
    public Page<User> vagueSelect(@Param(value = "keyword") String keyword,Pageable pageable);

}