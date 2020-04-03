package com.solum.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solum.app.dto.Product;
import com.solum.app.gateway.IntegrationGateway;

@RestController
@RequestMapping("/message")
public class IntegrationContoller {

	@Autowired
	private IntegrationGateway integrationGateway;
	
	@GetMapping(value ="{msg}")
	public String getIntegratedMessage(@PathVariable("msg") String message) {
		return integrationGateway.sendMessage(message);
		
	}

	/* Step 1/7 */
	@PostMapping("/product")
	public String ingestionProduct(@RequestBody Product product) {
		return integrationGateway.processProduct(product);
	}
	
	public IntegrationGateway getIntegrationGateway() {
		return integrationGateway;
	}

	public void setIntegrationGateway(IntegrationGateway integrationGateway) {
		this.integrationGateway = integrationGateway;
	}
	
}
