package com.watermelon.message.infra;

import java.util.Collection;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.google.common.collect.ImmutableMap;
import com.watermelon.message.dto.chat.ChatResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    // Kafka ConsumerFactory를 생성하는 Bean 메서드
    @Bean
    public ConsumerFactory<String, ChatResponse> consumerFactory() {
        JsonDeserializer<ChatResponse> deserializer = new JsonDeserializer<>();
        ImmutableMap<String, Object> properties = ImmutableMap.<String, Object>builder()
            .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
            .put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
            .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer)
            .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
            .build();

        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatResponse> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        ContainerProperties prop = factory.getContainerProperties();
        prop.setConsumerRebalanceListener(rebalanceListener());
        return factory;
    }

    @Bean
    public ConsumerAwareRebalanceListener rebalanceListener() {
        return new ConsumerAwareRebalanceListener() {
            @Override
            public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
                // here partitions
                for (TopicPartition partition : partitions) {
                    int partitionNumber = partition.partition();
                    log.info("사용중인파티션:{}", partitionNumber);
                }
            }
        };
    }

}