package com.xuanli.oepcms.entity;

import java.util.Date;

public class ExerciseDetailEntity {
    private Long id;

    private Long studentId;

    private Long exerciseId;

    private Long readSentenceId;

    private Double score;
    
    private String AudioPath;
    
    private String studentAudioPath;

    private Double integrity;

    private Double pronunciation;

    private Double fluency;

    private Long createId;

    private Date createDate;

    private Long updateId;

    private Date updateDate;

    private String enableFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Long getReadSentenceId() {
        return readSentenceId;
    }

    public void setReadSentenceId(Long readSentenceId) {
        this.readSentenceId = readSentenceId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getAudioPath() {
		return AudioPath;
	}

	public void setAudioPath(String audioPath) {
		AudioPath = audioPath;
	}

	public String getStudentAudioPath() {
        return studentAudioPath;
    }

    public void setStudentAudioPath(String studentAudioPath) {
        this.studentAudioPath = studentAudioPath;
    }

    public Double getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Double integrity) {
        this.integrity = integrity;
    }

    public Double getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(Double pronunciation) {
        this.pronunciation = pronunciation;
    }

    public Double getFluency() {
        return fluency;
    }

    public void setFluency(Double fluency) {
        this.fluency = fluency;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

}