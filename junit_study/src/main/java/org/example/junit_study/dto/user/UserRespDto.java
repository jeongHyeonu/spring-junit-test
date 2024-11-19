package org.example.junit_study.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.junit_study.domain.user.User;
import org.example.junit_study.util.CustomDateUtil;

public class UserRespDto {

    @Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
        }
    }


    @ToString
    @Setter
    @Getter
    public static class JoinRespDto{
        private Long id;
        private  String username;
        private String fullname;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
        }
    }

}
