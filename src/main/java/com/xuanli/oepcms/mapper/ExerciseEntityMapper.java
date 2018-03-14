package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExerciseEntity;
import com.xuanli.oepcms.entity.HomeworkEntity;
@Mapper
public interface ExerciseEntityMapper {
	/**
	 * Title: deleteExerciseEntity 
	 * Description:  
	 * @date 2018年3月9日 下午5:57:14
	 * @param id
	 * @return
	 */
    int deleteExerciseEntity(Long id);
    /**
     * Title: insertExerciseEntity 
     * Description:  
     * @date 2018年3月9日 下午5:57:16
     * @param record
     * @return
     */
    int insertExerciseEntity(ExerciseEntity record);
    /**
     * Title: selectById 
     * Description:  
     * @date 2018年3月9日 下午5:57:19
     * @param id
     * @return
     */
    ExerciseEntity selectById(Long id);
    /**
     * Title: updateExerciseEntity 
     * Description:  
     * @date 2018年3月9日 下午5:57:21
     * @param record
     * @return
     */
    int updateExerciseEntity(ExerciseEntity record);
    /**
     * Title: findHomeworkPageTotal 
     * Description:  页数
     * @date 2018年3月9日 下午5:59:45
     * @param exerciseEntity
     * @return
     */
    int findHomeworkPageTotal(ExerciseEntity exerciseEntity);
    /**
     * Title: findHomeworkPage 
     * Description:  分页查询
     * @date 2018年3月9日 下午5:59:47
     * @param exerciseEntity
     * @return
     */
    List<HomeworkEntity> findHomeworkPage(ExerciseEntity exerciseEntity);
	/**Title: selectExerciseEntity 
	 * Description:  
	 * @date 2018年3月13日 下午8:02:46
	 * @param exerceseEntity
	 * @return  
	 */
	ExerciseEntity selectExerciseEntity(ExerciseEntity exerceseEntity);
    
}