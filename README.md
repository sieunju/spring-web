## Hello Spring Server
- 개인 서버입니다~
- 개발자들의 겨울은 끝났고 봄이 왔다...

## 서버 스펙
- Spring Boot 3.2.0
- Java 17
- WAS undertow
- Swagger 3.0.0

## API 정의서
- [Local](http://localhost:10004/swagger-ui/index.html)
- [Production](https://til.qtzz.synology.me/swagger-ui/index.html)

## 빌드후 환경별로 서버 실행하는 방법
1. Build를 실행하고 jar 파일을 특정 디렉토리에 이동합니다.
```shell
./gradlew clean build -x test
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

## Exception 정의
1. JSendException
   - HTTP Status : 400
   - Message: ErrorMessage
2. JpaSystemException
   - HTTP Status : 400
   - Message: 올바르지 않은 데이터 입니다.
3. IllegalArgumentException
   - HTTP Status : 400
   - Message: ErrorMessage
4. InvalidAuthException
   - HTTP Status : 500
   - Message: 유효하지 않는 토큰입니다.
5. MethodArgumentNotValidException
   - HTTP Status : 400
   - Message: 유효한 값이 없습니다.
6. Exception
   - HTTP Status : 500
   - Message: ErrorMessage
7. ExpiredAuthException
   - HTTP Status : 401
   - Message: 만료된 토큰입니다.
