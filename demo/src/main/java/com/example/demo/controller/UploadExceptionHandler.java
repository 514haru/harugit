package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UploadExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public  @ResponseBody Map<String,Object> uploadException(MaxUploadSizeExceededException e) throws IOException {

        Map<String,Object> map = new HashMap<>();
        map.put("msg","最大上传文件为10M，上传文件大小超出限制!");
        map.put("result",false);
        return map;
    }

//    @ExceptionHandler(Exception.class)
//    public void myexce(Exception e) {
//        System.out.println("myexce>>>"+e.getMessage());
//    }
}
