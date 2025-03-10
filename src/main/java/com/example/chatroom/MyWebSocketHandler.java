package com.example.chatroom;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.ChatService;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatService chatService; // �`�J Redis ���A��

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;
    
    //�x�s�Ҧ��s����session
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // ���s���s���إ߮ɡA�|�I�s�o�Ӥ�k
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);  // �x�s�s���s��
        System.out.println("�s�s���إ�:"+ session.getId());

        session.sendMessage(new TextMessage("�w��ϥβ�ѫ�"));

        // ��s�Τ�s���ɡA�o�e��Ѿ��v
        List<String> history = chatService.getChatHistory();
        for(String msg : history){
            session.sendMessage(new TextMessage(msg));
        }
        
    }

    // ��������ɡA�|�I�s�o�Ӥ�k   "�ڭ�~���� �A�̮ȵ{������F"
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception{
        String msg = message.getPayload(); 
        System.out.println("�������:"+ msg);
        if ("ping".equals(msg)) {
            session.sendMessage(new TextMessage("pong"));
        }else{
            chatService.saveMessage(msg); // �O�s�T���� Redis
            /* 
            // �s���T�����Ҧ��s�����Ȥ��
            for(WebSocketSession s : sessions){
                s.sendMessage(new TextMessage(msg));
            }
            */
            redisMessagePublisher.publish(msg); // �o�e�T���� Redis
        }
        

    }

    // ��s�������ɡA�|�I�s�o�Ӥ�k
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status){
        sessions.remove(session); // �����������s��
        System.out.println("�s���w����:"+ session.getId());
    }

    
    public static void broadcastMessage(String message){
        // �o�̱��e�T�����Ҧ� WebSocket �s�u���Τ�
        for(WebSocketSession s : sessions){
            try{
                s.sendMessage(new TextMessage(message));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


}
