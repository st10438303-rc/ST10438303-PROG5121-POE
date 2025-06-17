/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_part_a;

/**
 *
 * @author RC_Student_lab
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all array storage operations for messages
 */
public class MessageArrayStorage {
    // Main arrays for message storage
    private final List<MessageSystem> sentMessages = new ArrayList<>();
    private final List<MessageSystem> storedMessages = new ArrayList<>();
    private final List<MessageSystem> disregardedMessages = new ArrayList<>();
    
    // Supporting arrays
    private final List<String> messageHashes = new ArrayList<>();
    private final List<Integer> messageIds = new ArrayList<>();
    
    // Stores a message in the appropriate array based on its flag
    
    public void addMessage(MessageSystem message) {
        switch (message.getFlag()) {
            case "Sent":
                sentMessages.add(message);
                break;
            case "Stored":
                storedMessages.add(message);
                break;
            case "Disregarded":
                disregardedMessages.add(message);
                break;
        }
        
        // Add to supporting arrays
        messageHashes.add(message.getMessageHash());
        messageIds.add(message.getMessageId());
    }
    
    
    // Finds a message by its ID
   
    public MessageSystem getMessageById(int id) {
        // Check all categories
        for (MessageSystem msg : getAllMessages()) {
            if (msg.getMessageId() == id) {
                return msg;
            }
        }
        return null;
    }
    
    // Gets all messages for a specific recipient
    
    public List<MessageSystem> getMessagesByRecipient(String recipient) {
        List<MessageSystem> result = new ArrayList<>();
        for (MessageSystem msg : getAllMessages()) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                result.add(msg);
            }
        }
        return result;
    }
    
    //Deletes a message using its hash
  
    public boolean deleteMessageByHash(String hash) {
        MessageSystem toRemove = null;
        
        // Find the message
        for (MessageSystem msg : getAllMessages()) {
            if (msg.getMessageHash().equals(hash)) {
                toRemove = msg;
                break;
            }
        }
        
        if (toRemove != null) {
            // Remove from all arrays
            sentMessages.remove(toRemove);
            storedMessages.remove(toRemove);
            disregardedMessages.remove(toRemove);
            messageHashes.remove(toRemove.getMessageHash());
            messageIds.remove(Integer.valueOf(toRemove.getMessageId()));
            return true;
        }
        return false;
    }
    
    //Gets the longest message in storage
    
    public MessageSystem getLongestMessage() {
        if (getAllMessages().isEmpty()) return null;
        
        MessageSystem longest = getAllMessages().get(0);
        for (MessageSystem msg : getAllMessages()) {
            if (msg.getMessageLength() > longest.getMessageLength()) {
                longest = msg;
            }
        }
        return longest;
    }
    
    // Getter methods for all arrays
    public List<MessageSystem> getAllMessages() {
        List<MessageSystem> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);
        all.addAll(disregardedMessages);
        return all;
    }
    
    public List<MessageSystem> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }
    
    public List<MessageSystem> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }
    
    public List<MessageSystem> getDisregardedMessages() {
        return new ArrayList<>(disregardedMessages);
    }
    
    public List<String> getMessageHashes() {
        return new ArrayList<>(messageHashes);
    }
    
    public List<Integer> getMessageIds() {
        return new ArrayList<>(messageIds);
    }
    
    //Clears all storage arrays
  
    public void clearAll() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashes.clear();
        messageIds.clear();
    }
}