package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.BlogAPIException;
import com.springboot.blog.exceptions.ResourseNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;


    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.mapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= mapToEntity(commentDto);
        // retrive post entity by id

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post","id",postId));
        //set post comment entity


        comment.setPost(post);
        // save comment entity to Db

       Comment newcomment= commentRepository.save(comment);




        return mapToDTO(newcomment);
    }

    @Override
    public List<CommentDto> getCommentsbyPostId(long postId) {
        // retrive comments by post id
        List<Comment > comments= commentRepository.findByPostId(postId);

// convert a list of comments on a list of Dto
        return comments.stream().map(comment -> mapToDTO(comment)).toList();
        //
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // get post entiry by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post","id",postId));
        //retrive comment by id

        Comment comment= commentRepository.findById(commentId)
                .orElseThrow(()->
                        new ResourseNotFoundException("Comment","id"
                                ,commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException
                    (HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto upddateComment(Long postId, Long commentId, CommentDto commentRequest) {
        // retrive post entity by id

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post","id",postId));

        //retrive comment by id
        Comment comment= commentRepository.findById(commentId)
                .orElseThrow(()->
                        new ResourseNotFoundException("Comment","id"
                                ,commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment=  commentRepository.save(comment);

        return mapToDTO(updatedComment);





    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrive post entity by id

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post","id",postId));

    //retrive comment by id
    Comment comment= commentRepository.findById(commentId)
            .orElseThrow(()->
                    new ResourseNotFoundException("Comment","id"
                            ,commentId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        commentRepository.delete(comment);

    }


    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto=mapper.map(comment,CommentDto.class);
//        CommentDto commentDto= new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=mapper.map(commentDto,Comment.class);
//        Comment comment= new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;

    }

}
