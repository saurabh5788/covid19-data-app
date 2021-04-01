# COVID19 Data BE Application (Demo Purpose)
It is a basic back-end service which collects the State wise Covid19 case updates and provide a consolidated information. 

## Technology/Library Stack
- Java 8 with Spring Boot (2.4.3)
- JPA : Hibernate (Default)
- Server : Undertow (Embedded)
- Hosting : Heroku (PAAS)
- Validation : Hibernate-validator (Default)
- Caching : Simple ConcurrentHashMap (Default)
- Messaging : RabbitMQ (@cloudamqp.com)
- DB : H2 (Local), MySQL (Heroku)
- API Doc : Swagger 
- API Security : JWT Token
- Monitoring : Actuator, Micrometer

## Pending
- Standardized Error Codes
- Error Handling in Messaging
- Implementation of User Roles
- Token Expiration handling 
- Google Signup/Signin
- CI/CD Pipeline using Travis
- Implementation of Spring Batch for purpose of scheduling all update messages accumulated in 1 day at once.

## Running Application on local Machine
1. Make sure you have [Java 8](https://www.java.com/download/) and GIT
2. Clone this repository 
```
$ git clone https://github.com/saurabh5788/covid19-data-app.git
```
3. Run the project
```
$ mvnw spring-boot:run -DENV=local
```
4. Navigate to `http://localhost:8081/api/swagger-ui.html` in your browser to check everything is working correctly.
5. Make a GET request to `http://localhost:8081/api/jwt/token` with the `username` and `password` sent as Headers parameters. In case of valid credentials the response will be JWT Token as below with 5 minute validity:
```javascript
{
  "token" : "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2MTcxMDM4NDYsImlhdCI6MTYxNzEwMzU0Nn0.Mrd-KKz1HmHDmXqEst1i9HTi7E1i10AtykIn_Xfubkwx7cQ2lhDHNGYS7q__8BEffhjFX7iFLpj51YUHA7Av5A"
}
```
6. Add `Authorization` header with `Authorization: Bearer <token>` for every request to secured endpoint.

## URLs
- API Docs : http://localhost:8081/api/swagger-ui/
- Local H2 DB URL : http://localhost:8081/api/h2-console

## State Data References
- State details reference : https://en.wikipedia.org/wiki/States_and_union_territories_of_India
- State Population : https://www.indiatoday.in/education-today/gk-current-affairs/story/indian-states-with-highest-population-1358414-2018-10-08

## Contribution
- Saurabh Singh <saurabh.singh5788@gmail.com>

