package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String databsePath){
        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:"+databsePath);
            System.out.println("Connected to database!");
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
        }
    }

    public void disconnect(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
                System.out.println("Disconnected from database");
            }
        }
        catch(java.sql.SQLException e){
            System.out.println(e);
        }
    }
}
