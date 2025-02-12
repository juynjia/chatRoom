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
    private SimpMessagingTemplate messagingTemplate;  // �`�J�����ҪO�A�Ω�o�e����

    // 1. �@������B�z
    @MessageMapping("/chat")  // ��ڸ��|�O /app/chat   , ��Ȥ�ݵo�e������o�Ӹ��|�ɡA�H�U�o�Ӥ�k�|�Q�ե�
    @SendTo("/topic/messages")  // �B�z��������A�N���G�o�e����ӥت��a //�Ҧ��q�\�F /topic/messages ���Ȥ�ݦ������
    public String  handleMessage(String message) {
        return message;
    }


    // 2. �A�Ⱦ��s��������k
    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public String broadcastMessage(@Payload String message) {
        // �o�e�� /topic/broadcast�A�Ҧ��q�\���D�D���Ȥ�ݳ��|����
        return "�s������: " + message;
    }


    // 3. �I���I�p�H������k
    @MessageMapping("/private-message") //������|�O /app/private-message�]�]���b WebSocketConfig ���]�m�F /app �e��^
    @SendToUser("/queue/private")  // �o�̨ϥ� @SendToUser
    public String privateMessage(@Payload PrivateMessage message, @Header("simpSessionId") String sessionId) {
        // �i�H�b�o�̲K�[�B�~���B�z�޿�
        // �Ҧp�G�����L�o�B��x�O���B�����ഫ��
        return "�p�H����: " + message.getContent();
    }



}