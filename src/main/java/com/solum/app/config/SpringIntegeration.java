package com.solum.app.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.MessageChannel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solum.app.dto.Product;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class SpringIntegeration {

	@Bean
	public MessageChannel receiverChannal() {
		return new DirectChannel();

	}

	@Bean
	public MessageChannel replyChannel() {
		return new DirectChannel();
	}

	// Transformers
	/* Step 3/7 */
	@Bean
	@Transformer(inputChannel = "message.gateway.channel.product", outputChannel = "message.gateway.channel.product.HeaderEnricher")
	public HeaderEnricher enrichHeader() {
		Map<String, HeaderValueMessageProcessor<String>> headersToAdd = new HashMap<>();
		headersToAdd.put("ownership", new StaticHeaderValueMessageProcessor<String>("SKBH"));
		headersToAdd.put("copyright-infringement-email-contact", new StaticHeaderValueMessageProcessor<String>("xorasysgen@yahoo.com"));
		HeaderEnricher enricher = new HeaderEnricher(headersToAdd);
		return enricher;
	}

	/* Step 4/7 */
	@Bean
	@Transformer(inputChannel = "message.gateway.channel.product.HeaderEnricher", outputChannel = "message.gateway.channel.product.ObjectToJson")
	public ObjectToJsonTransformer objectToJsonTransformer() {
		return new ObjectToJsonTransformer(getMapper());
	}

	@Bean
	public Jackson2JsonObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return new Jackson2JsonObjectMapper(mapper);
	}

	/* Step 6/7 */
	@Bean
	@Transformer(inputChannel = "message.gateway.channel.product.JsonToObject", outputChannel = "message.gateway.channel.product.FromTransformer.returnedchannel")
	JsonToObjectTransformer jsonToObjectTransformer() {
		return new JsonToObjectTransformer(Product.class);
	}

}
