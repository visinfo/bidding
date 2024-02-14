## Deutsche Bank Coding Challenge - Bid Auction System 
### Introduction
This project is a simple implementation of a bid auction system. The system is designed to  allow users to place bids on items. The system is implemented using Spring Boot and H2 in-memory database. The system is designed to be simple and easy to use and understand for the purpose of the coding challenge.

### Modules 
The project is divided into three modules:
1. **auction**: This module contains the API for the bid auction system. It contains the domain model, repository, service and controller for the bid auction system.
    - Assumption: Focus on the core functionality of the bid auction system. Also, unit tests are not covering the entire code base.
2. **product**: This module contains the API for the product service. It contains the domain model, repository, service and controller for the product service.
3. **user**: This module contains the API for the user service. It contains the domain model, repository, service and controller for the user service.
    - Assumption: User service is not implemented to reduce the scope. However, the user service can also be implemented to manage users in the system. User service will issue dynamic user Identifier for each user,to compile with PII regulations.

### How to run the application 
1. Clone the repository
2. Run the following command to start the application
    ```
    ./gradlew bootRun
    ```
3. The application will start on port 8080. You can access the application using the following URL:
    ```
    http://localhost:8080
    ```
4. You can use the following endpoints to interact with the application:
    - **Product Service**
        - GET /products/{id}
        - POST /products
    - **Bid Auction Service**
        - GET /auctions/{auction_id} : Get auction by id
        - POST /auctions : Create new auction
        - PUT /auctions/{auction_id}/endAuction: End auction and select winner
        - Patch /auctions/{auction_id}/placeBid : Place new bid on auction 
        - Get /auctions/{auction_id}/winner : Get winner of and auction
    - **User Service**
        Todo: User service is not implemented. However, it can be implemented to manage users in the system. User service will issue dynamic user Identifier for each user,to compile with PII regulations.
    Please see the [API documentation](docs/bidding-api.yaml) for more details on how to use the application.

### How to run the tests
1. Run the following command to run the tests
    ```
    ./gradlew test
    ```
2. The tests will run and the results will be displayed in the console.
3. You can also view the test report by opening the following file in a browser:
    ```
    build/reports/tests/test/index.html
    ```
   
### How to view the database
1. The application uses an in-memory H2 database. You can view the database by opening the following URL in a browser:
    ```
    http://localhost:8080/h2-console
    ```
   
2. The database URL is `jdbc:h2:mem:testdb` and the username is `sa`. You can connect to the database using these credentials.
3. You can view the tables and data in the database using the H2 console.

### How test apis 
   You can run below curl commands to test the apis
   - **Create Auction**
   ```
      curl --location 'http://localhost:8080/auctions/' \
--header 'Content-Type: application/json' \
--data '{

"productId":"1",
"minimumBid": 100.0,
"title":"Auction 1"

}'
   ```
   - **Get Auction**
   ```
      curl --location 'http://localhost:8080/auctions/1' \
      --header 'Content-Type: application/json' 
         
   ```
    
   - **Place Bid**
   ```
     curl --location --request PATCH 'http://localhost:8080/auctions/placeBid/1' \
     --header 'Content-Type: application/json' \
     --header 'Cookie: JSESSIONID=91D62AAD979A192BC1DA8C26A34477AC' \
     --data '{
     "bidderId": 1212123,
     "bidAmount": 101
     }'
   ```
   - **End Auction**
   ```
     curl --location --request PUT 'http://localhost:8080/auctions/1/endAuction' \
     --header 'Content-Type: application/json' 
   ```
   - **Get Winner**
   ```
     curl --location 'http://localhost:8080/auctions/1/winner' \
     --header 'Content-Type: application/json' 
   ```