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
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "SimpleDateFormat", "CutPasteId")
    private fun checkWeather(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.checkip.org"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val publicIP =
                    response.split("<span style=\"color: #5d9bD3;\">")[1].split("</span>")[0]
                val url2 =
                    "http://api.ipstack.com/$publicIP?access_key=d8d57e041b8d5da9101ba4fbde602b6b"

                val stringRequest2 = StringRequest(
                    Request.Method.GET, url2,
                    { responseA ->
                        val location = JSONObject(responseA)
                        val url3 = "http://api.weatherapi.com/v1/current.json?key=b0b2771b21d540478e565638212206&q=${location["latitude"]},${location["longitude"]}&aqi=no"

                        val url4 = "https://api.sunrise-sunset.org/json?lat=${location["latitude"]}&lng=${location["longitude"]}"
                        val stringRequest31 = StringRequest(
                            Request.Method.GET, url4,
                            { responseB1 ->
                                val obj = JSONObject(responseB1)
                                val k = obj["results"] as JSONObject
                                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                                val dateFormatA: DateFormat = SimpleDateFormat("h:mm")
                                val date = Date()
                                val dtn = Date(dateFormat.format(date) + " ${k["sunrise"]}")
                                val dtn2 = Date(dateFormat.format(date) + " ${k["sunset"]}")
//                                Log.d("K",dtn.time.toString())
                                val nsr = Date(dtn.time + 330*60000)

                                findViewById<TextView>(R.id.sunrise_time).text = HtmlCompat.fromHtml("<b>Sunrise</b><br>${dateFormatA.format(nsr)}",HtmlCompat.FROM_HTML_MODE_COMPACT)
                                findViewById<ProgressBar>(R.id.sunrise_loader).visibility = View.GONE
                                findViewById<TextView>(R.id.sunrise_time).visibility = View.VISIBLE
                                val nss = Date(dtn2.time + 330*60000)
                                findViewById<TextView>(R.id.sunset_time).text = HtmlCompat.fromHtml("<b>Sunset</b><br>${dateFormatA.format(nss)}",HtmlCompat.FROM_HTML_MODE_COMPACT)
                                findViewById<ProgressBar>(R.id.sunset_loader).visibility = View.GONE
                                findViewById<TextView>(R.id.sunset_time).visibility = View.VISIBLE
//                                Toast.makeText(this,Date(dateFormat.format(date) + " " +k["sunrise"].toString()).toLocaleString(),Toast.LENGTH_SHORT).show()
                            },{})
                        val stringRequest3 = StringRequest(
                            Request.Method.GET, url3,
                            { responseB ->
                                val dt = JSONObject(responseB)
                                val jobj = ((dt["current"] as JSONObject)["condition"] as JSONObject)
                                val dtp = arrayOf("${(dt["current"] as JSONObject)["temp_c"]}","${jobj["text"]}")
                                Picasso.get()
                                    .load("https:${jobj["icon"]}")
                                    .into(findViewById(R.id.curTempImg), object : Callback {
                                        @SuppressLint("SetTextI18n")
                                        override fun onSuccess() {
                                            findViewById<TextView>(R.id.temp_curr).text = dtp[0].toFloat().toInt().toString() + "°C"
                                            findViewById<TextView>(R.id.temp_stat).text = dtp[1]
                                            findViewById<TextView>(R.id.hum_data).text = HtmlCompat.fromHtml("<b>Humidity</b><br>${(dt["current"] as JSONObject)["humidity"]}",HtmlCompat.FROM_HTML_MODE_COMPACT)
                                            findViewById<TextView>(R.id.uv_text).text = HtmlCompat.fromHtml("<b>UV Index</b><br>${(dt["current"] as JSONObject)["uv"]}",HtmlCompat.FROM_HTML_MODE_COMPACT)
                                            findViewById<TextView>(R.id.hum_data).visibility = View.VISIBLE
                                            findViewById<TextView>(R.id.uv_text).visibility = View.VISIBLE
                                            findViewById<ProgressBar>(R.id.hum_loader).visibility = View.GONE
                                            findViewById<ProgressBar>(R.id.uv_loader).visibility = View.GONE
                                            findViewById<ProgressBar>(R.id.loaderWeather).visibility = View.GONE
                                            findViewById<LinearLayout>(R.id.loadedWeather).visibility = View.VISIBLE
                                        }

                                        override fun onError(e: Exception?) {
                                            Toast.makeText(this@MainActivity,"Internet is off :( ",Toast.LENGTH_SHORT).show()
                                        }
                                    })
                            }, {})
                        queue.add(stringRequest31)
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
//        val publicIP = (URL("http://www.checkip.org").readText().split("<span style=\"color: #5d9bD3;\">")[1]).split("</span>")[0]
//        val location = JSONObject(URL("http://api.ipstack.com/$publicIP?access_key=d8d57e041b8d5da9101ba4fbde602b6b").readText())
//        val dt = JSONObject(URL("http://api.weatherapi.com/v1/current.json?key=b0b2771b21d540478e565638212206&q=${location["latitude"]},${location["longitude"]}&aqi=no").readText())
//        Log.d("K","https://api.sunrise-sunset.org/json?lat=${location["latitude"]}&lng=${location["longitude"]}");
//        var dtp = Date().formatTo("yyyy-MM-dd")



        checkWeather()
        findViewById<CardView>(R.id.weather_card).setOnClickListener {
            findViewById<LinearLayout>(R.id.loadedWeather).visibility = View.GONE
            findViewById<ProgressBar>(R.id.loaderWeather).visibility = View.VISIBLE
            checkWeather()
        }

    }

}