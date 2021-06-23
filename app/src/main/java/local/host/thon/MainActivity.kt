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
import android.media.MediaPlayer







class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment1: Fragment = Home()
        val fragment = arrayOf(Home(),Summary(),Estimate(),Profile())
        val fragment4: Fragment = Profile()
//        getActivity().getSupportFragmentManager().beginTransaction();

        val fm: FragmentManager = this.supportFragmentManager
        var active: Fragment = fragment[0]
//        fm.beginTransaction().add(R.id.main_container, fragment[3], "4").hide(fragment[3]).commit();
//        fm.beginTransaction().add(R.id.main_container, fragment[2], "3").hide(fragment[2]).commit();
//        fm.beginTransaction().add(R.id.main_container, fragment[1], "2").hide(fragment[1]).commit();
        fm.beginTransaction().add(R.id.main_container,fragment[0], "1").commit();
        val mPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.sjli)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener{ item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        fm.beginTransaction().replace(R.id.main_container,Home()).commit()
                        active = fragment[0]
                        mPlayer.pause()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_summary -> {
                        fm.beginTransaction().replace(R.id.main_container,Summary()).commit()
                        active = fragment[1]
                        mPlayer.pause()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_estimate -> {
                        fm.beginTransaction().replace(R.id.main_container,Estimate()).commit()
//                        fm.beginTransaction().hide(active).show(fragment[2]).commit()
                        active = fragment[2]
                        mPlayer.pause()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_profile -> {
//                        fm.beginTransaction().hide(active).show(fragment[3]).commit()
                        fm.beginTransaction().replace(R.id.main_container,Profile()).commit()
                        active = fragment[3]
                        mPlayer.start()
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> {
                        return@setOnNavigationItemSelectedListener false
                    }
                }
            }

    }

}