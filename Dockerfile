FROM openjdk:11-jdk

WORKDIR /app

COPY target/ClusteredData-Warehouse-0.0.1.jar ClusteredData-Warehouse.jar

CMD ["java", "-jar", "ClusteredData-Warehouse.jar"]
