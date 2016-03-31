package com.shd.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AjaxConverter {

	/**
	 * Simple List<Bean(Non-Cascade)> Ajax Converter
	 * 
	 * @param jsonArray
	 * @param clazz
	 * @return
	 */
	public static List json2List(String jsonArray, Class clazz) {

		JSONArray ja = (JSONArray) JSONSerializer.toJSON(jsonArray);

		Iterator itor = ja.iterator();
		List list = new ArrayList();

		while (itor.hasNext()) {
			String json = ja.getString(ja.indexOf(itor.next()));
			list.add(AjaxConverter.json2Bean(json, clazz));
		}

		return list;
	}

	public static Object json2Bean(String json, Class clazz) {

		if(clazz != String.class){
			JSONObject jo = (JSONObject) JSONSerializer.toJSON(json);
			return JSONObject.toBean(jo, clazz);
		}else {
			return json;
		}
			
	}
	
	/**
     * Takes a {@code List<String>} and transforms it into a list of the
     * specified {@code clazz}.
     * 
     * @param <T>
     * @param stringValues
     *            the list of Strings to be used to create the list of the
     *            specified type
     * @param clazz
     *            must be a subclass of Number. Defines the type of the new List
     * @return
     */
    public static <T extends Number> List<T> toNumberList(List<String> stringValues, final Class<T> clazz) {
        List<T> ids = Lists.transform(stringValues, new Function<String, T>() {
            
            @Override
            public T apply(String from) {
                T retVal = null;
                if (clazz.equals(Integer.class)) {
                    retVal = (T) Integer.valueOf(from);
                } else if (clazz.equals(Long.class)) {
                    retVal = (T) Long.valueOf(from);
                } else if (clazz.equals(Float.class)) {
                    retVal = (T) Float.valueOf(from);
                } else if (clazz.equals(Double.class)) {
                    retVal = (T) Double.valueOf(from);
                } else {
                    throw new RuntimeException(String.format("Type %s is not supported (yet)", clazz.getName()));
                }
                return retVal;
            }
        });
        return ImmutableList.copyOf(ids);
    }	
    

	
}
