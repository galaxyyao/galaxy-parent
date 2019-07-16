package com.galaxy.authentication.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Entity
@DynamicUpdate
@Table(name = "TORG")
public class Org implements Serializable {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "C_ORGID")
	private String orgId;
	
	@Column(name="C_ORGNAME")
	private String orgName;
	
	@Column(name="C_ORGCODE")
	private String orgCode;
	
	@Column(name="C_ORGFULLCODE")
	private String orgFullCode;
	
	@Column(name="C_PARENTFULLCODE")
	private String parentFullCode;
	
	@Column(name="C_ORGTYPE")
	private String orgType;

	@Column(name = "C_CREATEDBYID")
	private String createdById;
	
	@Column(name = "D_CREATEDTIME")
	private LocalDateTime createdTime;
	
	@Column(name = "C_LASTMODIFIEDBYID")
	private String lastModifiedById;
	
	@Column(name = "D_LASTMODIFIEDTIME")
	private LocalDateTime lastModifiedTime;
	
	@Transient
	private Boolean isLeaf;
	
	@Transient
	private List<Org> children;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgFullCode() {
		return orgFullCode;
	}

	public void setOrgFullCode(String orgFullCode) {
		this.orgFullCode = orgFullCode;
	}
	
	public String getParentFullCode() {
		return parentFullCode;
	}

	public void setParentFullCode(String parentFullCode) {
		this.parentFullCode = parentFullCode;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
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

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public List<Org> getChildren() {
		return children;
	}

	public void setChildren(List<Org> children) {
		this.children = children;
	}
}
