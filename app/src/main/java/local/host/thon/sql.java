package local.host.thon;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class sql {

    public static Connection getconnectio() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = null;
        String url = "jdbc:mysql://WKTDxt18Em@remotemysql.com:3306/WKTDxt18Em";
        String user = "WKTDxt18Em";
        String pass = "2g87yWwn89";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
}
