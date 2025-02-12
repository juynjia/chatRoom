package com.example.chatroom;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
    
    //儲存所有連接的session
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // 當有新的連接建立時，會呼叫這個方法
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);  // 儲存新的連接
        System.out.println("新連接建立:"+ session.getId());
        session.sendMessage(new TextMessage("歡迎使用聊天室"));
    }

    // 當收到消息時，會呼叫這個方法
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception{
        String payload = message.getPayload(); //獲取訊息內容
        System.out.println("收到消息:"+ payload);
        // 廣播訊息給所有連接的客戶端
        for(WebSocketSession s : sessions){
            s.sendMessage(new TextMessage(payload));
        }

    }

    // 當連接關閉時，會呼叫這個方法
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status){
        sessions.remove(session); // 移除關閉的連接
        System.out.println("連接已關閉:"+ session.getId());



}
