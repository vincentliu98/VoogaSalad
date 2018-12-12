package util.data;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDownloader extends DatabaseConnector implements DatabaseQuery {

    public DatabaseDownloader(String username, String databasename, String password, String servername, int port) {
        super();
        this.setConnection(username, databasename, password, servername, port);
    }

    public ResultSet queryServer(String command) {
        try {
            Connection conn = myDataSrc.getConnection();
            Statement stmt = conn.createStatement();
            mySet = stmt.executeQuery(command);
            return mySet;
        } catch (SQLException ex) {
            System.out.println("Invalid SQl Command");
        }
        return null;
    }

}
