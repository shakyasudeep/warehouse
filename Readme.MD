# Deal Management System

The Deal Management System is a Spring Boot application that allows users to manage deals. It provides functionality to create deals.

## Features

- Create a new deal

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Docker

## Getting Started

### Prerequisites

Make sure you have the following installed on your machine:

- Java Development Kit (JDK) 8 or later
- Docker

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/your-username/warehouse.git
   ```

2. Navigate to the project directory:

   ```shell
   cd warehouse
   ```

3. Build the Docker image:

   ```shell
   docker build -t warehouse .
   ```

4. Run the Docker container:

   ```shell
   docker run -p 8080:8080 warehouse
   ```

5. The application will now be accessible at [http://localhost:8080](http://localhost:8080).

### Usage

The Deal Management System provides a RESTful API to interact with the deals. Here are some sample API endpoints:

- **Create a new deal**

  ```http
  POST /deals/import
  ```

  Request body:

  ```json
  {
    "dealId": "DEAL123",
    "fromCurrency": "USD",
    "toCurrency": "EUR",
    "timestamp": "2023-05-01T10:00:00",
    "amount": 100.0
  }
  ```

### Configuration

The application is configured to use a MySQL database. You can modify the database settings in the `application.properties` file.

### Contributing

Contributions are welcome! If you find any issues or want to add new features, feel free to open a pull request.

### License

This project is licensed under the [MIT License](LICENSE).

### Contact

For any inquiries or feedback, please contact:

- Sudeep Shakya
- Email: shakyasudeep@live.com
- GitHub: [Your GitHub Profile](https://github.com/shakyasudeep)

Feel free to reach out if you have any questions or need further assistance.

---

Thank you for choosing the Deal Management System! We hope this application helps you effectively manage your deals.