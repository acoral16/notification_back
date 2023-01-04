package com.decoded.messaging.config;

import com.decoded.messaging.constant.EnumCategory;
import com.decoded.messaging.controllers.redis.RedisFinanceSubscriber;
import com.decoded.messaging.controllers.redis.RedisMovieSubscriber;

import com.decoded.messaging.controllers.redis.RedisSportSubscriber;
import com.decoded.messaging.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("classpath:application.properties")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Autowired
    private SubscriberService subscriberService;


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(this.hostName,
                this.port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(this.password));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(movieListenerAdapter(EnumCategory.Category.MOVIE.toString(), subscriberService),
                new ChannelTopic(EnumCategory.Category.MOVIE.toString()));
        container.addMessageListener(sportListenerAdapter(EnumCategory.Category.SPORT.toString(), subscriberService),
                new ChannelTopic(EnumCategory.Category.SPORT.toString()));
        container.addMessageListener(financeListenerAdapter(EnumCategory.Category.FINANCE.toString(), subscriberService),
                new ChannelTopic(EnumCategory.Category.FINANCE.toString()));

        return container;
    }

    @Bean
    public MessageListenerAdapter movieListenerAdapter(String s, SubscriberService subscriberService) {
        return new MessageListenerAdapter(new RedisMovieSubscriber(s, subscriberService), "onMessage");
    }

    @Bean
    public MessageListenerAdapter sportListenerAdapter(String s, SubscriberService subscriberService) {
        return new MessageListenerAdapter(new RedisSportSubscriber(s, subscriberService), "onMessage");
    }

    @Bean
    public MessageListenerAdapter financeListenerAdapter(String s, SubscriberService subscriberService) {
        return new MessageListenerAdapter(new RedisFinanceSubscriber(s, subscriberService), "onMessage");
    }


//    @Bean
//    RedisMovieSubscriber receiver() {
//		return new RedisMovieSubscriber();
//    }


//    @Bean
//    MessageListenerAdapter messageListener(RedisMovieSubscriber subscriber) {
//        return new MessageListenerAdapter(subscriber, "onMessage");
//    }







}