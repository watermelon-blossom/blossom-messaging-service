package com.watermelon.message.infra;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.google.common.collect.ImmutableMap;
import com.watermelon.message.dto.chat.ChatResponse;

@EnableKafka
@Configuration
public class KafkaProducerConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

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
			.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringDeserializer.class)
			.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class)
			.build();
	}

	// KafkaTemplate을 생성하는 Bean 메서드
	@Bean
	public KafkaTemplate<String, ChatResponse> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}