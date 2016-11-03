package com.jpa.mvc.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpa.entity.Project;
import com.jpa.generator.CreateFileContext;
import com.jpa.generator.JavaServerPage;
import com.jpa.generator.JavaSourceFile;
import com.jpa.generator.TextFile;
import com.jpa.service.ProjectService;
import com.jpa.util.CreateFolder;
import com.jpa.util.EditorMode;
import com.jpa.util.JCompiler;
import com.jpa.util.ResponseBody;
import com.jpa.util.ResponseUtil;
import com.jpa.util.StringUtils;
import com.jpa.util.TreeNode;
@Controller
@RequestMapping("/create")
public class Create {
	@Resource ProjectService projectService;
	
	@RequestMapping("/build")
	public void create(@RequestParam String projectName,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String rootPath = getRootPath(request);
		if(StringUtils.isNotEmpty(projectName)) {
			boolean build = CreateFolder.build(projectName, rootPath);
			if(build){
				Project pro = new Project();
				pro.setCreateTime(new Date());
				pro.setName(projectName);
				pro.setPath("/"+projectName);
				projectService.save(pro);
				ResponseUtil.fastResponse(response, new ResponseBody(" Build successfully!"), true);
				
			}
		}
		else
		ResponseUtil.fastResponse(response, new ResponseBody(" Project's name can't be empty!"), true);
	}
	@RequestMapping("/get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		List<Project> findAll = projectService.findAll();
		String rootPath = getRootPath(request);
		String name;
		String path;
		File f;
		TreeNode t = new TreeNode();
		t.setId(UUID.randomUUID().toString());
		 t .setText("ProjectExplorer");
		 List<TreeNode> tc = new ArrayList<TreeNode>();
		for(Project pro : findAll){
			 name = pro.getName();
			 path = rootPath+File.separator+name;
			 f = new File(path);
			 TreeNode treeNode = getTreeNode(f);
			 treeNode.setState("closed");
			 tc.add(treeNode);
		}
		t.setChildren(tc);
		ResponseUtil.fastResponse(response, Arrays.asList(t), true);
		
	}
	private String getRootPath(HttpServletRequest request) {
		String realPath = request.getServletContext().getRealPath("");
		//D:\apache-tomcat-7.0.72\webapps\ProjectBuilder -->> D:\apache-tomcat-7.0.72\webapps\
		int indexOf = realPath.indexOf("webapps")+8;
		String rootPath = realPath.substring(0, indexOf);
		return rootPath;
	}
	
	
	
