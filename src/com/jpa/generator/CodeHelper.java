package com.jpa.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Column;

import org.apache.jasper.tagplugins.jstl.core.Set;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.jpa.entity.Project;
import com.jpa.util.PropertiesUtil;

public class CodeHelper {

	public static void main(String[] args) throws Exception {
		Class<? extends Object> clazz = Project.class;
		Field[] declaredFields = clazz.getDeclaredFields();
		Map<String,String> map = new HashMap<String,String>();
		for(Field f : declaredFields){
			Class<?> type = f.getType();
			if(type.isAssignableFrom(Set.class)||type.isAssignableFrom(List.class)||type.isAssignableFrom(Map.class)){
				continue;
			}
			javax.persistence.Id idfield = f.getAnnotation(javax.persistence.Id.class);
			if(idfield!=null) continue;
			Column column = f.getAnnotation(Column.class);
			if(null != column){
				String columnDefinition = column.columnDefinition();
				String upperCase = columnDefinition.toUpperCase();
				String[] split = upperCase.split("COMMENT");
				String trim;
				if(split.length != 1 && split.length==2){
				    trim = split[1].trim();
					if(trim.contains("'")){
						trim = trim.replace("'", "");
					}
					map.put(f.getName(), trim);
				}else{
					map.put(f.getName(), f.getName());
				}
			}else{
				map.put(f.getName(), f.getName());
			}
		}
		System.out.println(map);
		
		
		
		/**
		 * 模板引擎
		 */
		VelocityEngine ve = new VelocityEngine();
		 ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 InputStream is = PropertiesUtil.class.getResourceAsStream("/velocity.properties");
		 Properties dbProps = new Properties();
		 dbProps.load(is);
		 ve.init(dbProps);
		 is.close();
		 Template t = ve.getTemplate("hellovelocity.vm");
		 VelocityContext ctx = new VelocityContext();
		 
		 ctx.put("name", "velocity");
		 ctx.put("date", (new Date()).toString());
		 
		 List<String> temp = new ArrayList<String>();
		 temp.add("1");
		 temp.add("2");
		 ctx.put("list", temp);
		 
		 Map<String,String> paramValues=new HashMap<String, String>();  
		 paramValues.put("mapkey", "mapValue");
		  ctx.put("paramValues", paramValues);//传值给velocity  
		 
		 StringWriter sw = new StringWriter();
		 
		 t.merge(ctx, sw);
		 
		 System.out.println(sw.toString());
		 
		 
		 /**
		  * 生成repository层
		  */
		 PropertiesUtil.loads("/templateConfig.properties");
		 String vague = PropertiesUtil.map.get("vague");
		 t = ve.getTemplate("repository.vm");
		 ctx = new VelocityContext();
		 //设置实体泛型
		 ctx.put("name", clazz.getSimpleName());
		 //设置模糊查询的字段名
		 ctx.put("vague",vague);
		 
          sw = new StringWriter();
		 
		 t.merge(ctx, sw);
		 FileWriter fw = new FileWriter(new File("D://"+clazz.getSimpleName()+"Repository"+".java"));
		 fw.write(sw.toString());
		 fw.close();
		 System.out.println(sw.toString());
		 /**
		  * 生成service层
		  */
		 t = ve.getTemplate("service.vm");
		 ctx = new VelocityContext();
		 //设置实体泛型
		 String simpleName = clazz.getSimpleName();
		 String lowerCase = simpleName.toLowerCase();
		ctx.put("name", simpleName);
		//设置小写的实体泛型
		ctx.put("lowername", lowerCase);
        sw = new StringWriter();
		 
		 t.merge(ctx, sw);
		 fw = new FileWriter(new File("D://"+clazz.getSimpleName()+"Service"+".java"));
		 fw.write(sw.toString());
		 fw.close();
		 System.out.println(sw.toString());
		 
		 
		 /**
		  * 生成controller层
		  */
		 
		 t = ve.getTemplate("controller.vm");
		 ctx = new VelocityContext();
		 //设置实体泛型
		ctx.put("name", simpleName);
		//设置小写的实体泛型
		ctx.put("lowername", lowerCase);
        sw = new StringWriter();
		 
		 t.merge(ctx, sw);
		 fw = new FileWriter(new File("D://"+clazz.getSimpleName()+"Controller"+".java"));
		 fw.write(sw.toString());
		 fw.close();
		 System.out.println(sw.toString());
		 
		 /**
		  * 生成view层
		  */
		 t = ve.getTemplate("view.vm");
		 ctx = new VelocityContext();
		 //设置实体泛型
		ctx.put("name", simpleName);
		//设置小写的实体泛型
		ctx.put("lowername", lowerCase);
		//设置所有的属性
		ctx.put("map", map);
		PropertiesUtil.loads("/templateConfig.properties");
		vague = PropertiesUtil.map.get("vague");
		//模糊查询的字段名
		ctx.put("vague", vague);
        sw = new StringWriter();
		 
		 t.merge(ctx, sw);
		 fw = new FileWriter(new File("D://"+clazz.getSimpleName()+"view"+".jsp"));
		 fw.write(sw.toString());
		 fw.close();
		 System.out.println(sw.toString());
		 
	}
	
	public static String createByTemplate(String vmPath,Map<String,?> param,File target)  {
		/**
		 * 模板引擎
		 */
		VelocityEngine ve = new VelocityEngine();
		 ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 InputStream is = PropertiesUtil.class.getResourceAsStream("/velocity.properties");
		 Properties dbProps = new Properties();
		 try {
			dbProps.load(is);
			ve.init(dbProps);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Template t = ve.getTemplate(vmPath);
		 VelocityContext ctx = new VelocityContext();
		 
		if (null!=param) {
			java.util.Set<String> keySet = param.keySet();
			for (String key : keySet) {
				ctx.put(key, param.get(key));
			} 
		}
		StringWriter sw = new StringWriter();
		 
		 t.merge(ctx, sw);
		 FileWriter fw;
		try {
			fw = new FileWriter(target);
			fw.write(sw.toString());
			 fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		return sw.toString();
	}

}
