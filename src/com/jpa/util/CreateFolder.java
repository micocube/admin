package com.jpa.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class CreateFolder {
	public static boolean build(String projectName, String tomcatPath) throws IOException {
		String enter = System.getProperty("line.separator");
		File f = new File(tomcatPath+File.separator+projectName);
		if(f.exists()){
			return false;
		}else{
			f.mkdirs();
		}
		String rootPath = f.getAbsolutePath();
		f = new File(rootPath+File.separator+"index.jsp");
		f.createNewFile();
		FileWriter w = new FileWriter(f);
		w.write("<%@ page language=\"java\" pageEncoding=\"UTF-8\"%>");
	    w.write(enter);
	    w.write("<%out.println(\" It works! \");%>");
	    w.flush();
	    w.close();
		f = new File(rootPath+File.separator+"WEB-INF");
		f.mkdirs();
		f = new File(f.getAbsolutePath()+File.separator+"web.xml");
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		fw.write("<web-app>");
		fw.write(enter);
		fw.write("<welcome-file-list>");
		fw.write(enter);
		fw.write("<welcome-file>index.jsp</welcome-file>");
		fw.write(enter);
		fw.write("</welcome-file-list>");
		fw.write(enter);
		fw.write("</web-app>");
		fw.flush();
		fw.close();
		f = new File(rootPath+File.separator+"WEB-INF"+File.separator+"classes");
		f.mkdirs();
		String classesPath = f.getAbsolutePath();
		f = new File(classesPath+File.separator+"com"+File.separator+projectName.toLowerCase()+File.separator+"controller");
		f.mkdirs();
		f = new File(classesPath+File.separator+"com"+File.separator+projectName.toLowerCase()+File.separator+"service");
		f.mkdirs();
		f = new File(classesPath+File.separator+"com"+File.separator+projectName.toLowerCase()+File.separator+"repository");
		f.mkdirs();
		f = new File(rootPath+File.separator+"WEB-INF"+File.separator+"lib");
		f.mkdirs();
		return true;
	}

}
