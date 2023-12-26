## Hello Spring Server
- 개인 서버입니다~
- 개발자들의 겨울은 끝났고 봄이 왔다...

## 서버 스펙
- Spring Boot 3.2.0
- Java 17
- WAS undertow

## 빌드후 환경별로 서버 실행하는 방법
1. Build를 실행하고 jar 파일을 특정 디렉토리에 이동합니다.
```shell
./gradlew clean build
./gradlew releaseJar
```
2. 개발 버전
```shell
java -jar release/til-api-server.jar --spring.profiles.active=local
```
3. 운영 버전
```shell
java -jar release/til-api-server.jar --spring.profiles.active=prod
```

