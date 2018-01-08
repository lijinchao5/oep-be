package com.xuanli.oepcms.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleMenu {
	private Long id;
	private Long  roleId;
	private Long menuId;
	
	public RoleMenu() {};
	
	@Override
	public String toString() {
		return "RoleMenu [id=" + id + ", roleId=" + roleId + ", menuId=" + menuId + "]";
	}
	

}
