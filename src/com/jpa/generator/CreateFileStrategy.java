package com.jpa.generator;

import java.io.File;
import java.util.Map;

public interface CreateFileStrategy {
		public  String createFile(Map<String, ?> param,File target);
}
