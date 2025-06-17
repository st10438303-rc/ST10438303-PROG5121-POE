/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.poe_part_a;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MessageStorageTest {
    private MessageSystem msg1, msg2, msg3;

    @BeforeEach
    public void setUp() {
        // Clear storage before each test
        MessageStorage.clearAllMessages();
        
        // Create test messages
        msg1 = new MessageSystem("Alice", "+27111111111", "Hello there", "Sent");
        msg2 = new MessageSystem("Bob", "+27222222222", "Meeting at 3pm", "Stored");
        msg3 = new MessageSystem("Charlie", "+27333333333", "Please call me", "Disregarded");
        
        // Add to storage
        MessageStorage.addMessage(msg1);
        MessageStorage.addMessage(msg2);
        MessageStorage.addMessage(msg3);
    }

    @AfterEach
    public void tearDown() {
        MessageStorage.clearAllMessages();
    }

    @Test
    @DisplayName("Test adding and retrieving messages")
    public void testAddAndRetrieveMessages() {
        List<MessageSystem> allMessages = MessageStorage.getAllMessages();
        assertEquals(3, allMessages.size());
        assertTrue(allMessages.contains(msg1));
        assertTrue(allMessages.contains(msg2));
        assertTrue(allMessages.contains(msg3));
    }

    @Test
    @DisplayName("Test message categorization")
    public void testMessageCategorization() {
        assertEquals(1, MessageStorage.getSentMessages().size());
        assertEquals(1, MessageStorage.getStoredMessages().size());
        assertEquals(1, MessageStorage.getDisregardedMessages().size());
        
        assertTrue(MessageStorage.getSentMessages().contains(msg1));
        assertTrue(MessageStorage.getStoredMessages().contains(msg2));
        assertTrue(MessageStorage.getDisregardedMessages().contains(msg3));
    }

    @Test
    @DisplayName("Test get message by ID")
    public void testGetMessageById() {
        MessageSystem found = MessageStorage.getMessageById(msg2.getMessageId());
        assertNotNull(found);
        assertEquals(msg2.getMessageId(), found.getMessageId());
        assertEquals("Bob", found.getSender());
        assertEquals("Meeting at 3pm", found.getContent());
        
        assertNull(MessageStorage.getMessageById(9999)); // Non-existent ID
    }

    @Test
    @DisplayName("Test get messages by recipient")
    public void testGetMessagesByRecipient() {
        // Add another message to same recipient
        MessageSystem msg4 = new MessageSystem("Alice", "+27111111111", "Second message", "Sent");
        MessageStorage.addMessage(msg4);
        
        List<MessageSystem> messages = MessageStorage.getMessagesByRecipient("+27111111111");
        assertEquals(2, messages.size());
        assertTrue(messages.contains(msg1));
        assertTrue(messages.contains(msg4));
        
        // Test non-existent recipient
        assertTrue(MessageStorage.getMessagesByRecipient("+27999999999").isEmpty());
    }

    @Test
    @DisplayName("Test delete message by hash")
    public void testDeleteMessageByHash() {
        String hashToDelete = msg3.getMessageHash();
        assertTrue(MessageStorage.deleteMessageByHash(hashToDelete));
        
        assertEquals(2, MessageStorage.getAllMessages().size());
        assertFalse(MessageStorage.getAllMessages().contains(msg3));
        assertFalse(MessageStorage.getDisregardedMessages().contains(msg3));
        
        // Verify supporting arrays were updated
        assertFalse(MessageStorage.getMessageHashes().contains(hashToDelete));
        assertFalse(MessageStorage.getMessageIds().contains(msg3.getMessageId()));
        
        // Test deleting non-existent hash
        assertFalse(MessageStorage.deleteMessageByHash("invalid_hash"));
    }

    @Test
    @DisplayName("Test get longest message")
    public void testGetLongestMessage() {
        // Current longest is msg2 ("Meeting at 3pm" - 12 chars)
        assertEquals(msg2, MessageStorage.getLongestMessage());
        
        // Add longer message
        MessageSystem longMsg = new MessageSystem("Dave", "+27444444444", 
            "This is a much longer message that should be identified", "Sent");
        MessageStorage.addMessage(longMsg);
        
        assertEquals(longMsg, MessageStorage.getLongestMessage());
    }

    @Test
    @DisplayName("Test clear all messages")
    public void testClearAllMessages() {
        MessageStorage.clearAllMessages();
        
        assertTrue(MessageStorage.getAllMessages().isEmpty());
        assertTrue(MessageStorage.getSentMessages().isEmpty());
        assertTrue(MessageStorage.getStoredMessages().isEmpty());
        assertTrue(MessageStorage.getDisregardedMessages().isEmpty());
        assertTrue(MessageStorage.getMessageHashes().isEmpty());
        assertTrue(MessageStorage.getMessageIds().isEmpty());
    }

    @Test
    @DisplayName("Test supporting arrays")
    public void testSupportingArrays() {
        List<String> hashes = MessageStorage.getMessageHashes();
        List<Integer> ids = MessageStorage.getMessageIds();
        
        assertEquals(3, hashes.size());
        assertEquals(3, ids.size());
        
        assertTrue(hashes.contains(msg1.getMessageHash()));
        assertTrue(hashes.contains(msg2.getMessageHash()));
        assertTrue(ids.contains(msg1.getMessageId()));
        assertTrue(ids.contains(msg2.getMessageId()));
    }

    @Test
    @DisplayName("Test message count after operations")
    public void testMessageCount() {
        // Initial count
        assertEquals(3, MessageStorage.getAllMessages().size());
        
        // Add new message
        MessageSystem msg4 = new MessageSystem("Eve", "+27555555555", "New message", "Stored");
        MessageStorage.addMessage(msg4);
        assertEquals(4, MessageStorage.getAllMessages().size());
        assertEquals(2, MessageStorage.getStoredMessages().size());
        
        // Delete message
        MessageStorage.deleteMessageByHash(msg1.getMessageHash());
        assertEquals(3, MessageStorage.getAllMessages().size());
        assertEquals(0, MessageStorage.getSentMessages().size());
    }
}