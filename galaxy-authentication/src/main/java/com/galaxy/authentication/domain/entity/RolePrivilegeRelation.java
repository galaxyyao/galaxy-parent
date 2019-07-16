package com.galaxy.authentication.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicUpdate
@Table(name = "TROLEPRIVILEGERELATION")
public class RolePrivilegeRelation {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "C_ROLEPRIVILEGERELATIONID")
	private String rolePrivilegeRelationid;
	@Column(name = "C_ROLECODE")
	private String roleCode;
	@Column(name = "C_PRIVILEGEFULLCODE")
	private String privilegeFullCode;
	@Column(name = "C_ISDELETED")
	private String isDeleted;
	@Column(name = "C_CREATEDBYID")
	private String createdById;
	@Column(name = "D_CREATEDTIME")
	private LocalDateTime createdTime;
	@Column(name = "C_LASTMODIFIEDBYID")
	private String lastModifiedById;
	@Column(name = "D_LASTMODIFIEDTIME")
	private LocalDateTime lastModifiedTime;

	public String getRolePrivilegeRelationid() {
		return rolePrivilegeRelationid;
	}

	public void setRolePrivilegeRelationid(String rolePrivilegeRelationid) {
		this.rolePrivilegeRelationid = rolePrivilegeRelationid;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getPrivilegeFullCode() {
		return privilegeFullCode;
	}

	public void setPrivilegeFullCode(String privilegeFullCode) {
		this.privilegeFullCode = privilegeFullCode;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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