	public  TreeNode getTreeNode(File directory){
		TreeNode tn = new TreeNode();
		tn.setId(UUID.randomUUID().toString());
		tn.setText(directory.getName());
		List<TreeNode> rootChild = new ArrayList<TreeNode>();
		File[] listFiles = directory.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".class"))
					return false;
				else
					return true;
			}
			
		});
		for(File f:listFiles){
			if (f.isDirectory()) {
				TreeNode treeNode = getTreeNode(f);
				treeNode.setId(UUID.randomUUID().toString());
				treeNode.setText(f.getName());
				treeNode.setIconCls("icon-file");
				rootChild.add(treeNode);
			}else{
				TreeNode t = new TreeNode();
				t.setId(UUID.randomUUID().toString());
				t.setText(f.getName());
				rootChild.add(t);
			}
		}
		 tn.setChildren(rootChild);
		return tn;
	}
	
	@RequestMapping("/open")
	public void openFile(@RequestParam String path, HttpServletRequest request, HttpServletResponse response) {
		path = path.replace("ProjectExplorer/","").replace("/", File.separator);
		String rootPath = getRootPath(request);
		File f = new File(rootPath+File.separator+path);
		StringBuffer str = new StringBuffer();
		String enter = System.getProperty("line.separator");
		if(!f.isDirectory()){
			try {
				BufferedReader fr = new  BufferedReader(new FileReader(f));
				String st;
				while(null !=(st=fr.readLine())){
					str.append(st);
					str.append(enter);
				}
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ResponseUtil.fastResponse(response, new ResponseBody(1,EditorMode.getByFileName(f.getName())," Open file "+path+" successfully!",str.toString()),true);
			
		}else{
			ResponseUtil.fastResponse(response, new ResponseBody(0,EditorMode.getByFileName(f.getName())," Open file "+path+" unsuccessfully! because it's an directory!",str.toString()),true);
		}
		
	}
	
	@RequestMapping("/save")
	public void saveFile(@RequestParam String path,@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rs = "无法保存!";
		boolean notEmpty = StringUtils.isNotEmpty(code);
		
		if(notEmpty){
			path = path.replace("ProjectExplorer/","").replace("/", File.separator);
			String rootPath = getRootPath(request);
			File f = new File(rootPath+File.separator+path);
			StringBuffer str = new StringBuffer();
			
			FileWriter fw = new FileWriter(f);
			fw.write(code);
			fw.close();
			
			if (f.getName().endsWith("java")) {
				boolean compileFile = JCompiler.compileFile(f.getAbsolutePath());
				if (compileFile) {
					str.append("Save and compile  File " + path + " successfully!");
				} else {
					str.append("<font color='red'>Successfully saved file " + path + " compile  file unsuccessfully!<font>");
				}
			}else{
				str.append("Successfully saved file " + path +"!");
			}
			rs = str.toString();
		}
		ResponseUtil.fastResponse(response, new ResponseBody(rs),true);
	}
	
	@RequestMapping("/create")
	public void createFile(@RequestParam String path,@RequestParam String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		path = path.replace("ProjectExplorer/","").replace("/", File.separator);
		String rootPath = getRootPath(request);
		File f = new File(rootPath+File.separator+path);
		CreateFileContext   fileContext = new CreateFileContext(null);
		StringBuffer str = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		if(f.isDirectory()){
			f = new File(f.getAbsolutePath()+File.separator+fileName);
			if(f.exists()){
				ResponseUtil.fastResponse(response, new ResponseBody(0,null,"create file "+ fileName +" on "+path +"  unsuccessfully! because it's already exists!",null),true);
			}else{
				if(fileName.endsWith("java")){
					fileContext.changeStrategy(new JavaSourceFile());
					String pack = path.substring(path.indexOf("classes")+7+File.separator.length()).replace(File.separatorChar, '.');
					map.put("package", pack);
					map.put("className", fileName.substring(fileName.indexOf(".")+1));
					String create = fileContext.create(map, f);
					str.append(create);
				}else if(fileName.endsWith("jsp")){
					fileContext.changeStrategy(new JavaServerPage());
					String create = fileContext.create(null, f);
					str.append(create);
				}else{
					fileContext.changeStrategy(new TextFile());
					String create = fileContext.create(null, f);
					str.append(create);
				}
				ResponseUtil.fastResponse(response, new ResponseBody(1,EditorMode.getByFileName(f.getName()),"create file "+ fileName +" on "+path +" successfully!",str.toString()),true);
			}
		}else{
			ResponseUtil.fastResponse(response, new ResponseBody(0,null,"create file "+fileName+" on "+path+" unsuccessfully! because it's not an directory!",null),true);
		}
	}
	
	@RequestMapping("/remove")
	public void removFile(@RequestParam String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		path = path.replace("ProjectExplorer/","").replace("/", File.separator);
		String rootPath = getRootPath(request);
		File f = new File(rootPath+File.separator+path);
		if(f.isFile()){
			f.delete();
			ResponseUtil.fastResponse(response, new ResponseBody(1,null,"delete file "+path+" successfully! ",null),true);
		}else{
			ResponseUtil.fastResponse(response, new ResponseBody(0,null,"delete file "+path+" unsuccessfully! because it's not an file!",null),true);
		}
	}
}
