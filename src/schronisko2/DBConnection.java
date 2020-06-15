/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schronisko2;

import java.sql.*;
import javafx.scene.control.Alert;

public class DBConnection {
public static Integer debug =0;
    private static Connection conn;

    public static Connection getConnection() {
        String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        String DB_USER = "mdabkow1";
        String DB_PASS = "mdabkow1";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            if(debug == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You are connected to database...");
                alert.show();
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error to database connection");
            alert.setContentText("Details: " + exc.getMessage());
            alert.show();
        }
        return conn;
    }
}
