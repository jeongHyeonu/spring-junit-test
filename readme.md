# 스프링부트 JUnit 테스트 - 시큐리티를 활용한 Bank 애플리케이션

https://www.inflearn.com/course/lecture?courseSlug=%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-junit-%ED%85%8C%EC%8A%A4%ED%8A%B8&unitId=147266

인프런 강의를 듣고 기록한 스터디 내용입니다.

## 스프링부트 설정 & 세팅

### JWT

프로젝트 생성 시 다른 기본적인 dependency는 제공해 주지만, JWT는 제공하지 않기 때문에, 사용을 위해 MVN에서 찾아서 복사/붙여넣기 하면 사용할 수 있다.

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

[`User.java`](junit_study\src\main\java\org\example\junit_study\domain\user\User.java) [`Account.java`](junit_study\src\main\java\org\example\junit_study\domain\account\Account.java) [`Transaction.java`](junit_study\src\main\java\org\example\junit_study\domain\account\Transaction.java)
