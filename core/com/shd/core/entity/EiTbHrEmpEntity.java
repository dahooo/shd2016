package com.shd.core.entity;

import java.sql.Timestamp;

import com.shd.utils.ImDateStringUtils;



/**
 * EMP
 * @author Roy
 *
 */
public class EiTbHrEmpEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String EmpNo;
	private String Birthday;
	public String getEmpNo() {
		return EmpNo;
	}
	public void setEmpNo(String empNo) {
		EmpNo = empNo;
	}
	public String getBirthday() {
		return Birthday;
	}
	public void setBirthday(String birthday) {
		Birthday = birthday;
	}
}
