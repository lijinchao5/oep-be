package com.xuanli.oepcms.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    /**学段*/
    private String studySection;
    /**年级*/
    private String grade;
    /**教材版本*/
    private String bookEdition;
    /**盐值*/
    private String salt;
    /**验证码*/
    private String captcha;
    /**新增用户*/
	private String createId;
	/**新增时间*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	/**更新用户*/
	private String updateId;
	/**更新时间*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;
	/**状态*/
	private Integer enableFlag;
	/**省份*/
	private String province;
	/**所在城市*/
	private String city;
	/**所在地区*/
	private String district;
	
    public User() {
    }

	/** 
	 * @return 返回 id 
	 */
	public Integer getId() {
		return id;
	}

	/** 
	 * @setParam 设置id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 
	 * @return 返回 username 
	 */
	public String getUsername() {
		return username;
	}

	/** 
	 * @setParam 设置username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/** 
	 * @return 返回 password 
	 */
	public String getPassword() {
		return password;
	}

	/** 
	 * @setParam 设置password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/** 
	 * @return 返回 desc 
	 */
	public String getDesc() {
		return desc;
	}

	/** 
	 * @setParam 设置desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/** 
	 * @return 返回 gender 
	 */
	public String getGender() {
		return gender;
	}

	/** 
	 * @setParam 设置gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/** 
	 * @return 返回 schoolId 
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/** 
	 * @setParam 设置schoolId
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	/** 
	 * @return 返回 clasId 
	 */
	public String getClasId() {
		return clasId;
	}

	/** 
	 * @setParam 设置clasId
	 */
	public void setClasId(String clasId) {
		this.clasId = clasId;
	}

	/** 
	 * @return 返回 mobile 
	 */
	public String getMobile() {
		return mobile;
	}

	/** 
	 * @setParam 设置mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 
	 * @return 返回 birthDate 
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/** 
	 * @setParam 设置birthDate
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/** 
	 * @return 返回 studySection 
	 */
	public String getStudySection() {
		return studySection;
	}

	/** 
	 * @setParam 设置studySection
	 */
	public void setStudySection(String studySection) {
		this.studySection = studySection;
	}

	/** 
	 * @return 返回 grade 
	 */
	public String getGrade() {
		return grade;
	}

	/** 
	 * @setParam 设置grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/** 
	 * @return 返回 bookEdition 
	 */
	public String getBookEdition() {
		return bookEdition;
	}

	/** 
	 * @setParam 设置bookEdition
	 */
	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	/** 
	 * @return 返回 salt 
	 */
	public String getSalt() {
		return salt;
	}

	/** 
	 * @setParam 设置salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/** 
	 * @return 返回 captcha 
	 */
	public String getCaptcha() {
		return captcha;
	}

	/** 
	 * @setParam 设置captcha
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	/** 
	 * @return 返回 createId 
	 */
	public String getCreateId() {
		return createId;
	}

	/** 
	 * @setParam 设置createId
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/** 
	 * @return 返回 createDate 
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 
	 * @setParam 设置createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 
	 * @return 返回 updateId 
	 */
	public String getUpdateId() {
		return updateId;
	}

	/** 
	 * @setParam 设置updateId
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	/** 
	 * @return 返回 updateDate 
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/** 
	 * @setParam 设置updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/** 
	 * @return 返回 enableFlag 
	 */
	public Integer getEnableFlag() {
		return enableFlag;
	}

	/** 
	 * @setParam 设置enableFlag
	 */
	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	/** 
	 * @return 返回 province 
	 */
	public String getProvince() {
		return province;
	}

	/** 
	 * @setParam 设置province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/** 
	 * @return 返回 city 
	 */
	public String getCity() {
		return city;
	}

	/** 
	 * @setParam 设置city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/** 
	 * @return 返回 district 
	 */
	public String getDistrict() {
		return district;
	}

	/** 
	 * @setParam 设置district
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/** 
	 * @return 返回 serialversionuid 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    

    
}
