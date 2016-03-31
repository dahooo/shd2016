package com.shd.core.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "EX_TB_NEWS_DEPT_MAPPING_M", schema = "dbo")
public class ExTbNewsDeptMappingM implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long NEWS_M_ID;
	private String DEPT_CODE;
	private Long EX_TB_NEWS_DEPT_MAPPING_ID;
	private String SELECTED;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EX_TB_NEWS_DEPT_MAPPING_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getEX_TB_NEWS_DEPT_MAPPING_ID() {
		return EX_TB_NEWS_DEPT_MAPPING_ID;
	}
	public void setEX_TB_NEWS_DEPT_MAPPING_ID(Long eX_TB_NEWS_DEPT_MAPPING_ID) {
		EX_TB_NEWS_DEPT_MAPPING_ID = eX_TB_NEWS_DEPT_MAPPING_ID;
	}
	
	@Column(name = "NEWS_M_ID")
	public Long getNEWS_M_ID() {
		return NEWS_M_ID;
	}
	public void setNEWS_M_ID(Long nEWS_M_ID) {
		NEWS_M_ID = nEWS_M_ID;
	}
	
	@Column(name = "DEPT_CODE", length = 6)
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}
	
	@Column(name = "SELECTED", length = 1)
	public String getSELECTED() {
		return SELECTED;
	}
	public void setSELECTED(String sELECTED) {
		SELECTED = sELECTED;
	}
	
	
}
