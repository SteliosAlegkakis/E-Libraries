/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.Book;
import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Librarian;

/**
 *
 * @author Mike
 */
public class EditBooksTable {

    public static void addBookFromJSON(String json) throws ClassNotFoundException, SQLException {
        Book bt = jsonToBook(json);
        createNewBook(bt);
    }

    public static Book jsonToBook(String json) {
        Gson gson = new Gson();
        Book btest = gson.fromJson(json, Book.class);
        return btest;
    }

    public static String bookToJSON(Book bt) {
        Gson gson = new Gson();

        String json = gson.toJson(bt, Book.class);
        return json;
    }

    public static ArrayList<Book> databaseToBooks() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Book> books = new ArrayList<Book>();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM books");
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Book book = gson.fromJson(json, Book.class);
            books.add(book);
        }

        return books;
    }

    public static ArrayList<Book> databaseToBooks(String genre) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Book> books = new ArrayList<Book>();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM books WHERE genre= '" + genre + "'");
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Book book = gson.fromJson(json, Book.class);
            books.add(book);
        }

        return books;
    }

    public static Book databaseToBook(String title) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Book book = null;
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM books WHERE title= '" + title + "'");

        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            book = gson.fromJson(json, Book.class);
        }

        return book;
    }

    public static void updateBook(String isbn, String url) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Book bt = new Book();

        String update = "UPDATE books SET url='" + url + "'" + "WHERE isbn = '" + isbn + "'";
        stmt.executeUpdate(update);
    }

    public static void deleteBook(String isbn) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM books WHERE isbn='" + isbn + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    public static void createBooksTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE books "
                + "(isbn VARCHAR(13) not NULL, "
                + "title VARCHAR(500) not null,"
                + "authors VARCHAR(500)  not null, "
                + "genre VARCHAR(500)  not null, "
                + "pages INTEGER not null , "
                + "publicationyear INTEGER not null , "
                + "url VARCHAR (500), "
                + "photo VARCHAR (500), "
                + "PRIMARY KEY ( isbn ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public static void createNewBook(Book bt) throws ClassNotFoundException, SQLException {
        Connection con = DB_Connection.getConnection();

        Statement stmt = con.createStatement();

        String insertQuery = "INSERT INTO "
                + " books (isbn,title,authors,genre,pages,publicationyear,url,photo) "
                + " VALUES ("
                + "'" + bt.getIsbn() + "',"
                + "'" + bt.getTitle() + "',"
                + "'" + bt.getAuthors() + "',"
                + "'" + bt.getGenre() + "',"
                + "'" + bt.getPages() + "',"
                + "'" + bt.getPublicationyear() + "',"
                + "'" + bt.getUrl() + "',"
                + "'" + bt.getPhoto() + "'"
                + ")";
        //stmt.execute(table);
        System.out.println(insertQuery);
        stmt.executeUpdate(insertQuery);
        System.out.println("# The book was successfully added in the database.");

        /* Get the member id from the database and set it to the member */
        stmt.close();

    }
}
