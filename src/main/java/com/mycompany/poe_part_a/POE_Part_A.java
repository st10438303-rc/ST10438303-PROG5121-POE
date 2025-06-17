/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe_part_a;

/**
 *
 * @author RC_Student_lab
 */

import java.util.Scanner;
import java.util.List;

public class POE_Part_A {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        initializeTestData();
        System.out.println("=== Message Management System ===");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    sendNewMessage();
                    break;
                case 2:
                    viewAllMessages();
                    break;
                case 3:
                    searchMessageById();
                    break;
                case 4:
                    searchMessagesByRecipient();
                    break;
                case 5:
                    deleteMessageByHash();
                    break;
                case 6:
                    displayLongestMessage();
                    break;
                case 7:
                    generateFullReport();
                    break;
                case 8:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    
    private static void initializeTestData() {
        MessageStorage.clearAllMessages();
        MessageStorage.addMessage(new MessageSystem("System", "+27334557396", "Did you get the cake?", "Sent"));
        MessageStorage.addMessage(new MessageSystem("User1", "+27338884567", "Where are you? You are late! I have asked you to be on time.", "Stored"));
        MessageStorage.addMessage(new MessageSystem("User2", "+27334854567", "Orthodox, I am at your gate.", "Disregarded"));
        MessageStorage.addMessage(new MessageSystem("Developer", "0838884567", "It is dinner time!", "Sent"));
        MessageStorage.addMessage(new MessageSystem("User3", "+27338884567", "Oh, I am leaving without you.", "Stored"));
    }
    
    private static void displayMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Send a new message");
        System.out.println("2. View all messages");
        System.out.println("3. Search message by ID");
        System.out.println("4. Search messages by recipient");
        System.out.println("5. Delete a message by hash");
        System.out.println("6. Display longest message");
        System.out.println("7. Generate full report");
        System.out.println("8. Exit");
    }
    
    public static void sendNewMessage() {
        System.out.println("\n--- Send New Message ---");
        
        String sender = getStringInput("Your name: ");
        
        String recipient;
        do {
            recipient = getStringInput("Recipient's phone number (+ followed by digits): ");
            String validation = MessageSystem.validateRecipientFormat(recipient);
            if (!validation.equals("Valid")) {
                System.out.println(validation);
            }
        } while (!MessageSystem.validateRecipientFormat(recipient).equals("Valid"));
        
        String content;
        do {
            content = getStringInput("Message content (max 250 chars): ");
            MessageSystem tempMsg = new MessageSystem(sender, recipient, content, "Temporary");
            String lengthCheck = tempMsg.validateMessageLength();
            if (!lengthCheck.equals("Valid")) {
                System.out.println(lengthCheck);
                content = null;
            }
        } while (content == null);
        
        String flag;
        do {
            flag = getStringInput("Message flag (Sent/Stored/Disregarded): ");
            if (!flag.equals("Sent") && !flag.equals("Stored") && !flag.equals("Disregarded")) {
                System.out.println("Invalid flag. Must be Sent, Stored, or Disregarded.");
            }
        } while (!flag.equals("Sent") && !flag.equals("Stored") && !flag.equals("Disregarded"));
        
        MessageSystem message = new MessageSystem(sender, recipient, content, flag);
        MessageStorage.addMessage(message);
        
        System.out.println("\nMessage created successfully!");
        System.out.println(message.getMessageDetails());
    }
    
    private static void viewAllMessages() {
        List<MessageSystem> messages = MessageStorage.getAllMessages();
        if (messages.isEmpty()) {
            System.out.println("\nNo messages in the system.");
            return;
        }
        
        System.out.println("\n--- All Messages ---");
        for (MessageSystem msg : messages) {
            System.out.println(msg.getMessageDetails());
        }
    }
    
    private static void searchMessageById() {
        int id = getIntInput("Enter message ID to search: ");
        MessageSystem msg = MessageStorage.getMessageById(id);
        
        if (msg != null) {
            System.out.println("\n--- Message Found ---");
            System.out.println(msg.getMessageDetails());
        } else {
            System.out.println("Message with ID " + id + " not found.");
        }
    }
    
    private static void searchMessagesByRecipient() {
        String recipient = getStringInput("Enter recipient phone number to search: ");
        List<MessageSystem> messages = MessageStorage.getMessagesByRecipient(recipient);
        
        if (messages.isEmpty()) {
            System.out.println("\nNo messages found for recipient: " + recipient);
            return;
        }
        
        System.out.println("\n--- Messages for Recipient: " + recipient + " ---");
        for (MessageSystem msg : messages) {
            System.out.println(msg.getShortDetails());
        }
    }
    
    private static void deleteMessageByHash() {
        String hash = getStringInput("Enter message hash to delete: ");
        if (MessageStorage.deleteMessageByHash(hash)) {
            System.out.println("Message with hash " + hash + " deleted successfully.");
        } else {
            System.out.println("Message with hash " + hash + " not found.");
        }
    }
    
    private static void displayLongestMessage() {
        MessageSystem longest = MessageStorage.getLongestMessage();
        if (longest != null) {
            System.out.println("\n--- Longest Message ---");
            System.out.println(longest.getMessageDetails());
            System.out.println("Length: " + longest.getMessageLength() + " characters");
        } else {
            System.out.println("No messages in the system.");
        }
    }
    
    private static void generateFullReport() {
        System.out.println("\n=== FULL MESSAGE REPORT ===");
        System.out.println("Total messages: " + MessageStorage.getAllMessages().size());
        System.out.println("Sent messages: " + MessageStorage.getSentMessages().size());
        System.out.println("Stored messages: " + MessageStorage.getStoredMessages().size());
        System.out.println("Disregarded messages: " + MessageStorage.getDisregardedMessages().size());
        
        System.out.println("\n--- Message Statistics ---");
        MessageSystem longest = MessageStorage.getLongestMessage();
        if (longest != null) {
            System.out.println("Longest message (" + longest.getMessageLength() + " chars):");
            System.out.println("  From: " + longest.getSender());
            System.out.println("  To: " + longest.getRecipient());
            System.out.println("  Content: " + longest.getContent());
        }
        
        System.out.println("\n--- All Message Hashes ---");
        for (String hash : MessageStorage.getMessageHashes()) {
            System.out.println(hash);
        }
        
        System.out.println("\n--- All Message IDs ---");
        for (int id : MessageStorage.getMessageIds()) {
            System.out.println(id);
        }
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}