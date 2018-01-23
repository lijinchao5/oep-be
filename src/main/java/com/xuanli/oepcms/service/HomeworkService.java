/**
 * @fileName:  HomeworkService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 下午3:34:28
 */
package com.xuanli.oepcms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.controller.bean.HomeworkBean;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.entity.HomeworkDetailEntity;
import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.HomeworkDetailEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentScoreEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.util.FileUtil;

/**
 * @author QiaoYu
 */
@Service
public class HomeworkService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	HomeworkEntityMapper homeworkDao;
	@Autowired
	HomeworkDetailEntityMapper homeworkDetailDao;
	@Autowired
	HomeworkStudentEntityMapper homeworkStudentDao;
	@Autowired
	HomeworkStudentScoreEntityMapper homeworkStudentScoreDao;
	@Autowired
	UserService userService;
	@Autowired
	FileUtil fileUtil;
	@Autowired
	YunZhiSDK yunZhiSDK;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 下午3:43:08
	 */
	public void makeHomeWork(String name, String clasId, Date endTime, String remark, List<HomeworkBean> homeworkBeans, Long createId) {
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
			for (HomeworkBean homeworkBean : homeworkBeans) {
				String[] sections = homeworkBean.getSubjects().split(",");
				for (String sec : sections) {
					HomeworkDetailEntity homeworkDetailEntity = new HomeworkDetailEntity();
					homeworkDetailEntity.setCreateId(createId);
					homeworkDetailEntity.setCreateDate(createDate);
					homeworkDetailEntity.setHomeworkId(homeworkId);
					homeworkDetailEntity.setHomeworkType(homeworkBean.getType() + "");
					homeworkDetailEntity.setSectionDetailId(Long.parseLong(sec));
					homeworkDetailEntities.add(homeworkDetailEntity);
				}
			}
			if (null != homeworkDetailEntities && homeworkDetailEntities.size() > 0) {
				homeworkDetailDao.inserHomeworkDetailBatch(homeworkDetailEntities);
			}
		}
	}

	/**
	 * @Description: TODO 做家庭作业
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月19日 上午9:41:12
	 */
	public String doHomeWork(Long studentId, Long sectionId, Long homeworkId, MultipartFile file, String text, HttpServletRequest request) {
		String filePath = null;
		if (!file.isEmpty()) {
			InputStream inputStream = null;
			try {
				inputStream = file.getInputStream();
				filePath = fileUtil.uploadFile(inputStream, "student_homework_audio", request);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("上传文件失败!");
				filePath = null;
				return "1";
			} finally {
				if (null != inputStream) {
					try {
						inputStream.close();
						inputStream = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		HomeworkStudentScoreEntity scoreEntity = new HomeworkStudentScoreEntity();
		scoreEntity.setAudioPath(filePath);
		scoreEntity.setEnableFlag("T");
		scoreEntity.setCreateId(studentId);
		scoreEntity.setSectionId(sectionId);
		scoreEntity.setHomeworkId(homeworkId);
		scoreEntity.setText(text);
		homeworkStudentScoreDao.insertHomeworkStudentScoreEntity(scoreEntity);
		// 开始调用SDK计算分数
		// ----获取题目的详细信息
		HomeworkScoreBean homeworkScoreBean = new HomeworkScoreBean();
		homeworkScoreBean.setHomeworkId(homeworkId);
		homeworkScoreBean.setStudentId(studentId);
		List<HomeworkScoreBean> homeworkScoreBeans = homeworkStudentScoreDao.getStudentHomework(homeworkScoreBean);
		// 开始评分
		for (HomeworkScoreBean result : homeworkScoreBeans) {
			// 如果题型是听写的那么就不用让sdk评分了
			if (null != result.getHomeworkType() && result.getHomeworkType().intValue() == 5) {
				if (result.getStanderText().trim().equalsIgnoreCase(result.getStudentText())) {
					result.setStudentScore(new Double(100));
				} else {
					result.setStudentScore(new Double(0));
				}
			} else {
				String json = yunZhiSDK.generatorStudentScore(result);

			}
		}

		return "0";
	}

	public void test(long homeworkId, long studentId) {
		// 开始调用SDK计算分数
		// ----获取题目的详细信息
		HomeworkScoreBean homeworkScoreBean = new HomeworkScoreBean();
		homeworkScoreBean.setHomeworkId(homeworkId);
		homeworkScoreBean.setStudentId(studentId);
		List<HomeworkScoreBean> homeworkScoreBeans = homeworkStudentScoreDao.getStudentHomework(homeworkScoreBean);
		// 开始评分
		for (HomeworkScoreBean result : homeworkScoreBeans) {
			// 如果题型是听写的那么就不用让sdk评分了
			if (null != result.getHomeworkType() && result.getHomeworkType().intValue() == 5) {
				if (result.getStanderText().trim().equalsIgnoreCase(result.getStudentText())) {
					result.setStudentScore(new Double(100));
				} else {
					result.setStudentScore(new Double(0));
				}
			} else {
				String json = yunZhiSDK.generatorStudentScore(result);
				if (null == json || json.trim().equals("")) {
					System.out.println(result.getId()+"---出现问题,不能计算");
					
				}else {
					YunZhiBean yunZhiBean = JSONObject.parseObject(json,YunZhiBean.class);
					System.out.println(json);
					System.out.println(JSON.toJSONString(yunZhiBean));
					System.out.println("-------------------------------");
				}
			}
		}
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午10:46:49
	 */
	public List<HomeworkStudentScoreEntity> reportStudentScore(long homeworkId) {
		return homeworkStudentScoreDao.reportStudentScore(homeworkId);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午10:48:55
	 */
	public void updateHomeworkStudentScoreEntityBatch(HomeworkStudentEntity homeworkStudentEntity) {
		homeworkStudentDao.updateHomeworkStudentEntityBatch(homeworkStudentEntity);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午11:12:47
	 */
	public int reportHomeworkDetail(long homeworkId) {
		return homeworkDetailDao.reportHomeworkDetail(homeworkId);
	}
}
