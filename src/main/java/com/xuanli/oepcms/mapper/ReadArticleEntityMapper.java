package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.ReadArticleEntity;
@Mapper
public interface ReadArticleEntityMapper {
	/**
	 * Title: deleteReadArticleEntity 
	 * Description:  
	 * @date 2018年3月13日 上午10:58:48
	 * @param id
	 * @return
	 */
    int deleteReadArticleEntity(Long id);
    /**
     * Title: insertReadArticleEntity 
     * Description:  
     * @date 2018年3月13日 上午10:58:50
     * @param record
     * @return
     */
    int insertReadArticleEntity(ReadArticleEntity record);
    /**
     * Title: selectById 
     * Description:  
     * @date 2018年3月13日 上午10:58:52
     * @param id
     * @return
     */
    ReadArticleEntity selectById(Long id);
    /**
     * Title: updateReadArticleEntity 
     * Description:  
     * @date 2018年3月13日 上午10:58:54
     * @param record
     * @return
     */
    int updateReadArticleEntity(ReadArticleEntity record);
	/**Title: findHomeworkPageTotal 
	 * Description:  
	 * @date 2018年3月13日 上午11:04:37
	 * @param readArticleEntity
	 * @return  
	 */
	int findHomeworkPageTotal(ReadArticleEntity readArticleEntity);
	/**Title: findHomeworkPage 
	 * Description:  
	 * @date 2018年3月13日 上午11:04:43
	 * @param readArticleEntity
	 * @return  
	 */
	List<HomeworkEntity> findHomeworkPage(ReadArticleEntity readArticleEntity);

}