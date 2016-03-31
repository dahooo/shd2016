package com.shd.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.shd.utils.ImDateUtils;

public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Timestamp systime() {
		return ImDateUtils.getSysDateTime();
	}
	
}
