package com.example.chatroom;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {
    
    // �� Redis �W�D����s�����ɷ|�۰�Ĳ�o //Redis �q�\��(WebSocket Server)���� Redis �W�D�������� �۰ʩI�s
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String chatMessage = message.toString();
        System.out.println("WebSocket Server ���쪺����:"+ chatMessage);
        MyWebSocketHandler.broadcastMessage(chatMessage); // WebSocket Server��T�����e���Ҧ��s�����Τ�

    }
}
