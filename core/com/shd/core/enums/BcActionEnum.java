package com.shd.core.enums;

public enum BcActionEnum {

	action_1("新增","1"),
	action_2("退回","2"),
	action_3("同意","3"),
	action_4("作廢","4"),
	action_5("未入選","5"),
	action_6("發佈","6"),
	action_7("編輯","7"),
	action_8("無狀態","8");
	
	private final String str;
	private final String value;

	private BcActionEnum(String s,String v) {
		str = s;
		value = v;
	}

	public String getString(){
		return str;
	}
	
	public String getValue(){
		return value;
	}

	
	/*
	if("1".equals(ACTION)){
		return "新增";
	}else if("2".equals(ACTION)){
		return "退回";
	}else if("3".equals(ACTION)){
		return "同意";
	}else if("4".equals(ACTION)){
		return "作廢";
	}else if("5".equals(ACTION)){
		return "未入選";
	}else if("6".equals(ACTION)){
		return "發佈";
	}else if("7".equals(ACTION)){
		return "編輯";
	}else{
		return "無狀態";
	}*/
	
}
