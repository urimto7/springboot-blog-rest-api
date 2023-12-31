package com.springboot.blog.exceptions;

import com.springboot.blog.payload.ErrorrDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
          String fieldName=  ((FieldError)error).getField();
          String message=error.getDefaultMessage();
          errors.put(fieldName,message);

        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

    }
}
