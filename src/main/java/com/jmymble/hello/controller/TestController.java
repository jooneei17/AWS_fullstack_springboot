package com.jmymble.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmymble.hello.dto.ResponseDTO;
import com.jmymble.hello.dto.TestRequestBodyDTO;
import com.jmymble.hello.dto.TodoDTO;
import com.jmymble.hello.model.TodoEntity;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController() {
        return "hello world!";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "Hello World! testGetMapping";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
        return "Hello World! ID" + id;
    }

    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(defaultValue = "20") Integer id) {
        return "Hello World!" + id;
    }

    @GetMapping("requestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World id : " + testRequestBodyDTO.getId() + ", message : " + testRequestBodyDTO.getMessage();
    }

    @GetMapping("requestDTO")
    public String testControllerDTO(TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World id : " + testRequestBodyDTO.getId() + ", message : " + testRequestBodyDTO.getMessage();
    }

    // 반환 테스트
    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("hello world I'm responseDTO");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return responseDTO;
    }

    @GetMapping("testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseEntity. And you got 400!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        // http status를 400으로 설정
        return ResponseEntity.badRequest().body(response);
    }

}
