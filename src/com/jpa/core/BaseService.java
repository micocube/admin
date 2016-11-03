package com.jpa.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.util.BeanUtilsEx;
import com.jpa.util.PageableUtil;
import com.jpa.util.Result;
import com.jpa.util.SpecificationUtil;

@Transactional
public class BaseService<E> {

	protected Logger logger = Logger.getLogger(getClass());
	protected EntityManagerFactory emf;
	protected BaseDAO<E, Long> baseDAO;
	
	public EntityManager getEntityManager(){
		return this.emf.createEntityManager();
	}
	
	/**
	 * TODO
	 * 统计总记录数
	 * @return
	 */
	@Transactional(readOnly = true)
	public long count(){
		return baseDAO.count();
	}
	
	/**
	 * TODO
	 * 指定条件的记录总数
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public long count(Specification<E> spec){
		return baseDAO.count(spec);
	}
	
	/**
	 * TODO
	 * 指定条件的记录总数
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = true)
	public long count(E entity){
		return this.count(SpecificationUtil.getSpecification(entity, false, 1));
	}
	
	/**
	 * TODO
	 * 删除一条记录
	 * @param entity
	 */
	public void delete(E entity){
		baseDAO.delete(entity);
	}
	
	/**
	 * TODO
	 * 删除多条记录
	 * @param entities
	 */
	public void delete(Iterable<E> entities){
		baseDAO.delete(entities);
	}
	
	/**
	 * TODO
	 * 通过id删除一条记录
	 * @param id
	 */
	public void delete(Long id){
		baseDAO.delete(id);
	}
	
	/**
	 * TODO
	 * 删除所有记录
	 */
	public void deleteAll(){
		baseDAO.deleteAll();
	}
	
	/**
	 * TODO
	 * 批量删除多条记录
	 * @param entities
	 */
	public void deleteInBatch(Iterable<E> entities){
		baseDAO.deleteInBatch(entities);
	}
	
	/**
	 * TODO
	 * 判断某条记录是否存在
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean exists(Long id){
		return baseDAO.exists(id);
	}
	
	/**
	 * TODO
	 * 查询所有记录
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(){
		return baseDAO.findAll();
	}
	
	/**
	 * TODO
	 * 查询所有记录，分页返回
	 * @param page
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable){
		return baseDAO.findAll(pageable);
	}
	
	/**
	 * TODO
	 * 查询所有记录，分页返回
	 * @param start
	 * @param limit
	 * @param direction
	 * @param property
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<E> findAll(int start, int limit, String direction, String property){
		return this.findAll(PageableUtil.getPageable(start, limit, direction, property));
	}
	
	/**
	 * TODO
	 * 查询所有记录，排序返回
	 * @param sort
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(Sort sort){
		return baseDAO.findAll(sort);
	}

	/**
	 * TODO
	 * 查询所有记录，排序返回
	 * @param direction
	 * @param property
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(String direction, String property){
		return this.findAll(PageableUtil.getSort(direction, property));
	}
	
	/**
	 * TODO
	 * 查询指定条件的所有记录
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(Specification<E> spec){
		return baseDAO.findAll(spec);
	}

	/**
	 * TODO
	 * 查询指定条件的所有记录
	 * @param entity
	 * @param joinFetch
	 * @param ignoreProperties
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(E entity, boolean joinFetch, int fetchSize, String... ignoreProperties){
		return this.findAll(SpecificationUtil.getSpecification(entity, joinFetch, fetchSize, ignoreProperties));
	}

	/**
	 * TODO
	 * 查询指定条件的所有记录
	 * @param entity
	 * @param joinFetch
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(E entity, boolean joinFetch, int fetchSize){
		return this.findAll(SpecificationUtil.getSpecification(entity, joinFetch, fetchSize));
	}
	
	/**
	 * TODO
	 * 查询指定条件的所有记录
	 * 默认不抓取数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(E entity){
		return this.findAll(entity, false, 1);
	}

	/**
	 * TODO
	 * 查询指定条件的所有记录并分页
	 * @param spec
	 * @param page
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<E> findAll(Specification<E> spec, Pageable pageable){
		return baseDAO.findAll(spec, pageable);
	}

	/**
	 * TODO
	 * 查询指定条件的所有记录并分页
	 * @param start
	 * @param limit
	 * @param direction
	 * @param property
	 * @param entity
	 * @param joinFetch
	 * @param ignoreProperties
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<E> findAll(int start, int limit, String direction, String property, E entity, int fetchSize, String... ignoreProperties){
		return this.findAll(
			SpecificationUtil.getSpecification(entity, false, fetchSize, ignoreProperties), 
			PageableUtil.getPageable(start, limit, direction, property)
		);
	}
	
	/**
	 * TODO
	 * 查询指定条件的所有记录并分页
	 * @param start
	 * @param limit
	 * @param direction
	 * @param property
	 * @param entity
	 * @param joinFetch
	 * @param cacheable
	 * @param ignoreProperties
	 * @return
	 */
	public List<E> findAll(int start, int limit, String direction, String property, E entity, boolean joinFetch, int fetchSize, boolean cacheable, String... ignoreProperties){
		EntityManager em = this.getEntityManager();
		try{
			@SuppressWarnings("unchecked")
			Class<E> clazz = (Class<E>) entity.getClass();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<E> cq = cb.createQuery(clazz);
			Root<E> root = cq.from(clazz);
			
			// 查询条件
			List<Predicate> list = SpecificationUtil.builderPredicate(root, cb, new ArrayList<String>(), entity, joinFetch, fetchSize, ignoreProperties);
			cq.where(cb.and(list.toArray(new Predicate[list.size()])));
			// 排序
			List<Order> orders = new ArrayList<Order>(1);
			orders.add(direction.equalsIgnoreCase("asc") ? cb.asc(root.get(property)) : cb.desc(root.get(property)));
			cq.orderBy(orders);

			// 设置缓存
			TypedQuery<E> tq = em.createQuery(cq);
			tq.setHint("org.hibernate.cacheable", !joinFetch && cacheable);
			
			// 分页并返回结果
			return tq.setFirstResult(start).setMaxResults(limit).getResultList();
		} finally {
			em.clear();
			em.close();
		}
	}
	
