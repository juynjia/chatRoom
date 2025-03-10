package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CHAT_KEY = "chat_history";

    public void saveMessage(String message){
        redisTemplate.opsForList().rightPush(CHAT_KEY, message);

        if(redisTemplate.opsForList().size(CHAT_KEY) > 100){
            redisTemplate.opsForList().leftPop(CHAT_KEY); // 移除最左邊的元素 (最舊的訊息) 把舊訊息放入MySQL 
        }
    }

    // 取得聊天記錄
    public List<String> getChatHistory(){
        return redisTemplate.opsForList().range(CHAT_KEY, 0, -1);
    }
}
