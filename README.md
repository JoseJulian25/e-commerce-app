# E-Commerce Microservices Backend

## Description:
This project is a personal initiative aimed at creating an e-commerce application using a microservices architecture. The main objective is to apply and consolidate knowledge about this architecture, exploring the challenges and solutions associated with implementing microservices in a practical environment. Rather than focusing on robustness of functionality or advanced security aspects, this project focuses on understanding the fundamental concepts and patterns that arise when designing and developing distributed systems with Spring Boot.

## Applied Knowledge
In the development of this project, a series of key practices and technologies have been implemented in the microservices architecture, such as:

- <b> Centralized Configuration: </b> Centralized configuration management for all microservices, allowing flexibility and consistency in development and production environments.

- <b> Service Discovery: </b> Implementation of a service registry that facilitates location and dynamic communication between microservices.

- <b> API Gateway: </b> Using a gateway to centralize client requests, improve security, and manage communication between microservices.

- <b> Distributed Traceability: </b> Integration of tools to track and monitor interactions between microservices, facilitating performance analysis and problem identification.

- <b> Event-Oriented Architecture: </b> Implementation of an event-based communication model to decouple microservices and improve scalability.

- <b> Caching: </b> Using cache to optimize system performance, especially for frequent read operations.

- <b> Communication with gRPC: </b> Implementation of gRPC for efficient communication between services with support for strict contracts and high performance.

- <b> OAuth: </b> OAuth integration for user authentication and authorization, ensuring that interactions with microservices are protected.

- <b> Resilience with Circuit Breaker: </b> Application of the Circuit Breaker pattern to improve the system's resilience against failures and prevent the propagation of errors.

## Tecnologies used
- Java 17
- Spring boot 3.2
- Spring Cloud
- Apache Kafka
- Config server
- Zipkin
- gRPC
- OpenFeign
- Keycloack
- Maildev
- MySQL
- MongoDB
- PostgreSQL
- Flyway
- Thymeleaf
- Docker

## Architecture Diagram
![E-commerce diagram drawio](https://github.com/user-attachments/assets/4bbfe014-d872-4185-a0b7-adfad54082d0)


### Microservices Interaction When Placing an Order:
When a customer places an order in the application, a series of interactions are triggered between various microservices to ensure the correct execution of the entire process. The communication flow between microservices is described below:

1. <b> Client authentication: </b> Before any process begins, the client must be authenticated. This is achieved through `Keycloak`, which integrates with API Gateway to ensure that only authenticated users can proceed with the order.
   
2. <b> Customer Validation: </b> The Order Service communicates with the `Customer Service` using OpenFeign to validate the ID of the customer who is placing the order. This verification ensures that the client exists and is authorized to proceed.
   
3. <b> Product and Stock Validation: </b> Once the client is validated, the Order Service communicates with the Product Service using `gRPC`. During this interaction, the Product Service verifies the stock availability and validity of the products included in the order. If all products are valid and the stock is sufficient, the Product Service sends the confirmation to the Order Service. The product list `cache` is cleared, and the modified single product cache is updated. This mechanism ensures that the information presented to users is accurate and that application performance remains optimal.
   
4. <b> Order Processing: </b> With the customer validation and the confirmed products, the Order Service proceeds to create the order. Once created, the Order Service publishes a message to the Message Broker (implemented with `Apache Kafka`). This message notifies other services that a new order has been created.
   
5. <b> Payment Processing: </b> The Payment Service, which is subscribed to the `Message Broker message`, processes the payment associated with the order. Once the payment is successful, the Payment Service sends another message through `Kafka` indicating that the payment has been completed.
   
6. <b> Customer Notification: </b> The Notification Service, also subscribed to the Message Broker, receives both the notification of the order creation and the payment confirmation. This service is responsible for sending emails to the customer to confirm that the order has been placed and that the payment has been processed. Emails are generated using templates created with `Thymeleaf`, allowing for dynamic, personalized formatting and content.
   
7. <b> Traceability with Zipkin: </b> Finally, all the traceability of these interactions between microservices can be visualized using `Zipkin`. This allows developers and operators to monitor the entire request flow, identify any potential delays or failures in communication between services, and ensure that the entire process is carried out efficiently.

## Trace motoring:
![image](https://github.com/user-attachments/assets/98488068-0d5d-4b27-a78a-7789923cbf1d)

