/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_part_a;

import java.util.List;

/**
 *
 * @author RC_Student_lab
 */


/**
 * Facade class that provides the main interface to message storage
 */
public class MessageStorage {
    private static final MessageArrayStorage arrayStorage = new MessageArrayStorage();
    
    public static void addMessage(MessageSystem message) {
        arrayStorage.addMessage(message);
    }
    
    public static MessageSystem getMessageById(int id) {
        return arrayStorage.getMessageById(id);
    }
    
    public static List<MessageSystem> getMessagesByRecipient(String recipient) {
        return arrayStorage.getMessagesByRecipient(recipient);
    }
    
    public static boolean deleteMessageByHash(String hash) {
        return arrayStorage.deleteMessageByHash(hash);
    }
    
    public static MessageSystem getLongestMessage() {
        return arrayStorage.getLongestMessage();
    }
    
    // Delegate all getter methods to the array storage
    public static List<MessageSystem> getAllMessages() {
        return arrayStorage.getAllMessages();
    }
    
    public static List<MessageSystem> getSentMessages() {
        return arrayStorage.getSentMessages();
    }
    
    public static List<MessageSystem> getStoredMessages() {
        return arrayStorage.getStoredMessages();
    }
    
    public static List<MessageSystem> getDisregardedMessages() {
        return arrayStorage.getDisregardedMessages();
    }
    
    public static List<String> getMessageHashes() {
        return arrayStorage.getMessageHashes();
    }
    
    public static List<Integer> getMessageIds() {
        return arrayStorage.getMessageIds();
    }
    
    public static void clearAllMessages() {
        arrayStorage.clearAll();
    }
}