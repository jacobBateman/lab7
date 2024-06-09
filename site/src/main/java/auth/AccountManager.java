package auth;

import org.mindrot.jbcrypt.BCrypt;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
