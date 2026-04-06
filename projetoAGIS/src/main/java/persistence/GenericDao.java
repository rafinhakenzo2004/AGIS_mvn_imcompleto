package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SOLID: Single Responsibility Principle (SRP)
 * Esta classe possui a responsabilidade única de gerenciar a persistência 
 * dos dados de Conexão, não interferindo em regras de negócio complexas ou conexão bruta.
 */

public class GenericDao {

    private Connection c;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String hostName = "DESKTOP-J7FKVMO";
        String dbName = "Agis";
        String user = "sa";
        String senha = "12345678";
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        
        String url = String.format(
                "jdbc:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s;"
                + "encrypt=true;trustServerCertificate=true;", 
                hostName, dbName, user, senha);

        c = DriverManager.getConnection(url);

        return c;
    }
}