/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.poe_part_a;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MessageArrayStorageTest {
    private MessageArrayStorage storage;
    private MessageSystem msg1, msg2, msg3;
    
    @BeforeEach
    public void setUp() {
        storage = new MessageArrayStorage();
        msg1 = new MessageSystem("S1", "+27111111111", "Message 1", "Sent");
        msg2 = new MessageSystem("S2", "+27222222222", "Message 2", "Stored");
        msg3 = new MessageSystem("S3", "+27111111111", "Message 3", "Disregarded");
        
        storage.addMessage(msg1);
        storage.addMessage(msg2);
        storage.addMessage(msg3);
    }
    
    @Test
    @DisplayName("Test message categorization")
    public void testMessageCategorization() {
        assertEquals(1, storage.getSentMessages().size());
        assertEquals(1, storage.getStoredMessages().size());
        assertEquals(1, storage.getDisregardedMessages().size());
        assertEquals(3, storage.getAllMessages().size());
    }
    
    @Test
    @DisplayName("Test get message by ID")
    public void testGetMessageById() {
        assertEquals(msg1, storage.getMessageById(msg1.getMessageId()));
        assertEquals(msg2, storage.getMessageById(msg2.getMessageId()));
        assertNull(storage.getMessageById(9999)); // Non-existent ID
    }
    
    @Test
    @DisplayName("Test get messages by recipient")
    public void testGetMessagesByRecipient() {
        List<MessageSystem> messages = storage.getMessagesByRecipient("+27111111111");
        assertEquals(2, messages.size());
        assertTrue(messages.contains(msg1));
        assertTrue(messages.contains(msg3));
    }
    
    @Test
    @DisplayName("Test delete message by hash")
    public void testDeleteMessageByHash() {
        assertTrue(storage.deleteMessageByHash(msg2.getMessageHash()));
        assertEquals(2, storage.getAllMessages().size());
        assertFalse(storage.getStoredMessages().contains(msg2));
    }
    
    @Test
    @DisplayName("Test get longest message")
    public void testGetLongestMessage() {
        MessageSystem longMsg = new MessageSystem("S4", "+27444444444", 
            "This is a much longer message content", "Sent");
        storage.addMessage(longMsg);
        
        assertEquals(longMsg, storage.getLongestMessage());
    }
    
    @Test
    @DisplayName("Test clear all storage")
    public void testClearAll() {
        storage.clearAll();
        assertTrue(storage.getAllMessages().isEmpty());
        assertTrue(storage.getSentMessages().isEmpty());
        assertTrue(storage.getMessageHashes().isEmpty());
    }
}