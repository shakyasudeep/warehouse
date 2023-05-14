# Makefile

# Variables
DOCKER_COMPOSE_FILE := docker-compose.yml

# Build and start the Docker containers
up:
	docker-compose -f $(DOCKER_COMPOSE_FILE) up --build

# Stop and remove the Docker containers
down:
	docker-compose -f $(DOCKER_COMPOSE_FILE) down

# Run unit tests
test:
	mvn test

# Build the application JAR file
build:
	mvn clean package

# Run the application locally
run:
	mvn spring-boot:run

# Clean build artifacts
clean:
	mvn clean

# Help command to display available targets
help:
	@echo "Usage: make [target]"
	@echo ""
	@echo "Available targets:"
	@echo "  up        - Build and start the Docker containers"
	@echo "  down      - Stop and remove the Docker containers"
	@echo "  test      - Run unit tests"
	@echo "  build     - Build the application JAR file"
	@echo "  run       - Run the application locally"
	@echo "  clean     - Clean build artifacts"
	@echo "  help      - Display this help message"

# Default target
.DEFAULT_GOAL := help
