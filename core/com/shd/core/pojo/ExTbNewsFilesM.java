package com.shd.core.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "EX_TB_NEWS_FILES_M", schema = "dbo")
public class ExTbNewsFilesM implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long FILE_M_ID;
	private String FILE_ID;
	private String FILE_NAME;
	private String FILE_TYPE;
	private Long NEWS_ID;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "FILE_M_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getFILE_M_ID() {
		return FILE_M_ID;
	}
	public void setFILE_M_ID(Long fILE_M_ID) {
		FILE_M_ID = fILE_M_ID;
	}
	
	@Column(name = "FILE_ID", length = 40)
	public String getFILE_ID() {
		return FILE_ID;
	}
	public void setFILE_ID(String fILE_ID) {
		FILE_ID = fILE_ID;
	}
	
	@Column(name = "FILE_NAME", length = 100)
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	
	
	@Column(name = "FILE_TYPE", length = 10)
	public String getFILE_TYPE() {
		return FILE_TYPE;
	}
	public void setFILE_TYPE(String fILE_TYPE) {
		FILE_TYPE = fILE_TYPE;
	}
	
	@Column(name = "NEWS_ID")
	public Long getNEWS_ID() {
		return NEWS_ID;
	}
	public void setNEWS_ID(Long nEWS_ID) {
		NEWS_ID = nEWS_ID;
	}
	
	
	
	

}
