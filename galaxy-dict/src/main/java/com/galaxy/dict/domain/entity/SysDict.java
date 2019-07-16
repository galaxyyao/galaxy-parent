package com.galaxy.dict.domain.entity;

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

import com.galaxy.common.annotation.Mapping;

@Entity
@DynamicUpdate
@Table(name = "TSYSDICT")
public class SysDict implements Serializable {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "C_SYSDICTID")
	private String sysDictId;
	@Mapping(name = "sysDictCode")
	@Column(name = "C_SYSDICTCODE")
	private String sysDictCode;
	@Mapping(name = "sysDictName")
	@Column(name = "C_SYSDICTNAME")
	private String sysDictName;
	@Mapping(name = "sysDictFullCode")
	@Column(name = "C_SYSDICTFULLCODE")
	private String sysDictFullCode;
	@Mapping(name = "parentFullCode")
	@Column(name = "C_PARENTFULLCODE")
	private String parentFullCode;
	@Mapping(name = "bizDictCode")
	@Column(name = "C_BIZDICTCODE")
	private String bizDictCode;
	@Mapping(name = "sortId")
	@Column(name = "L_SORTID")
	private Integer sortId;
	@Mapping(name = "sysDictDesc")
	@Column(name = "C_SYSDICTDESC")
	private String sysDictDesc;
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
	
	@Transient
	private Boolean isLeaf;
	
	@Transient
	private List<SysDict> children;
	
	public String getSysDictId() {
		return sysDictId;
	}
	public void setSysDictId(String sysDictId) {
		this.sysDictId = sysDictId;
	}
	public String getSysDictCode() {
		return sysDictCode;
	}
	public void setSysDictCode(String sysDictCode) {
		this.sysDictCode = sysDictCode;
	}
	public String getSysDictName() {
		return sysDictName;
	}
	public void setSysDictName(String sysDictName) {
		this.sysDictName = sysDictName;
	}
	public String getSysDictFullCode() {
		return sysDictFullCode;
	}
	public void setSysDictFullCode(String sysDictFullCode) {
		this.sysDictFullCode = sysDictFullCode;
	}
	public String getParentFullCode() {
		return parentFullCode;
	}
	public void setParentFullCode(String parentFullCode) {
		this.parentFullCode = parentFullCode;
	}
	public String getBizDictCode() {
		return bizDictCode;
	}
	public void setBizDictCode(String bizDictCode) {
		this.bizDictCode = bizDictCode;
	}
	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	public String getSysDictDesc() {
		return sysDictDesc;
	}
	public void setSysDictDesc(String sysDictDesc) {
		this.sysDictDesc = sysDictDesc;
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
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public List<SysDict> getChildren() {
		return children;
	}
	public void setChildren(List<SysDict> children) {
		this.children = children;
	}
}
