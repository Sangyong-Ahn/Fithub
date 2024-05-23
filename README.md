# Fithub

스프링부트와 Vue.js를 사용한 웹 애플리케이션 프로젝트입니다.

## 목차

- [소개](#소개)
- [기술 스택](#기술-스택)
- [설치](#설치)
- [백엔드 실행](#백엔드-실행)
- [프론트엔드 실행](#프론트엔드-실행)

## 소개

이 프로젝트는 스프링부트를 이용한 RESTful API 서버와 Vue.js를 이용한 프론트엔드 애플리케이션으로 구성되어 있습니다.

## 기술 스택

- **백엔드**: Spring Boot, MySQL
- **프론트엔드**: Vue.js, Vue Router, Axios
- **빌드 도구**: Maven, Node.js, npm

## 설치

### 사전 요구 사항

- Java 11 이상
- Maven
- Node.js (v14 이상)
- npm

### 백엔드 실행

#### 의존성 설치
```bash
cd backend
mvn clean install
```
#### 데이터베이스 설정 
src/main/resources/application.properties 파일을 열어 데이터베이스 설정을 업데이트합니다.
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
```
#### 서버 실행 
```bash
mvn spring-boot:run
```
백엔드 서버는 기본적으로 http://localhost:8080에서 실행됩니다.

### 프론트엔드 실행 

#### 의존성 설치
```bash
cd frontend
npm install
npm install openai
npm install bootstrap
```

#### 개발 서버 실행
```bash
npm run dev
```
프론트엔드 개발 서버는 기본적으로 http://localhost:5173에서 실행됩니다.

