package com.yuri.ubots_test.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    private final String cardsQueueName = "cards-requests";
    private final String loansQueueName = "loans-requests";
    private final String othersQueueName = "others-requests";
    private final String exchangeName = "requests-ex";
    private final String cardsTopicName = "cards";
    private final String loansTopicName = "loans";
    private final String othersTopicName = "others";

    @Bean
    public Queue cardsQueue() {
        return new Queue(cardsQueueName, true);
    }

    @Bean
    public Queue loansQueue() {
        return new Queue(loansQueueName, true);
    }

    @Bean
    public Queue othersQueue() {
        return new Queue(othersQueueName, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Declarables bindings(@Qualifier("cardsQueue") Queue cardsQueue,
                                @Qualifier("loansQueue") Queue loansQueue,
                                @Qualifier("othersQueue") Queue othersQueue,
                                TopicExchange topicExchange) {

        return new Declarables(
                BindingBuilder.bind(cardsQueue).to(topicExchange).with(cardsTopicName),
                BindingBuilder.bind(loansQueue).to(topicExchange).with(loansTopicName),
                BindingBuilder.bind(othersQueue).to(topicExchange).with(othersTopicName));
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory(
            ConnectionFactory connectionFactory,
            ObjectConverter objectConverter
    ) {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(objectConverter);
        return factory;
    }
}
