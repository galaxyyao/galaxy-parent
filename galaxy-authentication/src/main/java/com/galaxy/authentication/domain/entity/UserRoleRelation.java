package com.galaxy.authentication.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Entity
@DynamicUpdate
@Table(name = "TUSERROLERELATION")
public class UserRoleRelation {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "C_USERROLERELATIONID")
	private String userRoleRelationId;

	@Column(name = "C_USERCODE")
	private String userCode;

	@Column(name = "C_ROLECODE")
	private String roleCode;

	@Column(name = "C_CREATEDBYID")
	private String createdById;

	@Column(name = "D_CREATEDTIME")
	private LocalDateTime createdTime;

	@Column(name = "C_LASTMODIFIEDBYID")
	private String lastModifiedById;

	@Column(name = "D_LASTMODIFIEDTIME")
	private LocalDateTime lastModifiedTime;

	public String getUserRoleRelationId() {
		return userRoleRelationId;
	}
	public void setUserRoleRelationId(String userRoleRelationId) {
		this.userRoleRelationId = userRoleRelationId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	public String getLastModifiedById() {
		return lastModifiedById;
	}
	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}
	public LocalDateTime getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}
