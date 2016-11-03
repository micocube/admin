package com.jpa.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	
	static Properties dbProps ;
	public static Map<String, String> map = new HashMap<String, String>();
	
	static public void loads(String path) {
		InputStream is = PropertiesUtil.class.getResourceAsStream(path);
		try {
			dbProps = new Properties();
			dbProps.load(is);
			Enumeration<?> keys = dbProps.propertyNames();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = dbProps.getProperty(key);
				map.put(key, value);
			}
			is.close();
		} catch (Exception e) {
			System.err.println("不能读取属性文件. " + "请确保db.properties在CLASSPATH指定的路径中");
		}
	}
	
	/** 
     * 写入properties信息 
     */  
    public static void writeProperties(String key, String value) {  
        try {  
        	URL resource = PropertiesUtil.class.getResource("/xhl.properties");
            OutputStream fos = new FileOutputStream(resource.getFile());  
            dbProps.setProperty(key, value);  
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流  
            dbProps.store(fos, "『comments』Update key：" + key);  
        } catch (IOException e) {  
        }  
    }   

}