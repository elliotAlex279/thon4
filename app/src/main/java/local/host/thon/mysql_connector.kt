package local.host.thon;

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement

class mysql_connector{

fun main() = runBlocking{
    try {
        // The newInstance() call is a work around for some
        // broken Java implementations
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
        val conn =
            DriverManager.getConnection("jdbc:mysql://remotemysql.com/WKTDxt18Em?user=WKTDxt18Em&password=2g87yWwn89");
            val st = conn.createStatement()
            val kt = st.executeQuery("select * from dataSet")
        while (kt.next()) println(kt.getInt(1))
    } catch (ex: Exception) {
        print(ex)
    }
}
}
