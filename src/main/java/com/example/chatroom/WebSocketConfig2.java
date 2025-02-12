package com.example.chatroom;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // 標記這是一個配置類
@EnableWebSocketMessageBroker // 啟用STOMP協議並開啟消息代理
public class WebSocketConfig2 implements WebSocketMessageBrokerConfigurer{
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // 設置消息代理，這裡我們設置了兩個前綴
        // 用於處理客戶端發送的消息前綴
        config.setApplicationDestinationPrefixes("/app");
        // 用於處理消息代理的消息前綴
        config.enableSimpleBroker("/topic", "/queue"); //這些消息會被發送到 /topic 或 /queue 路徑
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 註冊STOMP端點，客戶端會連接到這個端點
        registry.addEndpoint("/websocket-endpoint") // 設定 WebSocket 端點
                .setAllowedOrigins("*") // 設置跨域請求
                .withSockJS(); //啟用SockJS支持，當瀏覽器不支持WebSocket時，可以降級使用其他協議
    }

}

/*
 * 
 *    /topic：用於廣播消息，一個消息可以發送給多個訂閱者
 *    /queue：用於點對點消息，一個消息只能發送給一個訂閱者
 * 
 * 
 */