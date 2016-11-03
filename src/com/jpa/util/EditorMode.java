package com.jpa.util;

import java.util.HashMap;
import java.util.Map;

public class EditorMode {

	static Map<String,String> modeMap = new HashMap<String,String>();
	
	static{
		modeMap.put("java", "text/x-java");
		modeMap.put("js", "text/javascript");
		modeMap.put("css", "text/css");
		modeMap.put("jsp", "application/x-jsp");
		modeMap.put("xml", "application/xml");
		modeMap.put("html", "text/html");
	}
	public static void main(String[] args) {
		String byFileName = getByFileName("a.jsp");
		System.out.println(byFileName);

	}
	
	public static String getByFileName(String fileName){
		String ex = fileName.substring(fileName.lastIndexOf(".")+1).trim();
		return modeMap.get(ex);
	}
	
}
