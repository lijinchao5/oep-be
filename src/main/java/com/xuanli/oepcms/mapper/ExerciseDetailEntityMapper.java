package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExerciseDetailEntity;
import com.xuanli.oepcms.entity.ExerciseDetailWordEntity;
@Mapper
public interface ExerciseDetailEntityMapper {
	/**
	 * Title: deleteExerciseDetailEntity 
	 * Description:  
	 * @date 2018年3月9日 下午5:57:52
	 * @param id
	 * @return
	 */
    int deleteExerciseDetailEntity(Long id);
    /**
     * Title: insertExerciseDetailEntity 
     * Description:  
     * @date 2018年3月9日 下午5:57:55
     * @param record
     * @return
     */
    int insertExerciseDetailEntity(ExerciseDetailEntity record);
    /**
     * Title: selectById 
     * Description:  
     * @date 2018年3月9日 下午5:57:57
     * @param id
     * @return
     */
    ExerciseDetailEntity selectById(Long id);
    /**
     * Title: updateExerciseDetailEntity 
     * Description:  
     * @date 2018年3月9日 下午5:58:00
     * @param record
     * @return
     */
    int updateExerciseDetailEntity(ExerciseDetailEntity record);
	/**Title: getExerciseDetail 
	 * Description:  
	 * @date 2018年3月12日 上午11:05:14
	 * @param exerciseDetail
	 * @return  
	 */
	ExerciseDetailEntity getExerciseDetail(ExerciseDetailEntity exerciseDetail);
	/**Title: getExerciseDetailEntity 
	 * Description:  
	 * @date 2018年3月12日 下午3:45:55
	 * @param wordEntity
	 * @return  
	 */
	List<ExerciseDetailEntity> getExerciseDetailEntity(ExerciseDetailWordEntity wordEntity);
    
}