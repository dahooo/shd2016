package com.shd.core.entity;

import java.sql.Timestamp;

import com.shd.utils.ImDateStringUtils;



/**
 * 電子公佈欄/新聞
 * @author Roy
 *
 */
public class ExTbNewsModelMEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String DeptCode;
	private String DeptCname;
	private String ParentDeptNo;
	private String DeptLevel;
	
	private String empCname;
	
	private Long NEWS_M_ID;
	private String TOPIC;
	private Long NEWS_TYPE;
	private Timestamp CREATED_DATE;
	private Timestamp END_DATE;
	private String CREATED_ID;
	private String SOURCE_URL;
	private String CONTENT;
	private Timestamp UPDATED_DATE;
	private String UPDATED_ID;
	
	private String NEWS_TYPE_NAME;
	
	private Long NEWSTYPE_ID;
	private String NAME;
	
	private String END_DATE_STR;
	
	
	private String uploadFiles;
	
	private Long FILE_M_ID;
	private String FILE_ID;
	private String FILE_NAME;
	private String FILE_TYPE;
	
	
	private String relativeDeptNode;
	private String selectedDeptNode;
	

	public Long getFILE_M_ID() {
		return FILE_M_ID;
	}
	public void setFILE_M_ID(Long fILE_M_ID) {
		FILE_M_ID = fILE_M_ID;
	}
	public String getFILE_ID() {
		return FILE_ID;
	}
	public void setFILE_ID(String fILE_ID) {
		FILE_ID = fILE_ID;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public String getFILE_TYPE() {
		return FILE_TYPE;
	}
	public void setFILE_TYPE(String fILE_TYPE) {
		FILE_TYPE = fILE_TYPE;
	}
	public String getUploadFiles() {
		return uploadFiles;
	}
	public void setUploadFiles(String uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	public Long getNEWSTYPE_ID() {
		return NEWSTYPE_ID;
	}
	public void setNEWSTYPE_ID(Long nEWSTYPE_ID) {
		NEWSTYPE_ID = nEWSTYPE_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getEmpCname() {
		return empCname;
	}
	public void setEmpCname(String empCname) {
		this.empCname = empCname;
	}
	public String getUPDATED_DATEStr() {
		return ImDateStringUtils.formatFullDate(UPDATED_DATE);
	}
	public String getCREATED_DATEStr() {
		return ImDateStringUtils.formatDate(CREATED_DATE, "yyyy-MM-dd");
	}
	
	public String getEND_DATEStr() {
		return ImDateStringUtils.formatDate(END_DATE, "yyyy-MM-dd");
	}
	
	
	

	public String getEND_DATE_STR() {
		return END_DATE_STR;
	}
	public void setEND_DATE_STR(String eND_DATE_STR) {
		END_DATE_STR = eND_DATE_STR;
	}
	public String getNEWS_TYPE_NAME() {
		return NEWS_TYPE_NAME;
	}
	public void setNEWS_TYPE_NAME(String nEWS_TYPE_NAME) {
		NEWS_TYPE_NAME = nEWS_TYPE_NAME;
	}
	public Long getNEWS_M_ID() {
		return NEWS_M_ID;
	}
	public void setNEWS_M_ID(Long nEWS_M_ID) {
		NEWS_M_ID = nEWS_M_ID;
	}
	public String getTOPIC() {
		return TOPIC;
	}
	public void setTOPIC(String tOPIC) {
		TOPIC = tOPIC;
	}
	public Long getNEWS_TYPE() {
		return NEWS_TYPE;
	}
	public void setNEWS_TYPE(Long nEWS_TYPE) {
		NEWS_TYPE = nEWS_TYPE;
	}
	public Timestamp getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(Timestamp cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public Timestamp getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(Timestamp eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getCREATED_ID() {
		return CREATED_ID;
	}
	public void setCREATED_ID(String cREATED_ID) {
		CREATED_ID = cREATED_ID;
	}
	public String getSOURCE_URL() {
		return SOURCE_URL;
	}
	public void setSOURCE_URL(String sOURCE_URL) {
		SOURCE_URL = sOURCE_URL;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
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
	public String getDeptCode() {
		return DeptCode;
	}
	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}
	public String getDeptCname() {
		return DeptCname;
	}
	public void setDeptCname(String deptCname) {
		DeptCname = deptCname;
	}
	
	public String getParentDeptNo() {
		return ParentDeptNo;
	}
	public void setParentDeptNo(String parentDeptNo) {
		ParentDeptNo = parentDeptNo;
	}
	public String getDeptLevel() {
		return DeptLevel;
	}
	public void setDeptLevel(String deptLevel) {
		DeptLevel = deptLevel;
	}
	public String getRelativeDeptNode() {
		return relativeDeptNode;
	}
	public void setRelativeDeptNode(String relativeDeptNode) {
		this.relativeDeptNode = relativeDeptNode;
	}
	public String getSelectedDeptNode() {
		return selectedDeptNode;
	}
	public void setSelectedDeptNode(String selectedDeptNode) {
		this.selectedDeptNode = selectedDeptNode;
	}

	
	
}
