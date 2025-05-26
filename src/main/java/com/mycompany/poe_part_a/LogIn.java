/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_part_a;
/**
 *
 * @author RC_Student_lab
 */
public class LogIn {
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String cellNumber;
    private boolean isRegistered;
    private boolean isLoggedIn;

    // Constructor
    public LogIn(String firstName, String lastName, String userName, String passWord, 
                String cellNumber, boolean isRegistered, boolean isLoggedIn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.cellNumber = cellNumber;
        this.isRegistered = isRegistered;
        this.isLoggedIn = isLoggedIn;
    }

    //GETTER METHODS
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUserName() { return userName; }
    public String getCellNumber() { return cellNumber; }
    public boolean isRegistered() { return isRegistered; }
    public boolean isLoggedIn() { return isLoggedIn; }

    // SETTER METHODS
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.isRegistered = false;
        this.isLoggedIn = false;
    }

    public void setPassword(String passWord) {
        this.passWord = passWord;
        this.isRegistered = false;
        this.isLoggedIn = false;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
        this.isRegistered = false;
        this.isLoggedIn = false;
    }

    // VALIDATION METHODS
    public String checkUserName() {
        if (userName == null) {
            return "Username is not correctly formatted...";
        }
        return userName.contains("_") && userName.length() <= 5 
            ? "Username successfully captured." 
            : "Username is not correctly formatted...";
    }

    public String checkPassword() {
        if (passWord == null || passWord.length() < 8) {
            return getPasswordErrorMessage();
        }
        boolean hasCapital = !passWord.equals(passWord.toLowerCase());
        boolean hasNumber = passWord.matches(".*\\d.*");
        boolean hasSpecialChar = !passWord.matches("[A-Za-z0-9]*");
        
        return (hasCapital && hasNumber && hasSpecialChar)
            ? "Password successfully captured."
            : getPasswordErrorMessage();
    }

    private String getPasswordErrorMessage() {
        return "Password is not correctly formatted...";
    }

    public String checkCellphone() {
        if (cellNumber == null) {
            return "Cell phone number incorrectly formatted...";
        }
        return cellNumber.matches("^(\\+27|27|0)[67][0-9]{8}$")
            ? "Cell phone number successfully added."
            : "Cell phone number incorrectly formatted...";
    }

    //CORE FUNCTIONALITY
    public String registerUser() {
        String usernameCheck = checkUserName();
        if (!usernameCheck.equals("Username successfully captured.")) {
            return usernameCheck;
        }

        String passwordCheck = checkPassword();
        if (!passwordCheck.equals("Password successfully captured.")) {
            return passwordCheck;
        }

        String cellphoneCheck = checkCellphone();
        if (!cellphoneCheck.equals("Cell phone number successfully added.")) {
            return cellphoneCheck;
        }

        this.isRegistered = true;
        return "User registered successfully!";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (!isRegistered || enteredUsername == null || enteredPassword == null) {
            return false;
        }
        this.isLoggedIn = enteredUsername.equals(userName) && 
                         enteredPassword.equals(passWord);
        return this.isLoggedIn;
    }

    public String returnLoginStatus() {
        return isLoggedIn 
            ? "Welcome " + firstName + ", " + lastName + " It is great to see you again."
            : "Username or password incorrect, please try again.";
    }
}