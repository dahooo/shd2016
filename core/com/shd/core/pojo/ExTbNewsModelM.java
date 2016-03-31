package com.shd.core.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;



@Entity
@Table(name = "EX_TB_NEWS_MODEL_M", schema = "dbo")
public class ExTbNewsModelM implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

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
	private String DELETE_FLAG;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "NEWS_M_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getNEWS_M_ID() {
		return NEWS_M_ID;
	}
	public void setNEWS_M_ID(Long nEWS_M_ID) {
		NEWS_M_ID = nEWS_M_ID;
	}
	
	@Column(name = "TOPIC", length = 200)
	public String getTOPIC() {
		return TOPIC;
	}
	public void setTOPIC(String tOPIC) {
		TOPIC = tOPIC;
	}
	
	@Column(name = "NEWS_TYPE")
	public Long getNEWS_TYPE() {
		return NEWS_TYPE;
	}
	public void setNEWS_TYPE(Long nEWS_TYPE) {
		NEWS_TYPE = nEWS_TYPE;
	}
	
	@Column(name = "CREATED_DATE", length = 23)
	public Timestamp getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(Timestamp cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	
	@Column(name = "END_DATE", length = 23)
	public Timestamp getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(Timestamp eND_DATE) {
		END_DATE = eND_DATE;
	}
	
	@Column(name = "CREATED_ID", length = 20)
	public String getCREATED_ID() {
		return CREATED_ID;
	}
	public void setCREATED_ID(String cREATED_ID) {
		CREATED_ID = cREATED_ID;
	}
	
	@Column(name = "SOURCE_URL", length = 100)
	public String getSOURCE_URL() {
		return SOURCE_URL;
	}
	public void setSOURCE_URL(String sOURCE_URL) {
		SOURCE_URL = sOURCE_URL;
	}
	@Column(name = "CONTENT")
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	
	@Column(name = "UPDATED_DATE", length = 23)
	public Timestamp getUPDATED_DATE() {
		return UPDATED_DATE;
	}
	public void setUPDATED_DATE(Timestamp uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}
	
	@Column(name = "UPDATED_ID", length = 20)
	public String getUPDATED_ID() {
		return UPDATED_ID;
	}
	public void setUPDATED_ID(String uPDATED_ID) {
		UPDATED_ID = uPDATED_ID;
	}
	
	@Column(name = "DELETE_FLAG", length = 1)
	public String getDELETE_FLAG() {
		return DELETE_FLAG;
	}
	public void setDELETE_FLAG(String dELETE_FLAG) {
		DELETE_FLAG = dELETE_FLAG;
	}
	
	
	
	
}
