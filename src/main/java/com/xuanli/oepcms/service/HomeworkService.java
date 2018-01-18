/**
 * @fileName:  HomeworkService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 下午3:34:28
 */
package com.xuanli.oepcms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.HomeworkDetailEntity;
import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.HomeworkDetailEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentEntityMapper;

/**
 * @author QiaoYu
 */
@Service
public class HomeworkService {
	@Autowired
	HomeworkEntityMapper homeworkDao;
	@Autowired
	HomeworkDetailEntityMapper homeworkDetailDao;
	@Autowired
	HomeworkStudentEntityMapper homeworkStudentDao;
	@Autowired
	UserService userService;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 下午3:43:08
	 */
	public void makeHomeWork(String name, String clasId, Date endTime, String remark, String section, Long createId) {
		Date createDate = new Date();
		// 获取所有的班级id--可能是多个班级
		String[] clasIds = clasId.split(",");
		for (String classId : clasIds) {
			HomeworkEntity homeworkEntity = new HomeworkEntity();
			homeworkEntity.setClasId(Long.parseLong(classId));
			homeworkEntity.setName(name);
			homeworkEntity.setEndTime(endTime);
			homeworkEntity.setRemark(remark);
			homeworkEntity.setCreateId(createId);
			homeworkEntity.setCreateDate(createDate);
			// 保存作业信息
			homeworkDao.insertHomeworkEntity(homeworkEntity);
			Long homeworkId = homeworkEntity.getId();

			// 保存本次作业的学生
			UserEntity userEntity1 = new UserEntity();
			userEntity1.setClasId(classId);
			List<UserEntity> userEntities = userService.getClasUseStudent(userEntity1);
			List<HomeworkStudentEntity> homeworkStudentEntities = new ArrayList<HomeworkStudentEntity>();
			for (UserEntity userEntity : userEntities) {
				HomeworkStudentEntity homeworkStudentEntity = new HomeworkStudentEntity();
				homeworkStudentEntity.setCreateId(createId);
				homeworkStudentEntity.setHomeworkId(homeworkId);
				homeworkStudentEntity.setCreateDate(createDate);
				homeworkStudentEntity.setStudentId(userEntity.getId());
				homeworkStudentEntity.setWorkComplate("F");
				homeworkStudentEntities.add(homeworkStudentEntity);
			}
			if (null != homeworkStudentEntities && homeworkStudentEntities.size() > 0) {
				homeworkStudentDao.insertHomeworkStudentEntityBatch(homeworkStudentEntities);
			}

			// 保存作业详细信息
			List<HomeworkDetailEntity> homeworkDetailEntities = new ArrayList<HomeworkDetailEntity>();
			String[] sections = section.split(",");
			for (String sec : sections) {
				HomeworkDetailEntity homeworkDetailEntity = new HomeworkDetailEntity();
				homeworkDetailEntity.setCreateId(createId);
				homeworkDetailEntity.setCreateDate(createDate);
				homeworkDetailEntity.setHomeworkId(homeworkId);
				homeworkDetailEntity.setSectionDetailId(Long.parseLong(sec));
				homeworkDetailEntities.add(homeworkDetailEntity);
			}
			if (null != homeworkDetailEntities && homeworkDetailEntities.size() > 0) {
				homeworkDetailDao.inserHomeworkDetailBatch(homeworkDetailEntities);
			}
		}
	}

}
