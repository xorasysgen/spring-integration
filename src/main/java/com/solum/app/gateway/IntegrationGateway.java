package com.solum.app.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.solum.app.dto.Product;

@MessagingGateway
public interface IntegrationGateway {

	@Gateway(requestChannel = "message.gateway.channel.message")
	public String sendMessage(String message);
	
	@Gateway(requestChannel = "message.gateway.channel.product")
	public String processProduct(Product product);
}
