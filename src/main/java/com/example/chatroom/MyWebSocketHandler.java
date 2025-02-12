package com.example.chatroom;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
    
    //�x�s�Ҧ��s����session
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // ���s���s���إ߮ɡA�|�I�s�o�Ӥ�k
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);  // �x�s�s���s��
        System.out.println("�s�s���إ�:"+ session.getId());
        session.sendMessage(new TextMessage("�w��ϥβ�ѫ�"));
    }

    // ��������ɡA�|�I�s�o�Ӥ�k
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception{
        String payload = message.getPayload(); //����T�����e
        System.out.println("�������:"+ payload);
        // �s���T�����Ҧ��s�����Ȥ��
        for(WebSocketSession s : sessions){
            s.sendMessage(new TextMessage(payload));
        }

    }

    // ��s�������ɡA�|�I�s�o�Ӥ�k
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status){
        sessions.remove(session); // �����������s��
        System.out.println("�s���w����:"+ session.getId());



}
