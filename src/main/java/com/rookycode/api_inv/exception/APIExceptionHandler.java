package com.rookycode.api_inv.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.rookycode.api_inv.entity.ResponseDTO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.java.Log;

@Log
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info(ex.getMessage());
        
        List<FieldError> bindingResult = ex.getBindingResult().getFieldErrors();
        
        List<String> details = new ArrayList<>();
        for(FieldError fieldError: bindingResult){
            details.add(fieldError.getDefaultMessage());

        }
        ResponseDTO<?> responseDTO = ResponseDTO.builder()
            .status(status.toString())
            .message("Invalid data")
            .details(details).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        log.info(ex.getMessage());

        ResponseDTO<?> responseDTO = ResponseDTO.builder()
            .status(HttpStatus.BAD_REQUEST.toString())
            .message(ex.getMessage()).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

}