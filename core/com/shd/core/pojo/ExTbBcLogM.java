package com.shd.core.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;



@Entity
@Table(name = "EX_TB_BC_LOG_M", schema = "dbo")
public class ExTbBcLogM implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long LOG_M_ID;

	private String BC_M_ID;
	private String USER_ID;
	private String CONTENT;
	private Timestamp CREATED_TIME;
	private String ACTION;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "LOG_M_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getLOG_M_ID() {
		return LOG_M_ID;
	}
	public void setLOG_M_ID(Long lOG_M_ID) {
		LOG_M_ID = lOG_M_ID;
	}
	
	@Column(name = "BC_M_ID", length = 40)
	public String getBC_M_ID() {
		return BC_M_ID;
	}
	public void setBC_M_ID(String bC_M_ID) {
		BC_M_ID = bC_M_ID;
	}
	
	@Column(name = "USER_ID", length = 10)
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	@Column(name = "CREATED_TIME")
	public Timestamp getCREATED_TIME() {
		return CREATED_TIME;
	}
	public void setCREATED_TIME(Timestamp cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}
	
	@Column(name = "CONTENT", length = 1000)
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	@Column(name = "ACTION", length = 1)
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	
	
	
}
