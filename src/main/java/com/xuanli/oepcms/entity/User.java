package com.xuanli.oepcms.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    /**用户名*/
    private String username;
    private String password;
    private String desc;
    /**性别*/
    private String gender;
    /**校区id*/
    private String schoolId;
    /**班级id*/
    private String clasId;
    /**手机号*/
    private String mobile;
    /**盐值*/
    private String salt;
    /**验证码*/
    private String captcha;
    /**新增用户*/
	private String createId;
	/**新增时间*/
	private Date createDate;
	/**更新用户*/
	private String updateId;
	/**更新时间*/
	private Date updateDate;
	/**状态*/
	private String enableFlag;
	
    public User() {
    }
    

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.desc = user.getDesc();
        this.gender = user.getGender();
        this.schoolId = user.getSchoolId();
        this.clasId = user.getClasId();
        this.mobile = user.getMobile();
        this.salt = user.getSalt();
        this.captcha = user.getCaptcha();
        this.createId = user.getCreateId();
        this.createDate = user.getCreateDate();
        this.updateId = user.getCreateId();
        this.updateDate = user.getUpdateDate();
        this.enableFlag = user.getEnableFlag();
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", desc=" + desc + ", gender="
				+ gender + ", schoolId=" + schoolId + ", clasId=" + clasId + ", mobile=" + mobile + ", salt=" + salt
				+ ", captcha=" + captcha + ", createId=" + createId + ", createDate=" + createDate + ", updateId="
				+ updateId + ", updateDate=" + updateDate + ", enableFlag=" + enableFlag + "]";
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
