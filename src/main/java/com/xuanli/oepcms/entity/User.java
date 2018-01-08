package com.xuanli.oepcms.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private long id;
    /**用户名*/
    private String username;
    private String password;
    private String desc;
    /**性别*/
    private String gender;
    /**校区id*/
    private String school_id;
    /**班级id*/
    private String clas_id;
    /**手机号*/
    private String mobile;
    /**验证码*/
    private String captcha;
    /**新增用户*/
	private String create_id;
	/**新增时间*/
	private Date create_date;
	/**更新用户*/
	private String update_id;
	/**更新时间*/
	private Date update_date;
	/**状态*/
	private String enable_falg;
	
    public User() {
    }
    

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.desc = user.getDesc();
        this.gender = user.getGender();
        this.school_id = user.getSchool_id();
        this.clas_id = user.getClas_id();
        this.mobile = user.getMobile();
        this.captcha = user.getCaptcha();
        this.create_id = user.getCreate_id();
        this.create_date = user.getCreate_date();
        this.update_id = user.getCreate_id();
        this.update_date = user.getUpdate_date();
        this.enable_falg = user.getEnable_falg();
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", desc=" + desc + ", gender="
				+ gender + ", school_id=" + school_id + ", clas_id=" + clas_id + ", mobile=" + mobile + ", captcha="
				+ captcha + ", create_id=" + create_id + ", create_date=" + create_date + ", update_id=" + update_id
				+ ", update_date=" + update_date + ", enable_falg=" + enable_falg + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
			return false;
		return true;
	}

}
