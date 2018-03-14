package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ReadSentenceEntity;
@Mapper
public interface ReadSentenceEntityMapper {
	/**
	 * Title: deleteReadSentenceEntity 
	 * Description:  
	 * @date 2018年3月13日 上午10:59:18
	 * @param id
	 * @return
	 */
    int deleteReadSentenceEntity(Long id);
    /**
     * Title: insertReadSentenceEntity 
     * Description:  
     * @date 2018年3月13日 上午10:59:21
     * @param record
     * @return
     */
    int insertReadSentenceEntity(ReadSentenceEntity record);
    /**
     * Title: selectById 
     * Description:  
     * @date 2018年3月13日 上午10:59:23
     * @param id
     * @return
     */
    ReadSentenceEntity selectById(Long id);
    /**
     * Title: updateReadSentenceEntity 
     * Description:  
     * @date 2018年3月13日 上午10:59:25
     * @param record
     * @return
     */
    int updateReadSentenceEntity(ReadSentenceEntity record);
	/**Title: getReadSentence 
	 * Description:  
	 * @date 2018年3月13日 上午11:21:50
	 * @param readSentenceEntity
	 * @return  
	 */
	ReadSentenceEntity getReadSentence(ReadSentenceEntity readSentenceEntity);

}