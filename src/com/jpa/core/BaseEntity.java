package com.jpa.core;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.jpa.util.ResponseUtil;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 7789240689381843197L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String toJson(){
		return ResponseUtil.fastJson(this, true);
	}
	
}
