package com.jpa.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({FIELD})
public @interface Equal {
	
	/**
	 * 标注当前属性对应的值集中不等于某些值
	 * 通常是用于在一个下拉框中含多个值的情况下
	 * @return
	 */
	public abstract String notEqual() default "";
	
}
