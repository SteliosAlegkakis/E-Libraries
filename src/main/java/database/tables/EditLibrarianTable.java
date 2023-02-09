/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.Librarian;
import com.google.gson.Gson;
import mainClasses.User;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Student;

/**
 *
 * @author Mike
 */
public class EditLibrarianTable {

    public static void addLibrarianFromJSON(String json) throws ClassNotFoundException, SQLException {
        Librarian lib = jsonToLibrarian(json);
        addNewLibrarian(lib);
    }

    public static Librarian jsonToLibrarian(String json) {
        Gson gson = new Gson();

        Librarian lib = gson.fromJson(json, Librarian.class);
        return lib;
    }

    public static String librarianToJSON(Librarian lib) {
        Gson gson = new Gson();

        String json = gson.toJson(lib, Librarian.class);
        return json;
    }

    public static void updateLibrarian(String username, String personalpage) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE librarians SET personalpage='" +  personalpage + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
    }

    public static void printLibrarianDetails(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM librarians WHERE username = '" + username + "' AND password='" + password + "'");
        while (rs.next()) {
            System.out.println("===Result===");
            DB_Connection.printResults(rs);
        }
    }

    public static Librarian databaseToLibrarian(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM librarians WHERE username = '" + username + "' AND password='" + password + "'");
            System.out.println("SELECT * FROM librarians WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Librarian lib = gson.fromJson(json, Librarian.class);
            return lib;
        }
        catch(Exception e) {}

        return null;

    }

    public static Librarian databaseUsernameExistsInLibrarians(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM librarians WHERE username = '" + username + "'");
        System.out.println("SELECT * FROM Librarians WHERE username = '" + username +"'");
        rs.next();
        String json = DB_Connection.getResultsToJSON(rs);
        Gson gson = new Gson();
        Librarian lib = gson.fromJson(json, Librarian.class);

        return lib;

    }

    public static Librarian databaseLibrarianEmailExists(String email) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM librarians WHERE email = '" + email + "'");
        System.out.println("SELECT * FROM Librarians WHERE email = '" + email +"'");
        rs.next();
        String json = DB_Connection.getResultsToJSON(rs);
        Gson gson = new Gson();
        Librarian lib = gson.fromJson(json, Librarian.class);
        return lib;
    }

    public static ArrayList<Librarian> databaseToLibrarians() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Librarian> librarians=new ArrayList<Librarian>();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM librarians");
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Librarian lib = gson.fromJson(json, Librarian.class);
            librarians.add(lib);
        }
        return librarians;
    }


    public static void createLibrariansTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE librarians"
                + "(library_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(200) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(30) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    country VARCHAR(30) not null,"
                + "    city VARCHAR(50) not null,"
                + "    address VARCHAR(50) not null,"
                + "    libraryname VARCHAR(100) not null,"
                + "    libraryinfo VARCHAR(1000) not null,"
                + "    lat DOUBLE,"
                + "    lon DOUBLE,"
                + "    telephone VARCHAR(14),"
                + "    personalpage VARCHAR(200),"
                + " PRIMARY KEY (library_id))";
        stmt.execute(query);
        stmt.close();
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public static void addNewLibrarian(Librarian lib) throws ClassNotFoundException, SQLException {
        Connection con = DB_Connection.getConnection();

        Statement stmt = con.createStatement();

        String insertQuery = "INSERT INTO "
                + " librarians (username,email,password,firstname,lastname,birthdate,gender,country,city,address,"
                + "libraryname,libraryinfo,lat,lon,telephone,personalpage)"
                + " VALUES ("
                + "'" + lib.getUsername() + "',"
                + "'" + lib.getEmail() + "',"
                + "'" + lib.getPassword() + "',"
                + "'" + lib.getFirstname() + "',"
                + "'" + lib.getLastname() + "',"
                + "'" + lib.getBirthdate() + "',"
                + "'" + lib.getGender() + "',"
                + "'" + lib.getCountry() + "',"
                + "'" + lib.getCity() + "',"
                + "'" + lib.getAddress() + "',"
                + "'" + lib.getLibraryname()+ "',"
                + "'" + lib.getLibraryinfo()+ "',"
                + "'" + lib.getLat() + "',"
                + "'" + lib.getLon() + "',"
                + "'" + lib.getTelephone() + "',"
                + "'" + lib.getPersonalpage()+ "'"
                + ")";
        //stmt.execute(table);
        System.out.println(insertQuery);
        stmt.executeUpdate(insertQuery);
        System.out.println("# The libarian was successfully added in the database.");

        /* Get the member id from the database and set it to the member */
        stmt.close();
    }

    public static void updateLibrarianPage(String username,String personalpage) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE librarians SET personalpage='"+personalpage+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }

    public static void updateLibrarianCountry(String username,String country) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE librarians SET country='"+country+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }

    public static void updateLibrarianCity(String username,String city) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE librarians SET city='"+city+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }

    public static void updateLibrarianPassword(String username,String password) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE librarians SET password='"+password+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }

    public static void updateLibrarianAddress(String username,String address) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE librarians SET address='"+address+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }

    public static void updateLibrarianTelephone(String username,String telephone) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE librarians SET telephone='"+telephone+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }

}
