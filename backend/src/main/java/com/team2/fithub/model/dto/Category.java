package com.team2.fithub.model.dto;

public class Category {
	private int id;
    private String name;

    // 생성자
    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // getter와 setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
