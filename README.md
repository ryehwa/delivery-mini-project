## 28조 - 럭키비키

### 팀원 역할분담
- `례화` : 팀장
  - 요구사항 정의서 작성, ERD, API 명세서 작성
  - 프로젝트 초기 세팅
  - 상품(AI 연동) 및 배송지 API 구현


- `범수` : 부팀장
  - ERD, API 명세서 작성
  - 주문 및 결제 API 구현
  - 도전 1) 리뷰 및 평점 기능 구현


- `종영` : 팀원
  - 테이블 명세서 작성, ERD, API 명세서 작성
  - 회원 및 인증, 인가 API 구현
  - 프로젝트 배포


- `원준` : 팀원
  - 인프라 설계서 작성, ERD, API 명세서 작성
  - 가게 및 카테고리 API 구현
  - 도전 2) 공지사항 및 고객센터(신고) 기능 구현
  
<br>

### 서비스 구성 및 실행방법
1. Git Clone
```bash
git clone https://github.com/ryehwa/delivery-project.git
```
<br>

2. application-{profile}.yaml 파일 작성

- yaml 파일 예시
```yaml
# application-local.yaml

spring:
  data:
    redis:
      host: ${host}
      port: ${port}
      username: ${username}
      password: ${password}
  datasource:
    url: jdbc:postgresql://${host}:${port}/${database}
    username: ${username}
    password: ${password}
    driver-class-name: org.postgresql.Driver
  jpa:
    show-sql: true
    database: postgresql
    generate-ddl: true
    hibernate:
      ddl-auto: update
app:
  jwtSecret: ${secretKey}
  jwtExpirationInMs: ${accessTokenExpirationTime}
  jwtRefreshExpirationMs: ${refreshTokenExpirationTime}
```
<br>

3. Redis, PostgreSQL 설치 및 실행 (Docker 설치 권장)
- 도커 명령어
```bash
docker run -d -p 6379:6379 --name redis redis
docker run -d -p 5432:5432 -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -e POSTGRES_DB=${database} postgres
```
<br>

4. 어플리케이션 빌드
```bash
./gradlew build -x test
```
<br>

5. 어플리케이션 실행
```bash
java -jar build/libs/delivery-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```
<br>

6. API 테스트
- Postman 등의 API 테스트 도구를 사용하여 API 테스트
<br><br>

### 프로젝트 개요 
- 배달 및 포장 주문 관리 플랫폼을 개발
- 주문 접수, 결제 처리, 주문 내역 관리 등의 기능을 제공하며, 초기 운영 지역을 광화문 근처로 제한하되, 
향후 확장을 고려한 유연한 시스템 설계를 목표로 합니다.
- AI 기술을 활용하여 상품 설명을 자동으로 생성하고, 데이터의 보존 및 감사 로그를 통해 데이터의 안전성을 강화합니다.

<br>

### 프로젝트 주요 기능
- 가게 관리
  - 가게 CRUD: 음식점 정보 조회, 등록, 수정, 삭제 기능
  - 가게 분류: 한식, 중식, 분식, 치킨, 피자 등 다양한 카테고리로 분류
- 상품 관리
  - 상품 CRUD: 음식점 상품을 조회, 등록, 수정, 삭제 기능
  - 상품 설명: 상품 등록 시 AI를 이용해 자동으로 설명을 생성하는 기능
- 주문 관리
  - 주문 유형: 온라인 주문과 대면 주문(가게에서 직접 주문) 지원
  - 주문 취소: 주문 생성 후 5분 이내에만 취소 가능
- 결제 시스템
  - 결제 방식: 카드 결제만 지원
  - 결제 연동: 외주 개발된 PG사와 연동되며, 결제 내역은 플랫폼 DB에 저장
- 접근 권한 관리
  - 고객: 자신의 주문 내역만 조회, 리뷰 및 평점 작성
  - 가게 주인: 가게 정보 및 상품, 주문 내역 관리
  - 관리자: 모든 가게 및 주문에 대한 전체 권한 보유
- 데이터 보존 및 감사 로그
  - 모든 데이터는 완전 삭제되지 않고 숨김 처리
  - 모든 테이블에 생성, 수정, 삭제 정보에 대한 감사 로그 필드 추가
- 리뷰 및 평점
  - 고객이 주문한 상품에 대한 리뷰 및 평점 작성 기능
- 공지사항 및 신고
  - 공지사항 CRUD 기능
  - 고객이 가게, 리뷰, 다른 유저 신고 기능
  

  <br>
  

### 프로젝트 목적/상세
이 프로젝트의 주된 목적은 음식점들이 보다 효율적으로 배달 및 포장 주문을 관리할 수 있는 
플랫폼을 제공하는 것입니다. 고객은 앱을 통해 간편하게 음식을 주문하고, 가게 사장님은 주문을 실시간으로 관리하며, 
관리자는 전체 플랫폼을 운영할 수 있습니다. 이 플랫폼은 확장성을 고려한 유연한 설계로, 
향후 더 넓은 지역과 다양한 음식점 카테고리로 확장이 가능합니다. 또한, AI 기술을 연동하여 
상품 설명을 자동으로 생성함으로써 가게 사장님들의 편의성을 높이며, 데이터의 보존 및 감사 로그 기능을 통해 
데이터의 안전성과 추적 가능성을 강화하는 것을 목표로 합니다.

<br>

### ERD
![delivery-service-ERD](https://github.com/user-attachments/assets/c8b9f1f6-8f92-424a-8d48-93793c593378)

```sql
p_audit: 로그 감사 - 테이블로 존재하지는 않습니다. 모든 엔티티가 상속받아 사용합니다.

// Entity Mapping
가게:상품 = 일대다
가게:주문 = 일대다
상품:주문 = 다대다
가게:카테고리 = 다대다
유저:배송지 = 다대다
주문:배송지 = 다대다
유저:주문 = 일대다
유저:결제 = 일대다
가게:결제 = 일대다
주문:결제 = 일대일
유저:공지사항 = 일대다
유저:신고 = 일대다
```

<br>

### 기술 스택
<div style="width:50%">
  <img src="https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/>
  <img src="https://img.shields.io/badge/spring%20data%20jpa-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>
  <br>
  <img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
  <img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink"/>
  <br>
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white">
  <img src="https://img.shields.io/badge/GCP-4285F4?style=for-the-badge&logo=googlecloud&logoColor=white">
</div>

<br>

### API 명세서
https://amenable-galette-be8.notion.site/API-e4661b6bacea41409e23c9eb80b49ce9?pvs=4
