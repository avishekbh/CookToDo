package com.example.prateep.attempt3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by prateep on 3/2/17.
 */

public class Database {

    public ResultSet data(String table_name) throws ClassNotFoundException, SQLException {

        System.out.print("pdh");
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        Statement stmt = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CookToDo","root","das123");
        stmt = conn.createStatement();
        System.out.print("abc");
        String sql = "SELECT * FROM "+table_name+";";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.print("def");
        System.out.println(rs);
        return rs;
    }
}
