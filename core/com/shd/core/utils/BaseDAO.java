package com.shd.core.utils;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * 自制的 DAO(和 POJO 無關)都要繼承這個 BaseDAO
 */
public abstract class BaseDAO {

	@Autowired
	@Qualifier("imConfiguration")
	protected Properties imConfiguration;
	
	//private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * do flust
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * for HQL
	 * 
	 * @param name
	 * @return
	 */
	protected Query getNamedQuery(String name) {
		return getSession().getNamedQuery(name);
	}
	
	/**
	 * 
	 * @param queryString
	 * @return
	 * @author Johnson.Chen
	 */
	protected SQLQuery getNameQueryString(String queryString) {
		return getSession().createSQLQuery(queryString);
	}
	
	protected Query getNamedQuery(String name, Integer maxResults) {
		Query query = getNamedQuery(name);
		
		if( maxResults != null )
			query.setMaxResults(maxResults);
		
		return query;
	}

	/**
	 * for Native SQL
	 * 
	 * @param name
	 * @return
	 */
	protected SQLQuery getNamedSQLQuery(String name) {
		return (SQLQuery) getNamedQuery(name);
	}
	
	protected SQLQuery getNamedSQLString(String queryString) {
		return (SQLQuery) getNameQueryString(queryString);
	}

	/**
	 * 處理分頁
	 * 
	 * @param currentPage 目前頁碼
	 * @param pageSize 每頁筆數
	 * @param query the instance of the Query
	 * @return a object of Pagination
	 */
	@SuppressWarnings("rawtypes")
	protected Pagination processPagination(int currentPage, int pageSize, Query query) {
		Validate.notNull(query, "The instance of Query is null");
		Validate.isTrue(currentPage > 0, "The currentPage has to be greate than 0");
		Validate.isTrue(pageSize > 0, "The pageSize has to be greate than 0");
		ScrollableResults scrollableResults = query.scroll();
		query.setFirstResult((currentPage - 1) * pageSize);
		query.setMaxResults(pageSize + 1);
		scrollableResults.last();		
		Pagination pagination = new Pagination(query.list(), currentPage, pageSize, scrollableResults.getRowNumber() + 1);
		
		scrollableResults.close();
		scrollableResults = null;
		
		return pagination;
	}
	
	/**
	 * 處理分頁()
	 * @param currentPage 目前頁碼
	 * @param pageSize 每頁筆數
	 * @param query1 
	 * @param query2 處理總筆數
	 * @return
	 * @author Johnson
	 */
	protected Pagination processPagination(int currentPage, int pageSize, Query query1,Query query2) {
		Validate.notNull(query1, "The instance of Query1 is null");
		Validate.notNull(query2, "The instance of Query2 is null");
		Validate.isTrue(currentPage > 0, "The currentPage has to be greate than 0");
		Validate.isTrue(pageSize > 0, "The pageSize has to be greate than 0");
		query1.setFirstResult((currentPage - 1) * pageSize);
		query1.setMaxResults(pageSize + 1);		
		int totalRow=(Integer) query2.uniqueResult();
		Pagination pagination = new Pagination(query1.list(), currentPage, pageSize ,(totalRow));
		return pagination;
	}

	/**
	 * 處理分頁(預設每頁為10筆)
	 * 
	 * @param currentPage 目前頁碼
	 * @param query the instance of the Query
	 * @return a object of Pagination
	 */
	@SuppressWarnings("rawtypes")
	protected Pagination processPagination(int currentPage, Query query) {
		return processPagination(currentPage, 10, query);
	}

	/**
	 * get Hibernate session
	 * 
	 * @return
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 
	 * with like 
	 * 
	 * @param o
	 * @return
	 */
	protected String like(Object o) {
		return ((o != null && StringUtils.isNotBlank(o.toString())) ? "%" + o.toString() + "%" : null);
	}
	
	protected String like(Object o, String assignStr) {
		return ((o != null && StringUtils.isNotBlank(o.toString())) ? "%" + o.toString() + "%" : assignStr);
	}

}
