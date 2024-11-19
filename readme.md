# 스프링부트 JUnit 테스트 - 시큐리티를 활용한 Bank 애플리케이션

[강의 링크](https://www.inflearn.com/course/lecture?courseSlug=%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-junit-%ED%85%8C%EC%8A%A4%ED%8A%B8&unitId=147266)

인프런 강의를 듣고 기록한 스터디 내용입니다.

<h1>section 1.

## 스프링부트 설정 & 세팅

### JWT

프로젝트 생성 시 다른 기본적인 dependency는 제공해 주지만, JWT는 제공하지 않기 때문에, 사용을 위해 [MVN](https://mvnrepository.com/artifact/com.auth0/java-jwt/4.4.0) 에서 찾아서 복사/붙여넣기 하면 사용할 수 있다.

`build.gradle`

<p align="center">
 <img src = "./image/1.png">
</p>

### yml

application.yml로 프로젝트 환경 설정이 가능하다.
아래와 같이 yml을 개발용/테스트용/배포용 여러개 나눠 사용 가능하다.

```
개발용 application.yml 을 실행할 경우

spring:
  profiles:
    active:
      - dev
```

`application-dev.yml`
`application-test.yml`
`application-prod.yml`

<p align="center">
 <img src = "./image/2.png">
</p>

## 테이블 설계 및 엔티티 생성

bank 애플리케이션을 위한 테이블을 생성한다.

이때 user 테이블과 account 테이블을 1:N 으로 설계하고,

account 테이블과 transaction 테이블을 1:N 으로 설계한다.

**_Jpa LocalDataTime 자동 생성하는 법_**

- @EnableJpaAuditing ( Main 클래스 )
- @EntityListeners(AuditingEntityListener.class) ( Entity 클래스 )

```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, update
    @Column(nullable = false)
    private LocalDateTime createdAt;
```

테이블(jpa) 의 속성 관련 코드는 각 테이블 별 링크 참조

[`User.java`](https://github.com/jeongHyeonu/spring-junit-test/blob/main/junit_study/src/main/java/org/example/junit_study/domain/user/User.java) [`Account.java`](https://github.com/jeongHyeonu/spring-junit-test/blob/main/junit_study/src/main/java/org/example/junit_study/domain/account/Account.java) [`Transaction.java`](https://github.com/jeongHyeonu/spring-junit-test/blob/main/junit_study/src/main/java/org/example/junit_study/domain/transaction/Transaction.java)

<h1>section 2.</h1>

## 스프링부트 시큐리티 세팅

강의에서 사용한 시큐리티 필터 체인 코드는 [링크](https://github.com/codingspecialist/junit-bank-class/blob/main/src/main/java/shop/mtcoding/bank/config/SecurityConfig.java) 에서 확인할 수 있다.

강의에 오류가 있는데, 위의 코드를 그대로 사용하면 최신 버전의 경우 deprecated 된 부분이 있어 오류가 발생한다.

최신 버전으로 수정한 코드는 [링크](junit_study\src\main\java\org\example\junit_study\config\SecurityConfig.java)에서 확인할 수 있다.

```java
기존 버전
http.headers().frameOptions().sameOrigin();
```

위와 같은 메서드 체이닝이 아닌 아래의 람다식을 통해 함수형으로 설정하는 것을 지향한다.

```java
최신 버전
http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
```

**시큐리티 6.0부터는 websecurityconfigureradapter가 deprecated가 되고 SecurityFilterChain bean을 생성하도록 변경되었음을 유의하자.**

<h1>section 3.</h1>


회원가입 controller 만들기

(딱히 중요한거 없어서 생략..)


<h1>section 4.

## 스프링부트 JWT 인증과 인가

해당 과정을 잘 알아두자.

UPAF(인증) / BAF(인가)

<p align="center">
 <img src = "./image/3.png">
</p>

<h1>section 5.</h1>

### 계좌등록 서비스/컨트롤러 테스트