package com.example.chatroom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.listener.PatternTopic;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {  // Spring 提供操作 Redis 的工具
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // 設置序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class); //把 Java 物件轉換成 JSON 格式 存進 Redis，或從 Redis 讀取 JSON 轉換回 Java 物件。
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        
        return template; //把設定完成的 RedisTemplate 回傳，讓 Spring 管理並在應用程式中使用。
    }

    /* 
    Redis 訊息監聽容器，它允許 Spring Boot 監聽 Redis 的 Pub/Sub 訊息。
    主要功能：
            連接 Redis，監聽指定的頻道（channel）。
            當 Redis 發布訊息時，會自動通知訂閱者。
    */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat-room")); // 讓 WebSocket 伺服器訂閱 Redis的 chat-room 頻道
        return container;
    }


    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber);
    }
   


}

/*
 * 
 * 為什麼需要序列化？
 * 當我們存儲數據到Redis時：
 * Java對象 → 序列化 → 二進制數據 → 存入Redis
 * 從Redis讀取 → 二進制數據 → 反序列化 → Java對象
 */
