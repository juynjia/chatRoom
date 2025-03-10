package com.example.chatroom;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {
    
    // 當 Redis 頻道收到新消息時會自動觸發 //Redis 訂閱者(WebSocket Server)收到 Redis 頻道的消息時 自動呼叫
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String chatMessage = message.toString();
        System.out.println("WebSocket Server 收到的消息:"+ chatMessage);
        MyWebSocketHandler.broadcastMessage(chatMessage); // WebSocket Server把訊息推送給所有連接的用戶

    }
}
