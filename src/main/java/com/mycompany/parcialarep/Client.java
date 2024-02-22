/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parcialarep;

import java.io.IOException;

/**
 *
 * @author gabriel.silva
 */
public class Client {
     public static void main(String[] args) {
       
    }
    
    public static void testServerFunctionality() throws IOException {
        HttpConnectionExample httpConnection = new HttpConnectionExample();

        String[] nameClass = {"java.base", "java.compiler", "java.transfer"};

        for (String name : nameClass) {
            String response = httpConnection.getClass(name);
            System.out.println("clase: " + name);
            System.out.println(response);
            System.out.println();
        }
    }
    
}
