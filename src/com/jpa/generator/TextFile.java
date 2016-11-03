package com.jpa.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class TextFile implements CreateFileStrategy {

	@Override
	public String createFile( Map<String, ?> param,File target) {
		try {
			target.createNewFile();
			FileWriter fw  = new FileWriter(target);
			fw.write( System.getProperty("line.separator"));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  System.getProperty("line.separator");
	}
}
