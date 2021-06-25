package local.host.thon;

import android.annotation.SuppressLint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun main(): Unit = runBlocking{
    val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
    val dt = Date(dateFormat.format(Date()) + " 6:00 AM");


//    launch {
//    try {
//        // The newInstance() call is a work around for some
//        // broken Java implementations
//        Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
//        val conn =
//            DriverManager.getConnection("jdbc:mysql://remotemysql.com/WKTDxt18Em?user=WKTDxt18Em&password=2g87yWwn89");
//            val st = conn.createStatement()
//            val kt = st.executeQuery("select * from dataSet")
//        while (kt.next()) println(kt.getInt(1))
//    } catch (ex: Exception) {
//        print(ex)
//    }
//    }
}

