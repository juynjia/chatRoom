package com.example.chatroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;  // 注入消息模板，用於發送消息

    // 1. 一般消息處理
    @MessageMapping("/chat")  // 實際路徑是 /app/chat   , 當客戶端發送消息到這個路徑時，以下這個方法會被調用
    @SendTo("/topic/messages")  // 處理完消息後，將結果發送到哪個目的地 //所有訂閱了 /topic/messages 的客戶端收到消息
    public String  handleMessage(String message) {
        return message;
    }


    // 2. 服務器廣播消息方法
    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public String broadcastMessage(@Payload String message) {
        // 發送到 /topic/broadcast，所有訂閱此主題的客戶端都會收到
        return "廣播消息: " + message;
    }


    // 3. 點對點私人消息方法
    @MessageMapping("/private-message") //完整路徑是 /app/private-message（因為在 WebSocketConfig 中設置了 /app 前綴）
    @SendToUser("/queue/private")  // 這裡使用 @SendToUser
    public String privateMessage(@Payload PrivateMessage message, @Header("simpSessionId") String sessionId) {
        // 可以在這裡添加額外的處理邏輯
        // 例如：消息過濾、日誌記錄、消息轉換等
        return "私人消息: " + message.getContent();
    }



}