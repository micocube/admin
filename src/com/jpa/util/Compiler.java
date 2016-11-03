package com.jpa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Compiler {

	public static void main(String[] args) throws Exception {

		String cmd = "javac D:\\apache-tomcat-7.0.72\\webapps\\Mico\\WEB-INF\\classes\\com\\Mico\\controller\\Test.java";
		CompilerMessage errorGobbler = compiler(cmd);
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(new String(errorGobbler.getError().getBytes(),"utf-8"));
	}
    public static  CompilerMessage compilerSrc(String srcPath) throws Exception, InterruptedException{
    	return compiler("javac "+srcPath);
    } 
	public static CompilerMessage compiler(String cmd) throws IOException, InterruptedException {
		Process proc = Runtime.getRuntime().exec(cmd);
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "Error");
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "Output");
		errorGobbler.start();
		outputGobbler.start();
		proc.waitFor();
		return new CompilerMessage(outputGobbler.sb.toString(),errorGobbler.sb.toString());
	}

}

class CompilerMessage{
	private String outPut;
	private String error;
	public String getOutPut() {
		return outPut;
	}
	public void setOutPut(String outPut) {
		this.outPut = outPut;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public CompilerMessage(String outPut, String error) {
		super();
		this.outPut = outPut;
		this.error = error;
	}
	
	
	
}

class StreamGobbler extends Thread {
	InputStream is;
	public StringBuffer sb = new StringBuffer();

	String type;

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			String enter = System.getProperty("line.separator");
			while ((line = br.readLine()) != null) {
				if (type.equals("Error"))
					sb.append(line).append(enter);
				else
					System.out.println(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}