	public String getWhere(E entity){
		EntityManager em = this.getEntityManager();
		try{
			@SuppressWarnings("unchecked")
			Class<E> clazz = (Class<E>) entity.getClass();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<E> cq = cb.createQuery(clazz);
			Root<E> root = cq.from(clazz);
			
			// 查询条件
			List<Predicate> list = SpecificationUtil.builderPredicate(root, cb, new ArrayList<String>(), entity, false, 0);
			cq.where(cb.and(list.toArray(new Predicate[list.size()])));
			
			// 设置缓存
			TypedQuery<E> tq = em.createQuery(cq);
			String sql = tq.unwrap(org.hibernate.Query.class).getQueryString();
	         
	        int count = 0;
	        while((sql.indexOf("param"+ count)) != -1){
	        	String value = "";
	        	value = String.valueOf(tq.unwrap(org.hibernate.jpa.internal.QueryImpl.class).getParameterValue("param"+ count));
	        	sql = sql.replace("param"+count+"", "'"+value+"'");
	        	count++;
	        }
			if(count == 0){
				return "where a.deleteMark = 0";
			}
			sql = sql.replace("generatedAlias0", "a");// 把表别名替换成a
			sql = sql.replace(":", "");
			sql = sql.substring(sql.indexOf("where"));// 截取where 和后面的sql
			sql += " and a.deleteMark = 0";
			// 分页并返回结果
			return sql;
		} finally {
			em.clear();
			em.close();
		}
	}
	
	/**
	 * TODO
	 * 查询指定条件的所有记录并排序
	 * @param spec
	 * @param sort
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(Specification<E> spec, Sort sort){
		return baseDAO.findAll(spec, sort);
	}

	/**
	 * TODO
	 * 查询指定条件的所有记录并排序
	 * @param direction
	 * @param property
	 * @param entity
	 * @param joinFetch
	 * @param ignoreProperties
	 * @param sort
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(String direction, String property, E entity, boolean joinFetch, int fetchSize, String... ignoreProperties){
		return this.findAll(
			SpecificationUtil.getSpecification(entity, joinFetch, fetchSize, ignoreProperties), 
			PageableUtil.getSort(direction, property)
		);
	}
	
	/**
	 * TODO
	 * 通过id查询一条记录
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public E findOne(Long id){
		return baseDAO.findOne(id);
	}
	
	/**
	 * TODO
	 * 查询指定条件的一条记录
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public E findOne(Specification<E> spec){
		return baseDAO.findOne(spec);
	}

	/**
	 * TODO
	 * 查询指定条件的一条记录
	 * @param entity
	 * @param joinFetch
	 * @param ignoreProperties
	 * @return
	 */
	@Transactional(readOnly = true)
	public E findOne(E entity, boolean joinFetch, int fetchSize, String... ignoreProperties){
		return baseDAO.findOne(SpecificationUtil.getSpecification(entity, joinFetch, fetchSize, ignoreProperties));
	}
	
