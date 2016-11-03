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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jpa.entity.formal.User;
import com.jpa.service.UserService;
import com.jpa.util.PageableUtil;
import com.jpa.util.ResponseUtil;

/**
 * 用户管理
 * 
 * @author mico
 */
@Controller
public class UserController {
    @Resource UserService userService;
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
		    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");  
		    dateFormat.setLenient(false);  
		    binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));   //是否允许空值
    }
    
  //查询单个详情
    @RequestMapping(value="/inf/{id}",method=RequestMethod.GET)
    public  ModelAndView inf(@PathVariable Long id){
    	User findById = userService.findOne( id);
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("detail");
        mv.addObject("user", findById);
        return mv;
    }
    @RequestMapping(value="/inff/{id}",method=RequestMethod.GET)
    public void inff(@PathVariable Long id,HttpServletResponse response){
    	User findById = userService.findOne( id);
    	 ResponseUtil.fastResponse(response, findById, true);
    }
    
    //查询所有，要求分页
    @RequestMapping(value="/userList",method=RequestMethod.GET)
    public void  userList(HttpServletResponse response,String keyword,Integer page,Integer rows){
    	page -=1;
    	Map<String,Object> map = new HashMap<String,Object>();
    	 Page<User> findAlls = userService.findAll(PageableUtil.getPageable(page, rows, "desc", "id"));
    	 map.put("total", findAlls.getTotalElements());
    	 map.put("rows", findAlls.getContent());
    	 ResponseUtil.fastResponse(response, map, true);
    }
    
    @RequestMapping("/save")
    public void Save(HttpServletResponse response,User user) { // user:视图层传给控制层的表单对象；model：控制层返回给视图层的对象
    	userService.save(user);
    	ResponseUtil.fastResponse(response, "保存成功");
    }
    
    @RequestMapping(value="/vagueSelect",method=RequestMethod.GET)
    public void vagueSelect(HttpServletResponse response,String keyword,Integer page,Integer rows) throws Exception{
    	Map<String,Object> map = new HashMap<String,Object>();
    	if(StringUtils.isNotBlank(keyword)){
    		keyword= new String(keyword.getBytes("iso8859-1"),"UTF-8");
    		Page<User> vagueSelect = userService.vagueSelect(keyword,PageableUtil.getPageable(page-1, rows, "asc", "id"));
    		 map.put("total", vagueSelect.getTotalElements());
        	 map.put("rows", vagueSelect.getContent());
        	 ResponseUtil.fastResponse(response, map, true);
    		return;
    	}else{
	    	 Page<User> findAlls = userService.findAll(PageableUtil.getPageable(page-1, rows, "desc", "id"));
	       	 map.put("total", findAlls.getTotalElements());
	       	 map.put("rows", findAlls.getContent());
	       	 ResponseUtil.fastResponse(response, map, true);
    	}
    }
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public void delete(@RequestParam(value = "ids[]") Long[] ids,HttpServletResponse response){
    	for(Long id : ids)
    		userService.delete(id);
    	ResponseUtil.fastResponse(response, "删除成功！");
    }
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    public void userEdit(User  user,HttpServletResponse response) { // user:视图层传给控制层的表单对象；model：控制层返回给视图层的对象
    	userService.saveAndFlush(user);
    	ResponseUtil.fastResponse(response, "更新成功！");
    	
    }
	
    @RequestMapping(value="/folderTree",method=RequestMethod.GET)
    public void getFolderTree(HttpServletResponse response){
    	String getenv = System.getProperty("user.dir");
    	System.out.println(getenv);
    }
    
    
    /**
     * restful 风格的api
     */
     //添加操作
    @RequestMapping(value="/create",method=RequestMethod.POST)
    public static String create(@RequestBody User user){
    	//对user进行一些操作
    	//用于把传来的JSON 转换成接收的对象, 如果是form提交就不需要了, 
    	//但如果前台用的是application/json类型传进来,就一定要加@RequestBody
    	return "success";
    }
    
     //查询所有，要求分页
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public static String list(){
    	return "success";
    }
    
  //查询单个详情
    @RequestMapping(value="/info/{id}",method=RequestMethod.GET)
    public static String info(@PathVariable Integer id){
    	return "success";
    }
    
    /**
     * PUT DELETE 就和表单不一样了, 因为表单只支持GET和POST
	 *	这时候就需要用到ajax,  或者nodejs调用
     */
    
    //更新操作
    @RequestMapping(value="/update",method=RequestMethod.PUT)
    public static String update(@RequestBody User user){
    	//对user进行一些操作
    	//用于把传来的JSON 转换成接收的对象, 如果是form提交就不需要了, 
    	//但如果前台用的是application/json类型传进来,就一定要加@RequestBody
    	return "success";
    }
    
  //删除操作
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public static String update(@PathVariable Integer id){
    	//对user进行一些操作
    	//用于把传来的JSON 转换成接收的对象, 如果是form提交就不需要了, 
    	//但如果前台用的是application/json类型传进来,就一定要加@RequestBody
    	return "success";
    }
}