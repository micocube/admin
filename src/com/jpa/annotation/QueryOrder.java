package com.jpa.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(value={FIELD, METHOD})
@Retention(value=RUNTIME)
public @interface QueryOrder {

	/**
	 * 查询条件中的位置
	 * @return
	 */
	public abstract int pos();
	
}
