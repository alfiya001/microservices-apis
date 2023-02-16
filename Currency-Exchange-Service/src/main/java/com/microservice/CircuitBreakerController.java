package com.microservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CircuitBreakerController {

	private ResponseEntity<Object> forEntity;

	@GetMapping("/sample-api")
//	@Retry(name="sample-api", fallbackMethod = "hardcoded")
	@CircuitBreaker(name="sample-api", fallbackMethod = "hardcoded")
	public String SampleAPI() {
		log.info("sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate()
				.getForEntity("http://localhost:8080/some-dummy", String.class);
		return forEntity.getBody();
	}
	
	public String hardcoded(Exception ex) {
		return "fallback";
	}
}
