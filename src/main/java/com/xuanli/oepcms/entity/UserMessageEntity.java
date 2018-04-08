package com.xuanli.oepcms.entity;

public class UserMessageEntity {
    private Long id;

    private String type;

    private String text;

    private Long userId;

    private Boolean flags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getFlags() {
        return flags;
    }

    public void setFlags(Boolean flags) {
        this.flags = flags;
    }
}