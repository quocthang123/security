package com.spring.jpa.sercurity.jwt.study_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "app_menu")
public class AppMenu {
    @Id
    @Column(name = "MENU_ID", nullable = false)
    private String menuId;

    @Column(name = "MENU_SEQ")
    private int menuSeq;

    @Column(name = "MENU_NAME")
    private String menuName;

    @Column(name = "PARENT_MENU")
    private String parentMenu;

    @Column(name = "URL")
    private String url;

    @Column(name = "LEVEL")
    private String level;

    @Column(name = "DELETE_YN")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean delete;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(int menuSeq) {
        this.menuSeq = menuSeq;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(String parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

}
