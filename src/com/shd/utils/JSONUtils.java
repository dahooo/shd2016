package com.shd.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;

public class JSONUtils {

	private Logger logger = Logger.getLogger(getClass());

	private JsonConfig config = new JsonConfig();

	private Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * 是否使用 JsonConfig
	 */
	private boolean isUseConfig = false;

	/**
	 * 傳回到頁面的 resultKey, 可以讓頁面判斷 AJAX 是否有正確處理, 值( true | false )
	 */
	private String resultKey = "result";

	/**
	 * 傳回到頁面的 dataKey, 值存放 vo json 格式的字串
	 */
	private String dataKey = "vo";

	/**
	 * 傳回到頁面的 errorMsgKey, 當 resultKey 的值為 false, 那麼要顯示給使用者看到的訊息
	 */
	private String errorMsgKey = "errorMsg";

	public JSONUtils() {

	}

	public void put(String key, Object obj) {
		map.put(key, obj);
	}

	

	

	/**
	 * 取得 json 的字串, 使用 JSONObject
	 * 
	 * @param obj
	 * @return
	 */
	public String toStringByJSONObject(Object obj) {
		JSONObject json;

		if (isUseConfig)
			json = JSONObject.fromObject(obj, config);
		else
			json = JSONObject.fromObject(obj);

		return json.toString();
	}

	/**
	 * 取得 json 的字串, 使用 JSONArray
	 * 
	 * @param obj
	 * @return
	 */
	public String toStringByJSONArray(Object obj) {
		JSONArray json;
		if (isUseConfig)
			json = JSONArray.fromObject(obj, config);
		else
			json = JSONArray.fromObject(obj);

		return json.toString();
	}

	/**
	 * Creates a bean from a JSONObject, with a specific target class
	 * 
	 * @param source
	 * @param beanClass
	 * @return
	 */
	public Object toBean(String source, Class beanClass) {
		if (isUseConfig)
			return JSONObject.toBean(JSONObject.fromObject(source), beanClass, config);
		else
			return JSONObject.toBean(JSONObject.fromObject(source), beanClass);
	}
	
	public Object toBean(String source, Class beanClass, Map<String, Class> classMap){
		return JSONObject.toBean(JSONObject.fromObject(source), beanClass, classMap);
	}

	/**
	 * JSON 在做轉換時, 可以過濾的 property
	 * 
	 * @param propertyNames
	 */
	public void setPropertyFilter(final String[] propertyNames) {
		isUseConfig = true;

		config.setJsonPropertyFilter(new PropertyFilter() {

			public boolean apply(Object source, String name, Object value) {
				for (int i = 0; i < propertyNames.length; i++) {
					if (name.equals(propertyNames[i]))
						return true;
				}

				return false;
			}
		});
	}

	/**
	 * <pre>
	 * 當為 null 時, 可以預設要轉換的值, 如: theClass = Integer.class, value = "", 
	 * 所以 json 在轉換時, 預到 Integer 為 null 時, 會回傳 ""
	 * </pre>
	 * 
	 * @param theClass
	 * @param value
	 */
	public void registerDefaultValueProcessor(Class theClass, final Object value) {
		isUseConfig = true;

		config.registerDefaultValueProcessor(theClass, new DefaultValueProcessor() {

			@Override
			public Object getDefaultValue(Class type) {
				return value;
			}
		});
	}

}
