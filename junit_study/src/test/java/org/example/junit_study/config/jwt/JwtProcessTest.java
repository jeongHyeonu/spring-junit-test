package org.example.junit_study.config.jwt;

import org.example.junit_study.config.auth.LoginUser;
import org.example.junit_study.domain.user.User;
import org.example.junit_study.domain.user.UserEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    @Test
    void create_test() {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        //when
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println(jwtToken);
        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    void verify_test() {
        String jwtToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYW5rIiwiZXhwIjoxNzMyNDIwODc3LCJpZCI6MSwicm9sZSI6IkNVU1RPTUVSIn0.R6KwTsWuxXfmGaL6OmzaYd-SoJxmioJdjaTLcst3JuUrTsMHNVzOUXXx0zRjL4-3cVruf-acNJ6anguYvxOzCQ";

        //when
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println(loginUser.getUser().getId());

        // then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
    }
}