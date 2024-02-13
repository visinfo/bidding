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