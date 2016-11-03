package com.jpa.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


public class PageableUtil{
	
	// PageSize
	private static Integer getPagesize(Integer start, Integer limit){
		return (start % limit == 0) ? (start / limit) : ((start / limit) + 1);
	}
	
	// Direction
	private static Direction getDirection(String direction){
		return direction.equalsIgnoreCase("ASC") ? Direction.ASC : Direction.DESC;
	}
	
	// Sort
	public static Sort getSort(String direction, String... properties){
		return new Sort(PageableUtil.getDirection(direction), properties);
	}
	
	// PageRequest
	public static PageRequest getPageable(Integer start, Integer limit, String direction, String property){
		return new PageRequest(PageableUtil.getPagesize(start, limit), limit, PageableUtil.getSort(direction, property));
	}
	
}
