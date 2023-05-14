# Deal Management System

The Deal Management System is a Spring Boot application designed to manage deals between different currencies. It provides functionality to create, retrieve, update, and delete deals. The application utilizes Spring Data JPA for data persistence and exposes RESTful APIs for interacting with the deals.

## Features

- Create a new deal
- Retrieve a deal by ID
- Update a deal
- Delete a deal
- List all deals

## Technologies Used

The Deal Management System is built using the following technologies:

- Java
- Spring Boot
- Spring Data JPA
- H2 Database (for development)
- MySQL Database (for production)
- Docker (for containerization)

## Getting Started

To get started with the Deal Management System, follow these steps:

1. **Clone the repository** - Clone the repository to your local machine using the following command:

   ```
   git clone https://github.com/your-username/deal-management-system.git
   ```

2. **Configure the database** - Update the `application.properties` file with the appropriate database configuration. For development, use the H2 Database. For production, use MySQL Database.

3. **Build the project** - Navigate to the project directory and build the project using Maven:

   ```
   cd deal-management-system
   mvn clean install
   ```

4. **Run the application** - Execute the following command to run the application:

   ```
   mvn spring-boot:run
   ```

5. **Access the API** - Once the application is running, you can access the API endpoints using a tool like Postman or cURL. The base URL for the API is `http://localhost:8080/api/deals`.

6. **Explore the API documentation** - The API documentation is available at `http://localhost:8080/swagger-ui.html` when the application is running. Swagger UI provides an interactive interface to explore the available endpoints, send requests, and view responses.

## Testing

The Deal Management System includes unit tests to ensure the correctness of its functionality. You can run the tests using the following command:

```
mvn test
```

## Deployment

The Deal Management System can be deployed using Docker. To deploy the application using Docker, follow these steps:

1. **Build the Docker image** - Execute the following command to build the Docker image:

   ```
   docker build -t deal-management-system .
   ```

2. **Run the Docker container** - Run the Docker container using the following command:

   ```
   docker run -p 8080:8080 deal-management-system
   ```

3. **Access the application** - The application will be accessible at `http://localhost:8080` from your web browser.

## Contributing

Contributions to the Deal Management System are always welcome. Here's how you can contribute:

1. Fork the repository
2. Create a new branch
3. Make your changes
4. Commit your changes
5. Push the branch
6. Submit a pull request

Please make sure to follow the existing coding style and provide detailed information about the changes you made.

## License

The Deal Management System is licensed under the [MIT License](LICENSE).

## Contact

For any inquiries or feedback, please contact:

- Your Name
- Email: yourname@example.com
- GitHub: [Your GitHub Profile](https://github.com/your-username)

Feel free to reach out if you have any questions or need further assistance.

---

Thank you for choosing the Deal Management System! We hope this application helps you effectively manage your deals.