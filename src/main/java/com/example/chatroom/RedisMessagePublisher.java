package com.example.chatroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String message){
        redisTemplate.convertAndSend("chat-room", message);
    }
    
}
