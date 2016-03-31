package com.shd.core.entity;

import java.sql.Timestamp;

import com.shd.core.enums.BcActionEnum;
import com.shd.utils.ImDateStringUtils;

/**
 * 好事曆
 * @author Roy
 *
 */
public class ExTbBcModelMEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String BC_M_ID;
	private String NAME;
	private String SENIORITY;
	private String BC_GROUP;
	private String STORE_NAME;
	private Timestamp AWARD_DATE;
	private Timestamp CREATED_DATE;
	private String CREATED_ID;
	private Timestamp UPDATED_DATE;
	private String UPDATED_ID;
	private String TOPIC;
	private String PIC_NAME;
	private String NEW_UPLOAD_PIC_NAME;
	private String EDIT_STATUS;
	private String INTRODUCTION;
	
	private String positionCode;
	private String empNo;
	private String empCname;
	private String deptCode;
	private String roleLevel;
	
	private String AWARD_DATE_STR;
	private String CREATED_DATE_STR;
	
	
	//LOG
	private String HISTORY_COMMENT;
	private String USER_ID;
	private String CONTENT;
	private String ACTION;
	private Timestamp CREATED_TIME;
	
	
	public String getCREATED_TIMEStr() {
		return ImDateStringUtils.formatFullDate(CREATED_TIME);
	}
	public String getAWARD_DATEStr() {
		return ImDateStringUtils.formatDate(AWARD_DATE, "yyyy-MM-dd");
	}
	public String getUPDATED_DATEStr() {
		return ImDateStringUtils.formatFullDate(UPDATED_DATE);
	}
	public String getCREATED_DATEStr() {
		return ImDateStringUtils.formatDate(CREATED_DATE, "yyyy-MM-dd");
	}
	
	
	public String getACTIONStr() {
		return actionMapping(ACTION);
	}
	
	
	public String getEDIT_STATUSStr() {
		return actionMapping(EDIT_STATUS);
	}
	
	//========
	public String getAWARD_DATE_STR() {
		return AWARD_DATE_STR;
	}
	public String getNEW_UPLOAD_PIC_NAME() {
		return NEW_UPLOAD_PIC_NAME;
	}
	public void setNEW_UPLOAD_PIC_NAME(String nEW_UPLOAD_PIC_NAME) {
		NEW_UPLOAD_PIC_NAME = nEW_UPLOAD_PIC_NAME;
	}
	public String getBC_M_ID() {
		return BC_M_ID;
	}
	public void setBC_M_ID(String bC_M_ID) {
		BC_M_ID = bC_M_ID;
	}
	public void setAWARD_DATE_STR(String aWARD_DATE_STR) {
		AWARD_DATE_STR = aWARD_DATE_STR;
	}
	public String getCREATED_DATE_STR() {
		return CREATED_DATE_STR;
	}
	public void setCREATED_DATE_STR(String cREATED_DATE_STR) {
		CREATED_DATE_STR = cREATED_DATE_STR;
	}
	
	
	
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getSENIORITY() {
		return SENIORITY;
	}
	public void setSENIORITY(String sENIORITY) {
		SENIORITY = sENIORITY;
	}
	public String getBC_GROUP() {
		return BC_GROUP;
	}
	public void setBC_GROUP(String bC_GROUP) {
		BC_GROUP = bC_GROUP;
	}
	public String getSTORE_NAME() {
		return STORE_NAME;
	}
	public void setSTORE_NAME(String sTORE_NAME) {
		STORE_NAME = sTORE_NAME;
	}
	public Timestamp getAWARD_DATE() {
		return AWARD_DATE;
	}
	public void setAWARD_DATE(Timestamp aWARD_DATE) {
		AWARD_DATE = aWARD_DATE;
	}
	public Timestamp getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(Timestamp cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public String getCREATED_ID() {
		return CREATED_ID;
	}
	public void setCREATED_ID(String cREATED_ID) {
		CREATED_ID = cREATED_ID;
	}
	public Timestamp getUPDATED_DATE() {
		return UPDATED_DATE;
	}
	public void setUPDATED_DATE(Timestamp uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}
	public String getUPDATED_ID() {
		return UPDATED_ID;
	}
	public void setUPDATED_ID(String uPDATED_ID) {
		UPDATED_ID = uPDATED_ID;
	}
	public String getTOPIC() {
		return TOPIC;
	}
	public void setTOPIC(String tOPIC) {
		TOPIC = tOPIC;
	}
	public String getEDIT_STATUS() {
		return EDIT_STATUS;
	}
	public void setEDIT_STATUS(String eDIT_STATUS) {
		EDIT_STATUS = eDIT_STATUS;
	}
	public String getINTRODUCTION() {
		return INTRODUCTION;
	}
	public void setINTRODUCTION(String iNTRODUCTION) {
		INTRODUCTION = iNTRODUCTION;
	}
	public String getPIC_NAME() {
		return PIC_NAME;
	}
	public void setPIC_NAME(String pIC_NAME) {
		PIC_NAME = pIC_NAME;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpCname() {
		return empCname;
	}
	public void setEmpCname(String empCname) {
		this.empCname = empCname;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	public Timestamp getCREATED_TIME() {
		return CREATED_TIME;
	}
	public void setCREATED_TIME(Timestamp cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}
	
	private String actionMapping(String ACTION) {
		if(BcActionEnum.action_1.getValue().equals(ACTION)){
			return BcActionEnum.action_1.getString();
		}else if(BcActionEnum.action_2.getValue().equals(ACTION)){
			return BcActionEnum.action_2.getString();
		}else if(BcActionEnum.action_3.getValue().equals(ACTION)){
			return BcActionEnum.action_3.getString();
		}else if(BcActionEnum.action_4.getValue().equals(ACTION)){
			return BcActionEnum.action_4.getString();
		}else if(BcActionEnum.action_5.getValue().equals(ACTION)){
			return BcActionEnum.action_5.getString();
		}else if(BcActionEnum.action_6.getValue().equals(ACTION)){
			return BcActionEnum.action_6.getString();
		}else if(BcActionEnum.action_7.getValue().equals(ACTION)){
			return BcActionEnum.action_7.getString();
		}else{
			return BcActionEnum.action_8.getString();
		}
	}
	public String getHISTORY_COMMENT() {
		return HISTORY_COMMENT;
	}
	public void setHISTORY_COMMENT(String hISTORY_COMMENT) {
		HISTORY_COMMENT = hISTORY_COMMENT;
	}
	
}
