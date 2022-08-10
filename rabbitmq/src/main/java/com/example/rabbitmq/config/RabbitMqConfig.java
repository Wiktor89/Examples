package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String queue;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Bean
    public Queue queueFanout() {
        return new Queue(queue.concat("-fanout"));
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchange.concat("-fanout"));
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueFanout())
                .to(fanoutExchange());
    }

    @Bean
    public Queue queueDirect() {
        return new Queue(queue.concat("-direct"));
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange.concat("-direct"));
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(queueDirect())
                .to(directExchange())
                .withQueueName();
    }

    @Bean
    public Queue queueTopic() {
        return new Queue(queue.concat("-topic"));
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange.concat("-topic"));
    }

    @Bean
    public Binding bindingTopic() {
        return BindingBuilder.bind(queueTopic())
                .to(topicExchange())
                .with("*.top.*");
    }

    @Bean
    public Queue queueHeaderOne() {
        return new Queue(queue.concat("-header-one"));
    }

    @Bean
    public Queue queueHeaderTwo() {
        return new Queue(queue.concat("-header-two"));
    }

    @Bean
    public Queue queueHeaderThree() {
        return new Queue(queue.concat("-header-three"));
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(exchange.concat("-header"));
    }

    @Bean
    public Binding bindingHeaderOne() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-match", "all");
        headers.put("h1", "Header1");
        headers.put("h2", "Header2");
        return BindingBuilder.bind(queueHeaderOne())
                .to(headersExchange())
                .whereAll(headers)
                .match();
    }

    @Bean
    public Binding bindingHeaderTwo() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-match", "any");
        headers.put("h1", "Header1");
        return BindingBuilder.bind(queueHeaderTwo())
                .to(headersExchange())
                .whereAny(headers)
                .match();
    }

    @Bean
    public Binding bindingHeaderThree() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-match", "any");
        headers.put("h2", "Header2");
        return BindingBuilder.bind(queueHeaderThree())
                .to(headersExchange())
                .whereAny(headers)
                .match();
    }
}
