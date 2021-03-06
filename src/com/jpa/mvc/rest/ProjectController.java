package com.jpa.mvc.rest;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpa.entity.Project;
import com.jpa.service.ProjectService;
import com.jpa.util.PageableUtil;
import com.jpa.util.ResponseUtil;

/**
 * Project manager
 * 
 * @author mico
 */
@Controller
@RequestMapping("/project")
public class ProjectController {
    @Resource ProjectService projectService;
    
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
		    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");  
		    dateFormat.setLenient(false);  
		    binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));   //是否允许空值
    }

   @RequestMapping("/save")
    public void Save(HttpServletResponse response,Project project) { 
    	projectService.save(project);
    	ResponseUtil.fastResponse(response, "Saved successfully!");
    }
    
    @RequestMapping(value="/vagueSelect",method=RequestMethod.GET)
    public void vagueSelect(HttpServletResponse response,String keyword,Integer page,Integer rows) throws Exception{
    	Map<String,Object> map = new HashMap<String,Object>();
    	if(StringUtils.isNotBlank(keyword)){
    		keyword= new String(keyword.getBytes("iso8859-1"),"UTF-8").toUpperCase();
    		Page<Project> vagueSelect = projectService.vagueSelect(keyword,PageableUtil.getPageable(page-1, rows, "asc", "id"));
    		 map.put("total", vagueSelect.getTotalElements());
        	 map.put("rows", vagueSelect.getContent());
        	 ResponseUtil.fastResponse(response, map, true);
    		return;
    	}else{
	    	 Page<Project> findAlls = projectService.findAll(PageableUtil.getPageable(page-1, rows, "desc", "id"));
	       	 map.put("total", findAlls.getTotalElements());
	       	 map.put("rows", findAlls.getContent());
	       	 ResponseUtil.fastResponse(response, map, true);
    	}
    }
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public void delete(@RequestParam(value = "ids[]") Long[] ids,HttpServletResponse response){
    	for(Long id : ids)
    		projectService.delete(id);
    	ResponseUtil.fastResponse(response, "Successfully deleted !");
    }
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    public void userEdit(Project  project,HttpServletResponse response) { 
    	projectService.saveAndFlush(project);
    	ResponseUtil.fastResponse(response, "Update completed");
    	
    }
}