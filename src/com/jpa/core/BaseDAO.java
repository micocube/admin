package com.jpa.core;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BaseDAO<E , ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E>{

}
