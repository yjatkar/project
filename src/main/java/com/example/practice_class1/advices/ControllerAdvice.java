package com.example.practice_class1.advices;

import com.example.practice_class1.Dtos.ErrorDto;
import com.example.practice_class1.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    //Add Exception Handler
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException)
    {
        ErrorDto errorDto=new ErrorDto();
        errorDto.setMessage(productNotFoundException.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);

    }

}


// IF FROM ANY CONTROLLER WE GET EXCEPTION THIS WILL HANDLE THAT