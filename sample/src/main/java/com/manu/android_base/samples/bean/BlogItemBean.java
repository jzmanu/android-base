package com.manu.android_base.samples.bean;

import java.util.Date;

/**
 * Powered by jzman.
 * Created on 2018/7/18 0018.
 */
public class BlogItemBean {
    private String Id;
    private String content;
    private Date createdAt;
    private Date publishedat;
    private String randId;
    private String title;
    private Date updatedAt;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPublishedat() {
        return publishedat;
    }

    public void setPublishedat(Date publishedat) {
        this.publishedat = publishedat;
    }

    public String getRandId() {
        return randId;
    }

    public void setRandId(String randId) {
        this.randId = randId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BlogItemBean{" +
                "Id='" + Id + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", publishedat=" + publishedat +
                ", randId='" + randId + '\'' +
                ", title='" + title + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

