package local.host.thon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import androidx.annotation.NonNull





@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment1: Fragment = Home()
        val fragment = arrayOf(Home(),Summary(),Estimate(),Profile())
        val fragment4: Fragment = Profile()
        val fm: FragmentManager = supportFragmentManager
        var active: Fragment = fragment[0]

        fm.beginTransaction().add(R.id.main_container, fragment[3], "4").hide(fragment[3]).commit();
        fm.beginTransaction().add(R.id.main_container, fragment[2], "2").hide(fragment[2]).commit();
        fm.beginTransaction().add(R.id.main_container, fragment[1], "2").hide(fragment[1]).commit();
        fm.beginTransaction().add(R.id.main_container,fragment[0], "1").commit();

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener{ item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        fm.beginTransaction().hide(active).show(fragment[0]).commit()
                        active = fragment[0]
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_summary -> {
                        fm.beginTransaction().hide(active).show(fragment[1]).commit()
                        active = fragment[1]
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_estimate -> {
                        fm.beginTransaction().hide(active).show(fragment[2]).commit()
                        active = fragment[2]
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_profile -> {
                        fm.beginTransaction().hide(active).show(fragment[3]).commit()
                        active = fragment[3]
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> {
                        return@setOnNavigationItemSelectedListener false
                    }
                }
            }

    }

}