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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Entity
@DynamicUpdate
@Table(name = "TUSER")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "C_USERID")
    private String userId;

    @Column(name = "C_USERNAME")
    private String userName;

    @Column(name = "C_USERCODE")
    private String userCode;

    @Column(name = "C_ORGFULLCODE")
    private String orgFullCode;

    @Column(name = "C_MOBILE")
    private String mobile;

    @Column(name = "C_EMAIL")
    private String email;

    @Column(name = "C_ISLOCKED")
    private String isLocked;

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

    @Column(name = "C_PASSWORD")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOrgFullCode() {
        return orgFullCode;
    }

    public void setOrgFullCode(String orgFullCode) {
        this.orgFullCode = orgFullCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
