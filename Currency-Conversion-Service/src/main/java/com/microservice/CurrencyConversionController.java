package com.microservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}") 
	public CurrencyConversion calculateCurrency(@PathVariable String from, @PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		HashMap<String, String> uriVariable = new HashMap<>();
		uriVariable.put("from", from);
		uriVariable.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new 
				RestTemplate()
				.getForEntity("http://localhost:8000/currency-exchange/from/USD/to/INR",
						CurrencyConversion.class, uriVariable);
		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		return new CurrencyConversion(currencyConversion.getId(),from, to, quantity,
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),
				currencyConversion.getEnvironment()
				);
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}") 
	public CurrencyConversion calculateCurrencyFeign(@PathVariable String from, @PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		CurrencyConversion currencyConversion = currencyExchangeProxy.retreiveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(),from, to, quantity,
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),
				currencyConversion.getEnvironment()
				);
	}

}
