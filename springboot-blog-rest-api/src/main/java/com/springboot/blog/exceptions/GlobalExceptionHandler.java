package com.springboot.blog.exceptions;

import com.springboot.blog.payload.ErrorrDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice

public class GlobalExceptionHandler {
    //handle specific exceptions
    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ErrorrDetails> handleResourceNotFoundException(ResourseNotFoundException exception,
                                                                         WebRequest webRequest) {
        ErrorrDetails errorrDetails = new ErrorrDetails(new Date(),
                exception.getMessage(), webRequest.getDescription(false
        ));
        return new ResponseEntity<>(errorrDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorrDetails> handleBlogAPIException(BlogAPIException exception,
                                                                WebRequest webRequest) {
        ErrorrDetails errorrDetails = new ErrorrDetails(new Date(),
                exception.getMessage(), webRequest.getDescription(false
        ));
        return new ResponseEntity<>(errorrDetails, HttpStatus.BAD_REQUEST);

    }

    //global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorrDetails> handleGlobalException(Exception exception,
                                                               WebRequest webRequest) {
        ErrorrDetails errorrDetails = new ErrorrDetails(new Date(),
                exception.getMessage(), webRequest.getDescription(false
        ));
        return new ResponseEntity<>(errorrDetails, HttpStatus.INTERNAL_SERVER_ERROR);


    }
}
