/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.validation;

/**
 *
 * @author anton
 */

public class SimpleValidators {

    public static boolean notBlank(String s){
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isPositive(Double n){
        return n != null && n > 0;
    }

    public static boolean isPositiveInt(Integer n){
        return n != null && n > 0;
    }
}