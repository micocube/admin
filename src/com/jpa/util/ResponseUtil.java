package com.jpa.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil {
	/**
	 * 
	 * @param response
	 * @param obj
	 * @param skipEmpty
	 */
	public static void fastResponse(HttpServletResponse response, Object obj, boolean skipEmpty) {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
    	PrintWriter writer = null;
    	try {
    		writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if (null != writer) {
			writer.append(fastJson(obj,skipEmpty));
			writer.flush();
			writer.close();
		}
	}
	
	public static void fastResponse(HttpServletResponse response, String str) {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
    	PrintWriter writer = null;
    	try {
    		writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if (null != writer) {
			writer.append(str);
			writer.flush();
			writer.close();
		}
	}
	
	public static String fastJson(Object obj, boolean skipEmpty){
		ObjectMapper  om = new ObjectMapper ();
		if (skipEmpty) {
			om.setSerializationInclusion(Include.NON_EMPTY);
		}
		String writeValueAsString="";
		 try {
			writeValueAsString = om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		 return writeValueAsString;
	}
}
