package utilitaire;
import java.sql.*;
public class Connect {
 
    public  Connection dbConnect(String base)throws SQLException{
        if (base.equals("")) {
            throw new IllegalArgumentException("le parametre de la connection n'est pas valide");

        }
        Connection connex = null;
        String username ="";
        String password ="";
        String url ="";
        if (base.equals("postgres")) {
            url = "jdbc:postgresql://localhost:5432/restaurant";
            username = "postgres";
            password="postgresl2";
        }

        if (!url.isEmpty() && !username.isEmpty() &&  !password.isEmpty()) {
            connex = DriverManager.getConnection(url, username, password);            
        }

        return connex;
    }
    
}
