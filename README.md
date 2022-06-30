# 마일리지 서비스 API(Mileage)

- 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트를 관리합니다.
- 히스토리는 MongoDB에 저장됩니다.

### Requirements

- Java >= 1.8

### 기본 구성

- Java 1.8
- Spring Boot 2.7.1-SNAPSHOT
- MySQL 5.7.38
- MongoDB 4.2.21
- lombok
- MapStruct
- CheckStyle
- Gradle
- Swagger
- Docker

### 간편 서버 실행(docker-compose)

```
docker-compose up
```

### 빌드

```
gradle build
```

### 엔드포인트 목록

- http://localhost:8080/api/v1/mileages/events (HTTP:POST)
- http://localhost:8080/api/v1/mileages/{userId} (HTTP:GET)

### Swagger

- http://localhost:8080/swagger-ui/index.html

### MySQL 스키마 및 테이블 생성 쿼리

```
create schema mileage collate utf8mb4_unicode_ci;
use mileage;

create table mileage(user_id binary(16) not null primary key, accumulated_point int not null);

create table review_mileage(place_id binary(16) not null, review_id binary(16) not null, user_id binary(16) not null,
first_review bit not null, has_content bit not null, has_photo bit not null, primary key (place_id, review_id, user_id))
;
```
