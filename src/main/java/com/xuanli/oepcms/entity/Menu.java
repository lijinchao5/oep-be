package com.xuanli.oepcms.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private Long menuId;
	/**父菜单ID，一级菜单为0*/
	private Long parentId;
	/**菜单名称*/
	private String name;
	/**菜单URL*/
	private String url;
	/**授权(多个用逗号分隔，如：user:list,user:create)*/
	private String permission;
	/**类型 0：目录 1：菜单 2：按钮*/
	private Integer type;
	/**菜单图标*/
	private String icon;
	/**排序*/
	private Integer sort;
	/**创建者id*/
	private String createId;
	/**修改id*/
	private String updateId;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date updateDate;
	
	public Menu() {};
	
	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", parentId=" + parentId + ", name=" + name + ", url=" + url + ", permission="
				+ permission + ", type=" + type + ", icon=" + icon + ", sort=" + sort + ", createId=" + createId
				+ ", updateId=" + updateId + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}
	
	
}
