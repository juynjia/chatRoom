package com.example.chatroom;

public class PrivateMessage {
    private String recipientId;  // 接收者ID
    private String content;      // 消息內容
    
    // getters and setters
    public String getRecipientId() {
        return recipientId;
    }
    
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
