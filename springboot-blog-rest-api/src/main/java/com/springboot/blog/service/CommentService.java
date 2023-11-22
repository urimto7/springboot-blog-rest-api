package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsbyPostId(long postId);
    CommentDto getCommentById(Long postId, Long commentId );
    CommentDto upddateComment(Long postId, Long commentId, CommentDto commentRequest);
    void deleteComment(Long postId, Long commentId);


}
