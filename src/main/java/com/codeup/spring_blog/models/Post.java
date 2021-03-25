package com.codeup.spring_blog.models;

public class Post {

    private long id;
    private long userId;
    private String title;
    private String body;

    public Post(long id, long userId, String title, String description) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = description;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setDescription(String description) {
        this.body = body;
    }
}
