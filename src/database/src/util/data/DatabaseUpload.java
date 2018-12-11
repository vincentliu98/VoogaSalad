package util.data;

public interface DatabaseUpload {

    /**
     * @param username
     * @param databasename
     * @param password
     * @param servername
     * @param port
     */
    void setConnection(String username, String databasename, String password, String servername, int port);

    void upload(String command);

}
