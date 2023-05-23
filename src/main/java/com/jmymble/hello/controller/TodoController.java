package com.jmymble.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmymble.hello.dto.ResponseDTO;
import com.jmymble.hello.dto.TodoDTO;
import com.jmymble.hello.model.TodoEntity;
import com.jmymble.hello.service.TodoService;


@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    // testTodo 메서드 작성하기
    // @GetMapping("testTodo")
    public ResponseEntity<?> testTodo() {
        List<String> list = new ArrayList<>();
        list.add(service.testService());
        return ResponseEntity.ok().body(ResponseDTO.<String>builder().data(list).build());
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {
        try {
//            String temporaryUserId = "temporary-user";

            // (1) TodoEntity 로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문
            entity.setId(null);

            // (3) 임시 유저 아이디 설정.
            entity.setUserId(userId);

            // (4) 서비스를 이용해 Todo 엔티티 생성
            List<TodoEntity> entities = service.create(entity);

            // (5) 자바 스트림을 이용해 리턴된 엔티티 리스트 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // (7) ResponseDTO를 리턴
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            // (8) 예외가 발생할 경우 dto 대신 error에 미시지를 넣어 리턴
            return ResponseEntity.badRequest().body(ResponseDTO.<String>builder().error(e.getMessage()).build());
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodo(@AuthenticationPrincipal String userId) {
//        String temporaryUserId = "temporary-user";

        // (1) 서비스 메서드의 retrieve 메서드를 사용해 Todo 리스트 가져오기
        List<TodoEntity> entities = service.retrieve(userId);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {
//        String temporaryUserId = "temporary-user";

        TodoEntity entity = TodoDTO.toEntity(dto);

        entity.setUserId(userId);

        List<TodoEntity> entities = service.update(entity);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {
        try {
//            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setUserId(userId);

            List<TodoEntity> entities = service.delete(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String error = e.getMessage();

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}