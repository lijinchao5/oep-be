/**
 * 
 */
package com.xuanli.oepcms.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.entity.ExerciseDetailEntity;
import com.xuanli.oepcms.entity.ExerciseDetailWordEntity;
import com.xuanli.oepcms.entity.ExerciseEntity;
import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.ReadArticleEntity;
import com.xuanli.oepcms.entity.ReadSentenceEntity;
import com.xuanli.oepcms.mapper.ExerciseDetailEntityMapper;
import com.xuanli.oepcms.mapper.ExerciseDetailWordEntityMapper;
import com.xuanli.oepcms.mapper.ExerciseEntityMapper;
import com.xuanli.oepcms.mapper.ReadArticleEntityMapper;
import com.xuanli.oepcms.mapper.ReadSentenceEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author lijinchao
 * @date 2018年3月9日 下午4:23:50
 */
@Service
public class ExerciseService extends BaseService {
	@Autowired
	ExerciseEntityMapper exerciseDao;
	@Autowired
	ReadArticleEntityMapper readArticleDao;
	@Autowired
	ReadSentenceEntityMapper readSentenceDao;
	@Autowired
	ExerciseDetailEntityMapper exerciseDetailDao;
	@Autowired
	ExerciseDetailWordEntityMapper exerciseDetailWordDao;
	@Autowired
	YunZhiSDK yunZhiSDK;

	/**
	 * Title: findReadArticlePage Description: 练习文章列表
	 * 
	 * @date 2018年3月13日 上午11:29:46
	 * @param readArticleEntity
	 * @param pageBean
	 */
	public void findReadArticlePage(ReadArticleEntity readArticleEntity, PageBean pageBean) {
		int findReadArticlePageTotal = readArticleDao.findReadArticlePageTotal(readArticleEntity);
		pageBean.setTotal(findReadArticlePageTotal);
		readArticleEntity.setStart(pageBean.getRowFrom());
		readArticleEntity.setEnd(pageBean.getPageSize());
		List<ReadArticleEntity> findReadArticlePage = readArticleDao.findReadArticlePage(readArticleEntity);
		pageBean.setRows(findReadArticlePage);
	}

	/**
	 * Title: getReadSentence Description: 文章段落
	 * 
	 * @date 2018年3月13日 上午11:30:08
	 * @param articleId
	 * @param studentId
	 * @return
	 */
	public ReadSentenceEntity getReadSentence(Long articleId, Long studentId) {
		ReadSentenceEntity readSentenceEntity = new ReadSentenceEntity();
		readSentenceEntity.setArticleId(articleId);
		readSentenceEntity.setStudentId(studentId);
		return readSentenceDao.getReadSentence(readSentenceEntity);
	}

