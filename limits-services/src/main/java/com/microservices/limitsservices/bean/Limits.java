package com.microservices.limitsservices.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Limits {
	private int minimum;
	private int maximum;
}
