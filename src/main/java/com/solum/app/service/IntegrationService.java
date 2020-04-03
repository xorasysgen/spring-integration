package com.solum.app.service;

import java.util.Date;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.solum.app.dto.Product;

@Service
public class IntegrationService {

	@ServiceActivator(inputChannel = "message.gateway.channel.message")
	public void integrationMessagingService(Message<String> msg) {
		MessageChannel replyChannel = (MessageChannel) msg.getHeaders().getReplyChannel();
		Message<String> mbMessage = MessageBuilder.withPayload("Hi " + msg.getPayload() + " " + new Date().toString())
				.build();
		replyChannel.send(mbMessage);
	}

	/* Step 5/7 */
	@ServiceActivator(inputChannel = "message.gateway.channel.product.ObjectToJson", outputChannel = "message.gateway.channel.product.JsonToObject")
	public Message<?> receivedMessage(Message<?> message) {
		System.out.println("::::::ObjectToJson:::::");
		System.out.println("full msg <<" + message);
		System.out.println("msg <<" + message.getPayload());
		return message;
	}
	
	/* Step 7/7 */
	@ServiceActivator(inputChannel = "message.gateway.channel.product.FromTransformer.returnedchannel")
	public void processedProduct(Message<?> message) {
		System.out.println("::::::JsonToObject:::::");
		MessageChannel replyChannel=(MessageChannel) message.getHeaders().getReplyChannel();
		System.out.println("returning >>" + message.getPayload());
		Product product=(Product) message.getPayload();
		Message<?> msg=MessageBuilder.withPayload(product.toString()).build();
		replyChannel.send(msg);
		
		
	}

}
