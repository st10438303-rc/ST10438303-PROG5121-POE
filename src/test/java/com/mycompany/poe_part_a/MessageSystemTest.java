package com.mycompany.poe_part_a;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MessageSystemTest {
    private MessageSystem message;
    private final String TEST_SENDER = "TestSender";
    private final String TEST_RECIPIENT = "+27123456789";
    private final String TEST_CONTENT = "This is a test message";

    @BeforeEach
    public void setUp() {
        message = new MessageSystem(TEST_SENDER, TEST_RECIPIENT, TEST_CONTENT, "Sent");
    }

    @Test
    @DisplayName("Test message creation with valid parameters")
    public void testMessageCreation() {
        assertNotNull(message);
        assertEquals(TEST_SENDER, message.getSender());
        assertEquals(TEST_RECIPIENT, message.getRecipient());
        assertEquals(TEST_CONTENT, message.getContent());
        assertEquals("Sent", message.getFlag());
        assertTrue(message.getMessageId() >= 1000 && message.getMessageId() <= 9999);
        assertNotNull(message.getMessageHash());
    }

    @Test
    @DisplayName("Test message length validation")
    public void testValidateMessageLength() {
        // Test valid length
        assertEquals("Valid", message.validateMessageLength());

        // Test invalid length
        String longContent = "a".repeat(251);
        MessageSystem longMessage = new MessageSystem(TEST_SENDER, TEST_RECIPIENT, longContent, "Sent");
        assertTrue(longMessage.validateMessageLength().contains("exceeds"));
    }

    @Test
    @DisplayName("Test recipient validation")
    public void testValidateRecipientFormat() {
        // Test valid formats
        assertEquals("Valid", MessageSystem.validateRecipientFormat("+2712345678"));
        assertEquals("Valid", MessageSystem.validateRecipientFormat("+1234567890"));

        // Test invalid formats
        assertNotEquals("Valid", MessageSystem.validateRecipientFormat("2712345678")); // Missing +
        assertNotEquals("Valid", MessageSystem.validateRecipientFormat("+abcdefghij")); // Non-digits
        assertNotEquals("Valid", MessageSystem.validateRecipientFormat("+12345678901")); // Too long
        assertNotEquals("Valid", MessageSystem.validateRecipientFormat(null)); // Null
    }

    @Test
    @DisplayName("Test message hash generation")
    public void testCreateMessageHash() {
        String hash = message.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.startsWith(String.format("%04d", message.getMessageId())));
        assertTrue(hash.contains(":"));
        assertTrue(hash.split(":").length == 4);
    }

    @Test
    @DisplayName("Test message details formatting")
    public void testGetMessageDetails() {
        String details = message.getMessageDetails();
        assertNotNull(details);
        assertTrue(details.contains("ID: " + message.getMessageId()));
        assertTrue(details.contains("From: " + TEST_SENDER));
        assertTrue(details.contains("To: " + TEST_RECIPIENT));
        assertTrue(details.contains("Content: " + TEST_CONTENT));
        assertTrue(details.contains("Hash: " + message.getMessageHash()));
    }

    @Test
    @DisplayName("Test total messages sent counter")
    public void testGetTotalMessagesSent() {
        int initialCount = MessageSystem.getTotalMessagesSent();
        new MessageSystem("S1", "+27111111111", "Test1", "Sent");
        new MessageSystem("S2", "+27222222222", "Test2", "Stored");
        assertEquals(initialCount + 2, MessageSystem.getTotalMessagesSent());
    }
}