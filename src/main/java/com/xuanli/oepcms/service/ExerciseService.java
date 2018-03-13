/**
 * 
 */
package com.xuanli.oepcms.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.entity.ExerciseDetailEntity;
import com.xuanli.oepcms.entity.ExerciseDetailWordEntity;
import com.xuanli.oepcms.entity.ExerciseEntity;
import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreWordEntity;
import com.xuanli.oepcms.mapper.ExerciseDetailEntityMapper;
import com.xuanli.oepcms.mapper.ExerciseDetailWordEntityMapper;
import com.xuanli.oepcms.mapper.ExerciseEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;
import com.xuanli.oepcms.util.PageBean;

/**
 * @author lijinchao
 * @date 2018年3月9日 下午4:23:50
 */
@Service
public class ExerciseService extends BaseService {
	@Autowired
	ExerciseEntityMapper exerciseDao;
	@Autowired
	ExerciseDetailEntityMapper exerciseDetailDao;
	@Autowired
	ExerciseDetailWordEntityMapper exerciseDetailWordDao;
	@Autowired
	YunZhiSDK yunZhiSDK;

	public void findExercisePage(ExerciseEntity exerciseEntity, PageBean pageBean) {
		int findHomeworkPageTotal = exerciseDao.findHomeworkPageTotal(exerciseEntity);
		pageBean.setTotal(findHomeworkPageTotal);
		exerciseEntity.setStart(pageBean.getRowFrom());
		exerciseEntity.setEnd(pageBean.getPageSize());
		List<HomeworkEntity> findHomeworkPage = exerciseDao.findHomeworkPage(exerciseEntity);
		pageBean.setRows(findHomeworkPage);
	}

	public ExerciseDetailEntity getExerciseDetail(Long exerciseId, Long studentId) {
		ExerciseDetailEntity exerciseDetail = new ExerciseDetailEntity();
		exerciseDetail.setExerciseId(exerciseId);
		exerciseDetail.setStudentId(studentId);
		return exerciseDetailDao.getExerciseDetail(exerciseDetail);
	}

	public String doExercise(Long studentId, Long exerciseId, Long detailId, String file, HttpServletRequest request) {
		ExerciseDetailEntity exerciseDetailEntity = new ExerciseDetailEntity();
		exerciseDetailEntity.setStudentId(studentId);
		exerciseDetailEntity.setCreateId(studentId);
		exerciseDetailEntity.setUpdateDate(new Date());
		exerciseDetailEntity.setEnableFlag("F");
		exerciseDetailEntity.setStudentAudioPath(file);

		ExerciseDetailWordEntity wordEntity = new ExerciseDetailWordEntity();
		wordEntity.setStudentId(studentId);
		wordEntity.setExerciseId(exerciseId);
		wordEntity.setDetailId(detailId);
		List<ExerciseDetailEntity> exerciseDetailEntities = exerciseDetailDao.getExerciseDetailEntity(wordEntity);
		for (ExerciseDetailEntity result : exerciseDetailEntities) {
			ExerciseDetailEntity detailEntity = new ExerciseDetailEntity();
			detailEntity.setId(result.getId());
			String json = yunZhiSDK.generatorExerciseScore(result);
			if (null == json || json.trim().equals("")) {
				System.out.println(result.getId() + "---出现问题,不能计算");
				return "0";
			} else {
				YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
				System.out.println(JSON.toJSONString(yunZhiBean));
				// 开始分析作业分数并且更新到数据库中
				// 设置分数
				detailEntity.setScore(yunZhiBean.getScore());
				if (null != result.getAudioPath()) {
					// 这里有句子的流畅度完整度流利度
					List<YunZhiline> yunZhilines = yunZhiBean.getLines();
					if (null != yunZhilines && yunZhilines.size() > 0) {
						YunZhiline yunZhiline = yunZhilines.get(0);
						detailEntity.setFluency(yunZhiline.getFluency());
						detailEntity.setIntegrity(yunZhiline.getIntegrity());
						detailEntity.setPronunciation(yunZhiline.getPronunciation());
						for (YunZhiline line : yunZhilines) {
							List<YunZhiWords> yunZhiSubWords = line.getWords();
							for (YunZhiWords word : yunZhiSubWords) {
								double score = word.getScore();
								String text = word.getText();
								ExerciseDetailWordEntity detailWordEntity = new ExerciseDetailWordEntity();
								detailWordEntity.setExerciseId(exerciseId);
								detailWordEntity.setDetailId(detailId);
								detailWordEntity.setStudentId(studentId);
								detailWordEntity.setWord(text);
								double sc = score * 10;
								sc = new BigDecimal(sc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
								detailWordEntity.setScore(sc);
								exerciseDetailWordDao.insertExerciseDetailWordEntity(detailWordEntity);
							}
						}
					}
				}
			}
			detailEntity.setCreateId(studentId);
			detailEntity.setCreateDate(new Date());
			detailEntity.setUpdateId(studentId);
			detailEntity.setUpdateDate(new Date());
			detailEntity.setEnableFlag("F");
			int resultCount = exerciseDetailDao.updateExerciseDetailEntity(detailEntity);
			if (resultCount > 0) {
				return "1";
			} else {
				return "0";
			}
		}
		return "1";
	}
}
