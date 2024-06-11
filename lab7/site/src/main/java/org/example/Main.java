package org.example;

import auth.AccountManager;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect("C:\\Users\\jakub\\IdeaProjects\\lab7\\site\\src\\test.db");
        Connection con = dbConnection.getConnection();


        try{
            Statement stmt = con.createStatement();

            //Tworzenie tabeli
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY , username TEXT, password TEXT)";
            stmt.execute(createTableSQL);

            //Wstawianie danych
            String insertDataSql = "INSERT INTO users (id, username,password) VALUES(1,'Laslo','Negro77'), (2,'Malahowsky','icecream420')";
            stmt.execute(insertDataSql);

            //Odczytywanie danych
            String selectDataSql = "SELECT * FROM users;";
            ResultSet rs = stmt.executeQuery(selectDataSql);

            while(rs.next()){
                System.out.println("Name: " + rs.getString("username")+ " Password: " + rs.getString("password"));

            }

            rs.close();
            stmt.close();

            AccountManager mn = new AccountManager(dbConnection);
            mn.register("Pier","Monkey89");
            System.out.println(mn.authenticate("Pier","Monkey89"));



        }
        catch (java.sql.SQLException e){
            System.out.println(e);
        } finally {
            dbConnection.disconnect();
        }
    }
}