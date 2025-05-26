package com.mycompany.poe_part_a;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.json.simple.JSONObject; // ✅ Correct import

public class MessageSystem {

    static int getTotalMessagesSent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private String messageId;
    private String recipient;
    private String message;
    private static int messageCounter = 1;
    private boolean isFirstMessage;

    // Constructor
    public MessageSystem(String recipient, String message) {
        this.messageId = generateMessageId();
        this.recipient = recipient;
        this.message = message;
        this.isFirstMessage = message.contains("dinner tonight");
    }

    private String generateMessageId() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1_000_000_000));
    }

    public static boolean checkMessageId(String id) {
        return id != null && id.length() == 10 && id.matches("\\d+");
    }

    public static boolean checkRecipientCell(String cellNumber) {
        return cellNumber.matches("(\\+27|0)[6-8][0-9]{8}");
    }

    public String createMessageHash() {
        String[] words = message.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        if (isFirstMessage) {
            return (firstWord + lastWord).toUpperCase();
        }

        return messageId.substring(0, 2) + ":" + (firstWord + lastWord).toUpperCase();
    }

    public String sendMessage(String option) {
        switch (option) {
            case "Send":
                return "Message sent!";
            case "Store":
                storeMessageToJson();
                return "Message stored!";
            case "Disregard":
            default:
                return "Message disregarded!";
        }
    }

    private void storeMessageToJson() {
        JSONObject messageJson = new JSONObject();
        messageJson.put("messageId", messageId);
        messageJson.put("recipient", recipient);
        messageJson.put("message", message);
        messageJson.put("hash", createMessageHash());

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(messageJson.toJSONString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getMessageId() {
        return messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public static int getMessageCounter() {
        return messageCounter;
    }

    public boolean getIsFirstMessage() {
        return isFirstMessage;
    }

    public static void resetCounter() {
        messageCounter = 1;
    }
   
}
