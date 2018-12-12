package util.data;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.ResultSet;

//import java.sql.ResultSet;

public class DatabaseConnector {
//    public static final String USER_NAME = "client";
//    public static final String DATABASE_NAME = "store";
//    public static final String PASSWORD = "e.printstacktrace";
//    public static final String SERVER_NAME = "vcm-7456.vm.duke.edu";
//    public static final int PORT = 3306;

    protected MysqlDataSource myDataSrc;
    protected ResultSet mySet;

    public DatabaseConnector() {
    }

    public void setConnection(String username, String databasename, String password, String servername, int port) {
        myDataSrc = new MysqlDataSource();
        myDataSrc.setUser(username);
        myDataSrc.setDatabaseName(databasename);
        myDataSrc.setPassword(password);
        myDataSrc.setServerName(servername);
        myDataSrc.setPort(port);
    }


}
