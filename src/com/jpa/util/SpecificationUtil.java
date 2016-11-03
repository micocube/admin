package com.jpa.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.jpa.annotation.Equal;
import com.jpa.annotation.QueryOrder;

public class SpecificationUtil{
	
	public static <E> Specification<E> getSpecification(final E entity, final boolean joinFetch, final int fetchSize, final String... ignoreProperties){
		return new Specification<E>() {
			public Predicate toPredicate(Root<E> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<String> layer = new ArrayList<String>(1);
				List<Predicate> list = SpecificationUtil.builderPredicate(root, cb, layer, entity, joinFetch, fetchSize, ignoreProperties);
				if(list.isEmpty()){ return cb.conjunction(); }
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		};
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> List<Predicate> builderPredicate(Root<E> root, CriteriaBuilder cb, List<String> layer, E entity, boolean joinFetch, int fetchSize, String... ignoreProperties){
		List<Predicate> list = new ArrayList<Predicate>();
		
		// 控制抓取范围为 0 - 2
		if(fetchSize < 0) { fetchSize = 0; }
		if(fetchSize > 2) { fetchSize = 2; }
		
		// 由 Bean 转 Map 用于分析 key 是否可以作为查询条件
		final Class<E> clazz = (Class<E>) entity.getClass();
		Map<String, Object> map = BeanUtilsEx.describe(entity, ignoreProperties);
		
		// 按指定的顺序组织查询条件
		List<String> keyList = new ArrayList<String>(map.keySet());
		Collections.sort(keyList, new Comparator<String>() {
			public int compare(String o1, String o2) {
				// 倒序排列, 越大排越前
				return SpecificationUtil.getPos(clazz, o2) - SpecificationUtil.getPos(clazz, o1);
			}
		});
		
		try {
			Field field = null;
			boolean isRelationTable = false;
			
			for(String key : keyList){
				Object value = map.get(key);

				// 判断是否是关联表
				isRelationTable = false;
				field = clazz.getDeclaredField(key);
				if(null != field.getAnnotation(ManyToOne.class) || null != field.getAnnotation(OneToOne.class)){
					isRelationTable = true;
				}
				
				/**
				 * 当字段含有 @ManyToOne 或 @OneToOne 注解时( @OneToMany 和 @ManyToMany 除外)，
				 * 若需要查询，都以 LEFT JOIN FETCH 方式直接抓取过来，从而可以避免 N+1的问题
				 * e.g : root.fetch(property, JoinType.LEFT);
				 * 
				 * 当用到关联对象中的字段查询时（e.g：user.role.roleName），
				 * 若没有开启抓取，JPQL查询也会自动加上JOIN来关联
				 * 但是不能在查询结果中使用 role 对象，因为 hibernate 的 session 已经关闭
				 * 
				 * 注：本操作不是查询条件的一部分
				 */
				if(isRelationTable && joinFetch && (layer.size() < fetchSize)){
					Fetch<?, ?> fetch = null;
					if(layer.isEmpty()){
						fetch = root.fetch(key, JoinType.LEFT);
					} else {
						// 同一张表下的抓取每次都会重复抓取第一个关联表，等待优化
						fetch = root.fetch(layer.get(0), JoinType.LEFT);
						for(int i = 1; i < layer.size(); i++){
							fetch = fetch.fetch(layer.get(i), JoinType.LEFT);
						}
						fetch = fetch.fetch(key, JoinType.LEFT);
					}
				}
				
				// 若值为 NULL 则跳过
				if(null == value){
					continue;
				}

				// 空值 或 -1 或 IGNORE[ComboBox中的不选择] 则跳过
				String sValue = value.toString().trim();
				if(sValue.isEmpty() || sValue.equals("-1") || sValue.equalsIgnoreCase("IGNORE")){
					continue;
				}

				/**
				 * 判断需要查询的真实的(数据库表)字段名称
				 * 因为在实体类中， @Transient 注解的字段不是表的真实字段
				 * 是不能用来构造查询路径的，但都是以 Begin 和 End 结尾
				 * 所以去掉这些结尾就是表字段
				 */
				String property = null;
				if(key.endsWith("Begin")){
					property = key.substring(0, key.length() - 5);
				} else if(key.endsWith("End")){
					property = key.substring(0, key.length() - 3);
				} else {
					property = key;
				}

				/**
				 * 构造查询的路径
				 * e.g : 
				 * user.username
				 * user.role.roleName
				 * user.role.createTime
				 */
				Path<?> path = null;
				if(layer.isEmpty()){
					path = root.get(property);
				} else {
					path = root.get(layer.get(0));
					for(int i = 1; i < layer.size(); i++){
						path = path.get(layer.get(i));
					}
					path = path.get(property);
				}

				// isNull
				if(sValue.equals("-2") || sValue.equalsIgnoreCase("isNull")){
					list.add(cb.isNull(path));
					continue;
				}

				// isNotNull
				if(sValue.equals("-3") || sValue.equalsIgnoreCase("isNotNull")){
					list.add(cb.isNotNull(path));
					continue;
				}

				// 区间值的开始条件
				if(key.endsWith("Begin")){
					if(clazz.getDeclaredField(property).getType().getName().endsWith("Date")){
						list.add(cb.greaterThanOrEqualTo((Path) path, (Comparable) DateUtils.str2Date(sValue)));
					} else {
						list.add(cb.greaterThanOrEqualTo((Path) path, (Comparable) value));
					}
					continue;
				}
				
				// 区间值的结束条件
				if(key.endsWith("End")){
					if(clazz.getDeclaredField(property).getType().getName().endsWith("Date")){
						list.add(cb.lessThanOrEqualTo((Path) path, (Comparable) DateUtils.str2Date(sValue)));
					} else {
						list.add(cb.lessThanOrEqualTo((Path) path, (Comparable) value));
					}
					continue;
				}

				// 带有 @Equal 注解的字段
				Equal eq = field.getAnnotation(Equal.class);
				if(null != eq){
					/**
					 * 例如字段名为 disposition, 字段值为 answer
					 * 1. 当notEqual为""或answer时, 使用equal规则, e.g. disposition = 'answer'
					 * 2. 否则, 使用notEqual规则, e.g. disposition != 'answer'
					 */
					String nEqual = eq.notEqual();
					if(nEqual.isEmpty() || nEqual.equals(sValue)){
						list.add(cb.equal(path, value));
					} else {
						list.add(cb.notEqual(path, nEqual));
					}
					continue;
				}
				
				// 处理数字、Boolean和枚举等值的条件
				if(value instanceof Number || value instanceof Boolean || value instanceof Enum){
					list.add(cb.equal(path, value));
					continue;
				}

				/**
				 * 递归调用
				 * 关联对象同样构建所有查询条件
				 * （往下）最多不得超过2层，超出无效
				 * e.g：button.menuSecond.menuFirst
				 */
				if(isRelationTable){
					layer.add(field.getName());
					if(layer.size() < fetchSize + 1){
						list.addAll(SpecificationUtil.builderPredicate(root, cb, layer, (E) value, joinFetch, fetchSize, ignoreProperties));
					}
					
					layer.remove(layer.size() - 1);
					continue;
				} 
				
				list.add(cb.like((Path) path, sValue + "%"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	private static int getPos(Class<?> clazz, String fieldName){
		try {
			Field field = clazz.getDeclaredField(fieldName);
			QueryOrder order = field.getAnnotation(QueryOrder.class);
			if(null == order) return 0;
			return order.pos();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
