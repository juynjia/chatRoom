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
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {  // Spring ���Ѿާ@ Redis ���u��
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // �]�m�ǦC�ƾ�
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class); //�� Java �����ഫ�� JSON �榡 �s�i Redis�A�αq Redis Ū�� JSON �ഫ�^ Java ����C
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        
        return template; //��]�w������ RedisTemplate �^�ǡA�� Spring �޲z�æb���ε{�����ϥΡC
    }

    /* 
    Redis �T����ť�e���A�����\ Spring Boot ��ť Redis �� Pub/Sub �T���C
    �D�n�\��G
            �s�� Redis�A��ť���w���W�D�]channel�^�C
            �� Redis �o���T���ɡA�|�۰ʳq���q�\�̡C
    */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat-room")); // �� WebSocket ���A���q�\ Redis�� chat-room �W�D
        return container;
    }


    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber);
    }
   


}

/*
 * 
 * ������ݭn�ǦC�ơH
 * ��ڭ̦s�x�ƾڨ�Redis�ɡG
 * Java��H �� �ǦC�� �� �G�i��ƾ� �� �s�JRedis
 * �qRedisŪ�� �� �G�i��ƾ� �� �ϧǦC�� �� Java��H
 */
