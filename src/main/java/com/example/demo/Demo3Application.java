package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@SpringBootApplication
public class Demo3Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

		return builder -> {

			// formatter
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			// deserializers
			builder.deserializers(new LocalDateDeserializer(dateFormatter));
			builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

			// serializers
			builder.serializers(new LocalDateSerializer(dateFormatter));
			builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));

			JavaTimeModule javaTimeModule = new JavaTimeModule();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
			javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
			builder.modules(new JavaTimeModule());//, new Jdk8Module(), new JavaTimeModule());
			//	builder.findModulesViaServiceLoader(true);
			//	builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

			//objectMapper.registerModule(javaTimeModule);
		};
	}
}
