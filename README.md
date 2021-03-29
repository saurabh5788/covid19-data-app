# COVID19 Data BE Application (Demo Purpose)
This is a sample Java / Maven / Spring Boot (version 2.4.3) application. It is a basic back-end service which can collect the State wise Covid19 case updates and provide a consolidated data. 

## Technology/Library Stack
- Java 8 with Spring Boot
- JPA : Hibernate (Default)
- Embedded Server : Undertow
- Hosting : Heroku (PAAS)
- Validation : Hibernate-validator (Default)
- Caching : Simple ConcurrentHashMap (Default)
- Messaging : RabbitMQ (@cloudamqp.com)
- DB : H2 (Local), MySQL (Heroku)
- API Doc : Swagger 

## Application Features
- Profiling : local & heroku 
- Caching
- Logging
- Actuator Monitoring

## Running Application on local Machine
- Clone this repository
- Build and run the project : 
	```
	mvnw clean install -DENV=local
	java -jar target\covid19-data-app.jar
	```
	or
	```
	mvnw spring-boot:run -DENV=local
	```

## REST Endpoints
- https://covid19-data-app-india.herokuapp.com/api/swagger-ui/

## Data References
- State details reference : https://en.wikipedia.org/wiki/States_and_union_territories_of_India
- State Population : https://www.indiatoday.in/education-today/gk-current-affairs/story/indian-states-with-highest-population-1358414-2018-10-08

