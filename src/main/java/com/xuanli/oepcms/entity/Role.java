package com.xuanli.oepcms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long roleId;
	private String name;
	private String roleSign;
	private String schoolId;
	private String clasId;
	private List<Long> menuIds;
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private String enableFlag;
	
	public Role() {};
	
	public Role(Role role) {
		super();
		this.id =role.getId();
		this.roleId = role.getRoleId();
		this.name = role.getSchoolId();
		this.schoolId = role.getSchoolId();
		this.clasId = role.getClasId();
		this.menuIds = role.getMenuIds();
		this.createId = role.getCreateId();
		this.createDate = role.getCreateDate();
		this.updateId = role.getUpdateId();
		this.updateDate = role.getUpdateDate();
		this.enableFlag = role.getEnableFlag();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleId=" + roleId + ", name=" + name + ", roleSign=" + roleSign + ", schoolId="
				+ schoolId + ", clasId=" + clasId + ", menuIds=" + menuIds + ", createId=" + createId + ", createDate="
				+ createDate + ", updateId=" + updateId + ", updateDate=" + updateDate + ", enableFlag=" + enableFlag
				+ "]";
	}
	
}
