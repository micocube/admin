package com.jpa.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.core.BaseService;
import com.jpa.entity.formal.User;
import com.jpa.repository.UserRepository;
@Service
public class UserService extends BaseService<User> {
	    private UserRepository userRepository;
	    @Autowired
		public void setDAO(EntityManagerFactory emf, UserRepository userRepository){
			this.emf = emf;
			this.baseDAO = userRepository;
			this.userRepository =  userRepository;
		}
		
	    //@Transactional(rollbackFor=Exception.class,propagation = Propagation.REQUIRED)
		public Page<User> vagueSelect(String keyword,Pageable page){
//			userRepository.delete(1L);
			Page<User> saveAndFlush = userRepository.vagueSelect(keyword, page);
			return saveAndFlush;
		}
}
