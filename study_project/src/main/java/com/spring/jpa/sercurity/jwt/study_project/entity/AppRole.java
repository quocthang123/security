package com.spring.jpa.sercurity.jwt.study_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "app_role")
public class AppRole {

    @Id
    @Column(name = "ROLE_ID")
    private int roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_NAME")
    private EAppRole roleName;

    @Column(name = "DESCRIPT")
    private String descript;

    @Column(name = "DELETE_YN")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean delete;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public EAppRole getRoleName() {
        return roleName;
    }

    public void setRoleName(EAppRole roleName) {
        this.roleName = roleName;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDeleteYn(boolean delete) {
        this.delete = delete;
    }

}
