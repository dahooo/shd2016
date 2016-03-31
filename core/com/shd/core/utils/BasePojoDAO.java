package com.shd.core.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;


/**
 * 存放和 entity 有關的 DAO, 如果使用的 DAO 不是和 Entity 有關, 要改使用 BaseDAO
 */
public abstract class BasePojoDAO<T extends Serializable, PK extends Serializable> extends BaseDAO {

	private static int QUERY_TYPE_DELETE = 2;

	private static int QUERY_TYPE_QUERY = 1;

	/**
	 * delete by persistent instance
	 * 
	 * @param persistentInstance a persistent instance
	 */
	public void delete(T persistentInstance) {
		getSession().delete(persistentInstance);
	}

	/**
	 * delete all data
	 * 
	 * @return count by deleting
	 */
	public int deleteAll() {
		return getSession().createQuery("delete from " + getPojoClass().getSimpleName()).executeUpdate();
	}

	/**
	 * delete data by PK
	 * 
	 * @param id
	 * @throws NotFoundException
	 */
	public void deleteByPK(PK id) throws Exception {
		delete(find(id));
	}

	/**
	 * <pre>
	 * delete data by properties
	 * 
	 * 這邊使用 "and" 和  "=", 如: propertyName1=:value1 and propertyName2=value2, 如果不是使用這種預設的方式, 
	 * 請手動製造 HQL 或 Native SQL
	 * </pre>
	 * 
	 * @param propertyNames a array of propertyName
	 * @param values a array of Objet value
	 * @return count by deleting
	 */
	public int deleteByProperties(String[] propertyNames, Object[] values) {
		Validate.notNull(propertyNames);
		Validate.isTrue(propertyNames.length > 0, "The length of propertyeNames must be great then 0");

		Validate.notNull(values);
		Validate.isTrue(values.length > 0, "The length of values must be great then 0");

		Validate.isTrue(propertyNames.length == values.length, "The length of propertyNames must be equal to values");

		return getQuery(propertyNames, values, QUERY_TYPE_DELETE).executeUpdate();
	}

	/**
	 * <pre>
	 * delete by property
	 * 
	 * 這邊使用  "=", 如: propertyName1=:value1  如果不是使用這種預設的方式, 
	 * 請手動製造 HQL 或 Native SQL
	 * </pre>
	 * 
	 * @param propertyName a property name
	 * @param value a value
	 * @return count by deleting
	 */
	public int deleteByProperty(String propertyName, Object value) {
		return deleteByProperties(new String[] { propertyName }, new Object[] { value });
	}

	/**
	 * get a persistent instance
	 * 
	 * @param id a pk
	 * @return a pojo
	 * @throws NotFoundException when can't find the pojo by id
	 */
	@SuppressWarnings("unchecked")
	public T find(PK id) throws Exception {
		Validate.notNull(id);

		Object obj = getSession().get(getPojoClass(), id);

		if (obj == null)
			throw new Exception("Can't find the pojo of " + getPojoClass().getSimpleName() + ", id=" + id);

		return (T) obj;
	}

	/**
	 * get all persistent instances
	 * 
	 * @return a List of Persistent instance
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) getQuery().list();
	}

	/**
	 * get all persistent instances by pagination
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @author Grandy
	 */
	@SuppressWarnings("unchecked")
	public Pagination<T> findAll(int currentPage, int pageSize) {
		return processPagination(currentPage, pageSize, getQuery());
	}

