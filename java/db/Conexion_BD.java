/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anton
 */
public class Conexion_BD {
    private static final String URL  = "jdbc:mysql://localhost:3306/concretos_web?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";     // ajusta
    private static final String PASS = "";         // ajusta


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }static {
  try { Class.forName("com.mysql.cj.jdbc.Driver"); }
  catch (ClassNotFoundException e) { throw new RuntimeException("Driver MySQL no encontrado", e); }
}

}