	/**
	 * Title: doExercise Description: 做练习
	 * 
	 * @date 2018年3月13日 上午11:36:08
	 * @param studentId
	 * @param articleId
	 * @param sentenceId
	 * @param file
	 * @return
	 */
	public String doExercise(Long studentId, Long articleId, Long sentenceId, String file) {
		ExerciseDetailEntity exerciseDetailEntity = new ExerciseDetailEntity();
		exerciseDetailEntity.setStudentId(studentId);
		exerciseDetailEntity.setCreateId(studentId);
		exerciseDetailEntity.setCreateDate(new Date());
		exerciseDetailEntity.setUpdateDate(new Date());
		exerciseDetailEntity.setEnableFlag("T");
		exerciseDetailEntity.setStudentAudioPath(file);
		exerciseDetailEntity.setArticleId(articleId);
		exerciseDetailEntity.setReadSentenceId(sentenceId);
		// 如果作业已做过，更新
		ExerciseDetailEntity exerciseDetailEntity1 = exerciseDetailDao.selectExerciseDetailEntity(exerciseDetailEntity);
		if (null != exerciseDetailEntity1) {
			exerciseDetailEntity.setId(exerciseDetailEntity1.getId());
			exerciseDetailDao.updateExerciseDetailEntity(exerciseDetailEntity);
		} else {
			exerciseDetailDao.insertExerciseDetailEntity(exerciseDetailEntity);
		}

		ReadSentenceEntity readSentenceEntity = readSentenceDao.selectById(sentenceId);
		String text = readSentenceEntity.getSentenceCont();
		String json = yunZhiSDK.generatorExerciseScore(text, file);
		if (null == json || json.trim().equals("")) {
			System.out.println(readSentenceEntity.getId() + "---出现问题,不能计算");
		} else {
			YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
			System.out.println(JSON.toJSONString(yunZhiBean));
			// 开始分析作业分数并且更新到数据库中
			// 设置分数
			exerciseDetailEntity.setScore(yunZhiBean.getScore());
			if (null != exerciseDetailEntity.getStudentAudioPath()) {
				// 这里有句子的流畅度完整度流利度
				List<YunZhiline> yunZhilines = yunZhiBean.getLines();
				if (null != yunZhilines && yunZhilines.size() > 0) {
					YunZhiline yunZhiline = yunZhilines.get(0);
					exerciseDetailEntity.setFluency(yunZhiline.getFluency());
					exerciseDetailEntity.setIntegrity(yunZhiline.getIntegrity());
					exerciseDetailEntity.setPronunciation(yunZhiline.getPronunciation());
					// 删除word评分
					ExerciseDetailWordEntity exercisedetailWordEntity = new ExerciseDetailWordEntity();
					exercisedetailWordEntity.setArticleId(articleId);
					exercisedetailWordEntity.setStudentId(studentId);
					exercisedetailWordEntity.setSentenceId(sentenceId);
					exerciseDetailWordDao.deleteDetailWordEntity(exercisedetailWordEntity);
					for (YunZhiline line : yunZhilines) {
						List<YunZhiWords> yunZhiSubWords = line.getWords();
						for (YunZhiWords word : yunZhiSubWords) {
							double score = word.getScore();
							String text1 = word.getText();
							ExerciseDetailWordEntity detailWordEntity = new ExerciseDetailWordEntity();
							detailWordEntity.setArticleId(articleId);
							detailWordEntity.setSentenceId(sentenceId);
							detailWordEntity.setDetailId(exerciseDetailEntity.getId());
							detailWordEntity.setStudentId(studentId);
							detailWordEntity.setWord(text1);
							double sc = score * 10;
							sc = new BigDecimal(sc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
							detailWordEntity.setScore(sc);
							exerciseDetailWordDao.insertExerciseDetailWordEntity(detailWordEntity);
						}
					}
				}
			}
		}
		int resultCount = exerciseDetailDao.updateExerciseDetailEntity(exerciseDetailEntity);
		if (resultCount > 0) {
			return "1";
		} else {
			return "0";
		}
	}
	
	/**
	 * Title: submitExercise 
	 * Description:  提交练习
	 * @date 2018年3月13日 下午9:06:48
	 * @param studentId
	 * @param articleId
	 * @return
	 */
	public RestResult<String> submitExercise(Long studentId, Long articleId) {
		ExerciseEntity exerceseEntity = new ExerciseEntity();
		exerceseEntity.setEnableFlag("T");
		exerceseEntity.setCreateId(studentId);
		exerceseEntity.setReadArticleId(articleId);
		exerceseEntity.setStudentId(studentId);
		exerceseEntity.setEndTime(new Date());
		exerceseEntity.setUpdateDate(new Date());
		
		ExerciseDetailEntity exerciseDetailEntity = new ExerciseDetailEntity();
		exerciseDetailEntity.setStudentId(studentId);
		exerciseDetailEntity.setArticleId(articleId);
		//统计平均分
		ExerciseDetailEntity exerciseDetailEntitity = exerciseDetailDao.getExerciseDetailScore(exerciseDetailEntity);
		exerceseEntity.setScore(exerciseDetailEntitity.getScore());
		exerceseEntity.setIntegrity(exerciseDetailEntitity.getIntegrity());
		exerceseEntity.setFluency(exerciseDetailEntitity.getFluency());
		exerceseEntity.setPronunciation(exerciseDetailEntitity.getPronunciation());
		
		ExerciseEntity exerciseEntity1 = exerciseDao.selectExerciseEntity(exerceseEntity);
		if(null!=exerciseEntity1) {
			exerceseEntity.setId(exerciseEntity1.getId());
			exerciseDao.updateExerciseEntity(exerceseEntity);
		}else {
			exerciseDao.insertExerciseEntity(exerceseEntity);
		}
		return okNoResult("完成提交");
	}
}
