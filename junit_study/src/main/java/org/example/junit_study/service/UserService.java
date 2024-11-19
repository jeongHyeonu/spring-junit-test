package org.example.junit_study.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.junit_study.domain.user.User;
import org.example.junit_study.domain.user.UserEnum;
import org.example.junit_study.domain.user.UserRepository;
import org.example.junit_study.dto.user.UserReqDto;
import org.example.junit_study.dto.user.UserRespDto;
import org.example.junit_study.handler.ex.CustomApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional // 트랜잭션이 메서드 시작될 때 시작되고 종료될 때 함께 종료
    public UserRespDto.JoinRespDto 회원가입(UserReqDto.JoinReqDto joinReqDto){
        // 1. 동일 유저이름 존재 검사
        Optional<User> userOP = userRepository.findByUserName(joinReqDto.getUsername());
        if(userOP.isPresent()){
            // 유저 중복일 경우
            throw new CustomApiException("동일한 username 존재");
        }

        // 2. 패스워드 인코딩
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new UserRespDto.JoinRespDto(userPS);
    }

}
