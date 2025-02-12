package com.example.chatroom;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // �аO�o�O�@�Ӱt�m��
@EnableWebSocketMessageBroker // �ҥ�STOMP��ĳ�ö}�Ү����N�z
public class WebSocketConfig2 implements WebSocketMessageBrokerConfigurer{
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // �]�m�����N�z�A�o�̧ڭ̳]�m�F��ӫe��
        // �Ω�B�z�Ȥ�ݵo�e�������e��
        config.setApplicationDestinationPrefixes("/app");
        // �Ω�B�z�����N�z�������e��
        config.enableSimpleBroker("/topic", "/queue"); //�o�Ǯ����|�Q�o�e�� /topic �� /queue ���|
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // ���USTOMP���I�A�Ȥ�ݷ|�s����o�Ӻ��I
        registry.addEndpoint("/websocket-endpoint") // �]�w WebSocket ���I
                .setAllowedOrigins("*") // �]�m���ШD
                .withSockJS(); //�ҥ�SockJS����A���s���������WebSocket�ɡA�i�H���ŨϥΨ�L��ĳ
    }

}

/*
 * 
 *    /topic�G�Ω�s�������A�@�Ӯ����i�H�o�e���h�ӭq�\��
 *    /queue�G�Ω��I���I�����A�@�Ӯ����u��o�e���@�ӭq�\��
 * 
 * 
 */