package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
@Mapper
public interface HomeworkStudentScoreEntityMapper {
    int deleteHomeworkStudentScoreEntity(Long id);

    int insertHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);

    HomeworkStudentScoreEntity selectById(Long id);

    int updateHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午10:47:32
	 */
	List<HomeworkStudentScoreEntity> reportStudentScore(long homeworkId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 下午4:12:04
	 */
	List<HomeworkScoreBean> getStudentHomework(HomeworkScoreBean homeworkScoreBean);
}