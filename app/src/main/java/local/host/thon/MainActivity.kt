package local.host.thon

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.text.MessageFormat.format
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.format.DateFormat.format
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception
import java.lang.String.format
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    private fun convertDate(dateInMilliseconds: Long): String? {
        val formatter: DateFormat = SimpleDateFormat("HH:mm")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateInMilliseconds
        return formatter.format(calendar.time)
    }

    @SuppressLint("SetTextI18n")
    private fun checkWeather(){
//        val publicIP = (URL("http://www.checkip.org").readText().split("<span style=\"color: #5d9bD3;\">")[1]).split("</span>")[0]
//        val location = JSONObject(URL("http://api.ipstack.com/$publicIP?access_key=d8d57e041b8d5da9101ba4fbde602b6b").readText());
//        val dt = JSONObject(URL("http://api.weatherapi.com/v1/current.json?key=b0b2771b21d540478e565638212206&q=${location["latitude"]},${location["longitude"]}&aqi=no").readText())
//        val jobj = ((dt["current"] as JSONObject)["condition"] as JSONObject)
//        val dtp = arrayOf("${(dt["current"] as JSONObject)["temp_c"]}","${jobj["text"]}");
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.checkip.org"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val publicIP =
                    response.split("<span style=\"color: #5d9bD3;\">")[1].split("</span>")[0];
                val url2 =
                    "http://api.ipstack.com/$publicIP?access_key=d8d57e041b8d5da9101ba4fbde602b6b"

                val stringRequest2 = StringRequest(
                    Request.Method.GET, url2,
                    { responseA ->
                        val location = JSONObject(responseA);
                        val url3 = "http://api.weatherapi.com/v1/current.json?key=b0b2771b21d540478e565638212206&q=${location["latitude"]},${location["longitude"]}&aqi=no"
                        val stringRequest3 = StringRequest(
                            Request.Method.GET, url3,
                            { responseB ->
                                val dt = JSONObject(responseB)
                                val jobj = ((dt["current"] as JSONObject)["condition"] as JSONObject)
                                val dtp = arrayOf("${(dt["current"] as JSONObject)["temp_c"]}","${jobj["text"]}");
                                Picasso.get()
                                    .load("https:${jobj["icon"]}")
                                    .into(findViewById(R.id.curTempImg), object : Callback {
                                        @SuppressLint("SetTextI18n")
                                        override fun onSuccess() {
                                            findViewById<TextView>(R.id.temp_curr).text = dtp[0].toFloat().toInt().toString() + "°C"
                                            findViewById<TextView>(R.id.temp_stat).text = dtp[1]
                                            findViewById<ProgressBar>(R.id.loaderWeather).visibility = View.GONE
                                            findViewById<RelativeLayout>(R.id.loadedWeather).visibility = View.VISIBLE
                                        }

                                        override fun onError(e: Exception?) {
                                            Toast.makeText(this@MainActivity,"Internet is off :( ",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            }, {})
                        queue.add(stringRequest3)
                        // Display the first 500 characters of the response string.
                    },
                    { })
                queue.add(stringRequest2)
            },{})

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val publicIP = (URL("http://www.checkip.org").readText().split("<span style=\"color: #5d9bD3;\">")[1]).split("</span>")[0]
        val location = JSONObject(URL("http://api.ipstack.com/$publicIP?access_key=d8d57e041b8d5da9101ba4fbde602b6b").readText());
        val dt = JSONObject(URL("http://api.weatherapi.com/v1/current.json?key=b0b2771b21d540478e565638212206&q=${location["latitude"]},${location["longitude"]}&aqi=no").readText())
//        Log.d("K","https://api.sunrise-sunset.org/json?lat=${location["latitude"]}&lng=${location["longitude"]}");
//        var dtp = Date().formatTo("yyyy-MM-dd")



        checkWeather()
        findViewById<CardView>(R.id.weather_card).setOnClickListener {
            findViewById<RelativeLayout>(R.id.loadedWeather).visibility = View.GONE
            findViewById<ProgressBar>(R.id.loaderWeather).visibility = View.VISIBLE
            checkWeather()
        }

    }

    private fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)!!
    }

    private fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }

}