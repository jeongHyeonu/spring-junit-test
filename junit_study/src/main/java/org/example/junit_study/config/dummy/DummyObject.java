package org.example.junit_study.config.dummy;

import org.example.junit_study.domain.user.User;
import org.example.junit_study.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {
    protected User newUser(String userName, String fullName){
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String encPassword = pwdEncoder.encode("1234");
        return User.builder()
                .username(userName)
                .password(encPassword)
                .email(userName+"@nate.com")
                .fullname(fullName)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    protected User newMockUser(Long id, String userName, String fullName){
        BCryptPasswordEncoder passwordEnc = new BCryptPasswordEncoder();
        String encPassword = passwordEnc.encode("1234");
        return User.builder()
                .id(1L)
                .username(userName)
                .password(encPassword)
                .email(userName+"@nate.com")
                .fullname(fullName)
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
