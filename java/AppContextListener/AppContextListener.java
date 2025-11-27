/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.AppContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 *
 * @author anton
 */

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 1) Apagar hilo de limpieza de MySQL (firma cambia según versión del conector)
        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Throwable t) {
            // Ignorar: en algunas versiones no lanza nada; en otras puede lanzar runtime
        }

        // 2) Desregistrar drivers JDBC para evitar memory leaks en redeploy
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver d = drivers.nextElement();
                try { DriverManager.deregisterDriver(d); } catch (Exception ignore) {}
            }
        } catch (Throwable ignore) {}
    }
}