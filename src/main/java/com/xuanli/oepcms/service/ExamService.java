/**
 * @fileName:  ExamService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月6日 下午3:01:28
 */
package com.xuanli.oepcms.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.controller.bean.ExamBean;
import com.xuanli.oepcms.controller.bean.ExamStudentBean;
import com.xuanli.oepcms.controller.bean.ExamStudentScoreBean;
import com.xuanli.oepcms.entity.ExamEntity;
import com.xuanli.oepcms.entity.ExamStudentEntity;
import com.xuanli.oepcms.entity.ExamStudentScoreEntity;
import com.xuanli.oepcms.entity.ExamStudentScoreWordEntity;
import com.xuanli.oepcms.entity.ExamSubjectEntity;
import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.entity.PaperOptionEntity;
import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.entity.PaperSubjectEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.ExamEntityMapper;
import com.xuanli.oepcms.mapper.ExamStudentEntityMapper;
import com.xuanli.oepcms.mapper.ExamStudentScoreEntityMapper;
import com.xuanli.oepcms.mapper.ExamStudentScoreWordEntityMapper;
import com.xuanli.oepcms.mapper.ExamSubjectEntityMapper;
import com.xuanli.oepcms.mapper.PaperEntityMapper;
import com.xuanli.oepcms.mapper.PaperOptionEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;
import com.xuanli.oepcms.util.AliOSSUtil;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Service
public class ExamService extends BaseService {
	@Autowired
	PaperSubjectDetailEntityMapper paperSubjectDetailEntityMapper;
	@Autowired
	ExamStudentScoreEntityMapper examStudentScoreEntityMapper;
	@Autowired
	PaperOptionEntityMapper paperOptionEntityMapper;
	@Autowired
	AliOSSUtil ossUtils;
	@Autowired
	ExamEntityMapper examEntityMapper;
	@Autowired
	YunZhiSDK yunZhiSDK;
	@Autowired
	ExamStudentEntityMapper examStudentEntityMapper;
	@Autowired
	UserService userService;
	@Autowired
	PaperEntityMapper paperEntityMapper;
	@Autowired
	PaperSubjectEntityMapper paperSubjectEntityMapper;
	@Autowired
	ExamSubjectEntityMapper examSubjectEntityMapper;
	@Autowired
	ExamStudentScoreWordEntityMapper examStudentScoreWordEntityMapper;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月6日 下午3:03:03
	 */
	public void submitExam(Long studentId, Long examId, String detailIds, String answer, MultipartFile file, Integer timeout) {
		String[] detailIds1 = detailIds.split(",");
		for (int i = 0; i < detailIds1.length; i++) {
			long detailId = Long.parseLong(detailIds1[i]);
			// 获取题目详情
			PaperSubjectDetailEntity paperSubjectDetailEntity = paperSubjectDetailEntityMapper.selectById(detailId);
			PaperOptionEntity record = new PaperOptionEntity();
			record.setDetailId(detailId);
			List<PaperOptionEntity> paperOptionEntities = paperOptionEntityMapper.selectByDetailId(record);
			// 获取考试详情
			ExamEntity examEntity = examEntityMapper.selectById(examId);
			double pointScore = examEntity.getPointScore().doubleValue();
			ExamStudentScoreEntity examStudentScoreEntity = new ExamStudentScoreEntity();
			examStudentScoreEntity.setSubjectDetailId(detailId);
			examStudentScoreEntity.setExamId(examId);
			examStudentScoreEntity.setStudentId(studentId);
			/** TODO   生成分数前删除旧score分数   **/ 
			examStudentScoreEntityMapper.deleteExamStudentScore(examStudentScoreEntity);
			// 听后回答
			String fileId = null;
			String text = null;
			Double score = 0.00;
			InputStream inputStream = null;
			// 听后回答是有音频的---- type --1(听后回答)
			ByteArrayOutputStream baos = null;
			if (paperSubjectDetailEntity.getType().intValue() == 1) {
				try {
					if (null != file && !file.isEmpty()) {
						baos = new ByteArrayOutputStream();
						inputStream = file.getInputStream();
						byte[] buffer = new byte[1024];
						int len;
						while ((len = inputStream.read(buffer)) > -1) {
							baos.write(buffer, 0, len);
						}
						baos.flush();
						fileId = ossUtils.uploadFile(new ByteArrayInputStream(baos.toByteArray()), "exam_student", "mp3");
						// 计算分数
						// 获取本地答案的详细信息
						for (PaperOptionEntity paperOptionEntity : paperOptionEntities) {
							String correnctResult = paperOptionEntity.getCorrectResult();
							if (null == correnctResult || StringUtils.isEmpty(correnctResult)) {
								continue;
							}
							String correncts[] = correnctResult.split("\\|\\|");
							for (String correnct : correncts) {
								String json = yunZhiSDK.generatorStudentExamScore(new ByteArrayInputStream(baos.toByteArray()), correnct, "A");
								if (null == json || json.trim().equals("")) {
									System.out.println(paperOptionEntity.getId() + "---出现问题,不能计算");
								} else {
									YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
									if (yunZhiBean.getScore() > score) {
										score = yunZhiBean.getScore();
									}
								}
							}
						}
						for (PaperOptionEntity paperOptionEntity : paperOptionEntities) {
							String correnctResult = paperOptionEntity.getPointResult();
							if (null == correnctResult || StringUtils.isEmpty(correnctResult)) {
								continue;
							}
							String correncts[] = correnctResult.split("\\|\\|");
							for (String correnct : correncts) {
								String json = yunZhiSDK.generatorStudentExamScore(new ByteArrayInputStream(baos.toByteArray()), correnct, "A");
								if (null == json || json.trim().equals("")) {
									System.out.println(paperOptionEntity.getId() + "---出现问题,不能计算");
								} else {
									YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
									if (yunZhiBean.getScore() * pointScore > score) {
										score = yunZhiBean.getScore();
									}
								}
							}
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
					fileId = "";
				} finally {
					if (null != inputStream) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						inputStream = null;
					}
					if (null != baos) {
						try {
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				score = score * paperSubjectDetailEntity.getScore() / 100;
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(text);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
				// 听后选择---- type --2(选择题)
			} else if (paperSubjectDetailEntity.getType().intValue() == 2) {
				// 传入的为ABCDEF这种参数-
				String textStr = answer.split(",")[i];
				if (paperOptionEntities.get(0).getCorrectResult().trim().equalsIgnoreCase(textStr.trim())) {
					score = 100.00;
				} else {
					score = 0.00;
				}
				score = score * paperSubjectDetailEntity.getScore() / 100;
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(textStr);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
				// 听后填空题---- type --3(填空题)
			} else if (paperSubjectDetailEntity.getType().intValue() == 3) {
				// 传入的为填空题的答案
				String textStr = answer.split(",")[i];
				if (paperOptionEntities.get(0).getCorrectResult().trim().indexOf(textStr.trim()) >= 0) {
					score = 100.00;
				} else {
					score = 0.00;
				}
				score = score * paperSubjectDetailEntity.getScore() / 100;
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(textStr);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
				// 听后转述---- type --4(听后转述)
			} else if (paperSubjectDetailEntity.getType().intValue() == 4) {
				// 听后转述是有音频的
				try {
					if (null != file && !file.isEmpty()) {
						inputStream = file.getInputStream();
						baos = new ByteArrayOutputStream();
						byte[] buffer = new byte[1024];
						int len;
						while ((len = inputStream.read(buffer)) > -1) {
							baos.write(buffer, 0, len);
						}
						baos.flush();
						fileId = ossUtils.uploadFile(new ByteArrayInputStream(baos.toByteArray()), "exam_student", "mp3");
						// 计算分数 //按照比例去计算分数
						double thisScore = 0.00;
						double picScore = paperSubjectDetailEntity.getScore() / paperOptionEntities.size();
						String correnctResult = paperOptionEntities.get(0).getCorrectResult();
						String correncts[] = correnctResult.split("\\|\\|");
						for (String correnct : correncts) {
							String json = yunZhiSDK.generatorStudentExamScore(new ByteArrayInputStream(baos.toByteArray()), correnct, "A");
							if (null == json || json.trim().equals("")) {
								System.out.println(paperSubjectDetailEntity.getId() + "---出现问题,不能计算");
							} else {
								YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
								thisScore = thisScore + (yunZhiBean.getScore() * picScore / 100);
							}
						}
						examStudentScoreEntity.setScore(thisScore);
					}
				} catch (IOException e) {
					e.printStackTrace();
					fileId = "";
				} finally {
					if (null != inputStream) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						inputStream = null;
					}
					if (null != baos) {
						try {
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(text);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
			} else if (paperSubjectDetailEntity.getType().intValue() == 5) {
				// 朗读短文---- type --5(朗读短文)
				// 阅读是有音频的
				try {
					if (null != file && !file.isEmpty()) {
						inputStream = file.getInputStream();
						baos = new ByteArrayOutputStream();
						byte[] buffer = new byte[1024];
						int len;
						while ((len = inputStream.read(buffer)) > -1) {
							baos.write(buffer, 0, len);
						}
						baos.flush();
						fileId = ossUtils.uploadFile(new ByteArrayInputStream(baos.toByteArray()), "exam_student", "mp3");
						// 阅读 直接把问题text传入即可
						String json = yunZhiSDK.generatorStudentExamScore(new ByteArrayInputStream(baos.toByteArray()), paperSubjectDetailEntity.getQuestion(), "E");
						if (null == json || json.trim().equals("")) {
							System.out.println(paperSubjectDetailEntity.getId() + "---出现问题,不能计算");
						} else {
							YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
							score = yunZhiBean.getScore();
							// 这里要弄一下流利度流畅度完整度三个信息
							if (null != yunZhiBean.getLines() && yunZhiBean.getLines().size() > 0) {
								/** TODO   生成分数前删除旧word分数   **/ 
								ExamStudentScoreWordEntity examStudentScoreWordEntity1 = new ExamStudentScoreWordEntity();
								examStudentScoreWordEntity1.setExamId(examId);
								examStudentScoreWordEntity1.setStudentId(studentId);
								examStudentScoreWordEntity1.setExamDetailId(paperSubjectDetailEntity.getId());
								examStudentScoreWordEntityMapper.deleteExamStudentScoreWord(examStudentScoreWordEntity1);
								YunZhiline yunZhiline = yunZhiBean.getLines().get(0);
								examStudentScoreEntity.setFluency(yunZhiline.getFluency());
								examStudentScoreEntity.setIntegrity(yunZhiline.getIntegrity());
								examStudentScoreEntity.setPronunciation(yunZhiline.getPronunciation());
								List<YunZhiWords> yunZhiWords = yunZhiline.getWords();
								for (YunZhiWords yunZhiWord : yunZhiWords) {
									// yunZhiWord.
									ExamStudentScoreWordEntity examStudentScoreWordEntity = new ExamStudentScoreWordEntity();
									examStudentScoreWordEntity.setExamDetailId(paperSubjectDetailEntity.getId());
									examStudentScoreWordEntity.setExamId(examId);
									examStudentScoreWordEntity.setScore(yunZhiWord.getScore());
									examStudentScoreWordEntity.setType(yunZhiWord.getType()+"");
									examStudentScoreWordEntity.setText(yunZhiWord.getText());
									examStudentScoreWordEntity.setStudentId(studentId);
									examStudentScoreWordEntityMapper.insertSelective(examStudentScoreWordEntity);
								}

							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					fileId = "";
				} finally {
					if (null != inputStream) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						inputStream = null;
					}
					if (null != baos) {
						try {
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				score = score * paperSubjectDetailEntity.getScore() / 100;
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(text);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
			}
		}
		// 更新学生做题信息
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setExamId(examId);
		examStudentEntity.setStudentId(studentId);
		examStudentEntity.setUpdateDate(new Date());
		examStudentEntity.setTimeOut(timeout);
		examStudentEntityMapper.updateExamStudentEntityByExamId(examStudentEntity);
	}

	/**
	 * @Description: TODO 分页展示考试列表
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月7日 上午10:44:41
	 */
	public void findExamByPage(ExamEntity examEntity, PageBean pageBean) {
		int total = examEntityMapper.findExamByPageTotal(examEntity);
		pageBean.setTotal(total);
		examEntity.setStart(pageBean.getRowFrom());
		examEntity.setEnd(pageBean.getPageSize());
		List<ExamEntity> examEntities = examEntityMapper.findExamByPage(examEntity);
		pageBean.setRows(examEntities);
	}

	/**
	 * @Description: TODO 生成作业报告--其实就是计算学生总分数
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月9日 上午10:25:55
	 */
	public RestResult<String> generatorExamReport(Long examId, Long studentId) {
		//
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setExamId(examId);
		List<ExamStudentEntity> examStudentEntities = examStudentEntityMapper.generatorExamReport(examStudentEntity);
		for (ExamStudentEntity ese : examStudentEntities) {
			ese.setComplate("T");
			examStudentEntityMapper.updateExamStudentEntityByExamId(ese);
		}
		return okNoResult("成功");
	}

	/**
	 * @Description: TODO //布置模拟考试
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月23日 下午2:53:36
	 */
	public RestResult<String> genteratorExam(Long userId, String name, String notice, String classIds, Date startTime, Date endTime, Long paperId) {
		String clasIds[] = classIds.split(",");
		for (int i = 0; i < clasIds.length; i++) {
			String clasId = clasIds[i];
			// 考试信息
			ExamEntity examEntity = new ExamEntity();
			examEntity.setEnableFlag("T");
			examEntity.setNotice(notice);
			examEntity.setName(name);
			examEntity.setStartTime(startTime);
			examEntity.setEndTime(endTime);
			examEntity.setPaperId(paperId);
			examEntity.setClassId(Long.parseLong(clasId));
			//模拟考试
			examEntity.setType("1");
			examEntity.setCreateId(userId);
			examEntity.setCreateDate(new Date());
			examEntityMapper.insertExamEntity(examEntity);
			// 考题信息
			PaperEntity paperEntity = paperEntityMapper.selectById(paperId); // 获取试卷信息
			Integer timeOut = paperEntity.getTotalTime();
			PaperSubjectEntity paperSubjectEntity = new PaperSubjectEntity();
			paperSubjectEntity.setPaperId(paperId);
			List<PaperSubjectEntity> paperSubjectEntities = paperSubjectEntityMapper.getPaperSubjectEntity(paperSubjectEntity); // 获取试卷详细信息
			for (PaperSubjectEntity paperSubjectEntity2 : paperSubjectEntities) {
				ExamSubjectEntity examSubjectEntity = new ExamSubjectEntity();
				examSubjectEntity.setCreateDate(new Date());
				examSubjectEntity.setCreateId(userId);
				examSubjectEntity.setEnableFlag("T");
				examSubjectEntity.setSubjectId(paperSubjectEntity2.getId());
				examSubjectEntity.setExamId(examEntity.getId());
				examSubjectEntityMapper.insertExamSubjectEntity(examSubjectEntity);
			}
			// 考生信息
			UserEntity userEntity1 = new UserEntity();
			userEntity1.setClasId(clasId);
			List<UserEntity> userEntities = userService.getClasUseStudent(userEntity1);
			for (UserEntity userEntity : userEntities) {
				ExamStudentEntity examStudentEntity = new ExamStudentEntity();
				examStudentEntity.setComplate("F");
				examStudentEntity.setEnableFlag("T");
				examStudentEntity.setCreateId(userId);
				examStudentEntity.setExamId(examEntity.getId());
				examStudentEntity.setStudentId(userEntity.getId());
				examStudentEntity.setTimeOut(timeOut);
				examStudentEntity.setScore(0.00);
				examStudentEntityMapper.insertExamStudentEntity(examStudentEntity);
			}
		}
		return okNoResult("成功布置模拟考试!");
	}

	// 考试预览题目信息
	public RestResult<ExamBean> getExamInfo(Long examId) {
		ExamBean examBean = examEntityMapper.getExamInfo(examId);
		return ok(examBean);
	}

	// 获取学生考试的详细信息
	public RestResult<Map<String, Object>> getStudentExamInfo(Long examId, Long studentId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取学生考试的分数信息
		ExamStudentBean examStudentBean = new ExamStudentBean();
		examStudentBean.setExamId(examId);
		examStudentBean.setStudentId(studentId);
		List<ExamStudentBean> examStudentBeans = examStudentEntityMapper.getStudentExamScore(examStudentBean);
		// 获取学生考试的详细信息
		List<ExamStudentScoreBean> examStudentScoreBeans = examStudentEntityMapper.getStudentExamScoreDetail(examStudentBean);
		//获取学生朗读短文的每个句子的得分
		ExamStudentScoreWordEntity examStudentScoreWordEntity = new ExamStudentScoreWordEntity();
		examStudentScoreWordEntity.setExamId(examId);
		examStudentScoreWordEntity.setStudentId(studentId);
		List<ExamStudentScoreWordEntity> examStudentScoreWordEntities = examStudentScoreWordEntityMapper.getExamStudentWords(examStudentScoreWordEntity);
		// 获取到学生按照题型的分数(包括学生未做题目的分数)
		resultMap.put("examStudentScores", examStudentBeans);
		resultMap.put("examStudentScoreDetails", examStudentScoreBeans);
		resultMap.put("examStudentWordsScore", examStudentScoreWordEntities);
		return ok(resultMap);
	}

	// 获取报告内容
	public RestResult<Map<String, Object>> getExamReport(Long examId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setExamId(examId);
		List<ExamStudentEntity> examStudentEntities = examStudentEntityMapper.getExamStudentRank(examStudentEntity);
		//查询
		
		
		
		
		
		// 学生排名等信息
		resultMap.put("examStudents", examStudentEntities);
		return ok(resultMap);
	}

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月28日 上午9:43:34
	 */
	public RestResult<Map<String, Object>> getExamDetail(Long examId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ExamEntity examEntity = examEntityMapper.selectById(examId);
		map.put("examDetail", examEntity);
		List<Map<String, Object>> maps1 = paperEntityMapper.getPaperDetail(examEntity.getPaperId());
		map.put("paperDetail", maps1);
		map.put("paperInfo", paperEntityMapper.selectById(examEntity.getPaperId()));
		return ok(map);
	}

	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月7日 下午3:38:05
	 */
	public void findStudentExamByPage(Long studentId, PageBean pageBean) {
		Map<String, Object> requiredMap = new HashMap<String, Object>();
		requiredMap.put("studentId", studentId);
		int total = examEntityMapper.findStudentExamByPageTotal(studentId);
		pageBean.setTotal(total);
		requiredMap.put("start", pageBean.getRowFrom());
		requiredMap.put("end", pageBean.getPageSize());
		List<Map<String, Object>> resultMap = examEntityMapper.findStudentExamByPage(requiredMap);
		pageBean.setRows(resultMap);
	}

	
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月28日 上午9:43:34
	 */
	public RestResult<Map<String, Object>> findStudentExamDetail(Long examId,Long studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ExamEntity examEntity = examEntityMapper.selectById(examId);
		List<Map<String, Object>> maps1 = paperEntityMapper.getPaperDetail(examEntity.getPaperId());
		//学生做题的信息
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setStudentId(studentId);
		examStudentEntity.setExamId(examId);
		ExamStudentEntity examStudentInfo = examStudentEntityMapper.getExamStudentInfo(examStudentEntity);
		map.put("examStudentInfo",examStudentInfo);
		map.put("paperDetail", maps1);
		map.put("paperInfo", paperEntityMapper.selectById(examEntity.getPaperId()));
		map.put("examDetail", examEntity);
		return ok(map);
	}
}
