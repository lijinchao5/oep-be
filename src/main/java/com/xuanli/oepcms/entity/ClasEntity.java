package com.xuanli.oepcms.entity;

import java.util.Date;

import com.xuanli.oepcms.util.BasePageBean;

public class ClasEntity extends BasePageBean{
    private Long id;

    private String clasId;

    private String name;

    private Integer schoolId;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;
    
    private String grade;
    
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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasId() {
        return clasId;
    }

    public void setClasId(String clasId) {
        this.clasId = clasId == null ? null : clasId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }
}