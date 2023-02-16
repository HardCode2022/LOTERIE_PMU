package com.course.pmu.configs;

import com.course.pmu.entity.Course;
import com.course.pmu.repository.CourseRepository;
import com.course.pmu.repository.CourseServiceInterface;
import com.course.pmu.service.Impl.CourseServiceImpl;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration kfka pour la gestion de l'envoi des messages vers le Bus
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.template.default-topic}")
    private String courseTopic;

    @Bean
    public AdminClient adminClient() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return AdminClient.create(properties);
    }

    @Bean
    public NewTopic courseTopic() {
        return new NewTopic(courseTopic, 1, (short) 1);
    }

    @Bean
    public void createTopics() {
        NewTopic topic = courseTopic();
        AdminClient adminClient = adminClient();
        adminClient.createTopics(Collections.singleton(topic));
        adminClient.close();
    }

    @Bean
    public KafkaTemplate<String, Course> kafkaTemplate() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configs));
    }

    @Bean
    public CourseServiceInterface courseServiceInterface() {
        return  new CourseServiceImpl();
    }
}