	/**
	 * <pre>
	 * get persistent instances by specified properties with Pagination
	 * 
	 * 這邊使用 "and" 和  "=", 如: propertyName1=:value1 and propertyName2=value2, 如果不是使用這種預設的方式, 
	 * 請手動製造 HQL 或 Native SQL
	 * </pre>
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination<T> findByProperties(int currentPage, int pageSize, String[] propertyNames, Object[] values) {
		return processPagination(currentPage, pageSize, getQuery(propertyNames, values, QUERY_TYPE_QUERY));
	}

	/**
	 * <pre>
	 * get persistent instances by specified properties
	 * 
	 * 這邊使用 "and" 和  "=", 如: propertyName1=:value1 and propertyName2=value2, 如果不是使用這種預設的方式, 
	 * 請手動製造 HQL 或 Native SQL
	 * </pre>
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperties(String[] propertyNames, Object[] values) {
		return getQuery(propertyNames, values, QUERY_TYPE_QUERY).list();
	}

	@SuppressWarnings("rawtypes")
	public Pagination findByPropertiesOrderByLongitudeAndLatitude(int currentPage, int pageSize, String[] propertyNames, Object[] values, BigDecimal longitude,
			BigDecimal latitude) {
		String queryString = getQueryString(propertyNames, values, QUERY_TYPE_QUERY);
		queryString += " and pojo.local - :locale";
		queryString += " order by square( pojo.longitude - :longitude ) + square( pojo.latitude - :latitude )";

		Query query = getSession().createQuery(queryString);

		for (int i = 0; i < values.length; i++)
			query.setParameter("value" + i, values[i]);

		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);

		return processPagination(currentPage, pageSize, query);
	}

	@SuppressWarnings("rawtypes")
	public Pagination findByPropertiesOrderByCreatedTime(int currentPage, int pageSize, String[] propertyNames, Object[] values) {
		String queryString = getQueryString(propertyNames, values, QUERY_TYPE_QUERY);
		queryString += " order by createdTime desc";

		Query query = getSession().createQuery(queryString);

		for (int i = 0; i < values.length; i++)
			query.setParameter("value" + i, values[i]);

		return processPagination(currentPage, pageSize, query);
	}

	@SuppressWarnings("rawtypes")
	public Pagination findByPropertiesOrderByLongitudeAndLatitude(int currentPage, int pageSize, String[] propertyNames, Object[] values, BigDecimal longitude,
			BigDecimal latitude, String locale) {
		String queryString = getQueryString(propertyNames, values, QUERY_TYPE_QUERY);
		queryString += " and pojo.locale =:locale";
		queryString += " order by square( pojo.longitude - :longitude ) + square( pojo.latitude - :latitude )";

		Query query = getSession().createQuery(queryString);

		for (int i = 0; i < values.length; i++)
			query.setParameter("value" + i, values[i]);

		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);
		query.setParameter("locale", locale);

		return processPagination(currentPage, pageSize, query);
	}

	/**
	 * 由近而遠
	 * 
	 * @see {@link #findByProperties(String[], Object[])}
	 * @param propertyNames
	 * @param values
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author Grandy
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertiesOrderByLongitudeAndLatitude(String[] propertyNames, Object[] values, BigDecimal longitude, BigDecimal latitude) {
		String queryString = getQueryString(propertyNames, values, QUERY_TYPE_QUERY);
		queryString += " order by square( pojo.longitude - :longitude ) + square( pojo.latitude - :latitude )";

		Query query = getSession().createQuery(queryString);

		for (int i = 0; i < values.length; i++)
			query.setParameter("value" + i, values[i]);

		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);

		return query.list();
	}

	/**
	 * <pre>
	 * get persistent instances by specified property with Pagination
	 * 
	 * 這邊使用  "=", 如: propertyName1=:value1  如果不是使用這種預設的方式, 
	 * 請手動製造 HQL 或 Native SQL
	 * </pre>
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @return a object of {@link Pagination}
	 */
	@SuppressWarnings("rawtypes")
	public Pagination findByProperty(int currentPage, int pageSize, String propertyName, Object value) {
		Validate.notNull(propertyName);
		Validate.notNull(value);

		return findByProperties(currentPage, pageSize, new String[] { propertyName }, new Object[] { value });
	}

	/**
	 * <pre>
	 * get persistent instances by specified property
	 * 
	 * 這邊使用  "=", 如: propertyName1=:value1  如果不是使用這種預設的方式, 
	 * 請手動製造 HQL 或 Native SQL
	 * </pre>
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value) {
		Validate.notNull(propertyName);
		Validate.notNull(value);

		return findByProperties(new String[] { propertyName }, new Object[] { value });
	}

	/**
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author Grandy
	 */
	@SuppressWarnings("rawtypes")
	public Pagination findByPropertyOrderByLongitudeAndLatitude(int currentPage, int pageSize, String propertyName, Object value, BigDecimal longitude,
			BigDecimal latitude) {
		Validate.notNull(propertyName);
		Validate.notNull(value);
		Validate.notNull(longitude);
		Validate.notNull(latitude);

		return findByPropertiesOrderByLongitudeAndLatitude(currentPage, pageSize, new String[] { propertyName }, new Object[] { value }, longitude, latitude);
	}

	/**
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @return
	 * @author Wayne
	 */
	@SuppressWarnings("rawtypes")
	public Pagination findByPropertyOrderByCreatedTime(int currentPage, int pageSize, String propertyName, Object value) {
		Validate.notNull(propertyName);
		Validate.notNull(value);

		return findByPropertiesOrderByCreatedTime(currentPage, pageSize, new String[] { propertyName }, new Object[] { value });
	}

	/**
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param propertyName
	 * @param value
	 * @param longitude
	 * @param latitude
	 * @param locale
	 * @return
	 * @author Wayne
	 */
	@SuppressWarnings("rawtypes")
	public Pagination findByPropertyOrderByLongitudeAndLatitude(int currentPage, int pageSize, String propertyName, Object value, BigDecimal longitude,
			BigDecimal latitude, String locale) {
		Validate.notNull(propertyName);
		Validate.notNull(value);
		Validate.notNull(longitude);
		Validate.notNull(latitude);

		return findByPropertiesOrderByLongitudeAndLatitude(currentPage, pageSize, new String[] { propertyName }, new Object[] { value }, longitude, latitude, locale);
	}

	/**
	 * order by 由近而遠
	 * 
	 * @see {@link #findByProperty(String, Object)}
	 * @param propertyName
	 * @param value
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author Grandy
	 */
	public List<T> findByPropertyOrderByLongitudeAndLatitude(String propertyName, Object value, BigDecimal longitude, BigDecimal latitude) {
		Validate.notNull(propertyName);
		Validate.notNull(value);
		Validate.notNull(longitude);
		Validate.notNull(latitude);

		return findByPropertiesOrderByLongitudeAndLatitude(new String[] { propertyName }, new Object[] { value }, longitude, latitude);
	}

