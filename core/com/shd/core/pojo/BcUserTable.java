package com.shd.core.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "BC_USER_TABLE", schema = "dbo")
public class BcUserTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String UserID;
	private String ResetPasswordDate;
	private String IsNewEmployee;
	private String Status;
	
	private Timestamp CreatedTime;
	private Timestamp EditedTime;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "UserID", unique = true)
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	
	@Column(name = "ResetPasswordDate")
	public String getResetPasswordDate() {
		return ResetPasswordDate;
	}
	public void setResetPasswordDate(String resetPasswordDate) {
		ResetPasswordDate = resetPasswordDate;
	}
	
	@Column(name = "IsNewEmployee")
	public String getIsNewEmployee() {
		return IsNewEmployee;
	}
	public void setIsNewEmployee(String isNewEmployee) {
		IsNewEmployee = isNewEmployee;
	}
	
	@Column(name = "Status")
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	@Column(name = "CreatedTime")
	public Timestamp getCreatedTime() {
		return CreatedTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		CreatedTime = createdTime;
	}
	@Column(name = "EditedTime")
	public Timestamp getEditedTime() {
		return EditedTime;
	}
	public void setEditedTime(Timestamp editedTime) {
		EditedTime = editedTime;
	}
	
	
	
}
