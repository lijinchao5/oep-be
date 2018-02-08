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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.entity.ExamEntity;
import com.xuanli.oepcms.entity.ExamStudentScoreEntity;
import com.xuanli.oepcms.entity.PaperOptionEntity;
import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.mapper.ExamEntityMapper;
import com.xuanli.oepcms.mapper.ExamStudentScoreEntityMapper;
import com.xuanli.oepcms.mapper.PaperOptionEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectDetailEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;
import com.xuanli.oepcms.util.AliOSSUtil;
import com.xuanli.oepcms.util.PageBean;

/**
 * @author QiaoYu
 */
@Service
public class ExamService {
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

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月6日 下午3:03:03
	 */
	public void submitExam(Long studentId, Long examId, String detailIds, String answer, MultipartFile file) {
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
				// 朗读短文---- type --4(朗读短文)
			} else if (paperSubjectDetailEntity.getType().intValue() == 5) {
				// 听后阅读是有音频的
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
						String json = yunZhiSDK.generatorStudentExamScore(new ByteArrayInputStream(baos.toByteArray()), paperOptionEntities.get(0).getCorrectResult(), "E");
						if (null == json || json.trim().equals("")) {
							System.out.println(paperSubjectDetailEntity.getId() + "---出现问题,不能计算");
						} else {
							YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
							score = yunZhiBean.getScore();
							// 这里要弄一下流利度流畅度完整度三个信息
							if (null != yunZhiBean.getLines() && yunZhiBean.getLines().size() > 0) {
								YunZhiline yunZhiline = yunZhiBean.getLines().get(0);
								examStudentScoreEntity.setFluency(yunZhiline.getFluency()*10);
								examStudentScoreEntity.setIntegrity(yunZhiline.getIntegrity()*10);
								examStudentScoreEntity.setPronunciation(yunZhiline.getPronunciation()*10);
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
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(text);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
			}
		}
	}

	/**
	 * @Description: TODO
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
}
