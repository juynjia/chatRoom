
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.chatroom.MyWebSocketHandler;

@Configuration   // �аO�o�O�@�Ӱt�m��
@EnableWebSocket //�ҥ� WebSocket

/*
 * 1. ���U WebSocket �����I�]endpoint�^�G/websocket-endpoint
 * 2. ���w�B�z���GMyWebSocketHandler
 * 3. �]�m���\���ӷ��]setAllowedOrigins("*")��ܤ��\�Ҧ��ӷ��^
 * 
 * 
 * 
 * �u���H�s�� /websocket-endpoint �o�Ӻ��}�ɡA�Х� MyWebSocketHandler �ӳB�z�v
 */


public class WebSocketConfig implements WebSocketConfigurer {
    /**
     * ���UWebSocket ���I�M�B�z��
     * 
     * @param registry WebSocketHandlerRegistry ���Ѻ��I���U��k 
     */

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
                            // ���w�B�z��(�t�d�B�zWebSocket�ƥ�)  // ���|:�w�q�Ȥ�ݳs�u�� URL�C    
        registry.addHandler(new MyWebSocketHandler(), "/WebSocket-endpoint").setAllowedOrigins("*");

        //registry.addHandler(new NotificationHandler(), "/notification-endpoint")
        

    }



}

