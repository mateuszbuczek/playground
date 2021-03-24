package com.example.querydsl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class BlogPost {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String body;

    @ManyToOne
    private User user;
}
