package com.example.newsfeed.service;

import com.example.newsfeed.dto.CommentRequestDto;
import com.example.newsfeed.dto.CommentResponseDto;
import com.example.newsfeed.dto.MenuResponseDto;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.CommenetRepository;
import com.example.newsfeed.repository.MenuRepository;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IComment;

import java.util.concurrent.RejectedExecutionException;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommenetRepository commenetRepository;
    private final MenuRepository menuRepository;
    private User user;
    private Menu menu;
    private Comment comment;

    public MenuResponseDto creataComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        menu = getMenuById(commentRequestDto.getMenuId());
        Comment comment = new Comment(commentRequestDto, user, menu);
        menu.addComment(comment);
        commenetRepository.save(comment);
        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto updateComment(CommentRequestDto commentRequestDto,
                                         UserDetailsImpl userDetails, Long commentId) {
        user = userDetails.getUser();
        menu = getMenuById(commentRequestDto.getMenuId());
        comment = getCommentById(commentId);
        if(user.getUsername().equals(menu.getUser().getUsername())) {
            comment.updateComment(commentRequestDto);
        }else{
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return new MenuResponseDto(menu);
    }
    @Transactional
    public MenuResponseDto deleteComment(CommentRequestDto commentRequestDto,
                                         UserDetailsImpl userDetails, Long commentId) {
        user = userDetails.getUser();
        menu = getMenuById(commentRequestDto.getMenuId());
        comment = getCommentById(commentId);
        if(user.getUsername().equals(menu.getUser().getUsername())) {
            commenetRepository.delete(comment);
        }else{
            throw new RejectedExecutionException("작성자만 삭제할 수 있습니다.");
        }
        return new MenuResponseDto(menu);
    }

    public Menu getMenuById(Long menuId){
        return menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 ID 입니다."));
    }
    public Comment getCommentById(Long commentId){
        return comment = commenetRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 ID 입니다."));
    }
}
