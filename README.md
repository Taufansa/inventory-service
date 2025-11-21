# INVENTORY-SERVICE-API

#### How To Run Service
- run **/queries/ddl.sql** in your local database postgres
- add environment variables in your local machine
```
#adjust value following your local setups
DB_HOST=127.0.0.1;DB_NAME=inventory_db;DB_PASS=postgres;DB_PORT=5432;DB_USERNAME=postgres
````
- run this command in root project directory for running back end service
```
  mvn spring-boot:run
```
- if you using docker, you can run docker build to build image in your local docker and run as docker container. dockerfile script are in root directory

#### Design
- I am create one simple service that handle item, variant, stock, price and order
- I am just implement builder pattern with SOLID principle
- I am implement micrometer to define trace id and span id for each request in service, this will define log pattern in our service and can easily indexed for logging tools
- I am implement reference number for each request
- Ready to containerization this service

#### Assumption
- process order deduct item's stock depend on state
- inventory service are hit on order process
- this service are hit on internal microservices

#### API Endpoint Examples
- local swagger http://127.0.0.1:8082/inventory/api/swagger-ui/index.html
- postman collection **Inventory API.postman_collection.json** in root directory


