package com.example.newsfeed.controller;

import com.example.newsfeed.dto.CommentRequestDto;
import com.example.newsfeed.dto.MenuResponseDto;
import com.example.newsfeed.service.CommentService;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<MenuResponseDto> post(@RequestBody CommentRequestDto commentRequestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(commentService.creataComment(commentRequestDto,userDetails));
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<MenuResponseDto> update(@RequestBody CommentRequestDto commentRequestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(commentService.updateComment(commentRequestDto, userDetails, commentId));
    }
    @DeleteMapping("{commentId}")
    public ResponseEntity<MenuResponseDto> delete(@RequestBody CommentRequestDto commentRequestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(commentService.deleteComment(commentRequestDto, userDetails, commentId));
    }
}
