package com.xuanli.oepcms.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Clas implements Serializable{

	private static final long serialVersionUID = -1416975462745369349L;

		private Integer id;

	    private String stuName;

	    private String schoolId;

	    private String clasId;

	    private String homework;

	    private String simulation;

	    private String unified;

	    private String proposition;

	    private String paper;

	    private String createId;

	    private Date createDate;

	    private String updateId;

	    private Date updateDate;

	    private String enableFlag;

		@Override
		public String toString() {
			return "Clas [id=" + id + ", stuName=" + stuName + ", schoolId=" + schoolId + ", clasId=" + clasId
					+ ", homework=" + homework + ", simulation=" + simulation + ", unified=" + unified
					+ ", proposition=" + proposition + ", paper=" + paper + ", createId=" + createId + ", createDate="
					+ createDate + ", updateId=" + updateId + ", updateDate=" + updateDate + ", enableFlag="
					+ enableFlag + "]";
		}
	
}
