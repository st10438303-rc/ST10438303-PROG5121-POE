package com.mycompany.poe_part_a;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageSystemTest {

    private final String filePath = "messages.json";

    @AfterEach
    void cleanupFile() throws Exception {
        // Clean up the messages.json after each test that writes
        File file = new File(filePath);
        if (file.exists()) {
            Files.delete(Paths.get(filePath));
        }
    }

    @Test
    void testCheckMessageId_Valid() {
        assertTrue(MessageSystem.checkMessageId("1234567890"));
    }

    @Test
    void testCheckMessageId_Invalid() {
        assertFalse(MessageSystem.checkMessageId(null));
        assertFalse(MessageSystem.checkMessageId("12345"));
        assertFalse(MessageSystem.checkMessageId("abcdefghij"));
        assertFalse(MessageSystem.checkMessageId("12345678901"));
    }

    @Test
    void testCheckRecipientCell_Valid() {
        assertTrue(MessageSystem.checkRecipientCell("+27601234567"));
        assertTrue(MessageSystem.checkRecipientCell("0612345678"));
    }

    @Test
    void testCheckRecipientCell_Invalid() {
        assertFalse(MessageSystem.checkRecipientCell("+2712345678"));
        assertFalse(MessageSystem.checkRecipientCell("0212345678"));
        assertFalse(MessageSystem.checkRecipientCell("1234567890"));
        assertFalse(MessageSystem.checkRecipientCell(null));
    }

    @Test
    void testCreateMessageHash_FirstMessage() {
        MessageSystem msg = new MessageSystem("0612345678", "Let's have dinner tonight");
        String hash = msg.createMessageHash();
        assertEquals("LET'STONIGHT", hash);
    }

    @Test
    void testCreateMessageHash_NonFirstMessage() {
        MessageSystem msg = new MessageSystem("0612345678", "Hello world");
        String hash = msg.createMessageHash();
        String expectedStart = msg.getMessageId().substring(0, 2) + ":";
        assertTrue(hash.startsWith(expectedStart));
        assertTrue(hash.endsWith("HELLOWORLD"));
    }

    @Test
    void testSendMessage_Send() {
        MessageSystem msg = new MessageSystem("0612345678", "Hello");
        assertEquals("Message sent!", msg.sendMessage("Send"));
    }

    @Test
    void testSendMessage_Disregard() {
        MessageSystem msg = new MessageSystem("0612345678", "Hello");
        assertEquals("Message disregarded!", msg.sendMessage("Disregard"));
        assertEquals("Message disregarded!", msg.sendMessage("Other"));
    }

    @Test
    void testSendMessage_Store_CreatesFile() throws Exception {
        MessageSystem msg = new MessageSystem("0612345678", "Hello world");
        assertEquals("Message stored!", msg.sendMessage("Store"));

        File file = new File(filePath);
        assertTrue(file.exists());
        String content = Files.readString(Paths.get(filePath));
        assertTrue(content.contains("Hello world"));
        assertTrue(content.contains(msg.getMessageId()));
    }
}
