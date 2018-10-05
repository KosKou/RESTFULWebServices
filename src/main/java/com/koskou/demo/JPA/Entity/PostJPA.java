package com.koskou.demo.JPA.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "post")
public class PostJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private UserJPA user;

    public PostJPA() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserJPA getUser() {
        return user;
    }

    public void setUser(UserJPA user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PostJPA{" +
                "description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