	/**
	 * Convenience method to return a single instance that matches the query, or null if the query returns no results.
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * @author Grandy
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueResultByProperties(String[] propertyNames, Object[] values) {
		return (T) getQuery(propertyNames, values, QUERY_TYPE_QUERY).uniqueResult();
	}

	/**
	 * Convenience method to return a single instance that matches the query, or null if the query returns no results.
	 * 
	 * @param propertyName
	 * @param value
	 * @return the single result or <tt>null</tt>
	 * @throws NonUniqueResultException if there is more than one matching result
	 */
	public T findUniqueResultByProperty(String propertyName, Object value) {
		return findUniqueResultByProperties(new String[] { propertyName }, new Object[] { value });
	}

	/**
	 * save pojo and return PK value
	 * 
	 * @param transientInstance a pojo with transient status
	 * @return a PK
	 */
	@SuppressWarnings("unchecked")
	public PK save(T transientInstance) {
		return (PK) getSession().save(transientInstance);
	}

	/**
	 * save a list of Pojo
	 * 
	 * @param transientInstanceList
	 * @return
	 */
	public int saveList(List<T> transientInstanceList) {
		Validate.notNull(transientInstanceList);

		for (T transientInstance : transientInstanceList)
			save(transientInstance);

		return transientInstanceList.size();
	}

	/**
	 * save or update pojo
	 * 
	 * @param instance
	 */
	public void saveOrUpdate(T instance) {
		getSession().saveOrUpdate(instance);
	}

	/**
	 * get pojo class
	 * 
	 * @return
	 */
	protected abstract Class<T> getPojoClass();

	/**
	 * <pre>
	 * 處理要查詢的 Camera 由近而遠
	 * </pre>
	 * 
	 * @param query
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author Wayne
	 */
	protected Query processCameraOrderByLongitudeAndLatitude(String queryString, BigDecimal longitude, BigDecimal latitude) {
		Query query = getSession().createQuery(queryString + " order by square( camera.longitude - :longitude ) + square( camera.latitude - :latitude )");
		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);

		return query;
	}

	/**
	 * <pre>
	 * 處理要查詢的 Traffic Event 由近而遠
	 * </pre>
	 * 
	 * @param query
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author Wayne
	 */
	protected Query processEventOrderByLongitudeAndLatitude(String queryString, BigDecimal longitude, BigDecimal latitude) {
		Query query = getSession().createQuery(queryString + " order by square( roadEvent.long1 - :longitude ) + square( roadEvent.lat1 - :latitude )");
		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);

		return query;
	}

	// /**
	// * <pre>
	// * 處理要查詢的 POI 由近而遠
	// * 註: 在 hql 語法中, Poi 要命名為 poi
	// * </pre>
	// *
	// * @param query
	// * @param longitude
	// * @param latitude
	// * @param locale
	// * @return
	// * @author Wayne
	// */
	// protected Query processOrderByLongitudeAndLatitude(String queryString, BigDecimal longitude, BigDecimal latitude, String locale) {
	// Query query = getSession().createQuery(queryString + " order by square( poi.longitude - :longitude ) + square( poi.latitude - :latitude )");
	// query.setParameter("longitude", longitude);
	// query.setParameter("latitude", latitude);
	//
	// return query;
	// }

	/**
	 * <pre>
	 * 處理要查詢的 POI 由近而遠
	 * 註: 在 hql 語法中, Poi 要命名為 poi
	 * </pre>
	 * 
	 * @param query
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author Grandy
	 */
	protected Query processOrderByLongitudeAndLatitude(String queryString, BigDecimal longitude, BigDecimal latitude) {
		Query query = getSession().createQuery(queryString + " order by square( poi.longitude - :longitude ) + square( poi.latitude - :latitude )");
		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);

		return query;
	}

	private Query getQuery() {
		return getSession().createQuery("from " + getPojoClass().getSimpleName());
	}

	/**
	 * 取得 Query
	 * 
	 * @param propertyNames
	 * @param values
	 * @param queryType {@link QUERY_TYPE_QUERY} or {@link QUERY_TYPE_DELETE}
	 * @return
	 */
	private Query getQuery(String[] propertyNames, Object[] values, int queryType) {
		Query query = getSession().createQuery(getQueryString(propertyNames, values, queryType));

		for (int i = 0; i < values.length; i++)
			query.setParameter("value" + i, values[i]);

		return query;
	}

	private String getQueryString(String[] propertyNames, Object[] values, int queryType) {
		StringBuilder builder = new StringBuilder();
		if (queryType == QUERY_TYPE_DELETE)
			builder.append("delete ");

		builder.append("from " + getPojoClass().getSimpleName() + " pojo where");
		builder.append(" pojo." + propertyNames[0] + "=:value0");

		for (int i = 1; i < propertyNames.length; i++)
			builder.append(" and pojo." + propertyNames[i] + "=:value" + i);

		return builder.toString();
	}
}
