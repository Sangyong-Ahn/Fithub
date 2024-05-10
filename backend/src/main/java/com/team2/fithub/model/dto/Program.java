package com.team2.fithub.model.dto;

import java.util.Date;
import java.util.List;

public class Program {
	private int id;
    private int mentorId;
    private int categoryId;
    private String title;
	private Date reservationStartDate;
    private Date reservationEndDate;
    private Date programStartDate;
    private Date programEndDate;
    private String thumbnail;
    private String content;
    private String youtubeUrl;
    private Double latitude;
    private Double longitude;
    private Date createdAt;
    
    // 외부 객체 정보 담을 필드
    private List<Time> times;
    private Mentor mentorInfo;

    // 생성자
    public Program() {
    }
    
    // getter와 setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(Date reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public Date getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(Date reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public Date getProgramStartDate() {
        return programStartDate;
    }

    public void setProgramStartDate(Date programStartDate) {
        this.programStartDate = programStartDate;
    }

    public Date getProgramEndDate() {
        return programEndDate;
    }

    public void setProgramEndDate(Date programEndDate) {
        this.programEndDate = programEndDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

	public List<Time> getTimes() {
		return times;
	}

	public void setTimes(List<Time> times) {
		this.times = times;
	}

	public Mentor getMentorInfo() {
		return mentorInfo;
	}

	public void setMentorInfo(Mentor mentorInfo) {
		this.mentorInfo = mentorInfo;
	}
}
