package com.example.mytestproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // DB에 테이블이 생성됨  H2 SQL DB에 저장됨
@AllArgsConstructor //생성자
@ToString
@NoArgsConstructor //기본생성자
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//DB에게 ID 중복 안되도로 자동 생성 요청
    private Long id;

    @Column
    private String title;

    @Column
    private String content;



    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }
}
