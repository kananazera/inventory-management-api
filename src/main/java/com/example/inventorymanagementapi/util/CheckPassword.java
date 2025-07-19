package com.example.inventorymanagementapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CheckPassword {
    public static void main(String[] args) {
        String rawPassword = "12345";
        String hashedPassword = "$2a$10$BRzsxMP2TSGDHLmyZS/Dde.AcEGwL0NzpBURsEstBumJS9zuiqZPO";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches(rawPassword, hashedPassword));
    }
}