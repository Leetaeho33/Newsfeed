package com.example.newsfeed.entity;

import com.example.newsfeed.dto.MenuRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu")
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime modifiedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList;

    public Menu(MenuRequestDto menuRequestDto) {
        this.title = menuRequestDto.getTitle();
        this.content = menuRequestDto.getContent();
        this.createdAt = LocalDateTime.now();
    }
    public void updateMenu(MenuRequestDto menuRequestDto){
        this.title = menuRequestDto.getTitle();
        this.content = menuRequestDto.getContent();
        this.modifiedAt = LocalDateTime.now();
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
    }

}
