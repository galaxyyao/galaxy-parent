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
@Table(name = "TPRIVILEGE")
public class Privilege implements Serializable {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "C_PRIVILEGEID")
	private String privilegeId;

	@Column(name = "C_PRIVILEGECODE")
	private String privilegeCode;

	@Column(name = "C_PRIVILEGENAME")
	private String privilegeName;

	@Column(name = "C_PRIVILEGEFULLCODE")
	private String privilegeFullCode;

	@Column(name = "C_PARENTFULLCODE")
	private String parentFullCode;

	@Column(name = "C_PRIVILEGETYPE")
	private String privilegeType;

	@Column(name = "C_PRIVILEGEURL")
	private String privilegeUrl;

	@Column(name = "C_ICON")
	private String icon;

	@Column(name = "L_SORTID")
	private Integer sortId;

	@Column(name = "C_PRIVILEGEDESC")
	private String privilegeDesc;

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
	private List<Privilege> children;

	@Transient
	private String privilegeTypeText;

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeFullCode() {
		return privilegeFullCode;
	}

	public void setPrivilegeFullCode(String privilegeFullCode) {
		this.privilegeFullCode = privilegeFullCode;
	}

	public String getParentFullCode() {
		return parentFullCode;
	}

	public void setParentFullCode(String parentFullCode) {
		this.parentFullCode = parentFullCode;
	}

	public String getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}

	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public String getPrivilegeDesc() {
		return privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
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

	public List<Privilege> getChildren() {
		return children;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}

	public String getPrivilegeTypeText() {
		return privilegeTypeText;
	}

	public void setPrivilegeTypeText(String privilegeTypeText) {
		this.privilegeTypeText = privilegeTypeText;
	}
}
