/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.project;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Matt
 */
public class DBConnection {
    
    private static final String databaseName = "U04EQF";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username = "U04EQF";
    private static final String password = "53688216212";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn = null;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
        if (conn == null) {
            conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
            System.out.println("Connection Successful!");
        }
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection closed.");
    }
}

