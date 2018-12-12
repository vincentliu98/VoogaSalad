package util.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUploader extends DatabaseConnector implements DatabaseUpload {

    public DatabaseUploader(String username, String databasename, String password, String servername, int port) {
        super();
        this.setConnection(username, databasename, password, servername, port);
    }

    @Override
    public void upload(String command) {
        try {
            Connection conn = myDataSrc.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(command);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Invalid SQl Command");
        }
    }
}
