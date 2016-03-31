package com.shd.core.enums;

public enum DeptCodeEnum {

	DeptLevel50("50"), 
	DeptLevel60("60"), 
	DeptLevel70("70"), 
	DeptLevel80("80"), 
	DeptLevel90("90"); 
	
	
	private String index; 

	DeptCodeEnum(String idx) { 
        this.index = idx; 
    } 

    public String getIndex() { 
        return index; 
    }  
}
