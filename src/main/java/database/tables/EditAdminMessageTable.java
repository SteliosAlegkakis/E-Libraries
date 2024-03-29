/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.AdminMessage;

/**
 *
 * @author mountant
 */
public class EditAdminMessageTable {

    public static void addAdminMessageFromJSON(String json) throws ClassNotFoundException, SQLException {
        AdminMessage msg = jsonToAdminMessage(json);
        createNewAdminMessage(msg);
    }

    public static AdminMessage jsonToAdminMessage(String json) {
        Gson gson = new Gson();
        AdminMessage msg = gson.fromJson(json, AdminMessage.class);
        return msg;
    }

    public static String reviewToJSON(AdminMessage msg) {
        Gson gson = new Gson();

        String json = gson.toJson(msg, AdminMessage.class);
        return json;
    }

    public static AdminMessage databaseToAdminMessage(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM adminmessages WHERE message_id= '" + id + "'");
        rs.next();
        String json = DB_Connection.getResultsToJSON(rs);
        Gson gson = new Gson();
        AdminMessage bt = gson.fromJson(json, AdminMessage.class);
        return bt;
    }

    public static void createAdminMessageTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE adminmessages "
                + "(message_id INTEGER not NULL AUTO_INCREMENT, "
                + "message VARCHAR(500) not NULL, "
                + "date DATE not null,"
                + "PRIMARY KEY ( message_id ))";
        ;
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public static void createNewAdminMessage(AdminMessage msg) throws ClassNotFoundException, SQLException {
        Connection con = DB_Connection.getConnection();

        Statement stmt = con.createStatement();

        String insertQuery = "INSERT INTO "
                + " adminmessages (message,date) "
                + " VALUES ("
                + "'" + msg.getMessage() + "',"
                + "'" + msg.getDate() + "'"
                + ")";
        //stmt.execute(table);
        System.out.println(insertQuery);
        stmt.executeUpdate(insertQuery);
        System.out.println("# The message was successfully added in the database.");

        /* Get the member id from the database and set it to the member */
        stmt.close();
    }

}
