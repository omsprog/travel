## Setup

`cd travel-backend`  
`docker compose up`  
`mvn spring-boot:run`    

## Shell

`./mvnw clean package`  
`TRAVEL_LANDING_PAGE_MESSAGE="Staging env" java -jar target/travel-0.0.1-SNAPSHOT.jar`  

## TODO
- Add Created_at and Updated_at columns, use those columns to sort the results
- Implement Roles/Authorities