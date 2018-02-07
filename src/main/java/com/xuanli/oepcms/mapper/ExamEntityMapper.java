package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExamEntity;
@Mapper
public interface ExamEntityMapper {
	/**
	 * Title: deleteExamEntity 
	 * Description:   删除试卷方法
	 * @date 2018年2月6日 下午2:00:20
	 * @param id
	 * @return
	 */
    int deleteExamEntity(Long id);
    /**
     * Title: insertExamEntity 
     * Description:   添加试卷方法
     * @date 2018年2月6日 下午2:00:35
     * @param record
     * @return
     */
    int insertExamEntity(ExamEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:00:50
     * @param id
     * @return
     */
    ExamEntity selectById(Long id);
    /**
     * Title: updateExamEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:00:59
     * @param record
     * @return
     */
    int updateExamEntity(ExamEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月7日 上午10:45:24
	 */
	int findExamByPageTotal(ExamEntity examEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月7日 上午10:45:34
	 */
	List<ExamEntity> findExamByPage(ExamEntity examEntity);
}