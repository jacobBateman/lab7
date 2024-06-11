package auth;

import org.mindrot.jbcrypt.BCrypt;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountManager {
    private final DatabaseConnection dbConnection;

    public AccountManager(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean register(String username, String password){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try(
            Connection con = dbConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)")){
            pstmt.setString(1,username);
            pstmt.setString(2,hashedPassword);
            pstmt.executeUpdate();
            return true;

        }catch(java.sql.SQLException e){
            return false;
        }
    }

    public boolean authenticate(String username, String password){
        try{
            Connection con = dbConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users;");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String username_db = rs.getString("username");
                String password_db = rs.getString("password");
                System.out.println(username_db);
                System.out.println(password_db);
                rs.close();
                pstmt.close();
                if(username_db.equals(username) && BCrypt.checkpw(password,password_db)){
                    return true;
                }
            }
        }catch(java.sql.SQLException e){
            return false;
        }
        return false;
    }

    public Account account(){
        Connection con = dbConnection.getConnection();
        try {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = pstm.executeQuery();
            Account new_account  = new Account(rs.getInt("id"),rs.getString("username"));
            return new_account;
        }catch(java.sql.SQLException e){
            System.out.println(e);
        }
        return null;
    }



}
