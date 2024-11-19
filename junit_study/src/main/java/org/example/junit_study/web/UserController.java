package org.example.junit_study.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.junit_study.dto.ResponseDto;
import org.example.junit_study.dto.user.UserReqDto;
import org.example.junit_study.dto.user.UserRespDto.JoinRespDto;
import org.example.junit_study.dto.user.UserReqDto.JoinReqDto;
import org.example.junit_study.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String,String> errMap = new HashMap<>();

            for(FieldError error:bindingResult.getFieldErrors()){
                errMap.put(error.getField(),error.getDefaultMessage());
            }

            return new ResponseEntity<>(new ResponseDto<>(-1,"유효성검사실패",errMap),HttpStatus.BAD_REQUEST);
        }
        JoinRespDto dto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원가입성공",dto), HttpStatus.CREATED);
    }

}
