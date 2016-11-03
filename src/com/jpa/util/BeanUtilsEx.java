package com.jpa.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class BeanUtilsEx extends BeanUtils {
	
	// Describe Object to Map With Some Properties
	public static Map<String, Object> describeWith(Object source, String... properties) {
		
		Assert.notNull(source, "source must not be null");
		Assert.notNull(properties, "properties must not be null");
		
		Map<String, Object> target = new HashMap<String, Object>();
		Class<?> clazz = source.getClass();
		PropertyDescriptor[] pds = getPropertyDescriptors(clazz);
		List<String> withList = Arrays.asList(properties);

		for (PropertyDescriptor pd : pds) {
			if (withList.contains(pd.getName()) && pd.getReadMethod() != null) {
				try {
					Method readMethod = pd.getReadMethod();
					if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(source);
					target.put(pd.getName(), value);
				} catch (Throwable ex) {
					throw new FatalBeanException("Could not copy properties from source to target:(" + pd.getName() + ")", ex);
				}
			}

		}
		return target;
	}

	// Describe Object to Map Ignore Some Properties
	public static Map<String, Object> describe(Object source, String... ignoreProperties) {
		return describe(source, false, ignoreProperties);
	}
	
	// Describe Object to Map Ignore Null value and Some Properties
	public static Map<String, Object> describe(Object source, boolean ignoreNullValue, String... ignoreProperties) {
		Assert.notNull(source, "source must not be null");
		Map<String, Object> target = new HashMap<String, Object>();
		Class<?> clazz = source.getClass();
		PropertyDescriptor[] pds = getPropertyDescriptors(clazz);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor pd : pds) {
			if (pd.getReadMethod() != null && !pd.getName().equals("class") && (ignoreProperties == null || (!ignoreList.contains(pd.getName())))) {
				try {
					Method readMethod = pd.getReadMethod();
					if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(source);
					if(ignoreNullValue && null == value){
						continue;
					}
					target.put(pd.getName(), value);
				} catch (Throwable ex) {
					throw new FatalBeanException("Could not copy properties from source to target:(" + pd.getName() + ")", ex);
				}
			}
		}
		return target;
	}

	/**
	 * Copy the property values of the given source bean into the given target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * @param source the source bean
	 * @param target the target bean
	 * @param ignoreProperties array of property names to ignore
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyPropertiesWithNotNull(Object source, Object target, String[] ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null &&
					(ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if(null == value || value.toString().trim().length() < 1){
							continue;
						}
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
}
