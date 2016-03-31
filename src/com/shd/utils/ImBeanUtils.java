package com.shd.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.lang.Validate;

public class ImBeanUtils {

	/**
	 * <pre>
	 * bean copy properties
	 * 目前只有設定下列當來源為 null 時, 目的也會存成 null
	 * 1. Integer
	 * 2. Double
	 * </pre>
	 * 
	 * @param dest
	 * @param source
	 * @throws BaseException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static void copyProperties(Object dest, Object source) throws IllegalAccessException, InvocationTargetException {
		Validate.notNull(dest);
		Validate.notNull(source);

		ConvertUtils.register( new IntegerConverter( null ), Integer.class );
		ConvertUtils.register( new LongConverter( null ), Long.class );
		ConvertUtils.register( new FloatConverter( null ), Float.class );
		ConvertUtils.register( new DoubleConverter( null ), Double.class );
		ConvertUtils.register( new BigDecimalConverter( null ), BigDecimal.class );
		ConvertUtils.register( new DateConverter( null ), Date.class );
		ConvertUtils.register( new SqlTimestampConverter( null ), Timestamp.class );

		BeanUtils.copyProperties(dest, source);
	}
	
	
}
