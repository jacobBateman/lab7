package org.example;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect("test.db");
        Connection con = dbConnection.getConnection();


        try{
            Statement stmt = con.createStatement();

            //Tworzenie tabeli
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY , name TEXT, age INTEGER)";
            stmt.execute(createTableSQL);

            //Wstawianie danych
            String insertDataSql = "INSERT INTO users (id, name) VALUES('Alice',30), ('Jane',21)";
            stmt.execute(insertDataSql);

            //Odczytywanie danych
            String selectDataSql = "SELECT * FROM users;";
            ResultSet rs = stmt.executeQuery(selectDataSql);

            while(rs.next()){
                System.out.println("Name: " + rs.getInt("id")+ " Age: " + rs.getString("name"));
                rs.close();
                stmt.close();
            }
        }
        catch (java.sql.SQLException e){
            System.out.println(e);
        } finally {
            dbConnection.disconnect();
        }
    }
}