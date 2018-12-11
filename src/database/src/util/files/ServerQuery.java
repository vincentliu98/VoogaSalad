package util.files;

public interface ServerQuery {

    /**
     * Sets up a sftp connection with a server.
     *
     * @param username The name of the server user
     * @param host     The host server
     * @param port     The port of the sftp daemon
     * @param password The password of the user
     */
    void connectServer(String username, String host, int port, String password);

    /**
     * Downloads a file from the server defined in the connectServer call to a destination.
     * connectServer must be called first.
     *
     * @param filePath        The path of the file to be downloaded
     * @param fileDestination The path of the download destination
     */
    void downloadFile(String filePath, String fileDestination);

    /**
     * Downloads a directory from the server defined in the connectServer call to a destination.
     * connectServer must be called first.
     *
     * @param directoryPath        The path of the directory on the server
     * @param directoryDestination The path of the destination
     */
    void dowloadDirectory(String directoryPath, String directoryDestination, String directoryName);

    /**
     * Sets up the class to use the full path passed in the uploadFile parameters.
     * Not default setting.
     */
    void useFullPath();

    /**
     * Sets up the class to use the preset path defined internally.
     * Default setting.
     */
    void usePresetPath();
}
