package com.team2.fithub.model.dto;

import java.util.Date;

public class Match {
	private int id;
    private int timeId;
    private int mentorId;
    private int userId;
    private boolean purchaseConfirm;
    private Date createdAt;

    // 생성자
    public Match() {
    }

    // getter와 setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isPurchaseConfirm() {
        return purchaseConfirm;
    }

    public void setPurchaseConfirm(boolean purchaseConfirm) {
        this.purchaseConfirm = purchaseConfirm;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
