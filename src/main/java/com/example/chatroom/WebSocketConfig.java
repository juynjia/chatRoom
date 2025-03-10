
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.chatroom.MyWebSocketHandler;

@Configuration   // 標記這是一個配置類
@EnableWebSocket //啟用 WebSocket

/*
 * 1. 註冊 WebSocket 的端點（endpoint）：/websocket-endpoint
 * 2. 指定處理器：MyWebSocketHandler
 * 3. 設置允許的來源（setAllowedOrigins("*")表示允許所有來源）
 * 
 * 
 * 
 * 「當有人連到 /websocket-endpoint 這個網址時，請用 MyWebSocketHandler 來處理」
 */


public class WebSocketConfig implements WebSocketConfigurer {
    /**
     * 註冊WebSocket 端點和處理器
     * 
     * @param registry WebSocketHandlerRegistry 提供端點註冊方法 
     */

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
                            // 指定處理器(負責處理WebSocket事件)  // 路徑:定義客戶端連線的 URL。    
        registry.addHandler(new MyWebSocketHandler(), "/WebSocket-endpoint").setAllowedOrigins("*");

        //registry.addHandler(new NotificationHandler(), "/notification-endpoint")
        

    }



}

