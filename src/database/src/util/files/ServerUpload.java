package util.files;

public interface ServerUpload {

    /**
     * Sets up a sftp connection with a server.
     *
     * @param username The name of the server user
     * @param host The host server
     * @param port The port of the sftp daemon
     * @param password The password of the user
     */
    void connectServer(String username, String host, int port, String password);

    /**
     * Uploads a file from the server defined in the connectServer call to a destination.
     * connectServer must be called first.
     *
     * @param filePath The path of the file to be uploaded
     * @param fileDestination The path of the upload destination
     */
    void uploadFile(String filePath, String fileDestination);
}
