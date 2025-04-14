/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poe_part_a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
 */
public class LogInTest {

    private LogIn user;
   
    
    @BeforeEach
    void setUp() {
        user = new LogIn("Kyle", "Smith", null, null, null, false, false);
    }

    // =============== USERNAME TESTS ===============
    @Test
    void testUsername_ValidFormat() {
        user.setUserName("kyl_1");
        assertEquals("Username successfully captured.", user.checkUserName());
    }

    @Test
    void testUsername_InvalidFormat() {
        user.setUserName("kyle!!!!!!");
        assertEquals("Username is not correctly formatted...", user.checkUserName());
    }

    @Test
    void testUsername_Exactly5Chars() {
        user.setUserName("a_b_c");  // Exactly 5 characters with underscore
        assertEquals("Username successfully captured.", user.checkUserName());
    }

    //PASSWORD TESTS
    @Test
    void testPassword_ValidComplex() {
        user.setPassword("Ch&&sec@ke99!");
        assertEquals("Password successfully captured.", user.checkPassword());
    }

    @Test
    void testPassword_InvalidSimple() {
        user.setPassword("invalid");
        assertEquals("Password is not correctly formatted...", user.checkPassword());
    }

    @Test
    void testPassword_MinimumComplex() {
        user.setPassword("A1@abcd");  // Exactly 8 chars with all requirements
        assertEquals("Password is not correctly formatted...", user.checkPassword());
    }

    // CELLPHONE TESTS
    @Test
    void testCellphone_InvalidLocalFormat() {
         user.setCellNumber("08966553");
    }

    @Test
    void testCellphone_ValidIntlFormat () {
        user.setCellNumber("+27838968976");
    }

    @Test
    void testLogin_Failure() {
        assertFalse(user.loginUser("wrong", "wrong"));
        assertEquals("Username or password incorrect, please try again.",
                    user.returnLoginStatus());
    }
}