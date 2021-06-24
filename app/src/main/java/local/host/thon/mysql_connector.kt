package local.host.thon;

import java.lang.Exception
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement

fun main(){
    try {
        // The newInstance() call is a work around for some
        // broken Java implementations
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
//        val url = "jdbc:mysql//localhost:3306/store-DB";
//        val conn = DriverManager.getConnection(url,"root","qazxsw@123");
//        val dt : Statement =  conn.createStatement();
//        val ts = dt.executeQuery( "SELECT * FROM orderlist")
//        while (ts.next()){
//            println(ts.getInt(1))
//        }
        val conn =
            DriverManager.getConnection("jdbc:mysql://remotemysql.com/WKTDxt18Em?user=WKTDxt18Em&password=2g87yWwn89");
            val st = conn.createStatement()
            val kt = st.executeQuery("select * from dataSet")
        while (kt.next()) println(kt.getInt(1))
    } catch (ex: Exception) {
        print(ex)
    }

}
