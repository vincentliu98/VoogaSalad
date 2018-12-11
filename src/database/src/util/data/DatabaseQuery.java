package util.data;

import java.sql.ResultSet;

public interface DatabaseQuery {

    /**
     * @param username
     * @param databasename
     * @param password
     * @param servername
     * @param port
     */
    void setConnection(String username, String databasename, String password, String servername, int port);

    /**
     * Allows the query of the server through a mysql command.
     * setConnection must be called first.
     *
     * @param command The mysql command
     * @return The result of the query
     */
    ResultSet queryServer(String command);


}
