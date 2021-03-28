# COVID19 Data BE Application (Demo Purpose)

## Technology/Library Stack
- Java 8 with Spring Boot
- JPA : Hibernate (Default)
- Embedded Server : Undertow
- PAAS : Heroku
- Validation : Hibernate-validator (Default)
- Caching : Simple ConcurrentHashMap (Default)
- Messaging : RabbitMQ (cloudamqp.com)
- DB : H2 (Local), MySQL (Heroku)

## Application Features
- Profiling : local & heroku (PAAS) 
- Caching
- Logging
- Actuator Monitoring
- Messaging based status updation

## Bootstrapping Application

## REST Endpoints
- https://covid19-data-app-india.herokuapp.com/api/state/list
- https://covid19-data-app-india.herokuapp.com/api/state/{code}

##Other Details
- State details reference : https://en.wikipedia.org/wiki/States_and_union_territories_of_India

## Future Additions
