package com.watermelon.chat.infra;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.google.common.collect.ImmutableMap;
import com.watermelon.chat.dto.chat.ChatResponse;

@EnableKafka
@Configuration
public class KafkaProducerConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Value("${spring.kafka.properties.sasl.mechanism}")
	private String saslMechanism;

	@Value("${spring.kafka.properties.sasl.jaas.config}")
	private String saslJaasConfig;

	@Value("${spring.kafka.properties.security.protocol}")
	private String securityProtocol;

	@Value("${spring.kafka.properties.session.timeout.ms}")
	private String sessionTimeOut;

	// Kafka ProducerFactory를 생성하는 Bean 메서드
	@Bean
	public ProducerFactory<String, ChatResponse> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigurations());
	}

	// Kafka Producer 구성을 위한 설정값들을 포함한 맵을 반환하는 메서드
	@Bean
	public Map<String, Object> producerConfigurations() {
		return ImmutableMap.<String, Object>builder()
			.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
			.put("group.id", groupId)
			.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
			.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
			.put("sasl.mechanism", saslMechanism)
			.put("sasl.jaas.config", saslJaasConfig)
			.put("security.protocol", securityProtocol)
			.put("session.timeout.ms", sessionTimeOut)
			.build();
	}

	// KafkaTemplate을 생성하는 Bean 메서드
	@Bean
	public KafkaTemplate<String, ChatResponse> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}