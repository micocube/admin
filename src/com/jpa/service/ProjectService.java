package com.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManagerFactory;

import com.jpa.core.BaseService;
import com.jpa.entity.Project;
import com.jpa.repository.ProjectRepository;
@Service
public class ProjectService extends BaseService<Project> {
	    private ProjectRepository projectRepository;
	    @Autowired
	    public void setDAO(EntityManagerFactory emf, ProjectRepository projectRepository){
			this.emf = emf;
			this.baseDAO =  projectRepository;
			this.projectRepository =  projectRepository;
		}
	    
		public Page<Project> vagueSelect(String keyword,Pageable page){
			Page<Project> saveAndFlush = projectRepository.vagueSelect(keyword, page);
			return saveAndFlush;
		}
}
