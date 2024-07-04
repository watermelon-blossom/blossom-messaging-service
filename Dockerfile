# 1단계 : Gradle 7.6.2 버전 빌드 이미지 기반으로 프로젝트 빌드
FROM gradle:7.6.2-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /dateapp

# Gradle 설정 파일 및 소스 코드 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src
COPY gradlew ./
COPY gradlew.bat ./

# 전체 애플리케이션 빌드 및 테스트 제외 (gradle wrapper 사용)
RUN ./gradlew clean build -x test --parallel

# 2단계 : OpenJDk 이미지를 사용하여 애플리케이션 실행
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /dateapp

# 빌드 단계에서 생성된 JAR 파일 복사
COPY --from=build /dateapp/build/libs/*.jar app.jar

# 애플리케이션 사용포트 지정
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
