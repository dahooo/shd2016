package com.shd.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shd.core.entity.BaseEntity;
import com.shd.core.utils.BaseParam;
import com.shd.core.utils.Pagination;
import com.shd.utils.ImBeanUtils;

/**
 * <pre>
 * 所有的 module abstract service, 都要繼承 BaseService
 * </pre>
 * 
 * @author Johnson.Chen
 */
@Service
public class BaseService {

	protected int defaultPageSize = 10;

	private Properties properties;
		
	@Autowired
	@Qualifier("imConfiguration")
	protected Properties imConfiguration;
	
	/**
	 * 
	 * @param dest
	 * @param source
	 * @throws Exception
	 * @author Grandy
	 * @throws Exception 
	 */
	protected void copyProperties(Object dest, Object source) throws Exception{
		try {
			ImBeanUtils.copyProperties(dest, source);
		} catch (Exception e) {
			throw new Exception("can't use copyProperties.");
		} 
	}

	/**
	 * 
	 * @param entity
	 * @param theClass
	 * @return
	 * @throws Exception
	 * @author Roy
	 */
	public <T> T transferEntity2Pojo(BaseEntity entity, Class<T> theClass) throws Exception {
		return transferObject(entity, theClass);
	}
	
	
	/**
	 * 
	 * @param param
	 * @param theClass
	 * @return
	 * @throws Exception
	 * @author Grandy
	 */
	public <T> T transferParam2Pojo(BaseParam param, Class<T> theClass) throws Exception {
		return transferObject(param, theClass);
	}

	/**
	 * 
	 * @param pojo
	 * @param theClass
	 * @return
	 * @throws Exception
	 * @author Grandy
	 */
	public <T> T transferPojo2ViewBean(java.io.Serializable pojo, Class<T> theClass) throws Exception {
		return transferObject(pojo, theClass);
	}
	
	public <T> List<T> listPojo2ViewBean(List pojoList, Class<T> theClass) throws Exception{
		List<T> list = new ArrayList<T>();
		for(java.io.Serializable pojo : (List<java.io.Serializable>) pojoList){
			list.add(transferPojo2ViewBean(pojo, theClass));
		}
		
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> Pagination paginPojo2ViewBean(Pagination pojoPagin, Class<T> theClass) throws Exception{
		
		List<T> list = listPojo2ViewBean(pojoPagin.getResultList(), theClass);

		pojoPagin.setResultList(list);
		
		return pojoPagin;
	}

	/**
	 * 
	 * @param object
	 * @param theClass
	 * @return
	 * @throws Exception
	 * @author Grandy
	 */
	private <T> T transferObject(Object object, Class<T> theClass) throws Exception {
		try {
			T bean = theClass.newInstance();

			if (object == null)
				return bean;

			ImBeanUtils.copyProperties(bean, object);

			return bean;
		} catch (Exception e) {
			throw new Exception("can't use copyProperties.");
		} 
	}
	


}
