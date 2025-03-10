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
    private ChatService chatService; // 注入 Redis 的服務

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;
    
    //儲存所有連接的session
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // 當有新的連接建立時，會呼叫這個方法
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);  // 儲存新的連接
        System.out.println("新連接建立:"+ session.getId());

        session.sendMessage(new TextMessage("歡迎使用聊天室"));

        // 當新用戶連接時，發送聊天歷史
        List<String> history = chatService.getChatHistory();
        for(String msg : history){
            session.sendMessage(new TextMessage(msg));
        }
        
    }

    // 當收到消息時，會呼叫這個方法   "我剛洗完澡 你們旅程聊到哪邊了"
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception{
        String msg = message.getPayload(); 
        System.out.println("收到消息:"+ msg);
        if ("ping".equals(msg)) {
            session.sendMessage(new TextMessage("pong"));
        }else{
            chatService.saveMessage(msg); // 保存訊息到 Redis
            /* 
            // 廣播訊息給所有連接的客戶端
            for(WebSocketSession s : sessions){
                s.sendMessage(new TextMessage(msg));
            }
            */
            redisMessagePublisher.publish(msg); // 發送訊息到 Redis
        }
        

    }

    // 當連接關閉時，會呼叫這個方法
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status){
        sessions.remove(session); // 移除關閉的連接
        System.out.println("連接已關閉:"+ session.getId());
    }

    
    public static void broadcastMessage(String message){
        // 這裡推送訊息給所有 WebSocket 連線的用戶
        for(WebSocketSession s : sessions){
            try{
                s.sendMessage(new TextMessage(message));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


}
