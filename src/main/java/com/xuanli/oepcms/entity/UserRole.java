package com.xuanli.oepcms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRole {
    private Long id;
    private Long userId;
    private Long roleId;
    
    public UserRole() {};
    
	@Override
	public String toString() {
		return "UserRole [id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
	}


}
