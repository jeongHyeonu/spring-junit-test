package org.example.junit_study.config;

import org.example.junit_study.domain.user.UserEnum;
import org.example.junit_study.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean // Ioc 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨.
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            //builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            //builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }

        public HttpSecurity build(){
            return getBuilder();
        }
    }

    // JWT 서버를 만들 예정!! Session 사용안함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");

        http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
        http.csrf(cf->cf.disable());
        http.cors(co->co.configurationSource(configurationSource()));

        http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(f->f.disable());

        http.httpBasic(hb->hb.disable());

        // 필터 적용
        http.with(new CustomSecurityFilterManager(), c-> c.build());

        // Exception 가로채기
        http.exceptionHandling(e-> e.authenticationEntryPoint((request, response, authenticationException) -> {
            CustomResponseUtil.fail(response,"로그인을 진행 해 주세요.", HttpStatus.UNAUTHORIZED);
        }));

        http.exceptionHandling(e-> e.accessDeniedHandler((request, response, accessDeniedException) -> {
            CustomResponseUtil.fail(response,"권한이 없습니다.", HttpStatus.FORBIDDEN);
        }));

        http.authorizeHttpRequests(c->
                c.requestMatchers("/api/s/**").authenticated()
                        .requestMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN)
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
        );

        return http.build();
    }

        public CorsConfigurationSource configurationSource() {
            log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedHeader("*");
            configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
            configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
            configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
            configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
}