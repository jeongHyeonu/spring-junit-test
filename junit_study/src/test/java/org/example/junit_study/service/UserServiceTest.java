package org.example.junit_study.service;

import org.example.junit_study.domain.user.User;
import org.example.junit_study.domain.user.UserRepository;
import org.example.junit_study.dto.user.UserRespDto.JoinRespDto;
import org.example.junit_study.dto.user.UserReqDto.JoinReqDto;
import org.example.junit_study.config.dummy.DummyObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Spring 관련 Bean들이 하나도 없는 환경!!
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        // stub 1
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());
        // when(userRepository.findByUsername(any())).thenReturn(Optional.of(new
        // User()));

        // stub 2
        User ssar = newMockUser(1L,"ssar","쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        // when
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        System.out.println("테스트 : " + joinRespDto);

        // then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");
    }

}