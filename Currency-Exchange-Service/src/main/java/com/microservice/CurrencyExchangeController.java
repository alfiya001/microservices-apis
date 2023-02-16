package com.microservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/currency-exchange")
@RestController
public class CurrencyExchangeController {
	
		@Autowired
		private CurrencyExchangeRepository repository;
	
		@Autowired
		private Environment environment;

		@GetMapping("/from/{from}/to/{to}")
		public CurrencyExchange retreiveExchangeValue(@PathVariable String from,
				@PathVariable String to) {
			String port =environment.getProperty("local.server.port");
			CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
			currencyExchange.setEnvironment("8000");
			if(currencyExchange==null)
					throw new RuntimeException("Unable to find currency for "+ from +" and to " + to);
//			repository.findByFromAndTo(from, to);
			return currencyExchange;
		}
}
