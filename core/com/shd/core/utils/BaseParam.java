package com.shd.core.utils;

import java.sql.Timestamp;

import com.shd.utils.ImDateUtils;

public class BaseParam {

	public static Timestamp systime() {
		return ImDateUtils.getSysDateTime();
	}
	
}
