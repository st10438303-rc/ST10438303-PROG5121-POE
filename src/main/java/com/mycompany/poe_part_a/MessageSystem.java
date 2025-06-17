package com.mycompany.poe_part_a;

/**
 *
 * @author RC_Student_lab
 */
import java.util.Random;
import java.util.regex.Pattern;

public class MessageSystem {
    private static int messagesSent = 0;
    private int messageId;
    private String recipient;
    private String sender;
    private String content;
    private String messageHash;
    private String flag; // "Sent", "Stored", or "Disregarded"
    
    public MessageSystem(String sender, String recipient, String content, String flag) {
        this.messageId = generateMessageId();
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.flag = flag;
        this.messageHash = createMessageHash();
        messagesSent++;
    }

    public String validateMessageLength() {
        if (content.length() > 250) {
            int excess = content.length() - 250;
            return String.format("Message exceeds 250 characters by %d characters.", excess);
        }
        return "Valid";
    }
    
    public static String validateRecipientFormat(String recipient) {
        if (recipient == null || !Pattern.matches("^\\+\\d{1,10}$", recipient)) {
            return "Invalid format. Use + followed by up to 10 digits (e.g., +1234567890).";
        }
        return "Valid";
    }
    
    private int generateMessageId() {
        return new Random().nextInt(9000) + 1000; // 4-digit number
    }
    
    private String createMessageHash() {
        String[] words = content.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";
        String[] colors = {"RED", "BLU", "GRN", "YLW"};
        String color = colors[new Random().nextInt(colors.length)];
        
        return String.format("%04d:%s:%d:%s_%s", 
            messageId, color, content.length(), firstWord.toUpperCase(), lastWord.toUpperCase());
    }
    
    public String getMessageDetails() {
        return String.format(
            "ID: %04d | From: %s | To: %s | Status: %s\nContent: %s\nHash: %s\n",
            messageId, sender, recipient, flag, content, messageHash
        );
    }
    
    public static int getTotalMessagesSent() {
        return messagesSent;
    }
    
    // Getters
    public int getMessageId() { return messageId; }
    public String getRecipient() { return recipient; }
    public String getSender() { return sender; }
    public String getContent() { return content; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }
    public int getMessageLength() { return content.length(); }

    boolean getShortDetails() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}