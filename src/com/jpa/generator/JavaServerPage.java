package com.jpa.generator;

import java.io.File;
import java.util.Map;

public class JavaServerPage implements CreateFileStrategy {

	@Override
	public String createFile(Map<String, ?> param,File target) {
		return CodeHelper.createByTemplate("/JavaServerPage.vm", param,target);
	}
}
