package com.jpa.generator;

import java.io.File;
import java.util.Map;

public class CreateFileContext {
	private CreateFileStrategy strategy;

	public CreateFileContext(CreateFileStrategy strategy) {
		super();
		this.strategy = strategy;
	}
	
	public String create(Map<String,?> param,File target){
		if(null == strategy){
			this.strategy = new TextFile();
		}
		return this.strategy.createFile(param, target);
	}
	
	public void changeStrategy(CreateFileStrategy strategy){
		this.strategy = strategy;
	}
}