	/**
	 * TODO
	 * 刷新缓存写入数据库
	 */
	@Transactional(readOnly = true)
	public void flush(){
		baseDAO.flush();
	}
	
	/**
	 * TODO
	 * 保存一条记录
	 * @param entity
	 * @return
	 */
	public E save(E entity){
		return baseDAO.save(entity);
	}
	
	/**
	 * TODO
	 * 保存多条记录
	 * @param entities
	 * @return
	 */
	public List<E> save(Iterable<E> entities){
		return baseDAO.save(entities);
	}
	
	/**
	 * TODO
	 * 保存一条记录并强制写入数据库
	 * @param entity
	 * @return
	 */
	public E saveAndFlush(E entity){
		return baseDAO.saveAndFlush(entity);
	}
	
	
	
	
	
	/**
	 * TODO
	 * 根据条件批量更新数据
	 * @param entityCond 作为查询条件的实体类
	 * @param entityData 需要更新数据的实体类
	 * @param allowUpdateEmpty 是否允许空字符('')更新到数据库
	 * @return
	 */
	public String updateAll(E entityCond, E entityData, boolean allowUpdateEmpty){
		long total = this.count(entityCond);
		if(total < 1) {
			return Result.NOTHING.name();
		}
		
		EntityManager em = this.getEntityManager();
		try {
			@SuppressWarnings("unchecked")
			Class<E> clazz = (Class<E>) entityCond.getClass();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaUpdate<E> cu = cb.createCriteriaUpdate(clazz);
			Root<E> root = cu.from(clazz);
			// SET
			Map<String, Object> map = BeanUtilsEx.describe(entityData, true);
			Set<Entry<String, Object>> set = map.entrySet();
			for(Entry<String, Object> entry : set){
				String key = entry.getKey();
				Object value = entry.getValue();
				if(value instanceof BaseEntity){
					Map<String, Object> subMap = BeanUtilsEx.describeWith(value, "id");
					if(!subMap.isEmpty()){
						cu.set(root.get(key).get("id"), subMap.get("id"));
					}
				} else if(allowUpdateEmpty){
					cu.set(key, value);
				} else if(!value.toString().isEmpty()){
					cu.set(key, value);
				}
			}
			// WHERE
			List<Predicate> list = SpecificationUtil.builderPredicate(root, cb, new ArrayList<String>(), entityCond, false, 1);
			cu.where(cb.and(list.toArray(new Predicate[list.size()])));
			// 开启事物/提交
			em.getTransaction().begin();
			total = em.createQuery(cu).executeUpdate();
			em.getTransaction().commit();
			
			return total + "," + Result.SUCCESS.name();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.FAILED.name();
		} finally {
			em.clear();
			em.close();
		}
	}
	
	/**
	 * TODO
	 * 根据条件批量删除数据
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public String deleteAll(E entity) {
		long total = this.count(entity);
		if(total < 1) {
			return Result.NOTHING.name();
		}
		
		EntityManager em = this.getEntityManager();
		try {
			@SuppressWarnings("unchecked")
			Class<E> clazz = (Class<E>) entity.getClass();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaDelete<E> cd = cb.createCriteriaDelete(clazz);
			Root<E> root = cd.from(clazz);
			// 查询条件与排序
			List<Predicate> list = SpecificationUtil.builderPredicate(root, cb, new ArrayList<String>(), entity, false, 1);
			cd.where(cb.and(list.toArray(new Predicate[list.size()])));
			// 开启事物/提交
			em.getTransaction().begin();
			total = em.createQuery(cd).executeUpdate();
			em.getTransaction().commit();
			
			return total + "," + Result.SUCCESS.name();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.FAILED.name();
		} finally {
			em.clear();
			em.close();
		}
	}
	
	/**
	 * TODO
	 * 执行SQL语句
	 * @param SQL
	 * @return
	 */
	public String executeUpdate(String SQL){
		EntityManager em = this.getEntityManager();
		try {
			em.getTransaction().begin();
			int count = em.createNativeQuery(SQL).executeUpdate();
			em.getTransaction().commit();
			return count + "," + Result.SUCCESS.name();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return Result.FAILED.name();
		} finally {
			em.clear();
			em.close();
		}
	}
}