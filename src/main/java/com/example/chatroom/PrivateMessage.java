package com.example.chatroom;

public class PrivateMessage {
    private String recipientId;  // ������ID
    private String content;      // �������e
    
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
